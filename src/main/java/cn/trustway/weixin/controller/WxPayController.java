package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.HttpClientUtil;
import cn.trustway.weixin.util.MD5;
import cn.trustway.weixin.util.http.AppClient;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信支付控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/wxPay")
public class WxPayController extends BaseController {
    private final static Logger log = Logger.getLogger(WxPayController.class);

    @Autowired
    OrderService<Order> orderService;

    @Autowired(required = false)
    private AuctionItemService<AuctionItem> auctionItemService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired
    private MoneyStreamService<MoneyStream> moneyStreamService;

    @Autowired(required = false)
    private ActivityService<Activity> activityService;

    @Autowired
    private IdentifyService<Identify> identifyService;

    @Autowired(required = false)
    private MessageService<Message> messageService;

    @Autowired
    private TextMessageService<TextMessage> textMessageService;

    /**
     * 微信支付统一下单
     *
     * @param wxid
     * @param orderId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createUnifiedOrder")
    public void createUnifiedOrder(String wxid, Integer orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(wxid) || orderId == null || orderId == 0) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ORDER_ERROR, "订单有误");
            return;
        }
        // 根据订单号得到订单金额
        Order order = orderService.queryById(orderId);
        if (null == order || !wxid.equals(order.getWxid())) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ORDER_ERROR, "订单有误");
            return;
        }

        //接受参数(金额)
        Double amount = order.getOrderMoney();
        //接受参数(openid)
        String openid = wxid;
        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        String amountFen = String.valueOf((int)(amount * 100));
        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();

        //设置随机字符串
        String nonceStr = RandomStringUtils.randomAlphanumeric(16);
        //设置商户订单号
        String outTradeNo = AppInitConstants.XCX_ORDER_PRE + orderId;
        //设置body变量 (支付成功显示在微信支付 商品详情中)
        String body = outTradeNo;

        //设置请求参数(小程序ID)
        paraMap.put("appid", AppInitConstants.XCX_APP_ID);
        //设置请求参数(商户号)
        paraMap.put("mch_id", AppInitConstants.XCX_MCHID);
        //设置请求参数(随机字符串)
        paraMap.put("nonce_str", nonceStr);
        //设置请求参数(商品描述)
        paraMap.put("body", body);
        //设置请求参数(商户订单号)
        paraMap.put("out_trade_no", outTradeNo);
        //设置请求参数(总金额)
        paraMap.put("total_fee", amountFen);
        //设置请求参数(终端IP)
        paraMap.put("spbill_create_ip", getIpAddr(request));
        //设置请求参数(通知地址)
        paraMap.put("notify_url", AppInitConstants.XCX_SERVICE_URL + "wxPay/unifiedOrderCallback.do");
        //设置请求参数(交易类型)
        paraMap.put("trade_type", "JSAPI");
        //设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
        paraMap.put("openid", openid);
        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        String sign = MD5.md532(stringA + "&key=" + AppInitConstants.XCX_MCHKEY).toUpperCase();
        //将参数 编写XML格式
        StringBuffer paramBuffer = new StringBuffer();
        paramBuffer.append("<xml>");
        paramBuffer.append("<appid>" + AppInitConstants.XCX_APP_ID + "</appid>");
        paramBuffer.append("<body>" + body + "</body>");
        paramBuffer.append("<mch_id>" + AppInitConstants.XCX_MCHID + "</mch_id>");
        paramBuffer.append("<nonce_str>" + paraMap.get("nonce_str") + "</nonce_str>");
        paramBuffer.append("<notify_url>" + paraMap.get("notify_url") + "</notify_url>");
        paramBuffer.append("<openid>" + paraMap.get("openid") + "</openid>");
        paramBuffer.append("<out_trade_no>" + paraMap.get("out_trade_no") + "</out_trade_no>");
        paramBuffer.append("<spbill_create_ip>" + paraMap.get("spbill_create_ip") + "</spbill_create_ip>");
        paramBuffer.append("<total_fee>" + paraMap.get("total_fee") + "</total_fee>");
        paramBuffer.append("<trade_type>" + paraMap.get("trade_type") + "</trade_type>");
        paramBuffer.append("<sign>" + sign + "</sign>");
        paramBuffer.append("</xml>");

        try {
            //发送请求(POST)(获得数据包ID)(这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码 就算你改成UTF8编码也一样不好使 所以修改成ISO8859-1)
            Map<String, String> map = doXMLParse(HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/pay/unifiedorder", new String(paramBuffer.toString().getBytes(), "UTF-8"), "UTF-8"));
            //应该创建 支付表数据
            if (map != null && "SUCCESS".equals(map.get("return_code"))) {
                order.setPrepayId(map.get("prepay_id"));
                orderService.updateBySelective(order);

                Map<String, Object> result = new HashMap<String, Object>();
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                jsonMap.put("timeStamp", String.valueOf(new Date().getTime()));
                jsonMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(16));
                jsonMap.put("package", "prepay_id=" + map.get("prepay_id"));
                jsonMap.put("prepay_id", map.get("prepay_id"));
                String signString = "appId=" + AppInitConstants.XCX_APP_ID + "&nonceStr=" + jsonMap.get("nonceStr")
                    + "&package=prepay_id=" + map.get("prepay_id") + "&signType=MD5&timeStamp=" + jsonMap.get("timeStamp")
                    + "&key=" + AppInitConstants.XCX_MCHKEY;
                jsonMap.put("paySign", MD5.md532(signString).toUpperCase());
                jsonMap.put("orderId", orderId);
                jsonMap.put("amount", amount);
                result.put(CODE, AppInitConstants.HttpCode.HTTP_SUCCESS);
                result.put("data", jsonMap);
                HtmlUtil.writerJson(response, result);
            } else {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_WXPAY_FAIL, "调用微信支付失败");
                log.info("微信统一下单 失败:" + map.get("return_msg"));
            }

            log.info(map.toString());
        } catch (UnsupportedEncodingException e) {
            log.info("微信统一下单 异常：" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.info("微信统一下单 异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 微信支付成功-- 异步回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/unifiedOrderCallback")
    public void unifiedOrderCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("微信回调接口方法 start");
        String inputLine = "";
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            //关闭流
            request.getReader().close();
            log.info("微信回调内容信息：" + notityXml);
            //解析成Map
            Map<String, String> map = doXMLParse(notityXml);
            //判断 支付是否成功
            if ("SUCCESS".equals(map.get("result_code"))) {
                log.info("微信回调返回是否支付成功：是");
                //获得 返回的商户订单号
                String outTradeNo = map.get("out_trade_no");
                outTradeNo = outTradeNo.replace(AppInitConstants.XCX_ORDER_PRE, "");
                Integer orderId = Integer.parseInt(outTradeNo);
                log.info("微信回调返回商户订单号：" + outTradeNo);
                //访问DB
                Order order = orderService.queryById(orderId);

                if("3".equals(order.getStatus())){
                    //已经支付过的订单
                    writeMessageToResponse(response, "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
                    return;
                }
                order.setTransactionId(map.get("transaction_id"));
                order.setPayTime(new Date());
                order.setStatus("3");
                orderService.updateBySelective(order);
                // 如果是购买商品，商品库存减一
                if("2".equals(order.getOrderType()) || "3".equals(order.getOrderType()) ) {
                    // 发送短信  找到买家
                    WeixinUser payMan = weixinUserService.queryWeixinUser(order.getWxid());  //买家
                    TextMessage bean = new TextMessage();
                    bean.setContent("【百姓收藏】您的订单支付成功");
                    bean.setType(TextMessageController.MESSAGE_TYPE_NOTIFY);
                    bean.setPhoneNum(payMan.getPhoneNum());
                    textMessageService.add(bean);
                    AppClient.sendChuanglanMessage("【百姓收藏】您的订单支付成功", payMan.getPhoneNum());
                    AuctionItem auctionItem = auctionItemService.queryById(order.getItemId());
                    if("2".equals(order.getOrderType())){
                        auctionItem.setStock(auctionItem.getStock() - 1);
                        // 库存小于等于0，设置下架
                        if(auctionItem.getStock() <= 0) {
                            auctionItem.setIsOnsale("0");
                        }
                        auctionItemService.updateBySelective(auctionItem);
                    }
                    // 发送短信  找到卖家
                    WeixinUser saleMan = weixinUserService.queryWeixinUser(auctionItem.getUploadWxid());  //买家
                    TextMessage bean2 = new TextMessage();
                    bean2.setContent("【百姓收藏】您有新的订单待处理");
                    bean2.setType(TextMessageController.MESSAGE_TYPE_NOTIFY);
                    bean.setPhoneNum(saleMan.getPhoneNum());
                    textMessageService.add(bean);
                    AppClient.sendChuanglanMessage("【百姓收藏】您有新的订单待处理", saleMan.getPhoneNum());
                    // ------------------------------余额 ------------------------------------------------------
                    if(null != order.getOrderMoney()){
                        // 处理余额到卖家
                        BigDecimal orderMoney = new BigDecimal(order.getOrderMoney());
                        // 减去百分之6的平台手续费
                        BigDecimal sxf = orderMoney.multiply(new BigDecimal("0.06"));
                        // 卖家获取金额
                        BigDecimal balance = orderMoney.subtract(sxf);
                        BigDecimal myBalance = new BigDecimal("0");
                        if(null != saleMan.getBalance()){
                            myBalance = new BigDecimal(saleMan.getBalance());
                        }
                        myBalance = myBalance.add(balance);
                        saleMan.setBalance(myBalance.doubleValue());
                        weixinUserService.updateBySelective(saleMan);
                    }
                }
                if("3".equals(order.getOrderType()))
                {
                    //充值订单
                    String atferStatus = weixinUserService.payAfter(order);

                }else if("4".equals(order.getOrderType())){
                    //报名订单
                    Map<String, Object> params = new HashMap<>();
                    params.put("activityId", order.getItemId());//报名时，报名id就是订单项id，不另启用其他字段
                    params.put("wx_id", order.getWxid());
                    activityService.addJoin(params);
                }else if("5".equals(order.getOrderType())){
                    //鉴定订单，只要支付成功，修改状态就ok了。
                    Identify identify = identifyService.queryById(order.getItemId());
                    identify.setStatus("0");//已付款，待鉴定
                    identifyService.updateBySelective(identify);
                }else if("6".equals(order.getOrderType())){
                    //鉴定订单，只要支付成功，修改状态就ok了。
                    WeixinUser wxuser = new WeixinUser();
                    wxuser.setWxid(order.getWxid());
                    wxuser.setVipGrade("5");
                    weixinUserService.updateBySelective(wxuser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void writeMessageToResponse(HttpServletResponse response, String message) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            writer = response.getWriter();
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * 余额支付
     * @param request
     * @param response
     */
    @RequestMapping(value = "/myMoneyPay")
    public void myMoneyPay(HttpServletRequest request,Integer orderId, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Order order =  orderService.queryById(orderId);
            WeixinUser wu = weixinUserService.queryWeixinUser(order.getWxid());
            if(null != wu.getBalance()){
                BigDecimal myMoney = new BigDecimal(wu.getBalance());
                BigDecimal ordMoney = new BigDecimal(order.getOrderMoney());
                if(myMoney.compareTo(ordMoney) > -1)
                {
                    myMoney = myMoney.subtract(ordMoney);
                    wu.setBalance(myMoney.doubleValue());
                    weixinUserService.updateByBal(wu);

                    //访问DB
                    order.setPayTime(new Date());
                    order.setStatus("3");
                    orderService.updateBySelective(order);

                    // 如果是购买商品，商品库存减一
                    if("2".equals(order.getOrderType())) {
                        AuctionItem auctionItem = auctionItemService.queryById(order.getItemId());
                        auctionItem.setStock(auctionItem.getStock() - 1);
                        // 库存小于等于0，设置下架
                        if(auctionItem.getStock() <= 0) {
                            auctionItem.setIsOnsale("0");
                        }
                        auctionItemService.updateBySelective(auctionItem);
                    }

                    //保存一条站内通知
                    Message message = new Message();
                    message.setWxid("0");
                    message.setToWxid(auctionItemService.queryById(order.getItemId()).getUploadWxid());
                    message.setMessagenote("您有新的订单待处理");
                    message.setMessagetype(1);
                    message.setStatus(0);
                    messageService.add(message);
                    message.setParentId(message.getId());
                    messageService.updateBySelective(message);

                    //生成充值流水
                    MoneyStream bean = new MoneyStream();
                    bean.setWxid(order.getWxid());
                    bean.setStreammoney(ordMoney.doubleValue());
                    bean.setCreatetime(new Date());
                    bean.setFlownumber(createCode());
                    bean.setStreamtype("2");
                    bean.setStatus(1);
                    bean.setWhereabouts("订单支付");
                    moneyStreamService.add(bean);
                    result.put(CODE, AppInitConstants.HttpCode.HTTP_SUCCESS);
                }else{
                    result.put(CODE, AppInitConstants.HttpCode.HTTP_PAY_FAIL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlUtil.writerJson(response, result);
    }

    /**
     * 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写
     * @return
     */
    private static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff;
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws Exception
     */
    private Map<String, String> doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map<String, String> m = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    private String createCode (){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return  "No"+sdf.format(new Date())+getIDCode4();
    }

    /**
     * 生成4位数的随机数
     * @return
     */
    protected static String getIDCode4() {
        int idCode = (int) (Math.random()*9000+1000);
        return idCode+"";
    }
}
