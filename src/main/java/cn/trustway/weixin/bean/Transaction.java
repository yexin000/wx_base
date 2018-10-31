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

    /**
     * 拍品名称
     */
    private String name;

    /**
     * 成交价格
     */
    private String finalprice;

    /**
     * 商品性质:0-拍卖品,1-商品,2-非卖品',
     */
    private String attribute;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(String finalprice) {
        this.finalprice = finalprice;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
