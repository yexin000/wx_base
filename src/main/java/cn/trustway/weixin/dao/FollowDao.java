package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.Follow;
import cn.trustway.weixin.model.FollowModel;

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


    /**
     * 查询我关注的更多用户
     * @return
     */
    List<Follow> queryUserByList(FollowModel followModel);


    /**
     * 查询我关注的更多展览
     * @return
     */
    List<Follow> queryAuctionByList(FollowModel followModel);

    /**
     * 查询我关注的更多拍品
     * @return
     */
    List<Follow> queryAuctionItemByList(FollowModel followModel);

    /**
     * 根据wxid和关注id查询关注信息
     * @param params
     * @return
     */
    Follow queryByWxidAndFollowId(Map<String, Object> params);
}
