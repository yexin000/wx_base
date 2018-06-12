package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.MoneyStream;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.model.GroupModel;
import cn.trustway.weixin.model.MoneyStreamModel;
import cn.trustway.weixin.model.OrderModel;
import cn.trustway.weixin.service.MoneyStreamService;
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
 * 微拍群控制类
 * @author dingjia
 *
 */
@Controller
@RequestMapping("/moneyStream")
public class MoneyStreamController extends BaseController {
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
    public ModelAndView list(OrderModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/moneyStream", context);
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
    public void dataList(MoneyStreamModel model, HttpServletResponse response) throws Exception {
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
    public void ajaxDataList(@RequestBody MoneyStreamModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(MoneyStreamModel model, HttpServletResponse response) throws Exception {
        List<MoneyStream> dataList = moneyStreamService.queryByList(model);
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
    public void save(@RequestBody MoneyStream bean, HttpServletResponse response) throws Exception {
        Integer id = bean.getId();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String status = "0";
        if(StringUtils.isNotEmpty(bean.getStreammoney()+"")){
            BigDecimal sm = new BigDecimal(bean.getStreammoney());
            WeixinUser wu = weixinUserService.queryWeixinUser(bean.getWxid());
            if(null != wu){
                BigDecimal myBal = new BigDecimal(wu.getBalance());
                if(myBal.compareTo(sm) < 0){
                    status = "-1";
                }else{
                    //成功
                    myBal =  myBal.subtract(sm);
                    wu.setBalance(myBal.doubleValue());
                    wu.setMoneyExtracting(sm.doubleValue());
                    weixinUserService.updateByBal(wu);
                    //生成充值流水
                    MoneyStream ms = new MoneyStream();
                    bean.setWxid(bean.getWxid());
                    bean.setStreammoney(sm.doubleValue());
                    bean.setCreatetime(new Date());
                    bean.setFlownumber(createCode());
                    bean.setStreamtype("3");
                    bean.setStatus(0);
                    bean.setWhereabouts(bean.getWhereabouts());
                    moneyStreamService.add(bean);
                }
            }
        }

        if(id != null && id > 0) {
            moneyStreamService.updateBySelective(bean);
        } else {
            bean.setCreatetime(new Date());
            bean.setFlownumber(createCode());
            moneyStreamService.add(bean);
        }
        jsonMap.put("status", status);
        HtmlUtil.writerJson(response, jsonMap);
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
        MoneyStream bean = moneyStreamService.queryById(id);
        if (bean == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
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
        moneyStreamService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }

}
