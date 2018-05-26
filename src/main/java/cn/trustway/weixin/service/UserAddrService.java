package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.UserAddrDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出价服务类
 *
 * @author yexin
 */
@Service("userAddrService")
public class UserAddrService<T> extends BaseService<T> {
    @Autowired
    private UserAddrDao<T> dao;

    @Override
    public UserAddrDao<T> getDao() {
        return dao;
    }

    public T getDefaultAddrByWxid(String wxid) {
        return getDao().getDefaultAddrByWxid(wxid);
    }

    public void setDefaultAddr(Integer id) {
        getDao().setDefaultAddr(id);
    }
}
