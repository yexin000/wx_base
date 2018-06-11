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

function toBusinessDetail(id)
{
    window.location.href = '../../html/lh/businessDetail.html?id='+id;
}
function toAuctionDetail(id)
{
    window.location.href = '../../html/lh/auctionDetail.html?id='+id;
}
function toAuctionItemDetail(id,type)
{
    if(type == "0"){
        window.location.href = '../../html/lh/auctionItemDetail.html?id='+id;
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