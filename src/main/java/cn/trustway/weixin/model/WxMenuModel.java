package cn.trustway.weixin.model;

/**
 * 微信项目菜单配置
 * 
 * @author hucheng
 *
 */
public class WxMenuModel extends BaseModel {

	private Integer id;// 主键
	private String name;// 代码名称
	private String menuKey;// 标识
	private String msgType;// 消息类型
	private String type;// 菜单类型
	private String url;// 链接
	private Integer rank;// 顺序
	private Integer parentId;// 父id 关联wx_menu.id
	private Integer levels;//级数

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

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

}
