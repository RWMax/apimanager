package com.yinhai.ec.base.exception;  
  
/**
* @package com.yinhai.ec.base.exception
* <p>Title: BaseParamException.java</p>
* <p>Description: 业务更新失败异常</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 久远银海</p>
* @author LIUHUITAO
* @date 2016-1-20 上午9:24:06
* @version 1.0
 */
public class BaseUpdateException extends BaseAppException{

	private static final long serialVersionUID = -3423452244964698784L;
	private String errorMessage;// 错误信息
	private Integer updateCount;
	
	public BaseUpdateException(){}
	
	public BaseUpdateException(String errorMessage){
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public BaseUpdateException(Integer updateCount,String errorMessage){
		super(errorMessage);
		this.updateCount = updateCount;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getMessage() {
		return errorMessage;
	}
	
	public Integer getUpdateCount() {
		return updateCount;
	}
}
 