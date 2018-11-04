package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 活动Bean
 *
 * @author dingjia
 *
 */
public class Message extends BaseBean {
    /**
     *主键-id
     */
    Integer id;
    /**
     *父id
     */
    Integer parentId;
    /**
     *消息文本
     */
    String messagenote;
    /**
     *消息类型
     */
    Integer messagetype;
    /**
     *状态
     */
    Integer status;

    /**
     *主动发送的用户
     */
    String wxid;

    /**
     *被推送的用户
     */
    String toWxid;

    /**
     *创建时间
     */
    Date createtime;

    /**
     *发送人名称
     */
    String nickName;

    /**
     *发送人头像
     */
    String path;

    /**
     *接收人名称
     */
    String toNickName;

    /**
     *接收人头像
     */
    String toPath;

    /**
     *V5ID
     */
    Integer v5Id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMessagenote() {
        return messagenote;
    }

    public void setMessagenote(String messagenote) {
        this.messagenote = messagenote;
    }

    public Integer getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(Integer messagetype) {
        this.messagetype = messagetype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getToWxid() {
        return toWxid;
    }

    public void setToWxid(String toWxid) {
        this.toWxid = toWxid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public String getToPath() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
    }

    public Integer getV5Id() {
        return v5Id;
    }

    public void setV5Id(Integer v5Id) {
        this.v5Id = v5Id;
    }
}
