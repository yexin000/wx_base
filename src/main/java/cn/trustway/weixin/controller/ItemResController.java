package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.ItemRes;
import cn.trustway.weixin.model.ItemResModel;
import cn.trustway.weixin.service.FileUploadService;
import cn.trustway.weixin.service.ItemResService;
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
 * 资源关联功能页面控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/itemRes")
public class ItemResController extends BaseController {
    @Autowired
    private ItemResService itemResService;

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
     * 添加或修改数据
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @ResponseBody
    public void save(@RequestParam(required = false) MultipartFile picFile, ItemRes bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> uploadResult = fileUploadService.uploadFile(picFile, request, response);
        boolean uploadFlag = Boolean.valueOf(uploadResult.get(SUCCESS).toString());
        if(uploadFlag) {
            bean.setPath(uploadResult.get(MSG).toString());
            Integer id = bean.getId();
            if(id != null && id > 0) {
                itemResService.updateBySelective(bean);
            } else {
                itemResService.add(bean);
            }
            sendSuccessMessageText(response, "保存成功~");
        } else {
            sendFailureMessageText(response, "上传失败");
        }
    }
}
