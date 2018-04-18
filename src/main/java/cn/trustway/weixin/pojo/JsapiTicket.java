package cn.trustway.weixin.pojo;

/**
 * 微信通用js-sdk凭证
 * 
 * @author yexin
 * @date 2015-05-05
 */
public class JsapiTicket {
	// 获取到的凭证
	private String ticket;
	// 凭证有效时间，单位：秒
	private int expiresIn;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
}