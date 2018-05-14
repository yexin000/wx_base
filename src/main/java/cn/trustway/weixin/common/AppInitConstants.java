package cn.trustway.weixin.common;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.trustway.weixin.service.WxCodeService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 启动时装载-系统初始化常量类
 */
public class AppInitConstants {
	/** 获取Log4j实例 */
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * 小程序APPID
	 */
	public static String XCX_APP_ID;

	/**
	 * 小程序APPSECRET
	 */
	public static String XCX_APP_SECRET;

	/**
	 * 小程序授权地址
	 */
	public static final String XCX_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * 小程序授权类型
	 */
	public static final String XCX_AUTH_TYPE = "authorization_code";

	@SuppressWarnings("unchecked")
	private static WxCodeService wxCodeService = null;
	/**
	 * 由InitServlet调用
	 */
	@SuppressWarnings("unchecked")
	public AppInitConstants(ServletContext servletContext) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		wxCodeService = (WxCodeService) ctx.getBean("wxCodeService");
	}
	
	/**
	 * 初始化函数
	 */
	public void initialize() {
		logger.info("加载微信配置...");
		getWeixinParam();
		logger.info("加载微信配置结束！");
	}
	
	private void getWeixinParam() {
		// 获取properties
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/pref.properties");
		try {
			prop.load(in);
			XCX_APP_ID = prop.getProperty("wx.appId");
			XCX_APP_SECRET = prop.getProperty("wx.appSecret");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}