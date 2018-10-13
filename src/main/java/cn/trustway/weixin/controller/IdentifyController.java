package cn.trustway.weixin.controller;


import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.IdentifyModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.IdentifyService;
import cn.trustway.weixin.service.ItemResService;
import cn.trustway.weixin.service.WeixinUserService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户鉴定控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/identify")
public class IdentifyController extends BaseController {
    @Autowired
    private IdentifyService<Identify> identifyService;
    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;
    @Autowired
    private ItemResService<ItemRes> itemResService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(IdentifyModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/identifyList", context);
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
    public void dataList(IdentifyModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * 保存鉴定结果
     * @param identify
     * @param response
     * @throws Exception
     */
    @RequestMapping("/identify")
    public void identify(Identify identify, HttpServletResponse response) throws Exception {
        identify.setStatus("1");
        identifyService.updateBySelective(identify);
        sendSuccessMessage(response, "鉴定成功");
    }

    private void queryDataList(IdentifyModel model, HttpServletResponse response) throws Exception {
        List<Identify> dataList = identifyService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", identifyService.queryByCount(model));
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
    public void ajaxDataList(@RequestBody IdentifyModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * ajax根据ID查找记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxGetId")
    public void ajaxGetId(Integer id, HttpServletResponse response) throws Exception {
        getById(id, response);
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
        getById(id, response);
    }

    private void getById(Integer id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        Identify bean = identifyService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }

        ItemResModel resModel  = new ItemResModel();
        resModel.setConid(bean.getId());
        resModel.setConType("4");
        List<ItemRes> resDataList = itemResService.queryByList(resModel);
        bean.setResList(resDataList);

        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 上传鉴定商品
     *
     * @param itemUpload
     * @return
     * search  auctionItem/ajaxGetId
     * @throws Exception
     */
    @RequestMapping("/ajaxUploadIdentify")
    public void ajaxUploadAuctionItem(IdentifyUpload itemUpload, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (null == itemUpload) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_UPLOAD_ERROR, "上传鉴定失败");
            return;
        }
        String wxid = itemUpload.getWxid();
        if (StringUtils.isEmpty(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "上传失败，用户信息有误");
            return;
        }
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if (null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "上传失败，用户信息有误");
            return;
        }

        // 插入鉴定信息
        Identify identifyItem = new Identify();
        BeanUtils.copyProperties(itemUpload, identifyItem);
        identifyItem.setStatus("0");
        identifyService.add(identifyItem);

        if (null != identifyItem.getId() && identifyItem.getId() > 0 ) {
            if(StringUtils.isNotBlank(itemUpload.getImageList())) {
                JSONArray jsonArray = JSONArray.fromObject(itemUpload.getImageList());
                List<UploadImage> imageList = new ArrayList<>();
                for (int j = 0; j < jsonArray.size(); j++) {
                    UploadImage img = new UploadImage();
                    img.setWidth(String.valueOf(jsonArray.getJSONObject(j).get("width")));
                    img.setHeight(String.valueOf(jsonArray.getJSONObject(j).get("height")));
                    img.setData((String) jsonArray.getJSONObject(j).get("data"));
                    imageList.add(img);
                }

                for (int i = 0; i < imageList.size(); i ++) {
                    ItemRes itemImage = new ItemRes();
                    itemImage.setConid(identifyItem.getId());
                    itemImage.setPath(imageList.get(i).getData());
                    itemImage.setType("1");
                    itemImage.setConType("4");
                    itemImage.setIdx(i);
                    itemImage.setHeight(Integer.parseInt(imageList.get(i).getHeight()));
                    itemImage.setWidth(Integer.parseInt(imageList.get(i).getWidth()));
                    itemResService.add(itemImage);
                }
            }
        }

        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "上传成功");
    }
}
