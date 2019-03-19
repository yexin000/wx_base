package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.MoneyStream;
import cn.trustway.weixin.bean.PutForward;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.model.MoneyStreamModel;
import cn.trustway.weixin.model.OrderModel;
import cn.trustway.weixin.model.PutForwardModel;
import cn.trustway.weixin.service.MoneyStreamService;
import cn.trustway.weixin.service.PutForwardService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现控制类
 *
 * @author dingjia
 */
@Controller
@RequestMapping("/putForward")
public class PutForwardController extends BaseController {
    @Autowired
    private PutForwardService<PutForward> putForwardService;

    @Autowired
    private MoneyStreamService<MoneyStream> moneyStreamService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(PutForwardModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/putForward", context);
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
    public void dataList(PutForwardModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    /**
     * 前端数据列表查询
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(@RequestBody PutForwardModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(PutForwardModel model, HttpServletResponse response) throws Exception {
        List<PutForward> dataList = putForwardService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", putForwardService.queryByCount(model));
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
        PutForward bean = putForwardService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    /**
     * 根据ID审核记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/audit")
    public void audit(Integer id, HttpServletResponse response) throws Exception {
        if (id != null && id > 0) {
            putForwardService.updateByExamine(id);
            PutForward pf = putForwardService.queryById(id);
            //生成提现流水
            MoneyStream bean = new MoneyStream();
            bean.setStreammoney(pf.getMoney());
            bean.setCreatetime(new Date());
            bean.setStreamtype("3");
            bean.setStatus(0);
            bean.setPfId(pf.getId());
            bean.setFlownumber(createCode());
            bean.setWxid(pf.getWxid());
            bean.setWhereabouts(pf.getWxAccount());
            moneyStreamService.add(bean);

            WeixinUser wu = weixinUserService.queryWeixinUser(pf.getWxid());
            //提现成功，更新提现中金额
            BigDecimal myMoneyExtracting = new BigDecimal(wu.getMoneyExtracting());
            myMoneyExtracting = myMoneyExtracting.subtract(new BigDecimal(pf.getMoney()));
            wu.setMoneyExtracting(myMoneyExtracting.doubleValue());
            weixinUserService.updateByExtracting(wu);

            sendSuccessMessage(response, "审核成功");
        } else {
            sendFailureMessage(response, "审核失败");
        }
    }

    /**
     * 根据ID审核记录
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditDeny")
    public void auditDeny(Integer id, HttpServletResponse response) throws Exception {
        if (id != null && id > 0) {
            putForwardService.updateByDeny(id);
            PutForward pf = putForwardService.queryById(id);

            WeixinUser wu = weixinUserService.queryWeixinUser(pf.getWxid());
            // 提现失败，余额回滚
            wu.setBalance(wu.getBalance() + wu.getMoneyExtracting());
            weixinUserService.updateByBal(wu);
            // 提现失败，更新提现中金额
            BigDecimal myMoneyExtracting = new BigDecimal(wu.getMoneyExtracting());
            myMoneyExtracting = myMoneyExtracting.subtract(new BigDecimal(pf.getMoney()));
            wu.setMoneyExtracting(myMoneyExtracting.doubleValue());
            weixinUserService.updateByExtracting(wu);
            sendSuccessMessage(response, "审核成功");
        } else {
            sendFailureMessage(response, "审核失败");
        }
    }


    /**
     * 添加或修改数据
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public void save(@RequestBody PutForwardModel model, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String status = "0";
        BigDecimal sm = new BigDecimal(model.getMoney());
        WeixinUser wu = weixinUserService.queryWeixinUser(model.getWxid());
        if (null != wu) {
            BigDecimal myBal = new BigDecimal(wu.getBalance());
            if (myBal.compareTo(sm) < 0) {
                status = "-1";
            } else {
                //成功
                myBal = myBal.subtract(sm);
                wu.setBalance(myBal.doubleValue());
                //如果已经有余额正在提现中，请累加
                BigDecimal myMoneyExtracting = null;
                if (null != wu.getMoneyExtracting()) {
                    myMoneyExtracting = new BigDecimal(wu.getMoneyExtracting());
                    myMoneyExtracting = myMoneyExtracting.add(sm);
                } else {
                    myMoneyExtracting = sm;
                }
                wu.setMoneyExtracting(myMoneyExtracting.doubleValue());
                weixinUserService.updateByBal(wu);
                weixinUserService.updateByExtracting(wu);
                //生成提现记录
                PutForward bean = new PutForward();
                bean.setWxid(model.getWxid());
                bean.setMoney(sm.doubleValue());
                bean.setCreatetime(new Date());
                bean.setWxAccount(model.getWxaccount());
                bean.setStatus(0);
                bean.setPutForwardNo(createCode());
                bean.setBankName(model.getBankName());
                bean.setCardNum(model.getCardNum());
                bean.setPhone(model.getPhone());
                putForwardService.add(bean);
            }
        }
        jsonMap.put("status", status);
        HtmlUtil.writerJson(response, jsonMap);
    }


    private String createCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return "No" + sdf.format(new Date()) + getIDCode4();
    }

    /**
     * 生成4位数的随机数
     *
     * @return
     */
    protected static String getIDCode4() {
        int idCode = (int) (Math.random() * 9000 + 1000);
        return idCode + "";
    }


}
