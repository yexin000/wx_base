package cn.trustway.weixin.service;

import cn.trustway.weixin.dao.FabulousDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户收藏服务类
 *
 * @author yexin
 */
@Service("fabulousService")
public class FabulousService<T> extends BaseService<T> {
  @Autowired
  private FabulousDao<T> dao;

  @Override
  public FabulousDao<T> getDao() {
    return dao;
  }

  /**
   * 根据wxid、fabulousId查询记录
   * @param params
   * @return
   */
  public T queryByWxidAndFabulousId(Map params) {
    return getDao().queryByWxidAndFabulousId(params);
  }
}
