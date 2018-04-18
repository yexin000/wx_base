package cn.trustway.weixin.message.pushmsg;

/**
 * 推送消息
 * 
 * @author xiaoye
 */

public class BasePushMsg {
	private String touser; // 要推送的微信openid
	private String msgtype; // 推送消息类型

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}