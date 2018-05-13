
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
	
	$("#backtop").hide()
    var id = getParam("id");
    loadfindData(id);
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

//加载商家数据
function loadfindData(id){

    var url= '/weixin/business/ajaxGetId.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(id),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataObj = data.data;
            $("#businessImg").attr("src", hostPath + dataObj.logoPath);
            $("#businessName").html( dataObj.name);
            $("#shoppingCount").html( dataObj.itemCount);

            $("#dataDiv").append(dataObj);
        }
    })
}