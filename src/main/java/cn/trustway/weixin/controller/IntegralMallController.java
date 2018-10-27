package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.IntegralMallModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.model.MessageModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    @Autowired
    UserAddrService<UserAddr> userAddrService;
    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;
    @Autowired(required = false)
    private ExchangeRecodeService<ExchangeRecode> exchangeRecodeService;
    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(IntegralMallModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/integralMall", context);
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

        //图片数据
        ItemResModel resModel  = new ItemResModel();
        resModel.setConid(bean.getId());
        resModel.setConType("5");
        List<ItemRes> resDataList = itemResService.queryByList(resModel);
        bean.setResList(resDataList);

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


    /**
     * 兑换
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exchange")
    public void exchange(Integer id, String wxid, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        IntegralCommodity bean = integralMallService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        //判断库存是否还有
        if(bean.getStock() < 1)
        {
            sendFailureMessage(response, "库存不足");
            return;
        }

        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailureMessage(response, "获取用户信息失败");
            return;
        }

        String myintegralStr = user.getIntegral();
        int myIntegeral = 0;
        if (StringUtils.isNotEmpty(myintegralStr)){
            myIntegeral = Integer.parseInt(myintegralStr);
        }else{
            //积分不足
            sendFailureMessage(response, "积分不足");
        }
        if(myIntegeral < bean.getConsumeintegral()){
            sendFailureMessage(response, "积分不足");
        }

        // 查询用户默认收货地址
        UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(wxid);
        if(null == defaultAddr) {
            sendFailureMessage(response, "缺少收货地址");
            return;
        }

        // 生成兑换记录
        ExchangeRecode erBean = new ExchangeRecode();
        erBean.setIcId(id);
        erBean.setWxid(wxid);
        erBean.setCreatetime(new Date());
        erBean.setStatus(0);
        erBean.setNum(1);
        exchangeRecodeService.add(erBean);

        // 扣除积分
        user.setIntegral(myIntegeral-bean.getConsumeintegral()+"");
        weixinUserService.updateBySelective(user);

        //减少库存
        bean.setStock(bean.getStock() - 1);
        integralMallService.updateBySelective(bean);
        context.put(SUCCESS, true);
        HtmlUtil.writerJson(response, context);
    }

}
