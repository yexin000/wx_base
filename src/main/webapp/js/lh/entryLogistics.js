//充值
function submitLogistics(){
    //充值金额
   var ydbh = $("#ydbh").val();
   var wlgs = $("#wlgs").val();
   if(!ydbh){
       $("#dialogLabel").html("请输入物流编号");
       $("#iosDialog2").show();
       return;
   }
   //后台需要创建订单并返回订单id
    var params = {};
    params.wxid = localStorage.getItem("openId");
    params.id = getParam("orderId");
    params.ydbh = ydbh;
    params.wlgs = wlgs;
    var url = "/weixin/order/updateOrderLogistics.do";
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(params) ,
        contentType: "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success: function (result) {
            showToast("录入成功", function () {
                history.back(-1);
            });
            return;
        }
    });
}
function closeTip(){
    $("#iosDialog2").hide();
}