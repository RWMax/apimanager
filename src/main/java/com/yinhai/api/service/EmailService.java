package com.yinhai.api.service;

import java.util.Map;
@SuppressWarnings("rawtypes")
public interface EmailService {

	public void sendEmail(String user,String subject ,Map model);
}
