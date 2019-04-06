package com.cw.auction.util.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**@Comments ：
 * @Author ：陈伟
 * @Group : 项目部
 * @Worker: 1699
 * @Date ：2016年4月29日 上午9:45:33
 * @Project ：lovepro 
 * @Company ：Vstsoft
 */
public class ServletSessionUtil {
	
	private HttpServletRequest request;
	private HttpServletResponse response;

	private Map<String, Object> objectCache = new HashMap<String, Object>();
	
	public String MESSAGE_MAP = "MESSAGE_MAP";
	
	public ServletSessionUtil(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public Object getObjectFromCache(String name){
		return this.objectCache.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getMessageMap(){
		return (Map<String, String>)this.objectCache.get(MESSAGE_MAP);
	}
	
	public void setMessageMap(Map<String, String> messageMap){
		this.objectCache.put(MESSAGE_MAP, messageMap);
	}


}

