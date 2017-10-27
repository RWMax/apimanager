package com.liuht.api.test;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.liuht.ec.util.HttpUtils;

public class ApiMockTest {
	@Test
	public void testXMl() {
		String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
				"<RequestRegDeptByRegLevel><TransCode>0083</TransCode><DeptCode></DeptCode><Start>1</Start><RegDate>2013-12-27</RegDate><RequestQty>16</RequestQty><ReglevlCode></ReglevlCode><DoctCode></DoctCode><DisTrict></DisTrict><SchemaType></SchemaType></RequestRegDeptByRegLevel>";
		String url = "http://localhost:8088/apimanager/mock/api/12";
		HttpUtils.post(url, param, new HttpUtils.ResponseCallback() {
			@Override
			public void onResponse(int resultCode, String resultJson) {
				System.out.println("===================="+resultJson+"==================");
			}
		}, ContentType.APPLICATION_XML, null);
	}
	
	@Test
	public void testJSON() {
		String url = "http://localhost:8088/apimanager/mock/api/10";
		HttpUtils.post(url, null, new HttpUtils.ResponseCallback() {
			@Override
			public void onResponse(int resultCode, String resultJson) {
				System.out.println("===================="+resultJson+"==================");
			}
		}, ContentType.APPLICATION_JSON, null);
	}
}
