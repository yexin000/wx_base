package cn.trustway.weixin.model;

import java.util.Date;

public class FabulousModel extends BaseModel {
  /**
   * 主键-id
   */
  private Integer id;
  /**
   * 被赞id
   */
  private Integer fabulousId;
  /**
   * 点赞类型 1：拍品，2：展览，3：活动
   */
  private String fabulousType;
  /**
   * 点赞人wxid，关联weixin_user表
   */
  private String wxid;
  /**
   * 创建时间
   */
  private Date createtime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getFabulousId() {
    return fabulousId;
  }

  public void setFabulousId(Integer fabulousId) {
    this.fabulousId = fabulousId;
  }

  public String getFabulousType() {
    return fabulousType;
  }

  public void setFabulousType(String fabulousType) {
    this.fabulousType = fabulousType;
  }

  public String getWxid() {
    return wxid;
  }

  public void setWxid(String wxid) {
    this.wxid = wxid;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }
}
