package cn.trustway.weixin.message.model;

import cn.trustway.weixin.message.MsgType.RespType;
import cn.trustway.weixin.message.shared.Media;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复图片消息 

 */
@XStreamAlias("xml")
public class RespMsgImage extends RespMsg
{
	@XStreamAlias("Image")
	private Media image;
	
	public RespMsgImage(BaseMsg req, Media image)
	{
		super(req, RespType.image.name());
		this.image = image;
	}

	public Media getImage()
	{
		return image;
	}

	public void setImage(Media image)
	{
		this.image = image;
	}

}
