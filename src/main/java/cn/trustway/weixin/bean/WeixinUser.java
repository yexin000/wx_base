package cn.trustway.weixin.bean;

/**
 * 微信用户bean
 * @author yexin
 *
 */
public class WeixinUser extends BaseBean {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 微信id
	 */
	private String wxid;
	/**
	 * 手机号码
	 */
	private String phoneNum;
	/**
	 * 余额
	 */
	private Double balance;
	/**
	 * 提取中金额
	 */
	private Double moneyExtracting;
	/**
	 * 已提取金额
	 */
	private Double extractedMoney;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 微信头像地址
	 */
	private String avatarUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getMoneyExtracting() {
		return moneyExtracting;
	}

	public void setMoneyExtracting(Double moneyExtracting) {
		this.moneyExtracting = moneyExtracting;
	}

	public Double getExtractedMoney() {
		return extractedMoney;
	}

	public void setExtractedMoney(Double extractedMoney) {
		this.extractedMoney = extractedMoney;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}