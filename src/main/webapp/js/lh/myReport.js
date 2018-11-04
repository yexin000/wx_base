$(function(){
    loadOrderDetail(id);
})


function loadOrderDetail(id){
    $('#loadingToast').show();
    var params = {};
    params.wxid = localStorage.getItem("openId");
    var url= '/weixin/order/ajaxGetReport.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(params) ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){

        }
    })
}
