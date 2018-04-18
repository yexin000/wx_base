
var quesNum = 0;

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

$(document).ready(function() {
	$("#ExamArea").hide();
	$("#result").hide();
	$("#i_img").hide();
	$("#i_video").hide();
	ProgressBar.init();

	var come = unescape(getQueryString("come"));
	if (come == '1') {
		var params = "{\"question_type\":\"1\"}";
		addHead("科目一练习");
		$("div#foot").hide();
	} else {
		var params = "{\"question_type\":\"4\"}";
		addHead("安全文明常识练习");
		$("div#foot").hide();
	}
	
	ProgressBar.show();
	//var params = "{\"question_type\":\"4\"}";
	var rel = WebServiceUtil.getQuerstion(params, questionList);
	
});

var body = "";
function questionList(msg){
	//alert(msg);
	var jsonObj = eval("("+msg+")");
	var head = jsonObj.head;
	if (head.code != 1) {
		Showbo.Msg.alert(head.message);
		return ;
	}
	body = jsonObj.body;
	ProgressBar.hide();
	parseBody();
}

var correct = "";
var yes = "";
function parseBody(){
	$("#result").hide();
	if(quesNum < body.length){
		var quest_str = quesNum+1+"."+body[quesNum].question;
		var option_A = body[quesNum].optiona;
		var option_B = body[quesNum].optionb;
		var option_C = body[quesNum].optionc;
		var option_D = body[quesNum].optiond;
		
		var file_type = body[quesNum].file_type;
		
		var pic = body[quesNum].pic;
		
		var answer = body[quesNum].answer;
		yes = answer;
		switch(answer){
		case '1':
			correct = 'A';
				break;
		case '2':
			correct = 'B';
			break;
		case '3':
			correct = 'C';
			break;
		case '4':
			correct = 'D';
			break;
		default:
			correct = "";
		}
		
		init_question(quest_str,option_A,option_B,option_C,option_D,pic,file_type,answer);
	}
}

var flag = 0;
function init_question(quest_str,option_A,option_B,option_C,option_D,pic,type,answer){
	flag = 0;
	var exam_str = "";
	exam_str += "<p>"+quest_str+"</p>";
	switch(type){
	case 'jpg':
		exam_str += "<div style='background-color: #fff;width:100%;text-align: center;'><img style='max-width: 300px;' id='i_img' src='/"+pic+"'></img></div>";
		$("#i_img").show();
		break ;
	case 'rmvb':
		exam_str += "<video style='max-width: 300px;' id='i_video' autoplay='autoplay' controls='controls' src='/"+pic+"'></video>"
		$("#i_video").show();
		break ;
	default:
		$("#i_img").hide();
		$("#i_video").hide();
	}
	
	exam_str += "<ul id='ExamOpt'>";
	exam_str += "<li id='ExamOptA' class='kH' >";
	exam_str += "A."+option_A;
	exam_str += "</li>";
	exam_str += "<li id='ExamOptB' class='kH'>";
	exam_str += "B."+option_B;
	exam_str += "</li>";
	if (option_C != "") {
		exam_str += "<li id='ExamOptC' class='kH'>"
					+"C."+option_C
					+"</li>";
	}
	if (option_D != "") {
		exam_str += "<li id='ExamOptD' class='e kH'>"
			+"D."+option_D
			+"</li>";
	}
	exam_str += "</ul>";
		
	$("#ExamArea").html(exam_str);
	$("#ExamArea").show();
	selectItem();
}

function getPreQuestion(){
	quesNum--;
	if (quesNum < 0) {
		Showbo.Msg.alert("当前已经是第一题了");
		return ;
	}

	parseBody();
	
}

function getNextQuestion(){
	if (quesNum<0) {
		quesNum = 0;
	}
	quesNum++;
	if (quesNum >= body.length) {
		Showbo.Msg.alert("当前已经是最后一题了");
		return ;
	}
	
	parseBody();
}

function selectItem() {
	var lis = document.getElementsByTagName('li');
	var ok = 0;
	for ( var i = 0; i < lis.length; i++) {
		lis[i].onclick = function() {
			if (flag == 1) {
				return ;
			}
			
			
			for ( var j = 0; j < lis.length; j++) {
				lis[j].setAttribute("class", "kH");
				if ((j+1) == yes) {
					ok = j;
				}
			}
			var letter = (this.innerHTML.split("."))[0];
			if (letter == correct) {
				$(this).addClass("kK");
			} else {
				$(this).addClass("kE");
				$("#"+lis[ok].id).addClass("kK");
			}

			$("#yours").text(letter);
			$("#right").text(correct);
			$("#result").show();
			flag = 1;
		}
	}
	
	
//	var da = o.innerHTML;
//	var letter = da.split(".")[0];
//	alert(da);
//	alert(letter);
//	if (letter == correct) {
//		$("#"+o.id).addClass("kK");
//	}else{
//		$("#"+o.id).addClass("kE");
//	}
}








