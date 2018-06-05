$(function(){

})

//立即提现
function submitRecharge() {
    $('#loadingToast').show();
    var bean = {};
    bean.streamtype = 5;
    bean.streammoney = $("#rechargeMoney").val();
    bean.wxid = localStorage.getItem("openId");
    var url = '/weixin/moneyStream/save.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(bean),
        dataType: 'JSON',
        contentType: "application/json;charset=utf-8",
        cache: false,
        success: function (data) {
            $('#loadingToast').hide();
            if (data.success == true) {
                showToast("操作成功", function () {
                    history.back(-1);
                });
                return;
            }
        }
    })
}