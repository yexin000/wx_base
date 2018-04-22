//抢购进度条
$(".panicBuying>ul>li>p>i").each(function(i,e){
	$(e).css("width",parseInt($($(".panicBuying>ul>li>p>span")[i]).html())/200+"rem");
});
var userID=localStorage.getItem("userId");
 $(function(){

        $("#backtop").hide();
        //������ҳbanner
        loadindexbanner();
        //���ع���
        loadindexnotice();
        //���ؽ�������
        loadindexsales();
        //加载首页生活馆模块
        loadindexdress();
        //加载首页美妆馆模块
       loadindexmakeup();
     //加载首页生活馆模块
     loadindexlife()
        
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
   
    //加载首页banner
    function loadindexbanner(){
    	var str='';
    	var url=host_url+'findBannerList.do';
    	$.ajax({
    		url: url,
    		type: 'post',
    		data:{
    			'USER_ID':userID,
    			'CZMA':"01"
    		},
    		dataType: 'JSON',
    		cache: false,
    		success:function(data){
    			if(data.CZJG == "success"){
    				if(data.CZFH.pageList != null){
    					$.each(data.CZFH.pageList,function(i,obj){
    						if(obj.JUMP_TYPE == '01'){
    							str += '<div class="swiper-slide" id="'+obj.ID+'"><img src="'+imagePath+''+obj.BANNER_IMAGE+'"></div>'
    						}else if(obj.JUMP_TYPE == '02'){
    							str += '<div class="swiper-slide" id="'+obj.ID+'"><a href="'+host_url+''+obj.jump_address+'"><img src="'+imagePath+''+obj.BANNER_IMAGE+'"></a></div>'
    						}else{
    							str += '<div class="swiper-slide" id="'+obj.ID+'"><a href="'+host_url+''+obj.jump_address+'"><img src="'+imagePath+''+obj.BANNER_IMAGE+'"></a></div>'
    						}
    					});
    					$("#index-bannerSwipper .swiper-wrapper").append(str);
    				    var mySwiper1 = new Swiper('.header-index .swiper-container',{
    				        pagination: '.pagination',
    				        loop:false,
    				        grabCursor: true,
    				        paginationClickable: true,
    				        autoplayDisableOnInteraction : false,
    				        autoplay:3000
    				  })
    				}
    			}
    		}
    	})
    }
 
 //加载首页公告
 function loadindexnotice(){
	 var url=host_url+'findNoticeList.do';
	 $.ajax({
		 url:url,
		 data:{
			 "USER_ID":userID
		 },
		 dataType:"json",
		 type:"post",
		 success:function(data){
			 var str="";
			 if(data.CZJG == "success"){
				 if(data.CZFH.pageList.length == 3){
					 $.each(data.CZFH.pageList,function(i,obj){
						 if(obj.type == "1"){
							 str += '<li id="'+obj.ID+'"><a href="##">'+obj.title+'</a></li>';
						 }else if(obj.type == "2"){
							 str += '<li id="'+obj.ID+'"><a href="'+host_url+''+obj.COMMODITY_BRAND+'">'+obj.title+'</a></li>';
						 }else{
							 str += '<li id="'+obj.ID+'"><a href="'+host_url+''+obj.COMMODITY_BRAND+'">'+obj.title+'</a></li>';
						 }
					 })
					 $("#notice-wrapper").append(str)
				 }
			}
		 }
	 })
 }

 //加载首页特卖 
 function loadindexsales(){
	 var url=host_url+'findDayHostList.do';
	 $.ajax({
		 url:url,
		 data:{
			 "USER_ID":userID
		 },
		 type:"post",
		 dataType:"json",
		 success:function(data){
			 var str = "";
			 if(data.CZJG == "success"){
				// alert(data.CZFH.pageList.length)
				 if(data.CZFH.pageList != null){
					 $.each(data.CZFH.pageList,function(i,obj){
						 str += '<div class="swiper-slide"><a href="#"><img src="'+imagePath+''+obj.COMMODITY_IMG+'"><h3><small>￥</small>'+obj.MARKET_PRICE+'</h3><s><small>￥</small>'+obj.DISCOUNT_PRICE+'</s></a></div>'
					 })
				 }
				 $("#indexsales .swiper-wrapper").append(str);
				    var mySwiper2 = new Swiper('.sales .swiper-container', {
				        direction : 'horizontal',
				        paginationClickable: true,
				        slidesPerView: 4,
				        loop: true,
				        slidesOffsetBefore: 20,
				    })
			 }
		 }
	 })
 }
 
 //加载首页服装馆模块
  function loadindexdress(){
	  var url = host_url+'loadModuleList.do';
	  $.ajax({
		  url:url,
		  data:{
			  "USER_ID":userID,
			  "CZMA":"1"
		  },
		  type:"post",
		  dataType:"json",
		  success:function(data){
			  var str= '';
			  if(data.CZJG == "success"){
				  //alert(data.CZFH.pageList.length)
				  $.each(data.CZFH.pageList,function(i,obj){
					  str += '<li><a href="#"><hgroup><h3>'+obj.MODEL_TITLE+'</h3><h4>'+obj.MODEL_MESSAGE+'</h4></hgroup><img src="'+imagePath+''+obj.MODEL_IMAGE+'" alt=""></a></li>'
				  })
				  $("#indexdress .col-2").append(str)
			  }
		  }
	  })
  } 
   
  //加载首页美妆馆模块
  function loadindexmakeup(){
	  var url = host_url+'loadModuleList.do';
	  //alert(url)
	  $.ajax({
		  url:url,
		  data:{
			  "USER_ID":userID,
			  "CZMA":"2"
		  },
		  type:"post",
		  dataType:"json",
		  success:function(data){
			  var str= '';
			  if(data.CZJG == "success"){
				  $.each(data.CZFH.pageList,function(i,obj){
					  var massage1=obj.MODEL_MESSAGE.substring(0,2);
					  var massage2=obj.MODEL_MESSAGE.substring(2,4);
					  if(i == 0){
						  str += '<li><a href="#"><h3>'+obj.MODULE_NAME+'</h3><p class="makeup-tips"><span style="color:#c58c32">'+massage1+'</span><span>'+massage2+'</span></p><img src="images/makeup02.png" alt=""></a></li>'
					  }else if(i == 1){
						  str += '<li><a href="#"><h3>'+obj.MODULE_NAME+'</h3><p class="makeup-tips"><span style="color:#00aaec">'+massage1+'</span><span>'+massage2+'</span></p><img src="images/makeup02.png" alt=""></a></li>'
					  }else{
						  str += '<li><a href="#"><h3>'+obj.MODULE_NAME+'</h3><p class="makeup-tips"><span style="color:#eb5a57">'+massage1+'</span><span>'+massage2+'</span></p><img src="images/makeup02.png" alt=""></a></li>'
					  }
				  })
				  $("#indexmakeup .col-3").append(str)
			  }
		  }
	  })
  }
   
//加载首页生活馆模块
  function loadindexlife(){
	  var url = host_url+'loadModuleList.do';
	  $.ajax({
		  url:url,
		  data:{
			  "USER_ID":userID,
			  "CZMA":"3"
		  },
		  type:"post",
		  dataType:"json",
		  success:function(data){
			  var str= '';
			  if(data.CZJG == "success"){
				  //alert(data.CZFH.pageList.length)
				  $.each(data.CZFH.pageList,function(i,obj){
					  str += '<li><a href="#"><hgroup><h3>'+obj.MODEL_TITLE+'</h3><h4>'+obj.MODEL_MESSAGE+'</h4></hgroup><img src="'+imagePath+''+obj.MODEL_IMAGE+'" alt=""></a></li>'
				  })
				  $("#indexlife .col-3").append(str)
			  }
		  }
	  })
  }
   
   
   
   