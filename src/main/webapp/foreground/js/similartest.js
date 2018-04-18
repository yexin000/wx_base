var simula_exam = {};

//题目序号-1，对应body的index
simula_exam.questNum = 0;

//正确答案
simula_exam.rightararry = [];

//答题答案
simula_exam.doararry = [];

//确认交卷后，控制其他按钮失效
simula_exam.isStop = true;

//当前问题的正确答案
simula_exam.frount = '';

//答题获得的分数
simula_exam.right_count = 0;

//所做题数
simula_exam.write_count = 0;

simula_exam.clock = "";

$(document).ready(function() {
	$("#ExamArea").hide();
	$("#i_img").hide();
	$("#i_video").hide();
	$(".dxqd").hide();
	
//	simula_exam.clock = $("#countTime").html();
	
	if ((simula_exam.getCom()) == '1') {
		addHead("科目一模拟考试");
		$("div#foot").hide();
	}
	
	if ((simula_exam.getCom()) == '4') {
		addHead("安全文明常识模拟考试");
		$("div#foot").hide();
	}

	ProgressBar.init();
	ProgressBar.show();

	simula_exam.getInterface();
});

simula_exam.getInterface = function(){
	//倒计时开始
//	simula_exam.start();
	
	simula_exam.isStop = true;
	
	simula_exam.questNum = 0;
	
	simula_exam.right_count = 0;
	//清空数据
	simula_exam.allDan = [];
	
	if ((simula_exam.getCom()) == '1') {
		var params = "{\"question_type\":\"1\"}";
	}
	
	if ((simula_exam.getCom()) == '4') {
		var params = "{\"question_type\":\"4\"}";
	}
	
//	var params = "{\"question_type\":\"4\"}";
	return WebServiceUtil.simulation(params, simula_exam.simularquest);
}

simula_exam.body = "";
simula_exam.simularquest = function (msg){
	ProgressBar.hide();
	var jsonObj = eval("("+msg+")");
	if (jsonObj.head.code != 1) {
		Showbo.Msg.alert(jsonObj.head.message);
		return ;
	}
	
	for ( var i in jsonObj.body) {
		simula_exam.rightararry.push(jsonObj.body[i].answer);
	}
	
	simula_exam.body = jsonObj.body;
	simula_exam.passbody();
}

//解析body
simula_exam.passbody = function (){
	if (simula_exam.questNum < simula_exam.body.length) {
		//返回的正确大案
		var answer = simula_exam.body[simula_exam.questNum].answer;
		
		simula_exam.frount = answer;
		
		var title_str = simula_exam.questNum+1+"."+simula_exam.body[simula_exam.questNum].question;
		var select_A = "A."+simula_exam.body[simula_exam.questNum].optiona;
		var select_B = "B."+simula_exam.body[simula_exam.questNum].optionb;
		var select_C = "C."+simula_exam.body[simula_exam.questNum].optionc;
		var select_D = "D."+simula_exam.body[simula_exam.questNum].optiond;
		
		var file_type = simula_exam.body[simula_exam.questNum].file_type;
		var pic = = "weixin/foreground/" + simula_exam.body[simula_exam.questNum].pic;
		
		simula_exam.initQuestion(title_str,select_A,select_B,select_C,select_D,file_type,pic,answer);
	}
	
}

