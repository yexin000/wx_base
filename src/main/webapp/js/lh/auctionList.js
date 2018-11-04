var pageId = 1;
$(function(){
    loadAuctions();
})

//加载会展数据
function loadAuctions() {
    $('#loadingToast').show();
    var AuctionModel = {};
    $.ajax({
        url: '/weixin/auction/ajaxDataList.do',
        type: 'post',
        data: JSON.stringify(AuctionModel) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(result){
            $('#loadingToast').hide();
                var auctionList = result.rows;
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
                        $("#auctionDiv").append(str);
                    }
                } else {
                    $("#auctionNoDataDiv").show();
                }

        }
    })
}


function loadMore(){
    loadAuctions();
}