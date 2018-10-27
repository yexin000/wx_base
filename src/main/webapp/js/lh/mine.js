$(function(){
	
	$("#backtop").hide()
    loadUser();
	$(window).scroll(function(){
        var scrolltop=$(document).scrollTop();
        var Vheight=$(window).height();
        if(scrolltop > 0){
            $("#backtop").show(); 
            }else{
                 $("#backtop").hide();
                }      
    });
	$("#backtop").click(function(){
		$("html,body").animate({scrollTop:0},"fast");
	})
	
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
            $("#balance").html(data.balance);
            $("#followNum").html(data.followNum);
            if(data.integral){
                $("#myIntegral").html(data.integral);
            }else{
                $("#myIntegral").html('0');
            }

            if(data.vipGrade == 1){
                $("#vipLv").append('<i class="mine-icon-level"></i>');
            }else if(data.vipGrade == 2){
                $("#vipLv").append('<i class="mine-icon-level"></i> <i class="mine-icon-level"></i>');
            }else if(data.vipGrade == 3){
                $("#vipLv").append('<i class="mine-icon-level"></i> <i class="mine-icon-level"></i> <i class="mine-icon-level"></i>');
            }else if(data.vipGrade == 4){
                $("#vipLv").append('<i class="mine-icon-level"></i> <i class="mine-icon-level"></i> <i class="mine-icon-level"></i> <i class="mine-icon-level"></i>');
            }else if(data.vipGrade == 5){
                $("#vipLv").append('<i class="mine-icon-level"></i> <i class="mine-icon-level"></i> <i class="mine-icon-level"></i> <i class="mine-icon-level"></i> <i class="mine-icon-level"></i>');
            }else{
                //无vip等级
                $("#vipLv").append('暂无等级');
            }


        }
    })
}

// 联系客服，调整客服页
function contactService() {
    var path = '/pages/service/contactService';
    wx.miniProgram.navigateTo({ url: path });
}

function toMyInfo() {
  window.location.href = '../../html/lh/myInfo.html';
}

