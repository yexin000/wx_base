package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.IdentifyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户鉴定服务类
 *
 * @author yexin
 */
@Service("identifyService")
public class IdentifyService<T> extends BaseService<T> {
    @Autowired
    private IdentifyDao<T> dao;

    @Override
    public IdentifyDao<T> getDao() {
        return dao;
    }
}
