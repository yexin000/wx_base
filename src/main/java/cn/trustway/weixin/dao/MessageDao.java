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
public interface MessageDao<T> extends BaseDao<T> {


    /**
     * 根据活动id查询报名状态
     * @return
     */
    Integer queryJoinById(Map<String, Object> params);
    /**
     * 发送消息
     * @return
     */
    Integer  addMessage(Map<String, Object> params);


    List<T> queryByJoinActivity(Map<String, Object> params);
}
