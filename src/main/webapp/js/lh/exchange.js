$(function(){

    var id = getParam("id");
    loadExchange(id);
    $("#payBtn").click(function(){
        exchange(id);
    })
})


function loadExchange(id){
    $('#loadingToast').show();
    var params = {};
    params.wxid = localStorage.getItem("openId");
    var url= '/weixin/integralMall/getId.do?id='+id+"&wxid="+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var idata = data.data;
            $("#itemName").html(idata.name);
            $("#itemImg").attr("src" , hostPath + idata.resList[0].path);
            $("#description").html(idata.consumeintegral);
            //截至时间
            $("#endtime").html(idata.endtime);
        }
    })

}

function exchange (id){
    var url = '/weixin/integralMall/exchange.do?id='+id+"&wxid="+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: "",
        contentType: "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success: function (result) {
            if(result.success == true) {
                showToast("兑换成功", function () {
                    history.back(-1);
                });
                return;
            } else {
                showToast("兑换失败", function () {
                    history.back(-1);
                });
                return;
            }
        }
    });
}