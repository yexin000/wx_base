package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 积分商城兑换记录bean
 */
public class IntegralCommodityRecord extends IntegralCommodity {
    /**
     * 积分商品id
     */
    private Integer icId;
    /**
     * wxid
     */
    private String wxid;

    private String wxidName;

    private String icName;
    /**
     * 0是未处理，1是已处理
     */
    private String status;
    /**
     * 兑换数量
     */
    private Integer num;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 兑换地址
     */
    private String address;

    public Integer getIcId() {
        return icId;
    }

    public void setIcId(Integer icId) {
        this.icId = icId;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWxidName() {
        return wxidName;
    }

    public void setWxidName(String wxidName) {
        this.wxidName = wxidName;
    }

    public String getIcName() {
        return icName;
    }

    public void setIcName(String icName) {
        this.icName = icName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
