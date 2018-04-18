package cn.trustway.weixin.message.model;

import cn.trustway.weixin.message.MsgType.RespType;
import cn.trustway.weixin.message.shared.MediaEx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复视频消息
 * 
 */
@XStreamAlias("xml")
public class RespMsgVideo extends RespMsg
{
	@XStreamAlias("Video")
	private MediaEx video;
	
	public RespMsgVideo(BaseMsg req, MediaEx video)
	{
		super(req, RespType.video.name());
		this.video = video;
	}

	public MediaEx getVideo()
	{
		return video;
	}

	public void setVideo(MediaEx video)
	{
		this.video = video;
	}

}
