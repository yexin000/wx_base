package cn.trustway.weixin.dao;

import java.util.List;

/**
 * 微信参数配置接口
 * @author hucheng
 *
 * @param <T>
 */
public interface WxCodeDao<T> extends BaseDao<T> {

	/**
	 * 查询所有微信参数列表
	 * 
	 * @return
	 */
	public List<T> queryByAll();
	
	/**
	 * 获取顶级参数
	 * 
	 * @return
	 */
	public List<T> getRootCode(java.util.Map map);
	
	/**
	 * 获取子参数
	 * 
	 * @return
	 */
	public List<T> getChildCode();
	
	public List<T> getChildCode(Integer parentId);

	public List<T> getAuctionItemType();

	public List<T> getAuctionItemSecondType(String code);

	List<T> getAllAuctionItemSecondType();
	
	/**
	 * 查询代码，检查是否存在
	 * @param code
	 * @return
	 */
	public int getCodeCountByCode(String code);
	
	public T getModelByCode(String code);

}
