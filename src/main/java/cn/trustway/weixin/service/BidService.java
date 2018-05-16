package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.BaseDao;
import cn.trustway.weixin.dao.BidDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出价服务类
 *
 * @author yexin
 */
@Service("bidService")
public class BidService<T> extends BaseService<T> {
    @Autowired
    private BidDao<T> dao;

    @Override
    public BaseDao<T> getDao() {
        return dao;
    }
}