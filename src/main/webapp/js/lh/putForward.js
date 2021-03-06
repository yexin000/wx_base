$(function(){

})

//立即提现
function submitRecharge() {

    var bean = {};
    bean.money = $("#rechargeMoney").val();
    bean.wxaccount = $("#whereabouts").val();
    bean.bankName = $("#bankName").val();
    bean.phone = $("#phone").val();
    bean.cardNum = $("#cardNum").val();
    bean.wxid = localStorage.getItem("openId");

    if(!$("#rechargeMoney").val() || !$("#bankName").val() || !$("#phone").val()|| !$("#cardNum").val()){
        $("#iosDialog3").show();
        return;
    }
    var money = parseInt($("#rechargeMoney").val());
    if(money <100){
        //充值下限低于100
        $("#ios").show();
        return;
    }

    $('#loadingToast').show();
    var url = '/weixin/putForward/save.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(bean),
        dataType: 'JSON',
        contentType: "application/json;charset=utf-8",
        cache: false,
        success: function (data) {
            $('#loadingToast').hide();
            if (data.status != "-1") {
                showToast("操作成功", function () {
                    history.back(-1);
                });
                return;
            }else{
                $("#iosDialog4").show();
            }
        }
    })
}

function closeTip(){
    $("#iosDialog2").hide();
    $("#iosDialog3").hide();
    $("#iosDialog4").hide();
    $("#ios").hide();
}