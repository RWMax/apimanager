package com.yinhai.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.api.common.domain.*;
import com.yinhai.api.service.ApiService;
import com.yinhai.api.service.ProjectService;
import com.yinhai.api.service.TeamService;
import com.yinhai.api.service.UserService;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings({ "rawtypes" })
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService{
	@Autowired
	private ApiService apiService;
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;

	@Override
	public List<Map<String, Object>> getMyProjects(Integer userid) {
		if(userid == null){
			return Collections.emptyList();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", userid);
		param.put("pu_status", HYConst.ProUserStatus.TEAM.getName());
		param.put("p_status", HYConst.AccountStatus.ENABLE.getName());
		return sqlSession.selectList("project.getMyProjects", param);
	}

	@Override
	public List<Map<String, Object>> getMyTeamProjects(Integer userid) {
		if(userid == null){
			return Collections.emptyList();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", userid);
		param.put("pu_status", HYConst.ProUserStatus.TEAM.getName());
		param.put("p_status", HYConst.AccountStatus.ENABLE.getName());
		return sqlSession.selectList("project.getMyTeamProjects", param);
	}

	@Override
	public List<Map<String, Object>> getPublicProjects(Integer userid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("p_permission", HYConst.ProjectPermission.PUBLIC.getName());
		param.put("p_status", HYConst.AccountStatus.ENABLE.getName());
		param.put("userid", userid);
		return sqlSession.selectList("project.getPublicProjects", param);
	}

	@Override
	@Transactional
	public void addProject(Project project) {
		if(project.getId() == null){
			Integer count = queryCountByName(project.getName());
			if(count > 0){
				throw new BaseAppException("项目名称已经有人用啦,另外取个名吧");
			}
			
			// 新增项目
			sqlSession.insert("project.insertProject", project);
			
			// 新增项目人员
			ProjectUser projectUser = new ProjectUser();
			projectUser.setProjectid(project.getId());
			projectUser.setUserid(project.getUserid());
			projectUser.setEditable(HYConst.YesOrNo.YES.getName());
			projectUser.setStatus(HYConst.ProUserStatus.OWNER.getName());
			addProjectUser(projectUser);
			
			// 自动添加一个默认模块
			ModuleWithBLOBs module = new ModuleWithBLOBs();
			module.setProjectid(project.getId());
			module.setName("默认模块");
			sqlSession.insert("module.insertModule", module);
			
			// 添加一个默认分类
			InterfaceFolder folder = new InterfaceFolder();
			folder.setProjectid(module.getProjectid());
			folder.setModuleid(module.getId());
			folder.setName("默认分类");
			sqlSession.insert("interfacefolder.insertFolder", folder);
		}else {
			// 更新项目
			if(sqlSession.update("project.updateProject", project) != 1){
				throw new BaseAppException("更新项目信息失败,请稍后重试...");
			}
		}
	}

	@Transactional
	private void addProjectUser(ProjectUser projectUser) {
		sqlSession.insert("project.insertProjectUser", projectUser);
	}

	private Integer queryCountByName(String name) {
		
		return sqlSession.selectOne("project.selectCountByName", name);
	}

	@Override
	public Project getProjectByID(Integer id) {
		if(id == null){
			return null;
		}
		return sqlSession.selectOne("project.selectByPrimaryKey", id);
	}

	@Override
	public Map<String, Object> getProjectMap(Integer id) {
		if (id == null) {
			return null;
		}
		return sqlSession.selectOne("project.selectById", id);
	}

	@Override
	public ProjectUser queryProjectUser(Integer projectId, Integer userId) {
		if(projectId == null || userId == null){
			return null;
		}
		Map<String, Integer> param = new HashMap<String,Integer>();
		param.put("projectId", projectId);
		param.put("userId", userId);
		return sqlSession.selectOne("project.queryProjectUser",param);
	}

	@Override
	public List<Map<String, Object>> queryProjectUsers(Integer projectId) {
		if (projectId == null) {
			return Collections.emptyList();
		}
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("projectId", projectId);
		return sqlSession.selectList("project.queryProjectUsers", param);
	}

	@Override
	public List<Module> queryModulesByProjectId(Integer projectId) {
		if(projectId == null ){
			return Collections.emptyList();
		}
		return sqlSession.selectList("module.queryModulesByProjectId", projectId);
	}

	@Override
	public List<Map<String, Object>> queryModules(Integer projectId) {
		return sqlSession.selectList("module.queryModules", projectId);
	}

	@Override
	public List<InterfaceFolder> queryFoldersByModuleId(Integer projectId, Integer moduleId) {
		if(projectId == null || moduleId == null){
			return Collections.emptyList();
		}
		Map<String, Integer> param = new HashMap<String,Integer>();
		param.put("projectId", projectId);
		param.put("moduleId", moduleId);
		return sqlSession.selectList("interfacefolder.queryFoldersByModuleId", param);
	}

	@Override
	public List<Map<String, Object>> queryFoldersList(Integer projectId, Integer moduleId) {
		if (projectId == null || moduleId == null) {
			return Collections.emptyList();
		}
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("moduleId", moduleId);
		param.put("projectId", projectId);
		return sqlSession.selectList("interfacefolder.queryFoldersList", param);
	}

	@Override
	public Module getModuleById(Integer moduleId) {
		return sqlSession.selectOne("module.getModuleById", moduleId);
	}

	@Override
	@Transactional
	public void saveModule(PageParam pageParam) {
		if(!pageParam.containsKey("name") || StringUtils.isEmpty(pageParam.get("name"))){
			throw new BaseAppException("请填写模板名称");
		}
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("projectId"))){
			throw new BaseAppException("操作失败,请刷新重试");
		}
		ModuleWithBLOBs module = new ModuleWithBLOBs();
		module.setProjectid(Integer.valueOf(pageParam.getString("projectId")));
		module.setName(pageParam.getString("name"));
		if(!StringUtils.isEmpty(pageParam.get("moduleId"))){
			// update
			module.setId(Integer.valueOf(pageParam.getString("moduleId")));
			int count = sqlSession.update("module.updateModule", module);
			if(count != 1){
				throw new BaseAppException("更新失败,请刷新重试");
			}
		}else{
			// add
			sqlSession.insert("module.insertModule", module);
			
			// 添加一个默认分类
			InterfaceFolder folder = new InterfaceFolder();
			folder.setProjectid(module.getProjectid());
			folder.setModuleid(module.getId());
			folder.setName("默认分类");
			sqlSession.insert("interfacefolder.insertFolder", folder);
		}
	}

	@Override
	@Transactional
	public void deleteModule(Integer projectId, Integer moduleId) {
		Module module = getModuleById(moduleId);
		if("默认模块".equals(module.getName())){
			throw new BaseAppException("不能删除默认模块");
		}
		
		// 删除folder
		apiService.deleteFolders(projectId, moduleId);
		
		int count = sqlSession.delete("module.deleteModule", moduleId);
		if(count != 1){
			throw new BaseAppException("删除失败,请刷新重试");
		}
	}

	@Override
	public List<Map<String, Object>> queryProUsers(Integer projectId) {
		
		return sqlSession.selectList("project.getProUsersByProjectId", projectId);
	}

	@Override
	@Transactional
	public void removeProUser(PageParam pageParam) {
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("userId"))){
			throw new BaseAppException("移除失败!");
		}
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		Integer userId = Integer.valueOf(pageParam.getString("userId"));
		if(!checkPermission(projectId, Integer.valueOf(pageParam.getString("loginId")))){
			throw new BaseAppException("您不是项目拥有者,不能执行此操作");
		}
		ProjectUser projectUser = queryProjectUser(projectId, userId);
		if(projectUser.getStatus().equals(HYConst.ProUserStatus.TEAM.getName())){
			// 删除团队成员记录
			Project project = getProjectByID(projectId);
			TeamUser teamUser = new TeamUser();
			teamUser.setUserid(userId);
			teamUser.setTeamid(project.getTeamid());
			teamService.removeTeamUser(teamUser);
		}
		sqlSession.delete("project.deleteProUser", pageParam);
	}

	@Override
	@Transactional
	public void addNormalUser(PageParam pageParam) {
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("email"))){
			throw new BaseAppException("邀请失败了,请刷新重试");
		}
		String email = pageParam.getString("email");
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		// 检查邮箱是否已经注册
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("email", email);
		User user = userService.queryUser(param);
		if(user == null){
			throw new BaseAppException("邮箱:"+email+" 还没有注册哦!!");
		}
		// 检查用户是否已经在项目里面了
		param.put("projectId", projectId);
		param.put("userId", user.getId());
		int count = sqlSession.selectOne("project.getUserCountFromProUser", param);
		if(count > 0 ){
			throw new BaseAppException("邮箱:"+email+" 已经是项目成员了");
		}
		
		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectid(projectId);
		projectUser.setUserid(user.getId());
		projectUser.setEditable(HYConst.YesOrNo.YES.getName());
		projectUser.setStatus(HYConst.ProUserStatus.NORMAL.getName());
		addProjectUser(projectUser);
	}

	@Override
	@Transactional
	public void addTeamUser(PageParam pageParam) {
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("email"))){
			throw new BaseAppException("邀请失败了,请刷新重试");
		}
		String email = pageParam.getString("email");
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		// 检查邮箱是否已经注册
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("email", email);
		User user = userService.queryUser(param);
		if(user == null){
			throw new BaseAppException("邮箱:"+email+" 还没有注册哦!!");
		}
		// 检查用户是否已经在项目里面了
		param.put("projectId", projectId);
		param.put("userId", user.getId());
		int count = sqlSession.selectOne("project.getUserCountFromProUser", param);
		if(count > 0 ){
			throw new BaseAppException("邮箱:"+email+" 已经是项目成员了");
		}
		
		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectid(projectId);
		projectUser.setUserid(user.getId());
		projectUser.setEditable(HYConst.YesOrNo.YES.getName());
		projectUser.setStatus(HYConst.ProUserStatus.TEAM.getName());
		addProjectUser(projectUser);
		
		Project project = getProjectByID(projectId);
		TeamUser teamUser = new TeamUser();
		teamUser.setUserid(user.getId());
		teamUser.setTeamid(project.getTeamid());
		teamUser.setTeamusertype(HYConst.TeamUserType.MEMBER.getName());
		teamService.addTeamUser(teamUser);
	}

	@Override
	@Transactional
	public void improveProUser(PageParam pageParam) {
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("userId"))){
			throw new BaseAppException("操作失败!");
		}
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		Integer userId = Integer.valueOf(pageParam.getString("userId"));
		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectid(projectId);
		projectUser.setUserid(userId);
		projectUser.setStatus(HYConst.ProUserStatus.TEAM.getName());
		int count = sqlSession.update("project.updateProUser", projectUser);
		if(count != 1){
			throw new BaseAppException("操作失败,请稍后重试");
		}
		Project project = getProjectByID(projectId);
		TeamUser teamUser = new TeamUser();
		teamUser.setUserid(userId);
		teamUser.setTeamid(project.getTeamid());
		teamUser.setTeamusertype(HYConst.TeamUserType.MEMBER.getName());
		teamService.addTeamUser(teamUser);
	}

	@Override
	@Transactional
	public void exchangeProUser(PageParam pageParam) {
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("userId"))){
			throw new BaseAppException("操作失败!");
		}
		Integer projectId = Integer.valueOf(pageParam.getString("projectId"));
		Integer userId = Integer.valueOf(pageParam.getString("userId"));
		if(!checkPermission(projectId, Integer.valueOf(pageParam.getString("loginId")))){
			throw new BaseAppException("您不是项目拥有者,不能执行此操作");
		}
		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectid(projectId);
		projectUser.setUserid(Integer.valueOf(pageParam.getString("loginId")));
		projectUser.setStatus(HYConst.ProUserStatus.TEAM.getName()); // 原项目拥有者降级为团队成员
		if(sqlSession.update("project.updateProUser", projectUser) != 1){
			throw new BaseAppException("操作失败,请稍后重试");
		}
		
		// 新的项目拥有者
		ProjectUser oldPU = queryProjectUser(projectId, userId);
		oldPU.setStatus(HYConst.ProUserStatus.OWNER.getName());
		if(sqlSession.update("project.updateProUser", oldPU) != 1){
			throw new BaseAppException("操作失败,请稍后重试");
		}
	}
	
	private boolean checkPermission(Integer projectId, Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("projectId", projectId);
		param.put("userId", userId);
		ProjectUser projectUser = sqlSession.selectOne("project.queryProjectUser", param);
		return projectUser.getStatus().equals(HYConst.ProUserStatus.OWNER.getName());
	}

	@Override
	public Map queryDefaultEnv(PageParam param) {
		
		return (Map)sqlSession.selectOne("project.queryDefaultEnv", param);
	}

	@Override
	@Transactional
	public void updateEnv(PageParam param) {
		int count = sqlSession.update("project.updateDefaultEnv", param);
		if(count != 1){
			throw new BaseAppException("默认环境设置失败");
		}
	}

	@Override
	public List getParamList(Integer projectId) {
		return sqlSession.selectList("project.getParamList",projectId);
	}

	@Override
	@Transactional
	public void deleteProject(Integer projectId) {
		// 删除项目人员
		sqlSession.delete("project.deleteProjectUser", projectId);
		sqlSession.delete("api_env.deleteEnvLists", projectId);
		sqlSession.delete("api_env.deleteEnvs", projectId);
		List<Module> modules = queryModulesByProjectId(projectId);
		for (Module module : modules) {
			// 删除module对应的所有分类及接口
			apiService.deleteFolders(projectId, module.getId());
			// 删除module
			sqlSession.delete("module.deleteModule", module.getId());
		}
		sqlSession.delete("project.deleteProject", projectId);
	}

	@Override
	@Transactional(readOnly = false)
	public void importProject(String content) throws Exception {
		Map<String, Object> project = (Map<String, Object>) JSONObject.parseObject(content).get("project");
		sqlSession.insert("project.insertProjectInfo", project);
		insertTableData(project.get("users"), "project.insertProjectUserInfo");
		if (!StringUtils.isEmpty(project.get("environments"))) {
			JSONArray environments = JSONArray.parseArray(project.get("environments") + "");
			if (environments != null) {
				Iterator<Object> environment = environments.iterator();
				while (environment.hasNext()) {
					Map<String, Object> environmentMap = (Map<String, Object>) environment.next();
					sqlSession.insert("api_env.insertEnvironments", environmentMap);
					if (!StringUtils.isEmpty(environmentMap.get("items"))) {
						JSONArray items = JSONArray.parseArray(environmentMap.get("items") + "");
						if (items != null) {
							Iterator<Object> item = items.iterator();
							while (item.hasNext()) {
								Map<String, Object> itemMap = (Map<String, Object>) item.next();
								sqlSession.insert("api_env.insertEnvironmentLists", itemMap);
							}
						}
					}
				}
			}
		}
		if (!StringUtils.isEmpty(project.get("modules"))) {
			JSONArray modules = JSONArray.parseArray(project.get("modules") + "");
			if (modules != null) {
				Iterator<Object> module = modules.iterator();
				while (module.hasNext()) {
					Map<String, Object> moduleMap = (Map<String, Object>) module.next();
					sqlSession.insert("module.insertModuleInfo", moduleMap);
					if (!StringUtils.isEmpty(moduleMap.get("folders"))) {//folders //interfaces
						JSONArray folders = JSONArray.parseArray(moduleMap.get("folders") + "");
						if (folders != null) {
							Iterator<Object> folder = folders.iterator();
							while (folder.hasNext()) {
								Map<String, Object> folderMap = (Map<String, Object>) folder.next();
								sqlSession.insert("interfacefolder.insertFolderInfo", folderMap);
								if (!StringUtils.isEmpty(folderMap.get("interfaces"))) {
									JSONArray interfaces = JSONArray.parseArray(folderMap.get("interfaces") + "");
									if (interfaces != null) {
										Iterator<Object> interfac = interfaces.iterator();
										while (interfac.hasNext()) {
											Map<String, Object> interfacMap = (Map<String, Object>) interfac.next();
											sqlSession.insert("interface.insertInterfaceInfo", interfacMap);
											JSONObject wsObject = JSONObject.parseObject(interfacMap.get("interfaceWS") + "");
											if (wsObject != null) {
												sqlSession.insert("interface.insertApiWsInfo", wsObject);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void insertTableData(Object object, String statement) {
		if (!StringUtils.isEmpty(object)) {
			JSONArray arrays = JSONArray.parseArray(object + "");
			Iterator<Object> iterator = arrays.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> iterMap = (Map<String, Object>) iterator.next();
				sqlSession.insert(statement, iterMap);
			}
		}
	}
}
