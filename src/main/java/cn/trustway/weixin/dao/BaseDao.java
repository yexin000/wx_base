package cn.trustway.weixin.dao;

import java.util.List;

import cn.trustway.weixin.model.BaseModel;

/**
 * DAO层泛型基类接口
 * @author hucheng
 *
 * @param <T>
 */
public interface BaseDao<T> {

	/**
	 * 添加对象
	 * @param t
	 */
	public void add(T t);

	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);

	/**
	 * 更新选择的对象
	 * @param t
	 */
	public void updateBySelective(T t);

	/**
	 * 根据ID删除对象
	 * @param id
	 */
	public void delete(Object id);

	/**
	 * 查询数量
	 * @param model
	 * @return
	 */
	public int queryByCount(BaseModel model);

	/**
	 * 查询列表对象
	 * @param model
	 * @return
	 */
	public List<T> queryByList(BaseModel model);

	/**
	 * 根据ID查找对象
	 * @param id
	 * @return
	 */
	public T queryById(Object id);
}
