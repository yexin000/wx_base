$(function(){
    var id = getParam("id");

    loadItemData(id);
    $("#bidBtn").click(function () {
        toPurchase();
    });

    $("#favBtn").click(function () {
        $('#loadingToast').show();
        var favStatus = $("#favStatus").val();
        if(favStatus == "0") {
            favorite();
        } else {
            cancleFavorite();
        }

    });

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
                    if(i == 0) {
                        itemImage = coverimg;
                    }
                    str+='<li class="bannerItem" onclick="showImg('+i+')">';
                    str+='<input type="hidden" id="imgSrc'+i+'" value="' + hostPath + coverimg +  '">';
                    str+='	<a href="javaScipt:void(0)">';
                    str+=' <img src="' + hostPath + coverimg +  '" alt="" >';
                    str+=' </a></li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
            $("#itemName").html(dataObj.name); //拍品名字
            itemName = dataObj.name;
            $("#itemDescription").html(dataObj.description); //拍品详情
            $("#startprice").html(dataObj.startPrice); //起拍价格
            $("#depositprice").html(dataObj.depositPrice); //保证金
            var details = "";
            if(dataObj.detail != null && dataObj.detail != '') {
                details = dataObj.detail.split("\n");
            }
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


//收藏
function favorite(){
    $('#loadingToast').show();
    var params={}
    params.wxid=localStorage.getItem("openId");
    params.favType="1";
    params.favId=getParam("id");
    var url= '/weixin/favorite/ajaxAddFavorite.do';
    $.post(url,params,function(data){
        $('#loadingToast').hide();
        showToast(data.msg, function () {
        });
        $("#favBtn").html("取消收藏");
        $("#favStatus").val("1");
    });
}

//取消收藏
function cancleFavorite(){
    $('#loadingToast').show();
    var params={};
    params.favId=getParam("id");
    params.wxid=localStorage.getItem("openId");
    var url= '/weixin/favorite/ajaxDelFavorite.do';
    $.post(url,params,function(data){
        $('#loadingToast').hide();
        showToast(data.msg, function () {
        });
        $("#favBtn").html("+收藏");
        $("#favStatus").val("0");
    });
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
            $('#loadingToast').hide();
            if(data.success == false){
                $("#msgLabel").html(data.msg);
                $("#msgDialog").show();
            }else{
                //生成订单
                //跳转订单详情列表
                toOrderDetail(data.data.id);
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
                var orderId = data.orderId;showImg
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


function showImg(i){
    var src = $("#imgSrc"+i).val();
    $("#footDiv").css("z-index","0");
    var str = '';
    str += '<div class="page gallery js_show " id="imgDiv">'
    str +=    '<div class="page__hd">'
    str +=    '<h1 class="page__title">Gallery</h1>'
    str +=     '<p class="page__desc">画廊，可实现上传图片的展示或幻灯片播放</p>'
    str += '</div>'
    str += '<div class="weui-gallery" style="display: block">'
    str +=    '<span class="weui-gallery__img" id="imageSpan" style="background-image: url('+src+');"></span>'
    str +=     '<div class="weui-gallery__opr">'
    str +=     '<a href="javascript:" class="weui-gallery__del">'
    str +=     '<i class="weui-icon-cancel weui-icon_gallery-cancel" onclick="closeImg()"></i>'
    str +=     '</a>'
    str +=     '</div>'
    str +=     '</div>'
    str +=    '</div>'
    $('body').append(str);
}

function closeImg(){
    $("#imgDiv").remove();
}

$("#shareBtn").click(function () {
    if(itemImage !=  '') {
        var itemUrl = encodeURIComponent(itemImage);
        var itemText = encodeURIComponent(itemName);
        var itemId = getParam("id");
        var shareUrl = '/pages/share/shareItem?itemUrl=' + itemUrl + "&itemText=" + itemText + "&itemId=" + itemId;
        wx.miniProgram.navigateTo({ url: shareUrl });
    }
});