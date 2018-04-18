/*
* 对日期格式为yyyymmddhhmi的时间数据进行操作
*/

// 获取当前时间
function getCurTime() {
	var myDate = new Date();
	var year = myDate.getFullYear().toString();
	var month = (myDate.getMonth() + 1) > 9 ? (myDate.getMonth() + 1).toString() : '0' + (myDate.getMonth() + 1).toString();
	var date = myDate.getDate() > 9 ? myDate.getDate().toString() : '0' + myDate.getDate().toString();
	var hour = myDate.getHours() > 9 ? myDate.getHours().toString() : '0' + myDate.getHours().toString();
	var min = myDate.getMinutes() > 9 ? myDate.getMinutes().toString() : '0' + myDate.getMinutes().toString();
	return year + month + date + hour + min;
}

// 校验YYYYMMDDHHMM日期是否正确
function checkCompleteDate(completeDate) {
	if(completeDate.length == 12) {
		var date = completeDate.substring(0,4) 
			+ '-' + completeDate.substring(4,6) 
			+ '-' + completeDate.substring(6,8);
		var time = completeDate.substring(8,12);
		if(checkDate(date) == 1 && checkTime(time) == 1) {
			return 1;
		} else {
			Showbo.Msg.alert("日期格式不正确");	
		//	eval($("#tmpt").text($.Prompt("日期格式不正确")));
			return 0;
		}
	} else if(completeDate.length == 0) {
		Showbo.Msg.alert("请填写起始结束日期");	
	//	eval($("#tmpt").text($.Prompt("请填写起始结束日期")));
	} else {
		Showbo.Msg.alert("日期长度不正确");	
	//	eval($("#tmpt").text($.Prompt("日期长度不正确")));
		return 0;
	}
	
}

// 校验YYYY-MM-DD日期是否正确（包括闰年）
function checkDate(date) {
	var reg = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
	if(!(date.match(reg))) { 
        return 0; 
    } else { 
        return 1; 
    } 
}

// 校验HHMM时间是否正确
function checkTime(time) {
	var hour = time.substring(0,2);
	var min = time.substring(2,4);
	if(hour >= 0 && hour < 24 && min >= 0 && min < 60) {
		return 1;
	}
	return 0;
}

// 计算两个时间的前后,时间格式YYMMDDHHMM
function compareTime(fistTime, secondTime) {
	var firstDate = fistTime.substring(0,4) + '-' + fistTime.substring(4,6)
					+ '-' + fistTime.substring(6,8) + ' ' + fistTime.substring(8,10)
					+ ':' + fistTime.substring(10,12) + ':' + '00';
	var secondDate = secondTime.substring(0,4) + '-' + secondTime.substring(4,6)
					+ '-' + secondTime.substring(6,8) + ' ' + secondTime.substring(8,10)
					+ ':' + secondTime.substring(10,12) + ':' + '00';
	
	var a = (Date.parse(firstDate) - Date.parse(secondDate)) / 3600 / 1000;
	if(a > 0) {
		return 1;
	} else if(a < 0) {
		return -1;
	} else {
		return 0;
	}
}