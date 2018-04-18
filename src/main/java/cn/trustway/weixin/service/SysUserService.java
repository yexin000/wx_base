package cn.trustway.weixin.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.trustway.weixin.bean.SysRoleRel;
import cn.trustway.weixin.bean.SysRoleRel.RelType;
import cn.trustway.weixin.dao.SysUserDao;
import cn.trustway.weixin.model.SysUserModel;

@Service("sysUserService")
public class SysUserService<T> extends BaseService<T> {
	private final static Logger log = Logger.getLogger(SysUserService.class);

	@Autowired
	private SysRoleRelService<SysRoleRel> sysRoleRelService;

	@Override
	public void delete(Object[] ids) throws Exception {
		super.delete(ids);
		for (Object id : ids) {
			sysRoleRelService.deleteByObjId((Integer) id, RelType.USER.key);
		}
	}

	/**
	 * 检查登录
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	public T queryLogin(String username, String pwd) {
		SysUserModel model = new SysUserModel();
		model.setUsername(username);
		model.setPwd(pwd);
		return getDao().queryLogin(model);
	}

	/**
	 * 查询邮箱总数，检查是否存在
	 * 
	 * @param username
	 * @return
	 */
	public int getUserCountByUsername(String username) {
		return getDao().getUserCountByUsername(username);
	}

	/**
	 * 查询用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysRoleRel> getUserRole(Integer userId) {
		return sysRoleRelService.queryByObjId(userId, RelType.USER.key);
	}

	/**
	 * 添加用户权限
	 * 
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	public void addUserRole(Integer userId, Integer[] roleIds) throws Exception {
		if (userId == null || roleIds == null || roleIds.length < 1) {
			return;
		}
		// 清除关联关系
		sysRoleRelService.deleteByObjId(userId, RelType.USER.key);
		for (Integer roleId : roleIds) {
			SysRoleRel rel = new SysRoleRel();
			rel.setRoleId(roleId);
			rel.setObjId(userId);
			rel.setRelType(RelType.USER.key);
			sysRoleRelService.add(rel);
		}
	}

	@Autowired
	private SysUserDao<T> dao;

	public SysUserDao<T> getDao() {
		return dao;
	}

}
