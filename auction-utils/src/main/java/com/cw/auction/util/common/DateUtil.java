package com.cw.auction.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @author chenwei
 *
 */
public class DateUtil {

	private final static Logger logger = LoggerFactory
			.getLogger(DateUtil.class);

	// 日期格式
	public static final String YMDHMS = "yyyyMMddHHmmss";
	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	public static final String PIC_YMD_HMS = "yyyy年MM月dd日 HH时:mm分:ss秒";
	public static final String YMD = "yyyy-MM-dd";
	public static final String YM = "yyyy-MM";

	/**
	 * 验证 日期时间格式的表达式
	 */
	private static final String DATETIME_REG = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))( (\\d|([01]\\d|2[0-3]))\\:(\\d|([0-5]\\d))\\:(\\d|[0-5]\\d))?$";

	/**
	 * @Comments ：是否是日期时间格式 ，格式的值是否合法
	 * @param value
	 *            如 2012-2-1 ，2012-02-01 ，2012/02/09，2012-02-01 12:2:59 错误的如：
	 *            2012-13-1， 2012-2-29，2012-6-31 ，2012-6-30 24:1:1 等
	 * @return
	 */
	private static boolean isValidDate(String value) {
		return Pattern.compile(DATETIME_REG).matcher(value).matches();
	}

	/**
	 * @Comments ：将日期解析为字符串
	 * @param date
	 *            日期
	 * @param format
	 *            个数化的格式 如 ： yyyy.MM.dd
	 * @return 返回格式化后的字符串 ，否者返回null
	 */
	public static String format(Date date, String format) {
		if (date == null || format == null || "".equals(format)) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			logger.error("date:" + date + ",format:" + format, e);
		}
		return null;
	}

	/**
	 * @Comments ：将字符串解析为日期
	 * @param date
	 *            日期的字符串
	 * @param format
	 *            日期格式
	 * @return 简析后的日期
	 */
	public static Date parse(String date, String format) {
		if (date == null || "".equals(date) || format == null
				|| "".equals(format) || !isValidDate(date)) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (Exception e) {
			logger.error("date:" + date + ",format:" + format, e);
		}
		return null;
	}
	
	/**
	 * 默认方式
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {
		try {
			return new SimpleDateFormat(YMD).parse(date);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @Comments ：转换日期
	 * @param date
	 * @return
	 * @Author ：陈伟
	 * @Group : 研发中心F组
	 * @Worker: 1699
	 * @Date ：2015年6月20日 下午2:22:03
	 */
	public static java.sql.Date paraseSqlDate(Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}
	/**
	 * @Comments ： 系统类专用
	 * @param date
	 * @return
	 * @Author ：陈伟
	 * @Group :临时组
	 * @Worker: 1001
	 * @Date ：2014-3-28 下午03:35:31
	 */
	public static String formatDateSecond(Date date) {
		return format(date, YMDHMS);
	}
	/**
	 * @Comments ：新增用户专用
	 * @param date
	 * @return
	 * @Author ：陈伟
	 * @Group : 研发中心F组
	 * @Worker: 1699
	 * @Date ：2015年6月21日 下午5:02:58
	 */
	public static Date parseDateSecond(String date) {
		return parse(date, YMD_HMS);
	}
	/**
     * @Comments ：新增用户专用
     * @param date
     * @return
     * @Author ：陈伟
     * @Group : 研发中心F组
     * @Worker: 1699
     * @Date ：2015年6月20日 下午2:21:25
     */
	public static String formatDateSecond2(Date date) {
		return format(date, YMD_HMS);
	}
	/**
	 * @Comments ：主页图片墙专用
	 * @param date
	 * @return
	 * @Author ：陈伟
	 * @Group : 研发中心F组
	 * @Worker: 1699
	 * @Date ：2015年6月23日 下午11:07:25
	 */
	public static String formatDateSecond3(Date date) {
		return format(date, PIC_YMD_HMS);
	}
	/**
	 * @Comments ：格式化日期
	 * @param date
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年3月17日 上午11:13:06
	 */
	public static String formatDateSecond2() {
		return format(new Date(), YMD_HMS);
	}
	
	public static void main(String[] args) {
		System.out.println(formatDateSecond3(new Date()).length());
	}
	
	
	
	
	
	
	
}