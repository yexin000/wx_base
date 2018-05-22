
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


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

    //加载首页轮播图数据
    function loadUser(){
        var AuctionItemModel = {};
        AuctionItemModel.isShowBanner = '1';
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
                        for(var j = 0; j < obj.resList.length; j ++) {
                            var resObj = obj.resList[j];
                            if(resObj.idx == 1)
                            {
                                coverimg = resObj.path;
                            }
                        }
                        str+='<li onclick="toAuctionItemDetail('+obj.id+')" class="bannerItem" style="background-image: url(' + hostPath + coverimg + '); no-repeat;background-size:100% 100%;-moz-background-size:100% 100%;"/>';
                    }

                    $(".bannerList").append(str);
                    bannerDW("banner1",3000,true,"red");
                }
            }
        })
    }