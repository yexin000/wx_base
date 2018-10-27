package cn.trustway.weixin.service;

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
    private IntegralMallDao<T> dao;

    @Override
    public IntegralMallDao<T> getDao() {
        return dao;
    }

}
