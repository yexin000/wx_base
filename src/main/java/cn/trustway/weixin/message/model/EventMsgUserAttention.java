package cn.trustway.weixin.message.model;

/**
 * 用户关注事件
 * 
 */
public class EventMsgUserAttention extends EventMsg
{
	// 事件KEY值，如果未关注，则表示为qrscene_为前缀，后面为二维码的参数值，已关注则直接为二维码参数值 
	private String EventKey;
	// 二维码的ticket，可用来换取二维码图片 
	private String Ticket;
	
	public String getEventKey()
	{
		return EventKey;
	}
	public void setEventKey(String eventKey)
	{
		EventKey = eventKey;
	}
	public String getTicket()
	{
		return Ticket;
	}
	public void setTicket(String ticket)
	{
		Ticket = ticket;
	}

}
