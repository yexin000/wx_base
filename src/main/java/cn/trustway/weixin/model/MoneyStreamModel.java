package cn.trustway.weixin.model;


public class MoneyStreamModel extends BaseModel {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 用户wxid，关联weixin_user表
     */
    private String wxid;
    /**
     * 流水类型：1-保证金,2-支付,3-提现,4-退款
     */
    private String streamtype;
    /**
     * 操作金额
     */
    private String streammoney;

    /**
     * 流水编号
     */
    private String flownumber;

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

    public String getStreammoney() {
        return streammoney;
    }

    public void setStreammoney(String streammoney) {
        this.streammoney = streammoney;
    }

    public String getFlownumber() {
        return flownumber;
    }

    public void setFlownumber(String flownumber) {
        this.flownumber = flownumber;
    }
}
