//var userID=localStorage.getItem("userId");
 $(function(){
     loadAgent()
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


    //加载经纪人数据
    function loadAgent(){
        var url= '/weixin/middleMan/ajaxGetMiddleMan.do?wxid='+ sessionStorage.getItem("openId");
        $.ajax({
            url: url,
            type: 'post',
            data: {} ,
            dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
            cache: false,
            success:function(data){
                var data = data.data;
                $("#agentImg").attr("src",hostPath + data.avatarUrl);
                var gradeStr = '';
                if(data.grade == '1'){
                    gradeStr = '初级经纪人';
                }else if(data.grade == '2'){
                    gradeStr = '中级经纪人';
                }else{
                    gradeStr = '高级经纪人';
                }
                $("#grade").html(gradeStr);
                $("#grades").html(gradeStr);
                $("#phone").html(data.phoneNum);
                $("#wxNum").html(data.wxAcount);
                $("#description").html(data.description);
            }
        })
    }


   
   