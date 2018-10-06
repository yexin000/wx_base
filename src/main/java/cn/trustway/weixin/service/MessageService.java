package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
