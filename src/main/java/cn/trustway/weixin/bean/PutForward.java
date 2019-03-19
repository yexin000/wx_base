package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 提现Bean
 * @author dingjia
 */
public class PutForward extends BaseBean  {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 用户wxid
     */
    private String wxid;
    /**
     * 流水编号
     */
    private String putForwardNo;
    /**
     * 提现金额
     */
    private Double money;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 微信号
     */
    private String wxAccount;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 手机
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 备注（提现被拒理由/预留字段）
     * @return
     */
    private String remark;

    /**
     *
     * @return
     */
    private String wxName;

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

    public String getPutForwardNo() {
        return putForwardNo;
    }

    public void setPutForwardNo(String putForwardNo) {
        this.putForwardNo = putForwardNo;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
