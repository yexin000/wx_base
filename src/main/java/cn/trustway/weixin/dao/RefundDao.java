package cn.trustway.weixin.dao;

/**
 * 退货退款dao
 * @param <T>
 */
public interface RefundDao<T> extends BaseDao<T> {
    /**
     * 根据订单id查询退货退款
     * @param id
     */
    T queryByOrderId(Object id);
}
