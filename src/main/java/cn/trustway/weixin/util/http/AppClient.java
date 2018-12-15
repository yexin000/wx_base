package cn.trustway.weixin.util.http;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.trustway.weixin.bean.SmsSendResponse;
import cn.trustway.weixin.model.SmsSendRequest;
import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.NameValuePair;

public class AppClient {
	  public static String buildRequest(String url, String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {
		     //   Map<String, String> sPara = buildRequestPara(sParaTemp);

		        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		        request.setCharset("UTF-8");
		        request.setMethod("POST");
		        request.setParameters(generatNameValuePair(sParaTemp));
		        request.setUrl(url);

		        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
		        if (response == null) {
		            return null;
		        }
		        
		        String strResult = response.getStringResult();

		        return strResult;
		    }
		   /**
		     * MAP类型数组转换成NameValuePair类型
		     * @param properties  MAP类型数组
		     * @return NameValuePair类型数组
		     */
		    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
		        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		        int i = 0;
		        for (Map.Entry<String, String> entry : properties.entrySet()) {
		            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		        }

		        return nameValuePair;
		    }
		    public static String buildRequest1(String url, String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {
			     //   Map<String, String> sPara = buildRequestPara(sParaTemp);

			        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

			        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			        request.setCharset("gbk");

			        request.setParameters(generatNameValuePair(sParaTemp));
			        request.setUrl(url);

			        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
			        if (response == null) {
			            return null;
			        }
			        
			        String strResult = response.getStringResult();

			        return strResult;
			    }
		    public static byte[] buildByteRequest(String url, String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {
			     //   Map<String, String> sPara = buildRequestPara(sParaTemp);

			        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

			        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			        request.setCharset("gbk");
			        request.setMethod(HttpRequest.METHOD_GET);
			        request.setParameters(generatNameValuePair(sParaTemp));
			        request.setUrl(url);
			        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
			        if (response == null) {
			            return null;
			        }
			        
			        byte[] bResult = response.getByteResult();

			        return bResult;
			    }
	public static String sendSmsByPost(String path, String postContent) {
		URL url = null;
		try {
			url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
			httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");

//			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//			printWriter.write(postContent);
//			printWriter.flush();

			httpURLConnection.connect();
			OutputStream os=httpURLConnection.getOutputStream();
			os.write(postContent.getBytes("UTF-8"));
			os.flush();

			StringBuilder sb = new StringBuilder();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				// 开始获取数据
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String charset = "utf-8";

	// 请登录zz.253.com 获取创蓝API账号(非登录账号,示例:N1234567)
	public static String account = "N9398925";

	// 请登录zz.253.com 获取创蓝API密码(非登录密码)
	public static String pswd = "i6eDO2tH1";

	//短信发送的URL 请登录zz.253.com 获取完整的URL接口信息
	public static String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";

	//状态报告
	public static String report= "true";

	public static SmsSendResponse sendChuanglanMessage (String msg ,String phone){
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);
		String requestJson = JSON.toJSONString(smsSingleRequest);
		String response = sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		return smsSingleResponse;
	}

	public static void main(String[] args) throws Exception {
		// 设置您要发送的内容：其中“【】”中括号为运营商签名符号，多签名内容前置添加提交
		String msg = "【百姓收藏】您有新的订单待处理";
		//手机号码
		String phone = "13755158660";
		SmsSendResponse s = sendChuanglanMessage(msg,phone);
		System.out.println("response  toString is :" + s);
	}



//		    public static void main(String[] args) {
//		    	Map<String, String> map = new HashMap<String, String>();
//		    	String url = "http://smssh1.253.com/msg/send/json";
//		    	map.put("account", "M3557304");
//				map.put("password", "Lf9ndB5Kk19893");
//				map.put("msg", "您有新的订单需要处理");
//				map.put("phone", "13755158660");
//				map.put("report", "true");
//		    	try {
//		    		String result = buildRequest(url, "", "", map);
//		    		System.out.println(result);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//
//			}
}
