package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.AuctionItem;
import cn.trustway.weixin.bean.BidBean;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.BidModel;
import cn.trustway.weixin.service.AuctionItemService;
import cn.trustway.weixin.service.BidService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.SessionUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 出价记录控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/bid")
public class BidController extends BaseController {

    @Autowired
    private BidService<BidBean> bidService;

    @Autowired
    private AuctionItemService<AuctionItem> auctionItemService;

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
    public ModelAndView list(BidModel model, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("auction/auctionList", context);
    }

    /**
     * 前端用户竞拍出价商品
     *
     * @param bean
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxAddBid", method = RequestMethod.POST)
    public void ajaxAddBid(@RequestBody BidBean bean, HttpServletResponse response) throws Exception {
        String wxid = bean.getWxid();
        if(StringUtils.isBlank(wxid) || null == weixinUserService.queryWeixinUser(wxid)) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "出价失败，用户信息有误");
            return;
        }

        if(null == bean.getAuctionItemId() || bean.getAuctionItemId() <= 0) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_ERROR, "出价失败，商品信息有误");
            return;
        }

        // 商品信息
        AuctionItem auctionItem = auctionItemService.queryById(bean.getAuctionItemId());
        if(null == auctionItem) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_ERROR, "出价失败，商品信息有误");
            return;
        }
        //判断当前是否在拍卖时间中
        Date now = new Date();
        if(now.compareTo(auctionItem.getStartTime()) < 0 || now.compareTo(auctionItem.getEndTime()) > 0) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_TIME_ERROR, "出价失败，不在有效时间范围");
            return;
        }

        // 当前出价小于起拍价或当前价
        if(bean.getBidPrice() < auctionItem.getStartPrice() || bean.getBidPrice() < auctionItem.getCurPrice()) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_PRICE_SRTART_ERROR, "出价失败，加价金额低于起拍价");
            return;
        }

        //查询商品当前出价列表
        BidModel model = new BidModel();
        model.setAuctionItemId(bean.getAuctionItemId());
        List<BidBean> dataList = bidService.queryByList(model);

        //比较当前商品出价是否符合规则
        // 商品已有用户出价
        if(CollectionUtils.isNotEmpty(dataList)) {
            // 最后出价
            BidBean lastBid = dataList.get(0);

            // 当前出价小于最后出价加最低加价，出价失败
            if(bean.getBidPrice() < (lastBid.getBidPrice() + auctionItem.getAddPrice())) {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_PRICE_MIN_ERROR, "出价失败，加价金额低于最低加价金额");
                return;
            }
        }

        // 设置商品所有出价记录为出局
        bidService.updateBidOutByAuctionItemId(bean.getAuctionItemId());
        //插入出价数据
        bean.setStatus("1");
        bidService.add(bean);
        sendSuccess(response, AppInitConstants.HttpCode.HTTP_SUCCESS, "保存成功~");
    }

    /**
     * 前端查询商品出价记录
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxDataList", method = RequestMethod.POST)
    public void ajaxDataList(BidModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
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
    public void dataList(BidModel model, HttpServletResponse response) throws Exception {
        queryDataList(model, response);
    }

    private void queryDataList(BidModel model, HttpServletResponse response) throws Exception {
        List<BidBean> dataList = bidService.queryByList(model);
        // 设置页面数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
}
