package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.IntegralCommodity;
import cn.trustway.weixin.bean.ItemRes;
import cn.trustway.weixin.bean.Message;
import cn.trustway.weixin.model.IntegralMallModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.model.MessageModel;
import cn.trustway.weixin.service.IntegralMallService;
import cn.trustway.weixin.service.ItemResService;
import cn.trustway.weixin.service.MessageService;
import cn.trustway.weixin.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分商城功能页面控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/integralMall")
public class IntegralMallController extends BaseController {

    @Autowired(required = false)
    private IntegralMallService<IntegralCommodity> integralMallService;
    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(MessageModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/activityList", context);
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
    public void dataList(IntegralMallModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * 前端数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(HttpServletResponse response) throws Exception {
        IntegralMallModel model = new IntegralMallModel();
        queryDataList(model, response);
    }



    private void queryDataList(IntegralMallModel model, HttpServletResponse response) throws Exception {
        List<IntegralCommodity> dataList = integralMallService.queryByList(model);

        for(IntegralCommodity ic : dataList){
            //图片数据
            ItemResModel resModel  = new ItemResModel();
            resModel.setConid(ic.getId());
            resModel.setConType("5");
            List<ItemRes> resDataList = itemResService.queryByList(resModel);
            ic.setResList(resDataList);
        }
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", integralMallService.queryByCount(model));
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
    public void getId(Integer id,String wxid, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        IntegralCommodity bean = integralMallService.queryById(id);
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
    public void save(IntegralCommodity bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            integralMallService.update(bean);
        } else {
            integralMallService.add(bean);
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
        integralMallService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
