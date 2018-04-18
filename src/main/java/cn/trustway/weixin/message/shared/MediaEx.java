package cn.trustway.weixin.message.shared;

public class MediaEx extends Media
{
	// 多媒体标题 ，可选  
    public String title;  
    // 多媒体描述   可选
	public String description;
	
	public MediaEx(String media_id, String title, String description)
	{
		super(media_id);
		this.title = title;
		this.description = description;
	}  
}
