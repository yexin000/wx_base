package cn.trustway.weixin.message.template;

/**
 * 模板消息内容
 */
public class KeyWord {
    private KeyWordValue keyword1 = new KeyWordValue();
    private KeyWordValue keyword2 = new KeyWordValue();

    public KeyWordValue getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(KeyWordValue keyword1) {
        this.keyword1 = keyword1;
    }

    public KeyWordValue getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(KeyWordValue keyword2) {
        this.keyword2 = keyword2;
    }
}
