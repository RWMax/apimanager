package com.yinhai.api.service.impl;

import com.yinhai.api.common.domain.Environments;
import com.yinhai.api.common.domain.EnvironmentsList;
import com.yinhai.api.service.EnvService;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EnvServiceImpl extends BaseServiceImpl implements EnvService{

	@Override
	@Transactional
	public ResultBean insertEnv(PageParam param) {
		if(param.get("projectId") == null || "".equals(param.get("projectId").toString())){
			throw new BaseAppException("参数错误");
		}
		if(param.get("env_name") == null || "".equals(param.get("env_name").toString())){
			throw new BaseAppException("环境名称不能为空");
		}
		if(param.get("paramName") == null || "".equals(param.get("paramName").toString())){
			throw new BaseAppException("变量名称不能为空");
		}
		if(param.get("paramValue") == null || "".equals(param.get("paramValue").toString())){
			throw new BaseAppException("变量值不能为空");
		}
		String [] paramName = param.get("paramName").toString().split(",");
		String [] paramValue = param.get("paramValue").toString().split(",");
		if(paramName.length != paramValue.length){
			throw new BaseAppException("变量名称与值不匹配");
		}
		//新增环境名称
		sqlSession.insert("api_env.insertEnv",param);
		String envId = param.get("id").toString();
		List list = new ArrayList();
		for( int i =0,len = paramName.length; i < len; i++){
			Map map = new HashMap();
			map.put("envId", envId);
			map.put("paramName",paramName[i]);
			map.put("paramValue", paramValue[i]);
			list.add(map);
		}
		sqlSession.insert("api_env.insertEnvValue",list);
		ResultBean bean = new ResultBean();
		bean.setError(false);
		bean.setSuccess_msg("添加成功");
		return bean;
	}

	@Override
	public Map getEnvList(PageParam param) {
		Map result = new HashMap();
		List list = sqlSession.selectList("api_env.getEnvList", param);
		if(list!=null && list.size() > 0) {
			List countList = sqlSession.selectList("api_env.getEnvCount", param);
			int j = 0;
			for(int i = 0, len = countList.size(); i < len; i++){
				int size =Integer.valueOf(((Map)countList.get(i)).get("count").toString());
				List temp = list.subList(j, j + size);
				result.put(((Map)temp.get(0)).get("envId").toString(), temp);
				j = j + size;
			}
		}
		return result ;
	}

	@Override
	@Transactional
	public ResultBean deleteEnv(PageParam param) {
		int i = sqlSession.delete("api_env.deleteEnv", param);
		int j = sqlSession.delete("api_env.deleteEnvList", param);
		int count = sqlSession.update("api_env.updateforNext", param);
		if(i>0 && j>0 && count == 1){
			ResultBean bean = new ResultBean();
			bean.setError(false);
			bean.setSuccess_msg("删除成功");
			return bean;
		}else{
			throw new BaseAppException("删除失败");
		}
	}

	@Override
	public List getEnvByEnvid(PageParam param) {
		if(param.get("envId") == null || "".equals(param.get("envId").toString())){
			throw new BaseAppException("未传入envId");
		}
		return sqlSession.selectList("api_env.getEnvByEnvid", param);
	}

	@Override
	@Transactional
	public ResultBean updateEnvByEnvid(PageParam param) {
		if(param.get("envId") == null || "".equals(param.get("envId").toString())){
			throw new BaseAppException("未传入envId");
		}
		//更新环境名称
		Object env_name = param.get("env_name");
		if(env_name !=null && !"".equals(env_name .toString())){
			int i = sqlSession.update("api_env.updateEnvByEnvid", param);
			if(i != 1){
				throw new BaseAppException("修改失败");
			}
		}
		//更新环境列表
		Object update_paramName = param.get("update_paramName");
		Object update_paramValue = param.get("update_paramValue");
		Object id = param.get("id");
		if(update_paramName !=null && !"".equals(update_paramName .toString())){
			String[] paramName = update_paramName.toString().split(",");
			String[] paramValue = update_paramValue.toString().split(",");
			String [] _id = id.toString().split(",");
			for(int i=0; i< paramName.length;i++){
				Map map = new HashMap();
				map.put("id",_id[i]);
				map.put("paramName", paramName[i]);
				map.put("paramValue",paramValue[i]);
				int count = sqlSession.update("api_env.updateEnvItems", map);
				map = null;
				if(count != 1){
					throw new BaseAppException("修改失败");
				}
			}	
		}
		//新增
		Object insert_paramName = param.get("insert_paramName");
		Object insert_paramValue = param.get("insert_paramValue");
		if( insert_paramName !=null && !"".equals( insert_paramName .toString())){
			List  insert = new ArrayList();
			String[] paramName =  insert_paramName.toString().split(",");
			String[] paramValue =  insert_paramValue.toString().split(",");
			for(int i=0; i< paramName.length;i++){
				Map map = new HashMap();
				map.put("envId",param.get("envId").toString());
				map.put("paramName", paramName[i]);
				map.put("paramValue",paramValue[i]);
				insert.add(map);
			}
			//insert
			sqlSession.insert("api_env.insertEnvValue",insert);
		}
		//删除
		Object del = param.get("del");
		if( del !=null && !"".equals( del.toString())){
				String[] delete = del.toString().split(",");
				Map  m= new HashMap();
				m.put("paramId", delete);
				sqlSession.delete("api_env.deleteEnvListMuilt",m);
		}
	    ResultBean bean = new ResultBean();
	    bean.setError(false);
	    bean.setSuccess_msg("修改成功");
		return bean;
	}

	@Override
	@Transactional
	public ResultBean addEnvCopy(PageParam param) {
		sqlSession.insert("api_env.insertNewCopy", param);
		sqlSession.insert("api_env.insertNewCopyList",param);
		param = null;
		ResultBean bean = new ResultBean();
		bean.setError(false);
		bean.setSuccess_msg("复制成功");
		return bean;
	}

	@Override
	public List<Map<String, Object>> getProjectEnvNames(Integer projectId) {
		
		return sqlSession.selectList("api_env.getProjectEnvNames",projectId);
	}
	@Override
	public List<Environments> getProjectEnvironments(Integer projectId) {
		return sqlSession.selectList("api_env.getProjectEnvironments",projectId);
	}

	@Override
	public List<Map<String, Object>> getParamList(Integer envId) {
		
		return sqlSession.selectList("api_env.getEnvParamList", envId);
	}
	@Override
	public List<EnvironmentsList> getEnvironmentLists(Integer envId) {
		return sqlSession.selectList("api_env.getEnvironmentLists", envId);
	}

}
