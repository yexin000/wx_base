package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 积分商城兑换详情模型
 *
 * @author dingjia
 *
 */
public class ExchangeRecodeModel extends BaseModel {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *积分商品id
     */
    private String icId;

    /**
     *兑换人
     */
    private String wxid;

    /**
     *状态
     */
    private Integer status;


    /**
     *创建时间
     */
    private Date createtime;

    /**
     * 数量
     */
    private Integer num;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcId() {
        return icId;
    }

    public void setIcId(String icId) {
        this.icId = icId;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
