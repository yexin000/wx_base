	var syncCache = false;
	var timerLimitCount;
	var validate;
	//进行违法处理
	function handleVio(hpzl,hphm){
		document.handleVioForm.hpzl.value = hpzl;
		document.handleVioForm.hphm.value = hphm;
		document.handleVioForm.submit();
	}
	//开始同步违法信息
	function saveSyncVehInfo(){
		timerLimitCount = 0;
		var hpzl = document.getElementById("hpzl");
		var hphm = document.getElementById("hphm");
		var sjhm = document.getElementById("sjhm");
		if(hpzl.value == ""){
			alert("提示：请选择号牌种类！");
			hpzl.focus();
			return;
		}
		if(hphm.value == ""){
			alert("提示：请输入您的号牌号码！");
			hphm.focus();
			return;
		}
		if(/[\u4E00-\u9FA5\uF900-\uFA2D]/g.test(hphm.value)){
			hphm.focus();
			return;
		}
		var hphm_pre = hphm.value.substring(0,1);
		if(hphm_pre.toUpperCase() != "C"){
			alert("提示：本系统仅限温州车辆，请重新输入！");
			hphm.focus();
			return;
		}
		//业务办理需要验证相关信息
		if( busi == "5" || busi == "6" || busi == "7" || busi == "8" || busi == "12" ){
			if( busi != "12"){
				var clsbdh = document.getElementById("clsbdh");
				if(clsbdh.value == ""){
					alert("提示：请输入您的车辆识别代号（车架号）末6位。");
					clsbdh.focus();
					return;
				}
			}
			
			var xm = document.getElementById("xm");
			var sfzmhm = document.getElementById("sfzmhm");
			
			if(xm.value == ""){
				alert("提示：请输入您的姓名。");
				xm.focus();
				return;
			}
			if(sfzmhm.value == ""){
				alert("提示：请输入您的身份证号码。");
				sfzmhm.focus();
				return;
			}
			if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(sfzmhm.value)){
				alert("提示：您输入的身份证号码不合法！");
				sfzmhm.focus();
				return;
			}
			if( busi != "12"){
				clsbdh.disabled = true;
			}
			xm.disabled = true;
			sfzmhm.disabled = true;
		}
		if(sjhm.value == ""){
			alert("提示：请输入车辆初次登记时填写的手机号码！");
			sjhm.focus();
			return;
		}
		if(!/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/.test(sjhm.value)){
			alert("提示：请输入正确的手机号码！");
			sjhm.focus();
			return;
		}
		
		//if( busi == "12"){
		//	var dzyx = document.getElementById("dzyx");
		//	if(dzyx.value == ""){
		//		alert("提示：请输入电子邮箱地址！");
		//		dzyx.focus();
		//		return;
		//	}
		//	dzyx.disabled = true;
		//}
		hpzl.disabled = true;
		hphm.disabled = true;
		sjhm.disabled = true;
		document.getElementById("confirm_next").disabled = true;
		//已同步成功的数据
		if(syncCache){
			getVehicle();
			return;
		}
		
		//显示同步等待
		Shade.show(waitVeh);
		InterfaceServiceJS.saveSync("",hpzl.value,hphm.value.toUpperCase(),"1",function(result){
			//保存同步信息成功
			if(typeof(result) != "undefined" && result == "1"){
				checkSyncVehStatu();	//开始检查同步状态
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
	function checkSyncVehStatu(){
		timerLimitCount++;
		var hpzl = document.getElementById("hpzl");
		var hphm = document.getElementById("hphm");
		//检查是否同步
		InterfaceServiceJS.getSyncStatus("",hpzl.value,hphm.value.toUpperCase(),"1",function(statu){
			//未同步成功
			if(statu == null || statu == "undefined" || statu == "0"){
				if(checkSyncTimer == null){
					//定时器 timerLimitSecond秒钟去检查一次
					checkSyncTimer = setInterval("checkSyncVehStatu()",timerLimitSecond*1000);
				}
			}
			//同步成功
			if(typeof(statu) != "undefined" && statu == "1"){
				//清除定时器
				clearTimer();
				syncCache = true;
				window.setTimeout("getVehicle()",2000);  //延迟2秒获取机动车信息
			}
			//未找到车辆信息
			if(typeof(statu) != "undefined" && statu == "2"){
				checkFailed();
				alert("提示：系统未同步到您的车辆信息,请检查你输入的信息是否正确!");
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
		document.getElementById("hpzl").disabled = false;
		document.getElementById("hphm").disabled = false;
		document.getElementById("sjhm").disabled = false;
		document.getElementById("confirm_next").disabled = false;
		Shade.hide();
	}
	
	//获取机动车信息
	function getVehicle(){
		var hpzl = document.getElementById("hpzl");
		var hphm = document.getElementById("hphm");
		var sjhm = document.getElementById("sjhm");
		InterfaceServiceJS.getVehicle(hpzl.value,hphm.value.toUpperCase(),function(vehicle){
			if(vehicle != null && typeof(vehicle) != "undefined"){
				if(!isQuery){
					if(null == vehicle.isAllowed || vehicle.isAllowed == false){
						alert("提示：该业机动车不能在网上申请，请到车管所窗口办理！");
						document.getElementById("sjhm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				if( busi == "5" || busi == "6" || busi == "7" || busi == "8" || busi == "12" ){		//业务办理需要验证相关信息
					if( busi == "6" ){ 	//单位用户不得在网上申请 补行驶证号牌、异地委托年检，但可以申请联系方式变更
						if(vehicle.SFZMMC != document.getElementById("sfzmmc").value){
							alert("提示：身份证明名称比对失败，请重试！");
							document.getElementById("sfzmmc").focus();
							document.getElementById("re_input").style.display = "";
							document.getElementById("confirm_next").disabled = false;
							Shade.hide();
							return;
						}
					}else{
						if( vehicle.SFZMMC != "A" ){
							alert("提示：单位车辆不可在网上申请办理该业务，请到车管所窗口申请！");
							document.getElementById("re_input").style.display = "";
							document.getElementById("confirm_next").disabled = false;
							Shade.hide();
							return;
						}
					}
					
					if( busi != "12" ){
						//比对车辆识别代号末6位
						var clsbdh = vehicle.CLSBDH;
						var temp_clsbdh = "";
						if(clsbdh.length > 6 ){
							temp_clsbdh = clsbdh.substring(clsbdh.length - 6,clsbdh.length);
						}else{
							temp_clsbdh = clsbdh;
						}
						if(document.getElementById("clsbdh").value.toUpperCase() != temp_clsbdh){
						alert("提示：车辆识别代号（车架号）末6位比对失败，请重试！");
							document.getElementById("clsbdh").disabled = false;
							document.getElementById("re_input").style.display = "";
							document.getElementById("confirm_next").disabled = false;
							Shade.hide();
							return;
						}
					}
					if(vehicle.SYR != document.getElementById("xm").value){
						alert("提示：机动车所有人姓名验证失败，请重试！");
						document.getElementById("xm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
					if(vehicle.SFZMHM != document.getElementById("sfzmhm").value.toUpperCase()){
						alert("提示：机动车所有人身份证号码验证失败，请重试！");
						document.getElementById("sfzmhm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				if(null == vehicle.SJHM || "" == vehicle.SJHM){
					alert("提示：您的机动车在初次登记时未登记手机号码,请到车管所及时更新！");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				if(vehicle.SJHM != sjhm.value){
					alert("提示：手机号码比对失败，请检查后重新输入！\r\n如果你的信息与页面不符,请到车管所及时更新！");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				
				if( busi == "12"  ){
					if(vehicle.SYR != document.getElementById("xm").value){
						alert("提示：机动车所有人姓名验证失败，请重试！");
						document.getElementById("xm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				//所有信息比对成功，则发送短信验证码
				if(busi == 12 ){
					sendValidateKaoPingMsg();
				}else{
					sendValidateMsg();
				}
			}else{
				alert("提示：获取机动车信息失败，请重新提交同步申请！");
				checkFailed();
				syncCache = false;
			}
		});
	}
	
	//发送短信
	function sendValidateMsg(){
		document.getElementById("btn_resend").disabled = true;
		i= 90;
		timer = window.setInterval("timerInterval()",1000);
		InterfaceServiceJS.sendMsg("1",function(data){
			if(typeof(data) != "undefined" && !isNaN(data) && data != "ERR"){
				validate = data;
				document.getElementById("tr_yzm").style.display = "block";
				document.getElementById("confirm_next").disabled = true;
				document.getElementById("yzm").value = "";
				document.getElementById("yzm").focus();
			}else{
				alert("提示：发送短信超时，请稍后再试！");
			}
		});
		Shade.hide();
	}
	
	
	//重新输入号牌号码
	function toBegin(){
		window.location.reload();
	}
	
	//清空定时器
	function clearTimer(){
		window.clearInterval(checkSyncTimer);
		checkSyncTimer = null;
	}
	
	window.onload = function(){
		document.getElementById("hphm").focus();
	}
	
	function checkChineseHphm(){
		var hphm = document.getElementById('hphm');
		var errMsg = document.getElementById('errMsgF');
		if(/[\u4E00-\u9FA5\uF900-\uFA2D]/g.test(hphm.value)){
			errMsg.innerHTML = '号牌号码输入不能包含中文！';
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
			document.getElementById("btn_valdate").disabled = true;
			document.getElementById("btn_valdate").value = "处理中...";
			window.location.href = url;
		}else{
			alert("提示：验证码输入错误！");
		}
	}