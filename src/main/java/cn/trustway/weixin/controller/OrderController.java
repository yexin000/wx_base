package cn.trustway.weixin.controller;


import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.model.OrderModel;
import cn.trustway.weixin.service.*;
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
    public void ajaxDataList(@RequestBody OrderModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(OrderModel model, HttpServletResponse response) throws Exception {
        List<Order> dataList = orderService.queryByList(model);

        for(Order order : dataList)
        {
            ItemResModel resModel  = new ItemResModel();
            resModel.setConid(order.getItemId());
            resModel.setConType("2");
            List<ItemRes> resDataList = itemResService.queryByList(resModel);
            if(null != resDataList && resDataList.size() > 0)
            {
                order.setOrderCoverImg(resDataList.get(0).getPath());
            }
        }

        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
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
}
