package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 拍卖会Bean
 *
 * @author yexin
 *
 */
public class Auction extends BaseBean {
    /**
     *主键-id
     */
    private Integer id;
    /**
     *拍卖会名称
     */
    private String name;
    /**
     *开始时间
     */
    private Date starttime;
    /**
     *结束时间
     */
    private Date endtime;
    /**
     *创建时间
     */
    private Date createtime;
    /**
     *创建人
     */
    private Integer creator;
    /**
     *创建人姓名
     */
    private String creatorName;
    /**
     *修改时间
     */
    private Date modifytime;
    /**
     *修改人
     */
    private Integer modifier;
    /**
     *创建人姓名
     */
    private String modifierName;
    /**
     *商家id
     */
    private Integer businessid;
    /**
     *商家名称
     */
    private String businessName;
    /**
     *描述说明
     */
    private String description;
    /**
     *状态：0-删除，1-正常, 2-已开始, 3-已结束
     */
    private String status;
    /**
     *浏览数目
     */
    private Integer viewnum;
    /**
     *拍卖会类型：待定
     */
    private String type;

    /**
     * 关联图片数量
     */
    private Integer picCount = 0;

    /**
     *是否在首页显示
     */
    private String isShow;

    /**
     * 拍卖会logo(第一张图片)
     */
    private String logoPath;

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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
