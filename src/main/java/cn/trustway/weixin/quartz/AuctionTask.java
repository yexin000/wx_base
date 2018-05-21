package cn.trustway.weixin.quartz;


import cn.trustway.weixin.bean.Auction;
import cn.trustway.weixin.bean.AuctionItem;
import cn.trustway.weixin.service.AuctionItemService;
import cn.trustway.weixin.service.AuctionService;
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

    /**
     * 拍卖会状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void auctionStatusTask() throws Exception {
        System.out.println("定时设置拍卖会状态开始");
        // 设置开始时间超出当前时间的正常的拍卖会为已开始
        auctionService.updateAuctionStart();

        // 设置结束时间超出当前时间的正在拍卖的拍卖会为已结束
        auctionService.updateAuctionEnd();
        System.out.println("定时设置拍卖会、拍卖品状态结束");
    }

    /**
     * 拍卖品状态监控定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void auctionItemStatusTask() throws Exception {
        System.out.println("定时设置拍卖品状态开始");
        // 设置开始时间超出当前时间的正常的拍卖品为正在拍卖
        auctionItemService.updateAuctionItemStart();

        // TODO 查询结束时间超出当前时间的正在拍卖的拍卖品，根据拍卖情况设置对应状态
        //List<AuctionItem> auctionItemList = auctionItemService
        // TODO 生成订单

        System.out.println("定时设置拍卖品状态结束");
    }
}
