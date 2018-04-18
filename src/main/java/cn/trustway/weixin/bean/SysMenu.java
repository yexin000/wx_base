package cn.trustway.weixin.bean;

import java.util.Date;
import java.util.List;
/**
 * 系统菜单bean
 * @author hucheng
 *
 */
public class SysMenu extends BaseBean {
	private Integer id;//   主键	private String name;//   菜单名称
	private String url;//   系统url	private Integer parentId;//   父id 关联sys_menu.id	private Integer deleted;//   是否删除,0=未删除，1=已删除	private Date createTime;//   创建时间
	private Date updateTime;//   修改时间	private Integer rank;//   排序
	private String actions; //注册Action 按钮|分隔
	
	private int subCount;//子菜单总数
	
	//菜单按钮
	private List<SysMenuBtn> btns;
	
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public List<SysMenuBtn> getBtns() {
		return btns;
	}
	public void setBtns(List<SysMenuBtn> btns) {
		this.btns = btns;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public int getSubCount() {
		return subCount;
	}
	public void setSubCount(int subCount) {
		this.subCount = subCount;
	}	
}
