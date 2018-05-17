package cn.trustway.weixin.bean;

/**
 * 退货退款日志bean
 * @author yexin
 */
public class RefundLog extends Refund {
    /**
     * 退货退款id
     */
    private Integer refundId;

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }
}
