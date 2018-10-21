package cn.trustway.weixin.dao;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询我参与的拍卖品
     * @return
     */
    List<T> queryMyJoinByList(Map<String, Object> params);

    void updateByItemStatus(Map<String, Object> params);

    /**
     * 查询用户下的拍卖品
     * @return
     */
    List<T> queryByWxid(Map<String, Object> params);
}
