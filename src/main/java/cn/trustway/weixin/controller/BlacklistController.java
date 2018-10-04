package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.Blacklist;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.BlacklistModel;
import cn.trustway.weixin.service.BlacklistService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黑名单控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/blacklist")
public class BlacklistController extends BaseController {
    @Autowired
    private BlacklistService<Blacklist> blacklistService;
    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(BlacklistModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/blacklistList", context);
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
    public void dataList(BlacklistModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(BlacklistModel model, HttpServletResponse response) throws Exception {
        List<Blacklist> dataList = blacklistService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", blacklistService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 前端数据列表查询
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(@RequestBody BlacklistModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * 前端用户添加某用户为黑名单
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxAddBlacklist", method = RequestMethod.POST)
    public void ajaxAddBlacklist(@RequestBody Blacklist bean, HttpServletResponse response) throws Exception {
        String wxid = bean.getCreatorid();
        if(StringUtils.isBlank(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "添加失败，用户信息有误");
            return;
        }

        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "添加失败，用户信息有误");
            return;
        }

        blacklistService.add(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "添加成功");
    }

    /**
     * 前端用户移除某用户黑名单
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxRemoveBlacklist", method = RequestMethod.POST)
    public void ajaxRemoveBlacklist(@RequestBody Blacklist bean, HttpServletResponse response) throws Exception {
        String wxid = bean.getCreatorid();
        if(StringUtils.isBlank(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "移除失败，用户信息有误");
            return;
        }

        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "移除失败，用户信息有误");
            return;
        }

        blacklistService.delete(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "移除成功");
    }

}
