package cn.trustway.weixin.js;

import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.message.pushmsg.BasePushMsg;
import cn.trustway.weixin.message.pushmsg.Text;
import cn.trustway.weixin.message.pushmsg.TextPushMsg;
import cn.trustway.weixin.servlet.TokenThread;
import cn.trustway.weixin.util.PushMsgUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import oracle.sql.BLOB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

/**
 * 
 * @Title:WebServiceUtil
 * @Description: WebService接口调用
 * @Company: Hangzhou Trustway Technology Co. Ltd
 * @author yexin
 * @version v1.0.1
 * @date 2015-12-7 上午11:21:04
 * 
 * @author xiaoyan
 * @version v1.0.2
 * @date 2015-12-7 上午11:21:04
 */
@SuppressWarnings("restriction")
public class WebServiceUtil {
	/** 自主选号接口序列号 */
	private static final String SELECT_CARNUMBER_XLH = "3385529929C28424485DDF6049E9218D";

	/** 自主选号接口地址 */
	private static final String SELECT_CARNUMBER_URL = "http://220.191.243.245:6081/outService/services/CdtOutAccess?wsdl";

	/** 日志对象 */
	private static Logger log = LoggerFactory.getLogger(WebServiceUtil.class);

	/** 异常处理返回值 */
	private static final String EXCEPT_STRING = "{\"head\":{\"message\":\"服务器正忙,请稍后重试\",\"code\":\"-1\"}}";

	/** 微信服务URL */
	public static final String SERVICE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**  */
	public static TrffAppService instance = null;

	public static synchronized TrffAppService getInstance() {
		if (instance == null) {
			instance = new TrffAppService();
		}
		return instance;
	}

	/**
	 * 
	 * @Description: outService查询数据
	 * @param jkid
	 *            接口号
	 * @param params
	 *            接口参数，json格式
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 * @return String 查询结果
	 * @author yexin
	 * @date 2014-12-7
	 * 
	 * @author xiaoyan
	 * @version v1.0.2
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public static String queryOutService(String jkid, String params) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		String result = null;
		Map resultMap = getInstance().queryOutService(
				AppInitConstants.outServiceUrl, AppInitConstants.outServiceXlh,
				jkid, paramMap);
		try {
			result = JSONSerializer.toJSON(resultMap).toString();
		} catch (Exception e) {
			((Map) ((List) resultMap.get("body")).get(0)).remove("null");
			result = JSONSerializer.toJSON(resultMap).toString();
		}

		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				try {
					result = URLDecoder.decode(result.replaceAll("%", "%25"),
							"UTF-8");
				} catch (UnsupportedEncodingException uee) {
					log.error(uee.getMessage(), uee);
				}
			}
		}
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	/**
	 * 
	 * @Description: 可选车号查询
	 * @param params
	 *            json格式的接口参数
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 * @return String 查询结果
	 * @author yexin
	 * @date 2015-12-7
	 * 
	 * @author xiaoyan
	 * @version v1.0.2
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public static String queryOutServiceCarNumber(String params)
			throws UnsupportedEncodingException {
		String jkid = "A000005";
		String ip = "118.250.188.80";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		paramMap.put("ip", ip);
		String result = null;
		Map resultMap = getInstance().queryOutService(SELECT_CARNUMBER_URL,
				SELECT_CARNUMBER_XLH, jkid, paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				result = URLDecoder.decode(result.replaceAll("%", "%25"),
						"UTF-8");
			}
		}
		// 异常数据处理
		outServiceException(result);
		return result;
	}

	/**
	 * 
	 * @Description: 法律法规通告信息
	 * @param params
	 *            文章列表编号，json格式
	 * @return String 返回通告文章列表
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getLawsAndRegulationsInfo(String params) {
		String jkid = "A001664";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 驾驶人违法查询
	 * @param params
	 *            json格式：驾驶证号和档案编号
	 * @return String 驾驶证违法信息列表
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String driverLostLaw(String params) {
		String jkid = "A001676";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 模拟考试题库查询接口
	 * @param params
	 *            json格式：试题类型question_type
	 * @return String 题库中的试题或异常提示信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String simulation(String params) {
		String jkid = "A002941";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 驾驶证累积记分查询
	 * @param params
	 *            json格式数据
	 * @return String 返回驾驶证当前记分和
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String queryMark(String params) {
		String jkid = "A002938";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 游客模式查询机动车基本信息
	 * @param params
	 *            json格式数据
	 * @return String 机动车基本信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getVechicleInfo(String params) {
		String jkid = "A001716";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 业务退办
	 * @param params
	 *            json格式：token，业务累心，业务序号等
	 * @return String 写入信息是否成功
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String backTodoOper(String params) {
		String jkid = "B001121";
		return writeOutService(jkid, params);
	}

	/**
	 * @Description: 业务办理点详细信息
	 * @param params
	 *            json格式数据
	 * @return String 业务网点信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String webPointDealMap(String params) {
		String jkid = "A001697";
		return queryOutService(jkid, params);
	}

	/**
	 * @Description: 业务流程
	 * @param params
	 *            json格式数据
	 * @return String 返回流程信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String businessProcesses(String params) {
		String jkid = "A001698";
		return queryOutService(jkid, params);
	}

	/**
	 * @Description: 查询驾校信息
	 * @param params
	 *            json格式数据
	 * @return String 对应区域的所有驾校信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String findSchoolInfo(String params) {
		String jkid = "A001719";
		return queryOutService(jkid, params);
	}

	/**
	 * @Description: 获取驾校信息列表
	 * @param params
	 *            json格式：null
	 * @return String 驾校列表
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String findSchoolList(String params) {
		String jkid = "A001718";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 游客机动车违法查询
	 * @param params
	 *            json格式：号牌种类，号牌号码，车架号
	 * @return String 违法信息列表
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String carLostLaw(String params) {
		String jkid = "A001674";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 机动车信息查询
	 * @param params
	 *            json格式：号牌种类，号牌号码，车架号
	 * @return String 机动车基本信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String selectCar(String params) {
		String jkid = "A001673";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 绑定机动车信息查询
	 * @param params
	 *            json格式：token，身份证明名称，身份证明号码
	 * @return String 绑定机动车信息查询
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String selectBindedCar(String params) {
		String jkid = "A001671";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 驾驶人信息查询
	 * @param params
	 *            json格式：档案编号，身份证明号码
	 * @return String 驾驶人信息
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String selectDiver(String params) {
		String jkid = "A001675";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 获取驾校通报信息详情
	 * @param jxxh
	 *            驾校序号
	 * @return String 驾校通报详情
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	@SuppressWarnings( { "unchecked" })
	public static String selectDSchool(String jxxh) {
		String jkid = "A001720";
		String params = "{\"jxxh\":\"" + jxxh + "\"}";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		String result = null;
		Map resultMap = getInstance().queryOutService(
				AppInitConstants.outServiceUrl, AppInitConstants.outServiceXlh,
				jkid, paramMap);

		String intro;
		/*try {
			intro = new String(Base64.decode(((Map) ((List) resultMap
					.get("body")).get(0)).get("intro").toString()), "GB2312");
			((Map) ((List) resultMap.get("body")).get(0)).put("intro", intro);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}*/
		result = JSONSerializer.toJSON(resultMap).toString();
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	@SuppressWarnings( { "unchecked" })
	public static String queryOutServiceBase64(String jkid, String params) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		String result = null;
		Map resultMap = getInstance().queryOutService(
				AppInitConstants.outServiceUrl, AppInitConstants.outServiceXlh,
				jkid, paramMap);

