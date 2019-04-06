package com.cw.auction.framework.basecontroller.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.cw.auction.framework.basecontroller.IBaseController;
import com.cw.auction.framework.baseservice.IBaseService;
import com.cw.auction.util.common.JsonResult;
import com.cw.auction.util.common.PageUtil;

public abstract class BaseControllerImpl implements IBaseController {

	/*-----跳转到指定页面-------*/
	public final static String URL_PAGE = "/page";

	public final static String URL_SELECTLIST = "/selectList";
	
	public final static String URL_SELECTLIST_TREE = "/selectListTree";

	public final static String URL_SELECTlIST_BYPAGE = "/selectListByPage";

	public final static String URL_INSERT = "/insert";

	public final static String URL_INSERT_BYBATCH = "/insertByBatch";

	public final static String URL_UPDATE = "/update";

	public final static String URL_UPDATE_BYBATCH = "/updateByBatch";

	public final static String URL_DELETE = "/delete";

	public final static String URL_DELETE_BYBATCH = "/deleteByBatch";

	public final static String URL_SELECTBYID = "/selectById";

	public abstract IBaseService getBaseService();
	

	public JSON selectList(HttpServletRequest request) {
		
		List<Map<String, Object>> list = this.getBaseService().selectList_R(PageUtil.getFormParameter(request));
		return JsonResult.listToJson(list);
	}

	public JSON selectListByPage(HttpServletRequest request) {
		
		Map<String, Object> resultMap =  PageUtil.pageListToMap(
				this.getBaseService().selectListByPage_R(PageUtil.getPageParameter(request))); 
		return JsonResult.mapToJson(resultMap);
	}

	public JSON insert(HttpServletRequest request) {

		return this.getBaseService().insert_T(PageUtil.getFormParameter(request));
	}

	public JSON insertByBatch(HttpServletRequest request) {

		return null;
	}

	public JSON update(HttpServletRequest request) {

		return this.getBaseService().update_T(PageUtil.getFormParameter(request));
	}

	public JSON delete(HttpServletRequest request) {

		return this.getBaseService().delete_T(PageUtil.getFormParameter(request));
	}

	public JSON deleteByBatch(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		return this.getBaseService().deleteByBatch_T(ids);
	}

	public JSON selectById(HttpServletRequest request) {
		return this.getBaseService().selectById_R(PageUtil.getFormParameter(request));
	}

}
