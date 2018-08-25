package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.ActivityJoinUser;

import java.util.List;
import java.util.Map;

/**
 * 活动dao
 * @author dingjia
 *
 * @param <T>
 */
public interface ActivityDao<T> extends BaseDao<T> {
    /**
     * 设置开始时间超出当前时间的正常的活动为已开始
     * @return
     */
    void updateActivityStart();

    /**
     * 设置结束时间超出当前时间的正在举行的活动为已结束
     * @return
     */
    void updateActivityEnd();

    /**
     * 根据活动id查询报名状态
     * @return
     */
    Integer queryJoinById(Map<String, Object> params);
    /**
     * 报名
     * @return
     */
    Integer  addJoin(Map<String, Object> params);
    /**
     * 查询报名用户
     * @return
     */
    List<ActivityJoinUser> queryJoinListById(Map<String, Object> params);

    List<T> queryByJoinActivity(Map<String, Object> params);
}
