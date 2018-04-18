package cn.trustway.weixin.dao;

import java.util.List;

import cn.trustway.weixin.bean.SysRoleRel;
/**
 * 角色关联操作接口
 * @author hucheng
 *
 * @param <T>
 */
public interface SysRoleRelDao<T> extends BaseDao<T> {

	/**
	 * 根据角色id删除角色
	 * @param param
	 */
	public void deleteByRoleId(java.util.Map<String, Object> param);

	/**
	 * 根据对象id删除角色
	 * @param param
	 */
	public void deleteByObjId(java.util.Map<String, Object> param);

	/**
	 * 根据角色id查询角色关联列表
	 * @param param
	 * @return
	 */
	public List<SysRoleRel> queryByRoleId(java.util.Map<String, Object> param);

	/**
	 * 根据对象id查询角色关联列表
	 * @param param
	 * @return
	 */
	public List<SysRoleRel> queryByObjId(java.util.Map<String, Object> param);
}
