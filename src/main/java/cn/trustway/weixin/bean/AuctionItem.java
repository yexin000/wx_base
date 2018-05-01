package cn.trustway.weixin.bean;

import java.util.Date;

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
    private Date starttime;
    /**
     *结束拍卖时间
     */
    private Date endtime;
    /**
     *起拍价格
     */
    private Double startprice;
    /**
     *当前拍卖价格
     */
    private Double curprice = 0.0d;
    /**
     *成交价格
     */
    private Double finalprice = 0.0d;
    /**
     *加价最低价格
     */
    private Double addprice;
    /**
     *保证金
     */
    private Double depositprice = 0.0d;
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
    private String isshow;
    /**
     *拍卖状态：0-未开始拍卖，1-正在拍卖，2-拍卖成功，3-流拍
     */
    private String auctionstatus;
    /**
     *拍卖会id
     */
    private Integer auctionid;
    /**
     *拍卖会名称
     */
    private String auctionname;

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

    public Double getStartprice() {
        return startprice;
    }

    public void setStartprice(Double startprice) {
        this.startprice = startprice;
    }

    public Double getCurprice() {
        return curprice;
    }

    public void setCurprice(Double curprice) {
        this.curprice = curprice;
    }

    public Double getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(Double finalprice) {
        this.finalprice = finalprice;
    }

    public Double getAddprice() {
        return addprice;
    }

    public void setAddprice(Double addprice) {
        this.addprice = addprice;
    }

    public Double getDepositprice() {
        return depositprice;
    }

    public void setDepositprice(Double depositprice) {
        this.depositprice = depositprice;
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

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getAuctionstatus() {
        return auctionstatus;
    }

    public void setAuctionstatus(String auctionstatus) {
        this.auctionstatus = auctionstatus;
    }

    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    }

    public String getAuctionname() {
        return auctionname;
    }

    public void setAuctionname(String auctionname) {
        this.auctionname = auctionname;
    }
}
