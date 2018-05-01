package cn.trustway.weixin.service;


import cn.trustway.weixin.dao.BaseDao;
import cn.trustway.weixin.dao.ItemResDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拍卖会/拍品-资源关联服务类
 *
 * @author yexin
 */
@Service("itemResService")
public class ItemResService<T> extends BaseService<T> {
    @Autowired
    private ItemResDao<T> dao;

    @Override
    public BaseDao<T> getDao() {
        return dao;
    }
}
