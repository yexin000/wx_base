
/**
 * 根据类型编号查询到相应的交管信息
 * @param lbbh:交管信息的类型编号
 */
var headText="";//头部文本
function getTrafficInforByLbbh(lbbh,headInfo){
	var params = "{\"lbbh\":"+lbbh+"}";
	var rel = WebServiceUtil.getLawsAndRegulationsInfo(params, callBack);
	headText=headInfo;
}

var head = "";
function callBack(msg){
	
	var jsonObject = eval("(" + msg + ")");
	
	if (jsonObject.head.rownum == 0) {
		
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
								+ "<img style=\"height: 50px;width: 40px;\" src=\"/weixin/foreground/images/arrow3.png\">"
								+ "</img>"
								+ "</div>"
								+ "</div>"
								+ "<hr style=\"width: 100%; color: #cccccc\" />"
								+ "</li>");
	}
}

function openList(o) {
	// alert(xh);
	window.location.href = "/weixin/foreground/html/communication/pubnote.html?value=" + encodeURI(o)+"&headText="+encodeURI(headText);
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
}
/**
 * 显示具体的交管信息
 * @returns {*}
 */
function displayTrafficInfor(){
	var lawxh = decodeURI(GetQueryString("value"));
	var params = "{\"xh\":\""+lawxh+"\"}";
	return WebServiceUtil.getSchoolNoteArt(params,result);
}

function result(msg){
	var object = eval("("+msg+")");
	$("#titleText").text(object.body[0].title);
	for (var i in object.body){
		$("#content").html(object.body[i].content);
	}
}
