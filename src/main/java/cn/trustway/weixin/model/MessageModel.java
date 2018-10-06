package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 消息模型
 *
 * @author dingjia
 *
 */
public class MessageModel extends BaseModel {
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
}
