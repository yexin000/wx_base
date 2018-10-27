package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.ExchangeRecodeDao;
import cn.trustway.weixin.dao.IntegralMallDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 积分商城商品兑换记录服务类
 *
 * @author dingjia
 */
@Service("exchangeRecodeService")
public class ExchangeRecodeService<T> extends BaseService<T> {
    @Autowired
    private ExchangeRecodeDao<T> dao;

    @Override
    public ExchangeRecodeDao<T> getDao() {
        return dao;
    }

}
