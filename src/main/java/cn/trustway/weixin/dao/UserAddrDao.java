package cn.trustway.weixin.dao;

import java.util.Map;

/**
 * 用户地址dao
 * @author yexin
 */
public interface UserAddrDao<T> extends BaseDao<T> {
    /**
     * 设置商品所有出价记录为出局
     * @param wxid
     * @return
     */
    T getDefaultAddrByWxid(String wxid);

    /**
     * 设置默认收货地址
     * @param params
     * @return
     */
    void setDefaultAddr(Map<String, Object> params);
}
