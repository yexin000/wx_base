package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.TextMessage;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.service.TextMessageService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.http.AppClient;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 短信发送功能控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/textMessage")
public class TextMessageController extends BaseController {
    /**
     * 消息类型，1-通知短信
     */
    public static final String MESSAGE_TYPE_NOTIFY = "1";
    /**
     * 消息类型，0-短信验证码
     */
    public static final String MESSAGE_TYPE_VERIFY = "0";
    public static final String MESSAGE_VERIFY_CONTENT = "【百姓收藏】您的短信验证码为%s,有效时间3分钟.";
    @Autowired
    private TextMessageService<TextMessage> textMessageService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    public static void main(String[] args) {
        System.out.println(String.format(MESSAGE_VERIFY_CONTENT, getVerifyCode()));
    }

    /**
     * 发送通知短信
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendNotifyMessage")
    public void sendNotifyMessage(@RequestBody TextMessage bean, HttpServletResponse response) throws Exception {
        if (!validateParams(bean, response, MESSAGE_TYPE_VERIFY)) {
            return;
        }
        // TODO 调用短信接口发送短信
        bean.setContent("【百姓收藏】您有新的订单待处理");
        bean.setType(MESSAGE_TYPE_NOTIFY);
        textMessageService.add(bean);
        AppClient.sendChuanglanMessage("【百姓收藏】您有新的订单待处理", bean.getPhoneNum());
        sendSuccessMessage(response, "发送成功");
    }

    /**
     * 发送验证短信
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendVerifyMessage")
    public void sendVerifyMessage(@RequestBody TextMessage bean, HttpServletResponse response) throws Exception {
        if (!validateParams(bean, response, MESSAGE_TYPE_VERIFY)) {
            return;
        }

        String verifyCode = getVerifyCode();
        bean.setType(MESSAGE_TYPE_VERIFY);
        bean.setContent(String.format(MESSAGE_VERIFY_CONTENT, verifyCode));
        bean.setVerifyCode(verifyCode);
        // 调用短信接口发送短信
        AppClient.sendChuanglanMessage(String.format(MESSAGE_VERIFY_CONTENT, verifyCode), bean.getPhoneNum());
        textMessageService.add(bean);

        sendSuccessMessage(response, "发送成功");
    }

    /**
     * 短信验证
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/verifyMessage")
    public void verifyMessage(@RequestBody TextMessage bean, HttpServletResponse response) throws Exception {
        if(StringUtils.isEmpty(bean.getPhoneNum())) {
            sendFailureMessage(response, "验证失败");
        }

        if(StringUtils.isEmpty(bean.getVerifyCode())) {
            sendFailureMessage(response, "验证失败");
        }

        TextMessage textMessage = textMessageService.getValidMessageByCode(bean);

        if(textMessage != null) {
            WeixinUser weixinUser = weixinUserService.queryWeixinUser(bean.getWxid());
            weixinUser.setPhoneNum(bean.getPhoneNum());
            weixinUserService.updatePhonenumByWxid(weixinUser);
            sendSuccessMessage(response, "验证成功");
        } else {
            sendFailureMessage(response, "验证失败");
        }
    }

    /**
     * 验证参数
     *
     * @param bean
     * @param response
     * @return
     */
    private boolean validateParams(TextMessage bean, HttpServletResponse response, String type) {
        boolean validateResult = true;
        // 手机号码必须
        if (StringUtils.isEmpty(bean.getPhoneNum())) {
            sendFailureMessage(response, "发送失败");
            validateResult = false;
        }

        // 通知短信必须有内容，验证短信内容后台生成二维码
        if (MESSAGE_TYPE_NOTIFY.equals(type) && StringUtils.isEmpty(bean.getContent())) {
            sendFailureMessage(response, "发送失败");
            validateResult = false;
        }

        return validateResult;
    }

    /**
     * 获取随机验证码
     *
     * @return
     */
    private static String getVerifyCode() {
        return RandomStringUtils.random(6, "0123456789");
    }
}