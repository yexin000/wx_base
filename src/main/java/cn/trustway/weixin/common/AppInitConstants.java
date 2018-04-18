package cn.trustway.weixin.common;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.trustway.weixin.service.WxCodeService;

/**
 * 启动时装载-系统初始化常量类
 */
public class AppInitConstants {
	/** 获取Log4j实例 */
	private final Logger logger = Logger.getLogger(getClass());
	/**
	 * outService接口调用密钥
	 */
	public static String outServiceXlh = null;

	/**
	 * outService接口调用URL
	 */
	public static String outServiceUrl = null;

	/**
	 * 微信关注欢迎语
	 */
	public static String wxWelcomeStr = null;

	/**
	 * 微信自动回复语
	 */
	public static String wxAutoReplyStr = null;

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
		System.out.println("加载微信配置...");
		getWeixinParam();
		System.out.println("加载微信配置结束！");
	}
	
	private void getOutServiceParam() {
		outServiceXlh = wxCodeService.getBeanByCode("outServiceXlh").getValue();
		outServiceUrl = wxCodeService.getBeanByCode("outServiceUrl").getValue();
	}
	
	private void getWeixinParam() {
		wxWelcomeStr = wxCodeService.getBeanByCode("wxWelcomeStr").getValue();
		wxAutoReplyStr = wxCodeService.getBeanByCode("wxAutoReplyStr").getValue();
	}
}