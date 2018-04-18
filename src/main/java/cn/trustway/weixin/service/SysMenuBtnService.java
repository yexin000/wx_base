package cn.trustway.weixin.service;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.trustway.weixin.dao.SysMenuBtnDao;

@Service("sysMenuBtnService")
public class SysMenuBtnService<T> extends BaseService<T> {
	private final static Logger log = Logger.getLogger(SysMenuBtnService.class);

	public List<T> queryByAll() {
		return getDao().queryByAll();
	}

	public List<T> queryByMenuid(Integer menuid) {
		return getDao().queryByMenuid(menuid);
	}

	public List<T> queryByMenuUrl(String url) {
		return getDao().queryByMenuUrl(url);
	}

	public void deleteByMenuid(Integer menuid) {
		getDao().deleteByMenuid(menuid);
	}

	public List<T> getMenuBtnByUser(Integer userid) {
		return getDao().getMenuBtnByUser(userid);
	}

	@Autowired
	private SysMenuBtnDao<T> dao;

	public SysMenuBtnDao<T> getDao() {
		return dao;
	}

}
