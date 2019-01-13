package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.Follow;
import cn.trustway.weixin.dao.FollowDao;
import cn.trustway.weixin.model.FollowModel;
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
     * 查询我关注的用户列表
     * @param params
     * @return
     */
    public List<Follow> queryFollowUserByList(Map<String, Object> params) {

        return getDao().queryFollowUserByList(params);
    }

    /**
     * 查询我关注的商品列表
     * @param params
     * @return
     */
    public List<Follow> queryFollowAuctionItemByList(Map<String, Object> params) {

        return getDao().queryFollowAuctionItemByList(params);
    }

    /**
     * 查询我关注的拍卖会列表
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



    /**
     * 查询我所有关注用户
     * @param followModel
     * @return
     */
    public List<Follow> queryUserByList(FollowModel followModel) {
        return getDao().queryUserByList(followModel);
    }

    /**
     * 查询我所有关注展览
     * @param followModel
     * @return
     */
    public List<Follow> queryAuctionByList(FollowModel followModel) {
        return getDao().queryAuctionByList(followModel);
    }

    /**
     * 查询我所有关注拍品
     * @param followModel
     * @return
     */
    public List<Follow> queryAuctionItemByList(FollowModel followModel) {
        return getDao().queryAuctionItemByList(followModel);
    }

    /**
     * 根据wxid和关注id查询关注信息
     * @param params
     * @return
     */
    public Follow queryByWxidAndFollowId(Map<String, Object> params) {
        return getDao().queryByWxidAndFollowId(params);
    }
}
