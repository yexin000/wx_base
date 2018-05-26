package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 用户收藏Bean
 *
 * @author yexin
 *
 */
public class Favorite extends BaseBean {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 收藏人wxid，关联weixin_user表
     */
    private String wxid;
    /**
     * 收藏类型:1-拍卖品,2-商品
     */
    private String favType;
    /**
     * 收藏项id
     */
    private String favId;
    /**
     * 收藏项名称
     */
    private String favName;
    /**
     * 创建/收藏时间
     */
    private Date createTime;
    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品库存
     */
    private String stock;

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

    public String getFavName() {
        return favName;
    }

    public void setFavName(String favName) {
        this.favName = favName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
