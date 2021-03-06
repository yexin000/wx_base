package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;

/**
 * 积分商城Bean
 *
 * @author dingjia
 *
 */
public class IntegralCommodity extends BaseBean {
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
    private String describes;

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

    /**
     *结束时间
     */
    private Date endtime;

    /**
     * 资源列表
     */
    private List<ItemRes> resList;

    private Integer picCount;

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

    public List<ItemRes> getResList() {
        return resList;
    }

    public void setResList(List<ItemRes> resList) {
        this.resList = resList;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }
}
