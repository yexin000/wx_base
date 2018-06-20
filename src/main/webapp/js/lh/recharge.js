//充值
function submitRecharge(){
    //充值金额
   var rechargeMoney = $("#rechargeMoney").val();
   if(rechargeMoney){
       var money = parseInt(rechargeMoney);
       if(money <100){
           //充值下限低于100
           $("#dialogLabel").html("充值金额不能低于100");
           $("#iosDialog2").show();
           return;
       }
   } else {
       $("#dialogLabel").html("请输入充值金额");
       $("#iosDialog2").show();
       return;
   }
   //后台需要创建订单并返回订单id
    var params = {};
    params.wxid = localStorage.getItem("openId");
    params.orderMoney = '0.01';
    var url = "/weixin/order/rechargeOrder.do";
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(params) ,
        contentType: "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success: function (result) {
            var orderId = result.data.id;
            zhifu(orderId);
        }
    });
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
                var params = "?timeStamp=" +timeStamp+ "&nonceStr=" + nonceStr
                    + "&prepay_id="+prepay_id+"&paySign=" + paySign
                    + "&orderId=" + orderId;
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
function closeTip(){
    $("#iosDialog2").hide();
}