var hostPath = "https://mlhdkj.com/weixin/";

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


/**
 * 获取指定的URL参数值
 * URL:http://www.quwan.com/index?name=tyler
 * 参数：paramName URL参数
 * 调用方法:getParam("name")
 * 返回值:tyler
 */
function getParam(paramName) {
    paramValue = "", isFound = !1;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
    }
    return paramValue == "" && (paramValue = null), paramValue
}

/**
 * 去商家列表
 * @param id
 */
function toBusinessList()
{
    window.location.href = '../../html/lh/recommend.html';
}

/**
 * 去我的关注
 * @param id
 */
function toMyFollowList()
{
    window.location.href = '../../html/lh/myFollow.html';
}

/**
 * 去联系客服(新)
 * @param
 */
function toService()
{
    window.location.href = '../../html/lh/toService.html';
}

/**
 * 去v5特区
 * @param
 */
function toAuctionItemV5()
{
    window.location.href = '../../html/lh/auctionItemV5List.html';
}


/**
 * 去v5特区留言板
 * @param
 */
function toAuctionItemV5Board(id)
{
    window.location.href = '../../html/lh/v5Board.html?id='+id;
}

/**
 * 去我要鉴定
 * @param
 */
function toMyIdentify()
{
    window.location.href = 'myIdentify.html';
}


/**
 * 去平台须知
 * @param
 */
function toPlatformNotes()
{
    window.location.href = '../../html/lh/platformKnowledge.html';
}

/**
 * 去推荐商品
 * @param
 */
function toPickLot()
{
    window.location.href = '../../html/lh/pickLot.html';
}


/**
 * 去商家详情
 * @param id
 */
function toBusinessDetail(id)
{
    window.location.href = '../../html/lh/businessDetail.html?id='+id;
}

/**
* 去商家详情
* @param id
*/
function toMessageDetail(id)
{
    window.location.href = '../../html/lh/messageDetail.html?id='+id;
}


/**
 * 去会展详情
 * @param id
 */
function toAuctionDetail(id)
{
    window.location.href = '../../html/lh/auctionDetail.html?id='+id;
}

/**
 * 去订单详情
 * @param id
 */
function toOrderDetail(orderId) {
    window.location.href = '../../html/lh/orderDetail.html?id='+orderId;
}

/**
 * 去活动列表
 * @param id
 */
function toActivityList() {
    window.location.href = '../../html/lh/activityList.html';
}

/**
 * 去积分商城
 * @param id
 */
function toIntegralMall() {
    window.location.href = '../../html/lh/integralMall.html';
}

/**
 * 去积分商城商品详情
 * @param id
 */
function integralDetail(id) {
    window.location.href = '../../html/lh/exchange.html?id='+id;
}

/**
 * 去大额支付
 * @param id
 */
function toBigTransactionl() {
    window.location.href = '../../html/lh/bigTransaction.html';
}
/**
 * 去活动详情
 * @param id
 */
function toActivityDetail(actId) {
    window.location.href = '../../html/lh/activityDetail.html?id='+actId;
}

/**
 * 去用户详情
 * @param id
 */
function toUserInfo(wxId) {
    window.location.href = '../../html/lh/myInfo.html?wxId='+wxId;
}

/**
 * 去兑换记录
 * @param wxid
 */
function toMyExchange() {
    window.location.href = '../../html/lh/myExchange.html';
}


function toAuctionItemDetail(id,type)
{
    //商品性质:0-拍卖品,1-商品,2-非卖品,3是v5商品
    if(type == "0"){
        window.location.href = '../../html/lh/auctionItemDetail.html?id='+id;
    }else if(type == 3){
        window.location.href = '../../html/lh/commodityV5Detail.html?id='+id+'&type=3';
    }else{
        window.location.href = '../../html/lh/commodityDetail.html?id='+id;
    }

}

function showToast(msg, callback) {
    var toast = $('<div id="toast" style="opacity: 1; ">' +
                    '<div class="weui-mask_transparent"></div>' +
                    '<div class="weui-toast">' +
                        '<i class="weui-icon-success-no-circle weui-icon_toast"></i>' +
                        '<p class="weui-toast__content">' + msg + '</p>' +
                    '</div>' +
                   '</div>');
    $("body").append(toast);
    setTimeout(function () {
        $("#toast").remove();
        callback();
    }, 1000);

}

function showPublish() {
  $('#publishActionsheet').addClass('weui-actionsheet_toggle');
  $('#publishActionsheet').addClass('mb60');
  $('#publishMask').fadeIn(200);
}

function hideActionSheet() {
  $('#publishActionsheet').removeClass('weui-actionsheet_toggle');
  $('#publishActionsheet').removeClass('mb60');
  $('#publishMask').fadeOut(200);
}

function toMyStore() {
  window.location.href = '/weixin/foreground/html/lh/myStore.html';
}

/**
 * 我去的退款
 */
function toMyReturn() {
    window.location.href = '/weixin/foreground/html/lh/myReturn.html';
}


function toUpload(attr) {
  window.location.href = '/weixin/foreground/html/lh/uploadTest.html?attr=' + attr;
}

function toCreateAuction() {
  window.location.href = '/weixin/foreground/html/lh/createAuction.html';
}

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}


