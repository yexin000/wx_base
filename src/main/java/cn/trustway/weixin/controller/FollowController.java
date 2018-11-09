package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ActivityModel;
import cn.trustway.weixin.model.BlacklistModel;
import cn.trustway.weixin.model.FollowModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关注功能页面控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/follow")
public class FollowController extends BaseController {

    @Autowired(required = false)
    private FollowService<Follow> followService;

    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired
    private BlacklistService<Blacklist> blacklistService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(FollowModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/activityList", context);
    }

    /**
     * json 列表页面
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dataList", method = RequestMethod.POST)
    public void dataList(FollowModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * 前端数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(HttpServletResponse response) throws Exception {
        FollowModel model = new FollowModel();
        queryDataList(model, response);
    }


    /**
     * 前端数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxUserDataList", method = RequestMethod.POST)
    public void ajaxUserDataList(String wxid,HttpServletResponse response) throws Exception {
        FollowModel model = new FollowModel();
        model.setWxid(wxid);
        model.setFollowType(1);
        queryUserDataList(model, response);
    }

    /**
     * 前端数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxAuctionDataList", method = RequestMethod.POST)
    public void ajaxAuctionDataList(String wxid,HttpServletResponse response) throws Exception {
        FollowModel model = new FollowModel();
        model.setWxid(wxid);
        model.setFollowType(3);
        queryAuctionDataList(model, response);
    }

    /**
     * 前端数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxAuctionItemDataList", method = RequestMethod.POST)
    public void ajaxAuctionItemDataList(String wxid,HttpServletResponse response) throws Exception {
        FollowModel model = new FollowModel();
        model.setWxid(wxid);
        model.setFollowType(2);
        queryAuctionItemDataList(model, response);
    }


    /**
     * 查询wxid用户是否关注followWxId用户,wxid用户是否添加followWxId用户为黑名单
     * @param wxid
     * @param followWxId
     */
    @RequestMapping(value = "/ajaxIsFollow", method = RequestMethod.POST)
    public void ajaxIsFollow(String wxid, String followWxId, HttpServletResponse response) throws Exception {
        if(StringUtils.isEmpty(wxid) || StringUtils.isEmpty(followWxId)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "查询失败，用户信息有误");
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "查询失败，用户信息有误");
            return;
        }

        WeixinUser followUser = weixinUserService.queryWeixinUser(followWxId);
        if(null == followUser) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "查询失败，用户信息有误");
            return;
        }

        FollowModel model = new FollowModel();
        model.setFollowType(1);
        model.setFollowId(followUser.getId());
        model.setWxid(wxid);
        Integer followCount = followService.queryByCount(model);

        BlacklistModel bModel = new BlacklistModel();
        bModel.setBlackid(followWxId);
        bModel.setCreatorid(wxid);
        Integer blackCount = blacklistService.queryForegroundByCount(bModel);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("isFollow", followCount > 0);
        jsonMap.put("isBlacklist", blackCount > 0);
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 前端列表数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxGetAllList", method = RequestMethod.POST)
    public void ajaxGetJoinList(HttpServletResponse response ,  Integer limit ,String wxid) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid",wxid);
        params.put("limit",4);

        //查询关注的用户
        List<Follow> dataList1 = followService.queryFollowUserByList(params);

        //查询关注的拍品
        List<Follow> dataList2 = followService.queryFollowAuctionItemByList(params);
        //需要查询第一张图
        if(null != dataList2 && dataList2.size()>0){
            for(Follow follow : dataList2){
                Map<String, Object> imgParams = new HashMap<>();
                imgParams.put("conid",follow.getFollowId());
                ItemRes ir = itemResService.queryByConId(imgParams);
                if(null != ir){
                    follow.setPath(ir.getPath());
                }
            }
        }

        //查询关注的展览
        List<Follow> dataList3 = followService.queryFollowAuctionByList(params);
        //需要查询第一张图
        if(null != dataList3 && dataList3.size()>0){
            for(Follow follow : dataList3){
                Map<String, Object> imgParams = new HashMap<>();
                imgParams.put("conid",follow.getFollowId());
                ItemRes ir = itemResService.queryByConId(imgParams);
                if(null != ir){
                    follow.setPath(ir.getPath());
                }
            }
        }
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows1", dataList1);
        jsonMap.put("rows2", dataList2);
        jsonMap.put("rows3", dataList3);
        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryDataList(FollowModel model, HttpServletResponse response) throws Exception {
        List<Follow> dataList = followService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", followService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryUserDataList(FollowModel model, HttpServletResponse response) throws Exception {
        List<Follow> dataList = followService.queryUserByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", followService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryAuctionDataList(FollowModel model, HttpServletResponse response) throws Exception {
        List<Follow> dataList = followService.queryAuctionByList(model);
        // 设置页面数据
        //需要查询第一张图
        if(null != dataList && dataList.size()>0){
            for(Follow follow : dataList){
                Map<String, Object> imgParams = new HashMap<>();
                imgParams.put("conid",follow.getFollowId());
                ItemRes ir = itemResService.queryByConId(imgParams);
                if(null != ir){
                    follow.setPath(ir.getPath());
                }
            }
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", followService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryAuctionItemDataList(FollowModel model, HttpServletResponse response) throws Exception {
        List<Follow> dataList = followService.queryAuctionItemByList(model);
        // 设置页面数据
        if(null != dataList && dataList.size()>0){
            for(Follow follow : dataList){
                ItemResModel resModel  = new ItemResModel();
                resModel.setConid(follow.getFollowId());
                resModel.setConType("2");
                List<ItemRes> resDataList = itemResService.queryByList(resModel);
                follow.setResList(resDataList);
            }
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", followService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 关注
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public void save(Follow bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        followService.add(bean);
        sendSuccessMessage(response, "保存成功~");
    }

    /**
     * 前端添加关注
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxFollowSave")
    public void ajaxFollowSave(Follow bean, HttpServletResponse response) throws Exception {
        if(null == bean) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_FOLLOW_ERROR, "关注失败");
            return;
        }
        String wxid = bean.getWxid();
        if(StringUtils.isEmpty(wxid) || StringUtils.isEmpty(bean.getFollowWxId())) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "关注失败，用户信息有误");
            return;
        }

        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "关注失败，用户信息有误");
            return;
        }

        // 关注用户，验证并设置被关注用户信息
        if(1 == bean.getFollowType()) {
            WeixinUser followUser = weixinUserService.queryWeixinUser(bean.getFollowWxId());
            if(null == followUser) {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "关注失败，用户信息有误");
                return;
            }
            bean.setFollowId(followUser.getId());
        }

        followService.add(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "关注成功");
    }

    /**
     * 前端取消关注
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxFollowDel")
    public void ajaxFollowDel(Follow bean, HttpServletResponse response) throws Exception {
        if(null == bean) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_FOLLOW_ERROR, "取消关注失败");
            return;
        }
        String wxid = bean.getWxid();
        if(StringUtils.isEmpty(wxid) || StringUtils.isEmpty(bean.getFollowWxId())) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "取消关注失败，用户信息有误");
            return;
        }

        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "取消关注失败，用户信息有误");
            return;
        }

        // 关注用户，验证并设置被关注用户信息
        if(1 == bean.getFollowType()) {
            WeixinUser followUser = weixinUserService.queryWeixinUser(bean.getFollowWxId());
            if(null == followUser) {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "取消关注失败，用户信息有误");
                return;
            }
            bean.setFollowId(followUser.getId());
        }

        followService.delete(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "取消关注成功");
    }

    /**
     * 根据ID删除记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public void delete(Integer[] id, HttpServletResponse response) throws Exception {
        followService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }

}
