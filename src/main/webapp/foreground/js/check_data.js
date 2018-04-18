
//var errorColor = "yellow";
var errorColor = ";font:12px;background:#ffff00;height:20";
var okColor = ";font:12px;background:#ffffff;height:20";
/*
功  能: 主要进行数据校验
更  新: 是建荣 2004年7月1日
*/

//利用Javascript中每个对象(Object)的prototype属性我们可以为Javascript中的内置对象添加我们自己的方法和属性。
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}
String.prototype._length = function() {
	var len = 0;
	for ( var i = 0; i < this.length; i++)
		if (this.charCodeAt(i) >= 10000) {
			len += 2;
		} else {
			len++;
		}
	return len;
}

function errshow(obj) {
	var obj_class = obj.className;
	var obj_width = obj.style.width;
	if (obj_class.indexOf("text_bg_yellow") < 0) {
		obj.className = obj_class + " text_bg_yellow";
	}
	//alert(obj.className);
	obj.style.width = obj_width;
}
function okshow(obj) {
	var obj_width = obj.style.width;
	var obj_class = obj.className;
	if (obj_class.indexOf("text_bg_yellow") > -1) {
		obj.className = obj_class.replace(" text_bg_yellow", "");
	}
	//obj.style.cssText = okColor;
	obj.style.width = obj_width;
}
//-------------------------------
//  函数名：notNull(i_field,i_value)
//  功能介绍：检查输入是否为非空
//  参数说明：数据项，输入的对应值
//  返回值  ：1-非空,0-为空
//-------------------------------

