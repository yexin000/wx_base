package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.Favorite;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.FavoriteModel;
import cn.trustway.weixin.service.FavoriteService;
import cn.trustway.weixin.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收藏控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/favorite")
public class FavoriteController extends BaseController {
    @Autowired
    FavoriteService<Favorite> favoriteService;

    /**
     * 前端用户添加收藏
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxAddFavorite", method = RequestMethod.POST)
    public void ajaxAddFavorite(Favorite bean, HttpServletResponse response) throws Exception {
        favoriteService.add(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "收藏成功~");
    }

    /**
     * 前端用户取消收藏
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDelFavorite", method = RequestMethod.POST)
    public void ajaxDelFavorite(Favorite bean, HttpServletResponse response) throws Exception {
        favoriteService.delete(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "取消收藏成功~");
    }

    /**
     * 前端查询收藏记录
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(FavoriteModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(FavoriteModel model, HttpServletResponse response) throws Exception {
        List<Favorite> dataList = favoriteService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
}
