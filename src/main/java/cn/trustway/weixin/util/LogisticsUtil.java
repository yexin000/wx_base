package cn.trustway.weixin.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.trustway.weixin.util.http.AppClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class LogisticsUtil {


	/**
	 *
	 输入参数

	 名称	类型	是否必需	示例值	　　描述
	 customer	String	是		我方分配给贵司的的公司编号, 请参考邮件《快递100-企业版快递查询接口（API）——授权key及相关工具》
	 sign	String	是		签名， 用于验证身份， 按param + key + customer 的顺序进行MD5加密（注意加密后字符串一定要转大写）， 不需要加上“+”号， 如{"com": "yuantong", "num": "500306190180", "from": "广东省深圳市", "to": "北京市朝阳区"}xxxxxxxxxxxxyyyyyyyyyyy yyyyyyyyyyyyyyyyyyyyy
	 param	Object	是		由其他字段拼接
	 └ com	string	是	yuantong	查询的快递公司的编码， 一律用小写字母
	 └ num	string	是	12345678	查询的快递单号， 单号的最大长度是32个字符
	 └ from	string	否	广东深圳	出发地城市
	 └ to	string	否	北京朝阳	目的地城市，到达目的地后会加大监控频率
	 └ resultv2	int	否	1	添加此字段表示开通行政区域解析功能



	 .返回结果
	 字段名称	　　字段含义
	 message	消息体，请忽略
	 state	快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
	 status	通讯状态，请忽略
	 condition	快递单明细状态标记，暂未实现，请忽略
	 ischeck	是否签收标记，请忽略，明细状态请参考state字段
	 com	快递公司编码,一律用小写字母
	 nu	单号
	 data	最新查询结果，数组，包含多项，全量，倒序（即时间最新的在最前），每项都是对象，对象包含字段请展开
	  └ context	内容
	  └ time	时间，原始格式
	  └ ftime	格式化后时间

	 */
	public static String getLogistics(String com, String nu, String from,
									  String to) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		LinkedHashMap<String, String> signMap = new LinkedHashMap<String, String>();
		signMap.put("com", com);
		signMap.put("num", nu);
		signMap.put("from", from);
		signMap.put("to", to);
		JSONObject jb = JSONObject.fromObject(signMap);
		String sign = MD5.md5dx(jb.toString() + "nqiTLywn263"
				+ "AF14F65F85360E58A384D87DC7B0DE20");
		map.put("customer", "AF14F65F85360E58A384D87DC7B0DE20");
		map.put("sign", sign);


		map.put("param", jb.toString());
		String str = AppClient.buildRequest(
				"http://poll.kuaidi100.com/poll/query.do", "", "", map);
		return str;
	}


	public static void main(String[] args) {
		try {
			String str2 = getLogistics("yuantong", "DD2047086911", "2222",
					"3333");
			JSONObject jo = JSONObject.fromObject(str2);
			String message = jo.get("message")+"";
			String dataList = jo.get("data")+"";
			JSONArray ja =  JSONArray.fromObject(dataList);

			for(int i=0; i < ja.size();i++){
				String logisticsObj = ja.get(i)+"";
				JSONObject ljb = JSONObject.fromObject(logisticsObj);
				String time = ljb.get("time")+"";
				String context = ljb.get("context")+"";
				System.out.println(time+"--->>"+context);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
