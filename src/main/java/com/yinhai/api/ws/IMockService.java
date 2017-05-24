package com.yinhai.api.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.annotations.WSDLDocumentation;

/**
* @package com.yinhai.api.ws
* <p>Title: IMockService.java</p>
* <p>模拟webservice请求</p>
* @author 刘惠涛
* @date 2016年12月16日 下午4:49:32
 */
@WebService
@WSDLDocumentation(value = "Webservice接口模拟请求",placement = WSDLDocumentation.Placement.TOP)
public interface IMockService {
	@WebMethod()
	@WSDLDocumentation("模拟JSON参数的Webservice接口")
	public String mockJSON(@WebParam(name="apiId") Integer apiId, @WebParam(name="jsonString") String jsonString);
	
	@WebMethod()
	@WSDLDocumentation("模拟XML参数的Webservice接口")
	public String mockXML(@WebParam(name="apiId") Integer apiId, @WebParam(name="xmlString") String xmlString);

	@WebMethod()
	@WSDLDocumentation("模拟请求数据类型为application/x-www-form-urlencoded的Webservice接口,(此种方式暂时不验证参数,只返回预期数据)")
	public String mockURLENCODED(@WebParam(name="apiId") Integer apiId);
}
