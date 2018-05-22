package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微拍群服务类
 *
 * @author yexin
 */
@Service("groupService")
public class GroupService<T> extends BaseService<T> {
    @Autowired
    private GroupDao<T> dao;

    @Override
    public GroupDao<T> getDao() {
        return dao;
    }

}
