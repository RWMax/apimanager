package com.yinhai.api.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.api.common.domain.InterfaceWS;
import com.yinhai.api.common.domain.InterfaceWithBLOBs;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.common.XMLUtil;
import com.yinhai.ec.util.CXFClientUtil;
import com.yinhai.ec.util.HttpUtils;
import com.yinhai.ec.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @package com.yinhai.api.util
* <p>Title: RequestHelper.java</p>
* <p>请求助手</p>
* @author 刘惠涛
* @date 2016年12月8日 下午4:46:06
 */
@SuppressWarnings("unchecked")
public class RequestHelper {
	private static final Logger LOG = LoggerFactory.getLogger(RequestHelper.class);
	/**
	 * 发起请求时,判断URL是否有效
	 * @return
     */
	public static boolean isUrlEnable(String url) {
		boolean flag = false;
		if (url == null || "".equals(url)) {
			return false;
		}
		try {
			HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
			int state = connection.getResponseCode();
			if(state == 200){
				flag = true;
			}
		} catch (IOException e) {
			LOG.debug("URL <"+url+">,is not enable;{}",e);
		}
		return flag;
	}

	/**
	 * 发起Webservice请求
	  * @return Object 
	  * @author 刘惠涛
	 */
	public static Object handleWebserviceRequest(String url, InterfaceWS ws, PageParam pageParam,
			InterfaceWithBLOBs api) {
		if(!isUrlEnable(url)){
			throw new RuntimeException("接口请求失败: 不是有效连接");
		}
		String paramString = null;
		if(api.getContenttype().equals(ContentType.APPLICATION_JSON.getMimeType())){
			paramString = JSONObject.toJSONString(pageParam);
			return CXFClientUtil.invokeMethod(url, ws.getMethodname(), paramString)[0];
		}else if(api.getContenttype().equals(ContentType.APPLICATION_XML.getMimeType())){
			paramString = XMLUtil.map2xmlBody(new LinkedHashMap<String, Object>(pageParam.getMap()), api.getRequestrootelement());
			return CXFClientUtil.invokeMethod(url, ws.getMethodname(), paramString)[0];
		}else if(api.getContenttype().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())){
			Map<String, Object> map = pageParam.getMap();
			Object[] strings = new Object[map.size()];
			int i = 0;
			for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				strings[i] = map.get(name);
				i++;
			}
			return CXFClientUtil.invokeMethod(url, ws.getMethodname(), strings)[0];
		}
		return null;
	}
	
	/**
	 * 发起http请求
	  * @return Object 
	  * @author 刘惠涛
	 */
	public static ResponseBody handleHttpRequest(String url, PageParam pageParam, InterfaceWithBLOBs api) {
		if(!isUrlEnable(url)){
			throw new RuntimeException("接口请求失败: 不是有效连接");
		}
		DefaultResponseCallBack callBack = new RequestHelper().new DefaultResponseCallBack();
		String hs = api.getRequestheaders();
		JSONArray requestheaders = JSONArray.parseArray(hs);
		List<Header> headers = null;
		if(requestheaders != null && !requestheaders.isEmpty()){
			headers = new ArrayList<Header>();
			for (int i = 0; i < requestheaders.size(); i++) {
				JSONObject jsonObject = (JSONObject) requestheaders.get(i);
				if(!StringUtils.isEmpty(jsonObject.get("paramName")) && !StringUtils.isEmpty(jsonObject.get("defaultValue"))){
					Header header = new BasicHeader(jsonObject.getString("paramName"), jsonObject.getString("defaultValue"));
					headers.add(header);
				}
			}
		}
		if ("GET".equals(api.getRequestmethod())) {
			HttpUtils.get(url, pageParam, callBack, null, headers);
		}else if("POST".equals(api.getRequestmethod())){
			if(api.getContenttype().equals(ContentType.APPLICATION_JSON.getMimeType()) 
					|| api.getContenttype().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())){
				HttpUtils.post(url, JSONObject.toJSONString(pageParam), callBack ,ContentType.create(api.getContenttype(), HttpUtils.UTF_8), headers);
			} else {
				HttpUtils.post(url, XMLUtil.map2xmlBody(new LinkedHashMap<String,Object>(pageParam.getMap()), api.getRequestrootelement()), callBack ,ContentType.create(api.getContenttype(), HttpUtils.UTF_8), headers);
			}
		}else if("PUT".equals(api.getRequestmethod())){
			HttpUtils.put(url, JSONObject.toJSONString(pageParam), callBack, null, headers);
		}else if("DELETE".equals(api.getRequestmethod())){
			HttpUtils.delete(url, pageParam, callBack, null, headers);
		}
		
		return callBack.getResponseBody();
	}
	
	class DefaultResponseCallBack implements HttpUtils.ResponseCallback{
		private ResponseBody responseBody = new ResponseBody();
		
		@Override
		public void onResponse(int resultCode, String resultJson) {
			responseBody.setResultCode(resultCode);
			responseBody.setResultString(resultJson);
		}
		
		public ResponseBody getResponseBody() {
			return responseBody;
		}
	}
	
	public class ResponseBody {
		int resultCode;
		String resultString;
		
		public int getResultCode() {
			return resultCode;
		}
		public void setResultCode(int resultCode) {
			this.resultCode = resultCode;
		}
		public String getResultString() {
			return resultString;
		}
		public void setResultString(String resultString) {
			this.resultString = resultString;
		}
	}
}
