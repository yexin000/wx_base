package cn.trustway.weixin.message.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 语音请求消息及语音识别消息。
 *
 */
@XStreamAlias("xml")
public class ReqMsgVoice extends ReqMsgMedia
{
    // 语音格式，如amr，speex等   
    private String Format;
    
	// 语音识别结果，UTF8编码
	private String Recognition;

	public String getRecognition()
	{
		return Recognition;
	}

	public void setRecognition(String recognition)
	{
		Recognition = recognition;
	}
    
	public String getFormat()
	{
		return Format;
	}
	public void setFormat(String format)
	{
		Format = format;
	}
}
