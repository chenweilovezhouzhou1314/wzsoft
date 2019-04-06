package com.cw.auction.util.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Comments ：系统公用工具类
 * @Author ：陈伟
 * @Group : A组
 * @Worker: 1001
 * @Date ：2016年10月12日 下午9:27:44
 * @Project ：ktshop-web 
 * @Company ：枣庄康婷控股有限公司
 */
public class V {
	final static Logger logger = LoggerFactory.getLogger(V.class);
	
	public final static String UTF8 = "UTF-8";
	public final static String UI = "ui";
	public final static String WEB_INF = "WEB-INF";
	public final static String CLASSESS = "classes";
	public final static String PAGES = "pages";
	public final static String File_SEP = "/";
	public final static String EASYUI = "easyui";
	public final static String File_COMMA = ",";
	/**
	 * @Comments ：处理js请求路径
	 * @param request
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月29日 下午10:46:31
	 */
	public static String buildRequestPath(HttpServletRequest request ,String requestPath){
		String fullPath = "";
		if(request.getRequestURI().contains(UI)) {
			fullPath = buildUiPath(request,requestPath);
		}else{
			fullPath = buildPagePath(request,requestPath);
		}
		return fullPath;
	}
	/**
	 * @Comments ：处理ui下面的js
	 * @param requestUri
	 * @param requestPath
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 上午10:45:07
	 */
	public static String buildUiPath(HttpServletRequest request ,String requestPath){
		String requestUri = request.getRequestURI();
		requestUri = UI+File_SEP+requestUri.substring(14,requestUri.length());
		if(requestPath.contains(WEB_INF))
			requestPath = requestPath.substring(0,requestPath.indexOf(WEB_INF));
		return requestPath+requestUri;
	}
	/**
	 * @Comments ：处理page页面下的js
	 * @param requestUri
	 * @param requestPath
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 上午10:46:13
	 */
	public static String buildPagePath(HttpServletRequest request,String requestPath){
		String requestUri = request.getRequestURI();
		if(StringUtils.isNotBlank(requestUri))
			requestUri=requestUri.substring(request.getContextPath().length(),requestUri.length());
		if(requestPath.contains(CLASSESS))
			requestPath = requestPath.substring(0,requestPath.indexOf(CLASSESS));
		return requestPath+PAGES+requestUri;
	}
	
	
	/**
	 * @Comments ：获取easyui 版本
	 * @param request 
	 * @return   
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月29日 下午11:04:09
	 */
	public static String getEasyUiVersion(HttpServletRequest request){
		String browInfo = request.getHeader("user-agent")==null ?"MSIE 9.0" :request.getHeader("user-agent");
		String easyUiVersion = "easyui";
		if(browInfo.indexOf("MSIE 9.0") != -1) easyUiVersion = "easyui";
		if(browInfo.indexOf("MSIE 8.0") != -1) easyUiVersion = "easyui";
		if(browInfo.indexOf("MSIE 7.0") != -1) easyUiVersion = "easyui";
		if(browInfo.indexOf("Firefox") != -1)  easyUiVersion = "easyui";
		if(browInfo.indexOf("Chrome") != -1 && browInfo.indexOf("Safari") != -1) easyUiVersion = "easyui";
		return easyUiVersion;
	}
	/**
	 * @Comments ：获取请求浏览器类型(登录时用)
	 *     判断主要针对 主流常用浏览器 如360浏览器为IE内核
	 *     按照IE浏览器进行判断
	 * @param request
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 下午5:24:48
	 */
	public static String getBrowVersion(HttpServletRequest request){
		String browInfo = request.getHeader("user-agent")==null ?"MSIE 9.0" :request.getHeader("user-agent");
		String browVersion = "";
		if(browInfo.indexOf("MSIE") != -1) browVersion = "IE";
		if(browInfo.indexOf("Firefox") != -1)  browVersion = "Firefox";
		if(browInfo.indexOf("Chrome") != -1 && browInfo.indexOf("Safari") != -1) browVersion = "Chrome";
		return browVersion;
	}
	/**
	 * @Comments ：加载js
	 * @param request
	 * @param path
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 下午12:56:14
	 */
	public static String includedJavascript(HttpServletRequest request,String path) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'");
		sb.append(" src='").append(path).append("'>");
		sb.append("</script>");
		return sb.toString();
	}
	/**
	 * @Comments ：加载css
	 * @param request
	 * @param path
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 下午12:56:53
	 */
	public static String includedStyle(HttpServletRequest request, String path) {
		StringBuffer sb = new StringBuffer();
		sb.append("<link type='text/css' rel='stylesheet'");
		sb.append(" href='").append(path).append("'>");
		return sb.toString();
	}
	
	/**
	 * @Comments ：下载输出js
	 * @param response
	 * @param o
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月24日 上午10:31:56
	 */
	public static void outPrintJs(HttpServletResponse response, String o) {
		response.setCharacterEncoding(UTF8);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(o);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	
}
