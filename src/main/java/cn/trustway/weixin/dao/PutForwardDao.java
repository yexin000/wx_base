package cn.trustway.weixin.dao;


/**
 * 流水
 * @author dingjia
 *
 * @param <T>
 */
public interface PutForwardDao<T> extends BaseDao<T> {

    /**
     * 审核通过
     * @param id
     * @return
     */
    public void updateByExamine(Integer id);

    /**
     * 审核不通过
     * @param id
     * @return
     */
    public void updateByDeny(Integer id);
}
