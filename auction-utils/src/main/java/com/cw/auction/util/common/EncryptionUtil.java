package com.cw.auction.util.common;



import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptionUtil {
	private static Key key;
	private static String KEY_STR = "myKey";
	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom(KEY_STR.getBytes()));
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对str进行DES加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		if(StringUtils.isBlank(str)){
			return null;
		}
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对str进行DES解密
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		if(StringUtils.isBlank(str)){
			return null;
		}
		BASE64Decoder base64De = new BASE64Decoder();
		try {
			byte[] strBytes = base64De.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println(getEncryptString("18612940593"));
		//System.out.println(getDecryptString("xW6OiZLo8/km+o3XGyFWCKcUgCWR5dfNMi6HPVRsPeyrVN9cE52F9Q=="));
	}
}

