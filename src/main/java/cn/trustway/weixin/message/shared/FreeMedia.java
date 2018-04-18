package cn.trustway.weixin.message.shared;

public abstract class FreeMedia
{
	// 多媒体标题 ，可选  
    public String title;  
    // 多媒体描述   可选
	public String description;
	
	public FreeMedia(String title, String description)
	{
		this.title = title;
		this.description = description;
	}
	
	
}
