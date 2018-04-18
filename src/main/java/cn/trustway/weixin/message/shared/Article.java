package cn.trustway.weixin.message.shared;

public class Article extends FreeMedia
{
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致   
	public String picurl;
    
    // 点击图文消息跳转链接
    public String url;

	public Article( String title, String description,
			String picurl, String url)
	{
		super(title, description);
		this.picurl = picurl;
		this.url = url;
	}
}
