package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.MoneyStreamDao;
import cn.trustway.weixin.dao.PutForwardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拍品服务类
 *
 * @author yexin
 */
@Service("putForwardService")
public class PutForwardService<T> extends BaseService<T> {
    @Autowired
    private PutForwardDao dao;

    @Override
    public PutForwardDao<T> getDao() {
        return dao;
    }


    /**
     * 审核通过
     * @param
     * @return
     */
    public void updateByExamine(Integer id) {
        getDao().updateByExamine(id);
    }

    /**
     * 审核不通过
     * @param
     * @return
     */
    public void updateByDeny(Integer id) {
        getDao().updateByDeny(id);
    }

}
