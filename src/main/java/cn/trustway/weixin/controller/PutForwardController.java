package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.MoneyStream;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.model.MoneyStreamModel;
import cn.trustway.weixin.model.OrderModel;
import cn.trustway.weixin.service.MoneyStreamService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/putForward")
public class PutForwardController extends BaseController {
    @Autowired
    private MoneyStreamService<MoneyStream> moneyStreamService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(MoneyStreamModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/putForward", context);
    }


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

    /**
     * 前端数据列表查询
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(@RequestBody MoneyStreamModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(MoneyStreamModel model, HttpServletResponse response) throws Exception {
        model.setStreamtype("3");
        List<MoneyStream> dataList = moneyStreamService.queryByList(model);
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
     * 根据ID审核记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/audit")
    public void audit(Integer id, HttpServletResponse response) throws Exception {
        if(id != null && id > 0) {
            moneyStreamService.updateByExamine(id);
            sendSuccessMessage(response, "审核成功");
        } else {
            sendFailureMessage(response, "审核失败");
        }

    }

}
