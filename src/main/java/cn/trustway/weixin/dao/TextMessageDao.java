package cn.trustway.weixin.dao;

/**
 * 短信发送操作接口
 *
 * @param <T>
 * @author yexin
 */
public interface TextMessageDao<T> extends BaseDao<T> {
    /**
     * 根据手机号和验证码验证
     * @param t
     * @return
     */
    T getValidMessageByCode(T t);
}