package com.cw.auction.util.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class MapUtil {
	
	/**
	 * map对象转换成pojo
	 * BigDecimal和Date做了判断处理，特殊类型暂无法封装
	 * @param map
	 * @param pojo
	 * @return
	 */
	public static void mapToPojo(Map<String,Object> map,Object pojo){
		Class classType = pojo.getClass();		
			if(map != null){
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					try {
						Field field;
						field = classType.getDeclaredField(entry.getKey().toLowerCase());
						field.setAccessible(true);
						if(field.getType()==BigDecimal.class){
							field.set(pojo, new BigDecimal(entry.getValue().toString()));
						}else if(field.getType()==Date.class){
						field.set(pojo, DateUtil.parse(entry.getValue().toString()));
						}else{
							field.set(pojo, entry.getValue());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}					
	} 
	/**
	 * 重新构建Map
	 * @param map
	 * @return map
	 */
	public static Map<String,Object> reBuildMap(Map<String,Object> map){
			Map<String,Object> newMap = new HashMap<String,Object>();
			if(map != null){
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					try {
							Object str = entry.getValue();
							if(str instanceof String){
								if(((String)str).isEmpty()){
									continue;
								}
							}
							newMap.put(entry.getKey(),str);							
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}		
			return newMap;
	} 
	/**
	 * 将srcMap中的value和key放入targetMap中
	 * @param targetMap
	 * @param srcMap
	 */
	public static void addMapNoKey(Map<String ,Object> targetMap,Map<String,Object> srcMap){
		if(targetMap == null){
			targetMap = new HashMap<String,Object>();
		}
		if(srcMap != null){
			for (Map.Entry<String, Object> entry : srcMap.entrySet()) {
				try {
					Object str = entry.getValue();
					if(str instanceof String){
						if(((String)str).isEmpty()){
							continue;
						}
					}
					if(!targetMap.containsKey(entry.getKey())){
						targetMap.put(entry.getKey(),str);							
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
	}
	/**
	 * @Comments ：将map中的key变成小写
	 * @param targetMap
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 下午6:13:21
	 */
	public static Map<String ,Object> mapKeyLowwer(Map<String ,Object> targetMap){
		if(targetMap == null) return null;
		Map<String ,Object> nowMap = new HashMap<String,Object>();
		for (Map.Entry<String, Object> entry : targetMap.entrySet()) 
			nowMap.put(entry.getKey().toLowerCase(),entry.getValue());
		return nowMap;
	} 
	/**
	 * @Comments ：将要list(map)中的key小写
	 * @param list
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年5月30日 下午6:13:38
	 */
	public static List<Map<String ,Object>> listMapKeyLowwer(List<Map<String ,Object>> list){
		List<Map<String ,Object>> newList = new ArrayList<Map<String ,Object>>();
		if(list == null)  return null;
		for (Map<String, Object> targetMap : list) 
			  newList.add(mapKeyLowwer(targetMap));
		return newList;
	} 
	/**
	 * @Comments ：根据指定的key 创造新的list
	 * @param list
	 * @param str  ex: "id,text,state,iconCls,attributes" istMapKeyLowwer(list,"username,password")
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年6月2日 下午9:59:07
	 */
	public static List<Map<String ,Object>> listMapKeyLowwer(List<Map<String ,Object>> list,String str){
		List<Map<String ,Object>> newList = new ArrayList<Map<String ,Object>>();
		if(list == null || StringUtils.isBlank(str)){
			return null;
		} 
		String [] strArray = str.split(",");
		if(list.size()!=strArray.length){
			return null;
		}
		String [] newArray = new String[list.size() * strArray.length];
		int m = -1;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < strArray.length; j++) {
				m++;
				newArray[m] = strArray[j];
			}
		}
		Map<String ,Object> nowMap;
		int j = -1;
		for (int i = 0; i < list.size(); i++) {
			nowMap = new HashMap<String,Object>();
			for (Map.Entry<String, Object> entry : list.get(i).entrySet()) {
				j++;
				if(newArray[j].contains(":")){
					nowMap.put(newArray[j].substring(0,newArray[j].indexOf(":")),
							newArray[j].substring(newArray[j].indexOf(":")+1, newArray[j].length()));
				}else{
					nowMap.put(newArray[j], entry.getValue());
				}
				
			}
			newList.add(nowMap);
		}
		return newList;
	} 
	
	/**
	 * @Comments ：首条加空
	 * @param list
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年6月2日 上午9:51:54
	 */
	public static List<Map<String, Object>> dmListAddNull(List<Map<String ,Object>> list){
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		if(list == null) return null;
		Map<String, Object> nullMap = new HashMap<String,Object>();
		nullMap.put("DM", "");
		nullMap.put("MC", "请选择...");
		newList.add(nullMap);
		Map<String ,Object> nowMap = null;
		for (Map<String, Object> targetMap : list) {
			nowMap = new HashMap<String,Object>();
			for (Map.Entry<String, Object> entry : targetMap.entrySet()) {
				nowMap.put(entry.getKey(),entry.getValue());
			}	
			newList.add(nowMap);
		}
		return newList;
	} 
	
	/**
	 * @Comments ：比较两个map是否相等
	 * @param map1
	 * @param map2
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年6月4日 下午2:56:13
	 */
	public static boolean mapEquals(Map<String, Object> map1 ,Map<String, Object> map2){
		if(!map1.equals(map2)){
			return false;
		}
		return true;
	}
	/**
	 * @Comments ：把map中相同的key不同值数取出来封成Map
	 *             map1 跟 map2对比  取map1中不同的
	 * @param map1  当前map
	 * @param map2  对比对象
	 * @return
	 * @Author ：陈伟
	 * @Group : 项目部
	 * @Worker: 1699
	 * @Date ：2016年6月4日 下午2:56:28
	 */
	public static Map<String, Object> comparMap(Map<String, Object> map1 ,Map<String, Object> map2){
		Map<String, Object> newMap  = null;
		if(!mapEquals(map1, map2)){
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if(!StringUtils.equals((String)entry.getValue(), (String)map2.get(entry.getKey()))){
					newMap = new HashMap<String,Object>();
					newMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return newMap;
	}

}
