package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 订单bean
 * @author dingjia
 */
public class Report extends BaseBean {

    /**
     * 今日成拍金额
     */
    private String filmingTodayMoney;

    /**
     * 今日成拍数量
     */
    private String filmingTodayNum;

    /**
     * 今日付款金额
     */
    private String PaymentTodayMoney;

    /**
     * 今日付款数量
     */
    private String PaymentTodayNum;

    /**
     * 今日收款金额
     */
    private String CollectionTodayMoney;

    /**
     * 今日收款数量
     */
    private String CollectionTodayNum;

    public String getFilmingTodayMoney() {
        return filmingTodayMoney;
    }

    public void setFilmingTodayMoney(String filmingTodayMoney) {
        this.filmingTodayMoney = filmingTodayMoney;
    }

    public String getFilmingTodayNum() {
        return filmingTodayNum;
    }

    public void setFilmingTodayNum(String filmingTodayNum) {
        this.filmingTodayNum = filmingTodayNum;
    }

    public String getPaymentTodayMoney() {
        return PaymentTodayMoney;
    }

    public void setPaymentTodayMoney(String paymentTodayMoney) {
        PaymentTodayMoney = paymentTodayMoney;
    }

    public String getPaymentTodayNum() {
        return PaymentTodayNum;
    }

    public void setPaymentTodayNum(String paymentTodayNum) {
        PaymentTodayNum = paymentTodayNum;
    }

    public String getCollectionTodayMoney() {
        return CollectionTodayMoney;
    }

    public void setCollectionTodayMoney(String collectionTodayMoney) {
        CollectionTodayMoney = collectionTodayMoney;
    }

    public String getCollectionTodayNum() {
        return CollectionTodayNum;
    }

    public void setCollectionTodayNum(String collectionTodayNum) {
        CollectionTodayNum = collectionTodayNum;
    }
}
