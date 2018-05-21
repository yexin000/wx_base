package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.AuctionItemDao;
import cn.trustway.weixin.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拍品服务类
 *
 * @author yexin
 */
@Service("auctionItemService")
public class AuctionItemService<T> extends BaseService<T> {
    @Autowired
    private AuctionItemDao<T> dao;

    @Override
    public AuctionItemDao<T> getDao() {
        return dao;
    }

    public void updateAuctionItemStart() {
        getDao().updateAuctionItemStart();
    }
}
