package com.cw.auction.framework.basecontroller;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;

public interface IBaseController {

	public JSON selectList(HttpServletRequest request);

	public JSON selectListByPage(HttpServletRequest request);

	public JSON insert(HttpServletRequest request);

	public JSON insertByBatch(HttpServletRequest request);

	public JSON update(HttpServletRequest request);

	public JSON delete(HttpServletRequest request);

	public JSON deleteByBatch(HttpServletRequest request);

	public JSON selectById(HttpServletRequest request);

}
