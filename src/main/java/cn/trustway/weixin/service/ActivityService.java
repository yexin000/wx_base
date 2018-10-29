package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.ActivityJoinUser;
import cn.trustway.weixin.dao.ActivityDao;
import cn.trustway.weixin.dao.AuctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 活动服务类
 *
 * @author dingjia
 */
@Service("activityService")
public class ActivityService<T> extends BaseService<T> {
    @Autowired
    private ActivityDao<T> dao;

    @Override
    public ActivityDao<T> getDao() {
        return dao;
    }

    /**
     * 设置开始时间超出当前时间的正常的活动为已开始
     * @return
     */
    public void updateAuctionStart() {
        getDao().updateActivityStart();
    }

    /**
     * 设置结束时间超出当前时间的正在竞拍的竞拍会为已结束
     * @return
     */
    public void updateAuctionEnd() {
        getDao().updateActivityEnd();
    }

    /**
     * 根据活动id查询报名状态
     * @return
     */
    public Integer queryJoinById(Map<String, Object> params) {
        return getDao().queryJoinById(params);
    }

    public List<ActivityJoinUser> queryJoinListById(Map<String, Object> params) {

        return getDao().queryJoinListById(params);
    }

    /**
     * 报名
     * @return
     */
    public Integer addJoin(Map<String, Object> params) {
        return getDao().addJoin(params);
    }

    public List<T> queryByJoinActivity(Map<String, Object> params) {
        return getDao().queryByJoinActivity(params);
    }
}
