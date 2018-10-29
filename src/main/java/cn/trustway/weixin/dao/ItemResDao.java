package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.ItemRes;

import java.util.Map;

/**
 * 竞拍会/拍品-资源关联dao
 * @author yexin
 *
 * @param <T>
 */
public interface ItemResDao<T> extends BaseDao<T> {

    /**
     * 查找第一张图
     * @return
     */
    ItemRes queryByConId (Map<String, Object> params);
}
