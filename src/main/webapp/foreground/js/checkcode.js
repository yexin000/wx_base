var InterValObj; // timer变量，控制时间
var count = 30; // 间隔函数，1秒执行
var curCount;// 当前剩余秒数

function sendMessage(mark) {
	var sjhm = $("label#txt_sjhm").text();
	if (isValidMobileNum(sjhm)) {
		curCount = count;

		switch (mark) {
		case "vehicle":
			//bindUserVehicle(syr,hpzl,hphm.substring(1,7),clsbdh,sjhm,"0","");
			bindUserVehicle(hpzl,hphm.substring(1,7),clsbdh,sjhm,"0","");
			break;
		case "driver":
			bindUserDriver(jszh,xm,dabh,sjhm,"0","");
			break;
		case "student":
			bindUserStudent(xm, sfzh, sjhm,"0","");
			break;

		default:
			break;
		}
		

		// 设置button效果，开始计时
		$("#codeButton").attr("disabled", true);
		$("#codeButton").removeClass("inputBut").addClass("inputButAfter");
		$("#codeButton").val(curCount + "秒");
		InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
	} else {
		Showbo.Msg.alert("请输入正确的手机号码");
		//eval($("#tmpt").text($.Prompt("请输入正确的手机号码")));
	}
}



//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {                
		window.clearInterval(InterValObj);//停止计时器
		$("#codeButton").attr("disabled", false);//启用按钮
		$("#codeButton").removeClass("inputButAfter").addClass("inputBut");
		$("#codeButton").val("重新发送");
    }
    else {
        curCount--;
        $("#codeButton").val(curCount + "秒");
    }
}