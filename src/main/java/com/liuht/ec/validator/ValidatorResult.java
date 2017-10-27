package com.liuht.ec.validator;

import java.util.Iterator;
import java.util.Map;

public class ValidatorResult implements Result{

	//校验结果是否有错
	private boolean hasErrors = false;
	//校验错误信息
	private Map<String,String> errorMsgs;
	
	public boolean isHasErrors() {
		return hasErrors;
	}
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	public Map<String, String> getErrorMsgs() {
		return errorMsgs;
	}
	public void setErrorMsgs(Map<String, String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	
	@Override
	public String toString() {
		return "ValidationResult [hasErrors=" + hasErrors + ", errorMsgs="
				+ errorMsgs + "]";
	}
	@Override
	public String getFieldError() {
		if(isHasErrors()){
			for (Iterator<String> iterator = errorMsgs.keySet().iterator(); iterator.hasNext();) {
				String field = iterator.next();
				return errorMsgs.get(field);
			}
		}
		return null;
	}
	@Override
	public Map<String, String> getFieldErrors() {
		if(isHasErrors()){
			return errorMsgs;
		}
		return null;
	}
	@Override
	public String getFieldError(String filedName) {
		if(isHasErrors()){
			for (Iterator<String> iterator = errorMsgs.keySet().iterator(); iterator.hasNext();) {
				String field = iterator.next();
				if(filedName.equals(field)){
					return errorMsgs.get(field);
				}
			}
		}
		return null;
	}
}
