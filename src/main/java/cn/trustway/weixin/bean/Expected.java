package cn.trustway.weixin.bean;


import java.util.Date;

/**
 * 预出价用户Bean
 *
 * @author dingjia
 *
 */
public class Expected extends BaseBean {
    /**
     *报名-id
     */
    private Integer id;
    /**
     *商品id
     */
    private Integer commodityId;
    /**
     *用户
     */
    private String wxid;

    /**
     *商品上传用户
     */
    private String  uploadwxid;

    /**
     *时间
     */
    private Date createtime;

    /**
     *价钱
     */
    private Double price;

    /**
     *状态
     */
    private Integer status;

    /**
     *昵称
     */
    private String nickName;

    /**
     *头像
     */
    private String path;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUploadwxid() {
        return uploadwxid;
    }

    public void setUploadwxid(String uploadwxid) {
        this.uploadwxid = uploadwxid;
    }
}
