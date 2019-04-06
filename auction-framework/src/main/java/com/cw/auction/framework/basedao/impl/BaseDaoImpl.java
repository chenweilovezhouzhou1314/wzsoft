package com.cw.auction.framework.basedao.impl;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.cw.auction.framework.basedao.IBaseDao;
import com.cw.auction.framework.baseentity.IBaseMapper;

/**
 * 通用dao
 * @author Administrator
 *
 */
public abstract class BaseDaoImpl implements IBaseDao {
	
	
	public abstract IBaseMapper getBaseMapper();
	

	public List<Map<String, Object>> selectList(Map<String, Object> map) {
		
		return this.getBaseMapper().selectList(map);
	}

	public List<Map<String, Object>> selectListByPage(Map<String, Object> map, RowBounds pageBounds) {
		
		return this.getBaseMapper().selectList(map, pageBounds);
	}

	public int insert(Map<String, Object> map) {
		
		return this.getBaseMapper().insert(map);
	}

	public int insertByBatch(List<?> list) {
		
		return this.getBaseMapper().insertByBatch(list);
	}

	public int update(Map<String, Object> map) {
		
		return this.getBaseMapper().update(map);
	}

	public int delete(Map<String, Object> map) {
		
		return this.getBaseMapper().delete(map);
	}

	public int deleteByBatch(List<?> list) {
		
		return this.getBaseMapper().deleteByBatch(list);
	}

	public Map<String, Object> selectById(Map<String, Object> map) {
		
		return this.getBaseMapper().selectById(map);
	}

	public int selectCount(Map<String, Object> map) {
		
		return this.getBaseMapper().selectCount(map);
	}

}