		/*try {
			String content;
			try {
				content = new String(Base64.decode(((Map) ((List) resultMap
						.get("body")).get(0)).get("content").toString()),
						"GB2312");
				((Map) ((List) resultMap.get("body")).get(0)).put("content",
						content);
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
			}

		} catch (Base64DecodingException e) {
			log.error(e.getMessage(), e);
		}*/
		result = JSONSerializer.toJSON(resultMap).toString();
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	/**
	 * 
	 * @Description: 调用写入类型outService接口
	 * @param jkid
	 * @param params
	 * @return
	 * @return String
	 * @throws
	 * @author yexin
	 * @date 2016-6-29
	 */
	@SuppressWarnings("unchecked")
	public static String writeOutService(String jkid, String params) {
		String result = null;
		JSONObject paramObject = JSONObject.fromObject(params);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Iterator iterator = paramObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = paramObject.getString(key);
			paramMap.put(key, value);
		}
		Map resultMap = getInstance().writeOutService(
				AppInitConstants.outServiceUrl, AppInitConstants.outServiceXlh,
				jkid, "WriteCondition", paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	private static boolean isUtf8(String str) {
		boolean isUtf8 = false;
		String encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				isUtf8 = true;
			}
		} catch (Exception exception2) {

		}
		return isUtf8;
	}

	/**
	 * 
	 * @Description: 获取掌上车管业务类型
	 * @param params
	 *            null
	 * @return String 所有业务类型
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getJobGuideTypeName(String params) {
		String jkid = "A001703";
		return queryOutService(jkid, params);
	}

	public static String getWorkdays(String params) {
		String jkid = "A002915";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 写入补领合格标志信息
	 * @param params
	 *            json格式
	 * @return String 写入信息是否成功
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getMarkOfConformity(String params) {
		String jkid = "B001091";
		return writeOutService(jkid, params);
	}

	/**
	 * @Description: 确认选号
	 * @param params
	 *            json格式
	 * @return String 写入信息是否成功
	 * @author yexin
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public static String selectNum(String params)
			throws UnsupportedEncodingException {
		String jkid = "B000001";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		String result = null;
		Map resultMap = getInstance().writeOutService(SELECT_CARNUMBER_URL,
				SELECT_CARNUMBER_XLH, jkid, "WriteCondition", paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				result = URLDecoder.decode(result.replaceAll("%", "%25"),
						"UTF-8");
			}
		}
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	/**
	 * 
	 * @Description: 选号成功发送微信消息
	 * @param openId
	 *            用户ID tips
	 * 
	 * @return String
	 * 
	 * @author yexin
	 * @date 2015-12-7
	 */
	public String sendSelectSuccuss(String openId, String tips) {
		String result = "";
		pushTextMsg(openId, tips);
		return result;
	}

	/**
	 * 选号成功记录微信号、所选号牌及选号日期
	 * 
	 * @param wxid
	 *            hphm 号牌号码 xhsj 选号时间
	 * 
	 * @return String
	 * @author yexin
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public String saveSelectSuccussRec(String wxid, String hphm, String xhsj) {
		String result = "";
		/*try {
			ApplicationContext ctx = AppApplicationContextUtil.getContext();
			WxAppSelectRecService wxAppSelectRecService = (WxAppSelectRecService) ctx
					.getBean("wxAppSelectRecService");
			// 插入新的选号记录
			WxAppSelectRec wxAppSelectRec = new WxAppSelectRec();
			wxAppSelectRec.setWxid(wxid);
			wxAppSelectRec.setHphm(hphm);
			wxAppSelectRec.setXhsj(new Date());
			wxAppSelectRecService.add(wxAppSelectRec);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}*/
		return result;
	}

	/**
	 * 判断微信号一个月内是否有选号
	 * 
	 * @param wxid
	 *            微信ID
	 * 
	 * @return boolean
	 * 
	 * @author yexin
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public boolean isSelectedInMonth(String wxid) {
		boolean result = false;
		/*try {
			ApplicationContext ctx = AppApplicationContextUtil.getContext();
			WxAppSelectRecService wxAppSelectRecService = (WxAppSelectRecService) ctx
					.getBean("wxAppSelectRecService");
			WxAppSelectRec wxAppSelectRec = (WxAppSelectRec) wxAppSelectRecService
					.queryLastRecByWxid(wxid);
			if (null == wxAppSelectRec || "".equals(wxAppSelectRec)) { // 该微信号没有选号记录
				result = false;
			} else {
				// 选号日期
				java.util.Date xhsj = wxAppSelectRec.getXhsj();
				// 当前日期
				java.util.Date today = new java.util.Date();
				long quot = today.getTime() - xhsj.getTime();
				long days = quot / 1000 / 60 / 60 / 24 % 365;
				result = days <= 30 && days >= 0;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}*/

		return result;
	}

	/**
	 * 
	 * @Description: 向指定微信用户推送客服消息
	 * @param wxid
	 * @param tsnr
	 * @return void
	 * @throws
	 * @author yexin
	 * @date 2016-6-29
	 */
	private void pushTextMsg(String wxid, String tsnr) {
		String url = SERVICE_URL.replace("ACCESS_TOKEN",
				TokenThread.accessToken.getToken());
		TextPushMsg textPushMsg = new TextPushMsg();
		textPushMsg.setTouser(wxid);
		textPushMsg.setMsgtype(PushMsgUtil.PUSH_MESSAGE_TYPE_TEXT);
		textPushMsg.setText(new Text(tsnr));

		sendRequest(textPushMsg, url);
	}

	/**
	 * 获取可选号牌区间
	 * 
	 * @Description: 获取可选号牌区间
	 * @param params
	 * @return String
	 * @author yexin
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public static String getNumArea(String params) {
		String jkid = "A000004";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		String result = null;
		Map resultMap = getInstance().queryOutService(SELECT_CARNUMBER_URL,
				SELECT_CARNUMBER_XLH, jkid, paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				try {
					result = URLDecoder.decode(result.replaceAll("%", "%25"),
							"UTF-8");
				} catch (UnsupportedEncodingException uee) {
					log.error(uee.getMessage(), uee);
				}
			}
		}
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	/**
	 * 获取手机号一个月内是否有选号
	 * 
	 * @param params
	 * @return
	 * @author yexin
	 * @date 2015-12-7
	 */
	@SuppressWarnings("unchecked")
	public static String getNumChoosed(String params) {
		String jkid = "A000010";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}
		String result = null;
		Map resultMap = getInstance().queryOutService(SELECT_CARNUMBER_URL,
				SELECT_CARNUMBER_XLH, jkid, paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				try {
					result = URLDecoder.decode(result.replaceAll("%", "%25"),
							"UTF-8");
				} catch (UnsupportedEncodingException uee) {
					log.error(uee.getMessage(), uee);
				}
			}
		}
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	private void sendRequest(BasePushMsg pushMsg, String url) {
		try {
			URL messageUrl = new URL(url);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) messageUrl
					.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("POST");
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(PushMsgUtil.pushMsgToJson(pushMsg).getBytes(
					"UTF-8"));
			outputStream.close();

			// 将返回的输入流转换成字符串
			StringBuffer buffer = new StringBuffer();
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			log.error(ce.getMessage(), ce);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @Description: 获取掌上车管所有业务导办信息
	 * @param params
	 * @return String
	 * 
	 * @author yexin
	 * @date 2015-12-7
	 */
	public static String getJobGuideAddrName(String params) {
		String jkid = "A001696";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 业务导办详情
	 * @param params
	 *            json格式字符串
	 * @return String 业务的详细办理步骤
	 * @author yexin
	 * @date 2015-12-7
	 */
	public static String getJobGuideDisplay(String params) {
		String jkid = "A001702";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 用户机动车信息绑定
	 * @param params
	 *            json格式字符串
	 * @return String 返回写入是否成功信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String bindUserVehicle(String params) {
		String jkid = "A002906";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 用户驾驶证信息绑定
	 * @param params
	 *            json格式字符串
	 * @return String 返回写入是否成功信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String bindUserDrivers(String params) {
		String jkid = "A002907";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 用户学员信息绑定
	 * @param params
	 *            json格式字符串
	 * @return String 返回写入是否成功信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String bindUserStudent(String params) {
		String jkid = "A002910";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 用于用户驾驶证信息解绑
	 * @param params
	 *            json格式字符串
	 * @return String 返回写入是否成功信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String unBindUserDriver(String params) {
		String jkid = "A002908";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 用户机动车信息解绑
	 * @param params
	 *            json格式字符串
	 * @return String 返回写入是否成功信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String unBindUserVehicle(String params) {
		String jkid = "A002909";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 获取帐号绑定驾驶证信息
	 * @param params
	 *            json格式字符串
	 * @return String 返回驾驶证详细信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getBindedDriver(String params) {

		String jkid = "A001669";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 获取帐号绑定信息
	 * @param params
	 *            json格式字符串
	 * @return String 返回驾驶证详细信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getBindedInfo(String params) {
		String jkid = "A002911";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 获取检测站信息
	 * @param params
	 *            json格式字符串
	 * @return String 返回检测站信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getCheckSpaceMsg(String params) {
		String jkid = "A001694";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 获取检测站预约时间
	 * @param params
	 *            json格式字符串
	 * @return String 返回检测站预约时间列表
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getBookingPlan(String params) {
		String jkid = "A001695";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 写入车辆预约检验信息
	 * @param params
	 *            json格式字符串
	 * @return String 返回写入信息是否成功
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String writeBookingMsg(String params) {
		String jkid = "B001098";
		return writeOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 关注接口
	 * @param params
	 *            json格式字符串
	 * @return String 返回关注用户信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String subscribe(String params) {
		String jkid = "A002904";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 取消关注接口
	 * @param params
	 *            json格式字符串
	 * @return String 返回关注用户信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String unsubscribe(String params) {
		String jkid = "A002905";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 获取学员信息
	 * @param params
	 *            json格式字符串
	 * @return String 返回考试学员信息
	 * 
	 * @author xiaoyan
	 * @date 2015-12-7
	 */
	public static String getStudentTestMsg(String params) {
		String jkid = "A001706";
		return queryOutService(jkid, params);
	}

	/**
	 * 获取考试计划
	 * 
	 * @param params
	 * @return
	 */
	public static String getStudentTestPlan(String params) {
		String jkid = "A001707";
		return queryOutService(jkid, params);
	}

	/**
	 * 写入学员预约考试信息
	 * 
	 * @param params
	 * @return
	 */
	public static String writeTestBookingMsg(String params) {
		String jkid = "B001100";
		return writeOutService(jkid, params);
	}

	/**
	 * 补领换驾驶证
	 * 
	 * @param params
	 * @return
	 */
	public static String writeDriverCardChange(String params) {
		String jkid = "B001088";
		return writeOutService(jkid, params);
	}

	/**
	 * 驾驶人联系方式变更
	 * 
	 * @param params
	 * @return
	 */
	public static String writeContactInfoChange(String params) {
		String jkid = "B001087";
		return writeOutService(jkid, params);
	}

	/**
	 * 绑定用户机动车违法查询
	 * 
	 * @param params
	 * @return
	 */
	public static String getIllegalQuery(String params) {
		String jkid = "A001672";
		return queryOutService(jkid, params);
	}

	public static String getQuerstion(String params) {
		String jkid = "A002940";
		return queryOutService(jkid, params);
	}

	/**
	 * 获取驾校通报详细信息
	 * 
	 * @param params
	 * @return
	 */
	public static String getSchoolNoteArt(String params) {
		String jkid = "A001685";
		return queryOutServiceBase64(jkid, params);
	}

	/**
	 * 驾校通报列表
	 * 
	 * @param params
	 * @return
	 */
	public static String getListOfNotification(String params) {
		String jkid = "A001721";
		return queryOutService(jkid, params);
	}

	/**
	 * 移车信息写入
	 * 
	 * @param params
	 * @return
	 */
	public static String writeMoveCarMsg(String params) {
		String jkid = "B001116";
		return writeOutService(jkid, params);
	}

	/**
	 * get openid
	 * 
	 */
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 隐藏appid 和 secret
	 * 
	 * @param code
	 * @return
	 */
	public static String madeUrl(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ TokenThread.appid + "&secret=" + TokenThread.appsecret
				+ "&code=%s&grant_type=authorization_code";
		return sendGet(String.format(url, code));
	}

	/**
	 * 业务进度查询
	 * 
	 * @param params
	 */
	public static String operationList(String params) {
		String jkid = "A001693";
		return WebServiceUtil.queryOutService(jkid, params);
	}

	/**
	 * 获取验证码
	 * 
	 * @param params
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String getSelfYzm(String params) {
		String jkid = "B000002";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}

		String result = null;
		Map resultMap = getInstance().writeOutService(SELECT_CARNUMBER_URL,
				SELECT_CARNUMBER_XLH, jkid, "WriteCondition", paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				try {
					result = URLDecoder.decode(result.replaceAll("%", "%25"),
							"UTF-8");
				} catch (UnsupportedEncodingException uee) {
					log.error(uee.getMessage(), uee);
				}
			}
		}
		// 异常数据处理
		outServiceException(result);
		return result;
	}

	/**
	 * 验证码验证
	 * 
	 * @param params
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String identifySelfYzm(String params) {
		String jkid = "B000003";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (params != null && !params.equals("")) {
			JSONObject paramObject = JSONObject.fromObject(params);
			Iterator iterator = paramObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = paramObject.getString(key);
				paramMap.put(key, value);
			}
		}

		String result = null;
		Map resultMap = getInstance().writeOutService(SELECT_CARNUMBER_URL,
				SELECT_CARNUMBER_XLH, jkid, "WriteCondition", paramMap);

		result = JSONSerializer.toJSON(resultMap).toString();
		if (isUtf8(result)) {
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				try {
					result = URLDecoder.decode(result.replaceAll("%", "%25"),
							"UTF-8");
				} catch (UnsupportedEncodingException uee) {
					log.error(uee.getMessage(), uee);
				}
			}
		}
		// 处理异常结果
		outServiceException(result);
		return result;
	}

	/**
	 * ZSCG_获取验证码
	 * 
	 * @param params
	 */
	public static String getZscgCode(String params) {
		String jkid = "A002932";
		return queryOutService(jkid, params);
	}

	/**
	 * ZSCG_验证验证码
	 * 
	 * @param params
	 */
	public static String checkZscgCode(String params) {
		String jkid = "A002933";
		return queryOutService(jkid, params);
	}

	/**
	 * 机动车联系方式变更
	 * 
	 * @param params
	 */
	public static String changeContactInfo(String params) {
		String jkid = "B001089";
		return writeOutService(jkid, params);
	}

	/**
	 * 公共通行证办理登录
	 * 
	 * @param params
	 */
	public static String publicPassListLogin(String params) {
		String jkid = "A002926";
		return queryOutService(jkid, params);
	}

	/**
	 * 获取部门通行证列表
	 * 
	 * @param params
	 */
	public static String getPublicPassList(String params) {
		String jkid = "A002927";
		return queryOutService(jkid, params);
	}

	/**
	 * 获取通行证详细信息
	 * 
	 * @param params
	 */
	public static String getPassDetail(String params) {
		String jkid = "A002928";
		return queryOutService(jkid, params);
	}

	/**
	 * 保存通行证信息
	 * 
	 * @param params
	 */
	public static String savaPassMessage(String params) {
		String jkid = "A002929";
		return queryOutService(jkid, params);
	}

	/**
	 * 删除通行证信息
	 * 
	 * @param params
	 */
	public static String deletePassMessage(String params) {
		String jkid = "B001119";
		return writeOutService(jkid, params);
	}

	/**
	 * 违法预处理
	 * 
	 * @param params
	 */
	public static String illegalPretreat(String params) {
		String jkid = "A002933";
		return queryOutService(jkid, params);
	}

	/**
	 * 补换领号牌
	 * 
	 * @param params
	 */
	public static String exchangeFlapper(String params) {
		String jkid = "B001090";
		return writeOutService(jkid, params);
	}

	/**
	 * 异地委托年检
	 * 
	 * @param params
	 */
	public static String remoteEntrust(String params) {
		String jkid = "B001091";
		return writeOutService(jkid, params);
	}

	/**
	 * 获取发证机关
	 * 
	 * @param params
	 */
	public static String getCertificationList(String params) {
		String jkid = "A001692";
		return queryOutService(jkid, params);
	}

	/**
	 * 消息推送
	 * 
	 * @param params
	 */
	public static String getMessageList(String params) {
		String jkid = "A001687";
		return queryOutService(jkid, params);
	}

	/**
	 * 用户反馈
	 * 
	 * @param params
	 */
	public static String userFeedback(String params) {
		String jkid = "A001666";
		return queryOutService(jkid, params);
	}

	/**
	 * 获取已解决反馈
	 * 
	 * @param params
	 */
	public static String getFeedback(String params) {
		String jkid = "A002935";
		return queryOutService(jkid, params);
	}

	/**
	 * 更新已解决反馈
	 * 
	 * @param params
	 */
	public static String updateFeedback(String params) {
		String jkid = "A002936";
		return queryOutService(jkid, params);
	}

	/**
	 * 查询已处理未交款的违法记录
	 * 
	 * @param params
	 */
	public static String getUnpayedIllegal(String params) {
		String jkid = "A002939";
		return queryOutService(jkid, params);
	}

	/**
	 * 交通设施保修
	 * 
	 * @param params
	 */
	public static String deviceReport(String params) {
		String jkid = "A002937";
		return queryOutService(jkid, params);
	}

	/**
	 * 交通设施保保存照片
	 * 
	 * @param params
	 */
	public static String deviceReportPic(String params) {
		String jkid = "B001096";
		return writeOutService(jkid, params);
	}

	protected final static String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	public String jobDownloadPic(String xh, String pic, String kind)
			throws SQLException {
		String result = "";
		String[] serverIds = pic.split("&");
		String picBase64 = "";
		for (int i = 0; i < serverIds.length; i++) {
			String requestUrl = DOWNLOAD_MEDIA_URL.replace("ACCESS_TOKEN",
					TokenThread.accessToken.getToken()).replace("MEDIA_ID",
					serverIds[i]);
			try {
				URL url = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.setRequestMethod("GET");
				BufferedInputStream bis = new BufferedInputStream(conn
						.getInputStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				byte[] temp = new byte[1024];
				int size = 0;
				while ((size = bis.read(temp)) != -1) {
					out.write(temp, 0, size);
				}
				bis.close();
				byte[] content = out.toByteArray();
				String contentBase64 = com.sun.org.apache.xerces.internal.impl.dv.util.Base64
						.encode(content);
				if (i == (serverIds.length - 1)) {
					picBase64 = picBase64 + contentBase64;
				} else {
					picBase64 = picBase64 + contentBase64 + "&";
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}
		jobSavePic(xh, picBase64, kind);
		return result;
	}

	@SuppressWarnings("deprecation")
	public String jobDownloadPicByte(String xh, String pic, String kind)
			throws SQLException {
		String serverId = pic;
		String requestUrl = DOWNLOAD_MEDIA_URL.replace("ACCESS_TOKEN",
				TokenThread.accessToken.getToken()).replace("MEDIA_ID",
				serverId);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			BufferedInputStream bis = new BufferedInputStream(conn
					.getInputStream());
			ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = bis.read(temp)) != -1) {
				bOut.write(temp, 0, size);
			}
			// 图片二进制数据
			byte[] content = bOut.toByteArray();
			bis.close();
			String path = WebServiceUtil.class.getResource("/").getPath();
			Connection connection = null;
			PreparedStatement pstmt = null;

			try {
				String filePath = URLDecoder.decode(path + "zscgdb.properties",
						"UTF-8");
				FileInputStream is = new FileInputStream(filePath);
				Properties prop = new Properties();
				prop.load(is);
				String dbUrl = prop.getProperty("url");
				String dbName = prop.getProperty("zscg_username");
				String dbpwd = prop.getProperty("zscg_password");
				oracle.jdbc.driver.OracleDriver driver = new oracle.jdbc.driver.OracleDriver();
				DriverManager.registerDriver(driver);
				connection = DriverManager.getConnection(dbUrl, dbName, dbpwd);
			} catch (Exception e) {
				e.printStackTrace();
				return "false";
			}
			connection.setAutoCommit(false);

			String querysql = "select " + "xh from APP_PICTURE " + "where xh="
					+ "'" + xh + "'" + " and tablename='" + kind + "'";
			pstmt = connection.prepareStatement(querysql);
			ResultSet rs = pstmt.executeQuery();
			boolean isPicExist = false;
			if (rs.next()) {
				isPicExist = true;
			}
			pstmt.close();

			if (!isPicExist) {
				String insertsql = "insert into " + "APP_PICTURE"
						+ "(XH,TABLENAME) values(?,?)";
				pstmt = connection.prepareStatement(insertsql);
				pstmt.setString(1, xh);
				pstmt.setString(2, kind);
				pstmt.executeUpdate();
				pstmt.close();
			}
			// 往oracle里面类型为blob写入时，必须先插入一个empty_blob，实行update
			String sql = "update APP_PICTURE set " + "PIC"
					+ "=empty_blob() where XH=" + "'" + xh
					+ "' and tablename='" + kind + "'";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();

			pstmt = connection.prepareStatement("select " + "PIC"
					+ " from APP_PICTURE where XH=" + "'" + xh
					+ "' and tablename='" + kind + "'" + " for update");
			ResultSet rset = pstmt.executeQuery();
			BLOB blob = null;
			if (rset.next()) {
				blob = (BLOB) rset.getBlob("PIC");
			}
			ByteArrayInputStream httpBis = new ByteArrayInputStream(content);

			OutputStream out = blob.getBinaryOutputStream();
			byte[] b = new byte[blob.getBufferSize()];
			int len = 0;

			try {
				while ((len = httpBis.read(b)) != -1) {
					out.write(b, 0, len);
				}
				httpBis.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				return "false";
			}
			pstmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "false";
		}
		return "true";
	}

	/**
	 * 业务办理保存照片
	 * 
	 * @param params
	 * @throws SQLException
	 */
	public String jobSavePic(String xh, String pic, String kind)
			throws SQLException {
		String result = "";
		jobWritePic(xh, pic, kind);
		return result;
	}

	@SuppressWarnings("deprecation")
	public void jobWritePic(String xh, String pic, String kind)
			throws SQLException {
		String path = WebServiceUtil.class.getResource("/").getPath();
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			String filePath = URLDecoder.decode(path + "zscgdb.properties",
					"UTF-8");
			FileInputStream is = new FileInputStream(filePath);
			Properties prop = new Properties();
			prop.load(is);
			String dbUrl = prop.getProperty("url");
			String dbName = prop.getProperty("zscg_username");
			String dbpwd = prop.getProperty("zscg_password");
			oracle.jdbc.driver.OracleDriver driver = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbUrl, dbName, dbpwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.setAutoCommit(false);

		String querysql = "select " + "xh from APP_PICTURE " + "where xh="
				+ "'" + xh + "'";
		pstmt = connection.prepareStatement(querysql);
		ResultSet rs = pstmt.executeQuery();
		boolean isPicExist = false;
		if (rs.next()) {
			isPicExist = true;
		}
		pstmt.close();

		if (!isPicExist) {
			String insertsql = "insert into " + "APP_PICTURE"
					+ "(XH,TABLENAME) values(?,?)";
			pstmt = connection.prepareStatement(insertsql);
			pstmt.setString(1, xh);
			pstmt.setString(2, kind);
			pstmt.executeUpdate();
			pstmt.close();
		}
		// 往oracle里面类型为blob写入时，必须先插入一个empty_blob，实行update
		String sql = "update APP_PICTURE set " + "PIC"
				+ "=empty_blob() where XH=" + "'" + xh + "'";
		pstmt = connection.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();

		pstmt = connection.prepareStatement("select " + "PIC"
				+ " from APP_PICTURE where XH=" + "'" + xh + "'"
				+ " for update");
		ResultSet rset = pstmt.executeQuery();
		BLOB blob = null;
		if (rset.next()) {
			blob = (BLOB) rset.getBlob("PIC");
		}
		ByteArrayInputStream httpBis = new ByteArrayInputStream(pic.getBytes());

		OutputStream out = blob.getBinaryOutputStream();
		byte[] b = new byte[blob.getBufferSize()];
		int len = 0;

		try {
			while ((len = httpBis.read(b)) != -1) {
				out.write(b, 0, len);
			}
			httpBis.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		pstmt.close();
		connection.commit();
		connection.close();
	}

	/**
	 * 上传多媒体文件写入本地文件系统中
	 * 
	 * @param filename
	 * @param mediaId
	 * @return
	 */
	public boolean uploadFile(String filename, String mediaId) {
		String requestUrl = DOWNLOAD_MEDIA_URL.replace("ACCESS_TOKEN",
				TokenThread.accessToken.getToken())
				.replace("MEDIA_ID", mediaId);
		String basePath = "E:\\weixin_upload\\";
		String filePath = basePath + filename;

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			BufferedInputStream bis = new BufferedInputStream(conn
					.getInputStream());
			ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = bis.read(temp)) != -1) {
				bOut.write(temp, 0, size);
			}
			// 图片二进制数据
			byte[] content = bOut.toByteArray();
			bis.close();

			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

			// 建立输出字节流
			FileOutputStream fos = new FileOutputStream(file);
			// 用FileOutputStream 的write方法写入字节数组
			fos.write(content);
			System.out.println(filename + "写入成功");

			// 为了节省IO流的开销，需要关闭
			fos.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 用户上报拥堵数据
	 * 
	 * @param params
	 * @return
	 */
	public String congestionUpload(String params) {
		String jkid = "B001154";
		return writeOutService(jkid, params);
	}

	/**
	 * 查询活动情况
	 * 
	 * @param params
	 * @throws SQLException
	 */
	public static String activityQuery(String params) throws SQLException {
		String jkid = "A012905";
		return queryOutService(jkid, params);
	}

	/**
	 * 写入活动用户提交信息
	 * 
	 * @param params
	 * @throws SQLException
	 */
	public static String activityWrite(String params) throws SQLException {
		String jkid = "A012906";
		return queryOutService(jkid, params);
	}

	/**
	 * 微信判断是否绑定接口
	 * 
	 * @param params
	 */
	public static String wxZscgIsbind(String params) {
		String jkid = "A900017";
		return queryOutService(jkid, params);
	}

	/**
	 * 微信绑定接口
	 * 
	 * @param params
	 */
	public static String wxZscgBind(String params) {
		String jkid = "A900018";
		return queryOutService(jkid, params);
	}

	/**
	 * 微信解绑接口
	 * 
	 * @param params
	 */
	public static String wxZscgUnbind(String params) {
		String jkid = "A900019";
		return queryOutService(jkid, params);
	}

	/**
	 * 举报交通违法接口
	 * 
	 * @param params
	 */
	public static String reportTrafficVio(String params) {
		String jkid = "A900020";
		return queryOutService(jkid, params);
	}

	/**
	 * 举报交通上传用户信息接口
	 * 
	 * @param params
	 */
	public static String reportTrafficVioPerson(String params) {
		String jkid = "A900022";
		return queryOutService(jkid, params);
	}

	/**
	 * 金华机动车信息查询
	 * 
	 * @param params
	 */
	public static String queryVehInfoJh(String params) {
		String jkid = "A900023";
		return queryOutService(jkid, params);
	}

	/**
	 * 金华机动车违法查询
	 * 
	 * @param params
	 */
	public static String queryVehVioJh(String params) {
		String jkid = "A900024";
		return queryOutService(jkid, params);
	}

	/**
	 * 金华驾驶人信息查询
	 * 
	 * @param params
	 */
	public static String queryDrvInfoJh(String params) {
		String jkid = "A900025";
		return queryOutService(jkid, params);
	}

	/**
	 * 金华驾驶人违法查询
	 * 
	 * @param params
	 */
	public static String queryDrvVioJh(String params) {
		String jkid = "A900026";
		return queryOutService(jkid, params);
	}

	/**
	 * 
	 * @Description: 记录用户领取红包信息
	 * @param wxid
	 *            用户ID
	 * @param flag
	 * @throws SQLException
	 * @return String 数据是否写入成功
	 * @author yexin
	 * @date 2015-12-7
	 */
	public static String activityRecWrite(String wxid, String flag)
			throws SQLException {
		/*
		 * WxActivityRec rec = new WxActivityRec(); try {
		 * rec.setXh(DaoFactory.getWxActivityRecDaoInstance().getNextXh());
		 * rec.setWxid(wxid); rec.setFlag(flag);
		 * DaoFactory.getWxActivityRecDaoInstance().insertWxActivityRec(rec); }
		 * catch (Exception e) { // e.printStackTrace(); }
		 */
		return "{\"head\":{\"message\":\"接口调用成功\",\"code\":\"1\"}}";
	}

	/**
	 * 
	 * @Description: 从题库随机获取试题
	 * @throws SQLException
	 * 
	 * @return String 活动试题列表
	 * 
	 * @author yexin
	 * @date 2015-12-7
	 */
	public static String getActivityExam() throws SQLException {
		/*
		 * List<WxActivityExam> list=new ArrayList<WxActivityExam>(); JSONObject
		 * jsonObject = new JSONObject(); try { int[] random =
		 * randomCommon(1,11,3);
		 * list.add(DaoFactory.getWxActivityExamDaoInstance()
		 * .selectValueFromWxActivityExam(random[0]));
		 * list.add(DaoFactory.getWxActivityExamDaoInstance()
		 * .selectValueFromWxActivityExam(random[1]));
		 * list.add(DaoFactory.getWxActivityExamDaoInstance()
		 * .selectValueFromWxActivityExam(random[2]));
		 * 
		 * //JSONArray jsonArray = JSONArray.fromObject(list);//构建json数组
		 * //往json对象中加值 //jsonObject.put("person", person);//添加对象
		 * jsonObject.put("exams", list);//添加数组，list和ss都可以 //s.put("personss",
		 * ss);//添加数组，list和ss都可以 //jsonObject.put("comCount", 3);
		 * System.out.println(jsonObject.toString()); } catch (Exception e) { //
		 * e.printStackTrace(); } return jsonObject.toString();
		 */
		return null;
	}

	/**
	 * 随机指定范围内N个不重复的数 最简单最基本的方法
	 * 
	 * @param min
	 *            指定范围最小值
	 * @param max
	 *            指定范围最大值
	 * @param n
	 *            随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
	}

	/**
	 * 存储过程的异常处理方法
	 * 
	 * @Description: 捕获存储过程异常，处理异常数据
	 * 
	 * @param string
	 *            存储过程返回的xml
	 * 
	 * @return String 返回异常处理结果
	 * 
	 * @author xiaoyan
	 * 
	 * @date 2015-12-7
	 */
	private static String outServiceException(String string) {
		String result = null;
		if (string.contains("Could not invoke service")) {
			result = EXCEPT_STRING;
		}
		if (string.contains("220.191.243.245")
				|| string.contains("zzxh.zjsgat.gov.cn")) {
			result = EXCEPT_STRING;
		}
		return result;
	}

}
