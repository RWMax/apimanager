package com.yinhai.api.mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yinhai.api.common.domain.InterfaceWithBLOBs;
import com.yinhai.api.service.ApiService;
import com.yinhai.api.util.MockUtil;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.exception.BaseAppException;

/**
* @package com.yinhai.api.mock
* <p>Title: MockAPIController.java</p>
* <p>模拟各种借口</p>
* @author 刘惠涛
* @date 2016年12月8日 上午10:38:02
 */
@RestController
@RequestMapping("/mock")
public class MockAPIController extends BaseController{
	@Autowired
	private ApiService apiService;
	
	/**
	 * 动态模拟接口api,返回预期数据
	  * @return Object 
	  * @author 刘惠涛
	 */
	@RequestMapping("/api/{requestId}")
	public void mockAPI(@PathVariable final Integer requestId, HttpServletRequest request, HttpServletResponse response) {
		InterfaceWithBLOBs api = apiService.getApiByID(requestId);
		getLogger(getClass()).debug("后台接收到模拟接口请求,接口名称: "+api.getName() + " 接口URL: "+api.getUrl());
		// 根据contentType 获取参数
		Map<String, Object> param = MockUtil.getParam(api,request);
		// 获取参数成功,开始比对参数
		getLogger(getClass()).debug("后台接收到模拟接口请求,请求参数:{}",param);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			MockUtil.checkRequestArgs(api.getRequestargs(),param);
		} catch (BaseAppException e) {
			result.put("success", false);
			result.put("error_msg", e.getMessage());
		}
		
		if (result.isEmpty()) {
			// 参数验证通过
			// 开始准备返回数据
			result = MockUtil.getResultBYResponseArgs(JSONArray.parseArray(api.getResponseargs()));
		}
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			String resultString = MockUtil.handleResult(result, api);
			getLogger(getClass()).debug("模拟接口请求成功,接口名称:{},返回数据:{}",api.getName(),resultString);
			writer.print(resultString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
