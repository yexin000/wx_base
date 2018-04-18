package cn.trustway.weixin.message.model;

import cn.trustway.weixin.message.MsgType.EventType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信事件消息基类
 */
public abstract class EventMsg extends BaseMsg
{
	// 事件类型 
	@XStreamAlias("Event")
	private String event;
	@XStreamAlias("EventKey")
	private String eventKey;
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getEvent()
	{
		return event;
	}
	
	public void setEvent(String event)
	{
		this.event = event;
	}

	public boolean isClickEvent()
	{
		return EventType.CLICK.name().equalsIgnoreCase(event);
	}
	
	public boolean isViewEvent(){
		return EventType.VIEW.name().equalsIgnoreCase(event);
	}
	
	public boolean isSubscribeEvent()
	{
		return EventType.subscribe.name().equalsIgnoreCase(event);
	}
	public boolean isUnsubscribeEvent()
	{
		return EventType.unsubscribe.name().equalsIgnoreCase(event);
	}
	public boolean isScanEvent()
	{
		return EventType.SCAN.name().equalsIgnoreCase(event);
	}
	public boolean isLocationEvent()
	{
		return EventType.LOCATION.name().equalsIgnoreCase(event);
	}
}
