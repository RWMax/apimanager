package com.yinhai.api.test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Test;

import com.yinhai.ec.common.XMLUtil;
import com.yinhai.ec.util.CXFClientUtil;

public class ApiWsMockTest {

	@Test
	public void testXMl() throws Exception {
		String url = "http://localhost:8088/apimanager/services/mockWS?wsdl";
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", "1111");
		Object[] results = CXFClientUtil.invokeMethod(url, "mockXML", 11, XMLUtil.map2xmlBody(map, "xml"));
		System.out.println(Arrays.toString(results));
	}
	
	@Test
	public void testJSON() throws Exception {
		String url = "http://localhost:8088/apimanager/services/mockWS?wsdl";
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", "1111");
		Object[] results = CXFClientUtil.invokeMethod(url, "mockJSON", 10, null);
		System.out.println(Arrays.toString(results));
	}
}
