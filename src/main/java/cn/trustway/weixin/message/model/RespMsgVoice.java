package cn.trustway.weixin.message.model;


import cn.trustway.weixin.message.MsgType.RespType;
import cn.trustway.weixin.message.shared.Media;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复语音消息
 */
@XStreamAlias("xml")
public class RespMsgVoice extends RespMsg
{
	@XStreamAlias("Voice")
	private Media voice;
	
	public RespMsgVoice(BaseMsg req, Media voice)
	{
		super(req, RespType.voice.name());
		this.voice = voice;
	}

	public Media getVoice()
	{
		return voice;
	}

	public void setVoice(Media voice)
	{
		this.voice = voice;
	}

}
