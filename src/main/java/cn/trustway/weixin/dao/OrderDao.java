package cn.trustway.weixin.dao;

import cn.trustway.weixin.model.OrderModel;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询我的待处理订单
     * @return
     */
    List<T> queryMyOutOrderByList(OrderModel params);
}
