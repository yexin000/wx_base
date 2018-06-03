$(function(){


})

//充值
function submitRecharge(){
    //充值金额
   var rechargeMoney = $("#rechargeMoney").val();
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
                var data = data.data;
                $("#user").html(data.nickName);
                $("#headImg").attr("src",   data.avatarUrl);
                $("#balance").html(data.balance);
            }
        })



}

