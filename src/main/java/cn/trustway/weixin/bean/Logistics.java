package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 物流bean
 * @author yexin
 */
public class Logistics {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 物流公司
     */
    private String logisticsComp;
    /**
     * 发货时间
     */
    private Date sendTime;
    /**
     * 运单编号
     */
    private String logisticsCode;
    /**
     * 物流类型:1-发货,2-退货
     */
    private String logisticsType;
    /**
     * 退款id
     */
    private Integer refundId;

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

    public String getLogisticsComp() {
        return logisticsComp;
    }

    public void setLogisticsComp(String logisticsComp) {
        this.logisticsComp = logisticsComp;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }
}
