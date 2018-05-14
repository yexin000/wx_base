/**  
 * @Title: HttpClientUtil.java
 * @Package cn.trustway.weixin.util
 * @Description: HTTP请求工具类
 * @author yexin
 * @date 2016-6-1
 */
package cn.trustway.weixin.util;

import cn.trustway.weixin.pojo.WxSession;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 * @Title:HttpClientUtil
 * @Description: HTTP请求工具类
 * @Company: Hangzhou Trustway Technology Co. Ltd
 * @author yexin
 * @version
 * @date 2016-6-1 下午02:35:23
 */
public class HttpClientUtil {
	/**
	 * 
	 * @Description: 发送post请求并传递json格式参数
	 * @param url
	 * @param jsonString
	 * @param charset
	 * @return   
	 * @return String  
	 * @throws
	 * @author yexin
	 * @date 2016-6-1
	 */
	public static String doPost(String url, String jsonString, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			StringEntity entity = new StringEntity(jsonString,"UTF-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity); 
			
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx42296a47e670ec4d&secret=ba97620f5dd7054926f8a4b9621bca3c&grant_type=authorization_code&js_code=0713OVMJ1lEpE40Fu9KJ1s0WMJ13OVMZ";
	    String charset = "UTF-8";  
	    HttpClientUtil httpClientUtil = new HttpClientUtil(); 
	    String jsonString = "{}";
		String httpOrgCreateTestRtn = httpClientUtil.doPost(url, jsonString, charset);
		JSONObject obj = new JSONObject().fromObject(httpOrgCreateTestRtn);
		WxSession wxSession = (WxSession)JSONObject.toBean(obj,WxSession.class);
        System.out.println("result:"+httpOrgCreateTestRtn);  
	}
}
