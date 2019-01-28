package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.IntegralCommodityRecord;
import cn.trustway.weixin.dao.IntegralMallDao;
import cn.trustway.weixin.model.IntegralMallRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<IntegralCommodityRecord> queryRecordByList(IntegralMallRecordModel model) {
        return getDao().queryRecordByList(model);
    }

    public Integer queryRecordByCount(IntegralMallRecordModel model) {
        return getDao().queryRecordByCount(model);
    }

    public void updateRecordStatus(IntegralMallRecordModel model) {
        getDao().updateRecordStatus(model);
    }
}
