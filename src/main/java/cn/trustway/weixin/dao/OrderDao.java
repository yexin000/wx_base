package cn.trustway.weixin.dao;

import java.util.List;

/**
 * 订单dao
 * @author yexin
 *
 * @param <T>
 */
public interface OrderDao<T> extends BaseDao<T> {
    /**
     * 查询所有待支付且超过失效时间的订单
     * @return
     */
    List<T> queryInvalidOrderList();
}
