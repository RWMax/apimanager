package com.yinhai.api.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import com.yinhai.api.common.domain.Project;

public class HibernateValidatorTest {

	@Test
	public void test1() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Project project = new Project();
		Set<ConstraintViolation<Project>> violations = validator.validate(project);
		for (ConstraintViolation<Project> constraintViolation : violations) {    
            System.out.println("对象属性:"+constraintViolation.getPropertyPath()); 
            System.out.println("国际化key:"+constraintViolation.getMessageTemplate()); 
            System.out.println("错误信息:"+constraintViolation.getMessage()); 
        }
	}
}
