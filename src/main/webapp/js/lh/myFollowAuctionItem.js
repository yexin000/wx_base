
$(function(){

    //加载关注数据
    loadMyFollowListData();

})


//加载数据
function loadMyFollowListData(){
    $('#loadingToast').show();
    var url= '/weixin/follow/ajaxAuctionItemDataList.do?wxid='+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
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

                    str += '<a href="javascript:toAuctionItemDetail(\''+dataList[i].followId+'\',\'' + dataList[i].attribute + '\');" class="weui-grid1" style="position: initial;padding: 10px 0px; ">'
                        +   '<div class="mystore-auctionitem-div">'
                        +     '<div class="weui-grid__icon" style="width: 90%;height: 180px;">'
                        +       '<img src="' + coverImage + '" alt="">'
                        +     '</div>'
                        +     '<p class="weui-grid__label auctionitem-auctionitem-label">名称:' + dataList[i].followName + '</p>'
                        +     '<p class="weui-grid__label auctionitem-auctionitem-label">作者:' + dataList[i].itemCreator + '</p>'
                        +     '<p class="weui-grid__label auctionitem-auctionitem-label">价格:' + dataList[i].price + '</p>'
                        +     '<p class="weui-grid__label auctionitem-auctionitem-label">结束时间:' + dataList[i].endTime + '</p>'
                        +   '</div>'
                        +'</a>';
                    $("#auctionItemData").append(str);
                });
            }
        }
    })
}

