package cn.trustway.weixin.model;

import java.util.Date;

public class IntegralMallRecordModel extends IntegralMallModel {
    /**
     * 积分商品id
     */
    private Integer icId;
    /**
     * wxid
     */
    private String wxid;
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
}
