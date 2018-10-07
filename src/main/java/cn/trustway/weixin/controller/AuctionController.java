package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.AuctionModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.AuctionService;
import cn.trustway.weixin.service.FileUploadService;
import cn.trustway.weixin.service.ItemResService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.SessionUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拍卖功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/auction")
public class AuctionController extends BaseController {

    @Autowired(required = false)
    private AuctionService<Auction> auctionService;

    @Autowired
    private FileUploadService fileUploadService;


    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    private static final Integer[] AUCTION_ITEMS = {9, 12};
    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(AuctionModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/auctionList", context);
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
    public void dataList(AuctionModel model, HttpServletResponse response) throws Exception {
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
    public void ajaxDataList(@RequestBody AuctionModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(AuctionModel model, HttpServletResponse response) throws Exception {
        List<Auction> dataList = auctionService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", auctionService.queryByCount(model));
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
    public void getId(Integer id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        Auction bean = auctionService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }

        ItemResModel resModel  = new ItemResModel();
        resModel.setConid(bean.getId());
        resModel.setConType("1");
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
    public void save(Auction bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser sysUser = SessionUtil.getUser(request);
        Integer id = bean.getId();
        if(id != null && id > 0) {
            bean.setModifier(sysUser.getId());
            auctionService.updateBySelective(bean);
        } else {
            bean.setCreator(sysUser.getId());
            bean.setModifier(sysUser.getId());
            auctionService.add(bean);
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
        auctionService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }

    /**
     * 上传封面
     *
     * @param headImg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/uploadLogo",method=RequestMethod.POST)
    @ResponseBody
    public void uploadLogo(@RequestParam(required = false)MultipartFile headImg, @RequestParam String businessid,
                           HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> uploadResult = fileUploadService.uploadFile(headImg, request, response);
        boolean uploadFlag = Boolean.valueOf(uploadResult.get(SUCCESS).toString());
        if(uploadFlag) {
            Auction bean = auctionService.queryById(businessid);
            if (bean == null) {
                sendFailureMessage(response, "没有找到对应的记录!");
                return;
            } else {

                sendSuccessMessage(response, "上传成功");
            }
        } else {
            sendFailureMessage(response, "上传失败");
        }
    }

    /**
     * 查询商户下的拍卖会
     * @param businessId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ajaxGetJoinAuctions")
    public void ajaxGetJoinAuctions(Integer businessId, HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if(businessId != null && businessId > 0) {
            params.put("businessId", businessId);
        }
        params.put("auctionIds", AUCTION_ITEMS);
        List<Auction> joinAuctions = auctionService.queryByJoinAuction(params);
        if(CollectionUtils.isNotEmpty(joinAuctions)) {
            Map<String, Object> context = getRootMap();
            context.put(CODE, AppInitConstants.HttpCode.HTTP_SUCCESS);
            context.put("data", joinAuctions);
            HtmlUtil.writerJson(response, context);
            return;
        } else {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_NO_BUSINESS_AUCTIONS_ERROR, "未找到拍卖会信息");
            return;
        }

    }

    /**
     * 根据ID查找记录
     *
     * @param auctionUpload
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxCreateAuction")
    public void ajaxCreateAuction(AuctionUpload auctionUpload, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(null == auctionUpload) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_UPLOAD_ERROR, "创建拍卖专场失败");
            return;
        }
        String wxid = auctionUpload.getWxid();
        if(StringUtils.isEmpty(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "上传失败，用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "上传失败，用户信息有误");
            return;
        }

        // 图片信息
        JSONArray jsonArray = JSONArray.fromObject(auctionUpload.getImageList());
        List<UploadImage> imageList = new ArrayList<>();
        for(int j = 0; j < jsonArray.size(); j ++) {
            UploadImage img = new UploadImage();
            img.setWidth(String.valueOf(jsonArray.getJSONObject(j).get("width")));
            img.setHeight(String.valueOf(jsonArray.getJSONObject(j).get("height")));
            img.setData((String) jsonArray.getJSONObject(j).get("data"));
            imageList.add(img);
        }

        // 插入拍卖会信息
        Auction auction = new Auction();
        BeanUtils.copyProperties(auctionUpload, auction);

        auctionService.add(auction);

        if(null != auction.getId() && auction.getId() > 0 ) {
            for(int i = 0; i < imageList.size(); i ++) {
                ItemRes itemImage = new ItemRes();
                itemImage.setConid(auction.getId());
                itemImage.setPath(imageList.get(i).getData());
                itemImage.setType("1");
                itemImage.setConType("1");
                itemImage.setIdx(i);
                itemImage.setHeight(Integer.parseInt(imageList.get(i).getHeight()));
                itemImage.setWidth(Integer.parseInt(imageList.get(i).getWidth()));
                itemResService.add(itemImage);
            }
        }

        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "上传成功");
    }
}
