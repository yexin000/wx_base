package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 微拍群bean
 * @author yexin
 *
 */
public class GroupBean extends BaseBean {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 二维码图片地址
     */
    private String codeUrl;
    /**
     * 群头像地址
     */
    private String logoUrl;
    /**
     * 状态：0-删除，1-正常
     */
    private String status;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
