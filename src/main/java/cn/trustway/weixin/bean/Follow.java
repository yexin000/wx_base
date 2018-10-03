package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;

/**
 * 拍卖会Bean
 *
 * @author dingjia
 *
 */
public class Follow extends BaseBean {
    /**
     *主键-id
     */
    private Integer id;

    /**
     *关注类型   1：用户，2：拍品，3：展览
     */
    private Integer followType;
    /**
     *关注用户
     */
    private String wxid;

    /**
     *被关注用户
     */
    private String followWxId;

    /**
     *被关注id
     */
    private Integer followId;

    /**
     *被关注id 属性（区别拍品和商品）
     */
    private String followAttribute;

    /**
     *被关注名
     */
    private String followName;

    /**
     *图片路径
     */
    private String path;

    /**
     *创建时间
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

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFollowAttribute() {
        return followAttribute;
    }

    public void setFollowAttribute(String followAttribute) {
        this.followAttribute = followAttribute;
    }

    public String getFollowWxId() {
        return followWxId;
    }

    public void setFollowWxId(String followWxId) {
        this.followWxId = followWxId;
    }
}
