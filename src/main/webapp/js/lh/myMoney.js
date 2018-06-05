$(function(){
    loadUser();
})

//加载个人信息数据
function loadUser(){
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
            $("#myMoney").html(data.balance);
        }
    })
}

function toRecharge()
{
    window.location.href = '../../html/lh/recharge.html';
}

function toPutForward()
{
    window.location.href = '../../html/lh/putForward.html';
}

function toTradingCenter()
{
    window.location.href = '../../html/lh/tradingCenter.html';
}