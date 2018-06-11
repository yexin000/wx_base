$(function(){
    var id = getParam("id");
    var payFail = getParam("payFail");
    if(payFail){
        $("#msgLabel").html("支付失败");
        $("#msgDialog").show();
        //设置成失效订单 TODO
    }
    loadItemData(id);
    $("#bidBtn").click(function () {
        toPurchase();
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

})

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
                    str+='<li class="bannerItem">';
                    str+='	<a href="javaScipt:void(0)">';
                    str+=' <img src="' + hostPath + coverimg +  '" alt="">';
                    str+=' </a></li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
            $("#itemName").html(dataObj.name); //拍品名字
            $("#itemDescription").html(dataObj.description); //拍品详情
            $("#startprice").html(dataObj.startPrice); //起拍价格
            $("#depositprice").html(dataObj.depositPrice); //保证金
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


//购买
function toPurchase(){
    $('#loadingToast').show();
    var id = getParam("id");
    var url= '/weixin/auctionItem/purchase.do?id='+id+"&wxid="+localStorage.getItem('openId');
    $.ajax({
        url: url,
        type: 'post',
        data:{},
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            debugger
            $('#loadingToast').hide();
            if(data.success == false){
                $("#msgLabel").html(data.msg);
                $("#msgDialog").show();
            }else{
                //生成订单
                zhifu(data.data.id);
            }
        }
    })
}


function zhifu(orderId){
    var params = {};
    params.wxid = localStorage.getItem("openId");
    params.orderId = orderId;
    var url = "/weixin/wxPay/createUnifiedOrder.do?wxid="+params.wxid+"&orderId="+params.orderId;
    // 发起统一下单请求
    $.ajax({
        url: url,
        type: 'post',
        data: "",
        contentType: "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success: function (result) {
            if(result.code == "0") {
                var data = result.data;
                var timeStamp = data.timeStamp;
                var nonceStr = data.nonceStr;
                var package = data.package;
                var prepay_id = data.prepay_id;
                var paySign = data.paySign;
                var orderId = data.orderId;
                var itemId = data.itemId;
                var params = "?timeStamp=" +timeStamp+ "&nonceStr=" + nonceStr
                    + "&prepay_id="+prepay_id+"&paySign=" + paySign
                    + "&orderId=" + orderId  + "&itemId="+itemId;
                var path = '/pages/wxpay/wxpayRec' + params;
                wx.miniProgram.navigateTo({ url: path });
            } else {
                showToast("调用微信支付失败", function () {
                    history.back(-1);
                });
                return;
            }
        }
    });
}
