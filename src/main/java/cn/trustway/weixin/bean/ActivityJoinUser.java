package cn.trustway.weixin.bean;


/**
 * 报名用户Bean
 *
 * @author dingjia
 *
 */
public class ActivityJoinUser extends BaseBean {
    /**
     *报名-id
     */
    private Integer id;
    /**
     *用户微信
     */
    private String wxid;
    /**
     *用户名称
     */
    private String name;

    /**
     *用户头像地址
     */
    private String avatarUrl;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
