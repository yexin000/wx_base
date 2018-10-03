package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ActivityModel;
import cn.trustway.weixin.model.FollowModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
 * 关注功能页面控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/follow")
public class FollowController extends BaseController {

    @Autowired(required = false)
    private FollowService<Follow> followService;


    @Autowired(required = false)
    private ItemResService<ItemRes> itemResService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(FollowModel model, HttpServletRequest request) throws Exception {
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
    public void dataList(FollowModel model, HttpServletResponse response) throws Exception {
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
    public void ajaxDataList(HttpServletResponse response) throws Exception {
        FollowModel model = new FollowModel();
        queryDataList(model, response);
    }

    /**
     * 前端列表数据列表查询
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxGetAllList", method = RequestMethod.POST)
    public void ajaxGetJoinList(HttpServletResponse response ,  Integer limit ,String wxid) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid",wxid);
        params.put("limit",4);

        //查询关注的用户
        List<Follow> dataList1 = followService.queryFollowUserByList(params);

        //查询关注的拍品
        List<Follow> dataList2 = followService.queryFollowAuctionItemByList(params);
        //需要查询第一张图
        if(null != dataList2 && dataList2.size()>0){
            for(Follow follow : dataList2){
                Map<String, Object> imgParams = new HashMap<>();
                imgParams.put("conid",follow.getFollowId());
                ItemRes ir = itemResService.queryByConId(imgParams);
                if(null != ir){
                    follow.setPath(ir.getPath());
                }
            }
        }

        //查询关注的展览
        List<Follow> dataList3 = followService.queryFollowAuctionByList(params);
        //需要查询第一张图
        if(null != dataList3 && dataList3.size()>0){
            for(Follow follow : dataList3){
                Map<String, Object> imgParams = new HashMap<>();
                imgParams.put("conid",follow.getFollowId());
                ItemRes ir = itemResService.queryByConId(imgParams);
                if(null != ir){
                    follow.setPath(ir.getPath());
                }
            }
        }
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows1", dataList1);
        jsonMap.put("rows2", dataList2);
        jsonMap.put("rows3", dataList3);
        HtmlUtil.writerJson(response, jsonMap);
    }

    private void queryDataList(FollowModel model, HttpServletResponse response) throws Exception {
        List<Follow> dataList = followService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", followService.queryByCount(model));
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }


    /**
     * 关注
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public void save(Follow bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        followService.add(bean);
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
        followService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }

}
