package cn.trustway.weixin.dao;

import cn.trustway.weixin.model.SysUserModel;

/**
 * 用户操作接口
 * @author hucheng
 *
 * @param <T>
 */
public interface SysUserDao<T> extends BaseDao<T> {

	/**
	 * 检查登录
	 * @param model
	 * @return
	 */
	public T queryLogin(SysUserModel model);
	
	
	/**
	 * 查询邮箱总数，检查是否存在
	 * @param username
	 * @return
	 */
	public int getUserCountByUsername(String username);
}
