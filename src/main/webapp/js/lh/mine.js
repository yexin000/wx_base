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
        }
    })
}

// 联系客服，调整客服页
function contactService() {
    var path = '/pages/service/contactService';
    wx.miniProgram.navigateTo({ url: path });
}
