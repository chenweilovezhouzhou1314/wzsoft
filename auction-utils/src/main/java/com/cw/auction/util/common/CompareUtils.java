package com.cw.auction.util.common;

import java.util.Comparator;
import java.util.Map;

/**
 * @Comments ：
 * @Author ：陈伟
 * @Group : 项目部
 * @Worker: 1699
 * @Date ：2016年3月17日 下午3:50:50
 * @Project ：myExamples
 * @Company ：Vstsoft
 */
@SuppressWarnings("rawtypes")
public class CompareUtils implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Map map1 = (Map) obj1;
		Map map2 = (Map) obj2;
		if (map1.get("noticeid") != null && map2.get("noticeid") != null) {
			return map2.get("noticeid").toString()
					.compareTo(map1.get("noticeid").toString());
		}
		return 0;
	}

}
