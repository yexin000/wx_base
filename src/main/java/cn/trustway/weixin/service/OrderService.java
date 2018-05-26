package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
