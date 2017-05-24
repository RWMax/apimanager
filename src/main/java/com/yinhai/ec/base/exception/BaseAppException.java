package com.yinhai.ec.base.exception;

public class BaseAppException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BaseAppException() {
	}
	
	public BaseAppException(String message) {
		super(message);
	}

	public BaseAppException(Throwable cause) {
		super(cause);
	}

	public BaseAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
