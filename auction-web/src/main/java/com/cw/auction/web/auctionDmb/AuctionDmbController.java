package com.cw.auction.web.auctionDmb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cw.auction.framework.basecontroller.impl.BaseControllerImpl;
import com.cw.auction.framework.baseservice.IBaseService;
import com.cw.auction.service.auctionDmb.IAuctionDmbService;


/**
 * 
 * 系统代码表配置Controller类
 * 
 * @version 
 * <pre>
 * Author Administrator
 * Version 	1.0
 * Date  2019年04月05日
 * </pre>
 * @since 1.0
 */

@Controller
@RequestMapping("/AuctionDmbController")
public class AuctionDmbController extends BaseControllerImpl {
	
	
	@Autowired
	private IAuctionDmbService iAuctionDmbService;
	
	@Override
	public IBaseService getBaseService() {
		// TODO Auto-generated method stub
		return iAuctionDmbService;
	}
	
	/**
	 * 跳转到系统代码表配置Index页面
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_PAGE)
	public ModelAndView indexAuctionDmb(HttpServletRequest request) {
		return new ModelAndView("/auctionDmb/auctionDmb-Index");
	}
	
	/**
	 * 查询数据字典列表(分页)
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_SELECTlIST_BYPAGE)
	@ResponseBody
	public JSON selectAuctionDmbListByPage(HttpServletRequest request) {
		return  this.selectListByPage(request);
	}
	
	/**
	 * 查询系统代码表配置列表(不分页)
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_SELECTLIST)
	@ResponseBody
	public JSON selectAuctionDmbList(HttpServletRequest request) {
		return  this.selectList(request);
	}
	
	/**
	 * 查询系统代码表配置信息(通过Id)
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_SELECTBYID)
	@ResponseBody
	public JSON selectAuctionDmbById(HttpServletRequest request){
		return  this.selectById(request);
	}
	
	/**
	 * 插入系统代码表配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_INSERT)
	@ResponseBody
	public JSON insertAuctionDmb(HttpServletRequest request) {
		return  this.insert(request);
	}
	
	/**
	 * 删除系统代码表配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_DELETE)
	@ResponseBody
	public JSON deleteAuctionDmb(HttpServletRequest request) {
		return this.delete(request);
	}
	
	/**
	 * 删除系统代码表配置信息(批量)
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_DELETE_BYBATCH)
	@ResponseBody
	public JSON deleteAuctionDmbByBatch(HttpServletRequest request) {
		return this.deleteByBatch(request);
	}
	
	/**
	 * 更新系统代码表配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(BaseControllerImpl.URL_UPDATE)
	@ResponseBody
	public JSON updateAuctionDmb(HttpServletRequest request) {
		return this.update(request);
	}

}
