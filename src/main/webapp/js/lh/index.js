//var userID=localStorage.getItem("userId");
 $(function(){
        $("#backtop").hide();
        loadindexBanner();
        loadindexAuction();
        loadindexAuctionItem();
        //  记得从小程序回调请求里面获取wxid，存到localStorage
        if(getParam("openId"))
        {
            localStorage.setItem('openId',getParam("openId"));
        }

        var itemId = getParam("itemId");
        if(itemId && itemId != '') {
            var url= '/weixin/auctionItem/ajaxGetId.do?id='+itemId+"&wxid="+localStorage.getItem("openId");
            $.ajax({
                url: url,
                type: 'post',
                data:"",
                contentType : "application/json;charset=utf-8",
                dataType: 'JSON',
                cache: false,
                success:function(data){
                    var dataObj = data.data;
                    var attribute = dataObj.attribute;
                    if(attribute != null && attribute != '') {
                        toAuctionItemDetail(itemId,attribute);
                    }
                }
            })
        }

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
        $('#loadingToast').show();
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
                $('#loadingToast').hide();
                var dataList = data.rows;
                if(dataList.length> 0)
                {
                    var str = '';
                    for(var i = 0; i < dataList.length; i ++) {
                        var obj = dataList[i];
                        var coverimg = '';
                        var coverimgWidth = '';
                        var coverimgHeight = '';
                        if(obj.resList && obj.resList.length > 0) {
                            coverimg = obj.resList[0].path;
                            coverimgWidth = obj.resList[0].width;
                            coverimgHeight = obj.resList[0].height;
                        }
                        str+='<li   class="bannerItem" onclick="toAuctionItemDetail('+obj.id+','+obj.attribute+')">';
                        str+='	    <div style="position: relative; width: 3.2rem;height: 1.75rem;"><a>';
                        var loadClass = '';
                        if(parseInt(coverimgWidth) > parseInt(coverimgHeight * 1.8)){
                            loadClass = 'width';
                        }else{
                            loadClass = 'length';
                        }
                        str+='          <img src="' + hostPath + coverimg +  '" alt="" class="'+loadClass+'" >';
                        str+='      </a></div> ';
                        str+='  </li>';

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
                        var status = obj.status;
                        var label = "";
                        if(status == "1") {
                            label = obj.starttime + "开始，敬请期待";
                        } else if(status == "2") {
                            label = obj.starttime + "开始，火热进行中";
                        } else if(status == "3") {
                            label = obj.endtime + "已结束";
                        }
                        if(obj.logoPath == null || obj.logoPath == '') {
                            obj.logoPath = "foreground/images/no-image.jpg";
                        }

                        str+='<div class="posr" onclick="toAuctionDetail('+obj.id+')">';
                        str+='	<div class="left"  >';
                        var loadClass = '';
                        if(parseInt(obj.width) > parseInt(obj.height * 1.8)){
                            loadClass = 'width';
                        }else{
                            loadClass = 'length';
                        }
                        str+=' 		<img src="' +  hostPath +  obj.logoPath +  '" alt=""  class="'+loadClass+'" >';
                        str+='	</div>';
                        str+='<p style="padding-top: 1.4rem; font-size:18px;font-weight:bold; margin-left: 0.04rem;">'+obj.name+'</p>';
                        str+='<p style="margin-left: 0.04rem;"> '+label+'</p>';
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
                //$("#uploadBtn").show();
                var dataList = data.rows;
                if(dataList.length> 0)
                {
                    var str = '';
                    $.each(dataList,function(i,obj){
                        var coverimg = '';
                        var coverimgWidth = '';
                        var coverimgHeight = '';
                        if(obj.resList && obj.resList.length > 0) {
                            coverimg = obj.resList[0].path;
                            coverimgWidth = obj.resList[0].width;
                            coverimgHeight = obj.resList[0].height;
                        }

                        str+='<div onclick="toAuctionItemDetail('+obj.id+','+obj.attribute+')" class="posr-item">';
                        str+='	<div class="left" >';
                        var loadClass = '';
                        if(parseInt(coverimgWidth) > parseInt(coverimgHeight * 1.8)){
                            loadClass = 'width';
                        }else{
                            loadClass = 'length';
                        }
                        str+=' 		<img src="' + hostPath + coverimg +  '" alt="" class="'+loadClass+'">';
                        str+='	</div>';

                        str+='</div>';
                    });
                }
                $("#dataDivItem").append(str);
            }
        })
    }
