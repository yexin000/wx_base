package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.pojo.WxSession;
import cn.trustway.weixin.redis.RedisUtil;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.service.WxAuthService;
import cn.trustway.weixin.util.AES;
import cn.trustway.weixin.util.HtmlUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
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
    WxAuthService wxAuthService;

    @Autowired
    WeixinUserService weixinUserService;

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
                    weixinUser.setNickName(userObj.getString("nickName"));
                    weixinUser.setAvatarUrl(userObj.getString("avatarUrl"));

                    try {
                        if(null != weixinUser.getId() && weixinUser.getId() > 0) {
                            weixinUserService.updateBySelective(weixinUser);
                        } else {
                            weixinUserService.add(weixinUser);
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
}
