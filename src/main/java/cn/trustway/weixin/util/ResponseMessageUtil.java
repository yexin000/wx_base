package cn.trustway.weixin.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.trustway.weixin.message.resp.Article;
import cn.trustway.weixin.message.resp.NewsMessage;

public class ResponseMessageUtil {
	public static final String DEFAULT_TEXT = "";

	public static final String WELCOME_STRING = "您好，感谢您关注测试公众平台！";
			/*"您好，感谢您关注金华交警！\n"
			+ "我们将通过微信平台为您提供以下服务，请根据微信菜单导航进行操作：\n"
			+ "信息查询：违法查询，路况查询，交管查询，办事指南，法律法规；\n"
			+ "便民服务：事故快处快撤，更多服务；\n"
			+ "警民互动：投诉建议，行车宝典，吕奇说交通。";*/

	public static final String HELP_STRING = "您好，感谢您关注金华交警公众平台，请点击下方菜单获取服务。";

	public static final String SEND_PIC_STRINGS = "如您发现交通设施（含交通信号灯，标志，标线，护栏等）存在故障、错误、不合理的，可简单文字描述（含地点）或上传照片:";

	public static final String NO_SEND_PIC_STRING = "您还没有上传图片，请先上传图片。";

	public static final String SEND_LOC_STRING = "请上传您的地理位置信息以便我们定位故障设施。\n"
			+ "操作方法：点击“+”号按钮选择“位置-发送位置”。";

	// public static final String SEND_TEXT_DETAIL =
	// "如果回复文字，请尽量详细地描述故障设施的问题以及故障设施的位置。";

	public static final String SEND_TEXT_DETAIL = "您还可回复文字描述地点或点击“+”上传位置";

	public static final String DEVICE_TEXT_DESC = "请简单描述故障或地点:";

	public static final String COMMIT_ENDED = "您上传的信息已收到，感谢您对交管工作的关心和支持！";

	public static final String COMMIT_RETURN = "交流反馈：\n a.交通信息举报\n b.市民提问\n c.公告发布\n d.法律法规 \n 请输入您选择的操作编号，如：举报交通信息输入 ‘a’";

	public static final String TIP_MSG = "有图有真像，请上传需要举报的交通信息现场图片吧！\n操作方法：点击“+”号按钮选择“图片-拍摄照片“。最多可以上传三张照片。";

	public static final String TIP_MSG_DETAIL = "\n在您照片上传完毕后，请尽量详细地描述违法现场的情况以及违法现场的位置。以便我们及时的处理。";

	public static final String TIP_MSG_RESP = "您好，您的举报信息已收到，我们会尽快处理！";

	// 微信默认回复
	public static final String DEFAULT_RESPS = "您好，如需联系我们，请选择“警民互动”版块，进入“投诉建议”留言。";

	// 事故快处快撤步骤概略
	public static final String ACCIDENT_MSG_RESP = "微信处理事故须知    "
			+ "<a href=\"http://nbwxtest.hzcdt.com/troubleTipOne.html\">帮助手册</a>\n"
			+ "1.仅限机动车互碰的车损交通事故\n" + "2.撤离事故现场前要拍照固定证据\n" + "3.应相互查验对方驾驶证\n"
			+ "同意微信处理的，回复\"1\"，需要测试体验的，回复\"0\".";
	public static final String ACCIDENT_MSG_TAKEPHOTOS = "请用您的手机拍摄能反映事故现场、各方车牌号码和车辆碰撞部位的若干张照相，固定保存证据，然后任选一张照相上传。";

	// 事故快处快撤民警步骤概略
	public static final String ACCIDENT_MSG_RESP_POLICE = "感谢您使用事故快处快撤功能。\n"
			+ "使用事故快处快撤功能您需要完成以下步骤：\n" + "1.上报事故发生行政区域。\n" + "2.录入民警警号或协警编号。\n"
			+ "3.上报当事人的手机号码。\n" + "4.上传事故现场前方5米前景照片。\n" + "5.上传事故现场后方5米后景照片。\n"
			+ "6.上传车辆接触损坏部位照片。\n" + "7.上传当事人证件照片。\n" + "8.上传事故认定意见。\n"
			+ "请回复“y”开始事故快处快撤功能，回复“n”取消事故快处快撤功能。\n";

