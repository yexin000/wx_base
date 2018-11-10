package cn.trustway.weixin.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预出价模型
 *
 * @author dingjia
 *
 */
public class ExpectedModel extends BaseModel {
    /**
     *报名-id
     */
    private Integer id;
    /**
     *商品id
     */
    private Integer commodityId;
    /**
     *用户
     */
    private String wxid;

    /**
     *时间
     */
    private Date createtime;

    /**
     *状态
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
