package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.ExpectedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 预出价服务类
 *
 * @author dingjia
 */
@Service("expectedService")
public class ExpectedService<T> extends BaseService<T> {
    @Autowired
    private ExpectedDao<T> dao;

    @Override
    public ExpectedDao<T> getDao() {
        return dao;
    }

    public List<T> queryByJoinActivity(Map<String, Object> params) {
        return getDao().queryByJoinActivity(params);
    }
}
