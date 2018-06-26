package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.ItemRes;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.FileUploadService;
import cn.trustway.weixin.service.ItemResService;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源关联功能页面控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/itemRes")
public class ItemResController extends BaseController {
    @Autowired
    private ItemResService<ItemRes> itemResService;

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
    public ModelAndView list(ItemResModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("conid", model.getConid());
        context.put("conType", model.getConType());
        context.put("conName", model.getConName());
        return forword("auction/itemResList", context);
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
    public void dataList(ItemResModel model, HttpServletResponse response) throws Exception {
        List<ItemRes> dataList = itemResService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", model.getPager().getRowCount());
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
        ItemRes bean = itemResService.queryById(id);
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
    @ResponseBody
    public void save(ItemRes bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        MultipartFile picFile = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            picFile = multiRequest.getFileMap().get("picFile");
        }

        boolean isUploadFile = picFile != null;
        String uploadPath = "";
        String width = "";
        String height = "";
        if(isUploadFile) {
            Map<String, Object> uploadResult = fileUploadService.uploadFile(picFile, request, response);
            boolean uploadFlag = Boolean.valueOf(uploadResult.get(SUCCESS).toString());
            if(uploadFlag) {
                uploadPath = uploadResult.get(MSG).toString();
                width = uploadResult.get("width").toString();
                height = uploadResult.get("height").toString();
            } else {
                sendFailureMessageText(response, "上传失败");
                return;
            }
        } else {
            if(id == null || id == 0) {
                sendFailureMessageText(response, "请选择图片文件");
                return;
            }
        }

        if(StringUtils.isNotBlank(uploadPath)) {
            bean.setWidth(Integer.parseInt(width));
            bean.setHeight(Integer.parseInt(height));
            bean.setPath(uploadPath);
        }

        if(id != null && id > 0) {
            itemResService.updateBySelective(bean);
        } else {
            itemResService.add(bean);
        }
        sendSuccessMessageText(response, "保存成功~");

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
        itemResService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
