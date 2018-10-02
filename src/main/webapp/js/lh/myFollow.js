var itemImage = '../../../images/lh/list9.png';
var itemName = '';

$(function(){
    var id = getParam("id");
    //加载用户详情
    loadUser(id);
    //加载关注商品
    loadMyFollowListData();

    $("#joinOnBtn").click(function () {

    });

    //去报名列表
    $("#dataDiv").click(function () {
        window.location.href = '../../html/lh/activityJoinList.html?id='+id;
    });
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
            $("#user").html(data.nickName);
            $("#headImg").attr("src",   data.avatarUrl);
        }
    })
}

//加载数据
function loadMyFollowListData(){
    $('#loadingToast').show();
    var params = {};
    var url= '/weixin/follow/ajaxGetAllList.do?wxid='+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows1;
            debugger
            var str = '';
            $.each(dataList,function(i,obj){
                str+='     <img src="' +  obj.avatarUrl +  '" style="border-radius: 50%;width: 0.18rem;width: 0.18rem;"> ';
            });
            $("#dataListDiv").append(str);
        }
    })
}

