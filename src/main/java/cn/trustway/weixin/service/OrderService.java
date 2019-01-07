package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.OrderDao;
import cn.trustway.weixin.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单服务类
 *
 * @author yexin
 */
@Service("orderService")
public class OrderService<T> extends BaseService<T> {
    @Autowired
    private OrderDao<T> dao;

    @Override
    public OrderDao<T> getDao() {
        return dao;
    }

    /**
     * 查询所有待支付且超过失效时间的订单
     * @return
     */
    public List<T> queryInvalidOrderList() {
        return getDao().queryInvalidOrderList();
    }

    public Double queryByFilmingTodayMoney(String wxid) {
        return getDao().queryByFilmingTodayMoney(wxid);
    }

    public Integer queryByFilmingTodayNum(String wxid) {
        return getDao().queryByFilmingTodayNum(wxid);
    }

    public Double queryByPaymentTodayMoney(String wxid) {
        return getDao().queryByPaymentTodayMoney(wxid);
    }

    public Integer queryByPaymentTodayNum(String wxid) {
        return getDao().queryByPaymentTodayNum(wxid);
    }

    public Double queryByCollectionTodayMoney(String wxid) {
        return getDao().queryByCollectionTodayMoney(wxid);
    }

    public Integer queryByCollectionTodayNum(String wxid) {
        return getDao().queryByCollectionTodayNum(wxid);
    }

    public List<T> queryMyOutOrderByList(OrderModel params) {
        return getDao().queryMyOutOrderByList(params);
    }
}
