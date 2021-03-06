var topMoney = 0;//最高出价
var addMoney = 0;//加价
$(function(){
    var id = getParam("id");

    loadItemData(id);
    $("#bidBtn").click(function () {
      $('#loadingToast').show();
      var url= '/weixin/wxAuth/ajaxGetId.do?wxid='+ localStorage.getItem("openId");
      $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
        $('#loadingToast').hide();
        if(data.hasAddress == false){
            $("#msgLabel").html("请创建一个收货地址");
            $("#msgDialog").show();
            return false;
        }
          var data = data.data;
          if(!data.phoneNum) {
            $("#phoneDialog").show();
          } else {
            topMoney = parseInt(topMoney + addMoney);
            $("#bidMoney").val(topMoney);
            $('#bidDialog').show();
          }
        }
      })
    });

    $("#favBtn").click(function () {
        $('#loadingToast').show();
        var favStatus = $("#favStatus").val();
        if(favStatus == "0") {
            var followBean = {};
            followBean.followType = "2";
            followBean.wxid = localStorage.getItem("openId");
            followBean.followId = getParam("id");
            followBean.followWxId = localStorage.getItem("openId");
            var url= '/weixin/follow/ajaxFollowSave.do';
            $.post(url,followBean,function(data){
                $('#loadingToast').hide();
                showToast(data.msg, function () {
                });
              $("#favBtn").text("取消关注");
              $("#favStatus").val("1");
            });
        } else {
            // 取消关注
            var followBean = {};
            followBean.followType = "2";
            followBean.wxid = localStorage.getItem("openId");
            followBean.followId = getParam("id");
            followBean.followWxId = localStorage.getItem("openId");
            var url= '/weixin/follow/ajaxFollowDel.do';
            $.post(url,followBean,function(data){
                $('#loadingToast').hide();
                showToast(data.msg, function () {
                });
                $("#favBtn").text("关注");
                $("#favStatus").val("0");
          });
        }

    });

    $("#shareBtn").click(function () {
        if(itemImage !=  '') {
            var itemUrl = encodeURIComponent(itemImage);
            var itemText = encodeURIComponent(itemName);
            var itemId = getParam("id");
            var shareUrl = '/pages/share/shareItem?itemUrl=' + itemUrl + "&itemText=" + itemText + "&itemId=" + itemId;
            wx.miniProgram.navigateTo({ url: shareUrl });
        }
    });

    // 每45秒加载一次出价
    setInterval(function () {
        loadAuctionItemBid(id);
    }, 45000);
})

var itemImage = '';
var itemName = '';

//加载商品数据
function loadItemData(id){
    $('#loadingToast').show();
    var params = {};
    params.id = id;
    var url= '/weixin/auctionItem/ajaxGetId.do?id='+id+"&wxid="+localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data:"",
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataObj = data.data;
            //先处理商品轮播
            var dataList = dataObj.resList;
            if(dataList.length> 0) {
                var str = '';
                $.each(dataList,function(i,obj){
                    var coverimg = obj.path;
                    var coverimgWidth  = obj.width;
                    var coverimgHeight = obj.height;
                    if(i == 0) {
                        itemImage = coverimg;
                    }

                    str+='<li class="bannerItem banner-bg-dark"  onclick="showImg('+i+')">';
                    str+='<input type="hidden" id="imgSrc'+i+'" value="' + hostPath + coverimg +  '">';
                    str+='	    <div style="position: relative; width: 3.2rem;height: 1.75rem;"><a>';
                    var loadClass = '';
                    if(parseInt(coverimgWidth) > parseInt(coverimgHeight * 1.8)){
                        loadClass = 'width';
                    }else{
                        loadClass = 'length';
                    }
                    str+='          <img src="' + hostPath + coverimg +  '" alt="" class="'+loadClass+' userImg" >';
                    str+='      </a></div> ';
                    str+='  </li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
            //处理商品基本信息
            if(dataObj.auctionStatus == '0') {
                $("#timeLabel").text("即将开始 " + dataObj.startTime);
                $("#bidBtn").css("background", "#d5d5d6");
                $("#bidBtn").unbind();
            } else if(dataObj.auctionStatus == '1') {
                //$("#timeLabel").text("正在竞拍:  " + dataObj.startTime + "至" + dataObj.endTime);
                TimeDown('timeLabel',dataObj.endTime );
            } else {
                $("#timeLabel").text("竞拍已结束");
                $("#bidBtn").css("background", "#d5d5d6");
                $("#bidBtn").unbind();
            }

            $("#itemName").html(dataObj.name); //拍品名字
            itemName = dataObj.name;
            $("#itemDescription").html(dataObj.description); //拍品详情
            $("#startprice").text(dataObj.startPrice); //起拍价格
            $("#curprice").html(dataObj.curPrice); //当前价格
            topMoney = dataObj.startPrice  ;
            addMoney = dataObj.addPrice ;
            $("#addprice").html(dataObj.addPrice); //加价最低价格
            $("#rate").html(dataObj.rate + "%"); //手续费比率
            $("#depositprice").html(dataObj.depositPrice); //保证金
            $("#wanfenbi").html('('+dataObj.wanfenbi+"万元)"); //保证金
            if(dataObj.detail != null && dataObj.detail != '') {
                var details = dataObj.detail.split("\n");
                var detail = "";
                for(var i = 0; i < details.length; i ++) {
                    detail = detail + details[i] + "<br/>";
                }
                $("#detailLabel").html(detail); //详情
            }

            if(dataObj.isFollow == "1") {
                $("#favBtn").text("取消关注");
            } else {
                $("#favBtn").text("关注");
            }
            $("#favStatus").val(dataObj.isFollow);

            if(dataObj.uploadWxid != null && dataObj.uploadWxid != "0") {
              $("#storeBtn").click(function () {
                window.location.href = "../../html/lh/myStore.html?wxid=" + dataObj.uploadWxid;
              })
            }

            loadAuctionItemBid(id);
        }
    })
}


