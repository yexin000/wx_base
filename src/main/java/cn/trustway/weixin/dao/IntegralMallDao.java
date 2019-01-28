package cn.trustway.weixin.dao;


import cn.trustway.weixin.bean.IntegralCommodityRecord;
import cn.trustway.weixin.model.IntegralMallRecordModel;

import java.util.List;

/**
 * 积分商城dao
 * @author dingjia
 *
 * @param <T>
 */
public interface IntegralMallDao<T> extends BaseDao<T> {

    List<IntegralCommodityRecord> queryRecordByList(IntegralMallRecordModel model);

    Integer queryRecordByCount(IntegralMallRecordModel model);

    void updateRecordStatus(IntegralMallRecordModel model);
}