	// 金华事故快处快撤步骤概略
	/*public static final String ACCIDENT_MSG_RESP_JINHUA = "感谢您使用事故快处快撤功能。\n使用事故快处快撤功能您需要完成以下步骤：\n"
			+ "1.上传事故现场全景照片。\n2.上传事故现场损坏部位照片。\n3.上传当事人证件照片。\n4.上传您的位置信息。\n"
			+ "请回复“y”开始事故快处快撤功能，回复“n”取消事故快处快撤功能。\n"
			+ "<a href=\"http://jhwxtest.hzcdt.com/weixin/foreground/jsp/weixinAcciGuid.jsp\">点击查看事故快处快撤简介</a>";*/
	public static final String ACCIDENT_MSG_RESP_JINHUA = "您应当在确保安全情况下用手机对现场拍照取证" 
									+ "后迅速撤离事故现场，将车辆移至附近不影响交通的地点，相互查验驾驶证、行驶证、保险凭证等，到理赔中心处理。\n"
									+ "适用范围：在XX市区内，发生仅造成财产损失（或者人员轻微伤），机动车能够自行移动且在本省投保交通事故责任强制保险（以下简称交强险）的道路交通事故。\n"
									+ "快处快撤功能步骤：\n"
									+ "第一步：上传至少两张全景照片；\n"
									+ "第二步：上传至少两张碰撞部位照片；\n"
									+ "第三步：上传当事人的驾驶证（身份证），行驶证，保险证；\n"
									+ "第四步：上传事故地理位置信息。\n"
									+ "请回复“y”开始事故快处快撤功能，回复“n”取消事故快处快撤功能。\n";
									//+ "<a href=\"http://jhwxtest.hzcdt.com/weixin/foreground/jsp/weixinAcciGuid.jsp\">点击查看不适用本功能情形</a>";

	// 用户退出事故快处快撤功能
	public static final String ACCI_QUIT = "您已退出事故快处快撤功能。";

	// 用户事故快处快撤功能输入错误提示
	public static final String ACCI_INPUT_ERROR = "请按照提示进行操作。";

	// 用户事故行政区输入错误提示
	public static final String ACCI_INPUT_XZQ_ERROR = "请输入正确的数字。\n";

	// 选择行政区域提示
	public static final String ACCI_NINGBO_XZQ = "请回复对应数字选择事故发生的行政区域：\n"
			+ "1.海曙区\n" + "" + "2.江东区\n" + "3.江北区\n" + "4.北仑区\n" + "5.镇海区\n"
			+ "6.鄞州区\n" + "7.象山县\n" + "8.宁海县\n" + "9.余姚市\n" + "10.慈溪市\n"
			+ "11.奉化市\n";

	// 行政区名数组
	public static final String[] ARRAY_XZQ = { "海曙区", "江东区", "江北区", "北仑区",
			"镇海区", "鄞州区", "象山县", "宁海县", "余姚市", "慈溪市", "奉化市" };

	// 用户输入手机号提示
	public static final String ACCI_NINGBO_PHONENUM = // "为确保安全，请放置警示标志，注意过往车辆。\n"+
	"请回复您的手机号码：";
	public static final String ACCI_NINGBO_PHONENUM_SECOND = "请回复另一位当事人的手机号码：";
	public static final String ACCI_NINGBO_PHONENUM_SAME = "手机号码相同,请重新输入另一位当事人的手机号码：";

	// 民警录入手机号提示
	public static final String ACCI_NINGBO_PHONENUM_TIP = "请录入各方当事人手机号码，每个当事人以逗号分隔：";

	// 民警录入民警警号或协警编号提示
	public static final String ACCI_NINGBO_POLICENUM = "请录入您的民警警号或协警编号：";

	// 用户输入错误手机号提示
	public static final String ACCI_NINGBO_PHONENUM_ERROR = "请回复正确的手机号码：";

	// 上传照片提示信息
	public static final String EVIDENCE_TIPS = "操作方法：点击“+”号按钮选择“图片-拍摄照片”。";

