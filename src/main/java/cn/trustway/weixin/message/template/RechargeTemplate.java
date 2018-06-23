package cn.trustway.weixin.message.template;

/**
 * 充值成功模板消息
 */
public class RechargeTemplate extends BaseTemplate {
    /**
     * 所需下发的模板消息的id
     */
    private String template_id = "TpxYy97XI4D3JpGnI43NYw7Jp4Qr_q_-b-CW2I9QdyE";

    private KeyWord data = new KeyWord();

    /**
     * 设置发送内容
     * @return
     */
    public void setTemplateValue(String touser, String rechargeTime, Double rechargeNum, String formId) {
        this.setTouser(touser);
        this.getData().getKeyword2().setValue(rechargeTime);
        this.getData().getKeyword1().setValue(String.valueOf(rechargeNum));
        this.setForm_id(formId);
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public KeyWord getData() {
        return data;
    }

    public void setData(KeyWord data) {
        this.data = data;
    }
}
