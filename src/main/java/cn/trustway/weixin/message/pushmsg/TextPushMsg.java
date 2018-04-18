package cn.trustway.weixin.message.pushmsg;

/**
 * 推送文本消息
 * 
 * @author xiaoye
 */

public class TextPushMsg extends BasePushMsg {
	private Text text; // 消息内容

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}