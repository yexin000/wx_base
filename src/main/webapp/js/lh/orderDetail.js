$(function(){

    var id = getParam("id");
    loadOrderDetail(id);
    $("#payBtn").click(function(){
        var timestamp = new Date().getTime();
        var nonceStr = "1111aaaa";
        var prepay_id = "prepay_id123";
        var sign = "sign12345"
        var params = "?timestamp=" +timestamp+ "&nonceStr=" + nonceStr
            + "&prepay_id=" +prepay_id+ "&signType=MD5" + "&paySign=" + sign
            + "&orderId=123456789";
        var path = '/pages/wxpay/wxpay' + params;
        wx.miniProgram.navigateTo({ url: path });
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
                statuName = '订单已失效';
            }else if(order.status == '2'){
                statuName = '订单待支付';
            }else if(order.status == '3'){
                statuName = '订单已支付';
            }else if(order.status == '4'){
                statuName = '订单已发货';
            }else{
                //0
                statuName = '订单已删除';
            }
            $("#orderStatus").html(statuName);
        }
    })

}