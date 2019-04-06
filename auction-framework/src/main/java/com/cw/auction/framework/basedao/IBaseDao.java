package com.cw.auction.framework.basedao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

/**
 * 通用dao接口
 * @author Administrator
 *
 */
public abstract interface IBaseDao {

	public List<Map<String, Object>> selectList(Map<String, Object> map);

	public List<Map<String, Object>> selectListByPage(Map<String, Object> map, RowBounds pageBounds);

	public int insert(Map<String, Object> map);

	public int insertByBatch(List<?> list);

	public int update(Map<String, Object> map);

	public int delete(Map<String, Object> map);

	public int deleteByBatch(List<?> list);

	public Map<String, Object> selectById(Map<String, Object> map);

	public int selectCount(Map<String, Object> map);

}
