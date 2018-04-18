	var syncCache = false;
	var timerLimitCount;
	var validate;
	//开始同步违法信息
	function saveSyncVioInfo(){
		timerLimitCount = 0;
		var sfzmhm = document.getElementById("sfzmhm");
		var jgdm = document.getElementById("jgdm");
		if(sfzmhm.value==''){
			sfzmhm.value = jgdm.value;
		}
		document.getElementById("queryBt").disabled = true;
		document.getElementById("syncBt").disabled = true;
		
		//已同步成功的数据
		if(syncCache){
			getVehicle();
			return;
		}
		//显示同步等待
		document.getElementById("selectLable").style.display = "none";
		document.getElementById("syncMsg").style.display = "";
		InterfaceServiceJS.saveSync(sfzmhm.value,"","","3",function(result){
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
		var jgdm = document.getElementById("jgdm");
		if(sfzmhm.value==''){
			sfzmhm.value = jgdm.value;
		}
		//检查是否同步
		InterfaceServiceJS.getSyncStatus(sfzmhm.value,"","","3",function(statu){
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
				window.setTimeout("getVehicle()",2000);  //延迟2秒获取机动车信息
			}
			//未找到车辆信息
			if(typeof(statu) != "undefined" && statu == "2"){
				checkFailed();
				alert("提示：系统未同步到您的车辆信息!");
				clearTimer();
				syncCache = false;
			}
			//同步网络中断
			if(typeof(statu) != "undefined" && statu == "9"){
				checkFailed();
				alert("提示：网络中断，请稍候再试。");
				clearTimer();
				syncCache = false;
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
		document.getElementById("syncBt").disabled = false;
		document.getElementById("queryBt").disabled = false;
		document.getElementById("syncMsg").style.display = "none";
		
	}
	
	//获取机动车信息
	function getVehicle(){
		document.getElementById("syncBt").disabled = false;
		document.getElementById("queryBt").disabled = false;
		document.getElementById("queryBt").click();
	}
	
	
	//清空定时器
	function clearTimer(){
		window.clearInterval(checkSyncTimer);
		checkSyncTimer = null;
	}