package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 微信项目代码配置
 * 
 * @author hucheng
 *
 */
public class WxCode extends BaseBean {

	private Integer id;// 主键
	private String name;// 代码名称
	private String code;// 代码
	private String value;// 代码值
	private Integer parentId;// 父id 关联wx_code.id
	private Integer deleted;// 是否删除,0=未删除，1=已删除
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private String remarks;// 备注
	private int subCount;//子参数总数
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getSubCount() {
		return subCount;
	}

	public void setSubCount(int subCount) {
		this.subCount = subCount;
	}

}
