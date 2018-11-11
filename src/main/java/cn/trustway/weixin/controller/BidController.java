package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.AuctionItem;
import cn.trustway.weixin.bean.BidBean;
import cn.trustway.weixin.bean.Blacklist;
import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.model.BidModel;
import cn.trustway.weixin.model.BlacklistModel;
import cn.trustway.weixin.service.AuctionItemService;
import cn.trustway.weixin.service.BidService;
import cn.trustway.weixin.service.BlacklistService;
import cn.trustway.weixin.service.WeixinUserService;
import cn.trustway.weixin.util.DateUtil;
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

    @Autowired
    private BlacklistService<Blacklist> blacklistService;

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
        WeixinUser user = weixinUserService.queryWeixinUser(wxid);
        if(StringUtils.isBlank(wxid) || null == user) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_URSER_ERROR, "出价失败，用户信息有误");
            return;
        }
        bean.setWxUserName(user.getNickName());
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

        BlacklistModel bModel = new BlacklistModel();
        bModel.setBlackid(wxid);
        bModel.setCreatorid(auctionItem.getUploadWxid());
        Integer blackCount = blacklistService.queryForegroundByCount(bModel);
        if(blackCount > 0) {
            sendFailure(response, AppInitConstants.HttpCode.HTTP_ITEM_ERROR, "出价失败，已被商家加入黑名单");
            return;
        }

        bean.setAuctionItemName(auctionItem.getAuctionName());
        //判断当前是否在竞拍时间中
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

        String beOveredUser = "";
        //比较当前商品出价是否符合规则
        // 商品已有用户出价
        if(CollectionUtils.isNotEmpty(dataList)) {
            // 最后出价
            BidBean lastBid = dataList.get(0);

            // 当前出价小于最后出价加最低加价，出价失败
            if(lastBid.getWxid().equals(bean.getWxid())) {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_PRICE_SAME_ERROR, "出价失败，您已是当前最高出价了");
                return;
            }

            // 当前出价小于最后出价加最低加价，出价失败
            if(bean.getBidPrice() < (lastBid.getBidPrice() + auctionItem.getAddPrice())) {
                sendFailure(response, AppInitConstants.HttpCode.HTTP_PRICE_MIN_ERROR, "出价失败，加价金额低于最低加价金额");
                return;
            }
            beOveredUser = lastBid.getWxid();
        }

        // 设置商品所有出价记录为出局
        bidService.updateBidOutByAuctionItemId(bean.getAuctionItemId());
        //插入出价数据
        bean.setStatus("1");
        bidService.add(bean);
        // 更新商品价格
        auctionItem.setCurPrice(bean.getBidPrice());
        auctionItemService.updateBySelective(auctionItem);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("beOveredUser", beOveredUser);
        jsonMap.put(CODE, AppInitConstants.HttpCode.HTTP_SUCCESS);
        HtmlUtil.writerJson(response, jsonMap);
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
    public void ajaxDataList(@RequestBody BidModel model, HttpServletResponse response) throws Exception {
        List<BidBean> dataList = bidService.queryByList(model);
        // 设置页面数据
        for(BidBean bb :dataList){
            String mo = DateUtil.convertDateToMonth(bb.getBidTime());
            String da = DateUtil.convertDateToDay(bb.getBidTime());
            String hh = DateUtil.convertDateToHour(bb.getBidTime());
            String dd = DateUtil.convertDateToMinute(bb.getBidTime());
            bb.setStrDate(mo+"."+da+" "+hh+"."+dd);
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
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
