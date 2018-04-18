package cn.trustway.weixin.message.model;


public abstract class RespMsg extends BaseMsg
{   
	public RespMsg()
	{
		this.createTime = System.currentTimeMillis();
	}
	
	public RespMsg(String toUserName, String fromUserName,
			String msgType)
	{
		this();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.msgType = msgType;
	}
	
	/**
	 * 从请求消息中构建。应答消息的ToUserName与FromUserName与请求消息相反。
	 * 
	 * @param reqMsg
	 * @param createTime
	 * @param msgType
	 */
	public RespMsg(BaseMsg reqMsg, String msgType)
	{
		this(reqMsg.fromUserName, reqMsg.toUserName, msgType);
	}
	
	public void setMsgType(String msgType)
	{
		this.msgType = msgType;
	}
	
}
