package cn.trustway.weixin.bean;

/**
 * 订单日志bean
 * @author yexin
 */
public class OrderLog extends Order {
    /**
     * 订单id
     */
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
