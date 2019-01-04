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