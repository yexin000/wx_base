package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 用户点赞Bean
 *
 * @author yexin
 *
 */
public class Fabulous extends BaseBean {
  /**
   * 主键-id
   */
  private Integer id;
  /**
   * 被赞id
   */
  private String fabulousId;
  /**
   * 点赞类型 1：拍品，2：展览
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

  public String getFabulousId() {
    return fabulousId;
  }

  public void setFabulousId(String fabulousId) {
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
