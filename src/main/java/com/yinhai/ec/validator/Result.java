package com.yinhai.ec.validator;

import java.util.Map;

public interface Result {
	
	/**
	 * 随机返回一个错误信息
	  * @return String 
	  * @author 刘惠涛
	 */
	String getFieldError();
	
	/**
	 * 返回所有错误信息
	  * @return Map<String,String> 
	  * @author 刘惠涛
	 */
	Map<String,String> getFieldErrors();
	
	/**
	 * 返回指定prop的错误信息
	  * @return String 
	  * @author 刘惠涛
	 */
	String getFieldError(String filedName);
}
