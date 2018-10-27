package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;

/**
 * 积分商城兑换像Bean
 *
 * @author dingjia
 *
 */
public class ExchangeRecode extends BaseBean {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *积分商品id
     */
    private Integer icId;

    /**
     *名称
     */
    private String icName;

    /**
     *兑换人
     */
    private String wxid;

    /**
     *状态
     */
    private Integer status;

    /**
     * 数量
     */
    private Integer num;

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

    public Integer getIcId() {
        return icId;
    }

    public void setIcId(Integer icId) {
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

    public List<ItemRes> getResList() {
        return resList;
    }

    public void setResList(List<ItemRes> resList) {
        this.resList = resList;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getIcName() {
        return icName;
    }

    public void setIcName(String icName) {
        this.icName = icName;
    }
}
