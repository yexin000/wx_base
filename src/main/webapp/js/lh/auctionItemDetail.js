$(function(){
    var id = getParam("id");
    loadItemData(id);
    loadAuctionItemBid(id);
    $("#bidBtn").click(function () {
        $('#bidDialog').show();
    });

    $("#favBtn").click(function () {
        $('#loadingToast').show();
        var favStatus = $("#favStatus").val();
        if(favStatus == "0") {
            var params={}
            params.wxid=localStorage.getItem("openId");
            params.favType="1";
            params.favId=getParam("id");
            $.post("/weixin/favorite/ajaxAddFavorite.do",params,function(data){
                $('#loadingToast').hide();
                showToast(data.msg, function () {
                });
                $("#favBtn").html("取消收藏");
                $("#favStatus").val("1");
            });
        } else {
            // 取消收藏
            var params={};
            params.favId=getParam("id");
            params.wxid=localStorage.getItem("openId");
            $.post("/weixin/favorite/ajaxDelFavorite.do",params,function(data){
                $('#loadingToast').hide();
                showToast(data.msg, function () {
                });
                $("#favBtn").html("+收藏");
                $("#favStatus").val("0");
            });
        }

    });

    $("#shareBtn").click(function () {
        if(itemImage !=  '') {
            var itemUrl = encodeURIComponent(itemImage);
            var itemText = encodeURIComponent(itemName);
            var itemId = getParam("id");
            var shareUrl = '/pages/share/shareItem?itemUrl=' + itemUrl + "&itemText=" + itemText + "&itemId=" + itemId;
            wx.miniProgram.navigateTo({ url: shareUrl });
        }
    });

    // 每45秒加载一次出价
    setInterval(function () {
        loadAuctionItemBid(id);
    }, 45000);
})

var itemImage = '';
var itemName = '';

//加载商品数据
function loadItemData(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/auctionItem/ajaxGetId.do?id='+id+"&wxid="+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataObj = data.data;
            //先处理商品轮播
            var dataList = dataObj.resList;
            if(dataList.length> 0) {
                var str = '';
                $.each(dataList,function(i,obj){
                    var coverimg = obj.path;
                    itemImage = coverimg;
                    str+='<li class="bannerItem">';
                    str+='	<a href="javaScipt:void(0)">';
                    str+=' <img src="' + hostPath + coverimg +  '" alt="">';
                    str+=' </a></li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
            //处理商品基本信息
            if(dataObj.auctionStatus == '0') {
                $("#timeLabel").html("即将开始 " + dataObj.startTime);
                $("#bidBtn").css("background", "#d5d5d6");
                $("#bidBtn").unbind();
            } else if(dataObj.auctionStatus == '1') {
                $("#timeLabel").html("正在竞拍:  " + dataObj.startTime + "至" + dataObj.endTime);
            } else {
                $("#timeLabel").html("竞拍已结束");
                $("#bidBtn").css("background", "#d5d5d6");
                $("#bidBtn").unbind();
            }

            $("#itemName").html(dataObj.name); //拍品名字
            itemName = dataObj.name;
            $("#itemDescription").html(dataObj.description); //拍品详情
            $("#startprice").html(dataObj.startPrice); //起拍价格
            $("#curprice").html(dataObj.curPrice); //当前价格
            $("#addprice").html(dataObj.addPrice); //加价最低价格
            $("#rate").html(dataObj.rate); //手续费比率
            $("#depositprice").html(dataObj.depositPrice); //保证金
            $("#wanfenbi").html('('+dataObj.wanfenbi+"万元)"); //保证金
            $("#standardLabel").html(dataObj.standard); //规格
            $("#ageLabel").html(dataObj.age); //年代
            $("#degreeLabel").html(dataObj.degree); //等级
            var details = dataObj.detail.split("\n");
            var detail = "";
            for(var i = 0; i < details.length; i ++) {
                detail = detail + details[i] + "<br/>";
            }
            $("#detailLabel").html(detail); //详情

            if(dataObj.isFavorite == "1") {
                $("#favBtn").html("取消收藏");
            } else {
                $("#favBtn").html("+收藏");
            }
            $("#favStatus").val(dataObj.isFavorite);
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
            $("#bidDataDiv").empty();
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
                    var label = '出局';
                    if(i == 0){
                        $("#curprice").html(obj.bidPrice);
                        label = '领先';
                        isFirst = 'bidFirst';
                    }
                    str+='  <span class="'+isFirst+'">' + label + '</span> <span class="biddiv2span">' +obj.strDate+'</span> <span class="biddiv2span">'+ obj.bidPrice +'</span>';
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
    var bidMoney = $("#bidMoney").val();
    if(bidMoney == null) {
        showToast("请输入竞拍金额", function () {
        });
        return;
    }
    if(bidMoney <= 0) {
        showToast("请输入正确的金额", function () {
        });
        return;
    }
    $('#bidDialog').hide();
    $('#loadingToast').show();
    var id = getParam("id");
    var BidBean = {};
    BidBean.wxid = localStorage.getItem('openId');
    BidBean.auctionItemId = id;
    BidBean.bidPrice = bidMoney;
    var url= '/weixin/bid/ajaxAddBid.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(BidBean),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var code = data.code;
            if(code == 0){
                var beOveredUser = data.beOveredUser;
                var path = '/pages/bid/bid?bidMoney='+bidMoney+"&itemName="+$("#itemName").html()+"&beOveredUser="+beOveredUser;
                loadAuctionItemBid(id);
                wx.miniProgram.navigateTo({ url: path });
            }else{
                $("#msgLabel").html(data.msg);
                $("#msgDialog").show();
            }
        }
    })
}

function goBack() {
    var lastUrl = document.referrer;
    if(lastUrl.indexOf("index.html") >= 0 && lastUrl.indexOf("itemId") >= 0) {
        window.location.href = "../../html/lh/index.html";
    } else {
        history.go(-1);
    }
}


