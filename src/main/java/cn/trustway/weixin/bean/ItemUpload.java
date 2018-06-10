package cn.trustway.weixin.bean;

/**
 * 商品上传bean
 */
public class ItemUpload extends AuctionItem {
    /**
     * 商家名称
     */
    private String businessName;
    /**
     * 商家电话
     */
    private String businessTelNum;
    /**
     * 微信号
     */
    private String businessWxNum;
    /**
     * 商家地址
     */
    private String businessAddress;
    /**
     * 商品性质
     */
    private String attribute;
    /**
     * 上传人wxid
     */
    private String wxid;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessTelNum() {
        return businessTelNum;
    }

    public void setBusinessTelNum(String businessTelNum) {
        this.businessTelNum = businessTelNum;
    }

    public String getBusinessWxNum() {
        return businessWxNum;
    }

    public void setBusinessWxNum(String businessWxNum) {
        this.businessWxNum = businessWxNum;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }
}
