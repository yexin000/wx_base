var openId = "";
var vehicleNum = 0;
var driverNum = 0;
var studentNum = 0;
var unbindvehicle_hphm = "";
var bindRole = "";
var role = "";
//var token = "de3e6efa2a574f9095e23efff860398d";

tplVehicleInfo = function(syr,sjhm,hpzl,hphm,clsbdh,zt) {
	ProgressBar.hide();
	//alert(syr);
	return '<div id="vehicle_info_'+hphm+'" style="border-style: solid;border-radius: 3px;border-width:1px;border-color: #ccc #ccc #ccc #ccc;width:96%;margin-left: 2%;margin-top:5px;">'
			+ '<table align="center" data-theme="a" style="width: 100%;" ><tbody>'
			+ '<tr><td class="left">所有人：</td><td class="right">'
			+ syr
			+ '</td></tr><tr><td class="left">手机号码：</td><td class="right" id = "txt_sjhm_vehicle">'
			+ sjhm
			+ '</td></tr><tr><td class="left">号牌种类：</td><td class="right">'
			+ decode(hpzl)
			+ '</td></tr><tr ><td class="left">号牌号码：</td><td class="right">浙'
			+ hphm
			+ '</td></tr><tr ><td class="left">车辆识别代号：</td><td class="right">'
			+ clsbdh
			+ '</td></tr><tr ><td class="left">状态：</td><td class="right">'
			+ zt
			+ '</td></tr></tbody></table><div class="center"><button  class="inputBtn" style="width: 40%;margin-left: 30%;" data-hpzl="s'+hpzl+'" data-hphm="'+hphm+'"> 解绑信息</button></div></div>'

};

//获取openid
//getOpenId = function(code) {
//	var tockenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx52d9a1721ad602a4&secret=2d1ac12097dc2e317a8f95f41d8a1660&code="
//			+ code + "&grant_type=authorization_code";
//	return WebServiceUtil.sendGet(tockenUrl, callbackOpenId);
//
//};
//
//// 尚未做错误验证
//function callbackOpenId(msg) {
//	var jsonObject = eval("(" + msg + ")");
//	openId = jsonObject.openid;
//	getBindedInfo();
//};






//获取用户绑定信息，包括机动车、驾驶证、学员
//getBindedInfo = function() {
//	var token = $.md5(openId);
//	var params = "{\"token\":\"" + token + "\"}";
//	return WebServiceUtil.getBindedInfo(params, callbackBindedInfo);
//};

//function callbackBindedInfo(msg) {
//
//	alert(msg);
//	var jsonObject = eval("(" + msg + ")"), li_array = new Array();
//	var ja = jsonObject.body;
//	if (jsonObject.head.code != 1) {
//		eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
//		return;
//	}
//
//	for ( var i in ja) {
//
//		var jo = ja[i];
//		userInfo(jo,li_array);
//
//	}
//	
//	if(studentNum>0){
//		if(vehicleNum<3){
//			showStudentBindedVehicle();
//		}
//		else{
//			showStudentBindedVehicleMoreThree();
//		}
//	}
//	else if(driverNum>0){
//		if(vehicleNum<3){
//			showDriverBindedVehicle();
//		}
//		else{
//			showDriverBindedVehicleMoreThree();
//		}
//	}
//	else {
//		if(vehicleNum<3){
//			showStudentVehicleDriverInfo();
//		}
//		else{
//			showStudentBindedVehicleMoreThree();
//		}
//	}
//	
////	if(vehicleNum<3){
////		$("#bind_vehicle").show();
////		$("#vehicles").show();
////		$("#vehicle_home").show();
////		}
////	else{
////		$("#bind_vehicle").hide();
////		$("#vehicles").show();
////		$("#vehicle_home").show();
////	} 
//
//	var $vehicles = $("#vehicles");
//	$vehicles.html(li_array.join(''));
//	$vehicles.delegate('button', 'click', function(e) {
//	unbindVehicle($(this).data("hpzl").substring(1),$(this).data("hphm"));
//	});
//};
function init(msg) {
	//alert(msg);
	var  li_array = new Array();
	//jsonObject = eval("(" + msg + ")")
	var ja = msg;
//	if (msg.head.code != 1) {
//		eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
//		return;
//	}
	
	//用户未绑定
	if (!isBanding) {
		bindRole = "unBind";
		ProgressBar.hide();
		$("#unbind").show();
		return;
		
	}
	
	for ( var i in ja) {
		
		var jo = ja[i];
		userInfo(jo,li_array);
		
	}
	
	
	if(studentNum>0){
		if(vehicleNum>0){
			showStudentVehicleInfo();
			bindRole = "studentVehicle";
		}
		else{
			showStudentInfo();
			bindRole = "student";
		}
	}
	else if(driverNum>0){
		if(vehicleNum>0){
			showDriverVehicleInfo();
			bindRole = "driverVehicle";
		}
		else{
			showDriverInfo();
			bindRole = "driver";
		}
	}
	else {
		showVehicleInfo();
		bindRole = "vehicle";
	}
	
//	if(vehicleNum<3){
//		$("#bind_vehicle").show();
//		$("#vehicles").show();
//		$("#vehicle_home").show();
//		}
//	else{
//		$("#bind_vehicle").hide();
//		$("#vehicles").show();
//		$("#vehicle_home").show();
//	} 
	
	var $vehicles = $("#vehicles");
	$vehicles.html(li_array.join(''));
	$vehicles.delegate('button', 'click', function(e) {
		unbindVehicle($(this).data("hpzl").substring(1),$(this).data("hphm"));
	});
};

