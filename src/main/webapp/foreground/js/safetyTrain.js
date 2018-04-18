function safetyTrain(){
	var params = "{\"lbbh\":\"190000\"}";
	var rel = WebServiceUtil.getLawsAndRegulationsInfo(params, callBack);
}


var head = "";
function callBack(msg){
	alert(msg);
	var jsonObject = eval("(" + msg + ")");
	if (jsonObject.head.rownum == 0) {
		Showbo.Msg.alert(jsonObject.head.message);
		//eval($("#tmpt").text($.Prompt(jsonObject.head.message)));
		return;
	}
	var body = jsonObject.body;
	for ( var i in body) {
		var date = body[i].fbrq.split(' ')[0];
		head = body[i].title;
		$("ul#rowList")
				.append(
						"<li onclick=\"openList(" + "'"
								+ body[i].xh
								+ "'"
								+ ")\">"
								+ "<div class=\"li-div-border\">"
								+ "<div class=\"li-div\">"
								+ "<label class=\"li-div-la1\">"
								+ "<p class=\"li-div-p\">"
								+ body[i].title
								+ "</p>"
								+ "</label>"
								+ "<label class=\"li-div-la2\">"
								+ "<p>"
								+ "发表日期:"+date
								+ "</p>"
								+ "</label>"
								+ "</div>"
								+ "<div class=\"li-div-img\">"
								+ "<img style=\"height: 50px;width: 40px;\" src=\"../../images/arrow3.png\">"
								+ "</img>"
								+ "</div>"
								+ "</div>"
								+ "<hr style=\"width: 100%; color: #cccccc\" />"
								+ "</li>");
	}
	
}

function openList(o) {
	// alert(xh);
	window.location.href = "./safetyTrainingDetail.html?value=" + escape(o)+"&headText="+escape(head);
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function display(){
	var lawxh = unescape(GetQueryString("value"));
	var params = "{\"xh\":\""+lawxh+"\"}";
	return WebServiceUtil.getSchoolNoteArt(params,result);
}

function result(msg){
	var object = eval("("+msg+")");
	//$("#titleText").text(unescape(GetQueryString("headText")));
	$("#titleText").text(object.body[0].title);
	for (var i in object.body){
		$("#content").html(object.body[i].content);
	}
}