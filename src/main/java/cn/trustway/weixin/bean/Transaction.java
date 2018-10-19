package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;

/**
 * 大额交易Bean
 *
 * @author dingjia
 *
 */
public class Transaction extends BaseBean {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *拍品id
     */
    private Integer auctionitemid;

    /**
     *银行名称
     */
    private String bankName;

    /**
     *银行号码
     */
    private String bankNo;

    /**
     *创建时间
     */
    private Date createtime;

    /**
     * 资源列表
     */
    private List<ItemRes> resList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuctionitemid() {
        return auctionitemid;
    }

    public void setAuctionitemid(Integer auctionitemid) {
        this.auctionitemid = auctionitemid;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public List<ItemRes> getResList() {
        return resList;
    }

    public void setResList(List<ItemRes> resList) {
        this.resList = resList;
    }
}
