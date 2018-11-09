
$(function(){
    //加载关注数据
    loadMyFollowListData();
})


//加载数据
function loadMyFollowListData(){
    $('#loadingToast').show();
    var url= '/weixin/follow/ajaxAuctionDataList.do?wxid='+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
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
                        +       '<em class="weui-form-preview__value">专场名称  ' + auctionList[i].followName + '</em>'
                        +     '</div>'
                        +   '</div>'
                        +   '<div class="weui-form-preview__bd mystore-auction-preview-bd" style="text-align: center;" onclick="toAuctionDetail(' + auctionList[i].followId + '' + ')">'
                        +     '<div class="weui-form-preview__item">'
                        +       '<img class="mystore-auction-img" src="' + hostPath + auctionList[i].path + '" style="height: 120px;width: ' + 120 /auctionList[i].height * auctionList[i].width + 'px;">'
                        +     '</div>'
                        +   '</div>'
                        +   '<div class="weui-cell" style="background-color: black;">'
                        +     '<div class="weui-cell__hd"><img class="headimg mystore-auction-headimg" src="'+auctionList[i].createAuctionPath+'"></div>'
                        +     '<div class="weui-cell__bd"><p class="headtext mystore-auction-headtext">  '+auctionList[i].createAuctionName+' </p></div>'
                        +     '<div class="weui-cell__hd"><img src="/weixin/images/lh/mystore-fav-icon.png" class="mystore-auction-favicon"></div>'
                        +     '<div class="weui-cell__ft">0</div>'
                        +   '</div>'
                        +'</div>'
                    $("#dataDiv").append(str);
                }
            }
        }
    })
}