//加载出价列表数据
function loadAuctionItemBid(id){
    var model = {};
    model.auctionItemId = id;
    var url= '/weixin/bid/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(model) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            $("#bidDataDiv").empty();
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<div class="bid" style="color: white;">';
                    str+='	<div class="biddiv1" onclick="toUserInfo(\'' + obj.wxid + '\')">';
                    str+=' 		<img src="'  + obj.avatarUrl + '" class="bidimg">';
                    str+='	      <span class="biddiv1span">'+ obj.wxUserName+'</span>';
                    str+='  </div>';
                    str+='  <div  class="biddiv2">';
                    var isFirst = '';
                    var label = '出局';
                    if(i == 0){
                        $("#curprice").html(obj.bidPrice);
                        topMoney = obj.bidPrice;
                        label = '领先';
                        isFirst = 'bidFirst';
                    }
                    str+='  <span class="'+isFirst+'">' + label + '</span> <span class="biddiv2span">' +obj.strDate+'</span> <span class="biddiv2span">'+ obj.bidPrice +'</span>';
                    str+='  </div>';
                    str+='</div>';
                });
            }

            $("#bidDataDiv").append(str);
        }
    })
}



//出价
function toBid(){

    var bidMoney = $("#bidMoney").val();
    if(bidMoney == null) {
        showToast("请输入竞拍金额", function () {
        });
        return;
    }
    if(bidMoney <= 0) {
        showToast("请输入正确的金额", function () {
        });
        return;
    }
    $('#bidDialog').hide();
    $('#loadingToast').show();
    var id = getParam("id");
    var BidBean = {};
    BidBean.wxid = localStorage.getItem('openId');
    BidBean.auctionItemId = id;
    BidBean.bidPrice = bidMoney;
    var url= '/weixin/bid/ajaxAddBid.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(BidBean),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var code = data.code;
            if(code == 0){
                var beOveredUser = data.beOveredUser;
                var path = '/pages/bid/bid?bidMoney='+bidMoney+"&itemName="+$("#itemName").html()+"&beOveredUser="+beOveredUser;
                loadAuctionItemBid(id);
                wx.miniProgram.navigateTo({ url: path });
            }else{
                $("#msgLabel").html(data.msg);
                $("#msgDialog").show();
            }
        }
    })
}

function goBack() {
    var lastUrl = document.referrer;
    if(lastUrl.indexOf("index.html") >= 0 && lastUrl.indexOf("itemId") >= 0) {
        window.location.href = "../../html/lh/index.html";
    } else {
        history.go(-1);
    }
}



function showImgs(i){
    var src = $("#imgSrc"+i).val();
    $("#footDiv").css("z-index","0");
    var str = '';
    str += '<div class="page gallery js_show " id="imgDiv">'
    str +=    '<div class="page__hd">'
    str +=    '<h1 class="page__title">Gallery</h1>'
    str +=     '<p class="page__desc">画廊，可实现上传图片的展示或幻灯片播放</p>'
    str += '</div>'
    str += '<div class="weui-gallery" style="display: block">'
    str +=    '<span class="weui-gallery__img" id="imageSpan" style="background-image: url('+src+');"></span>'
    str +=     '<div class="weui-gallery__opr">'
    str +=     '<a href="javascript:" class="weui-gallery__del">'
    str +=     '<i class="weui-icon-cancel weui-icon_gallery-cancel" onclick="closeImg()"></i>'
    str +=     '</a>'
    str +=     '</div>'
    str +=     '</div>'
    str +=    '</div>'
    $('body').append(str);
}



/*调用微信预览图片的方法*/
function showImg(){
    var imgs = [];
    var imgObj = $(".userImg");//这里改成相应的对象
    for(var i=0; i<imgObj.length; i++){
        imgs.push(imgObj.eq(i).attr('src'));
        imgObj.eq(i).click(function(){
            var nowImgurl = $(this).attr('src');
            WeixinJSBridge.invoke("imagePreview",{
                "urls":imgs,
                "current":nowImgurl
            });
            WeixinJSBridge.call('closeWindow');
        });
    }

}


function closeImg(){
    $("#imgDiv").remove();
}
