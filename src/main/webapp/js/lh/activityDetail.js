var itemImage = '../../../images/lh/list9.png';
var itemName = '';

$(function(){
    var id = getParam("id");
    loadActivityData(id);
    loadActivityJoinListData(id);

    $("#shareBtn").click(function () {
        if(itemImage !=  '') {
            var itemUrl = encodeURIComponent(itemImage);
            var itemText = encodeURIComponent(itemName);
            var itemId = getParam("id");
            var shareUrl = '/pages/share/shareItem?itemUrl=' + itemUrl + "&itemText=" + itemText + "&itemId=" + itemId;
            wx.miniProgram.navigateTo({ url: shareUrl });
        }
    });
    $("#joinOnBtn").click(function () {
        joinActivity(id);
    });

    //去报名列表
    $("#dataDiv").click(function () {
        window.location.href = '../../html/lh/activityJoinList.html?id='+id;
    });
})

//加载活动数据
function loadActivityData(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/activity/getId.do?id='+id+"&wxid="+localStorage.getItem("openId");
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
            itemName = dataObj.name;
            //处理活动基本信息
            $("#activityDescription").html(dataObj.description); //活动详情
            if(dataObj.isJoin == '1'){
                $("#joinOnBtn").html("已报名")
            }
        }
    })
}

//加载活动数据
function loadActivityJoinListData(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/activity/ajaxGetJoinList.do?id='+id+"&limit=3";
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            var str = '';
            $.each(dataList,function(i,obj){
                str+='     <img src="' +  obj.avatarUrl +  '" style="border-radius: 50%;width: 0.18rem;width: 0.18rem;"> ';
            });
            $("#dataListDiv").append(str);
        }
    })
}

//报名接口
function joinActivity(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/activity/ajaxJoinActivity.do?activityId='+id+"&wxid="+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            if(data.code == 10002){
                //不在有效范围
                alert("已结束")
            }else if(data.code == 0){
                var order = data.order;
                zhifu(order.id);
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
                var path = '/pages/wxpay/wxpayActivity' + params;
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