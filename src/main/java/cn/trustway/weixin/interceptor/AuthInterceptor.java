package cn.trustway.weixin.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.trustway.weixin.bean.SysUser;
import cn.trustway.weixin.controller.BaseController;
import cn.trustway.weixin.annotation.AuthVerify;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.SessionUtil;

/**
 * 权限拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log = Logger.getLogger(AuthInterceptor.class);
	private List<String> uncheckUrls;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String clazzName = ((HandlerMethod) handler).getBean().getClass().getName();
		String requestUrl = request.getServletPath();  
		if (uncheckUrls.contains(requestUrl) || uncheckUrls.contains(clazzName)) {
			return true;
		} else {
			HandlerMethod method = (HandlerMethod) handler;
			AuthVerify auth = method.getMethod()
					.getAnnotation(AuthVerify.class);
			// //验证登陆超时问题 auth = null，默认验证
			if (auth == null || auth.verifyLogin()) {
				String baseUri = request.getContextPath();
				String path = request.getServletPath();
				SysUser user = SessionUtil.getUser(request);

				if (user == null) {
					if (path.endsWith(".shtml")) {
						response.setStatus(response.SC_GATEWAY_TIMEOUT);
						response.sendRedirect(baseUri + "/login.shtml");
						return false;
					} else {
						response.setStatus(response.SC_GATEWAY_TIMEOUT);
						Map<String, Object> result = new HashMap<String, Object>();
						result.put(BaseController.SUCCESS, false);
						result.put(BaseController.LOGOUT_FLAG, true);// 登录标记
																		// true
																		// 退出
						result.put(BaseController.MSG, "登录超时.");
						HtmlUtil.writerJson(response, result);
						return false;
					}
				}
			}
			// 验证URL权限
			if (auth == null || auth.verifyURL()) {
				// 判断是否超级管理员
				if (!SessionUtil.isAdmin(request)) {
					String menuUrl = StringUtils.remove(
							request.getRequestURI(), request.getContextPath());
					if (!SessionUtil.isAccessUrl(request,
							StringUtils.trim(menuUrl))) {
						// 日志记录
						String username = SessionUtil.getUser(request)
								.getUsername();
						String msg = "URL权限验证不通过:[url=" + menuUrl
								+ "][username =" + username + "]";
						log.error(msg);

						response.setStatus(response.SC_FORBIDDEN);
						Map<String, Object> result = new HashMap<String, Object>();
						result.put(BaseController.SUCCESS, false);
						result.put(BaseController.MSG, "没有权限访问,请联系管理员.");
						HtmlUtil.writerJson(response, result);
						return false;
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

	public List<String> getUncheckUrls() {
		return uncheckUrls;
	}

	public void setUncheckUrls(List<String> uncheckUrls) {
		this.uncheckUrls = uncheckUrls;
	}

}
