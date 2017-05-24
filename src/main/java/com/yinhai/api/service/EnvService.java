package com.yinhai.api.service;

import com.yinhai.api.common.domain.Environments;
import com.yinhai.api.common.domain.EnvironmentsList;
import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;

import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes" })
public interface EnvService extends BaseService{

	/**
	 * 新增环境
	 * @param param
	 * @return
	 */
	public ResultBean insertEnv(PageParam param);
	/**
	 * 获取环境列表
	 * @param param
	 * @return
	 */
	public Map getEnvList(PageParam param);
	/**
	 * 删除环境
	 * @param param
	 * @return
	 */
	public ResultBean deleteEnv(PageParam param);
	/**
	 * 通过id查询环境详情
	 * @param param
	 * @return
	 */
	public List getEnvByEnvid(PageParam param);
	/**
	 * 修改环境
	 * @param param
	 * @return
	 */
	public ResultBean updateEnvByEnvid(PageParam param);
	/**
	 * 复制环境
	 * @param param
	 * @return
	 */
	public ResultBean addEnvCopy(PageParam param);
	/**
	 * 查询工程环境名称(返回map列表)
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectEnvNames(Integer projectId);
	/**
	 * 查询工程环境名称(返回实体类列表)
	 * @param projectId
	 * @return
	 */
	public List<Environments> getProjectEnvironments(Integer projectId);
	/**
	 * 查询环境参数列表(返回map列表)
	 * @param envId
	 * @return
	 */
	public List<Map<String, Object>> getParamList(Integer envId);
	/**
	 * 查询环境参数列表(返回实体类列表)
	 * @param envId
	 * @return
	 */
	public List<EnvironmentsList> getEnvironmentLists(Integer envId);

}
