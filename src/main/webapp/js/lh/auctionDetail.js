var pageId = 1;
var auctionId = '';
$(function(){
    var id = getParam("id");
    auctionId = id;
    loadAuctionData(id);
    loadAuctionItem(id);
})

//加载会展数据
function loadAuctionData(){
    $('#loadingToast').show();
    var url= '/weixin/auction/getId.do?id='+auctionId;
    $.ajax({
        url: url,
        type: 'post',
        data:{},
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataObj = data.data;
            $("#businessName").html( dataObj.name);
            //先处理商品轮播
            var dataList = dataObj.resList;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    var coverimg = obj.path;
                    var coverimgWidth = obj.width;
                    var coverimgHeight = obj.height;
                    str+='<li class="bannerItem">';

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
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
        }
    })
}


//加载会展拍品数据
function loadAuctionItem(){
    var AuctionItemModel = {};
    AuctionItemModel.auctionId = auctionId;
    AuctionItemModel.attribute = '0';
    AuctionItemModel.page = pageId;
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
                $("#shoppingCount").html(datalength);
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
                    str+='<tr onclick="toAuctionItemDetail('+obj.id+','+obj.attribute+')">';
                    str+='  <td class="pro-item-M">' ;
                    str+='  <div class="itemDiv">' ;

                    var loadClass = '';
                    if(parseInt(coverimgHeight) > parseInt( coverimgWidth)){
                        loadClass = 'height';
                    }else{
                        loadClass = 'width';
                    }
                    str+='      <img src="' + hostPath + coverimg +  '"   class="'+loadClass+'">'  ;
                    str+='  </div>' ;
                    str+='  </td>';
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
