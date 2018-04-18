package cn.trustway.weixin.model;

/**
 * 微信用户模型
 * @author yexin
 *
 */
public class WeixinUserModel extends BaseModel {
	private Integer xh;// 序号
	private String wxid;// 微信openid
	private String zt;// 状态
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
}