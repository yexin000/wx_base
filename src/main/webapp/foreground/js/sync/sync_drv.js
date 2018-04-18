	var syncCache = false;
	var timerLimitCount;
	var validate;
	//��ʼͬ����ʻ����Ϣ
	function saveSyncVioInfo(){
		timerLimitCount = 0;
		var sfzmhm = document.getElementById("sfzmhm");
		var sjhm = document.getElementById("sjhm");
		var xm = document.getElementById("xm");
		if(sfzmhm.value == ""){
			alert("��ʾ�����������ļ�ʻ֤���룡");
			sfzmhm.focus();
			return false;
		}
		if(check_sfzh(sfzmhm) == 0){
			return false;
		}
		if(busi == 1 || busi == 2 || busi == 12 ){
			if(xm.value == ""){
				alert("��ʾ���������ʻ��������");
				xm.focus();
				return;
			}
			xm.disabled = true;
		}
		if(typeof(busi) != "undefined" && busi == 2 ){	//��֤�������  ��ʻ����Ϣ�����Ҫ���뵵�����
			var dabh = document.getElementById("dabh");
			if(dabh.value == ""){
				alert("��ʾ�����������ļ�ʻ֤������ţ�");
				dabh.focus();
				return false;
			}
			if(isNaN(dabh.value) || dabh.value.length != 12){
				alert("��ʾ��������12λ���ֵĵ�����ţ�");
				dabh.focus();
				return false;
			}
			dabh.disabled = true;
		}
		if(sjhm.value == ""){
			alert("��ʾ�������복�����εǼ�ʱ��д���ֻ����룡");
			sjhm.focus();
			return false;
		}
		if(!/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/.test(sjhm.value)){
			alert("��ʾ����������ȷ���ֻ����룡");
			sjhm.focus();
			return false;
		}
		//if(busi == 12){
		//	var dzyx = document.getElementById("dzyx");
		//	if(dzyx.value == ""){
		//		alert("��ʾ����������������ַ��");
		//		dzyx.focus();
		//		return;
		//	}
		//	dzyx.disabled = true;
		//}
		
		sfzmhm.disabled = true;
		sjhm.disabled = true;
		document.getElementById("confirm_next").disabled = true;
		//��ͬ���ɹ�������
		if(syncCache){
			getDrvInfo();
			return;
		}
		
		//��ʾͬ���ȴ�
		Shade.show(waitDrv);
		InterfaceServiceJS.saveSync(sfzmhm.value,"","","2",function(result){
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
		//����Ƿ�ͬ��
		InterfaceServiceJS.getSyncStatus(sfzmhm.value.toUpperCase(),"","","2",function(statu){
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
				syncCache = true;
				window.setTimeout("getDrvInfo()",2000);	//�ӳ�2���ȡ��ʻ����Ϣ
			}
			//δ�ҵ�������Ϣ
			if(typeof(statu) != "undefined" && statu == "2"){
				checkFailed();
				alert("��ʾ��ϵͳδͬ�������ļ�ʻ֤��Ϣ,�������������Ϣ�Ƿ���ȷ!");
				clearTimer();
				syncCache = false;
			}
			//ͬ�������ж�
			if(typeof(statu) != "undefined" && statu == "9"){
				checkFailed();
				alert("��ʾ�������жϣ����Ժ����ԡ�");
				clearTimer();
				checkFailed();
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
		document.getElementById("sfzmhm").disabled = false;
		document.getElementById("sjhm").disabled = false;
		if(typeof(busi) != "undefined" && busi == 2 ){	//��֤�������  ��ʻ����Ϣ�����Ҫ���뵵�����
			document.getElementById("dabh").disabled = false;
		}
		document.getElementById("confirm_next").disabled = false;
		Shade.hide();
	}
	
	//��ȡ��ʻ֤��Ϣ
	function getDrvInfo(){
		var sfzmhm = document.getElementById("sfzmhm");
		var sjhm = document.getElementById("sjhm");
		InterfaceServiceJS.getDrvInfo("",sfzmhm.value.toUpperCase(),function(drver){
			if(drver != null && typeof(drver) != "undefined"){
				if(!isQuery){
					if(null == drver.isAllowed || drver.isAllowed == false){
						alert("��ʾ���ü�ʻ֤�������������룬�뵽���������ڰ���");
						document.getElementById("sjhm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				//��ʻ֤״̬�Ƿ�Ϊ����״̬
				/**
				if(drver.ZT != 'A' ){
					alert("��ʾ����ʻ֤״̬��Ҫ��[����]����²ſɰ����ҵ��!");
					checkFailed();
					syncCache = false;
					return;
				}
				**/
				//��ʻ֤��֤��Ҫ���ݻ����ſ����루������ס֤��������ס֤��Ϊ�գ���Ǳ��ػ�����
				/**
				if(busi == 1 && drver.ZZZM != null && drver.ZZZM != "" ){
					alert("��ʾ���Ǳ��ػ�����ʻ֤�������������룬�뵽���������ڰ���");
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				**/
				if(busi == 1 || busi == 2  || busi == 12){   //��ʻ֤��֤����ϵ��ʽ�����Ҫ��֤ ����
					if(document.getElementById("xm").value != drver.XM){
						alert("��ʾ����ʻ�������ȶ�ʧ�ܣ������ԣ�");
						document.getElementById("xm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				if(null == drver.SJHM || "" == drver.SJHM){
					alert("��ʾ�����ļ�ʻ֤�ڳ��εǼ�ʱδ�Ǽ��ֻ�����,�뵽��������ʱ���£�");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				if(typeof(busi) != "undefined" && busi == 2 ){	//��֤�������  ��ʻ����Ϣ�����Ҫ���뵵�����
					var dabh = document.getElementById("dabh");
					//�ȶԵ������
					if(dabh.value != drver.DABH){
						alert("��ʾ��������ĵ�����űȶ�ʧ��,������������룡");
						document.getElementById("dabh").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				//�ȶ��ֻ�����
				var veh_sjhm = drver.SJHM;
				//�ȶԳɹ�
				if(sjhm.value == veh_sjhm){
					if(busi == 12 ){
						sendValidateKaoPingMsg();
					}else{
						sendValidateMsg();
					}
				}else {
					alert("��ʾ���ֻ�����ȶ�ʧ�ܣ�������������룡\r\n��������Ϣ��ҳ�治��,�뵽��������ʱ���£�");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
				}
			}else{
				alert("��ʾ����ȡ��ʻ֤��Ϣʧ�ܣ��������ύͬ�����룡");
				checkFailed();
				syncCache = false;
			}
		});
	}
	
	//���Ͷ���
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
				alert("��ʾ�����Ͷ��ų�ʱ�����Ժ����ԣ�");
			}
		});
		Shade.hide();
	}
	
	
	//���Ͷ���
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
				alert("��ʾ�����Ͷ��ų�ʱ�����Ժ����ԣ�");
			}
		});
		Shade.hide();
	}
	
	
	//����������Ϣ
	function toBegin(){
		window.location.reload();
	}
	
	//��ն�ʱ��
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
			errMsg.innerHTML = '��ʻ֤�����벻�ܰ������ģ�';
			hphm.focus();
		}else{
			errMsg.innerHTML = '';
		}
	}
	
	function checkValidate(url){
		var input_validate = document.getElementById("yzm");
		if(input_validate.value == ""){
			alert("��ʾ���������ֻ����յ�����֤�룡");
			input_validate.focus();
			return;
		}
		if(input_validate.value == validate){
			document.getElementById("btn_validate").disabled = true;
			document.getElementById("btn_validate").value = " ������... ";
			window.location.href = url;
		}else{
			alert("��ʾ����֤���������");
		}
	}