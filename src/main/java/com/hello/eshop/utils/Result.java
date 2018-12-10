package com.hello.eshop.utils;

import java.util.HashMap;
import java.util.Map;

import com.hello.eshop.constant.Constant;

/**
 * 通用的返回的类
 * @author Hello
 *
 */
public class Result {
	
	// 状态码	100-成功	200-失败
	private Integer code;
	// 提示信息
	private String msg;
	// 用户要返回给浏览器的数据
	private Map<String, Object> data = new HashMap<String, Object>();

	
	/**
	 * 封装好的的成功信息
	 * @return
	 */
	public static Result success(){
		Result result = new Result();
		result.setCode(Constant.RESULT_SUCCESS);
		result.setMsg("处理成功");
		return result;
	}
	/**
	 * 封装好的失败信息
	 * @return
	 */
	public static Result fail(){
		Result result = new Result();
		result.setCode(Constant.RESULT_FAIL);
		result.setMsg("处理失败");
		return result;
	}
	
	/**
	 * 添加数据
	 * @param key
	 * @param value
	 * @return
	 */
	public Result add(String key,Object value){
		this.getData().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public Result() {
		super();
	}
	
	public Result(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public Result(Integer code, String msg, Map<String, Object> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
}
