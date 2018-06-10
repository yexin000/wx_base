package cn.trustway.weixin.controller;

import cn.trustway.weixin.service.TemplateMsgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 发送模板消息控制类
 */
@Controller
@RequestMapping("/templateMsg")
public class TemplateMsgController extends BaseController {
    @Autowired
    TemplateMsgService templateMsgService;

    /**
     * 向用户发送出价被超过模板消息
     *
     * @param formId 表单id
     * @param bidMoney  出价金额
     * @param itemName  拍品名称
     * @param beOveredUser 模板消息通知对象
     * @return
     */
    @RequestMapping(value = "bidOverTemplateMsg", produces = "application/json")
    public void bidOverTemplateMsg(@RequestParam String formId,
                               @RequestParam String bidMoney,
                               @RequestParam String itemName,
                               @RequestParam String beOveredUser,
                               HttpServletResponse response) {
        if(StringUtils.isBlank(formId) || StringUtils.isBlank(beOveredUser)) {
            sendFailureMessage(response, "发送模板消息失败");
            return;
        }
        try {
            itemName = URLDecoder.decode(itemName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        templateMsgService.sendBidOverTemplate(beOveredUser, itemName, Double.valueOf(bidMoney), formId);
    }
}
