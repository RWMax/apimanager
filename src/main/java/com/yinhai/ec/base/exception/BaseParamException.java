package com.yinhai.ec.base.exception;  
  
/**
* @package com.yinhai.ec.base.exception
* <p>Title: BaseParamException.java</p>
* <p>Description: 业务参数验证失败异常</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 久远银海</p>
* @author LIUHUITAO
* @date 2016-1-20 上午9:24:06
* @version 1.0
 */
public class BaseParamException extends BaseAppException{

	private static final long serialVersionUID = -3423452244964698784L;
	private String errorMessage;// 错误信息
	private String filed;// 参数名
	
	public BaseParamException(){}
	
	public BaseParamException(String errorMessage){
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public BaseParamException(String filed,String errorMessage){
		super(errorMessage);
		this.filed = filed;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getMessage() {
		return errorMessage;
	}
	
	public String getFiled() {
		return filed;
	}
}
 