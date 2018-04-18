var activity_exam = {};
activity_exam.answer = [];

$(document).ready(function(){
	addHead('答题赢红包');
	$("div#content").empty();
	
	ProgressBar.init();
	ProgressBar.show();
	WebServiceUtil.getActivityExam(resultFunc);
	
	
});

function resultFunc(msg) {
	ProgressBar.hide();
	var jsonObject = eval("("+msg+")");
	var exams = jsonObject.exams;
	for ( var i = 0; i < 3; i++) {
		var exam_str = "<div id=\"ExamArea\">";
		exam_str += "<p>"+(i+1)+exams[i].question + "</p>";
		exam_str += "<ul id='ExamOpt"+i+"'>";
		exam_str += "<li name='"+i+"' id='ExamOptA"+i+"' flag='false' class='kH' >";
		exam_str += "A." + exams[i].optiona;
		exam_str += "</li>";
		exam_str += "<li name='"+i+"' id='ExamOptB"+i+"' flag='false' class='kH'>";
		exam_str += "B." + exams[i].optionb;
		exam_str += "</li>";
		exam_str += "<li name='"+i+"' id='ExamOptC"+i+"' flag='false' class='kH' >";
		exam_str += "C." +exams[i].optionc;
		exam_str += "</li>";
		//exam_str += "<li  name='"+i+"' id='ExamOptD"+i+"' flag='false' class='e kH'>";
		//exam_str += "D.正常行为";
		//exam_str += "</li>"
		exam_str += "</ul>"
		exam_str += "</div>"
		$("div#content").append(exam_str);
	}
	
	activity_exam.selectItem();
}

activity_exam.selectItem = function() {
	var lis = document.getElementsByTagName("li");
	for ( var j = 0; j < lis.length; j++) {

		lis[j].onclick = function() {
			var item = $(this).attr('name');

			var selitems = document.getElementsByName(item);

			for ( var k = 0; k < selitems.length; k++) {
				$("#" + selitems[k].id).removeClass("kK");
				$("#" + selitems[k].id).attr('flag','false')
			}

			$(this).addClass("kK");
			$(this).attr("flag","true");
			activity_exam.answer[item] = (($(this).html()).split('.'))[0];
		}
	}
}

activity_exam.sure = function() {
	for ( var m = 0; m < activity_exam.answer.length; m++) {
	}
	
}
