package cn.trustway.weixin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContex对象配置获取
 * @author yexin
 *
 */
public class AppApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	// 声明一个静态变量保存

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}
}
