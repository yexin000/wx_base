package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 关注模型
 *
 * @author dingjia
 *
 */
public class FollowModel extends BaseModel {

    /**
     *主键-id
     */
    private Integer id;

    /**
     *关注id
     */
    private Integer followId;

    /**
     *关注用户
     */
    private String wxid;

    /**
     *关注类型
     */
    private Integer followType;

    /**
     *关注时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
