package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 用户formid
 */
public class WxForm extends BaseBean {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * wxid
     */
    private String wxid;
    /**
     * 表单id
     */
    private String formId;
    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
