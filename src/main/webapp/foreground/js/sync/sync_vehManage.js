	var syncCache = false;
	var timerLimitCount;
	var validate;
	//��ʼͬ��Υ����Ϣ
	function saveSyncVioInfo(){
		timerLimitCount = 0;
		var sfzmhm = document.getElementById("sfzmhm");
		var jgdm = document.getElementById("jgdm");
		if(sfzmhm.value==''){
			sfzmhm.value = jgdm.value;
		}
		document.getElementById("queryBt").disabled = true;
		document.getElementById("syncBt").disabled = true;
		
		//��ͬ���ɹ�������
		if(syncCache){
			getVehicle();
			return;
		}
		//��ʾͬ���ȴ�
		document.getElementById("selectLable").style.display = "none";
		document.getElementById("syncMsg").style.display = "";
		InterfaceServiceJS.saveSync(sfzmhm.value,"","","3",function(result){
			//����ͬ����Ϣ�ɹ�
			if(typeof(result) != "undefined" && result == "1"){
				checkSyncStatu();	//��ʼ���ͬ��״̬
			}
			//����ͬ����Ϣʧ��
			if(typeof(result) != "undefined" && result == "0"){
				alert("��ǰ�����û�̫��,���Ժ�����!");
				checkFailed();
			}
		});
	}
	
	var checkSyncTimer = null;
	//У��ͬ��״̬
	function checkSyncStatu(){
		timerLimitCount++;
		var sfzmhm = document.getElementById("sfzmhm");
		var jgdm = document.getElementById("jgdm");
		if(sfzmhm.value==''){
			sfzmhm.value = jgdm.value;
		}
		//����Ƿ�ͬ��
		InterfaceServiceJS.getSyncStatus(sfzmhm.value,"","","3",function(statu){
			//δͬ���ɹ�
			if(statu == null || statu == "undefined" || statu == "0"){
				if(checkSyncTimer == null){
					//��ʱ�� timerLimitSecond����ȥ���һ��
					checkSyncTimer = setInterval("checkSyncStatu()",timerLimitSecond*1000);
				}
			}
			//ͬ���ɹ�
			if(typeof(statu) != "undefined" && statu == "1"){
				//�����ʱ��
				clearTimer();
				window.setTimeout("getVehicle()",2000);  //�ӳ�2���ȡ��������Ϣ
			}
			//δ�ҵ�������Ϣ
			if(typeof(statu) != "undefined" && statu == "2"){
				checkFailed();
				alert("��ʾ��ϵͳδͬ�������ĳ�����Ϣ!");
				clearTimer();
				syncCache = false;
			}
			//ͬ�������ж�
			if(typeof(statu) != "undefined" && statu == "9"){
				checkFailed();
				alert("��ʾ�������жϣ����Ժ����ԡ�");
				clearTimer();
				syncCache = false;
			}
			if(timerLimitCount > timerLimitCountMax){
				checkFailed();
				alert("��ʾ�����糬ʱ�������ԡ�");
				clearTimer();
				syncCache = false;
			}
		});
	}
	
	//У��ʧ��
	function checkFailed(){
		document.getElementById("syncBt").disabled = false;
		document.getElementById("queryBt").disabled = false;
		document.getElementById("syncMsg").style.display = "none";
		
	}
	
	//��ȡ��������Ϣ
	function getVehicle(){
		document.getElementById("syncBt").disabled = false;
		document.getElementById("queryBt").disabled = false;
		document.getElementById("queryBt").click();
	}
	
	
	//��ն�ʱ��
	function clearTimer(){
		window.clearInterval(checkSyncTimer);
		checkSyncTimer = null;
	}