package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.UserAddr;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.service.UserAddrService;
import cn.trustway.weixin.service.WeixinUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 出价记录控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/userAddr")
public class UserAddrController extends BaseController {
    @Autowired
    UserAddrService<UserAddr> userAddrService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    /**
     * 是否默认地址-是
     */
    public static final String DEFAULT_ADDR_TRUE = "1";
    /**
     * 是否默认地址-否
     */
    public static final String DEFAULT_ADDR_FALSE = "0";

    /**
     * 前端用户添加收货地址
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxAddAddr", method = RequestMethod.POST)
    public void ajaxAddAddr(UserAddr bean, HttpServletResponse response) throws Exception {
        String wxid = bean.getWxid();
        if(StringUtils.isBlank(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "操作失败，用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "操作失败，用户信息有误");
            return;
        }

        // 创建默认收货地址
        if(DEFAULT_ADDR_TRUE.equals(bean.getIsDefault())) {
            // 查询用户默认收货地址
            UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(wxid);
            if(null != defaultAddr) {
                // 设置原来的默认收货地址为非默认
                defaultAddr.setIsDefault(DEFAULT_ADDR_FALSE);
                userAddrService.updateBySelective(defaultAddr);
            }
        }

        userAddrService.add(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "创建成功~");
    }

    /**
     * 前端用户编辑收货地址
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxEditAddr", method = RequestMethod.POST)
    public void ajaxEditAddr(UserAddr bean, HttpServletResponse response) {
        String wxid = bean.getWxid();
        if(StringUtils.isBlank(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "操作失败，用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "操作失败，用户信息有误");
            return;
        }

        // 编辑默认收货地址
        if(DEFAULT_ADDR_TRUE.equals(bean.getIsDefault())) {
            // 查询用户默认收货地址
            UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(wxid);
            // 存在默认地址并且与修改的默认地址不同
            if(null != defaultAddr && defaultAddr.getId() != bean.getId()) {
                // 设置原来的默认收货地址为非默认
                defaultAddr.setIsDefault(DEFAULT_ADDR_FALSE);
                userAddrService.updateBySelective(defaultAddr);
            }
        }

        userAddrService.updateBySelective(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "操作成功~");
    }

    /**
     * 前端用户删除收货地址
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDeleteAddr", method = RequestMethod.POST)
    public void ajaxDeleteAddr(Integer[] id, HttpServletResponse response) throws Exception  {
        userAddrService.delete(id);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "操作成功~");
    }

    /**
     * 前端用户设置默认收货地址
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxSetDefaultAddr", method = RequestMethod.POST)
    public void ajaxSetDefaultAddr(Integer id, HttpServletResponse response) {
        userAddrService.setDefaultAddr(id);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "操作成功~");
    }
}
