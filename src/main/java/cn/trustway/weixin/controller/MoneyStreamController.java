package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.MoneyStream;
import cn.trustway.weixin.model.GroupModel;
import cn.trustway.weixin.model.MoneyStreamModel;
import cn.trustway.weixin.service.MoneyStreamService;
import cn.trustway.weixin.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微拍群控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/moneyStream")
public class MoneyStreamController extends BaseController {
    @Autowired
    private MoneyStreamService<MoneyStream> moneyStreamService;

    /**
     * json 列表页面
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dataList", method = RequestMethod.POST)
    public void dataList(MoneyStreamModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(MoneyStreamModel model, HttpServletResponse response) throws Exception {
        List<MoneyStream> dataList = moneyStreamService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
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
    public void save(@RequestBody MoneyStream bean, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            moneyStreamService.updateBySelective(bean);
        } else {
            bean.setCreatetime(new Date());
            moneyStreamService.add(bean);
        }
        sendSuccessMessage(response, "保存成功~");
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
        MoneyStream bean = moneyStreamService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
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
        moneyStreamService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }



    /**
     * 前端用户查询微拍群
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxGetMoneyStreamList")
    public void ajaxGetMoneyStreamList(HttpServletResponse response) throws Exception {
        GroupModel model = new GroupModel();
        model.setRows(100);
        List<MoneyStream> dataList = moneyStreamService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
}
