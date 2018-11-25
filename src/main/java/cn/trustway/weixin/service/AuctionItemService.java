package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.AuctionItemDao;
import cn.trustway.weixin.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<T> queryInAuctionByList() {
        return getDao().queryInAuctionByList();
    }

    public void updateByItemStatus(Map<String, Object> params) {
        getDao().updateByItemStatus(params);
    }


    public List<T> queryMyJoinByList(Map<String, Object> params){
        return getDao().queryMyJoinByList(params);
    }

    public List<T> queryByWxid(Map<String, Object> params) {
        return getDao().queryByWxid(params);
    }

    public void changeItemOnsale(T t) {
        getDao().changeItemOnsale(t);
    }
}
