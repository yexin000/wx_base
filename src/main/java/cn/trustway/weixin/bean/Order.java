package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 订单bean
 * @author yexin
 */
public class Order extends BaseBean {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 订单类型:1-竞拍,2-买卖,3-充值,4-报名活动,5鉴定支付
     */
    private String orderType;
    /**
     * 订单金额
     */
    private Double orderMoney;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 订单支付时间
     */
    private Date payTime;
    /**
     * 订单失效时间
     */
    private Date invalidTime;
    /**
     * 	微信支付订单号
     */
    private String transactionId;
    /**
     * 微信支付预支付交易会话标识
     */
    private String prepayId;
    /**
     * 订单状态:0-删除,1-失效,2-待支付,3-已支付
     */
    private String status;
    /**
     * 用户wxid
     */
    private String wxid;
    /**
     * 用户昵称
     */
    private String wxName;
    /**
     * 订单地址id
     */
    private String addressId;
    /**
     * 订单地址
     */
    private String addressName;
    /**
     * 商品id
     */
    private Integer itemId;
    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户名称
     */
    private String businessId;
    /**
     * 商户名称
     */
    private String businessName;

    /**
     * 订单封面图
     */
    private String orderCoverImg;

    /**
     * 订单封面图宽
     */
    private Integer orderCoverImgWidth;

    /**
     * 订单封面图高
     */
    private Integer orderCoverImgHeight;

    /**
     * 活动id
     */
    private Integer actId;

    /**
     * 运单编号
     */
    private String ydbh;

    /**
     * 物流公司
     */
    private String wlgs;

    /**
     * 是我上传的
     */
    private Integer isMyUpload;

    /**
     * 确认时间
     */
    private Date sureTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOrderCoverImg() {
        return orderCoverImg;
    }

    public void setOrderCoverImg(String orderCoverImg) {
        this.orderCoverImg = orderCoverImg;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getOrderCoverImgWidth() {
        return orderCoverImgWidth;
    }

    public void setOrderCoverImgWidth(Integer orderCoverImgWidth) {
        this.orderCoverImgWidth = orderCoverImgWidth;
    }

    public Integer getOrderCoverImgHeight() {
        return orderCoverImgHeight;
    }

    public void setOrderCoverImgHeight(Integer orderCoverImgHeight) {
        this.orderCoverImgHeight = orderCoverImgHeight;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getYdbh() {
        return ydbh;
    }

    public void setYdbh(String ydbh) {
        this.ydbh = ydbh;
    }

    public String getWlgs() {
        return wlgs;
    }

    public void setWlgs(String wlgs) {
        this.wlgs = wlgs;
    }

    public Integer getIsMyUpload() {
        return isMyUpload;
    }

    public void setIsMyUpload(Integer isMyUpload) {
        this.isMyUpload = isMyUpload;
    }

    public Date getSureTime() {
        return sureTime;
    }

    public void setSureTime(Date sureTime) {
        this.sureTime = sureTime;
    }
}
