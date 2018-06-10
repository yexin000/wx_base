package cn.trustway.weixin.message.template;

/**
 * 出价被超过模板消息
 */
public class BidOverTemplate extends BaseTemplate {
    /**
     * 所需下发的模板消息的id
     */
    private String template_id = "2SZVa0psb9AltWz6xY1Mf2RxbPaIXJv5LfMN8GpTGnk";

    private KeyWord data = new KeyWord();

    /**
     * 设置发送内容
     * @return
     */
    public void setTemplateValue(String touser, String itemName, Double newPrice, String formId) {
        this.setTouser(touser);
        this.getData().getKeyword1().setValue(itemName);
        this.getData().getKeyword2().setValue(String.valueOf(newPrice));
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
