package com.liuht.api.controller;

import com.liuht.api.common.domain.*;
import com.liuht.api.service.ApiService;
import com.liuht.api.service.EnvService;
import com.liuht.api.service.ProjectService;
import com.liuht.ec.base.controller.BaseController;
import com.liuht.ec.base.exception.BaseAppException;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.PageParam;
import com.liuht.ec.base.util.ResultBean;
import com.liuht.ec.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @package com.liuht.api.controller
* <p>Title: ApiController.java</p>
* <p>接口定义 接口分类</p>
* @author 刘惠涛
* @date 2016年11月24日 下午5:10:15
 */
@Controller
@RequestMapping("/api")
@SuppressWarnings({"rawtypes","unchecked"})
public class ApiController extends BaseController{
	@Autowired
	private ApiService apiService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EnvService envService;
	@RequestMapping("/folder/add")
	public String goAddFolder(@RequestParam(required=true,value="projectId") final String projectId, 
			@RequestParam(required=true,value="moduleId") final String moduleId,
			@RequestParam(required=false,value="folderId") final String folderId,ModelMap map) {
		if(folderId != null){
			InterfaceFolder folder = apiService.getFolderById(Integer.valueOf(folderId));
			map.put("folder", folder);
		}
		map.put("projectId", projectId);
		map.put("moduleId", moduleId);
		return "/api/add_folder.jsp";
	}
	