	// 事故信息收集完毕提示
	// public static final String ACCI_COMPLETE_TIPS =
	// "事故信息已上传，请立即撤除现场，将车移动至不妨碍交通的地方。" +
	// "事故编号等信息将以短信形式发送至各方当事人，请各方当事人收到短信后在24小时内自行约定到就近的理赔服务中心处理。";
	public static final String ACCI_COMPLETE_TIPS = "上传已成功，您的事故编号：SGBH，按以下步骤办理：\n"
			+ "1.请立即将车辆移至路边，避免造成交通堵塞;\n"
			+ "2.对事故事实有争议的，请立即报警;\n"
			+ "3.对事故事实无争议的，请互留联系方式并在24小时内到双方约定的理赔服务中心办理登记，并提供您所拍的事故照相。";

	// 理赔中心地址
	public static final String ACCI_COMPLETE_MAPS = "宁波市理赔服务中心地址如下：\n"
			+ "宁海 地址：宁海县跃龙路136-1号 联系电话：65348761\n"
			+ "象山 地址：象山县东陈乡汽车城里井路6号 联系电话：59121678\n"
			+ "北仑 地址：北仑区大港一路56号  联系电话：0574-86987781\n"
			+ "镇海 地址：镇海区俞范东路368号  联系电话：86660769\n"
			+ "海曙 地址：海曙区南苑街368号 联系电话：87452888\n"
			+ "江东 地址：江东区江东北路340号 联系电话：87722066\n"
			+ "江北 地址：江北环城北路东段548号 联系电话：87627666\n"
			+ "鄞州 地址：鄞州区下应北路600号 联系电话：55338996\n"
			+ "余姚 地址：余姚市兰江街道谭家岭西路688号 联系电话：62512988\n"
			+ "地址：余姚市梨洲街道东南经二路1号 联系电话：58224922\n"
			+ "奉化 地址：奉化市岳林街道奉白公路2号 联系电话：88522122\n";

	// 测试完毕提示
	public static final String ACCI_TEST_COMPLETE_TIPS = "测试结束。";

	// 证件照上传成功提示
	public static final String ACCI_NINGBO_IDENTITY = "照片上传成功，多个当事人请继续上传照片或回复“y”结束上传。";

	// 证据照上传回复错误提示
	public static final String ACCI_NINGBO_IDENTITY_ERROR = "多个当事人请继续上传照片或回复“y”结束上传。";

	// 上报地理位置信息
	public static final String ACCI_NINGBO_LOC = "请上传地理位置信息。\n请使用微信发送您的地理位置信息。"
			+ "操作方法：点击“+”号按钮选择“位置-发送位置”。";

	// 民警认定意见提示
	public static final String ACCI_NINGBO_SUGGEST_TIP = "请根据现场调查情况确定并上传事故认定意见：";

	// 民警事故信息上传完成提示
	public static final String ACCI_POLICE_COMPLETE_TIPS = "事故信息收集完毕。";

	// 事故编号前缀
	// public static final String INFORMATION_RE = "330200";
	public static final String INFORMATION_RE = "";

	public static final String USER_QUESTION = "您好，有什么可以帮到您的？";

	public static final String FEED_BACK_QUESTIONS = "您好，请以文字形式给我们留言：";

	public static final String USER_QUESTION_RESP = "您的留言已收到，我们会尽快答复您！";

	public static final String USER_QUESTION_RESP_ERROR = "服务器正忙,请稍后重试.";

	public static final String NOTICE_PUBLIC = "公告信息";

	public static final String PHOTO_END = "您上传的信息已经收到，谢谢您的配合！";

	public static final String TRAFFIC_QUERY = "请输入您要查询的道路名称，如：人民路";

	public static final String ROAD_CLEAN = "您查询的道路双向畅通。";

	public static final String RELATER_NUMBER1 = "请发送您的电话号码";

	public static final String BINDING_PHONE_VERIFY = "请回复收到的验证码以绑定手机号。";

	public static final String BINDING_PHONE_FAIL1 = "请发送正确的手机号码！";

	public static final String RELATER_NUMBER2 = "请发送您的身份证号码";

	public static final String BINDING_PHONE_SUCCESS = "谢谢您的支持，帐号已绑定成功！";

	public static final String BINDING_PHONE_FAIL2 = "您发送的证件号码不正确，请重新编辑发送";

	public static final String VEHICLE_MOVING = "自助移车：\n请输入堵车方车牌号\n如浙BF8808";
	public static final String VEHICLE_MOBING_FINISH_ANSWER = "信息已发送给移车方，请耐心等待";

	public static final String TEST_1 = "test1";

