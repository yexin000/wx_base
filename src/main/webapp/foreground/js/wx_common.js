//var url_commom = "http://nbwxtest.hzcdt.com";
var wxCommon = {};
wxCommon.url_common = "http://nbwxtest.hzcdt.com";

wxCommon.is_weixn = function() {
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
    	location.href = "http://nbwxtest.hzcdt.com/html/error.html";
        return false;
    }
}
wxCommon.is_weixn();