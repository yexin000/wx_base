package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.WeixinUser;
import cn.trustway.weixin.message.model.BaseMsg;
import cn.trustway.weixin.message.model.EventMsg;
import cn.trustway.weixin.message.model.RespMsgText;
import cn.trustway.weixin.util.ResponseMessageUtil;
import cn.trustway.weixin.util.StringUtil;
import cn.trustway.weixin.util.XmlMsgUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 核心消息处理服务类
 * 
 * @author yexin
 * @date 2014-05-20
 */
public class CoreService {
	/** 微信用户服务名 */
	private static final String WEIXIN_USER_SERVICE_NAME = "weixinUserService";
	/** 菜单点击计数服务 */
	private static final String CLICK_AMOUNT_SERVICE_NAME = "clickAmountService";
	/** 接收消息存储服务名 */
	private static final String RECV_MSG_SERVICE_NAME = "recvMsgService";
	/** 微信用户服务 */
	private WeixinUserService<WeixinUser> weixinUserService;  
	private ServletContext servletContext;
	
	/**
	 * 构造方法，初始化变量
	 * @param servletContext
	 * @author yexin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CoreService(ServletContext servletContext) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);   
		weixinUserService = (WeixinUserService)ctx.getBean(WEIXIN_USER_SERVICE_NAME);  
		this.servletContext = servletContext;
	}

	/**
	 * 消息解析分发
	 * @param xml
	 * @author yexin
	 * @return 消息处理后的要返回的消息
	 */
	public String processMsg(String xml) {
		// 解析消息
		/*BaseMsg msg = XmlMsgUtil.xml2Bean(xml);
		try {
			// 判断用户是否存在
			if (!isUserBindExisted(msg.getFromUserName())) {
				// 将用户写入到用户信息表中
				addUser(msg.getFromUserName());
			}
			// 图文回复性功能超时处理
			// 获取用户最后一次操作时间
			WeixinUser weixinUser = weixinUserService.queryWeixinUser(msg.getFromUserName());
			String lastTimeStr = weixinUser.getYlzd1();
			if(!StringUtil.isEmpty(lastTimeStr)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 用户最后一次操作时间
				Date lastTime = sdf.parse(lastTimeStr);
				// 当前时间
				Date curTime = new Date();
				// 时间差,毫秒级
				long timeDiff = Math.abs(lastTime.getTime() - curTime.getTime());
				// 时间差相差30分钟，清除zt标志
				if(timeDiff > (1000 * 60 * 30)) {
					WeixinUser user = new WeixinUser();
					user.setWxid(msg.getFromUserName());
					user.setZt("00");
					weixinUserService.updateBySelective(user);
				}

			// 更新操作时间
			//weixinUserService.updateUserOperTime(msg.getFromUserName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (msg.isEventMsg()) {
			// 事件推送消息处理
			return processEventMsg(msg);
		} else {
			// 用户消息处理
			return processPublicMsg(msg);
		}*/
		return "";
	}

	/**
	 * 处理事件消息
	 * @param msg
	 * @author yexin
	 * @return 处理后事件推送消息后返回的消息
	 */
	public String processEventMsg(BaseMsg msg) {
		String respEventMsg = "";
		// 消息转换为事件消息
		EventMsg eventMsg = (EventMsg) msg;
		RespMsgText respMsgText = null;

		if (eventMsg.isSubscribeEvent()) {// 用户关注
			// 返回欢迎语
			respMsgText = new RespMsgText(msg,
					ResponseMessageUtil.WELCOME_STRING);
			respEventMsg = XmlMsgUtil.bean2Xml(respMsgText);
			
		} else if (eventMsg.isUnsubscribeEvent()) {// 用户取消关注
			// 不做处理
		} else if (eventMsg.isClickEvent()) {
			try {
				// 菜单点击事件
				String eventKey = eventMsg.getEventKey();
				WeixinUser user = new WeixinUser();
				user.setWxid(msg.getFromUserName());
				//user.setZt(eventKey);
				weixinUserService.updateBySelective(user);
				respEventMsg = processPublicMsg(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return respEventMsg;
	}

	/**
	 * 处理普通的消息
	 * 
	 * @param msg
	 * @return
	 */
	public String processPublicMsg(BaseMsg msg) {
		return XmlMsgUtil.bean2Xml(new RespMsgText(msg,
				ResponseMessageUtil.DEFAULT_RESPS));
	}

	/**
	 * 判断数据库中是否有该用户
	 * 
	 * @param wxid
	 * @return
	 * @throws Exception
	 */

	public boolean isUserBindExisted(String wxid) throws Exception {
		boolean flag = false;
		WeixinUser weixinUser = weixinUserService.queryWeixinUser(wxid);
		
		if(weixinUser != null) {
			String userName = weixinUser.getWxid();
			if (userName != null && userName.equals(wxid)) {
				flag = true;
			}
		}
		
		return flag;
	}

	// 新建并插入新用户
	private void addUser(String wxid) {
		WeixinUser weixinUser = new WeixinUser();
		weixinUser.setWxid(wxid);
		//weixinUser.setZt("0");
		weixinUserService.insertWeixinUser(weixinUser);
	}
}