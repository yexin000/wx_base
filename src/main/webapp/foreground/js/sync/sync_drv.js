	var syncCache = false;
	var timerLimitCount;
	var validate;
	//开始同步驾驶人信息
	function saveSyncVioInfo(){
		timerLimitCount = 0;
		var sfzmhm = document.getElementById("sfzmhm");
		var sjhm = document.getElementById("sjhm");
		var xm = document.getElementById("xm");
		if(sfzmhm.value == ""){
			alert("提示：请输入您的驾驶证号码！");
			sfzmhm.focus();
			return false;
		}
		if(check_sfzh(sfzmhm) == 0){
			return false;
		}
		if(busi == 1 || busi == 2 || busi == 12 ){
			if(xm.value == ""){
				alert("提示：请输入驾驶人姓名！");
				xm.focus();
				return;
			}
			xm.disabled = true;
		}
		if(typeof(busi) != "undefined" && busi == 2 ){	//验证档案编号  驾驶人信息变更需要输入档案编号
			var dabh = document.getElementById("dabh");
			if(dabh.value == ""){
				alert("提示：请输入您的驾驶证档案编号！");
				dabh.focus();
				return false;
			}
			if(isNaN(dabh.value) || dabh.value.length != 12){
				alert("提示：请输入12位数字的档案编号！");
				dabh.focus();
				return false;
			}
			dabh.disabled = true;
		}
		if(sjhm.value == ""){
			alert("提示：请输入车辆初次登记时填写的手机号码！");
			sjhm.focus();
			return false;
		}
		if(!/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/.test(sjhm.value)){
			alert("提示：请输入正确的手机号码！");
			sjhm.focus();
			return false;
		}
		//if(busi == 12){
		//	var dzyx = document.getElementById("dzyx");
		//	if(dzyx.value == ""){
		//		alert("提示：请输入电子邮箱地址！");
		//		dzyx.focus();
		//		return;
		//	}
		//	dzyx.disabled = true;
		//}
		
		sfzmhm.disabled = true;
		sjhm.disabled = true;
		document.getElementById("confirm_next").disabled = true;
		//已同步成功的数据
		if(syncCache){
			getDrvInfo();
			return;
		}
		
		//显示同步等待
		Shade.show(waitDrv);
		InterfaceServiceJS.saveSync(sfzmhm.value,"","","2",function(result){
			//保存同步信息成功
			if(typeof(result) != "undefined" && result == "1"){
				checkSyncStatu();	//开始检查同步状态
			}
			//保存同步信息失败
			if(typeof(result) != "undefined" && result == "0"){
				alert("当前操作用户太多,请稍候再试!");
				checkFailed();
			}
		});
	}
	
	var checkSyncTimer = null;
	//校验同步状态
	function checkSyncStatu(){
		timerLimitCount++;
		var sfzmhm = document.getElementById("sfzmhm");
		//检查是否同步
		InterfaceServiceJS.getSyncStatus(sfzmhm.value.toUpperCase(),"","","2",function(statu){
			//未同步成功
			if(statu == null || statu == "undefined" || statu == "0"){
				if(checkSyncTimer == null){
					//定时器 timerLimitSecond秒钟去检查一次
					checkSyncTimer = setInterval("checkSyncStatu()",timerLimitSecond*1000);
				}
			}
			//同步成功
			if(typeof(statu) != "undefined" && statu == "1"){
				//清除定时器
				clearTimer();
				syncCache = true;
				window.setTimeout("getDrvInfo()",2000);	//延迟2秒获取驾驶人信息
			}
			//未找到车辆信息
			if(typeof(statu) != "undefined" && statu == "2"){
				checkFailed();
				alert("提示：系统未同步到您的驾驶证信息,请检查你输入的信息是否正确!");
				clearTimer();
				syncCache = false;
			}
			//同步网络中断
			if(typeof(statu) != "undefined" && statu == "9"){
				checkFailed();
				alert("提示：网络中断，请稍候再试。");
				clearTimer();
				checkFailed();
			}
			if(timerLimitCount > timerLimitCountMax){
				checkFailed();
				alert("提示：网络超时，请重试。");
				clearTimer();	
				syncCache = false;
			}
		});
	}
	
	//校验失败
	function checkFailed(){
		document.getElementById("sfzmhm").disabled = false;
		document.getElementById("sjhm").disabled = false;
		if(typeof(busi) != "undefined" && busi == 2 ){	//验证档案编号  驾驶人信息变更需要输入档案编号
			document.getElementById("dabh").disabled = false;
		}
		document.getElementById("confirm_next").disabled = false;
		Shade.hide();
	}
	
	//获取驾驶证信息
	function getDrvInfo(){
		var sfzmhm = document.getElementById("sfzmhm");
		var sjhm = document.getElementById("sjhm");
		InterfaceServiceJS.getDrvInfo("",sfzmhm.value.toUpperCase(),function(drver){
			if(drver != null && typeof(drver) != "undefined"){
				if(!isQuery){
					if(null == drver.isAllowed || drver.isAllowed == false){
						alert("提示：该驾驶证不能在网上申请，请到车管所窗口办理！");
						document.getElementById("sjhm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				//驾驶证状态是否为正常状态
				/**
				if(drver.ZT != 'A' ){
					alert("提示：驾驶证状态需要在[正常]情况下才可办理该业务!");
					checkFailed();
					syncCache = false;
					return;
				}
				**/
				//驾驶证补证需要温州户籍才可申请（根据暂住证辨别，如果暂住证不为空，则非本地户籍）
				/**
				if(busi == 1 && drver.ZZZM != null && drver.ZZZM != "" ){
					alert("提示：非本地户籍驾驶证不可在网上申请，请到车管所窗口办理！");
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				**/
				if(busi == 1 || busi == 2  || busi == 12){   //驾驶证补证、联系方式变更需要验证 姓名
					if(document.getElementById("xm").value != drver.XM){
						alert("提示：驾驶人姓名比对失败，请重试！");
						document.getElementById("xm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				if(null == drver.SJHM || "" == drver.SJHM){
					alert("提示：您的驾驶证在初次登记时未登记手机号码,请到车管所及时更新！");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				if(typeof(busi) != "undefined" && busi == 2 ){	//验证档案编号  驾驶人信息变更需要输入档案编号
					var dabh = document.getElementById("dabh");
					//比对档案编号
					if(dabh.value != drver.DABH){
						alert("提示：您输入的档案编号比对失败,请检查后重新输入！");
						document.getElementById("dabh").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				//比对手机号码
				var veh_sjhm = drver.SJHM;
				//比对成功
				if(sjhm.value == veh_sjhm){
					if(busi == 12 ){
						sendValidateKaoPingMsg();
					}else{
						sendValidateMsg();
					}
				}else {
					alert("提示：手机号码比对失败，请检查后重新输入！\r\n如果你的信息与页面不符,请到车管所及时更新！");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
				}
			}else{
				alert("提示：获取驾驶证信息失败，请重新提交同步申请！");
				checkFailed();
				syncCache = false;
			}
		});
	}
	
	//发送短信
	function sendValidateMsg(){
		document.getElementById("btn_resend").disabled = true;
		i= 60;
		timer = window.setInterval("timerInterval()",1000);
		InterfaceServiceJS.sendMsg("2",function(data){
			if(typeof(data) != "undefined" && !isNaN(data) && data != "ERR"){
				validate = data;
				document.getElementById("tr_yzm").style.display = "block";
				document.getElementById("confirm_next").disabled = true;
				document.getElementById("yzm").focus();
			}else{
				alert("提示：发送短信超时，请稍后再试！");
			}
		});
		Shade.hide();
	}
	
	
	//发送短信
	function sendValidateKaoPingMsg(){
		var chktype = document.getElementById('chktype');
		var chkvalue = chktype.options[chktype.selectedIndex].value;
		document.getElementById("btn_resend").disabled = true;
		i= 60;
		timer = window.setInterval("timerInterval()",1000);
		InterfaceServiceJS.sendMsg(chkvalue,function(data){
			if(typeof(data) != "undefined" && !isNaN(data) && data != "ERR"){
				validate = data;
				document.getElementById("tr_yzm").style.display = "block";
				document.getElementById("confirm_next").disabled = true;
				document.getElementById("yzm").focus();
			}else{
				alert("提示：发送短信超时，请稍后再试！");
			}
		});
		Shade.hide();
	}
	
	
	//重新输入信息
	function toBegin(){
		window.location.reload();
	}
	
	//清空定时器
	function clearTimer(){
		window.clearInterval(checkSyncTimer);
		checkSyncTimer = null;
	}
	
	window.onload = function(){
		if(document.getElementById("xm") == null || document.getElementById("xm") == "undefined" ){
			document.getElementById("sfzmhm").focus();
		}else{
			document.getElementById("xm").focus();
		}
		
	}
	
	function checkChineseHphm(){
		var hphm = document.getElementById('hphm');
		var errMsg = document.getElementById('errMsgF');
		if(/[\u4E00-\u9FA5\uF900-\uFA2D]/g.test(hphm.value)){
			errMsg.innerHTML = '驾驶证号输入不能包含中文！';
			hphm.focus();
		}else{
			errMsg.innerHTML = '';
		}
	}
	
	function checkValidate(url){
		var input_validate = document.getElementById("yzm");
		if(input_validate.value == ""){
			alert("提示：请输入手机接收到的验证码！");
			input_validate.focus();
			return;
		}
		if(input_validate.value == validate){
			document.getElementById("btn_validate").disabled = true;
			document.getElementById("btn_validate").value = " 处理中... ";
			window.location.href = url;
		}else{
			alert("提示：验证码输入错误！");
		}
	}