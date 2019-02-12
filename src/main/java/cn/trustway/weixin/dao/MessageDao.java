package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.ActivityJoinUser;
import cn.trustway.weixin.model.MessageModel;

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
     * 查询数量
     * @param model
     * @return
     */
    public int queryGroupByCount(MessageModel model);

    /**
     * 查询数量
     * @param model
     * @return
     */
    public int queryUserByCount(MessageModel model);

    /**
     * 查询聊天内容
     * @return
     */
    List<T> queryGroupByList(MessageModel params);

    /**
     * 查询聊天内容
     * @return
     */
    List<T> queryParentByList(Map<String, Object> params);

    /**
     * 查询聊天内容
     * @return
     */
    List<T> queryUserByList(MessageModel params);

}
