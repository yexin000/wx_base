

function decodeywlx(ywlxText){
	var ywlxdm ;
	if (ywlxText == "机动车业务") {
		ywlxdm = "9005";
	}else if (ywlxText == "驾照业务") {
		ywlxdm = "9006";
	}else if (ywlxText == "违法处理业务") {
		ywlxdm = "9007";
	}else if (ywlxText == "驾驶人考试业务"){
		ywlxdm = "9013";
	}else if(ywlxText =="非机动车业务"){
		ywlxdm = "9014";
	}
	return ywlxdm;
}

function decodeywmc(ywmc,ywlxText){
	var ywmcdm ;
	var ywlx = decodeywlx(ywlxText);
	if (ywlx == "9005") {
		switch (ywmc) {
		case '注册登记':
			ywmcdm = "A"
			break;
		case '变更登记':
			ywmcdm = "D"
			break;
		case '转移登记':
			ywmcdm = "B"
			break;
		case '抵押登记':
			ywmcdm = "E"
			break;
		case '注销登记':
			ywmcdm = "G"
			break;
		case '补换牌证合格标志':
			ywmcdm = "K"
			break;
		case '补换登记证书':
			ywmcdm = "L"
			break;
		case '临时号牌':
			ywmcdm = "O"
			break;
		case '委托检验':
			ywmcdm = "Q"
			break;
		case '变更备案':
			ywmcdm = "L"
			break;
		case '临时入境':
			ywmcdm = "H"
			break;
		case '档案更正':
			ywmcdm = "M"
			break;
		case '解除监管':
			ywmcdm = "T"
			break;
		}	
	}else if (ywlx == "9006") {
		switch (kindText) {
		case '军警换证':
			ywmcdm ="3"
			break;
		case '临时入境':
			ywmcdm ="R"
			break;
		case '补证换证':
			ywmcdm ="C"
			break;
		case '信息变更':
			ywmcdm ="L"
			break;
		case '满分学习':
			ywmcdm ="F"
			break;
		case '注销登记':
			ywmcdm ="G"
			break;
		case '年度体检':
			ywmcdm ="K"
			break;
		case '驾驶人审验':
			ywmcdm ="V"
			break;
		case '延期体检':
			ywmcdm ="T"
			break;
		case '申请、取消校车资格':
			ywmcdm ="W"
			break;
		case '注销准驾车型':
			ywmcdm ="Y"
			break;
		case '注销实习期准驾车型':
			ywmcdm ="Z"
			break;
		case '档案更正':
			ywmcdm ="H"
			break;
		case '扣证还证':
			ywmcdm ="I"
			break;
		case '注销恢复':
			ywmcdm ="N"
			break;
		case '延期换证':
			ywmcdm ="S"
			break;
		}
	}else if (ywlx == "9007") {
		switch (kindText) {
		case '违法处理':
			ywmcdm = "";
			break;
		}
	}else if (ywlx == "9013") {
		switch (kindText) {
		case '科目一':
			ywmcdm = "1";
			break;
		case '科目二':
			ywmcdm = "3";
		default:
			ywmcdm = "4";
			break;
		}
	}
	return ywmcdm;
}