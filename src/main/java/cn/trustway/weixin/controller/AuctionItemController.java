package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.AuctionItemModel;
import cn.trustway.weixin.model.BlacklistModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private FavoriteService<Favorite> favoriteService;

    @Autowired
    private AuctionService<Auction> auctionService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired
    OrderService<Order> orderService;

    @Autowired
    OrderLogService<OrderLog> orderLogService;

    @Autowired
    UserAddrService<UserAddr> userAddrService;

    @Autowired
    private BlacklistService<Blacklist> blacklistService;

    /**
     * 默认手续费比率6%
     */
    public static final Double DEFUALT_RATE = 6d;

    /**
     * 商品类型：0-拍卖品
     */
    public static final String ATTRIBUTE_AUCTIONITEM = "0";

    /**
     * 商品类型：1-商品
     */
    public static final String ATTRIBUTE_GOODS = "1";

    /**
     * 商品类型：2-非卖品
     */
    public static final String ATTRIBUTE_NO_SALE = "2";

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
        jsonMap.put("total", auctionItemService.queryByCount(model));
        jsonMap.put("rows", dataList);

        HtmlUtil.writerJson(response, jsonMap);
    }


    /**
     * 前端数据列表查询  -- 我参与过的
     *
     * @param model
     * @param response
     * @return
     * @search auctionItem/ajaxDataList  方便前端检索
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxMyJoinDataList", method = RequestMethod.POST)
    public void ajaxMyJoinDataList(@RequestBody AuctionItemModel model, HttpServletResponse response) throws Exception {
        //主数据
        Map<String, Object> params = new HashMap<>();
        params.put("wxid",model.getUploadWxid());
        List<AuctionItem> dataList = auctionItemService.queryMyJoinByList(params);

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
        jsonMap.put("total", auctionItemService.queryByCount(model));
        jsonMap.put("rows", dataList);

        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryDataList(AuctionItemModel model, HttpServletResponse response) throws Exception {
        List<AuctionItem> dataList = auctionItemService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", auctionItemService.queryByCount(model));
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
        Integer auctionId = bean.getAuctionId();
        Auction auction = auctionService.queryById(auctionId);
        if(null != auction) {
            bean.setBusinessId(auction.getBusinessid());
        }

        if(id != null && id > 0) {
            auctionItemService.updateBySelective(bean);
        } else {
            bean.setUploadWxid("0");
            bean.setStock(1);
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
    public void ajaxGetId(Integer id, String wxid, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        AuctionItem bean = auctionItemService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }

        //做金钱运算
        BigDecimal startPrice = new BigDecimal(bean.getStartPrice());
        String wanyuan = startPrice.divide(new BigDecimal("10000")).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
        bean.setWanfenbi(wanyuan);
        ItemResModel resModel  = new ItemResModel();
        resModel.setConid(bean.getId());
        resModel.setConType("2");
        List<ItemRes> resDataList = itemResService.queryByList(resModel);
        bean.setResList(resDataList);

        // 查询是否收藏
        if(StringUtils.isNotBlank(wxid)) {
            Map<String, Object> params = new HashMap<>();
            params.put("wxid", wxid);
            params.put("favId", id);
            Favorite favorite = favoriteService.queryByWxidAndFavId(params);
            if(null != favorite) {
                bean.setIsFavorite("1");
            } else {
                bean.setIsFavorite("0");
            }
        } else {
            bean.setIsFavorite("0");
        }

        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 根据ID查找记录
     *
     * @param itemImages
     * @param itemUpload
     * @return
     * search  auctionItem/ajaxGetId
     * @throws Exception
     */
    @RequestMapping("/ajaxUploadAuctionItem")
    public void ajaxUploadAuctionItem(ItemUpload itemUpload, @RequestParam MultipartFile[] itemImages,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(null == itemUpload) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_UPLOAD_ERROR, "上传商品失败");
            return;
        }
        String wxid = itemUpload.getWxid();
        if(StringUtils.isEmpty(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "上传失败，用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "上传失败，用户信息有误");
            return;
        }

        JSONArray jsonArray = JSONArray.fromObject(itemUpload.getImageList());
        List<UploadImage> imageList = new ArrayList<>();
        for(int j = 0; j < jsonArray.size(); j ++) {
            UploadImage img = new UploadImage();
            img.setWidth(String.valueOf(jsonArray.getJSONObject(j).get("width")));
            img.setHeight(String.valueOf(jsonArray.getJSONObject(j).get("height")));
            img.setData((String) jsonArray.getJSONObject(j).get("data"));
            imageList.add(img);
        }

        // 更新用户的买家或卖家信息
        user.setBussinessName(itemUpload.getBusinessName());
        user.setBusinessAddress(itemUpload.getBusinessAddress());
        user.setWxAccount(itemUpload.getBusinessWxNum());
        weixinUserService.updateBySelective(user);

        // 插入商品信息
        AuctionItem auctionItem = new AuctionItem();
        BeanUtils.copyProperties(itemUpload, auctionItem);
        auctionItem.setUploadWxid(itemUpload.getWxid());
        auctionItem.setStartTime(new Date());
        auctionItem.setStartTime(itemUpload.getStartTime());
        auctionItem.setEndTime(itemUpload.getEndTime());
        auctionItem.setRate(DEFUALT_RATE);
        auctionItem.setBusinessId(0);
        auctionItem.setStatus("1");
        if(ATTRIBUTE_GOODS.equals(auctionItem.getAttribute()) || ATTRIBUTE_NO_SALE.equals(auctionItem.getAttribute())) {
            auctionItem.setAuctionId(0);
        }
        auctionItem.setIsShow("0");
        auctionItemService.add(auctionItem);

        if(null != auctionItem.getId() && auctionItem.getId() > 0 ) {
            for(int i = 0; i < imageList.size(); i ++) {
                ItemRes itemImage = new ItemRes();
                itemImage.setConid(auctionItem.getId());
                itemImage.setPath(imageList.get(i).getData());
                itemImage.setType("1");
                itemImage.setConType("2");
                itemImage.setIdx(i);
                itemImage.setHeight(Integer.parseInt(imageList.get(i).getHeight()));
                itemImage.setWidth(Integer.parseInt(imageList.get(i).getWidth()));
                itemResService.add(itemImage);
            }
        }

        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "上传成功");
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
        if (id != null && id > 0) {
            String status = "3";
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("status", status);
            auctionItemService.updateByItemStatus(params);
            sendSuccessMessage(response, "审核成功");
        } else {
            sendFailureMessage(response, "审核失败");
        }
    }

    /**
     * 根据ID审核记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditDeny")
    public void auditDeny(Integer id, HttpServletResponse response) throws Exception {
        if (id != null && id > 0) {
            String status = "2";
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("status", status);
            auctionItemService.updateByItemStatus(params);
            sendSuccessMessage(response, "审核成功");
        } else {
            sendFailureMessage(response, "审核失败");
        }
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
        Map<String, Object> context = getRootMap();
        AuctionItem bean = auctionItemService.queryById(id);
        if (bean == null) {
            sendFailure(response,"-1", "没有找到对应的记录!");
            return;
        }

        BlacklistModel bModel = new BlacklistModel();
        bModel.setBlackid(wxid);
        bModel.setCreatorid(bean.getUploadWxid());
        Integer blackCount = blacklistService.queryForegroundByCount(bModel);
        if(blackCount > 0) {
            sendFailureMessage(response, "购买失败，已被商家加入黑名单");
            return;
        }

        bModel.setStatus("1");
        Integer backgroundBlackCount = blacklistService.queryBackgroundByCount(bModel);
        if(backgroundBlackCount > 0) {
            sendFailureMessage(response, "购买失败，已被系统加入黑名单");
            return;
        }

        //判断库存是否还有
        if(bean.getStock() < 1)
        {
            sendFailureMessage(response, "库存不足");
            return;
        }

        //addressId
        // 查询用户默认收货地址
        UserAddr defaultAddr = userAddrService.getDefaultAddrByWxid(wxid);
        if(null == defaultAddr) {
            sendFailureMessage(response, "缺少收货地址");
            return;
        }
        // 生成订单
        Date currentTime = new Date();
        Order order = new Order();
        order.setOrderType("2");
        order.setOrderMoney(bean.getStartPrice());
        order.setWxid( wxid);
        order.setItemId(id);
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

        context.put(SUCCESS, true);
        context.put("data", order);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 查询用户下的拍卖品
     * @param wxid
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ajaxGetAuctionItems")
    public void ajaxGetAuctionItems(String wxid, String attribute, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "查询失败，用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if (null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "查询失败，用户信息有误");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("attribute", attribute);
        List<AuctionItem> auctionItems = auctionItemService.queryByWxid(params);
        if(CollectionUtils.isNotEmpty(auctionItems)) {
            Map<String, Object> context = getRootMap();
            context.put(CODE, AppInitConstants.HttpCode.HTTP_SUCCESS);
            context.put("data", auctionItems);
            HtmlUtil.writerJson(response, context);
            return;
        } else {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_NO_BUSINESS_AUCTIONS_ERROR, "未找到拍卖品信息");
            return;
        }
    }
}
