package cn.trustway.weixin.dao;

import java.util.List;

/**
 * 系统菜单接口
 * 
 * @author hucheng
 *
 * @param <T>
 */
public interface SysMenuDao<T> extends BaseDao<T> {

	/**
	 * 查询所有系统菜单列表
	 * 
	 * @return
	 */
	public List<T> queryByAll();

	/**
	 * 获取顶级菜单
	 * 
	 * @return
	 */
	public List<T> getRootMenu(java.util.Map map);

	/**
	 * 获取子菜单
	 * 
	 * @return
	 */
	public List<T> getChildMenu();

	/**
	 * 根据权限id查询菜单
	 * 
	 * @param roleId
	 * @return
	 */
	public List<T> getMenuByRoleId(Integer roleId);

	/**
	 * 根据用户id查询父菜单菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<T> getRootMenuByUser(Integer userId);

	/**
	 * 根据用户id查询子菜单菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<T> getChildMenuByUser(Integer userId);
}
