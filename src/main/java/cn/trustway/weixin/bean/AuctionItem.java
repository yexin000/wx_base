package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;
/**
 * 拍品Bean
 *
 * @author yexin
 *
 */
public class AuctionItem extends BaseBean {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *拍品名称
     */
    private String name;
    /**
     *拍品类型：待定
     */
    private String type;
    /**
     *介绍描述
     */
    private String description;
    /**
     *开始拍卖时间
     */
    private Date startTime;
    /**
     *结束拍卖时间
     */
    private Date endTime;
    /**
     *起拍价格
     */
    private Double startPrice;
    /**
     *当前拍卖价格
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
     *拍卖状态：0-未开始拍卖，1-正在拍卖，2-拍卖成功，3-流拍
     */
    private String auctionStatus;
    /**
     *拍卖会id
     */
    private Integer auctionId;
    /**
     *拍卖会名称
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
     * 万分比
     */
    private String wanfenbi ;

    /**
     * 是否首页轮播图显示
     */
    private String isShowBanner ;

    /**
     * 资源列表
     */
    private List<ItemRes> resList;

    /**
     * 库存
     */
    private String stock ;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public List<ItemRes> getResList() {
        return resList;
    }

    public void setResList(List<ItemRes> resList) {
        this.resList = resList;
    }

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

    public String getWanfenbi() {
        return wanfenbi;
    }

    public void setWanfenbi(String wanfenbi) {
        this.wanfenbi = wanfenbi;
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
}
