package cn.trustway.weixin.message;

import cn.trustway.weixin.message.model.EventMsgClick;
import cn.trustway.weixin.message.model.EventMsgLocation;
import cn.trustway.weixin.message.model.EventMsgUserAttention;
import cn.trustway.weixin.message.model.EventMsgView;
import cn.trustway.weixin.message.model.ReqMsgImage;
import cn.trustway.weixin.message.model.ReqMsgLink;
import cn.trustway.weixin.message.model.ReqMsgLocation;
import cn.trustway.weixin.message.model.ReqMsgText;
import cn.trustway.weixin.message.model.ReqMsgVideo;
import cn.trustway.weixin.message.model.ReqMsgVoice;

/**
 * 各种消息的类型定义。
 * 在此定义消息类型与其对应的JAVA BEAN的对应关系，
 * 使得系统增加新的消息时，只需要在此配置即可，无需更改解析逻辑。
 */
public class MsgType
{
	/**
	 * 用户请求消息类型与JAVA BEAN对应关系。
	 * 
	 */
	public static enum ReqType
	{
		text(ReqMsgText.class), 
		image(ReqMsgImage.class),
		link(ReqMsgLink.class),
		location(ReqMsgLocation.class),
		video(ReqMsgVideo.class),
		voice(ReqMsgVoice.class);	

		private Class<?> msgCls;
		
		private ReqType(Class<?> msgCls)
		{
			this.msgCls = msgCls;
		}

		public Class<?> getMsgCls()
		{
			return msgCls;
		}		
	}
	
	/**
	 * 事件消息类型与JAVA BEAN对应关系。
	 * 这里有大小写不不一致的情况，是因为微信消息本身定义的原因。
	 */
	public static enum EventType
	{
		subscribe(EventMsgUserAttention.class),
		unsubscribe(EventMsgUserAttention.class),
		SCAN(EventMsgUserAttention.class),
		LOCATION(EventMsgLocation.class),
		VIEW(EventMsgView.class),
		CLICK(EventMsgClick.class);
		
		private Class<?> msgCls;
		
		private EventType(Class<?> msgCls)
		{
			this.msgCls = msgCls;
		}

		public Class<?> getMsgCls()
		{
			return msgCls;
		}			
	}
	
	/**
	 * 应答消息类型定义。
	 */
	public static enum RespType
	{
		image,music,news,text,video,voice
	}
}
