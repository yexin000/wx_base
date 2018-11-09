
$(function(){

    //加载关注数据
    loadMyFollowListData();

})


//加载数据
function loadMyFollowListData(){
    $('#loadingToast').show();
    var url= '/weixin/follow/ajaxUserDataList.do?wxid='+localStorage.getItem("openId");
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
                str+='<div style="height:0.5rem;margin-left: 1%;  margin-top: 0.05rem;border-bottom: #cccccc solid 1px" onclick="toUserInfo()">';
                str+='   <div style=" width: 100%;padding-top: 0.05rem;">';
                str+='     <img src="' +  obj.path +  '" style="border-radius: 50%;height:0.4rem;width: 0.4rem;"> ';
                str+='     <span style="font-size: 0.2rem;position: absolute; margin-top: 0.05rem;margin-left: 0.1rem">'+obj.followName+'</span>';
                str+='   </div>';
                str+='</div>';
            });
            $("#dataDiv").append(str);
        }
    })
}

