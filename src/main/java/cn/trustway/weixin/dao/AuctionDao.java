package cn.trustway.weixin.dao;

/**
 * 拍卖会dao
 * @author yexin
 *
 * @param <T>
 */
public interface AuctionDao<T> extends BaseDao<T> {
    /**
     * 设置开始时间超出当前时间的正常的拍卖会为已开始
     * @return
     */
    void updateAuctionStart();

    /**
     * 设置结束时间超出当前时间的正在拍卖的拍卖会为已结束
     * @return
     */
    void updateAuctionEnd();
}
