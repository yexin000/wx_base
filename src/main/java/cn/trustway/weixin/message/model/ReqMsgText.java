package cn.trustway.weixin.message.model;

/**
 * 文本请求消息
 *
 */
public class ReqMsgText extends ReqMsg
{
	//请求内容
	private String content;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
