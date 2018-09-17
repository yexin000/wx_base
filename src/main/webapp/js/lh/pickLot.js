var pageId = 1;
$(function(){
    loadIndexAuctionItem();
})

//加载商家拍品数据
function loadIndexAuctionItem(){
    $('#loadingToast').show();
    $("#loadMore").hide();
    var AuctionItemModel = {};
    AuctionItemModel.isShow = "1";
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


function loadMore(){
    loadBusinessAuctionItem();
}