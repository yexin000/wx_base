
function tplStepListItem(step, stepcontent) {

	return '<li style = "padding-top: 0px;margin-left: 6%;list-style-type:none;">'
	        +'<div style= "border-left:2px solid #f8a114;height:100px;margin-top: 0px;margin-left: 9px;margin-top: -2px;"><span class="circle">'
			+ step
			+ '</span><p class = "step_text">'
			+ stepcontent
			+ '</p></div></li>';
}
function tplStepListBottomItem(step, stepcontent) {
	
	return '<li style = "padding-top: 0px;margin-left: 6%;list-style-type:none;"><div style= "border-left:2px solid #f8a114;height:50px;margin-top: 0px;margin-left: 9px;margin-top: -2px;"><span class="circle">'
	+ step
	+ '</span><p class = "step_text">'
	+ stepcontent
	+ '</p></div></li>';
}

getJobGuideTypeNameList = function(ywlx, ywdm, bldid) {
	var params = "{\"ywlx\":\"" + ywlx + "\"" + ",\"ywdm\":\"" + ywdm + "\""
			+ ",\"bldid\":\"" + bldid + "\"}";
	return WebServiceUtil.getJobGuideDisplay(params, callback);
};

function callback(msg) {
	var jsonObject = eval("(" + msg + ")"), li_array = new Array();
	var ja = jsonObject.body;
	if (jsonObject.head.code != 1) {
		return;
	}
	for ( var i in ja) {

		var jo = ja[i];
		var step = new change(jo.bzxh).pri_ary();
		if (i != ja.length - 1) {
			li_array.push(tplStepListItem(jo.bzxh, '第' + step + '步:'
					+ jo.ywbzmc));
		}

		else {
			li_array.push(tplStepListBottomItem(jo.bzxh, '第' + step + '步:'
					+ jo.ywbzmc));
		}
	}

	$("#step_list").html(li_array.join(" "));

}

var _change = {
	ary0 : [ "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" ],
	ary1 : [ "", "十", "百", "千" ],
	ary2 : [ "", "万", "亿", "兆" ],
	init : function(name) {
		this.name = name;
	},
	strrev : function() {
		var ary = [];
		for ( var i = this.name.length; i >= 0; i--) {
			ary.push(this.name[i])
		}
		return ary.join("");
	}, // 倒转字符串。
	pri_ary : function() {
		var $this = this;
		var ary = this.strrev();
		var zero = "";
		var newary = "";
		var i4 = -1
		for ( var i = 0; i < ary.length; i++) {
			if (i % 4 == 0) { // 首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
				i4++;
				newary = this.ary2[i4] + newary; // 将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
				zero = ""; // 在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

			}
			// 关于0的处理与判断。
			if (ary[i] == '0') { // 如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
				switch (i % 4) {
				case 0:
					break;
				// 如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
				case 1:
				case 2:
				case 3:
					if (ary[i - 1] != '0') {
						zero = "零"
					}
					; // 如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
					break;

				}

				newary = zero + newary;
				zero = '';
			} else { // 如果不是“0”
				newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4]
						+ newary; // 就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
			}

		}
		if (newary.indexOf("零") == 0) {
			newary = newary.substr(1);
		}// 处理前面的0
		return newary;
	}
};
// 创建class类
function change() {
	this.init.apply(this, arguments);
}
change.prototype = _change;
