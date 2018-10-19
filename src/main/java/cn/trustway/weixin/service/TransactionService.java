package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 大额交易服务类
 *
 * @author dingjia
 */
@Service("transactionService")
public class TransactionService<T> extends BaseService<T> {
    @Autowired
    private TransactionDao<T> dao;

    @Override
    public TransactionDao<T> getDao() {
        return dao;
    }

}
