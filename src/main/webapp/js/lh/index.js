//var userID=localStorage.getItem("userId");
 $(function(){

        $("#backtop").hide();
        loadindexBanner();
        loadindexAuction();
        loadindexAuctionItem();
        //  记得从小程序回调请求里面获取wxid，存到localStorage
        localStorage.setItem('openId',getParam("openId"));
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


    //加载首页轮播图数据
    function loadindexBanner(){
        var AuctionItemModel = {};
        AuctionItemModel.isShowBanner = '1';
        AuctionItemModel.status = '3';
        var url= '/weixin/auctionItem/ajaxDataList.do';
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(AuctionItemModel) ,
            dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
            cache: false,
            success:function(data){
                var dataList = data.rows;
                if(dataList.length> 0)
                {
                    var str = '';
                    for(var i = 0; i < dataList.length; i ++) {
                        var obj = dataList[i];
                        var coverimg = '';

                        if(obj.resList && obj.resList.length > 0) {
                            coverimg = obj.resList[0].path;
                        }
                        str+='<li onclick="toAuctionItemDetail('+obj.id+')" class="bannerItem" style="background-image: url(' + hostPath + coverimg + '); no-repeat;background-size:100% 100%;-moz-background-size:100% 100%;"/>';
                    }

                    $(".bannerList").append(str);
                    bannerDW("banner1",3000,true,"red");
                }
            }
        })
    }

    //加载首页会展数据
    function loadindexAuction(){
        var AuctionModel = {};
        AuctionModel.isShow = '1';
    	var url= '/weixin/auction/ajaxDataList.do';

    	$.ajax({
    		url: url,
    		type: 'post',
    		data: JSON.stringify(AuctionModel) ,
    		dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
    		cache: false,
    		success:function(data){
    			var dataList = data.rows;
                if(dataList.length> 0)
                {
                	 var str = '';
                    $.each(dataList,function(i,obj){

                        str+='<div class="posr" onclick="toAuctionDetail('+obj.id+')">';
                        str+='	<div class="left" >';
                        str+=' 		<img src="' + hostPath + obj.logoPath +  '" alt="">';
                        str+='	</div>';
                        str+='<p style="padding-top: 1.4rem;padding-left: -1rem ;font-size:18px;font-weight:bold;">'+obj.name+'</p>';
                        str+='<p style=" padding-left: -1rem ; "> '+obj.starttime+'开始，大家敬请期待</p>';
                        str+='</div>';
                    });
                }
                $("#dataDiv").append(str);

    		}
    	})
    }

    //加载首页会展项数据
    function loadindexAuctionItem(){
        var AuctionItemModel = {};
        AuctionItemModel.isShow = '1';
        AuctionItemModel.status = '3';
        var url= '/weixin/auctionItem/ajaxDataList.do';
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(AuctionItemModel) ,
            dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
            cache: false,
            success:function(data){
                var dataList = data.rows;
                if(dataList.length> 0)
                {
                    var str = '';
                    $.each(dataList,function(i,obj){
                        var coverimg = '';

                        if(obj.resList && obj.resList.length > 0) {
                            coverimg = obj.resList[0].path;
                        }
                        str+='<div onclick="toAuctionItemDetail('+obj.id+')" class="posr-item">';
                        str+='	<div class="left" >';
                        str+=' 		<img src="' + hostPath + coverimg +  '" alt="">';
                        str+='	</div>';

                        str+='</div>';
                    });
                }
                $("#dataDivItem").append(str);
            }
        })
    }
