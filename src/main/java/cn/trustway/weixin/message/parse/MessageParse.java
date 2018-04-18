package cn.trustway.weixin.message.parse;

import cn.trustway.weixin.message.model.BaseMsg;
import cn.trustway.weixin.message.model.EventMsg;
import cn.trustway.weixin.message.model.ReqMsgImage;
import cn.trustway.weixin.message.model.ReqMsgLocation;
import cn.trustway.weixin.message.model.ReqMsgText;

public abstract class MessageParse {
	
	/**
	 * 统一处理请求
	 * @param msg
	 * @return
	 */
	public String parseMsg(BaseMsg msg){
		String result = "";
		if(msg.isTextMsg()){
			ReqMsgText txtMsg = (ReqMsgText) msg;
			result = this.parseTextMsg(txtMsg);
		}else if(msg.isImageMsg()){
			ReqMsgImage imageMsg = (ReqMsgImage)msg;
			result = this.parseImageMsg(imageMsg);
		}else if(msg.isEventMsg()){
			EventMsg eventMsg = (EventMsg)msg;
			result = this.parseEventMsg(eventMsg);
		} else if(msg.isLocationMsg()) {
			ReqMsgLocation locationMsg = (ReqMsgLocation)msg;
			result = this.parseLocationMsg(locationMsg);
		}
		return result;
	}
	/**
	 * 处理文本信息
	 * @param textMsg
	 * @return
	 */
	public abstract String parseTextMsg(ReqMsgText textMsg);
	
	/**
	 * 处理图片信息
	 * @param imageMsg
	 * @return
	 */
	public abstract String parseImageMsg(ReqMsgImage imageMsg);
	
	/**
	 * 处理事件消息
	 * @param eventMsg
	 * @return
	 */
	public abstract String parseEventMsg(EventMsg eventMsg);
	
	/**
	 * 处理地理位置消息
	 * @param locationMsg
	 * @return
	 */
	public abstract String parseLocationMsg(ReqMsgLocation locationMsg);
	
}
