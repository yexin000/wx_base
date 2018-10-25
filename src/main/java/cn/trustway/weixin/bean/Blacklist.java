package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 黑名单bean
 * @author yexin
 *
 */
public class Blacklist extends BaseBean {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 创建人wxid
     */
    private String creatorid;
    /**
     * 黑名单类型：0-小程序黑名单,1-后台黑名单
     */
    private String type;
    /**
     * 被加入黑名单wxid
     */
    private String blackid;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 昵称
     */
    private String nickname;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlackid() {
        return blackid;
    }

    public void setBlackid(String blackid) {
        this.blackid = blackid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
