package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 金额流水Bean
 * @author yexin
 */
public class MoneyStream {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 用户wxid
     */
    private String wxid;
    /**
     * 流水类型：1-保证金,2-支付,3-提现,4-退款
     */
    private String streamType;
    /**
     * 流水金额
     */
    private Double streamMoney;
    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public Double getStreamMoney() {
        return streamMoney;
    }

    public void setStreamMoney(Double streamMoney) {
        this.streamMoney = streamMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
