package com.cw.auction.util.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * fast json 工具类
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class JsonResult {

	// 操作成功
	private String success;

	// 操作失败
	private String error;

	// 消息
	private String msg = "";
	
	//数据无变更
	private String nochange;
	
    
	/**
	 * 操作成功
	 * @param msg
	 * @return
	 */
	public static JSON successAsJson(String msg) {
		JsonResult result = new JsonResult();
		result.setSuccess("success");
		result.setMsg(msg);
		return result.toJson();
	}
    /**
     * 操作失败
     * @param msg
     * @return
     */
	public static JSON errorAsJson(String msg) {
		JsonResult result = new JsonResult();
		result.setError("error");
		result.setMsg(msg);
		return result.toJson();
	}
	
	/**
	 * 数据无改变
	 * @param msg
	 * @return
	 */
	public static JSON noChangeAsJson(String msg) {
		JsonResult result = new JsonResult();
		result.setSuccess("success");
		result.setMsg(msg);
		result.setNochange("nochange");
		return result.toJson();
	}
	
	/**
	 * 空壳jsonresult
	 * @return
	 */
	public static JSON emptyJson(){
		JsonResult result = new JsonResult();
		return result.toJson();
	}
	
	
	/**
	 * 把map 转换成json
	 * @param list
	 * @return
	 */
	public static JSON mapToJson(Map map){
		JsonResult result = new JsonResult();
		return result.toJson(map);
	}
	/**
	 * 把listMap 转换成json
	 * @param list
	 * @return
	 */
	public static JSON listToJson(List list){
		JsonResult result = new JsonResult();
		return result.toJson(list);
	}
	
	/**
	 * list转换为json
	 * @param map
	 * @return
	 */
	public JSON toJson(List list) {
		return (JSON) JSON.toJSON(list);
	}
	
	
	/**
	 * map转换为json
	 * @param map
	 * @return
	 */
	public JSON toJson(Map map) {
		return (JSON) JSON.toJSON(map);
	}
	
	
    /**
     * 转换为json
     * @return
     */
	public JSON toJson() {
		return (JSON) JSON.toJSON(this);
	}
    
	/**
	 * 转换成str
	 * @return
	 */
	public String toJsonString() {
		return this.toJson().toJSONString();
	}
	
	
	
	

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getNochange() {
		return nochange;
	}
	public void setNochange(String nochange) {
		this.nochange = nochange;
	}

}
