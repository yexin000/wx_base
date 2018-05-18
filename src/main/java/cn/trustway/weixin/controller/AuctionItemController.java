package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.AuctionItem;
import cn.trustway.weixin.bean.BidBean;
import cn.trustway.weixin.bean.ItemRes;
import cn.trustway.weixin.model.AuctionItemModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.AuctionItemService;
import cn.trustway.weixin.service.BidService;
import cn.trustway.weixin.service.ItemResService;
import cn.trustway.weixin.util.HtmlUtil;
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
 * 拍品功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/auctionItem")
public class AuctionItemController extends BaseController {

    @Autowired(required = false)
    private AuctionItemService<AuctionItem> auctionItemService;

    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    @Autowired(required = false)
    private BidService<BidBean> bidservice;
    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(AuctionItemModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/auctionItemList", context);
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
    public void dataList(AuctionItemModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * 前端数据列表查询
     *
     * @param model
     * @param response
     * @return
     * @search auctionItem/ajaxDataList  方便前端检索
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(@RequestBody AuctionItemModel model, HttpServletResponse response) throws Exception {
        //主数据
        List<AuctionItem> dataList = auctionItemService.queryByList(model);

        for(AuctionItem ai : dataList){
            //图片数据
            ItemResModel resModel  = new ItemResModel();
            resModel.setConid(ai.getId());
            resModel.setConType("2");
            List<ItemRes> resDataList = itemResService.queryByList(resModel);
            ai.setResList(resDataList);
        }

        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);

        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryDataList(AuctionItemModel model, HttpServletResponse response) throws Exception {
        List<AuctionItem> dataList = auctionItemService.queryByList(model);
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
    public void save(AuctionItem bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            auctionItemService.updateBySelective(bean);
        } else {
            auctionItemService.add(bean);
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
        AuctionItem bean = auctionItemService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }


    /**
     * 根据ID查找记录
     *
     * @param id
     * @param response
     * @return
     * search  auctionItem/ajaxGetId
     * @throws Exception
     */
    @RequestMapping("/ajaxGetId")
    public void ajaxGetId(@RequestBody Integer id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        AuctionItem bean = auctionItemService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        ItemResModel resModel  = new ItemResModel();
        resModel.setConid(bean.getId());
        resModel.setConType("2");
        List<ItemRes> resDataList = itemResService.queryByList(resModel);
        bean.setResList(resDataList);
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 根据ID出价
     *
     * @param id
     * @param response
     * @return
     * search  auctionItem/ajaxGetId
     * @throws Exception
     */
    @RequestMapping("/ajaxToBid")
    public void ajaxToBid(@RequestBody Integer id, @RequestBody String wxId,HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        AuctionItem bean = auctionItemService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        //先判断是出价时间是否到了
        int timeOut = bean.getEndTime().compareTo(new Date());
        if(timeOut > 0)
        {
            context.put(SUCCESS, false);
            context.put("data", "timeout");
            HtmlUtil.writerJson(response, context);
            return;
        }
        //出价流程
        BidBean bidbean = new BidBean();
        bidbean.setWxid(wxId);
        bidbean.setAuctionItemId(bean.getId());
        bidbean.setAuctionItemName(bean.getAuctionName());
        bidbean.setStatus("1");
        bidbean.setBidTime(new Date());
        bidservice.add(bidbean);
        ItemResModel resModel  = new ItemResModel();
        List<ItemRes> resDataList = itemResService.queryByList(resModel);
        bean.setResList(resDataList);
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
        auctionItemService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
