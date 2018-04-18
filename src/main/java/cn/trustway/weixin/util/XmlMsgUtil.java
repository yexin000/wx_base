package cn.trustway.weixin.util;


import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import cn.trustway.weixin.message.MsgType.EventType;
import cn.trustway.weixin.message.MsgType.ReqType;

import com.thoughtworks.xstream.XStream;
/**
 * XML消息工具类,负责XML与JAVA BEAN之间的转换
 */
public class XmlMsgUtil
{
	private static XStream xs;
	
	static
	{
		//启用CDATA数据填充，同时，配置不需要填充的字段。
		CDataDriver cDataDriver = new CDataDriver();
		cDataDriver.addExcluded("CreateTime");
		cDataDriver.addExcluded("ArticleCount");
		
		xs = new XStream(cDataDriver);
		xs.autodetectAnnotations(true);
	}
	
	/**
	 * 将应答消息转成XML。
	 * 
	 * @param bean
	 * @return
	 */
	public static String bean2Xml(Object bean)
	{
		return xs.toXML(bean);
	}
	
	public static Map<String, Object> xml2Map(String xml)
	{
		try
		{
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new StringReader(xml));
			Element root = doc.getRootElement();
			Map<String, Object> result = new HashMap<String, Object>();
 
			//将XML消息转换为MAP形式,其中的key值需转换为小写开头,以方便Java BEAN的处理.
			List<Element> elements = root.getChildren();
			for (Element element : elements)
			{
				String name = element.getName();
				result.put(name.substring(0,1).toLowerCase()+ name.substring(1), element.getText());
			}
			
			return result;
		}
		catch (Exception e) 
		{
			throw new RuntimeException("error while converting xml to map", e);
		}
	}
	
	
	/**
	 * 将XML请求消息转成JAVA BEAN.
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xml2Bean(String xml)
	{

    	try
    	{
    		Map<String, Object> props = xml2Map(xml);
    		Class<?> targetCls = null;
    		
    		String msgType = (String) props.get("msgType");
    		if("event".equalsIgnoreCase(msgType))
    		{//是事件消息
    			String event = (String) props.get("event");
    			EventType eventType = EventType.valueOf(event);
    			targetCls = eventType.getMsgCls();
    		}
    		else
    		{//是用户请求消息
    			ReqType reqType = ReqType.valueOf(msgType);
    			targetCls = reqType.getMsgCls();
    		}
    		
			//实例化后将MAP的值COPY到 BEAN里面
    		Object result = targetCls.newInstance();
    		BeanUtils.populate(result, props);
    		
    		return (T)result;	
    	}
    	catch (Exception e) 
    	{
    		throw new RuntimeException("error while converting xml message to java bean", e);
		}
	}
}
