package cn.trustway.weixin.bean;

import java.util.Date;

/**
 * 商品鉴定bean
 * @author yexin
 *
 */
public class Identify extends BaseBean {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 鉴定发起人wxid
     */
    private String wxid;
    /**
     * 鉴定商品名称
     */
    private String name;
    /**
     * 鉴定发起人描述
     */
    private String description;
    /**
     * 鉴定结果
     */
    private String result;
    /**
     * 鉴定状态：0-鉴定中,1-已鉴定
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date modifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
