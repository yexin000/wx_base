$(function(){
    loadOrderDetail();
})


function loadOrderDetail(){
    $('#loadingToast').show();
    var url= '/weixin/order/ajaxGetReport.do?wxid='+ localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            debugger
            if(data.data.collectionTodayMoney){
                $("#collectionTodayMoney").html(data.data.collectionTodayMoney)
            }else{
                $("#collectionTodayMoney").html(0)
            }

            if(data.data.collectionTodayNum){
                $("#collectionTodayNum").html(data.data.collectionTodayNum)
            }else{
                $("#collectionTodayNum").html(0)
            }

            if(data.data.filmingTodayMoney){
                $("#filmingTodayMoney").html(data.data.filmingTodayMoney)
            }else{
                $("#filmingTodayMoney").html(0)
            }

            if(data.data.filmingTodayNum){
                $("#filmingTodayNum").html(data.data.filmingTodayNum)
            }else{
                $("#filmingTodayNum").html(0)
            }

            if(data.data.paymentTodayMoney){
                $("#paymentTodayMoney").html(data.data.paymentTodayMoney)
            }else{
                $("#paymentTodayMoney").html(0)
            }

            if(data.data.paymentTodayNum){
                $("#paymentTodayNum").html(data.data.paymentTodayNum)
            }else{
                $("#paymentTodayNum").html(0)
            }
        }
    })
}
