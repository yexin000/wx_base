package cn.trustway.weixin.controller;


import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.model.OrderModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.LogisticsUtil;
import cn.trustway.weixin.util.http.AppClient;
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
import java.math.BigDecimal;
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

    @Autowired
    private MessageService<Message> messageService;

    @Autowired
    private RefundService<Refund> refundService;

    /**
     * 订单状态:删除
     */
    public static final String ORDER_STATUS_DELETE = "0";
    /**
     * 订单状态:失效
     */
    public static final String ORDER_STATUS_INVALID = "1";
    /**
     * 订单状态:待支付
     */
    public static final String ORDER_STATUS_NOTPAY = "2";
    /**
     * 订单状态:已支付
     */
    public static final String ORDER_STATUS_PAYED = "3";
    /**
     * 订单状态:已发货/待收货
     */
    public static final String ORDER_STATUS_SENDED = "4";
    /**
     * 订单状态:已完成
     */
    public static final String ORDER_STATUS_COMPLETED = "5";
    /**
     * 订单状态:退货中
     */
    public static final String ORDER_STATUS_REFUNDING = "6";
    /**
     * 订单状态:已退货
     */
    public static final String ORDER_STATUS_REFUNDED = "7";
    /**
     * 退款状态:0-未退款
     */
    public static final String REFUND_STATUS_NO = "0";
    /**
     * 退款状态:1-已退款
     */
    public static final String REFUND_STATUS_YES = "1";

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
        List<Order> dataList = orderService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>(2);
        jsonMap.put("total",orderService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
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
        if(StringUtils.isNotEmpty(model.getStatus())
            && ("5".equals(model.getStatus()) || "6".equals(model.getStatus()))){
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
     * 后台确认订单
     * @param wxid
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/confirmationOrder", method = RequestMethod.POST)
    public void confirmationOrder(Integer id, String wxid, HttpServletResponse response) throws Exception {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(id);
        orderModel.setWxid(wxid);
        this.confirmationOfOrder(orderModel, response);
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
            Float floatIntegral = Float.parseFloat(newOrder.getOrderMoney()+"");
            int orderIntegral =  floatIntegral.intValue();
            if(orderIntegral <= 0){
                orderIntegral = 0;
            }
            myIntegral = myIntegral + orderIntegral;
            myCountIntegral = myCountIntegral + orderIntegral;
            wu.setIntegral(myIntegral+"");
            wu.setCountIntegral(myCountIntegral+"");
            String v5Lv = wu.getVipGrade();
            //交易完成，当积分达到一定限度，升级vip等级
            if(!"5".equals(v5Lv)){
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
            }

            // ------------------------------余额 ------------------------------------------------------
            AuctionItem auctionItem = auctionItemService.queryById(newOrder.getItemId());
            if(null != auctionItem){
                WeixinUser saleMan = weixinUserService.queryWeixinUser(auctionItem.getUploadWxid());  //买家
                if(null != newOrder.getOrderMoney()){
                    // 处理余额到卖家
                    BigDecimal orderMoney = new BigDecimal(newOrder.getOrderMoney());
                    // 减去百分之6的平台手续费
                    BigDecimal sxf = orderMoney.multiply(new BigDecimal("0.06"));
                    // 卖家获取金额
                    BigDecimal balance = orderMoney.subtract(sxf);
                    BigDecimal myBalance = new BigDecimal("0");
                    if(null != saleMan.getBalance()){
                        myBalance = new BigDecimal(saleMan.getBalance());
                    }
                    myBalance = myBalance.add(balance);
                    saleMan.setBalance(myBalance.doubleValue());
                    weixinUserService.updateBySelective(saleMan);
                }
            }
            order.setSureTime(new Date());
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
    public void ajaxGetReport(String wxid,  HttpServletResponse response) throws Exception {
        // 设置页面数据
        Map<String, Object> context = getRootMap();
        Double tm = orderService.queryByFilmingTodayMoney(wxid);
        Integer tn = orderService.queryByFilmingTodayNum(wxid);
        Double tm1 =  orderService.queryByPaymentTodayMoney(wxid);
        Integer tn1 = orderService.queryByPaymentTodayNum(wxid);
        Double tm2 = orderService.queryByCollectionTodayMoney(wxid);
        Integer tn2 = orderService.queryByCollectionTodayNum(wxid);
        Report bean = new Report();
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
        if(null != tm){
            bean.setFilmingTodayMoney(df.format(tm));
        }
        if(null != tn){
            bean.setFilmingTodayNum(tn.toString());
        }
        if(null != tm1){
            bean.setPaymentTodayMoney(df.format(tm2));
        }
        if(null != tn1){
            bean.setPaymentTodayNum(tn2.toString());
        }
        if(null != tm2){
            bean.setCollectionTodayMoney(df.format(tm2));
        }
        if(null != tn2){
            bean.setCollectionTodayNum(tn2.toString());
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 发送通知
     * @param message
     * @param response
     * @throws Exception
     */
    @RequestMapping("/sendMessage")
    public void sendMessage(Message message, HttpServletResponse response) throws Exception {
        message.setParentId(0);
        message.setMessagetype(1);
        message.setStatus(0);
        message.setWxid("0");
        messageService.add(message);
        message.setParentId(message.getId());
        messageService.updateBySelective(message);
        sendSuccessMessage(response, "发送通知成功");
    }

    /**
     * 已支付订单申请退货退款
     */
    @RequestMapping("/ajaxRefundOrder")
    public void ajaxRefundOrder(@RequestBody Refund refund,  HttpServletResponse response) throws Exception {
        String wxid = refund.getWxid();
        if(StringUtils.isBlank(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "退货退款失败,用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "退货退款失败,用户信息有误");
            return;
        }

        if( null == refund.getOrderId() || refund.getOrderId() <= 0) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_REFUND_ORDER_ERROR, "退货退款失败,订单有误");
            return;
        }

        Order order = orderService.queryById(refund.getOrderId());
        if(null == order) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_REFUND_ORDER_ERROR, "退货退款失败,订单有误");
            return;
        }

        // 订单状态为已支付或已发货才可以退货
        if(!ORDER_STATUS_PAYED.equals(order.getStatus()) && !ORDER_STATUS_SENDED.equals(order.getStatus())) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_REFUND_ORDER_ERROR, "退货退款失败,订单有误");
            return;
        }

        // 设置订单状态为退货中
        order.setStatus(ORDER_STATUS_REFUNDING);
        orderService.updateBySelective(order);

        // 生成退货退款表数据
        refund.setItemId(order.getItemId());
        refund.setRefundMoney(order.getOrderMoney());
        refund.setStatus(REFUND_STATUS_NO);
        refundService.add(refund);

        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "申请成功");
    }

    /**
     * 修改确认收到状态
     */
    @RequestMapping("/ajaxRefundConfirm")
    public void ajaxRefundConfirm(@RequestBody Refund refund,  HttpServletResponse response) throws Exception {
        String wxid = refund.getWxid();
        if(StringUtils.isBlank(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "退货退款失败,用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "退货退款失败,用户信息有误");
            return;
        }

        if( null == refund.getOrderId() || refund.getOrderId() <= 0) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_REFUND_ORDER_ERROR, "操作失败,订单有误");
            return;
        }

        Order order = orderService.queryById(refund.getOrderId());
        if(null == order) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_REFUND_ORDER_ERROR, "操作失败,订单有误");
            return;
        }

        // 订单状态为退货中才可以退货
        if(!ORDER_STATUS_REFUNDING.equals(order.getStatus())) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_REFUND_ORDER_ERROR, "操作失败,订单不在退货中");
            return;
        }

        Refund newRefund = refundService.queryByOrderId(refund.getOrderId());
        if(StringUtils.isNotEmpty(refund.getBuyerStatus())) {
            newRefund.setBuyerStatus(refund.getBuyerStatus());
        }
        if(StringUtils.isNotEmpty(refund.getSellerStatus())) {
            newRefund.setSellerStatus(refund.getSellerStatus());
        }

        if("1".equals(newRefund.getBuyerStatus()) && "1".equals(newRefund.getSellerStatus())) {
            newRefund.setStatus("1");
            order.setStatus(ORDER_STATUS_REFUNDED);
            orderService.updateBySelective(order);
        }
        refundService.updateBySelective(newRefund);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "操作成功");
    }

    /**
     * 查询退货退款订单数
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/refundCount", method = RequestMethod.POST)
    public void refundCount(HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total",orderService.queryRefundByCount());
        jsonMap.put(SUCCESS, true);
        HtmlUtil.writerJson(response, jsonMap);
    }
}
