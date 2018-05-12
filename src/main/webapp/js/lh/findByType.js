
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
	
	$("#backtop").hide()
    var type = getParam("type");
	alert(type);
    loadfindData();
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

//加载首页数据
function loadfindData(){

    var url= '/weixin/auction/dataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data:{},
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){

                    str+='<div class="posr">';
                    str+='	<div class="left" >';
                    str+=' 		<img src="../../../images/lh/wshop_indexbanner1.jpg" alt="">';
                    str+='	</div>';
                    str+='<p style="padding-top: 1.4rem;padding-left: -1rem ;font-size:18px;font-weight:bold;">'+obj.name+'</p>';
                    str+='<p style=" padding-left: -1rem ; "> '+obj.createtime+'开始，大家敬请期待</p>';
                    str+='</div>';
                });
            }
            $("#dataDiv").append(str);

        }
    })
}