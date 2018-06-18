package cn.trustway.weixin.servlet;

import cn.trustway.weixin.bean.WxCode;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.service.WxCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);
	private WxCodeService<WxCode> wxCodeService;

	public void init() {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		wxCodeService = (WxCodeService) ctx.getBean("wxCodeService");

		//加载系统配置
		AppInitConstants appInitConstants = new AppInitConstants(this.getServletContext());
		appInitConstants.initialize();
		TokenThread.appid = AppInitConstants.XCX_APP_ID;
		TokenThread.appsecret = AppInitConstants.XCX_APP_SECRET;

		System.out.println("weixin api appid:" + TokenThread.appid);
		System.out.println("weixin api appsecret:" + TokenThread.appsecret);
		// 未配置appid、appsecret时给出提示
		if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
			log.error("appid and appsecret configuration error, please check carefully.");
		} else {
			// 启动定时获取access_token的线程
			if(AppInitConstants.IS_TOKEN_CTRL) {
				new Thread(new TokenThread(ctx)).start();
			}

		}
	}
}