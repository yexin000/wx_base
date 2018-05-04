//var userID=localStorage.getItem("userId");
 $(function(){

        $("#backtop").hide();

        loadindexbanner();



        var p=0,t=0;
        $(window).scroll(function(){
            var p=$(document).scrollTop();
            //console.log($(document).height());
            if(t < p){
            	//页面下滑动距离超过整体高度10%,隐藏搜索框
            	if(p>$(document).height()*0.1){
                $(".header-index-top").css({"top":"-0.44rem"});
            	}
                $(".header-msg").css({"background-image":"url(images/msg_n2.png)"});
                $(".header-room img").attr("src","images/room2.png");
                //页面下滑动距离超过800,显示回到顶部按钮
                if(p>800){
                	$("#backtop").css("right","0");
                }
            }else{//页面上滑动则显示搜索框。隐藏:回到顶部按钮
               	$(".header-index-top").css({"top":"0", "background-image":"linear-gradient(180deg,rgba(0,0,0,0.65) 0%,rgba(0,0,0,0.2) 80%,rgba(0,0,0,0) 100%)",});
                $("#backtop").css("right","-20%");
            }      
            setTimeout(function(){t = p;},0);
        });
   })
   
    //加载首页数据
    function loadindexbanner(){

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

   
   