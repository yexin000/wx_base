package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 系统角色bean
 * @author hucheng
 *
 */
public class SysRole extends BaseBean {
	private Integer id;// id主键
	private String roleName;// 角色名称
	private Date createTime;// 创建时间
	private Integer createBy;// 创建人
	private Date updateTime;// 修改时间
	private Integer updateBy;// 修改人
	private Integer state;// 状态0=可用 1=禁用
	private String descr;// 角色描述

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}
