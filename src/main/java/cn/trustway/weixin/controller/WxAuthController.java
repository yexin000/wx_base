package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.model.FollowModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.model.MessageModel;
import cn.trustway.weixin.pojo.WxSession;
import cn.trustway.weixin.redis.RedisUtil;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.AES;
import cn.trustway.weixin.util.HtmlUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序授权控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/wxAuth")
public class WxAuthController extends BaseController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WxAuthService wxAuthService;

    @Autowired
    private MiddleManService<MiddleMan> middleManService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired
    private FollowService<Follow> followService;

    @Autowired(required = false)
    private MessageService<Message> messageService;

    @Autowired
    UserAddrService<UserAddr> userAddrService;

    /**
     * 小程序授权登陆后解析用户数据，将用户数据写入数据库，并返回首页地址
     *
     * @param code
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSession")
    public void createSession(String code, HttpServletResponse response) throws Exception {
        WxSession wxSession = wxAuthService.getWxSession(code);

        if (null == wxSession || StringUtils.isBlank(wxSession.getOpenid())) {
            sendFailureMessage(response, "授权失败");
            return;
        }

        String thirdSession = wxAuthService.create3rdSession(wxSession.getOpenid(), wxSession.getSession_key());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("thirdSession", thirdSession);
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 获取用户openId和unionId数据(如果没绑定微信开放平台，解密数据中不包含unionId)
     *
     * @param encryptedData 加密数据
     * @param iv            加密算法的初始向量
     * @param thirdSession  会话ID
     * @return
     */
    @RequestMapping(value = "decodeUserInfo", produces = "application/json")
    public void decodeUserInfo(@RequestParam(value = "encryptedData") String encryptedData,
                                              @RequestParam(defaultValue = "iv") String iv,
                                              @RequestParam(defaultValue = "thirdSession") String thirdSession,
                                              HttpServletResponse response) {
        //从缓存中获取session_key
        Object wxSessionObj = redisUtil.get(thirdSession);
        if (null == wxSessionObj) {
            sendFailureMessage(response, "用户信息解析失败");
        }
        String wxSessionStr = (String) wxSessionObj;
        String sessionKey = wxSessionStr.split("#")[0];

        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData.getBytes("UTF-8")),
                    Base64.decodeBase64(sessionKey.getBytes("UTF-8")), Base64.decodeBase64(iv.getBytes("UTF-8")));
            if (null != resultByte && resultByte.length > 0) {
                String userInfo = new String(resultByte, "UTF-8");
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                jsonMap.put("userInfo", userInfo);

                // 用户数据写入数据库
                JSONObject userObj = new JSONObject().fromObject(userInfo);
                String wxid = userObj.getString("openId");
                if(StringUtils.isNotBlank(wxid)) {
                    WeixinUser weixinUser = weixinUserService.queryWeixinUser(wxid);
                    if (null == weixinUser) {
                        weixinUser = new WeixinUser();
                    }
                    weixinUser.setWxid(wxid);
                    weixinUser.setNickName(userObj.getString("nickName").replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
                    weixinUser.setAvatarUrl(userObj.getString("avatarUrl"));

                    try {
                        if(null != weixinUser.getId() && weixinUser.getId() > 0) {
                            weixinUserService.updateBySelective(weixinUser);
                        } else {
                            weixinUserService.add(weixinUser);
                        }

                        // 查询微信用户是否有经纪人
                        MiddleMan middleMan = middleManService.getMiddleManByWxid(wxid);
                        // 没有经纪人随机分配一个
                        if(null == middleMan) {
                            // 随机得到一个经纪人
                            MiddleMan randomMiddleMan = middleManService.getMiddleManByRandom();
                            if(null != randomMiddleMan) {
                                WxidMiddleman wxidMiddleman = new WxidMiddleman();
                                wxidMiddleman.setWxid(wxid);
                                wxidMiddleman.setMiddlemanId(randomMiddleMan.getId());
                                middleManService.addMiddleManToWxid(wxidMiddleman);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                HtmlUtil.writerJson(response, jsonMap);
            }
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendFailureMessage(response, "用户信息解析失败");
    }



    /**
     * 根据ID查找记录
     *
     * @param wxid
     * @param response
     * @return
     * search  auctionItem/ajaxGetId
     * @throws Exception
     */
    @RequestMapping("/ajaxGetId")
    public void ajaxGetId(String wxid, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        WeixinUser bean = weixinUserService.queryWeixinUser(wxid);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }

        FollowModel myFollowModel = new FollowModel();
        myFollowModel.setWxid(wxid);
        myFollowModel.setFollowType(1);
        int myFollowNum = followService.queryByCount(myFollowModel);
        bean.setMyFollowNum(myFollowNum);

        FollowModel followModel = new FollowModel();
        followModel.setFollowId(bean.getId());
        followModel.setFollowType(1);
        int followNum = followService.queryByCount(followModel);
        bean.setFollowNum(followNum);


        FollowModel followAuctionModel = new FollowModel();
        followAuctionModel.setWxid(wxid);
        followAuctionModel.setFollowType(3);
        int followAuctionNum = followService.queryByCount(followAuctionModel);
        bean.setMyFollowAuctionNum(followAuctionNum);

        FollowModel followAuctionItemModel = new FollowModel();
        followAuctionItemModel.setWxid(wxid);
        followAuctionItemModel.setFollowType(2);
        int followAuctionItemNum = followService.queryByCount(followAuctionItemModel);
        bean.setMyFollowAuctionItemNum(followAuctionItemNum);

        //查询我的消息
        MessageModel msgModel = new MessageModel();
        msgModel.setToWxid(wxid);
        msgModel.setStatus(0);
        bean.setMsgCount(messageService.queryByCount(msgModel));

        //需要收货地址
        // 查询用户默认收货地址
        UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(wxid);
        if(null == defaultAddr) {
            context.put("hasAddress", false);
        }else{
            context.put("hasAddress", true);
        }

        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }
}
