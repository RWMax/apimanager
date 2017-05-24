package com.yinhai.api.ws.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.api.common.domain.InterfaceWithBLOBs;
import com.yinhai.api.service.ApiService;
import com.yinhai.api.util.MockUtil;
import com.yinhai.api.ws.IMockService;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.common.XMLUtil;
@SuppressWarnings("unchecked")
public class MockService implements IMockService{

	private ApiService apiService;
	public void setApiService(ApiService apiService) {
		this.apiService = apiService;
	}

	@Override
	public String mockJSON(Integer apiId, String jsonString) {
		InterfaceWithBLOBs api = apiService.getApiByID(apiId);
		Map<String, Object> param = JSONObject.parseObject(jsonString);
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
		return MockUtil.handleResult(result, api);
	}

	
	@Override
	public String mockXML(Integer apiId, String xmlString) {
		InterfaceWithBLOBs api = apiService.getApiByID(apiId);
		Map<String, Object> param = XMLUtil.xmlBody2map(xmlString);
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
		return MockUtil.handleResult(result, api);
	}

	@Override
	public String mockURLENCODED(Integer apiId) {
		InterfaceWithBLOBs api = apiService.getApiByID(apiId);
		Map<String, Object> result = new HashMap<String, Object>();
		result = MockUtil.getResultBYResponseArgs(JSONArray.parseArray(api.getResponseargs()));
		// 此种方式暂时不验证参数
		return MockUtil.handleResult(result, api);
	}
}
