package cn.trustway.weixin.dao;

import java.util.List;
import java.util.Map;

/**
 * 预出价dao
 * @author dingjia
 *
 * @param <T>
 */
public interface ExpectedDao<T> extends BaseDao<T> {


    List<T> queryByJoinActivity(Map<String, Object> params);
}
