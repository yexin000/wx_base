package cn.trustway.weixin.bean;

/**
 * wxid关联经纪人Bean
 *
 * @author yexin
 *
 */
public class WxidMiddleman extends BaseBean {
    private String wxid;
    private Integer middlemanId;

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Integer getMiddlemanId() {
        return middlemanId;
    }

    public void setMiddlemanId(Integer middlemanId) {
        this.middlemanId = middlemanId;
    }
}
