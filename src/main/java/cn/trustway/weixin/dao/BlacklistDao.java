package cn.trustway.weixin.dao;

import cn.trustway.weixin.model.BlacklistModel;

import java.util.List;

/**
 * 黑名单dao
 * @author yexin
 */
public interface BlacklistDao<T> extends BaseDao<T> {
  /**
   * 后台查询黑名单列表
   * @param model
   * @return
   */
  List<T> queryBackgroundByList(BlacklistModel model);

  /**
   * 查询后台黑名单总数
   * @param model
   * @return
   */
  int queryBackgroundByCount(BlacklistModel model);

  /**
   * 查询小程序黑名单总数
   * @param model
   * @return
   */
  int queryForegroundByCount(BlacklistModel model);

  /**
   * 后台移除黑名单
   * @param model
   * @return
   */
  void deleteByModel(BlacklistModel model);
}