function notNull(i_field, obj) {
	var i_value = obj.value.trim();
	if (i_value == "" || i_value == null) {
		//errshow(obj);
		//alert("'" + i_field + "' 不可为空！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：notNull(i_field,i_value)
//  功能介绍：检查输入是否为非空
//  参数说明：数据项，输入的对应值
//  返回值  ：1-非空,0-为空
//-------------------------------

function notZero(i_field, obj) {
	var i_value = obj.value;

	if (i_value == "0") {
		errshow(obj);
		alert("'" + i_field + "' 不可为零！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

function notNullvalue(i_field, i_value) {
	if (i_value == "" || i_value == null) {
		errshow(obj);
		alert("'" + i_field + "' 不可为空！");
		//obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;

}

//-------------------------------
//  函数名：isNum(i_field,i_value)
//  功能介绍：检查输入字符串是否为数字
//  参数说明：数据项，输入的对应值
//  返回值  ：1-是数字,0-非数字
//-------------------------------
function isNum(i_field, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (notNull(i_field, obj) == 0) {
		//errshow(obj);
		obj.focus();
		return 0;
	}

	re = new RegExp("[^0-9.-]");
	var s = i_value.match(re);
	if (s != null && s != "") {
		//errshow(obj);
		//alert("'" + i_field + "' 中含有非法字符 '" + s + "' ！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：isPureNum(i_field,i_value)
//  功能介绍：检查输入字符串是否为数字
//  参数说明：数据项，输入的对应值
//  返回值  ：1-是数字,0-非数字
//-------------------------------
function isPureNum(i_field, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;

	re = new RegExp("[^0-9.-]");
	var s = i_value.match(re);
	if (s != null && s != "") {
		//errshow(obj);
		alert("'" + i_field + "' 中含有非法字符 '" + s + "' ！");
		obj.focus();
		return 0;
	}
	//okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：isDate(i_field,thedate)
//  功能介绍：校验字符串是否为日期格式
//  参数说明：数据项，输入的字符串
//  返回值  ：1-是日期，false-不是
//-------------------------------

function isDateBirthday(i_field, thedate, obj) {
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
	var r = thedate.match(reg);
	if (r == null) {
		errshow(obj);
		alert("'" + i_field + "' 含非法字符！");
		obj.parentNode.previousSibling.focus();
		//obj.focus();
		return 0;

	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	var newStr = d.getFullYear() + r[2] + (d.getMonth() + 1) + r[2]
			+ d.getDate();
	var newDate = r[1] + r[2] + (r[3] - 0) + r[2] + (r[4] - 0);
	//alert("----------r:"+r+" d:"+d+" newStr:"+newStr+" newDate:"+newDate);
	if (newStr == newDate) {
		okshow(obj);
		return 1;
	}
	errshow(obj);
	alert("'" + i_field + "'日期格式不对,应为YYYY-MM-DD！");
	obj.parentNode.previousSibling.focus();
	//  obj.focus();
	return 0;
}

//-------------------------------
//  函数名：isLength(i_field,i_length,i_value)
//  功能介绍：检查输入值是否为指定长度
//  参数说明：数据项，要求长度，值
//  返回值  ：1-是指定长度,0-不是
//-------------------------------
function isLength(i_field, i_length, obj) {//  alert("---长度要求:"+i_length+" "+i_value.length);
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (!(i_value.length == i_length)) {
//		errshow(obj);
//		alert("'" + i_field + "' 的长度要求为' " + i_length + " '！");
		//obj.focus();
		return 0;
	}
//	okshow(obj);
	return 1;
}

function minNumber(i_field, i_num, obj) {
	return dyNumber(i_field, i_num, obj);
}

function maxNumber(i_field, i_num, obj) {
	return xyNumber(i_field, i_num, obj);
}

function dyNumber(i_field, i_num, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (Number(i_value) < Number(i_num)) {
		errshow(obj);
		alert("'" + i_field + "' 的大小至少为 '" + i_num + "'！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}
function xyNumber(i_field, i_num, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (Number(i_value) > Number(i_num)) {
		errshow(obj);
		alert("'" + i_field + "' 的大小至多为 '" + i_num + "'！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}
//-------------------------------
//  函数名：dyLength(i_field,i_length,i_value)
//  功能介绍：检查输入值是否达到指定长度以上
//  参数说明：数据项，要求长度，值
//  返回值  ：1-符合,0-不是
//-------------------------------

function minLength(i_field, i_length, obj) {
	return dyLength(i_field, i_length, obj);
}
function dyLength(i_field, i_length, obj) { //alert("---长度要求:"+i_length+" "+i_value.length);
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (i_value.length < i_length) {
		errshow(obj);
		alert("'" + i_field + "' 的长度至少为 '" + i_length + "'！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：xyLength(i_field,i_length,i_value)
//  功能介绍：检查输入值不要超过指定长度
//  参数说明：数据项，要求长度，值
//  返回值  ：1-符合,0-不是
//-------------------------------

function maxLength(i_field, i_length, obj) {
	return xyLength(i_field, i_length, obj);
}
function xyLength(i_field, i_length, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	//alert("---长度要求:"+i_length+" "+i_value.length);
	if (i_value.length > i_length) {
		errshow(obj);
		alert("'" + i_field + "' 的长度最长为 '" + i_length + "' ！(一个中文字符长度为2)");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：check_hm(标签，长度,i_value)
//  参数说明：标签，长度,值。
//  功能介绍：检查输入号码字符串长度是否满足；是否全数字。
//  返回值  ：1-是，false-不是
//-------------------------------
function check_hm(i_field, i_length, i_value) {

	if (isLength(i_field, i_length, i_value) == 0) {
		obj.focus();
		errshow(obj);
		return 0;
	}
	if (isNum(i_field, i_value) == 0) {
		obj.focus();
		errshow(obj);
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：check_yzbm(i_value)
//  参数说明：邮政编码值。
//  功能介绍：检查邮政编码是否是6位长数字。
//  返回值  ：1-是，false-不是
//-------------------------------
function check_yzbm(obj) {
	obj.value = obj.value.trim();
	if (isLength("邮政编码", "6", obj) == 0) {
		//obj.focus();
		//errshow(obj); 
		return 0;
	}
	if (isNum("邮政编码", obj) == 0) {
		//obj.focus();
		//errshow(obj);
		return 0;
	}
	//okshow(obj);
	return 1;
}

//-------------------------------
//函数名：check_lxdh(i_value)
//参数说明：固定电话。
//功能介绍：检查邮政编码是否是6位长数字。
//返回值  ：1-是，false-不是
//-------------------------------
function check_lxdh(i_field, i_length, obj) {
	obj.value = obj.value.trim();

	var arr_gddh = new Array();
	var lxdh_tmp;

	if (obj.value == "") {
		okshow(obj);
		return 1;
	}
	if (invalidLetter(i_field, obj, "0123456789-") == 0) {
		// errshow(obj);
		return 0;
	}

	if (obj.value.substring(0, 1) == "0" && obj.value.indexOf("-") > 0) {

		arr_gddh = obj.value.split("-");
		if ((arr_gddh[0].length != 3 && arr_gddh[0].length != 4)
				|| arr_gddh[1].length  != i_length) {

			alert("'" + i_field + "'的长度要求为' " + i_length
					+ " '，区号、分机号码用“-”分割，如0510-85520722-123！");
			errshow(obj);
			obj.focus();
			return 0;
		}
		
		
		
		if(check_lxdh_repeat(arr_gddh[1],i_length)){
			alert(i_field + "联系电话不允许为同一数字!");
			obj.focus();
			errshow(obj);
			return 0;
		}
		return 1;

	}

	if (obj.value.indexOf("-") > -1) {
		arr_gddh = obj.value.split("-");
	} else {
		arr_gddh[0] = obj.value;
	}
	if (arr_gddh[0].length != i_length) {

		alert("'" + i_field + "'的长度要求为' " + i_length
				+ " '，区号、分机号码用“-”分割，如0510-85520722-123！本地不录入区号。");
		errshow(obj);
		obj.focus();
		return 0;
	}
	
	if(check_lxdh_repeat(arr_gddh[0],i_length)){
		alert(i_field + "联系电话不允许为同一数字!");
		obj.focus();
		errshow(obj);
		return 0;
	}

	okshow(obj);
	return 1;
}

//-------------------------------
//  函数名：check_sjhm(i_value)
//  参数说明：手机号码值。
//  功能介绍：检查手机号码是否是11位长数字。
//  返回值  ：1-是，false-不是
// 0-空||长度小于11；1-合法；2-前两位不合法；3-后面八位为同一数字
//-------------------------------
function check_sjhm(i_field, obj) {
	obj.value = obj.value.trim();
	var sjhm_tmp;
	var a;
	if (notNull(i_field, obj) == 0) {
		//errshow(obj);
		obj.focus();
		return 0;
	}
	if (invalidLetter(i_field, obj, "0123456789") == 0) {
		//errshow(obj); 
		return 0;
	}
	if (obj.value.length != 11) {
		//alert(i_field + "的长度要求为11位!");
		obj.focus();
		//errshow(obj);
		return 0;
	}
	

	if (isNum(i_field, obj) == 0) {
		obj.focus();
		//errshow(obj);
		return 0;
	}
	sjhm_tmp=obj.value.substr(0,2);
	if (sjhm_tmp!="13" && sjhm_tmp!="14" && sjhm_tmp!="15" && sjhm_tmp!="18" && sjhm_tmp!="17"){
		//alert(i_field + "前两位必须为“13”、“15”、“18”、“14”、“17”!");
		obj.focus();
		//errshow(obj);
		return 2;
	}
	
	
	
	//alert( sjhm_tmp);
	sjhm_tmp=obj.value.substr(3,8);
	if(check_lxdh_repeat(sjhm_tmp,8)){
		//alert(i_field + "后面8位不允许为同一数字!");
		obj.focus();
		//errshow(obj);
		return 3;
	}
	
	
	
	okshow(obj);
	return 1;
}

function check_lxdh_repeat(mValue,i_length){
	var lxdh_tmp,lxdh_tmp2;
	var tmpS;
	lxdh_tmp=mValue;
	lxdh_tmp2="";
	var tmpS=mValue.charAt(1);
	for(var k=0;k<i_length;k++){
		lxdh_tmp=lxdh_tmp.replace(tmpS,"A");
		lxdh_tmp2=lxdh_tmp2+"A";
	}
	if(lxdh_tmp==lxdh_tmp2){
		
		return 1;
	}
	return 0;
}

function check_lxdh_sjhm(obj1, obj2) {
	if (obj1.value == "" && obj2.value == "") {
		alert("固定电话或移动电话至少需录入一个！");
		obj1.focus();
		errshow(obj1);
		return 0;
	}

	return 1;

}

//-------------------------------
//  函数名：check_zjhm(zjmc,obj)
//  参数说明：证件名称，证件号码。
//  功能介绍：检查身份证号码合法性。
//	      对身份证检查是否为全数字；出生日期格式是否正确；是否<=18,<=70；校验码检查
//  返回值  ：1-是，0-不
//-------------------------------
function check_zjhm(zjmc, zjhmObj) {
	var birthday = "";
	var zjhm1 = "";
	var zjhm2 = "";
	var s = "";
	var obj = zjhmObj;
	zjhmObj.value = zjhmObj.value.trim();
	zjhmObj.value = zjhmObj.value.toUpperCase();
	var zjhm = zjhmObj.value;
	if (notNull("身份证明号码", zjhmObj) == 0) {
		return 0;
	}
	if (zjmc == "A" || zjmc == "H") { //身份证号码
		if (!(zjhm.length == 15 || zjhm.length == 18)) {
			//zjhmObj.style.cssText = errorColor;  

			errshow(obj);
			alert("身份证明号码长度不对,请检查！");
			zjhmObj.parentNode.previousSibling.focus();
			//zjhmObj.focus();
			return 0;
		}
		zjhm1 = zjhm;
		if (zjhm.length == 18) {
			zjhm1 = zjhm.substr(0, 17);
			zjhm2 = zjhm.substr(17, 1);
		}

		re = new RegExp("[^0-9]");
		s = zjhm1.match(re);
		if (s != null && s != "") {
			//zjhmObj.style.cssText = errorColor;     
			errshow(obj);
			alert("输入的值中含有非法字符'" + s + "'请检查！");
			zjhmObj.parentNode.previousSibling.focus();
			// zjhmObj.focus();
			return 0;
		}
		//取出生日期
		if (zjhm.length == 15) {
			birthday = "19" + zjhm.substr(6, 6);
		} else {
			re = new RegExp("[^0-9A-Z]");
			s = zjhm2.match(re);
			if (s != null && s != "") { //18位身份证对末位要求数字或字符
				//zjhmObj.style.cssText = errorColor;      
				errshow(obj);
				alert("输入的值中含有非法字符'" + s + "'请检查！");
				zjhmObj.parentNode.previousSibling.focus();
				//zjhmObj.focus();
				return 0;
			}
			birthday = zjhm.substr(6, 8);
		}
		birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-"
				+ birthday.substr(6, 2);
		//alert("birthday"+birthday)

		if (isDateBirthday("身份证号码出生日期", birthday, zjhmObj) == 0) { //检查日期的合法性
			return 0;
		}

		//var nl=cal_years(birthday);//求年龄
		/*
		           if (nl-0<18 || nl>70)
		              {
		               alert("申请年龄要求 18--70 ,当前 "+nl+" ！");
		              return 0;
		             }*/
		if (zjhm.length == 18) {
			return (sfzCheck(zjhm, zjhmObj)); //对18位长的身份证进行末位校验
		}
		// else{
		//  zjhmObj.value=id15to18(zjhm);
		// }

	}
	if (zjmc == "B") {
		/*if (! (zjhm.length == 9 || zjhm.length == 10)) {
		    alert("组织机构代码长度应为9位或10位！");
		    zjhmObj.parentNode.previousSibling.focus();
		    //zjhmObj.focus();
		    return 0;
		  }
		 */
	}
	//zjhmObj.style.cssText = okColor
	okshow(obj);
	return 1;
}

function check_sfzmhm_thisdate(i_field, obj, thisdate) {
	var birthday = "";
	if (obj.value.length == 15) {
		birthday = "19" + obj.value.substr(6, 6);
	} else {
		birthday = obj.value.substr(6, 8);
	}

	birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-"
			+ birthday.substr(6, 2);
	if (birthday > thisdate) {
		errshow(obj);
		alert(i_field + "出生日期不能大于今日!");
		obj.focus();
		return false;
	}
	okshow(obj);
	return true;
}

//-------------------------------
//  函数名  ：check_sg(i_value)
//  参数说明：身高。
//  功能介绍：检查身高是否为数字；是否>=100,<=250
//  返回值  ：1-是，0-不是
//-------------------------------

function check_sg(i_value) {
	if (isNum("身高", i_value) == 0) //检查身高是否为数字
	{
		return 0;
	} else {
		if ((i_value - 0) < 100 || (i_value - 0) > 250) {
			alert("'身高'合理范围应在 100--250 ！");
			return 0;
		}
	}
	return 1;
}

//-------------------------------
//  函数名  ：check_sl(i_value)
//  参数说明：视力。
//  功能介绍：检查视力是否为数字；是否>=4.9,<=5.5
//  返回值  ：1-是，false-不是
//-------------------------------

function check_sl(i_value) {
	var reg = /^(\d{1,1})(\.)(\d{1,1})$/;
	var r = i_value.match(reg);
	if (r == null) {
		alert("视力的格式应为：x.x ！");
		return 0;
	}

	if ((i_value - 0) < 4.9 || (i_value - 0) > 5.5) {
		alert("'视力'应在 4.9--5.5 范围！");
		return 0;
	}
	return 1;
}

//-------------------------------
//  函数名：isHg(bsl,tl,sz,qgjb)
//  功能介绍：辨色力,听力,上肢,躯干颈部是否合格
//  参数说明：辨色力,听力,上肢,躯干颈部
//  返回值  ：1-符合申请,0-不符合
//-------------------------------

function isHg(bsl, tl, sz, qgjb) {
	//alert(bsl+tl+sz+qgjb);
	if (!(bsl == 1)) {
		alert("'辨色力'不合格者不能申请！");
		return 0;
	}
	if (!(tl == 1)) {
		alert("'听力'不合格者不能申请！");
		return 0;
	}
	if (!(sz == 1)) {
		alert("'上肢'不合格者不能申请！");
		return 0;
	}
	if (!(qgjb == 1)) {
		alert("'躯干颈部'不合格者不能申请！");
		return 0;
	}
	return 1;
}

//-------------------------------
//  函数名：sfzCheck(hm)
//  功能介绍：对18位长的身份证进行末位校验
//  参数说明：身份证号码
//  返回值  ：1-符合,0-不符合
//-------------------------------

function sfzCheck(hm, obj) {

	var w = new Array();
	var ll_sum;
	var ll_i;
	var ls_check = "";

	w[0] = 7;
	w[1] = 9;
	w[2] = 10;
	w[3] = 5;
	w[4] = 8;
	w[5] = 4;
	w[6] = 2;
	w[7] = 1;
	w[8] = 6;
	w[9] = 3;
	w[10] = 7;
	w[11] = 9;
	w[12] = 10;
	w[13] = 5;
	w[14] = 8;
	w[15] = 4;
	w[16] = 2;
	ll_sum = 0;

	for (ll_i = 0; ll_i <= 16; ll_i++) { //alert("ll_i:"+ll_i+" "+hm.substr(ll_i,1)+"w[ll_i]:"+w[ll_i]+"  ll_sum:"+ll_sum);
		ll_sum = ll_sum + (hm.substr(ll_i, 1) - 0) * w[ll_i];

	}
	ll_sum = ll_sum % 11;

	switch (ll_sum) {
	case 0:
		ls_check = "1";
		break;
	case 1:
		ls_check = "0";
		break;
	case 2:
		ls_check = "X";
		break;
	case 3:
		ls_check = "9";
		break;
	case 4:
		ls_check = "8";
		break;
	case 5:
		ls_check = "7";
		break;
	case 6:
		ls_check = "6";
		break;
	case 7:
		ls_check = "5";
		break;
	case 8:
		ls_check = "4";
		break;
	case 9:
		ls_check = "3";
		break;
	case 10:
		ls_check = "2";
		break;
	}

	if (hm.substr(17, 1) != ls_check) {
		            //alert("身份证校验错误！------ 末位应该:"+ls_check+" 实际:"+hm.substr(17,1));
		            //obj.parentNode.previousSibling.focus();
		            //obj.focus();
		           // return "身份证校验错误！------ 末位应该:"+ls_check+" 实际:"+hm.substr(17,1);
				//eval($("#tmpt").text($.Prompt("请输入正确的驾驶证号！")));
				Showbo.Msg.alert("请输入正确的驾驶证号！");
				return 0;
//		if (confirm("号码校验错误！------ 末位应该:" + ls_check + " 实际:"
//				+ hm.substr(17, 1) )) {
			//obj.focus();
			//errshow(obj);
			//obj.parentNode.previousSibling.focus();
//			return false;
//		}
	}
	//okshow(obj);
	return 1;
}

function id15to18(zjhm) {

	var strJiaoYan = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4",
			"3", "2");
	var intQuan = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1);
	var ll_sum = 0;
	var i;
	var ls_check;
	zjhm = zjhm.substring(0, 6) + "19" + zjhm.substring(6);
	for (i = 0; i <= 16; i++) {
		ll_sum = ll_sum + (parseFloat(zjhm.substr(i, 1))) * intQuan[i];
	}
	ll_sum = ll_sum % 11;
	zjhm = zjhm + strJiaoYan[ll_sum];
	return zjhm;
}
//-------------------------------
//  函数名：isDate(i_field,thedate)
//  功能介绍：校验字符串是否为日期格式
//  参数说明：数据项，输入的字符串
//  返回值  ：0-不是，1--是
//-------------------------------

function isDate(i_field, objdate) {
	objdate.value = objdate.value.trim();
	if (objdate.value.indexOf("-") > 0)
		changeDate("-", objdate);
	if (objdate.value.indexOf("/") > 0)
		changeDate("/", objdate);
	if (objdate.value.indexOf(".") > 0)
		changeDate(".", objdate);

	var thedate = objdate.value;
	if (thedate.length == 8)//输入8位
	{
		objdate.value = thedate.substr(0, 4) + "-" + thedate.substr(4, 2) + "-"
				+ thedate.substr(6, 2);
	}
	if (thedate.length == 10)//输入10位
	{
		//       objdate.value=objdate.value.replace(".", "-");
		//       objdate.value=objdate.value.replace(".", "-");
		//       objdate.value=objdate.value.replace("/", "-");
		//       objdate.value=objdate.value.replace("/", "-");
	}

	if (thedate.length == 6)/////输入六位
	{
		var theyear = parseInt(thedate.substr(0, 2), 10);
		if (theyear <= 30) {
			objdate.value = "20" + thedate.substr(0, 2) + "-"
					+ thedate.substr(2, 2) + "-" + thedate.substr(4, 2);
		} else {
			objdate.value = "19" + thedate.substr(0, 2) + "-"
					+ thedate.substr(2, 2) + "-" + thedate.substr(4, 2);
		}
	}
	thedate = objdate.value;

	var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
	var r = thedate.match(reg);
	var obj = objdate;
	if (r == null) {
		//objdate.style.cssText = errorColor;   
		errshow(obj);
		alert("请输入正确的'" + i_field + "' ！");
		objdate.focus();
		return 0;

	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	var newStr = d.getFullYear() + r[2] + (d.getMonth() + 1) + r[2]
			+ d.getDate();
	var newDate = r[1] + r[2] + (r[3] - 0) + r[2] + (r[4] - 0);
	//alert("----------r:"+r+" d:"+d+" newStr:"+newStr+" newDate:"+newDate);
	if (newStr == newDate) {
		//changeDate(objdate);
		//objdate.style.cssText = okColor
		okshow(obj);
		return 1;
	}
	//objdate.style.cssText = errorColor;
	errshow(obj);
	alert("'" + i_field + "'日期格式不对！");
	objdate.focus();
	return 0;
}

//-------------------------------
//  函数名：changeDate(thedate)
//  功能介绍：日期yyyymmdd转换成yyyy-mm-dd格式
//  参数说明：输入日期
//  返回值  ：0-不是，1--是
//-------------------------------

function changeDate(strformate, objdate) {
	var theDate = objdate.value;
	var iIndex1 = theDate.indexOf(strformate);
	var iIndex2 = theDate.indexOf(strformate, iIndex1 + 1);
	var newYear = parseFloat(theDate.substring(0, iIndex1));
	var newMonth = parseFloat(theDate.substring(iIndex1 + 1, iIndex2));
	var newDate = parseFloat(theDate.substring(iIndex2 + 1));

	var newDay = "";
	if (newYear <= 30) {
		newDay += 2000 + newYear;
	} else {
		if (newYear > 30 && newYear < 100) {
			newDay += 1900 + newYear;
		} else {
			newDay += newYear;
		}
	}

	if (newMonth < 10) {
		newDay += "-" + "0" + newMonth;
	} else {
		newDay += "-" + newMonth;
	}

	if (newDate < 10) {
		newDay += "-" + "0" + newDate;
	} else {
		newDay += "-" + newDate;
	}

	objdate.value = newDay;

}

//-------------------------------
//  函数名：DelHeadNum(i_field,i_value)
//  功能介绍：去掉输入字符串数字开头
//  参数说明：数据项，输入的对应值
//  返回值  ：无返回值
//-------------------------------
function DelHeadNum(obj) {
	obj.value = obj.value.trim();
	var i_value = "";
	for ( var i = 0; i < obj.value.length; i++) {

		i_value = obj.value.substring(i, i + 1);
		re = new RegExp("[a-zA-Z0-9_\\. ]");
		//alert(i_value.match(re));
		if (i_value.match(re) == null) {
			obj.value = obj.value.substring(i, obj.value.length);
			return true;
		}

	}
}
//-------------------------------
//  函数名：DateAddMonth(strDate,iMonths)
//  功能介绍：获得日期加上iMonths月数后的日期
//  参数说明：strDate 日期,iMonths月数
//  返回值  ：日期
//-------------------------------
function DateAddMonth(strDate, iMonths) {
	var thisYear = parseFloat(strDate.substring(0, 4));
	var thisMonth = parseFloat(strDate.substring(5, 7));
	var thisDate = parseFloat(strDate.substring(8, 10));
	var d = thisYear * 12 + thisMonth + parseFloat(iMonths);

	var newMonth = d % 12;
	if (newMonth == 0) {
		newMonth = 12;
	}
	var newYear = (d - newMonth) / 12;
	var newDate = thisDate;
	var iMonthLastDate = getMonthLastDate(newYear, newMonth);
	if (newDate > iMonthLastDate)
		newDate = iMonthLastDate;
	var newDay = "";

	newDay += newYear;
	if (newMonth < 10) {
		newDay += "-" + "0" + newMonth;
	} else {
		newDay += "-" + newMonth;
	}

	if (newDate < 10) {
		newDay += "-" + "0" + newDate;
	} else {
		newDay += "-" + newDate;
	}
	return (newDay); // 返回日期。
}

function getMonthLastDate(iYear, iMonth) {
	var dateArray = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	var days = dateArray[iMonth - 1];
	if ((((iYear % 4 == 0) && (iYear % 100 != 0)) || (iYear % 400 == 0))
			&& iMonth == 2) {
		days = 29;
	}
	return days;
}
//-------------------------------
//  函数名：GetAddDays(strDate,iDays)
//  功能介绍：获得日期加上iDays日数后的日期
//  参数说明：strDate 日期，iDays天数
//  返回值  ：日期
//-------------------------------
function DateAddDays(strDate, iDays) {
	var dateVal; // 声明变量。

	var DyMilli = 1000 * 60 * 60 * 24; //一天所包含的秒

	strDate = strDate.substr(5, 2) + "-" + strDate.substr(8, 2) + "-"
			+ strDate.substr(0, 4);
	dateVal = Date.parse(strDate); // 解析 testdate。
	dateVal = dateVal + parseFloat(iDays) * DyMilli;

	var dateObj = new Date(dateVal);
	var newDay = "";
	var newMonth = dateObj.getMonth() + 1;
	var newDate = dateObj.getDate();
	newDay += dateObj.getYear();

	if (newMonth < 10) {
		newDay += "-" + "0" + newMonth;
	} else {
		newDay += "-" + newMonth;
	}

	if (newDate < 10) {
		newDay += "-" + "0" + newDate;
	} else {
		newDay += "-" + newDate;
	}
	return (newDay); // 返回结果。

}

function DateDiff(strDate1, strDate2) {
	var dateVal1; // 声明变量。
	var newDate1, newDate2;
	var DyMilli = 1000 * 60 * 60 * 24; //一天所包含的秒
	newDate1 = strDate1.substr(5, 2) + "-" + strDate1.substr(8, 2) + "-"
			+ strDate1.substr(0, 4);
	dateVal1 = Date.parse(newDate1); // 解析 testdate。
	newDate2 = strDate2.substr(5, 2) + "-" + strDate2.substr(8, 2) + "-"
			+ strDate2.substr(0, 4);
	dateVal2 = Date.parse(newDate2); // 解析 testdate。
	var diffdate = Math.round((dateVal1 - dateVal2) / DyMilli);
	return (diffdate); // 返回结果。
}

var keyvalue = "";
var srcStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
var objName = "";
function findCode(event, obj) {
	if (objName != obj.name) {
		objName = obj.name;
		keyvalue = "";
	}
	if (event.keyCode == 13 || event.keyCode == 8 || event.keyCode == 32) {
		keyvalue = "";
		return true;
	}
	//alert(keyvalue);
	//if(srcStr.indexOf(String.fromCharCode(event.keyCode))!=-1)
	//	keyvalue="";
	keyvalue = keyvalue + String.fromCharCode(event.keyCode).toUpperCase();
	for ( var i = 0; i < obj.options.length; i++) {
		if (obj.options[i].value.indexOf(keyvalue) != -1) {
			obj.options[i].selected;
			obj.value = obj.options[i].value;
			return true;
		}
	}
}

//自动清除输入框中的空格
function ignoreSpaces(string) {
	var temp = "";
	var i = 0;
	string = '' + string;
	splitstring = string.split(" "); //双引号之间是个空格；

	for (i = 0; i < splitstring.length; i++)
		temp += splitstring[i];
	return temp;
}

//光标移动至最后
function movelast() {
	var e = event.srcElement;
	var r = e.createTextRange();
	r.moveStart('character', e.value.length);
	r.collapse(true);
	r.select();
}

function removeSelect(obj, strvalue) {
	var i = 0;
	for (i = obj.options.length - 1; i >= 0; i--) {
		if (strvalue.indexOf(obj.options[i].value) >= 0) {
			obj.options[i] = null;

		}
	}

	return true;
}
//保留可选项
function reserveSelect(obj, strvalue) {
	var i = 0;
	for (i = obj.options.length - 1; i >= 0; i--) {
		if (strvalue.indexOf(obj.options[i].value) >= 0) {
		} else {
			obj.options[i] = null;
		}
	}

	return true;
}
//设置Select标签被选择项
function setSelect(obj,strvalue){
	var i=0;
	for(i=obj.options.length-1;i>=0;i--){
		if (strvalue==obj.options[i].value) {
			obj.options[i].selected=true;
			break;
		} 
	}
}
//获得补换领原因
function get_bhlyy(bhlobj, bhlyy_value, bhlyyobj) {
	var i = 0;
	for (i = bhlobj.length - 1; i >= 0; i--) {
		bhlobj.options[i] = null;
	}

	for (i = 0; i < bhlyyobj.length; i++) {
		bhlobj.options[i] = new Option(bhlyyobj.options[i].text);
		bhlobj.options[i].value = bhlyyobj.options[i].value;
	}
	reserveSelect(bhlobj, bhlyy_value);
}

function invalidLetter(m_fields, obj, strLetter) {
	var m_Value = obj.value;
	var i, c;
	for (i = 0; i < m_Value.length; i++) {
		c = m_Value.charAt(i); //字符串s中的字符
		if (strLetter.indexOf(c) == -1) {
			errshow(obj);
			alert(m_fields + "含有非法字符‘" + c + "’!");
			obj.focus();
			return 0;
		}
	}
	okshow(obj);
	return 1;
}

function RegexpTest(m_fields, obj, strReg, strErr, bnull) {
	var m_value = obj.value;
	if (m_value.length == 0 && !bnull) {
		alert(m_fields + "不可为空!");
		errshow(obj);
		return false;
	}
	if (m_value.length == 0 && bnull) {
		okshow(obj);
		return true;

	}
	if (strReg.test(m_value)) {
		okshow(obj);
		return true;
	} else {
		alert(m_fields + strErr);
		//obj.style.cssText = errorColor;
		errshow(obj);
		return false;
	}
}

//检测Email地址的合法性
function checkEmail(m_fields, obj, isNull) {
	obj.value = obj.value.trim();
	if (isNull) {
		if (obj.value.length > 0) {
			if ((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
					.test(obj.value)) == false) {
				//obj.style.cssText = errorColor;
				errshow(obj);
				alert(m_fields + "输入有误！");
				obj.focus();
				return false;
			}
		}
	} else {
		if (obj.value.length > 0) {
			if ((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
					.test(obj.value)) == false) {
				errshow(obj);
				alert(m_fields + "输入有误！");
				obj.focus();
				return false;
			}
		} else {
			errshow(obj);
			alert(m_fields + "不能为空！");
			obj.focus();
			return false;
		}
	}
	okshow(obj);
	return true;
}
/**
 * 取下拉列表选择的内容
 * @param obj 下拉列表对下
 * @return
 */

function getSelectText(obj) {
	var strValue = obj.options[obj.selectedIndex].text;
	strValue = strValue.substr(strValue.indexOf(":") + 1);
	return strValue;
}

//-------------------------------
//函数名：maxDate(i_field,s_date,obj)
//功能介绍：检查输入最大值日期
//参数说明：数据项，最大值日期，值
//返回值  ：1-符合,0-不是
//-------------------------------
function maxDate(i_field, s_date, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (i_value > s_date) {
		errshow(obj);
		alert("'" + i_field + "' 不能大于 '" + s_date + "' ！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//函数名：maxDate(i_field,s_date,obj)
//功能介绍：检查输入最大值日期
//参数说明：数据项，最大值日期，值
//返回值  ：1-符合,0-不是
//-------------------------------
function minDate(i_field, s_date, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (i_value < s_date) {
		errshow(obj);
		alert("'" + i_field + "' 不能小于 '" + s_date + "' ！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//函数名：maxNumber(i_field,s_date,obj)
//功能介绍：检查输入最大数字
//参数说明：数据项，最大数字，值
//返回值  ：1-符合,0-不是
//-------------------------------
function maxNumber(i_field, s_number, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (parseFloat(i_value) > s_number) {
		errshow(obj);
		alert("'" + i_field + "' 不能大于 '" + s_number + "' ！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

//-------------------------------
//函数名：maxDate(i_field,s_number,obj)
//功能介绍：检查输入最小数字
//参数说明：数据项，最小数字，值
//返回值  ：1-符合,0-不是
//-------------------------------
function minNumber(i_field, s_number, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (parseFloat(i_value) < s_number) {
		errshow(obj);
		alert("'" + i_field + "' 不能小于 '" + s_number + "' ！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

function checkHphm(hpzl, hphm) {
	
	var hpzl6List = "01,02,06,07,08,12,13,14,20,21,03";
	var hpCount = 6;
	if (hpzl6List.indexOf(hpzl) < 0) {
		hpCount = 5;
	}
	if (hpzl == "22") {
		if (hphm.length != 5 && hphm.length != 6) {
			//alert("号牌号码长度不正确！");
			Showbo.Msg.alert("号牌号码长度不正确");
			//eval($("#tmpt").text($.Prompt("号牌号码长度不正确")));
			return 0;
		}
	} else {
		if (hphm.length != hpCount) {
			//alert("号牌号码长度不正确！");
			//eval($("#tmpt").text($.Prompt("号牌号码长度不正确")));
			Showbo.Msg.alert("号牌号码长度不正确");
			return 0;
		}
	}
	var CH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	if (CH.indexOf(hphm.substr(0, 1)) < 0 && hpzl != "03" && hpzl != "09") {
		///alert("发牌代号必须是字母！");
		return 0;
	}
	var num = "0123456789";
	var chr = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";

	var GbMask = "";
	var GbMaskList = "00000,10000,11000,01000,00100,00010,00001,10001,00011,10100,01100,10010,01010,01001,00110,00101,0000,1000,1100,0100,0010,0001,1010,0110,1001,0101,0011,";

	for ( var i = 0; i < hphm.length; i++) {
		var s = hphm.substr(i, 1);
		if (i > 0) {
			if (!(chr.indexOf(s) >= 0)) {
				//alert("号牌号码中存在非法的[" + s + "]！");
				Showbo.Msg.alert("号牌号码中存在非法的[" + s + "]");
				//eval($("#tmpt").text($.Prompt("号牌号码中存在非法的[" + s + "]")));
				return 0;
			}
			if (num.indexOf(s) >= 0) {
				GbMask = GbMask + "0";
			} else {
				GbMask = GbMask + "1";
			}
		}
	}

	//if (!(GbMaskList.indexOf(GbMask + ",") >= 0)) {
		//alert("号牌号码必须符合GB36-2007标准！");
	//	return 0;
	//}
	return 1;
}

/*
 * 检测输入号牌号码是否正确 包括 警车，挂车，教练车等 
 * obj :号牌号码,
 * objzl:号牌种类 ,
 * m_fzjg:发证机关
 * 返回false:不正确，true:正确
 */
function checkHphm2(obj, objzl, m_fzjg) {
	obj.value = obj.value.trim();
	obj.value = obj.value.toUpperCase();
	var s_value = obj.value;
	var zl_value = objzl.value.trim();
	var i_valueLen = 0;
	var s_tmp = "";
	var b_hphm_len = true;
	var m_fzjg_first = "";
	if (s_value.length == 0) {
		//errshow(obj);
		//alert("号牌号码不能为空！");
		obj.focus();
		return 0;
	} else {
		i_valueLen = s_value.length;
		// 原农机号牌
		if (zl_value == "25") {
			if (i_valueLen < 6) {
				errshow(obj);
				alert("号牌号码长度不对，应为大于等于6位！");
				obj.focus();
				return false;
			} else {
				return true;
			}

		}

		m_fzjg_first = m_fzjg.substring(0, 1);
		if (zl_value == "01" || zl_value == "02" || zl_value == "07"
				|| zl_value == "08") {

			if (m_fzjg_first == "京" || m_fzjg_first == "津" || m_fzjg == "浙A"
					|| m_fzjg == "粤B") {
				if (i_valueLen != 6 && i_valueLen != 7) {
					b_hphm_len = false;
				}
			} else if (i_valueLen != 6) {
				b_hphm_len = false;
			}

		} else if (zl_value == "04" || zl_value == "05" || zl_value == "09"
				|| zl_value == "10" || zl_value == "11" || zl_value == "15"
				|| zl_value == "16" || zl_value == "17" || zl_value == "18"
				|| zl_value == "19" || zl_value == "23" || zl_value == "24"
				|| zl_value == "26" || zl_value == "27") {
			// 挂车，教练车,警车
			if (i_valueLen != 5) {
				b_hphm_len = false;
				errshow(obj);
				alert("号牌号码长度不对，应为5位！");
				obj.focus();
				return false;
			}

		} else {
			if (i_valueLen != 6) {
				b_hphm_len = false;
			}
		}
		if (b_hphm_len == false) {
			errshow(obj);
			alert("号牌号码长度不对，应为6位！");
			obj.focus();
			return false;
		}
		s_tmp = s_value;

		// 检测是否有特殊符号,部分省的警车号牌第一位是数字
		if (!(zl_value == "03" || zl_value == "09" || zl_value == "23"
				|| zl_value == "24" || zl_value == "25")) {
			re = new RegExp("[^A-Z]");
			if (s = s_tmp.substring(0, 1).match(re)) {
				errshow(obj);
				alert("输入的值中含有非法字符'" + s + "'请检查！");
				obj.focus();
				return false;
			}
		}

		// 检测是否有特殊符号
		re = new RegExp("[^0-9A-Za-z]");
		if (s = s_tmp.match(re)) {
			errshow(obj);
			alert("输入的值中含有非法字符'" + s + "'请检查！");
			obj.focus();
			return false;
		}
	}
	okshow(obj);
	return true;
}

/*
 * 检测输入行驶证编号
 * obj :号牌号码,
 * objzl:号牌种类 ,
 * m_fzjg:发证机关
 * 返回false:不正确，true:正确
 */

function checkXszbh(i_field, obj) {
	obj.value = obj.value.trim();
	var i_value = obj.value;
	if (notNull(i_field, obj) == 0) {
		errshow(obj);
		obj.focus();
		return 0;
	}

	if (isLength(i_field, 13, obj) == 0) {
		errshow(obj);
		obj.focus();
		return 0;

	}

	re = new RegExp("[^0-9.-X]");
	var s = i_value.match(re);
	if (s != null && s != "") {
		errshow(obj);
		alert("'" + i_field + "' 中含有非法字符 '" + s + "' ！");
		obj.focus();
		return 0;
	}
	okshow(obj);
	return 1;
}

/*
 * 检测输入的流水号
 * 返回false:不正确，true:正确
 */

function checkLsh(i_field, obj) {

	obj.value = obj.value.trim();
	okshow(obj);
	var i_value = obj.value;
	if (notNull(i_field, obj) == 0) {
		errshow(obj);
		obj.focus();
		return 0;
	}

	if (isLength(i_field, 13, obj) == 0) {
		errshow(obj);
		obj.focus();
		return 0;

	}

	re = new RegExp("[^0-9A-X]");
	var s = i_value.match(re);
	if (s != null && s != "") {
		errshow(obj);
		alert("'" + i_field + "' 中含有非法字符 '" + s + "' ！");
		obj.focus();
		return 0;
	}

	return 1;
}

function setobjwrite(objname) {
	document.all[objname].readOnly = false;
	document.all[objname].style.border = "1px solid #7f9db9";
	document.all[objname].style.backgroundColor = "#F7F7F7";
}

function setobjreadonly(objname) {
	document.all[objname].readOnly = true;
	document.all[objname].style.border = "none";
	document.all[objname].style.backgroundColor = "#F7F7F7";
}
/**
 * 去除非法字符
 * @param obj
 * @return
 */

function DelInvalidChar(obj) {
	obj.value = obj.value.replace(/(\\*)/g, "");
	obj.value = obj.value.replace(/(\'*)/g, "");
	obj.value = obj.value.replace(/(\"*)/g, "");
}

//20090703 James 智能更新地址
function setNewAddr(pos, obj) {
	if (obj.value == "") {
		obj.value = Dmsm2Arrary[pos];
		return;
	}
	var addr = obj.value;
	var k = 0;
	var i = 0;
	for (i = Dmsm2Arrary.length; i >= 0; i--) {
		if (Dmsm2Arrary[i] != "" && addr.indexOf(Dmsm2Arrary[i]) >= 0) {
			addr = addr.replace(Dmsm2Arrary[i], "");
			k = 1;
		}
	}
	if (k == 0) {
		obj.value = Dmsm2Arrary[pos];
	} else {
		addr = Dmsm2Arrary[pos] + addr;
		obj.value = addr;
	}
}

function copySjdz_tmp() {
	if (tmri.tmp_zzxxdz.value.length == 0) {
		tmri.tmp_zzxzqh.value = tmri.tmp_zsxzqh.value;
		tmri.tmp_zzxxdz.value = tmri.tmp_zsxxdz.value;
	}
}

//检验组织机构代码值
function checkZzjgdm(code) {
	code = code.replace("-", "");
	var reg = /^$|^[0-9|A-Z]{8}[0-9|X]$/;
	var str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var ws = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
	var str_checkSum = "";
	var int_checkSum = 0;
	if (code.length != 9) {
		alert("组织机构代码数据格式不对!");
		return 0;
	}
	if (reg.test(code) == false) {
		alert("组织机构代码数据格式不正确!");
		return 0;
	} else {
		var sum = 0;
		for ( var i = 0; i < 8; i++) {
			sum += str.indexOf(code.charAt(i)) * ws[i];
		}
		int_checkSum = 11 - (sum % 11);
		if (int_checkSum == 11) {
			str_checkSum = "0";
		} else if (int_checkSum == 10) {
			str_checkSum = "X";
		} else {
			str_checkSum = int_checkSum;
		}
		if (str_checkSum != code.charAt(8)) {
			if (confirm("组织机构代码校验错误！------ 末位应该:" + str_checkSum + " 实际:"
					+ code.substr(8, 1) + "! \n 需修改请按‘确定’,不需修改请按‘取消’!")) {
				//obj.focus();
				//        	     errshow(obj); 
				//        	     obj.parentNode.previousSibling.focus();
				return 0;
			}

			//            alert("组织机构数据验证位不正确!");
			//            return 0;
		}
	}
	return 1;
}

//检验组织机构代码值
function checkZzjgdmObj(obj) {
	var code = obj.value;
	code = code.replace("-", "");
	var reg = /^$|^[0-9|A-Z]{8}[0-9|X]$/;
	var str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var ws = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
	var str_checkSum = "";
	var int_checkSum = 0;
	if (code.length != 9) {
		alert("组织机构代码数据格式不对!");
		return 0;
	}
	if (reg.test(code) == false) {
		alert("组织机构代码数据格式不正确!");
		return 0;
	} else {
		var sum = 0;
		for ( var i = 0; i < 8; i++) {
			sum += str.indexOf(code.charAt(i)) * ws[i];
		}
		int_checkSum = 11 - (sum % 11);
		if (int_checkSum == 11) {
			str_checkSum = "0";
		} else if (int_checkSum == 10) {
			str_checkSum = "X";
		} else {
			str_checkSum = int_checkSum;
		}

		if (str_checkSum != code.charAt(8)) {
			if (confirm("组织机构代码校验错误！------ 末位应该:" + str_checkSum +" 实际:" + code.substr(8, 1) + "! \n 需修改请按‘确定’,不需修改请按‘取消’!")) {
				errshow(obj);
				obj.focus();
				return 0;
			}

			//            alert("组织机构数据验证位不正确!");
			//            return 0;
		}
	}
	return 1;
}





function check_lxdz_sjhm(new_lxdz_obj ,old_lxdz_value,new_sjhm_obj,old_sjhm_value){
	okshow(new_lxdz_obj);
	okshow(new_sjhm_obj);
	var obj;
	//var ret ;
	 if (new_lxdz_obj.value==old_lxdz_value && new_sjhm_obj.value==old_sjhm_value){
		 //ret= displayDialog("4","请核实机动车所有人联系地址、手机号码是否变更！","confirm");
		 //alert (ret);
		 //msgbox('核实联系信息?','【注意】<font color=red>请核实机动车所有人联系地址、手机号码是否变更！</font>,点击【修改】进行修改,点击【取消】无需修改!','return 0;',1,1);

		 if (!confirm("请核实机动车所有人联系地址、手机号码是否变更！\r\n如联系地址、手机号码正确，请按【确定】,否则请按【取消】！")) {
	        if (new_sjhm_obj.value==old_sjhm_value){
	        	obj=new_sjhm_obj;
	        }else{
	        	obj=new_lxdz_obj;
	        }
	        errshow(obj);
	        obj.focus();
	        return 0;
	 	} 
	 }
	 return 1; 
}

function checkClsbm(clsbm){ 
	var re = new RegExp("^[A-Za-z0-9]{6}$");
	if (re.test(clsbm) && (clsbm.length == 6)) {
		return 1;
		
	} else {
		Showbo.Msg.alert("请输入正确的车辆识别代号末六位");
		//eval($("#tmpt").text($.Prompt("请输入正确的车辆识别代号末六位")));
		return 0;
	}
}

function checkHphm3(hphm) {
	var re=/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	if(hphm.search(re)==-1) {
		
		return 0;
	}
	
	return 1;
}

function checkFdjh(fdjh) {
	
//	var re = new RegExp("^[A-Za-z0-9-*]{6}$");
//	if ((re.test(fdjh)) && (fdjh.length == 6)) {
		if (fdjh.length == 6) {
		return 1;
	} else {
		Showbo.Msg.alert("请输入正确的发动机号末六位");
		//eval($("#tmpt").text($.Prompt("请输入正确的发动机号末六位")));
		return 0;
	}
	
	return 1;
}

function isValidMobileNum(num) {
	var re = /1((3\d)|(4[57])|(5[012356789])|(7[8])|(8\d))\d{8}/;
	if(re.test(num) && num.length == 11) {
		return 1;
	} else {
		Showbo.Msg.alert("请输入正确的手机号码");
		//eval($("#tmpt").text($.Prompt("请输入正确的手机号码")));
		return 0;
	}
}


function isValidPostalCode(yzbm) {
	var re =  /^\d{6}$/;
	if(re.test(yzbm)) {
		return 1;
	} else {
		Showbo.Msg.alert("请输入正确的邮政编码");
	//	eval($("#tmpt").text($.Prompt("请输入正确的邮政编码")));
		return 0;
	}
}

function isValidTelNum(num) {
	var re = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
	if(re.test(num)) {
		return 1;
	} else {
		Showbo.Msg.alert("请输入正确的联系电话");
	//	eval($("#tmpt").text($.Prompt("请输入正确的联系电话")));
		return 0;
	}
}

function isValidEmail(yxdz) {
	var re  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(re.test(yxdz)) {
		return 1;
	} else {
		Showbo.Msg.alert("请输入正确的邮箱地址");
		//eval($("#tmpt").text($.Prompt("请输入正确的邮箱地址")));
		return 0;
	}
}

function showContactDlg(m_hpzl,m_hphm){
	openwin1024("fzyw.veh?method=frd_fzywlist&cdbh=A123&gnid=0133&"+"&Hphm="+m_hphm+"&Hpzl="+m_hpzl,"vehsubwin2","yes");
	
}


function showVioListDlg(m_hpzl,m_hphm,m_cdbh,m_gnid){
	openwin800("CommBusiness.veh?method=getVioCheck&Hphm="+m_hphm+"&Hpzl="+m_hpzl+"&cdbh="+m_cdbh+"&gnid="+m_gnid,"vehsubwinvio","yes");
}


function showStolenCarDlg(m_clsbdh,m_fdjh,m_xh,m_lsh,m_czgw) {
	openwin800("CommBusiness.veh?method=getVehStolen&clsbdh="+m_clsbdh+"&fdjh="+m_fdjh+"&xh="+m_xh+"&lsh="+m_lsh+"&czgw="+m_czgw,"vehsubwinstolen","yes");
   
}  

/*
 * 函数名：check_jszh(jszh,obj)
 * 功能介绍：驾驶证号验证
 * 参数说明：身份证号
 * 返回值  ：1-校验成功,0-校验失败
 */
function check_jszh(jszh,obj){
	
	var re  = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	
	if(re.test(jszh)) {
		if (jszh.length<18) {
			Showbo.Msg.alert("请输入正确的驾驶证号");
		//	eval($("#tmpt").text($.Prompt("请输入正确的驾驶证号")));
			return 0;
		}
		return 1;
	} else {
		Showbo.Msg.alert("请输入正确的驾驶证号");
		//eval($("#tmpt").text($.Prompt("请输入正确的驾驶证号")));
		return 0;
	} 
//	if (jszh == "") {
//		return 0;
//	} else {
//		if (jszh.length <18) {
//			return 0;
//		} else {
//			var code1 = sfzCheck(jszh,obj);
//			if (code1 == 1) {
//				return 1;
//			} else {
//				eval($("#tmpt").text($.Prompt("请输入正确的驾驶证号")));
//				return 0;
//			}
//		}
//	}
}

/*
 * 函数名：check_jszh(dabh)
 * 功能介绍：驾驶证档案编号验证
 * 参数说明：档案编号
 * 返回值  ：1-校验成功,0-校验失败
 */
function check_dabh(dabh){
	if (dabh == "") {
		Showbo.Msg.alert("请输入档案编号");
		//eval($("#tmpt").text($.Prompt("请输入档案编号")));
		return 0;
	} else {
		if (dabh.length < 4) {
			Showbo.Msg.alert("请输入正确的档案编号");
			//eval($("#tmpt").text($.Prompt("请输入正确的档案编号")));
			return 0;
		} else {
			
			return 1;
		}
	}
}

/*
 * 函数名：check_sbdh(sbdh)
 * 功能介绍：识别代号验证
 * 参数说明：识别代号
 * 返回值  ：1-校验成功,0-校验失败
 */
function check_sbdh(sbdh){
	if (sbdh == "") {
		Showbo.Msg.alert("请输入车辆识别代号！");
		//eval($("#tmpt").text($.Prompt("请输入车辆识别代号！")));
		return 0;
	} else {
		if (sbdh.length <6 || sbdh.length >6) {
			Showbo.Msg.alert("请输入正确的车辆识别代号！");
		//	eval($("#tmpt").text($.Prompt("请输入正确的车辆识别代号！")));
			return 0;
		} else {
			return 1;
		}
	}
}

function check_dlwz(val){
	if (val == "") {
		Showbo.Msg.alert("您输入的\"停车位置\"不正确！");
		//eval($("#tmpt").text($.Prompt("您输入的\"停车位置\"不正确！")));
		return 0;
	} else {
		return 1;
	}
}

function check_wfdm(val){
	if (val == "") {
		Showbo.Msg.alert("请输入违法代码");
		//eval($("#tmpt").text($.Prompt("请输入违法代码")));
		return 0;
	}
	return 1;
}

//
//车辆识别代号
//
function checkClsbdh(m_fields, m_SourceValue) {
 //obj.value = obj.value.toUpperCase();
 //var m_Value = obj.value;
 var m_Value = m_SourceValue.toUpperCase();
 var strLetter = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
 var i, c;
 if(m_Value=="" || m_Value.length == 0){
 	//alert(m_fields + "不能为空！");
 	//obj.focus();
 	$("#confirm").html(m_fields + "不能为空！继续申请请点击确定，返回修改请点击取消。");
 	return 0;
 }
 for (i = 0; i < m_Value.length; i++) {
   c = m_Value.charAt(i); //字符串s中的字符
   if (c.charCodeAt(0)<255 && strLetter.indexOf(c) == -1) {
	  $("#confirm").html(m_fields + "含有非法字符‘" + c + "’! 继续申请请点击确定，返回修改请点击取消。");
      //alert("车辆识别代号含有非法字符‘" + c + "’! ");
      // obj.focus();
       return 0;
   }
 }
 for (i = 0; i < m_Value.length; i++) {
   c = m_Value.charAt(i); //字符串s中的字符
   if(getWordsCount(m_Value,c)>15){
	   $("#confirm").html(m_fields +"校验不符合规则！继续申请请点击确定，返回修改请点击取消。");  
   	 //alert("-3:车辆识别代号校验不符合规则！");
      // obj.focus();
       return 0;
   }
 }
 if(!isNaN(m_Value))
	{
	 	$("#confirm").html(m_fields + "校验不符合规则！继续申请请点击确定，返回修改请点击取消。");
		//alert(m_fields + "-2:车辆识别代号校验不符合规则！");
		//obj.focus();
     return 0;
	} 
 if (m_Value.length != 17) {
	$("#confirm").html(m_fields + "长度不是17位!继续申请请点击确定，返回修改请点击取消。");
   //alert(m_fields + "长度不是17位!")
     //obj.focus();
     return 0;
   
 }
 if (m_Value.length == 0) {
   return 0;
 }
 if (m_SourceValue.length == 0) {
   return chech_clshdh_Jyw(m_Value);
 }
 var i = 1;
 if (m_Value.indexOf("×") >= 0) {
	 $("#confirm").html(m_fields + "含有×!!!继续申请请点击确定，返回修改请点击取消。");
  //alert(m_fields + "含有×!!!");
  // obj.focus();
   return 0;
 }
 return 1;
}
function getWordsCount(str1,words){   
 var str   =   str1;
	var keyword   =   words   //关键字     
	eval("reg=/"+keyword+"/g");   
	var i=str.match(reg).length;
	return i;
}   
function chech_clshdh_Jyw(obj) {
//一、数字和字母的数值
//   阿拉伯数字指定值为实际数字，罗马字母数值如下：
//  A B C D E F G H J K L M N P R S T U V W X Y Z
//  1 2 3 4 5 6 7 8 1 2 3 4 5 7 9 2 3 4 5 6 7 8 9
//
//二、位置加权系数
//  1 2 3 4 5 6 7 8  9 10 11 12 13 14 15 16 17
//  8 7 6 5 4 3 2 10 0 9  8  7  6  5  4  3  2

 var strClsbdh = obj.value;

 var strLetter = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
 var arrayValue = new Array(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 7, 9, 2,
                            3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8,
                            9);
 var arrayIndex = new Array(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3,
                            2);

 var iSum = 0;
 var SingleLetter = "";
 for (i = 0; i < 17; i++) {
   iSum += arrayValue[strLetter.indexOf(strClsbdh.substr(i, 1))] *
       arrayIndex[i];
 }
 var checkValue = iSum % 11;
 var strCheckValue = checkValue + "";
 if (checkValue == 10) {
   strCheckValue = "X";
 }

 if (strClsbdh.substr(8, 1) != strCheckValue ) {
   
   //alert("车辆识别代号第九位不符合规则，应该为:"+strCheckValue+"！");
	 $("#confirm").html(m_fields + "校验不符合规则！继续申请请点击确定，返回修改请点击取消。");
   //alert("-1:车辆识别代号校验不符合规则！");
   //obj.focus();
   return 0;
 }
 return 1;
}

//验证身份证号码
function sfzhmCheck(sfzhm, obj) {

	var w = new Array();
	var ll_sum;
	var ll_i;
	var ls_check = "";

	w[0] = 7;
	w[1] = 9;
	w[2] = 10;
	w[3] = 5;
	w[4] = 8;
	w[5] = 4;
	w[6] = 2;
	w[7] = 1;
	w[8] = 6;
	w[9] = 3;
	w[10] = 7;
	w[11] = 9;
	w[12] = 10;
	w[13] = 5;
	w[14] = 8;
	w[15] = 4;
	w[16] = 2;
	ll_sum = 0;

	for (ll_i = 0; ll_i <= 16; ll_i++) { //alert("ll_i:"+ll_i+" "+hm.substr(ll_i,1)+"w[ll_i]:"+w[ll_i]+"  ll_sum:"+ll_sum);
		ll_sum = ll_sum + (sfzhm.substr(ll_i, 1) - 0) * w[ll_i];

	}
	ll_sum = ll_sum % 11;

	switch (ll_sum) {
	case 0:
		ls_check = "1";
		break;
	case 1:
		ls_check = "0";
		break;
	case 2:
		ls_check = "X";
		break;
	case 3:
		ls_check = "9";
		break;
	case 4:
		ls_check = "8";
		break;
	case 5:
		ls_check = "7";
		break;
	case 6:
		ls_check = "6";
		break;
	case 7:
		ls_check = "5";
		break;
	case 8:
		ls_check = "4";
		break;
	case 9:
		ls_check = "3";
		break;
	case 10:
		ls_check = "2";
		break;
	}

	if (sfzhm.substr(17, 1) != ls_check) {
				Showbo.Msg.alert("请输入正确的身份证号！");
				return 0;
	}
	return 1;
}





