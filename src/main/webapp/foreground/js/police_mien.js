var maxShowPicNum = 3;
function showList() {
	var params = "{\"lbbh\":\"111000\"}";
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
		var date = body[i].fbrq.split(' ')[0];
		var pics = body[i].intro.split('#');
		var picNum = pics.length;
		if(picNum > 1 && pics[picNum - 1] == "") {
			picNum = picNum - 1;
		}
		
		var imgStr = "";
		var showPicNum = picNum > maxShowPicNum ? maxShowPicNum : picNum;
		for(var j = 0; j < showPicNum; j ++) {
			imgStr = imgStr + "<img style='height: 100%;width: 33%;' src='" + pics[j] +"'></img>";
		}
		$("ul#rowList")
				.append(
						"<li onclick=\"openList(" + "'"
								+ body[i].xh
								+ "')\">"
								+ "<div class=\"li-div-border\">"
								+ "<div class=\"li_div\">"
								+ "<label class=\"li_div_la_first\">"
								+ "<p class=\"li_div_p\">"
								+ body[i].title
								+ "</p>"
								+ "</label>"
								+ imgStr
								+ "<label class=\"li_div_la_sencond\">"
								+ "<p style='text-align: right;'>"
								+ picNum + "图"
								+ "</p>"
								+ "</label>"
								+ "</div>"
								+ "</div>"
								+ "<hr style=\"width: 100%; color: #cccccc\" />"
								+ "</li>");
	}
}

function openList(o) {
	// alert(xh);
	window.location.href = "./police_mien_detail.html?value=" + escape(o);
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function display(){
	addHead("交警风采");
	
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