simula_exam.initQuestion = function (ti,sa,sb,sc,sd,ft,p,an){
	
	var str_exam = "";
	
	if (an.length > 1) {
		str_exam += "<p id='quest_T'>"+ti+"<span style='color: #f00'>[多选题]</span></p>"
		$(".dxqd").hide();
	}else{
		str_exam += "<p id='quest_T'>"+ti+"</p>"
		$(".dxqd").hide();
	}
	
	switch(ft){
	case 'jpg':
		str_exam += "<div style='background-color: #fff;width:100%;text-align: center;'>"
					+"<img style='max-width: 300px;' id='i_img' src='/"+p+"'></img></div>";
		$("#i_img").show();
		break ;
	case 'rmvb':
		str_exam += "<video id='i_video' autoplay='autoplay' controls='controls' src='/"+p+"'></video>";
		$("#i_video").show();
		break ;
	default:
		$("#i_img").hide();
		$("#i_video").hide();	
	}
	
	if (an.length > 1) {
		
		str_exam += "<ul id='ExamOpt'>";
		str_exam += "<li id='ExamOptA' val='1' flag='false' class='kh'>";
		str_exam += sa;
		str_exam += "</li>";
		
		if (sc != "C.") {
			str_exam += "<li id='ExamOptB' val='2' flag='false' class='kh'>";
			str_exam += sb;
			str_exam += "</li>";
			
			str_exam += "<li id='ExamOptC' val='3' flag='false' class='kh' >";
			str_exam += sc;
			str_exam += "</li>";
		} else{
			str_exam += "<li id='ExamOptB' val='2' flag='false' class='e kh'>";
			str_exam += sb;
			str_exam += "</li>";
		}
		
		if (sd != "D.") {
			str_exam += "<li id='ExamOptD' val='4' flag='false' class='e kh'>";
			str_exam += sd;
			str_exam += "</li>"
		}
		
	}else{
		str_exam += "<ul id='ExamOpt'>";
		str_exam += "<li id='ExamOptA' val='1' flag='false' class='kH'>";
		str_exam += sa;
		str_exam += "</li>";
		
		if (sc != "C.") {
			str_exam += "<li id='ExamOptB' val='2' flag='false' class='kH'>";
			str_exam += sb;
			str_exam += "</li>";
			
			str_exam += "<li id='ExamOptC' val='3' flag='false' class='kH' >";
			str_exam += sc;
			str_exam += "</li>";
		} else{
			str_exam += "<li id='ExamOptB' val='2' flag='false' class='e kH'>";
			str_exam += sb;
			str_exam += "</li>";
		}
		
		if (sd != "D.") {
			str_exam += "<li id='ExamOptD' val='4' flag='false' class='e kH'>";
			str_exam += sd;
			str_exam += "</li>"
		}
	}
	
	str_exam += "</ul>"
		
	var title_str = "";
		
	$("#ExamArea").html(str_exam);
	
	$("#ExamArea").show();
	ProgressBar.hide();
	
	simula_exam.selectItem(an.length);
}

simula_exam.selectItem = function(con,a){
	//alert("正确答案数："+con);
	var liArray = document.getElementsByTagName('li');
	if (con > 1) {
		simula_exam.checklis(liArray,a);
	}else{
		simula_exam.checkli(liArray,a);
	}
	
}

//单选选答案
simula_exam.checkli = function(array,da){
	for ( var i = 0; i < array.length; i++) {

		array[i].onclick = function() {
			
			for(var j=0; j < array.length; j++){
				$("#"+array[j].id).removeClass("kK");
				$("#"+array[j].id).attr("flag","false");
			}
			
			$(this).addClass("kK");
			$(this).attr("flag","true");
			
//			//单选自动提交答案
//			simula_exam.compare();
		}
		
	}
	
}

//多选题
simula_exam.checklis = function(lis,da){
	for ( var i = 0; i < lis.length; i++) {
		
		lis[i].onclick = function() {
			if ($("#"+this.id).attr('flag') == 'false') {
				$(this).addClass("kk");
				$(this).attr("flag","true");
				
			} else {
				$(this).removeClass("kk");
				$(this).attr("flag","false");
				
			}
		}
		
	}
}

