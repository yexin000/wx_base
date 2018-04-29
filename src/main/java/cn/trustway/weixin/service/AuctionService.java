package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.AuctionDao;
import cn.trustway.weixin.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拍卖会服务类
 *
 * @author yexin
 */
@Service("auctionService")
public class AuctionService<T> extends BaseService<T> {
    @Autowired
    private AuctionDao<T> dao;

    @Override
    public BaseDao<T> getDao() {
        return dao;
    }
}
