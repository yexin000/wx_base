$(function(){
  loadUser();
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
      $("#integral").html(data.integral == null ? 0 : data.integral);
      $("#balance").html(data.balance == null ? 0 : data.balance);
      $("#myFollowNum").html(data.myFollowNum == null ? 0 : data.myFollowNum);
      $("#followNum").html(data.followNum == null ? 0 : data.followNum);
    }
  })
}