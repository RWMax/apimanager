package com.liuht.api.service;

import com.liuht.api.common.domain.InterfaceFolder;
import com.liuht.api.common.domain.Module;
import com.liuht.api.common.domain.Project;
import com.liuht.api.common.domain.ProjectUser;
import com.liuht.ec.base.service.BaseService;
import com.liuht.ec.base.util.PageParam;

import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes" })
public interface ProjectService extends BaseService{

	/**
	 * 查询我的项目 包括OWNER 以及 NORMAL
	  * @return List<Project> 
	  * @author 刘惠涛
	 */
	List<Map<String, Object>> getMyProjects(Integer userid);

	/**
	 * 查询受邀请的项目 包括TEAM
	  * @return List<Project> 
	  * @author 刘惠涛
	 */
	List<Map<String, Object>> getMyTeamProjects(Integer userid);
	
	/**
	 * 查询公共项目 排除我创建的公共项目
	  * @return List<Project> 
	  * @author 刘惠涛
	 */
	List<Map<String, Object>> getPublicProjects(Integer userid);
	
	/**
	 * 新增项目
	  * @return void 
	  * @author 刘惠涛
	 */
	void addProject(Project project);

	/**
	 * 通过id查询
	  * @return Project 
	  * @author 刘惠涛
	 */
	Project getProjectByID(Integer valueOf);

	/**
	 * 查询项目详情
	 * @param valueOf 主键id
	 * @return Map<String,Object>>
	 */
	Map<String, Object> getProjectMap(Integer valueOf);

	/**
	 * 查询项目人员
	  * @return ProjectUser 
	  * @author 刘惠涛
	 */
	ProjectUser queryProjectUser(Integer projectId, Integer userId);

	/**
	 * 查询项目人员列表
	 * @param projectId 项目id
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryProjectUsers(Integer projectId);

	/**
	 * 根据项目id 查询项目模块
	  * @return List<Module> 
	  * @author 刘惠涛
	 */
	List<Module> queryModulesByProjectId(Integer projectId);

	/**
	 * 查询模块
	 * @param projectId
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryModules(Integer projectId);

	/**
	 * 根据模块id查询模块分类
	  * @return List<InterfaceFolder> 
	  * @author 刘惠涛
	 */
	List<InterfaceFolder> queryFoldersByModuleId(Integer projectId, Integer moduleId);

	/**
	 * 根据模块id查询模块分类
	 * @param projectId
	 * @param moduleId
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryFoldersList(Integer projectId, Integer moduleId);

	/**
	 * 根据id 查询module
	  * @return Module 
	  * @author 刘惠涛
	 */
	Module getModuleById(Integer moduleId);

	/**
	 * 保存模板
	  * @return void 
	  * @author 刘惠涛
	 */
	void saveModule(PageParam pageParam);

	/**
	 * 删除模块
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteModule(Integer projectId, Integer moduleId);
	
	/**
	 * 查询所有项目人员
	  * @return List<ProjectUser> 
	  * @author 刘惠涛
	 */
	List<Map<String, Object>> queryProUsers(Integer projectId);

	/**
	 * 移除项目人员
	  * @return void 
	  * @author 刘惠涛
	 */
	void removeProUser(PageParam pageParam);
	/**
	 * 查询默认环境
	 * @param param
	 * @return
	 */
	Map queryDefaultEnv(PageParam param);
	/**
	 * 更新默认环境
	 * @param param
	 */
	void updateEnv(PageParam param);

	/**
	 * 邮箱邀请
	  * @return void 
	  * @author 刘惠涛
	 */
	void addNormalUser(PageParam pageParam);

	/**
	 * 团队邀请
	  * @return void 
	  * @author 刘惠涛
	 */
	void addTeamUser(PageParam pageParam);

	/**
	 * 将普通项目成员 提升为团队项目成员
	  * @return void 
	  * @author 刘惠涛
	 */
	void improveProUser(PageParam pageParam);

	/**
	 * 项目拥有者转让
	  * @return void 
	  * @author 刘惠涛
	 */
	void exchangeProUser(PageParam pageParam);
	/**
	 * 通过工程id查询参数列表
	 * @param projectId
	 * @return
	 */
	List getParamList(Integer projectId);

	/**
	 * 删除项目
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteProject(Integer projectId);

	/**
	 * 导入项目
	 *
	 * @param content
	 * @throws Exception
	 */
	void importProject(String content) throws Exception;
}
