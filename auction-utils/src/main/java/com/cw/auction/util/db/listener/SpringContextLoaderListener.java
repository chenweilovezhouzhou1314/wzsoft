package com.cw.auction.util.db.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.core.annotation.Order;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * 初始化WebRootApplicationContext的listener，因为在系统中需要获取到WebRootApplicationContext，
 * 因此使用该类继承了spring的{@link ContextLoaderListener}，并提供方法获取WebRooApplicationContext
 * @author yanlg
 * @since 1.0
 * @date 2013-2-21
 */
@Order(2)
@WebListener
public class SpringContextLoaderListener extends ContextLoaderListener {
	private static WebApplicationContext applicationContext;
	
	public static ServletContext servletContext;
	
	public SpringContextLoaderListener() {
		super();
	} 
	
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		servletContext = event.getServletContext();
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}
	
	public static WebApplicationContext getWebRootAppContext() {
		return applicationContext;
	}
	public static ServletContext getServletContext() {
		return servletContext;
	}

}
