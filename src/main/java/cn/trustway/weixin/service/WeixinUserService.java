package cn.trustway.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.dao.WeixinUserDao;

@Service("weixinUserService")
public class WeixinUserService<T> extends BaseService<T> {

	@Autowired
	private WeixinUserDao<T> dao;

	public WeixinUserDao<T> getDao() {
		return dao;
	}
	
	/**
	 * 插入微信用户
	 * @param model
	 * @return
	 */
	public void insertWeixinUser(WeixinUser weixinUser) {
		getDao().insertWeixinUser(weixinUser);
	}
	
	
	/**
	 * 删除微信用户
	 * @param wxid
	 * @return
	 */
	public void deleteWeixinUser(String wxid) {
		getDao().deleteWeixinUser(wxid);
	}
	
	/**
	 * 查找微信用户
	 * @param wxid
	 * @return
	 */
	public WeixinUser queryWeixinUser(String wxid) { 
		return getDao().queryWeixinUser(wxid);
	}
	
	/**
	 * 修改微信用户
	 * @param wxid
	 * @return
	 */
	public void updateWeixinUser(WeixinUser weixinUser) {
		getDao().updateWeixinUser(weixinUser);
	}
	
	/**
	 * 更新微信用户操作时间
	 * @param wxid
	 * @return
	 */
	public void updateUserOperTime(String wxid) { 
		getDao().updateUserOperTime(wxid);
	}
}