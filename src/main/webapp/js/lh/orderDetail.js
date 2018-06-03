$(function(){

    var id = getParam("id");
    loadOrderDetail(id);
    $("#payBtn").click(function(){
        var params = {};
        params.wxid = localStorage.getItem("openId");
        params.orderId = id;
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
                    var path = '/pages/wxpay/wxpay' + params;
                    wx.miniProgram.navigateTo({ url: path });
                } else {
                    showToast("调用微信支付失败", function () {
                        history.back(-1);
                    });
                    return;
                }
            }
        });
    })
})

function loadOrderDetail(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/order/ajaxGetId.do?id='+id;
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            if(data.code == '10005') {
                showToast("商品不存在", function () {
                    history.back(-1);
                });
                return;
            }
            $('#loadingToast').hide();
            var addr = data.useraddr;
            $("#receiver").val(addr.receiver);
            $(".fr").html(addr.phoneNum);
            $("#addressDetail").val(addr.address);

            var auction = data.auctionItem;
            $("#itemName").val(auction.auctionName);
            $("#itemImg").attr("src" , hostPath + auction.resList[0].path);
            $("#description").html(auction.description);

            var order =  data.data;
            $("#orderMoney").html(order.orderMoney);
            $("#orderMoneys").html(order.orderMoney);
            $("#businessName").html(order.businessName);

            var statuName = '';
            if(order.status == '1'){
                $("#payBtn").text("已失效");
                $("#payBtn").css("background", "#d5d5d6");
                $("#payBtn").unbind()
                statuName = '订单已失效';
            }else if(order.status == '2'){
                statuName = '订单待支付';
            }else if(order.status == '3'){
                $("#payBtn").text("已支付");
                $("#payBtn").css("background", "#d5d5d6");
                $("#payBtn").unbind()
                statuName = '订单已支付';
            }else if(order.status == '4'){
                $("#payBtn").text("已支付");
                $("#payBtn").css("background", "#d5d5d6");
                $("#payBtn").unbind()
                statuName = '订单已发货';
            }else{
                $("#payBtn").text("已删除");
                $("#payBtn").css("background", "#d5d5d6");
                $("#payBtn").unbind()
                statuName = '订单已删除';
            }
            $("#orderStatus").html(statuName);
        }
    })

}