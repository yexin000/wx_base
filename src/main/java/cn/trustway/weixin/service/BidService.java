package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.BidBean;
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
    public BidDao<T> getDao() {
        return dao;
    }

    /**
     * 设置商品所有出价记录为出局
     * @param auctionItemId
     * @return
     */
    public void updateBidOutByAuctionItemId(Integer auctionItemId) {
        this.dao.updateBidOutByAuctionItemId(auctionItemId);
    }

    /**
     * 根据竞拍品id查询最高出价记录(领先记录)
     * @param auctionItemId
     * @return
     */
    public BidBean queryBidByItemId(Integer auctionItemId) {
        return getDao().queryBidByItemId(auctionItemId);
    }
}
