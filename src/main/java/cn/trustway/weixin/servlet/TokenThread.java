package cn.trustway.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.trustway.weixin.bean.WxCode;
import cn.trustway.weixin.pojo.AccessToken;
import cn.trustway.weixin.pojo.JsapiTicket;
import cn.trustway.weixin.service.WxCodeService;
import cn.trustway.weixin.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 定时获取微信access_token的线程
 * 
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// 第三方用户唯一凭证
	public static String appid = "";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "";
	public static AccessToken accessToken = null;
	public static JsapiTicket jsapiTicket = null;
	private WxCodeService<WxCode> wxCodeService;
	
	public TokenThread(ApplicationContext ctx) {
		wxCodeService = (WxCodeService) ctx.getBean("wxCodeService");
	}

	public void run() {
		while (true) {
			try {
				// 获取properties
				Properties prop = new Properties();  
				InputStream in = getClass().getResourceAsStream("/pref.properties");  
				boolean isTokenCtrl = false;
				try {   
		            prop.load(in); 
		            isTokenCtrl = Boolean.valueOf(prop.getProperty("isTokenCtrl").toString());
				} catch (IOException e) {   
		            e.printStackTrace();   
		        }   
				if(isTokenCtrl) {
					accessToken = WeixinUtil.getAccessToken(appid, appsecret);
					if (null != accessToken) {
						WxCode tokenCode = wxCodeService.getBeanByCode("access_token");
						tokenCode.setValue(accessToken.getToken());
						wxCodeService.update(tokenCode);
						System.out.println("获取access_token成功，有效时长" + accessToken	.getExpiresIn() + "秒" +  "token:" + accessToken.getToken());
						jsapiTicket = WeixinUtil.getJsapiTicket(accessToken.getToken());
						if(null != jsapiTicket) {
							WxCode jsapiCode = wxCodeService.getBeanByCode("jsapi_ticket");
							jsapiCode.setValue(jsapiTicket.getTicket());
							wxCodeService.update(jsapiCode);
							System.out.println("获取jsapi_ticket成功，有效时长" + jsapiTicket	.getExpiresIn() + "秒" +  "ticket:" + jsapiTicket.getTicket());
						}
						// 休眠7000秒
						Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
					} else {
						// 如果access_token为null，60秒后再获取
						Thread.sleep(60 * 1000);
					}
				} else {
					String access_token = wxCodeService.getBeanByCode("access_token").getValue();
					String jsapi_ticket = wxCodeService.getBeanByCode("jsapi_ticket").getValue();
					if (null != access_token && !"".equals(access_token)) {
						accessToken = new AccessToken();
						accessToken.setToken(access_token);
						accessToken.setExpiresIn(7000);
						System.out.println("数据库获取access_token成功，有效时长" + accessToken.getExpiresIn() + "秒" +  "token:" + accessToken.getToken());
						if(null != jsapi_ticket && !"".equals(jsapi_ticket)) {
							jsapiTicket = new JsapiTicket();
							jsapiTicket.setTicket(jsapi_ticket);
							jsapiTicket.setExpiresIn(7000);
							System.out.println("数据库获取jsapi_ticket成功，有效时长" + jsapiTicket.getExpiresIn() + "秒" +  "ticket:" + jsapiTicket.getTicket());
						}
						// 休眠7000秒
						Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
					} else {
						// 如果access_token为null，60秒后再获取
						Thread.sleep(60 * 1000);
					}
				}
			} catch (Exception e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}