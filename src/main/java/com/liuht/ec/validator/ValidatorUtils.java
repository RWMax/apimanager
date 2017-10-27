package com.liuht.ec.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.util.CollectionUtils;

public class ValidatorUtils {
	private static Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();
	
	/**
	 * 检验bean
	  * @return ValidatorResult 
	  * @author 刘惠涛
	 */
	public static ValidatorResult validateEntity(Object object) {
		ValidatorResult result = new ValidatorResult();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		if(!CollectionUtils.isEmpty(constraintViolations) ){
			result.setHasErrors(true);
			Map<String,String> errorMsg = new HashMap<String,String>();
			for(ConstraintViolation<Object> cv : constraintViolations){
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsgs(errorMsg);
		}
		return result;
	}
	
	/**
	 * 校验单个属性
	  * @return ValidatorResult 
	  * @author 刘惠涛
	 */
	public static ValidatorResult validateFiled(Object object, String fieldName) {
		ValidatorResult result = new ValidatorResult();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(object, fieldName);
		if(!CollectionUtils.isEmpty(constraintViolations) ){
			result.setHasErrors(true);
			Map<String,String> errorMsg = new HashMap<String,String>();
			for(ConstraintViolation<Object> cv : constraintViolations){
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsgs(errorMsg);
		}
		return result;
	}
}
