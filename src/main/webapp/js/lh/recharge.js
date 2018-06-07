$(function(){
})
//充值
function submitRecharge(){
    //充值金额
   var rechargeMoney = $("#rechargeMoney").val();
   if(rechargeMoney){
       var money = parseInt(rechargeMoney);
       if(money <100){
           //充值下限低于100
           $("#iosDialog2").show();
           return;
       }
   }
   //调用后台统一下单调起支付
        var url= '/weixin/order/ajaxGetId.do?wxid='+ localStorage.getItem("openId");
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(rechargeMoney) ,
            dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
            cache: false,
            success:function(data){
            }
        })
}

function closeTip(){
    $("#iosDialog2").hide();
}