package cn.trustway.weixin.dao;

import cn.trustway.weixin.model.BusinessModel;

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
}
