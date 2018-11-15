$("#footDiv").hide();
// 是否本人访问
var isSelf = true;
var wxid = getParam("wxid");
if(wxid == null || wxid == "" || wxid == localStorage.getItem("openId")) {
  // 本人访问
  wxid = localStorage.getItem("openId");
  $("#titleName").text("个人资料");
} else {
  // 他人访问
  isSelf = false;
}

$(function(){
  loadUser();
  $("#followBtn").click(function () {
    var isFollowed = $("#followStatus").val() == "1";
    $('#loadingToast').show();
    var followBean = {};
    followBean.followType = "1";
    followBean.wxid = localStorage.getItem("openId");
    followBean.followWxId = wxid;
    // 已关注的情况下取消关注
    if(isFollowed) {
      var url= '/weixin/follow/ajaxFollowDel.do';
      $.post(url,followBean,function(data){
        $('#loadingToast').hide();
        showToast(data.msg, function () {
        });
        $("#followBtn").html("关注");
        $("#followStatus").val("0");
        $("#followNum").html(parseInt($("#followNum").html()) - 1);
      });
    } else { // 未关注的情况下关注
      var url= '/weixin/follow/ajaxFollowSave.do';
      $.post(url,followBean,function(data){
        $('#loadingToast').hide();
        showToast(data.msg, function () {
        });
        $("#followBtn").html("取消关注");
        $("#followStatus").val("1");
        $("#followNum").html(parseInt($("#followNum").html()) + 1);
      });
    }
  });

  $("#blacklistBtn").click(function () {
    var isBlacklist = $("#blacklistStatus").val() == "1";
    $('#loadingToast').show();
    var blacklistBean = {};
    blacklistBean.type = "0";
    blacklistBean.creatorid = localStorage.getItem("openId");
    blacklistBean.blackid = wxid;
    // 已黑名单的情况下取消黑名单
    if(isBlacklist) {
      var url= '/weixin/blacklist/ajaxRemoveBlacklist.do';
      $.post(url,blacklistBean,function(data){
        $('#loadingToast').hide();
        showToast(data.msg, function () {
        });
        $("#blacklistBtn").html("加入黑名单");
        $("#blacklistStatus").val("0");
      });
    } else { // 未加入黑名单的情况下加入黑名单
      var url= '/weixin/blacklist/ajaxAddBlacklist.do';
      $.post(url,blacklistBean,function(data){
        $('#loadingToast').hide();
        showToast(data.msg, function () {
        });
        $("#blacklistBtn").html("取消黑名单");
        $("#blacklistStatus").val("1");
      });
    }
  })

    $("#messageBtn").click(function () {
      $('#loadingToast').show();
      // 查询对方将自己是否加入黑名单
      var url= '/weixin/follow/ajaxIsFollow.do?wxid='+wxid+'&followWxId='+localStorage.getItem("openId");
      $.post(url,{},function(data){
        $('#loadingToast').hide();

        if(data.isBlacklist == true) {
          showToast("已被对方加为黑名单", function () {
          });
        } else {
          toMessageDetail(0,getParam("wxid"));
        }

      });
    })
})

//加载个人信息数据
function loadUser(){
  $('#loadingToast').show();
  var url= '/weixin/wxAuth/ajaxGetId.do?wxid='+ wxid;
  $.ajax({
    url: url,
    type: 'post',
    data: {} ,
    dataType: 'JSON',
    contentType : "application/json;charset=utf-8",
    cache: false,
    success:function(data){
      $('#loadingToast').hide();
      var data = data.data;
      $("#user").html(data.nickName);
      $("#integral").html(data.integral == null ? 0 : data.integral);
      $("#balance").html(data.balance == null ? 0 : data.balance);
      $("#myFollowNum").html(data.myFollowNum == null ? 0 : data.myFollowNum);
      $("#followNum").html(data.followNum == null ? 0 : data.followNum);
      if(!isSelf) {
        $('#loadingToast').show();
        $("#titleName").text(data.nickName + "的资料");
        $("#footDiv").show();
        // 查询是否关注和是否加入黑名单
        var url= '/weixin/follow/ajaxIsFollow.do?wxid='+localStorage.getItem("openId")+'&followWxId='+wxid;
        $.post(url,{},function(data){
          $('#loadingToast').hide();
          if(data.isFollow == true) {
            $("#followBtn").html("取消关注");
            $("#followStatus").val("1");
          } else {
            $("#followBtn").html("关注");
            $("#followStatus").val("0");
          }

          if(data.isBlacklist == true) {
            $("#blacklistBtn").html("取消黑名单");
            $("#blacklistStatus").val("1");
          } else {
            $("#blacklistBtn").html("加入黑名单");
            $("#blacklistStatus").val("0");
          }

        });

      }
    }
  })
}