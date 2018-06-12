package cn.trustway.weixin.dao;

/**
 * 用户formid
 * @param <T>
 */
public interface WxFormDao<T> extends BaseDao<T>  {
    /**
     * 根据wxid查询7日内有效记录
     * @param wxid
     * @return
     */
    public T queryByWxid(String wxid);
}
