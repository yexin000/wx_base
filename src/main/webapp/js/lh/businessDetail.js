var selectType = 1;
var pageId = 1;
$(function(){
    $("#business-tit li").click(function(){
        $(".pro-item").empty();
        pageId = 1;
        $(this).addClass("cur").siblings().removeClass("cur");
        selectType = $(this).attr("selectType");
       if(selectType == '1'){
           loadindexAuction(id);
       }else{
           loadBusinessAuctionItem(id);
       }
    })
    var id = getParam("id");
    loadfindData(id);
    $('li').eq(0).click();
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
            if(dataObj.logoPath == null || dataObj.logoPath == '') {
                dataObj.logoPath = "foreground/images/no-image.jpg";
            }
            $("#businessImg").attr("src", hostPath + dataObj.logoPath);
            $("#businessName").html( dataObj.name);
            $("#dataDiv").append(dataObj);
        }
    })
}


//加载商家拍品数据
function loadBusinessAuctionItem(id){
    $('#loadingToast').show();
    $("#loadMore").hide();
    var AuctionItemModel = {};
    AuctionItemModel.businessId = id;
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
            var datalength = data.total;
            if(dataList.length> 0)
            {
                $("#shoppingCount").html(dataList.length);
                var str = '';
                $.each(dataList,function(i,obj){
                    var coverimg = '';

                    if(obj.resList && obj.resList.length > 0) {
                        coverimg = obj.resList[0].path;
                    }
                    str+='<tr onclick="toAuctionItemDetail('+obj.id+','+obj.attribute+')">';
                    str+='  <td class="pro-item-M"><img src="' + hostPath + coverimg +  '"  alt=""></td>';
                    str+='  <td class="pro-item-H">';
                    str+='      <h2>'+obj.name+'</h2>';
                    str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                    str+='      <p><span>商品价格: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span></p>';
                    str+='      <p><span>商品库存: </span><span style="overflow:hidden;  "> '+obj.stock +'<span></p>';
                    str+='  </td>';
                    str+='</tr>';
                });
            }
            $(".pro-item").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").hide();
            }else{
                $("#loadMore").show();
            }
            pageId++;
        }
    })
}


//加载首页会展数据
function loadindexAuction(id){
    $("#loadMore").hide();
    var AuctionModel = {};
    AuctionModel.businessid = id;
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
            var datalength = data.total;
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
                    // 图片地址
                    var img_url = hostPath + obj.logoPath + '?d='+Date.parse(new Date());;
                    // 创建对象
                    var img = new Image();
                    // 改变图片的src
                    img.src = img_url;
                    str+='<div class="posr" onclick="toAuctionDetail('+obj.id+')">';
                    str+='	<div class="left"  >';
                    var loadClass = '';
                    if(img.width > img.height){
                        loadClass = 'width';
                    }else{
                        loadClass = 'length';
                    }
                    str+=' 		<img src="' + img_url +  '" alt=""  class="'+loadClass+'" >';
                    str+='	</div>';
                    str+='<p style="padding-top: 1.4rem; font-size:18px;font-weight:bold; margin-left: 0.04rem;">'+obj.name+'</p>';
                    str+='<p style="margin-left: 0.04rem;"> '+label+'</p>';
                    str+='</div>';
                });
            }
            $(".pro-item").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").hide();
            }else{
                $("#loadMore").show();
            }
            pageId++;
        }
    })
}
