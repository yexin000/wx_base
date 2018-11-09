package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;

/**
 * 竞拍会Bean
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

    /**
     *会展创建人名称
     */
    private String createAuctionName;


    /**
     *会展创建人头像
     */
    private String createAuctionPath;

    /**
     *结束时间
     */
    private Date endTime;

    /**
     * 展品创建人
     */
    private String itemCreator;


    /**
     * 展品创建人头像
     */
    private String itemCreatorPath;

    /**
     * 价格
     */
    private Double price;

    private List<ItemRes> resList;

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

    public String getCreateAuctionName() {
        return createAuctionName;
    }

    public void setCreateAuctionName(String createAuctionName) {
        this.createAuctionName = createAuctionName;
    }

    public String getCreateAuctionPath() {
        return createAuctionPath;
    }

    public void setCreateAuctionPath(String createAuctionPath) {
        this.createAuctionPath = createAuctionPath;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getItemCreator() {
        return itemCreator;
    }

    public void setItemCreator(String itemCreator) {
        this.itemCreator = itemCreator;
    }

    public String getItemCreatorPath() {
        return itemCreatorPath;
    }

    public void setItemCreatorPath(String itemCreatorPath) {
        this.itemCreatorPath = itemCreatorPath;
    }

    public List<ItemRes> getResList() {
        return resList;
    }

    public void setResList(List<ItemRes> resList) {
        this.resList = resList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
