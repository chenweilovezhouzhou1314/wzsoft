package com.cw.auction.framework.baseservice.impl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cw.auction.framework.basedao.IBaseDao;
import com.cw.auction.framework.baseservice.IBaseService;
import com.cw.auction.util.common.JsonResult;
import com.cw.auction.util.common.PageUtil;

public abstract class BaseServiceImpl implements IBaseService {

	public abstract IBaseDao getBaseDao();

	public abstract void beforeInsert(Map<String, Object> map);
	
	public abstract void beforeUpdate(Map<String, Object> map);
	
	public abstract void beforeDelete(Map<String, Object> map);
	
	
	
	
	

	public List<Map<String, Object>> selectList_R(Map<String, Object> map) {
		List<Map<String, Object>> list = this.getBaseDao().selectList(map);
		return list;
	}
	
	
	

	public List<Map<String, Object>> selectListByPage_R(Map<String, Object> map) {
		PageUtil.setParMap(map);
		List<Map<String, Object>> list = this.getBaseDao().selectListByPage(map, PageUtil.getPageBounds());
		return list;
	}

	public JSON insert_T(Map<String, Object> map) {
		try {
			beforeInsert(map);
			int result = this.getBaseDao().insert(map);
			if (result > 0) {
				return JsonResult.successAsJson("新增成功!");
			} else {
				return JsonResult.errorAsJson("新增失败!");
			}

		} catch (Exception e) {
			
			e.printStackTrace();
			return JsonResult.errorAsJson("新增失败!");
		}
	}

	public JSON insertByBatch_T(List<?> list) {
		

		return null;
	}

	public JSON update_T(Map<String, Object> map) {
		
		try {
			beforeUpdate(map);
			int result = this.getBaseDao().update(map);
			if (result > 0) {
				return JsonResult.successAsJson("修改成功!");
			} else {
				return JsonResult.errorAsJson("修改失败!");
			}

		} catch (Exception e) {
			
			e.printStackTrace();
			return JsonResult.errorAsJson("修改失败!");
		}

	}

	public JSON delete_T(Map<String, Object> map) {
		
		try {
			beforeDelete(map);
			int result = this.getBaseDao().delete(map);
			if (result > 0) {
				return JsonResult.successAsJson("删除成功!");
			} else {
				return JsonResult.errorAsJson("删除失败!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.errorAsJson("删除失败!");
		}
	}

	public JSON deleteByBatch_T(String ids) {
		try {
			List<?> list = Arrays.asList(
					 StringUtils.split(ids,","));
			int result = this.getBaseDao().deleteByBatch(list);
			if (result > 0) {
				return JsonResult.successAsJson("删除成功!");
			} else {
				return JsonResult.errorAsJson("删除失败!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResult.errorAsJson("删除失败!");
		}

	}

	public JSON selectById_R(Map<String, Object> map) {
		Map<String, Object> resultMap = this.getBaseDao().selectById(map);
		return JsonResult.mapToJson(resultMap) ;
	}

	
	
	public int selectCount_R(Map<String, Object> map) {
		return this.getBaseDao().selectCount(map);
	}

}
