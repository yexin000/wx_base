package cn.trustway.weixin.util;

import cn.trustway.weixin.message.pushmsg.BasePushMsg;
import cn.trustway.weixin.message.pushmsg.TextPushMsg;

import net.sf.json.JSONObject;


/**
 * 推送消息工具类
 * 
 * @author yexin
 */

public class PushMsgUtil {
	/**
	 * 推送消息类型：文本
	 */
	public static final String PUSH_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 推送消息类型：图文
	 */
	public static final String PUSH_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 推送文本消息对象转换成json
	 * 
	 * @param textPushMsg文本消息对象
	 * @return json字符串
	 */
	public static String textPushMsgToJson(TextPushMsg textPushMsg) {
		JSONObject json;
		json = JSONObject.fromObject(textPushMsg);
		return json.toString();
	}

	/**
	 * 推送图文消息对象转换成json
	 * 
	 * @param newsPushMsg图文消息对象
	 * @return json字符串
	 */
//	public static String newsPushMsgToJson(NewsPushMsg newsPushMsg) {
//		JSONObject json;
//		json = JSONObject.fromObject(newsPushMsg);
//		return json.toString();
//	}
	
	/**
	 * 推送消息对象转换成json
	 * 
	 * @param pushMsg推送消息对象
	 * @return json字符串
	 */
	public static String pushMsgToJson(BasePushMsg basePushMsg) {
		JSONObject json;
		json = JSONObject.fromObject(basePushMsg);
		return json.toString();
	}

}