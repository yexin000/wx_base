package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.AuctionItemModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
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

    @Autowired
    private FavoriteService<Favorite> favoriteService;

    @Autowired
    private AuctionService<Auction> auctionService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 默认手续费比率6%
     */
    public static final Double DEFUALT_RATE = 6d;

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
        Integer auctionId = bean.getAuctionId();
        Auction auction = auctionService.queryById(auctionId);
        bean.setBusinessId(auction.getBusinessid());
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
        if(null == itemUpload || itemImages.length == 0) {
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
        auctionItem.setEndTime(new Date());
        auctionItem.setRate(DEFUALT_RATE);
        auctionItem.setAuctionId(0);
        auctionItem.setBusinessId(0);
        auctionItemService.add(auctionItem);

        if(null != auctionItem.getId() && auctionItem.getId() > 0 ) {
            for(int i = 0; i < itemImages.length; i ++) {
                String uploadPath = "";
                Map<String, Object> uploadResult = fileUploadService.uploadFile(itemImages[i], request, response);
                boolean uploadFlag = Boolean.valueOf(uploadResult.get(SUCCESS).toString());
                if(uploadFlag) {
                    uploadPath = uploadResult.get(MSG).toString();
                    ItemRes itemImage = new ItemRes();
                    itemImage.setConid(auctionItem.getId());
                    itemImage.setPath(uploadPath);
                    itemImage.setType("1");
                    itemImage.setConType("2");
                    itemImage.setIdx(i);
                    itemResService.add(itemImage);
                } else {
                    sendFailureMessageText(response, "上传失败");
                    return;
                }
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
}
