package cn.trustway.weixin.message.model;

import java.util.Arrays;
import java.util.List;

import cn.trustway.weixin.message.MsgType.RespType;
import cn.trustway.weixin.message.shared.Article;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复图文消息
 * 
 */
@XStreamAlias("xml")
public class RespMsgNews extends RespMsg
{
	@XStreamAlias("Articles")
	private List<Article> articles;
	@XStreamAlias("ArticleCount")
	private int articleCount;

	public RespMsgNews(BaseMsg req, List<Article> articles)
	{
		super(req, RespType.news.name());
		setArticles(articles);
		this.articleCount = articles.size();
	}
	
	public RespMsgNews(BaseMsg req, Article...articles)
	{
		this(req, Arrays.asList(articles));
	}

	public List<Article> getArticles()
	{
		return articles;
	}

	public void setArticles(List<Article> articles)
	{
		this.articles = articles;
		this.articleCount = articles.size();
	}

	public int getArticleCount()
	{
		return articleCount;
	}
}
