package cn.trustway.weixin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.trustway.weixin.annotation.AuthVerify;
import cn.trustway.weixin.bean.SysMenu;
import cn.trustway.weixin.bean.SysMenuBtn;
import cn.trustway.weixin.bean.SysUser;
import cn.trustway.weixin.bean.BaseBean.DELETED;
import cn.trustway.weixin.bean.BaseBean.STATE;
import cn.trustway.weixin.bean.TreeNode;
import cn.trustway.weixin.service.SysMenuBtnService;
import cn.trustway.weixin.service.SysMenuService;
import cn.trustway.weixin.service.SysUserService;
import cn.trustway.weixin.util.DateUtil;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.MethodUtils;
import cn.trustway.weixin.util.SessionUtil;
import cn.trustway.weixin.util.TreeUtil;
import cn.trustway.weixin.util.URLUtil;
import cn.trustway.weixin.util.Constant.SuperAdmin;

/**
 * 主页面控制类
 * @author hucheng
 *
 */
@Controller
public class MainController extends BaseController{

	private final static Logger log= Logger.getLogger(MainController.class);
	
	// Servrice start
	@Autowired(required=false) 
	private SysMenuService<SysMenu> sysMenuService; 
	
	@Autowired(required=false) 
	private SysUserService<SysUser> sysUserService; 
	
	@Autowired(required=false) 
	private SysMenuBtnService<SysMenuBtn> sysMenuBtnService;
	
