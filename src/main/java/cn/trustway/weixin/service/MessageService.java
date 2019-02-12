package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.MessageDao;
import cn.trustway.weixin.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消息服务类
 *
 * @author dingjia
 */
@Service("messageService")
public class MessageService<T> extends BaseService<T> {
    @Autowired
    private MessageDao<T> dao;

    @Override
    public MessageDao<T> getDao() {
        return dao;
    }

    public int queryGroupByCount(MessageModel model) throws Exception {
        return getDao().queryGroupByCount(model);
    }

    public int queryUserByCount(MessageModel model) throws Exception {
        return getDao().queryUserByCount(model);
    }

    public List<T> queryGroupByList(MessageModel model) {
        return getDao().queryGroupByList(model);
    }

    public List<T> queryParentByList(Map<String, Object> params) {
        return getDao().queryParentByList(params);
    }

    public List<T> queryUserByList(MessageModel params) {
        return getDao().queryUserByList(params);
    }

}
