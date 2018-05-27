package cn.trustway.weixin.quartz;


import cn.trustway.weixin.bean.*;
import cn.trustway.weixin.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 拍卖会定时任务
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
    BidService<BidBean> bidService;

    @Autowired
    OrderService<Order> orderService;

    @Autowired
    OrderLogService<OrderLog> orderLogService;

    /**
     * 拍卖会状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void auctionStatusTask() throws Exception {
        // 设置开始时间超出当前时间的正常的拍卖会为已开始
        auctionService.updateAuctionStart();

        // 设置结束时间超出当前时间的正在拍卖的拍卖会为已结束
        auctionService.updateAuctionEnd();
    }

    /**
     * 拍卖品状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void auctionItemStatusTask() throws Exception {
        // 设置开始时间超出当前时间的正常的拍卖品为正在拍卖
        auctionItemService.updateAuctionItemStart();

        // 查询结束时间超出当前时间的正在拍卖的拍卖品
        List<AuctionItem> auctionItemList = auctionItemService.queryInAuctionByList();
        // 根据拍卖品的竞拍情况设置对应状态
        for(AuctionItem auctionItem : auctionItemList) {
            // 查询拍卖品的最高出价
            BidBean bidBean = bidService.queryBidByItemId(auctionItem.getId());
            if(null != bidBean) {
                // 有人出价
                // 生成订单
                Order order = new Order();
                order.setOrderType("1");
                order.setOrderMoney(bidBean.getBidPrice());
                order.setWxid(bidBean.getWxid());
                order.setItemId(bidBean.getAuctionItemId());
                orderService.add(order);

                // 写入订单日志
                OrderLog orderLog = new OrderLog();
                BeanUtils.copyProperties(order, orderLog);
                orderLog.setOrderId(order.getId());
                orderLogService.add(orderLog);

                // 设置拍卖品为拍卖成功
                auctionItem.setAuctionStatus("2");
                auctionItemService.updateBySelective(auctionItem);
            } else {
                // 无人出价
                // 设置拍卖品为流拍
                auctionItem.setAuctionStatus("3");
                auctionItemService.updateBySelective(auctionItem);
            }
        }

    }

    /**
     * 拍卖品状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void orderStatusTask() throws Exception {
        // 查询所有待支付且超过失效时间的订单
        List<Order> invalidOrders = orderService.queryInvalidOrderList();
        if(CollectionUtils.isNotEmpty(invalidOrders)) {
            for(Order order : invalidOrders) {
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
