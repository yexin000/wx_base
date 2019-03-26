package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.RefundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退货退款服务类
 */
@Service("refundService")
public class RefundService<T> extends BaseService<T> {
    @Autowired
    private RefundDao<T> dao;

    @Override
    public RefundDao<T> getDao() {
        return dao;
    }

    public T queryByOrderId(Object id) {
        return getDao().queryByOrderId(id);
    }
}
