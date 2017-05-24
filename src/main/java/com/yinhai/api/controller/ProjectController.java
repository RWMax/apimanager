package com.yinhai.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.api.common.domain.*;
import com.yinhai.api.service.ApiService;
import com.yinhai.api.service.EnvService;
import com.yinhai.api.service.ProjectService;
import com.yinhai.api.service.TeamService;
import com.yinhai.api.util.ExportPdf;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @package com.yinhai.api.controller
* <p>Title: ProjectController.java</p>
* <p>项目 项目模块 项目成员</p>
* @author 刘惠涛
* @date 2016年11月24日 下午5:11:08
 */
@Controller
@RequestMapping("/project")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProjectController extends BaseController{
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private EnvService envService;
	@Autowired
	private ApiService apiService;
	
	/**
	 * 项目首页
	  * @return String 
	  * @author 刘惠涛
	 */
	@RequestMapping("/item{projectId}")
	public String showPro(@PathVariable final String projectId, ModelMap map,HttpServletRequest request) {
		Project project = projectService.getProjectByID(Integer.valueOf(projectId));
		map.put("project", project);
		
		List<Map<String, Object>> myProjects = projectService.getMyProjects(getUserId());
		List<Map<String, Object>> teamProjects = projectService.getMyTeamProjects(getUserId());
		myProjects.addAll(teamProjects);
		map.put("myProjects", myProjects);
		
		// 查询当前项目人员 如果当前登陆人员是项目成员的话
		Integer userId = getUserId();
		ProjectUser projectUser = projectService.queryProjectUser(Integer.valueOf(projectId), userId);
		map.put("projectUser", projectUser);
		
		//查询默认环境
		PageParam param = new PageParam();
		param.put("projectId", projectId);
		request.setAttribute("env", projectService.queryDefaultEnv(param));
		return "/api/pro_index.jsp";
	}
	
	/**
	 * 项目成员管理
	  * @return String 
	  * @author 刘惠涛
	 */
	@RequestMapping("/user/manager/{projectId}")
	public String userManager(@PathVariable final String projectId, ModelMap map) {
		List<Map<String, Object>> projectUsers = projectService.queryProUsers(Integer.valueOf(projectId));
		map.put("projectUsers", projectUsers);
		Project project = projectService.getProjectByID(Integer.valueOf(projectId));
		map.put("project", project);
		return "/api/pro_userManager.jsp";
	}
	
	/**
	 * 从项目移除人员
	  * @return ResultBean 
	  * @author 刘惠涛
	 */
	@RequestMapping("/user/remove")
	@ResponseBody
	public ResultBean userRemove(HttpServletRequest request) {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		pageParam.put("loginId", getUserId());
		try {
			projectService.removeProUser(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 项目转让
	  * @return ResultBean 
	  * @author 刘惠涛
	 */
	@RequestMapping("/user/exchange")
	@ResponseBody
	public ResultBean userExchange(HttpServletRequest request) {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		pageParam.put("loginId", getUserId());
		try {
			projectService.exchangeProUser(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 提升为团队项目成员
	  * @return ResultBean 
	  * @author 刘惠涛
	 */
	@RequestMapping("/user/improve")
	@ResponseBody
	public ResultBean userImprove(HttpServletRequest request) {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		try {
			projectService.improveProUser(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 邮箱邀请
	  * @return ResultBean 
	  * @author 刘惠涛
	 */
	@RequestMapping("/user/add/normal_email")
	@ResponseBody
	public ResultBean addNormalUser(HttpServletRequest request) {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		try {
			projectService.addNormalUser(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 团队邀请
	  * @return ResultBean 
	  * @author 刘惠涛
	 */
	@RequestMapping("/user/add/team_email")
	@ResponseBody
	public ResultBean addTeamUser(HttpServletRequest request) {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		try {
			projectService.addTeamUser(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 查询项目模块
	  * @return Object 
	  * @author 刘惠涛
	 */
	@RequestMapping("/{projectId}/modules")
	@ResponseBody
	public Object queryModule(@PathVariable final String projectId) {
		List<Module> modules = projectService.queryModulesByProjectId(Integer.valueOf(projectId));
		
		return modules;
	}
	
	/**
	 * 查询模块分类
	  * @return Object 
	  * @author 刘惠涛
	 */
	@RequestMapping("/{projectId}/{moduleId}/folders")
	@ResponseBody
	public Object queryFolders(@PathVariable final String projectId, 
			@PathVariable final String moduleId) {
		List<InterfaceFolder> folders = projectService.queryFoldersByModuleId(Integer.valueOf(projectId),Integer.valueOf(moduleId));
		return folders;
	}
	
	@RequestMapping("/goAdd")
	public String goAdd(HttpServletRequest request, ModelMap map) {
		String projectId = request.getParameter("projectId");
		if(!StringUtils.isEmpty(projectId)){
			Project project = projectService.getProjectByID(Integer.valueOf(projectId));
			map.put("project", project);
		}
		map.put("teams", teamService.getMyTeams(getUserId()));
		return "/api/add_project.jsp";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ResultBean addProject(@Valid Project project, BindingResult errors) {
		ResultBean bean = getResultBean();
		if(errors.hasErrors()){
			bean.setError(true);
			bean.setError_msg(errors.getFieldError().getDefaultMessage());
			return bean;
		}
		try {
			project.setUserid(getUserId());
			projectService.addProject(project);
			bean.setSuccess_msg("操作成功!");
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/module/add")
	public String addModule(@RequestParam(required=true,value="projectId") final String projectId, 
			@RequestParam(required=false,value="moduleId") final String moduleId, ModelMap map) {
		if(moduleId != null){
			Module module = projectService.getModuleById(Integer.valueOf(moduleId));
			map.put("module", module);
		}
		map.put("projectId", projectId);
		return "/api/add_module.jsp";
	}
	
	@RequestMapping("/module/save")
	@ResponseBody
	public ResultBean saveModule(HttpServletRequest request) {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		try {
			projectService.saveModule(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/module/delete")
	@ResponseBody
	public ResultBean deleteModule(@RequestParam(required=true,value="projectId") final String projectId, 
			@RequestParam(required=true,value="moduleId") final String moduleId) {
		ResultBean bean = getResultBean();
		try {
			projectService.deleteModule(Integer.valueOf(projectId), Integer.valueOf(moduleId));
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * 删除项目
	  * @return ResultBean 
	  * @author 刘惠涛
	 */
	@RequestMapping("/delete/{projectId}")
	@ResponseBody
	public ResultBean deleteProject(@PathVariable final Integer projectId) {
		ResultBean bean = getResultBean();
		try {
			projectService.deleteProject(projectId);
			bean.setSuccess_msg("已成功删除项目!");
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
	@RequestMapping("/exportPdf")
	public void exportPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam pageParam = getPageParam(request);
		try {
			int projectId = Integer.parseInt(pageParam.get("projectId") + "");
			Project project = projectService.getProjectByID(projectId);
			List<ApiDocument> list = new ArrayList<>();
			ApiDocument apiDocument = new ApiDocument();

			List<Environments> environments = envService.getProjectEnvironments(projectId);
			for (Environments e:environments){
				e.setItems(envService.getEnvironmentLists(e.getId()));
			}
			apiDocument.setEnvironments(environments);
			List<Module> modules = projectService.queryModulesByProjectId(projectId);
			for(Module module:modules){
				List<InterfaceFolder> folders = projectService.queryFoldersByModuleId(projectId, module.getId());
				for(InterfaceFolder folder:folders){
					List<InterfaceWithBLOBs> interfaces = apiService.queryApiBlobsByFolderId(folder.getId());
					folder.setInterfaces(interfaces);
				}
				module.setFolders(folders);
			}
			apiDocument.setModules(modules);

			list.add(apiDocument);
			ExportPdf<ApiDocument> pdfUtil = new ExportPdf<ApiDocument>();
			response.setHeader("Content-disposition", "attachment; filename=" + new String((project.getName()+".pdf").getBytes("gb2312"), "ISO8859-1"));// 设定输出文件头
			pdfUtil.exportPdf(project.getName(),list, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/exportJson")
	public void exportJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam pageParam = getPageParam(request);
		try {
			Map<String, Object> map = new HashMap<>();
			int projectId = Integer.parseInt(pageParam.get("projectId") + "");
			Map<String, Object> projectMap = projectService.getProjectMap(projectId);
			map.put("project", buildProjectMap(projectMap, projectId));
			String content = JSONObject.toJSON(map).toString();
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((projectMap.get("name") + ".json").getBytes("gb2312"), "ISO8859-1"));// 设定输出文件头
			exportJson(response.getOutputStream(), content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取项目相关信息的map
	 *
	 * @param projectMap
	 * @param projectId
	 * @return
	 */
	private Map<String, Object> buildProjectMap(Map<String, Object> projectMap, int projectId) {
		List<Map<String, Object>> evList = envService.getProjectEnvNames(projectId);
		for (Map<String, Object> env : evList) {
			int envId = Integer.parseInt(env.get("id") + "");
			List<Map<String, Object>> paramList = envService.getParamList(envId);
			env.put("items", paramList);
		}
		projectMap.put("environments", evList);
		List<Map<String, Object>> modules = projectService.queryModules(projectId);
		for (Map<String, Object> module : modules) {
			int moduleId = Integer.parseInt(module.get("id") + "");
			List<Map<String, Object>> folders = projectService.queryFoldersList(projectId, moduleId);
			for (Map<String, Object> folder : folders) {
				int folderId = Integer.parseInt(folder.get("id") + "");
				List<Map<String, Object>> interfaces = apiService.queryApiBlobList(folderId);
				for (Map<String, Object> inter : interfaces) {
					if ("WEBSERVICE".equals(inter.get("protocol"))) {
						InterfaceWS ws = apiService.getApiWsByApiid(Integer.parseInt(inter.get("id") + ""));
						inter.put("interfaceWS", ws);
					}
				}
				folder.put("interfaces", interfaces);
			}
			module.put("folders", folders);
		}
		projectMap.put("modules", modules);
		projectMap.put("users", projectService.queryProjectUsers(projectId));
		return projectMap;
	}

	/**
	 * 导出Json文件
	 *
	 * @param out
	 * @param content
	 */
	private void exportJson(OutputStream out, String content) {
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		write.append(content);
		buff = new BufferedOutputStream(out);
		try {
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/importFile")
	@ResponseBody
	public Object importFile(MultipartFile file, MultipartHttpServletRequest request) {
		ResultBean bean = getResultBean();
		InputStream in = null;
		try {
			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			if (!".json".equals(extension)) {
				bean.setError(true);
				bean.setError_msg("请上传json后缀文件");
				return bean;
			}
			in = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			projectService.importProject(sb.toString());
			bean.setSuccess_msg("导入成功");
		} catch (IOException e) {
			bean.setError(true);
			bean.setError_msg("文件读取失败");
			e.printStackTrace();
		} catch (Exception e) {
			bean.setError(true);
			bean.setError_msg("导入失败,文件内容格式不正确或者项目已存在");
			e.printStackTrace();
		}
		return bean;
	}

	@RequestMapping("/env")
	@ResponseBody
	public Map updateDefaultEnv( HttpServletRequest request){
		PageParam param = getPageParam(request);
		Map map = new HashMap();
		try {
			projectService.updateEnv(param);
			List env = envService.getEnvByEnvid(param);
			map.put("error", false);
			map.put("result", env);
		} catch (Exception e) {
			map.put("error", true);
			if(e instanceof BaseAppException){
				map.put("msg", e.getMessage());
			}else{
				map.put("msg", "默认环境设置失败");
			}
		}
		return map;
	}
	@RequestMapping("/getEnvList")
	@ResponseBody
	public Map getEnvList( HttpServletRequest request){
		PageParam param = getPageParam(request);
		Map map = new HashMap();
		try {
			List env = envService.getEnvByEnvid(param);
			map.put("error", false);
			map.put("result", env);
		} catch (Exception e) {
			map.put("error", true);
			map.put("msg", "默认环境查询失败");
		}
		return map;
	}
	@RequestMapping("/queryDefaultEnv")
	public Map queryDefaultEnv( HttpServletRequest request){
		PageParam param = getPageParam(request);
		Map map = new HashMap();
		map.put("error", false);
		map.put("result", projectService.queryDefaultEnv(param));
		return map;
	}
}
