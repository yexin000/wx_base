package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 短信消息bean
 *
 * @author yexin
 */
public class TextMessage extends BaseBean {
    /**
     * 短信内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 手机号码
     */
    private String phoneNum;
    /**
     * 消息类型，0-是短信验证码，1-通知短信
     */
    private String type;
    /**
     * 验证码
     */
    private String verifyCode;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}