package com.liuht.ec.util;

import java.util.Arrays;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liuht.ec.base.exception.BaseAppException;

public class CXFClientUtil {
	private static final Logger LOG = LoggerFactory.getLogger(CXFClientUtil.class);
	/**
	 * 动态调用cxf webservice
	  * @return Object[] 
	  * @author 刘惠涛
	 */
	public static Object[] invokeMethod(String url,String methodName, Object ... param) {
		try {
			JaxWsDynamicClientFactory dynamicClientFactory = JaxWsDynamicClientFactory.newInstance();
			Client client = dynamicClientFactory.createClient(url);
			return client.invoke(methodName, param);
		} catch (ServiceConstructionException e) {
			LOG.error("调用webservice: "+url+" 方法: "+methodName+" 参数: "+ Arrays.toString(param)+" 失败. {}",e.getMessage());
			throw new BaseAppException("创建service失败,请检查WSDL地址!");
		} catch (Exception e) {
			LOG.error("调用webservice: "+url+" 方法: "+methodName+" 参数: "+ Arrays.toString(param)+" 失败. {}",e.getMessage());
			throw new BaseAppException("未知错误");
		}
	}
	
}
