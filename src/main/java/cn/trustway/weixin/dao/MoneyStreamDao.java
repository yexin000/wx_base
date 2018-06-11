package cn.trustway.weixin.dao;


/**
 * 流水
 * @author dingjia
 *
 * @param <T>
 */
public interface MoneyStreamDao<T> extends BaseDao<T> {

    /**
     * 查找微信用户
     * @param id
     * @return
     */
    public void updateByExamine(Integer id);
}
