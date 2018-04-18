var token = null;
var openId = null;
var isBanding = false;
function getOpenId(coded) {
	if(is_weixn()){
		WebServiceUtil.madeUrl(coded, callbackOpenId);
	}else{
		location.href = "http://jhwxtest.hzcdt.com/weixin/foreground/html/error.html";
	}
};

// 尚未做错误验证
function callbackOpenId(msg) {
	var jsonObject = eval("(" + msg + ")");
	var openId = jsonObject.openid;
	setCookie("openId",openId);
	getBindedInfo(openId);
}

function getBindedInfo(id) {
//	ProgressBar.show();
	token = $.md5(id);
	setCookie("token",token);
//	return getBindedInfoByToken(token);
};

function getBindedInfoByToken(token){
	if(is_weixn()){
		var params = "{\"token\":\"" + token + "\"}";

		return WebServiceUtil.getBindedInfo(params, callbackBindedInfo);

	}else{
		location.href = "http://jhwxtest.hzcdt.com/weixin/foreground/html/error.html";

	}
}

function callbackBindedInfo(msg) {
	ProgressBar.hide();
	var jsonObject = eval("(" + msg + ")");
	if (jsonObject.head.code != 1) {
		Showbo.Msg.alert(jsonObject.head.message);
		//eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	if (jsonObject.head.rownum == 0) {
		isBanding = false;
		init(jsonObject);
	}else{
		isBanding = true;
		init(jsonObject.body);
	}
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
    
function setCookie(name,value) 
{ 
    var Days = 30; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
} 

function delCookie(name) 
{ 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval=getCookie(name); 
    if(cval!=null) 
        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 

function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}
