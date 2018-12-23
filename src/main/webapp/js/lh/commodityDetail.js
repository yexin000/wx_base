var id = getParam("id");
$(function(){
    var type = getParam("type");
    var url= '/weixin/wxAuth/ajaxGetId.do?wxid='+ localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var data = data.data;
            if(type == 3){
                if(data.vipGrade != 5 ){
                    $("#msgLabel2").html("您的VIP等级不足，无法查看详情");
                    $("#msgDialog2").show();
                }
            }
            $("#phoneNum").val(data.phoneNum)

            loadItemData(id);
            $("#bidBtn").click(function () {
                toPurchase();
            });
            $("#bidBtnV5").click(function () {
                toV5Purchase();
            });
            $("#js-board").click(function () {
                toAuctionItemV5Board(id);
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

        }
    })


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
                    var coverimgWidth  = obj.width;
                    var coverimgHeight = obj.height;
                    if(i == 0) {
                        itemImage = coverimg;
                    }
                    str+='<li class="bannerItem banner-bg-dark"  onclick="showImg('+i+')">';
                    str+='<input type="hidden" id="imgSrc'+i+'" value="' + hostPath + coverimg +  '">';
                    str+='	    <div style="position: relative; width: 3.2rem;height: 1.75rem;"><a>';
                    var loadClass = '';
                    if(parseInt(coverimgWidth) > parseInt(coverimgHeight * 1.8)){
                        loadClass = 'width';
                    }else{
                        loadClass = 'length';
                    }
                    str+='          <img src="' + hostPath + coverimg +  '" alt="" class="'+loadClass+' userImg" >';
                    str+='      </a></div> ';
                    str+='  </li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
            $("#itemName").html(dataObj.name); //拍品名字
            itemName = dataObj.name;
            $("#itemDescription").html(dataObj.description); //拍品详情
            $("#startprice").html(dataObj.startPrice); //起拍价格
            $("#depositprice").html(dataObj.depositPrice); //保证金
            $("#wanfenbi").html('('+dataObj.wanfenbi+"万元)"); //万分比
            var details = "";
            if(dataObj.detail != null && dataObj.detail != '') {
                details = dataObj.detail.split("\n");
            }
            var detail = "";
            for(var i = 0; i < details.length; i ++) {
                detail = detail + details[i] + "<br/>";
            }
            $("#detailLabel").html(detail); //详情

            if(dataObj.attribute == "2") {
              $("#bidBtn").css("background", "rgb(213, 213, 214)")
              $("#bidBtn").unbind();
            }

            if(dataObj.isFavorite == "1") {
                $("#favBtn").html("取消收藏");
            } else {
                $("#favBtn").html("收藏");
            }
            $("#favStatus").val(dataObj.isFavorite);

          if(dataObj.uploadWxid != null && dataObj.uploadWxid != "0") {
            $("#storeBtn").click(function () {
              window.location.href = "../../html/lh/myStore.html?wxid=" + dataObj.uploadWxid;
            })
          }
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
        $("#favBtn").html("收藏");
        $("#favStatus").val("0");
    });
}


//购买
function toPurchase(){
    $('#loadingToast').show();
    var url= '/weixin/wxAuth/ajaxGetId.do?wxid='+ localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var data = data.data;
            if(!data.phoneNum) {
                $('#loadingToast').hide();
                $("#phoneDialog").show();
            }else{
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
        }
    })
}



//购买V5
function toPurchaseV5(){
    $('#loadingToast').show();
    var url= '/weixin/wxAuth/ajaxGetId.do?wxid='+ localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var data = data.data;
            debugger
            if(data.vipGrade && data.vipGrade == 5) {
                $("#msgLabel").html("已经满足条件");
                $("#msgDialog").show();
            }else{
                var url= '/weixin/auctionItem/purchaseV5.do?wxid='+localStorage.getItem('openId');
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
                            var order = data.data;
                            v5Zhifu(order.id);
                        }
                    }
                })
            }
        }
    })
}

function v5Zhifu(orderId){
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
                var amount = data.amount;
                var params = "?timeStamp=" +timeStamp+ "&nonceStr=" + nonceStr
                    + "&prepay_id="+prepay_id+"&paySign=" + paySign
                    + "&orderId=" + orderId + "&amount=" + amount;
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

//V5特区购买
function toV5Purchase(){
    toService();
    //显示关键信息

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


function showImgs(i){
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


/*调用微信预览图片的方法*/
function showImg(){
    var imgs = [];
    var imgObj = $(".userImg");//这里改成相应的对象
    for(var i=0; i<imgObj.length; i++){
        imgs.push(imgObj.eq(i).attr('src'));
        imgObj.eq(i).click(function(){
            var nowImgurl = $(this).attr('src');
            WeixinJSBridge.invoke("imagePreview",{
                "urls":imgs,
                "current":nowImgurl
            });
            WeixinJSBridge.call('closeWindow');
        });
    }

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


$("#estimatePrice").click(function () {
    window.location.href = '../../html/lh/estimatePrice.html?id='+id;
});



function goBack() {
    var lastUrl = document.referrer;
    if(lastUrl.indexOf("index.html") >= 0 && lastUrl.indexOf("itemId") >= 0) {
        window.location.href = "../../html/lh/index.html";
    } else {
        history.go(-1);
    }
}



