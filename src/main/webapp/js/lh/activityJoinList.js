$(function(){
    var id = getParam("id");
    loadActivityJoinData(id);
})

//加载活动数据
function loadActivityJoinData(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/activity/ajaxGetJoinList.do?id='+id;
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            var str = '';
            $.each(dataList,function(i,obj){
                str+='<div style="height:0.5rem;margin-left: 1%;  margin-top: 0.2rem;border-bottom: #cccccc solid 1px" onclick="toUserInfo(\'' + obj.wxid + '\')">';
                str+='   <div style=" width: 100%;padding-top: 0.05rem;">';
                str+='     <img src="' +  obj.avatarUrl +  '" style="border-radius: 50%;height:0.4rem;width: 0.4rem;"> ';
                str+='     <span style="font-size: 0.2rem;position: absolute; margin-top: 0.05rem;margin-left: 0.1rem">'+obj.name+'</span>';
                str+='   </div>';
                str+='</div>';
            });
            $("#dataDiv").append(str);
        }
    })
}
