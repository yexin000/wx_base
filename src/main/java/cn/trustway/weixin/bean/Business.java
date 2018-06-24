package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 拍卖商家bean
 * @author yexin
 *
 */
public class Business extends BaseBean {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 商家地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 商家电话
     */
    private String telNum;
    /**
     * 图标地址
     */
    private String logoPath;

    /**
     * 状态：0-删除,1-正常
     */
    private String status;

    /**
     * 是否推荐：0-否,1-是
     */
    private String isShow;

    /**
     * 微信号
     */
    private String wxAccount;

    /**
     * 商家名称
     */
    private String bussinessName;

    /**
     * 商家地址
     */
    private String businessaddress;

    /**
     * 申请加入的wxid
     */
    private String wxid;

    /**
     * 审核状态
     */
    private String auditStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getBusinessaddress() {
        return businessaddress;
    }

    public void setBusinessaddress(String businessaddress) {
        this.businessaddress = businessaddress;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
