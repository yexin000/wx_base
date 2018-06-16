package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.AuctionItem;
import cn.trustway.weixin.bean.Business;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.model.BusinessModel;
import cn.trustway.weixin.service.AuctionItemService;
import cn.trustway.weixin.service.BusinessService;
import cn.trustway.weixin.service.FileUploadService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/business")
public class BusinessController extends BaseController {

    @Autowired(required = false)
    private BusinessService<Business> businessService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired(required = false)
    private AuctionItemService<AuctionItem> auctionItemService;

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(BusinessModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/businessList", context);
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
    public void dataList(BusinessModel model, HttpServletResponse response)
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
    public void ajaxDataList(@RequestBody BusinessModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(BusinessModel model, HttpServletResponse response) throws Exception {
        List<Business> dataList = businessService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", businessService.queryByCount(model));
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
        BusinessModel model = new BusinessModel();
        model.setId(id);
        queryById(model,response);
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
    public void ajaxGetId(@RequestBody Integer id, HttpServletResponse response) throws Exception {
        BusinessModel model = new BusinessModel();
        model.setId(id);
        model.setIsSelectItemCount("1");
        Map<String, Object> context = getRootMap();
        Business bean = businessService.queryCountById(model);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }


    /**
     * ajax根据ID查找记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxGetIdByOrder")
    public void ajaxGetIdByOrder(  Integer id, Integer itemId, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        Business bean = new Business();
        //订单内，如果商户id是0，则是用户商户类型
        if(id > 0){
            BusinessModel model = new BusinessModel();
            model.setId(id);
            model.setIsSelectItemCount("1");
            bean= businessService.queryCountById(model);
            bean.setBusinessaddress(bean.getAddress());
            bean.setBussinessName(bean.getName());
            if (bean == null) {
                sendFailureMessage(response, "没有找到对应的记录!");
                return;
            }
        }else{
            // 查询用户商户类型数据
            AuctionItem at = auctionItemService.queryById(itemId);
            if(null != at){
                WeixinUser wu = weixinUserService.queryWeixinUser(at.getUploadWxid());
                if(null != wu){
                    bean.setBusinessaddress(wu.getBusinessAddress());
                    bean.setBussinessName(wu.getBussinessName());
                    bean.setWxAccount(wu.getWxAccount());
                }else{
                    sendFailureMessage(response, "没有找到用户的记录!");
                    return;
                }
            }else {
                sendFailureMessage(response, "没有找到商品的记录!");
                return;
            }
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }


    private void queryById(BusinessModel businessModel, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        Business bean = businessService.queryById(businessModel);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
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
    public void save(Business bean, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            businessService.updateBySelective(bean);
        } else {
            businessService.add(bean);
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
        businessService.delete(id);
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
            BusinessModel model = new BusinessModel();
            model.setId(Integer.parseInt(businessid));
            Business bean = businessService.queryById(model);
            if (bean == null) {
                sendFailureMessage(response, "没有找到对应的记录!");
                return;
            } else {
                bean.setLogoPath(uploadResult.get(MSG).toString());
                businessService.updateBySelective(bean);
                sendSuccessMessage(response, "上传成功");
            }
        } else {
            sendFailureMessage(response, "上传失败");
        }
    }

}
