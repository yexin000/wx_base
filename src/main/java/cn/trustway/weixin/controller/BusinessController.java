package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.Business;
import cn.trustway.weixin.model.BusinessModel;
import cn.trustway.weixin.service.BusinessService;
import cn.trustway.weixin.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拍卖商家功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/business")
public class BusinessController extends BaseController {

    @Autowired(required = false)
    private BusinessService<Business> businessService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(BusinessModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/businessList", context);
    }

    /**
     * json数据列表
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/dataList")
    public void dataList(BusinessModel model, HttpServletResponse response)
            throws Exception {
        List<Business> dataList = businessService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 根据ID查找记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getId")
    public void getId(Integer id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        Business bean = businessService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 添加或修改数据
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public void save(Business bean, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            businessService.updateBySelective(bean);
        } else {
            businessService.add(bean);
        }
        sendSuccessMessage(response, "保存成功~");
    }

    /**
     * 根据ID删除记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public void delete(Integer[] id, HttpServletResponse response) throws Exception {
        businessService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
