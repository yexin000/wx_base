var InterValObjBind; // timer变量，控制时间
var countBind = 30; // 间隔函数，1秒执行
var curCountBind;// 当前剩余秒数
var valimark = "";

function sendMessageAuto() {
	// 设置button效果，开始计时
	curCountBind = countBind;
	$("#codeButton").attr("disabled", true);
	$("#codeButton").removeClass("inputBut").addClass("inputButAfter");
	$("#codeButton").val(curCountBind + "秒");
	InterValObj = window.setInterval(SetRemainTimeAuto, 1000); // 启动计时器，1秒执行一次
}

//timer处理函数
function SetRemainTimeAuto() {
    if (curCountBind == 0) {                
		window.clearInterval(InterValObjBind);//停止计时器
		$("#codeButton").attr("disabled", false);//启用按钮
		$("#codeButton").removeClass("inputButAfter").addClass("inputBut");
		$("#codeButton").val("重新发送");
    }
    else {
    	curCountBind--;
        $("#codeButton").val(curCountBind + "秒");
    }
}

//绑定机动车
bindUserVehicle = function(hpzl, hphm, clsbdh, sjhm, sjlx, yzm) {
	valimark = sjlx;
	var params = "{\"token\":\"" + token + "\"" + ",\"hpzl\":\"" + hpzl + "\""
			+ ",\"hphm\":\"" + hphm + "\"" + ",\"clsbdh\":\"" + clsbdh + "\""
			+ ",\"sjhm\":\"" + sjhm + "\""
			+ ",\"sjlx\":\"" + sjlx + "\""+ ",\"yzm\":\"" + yzm + "\"}";
	ProgressBar.show();
	return WebServiceUtil.bindUserVehicle(params, callback);
};

function callback(msg) {
	//alert(msg);
	var jsonObject = eval("(" + msg + ")"),li_array = new Array();
	var ja = jsonObject.body;
	ProgressBar.hide();
	if (jsonObject.head.code != 1) {
		Showbo.Msg.alert(jsonObject.head.message);
		//eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	
	if (jsonObject.head.code == 1) {
		
	//	Showbo.Msg.alert(jsonObject.head.message);
	//	eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
	}
	
	if(valimark == "0"){
	$("#vehicle_input").hide();
	//setVehicleMsg(syr,decode(hpzl)+"&nbsp&nbsp"+hphm,clsbdh,sjhm);
	setVehicleMsg(decode(hpzl)+"&nbsp&nbsp"+hphm,clsbdh,sjhm);
	sendMessageAuto();
	$("#code_input").show();
	}
	else if(valimark == "1"){
		Showbo.Msg.alert("机动车绑定成功");
		//eval($("#tmpt").text($.Prompt("机动车绑定成功")));
		setCookie("token",token);
		window.location.href = "./UserBind.html";
	}
//	for ( var i in ja) {
//
//		var jo = ja[i];
//		li_array.push(tplVehicleInfo(jo.syr,jo.sjhm,jo.hpzl,jo.hphm,jo.clsbdh));
//		//$("#vehicles").append(tplVehicleInfo(jo.syr,jo.sjhm,jo.hpzl,jo.hphm,jo.clsbdh));
//	}
	
//	eval($("#tmpt").text($.Prompt("机动车绑定成功")));
//	window.location.href = document.referrer;
//	
//	$("#vehicles").html(li_array.join(''));
//	vehicleNum++;
//	if(vehicleNum>=3){
//		$("#vehicle_input").hide();
//		$("#bind_vehicle").hide();
//		$("#vehicles").show();
//		$("#vehicle_home").show();
//		
//	}
//	else{
//		$("#vehicle_input").hide();
//		$("#bind_vehicle").show();
//		$("#vehicles").show();
//		$("#vehicle_home").show();
//	}


};