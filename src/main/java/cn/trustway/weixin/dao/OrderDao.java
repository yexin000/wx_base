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

    /**
     * 查询今日成拍金额
     * @return
     */
    Double queryByFilmingTodayMoney(String wxid);

    /**
     * 查询成拍数量
     * @return
     */
    Integer queryByFilmingTodayNum(String wxid);

    /**
     * 查询付款金额
     * @return
     */
    Double queryByPaymentTodayMoney(String wxid);

    /**
     * 查询付款数量
     * @return
     */
    Integer queryByPaymentTodayNum(String wxid);

    /**
     * 查询收款金额
     * @return
     */
    Double queryByCollectionTodayMoney(String wxid);

    /**
     * 查询收款数量
     * @return
     */
    Integer queryByCollectionTodayNum(String wxid);
}
