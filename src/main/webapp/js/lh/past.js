
//图片访问路径
var imagePath = "http://127.0.toTypeAuction0.1:8080/";


$(function(){	
	
	$("#backtop").hide()

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

function toTypeAuction()
{

    window.location.href = "../../../foreground/html/lh/findbyType.html?type=1";
}