function userInfo(obj,array) {
	if (obj.sjlx == "机动车") {
		
		array.push(tplVehicleInfo(obj.xm,obj.sjhm,obj.zm,obj.hm,obj.bh,obj.ztcn));
		vehicleNum++;
        
	} else if (obj.sjlx == "驾驶证") {
		
		driverNum++;
		setDriverMsg(obj.xm, obj.sjhm, obj.hm, obj.ztcn);
		$("#unbind_driver").attr("data-sfzmmc",obj.zm);
		$("#unbind_driver").attr("data-sfzmhm","s"+obj.hm);
		//alert(obj.hm);
	} else if(obj.sjlx == "学员"){
		
		studentNum++;
		setStudentMsg(obj.xm, obj.sjhm, obj.hm);
		$("#unbind_student").attr("data-sfzmmc",obj.zm);
		$("#unbind_student").attr("data-sfzmhm","s"+obj.hm);
		//alert(obj.hm);
	};

};



//解绑机动车
function  unbindVehicle(hpzl,hphm){
	unbindvehicle_hphm = hphm;
	//var token = $.md5(openId);
	var params = "{\"token\":\"" + token  + "\"" + ",\"hpzl\":\""+ hpzl +"\""
	+ ",\"hphm\":\"" + hphm +"\"}";
	//alert(params);
	return WebServiceUtil.unBindUserVehicle(params, callbackUnbindVehicle);
};

function callbackUnbindVehicle(msg) {

	//alert(msg);
	var jsonObject = eval("(" + msg + ")"), li_array = new Array();
	var ja = jsonObject.body;
	if (jsonObject.head.code != 1) {
		Showbo.Msg.alert(jsonObject.head.message);
	//	eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	
	Showbo.Msg.alert("机动车已经解除绑定");
	//eval($("#tmpt").text($.Prompt("机动车已经解除绑定")));
	$("#vehicle_info_"+unbindvehicle_hphm).remove();
	vehicleNum--;
	if(vehicleNum<1){
//		$("#bind_vehicle").show();
//		$("#vehicles").show();
		$("#vehicle_home").hide();
		if(driverNum<1&&studentNum<1){
			delCookie("token");
		};
	};
	showUnbindDisplay();
	
};

//解绑驾驶人、学员
function  unbindDriver(sfzmmc,sfzmhm){
	//var token = $.md5(openId);
	var params = "{\"token\":\"" + token  + "\"" + ",\"sfzmmc\":\""+ sfzmmc +"\""
	+ ",\"sfzmhm\":\"" + sfzmhm +"\"}";
	//alert(params);
	return WebServiceUtil.unBindUserDriver(params, callbackUnbindDriver);
	
};

function callbackUnbindDriver(msg) {

	//alert(msg);
	var jsonObject = eval("(" + msg + ")"), li_array = new Array();
	var ja = jsonObject.body;
	if (jsonObject.head.code != 1) {
		Showbo.Msg.alert(jsonObject.head.message);
	//	eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	if(role == "driver"){
		Showbo.Msg.alert("解绑驾驶证成功");
	//eval($("#tmpt").text($.Prompt("解绑驾驶证成功")));
	$("#driver_home").hide();
	driverNum--;
	}
	else if (role == "student") {
		Showbo.Msg.alert("解绑学员信息成功");
		//eval($("#tmpt").text($.Prompt("解绑学员信息成功")));
		$("#student_home").hide();
		studentNum--;
	}
	bindRole = "vehicle";
	if(vehicleNum<1){
		delCookie("token");
	};
	showUnbindDisplay();
};


function showDriverVehicleInfo() {
	$("#vehicle_home").show();
	$("#driver_home").show();
	$("#student_home").hide();
};
function showStudentVehicleInfo() {
	$("#vehicle_home").show();
	$("#driver_home").hide();
	$("#student_home").show();
};
function showVehicleInfo() {
	$("#vehicle_home").show();
	$("#driver_home").hide();
	$("#student_home").hide();
};
function showStudentInfo() {
	$("#vehicle_home").hide();
	$("#driver_home").hide();
	$("#student_home").show();
};
function showDriverInfo() {
	$("#vehicle_home").hide();
	$("#driver_home").show();
	$("#student_home").hide();
};


//function showDriverVehicleInfo() {
//	$("#vehicle_home").show();
//	$("#driver_home").show();
//	$("#vehicle_input").hide();
//	$("#driver_input").hide();
//	$("#bind_driver").hide();
//	$("#driver_info").show();
//};


function showUnbindDisplay(){
	
	if(vehicleNum<1&&studentNum<1&&driverNum<1){
		$("#unbind").show();
	}
	
}


function setDriverMsg(xm, sjhm, jszh, zt) {
	ProgressBar.hide();
	$("#txt_xm").html(xm);
	$("#txt_sjhm_driver").html(sjhm);
	$("#txt_jszh").html(jszh);
	$("#txt_zt").html(zt);
	
};

function setStudentMsg(xm, sjhm, sfzh) {
	ProgressBar.hide();
	$("#txt_student_xm").html(xm);
	$("#txt_student_sjhm").html(sjhm);
	$("#txt_student_sfzh").html(sfzh);
};


function setStudentMsg(xm, sjhm, sfzh) {
	ProgressBar.hide();
	$("#txt_student_xm").html(xm);
	$("#txt_student_sjhm").html(sjhm);
	$("#txt_student_sfzh").html(sfzh);
};

