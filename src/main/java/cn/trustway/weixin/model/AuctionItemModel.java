package cn.trustway.weixin.model;

import java.util.Date;
import java.util.List;

/**
 * 拍品模型
 *
 * @author yexin
 */
public class AuctionItemModel extends BaseModel {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *拍品名称
     */
    private String name;
    /**
     *拍品类型：wx_code.code
     */
    private String type;
    /**
     *介绍描述
     */
    private String description;
    /**
     *开始竞拍时间
     */
    private Date startTime;
    /**
     *结束竞拍时间
     */
    private Date endTime;
    /**
     *起拍价格
     */
    private Double startPrice;
    /**
     *当前竞拍价格
     */
    private Double curPrice = 0.0d;
    /**
     *成交价格
     */
    private Double finalPrice = 0.0d;
    /**
     *加价最低价格
     */
    private Double addPrice;
    /**
     *保证金
     */
    private Double depositPrice = 0.0d;
    /**
     *手续费比率
     */
    private Double rate;
    /**
     *拍品详情
     */
    private String detail;
    /**
     *状态：0-删除，1-未审核，2-审核未通过，3-正常(审核通过)
     */
    private String status;
    /**
     *是否在首页显示：0-否，1-是
     */
    private String isShow;
    /**
     *竞拍状态：0-未开始竞拍，1-正在竞拍，2-竞拍成功，3-流拍
     */
    private String auctionStatus;
    /**
     *竞拍会id
     */
    private Integer auctionId;
    /**
     *竞拍会名称
     */
    private String auctionName;

    /**
     * 关联图片数量
     */
    private Integer picCount = 0;

    /**
     * 商家id
     */
    private String businessId ;

    /**
     * 是否在首页的轮播图显示
     */
    private String isShowBanner ;

    /**
     * 库存
     */
    private String stock ;

    /**
     * 上传人wxid
     */
    private String uploadWxid;

    /**
     * 商品性质:0-竞拍品,1-商品
     */
    private String attribute;

    /**
     * 是否v5商品
     */
    private String isV5;

    /**
     * 是否v5商品推荐
     */
    private String isV5Show;

    private List<String> attributes;

    /**
     * 是否上架：0-否,1-是
     */
    private String isOnsale;

    /**
     * 货号
     */
    private String itemNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(Double addPrice) {
        this.addPrice = addPrice;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(String auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getIsShowBanner() {
        return isShowBanner;
    }

    public void setIsShowBanner(String isShowBanner) {
        this.isShowBanner = isShowBanner;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUploadWxid() {
        return uploadWxid;
    }

    public void setUploadWxid(String uploadWxid) {
        this.uploadWxid = uploadWxid;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getIsV5() {
        return isV5;
    }

    public void setIsV5(String isV5) {
        this.isV5 = isV5;
    }

    public String getIsV5Show() {
        return isV5Show;
    }

    public void setIsV5Show(String isV5Show) {
        this.isV5Show = isV5Show;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getIsOnsale() {
        return isOnsale;
    }

    public void setIsOnsale(String isOnsale) {
        this.isOnsale = isOnsale;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }
}
