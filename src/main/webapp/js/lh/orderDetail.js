var id = getParam("id");
var isMyUpload = getParam("isMyUpload");
$(function(){
  loadOrderDetail(id);
  $("#orderId").val(id);
  $("#payBtn").click(function(){
    if($("#hasAddress").is(":hidden"))
    {
      //没有收货地址
      $("#iosDialog1").show();
      return;
    }
    //先判断余额支付，余额不足时调用微信支付
    myMoneyPay(id);
  })
})

function myMoneyPay (id){
  var params = {};
  params.wxid = localStorage.getItem("openId");
  params.orderId = id;
  var url = "/weixin/wxPay/myMoneyPay.do?orderId="+params.orderId;
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
        showToast("余额支付成功", function () {
          history.back(-1);
        });
        return;
      } else {
        wxPay(id);
      }
    }
  });
}


function wxPay (id){
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
}

function loadOrderDetail(id){
  $('#loadingToast').show();
  var params = {};
  params.id = id;
  params.wxid = localStorage.getItem("openId");
  var url= '/weixin/order/ajaxGetId.do';
  $.ajax({
    url: url,
    type: 'post',
    data: JSON.stringify(params) ,
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
      if(addr){
        $("#notHasAddress").hide();
        $("#hasAddress").show();
        $("#receiver").html(addr.receiver);
        $(".fr").html(addr.phoneNum);
        $("#addressDetail").html(addr.address);
      }else{
        $("#hasAddress").hide();
        $("#notHasAddress").show();
      }


      var auction = data.auctionItem;
      $("#itemName").val(auction.auctionName);
      $("#itemImg").attr("src" , hostPath + auction.resList[0].path);
      $("#description").html(auction.description);
      $('#commodityDetail').bind("click", function(){
        toAuctionItemDetail(auction.id,auction.attribute);
      }) ;

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
        $("#payBtn").unbind();
        if(isMyUpload == 1){
          $("#luruBtn").show();
          $("#goLogisticsBtn").show();
        } else {
          // 买家申请退货
          if(order.wxid == localStorage.getItem("openId")){
            $("#tuihuoBtn").show();
          }
        }
        statuName = '订单已支付';
      }else if(order.status == '4'){
        $("#payBtn").css("background", "#d5d5d6");
        $("#payBtn").unbind();
        if(isMyUpload == 1){
          $("#luruBtn").show();
          $("#goLogisticsBtn").show();
        } else {
          // 买家申请退货
          if(order.wxid == localStorage.getItem("openId")){
            $("#tuihuoBtn").show();
          }
        }
        statuName = '订单已发货';

        $("#goLogisticsBtn").show();

        if(isMyUpload == 1){
          $("#luruBtn").show();
          $("#goLogisticsBtn").show();
          $("#payBtn").text("等待确认");
          $("#payBtn").css("background", "#d5d5d6");
          $("#payBtn").unbind();
        }else{
          if(order.wxid == localStorage.getItem("openId")){
            //订单是我创建的
            $("#payBtn").text("确认订单");
            $("#payBtn").css("background", "#ff7d13");
            $("#payBtn").bind("click",function(){
              //进入确认订单逻辑
              confirmationOfOrder(order.id);
            });
          }else{
            $("#luruBtn").show();
            $("#goLogisticsBtn").show();
            $("#payBtn").text("等待确认");
            $("#payBtn").css("background", "#d5d5d6");
            $("#payBtn").unbind();
          }
        }
      }else if(order.status == '5'){
        $("#payBtn").text("已完成");
        $("#payBtn").css("background", "#d5d5d6");
        $("#payBtn").unbind()
        statuName = '订单已完成';
      }else if(order.status == '6'){
        $("#payBtn").css("background", "#d5d5d6");
        $("#payBtn").unbind();
        $("#refundShow").text("申请退货退款中,原因:" + order.reason);
        $("#refundShow").show();
        statuName = '申请退货退款中';

        var refundBean = {};
        refundBean.wxid = localStorage.getItem('openId');
        refundBean.orderId = id;
        var url= '/weixin/order/ajaxRefundConfirm.do';
        if(order.wxid == localStorage.getItem("openId")){
          //订单是我创建的
          $("#refundBtn").text("已收到款");
          $("#refundBtn").click(function () {
            // 买家调用后台修改确认收到款状态
            refundBean.buyerStatus = "1";
            $("#refundBtn").click(function () {
              $('#loadingToast').show();
              $.ajax({
                url: url,
                type: 'post',
                data:JSON.stringify(refundBean),
                contentType : "application/json;charset=utf-8",
                dataType: 'JSON',
                cache: false,
                success:function(data){
                  $('#loadingToast').hide();
                  var code = data.code;
                  if(code == 0) {
                    $("#refundBtn").css("background", "#d5d5d6");
                    $("#refundBtn").unbind();
                    loadOrderDetail(id);
                  }
                  $("#msgLabel").html(data.msg);
                  $("#msgDialog").show();
                }
              })
            });
          });
          if(order.buyerStatus == '1') {
            $("#refundBtn").css("background", "#d5d5d6");
            $("#refundBtn").unbind();
          }
        } else {
          $("#refundBtn").text("已收到货");
          $("#refundBtn").click(function () {
            // 卖家调用后台修改确认收到货状态
            refundBean.sellerStatus = "1";
            $("#refundBtn").click(function () {
              $('#loadingToast').show();
              $.ajax({
                url: url,
                type: 'post',
                data:JSON.stringify(refundBean),
                contentType : "application/json;charset=utf-8",
                dataType: 'JSON',
                cache: false,
                success:function(data){
                  $('#loadingToast').hide();
                  var code = data.code;
                  if(code == 0) {
                    $("#refundBtn").css("background", "#d5d5d6");
                    $("#refundBtn").unbind();
                    loadOrderDetail(id);
                  }
                  $("#msgLabel").html(data.msg);
                  $("#msgDialog").show();
                }
              })
            });
          });
          if(order.sellerStatus == '1') {
            $("#refundBtn").css("background", "#d5d5d6");
            $("#refundBtn").unbind();
          }
        }
        $("#refundBtn").show();
      }else if(order.status == '7'){
        $("#payBtn").css("background", "#d5d5d6");
        $("#payBtn").unbind()
        statuName = '已退货退款';
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


function confirmationOfOrder (id){
  var params = {};
  params.wxid = localStorage.getItem("openId");
  params.id = id;
  var url = "/weixin/order/confirmationOfOrder.do";
  $.ajax({
    url: url,
    type: 'post',
    data: JSON.stringify(params) ,
    contentType: "application/json;charset=utf-8",
    dataType: 'JSON',
    cache: false,
    success: function (result) {
      if(result.code == "0") {
        showToast("交易完成", function () {
          history.back(-1);
        });
        return;
      } else {
        showToast("交易失败", function () {
          history.back(-1);
        });
        return;
      }
    }
  });
}


//新增地址
function addAddress(){
  window.location.href = '../../html/lh/addressDetail.html?orderId='+$("#orderId").val();
}

function cancelAddAddress(){
  $("#iosDialog1").hide();
}

function openAddLogistics(){
  window.location.href = '../../html/lh/entryLogistics.html?orderId='+$("#orderId").val();
}

function openLogistics(){
  $("#luruBtn").show();
  window.location.href = '../../html/lh/logistics.html?orderId='+$("#orderId").val();
}

function refundGoods() {
  $("#refundTip").show();
}

function cancelRefund() {
  $("#refundTip").hide();
}

function confirmRefund() {
  var refundReason = $("#refundInput").val();
  if(refundReason != null && refundReason != "") {
    // 请求退货申请后台
    var id = getParam("id");
    var refundBean = {};
    refundBean.wxid = localStorage.getItem('openId');
    refundBean.orderId = id;
    refundBean.reason = refundReason;
    var url= '/weixin/order/ajaxRefundOrder.do';
    $("#refundTip").hide();
    $('#loadingToast').show();
    $.ajax({
      url: url,
      type: 'post',
      data:JSON.stringify(refundBean),
      contentType : "application/json;charset=utf-8",
      dataType: 'JSON',
      cache: false,
      success:function(data){
        $('#loadingToast').hide();
        var code = data.code;
        if(code == 0) {
          $("#tuihuoBtn").hide();
          $("#refundShow").text("申请退货退款中,原因:" + refundReason);
          $("#refundShow").show();
          loadOrderDetail(id);
        }
        $("#msgLabel").html(data.msg);
        $("#msgDialog").show();
      }
    });
  } else {
    showToast("请输入退货原因", function () {
    });
  }
}