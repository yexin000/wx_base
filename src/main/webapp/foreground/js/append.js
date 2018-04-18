
//document.title="宁波交警";
if(window.localStorage){ 
	document.title = localStorage.getItem("d_title");
}else{
	document.title = getCookie("d_title");
}



//myCss.css
function insert(headText){
	//"<div id='header' role='banner' class='ui-header po-h'>" 
	 //初始化元素：在div中添加
    var div_1 = document.createElement('div');   
    div_1.id = 'header';   
    div_1.setAttribute('class','ui-header po-h');
    div_1.role = 'banner';
    div_1.innerHTML = "<h5>"+headText+"</h5><i id=\"c-ui-header-return\" style=\"position:fixed;\" class=\"returnico i_bef\" onClick=\"javascript :history.back(-1);\"></i>"; 

    var div_2 = document.createElement('div');  
   // div_2.id = 'foot';
    div_2.setAttribute('class','floot');
    
    if(window.localStorage){ 
    	div_2.innerHTML = localStorage.getItem("d_floot");
	}else{
		div_2.innerHTML = getCookie("d_floot");
	}

    var dm_div = document.getElementById('page');  
    dm_div.insertBefore(div_1,dm_div.firstChild);  
    dm_div.insertBefore(div_2,dm_div.firstChild);  
}
/**
 * 返回上一页
 */
function goBack(){
	//返回上一页
	window.history.go(-1);
	//刷新上一页
//document.execCommand('Refresh');
//location.reload();


}

//ningboweb.css
function addHead(ht) {
	var div_h = document.createElement('div'); 
	div_h.id = 'pagehead';
	div_h.innerHTML ="<h2>"+ht+"</h2><i id=\"c-ui-header-return\" class=\"returnico i_bef\" onClick=\"javascript :history.back(-1);\"></i><div class=\"clear\">";
	
	var div_f = document.createElement("div");
	div_f.id = 'foot';
	div_f.setAttribute('class','floot');
	
	if(window.localStorage){ 
		div_f.innerHTML = localStorage.getItem("d_floot");
	}else{
		div_f.innerHTML = getCookie("d_floot");
	}
	
	var div_m = document.getElementsByTagName('body')[0];
	div_m.insertBefore(div_h,div_m.firstChild);
	div_m.insertBefore(div_f,div_m.firstChild);
}

//顶部追加查找按钮
function searchHead(){
	$("div#header").append("<a onclick='javascript:ToUrl(\"/weixin/foreground/html/vehicle_admin/screencarnumber.html\");' style='float: right;height:0px; position: relative;top: -30px;'> "
			+"<img style='float: right;width:40px;height:40px;' src='/weixin/foreground/images/search_tool.png'></img></a>");
}

//顶部有编辑按钮和添加按钮
function editHead(){
	var edit = document.createElement('div'); 
	edit.setAttribute('class','editImg');
	edit.onclick = 'javascript:addPublicPass();';
	edit.innerHTML = "<img src=\"/img/add.png\" style=\"height: 35px; width: 35px;\"></img>";
	
	var hd_div = document.getElementById('pagehead');  
    hd_div.insertBefore(edit,hd_div.firstChild);  
}

function addBut(){
	$("#c-ui-header-return").hide();
	$("div#header").append("<div class='addImg' onclick='ToUrl(\"/html/account/addBind.html\")'><img src='/img/add.png' style='height: 33px;'></img></div>");
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function addHeadLogout(ht) {
	var div_h = document.createElement('div'); 
	div_h.id = 'pagehead';
	div_h.innerHTML ="<h2>"+ht+"</h2><i id=\"c-ui-header-return\" class=\"returnico i_bef\" onClick=\"javascript :history.back(-1);\"></i><div class=\"clear\">"
				+"<a id=\"loginUser\" style=\"position: fixed;left: 16px;top: 14px;font-size: 16px;color: #fff;\" >游客，你好</a>"+"<a style=\"position: fixed;right: 16px;top: 14px;font-size: 16px;color: #fff;\" onclick='toBind();'>解除绑定</a>";
	
	var div_f = document.createElement("div");
	div_f.id = 'foot';
	div_f.setAttribute('class','floot');
	
	if(window.localStorage){ 
		div_f.innerHTML = localStorage.getItem("d_floot");
	}else{
		div_f.innerHTML = getCookie("d_floot");
	}
	
	var div_m = document.getElementsByTagName('body')[0];
	div_m.insertBefore(div_h,div_m.firstChild);
	div_m.insertBefore(div_f,div_m.firstChild);
}

function toBind() {
	Showbo.Msg.confirm("确定要解除绑定吗？");
	Showbo.todo = function(){
		ProgressBar.show();
		var zscgParams = '{"wxid":"WXID"}';
		openId = getCookie("openId");
		zscgParams = zscgParams.replace("WXID", openId);
		WebServiceUtil.wxZscgUnbind(zscgParams, logoutCallback);
	};
}

function logoutCallback(msg) {
	ProgressBar.hide();
	window.location.reload();
}
