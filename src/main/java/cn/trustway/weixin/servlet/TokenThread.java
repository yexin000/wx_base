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
	// 公众号appid
	public static String gzhAppid = "";
	// 公众号appsecret
	public static String gzhAppsecret = "";
	public static AccessToken accessToken = null;
	public static AccessToken gzhAccessToken = null;
	public static JsapiTicket jsapiTicket = null;
	private WxCodeService<WxCode> wxCodeService;
	
	public TokenThread(ApplicationContext ctx) {
		wxCodeService = (WxCodeService) ctx.getBean("wxCodeService");
	}

	public void run() {
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
				gzhAccessToken = WeixinUtil.getAccessToken(gzhAppid, gzhAppsecret);
				if (null != accessToken || null != gzhAccessToken) {
					System.out.println("获取access_token成功，有效时长" + accessToken	.getExpiresIn() + "秒" +  "token:" + accessToken.getToken());
					System.out.println("获取公众号access_token成功，有效时长" + gzhAccessToken.getExpiresIn() + "秒" +  "token:" + gzhAccessToken.getToken());
					jsapiTicket = WeixinUtil.getJsapiTicket(gzhAccessToken.getToken());
					if(null != jsapiTicket) {
						System.out.println("获取公众号jsapi_ticket成功，有效时长" + jsapiTicket	.getExpiresIn() + "秒" +  "ticket:" + jsapiTicket.getTicket());
					}
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
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