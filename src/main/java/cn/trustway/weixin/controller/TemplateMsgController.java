package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.WxForm;
import cn.trustway.weixin.service.TemplateMsgService;
import cn.trustway.weixin.service.WxFormService;
import cn.trustway.weixin.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * 发送模板消息控制类
 */
@Controller
@RequestMapping("/templateMsg")
public class TemplateMsgController extends BaseController {
    @Autowired
    TemplateMsgService templateMsgService;
    @Autowired
    WxFormService<WxForm> wxFormService;

    /**
     * 向用户发送出价被超过模板消息
     *
     * @param formId       表单id
     * @param bidMoney     出价金额
     * @param itemName     拍品名称
     * @param beOveredUser 模板消息通知对象
     * @param wxid         提交表单wxid
     * @return
     */
    @RequestMapping(value = "bidOverTemplateMsg", produces = "application/json")
    public void bidOverTemplateMsg(@RequestParam String formId,
                                   @RequestParam String bidMoney,
                                   @RequestParam String itemName,
                                   @RequestParam String beOveredUser,
                                   @RequestParam String wxid,
                                   HttpServletResponse response) {
        if (StringUtils.isBlank(formId) || formId.contains("mock one") || StringUtils.isBlank(wxid)) {
            sendFailureMessage(response, "发送模板消息失败");
            return;
        }
        // 记录用户和表单id
        WxForm wxForm = new WxForm();
        wxForm.setFormId(formId);
        wxForm.setWxid(wxid);
        try {
            wxFormService.add(wxForm);
        } catch (Exception e) {
            sendFailureMessage(response, "记录用户表单失败");
            e.printStackTrace();
        }
        sendFailureMessage(response, "记录用户表单成功");
        // 通知对象不为空时，发送模板消息
        if (StringUtils.isNotBlank(beOveredUser)) {
            // 根据通知对象查询用户表单得到表单id
            WxForm toWxForm = wxFormService.queryByWxid(beOveredUser);
            if (null != toWxForm) {
                try {
                    itemName = URLDecoder.decode(itemName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                templateMsgService.sendBidOverTemplate(beOveredUser, itemName, Double.valueOf(bidMoney), toWxForm.getFormId());
                // 删除该表单id
                Integer[] ids = {toWxForm.getId()};
                try {
                    wxFormService.delete(ids);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sendFailureMessage(response, "发送模板消息失败");
            }
        }
    }

    /**
     * 向用户发送充值成功模板消息
     *
     * @param formId
     * @param rechargeNum
     * @param wxid
     * @param response
     */
    @RequestMapping(value = "rechargeTemplateMsg", produces = "application/json")
    public void rechargeTemplateMsg(@RequestParam String formId,
                                    @RequestParam String rechargeNum,
                                    @RequestParam String wxid,
                                    HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(formId) || formId.contains("mock one") || StringUtils.isBlank(wxid)) {
            sendFailureMessage(response, "发送模板消息失败");
            return;
        }

        String rechargeTime = DateUtil.getNowPlusTime();
        templateMsgService.sendRechargeTemplate(wxid, rechargeTime, Double.valueOf(rechargeNum), formId);
    }
}
