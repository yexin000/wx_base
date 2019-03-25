package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 退货退款bean
 * @author yexin
 */
public class Refund {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 退款金额
     */
    private Double refundMoney;
    /**
     * 商品id
     */
    private Integer itemId;
    /**
     * 退款/创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 退款状态:0-未退款,1-已退款
     */
    private String status;
    /**
     * 退款人wxid
     */
    private String wxid;
    /**
     * 退货退款原因
     */
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
