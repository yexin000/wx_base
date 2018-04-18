package cn.trustway.weixin.message.parse;

import cn.trustway.weixin.message.model.BaseMsg;

public class Context {

	private MessageParse messageParse;
	public Context(MessageParse messageParse){
		this.messageParse = messageParse;
	}
	
	public String parseMsg(BaseMsg msg){
		return this.messageParse.parseMsg(msg);
	}
}
