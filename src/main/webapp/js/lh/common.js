//接口访问路径
var host_url = "http://127.0.0.1:8080/sdweb/wshop/appInterfaceController/";
//var host_url = "http://127.0.0.1:8085/wshop/appInterfaceController/";
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";
//var imagePath = "http://127.0.0.1:8085/";

$(function(){	
	
	$("#backtop").hide()
	//�����ť���ض���
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
		$("html,body").animate({scrollTop:0},"fast");//���巵�ض���������Ϲ����Ķ���
	})
	
})