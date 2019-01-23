package cn.trustway.weixin.dao;

import cn.trustway.weixin.model.BusinessModel;

import java.util.Map;

/**
 * 拍卖商家操作接口
 * @author yexin
 *
 * @param <T>
 */
public interface BusinessDao<T> extends BaseDao<T> {

    /**
     * 根据ID查找对象
     * @param bussiness
     * @return
     */
    public T queryCountById(BusinessModel bussiness);

    void updateByBusinessStatus(Map<String, Object> params);

    T queryByWxid(String wxid);

    void applyExcellent(String id);

    void updateByIsExcellent(Map<String, Object> params);
}
