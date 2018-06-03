package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.MoneyStreamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拍品服务类
 *
 * @author yexin
 */
@Service("moneyStreamService")
public class MoneyStreamService<T> extends BaseService<T> {
    @Autowired
    private MoneyStreamDao dao;

    @Override
    public MoneyStreamDao<T> getDao() {
        return dao;
    }


}
