package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.MoneyStream;
import cn.trustway.weixin.bean.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.dao.WeixinUserDao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("weixinUserService")
public class WeixinUserService<T> extends BaseService<T> {

	@Autowired
	private WeixinUserDao<T> dao;

	@Autowired
	private MoneyStreamService<MoneyStream> moneyStreamService;



	public WeixinUserDao<T> getDao() {
		return dao;
	}
	
	/**
	 * 插入微信用户
	 * @param
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
	 * @param
	 * @return
	 */
	public void updateWeixinUser(WeixinUser weixinUser) {
		getDao().updateWeixinUser(weixinUser);
	}


	/**
	 * 修改微信用户
	 * @param
	 * @return
	 */
	public void updateByBal(WeixinUser weixinUser) {
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


	/**
	 * 生成用户流水
	 * @param
	 * @throws Exception
	 */
	public String payAfter(Order order) throws Exception {
		WeixinUser weixinUser =  queryWeixinUser(order.getWxid());
		if(null != weixinUser){
			BigDecimal moneyD = new BigDecimal(order.getOrderMoney());
			//更新用户余额
			double balance  = weixinUser.getBalance();
			BigDecimal bal = new BigDecimal(balance);
			bal = bal.add(moneyD);
			weixinUser.setBalance(bal.doubleValue());
			getDao().updateByBal(weixinUser);
			//生成充值流水
			MoneyStream bean = new MoneyStream();
			bean.setWxid(order.getWxid());
			bean.setStreammoney(moneyD.doubleValue());
			bean.setCreatetime(new Date());
			bean.setFlownumber(createCode());
			bean.setStreamtype(order.getOrderType());
			bean.setStatus(1);
			bean.setWhereabouts("用户余额");
			moneyStreamService.add(bean);
		}
		return "-1";
	}


	private String createCode (){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return  "No"+sdf.format(new Date())+getIDCode4();
	}

	/**
	 * 生成4位数的随机数
	 * @return
	 */
	protected static String getIDCode4() {
		int idCode = (int) (Math.random()*9000+1000);
		return idCode+"";
	}
}