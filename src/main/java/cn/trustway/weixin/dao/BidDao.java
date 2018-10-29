package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.BidBean;

/**
 * 出价dao
 * @author yexin
 *
 * @param <T>
 */
public interface BidDao<T> extends BaseDao<T> {
    /**
     * 设置商品所有出价记录为出局
     * @param auctionItemId
     * @return
     */
    void updateBidOutByAuctionItemId(Integer auctionItemId);

    /**
     * 根据竞拍品id查询最高出价记录(领先记录)
     * @param auctionItemId
     * @return
     */
    BidBean queryBidByItemId(Integer auctionItemId);
}
