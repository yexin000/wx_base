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

	/**
	 * 买家/卖家名称
	 */
	private String bussinessName;

	/**
	 * 买家/卖家地址
	 */
	private String businessAddress;

	/**
	 * 微信号
	 */
	private String wxAccount;

	/**
	 * 积分
	 */
	private String integral;

	/**
	 * 总积分
	 */
	private String countIntegral;

	/**
	 * vip等级
	 */
	private String vipGrade;

	/**
	 * 粉丝数
	 */
	private Integer followNum;

	/**
	 * 关注数
	 */
	private Integer myFollowNum;


	/**
	 * 关注展览数
	 */
	private Integer myFollowAuctionNum;

	/**
	 * 关注展品数
	 */
	private Integer myFollowAuctionItemNum;

	/**
	 * 未读消息数量
	 */
	private Integer msgCount;


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

	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getWxAccount() {
		return wxAccount;
	}

	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getVipGrade() {
		return vipGrade;
	}

	public void setVipGrade(String vipGrade) {
		this.vipGrade = vipGrade;
	}

	public Integer getFollowNum() {
		return followNum;
	}

	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
	}

	public String getCountIntegral() {
		return countIntegral;
	}

	public void setCountIntegral(String countIntegral) {
		this.countIntegral = countIntegral;
	}

	public Integer getMyFollowNum() {
		return myFollowNum;
	}

	public void setMyFollowNum(Integer myFollowNum) {
		this.myFollowNum = myFollowNum;
	}

	public Integer getMyFollowAuctionNum() {
		return myFollowAuctionNum;
	}

	public void setMyFollowAuctionNum(Integer myFollowAuctionNum) {
		this.myFollowAuctionNum = myFollowAuctionNum;
	}

	public Integer getMyFollowAuctionItemNum() {
		return myFollowAuctionItemNum;
	}

	public void setMyFollowAuctionItemNum(Integer myFollowAuctionItemNum) {
		this.myFollowAuctionItemNum = myFollowAuctionItemNum;
	}

	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}
}