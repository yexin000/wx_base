package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.BlacklistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
