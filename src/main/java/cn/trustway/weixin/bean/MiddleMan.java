package cn.trustway.weixin.bean;

/**
 * 经纪人Bean
 *
 * @author yexin
 *
 */
public class MiddleMan extends BaseBean {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 等级：待定
     */
    private String grade;
    /**
     * 微信号
     */
    private String wxAcount;
    /**
     * 经纪人电话
     */
    private String phoneNum;
    /**
     * 个人简介
     */
    private String description;
    /**
     * 头像地址
     */
    private String avatarUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getWxAcount() {
        return wxAcount;
    }

    public void setWxAcount(String wxAcount) {
        this.wxAcount = wxAcount;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
