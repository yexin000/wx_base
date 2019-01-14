package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.AuctionDao;
import cn.trustway.weixin.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public AuctionDao<T> getDao() {
        return dao;
    }

    /**
     * 设置开始时间超出当前时间的正常的拍卖会为已开始
     * @return
     */
    public void updateAuctionStart() {
        getDao().updateAuctionStart();
    }

    /**
     * 设置结束时间超出当前时间的正在拍卖的拍卖会为已结束
     * @return
     */
    public void updateAuctionEnd() {
        getDao().updateAuctionEnd();
    }

    public List<T> queryByJoinAuction(Map<String, Object> params) {
        return getDao().queryByJoinAuction(params);
    }

    public List<Integer> queryByAuctionStartList() {
        return getDao().queryByAuctionStartList();
    }

}
