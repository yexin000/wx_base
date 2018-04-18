
var valimark = "";
//绑定驾驶证
bindUserDriver = function(sfzmhm, xm, dabh, sjhm,sjlx,yzm) {
	//var token = $.md5(openId);
valimark = sjlx;
	//alert(token);
	var params = "{\"token\":\"" + token + "\"" + ",\"sfzmmc\":\"A\""
			+ ",\"sfzmhm\":\"" + sfzmhm + "\"" + ",\"xm\":\"" + xm + "\""
			+ ",\"sjlx\":\"" + sjlx + "\""+ ",\"yzm\":\"" + yzm + "\""
			+ ",\"dabh\":\"" + dabh + "\"" + ",\"sjhm\":\"" + sjhm + "\"}";
	ProgressBar.show();
	return WebServiceUtil.bindUserDriver(params, callbackDriver);
};

function callbackDriver(msg) {

	//alert(msg);
	var jsonObject = eval("(" + msg + ")"), li_array = new Array();
	var ja = jsonObject.body;
	ProgressBar.hide();
	if (jsonObject.head.code != 1) {
		Showbo.Msg.alert(jsonObject.head.message);
		//eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	
	if (jsonObject.head.code == 1) {
		//Showbo.Msg.alert(jsonObject.head.message);
		//eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
	}
	
	
	if(valimark == "0"){
		$("#driver_input").hide();
		//alert(valimark);
		setDriverMsg(xm,jszh,sjhm,dabh);
		$("#code_input").show();
	}else if(valimark == "1"){
		Showbo.Msg.alert("驾驶证绑定成功");
	//	eval($("#tmpt").text($.Prompt("驾驶证绑定成功")));
		setCookie("token",token);
		window.location.href = "./UserBind.html";
	}
	
	

//	for ( var i in ja) {
//
//		var jo = ja[i];
//		setDriverMsg(jo.xm, jo.sjhm, jo.sfzmhm);
//		$("#unbind_driver").attr("data-sfzmmc",jo.sfzmmc);//可能出问题
//		$("#unbind_driver").attr("data-sfzmhm","s"+jo.sfzmhm);
//	}
//    
//	eval($("#tmpt").text($.Prompt("绑定成功")));
//	showDriverInfo();
//	driverNum++;

};