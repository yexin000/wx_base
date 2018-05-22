package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.GroupBean;
import cn.trustway.weixin.model.GroupModel;
import cn.trustway.weixin.service.FileUploadService;
import cn.trustway.weixin.service.GroupService;
import cn.trustway.weixin.util.HtmlUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微拍群控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {
    @Autowired
    private GroupService<GroupBean> groupService;

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
    public ModelAndView list(GroupModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/groupList", context);
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
    public void dataList(GroupModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(GroupModel model, HttpServletResponse response) throws Exception {
        List<GroupBean> dataList = groupService.queryByList(model);
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
    public void save(GroupBean bean, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            groupService.updateBySelective(bean);
        } else {
            groupService.add(bean);
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
        GroupBean bean = groupService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
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
        groupService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }

    /**
     * 上传封面或二维码
     *
     * @param headImg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/uploadLogo",method=RequestMethod.POST)
    @ResponseBody
    public void uploadLogo(@RequestParam(required = false)MultipartFile headImg, @RequestParam String groupId,
                           @RequestParam String uploadType, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> uploadResult = fileUploadService.uploadFile(headImg, request, response);
        boolean uploadFlag = Boolean.valueOf(uploadResult.get(SUCCESS).toString());
        if(uploadFlag) {
            GroupModel model = new GroupModel();
            model.setId(Integer.parseInt(groupId));
            GroupBean bean = groupService.queryById(model);
            if (bean == null) {
                sendFailureMessage(response, "没有找到对应的记录!");
                return;
            } else {
                if("1".equals(uploadType)) {
                    bean.setLogoUrl(uploadResult.get(MSG).toString());
                } else if("2".equals(uploadType)) {
                    bean.setCodeUrl(uploadResult.get(MSG).toString());
                }

                groupService.updateBySelective(bean);
                sendSuccessMessage(response, "上传成功");
            }
        } else {
            sendFailureMessage(response, "上传失败");
        }
    }

    /**
     * 前端用户查询微拍群
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajaxGetGroupList")
    public void ajaxGetMiddleMan(HttpServletResponse response) throws Exception {
        GroupModel model = new GroupModel();
        model.setRows(100);
        List<GroupBean> dataList = groupService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
}
