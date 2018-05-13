
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
	
	$("#backtop").hide()

    loadbusinessData();
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

//加载商户数据
function loadbusinessData(){
    var bisinessModel = {};
    bisinessModel.isShow = '1';
    var url= '/weixin/business/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(bisinessModel) ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<div class="posr-item">';
                    str+='	<div class="left" onclick="toBusinessDetail('+obj.id+')">';
                    str+=' 		<img src= "'+hostPath + obj.logoPath + '" alt="">';
                    str+='	</div>';
                    str+='</div>';
                });
            }
            $("#dataDiv").append(str);
        }
    })
}