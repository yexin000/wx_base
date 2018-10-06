package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.IntegralMallDao;
import cn.trustway.weixin.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 积分商城服务类
 *
 * @author dingjia
 */
@Service("integraMallService")
public class IntegralMallService<T> extends BaseService<T> {
    @Autowired
    private IntegralMallDao<T> dao;

    @Override
    public IntegralMallDao<T> getDao() {
        return dao;
    }

}
