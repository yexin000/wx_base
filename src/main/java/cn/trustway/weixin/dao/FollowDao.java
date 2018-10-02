package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.Follow;

import java.util.List;
import java.util.Map;

/**
 * 关注dao
 * @author dingjia
 *
 * @param <T>
 */
public interface FollowDao<T> extends BaseDao<T> {

    /**
     * 添加关注
     * @return
     */
    Integer  addFollow(Map<String, Object> params);

    /**
     * 查询关注列表
     * @return
     */
    List<Follow> queryUserFollowList(Map<String, Object> params);

    /**
     * 查询关注列表
     * @return
     */
    List<Follow> queryFollowUserByList(Map<String, Object> params);

    /**
     * 查询关注列表
     * @return
     */
    List<Follow> queryFollowAuctionItemByList(Map<String, Object> params);

    /**
     * 查询关注列表
     * @return
     */
    List<Follow> queryFollowAuctionByList(Map<String, Object> params);

}
