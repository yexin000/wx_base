package cn.trustway.weixin.dao;

import java.util.List;

/**
 * 系统菜单所对应的操作按钮接口
 * @author hucheng
 *
 * @param <T>
 */
public interface SysMenuBtnDao<T> extends BaseDao<T> {
	public List<T> queryByMenuid(Integer menuid);

	public List<T> queryByMenuUrl(String url);

	public void deleteByMenuid(Integer menuid);

	public List<T> getMenuBtnByUser(Integer userid);

	public List<T> queryByAll();

}
