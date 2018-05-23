package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 订单model
 * @author yexin
 */
public class OrderModel extends BaseModel {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 订单类型:1-拍卖,2-买卖
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
     * 订单状态:0-删除,1-失效,2-待支付,3-已支付
     */
    private String status;
    /**
     * 用户wxid
     */
    private String wxid;
    /**
     * 订单地址id
     */
    private String addressId;
    /**
     * 商品id
     */
    private Integer itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
