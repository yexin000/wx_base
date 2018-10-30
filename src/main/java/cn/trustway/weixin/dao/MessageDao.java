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
     * 发送消息
     * @return
     */
    Integer  addMessage(Map<String, Object> params);





    /**
     * 查询聊天内容
     * @return
     */
    List<T> queryParentByList(Map<String, Object> params);
}
