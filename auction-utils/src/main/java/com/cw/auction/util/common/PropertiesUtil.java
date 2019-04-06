package com.cw.auction.util.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**@Comments ：加载所有配置文件
 * @Author ：陈伟
 * @Group : 研发中心F组
 * @Worker: 1699
 * @Date ：2015年5月29日 上午10:22:46
 * @Project ：lovepro 
 * @Company ：Vstsoft
 */
public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties config = new Properties();
	
	static{
		InputStream in = null ;
		ClassLoader loader =  PropertiesUtil.class.getClassLoader();
		try {
			 /*----加载系统配置文件------*/
			in = loader.getResourceAsStream("../../properties/config.properties");
			/*-----加载系统配置文件-----*/	
			
			config.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("配置文件读取出错");
		} finally{
			 if(in!=null){
			    try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
		
	}
	/**
	 * @Comments ：根据key值 获得配置文件配置的值
	 * @param name
	 * @return
	 * @Author ：陈伟
	 * @Group : 研发中心F组
	 * @Worker: 1699
	 * @Date ：2015年5月29日 上午11:07:12
	 */
	public static String getProperty(String name){
		if(name==null||name=="") return "";
		String value = null ;
		 try {
			value = config.getProperty(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return value;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getProperty("userName"));
	}
	
}

