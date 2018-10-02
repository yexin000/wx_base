package cn.trustway.weixin.model;

import java.util.Date;

/**
 * 关注模型
 *
 * @author dingjia
 *
 */
public class FollowModel extends BaseModel {

    /**
     *主键-id
     */
    Integer id;

    /**
     *关注id
     */
    Integer followId;

    /**
     *关注用户
     */
    Integer wxid;

    /**
     *关注类型
     */
    Integer followType;

    /**
     *关注时间
     */
    Date createTime;

}
