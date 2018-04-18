function trafficList() {
	var params = "{\"lbbh\":\"240000\"}";
	var rel = WebServiceUtil.getLawsAndRegulationsInfo(params, callBack);
}

function callBack(msg) {
	var jsonObject = eval("(" + msg + ")");
	if (jsonObject.head.rownum == 0) {
		Showbo.Msg.alert(jsonObject.head.message);
		return;
	}
	var body = jsonObject.body;
	for ( var i in body) {
		var date = body[i].fbrq;//.split(' ')[0];
		if(i > 2) {
			break;
		}
		$("ul#rowList")
				.append(
						"<li>"
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
						//+ "<div class=\"li-div-img\">"
						//+ "<img style=\"height: 50px;width: 40px;\" src=\"../../images/arrow3.png\">"
						//+ "</img>"
						//+ "</div>"
						+ "</div>"
						+ "<hr style=\"width: 100%; color: #cccccc\" />"
						+ "</li>");
	}
}

/*function openList(o) {
	// alert(xh);
	window.location.href = "./larl.html?value=" + escape(o);
}*/

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function display(){
	addHead("法律法规");
	
	var lawxh = unescape(GetQueryString("value"));
	var params = "{\"xh\":\""+lawxh+"\"}";
	ProgressBar.init();
	ProgressBar.show();
	return WebServiceUtil.getSchoolNoteArt(params,result);
}

function result(msg){
	ProgressBar.hide();
	var object = eval("("+msg+")")
	for (var i in object.body){
		$("#content").html(object.body[i].content);
	}
}


