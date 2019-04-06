package com.cw.auction.util.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**@Comments ：
 * @Author ：陈伟
 * @Group : 项目部
 * @Worker: 1699
 * @Date ：2016年4月29日 上午9:52:30
 * @Project ：lovepro 
 * @Company ：Vstsoft
 */
public class ServletContextUtil {
static ThreadLocal<ServletSessionUtil> smartContext = new ThreadLocal<ServletSessionUtil>();
    
    public static void setContext(ServletSessionUtil context) {
    	smartContext.set(context);
    }
    
    public static void cleanContext() {
    	smartContext.set(null);
    }

    public static ServletSessionUtil getContext() {
        return smartContext.get();
    }
    
    public static void remove() {
    	smartContext.remove();
    }
    
    public static HttpServletRequest getRequest() {
        return ServletContextUtil.getContext().getRequest();
    }
    
    public static HttpServletResponse getResponse() {
        return ServletContextUtil.getContext().getResponse();
    }
    
    public static Object getObjectFromCache(String name) {
        return ServletContextUtil.getContext().getObjectFromCache(name);
    }
}

