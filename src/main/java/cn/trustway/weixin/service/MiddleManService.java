package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.MiddleMan;
import cn.trustway.weixin.bean.WxidMiddleman;
import cn.trustway.weixin.dao.MiddleManDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 经纪人服务类
 *
 * @author yexin
 */
@Service("middleManService")
public class MiddleManService<T> extends BaseService<T> {
    @Autowired
    private MiddleManDao<T> dao;

    @Override
    public MiddleManDao<T> getDao() {
        return dao;
    }

    /**
     * 根据wxid查询经纪人
     * @param wxid
     * @return
     */
    public MiddleMan getMiddleManByWxid(String wxid) {
        return getDao().getMiddleManByWxid(wxid);
    }

    /**
     * 随机获得一个经纪人
     * @return
     */
    public MiddleMan getMiddleManByRandom() {
        return getDao().getMiddleManByRandom();
    }

    public void addMiddleManToWxid(WxidMiddleman wxidMiddleman) {
        getDao().addMiddleManToWxid(wxidMiddleman);
    }
}
