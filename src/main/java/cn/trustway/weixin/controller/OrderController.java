package cn.trustway.weixin.controller;


import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.model.OrderModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.LogisticsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
 * 拍卖商家功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    OrderService<Order> orderService;

    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    @Autowired
    UserAddrService<UserAddr> userAddrService;

    @Autowired(required = false)
    private AuctionItemService<AuctionItem> auctionItemService;

    @Autowired(required = false)
    private BusinessService<Business> businessService;

    @Autowired
    OrderLogService<OrderLog> orderLogService;

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
    public ModelAndView list(OrderModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/orderList", context);
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
    public void dataList(OrderModel model, HttpServletResponse response)
            throws Exception {
        queryDataList(model, response, false);
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
    public void ajaxDataList(@RequestBody OrderModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response, true);
    }

    private void queryDataList(OrderModel model, HttpServletResponse response, boolean isQueryRes) throws Exception {
        List<Order> dataList = null ;
        if(StringUtils.isNotEmpty(model.getStatus()) && "5".equals(model.getStatus())){
            Map<String, Object> params = new HashMap<>();
            params.put("wxid", model.getWxid());
            dataList = orderService.queryMyOutOrderByList(model);
            for (Order o :dataList ){
                o.setIsMyUpload(1);
            }
        }else{
            dataList = orderService.queryByList(model);
        }

        if(isQueryRes) {
            for (Order order : dataList) {
                ItemResModel resModel = new ItemResModel();
                resModel.setConid(order.getItemId());
                resModel.setConType("2");
                List<ItemRes> resDataList = itemResService.queryByList(resModel);
                if (null != resDataList && resDataList.size() > 0) {
                    order.setOrderCoverImg(resDataList.get(0).getPath());
                    order.setOrderCoverImgHeight(resDataList.get(0).getHeight());
                    order.setOrderCoverImgWidth(resDataList.get(0).getWidth());
                }
            }
        }

        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total",orderService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }


    /**
     * 根据ID查找记录
     *
     * @param orderModel
     * @param response
     * @return
     * search  auctionItem/ajaxGetId
     * @throws Exception
     */
    @RequestMapping("/ajaxGetId")
    public void ajaxGetId(@RequestBody OrderModel orderModel,  HttpServletResponse response) throws Exception {
        // 设置页面数据
        Map<String, Object> context = getRootMap();
        Order bean = orderService.queryById(orderModel.getId());
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        UserAddr useraddr = null;
        if(StringUtils.isNotEmpty(bean.getAddressId())){
            //带出地址信息
            useraddr = userAddrService.queryById(bean.getAddressId());
        }else{
            //启用用户的默认地址
            useraddr = userAddrService.getDefaultAddrByWxid(orderModel.getWxid());
        }
        if(null != useraddr){
            context.put("useraddr", useraddr);
        }
        if(null != bean.getItemId()){
            AuctionItem auctionItem = auctionItemService.queryById(bean.getItemId());
            if(null != auctionItem){
                context.put("auctionItem", auctionItem);
            } else {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_NOT_EXIST, "商品不存在");
            }
            ItemResModel resModel  = new ItemResModel();
            resModel.setConid(auctionItem.getId());
            resModel.setConType("2");
            List<ItemRes> resDataList = itemResService.queryByList(resModel);
            auctionItem.setResList(resDataList);

            Business business = businessService.queryById(auctionItem.getBusinessId());
            if(null != business){
                bean.setBusinessName(business.getName());
            }
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 创建充值订单
     *
     * @param
     * @param
     * @return
     * search  order/rechargeOrder
     * @throws Exception
     */
    @RequestMapping("/rechargeOrder")
    public void createOrder (@RequestBody OrderModel orderModel,  HttpServletResponse response) throws Exception{
        // 设置页面数据
        Map<String, Object> context = getRootMap();
        Date currentTime = new Date();
        Order order = new Order();
        order.setOrderType("3");//充值订单
        order.setOrderMoney(orderModel.getOrderMoney());
        order.setWxid(orderModel.getWxid());
        order.setItemId(0);
        order.setCreateTime(currentTime);
        orderService.add(order);

        // 写入订单日志
        Order newOrder = orderService.queryById(order.getId());
        OrderLog orderLog = new OrderLog();
        BeanUtils.copyProperties(newOrder, orderLog);
        orderLog.setOrderId(newOrder.getId());
        orderLog.setCreateTime(newOrder.getCreateTime());
        orderLog.setStatus(newOrder.getStatus());
        orderLogService.add(orderLog);

        context.put(SUCCESS, true);
        context.put("data", order);
        HtmlUtil.writerJson(response, context);
    }


    /**
     * 修改订单物流编号
     *
     * @param
     * @param
     * @return
     * search  order/rechargeOrder
     * @throws Exception
     */
    @RequestMapping("/updateOrderLogistics")
    public void updateOrderLogistics (@RequestBody OrderModel orderModel,  HttpServletResponse response) throws Exception{
        // 设置页面数据
        Map<String, Object> context = getRootMap();
        Order order = new Order();
        order.setId(orderModel.getId());
        order.setWlgs(orderModel.getWlgs());
        order.setYdbh(orderModel.getYdbh());
        order.setStatus("4");
        orderService.updateBySelective(order);

        // 写入订单日志
        Order newOrder = orderService.queryById(order.getId());
        OrderLog orderLog = new OrderLog();
        BeanUtils.copyProperties(newOrder, orderLog);
        orderLog.setOrderId(newOrder.getId());
        orderLog.setCreateTime(newOrder.getCreateTime());
        orderLog.setWlgs(orderModel.getWlgs());
        orderLog.setYdbh(orderModel.getYdbh());
        orderLogService.add(orderLog);

        context.put(SUCCESS, true);
        context.put("data", order);
        HtmlUtil.writerJson(response, context);
    }


    /**
     * 确认订单
     *
     * @param
     * @param
     * @return
     * search  order/rechargeOrder
     * @throws Exception
     */
    @RequestMapping("/confirmationOfOrder")
    public void confirmationOfOrder (@RequestBody OrderModel orderModel,  HttpServletResponse response) throws Exception{
        Order newOrder = orderService.queryById(orderModel.getId());
        Map<String, Object> context = getRootMap();
        Order order = new Order();
        if(!"5".equals(newOrder.getStatus())){
            order.setId(orderModel.getId());
            order.setStatus("5");


            //交易完成，修改用户积分
            WeixinUser wu = weixinUserService.queryWeixinUser(orderModel.getWxid());
            String myIntegralStr = wu.getIntegral();
            String myCountIntegralStr = wu.getIntegral();
            int myIntegral = 0;
            int myCountIntegral = 0;
            if(StringUtils.isNotEmpty(myIntegralStr)){
                myIntegral = Integer.parseInt(myIntegralStr);
            }
            if(StringUtils.isNotEmpty(myIntegralStr)){
                myCountIntegral = Integer.parseInt(myCountIntegralStr);
            }
            int orderIntegral =   newOrder.getOrderMoney().intValue();
            myIntegral = myIntegral + orderIntegral;
            myCountIntegral = myCountIntegral + orderIntegral;
            wu.setIntegral(myIntegral+"");
            wu.setCountIntegral(myCountIntegral+"");

            //交易完成，当积分达到一定限度，升级vip等级
            if(  myCountIntegral > 1 && myCountIntegral <5000){
                //1级
                wu.setVipGrade("1");
            }else if(myCountIntegral >= 5000 && myCountIntegral <20000){
                //2
                wu.setVipGrade("2");
            }else if(myCountIntegral >= 20000 && myCountIntegral <50000){
                //3
                wu.setVipGrade("3");
            }else if(myCountIntegral >= 50000 && myCountIntegral <100000){
                //4
                wu.setVipGrade("4");
            }else if(myCountIntegral >= 100000){
                //5
                wu.setVipGrade("5");
            }
            orderService.updateBySelective(order);
            weixinUserService.updateBySelective(wu);


            // 写入订单日志
            OrderLog orderLog = new OrderLog();
            BeanUtils.copyProperties(newOrder, orderLog);
            orderLog.setOrderId(newOrder.getId());
            orderLog.setCreateTime(newOrder.getCreateTime());
            orderLogService.add(orderLog);
            context.put(SUCCESS, true);
        }else{
            context.put(SUCCESS, false);
        }

        context.put("data", order);
        HtmlUtil.writerJson(response, context);
    }


    /**
     * 去查看物流界面，需要提供订单号码和货运公司编号
     */

    @RequestMapping("/ajaxGetLogistics")
    public void ajaxGetLogistics(@RequestBody OrderModel orderModel,  HttpServletResponse response) throws Exception {
        // 设置页面数据
        Map<String, Object> context = getRootMap();
        Order bean = orderService.queryById(orderModel.getId());
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        UserAddr useraddr = null;
        if(StringUtils.isNotEmpty(bean.getAddressId())){
            //带出地址信息
            useraddr = userAddrService.queryById(bean.getAddressId());
        }else{
            //启用用户的默认地址
            useraddr = userAddrService.getDefaultAddrByWxid(orderModel.getWxid());
        }
        if(null != useraddr){
            context.put("useraddr", useraddr);
        }
        if(null != bean.getItemId()){
            AuctionItem auctionItem = auctionItemService.queryById(bean.getItemId());
            if(null != auctionItem){
                context.put("auctionItem", auctionItem);
            } else {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_NOT_EXIST, "商品不存在");
            }
            ItemResModel resModel  = new ItemResModel();
            resModel.setConid(auctionItem.getId());
            resModel.setConType("2");
            List<ItemRes> resDataList = itemResService.queryByList(resModel);
            auctionItem.setResList(resDataList);
        }

        // 查询物流信息
        String logistics = LogisticsUtil.getLogistics(bean.getWlgs(),bean.getYdbh(),"test","test");
        context.put("dataList", logistics);
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }




    /**
     * 查询我的报表数据
     */

    @RequestMapping("/ajaxGetReport")
    public void ajaxGetReport(@RequestBody OrderModel orderModel,  HttpServletResponse response) throws Exception {
        // 设置页面数据
        Map<String, Object> context = getRootMap();
        Order bean = orderService.queryById(orderModel.getId());
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }

        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }




}
