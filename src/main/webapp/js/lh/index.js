//var userID=localStorage.getItem("userId");
 $(function(){
        $("#backtop").hide();
        loadindexBanner();
        loadindexAuction();
        loadindexAuctionItem();
        loadIndexActivity();
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

   })


//加载第一条活动标题
function loadIndexActivity(){
    $('#loadingToast').show();
    var url= '/weixin/activity/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                $("#pmd").html(dataList[0].name);
            }
        }
    })
}


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
                        str+='	    <div style="position: relative; width: 3.2rem;height: 1.3rem;"><a>';
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
                var auctionList = data.rows;

                if(auctionList.length > 0) {
                    $("#auctionNoDataDiv").hide();
                    $(".mystore-auction-preview").remove();
                    for(var i = 0; i < auctionList.length; i ++) {
                        var str = '';
                        str += '<div class="weui-form-preview mystore-auction-preview">'
                            +   '<div class="weui-form-preview__hd mystore-auction-preview-hd">'
                            +     '<div class="weui-form-preview__item">'
                            +       '<i class="mystore-vip-icon"></i>'
                            +       '<em class="weui-form-preview__value">专场名称  ' + auctionList[i].name + '</em>'
                            +     '</div>'
                            +   '</div>'
                            +   '<div class="weui-form-preview__bd mystore-auction-preview-bd" style="text-align: center;" onclick="toAuctionDetail(' + auctionList[i].id + '' + ')">'
                            +     '<div class="weui-form-preview__item">'
                            +       '<img class="mystore-auction-img" src="' + hostPath + auctionList[i].logoPath + '" style="height: 120px;width: ' + 120 /auctionList[i].height * auctionList[i].width + 'px;">'
                            +     '</div>'
                            +   '</div>'
                            +   '<div class="weui-cell" style="background-color: black;">'
                            +     '<div class="weui-cell__hd"><img class="headimg mystore-auction-headimg" src="'+auctionList[i].path+'"></div>'
                            +     '<div class="weui-cell__bd"><p class="headtext mystore-auction-headtext">  '+auctionList[i].wxName+' </p></div>'
                            +     '<div class="weui-cell__hd"><img src="/weixin/images/lh/mystore-fav-icon.png" class="mystore-auction-favicon"></div>'
                            +     '<div class="weui-cell__ft">0</div>'
                            +   '</div>'
                            +'</div>'
                        $("#dataDiv").append(str);
                    }
                }
               // $("#dataDiv").append(str);
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
