package com.cw.auction.framework.baseservice;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public abstract interface IBaseService {
	
	public List<Map<String, Object>> selectList_R(Map<String, Object> map);

	public List<Map<String, Object>> selectListByPage_R(Map<String, Object> map);

	public JSON insert_T(Map<String, Object> map);

	public JSON insertByBatch_T(List<?> list);

	public JSON update_T(Map<String, Object> map);

	public JSON delete_T(Map<String, Object> map);

	public JSON deleteByBatch_T(String ids);

	public JSON selectById_R(Map<String, Object> map);

	public int selectCount_R(Map<String, Object> map);

	

}
