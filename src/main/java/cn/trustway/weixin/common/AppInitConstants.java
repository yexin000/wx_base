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
	 * 小程序MCHID
	 */
	public static String XCX_MCHID;

	/**
	 * 项目服务器地址
	 */
	public static String XCX_SERVICE_URL;

	/**
	 * 商户平台密钥key
	 */
	public static String XCX_MCHKEY;

	/**
	 * 小程序授权地址
	 */
	public static final String XCX_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * 小程序授权类型
	 */
	public static final String XCX_AUTH_TYPE = "authorization_code";

	/**
	 * 订单号前缀
	 */
	public static final String XCX_ORDER_PRE = "LONGHAI_ORDER_";

	@SuppressWarnings("unchecked")
	private static WxCodeService wxCodeService = null;

	public class HttpCode {
		// 请求成功
		public static final String HTTP_SUCCESS = "0";
		// 用户信息有误
		public static final String HTTP_URSER_ERROR = "10000";
		// 商品信息有误
		public static final String HTTP_ITEM_ERROR = "10001";
		// 不在有效时间范围
		public static final String HTTP_ITEM_TIME_ERROR = "10002";
		// 加价金额低于起拍价
		public static final String HTTP_PRICE_SRTART_ERROR = "10003";
		// 加价金额低于最低加价金额
		public static final String HTTP_PRICE_MIN_ERROR = "10004";
		// 商品不存在
		public static final String HTTP_ITEM_NOT_EXIST = "10005";
		// 订单有误
		public static final String HTTP_ORDER_ERROR = "10006";
		// 调用微信支付失败
		public static final String HTTP_WXPAY_FAIL = "10007";
		// 已是当前最高出价
		public static final String HTTP_PRICE_SAME_ERROR = "10008";
		// 上传商品失败
		public static final String HTTP_ITEM_UPLOAD_ERROR = "10009";
	}

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
			XCX_MCHID = prop.getProperty("wx.mchId");
			XCX_SERVICE_URL = prop.getProperty("wx.serviceUrl");
			XCX_MCHKEY = prop.getProperty("wx.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}