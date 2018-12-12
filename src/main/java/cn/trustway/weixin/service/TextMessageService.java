package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.TextMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信发送服务类
 *
 * @author yexin
 */
@Service("textMessageService")
public class TextMessageService<T> extends BaseService<T> {
    @Autowired
    private TextMessageDao<T> dao;

    @Override
    public TextMessageDao<T> getDao() {
        return dao;
    }

    /**
     * 根据手机号和验证码验证
     * @param t
     * @return
     */
    public T getValidMessageByCode(T t) {
        return getDao().getValidMessageByCode(t);
    }
}