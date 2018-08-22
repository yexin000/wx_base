package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.Activity;
import cn.trustway.weixin.bean.Auction;
import cn.trustway.weixin.bean.ItemRes;
import cn.trustway.weixin.bean.SysUser;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ActivityModel;
import cn.trustway.weixin.model.AuctionModel;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.ActivityService;
import cn.trustway.weixin.service.AuctionService;
import cn.trustway.weixin.service.FileUploadService;
import cn.trustway.weixin.service.ItemResService;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.SessionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拍卖功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired(required = false)
    private ActivityService<Activity> activityService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    private static final Integer[] AUCTION_ITEMS = {9, 12};
    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(ActivityModel model, HttpServletRequest request) throws Exception {
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
    public void dataList(ActivityModel model, HttpServletResponse response) throws Exception {
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
    public void ajaxDataList(HttpServletResponse response) throws Exception {
        ActivityModel model = new ActivityModel();
        queryDataList(model, response);
    }

    private void queryDataList(ActivityModel model, HttpServletResponse response) throws Exception {
        List<Activity> dataList = activityService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", activityService.queryByCount(model));
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
        Activity bean = activityService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        ItemResModel resModel  = new ItemResModel();
        resModel.setConid(bean.getId());
        resModel.setConType("3");
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
    public void save(Activity bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            activityService.updateBySelective(bean);
        } else {
            activityService.add(bean);
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
        activityService.delete(id);
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
            Activity bean = activityService.queryById(businessid);
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
     * 报名接口
     * @param activityId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ajaxJoinActivity")
    public void ajaxGetJoinAuctions(Integer activityId,String wx_id, HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if(activityId != null && activityId > 0) {
            params.put("activityId", activityId);
        }
        if(StringUtils.isNotEmpty(wx_id)){
            params.put("wx_id", wx_id);
        }
        List<Activity> joinAuctions = activityService.queryByJoinActivity(params);
        if(null == joinAuctions || joinAuctions.size() == 0){
            //判断当前是否在活动时间中
            Activity activity = activityService.queryById(activityId);
            Date now = new Date();
            if(now.compareTo(activity.getStarttime()) < 0 || now.compareTo(activity.getEndtime()) > 0) {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_TIME_ERROR, "报名失败，不在有效时间范围");
                return;
            }
            //报名(支付才能成功)
            int col = activityService.addJoin(params);
            Map<String, Object> context = getRootMap();
            context.put(CODE, col > 0 ?  AppInitConstants.HttpCode.HTTP_SUCCESS : AppInitConstants.HttpCode.HTTP_PAY_FAIL);
            context.put("data", joinAuctions);
            HtmlUtil.writerJson(response, context);

        }
    }
}
