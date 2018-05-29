package cn.trustway.weixin.dao;

import java.util.Map;

/**
 * 用户收藏dao
 * @author yexin
 *
 * @param <T>
 */
public interface FavoriteDao<T> extends BaseDao<T> {
    /**
     * 根据wxid、favid查询记录
     * @param params
     * @return
     */
    T queryByWxidAndFavId(Map params);
}
