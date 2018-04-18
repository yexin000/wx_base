var InterValObj; // timer变量，控制时间
var count = 30; // 间隔函数，1秒执行
var curCount;// 当前剩余秒数

function sendcodeMessage() {
	var sjhm = $("#sjhm").val();
	if (isValidMobileNum(sjhm)) {
		curCount = count;

		var params = "{\"sjhm\":\"" + sjhm + "\"}";
		var result = WebServiceUtil.getZscgCode(params, callcode);

		// 设置button效果，开始计时
		$("#btnSendCode").attr("disabled", true);
		$("#btnSendCode").css("background","#bbbbbb"); 
		$("#btnSendCode").text(curCount + "秒");
		// $("#btnSendCode").button("refresh");
		InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
	} else {
		Showbo.Msg.alert("验证码发送失败");
		//eval($("#tmpt").text($.Prompt("验证码发送失败")));
	}
}

function callcode(msg) {
	//alert(msg);
	if (msg != null) {
		var jsonObject = eval("(" + msg + ")");
		var head = jsonObject.head;
		if (head.code == 1) {
			Showbo.Msg.alert("验证码已发送"+jsonObject.body[0].yzm);
			//eval($("#tmpt").text($.Prompt("验证码已发送"+jsonObject.body[0].yzm)));
		} else {
			Showbo.Msg.alert("验证码发送失败");
			eval($("#tmpt").text($.Prompt("验证码发送失败")));
		}
	}
}

function SetRemainTime() {
	if (curCount == 0) {
		window.clearInterval(InterValObj);// 停止计时器
		$("#btnSendCode").attr("disabled", false);// 启用按钮
		$("#btnSendCode").css("background-color","#F8A114");
		$("#btnSendCode").text("重新发送");
		// $("#btnSendCode").button("refresh");
	} else {
		curCount--;
		$("#btnSendCode").text(curCount + "秒");
		// $("#btnSendCode").button("refresh");
	}
}