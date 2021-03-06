package cn.trustway.weixin.service;

import cn.trustway.weixin.message.template.BidOverTemplate;
import cn.trustway.weixin.message.template.RechargeTemplate;
import cn.trustway.weixin.servlet.TokenThread;
import cn.trustway.weixin.util.HttpClientUtil;
import cn.trustway.weixin.util.json.JsonToolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 模板消息服务类
 */
@Service("templateMsgService")
public class TemplateMsgService {
    public static final String SERVICE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

    /**
     * 发送出价被超过模板消息
     * @param touser
     * @param itemName
     * @param newPrice
     * @param formId
     */
    public void sendBidOverTemplate(String touser, String itemName, Double newPrice, String formId) {
        if(null != TokenThread.accessToken && StringUtils.isNotBlank(TokenThread.accessToken.getToken())) {
            String serviceUrl = SERVICE_URL + TokenThread.accessToken.getToken();
            BidOverTemplate template = new BidOverTemplate();
            template.setTemplateValue(touser, itemName, newPrice, formId);
            String postJson = JsonToolUtil.bean2json(template);
            System.out.println(postJson);
            System.out.println(HttpClientUtil.doPost(serviceUrl, postJson, "UTF-8"));
        }
    }

    /**
     * 发送充值成功模板消息
     * @param touser
     * @param rechargeTime
     * @param rechargeNum
     * @param formId
     */
    public void sendRechargeTemplate(String touser, String rechargeTime, Double rechargeNum, String formId) {
        if(null != TokenThread.accessToken && StringUtils.isNotBlank(TokenThread.accessToken.getToken())) {
            String serviceUrl = SERVICE_URL + TokenThread.accessToken.getToken();
            RechargeTemplate template = new RechargeTemplate();
            template.setTemplateValue(touser, rechargeTime, rechargeNum, formId);
            String postJson = JsonToolUtil.bean2json(template);
            System.out.println(postJson);
            System.out.println(HttpClientUtil.doPost(serviceUrl, postJson, "UTF-8"));
        }
    }
}
