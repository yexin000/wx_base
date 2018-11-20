package cn.trustway.weixin.dao;

import java.util.Map;

public interface FabulousDao<T> extends BaseDao<T> {
  /**
   * 根据wxid、fabulousId查询记录
   * @param params
   * @return
   */
  T queryByWxidAndFabulousId(Map params);
}
