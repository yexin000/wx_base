package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;

/**
 * 拍卖会Bean
 *
 * @author dingjia
 *
 */
public class Activity extends BaseBean {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *活动名称
     */
    private String activityName;
    /**
     *开始时间
     */
    private Date starttime;
    /**
     *结束时间
     */
    private Date endtime;
    /**
     *创建时间
     */
    private Date createtime;
    /**
     *描述
     */
    private String activityDescribe;
    /**
     *金额
     */
    private String activityMoney;
    /**
     *状态：0-删除，1-正常, 3-已开始,4-已结束
     */
    private String status;
    /**
     *浏览数目
     */
    private Integer viewnum;
    /**
     *点赞数
     */
    private String likenum;
    /**
     *分享数
     */
    private String sharenum;
    /**
     * 资源列表
     */
    private List<ItemRes> resList;
    /**
     * 报名id
     */
    private String joinId ;

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

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    public String getActivityMoney() {
        return activityMoney;
    }

    public void setActivityMoney(String activityMoney) {
        this.activityMoney = activityMoney;
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

    public String getLikenum() {
        return likenum;
    }

    public void setLikenum(String likenum) {
        this.likenum = likenum;
    }

    public String getSharenum() {
        return sharenum;
    }

    public void setSharenum(String sharenum) {
        this.sharenum = sharenum;
    }

    public List<ItemRes> getResList() {
        return resList;
    }

    public void setResList(List<ItemRes> resList) {
        this.resList = resList;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }
}
