

var jlchp = ""; 

function shut(){  
    window.opener=null;  
    window.open('','_self');  
    window.close();  
}

function getStudentMsg(){
	var params = "{\"token\":\""+token+"\",\"sfzmmc\":\""+sfzm+"\",\"sfzmhm\":\""+sfzh+"\"}";
	return WebServiceUtil.getStudentTestMsg(params,backStudent);
}

function backStudent(msg){
	//alert("学员信息"+msg);
	var jsonObj = eval("("+msg+")");
	var body = jsonObj.body;
	for(var i in body){

		jlchp = body[i].jlc;
		
		if (body[i].bj != "0") {
			Showbo.Msg.alert(body[i].bz);
			return ;
		}
		
		if (body[i].bj == "0") {
			setTimeout("getStudentExamInfo()",15000);
		}
	}
}

function getStudentExamInfo(){
	var params = "{\"token\":\""+token+"\",\"sfzmmc\":\""+sfzm+"\",\"sfzmhm\":\""+sfzh+"\"}";
	//alert("计划"+params);
	return WebServiceUtil.getStudentTestPlan(params,backDate);
}


function backDate(msg){
	//alert("计划信息"+msg);
	var rqArray = [];
	var ddArray = [];
	var ddDetail = [];
	var ccArray = [];
	var ccDetail = [];
	var jsonObject = eval("("+msg+")");
	var head = jsonObject.head;
	if (head.code == -1) {
		Showbo.Msg.alert(head.message);
		//eval($("#tmpt").text($.Prompt(head.message)));
		shut();
		return;
	}
	
	var body = jsonObject.body;
	for (var j in body){
		
		rqArray[j] = body[j].ksrq;
		ddArray[j] = body[j].ksdd;
		ddDetail[j] = body[j].ksdd_cn;
		ccArray[j] = body[j].kscc;
		ccDetail[j] = body[j].kscc_cn;
	}
	var ksrqLast = unique(rqArray);
	for(var n in ksrqLast){
		$("#testDate").append("<option value=\""+ksrqLast[n]+"\">"
				+ksrqLast[n]+"</option>");
	}
	var selectText =$("#testDate").find("option:selected").text();
	//document.getElementsByTagName('span')[1].innerHTML=selectText;

	var ksddLast = unique(ddArray);
	var ksdd_cnLast = unique(ddDetail);
	for ( var int = 0; int < ksddLast.length; int++) {
		$("#testAddr").append("<option value=\""+ksddLast[int]+"\">"
				+ksdd_cnLast[int]+"</option>");
	}
	var selectText =$("#testAddr").find("option:selected").text();
	//document.getElementsByTagName('span')[2].innerHTML=selectText;
	
	var ksccLast = unique(ccArray);
	var kscc_cnLast = unique(ccDetail);
	for ( var int2 = 0; int2 < ccDetail.length; int2++) {
		$("#testcc").append("<option value=\""+ksccLast[int2]+"\">"
				+kscc_cnLast[int2]+"</option>");
	}
	var selectText =$("#testcc").find("option:selected").text();
	//document.getElementsByTagName('span')[3].innerHTML=selectText;
}

function unique(data){ 
	data = data || [];  
	var a = []; 
	for (var i=0; i<data.length; i++) { 
		var v = data[i]; 
		if (typeof(a[v]) == 'undefined'){ 
			a[v] = 1; 
		} 
	}
	data.length=0; 
	for (var i in a){ 
		data[data.length] = i; 
	} 
	return data; 
} 

var flag = 0;
function writeData(){
	$("#order").css("background-color","#eee");
	if (flag == 1) {
		return ;
	}
	flag = 1;
	// var xm = $("#name").val();
	// var sfzmhm = sfzh;
	// var hphm = $("#hpNumber").val();
	var kskm = $("#testSubject").find("option:selected").val();
	var ksdd = $("#testAddr").find("option:selected").val();
	var ksrq = $("#testDate").find("option:selected").val();
	var kscc = $("#testcc").find("option:selected").val();
	var params = "{\"token\":\""+token+"\",\"sfzmmc\":\""+sfzm+"\",\"sfzmhm\":\""+sfzh+"\",\"xm\":\""+xm+"\",\"kskm\":\""+kskm+"\",\"ksdd\":\""+ksdd+"\",\"ksrq\":\""+ksrq+"\",\"kscc\":\""+kscc+"\",\"hphm\":\""+jlchp+"\",\"ly\":\"A\"}";
	
	return WebServiceUtil.writeTestBookingMsg(params,writeBack);
}

function writeBack(msg){
	var jsonObject = eval("("+msg+")");
	var head = jsonObject.head;
	Showbo.Msg.alert(head.message);
	//eval($("#tmpt").text($.Prompt(head.message)));
}









