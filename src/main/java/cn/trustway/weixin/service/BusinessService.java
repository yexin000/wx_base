package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.BaseDao;
import cn.trustway.weixin.dao.BusinessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拍卖商家服务类
 *
 * @author yexin
 */
@Service("businessService")
public class BusinessService<T> extends BaseService<T> {
    @Autowired
    private BusinessDao<T> dao;

    @Override
    public BaseDao<T> getDao() {
        return dao;
    }
}
