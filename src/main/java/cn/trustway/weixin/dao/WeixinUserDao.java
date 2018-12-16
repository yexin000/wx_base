package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.WeixinUser;

/**
 * 微信用户操作接口
 * @author yexin
 *
 * @param <T>
 */
public interface WeixinUserDao<T> extends BaseDao<T> {

	/**
	 * 插入微信用户
	 * @param model
	 * @return
	 */
	public void insertWeixinUser(WeixinUser weixinUser);
	
	
	/**
	 * 删除微信用户
	 * @param wxid
	 * @return
	 */
	public void deleteWeixinUser(String wxid);
	
	/**
	 * 查找微信用户
	 * @param wxid
	 * @return
	 */
	public WeixinUser queryWeixinUser(String wxid);
	
	/**
	 * 修改微信用户
	 * @param wxid
	 * @return
	 */
	public void updateWeixinUser(WeixinUser weixinUser);
	
	/**
	 * 更新微信用户操作时间
	 * @param wxid
	 * @return
	 */
	public void updateUserOperTime(String wxid);

	/**
	 * 更新微信用户余额
	 * @param weixinUser
	 * @return
	 */
	public void updateByBal(WeixinUser weixinUser);

	/**
	 * 更新微信用户提现中金额
	 * @param weixinUser
	 * @return
	 */
	public void updateByExtracting(WeixinUser weixinUser);

	/**
	 * 更新用户手机号
	 * @param weixinUser
	 */
	void updatePhonenumByWxid(WeixinUser weixinUser);

}