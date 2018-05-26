package cn.trustway.weixin.model;

/**
 * 用户地址model
 * @author yexin
 */
public class UserAddrModel extends BaseModel {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 收藏人wxid
     */
    private String wxid;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 收件人电话
     */
    private String phoneNum;
    /**
     * 是否默认地址:0-否,1-是
     */
    private String isDefault;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
