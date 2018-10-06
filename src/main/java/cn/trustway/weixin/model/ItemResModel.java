package cn.trustway.weixin.model;

/**
 * 拍卖会/拍品-资源关联模型
 *
 * @author yexin
 */
public class ItemResModel extends BaseModel {
    /**
     * 主键-id
     */
    private Integer id;
    /**
     * 关联表id
     */
    private Integer conid;
    /**
     * 类型：1-图片，2-视频。目前只考虑图片，为以后做兼容
     */
    private String type = "1";
    /**
     * 资源路径
     */
    private String path;
    /**
     * 关联类型：1-拍卖会表(t_e_auction)，2-拍品表(t_e_auction_item)，3-活动表(t_e_activity)，4-鉴定表(t_e_user_identify)，5-积分商品表(t_e_integral_commodity)',
     */
    private String conType;
    /**
     * 序号，用于排序
     */
    private Integer idx;

    /**
     * 关联对象名称
     */
    private String conName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConid() {
        return conid;
    }

    public void setConid(Integer conid) {
        this.conid = conid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }
}
