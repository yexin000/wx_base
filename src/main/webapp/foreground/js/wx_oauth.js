var wxOauth = {};

wxOauth.getQueryString = function(name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
	
};

wxOauth.openId = "";
wxOauth.redirectUrl = "";
/*
 * 通过微信oAUTH2.0认证并且获取传回来的code
 * 第一次进去没有code，通过微信认证以后传回来code
 * 第二次进入以后通过获取openid验证code是否有效，防止人为的在链接后面添加code以逃避认证，直接进入网页
 * */
wxOauth.getCodeAndRedirect = function(url){
	wxOauth.redirectUrl = url;
	var code = wxOauth.getQueryString("code");
	if( code == null){
		var redirect12 = encodeURIComponent(url);
		var codeurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx52d9a1721ad602a4"+"&redirect_uri="+redirect12+"&response_type=code&scope=snsapi_base&state=0#wechat_redirect";
	    location.href = codeurl;
    }
	else{
		wxOauth.getOpenId(code);
	};
};

wxOauth.getOpenId = function(code){
	if(coded==null||coded==undefined ||coded == ""){
		alert("数据传输异常，请重试");
		return;
	}
		var tockenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx52d9a1721ad602a4&secret=2d1ac12097dc2e317a8f95f41d8a1660&code="
				+ coded + "&grant_type=authorization_code";
		WebServiceUtil.sendGet(tockenUrl, wxOauth.callbackOpenId);
	
};


wxOauth.callbackOpenId = function (msg) {
	var jsonObject = eval("(" + msg + ")");
	try{
	wxOauth.openId = jsonObject.openid;
	}catch(e){
        alert(e.message);
        location.href = wxOauth.redirectUrl;
        return;
    }
};

