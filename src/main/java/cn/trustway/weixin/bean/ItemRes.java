package cn.trustway.weixin.bean;

/**
 * 拍卖会/拍品-资源关联Bean
 *
 * @author yexin
 *
 */
public class ItemRes extends BaseBean {
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
     * 关联类型：1-拍卖会表(t_e_auction)，2-拍品表(t_e_auction_item)
     */
    private String conType;
    /**
     * 序号，用于排序
     */
    private Integer idx;

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
}
