var itemImage = '../../../images/lh/list9.png';
var itemName = '';

$(function(){
    var id = getParam("id");
    //加载用户详情
    loadUser(id);
    //加载关注数据
    loadMyFollowListData();

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
            $("#headImg").attr("src", data.avatarUrl);
            $("#followUser").html( data.myFollowNum);
            $("#followAuction").html( data.myFollowAuctionNum);
            $("#followAuctionItem").html( data.myFollowAuctionItemNum);
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
            var dataList1 = data.rows1;
            var dataList2 = data.rows2;
            var dataList3 = data.rows3;
            var str1 = '';
            var str2 = '';
            var str3 = '';
            $.each(dataList1,function(i,obj){
                str1+='<div style="height: 0.6rem;width: 0.6rem;margin-top: 0.1rem;display: inline-block;margin-left: 0.1rem;" onclick="toUserInfo(\''+obj.followWxId+'\')">';
                //因为头像都是微信过来的，所以不需要拼地址
                str1+='       <img src="' +  obj.path +  '" style="width: 100%;">';
                str1+='   </div>';
            });

            $.each(dataList2,function(i,obj){
                str2+='<div style="height: 0.6rem;width: 0.6rem;margin-top: 0.1rem;display: inline-block;margin-left: 0.1rem;" onclick="toAuctionItemDetail('+obj.followId+','+obj.followAttribute+')">';
                str2+='       <img src="' +  hostPath + obj.path +  '" style="width: 100%;height: 0.6rem;width: 0.6rem;">';
                str2+='   </div>';
            });

            $.each(dataList3,function(i,obj){
                str3+='<div style="height: 0.6rem;width: 0.6rem;margin-top: 0.1rem;display: inline-block;margin-left: 0.1rem;" onclick="toAuctionDetail('+obj.followId+')">';
                str3+='       <img src="' +  hostPath + obj.path +  '" style="width: 100%;height: 0.6rem;width: 0.6rem;">';
                str3+='   </div>';
            });
            $("#dataListDiv1").append(str1);
            $("#dataListDiv2").append(str2);
            $("#dataListDiv3").append(str3);
        }
    })
}


$("#more1").click(function () {
    window.location.href = '../../html/lh/myFollowUser.html?';
});
$("#more2").click(function () {
    window.location.href = '../../html/lh/myFollowAuction.html';
});
$("#more3").click(function () {
    window.location.href = '../../html/lh/myFollowAuctionItem.html';
});