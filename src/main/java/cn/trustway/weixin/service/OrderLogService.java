package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.OrderLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单日志服务类
 *
 * @author yexin
 */
@Service("orderLogService")
public class OrderLogService<T> extends BaseService<T> {
    @Autowired
    private OrderLogDao<T> dao;

    @Override
    public OrderLogDao<T> getDao() {
        return dao;
    }
}
