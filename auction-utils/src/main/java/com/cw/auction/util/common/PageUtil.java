package com.cw.auction.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * 分页相关工具类
 * 
 * @author Administrator
 *
 */
public class PageUtil {
	
    /*----分页每页条数----*/
	private final static String PAGESIZE = "pageSize";
	/*----分页当前页数----*/
	private final static String CURRENTPAGE = "currentPage";
	/*----分页总条数----*/
	private final static String TOTAL = "total";
	/*----分页记录标志----*/
	private final static String ROWS = "rows";
	/*----分页页数标志----*/
	private final static String PAGE = "page";
	/*----分页参数Map-----*/
	private static Map<String, Object> parMap = new HashMap<String,Object>();
	
	public static Map<String, Object> getParMap() {
		return parMap;
	}
	
	public static void setParMap(Map<String, Object> parMap) {
		PageUtil.parMap = parMap;
	}
	

	/**
	 * 获得表单参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getFormParameter(HttpServletRequest request) {
		Map<String, Object> formParameterMap = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String key = entry.getKey();
			if (key.length() < 3)
				continue;
			String[] values = entry.getValue();
			String value = StringUtils.join(values, ",");
			if ("".equals(value)) {
				value = null;
			}
			if (key.startsWith("f_") || key.startsWith("F_") || key.startsWith("p_") || key.startsWith("P_")) {
				key = key.substring(2);
				formParameterMap.put(key, value);
			}
		}
		return formParameterMap;
	}


	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getPageParameter() {
		parMap.put(PAGESIZE, parMap.get(ROWS) == null ? 2 : parMap.get(ROWS));
		parMap.put(CURRENTPAGE, parMap.get(PAGE) == null ? 1 : parMap.get(PAGE));
		return parMap;
	}
	/**
	 * 从request获取分页参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getPageParameter(HttpServletRequest request) {
		parMap.put(PAGESIZE, request.getParameter(ROWS) == null ? 10 : request.getParameter(ROWS));
		parMap.put(CURRENTPAGE, request.getParameter(PAGE) == null ? 1 : request.getParameter(PAGE));
		return parMap;
	}
	
	/**
	 * @Comments ：获取PageBounds对象
	 * @param parameterMap
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年6月3日 上午11:09:36
	 */
	public static PageBounds getPageBounds() {
		int currentPage = 0;
		int pageSize = 0 ;
		if(parMap!=null){
			currentPage = Integer.parseInt((String) parMap.get(CURRENTPAGE));
			pageSize = Integer.parseInt((String) parMap.get(PAGESIZE));
		}
		return new PageBounds(currentPage,pageSize);
	}


	/**
	 * @Comments ：包装分页查询结果
	 * @param list
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年6月3日 上午11:12:49
	 */
	public static Map<String, Object> pageListToMap(List<Map<String, Object>> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (list instanceof PageList) {
			Paginator paginator = ((PageList<Map<String, Object>>) list).getPaginator();
			map.put(TOTAL, paginator.getTotalCount());
		}
		map.put(ROWS, list);
		return map;
	}

	/**
	 * 将查询结果转换成前台select标签所需的数据模型。
	 * 
	 * @param resultList
	 * @param allowEmpty
	 * @param fromKey
	 * @param fromValue
	 * @param toKey
	 * @param toValue
	 * @return
	 */
	public static List<Map<String, Object>> toSelectDataModel(List<Map<String, Object>> resultList, boolean allowEmpty,
			String fromKey, String fromValue, String toKey, String toValue) {
		List<Map<String, Object>> resultListTemp = new ArrayList<Map<String, Object>>();

		if (allowEmpty) {
			Map<String, Object> nullMap = new HashMap<String, Object>();
			nullMap.put(toKey, "");
			nullMap.put(toValue, "请选择...");
			resultListTemp.add(nullMap);
		}

		for (Map<String, Object> map : resultList) {
			Map<String, Object> mapTemp = new HashMap<String, Object>();
			mapTemp.put(toKey, map.get(fromKey));
			mapTemp.put(toValue, map.get(fromValue));
			resultListTemp.add(mapTemp);
		}

		return resultListTemp;
	}

	public static List<Map<String, Object>> toSelectDataModel(List<Map<String, Object>> resultList, String fromKey,
			String fromValue, String toKey, String toValue) {

		return toSelectDataModel(resultList, true, fromKey, fromValue, toKey, toValue);
	}

	public static List<Map<String, Object>> toSelectDataModel(List<Map<String, Object>> resultList, boolean allowEmpty,
			String key, String value) {

		return toSelectDataModel(resultList, allowEmpty, key, value, key, value);
	}

	public static List<Map<String, Object>> toSelectDataModel(List<Map<String, Object>> resultList, String key,
			String value) {

		return toSelectDataModel(resultList, true, key, value, key, value);
	}

}
