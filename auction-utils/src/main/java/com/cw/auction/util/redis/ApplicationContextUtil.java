package com.cw.auction.util.redis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**@Comments ：系统会话工具类
 * @Author ：陈伟
 * @Group : A组
 * @Worker: 1001
 * @Date ：2016年12月4日 上午10:26:42
 * @Project ：ktshop-service 
 * @Company ：枣庄康婷控股有限公司
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextUtil.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

     
	

}
