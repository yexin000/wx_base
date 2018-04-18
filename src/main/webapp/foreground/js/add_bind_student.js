var valimark = "";


//绑定学员
bindUserStudent = function(xm, sfzmhm, sjhm,sjlx,yzm) {
	valimark = sjlx;
	var params = "{\"token\":\"" + token + "\"" + ",\"sfzmmc\":\"" + "A" + "\""
			+ ",\"sfzmhm\":\"" + sfzmhm + "\"" 
			+  ",\"sjhm\":\"" + sjhm + "\""
			+ ",\"sjlx\":\"" + sjlx + "\""+ ",\"yzm\":\"" + yzm + "\""
			+ ",\"xm\":\"" + xm + "\"}";
	
//	alert(params);
	
	ProgressBar.show();
	return WebServiceUtil.bindUserStudent(params, callback);
};

function callback(msg) {
	//alert(msg);
	ProgressBar.hide();
	var jsonObject = eval("(" + msg + ")"),li_array = new Array();
	var ja = jsonObject.body;
	if (jsonObject.head.code != 1) {
		Showbo.Msg.alert(jsonObject.head.message);
		//eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	
	if (jsonObject.head.code == 1) {
		//Showbo.Msg.alert(jsonObject.head.message);
	//	eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
	}
	
	if(valimark == "0"){
		$("#student_input").hide();
		setStudentMsg(xm,sjhm,sfzh);
		$("#code_input").show();
	}
	else if(valimark == "1"){
		Showbo.Msg.alert("学员绑定成功");
		//eval($("#tmpt").text($.Prompt("学员绑定成功")));
		setCookie("token",token);
		window.location.href = "./UserBind.html";
	}


};