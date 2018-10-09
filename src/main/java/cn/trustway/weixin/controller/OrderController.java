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
        List<Order> dataList = orderService.queryByList(model);

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
        String logistics = LogisticsUtil.getLogistics("yuantong","DD2047086911","test","test");
        context.put("dataList", logistics);
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }


}
