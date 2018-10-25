package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.BlacklistDao;
import cn.trustway.weixin.model.BlacklistModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 黑名单服务类
 *
 * @author yexin
 */
@Service("blacklistService")
public class BlacklistService<T> extends BaseService<T> {
    @Autowired
    private BlacklistDao<T> dao;

    @Override
    public BlacklistDao<T> getDao() {
        return dao;
    }

    public List<T> queryBackgroundByList(BlacklistModel model) {
        Integer rowCount = queryBackgroundByCount(model);
        model.getPager().setRowCount(rowCount);
        return dao.queryBackgroundByList(model);
    }

    public int queryBackgroundByCount(BlacklistModel model) {
        return dao.queryBackgroundByCount(model);
    }

    public void deleteByModel(BlacklistModel model) {
        dao.deleteByModel(model);
    }
}
