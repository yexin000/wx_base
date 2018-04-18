package cn.trustway.weixin.dao;

import java.util.List;

/**
 * 角色接口
 * @author hucheng
 *
 * @param <T>
 */
public interface SysRoleDao<T> extends BaseDao<T> {
	
	/**
	 * 查询全部有效的权限
	 * 
	 * @return
	 */
	public List<T> queryAllList();

	/**
	 * 根据用户Id查询权限
	 * 
	 * @return
	 */
	public List<T> queryByUserid(Integer userid);
}
