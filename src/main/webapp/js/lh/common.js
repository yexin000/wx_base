//鎺ュ彛璁块棶璺緞
var host_url = "http://127.0.0.1:8080/sdweb/wshop/appInterfaceController/";
//var host_url = "http://127.0.0.1:8085/wshop/appInterfaceController/";
//鍥剧墖璁块棶璺緞
var imagePath = "http://127.0.0.1:8080/";
//var imagePath = "http://127.0.0.1:8085/";

$(function(){	
	
	$("#backtop").hide()
	//点击按钮返回顶部
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
		$("html,body").animate({scrollTop:0},"fast");//定义返回顶部点击向上滚动的动画
	})
	
})