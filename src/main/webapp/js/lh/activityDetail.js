$(function(){
    var id = getParam("id");
    loadActivityData(id);
    $("#shareBtn").click(function () {
        if(itemImage !=  '') {
            var itemUrl = encodeURIComponent(itemImage);
            var itemText = encodeURIComponent(itemName);
            var itemId = getParam("id");
            var shareUrl = '/pages/share/shareItem?itemUrl=' + itemUrl + "&itemText=" + itemText + "&itemId=" + itemId;
            wx.miniProgram.navigateTo({ url: shareUrl });
        }
    });

})

//加载活动数据
function loadActivityData(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/activity/getId.do?id='+id+"&wxid="+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataObj = data.data;
            //处理活动基本信息
            $("#activityDescription").html(dataObj.describe); //拍品详情
            if(dataObj.isJoin == '1'){
                $("#joinOnBtn").html("已报名")
            }
        }
    })
}



function goBack() {
        history.go(-1);
}
