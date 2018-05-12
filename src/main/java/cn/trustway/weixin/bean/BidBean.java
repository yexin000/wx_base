package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 出价bean
 */
public class BidBean extends BaseBean {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 出价人id，关联weixin_user表id
     */
    private Integer wxUserId;
    /**
     * 出价人名字，关联weixin_user表nickname
     */
    private String wxUserName;
    /**
     * 拍品id
     */
    private Integer auctionItemId;
    /**
     * 拍品名称
     */
    private String auctionItemName;
    /**
     * 出价价格
     */
    private Double bidPrice;
    /**
     * 状态：0-出局，1-领先
     */
    private String status;
    /**
     * 出价时间
     */
    private Date bidTime;
    /**
     * 出价人名字，关联weixin_user表wxid
     */
    private String wxid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Integer wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getWxUserName() {
        return wxUserName;
    }

    public void setWxUserName(String wxUserName) {
        this.wxUserName = wxUserName;
    }

    public Integer getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Integer auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public String getAuctionItemName() {
        return auctionItemName;
    }

    public void setAuctionItemName(String auctionItemName) {
        this.auctionItemName = auctionItemName;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }
}
