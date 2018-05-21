package cn.trustway.weixin.dao;

import java.util.List;

/**
 * 拍品dao
 * @author yexin
 *
 * @param <T>
 */
public interface AuctionItemDao<T> extends BaseDao<T> {
    /**
     * 设置开始时间超出当前时间的未开始拍卖的拍卖会为已开始
     * @return
     */
    void updateAuctionItemStart();

    /**
     * 查询结束时间超出当前时间的正在拍卖的拍卖品
     * @return
     */
    List<T> queryInAuctionByList();
}
