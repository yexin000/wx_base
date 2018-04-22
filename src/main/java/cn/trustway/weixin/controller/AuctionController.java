package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.AuctionBean;
import cn.trustway.weixin.model.AuctionModel;
import cn.trustway.weixin.util.HtmlUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拍卖功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/auction")
public class AuctionController extends BaseController {

    /**
     * json 列表页面
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dataList", method = RequestMethod.POST)
    public void dataList(@RequestBody AuctionModel model, HttpServletRequest request, HttpServletResponse response) {
        // model中有ajax中传入的参数
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<AuctionBean> dataList = new ArrayList<>();
        AuctionBean auction = new AuctionBean();
        auction.setName("test111");
        dataList.add(auction);
        // 查询列表，设置total和rows，total是总数，rows是要返回的数据对象列表
        jsonMap.put("total", dataList.size());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
}
