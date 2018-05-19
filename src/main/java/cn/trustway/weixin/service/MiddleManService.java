package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.BaseDao;
import cn.trustway.weixin.dao.MiddleManDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 经纪人服务类
 *
 * @author yexin
 */
@Service("middleManService")
public class MiddleManService<T> extends BaseService<T> {
    @Autowired
    private MiddleManDao<T> dao;

    @Override
    public BaseDao<T> getDao() {
        return dao;
    }
}
