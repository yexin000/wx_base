package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.FavoriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户收藏服务类
 *
 * @author yexin
 */
@Service("favoriteService")
public class FavoriteService<T> extends BaseService<T> {
    @Autowired
    private FavoriteDao<T> dao;

    @Override
    public FavoriteDao<T> getDao() {
        return dao;
    }

    /**
     * 根据wxid、favid查询记录
     * @param params
     * @return
     */
    public T queryByWxidAndFavId(Map params) {
        return getDao().queryByWxidAndFavId(params);
    }
}
