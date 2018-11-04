package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.ActivityModel;
import cn.trustway.weixin.model.MessageModel;
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
 * 消息功能页面控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired(required = false)
    private MessageService<Message> messageService;


    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(MessageModel model, HttpServletRequest request) throws Exception {
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
    public void dataList(MessageModel model, HttpServletResponse response) throws Exception {
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
        MessageModel model = new MessageModel();
        queryDataList(model, response);
    }



    private void queryDataList(MessageModel model, HttpServletResponse response) throws Exception {
        List<Message> dataList = messageService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", messageService.queryByCount(model));
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
    public void getId(Integer id,String wxid, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        Message bean = messageService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put("data", bean);


        //当前主消息下面子消息
        Map<String, Object> maps = new HashMap<>();
        maps.put("parentId",id);
        List<Message> messageList = messageService.queryParentByList(maps);
        context.put("messageList",messageList);
        context.put(SUCCESS, true);

        HtmlUtil.writerJson(response, context);
    }

    /**
     * 根据ID查找记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByV5Id")
    public void getByV5Id(Integer id,String wxid, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        //当前V5所有子消息
        MessageModel model = new MessageModel();
        model.setV5Id(id);
        List<Message> messageList = messageService.queryByList(model);
        context.put("messageList",messageList);
        context.put(SUCCESS, true);
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
    public void save(Message bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        if(id != null && id > 0) {
            messageService.update(bean);
        } else {
            messageService.add(bean);
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
        messageService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }

}
