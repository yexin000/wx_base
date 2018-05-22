
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
    var id = getParam("id");
    loadItemData(id);
    loadAuctionItemBid(id);
})

//加载商品数据
function loadItemData(id){
    var url= '/weixin/auctionItem/ajaxGetId.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(id),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataObj = data.data;
            //先处理商品轮播
            var dataList = dataObj.resList;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<li class="bannerItem">';
                    str+='	<a href="javaScipt:void(0)">';
                    str+=' <img src="../../../images/lh/wshop_indexbanner1.jpg" alt="">';
                    str+=' </a></li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
            //处理商品基本信息
            $("#itemName").html(dataObj.name); //拍品名字
            $("#startprice").html(dataObj.startPrice); //起拍价格
            $("#curprice").html(dataObj.curPrice); //当前价格
            $("#addprice").html(dataObj.addPrice); //加价最低价格
            $("#rate").html(dataObj.rate); //手续费比率
            $("#depositprice").html(dataObj.depositPrice); //保证金
        }
    })
}


//加载出价列表数据
function loadAuctionItemBid(id){
    var model = {};
    model.auctionItemId = id;
    var url= '/weixin/bid/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(model) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<div class="bid">';
                    str+='	<div class="biddiv1">';
                    str+=' 		<img src="'  + obj.avatarUrl + '" class="bidimg">';
                    str+='	      <span class="biddiv1span">'+ obj.wxUserName+'</span>';
                    str+='  </div>';
                    str+='  <div  class="biddiv2">';
                    var isFirst = '';
                    if(i == 0){
                        isFirst = 'bidFirst';
                    }
                    str+='  <span class="'+isFirst+'">领先</span> <span class="biddiv2span">' +obj.strDate+'</span> <span class="biddiv2span">'+ obj.bidPrice +'</span>';
                    str+='  </div>';
                    str+='</div>';
                });
            }
            $("#bidDataDiv").append(str);
        }
    })
}



//出价
function toBid(){
    var id = getParam("id");
    var BidBean = {};
    BidBean.wxid = 'oNVx35HXrCsOVp-fXPhdCJUlWLeI';
    BidBean.wxUserName = '帅气的佳哥';
    BidBean.auctionItemId = id;
    BidBean.auctionItemName = 'dasdasd';
    BidBean.bidPrice = 600;
    var url= '/weixin/bid/ajaxAddBid.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(BidBean),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var code = data.code;
            if(code == 0){
                alert("竞拍成功");
            }else{
                alert("竞拍失败");
            }
        }
    })
}


