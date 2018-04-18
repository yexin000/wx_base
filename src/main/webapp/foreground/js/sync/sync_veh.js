	var syncCache = false;
	var timerLimitCount;
	var validate;
	//����Υ������
	function handleVio(hpzl,hphm){
		document.handleVioForm.hpzl.value = hpzl;
		document.handleVioForm.hphm.value = hphm;
		document.handleVioForm.submit();
	}
	//��ʼͬ��Υ����Ϣ
	function saveSyncVehInfo(){
		timerLimitCount = 0;
		var hpzl = document.getElementById("hpzl");
		var hphm = document.getElementById("hphm");
		var sjhm = document.getElementById("sjhm");
		if(hpzl.value == ""){
			alert("��ʾ����ѡ��������࣡");
			hpzl.focus();
			return;
		}
		if(hphm.value == ""){
			alert("��ʾ�����������ĺ��ƺ��룡");
			hphm.focus();
			return;
		}
		if(/[\u4E00-\u9FA5\uF900-\uFA2D]/g.test(hphm.value)){
			hphm.focus();
			return;
		}
		var hphm_pre = hphm.value.substring(0,1);
		if(hphm_pre.toUpperCase() != "C"){
			alert("��ʾ����ϵͳ�������ݳ��������������룡");
			hphm.focus();
			return;
		}
		//ҵ�������Ҫ��֤�����Ϣ
		if( busi == "5" || busi == "6" || busi == "7" || busi == "8" || busi == "12" ){
			if( busi != "12"){
				var clsbdh = document.getElementById("clsbdh");
				if(clsbdh.value == ""){
					alert("��ʾ�����������ĳ���ʶ����ţ����ܺţ�ĩ6λ��");
					clsbdh.focus();
					return;
				}
			}
			
			var xm = document.getElementById("xm");
			var sfzmhm = document.getElementById("sfzmhm");
			
			if(xm.value == ""){
				alert("��ʾ������������������");
				xm.focus();
				return;
			}
			if(sfzmhm.value == ""){
				alert("��ʾ���������������֤���롣");
				sfzmhm.focus();
				return;
			}
			if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(sfzmhm.value)){
				alert("��ʾ������������֤���벻�Ϸ���");
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
			alert("��ʾ�������복�����εǼ�ʱ��д���ֻ����룡");
			sjhm.focus();
			return;
		}
		if(!/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/.test(sjhm.value)){
			alert("��ʾ����������ȷ���ֻ����룡");
			sjhm.focus();
			return;
		}
		
		//if( busi == "12"){
		//	var dzyx = document.getElementById("dzyx");
		//	if(dzyx.value == ""){
		//		alert("��ʾ����������������ַ��");
		//		dzyx.focus();
		//		return;
		//	}
		//	dzyx.disabled = true;
		//}
		hpzl.disabled = true;
		hphm.disabled = true;
		sjhm.disabled = true;
		document.getElementById("confirm_next").disabled = true;
		//��ͬ���ɹ�������
		if(syncCache){
			getVehicle();
			return;
		}
		
		//��ʾͬ���ȴ�
		Shade.show(waitVeh);
		InterfaceServiceJS.saveSync("",hpzl.value,hphm.value.toUpperCase(),"1",function(result){
			//����ͬ����Ϣ�ɹ�
			if(typeof(result) != "undefined" && result == "1"){
				checkSyncVehStatu();	//��ʼ���ͬ��״̬
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
	function checkSyncVehStatu(){
		timerLimitCount++;
		var hpzl = document.getElementById("hpzl");
		var hphm = document.getElementById("hphm");
		//����Ƿ�ͬ��
		InterfaceServiceJS.getSyncStatus("",hpzl.value,hphm.value.toUpperCase(),"1",function(statu){
			//δͬ���ɹ�
			if(statu == null || statu == "undefined" || statu == "0"){
				if(checkSyncTimer == null){
					//��ʱ�� timerLimitSecond����ȥ���һ��
					checkSyncTimer = setInterval("checkSyncVehStatu()",timerLimitSecond*1000);
				}
			}
			//ͬ���ɹ�
			if(typeof(statu) != "undefined" && statu == "1"){
				//�����ʱ��
				clearTimer();
				syncCache = true;
				window.setTimeout("getVehicle()",2000);  //�ӳ�2���ȡ��������Ϣ
			}
			//δ�ҵ�������Ϣ
			if(typeof(statu) != "undefined" && statu == "2"){
				checkFailed();
				alert("��ʾ��ϵͳδͬ�������ĳ�����Ϣ,�������������Ϣ�Ƿ���ȷ!");
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
		document.getElementById("hpzl").disabled = false;
		document.getElementById("hphm").disabled = false;
		document.getElementById("sjhm").disabled = false;
		document.getElementById("confirm_next").disabled = false;
		Shade.hide();
	}
	
	//��ȡ��������Ϣ
	function getVehicle(){
		var hpzl = document.getElementById("hpzl");
		var hphm = document.getElementById("hphm");
		var sjhm = document.getElementById("sjhm");
		InterfaceServiceJS.getVehicle(hpzl.value,hphm.value.toUpperCase(),function(vehicle){
			if(vehicle != null && typeof(vehicle) != "undefined"){
				if(!isQuery){
					if(null == vehicle.isAllowed || vehicle.isAllowed == false){
						alert("��ʾ����ҵ�������������������룬�뵽���������ڰ���");
						document.getElementById("sjhm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				if( busi == "5" || busi == "6" || busi == "7" || busi == "8" || busi == "12" ){		//ҵ�������Ҫ��֤�����Ϣ
					if( busi == "6" ){ 	//��λ�û��������������� ����ʻ֤���ơ����ί����죬������������ϵ��ʽ���
						if(vehicle.SFZMMC != document.getElementById("sfzmmc").value){
							alert("��ʾ�����֤�����Ʊȶ�ʧ�ܣ������ԣ�");
							document.getElementById("sfzmmc").focus();
							document.getElementById("re_input").style.display = "";
							document.getElementById("confirm_next").disabled = false;
							Shade.hide();
							return;
						}
					}else{
						if( vehicle.SFZMMC != "A" ){
							alert("��ʾ����λ����������������������ҵ���뵽�������������룡");
							document.getElementById("re_input").style.display = "";
							document.getElementById("confirm_next").disabled = false;
							Shade.hide();
							return;
						}
					}
					
					if( busi != "12" ){
						//�ȶԳ���ʶ�����ĩ6λ
						var clsbdh = vehicle.CLSBDH;
						var temp_clsbdh = "";
						if(clsbdh.length > 6 ){
							temp_clsbdh = clsbdh.substring(clsbdh.length - 6,clsbdh.length);
						}else{
							temp_clsbdh = clsbdh;
						}
						if(document.getElementById("clsbdh").value.toUpperCase() != temp_clsbdh){
						alert("��ʾ������ʶ����ţ����ܺţ�ĩ6λ�ȶ�ʧ�ܣ������ԣ�");
							document.getElementById("clsbdh").disabled = false;
							document.getElementById("re_input").style.display = "";
							document.getElementById("confirm_next").disabled = false;
							Shade.hide();
							return;
						}
					}
					if(vehicle.SYR != document.getElementById("xm").value){
						alert("��ʾ��������������������֤ʧ�ܣ������ԣ�");
						document.getElementById("xm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
					if(vehicle.SFZMHM != document.getElementById("sfzmhm").value.toUpperCase()){
						alert("��ʾ�����������������֤������֤ʧ�ܣ������ԣ�");
						document.getElementById("sfzmhm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				if(null == vehicle.SJHM || "" == vehicle.SJHM){
					alert("��ʾ�����Ļ������ڳ��εǼ�ʱδ�Ǽ��ֻ�����,�뵽��������ʱ���£�");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				if(vehicle.SJHM != sjhm.value){
					alert("��ʾ���ֻ�����ȶ�ʧ�ܣ�������������룡\r\n��������Ϣ��ҳ�治��,�뵽��������ʱ���£�");
					document.getElementById("sjhm").disabled = false;
					document.getElementById("re_input").style.display = "";
					document.getElementById("confirm_next").disabled = false;
					Shade.hide();
					return;
				}
				
				if( busi == "12"  ){
					if(vehicle.SYR != document.getElementById("xm").value){
						alert("��ʾ��������������������֤ʧ�ܣ������ԣ�");
						document.getElementById("xm").disabled = false;
						document.getElementById("re_input").style.display = "";
						document.getElementById("confirm_next").disabled = false;
						Shade.hide();
						return;
					}
				}
				//������Ϣ�ȶԳɹ������Ͷ�����֤��
				if(busi == 12 ){
					sendValidateKaoPingMsg();
				}else{
					sendValidateMsg();
				}
			}else{
				alert("��ʾ����ȡ��������Ϣʧ�ܣ��������ύͬ�����룡");
				checkFailed();
				syncCache = false;
			}
		});
	}
	
	//���Ͷ���
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
				alert("��ʾ�����Ͷ��ų�ʱ�����Ժ����ԣ�");
			}
		});
		Shade.hide();
	}
	
	
	//����������ƺ���
	function toBegin(){
		window.location.reload();
	}
	
	//��ն�ʱ��
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
			errMsg.innerHTML = '���ƺ������벻�ܰ������ģ�';
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
			document.getElementById("btn_valdate").disabled = true;
			document.getElementById("btn_valdate").value = "������...";
			window.location.href = url;
		}else{
			alert("��ʾ����֤���������");
		}
	}