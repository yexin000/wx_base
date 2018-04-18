
document.write("<script language=javascript src='./js/myAlert.js'></script>");

function getData(){
	//第一步：读取用户表获得当前用户的车辆
	
	//第二步：根据车辆信息获取监测站信息	
	//ProgressBar.show();
	var params = "{\"token\":\""+token+"\",\"hpzl\":\""+param_hpzl+"\",\"hphm\":\""+param_hphm+"\"}";
	var result = WebServiceUtil.getCheckSpaceMsg(params,loadMsg);
}

	//获得检测站信息
	function loadMsg(msg){
	//	ProgressBar.hide();
		var jsonObj = eval("("+msg+")");
		if (jsonObj.head.code == -1) {
			Showbo.Msg.alert(jsonObj.head.message);
		//	eval($("#tmpt").text($.Prompt(jsonObj.head.message)));
			return;
		}
		
		$("#checkStation").empty();
		var n = [];
		if (msg != null) {
			var jsonObject = eval("("+msg+")");
			var body = jsonObject.body;
			for(var i in body){
				var item = body[i];
				n[i] = item.xh;
				setSelectOption(item.xh,item.lxdz,item.jczmc);
			}
			putlxdz();
		}
	}

	//动态添加OPTION检测站名称
	function setSelectOption(xh,lxdz,jczmc){
		$("#checkStation").append("<option value=\""+xh+"\" data-lxdz=\""+lxdz+"\">"+jczmc+"</option>");
	}
	
	//OPTION检测站的change事件
	var xh ;
	function putlxdz(){
		xh = $("#checkStation option:selected").val();
		var sttrLxdz = $("#checkStation").find("option:selected").attr("data-lxdz");
		$("#checkAddr").val(sttrLxdz);
		readBookDate(xh);
	}
	
	//加载选中项的对应的日期时间(调用接口)
	function readBookDate(jczxh){
		//ProgressBar.show();
		var params = "{\"token\":\""+token+"\",\"jczxh\":\""+ jczxh + "\"}";
		var plan = WebServiceUtil.getBookingPlan(params,loadMsgTwo);
	}
	
	//遍历日期时间
	function loadMsgTwo(msg){
		//ProgressBar.hide();
		var first = [];
		$("#yyrq").empty();
		$("#yysj").empty();
		var jsonObject = eval("("+msg+")");
		if (jsonObject.head.rownum == 0) {
			var d = $("#checkStation").find("option:selected").text();
			Showbo.Msg.alert(d+"预约名额已无剩余，请选择预约其他站点");
		//	eval($("#tmpt").text($.Prompt(d+"预约名额已无剩余，请选择预约其他站点。")));
			return;
		} else {
			var planBody = jsonObject.body;
			for ( var i in planBody) {
				var planItem = planBody[i]
				first[i] = planItem.gzr; 
				appendPlan(planItem.gzr,planItem.bldid);
			}
			//设置默认选中日期   数据库记录的第一条
			setFirstSelected(first[0]);
		}
	}
	
	//动态加载  日期OPTION
	function appendPlan(rq,id){
		$("#yyrq").append("<option value=\""+rq+"\" data-ddid=\""+id+"\">"
			+rq+"</option>");
	}
	
	//设置默认选中日期
	var gzr;
	function setFirstSelected(o2){
		var selectText =$("#yyrq").find("option:selected").text();
		putBookingDate()
	}
	
	//日期的change事件
	function putBookingDate(){
		gzr = $("#yyrq").find("option:selected").val();
		getPlanDay();
	}
	
	function getPlanDay(){
		//ProgressBar.show();
		var params = "{\"token\":\""+token+"\",\"jczxh\":\""+ xh + "\",\"worday\":\""+gzr+"\"}";
		var dayPlan = WebServiceUtil.getWorkdays(params,backDay);
	}
	
	function backDay(plan){
		//ProgressBar.hide();
		//$("#yysj").empty();
		var jsonObject = eval("("+plan+")");
		if (jsonObject.head.rownum == 0) {
			$("#yysj").empty();
			var d = $("#yyrq").find("option:selected").text();
			Showbo.Msg.alert(d+"无预约计划，请选择其他日期");
		//	eval($("#tmpt").text($.Prompt(d+"无预约计划，请选择其他日期。")));
			return;
		} else {
			var body = jsonObject.body;
			for(var i in body){
				$("#yysj").append("<option value=\""+body[i].changci+"\" data-kys=\""+body[i].kys+"\" data-cc=\""+body[i].changci+"\" data-yyrq=\""+gzr+"\">"
						+body[i].kssj+"-"+body[i].jssj+"      剩余名额:"+body[i].kys+"</option>");
			}
			$("#yysj").each(function(){
				$(this).children("option").each(function(){
					if (($(this).attr("data-kys")) == 0) {
						$(this).attr("disabled","disabled");
					}
				});
			});
		}
	}
	
	function putBookingTime(){
		if ($(this).children("option").attr("disabled") == "disabled") {
			Showbo.Msg.alert("剩余预约名额已为0");
			//eval($("#tmpt").text($.Prompt("剩余预约名额已为0")));
		}
	}
	
	var yyrq = "";
	var cc = "";
	function paramsInJk(){
		var hphm = $("#hpInfo").find("option:selected").attr("data-hphm");
		var hpzl = $("#hpInfo").find("option:selected").attr("data-hpzl");
		if ((document.getElementById('yyrq').options.length)== 0) {
			Showbo.Msg.alert("你还没有选择年检日期、时间");
	//		eval($("#tmpt").text($.Prompt("你还没有选择年检日期、时间。")));
			return ;
		}
		
		if ((document.getElementById('yysj').options.length)== 0) {
			Showbo.Msg.alert("你还没有选择年检日期、时间");
			//eval($("#tmpt").text($.Prompt("你还没有选择年检日期、时间。")));
			return ;
		} 
		yyrq = gzr;
		cc = $("#yysj").find("option:selected").val();
		//ProgressBar.show();
		var params = "{\"token\":\""+token+"\",\"hpzl\":\""+ hpzl+ "\",\"hphm\":\""+ hphm+ "\",\"jczxh\":\""+ xh+ "\",\"yyrq\":\""+ yyrq+ "\",\"cc\":\""+ cc + "\",\"ip\":\"192.168.0.10\",\"sjly\":\"C\"}";
		return WebServiceUtil.writeBookingMsg(params,callbakc);
	}
	
	function callbakc(msg){
		//ProgressBar.hide();
		var jsonObject = eval("("+msg+")");
		var head = jsonObject.head;
		var tip = head.message;
		Showbo.Msg.alert(tip);
	}