	/**
	 * 登录页面
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@AuthVerify(verifyLogin=false,verifyURL=false)
	@RequestMapping("/login")
	public ModelAndView  login(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("login", context);
	}
	
	
	/**
	 * 登录
	 * @param username 登录账号
	 * @param pwd 密码
	 * @param verifyCode 验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@AuthVerify(verifyLogin=false,verifyURL=false)
	@RequestMapping("/toLogin")
	public void  toLogin(String username,String pwd,String verifyCode,HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String vcode  = SessionUtil.getValidateCode(request);
//		SessionUtil.removeValidateCode(request);//清除验证码，确保验证码只能用一次
//		if(StringUtils.isBlank(verifyCode)){
//			sendFailureMessage(response, "验证码不能为空.");
//			return;
//		}
		//判断验证码是否正确
//		if(!verifyCode.toLowerCase().equals(vcode)){
//			sendFailureMessage(response, "验证码输入错误.");
//			return;
//		}
		username = "admin";
		pwd = "123456";
		if(StringUtils.isBlank(username)){
			sendFailureMessage(response, "账号不能为空.");
			return;
		}
		if(StringUtils.isBlank(pwd)){
			sendFailureMessage(response, "密码不能为空.");
			return;
		}
		String msg = "用户登录日志:";
		SysUser user = sysUserService.queryLogin(username, MethodUtils.MD5(pwd));
		if(user == null){
			//记录错误登录日志
			log.debug(msg+"["+username+"]"+"账号或者密码输入错误.");
			sendFailureMessage(response, "账号或者密码输入错误.");
			return;
		}
		if(STATE.DISABLE.key == user.getState()){
			sendFailureMessage(response, "账号已被禁用.");
			return;
		}
		//登录次数加1 修改登录时间
		int loginCount = 0;
		if(user.getLoginCount() != null){
			loginCount = user.getLoginCount();
		}
		user.setLoginCount(loginCount+1);
		Date curDate = DateUtil.getDateByString("");
		user.setLoginTime(curDate);
		sysUserService.update(user);
		//设置User到Session
		SessionUtil.setUser(request,user);
		//记录成功登录日志
		log.debug(msg+"["+username+"]"+"登录成功");
		sendSuccessMessage(response, "登录成功.");
	}
	
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@AuthVerify(verifyLogin=false,verifyURL=false)
	@RequestMapping("/logout")
	public void  logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SessionUtil.removeUser(request);
		response.sendRedirect(URLUtil.get("msUrl")+"/login.shtml");
	}
	
	/**
	 * 获取Action下的按钮
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@AuthVerify(verifyURL=false)
	@RequestMapping("/getActionBtn")
	public void  getActionBtn(String url,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> actionTypes = new ArrayList<String>();
		//判断是否超级管理员
		if(SessionUtil.isAdmin(request)){
			result.put("allType", true);
		}else{
			String menuUrl = URLUtil.getReqUri(url);
			menuUrl = StringUtils.remove(menuUrl,request.getContextPath());
			//获取权限按钮
			actionTypes = SessionUtil.getMemuBtnListVal(request, StringUtils.trim(menuUrl));
			result.put("allType", false);
			result.put("types", actionTypes);
		}
		result.put(SUCCESS, true);
		HtmlUtil.writerJson(response, result);
	}
	 
	
	/**
	 * 修改密码
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@AuthVerify(verifyURL=false)
	@RequestMapping("/modifyPwd")
	public void modifyPwd(String oldPwd,String newPwd,HttpServletRequest request,HttpServletResponse response) throws Exception{
		SysUser user = SessionUtil.getUser(request);
		if(user == null){
			sendFailureMessage(response, "对不起,登录超时.");
			return;
		}
		SysUser bean  = sysUserService.queryById(user.getId());
		if(bean.getId() == null || DELETED.YES.key == bean.getDeleted()){
			sendFailureMessage(response, "对不起,用户不存在.");
			return;
		}
		if(StringUtils.isBlank(newPwd)){
			sendFailureMessage(response, "密码不能为空.");
			return;
		}
		//不是超级管理员，匹配旧密码
		if(!MethodUtils.ecompareMD5(oldPwd,bean.getPwd())){
			sendFailureMessage(response, "旧密码输入不匹配.");
			return;
		}
		bean.setPwd(MethodUtils.MD5(newPwd));
		sysUserService.update(bean);
		sendSuccessMessage(response, "Save success.");
	}
	
	/**
	 * 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@AuthVerify(verifyURL=false)
	@RequestMapping("/main") 
	public ModelAndView  main(HttpServletRequest request){
		Map<String,Object>  context = getRootMap();
		SysUser user = SessionUtil.getUser(request);
		List<SysMenu> rootMenus = null;
		List<SysMenu> childMenus = null;
		List<SysMenuBtn> childBtns = null;
		//超级管理员
		if(user != null && SuperAdmin.YES.key ==  user.getSuperAdmin()){
			rootMenus = sysMenuService.getRootMenu(null);// 查询所有根节点
			childMenus = sysMenuService.getChildMenu();//查询所有子节点
		}else{
			rootMenus = sysMenuService.getRootMenuByUser(user.getId() );//根节点
			childMenus = sysMenuService.getChildMenuByUser(user.getId());//子节点
			childBtns = sysMenuBtnService.getMenuBtnByUser(user.getId());//按钮操作
			buildData(childMenus,childBtns,request); //构建必要的数据
		}
		context.put("user", user);
		context.put("menuList", treeMenu(rootMenus,childMenus));
		return forword("main/main",context); 
	}
	
	/**
	 * 构建树形数据
	 * @return
	 */
	private List<TreeNode> treeMenu(List<SysMenu> rootMenus,List<SysMenu> childMenus){
		TreeUtil util = new TreeUtil(rootMenus,childMenus);
		return util.getTreeNode();
	}
	
	
	/**
	 * 构建树形数据
	 * @return
	 */
	private void buildData(List<SysMenu> childMenus,List<SysMenuBtn> childBtns,HttpServletRequest request){
		//能够访问的url列表
		List<String> accessUrls  = new ArrayList<String>();
		//菜单对应的按钮
		Map<String,List> menuBtnMap = new HashMap<String,List>(); 
		for(SysMenu menu: childMenus){
			//判断URL是否为空
			if(StringUtils.isNotBlank(menu.getUrl())){
				List<String> btnTypes = new ArrayList<String>();
				for(SysMenuBtn btn  : childBtns){
					if(menu.getId().equals(btn.getMenuid())){
						btnTypes.add(btn.getBtnType());
						URLUtil.getBtnAccessUrls(menu.getUrl(), btn.getActionUrls(),accessUrls);
					}
				}
				menuBtnMap.put(menu.getUrl(), btnTypes);
				URLUtil.getBtnAccessUrls(menu.getUrl(), menu.getActions(),accessUrls);
				accessUrls.add(menu.getUrl());
			}
		}
		SessionUtil.setAccessUrl(request, accessUrls);//设置可访问的URL
		SessionUtil.setMemuBtnMap(request, menuBtnMap); //设置可用的按钮
	}
}
