package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ActivityModel;
import cn.trustway.weixin.model.ExpectedModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预出价页面控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/expected")
public class ExpectedPriceController extends BaseController {

    @Autowired(required = false)
    private ExpectedService<Expected> expectedService;
    @Autowired(required = false)
    private AuctionItemService<AuctionItem> auctionItemService;

    @Autowired
    UserAddrService<UserAddr> userAddrService;

    @Autowired
    OrderService<Order> orderService;

    @Autowired
    OrderLogService<OrderLog> orderLogService;

    @Autowired(required = false)
    private MessageService<Message> messageService;


    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(ExpectedModel model, HttpServletRequest request) throws Exception {
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
    public void dataList(ExpectedModel model, HttpServletResponse response) throws Exception {
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
    public void ajaxDataList(@RequestBody ExpectedModel model , HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(ExpectedModel model, HttpServletResponse response) throws Exception {

        AuctionItem bean = auctionItemService.queryById(model.getCommodityId());
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        if(bean.getUploadWxid().equals(model.getWxid())){
            //如果是卖家
            model.setWxid("");
        }
        List<Expected> dataList = expectedService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("data", bean);
        jsonMap.put("total", expectedService.queryByCount(model));
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
    public void save(@RequestBody Expected bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        //用户需要拥有收货地址
        UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(bean.getWxid());
        if(null == defaultAddr) {
            sendFailureMessage(response, "缺少收货地址");
            return;
        }
        if(id != null && id > 0) {
            expectedService.updateBySelective(bean);
        } else {
            expectedService.add(bean);
        }
        sendSuccessMessage(response, "保存成功~");
    }

    /**
     * 购买
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/purchase")
    public void purchase(Integer id, String wxid, HttpServletResponse response) throws Exception {
        //id为预出价id

        Expected expected = expectedService.queryById(id);

        Map<String, Object> context = getRootMap();
        AuctionItem bean = auctionItemService.queryById(expected.getCommodityId());
        if (bean == null) {
            sendFailure(response,"-1", "没有找到对应的记录!");
            return;
        }

        //判断库存是否还有
        if(bean.getStock() < 1)
        {
            sendFailureMessage(response, "库存不足");
            return;
        }

        // 查询用户默认收货地址
        UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(expected.getWxid());
        if(null == defaultAddr) {
            sendFailureMessage(response, "缺少收货地址");
            return;
        }
        // 生成订单
        Date currentTime = new Date();
        Order order = new Order();
        order.setOrderType("2");
        order.setOrderMoney(expected.getPrice());
        order.setWxid( expected.getWxid());
        order.setItemId(expected.getCommodityId());
        order.setAddressId(defaultAddr.getId()+"");
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


        //保存一条站内通知
        Message message = new Message();
        message.setWxid("0");
        message.setToWxid(expected.getWxid());
        message.setMessagenote("您有新的预支付订单待支付");
        message.setMessagetype(1);
        message.setStatus(0);
        messageService.add(message);

        //修改状态
        expected.setStatus(3);
        expectedService.update(expected);

        context.put(SUCCESS, true);
        context.put("data", order);
        HtmlUtil.writerJson(response, context);
    }
}
