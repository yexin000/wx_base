package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 拍卖会模型
 *
 * @author yexin
 *
 */
public class AuctionModel extends BaseModel {
    /**
     *主键-id
     */
    Integer id;
    /**
     *拍卖会名称
     */
    String name;
    /**
     *开始时间
     */
    Date starttime;
    /**
     *结束时间
     */
    Date endtime;
    /**
     *创建时间
     */
    Date createtime;
    /**
     *创建人
     */
    Integer creator;
    /**
     *修改时间
     */
    Date modifytime;
    /**
     *修改人
     */
    Integer modifier;
    /**
     *商家id
     */
    Integer businessid;
    /**
     *描述说明
     */
    String description;
    /**
     *状态：0-删除，1-正常
     */
    String status;
    /**
     *浏览数目
     */
    Integer viewnum;
    /**
     *拍卖会类型：待定
     */
    String type;
    /**
     *是否在首页显示
     */
    String isShow;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Integer getBusinessid() {
        return businessid;
    }

    public void setBusinessid(Integer businessid) {
        this.businessid = businessid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViewnum() {
        return viewnum;
    }

    public void setViewnum(Integer viewnum) {
        this.viewnum = viewnum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
