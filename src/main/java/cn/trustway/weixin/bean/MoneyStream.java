package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 金额流水Bean
 * @author yexin
 */
public class MoneyStream {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 用户wxid
     */
    private String wxid;
    /**
     * 流水类型：1-保证金,2-支付,3-提现,4-退款,5-充值
     */
    private String streamtype;
    /**
     * 流水金额
     */
    private Double streammoney;

    /**
     * 流水编号
     */
    private String flownumber;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 去向
     * @return
     */
    private String whereabouts;

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

    public String getStreamtype() {
        return streamtype;
    }

    public void setStreamtype(String streamtype) {
        this.streamtype = streamtype;
    }

    public Double getStreammoney() {
        return streammoney;
    }

    public void setStreammoney(Double streammoney) {
        this.streammoney = streammoney;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getFlownumber() {
        return flownumber;
    }

    public void setFlownumber(String flownumber) {
        this.flownumber = flownumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWhereabouts() {
        return whereabouts;
    }

    public void setWhereabouts(String whereabouts) {
        this.whereabouts = whereabouts;
    }
}
