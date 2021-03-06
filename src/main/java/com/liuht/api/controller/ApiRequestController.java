package com.liuht.api.controller;

import com.liuht.api.common.domain.InterfaceWS;
import com.liuht.api.common.domain.InterfaceWithBLOBs;
import com.liuht.api.service.ApiService;
import com.liuht.api.util.RequestHelper;
import com.liuht.ec.base.controller.BaseController;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/req")
public class ApiRequestController extends BaseController{
	@Autowired
	private ApiService apiService;

	/**
	 * 发起请求
	  * @return Object 
	  * @author 刘惠涛
	 */
	@RequestMapping("/send")
	public Object send(HttpServletRequest request) {
		PageParam pageParam = getPageParam(request);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		try {
			String url = pageParam.getString("requestUrl");
			String id = pageParam.getString("id");
			pageParam.remove("id");
			pageParam.remove("requestUrl");
			InterfaceWithBLOBs api = apiService.getApiByID(Integer.valueOf(id));
			if(api.getProtocol().equals(HYConst.Protocol.HTTP.getName())){
				// http请求
				RequestHelper.ResponseBody ResponseBody = RequestHelper.handleHttpRequest(url,pageParam,api);
				result.put("result", ResponseBody);
			}else if(api.getProtocol().equals(HYConst.Protocol.HTTPS.getName())) {
				// https 请求
			}else if(api.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())){
				// webservice请求
				InterfaceWS ws = apiService.getApiWsByApiid(api.getId());
				Object resultObject = RequestHelper.handleWebserviceRequest(url,ws,pageParam,api);
				result.put("result", resultObject);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("error_msg", e.getMessage() == null? "接口请求失败: 未知错误":"接口请求失败: "+e.getMessage());
			getLogger(getClass()).error("接口请求失败: {}",e.getMessage() == null? e:"接口请求失败: "+e.getMessage());
		}
		
		return result;
	}
}
