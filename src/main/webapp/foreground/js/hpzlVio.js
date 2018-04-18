function decode(hplx){
		if (hplx == "01") {
			return "大型汽车";
		}else if (hplx == "02") {
			return "小型汽车";
		}else if (hplx == "03") {
			return "使馆汽车";
		}else if (hplx == "04") {
			return "领馆汽车";
		}else if (hplx == "05") {
			return "境外汽车";
		}else if (hplx == "06") {
			return "外籍汽车";
		}else if (hplx == "07") {
			return "普通摩托车";
		}else if (hplx == "08") {
			return "轻便摩托车";
		}else if (hplx == "09") {
			return "使馆摩托车";
		}else if (hplx == "10") {
			return "领馆摩托车";
		}else if (hplx == "11") {
			return "境外摩托车";
		}else if (hplx == "12") {
			return "外籍摩托车";
		}else if (hplx == "13") {
			return "农用运输车";
		}else if (hplx == "14") {
			return "拖拉机";
		}else if (hplx == "15") {
			return "挂车";
		}else if (hplx == "16") {
			return "教练汽车";
		}else if (hplx == "17") {
			return "教练摩托车";
		}else if (hplx == "18") {
			return "试验汽车";
		}else if (hplx == "19") {
			return "试验摩托车";
		}else if (hplx == "20") {
			return "临时入境汽车";
		}else if (hplx == "21") {
			return "临时入境摩托车";
		}else if (hplx == "22") {
			return "临时行驶车";
		}else if (hplx == "23") {
			return "警用汽车";
		}else if (hplx == "24") {
			return "警用摩托车";
		}else if (hplx == "25") {
			return "原农机号牌";
		}else if (hplx == "26") {
			return "香港入出境车";
		}else if (hplx == "27") {
			return "澳门入出境车";
		}else if (hplx == "31") {
			return "武警号牌";
		}else if (hplx == "32") {
			return "军队号牌";
		}else if (hplx == "41") {
			return "无号牌";
		}else if (hplx == "42") {
			return "假号牌";
		}else if (hplx == "43") {
			return "挪用号牌";
		}else if (hplx == "99") {
			return "其他号牌";
		}
		
	}

function undecode(hplx){
	if (hplx == "大型汽车") {
		return "01";
	}else if (hplx == "小型汽车") {
		return "02";
	}else if (hplx == "使馆汽车") {
		return "03";
	}else if (hplx == "领馆汽车") {
		return "04";
	}else if (hplx == "境外汽车") {
		return "05";
	}else if (hplx == "外籍汽车") {
		return "06";
	}else if (hplx == "普通摩托车") {
		return "07";
	}else if (hplx == "轻便摩托车") {
		return "08";
	}else if (hplx == "使馆摩托车") {
		return "09";
	}else if (hplx == "领馆摩托车") {
		return "10";
	}else if (hplx == "境外摩托车") {
		return "11";
	}else if (hplx == "外籍摩托车") {
		return "12";
	}else if (hplx == "农用运输车") {
		return "13";
	}else if (hplx == "拖拉机") {
		return "14";
	}else if (hplx == "挂车") {
		return "15";
	}else if (hplx == "教练汽车") {
		return "16";
	}else if (hplx == "教练摩托车") {
		return "17";
	}else if (hplx == "试验汽车") {
		return "18";
	}else if (hplx == "试验摩托车") {
		return "19";
	}else if (hplx == "临时入境汽车") {
		return "20";
	}else if (hplx == "临时入境摩托车") {
		return "21";
	}else if (hplx == "临时行驶车") {
		return "22";
	}else if (hplx == "警用汽车") {
		return "23";
	}else if (hplx == "警用摩托车") {
		return "24";
	}else if (hplx == "原农机号牌") {
		return "25";
	}else if (hplx == "香港入出境车") {
		return "26";
	}else if (hplx == "澳门入出境车") {
		return "27";
	}else if (hplx == "武警号牌") {
		return "31";
	}else if (hplx == "军队号牌") {
		return "32";
	}else if (hplx == "无号牌") {
		return "41";
	}else if (hplx == "假号牌") {
		return "42";
	}else if (hplx == "挪用号牌") {
		return "43";
	}else if (hplx == "其他号牌") {
		return "99";
	}
}

 function carStyle(hpstyle){
	 if (hpstyle == "01") {
			return "car";
		}else if (hpstyle == "02") {
			return "car";
		}else if (hpstyle == "03") {
			return "car";
		}else if (hpstyle == "04") {
			return "car";
		}else if (hpstyle == "05") {
			return "car";
		}else if (hpstyle == "06") {
			return "car";
		}else if (hpstyle == "07") {
			return "motor";
		}else if (hpstyle == "08") {
			return "motor";
		}else if (hpstyle == "09") {
			return "motor";
		}else if (hpstyle == "10") {
			return "motor";
		}else if (hpstyle == "11") {
			return "motor";
		}else if (hpstyle == "12") {
			return "motor";
		}else  if (hpstyle == "15") {
			return "other";
		}else if (hpstyle == "16") {
			return "car";
		}else if (hpstyle == "17") {
			return "motor";
		}else if (hpstyle == "18") {
			return "car";
		}else if (hpstyle == "19") {
			return "motor";
		}else if (hpstyle == "20") {
			return "car";
		}else if (hpstyle == "21") {
			return "motor";
		}else if (hpstyle == "23") {
			return "car";
		}else if (hpstyle == "24") {
			return "motor";
		}
	
}



