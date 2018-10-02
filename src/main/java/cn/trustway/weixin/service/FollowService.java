package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.Follow;
import cn.trustway.weixin.dao.FollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 关注服务类
 *
 * @author dingjia
 */
@Service("followService")
public class FollowService<T> extends BaseService<T> {
    @Autowired
    private FollowDao<T> dao;

    @Override
    public FollowDao<T> getDao() {
        return dao;
    }


    /**
     * 查询关注列表
     * @param params
     * @return
     */
    public List<Follow> queryUserFollowList(Map<String, Object> params) {

        return getDao().queryUserFollowList(params);
    }

    /**
     * 查询我是所有关注列表
     * @param params
     * @return
     */
    public List<Follow> queryFollowUserByList(Map<String, Object> params) {

        return getDao().queryFollowUserByList(params);
    }

    /**
     * 查询我是所有关注列表
     * @param params
     * @return
     */
    public List<Follow> queryFollowAuctionItemByList(Map<String, Object> params) {

        return getDao().queryFollowAuctionItemByList(params);
    }

    /**
     * 查询我是所有关注列表
     * @param params
     * @return
     */
    public List<Follow> queryFollowAuctionByList(Map<String, Object> params) {

        return getDao().queryFollowAuctionByList(params);
    }




    /**
     * 关注
     * @return
     */
    public Integer addJoin(Map<String, Object> params) {

        return getDao().addFollow(params);
    }

}
