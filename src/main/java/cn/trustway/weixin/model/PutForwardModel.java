package cn.trustway.weixin.model;


/**
 * 出价model
 */
public class PutForwardModel extends BaseModel {
    /**
     * 提现金额
     */
    private Double money;
    /**
     * 提现微信
     */
    private String wxaccount;

    /**
     * 提现微信
     */
    private String wxid;

    /**
     * 提现微信
     */
    private String status;

    /**
     * 提现微信
     */
    private String putforwardno;

    /**
     * 提现微信
     */
    private String wxName;


    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getWxaccount() {
        return wxaccount;
    }

    public void setWxaccount(String wxaccount) {
        this.wxaccount = wxaccount;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPutforwardno() {
        return putforwardno;
    }

    public void setPutforwardno(String putforwardno) {
        this.putforwardno = putforwardno;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }
}
