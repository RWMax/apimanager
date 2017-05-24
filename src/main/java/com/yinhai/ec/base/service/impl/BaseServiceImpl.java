package com.yinhai.ec.base.service.impl;  

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.yinhai.ec.base.service.BaseService;
  
public abstract class BaseServiceImpl implements BaseService{
	@Autowired
	protected SqlSessionTemplate sqlSession;
}
 