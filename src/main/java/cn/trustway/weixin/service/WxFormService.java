package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.WxFormDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户formid
 * @param <T>
 */
@Service("wxFormService")
public class WxFormService<T> extends BaseService<T>  {
    @Autowired
    private WxFormDao<T> dao;

    @Override
    public WxFormDao<T> getDao() {
        return dao;
    }

    /**
     * 根据wxid查询7日内有效记录
     * @param wxid
     * @return
     */
    public T queryByWxid(String wxid) {
        return getDao().queryByWxid(wxid);
    }
}