simula_exam.allDan = [];
simula_exam.compare = function(){
	var da = []; 
	
	// 读取选择的答案放到数组da中
	var A_li = document.getElementsByTagName('li');
	for ( var i = 0; i < A_li.length; i++) {
		if($("#"+A_li[i].id).attr('flag')=='true'){
			da.push($("#"+A_li[i].id).attr('val'));
		}
	}
//	alert(da.length);
	
	//选择答案数不能为空
	if (da.length == 0) {
		simula_exam.allDan[simula_exam.questNum-1] = da;
		
//		Showbo.Msg.alert('您还没有作答！');
//		Showbo.todo = function (){};
	//	return ;
	}else {
		//所做题数
		simula_exam.allDan[simula_exam.questNum-1] = da;
	}

	
	
	var right = simula_exam.frount.split("");
	
	var com = true;
	
	//判断提交答案和正确答案，计算答对试题数
	if (da.length == right.length) {
		for ( var n = 0; n < da.length; n++) {			
			if ((da[n] != right[n])) {				
				com = false;
			}
		}
	}else{
		com = false;
	}
	
	// 答案比对成功，正确答案数+1
	if (com) {
		simula_exam.right_count++;
	}

	//最后一题提交答案的时候不执行下一题方法
	if (simula_exam.questNum+1 == simula_exam.body.length) {
		return ;
	}
	//simula_exam.getNextQuestion();
}




simula_exam.getPreQuestion = function (){
	
	if (simula_exam.isStop) {
		
		if (simula_exam.questNum >= simula_exam.body.length) {
			simula_exam.questNum = simula_exam.body.length-1;
		}
		
		simula_exam.questNum--;
		if (simula_exam.questNum < 0) {
			Showbo.Msg.alert("当前已经是第一题了");
			Showbo.todo = function (){};
			return ;
		}
		
		simula_exam.passbody();
		
		simula_exam.preClass();
	}
}

simula_exam.getNextQuestion = function(){
	
	if (simula_exam.isStop) {
	
		if (simula_exam.questNum <0) {
			simula_exam.questNum = 0;
		}
		
		simula_exam.questNum++;
		
		//先提交答案
		simula_exam.compare();
		
		if (simula_exam.questNum >= simula_exam.body.length) {
			Showbo.Msg.alert("当前已经是最后一题了");
			Showbo.todo = function (){};
			return ;
		}
		
		simula_exam.passbody();

		simula_exam.preClass();
		
	}
}

//上一题，如果已有答案，择为其添加效果
simula_exam.preClass = function (){
	
	//只有从第二题开始才有上一题，防止数组越界
	if((simula_exam.questNum >= 0) && (simula_exam.questNum < simula_exam.allDan.length)){
		
		var preDan = simula_exam.allDan[simula_exam.questNum];
//		alert("当前题的答案="+preDan);
		
		for(var i in preDan){
		//	alert("当前题的答案="+preDan[i]);
			switch(preDan[i]){
			case '1':
				$("#ExamOptA").attr('flag','true');
				if (simula_exam.frount.length > 1) {
					//多选
					$("#ExamOptA").addClass("kk");
				}else{
					$("#ExamOptA").addClass("kK");
				}
				
				break ;
			case '2':
				$("#ExamOptB").attr('flag','true');
				if (simula_exam.frount.length > 1) {
					//多选
					$("#ExamOptB").addClass("kk");
				}else{
					$("#ExamOptB").addClass("kK");
				}
				
				break ;
			case '3':
				$("#ExamOptC").attr('flag','true');
				if (simula_exam.frount.length > 1) {
					//多选
					$("#ExamOptC").addClass("kk");
				}else{
					$("#ExamOptC").addClass("kK");
				}
				
				break ;
			case '4':
				$("#ExamOptD").attr('flag','true');
				if (simula_exam.frount.length > 1) {
					//多选
					$("#ExamOptD").addClass("kk");
				}else{
					$("#ExamOptD").addClass("kK");
				}
				break ;
			default :
				break;
			}
		}
	}
	
}


