package cn.trustway.weixin.quartz;


import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.controller.TextMessageController;
import cn.trustway.weixin.model.FollowModel;
import cn.trustway.weixin.service.*;
import cn.trustway.weixin.util.http.AppClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 竞拍会定时任务
 * @author yexin
 *
 */
@Component
@Lazy(value=false)
public class AuctionTask {
    @Autowired
    AuctionService<Auction> auctionService;

    @Autowired
    AuctionItemService<AuctionItem> auctionItemService;

    @Autowired
    private WeixinUserService<WeixinUser> weixinUserService;

    @Autowired
    private TextMessageService<TextMessage> textMessageService;

    @Autowired(required = false)
    private MessageService<Message> messageService;

    @Autowired
    BidService<BidBean> bidService;

    @Autowired
    OrderService<Order> orderService;

    @Autowired
    OrderLogService<OrderLog> orderLogService;

    @Autowired(required = false)
    private FollowService<Follow> followService;

    /**
     * 竞拍会状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    @Transactional
    public void auctionStatusTask() throws Exception {
        // 找到即将开始的拍卖会
        List<Integer> idList = auctionService.queryByAuctionStartList();
        // TODO发送站内通知
        if(null != idList && idList.size() > 0){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",idList);
            List<Follow> followList = followService.queryToAuctionUserByList(map);
            if(null != followList && followList.size() > 0){
                for(Follow follow : followList){
                    if(StringUtils.isNotEmpty(follow.getPhoneNum())){
                        if(null != follow.getStartTime() && StringUtils.isNotEmpty(follow.getStartTime().toString())){
                            // 发送短信  找到买家
                            String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(follow.getStartTime()).toString().split("-");
                            String context = "您在百姓收藏关注的展览已于"+ Integer.parseInt(strNow[0]) + "年" +  Integer.parseInt(strNow[1]) + "月" +
                                    Integer.parseInt(strNow[2]) + "日开拍，请及时参与。";
                            AppClient.sendChuanglanMessage(context, follow.getPhoneNum());
                            TextMessage bean = new TextMessage();
                            bean.setContent(context);
                            bean.setPhoneNum(follow.getPhoneNum());
                            bean.setType(TextMessageController.MESSAGE_TYPE_NOTIFY);
                            textMessageService.add(bean);

                            //保存一条站内通知
                            Message message = new Message();
                            message.setWxid("0");
                            message.setToWxid(follow.getWxid());
                            message.setMessagenote("您有关注的拍卖会即将开始，请及时参加");
                            message.setMessagetype(1);
                            message.setStatus(0);
                            messageService.add(message);
                            //当新建聊天时触发
                            message.setParentId(message.getId());
                            messageService.updateBySelective(message);
                        }
                    }
                }
            }
        }
        // 设置开始时间超出当前时间的正常的竞拍会为已开始
        auctionService.updateAuctionStart();

        // 设置结束时间超出当前时间的正在竞拍的竞拍会为已结束
        auctionService.updateAuctionEnd();
    }

    /**
     * 竞拍品状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    @Transactional
    public void auctionItemStatusTask() throws Exception {
        // 设置开始时间超出当前时间的正常的竞拍品为正在竞拍
        auctionItemService.updateAuctionItemStart();

        // 查询结束时间超出当前时间的正在竞拍的竞拍品
        List<AuctionItem> auctionItemList = auctionItemService.queryInAuctionByList();
        // 根据竞拍品的竞拍情况设置对应状态
        for(AuctionItem auctionItem : auctionItemList) {
            // 查询竞拍品的最高出价
            BidBean bidBean = bidService.queryBidByItemId(auctionItem.getId());
            if(null != bidBean) {
                // 有人出价
                // 生成订单
                Date currentTime = new Date();
                Order order = new Order();
                order.setOrderType("1");
                order.setOrderMoney(bidBean.getBidPrice());
                order.setWxid(bidBean.getWxid());
                order.setItemId(bidBean.getAuctionItemId());
                order.setCreateTime(currentTime);
                // 加入买家地址
                if(StringUtils.isNotEmpty(bidBean.getAddressId())){
                    order.setAddressId(bidBean.getAddressId());
                }
                orderService.add(order);
                // 写入订单日志
                Order newOrder = orderService.queryById(order.getId());
                OrderLog orderLog = new OrderLog();
                BeanUtils.copyProperties(newOrder, orderLog);
                orderLog.setOrderId(newOrder.getId());
                orderLog.setCreateTime(newOrder.getCreateTime());
                orderLog.setStatus(newOrder.getStatus());
                orderLogService.add(orderLog);

                // 设置竞拍品为竞拍成功并设置成交价格
                auctionItem.setAuctionStatus("2");
                auctionItem.setFinalPrice(bidBean.getBidPrice());
                if(auctionItem.getStock() > 0) {
                    auctionItem.setStock(auctionItem.getStock() - 1);
                }
                auctionItemService.updateBySelective(auctionItem);

                // 发送短信  找到买家
                WeixinUser payMan = weixinUserService.queryWeixinUser(bidBean.getWxid());  //买家
                TextMessage bean = new TextMessage();
                bean.setContent("【百姓收藏】您的出价已经成拍");
                bean.setPhoneNum(payMan.getPhoneNum());
                bean.setType(TextMessageController.MESSAGE_TYPE_NOTIFY);
                textMessageService.add(bean);
                AppClient.sendChuanglanMessage("【百姓收藏】您的出价已经成拍", payMan.getPhoneNum());

                //保存一条站内通知
                Message message = new Message();
                message.setWxid("0");
                message.setToWxid(auctionItemService.queryById(order.getItemId()).getUploadWxid());
                message.setMessagenote("您有新的订单待处理");
                message.setMessagetype(1);
                message.setStatus(0);
                messageService.add(message);
                //当新建聊天时触发
                message.setParentId(message.getId());
                messageService.updateBySelective(message);


            } else {
                // 无人出价
                // 设置竞拍品为流拍
                auctionItem.setAuctionStatus("3");
                // 设置拍品下架
                auctionItem.setIsOnsale("0");
                auctionItemService.updateBySelective(auctionItem);
            }
        }

    }

    /**
     * 订单状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    @Transactional
    public void orderStatusTask() throws Exception {
        // 查询所有待支付且超过失效时间的订单
        List<Order> invalidOrders = orderService.queryInvalidOrderList();
        if(CollectionUtils.isNotEmpty(invalidOrders)) {
            for(Order order : invalidOrders) {
                // 订单包括的拍品库存+1
                if(null != order.getItemId() && 0 != order.getItemId()) {
                    AuctionItem auctionItem = auctionItemService.queryById(order.getItemId());
                    if(null != auctionItem) {
                        auctionItem.setStock(auctionItem.getStock() + 1);
                        auctionItemService.updateBySelective(auctionItem);
                    }
                }

                // 更新订单状态为1-失效
                order.setStatus("1");
                orderService.updateBySelective(order);

                // 写入订单日志
                OrderLog orderLog = new OrderLog();
                BeanUtils.copyProperties(order, orderLog);
                orderLog.setOrderId(order.getId());
                orderLogService.add(orderLog);
            }
        }
    }
}
