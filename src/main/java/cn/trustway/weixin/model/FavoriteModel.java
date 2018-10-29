package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 用户收藏model
 */
public class FavoriteModel extends BaseModel {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 收藏人wxid，关联weixin_user表
     */
    private String wxid;
    /**
     * 收藏类型:1-竞拍品,2-商品
     */
    private String favType;
    /**
     * 收藏项id
     */
    private String favId;
    /**
     * 创建/收藏时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getFavType() {
        return favType;
    }

    public void setFavType(String favType) {
        this.favType = favType;
    }

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
