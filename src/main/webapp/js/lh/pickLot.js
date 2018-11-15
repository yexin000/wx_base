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
    AuctionItemModel.attributes = ['0', '1'];
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
                    var coverImage = '/weixin/foreground/images/no-image.jpg';
                    if(dataList[i].resList != null && dataList[i].resList.length > 0) {
                        coverImage = hostPath + dataList[i].resList[0].path;
                    }

                    var price = dataList[i].curPrice;
                    if(price <= 0) {
                        price = dataList[i].startPrice;
                    }
                    str += '<a href="javascript:toAuctionItemDetail(\''+dataList[i].id+'\',\'' + dataList[i].attribute + '\');" class="weui-grid1" style="position: initial;padding: 10px 0px; ">'
                    str +=  '<div class="mystore-auctionitem-div">'
                    str +=     '<div   style="width: 90%;height: 180px;overflow: hidden;border: 1px solid yellow; border-radius: 3px;">'
                    str +=       '<img src="' + coverImage + '" alt="" style="width: 100%;overflow: hidden;">'
                    str +=     '</div>'
                    str +=     '<p class="weui-grid__label auctionitem-auctionitem-label">名称:' + dataList[i].name + '</p>'
                    str +=     '<p class="weui-grid__label auctionitem-auctionitem-label">作者:' + dataList[i].uploader + '</p>'
                    str +=     '<p class="weui-grid__label auctionitem-auctionitem-label">价格:' + price + '</p>'
                    str +=     '<p class="weui-grid__label auctionitem-auctionitem-label">结束时间:' + dataList[i].endTime + '</p>'
                    str +=   '</div>'
                    str +='</a>';

                });
                $("#auctionItemData").append(str);
            }

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
    loadIndexAuctionItem();
}