package cn.trustway.weixin.message.template;

/**
 * 模板消息基类
 */
public class BaseTemplate {
    /**
     * 接收者（用户）的 openid
     */
    private String touser;

    private String form_id;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }
}
