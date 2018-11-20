package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.Fabulous;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.service.FabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户收藏控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/fabulous")
public class FabulousController extends BaseController {
  @Autowired
  FabulousService<Fabulous> fabulousService;

  /**
   * 前端用户点赞
   *
   * @param bean
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/ajaxAddFabulous", method = RequestMethod.POST)
  public void ajaxAddFabulous(Fabulous bean, HttpServletResponse response) throws Exception {
    fabulousService.add(bean);
    sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "点赞成功");
  }

  /**
   * 前端用户取消点赞
   *
   * @param bean
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/ajaxDelFabulous", method = RequestMethod.POST)
  public void ajaxDelFabulous(Fabulous bean, HttpServletResponse response) throws Exception {
    fabulousService.delete(bean);
    sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "取消点赞成功");
  }
}
