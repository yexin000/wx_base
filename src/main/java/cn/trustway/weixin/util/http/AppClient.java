package cn.trustway.weixin.util.http;




import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

public class AppClient {
	  public static String buildRequest(String url, String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {
		     //   Map<String, String> sPara = buildRequestPara(sParaTemp);

		        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		        request.setCharset("UTF-8");
		       // request.setMethod("POST");
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

		    public static void main(String[] args) {
		    	Map<String, String> map = new HashMap<String, String>();
		    	String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		    	map.put("", "");
		    	try {
		    		String result = buildRequest(url, "", "", map);
		    		System.out.println(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
}
