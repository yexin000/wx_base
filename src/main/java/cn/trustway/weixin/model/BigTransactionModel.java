package cn.trustway.weixin.model;


/**
 * 大额支付模型模型
 *
 * @author dingjia
 *
 */
public class BigTransactionModel extends BaseModel {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *名称
     */
    private String bankNo;

    /**
     *拍品对象
     */
    private String auctionitemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getAuctionitemId() {
        return auctionitemId;
    }

    public void setAuctionitemId(String auctionitemId) {
        this.auctionitemId = auctionitemId;
    }
}