	@RequestMapping("/folder/save")
	@ResponseBody
	public ResultBean saveFolder(HttpServletRequest request) {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		try {
			apiService.saveFolder(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/folder/delete/{folderId}")
	@ResponseBody
	public ResultBean deleteFolder(@PathVariable final String folderId) {
		ResultBean bean = getResultBean();
		try {
			apiService.deleteFolder(Integer.valueOf(folderId));
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public ResultBean deleteApi(@PathVariable final String id) {
		ResultBean bean = getResultBean();
		try {
			apiService.deleteApiById(Integer.valueOf(id));
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 接口移动复制
	  * @return String 
	  * @author 刘惠涛
	 */
	@RequestMapping("/operation")
	public String apiOperation(@RequestParam final String operation, @RequestParam final Integer id, ModelMap map) {
		Interface api = apiService.getApiByID(id);
		List<Module> modules = projectService.queryModulesByProjectId(api.getProjectid());
		map.put("api", api);
		map.put("operation", operation);
		map.put("modules", modules);
		return "/api/api_operation.jsp";
	}
	
	@RequestMapping("/edit")
	public String editApi(HttpServletRequest request, ModelMap map) {
		PageParam pageParam = getPageParam(request);
		
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		Integer moduleId = Integer.valueOf(pageParam.getString("moduleId"));
		map.put("projectId", projectId);
		map.put("moduleId", moduleId);
		List<InterfaceFolder> folders = projectService.queryFoldersByModuleId(projectId, moduleId);
		map.put("folders", folders);
		
		if(!StringUtils.isEmpty(pageParam.get("folderId"))){
			Integer folderId = Integer.valueOf(pageParam.getString("folderId"));
			InterfaceFolder folder = apiService.getFolderById(folderId);
			map.put("folder", folder);
		}
		
		if(!StringUtils.isEmpty(pageParam.get("id"))){
			InterfaceWithBLOBs api = apiService.getApiByID(Integer.valueOf(pageParam.getString("id")));
			if(api != null){
				map.put("api", api);
				if(api.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())){
					InterfaceWS apiws = apiService.getApiWsByApiid(api.getId());
					if(apiws != null){
						map.put("apiws", apiws);
					}
				}
			}
		}
		
		return "/api/add_interface.jsp";
	}
	
	@RequestMapping("/view")
	public String showApi(HttpServletRequest request, ModelMap map) {
		PageParam pageParam = getPageParam(request);
		
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		Integer moduleId = Integer.valueOf(pageParam.getString("moduleId"));
		map.put("projectId", projectId);
		map.put("moduleId", moduleId);
		List<InterfaceFolder> folders = projectService.queryFoldersByModuleId(projectId, moduleId);
		map.put("folders", folders);
		
		if(!StringUtils.isEmpty(pageParam.get("folderId"))){
			Integer folderId = Integer.valueOf(pageParam.getString("folderId"));
			InterfaceFolder folder = apiService.getFolderById(folderId);
			map.put("folder", folder);
		}
		
		if(!StringUtils.isEmpty(pageParam.get("id"))){
			InterfaceWithBLOBs api = apiService.getApiByID(Integer.valueOf(pageParam.getString("id")));
			String url = api.getUrl();
			map.put("original_url",url);
			List list = projectService.getParamList(projectId);
			for(int i = 0,len = list.size(); i < len; i++){
				Map item = (Map)list.get(i);
				url = url.replaceAll("\\$"+item.get("paramName")+"\\$".toString(),item.get("paramValue").toString() );
			}
			api.setUrl(url);
			map.put("api", api);
			//查询环境名称
			List names = envService.getProjectEnvNames(projectId);
			map.put("names", names);
			
			InterfaceWS apiws = apiService.getApiWsByApiid(api.getId());
			if(apiws != null){
				map.put("apiws", apiws);
			}
			
			String ws_code = getWsCode();
			map.put("ws_code", ws_code);
		}
		return "/api/api_view.jsp";
	}
	
	private String getWsCode() {
		StringBuilder builder = new StringBuilder();
		builder.append("JaxWsDynamicClientFactory dynamicClientFactory = JaxWsDynamicClientFactory.newInstance();");
		builder.append("\r\n");
		builder.append("Client client = dynamicClientFactory.createClient(url);");
		builder.append("\r\n");
		builder.append("Object[] results = client.invoke(methodName, apiId, otherParams)");
		return builder.toString();
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object saveApi(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		PageParam pageParam = getPageParam(request);
		result.put("error", false);
		try {
			pageParam.put(HYConst.SESSION_USER, getCurrentSessionUser());
			Integer id = apiService.saveApi(pageParam);
			result.put("id", id);
			result.put("success_msg", "保存成功!");
		} catch (BaseAppException e) {
			result.put("error", true);
			result.put("error_msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 复制移动的保存
	  * @return Object 
	  * @author 刘惠涛
	 */
	@RequestMapping("/operation/save")
	@ResponseBody
	public Object operationSaveApi(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", false);
		PageParam pageParam = getPageParam(request);
		try {
			pageParam.put(HYConst.SESSION_USER, getCurrentSessionUser());
			Integer id = apiService.saveOperationApi(pageParam);
			result.put("id", id);
		} catch (BaseAppException e) {
			result.put("error", true);
			result.put("error_msg", e.getMessage());
		}
		result.put("success_msg", "操作成功!");
		return result;
	}
	
	@RequestMapping("/query/{folderId}")
	@ResponseBody
	public Object queryApisByFolderId(@PathVariable final String folderId) {
		List<Interface> apis = apiService.queryApisByFolderId(Integer.valueOf(folderId)); 
		return apis;
	}
	@RequestMapping("/getRealUrl")
	@ResponseBody
	public Map getRealUrl( HttpServletRequest request){
		PageParam param = getPageParam(request);
		Map map = new HashMap();
		List list = envService.getParamList(Integer.valueOf(param.get("envId").toString()));
		map.put("error", false);
		map.put("result", list);
		return map;
	}

	/**
	 * 跳转到接口说明文档页面
	 * @return
     */
	@RequestMapping("/document")
	public String document(@RequestParam(required=true,value="projectId") final Integer projectId,
                           @RequestParam(required=true,value="moduleId") final Integer moduleId, ModelMap map) {
        map.put("projectId", projectId);
        map.put("moduleId", moduleId);
        List<InterfaceFolder> folders = projectService.queryFoldersByModuleId(projectId, moduleId);
        map.put("folders", folders);
        map.put("document",true);
        return "/api/api_document.jsp";
	}

    /**
     * 保存接口说明文档
     * @return
     */
    @RequestMapping("/document/save")
    @ResponseBody
    public Object saveDocument(@RequestParam(required=true,value="projectId") final Integer projectId) {

        return null;
    }
}
