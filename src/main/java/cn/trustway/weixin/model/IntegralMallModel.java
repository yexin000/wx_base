package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 积分商城模型
 *
 * @author dingjia
 *
 */
public class IntegralMallModel extends BaseModel {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *名称
     */
    private String name;

    /**
     *描述
     */
    private String describe;

    /**
     *消耗积分
     */
    private Integer consumeintegral;

    /**
     *库存
     */
    private Integer stock;

    /**
     *创建时间
     */
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getConsumeintegral() {
        return consumeintegral;
    }

    public void setConsumeintegral(Integer consumeintegral) {
        this.consumeintegral = consumeintegral;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
