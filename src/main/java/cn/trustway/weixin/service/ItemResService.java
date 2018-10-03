package cn.trustway.weixin.service;


import cn.trustway.weixin.bean.ItemRes;
import cn.trustway.weixin.dao.BaseDao;
import cn.trustway.weixin.dao.ItemResDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public ItemResDao<T> getDao() {
        return dao;
    }

    /**
     * 根据关联id查询第一张图片
     * @param params
     * @return
     */
    public  ItemRes queryByConId(Map<String, Object> params) {

        return getDao().queryByConId(params);
    }

}
