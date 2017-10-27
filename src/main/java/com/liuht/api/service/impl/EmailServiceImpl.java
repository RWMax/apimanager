package com.liuht.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.liuht.api.service.EmailService;
import com.liuht.ec.base.exception.BaseAppException;
import com.liuht.ec.base.service.impl.BaseServiceImpl;
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class EmailServiceImpl extends BaseServiceImpl implements  EmailService{

	@Resource
	private JavaMailSenderImpl javaMailSender;
	@Resource
	private  VelocityEngine velocityEngine;
	
	@Override
	public void sendEmail(String user, String subject,Map model) {
		try {
			 String templateLocation = "config/email/find_password.vm";  
			 String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation,"UTF-8", model);  
			 MimeMessage message = javaMailSender.createMimeMessage();  
	         MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");// 处理中文编码  
	         helper.setSubject(subject);           
	         helper.setFrom(javaMailSender.getUsername());                                            
	         helper.setTo(user);                                                
	         helper.setText(text, true);  
	        javaMailSender.send(message);  
		} catch (Exception e) {
			throw new BaseAppException("邮件发送失败,请重试");
		}
	}

}
