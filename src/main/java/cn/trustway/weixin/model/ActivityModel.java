package cn.trustway.weixin.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拍卖会模型
 *
 * @author yexin
 *
 */
public class ActivityModel extends BaseModel {
    /**
     *主键-id
     */
    Integer id;
    /**
     *活动名称
     */
    String activityName;
    /**
     *活动封面
     */
    String activityBg;
    /**
     *活动描述
     */
    String activityDescribe;
    /**
     *活动金额
     */
    BigDecimal activityMoney;
    /**
     *开始时间
     */
    Date starttime;
    /**
     *结束时间
     */
    Date endtime;
    /**
     *创建时间
     */
    Date createtime;
    /**
     *状态：0-删除，1-正常, 2-未开始,3=已开始 4-已结束
     */
    String status;
    /**
     *浏览数目
     */
    Integer viewnum;
    /**
     *点赞数目
     */
    Integer likenum;
    /**
     *点赞数目
     */
    Integer sharenum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityBg() {
        return activityBg;
    }

    public void setActivityBg(String activityBg) {
        this.activityBg = activityBg;
    }

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    public BigDecimal getActivityMoney() {
        return activityMoney;
    }

    public void setActivityMoney(BigDecimal activityMoney) {
        this.activityMoney = activityMoney;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViewnum() {
        return viewnum;
    }

    public void setViewnum(Integer viewnum) {
        this.viewnum = viewnum;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Integer getSharenum() {
        return sharenum;
    }

    public void setSharenum(Integer sharenum) {
        this.sharenum = sharenum;
    }
}