//停止运行,交卷
simula_exam.stop = function () {

	simula_exam.write_count = 0;
	
	if (simula_exam.isStop) {
	
		var clickSure = 0;
		
		for(var i in simula_exam.allDan){
			if (simula_exam.allDan[i] != "") {
				simula_exam.write_count++;
			}
		}
		
		if (simula_exam.body.length > simula_exam.write_count) {
			Showbo.Msg.confirm('还有'+(simula_exam.body.length-simula_exam.write_count)+'题未做，确定交卷？');
		}
		
		if (simula_exam.body.length == simula_exam.write_count) {
			clickSure = 1;
			
			if (simula_exam.right_count < 90){
				Showbo.Msg.alert('你得了'+simula_exam.right_count+'分，没有过关。继续加油！<br/>点击确认重新考试。');
			}else{
				Showbo.Msg.alert('你得了'+simula_exam.right_count+'分，恭喜你通过考试。再接再厉！<br/>点击确认重新考试。');
			}
		}
		
		//确认交卷
		
		Showbo.todo = function (){
			// 倒计时停止，不能点击页脚的每一个按钮
			window.clearTimeout(simula_exam.timer);
			simula_exam.isStop = false;
			
			if (clickSure == 0) {
				
				clickSure = 1;
				if (simula_exam.right_count < 90){
					Showbo.Msg.alert('你得了'+simula_exam.right_count+'分，没有过关。继续加油！<br/>点击确认重新考试。');
				}else{
					Showbo.Msg.alert('你得了'+simula_exam.right_count+'分，恭喜你通过考试。再接再厉！<br/>点击确认重新考试。');
				}
				
			}else{
				
				Showbo.Msg.hide();
				
				simula_exam.getInterface();
			}
			
		}
	}
}
















// 秒表倒计时控制
simula_exam.counter;
simula_exam.startTime;
simula_exam.start1 = "";
simula_exam.finish = "00:00";
simula_exam.timer = null;

////考试时间到
//simula_exam.over = false;

simula_exam.start = function (){
	simula_exam.start1 = simula_exam.clock;
	
	// 开始运行
	simula_exam.counter = 0;
	// 初始化开始时间
	simula_exam.startTime = new Date().valueOf();
	// 注意setInterval函数: 时间逝去1000(毫秒)后, onTimer才开始执行
	simula_exam.timer = window.setInterval("onTimer()", 1000);
}

// 倒计时函数
function onTimer() {
	if (simula_exam.start1 == simula_exam.finish) {
		window.clearInterval(simula_exam.timer);
		
		Showbo.Msg.alert('时间到，你得了'+simula_exam.right_count+'分，没有过关。继续加油！<br/>点击确认重新考试。');
		
		Showbo.todo = function (){
			Showbo.Msg.hide();
				
			simula_exam.getInterface();
		}
		return;
	}
	var hms = new String(simula_exam.start1).split(":");
	var s = new Number(hms[1]);
	var m = new Number(hms[0]);
	s -= 1;
	if (s < 0) {
		s = 59;
		m -= 1;
	}

	var ss = s < 10 ? ("0" + s) : s;
	var sm = m < 10 ? ("0" + m) : m;
	simula_exam.start1 = sm + ":" + ss;
	$("#countTime").html(simula_exam.start1);
	// 清除上一次的定时器
	window.clearInterval(simula_exam.timer);
	// 自校验系统时间得到时间差, 并由此得到下次所启动的新定时器的时间nextelapse
	simula_exam.counter++;
	var counterSecs = simula_exam.counter * 100;
	var elapseSecs = new Date().valueOf() - simula_exam.startTime;
	var diffSecs = counterSecs - elapseSecs;
	var nextelapse = 1000 + diffSecs;
	if (nextelapse < 0)
		nextelapse = 0;
	// 启动新的定时器
	simula_exam.timer = window.setInterval("onTimer()", 1000);
}


simula_exam.getCom = function (){
	
	 var come = unescape(simula_exam.getQueryString("come"));
	 if (come == '1') {
		 return '1';		 
	 } else {
		 return '4';
	 }
	 
}

simula_exam.getQueryString = function (name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}



