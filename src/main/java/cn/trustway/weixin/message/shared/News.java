package cn.trustway.weixin.message.shared;

import java.util.Arrays;
import java.util.List;

public class News
{
	public List<Article> articles;

	public News(List<Article> articles)
	{
		this.articles = articles;
	}
	
	public News(Article... article)
	{
		this(Arrays.asList(article));
	}

}
