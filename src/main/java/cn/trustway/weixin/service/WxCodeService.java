package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.WxCode;
import cn.trustway.weixin.dao.WxCodeDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信参数配置
 *
 * @param <T>
 * @author hucheng
 */
@Service("wxCodeService")
public class WxCodeService<T> extends BaseService<T> {
    private final static Logger log = Logger.getLogger(WxCodeService.class);

    /**
     * 获取参数分类
     *
     * @return
     */
    public List<T> getRootCode(Integer codeId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("codeId", codeId);
        return dao.getRootCode(map);
    }

    /**
     * 获取子参数
     *
     * @return
     */
    public List<T> getChildCode() {
        return dao.getChildCode();
    }

    @Cacheable(value = "serviceCache")
    public List<T> getChildCode(int parentId) {
        return dao.getChildCode(parentId);
    }

    /**
     * 查询所有微信参数列表
     *
     * @return
     */
    @Cacheable(value = "serviceCache")
    public List<T> queryByAll() {
        return dao.queryByAll();
    }

    @Override
    public void delete(Object[] ids) throws Exception {
        super.delete(ids);
    }

    public int getCodeCountByCode(String code) {
        return dao.getCodeCountByCode(code);
    }

    @Cacheable(value = "serviceCache")
    public WxCode getBeanByCode(String code) {
        return (WxCode) dao.getModelByCode(code);
    }

    @Cacheable(value = "serviceCache")
    public List<T> getAuctionItemType() {
        return dao.getAuctionItemType();
    }

    @Cacheable(value = "serviceCache")
    public List<T> getAuctionItemSecondType(String code) {
        return dao.getAuctionItemSecondType(code);
    }

    @Cacheable(value = "serviceCache")
    public List<T> getAllAuctionItemSecondType() {
        return dao.getAllAuctionItemSecondType();
    }

    @Autowired
    private WxCodeDao<T> dao;

    @Override
    public WxCodeDao<T> getDao() {
        // TODO Auto-generated method stub
        return dao;
    }

}
