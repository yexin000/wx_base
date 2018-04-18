package cn.trustway.weixin.bean;

/**
 * 微信用户bean
 * @author yexin
 *
 */
public class WeixinUser extends BaseBean {
	private Integer xh;// 序号
	private String wxid;// 微信openid
	private String zt;// 状态
	private String ylzd1;
	
	public Integer getXh() {
		return xh;
	}
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	public String getWxid() {
		return wxid;
	}
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getYlzd1() {
		return ylzd1;
	}
	public void setYlzd1(String ylzd1) {
		this.ylzd1 = ylzd1;
	}
}