	public static final String TEST_2 = "test2";

	public static final String TEST_3 = "test3";

	public static final String ACTIVITY_RESP_FIRST = "感谢您参与我们的活动，请回复手机号码加上相关内容：";

	public static final String ACTIVITY_RESP_END = "您的信息已收到，请您继续关注“宁波交警”图文消息。";

	public static final String ACTIVITY_KEY = "活动";

	public static final String ACTIVITY_TOO_LONG = "您回复的内容过多，请重新输入";

	public static final String EVIDENCE_COMMITING_ATLEAST = "请至少上传两张证据照片。";
	public static final String EVIDENCE_LOCATION = "第四步：上传地理位置信息(共四步)\n请使用微信发送您的地理位置信息。操作方法：点击“+”号按钮选择“位置-发送位置”。\n您也可以直接回复文字信息上报您的地理位置。";
	//public static final String ACCI_END = "您的事故信息收集完毕，我们会尽快处理，请耐心等待并且请保持您的手机通讯畅通。如审核通过，将回复事故编号。\n<a href=\"http://220.191.230.215/accies/weixinAcciLipeiMap.jsp\">点击金华市交通事故保险定损理赔中心地图</a>";
	public static final String ACCI_END = "您的事故信息收集完毕，我们会尽快处理，请耐心等待并且请保持您的手机通讯畅通。如审核通过，将回复事故编号。";
	public static final String EVIDENCE_COMMITING_CONTINUE = "请继续上传照片或者回复“y”进入下一步。";
	public static NewsMessage getPicOverviewSample(String fromUserName,
			String toUserName) {
		List articleList = new ArrayList();
		Article article = new Article();
		article.setTitle("第一步：上传事故现场全景照片(共四步)\n请将车牌号码拍摄进去");
		article
				.setDescription("请按照上图拍摄角度拍摄。\n操作方法：点击“+”号按钮选择“图片-拍摄照片”。\n上传至少两张照片后，回复“y”进入下一步，如有多张照片请继续上传照片。");

		article
				.setPicUrl("http://jhwxtest.hzcdt.com/weixin/foreground/images/quanjing_v1.jpg");
		article
				.setUrl("http://jhwxtest.hzcdt.com/weixin/foreground/images/quanjing_v1.jpg");
		articleList.add(article);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

	public static NewsMessage getPicDetailSample(String fromUserName,
			String toUserName) {
		List articleList = new ArrayList();
		Article article = new Article();
		article.setTitle("第二步：上传现场损坏部位照片(共四步)\n多处损坏请上传多张照片");
		article
				.setDescription("请按照上图拍摄角度拍摄。\n操作方法：点击“+”号按钮选择“图片-拍摄照片”。\n上传至少两张照片后，回复“y”进入下一步，如有多张照片请继续上传照片。");

		article
				.setPicUrl("http://jhwxtest.hzcdt.com/weixin/foreground/images/sunhuai_v1.jpg");
		article
				.setUrl("http://jhwxtest.hzcdt.com/weixin/foreground/images/sunhuai_v1.jpg");

		articleList.add(article);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

	public static NewsMessage getCardSample(String fromUserName,
			String toUserName) {
		List articleList = new ArrayList();
		Article article = new Article();
		article.setTitle("第三步：上传当事人证件照片(共四步)\n每个当事人证件请放在一起拍摄");
		article
				.setDescription("请将当事人的驾驶证（身份证）、行驶证、保险证放在一起按照上图拍摄角度拍摄。\n注意：当事人双方请核查相互的证件。\n操作方法：点击“+”号按钮选择“图片-拍摄照片”。\n上传至少两张照片后，回复“y”进入下一步，如有多个当事人请继续上传照片。");

		article
				.setPicUrl("http://jhwxtest.hzcdt.com/weixin/foreground/images/zhengjian_v1.jpg");
		article
				.setUrl("http://jhwxtest.hzcdt.com/weixin/foreground/images/zhengjian_v1.jpg");

		articleList.add(article);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

	// 事故现场前方5米前景照提示
	public static NewsMessage getForegroundTips(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("请上传事故现场前方5米前景照");
		article1.setDescription("请按照上图拍摄角度拍摄。\n" + EVIDENCE_TIPS);
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/qianjing.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/images/qianjing.jpg");

		articleList.add(article1);

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	// 事故现场后方5米后景照提示
	public static NewsMessage getBackgroundTips(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("前景照上传成功\n" + "请上传事故现场后方5米后景照");
		article1.setDescription("请按照上图拍摄角度拍摄。\n" + EVIDENCE_TIPS);
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/houjing.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/images/houjing.jpg");

		articleList.add(article1);

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	// 车辆接触损坏部位照片提示
	public static NewsMessage getBrokenTips(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("后景照上传成功\n" + "请上传事故现场车辆接触损坏部位照片");
		article1.setDescription("请按照上图拍摄角度拍摄。\n" + EVIDENCE_TIPS);
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/sunhuai.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/images/sunhuai.jpg");

		articleList.add(article1);

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	// 证据保险单照提示
	public static NewsMessage getIdentityTips(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("车辆损坏照上传成功\n" + "请上传当事人证件和保险证照片");
		article1.setDescription("请参照上图将当事人的驾驶证（身份证）、行驶证、保险证放在一起按照上图拍摄角度拍摄。\n"
				+ EVIDENCE_TIPS + "\n如有多个当事人请连续上传照片，回复“y”结束照片上传。");
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/zhengjian.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/images/zhengjian.jpg");

		articleList.add(article1);

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	// 证据照提示
	public static NewsMessage getUserIdentityTips(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("车辆损坏照上传成功\n" + "请上传当事人证件和保险证照片");
		article1.setDescription("请参照上图将当事人的驾驶证（身份证）、行驶证放在一起按照上图拍摄角度拍摄。\n"
				+ EVIDENCE_TIPS + "\n如有多个当事人请连续上传照片，回复“y”结束照片上传。");
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/zhengjian.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/images/zhengjian.jpg");

		articleList.add(article1);

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	// 新建一个在线学习图文消息
	public static NewsMessage getLearnOnLine(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articles = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("科目一习题\n");
		article1.setDescription("科目一:驾驶理论");
		article1.setPicUrl("http://61.153.216.84/images/CarInfoQuery.jpg");
		article1.setUrl("http://service.zscg.hzcdt.com/app_banner/2.htm");

		Article article2 = new Article();
		article2.setTitle("\n科目三习题\n");
		article2.setDescription("科目三:安全文明驾驶常识");
		article2.setUrl("http://service.zscg.hzcdt.com/app_banner/2.htm");
		articles.add(article1);
		articles.add(article2);

		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		return newsMessage;
	}

	// 预约服务
	public static NewsMessage getPromiseService(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articles = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("车辆年检预约");
		article1.setDescription("车主可以通过点击年检预约进入到预约窗口，按要求提交信息，预约成功后可减免排队哦！");
		article1.setPicUrl("http://61.153.216.84/images/CarInfoQuery.jpg");
		article1.setUrl("http://61.153.216.84/CarYearCheckBooking.html");
		Article article2 = new Article();
		article2.setTitle("\n驾照考试预约\n");
		article2.setDescription("学员通过微信平台可以预约所有的考试项目...,再也不用老是跑驾校了...");
		article2.setUrl("http://61.153.216.84/DriverCardExamBooking.html");
		articles.add(article1);
		articles.add(article2);

		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		return newsMessage;
	}

	// 业务导办
	public static NewsMessage getGuider(String fromUserName, String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);
		List<Article> articles = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("机动车业务导办\n");
		article1
				.setDescription("实现通过移动终端对机动车业务进行步骤演示：如注册登记、变更登记、抵押登记、注销登记、转入转出业务等");
		article1.setPicUrl("http://61.153.216.84/images/CarInfoQuery.jpg");
		article1.setUrl("http://61.153.216.84/CarJobGuid.html");
		Article article2 = new Article();
		article2.setTitle("\n驾驶证业务导办\n");
		article2.setDescription("实现通过移动终端对驾照业务进行导办，如初次申领、增驾申请、军警换证等等!");
		article2.setUrl("http://61.153.216.84/CarJobGuid.jsp");

		Article article3 = new Article();
		article3.setTitle("\n违法处理业务导办\n");
		article3.setDescription("实现通过移动终端对违法处理业务进行导办。");
		article3.setUrl("http://61.153.216.84/CarJobGuid.jsp");
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		return newsMessage;
	}

	public static NewsMessage getLawFiles(String fromUserName, String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("法律、法规？我知道！");
		article1.setDescription("机动车相关法律、法规、政策、规范信息\n欢迎点读");
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/lawsAndRegulations.html");

		articleList.add(article1);

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	public static NewsMessage getNotification(String fromUserName,
			String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);

		List<Article> articles = new ArrayList<Article>();

		Article article = new Article();
		article.setTitle("公告信息-点这里");
		article.setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		article.setUrl("http://nbwxtest.hzcdt.com/publicNotification.html");

		articles.add(article);

		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);

		return newsMessage;
	}

	// 车驾信息查询图文消息
	public static NewsMessage getCarInfoQuery(String fromUserName,
			String toUserName) {
		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("机动车查询业务");
		article1.setDescription("");
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/carbaseoperation.html");

		Article article2 = new Article();
		article2.setTitle("\n驾驶人信息查询\n");
		article2.setDescription("");
		article2.setPicUrl("");
		article2.setUrl("http://nbwxtest.hzcdt.com/DriverQuery.html");

		// Article article3 = new Article();
		// article3.setTitle("\n业务进度查询\n");
		// article3.setDescription("");
		// article3.setPicUrl("");
		// article3
		// .setUrl("http://nbwxtest.hzcdt.com/JobProcessQuery.jsp");
		//
		// Article article4 = new Article();
		// article4.setTitle("\n可选车号查询\n");
		// article4.setDescription("");
		// article4.setPicUrl("");
		// article4
		// .setUrl("http://nbwxtest.hzcdt.com/CarNumAvailQuery.jsp");
		//
		// Article article5 = new Article();
		// article5.setTitle("\n驾校通报查询\n");
		// article5.setDescription("");
		// article5.setPicUrl("");
		// article5
		// .setUrl("http://nbwxtest.hzcdt.com/DriverSchoolReport.jsp");

		Article article6 = new Article();
		article6.setTitle("\n驾校查询\n");
		article6.setDescription("");
		article6.setPicUrl("");
		article6.setUrl("http://nbwxtest.hzcdt.com/driverschoolinfo.html");

		articleList.add(article1);
		articleList.add(article2);
		// articleList.add(article3);
		// articleList.add(article4);
		// articleList.add(article5);
		articleList.add(article6);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

	// 违法信息查询图文消息
	public static NewsMessage getIllegalQuery(String fromUserName,
			String toUserName) {
		List<Article> articleList = new ArrayList<Article>();
		// Article article1 = new Article();
		// article1.setTitle("违法代码查询");
		// article1.setDescription("");
		// article1
		// .setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		// article1.setUrl("http://nbwxtest.hzcdt.com/wfdmcx.jsp");

		Article article2 = new Article();
		article2.setTitle("\n机动车违法查询\n");
		article2.setDescription("");
		article2.setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		article2.setUrl("http://nbwxtest.hzcdt.com/caroutlawquery.html");

		Article article3 = new Article();
		article3.setTitle("\n驾驶人违法查询\n");
		article3.setDescription("");
		article3.setPicUrl("");
		article3.setUrl("http://nbwxtest.hzcdt.com/driveroutlawquery.html");

		// articleList.add(article1);
		articleList.add(article2);
		articleList.add(article3);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

	// 便民业务受理
	public static NewsMessage getConvPeople(String fromUserName,
			String toUserName) {
		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("机动车业务办理");
		article1.setDescription("");
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/CarJobHandle.html");

		Article article2 = new Article();
		article2.setTitle("\n驾驶证业务办理\n");
		article2.setDescription("");
		article2.setPicUrl("");
		article2.setUrl("http://nbwxtest.hzcdt.com/DriverCardJobHandle.html");

		articleList.add(article1);
		articleList.add(article2);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

	// 自助业务受理
	public static NewsMessage getBySelf(String fromUserName, String toUserName) {
		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("违法自助处理");
		article1.setDescription("");
		article1.setPicUrl("http://nbwxtest.hzcdt.com/images/CarInfoQuery.jpg");
		article1.setUrl("http://nbwxtest.hzcdt.com/wfzzcl.html");

		Article article2 = new Article();
		article2.setTitle("\n自助移车\n");
		article2.setDescription("");
		article2.setPicUrl("");

		articleList.add(article1);
		articleList.add(article2);

		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		return newsMessage;
	}

}