package cn.trustway.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.trustway.weixin.service.CoreService;
import cn.trustway.weixin.util.SignUtil;

/**
 * 核心请求处理类
 * 
 * @author yexin
 * @date 2014-05-18
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;
	/** 核心服务消息处理类 */
	private CoreService coreService;
	/** 接入请求参数：加密签名 */
	private static final String SIGNATURE = "signature";
	/** 接入请求参数：时间戳 */
	private static final String TIMESTAMP = "timestamp";
	/** 接入请求参数：随机数 */
	private static final String NONCE = "nonce";
	/** 接入请求参数：随机字符串 */
	private static final String ECHOSTR = "echostr";

	/**
	 * 微信服务器接入验证请求
	 * 
	 * @param request
	 * @param response
	 * @author yexin
	 * @return
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter(SIGNATURE);
		// 时间戳
		String timestamp = request.getParameter(TIMESTAMP);
		// 随机数
		String nonce = request.getParameter(NONCE);
		// 随机字符串
		String echostr = request.getParameter(ECHOSTR);

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		System.out.println("微信验证");
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			System.out.println("微信验证成功");
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 获取请求中的信息
	 * 
	 * @param request
	 * @param response
	 * @author yexin
	 * @return String
	 */
	private String getHttpBody(HttpServletRequest request) throws IOException {
		// 设置编码
		String charset = request.getCharacterEncoding();
		if (charset == null) {
			charset = "UTF-8";
		}

		// 读取数据
		BufferedReader in = new BufferedReader(new InputStreamReader(request
				.getInputStream(), charset));
		String line = null;
		StringBuilder result = new StringBuilder();
		while ((line = in.readLine()) != null) {
			result.append(line);
		}

		in.close();
		return result.toString();
	}

	/**
	 * 处理微信服务器发来的消息
	 * 
	 * @param request
	 * @param response
	 * @author yexin
	 * @return
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8，防止中文乱码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String httpBody = getHttpBody(request);
		// 消息处理类
		coreService = new CoreService(this.getServletContext());
		// 处理消息
		String respMessage = coreService.processMsg(httpBody);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

}