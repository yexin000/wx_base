package cn.trustway.weixin.message.model;

/**
 * 自定义菜单事件, EVENT值为CLICK
 *
 */
public class EventMsgClick extends EventMsg
{
	//事件KEY值，与自定义菜单接口中KEY值对应 
	private String EventKey;

	public String getEventKey()
	{
		return EventKey;
	}

	public void setEventKey(String eventKey)
	{
		EventKey = eventKey;
	} 
	
}
