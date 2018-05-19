package cn.trustway.weixin.dao;

import cn.trustway.weixin.bean.MiddleMan;
import cn.trustway.weixin.bean.WxidMiddleman;

/**
 * 经纪人dao
 * @author yexin
 */
public interface MiddleManDao<T> extends BaseDao<T> {

    /**
     * 根据wxid查询经纪人
     * @param wxid
     * @return
     */
    MiddleMan getMiddleManByWxid(String wxid);

    /**
     * 随机获得一个经纪人
     * @return
     */
    MiddleMan getMiddleManByRandom();

    /**
     * 新增wxid与经纪人关联记录
     * @param wxidMiddleman
     */
    void addMiddleManToWxid(WxidMiddleman wxidMiddleman);
}
