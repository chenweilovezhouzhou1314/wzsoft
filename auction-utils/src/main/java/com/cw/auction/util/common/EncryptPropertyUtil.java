package com.cw.auction.util.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @Comments ：处理加密的数据库连接配置属性
 * @Author ：陈伟
 * @Group : 研发中心F组
 * @Worker: 1699
 * @Date ：2015年6月2日 上午10:40:52
 * @Project ：lovepro
 * @Company ：Vstsoft
 */
public class EncryptPropertyUtil extends PropertyPlaceholderConfigurer {
	
	/*---驱动名称---*/
	public final static String LOVE_DRIVENAME = "driverName";
	/*---数据库连接url---*/
	public final static String LOVE_URL = "url";
	/*---数据库用户名---*/
	public final static String LOVE_USERNAME = "userName";
	/*---数据库用户密码---*/
	public final static String LOVE_PASSWORD = "password";
	
	// 定义要转换处理的属性的名称
	private String[] encryptPropNames = {LOVE_DRIVENAME,LOVE_URL, LOVE_USERNAME,LOVE_PASSWORD };
	
	
	
    /**
     * 把需要转换的属性进行解密转换
     */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName))
			return EncryptionUtil.getDecryptString(propertyValue);
		else
			return propertyValue;
	}

	/**
	 * @Comments ：判断是否是需要解密的属性
	 * @param propertyName
	 * @return
	 * @Author ：陈伟
	 * @Group : 研发中心F组
	 * @Worker: 1699
	 * @Date ：2015年6月2日 上午10:51:36
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encrypePropName : encryptPropNames) {
			if (StringUtils.equals(encrypePropName, propertyName)) {
				return true;
			}
		}
		return false;
	}

}
