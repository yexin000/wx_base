package cn.trustway.weixin.controller;


import cn.trustway.weixin.bean.Identify;
import cn.trustway.weixin.model.IdentifyModel;
import cn.trustway.weixin.service.IdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    IdentifyService<Identify> identifyService;

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
}
