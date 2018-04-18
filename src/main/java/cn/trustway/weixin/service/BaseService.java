package cn.trustway.weixin.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import cn.trustway.weixin.dao.BaseDao;
import cn.trustway.weixin.model.BaseModel;

public abstract class BaseService<T> {

	public abstract BaseDao<T> getDao();

	public void add(T t) throws Exception {
		getDao().add(t);
	}

	public void update(T t) throws Exception {
		getDao().update(t);
	}

	public void updateBySelective(T t) {
		getDao().updateBySelective(t);
	}

	public void delete(Object... ids) throws Exception {
		if (ids == null || ids.length < 1) {
			return;
		}
		for (Object id : ids) {
			getDao().delete(id);
		}
	}

	public int queryByCount(BaseModel model) throws Exception {
		return getDao().queryByCount(model);
	}

	@Cacheable(value = "serviceCache")  
	public List<T> queryByList(BaseModel model) throws Exception {
		Integer rowCount = queryByCount(model);
		model.getPager().setRowCount(rowCount);
		return getDao().queryByList(model);
	}

	public T queryById(Object id) throws Exception {
		return getDao().queryById(id);
	}
}
