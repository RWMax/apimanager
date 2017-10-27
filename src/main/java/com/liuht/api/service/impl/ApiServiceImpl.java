package com.liuht.api.service.impl;

import com.liuht.api.common.domain.*;
import com.liuht.api.service.ApiService;
import com.liuht.api.service.ProjectService;
import com.liuht.ec.base.exception.BaseAppException;
import com.liuht.ec.base.service.impl.BaseServiceImpl;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.PageParam;
import com.liuht.ec.util.StringUtils;
import com.liuht.plugin.dwr.message.MessagePush;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiServiceImpl extends BaseServiceImpl implements ApiService{
	@Autowired
	private ProjectService projectService;

	@Override
	@Transactional
	public void deleteFolders(Integer projectId, Integer moduleId) {
		Map<String, Integer> param = new HashMap<String,Integer>();
		param.put("projectId", projectId);
		param.put("moduleId", moduleId);
		List<InterfaceFolder> folders = sqlSession.selectList("interfacefolder.queryFoldersByModuleId", param);
		for (InterfaceFolder interfaceFolder : folders) {
			deleteApis(interfaceFolder.getId());
			int count = sqlSession.delete("interfacefolder.deleteInterfacefolder", interfaceFolder.getId());
			if(count != 1){
				throw new BaseAppException("操作失败,请稍后刷新重试");
			}
		}
	}

	@Override
	@Transactional
	public void deleteFolder(Integer folderId) {
		InterfaceFolder folder = getFolderById(folderId);
		if("默认分类".equals(folder.getName())){
			throw new BaseAppException("不能删除默认分类");
		}
		
		deleteApis(folderId);
		
		int count = sqlSession.delete("interfacefolder.deleteInterfacefolder", folderId);
		if(count != 1){
			throw new BaseAppException("操作失败,请稍后刷新重试");
		}
	}
	
	@Override
	@Transactional
	public void deleteApis(Integer folderId) {
		List<Interface> list = queryApisByFolderId(folderId);
		for (int i = 0; i < list.size(); i++) {
			Interface api = list.get(i);
			if(api.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())){
				deleteApiWsByApiid(api.getId());
			}
		}
		// 这里是删除多条 不判断
		sqlSession.delete("interface.deleteByFolderId", folderId);
	}

	@Override
	@Transactional
	public void deleteApiWsByApiid(Integer apiId) {
		if(sqlSession.delete("interface.deleteApiWsByapiId", apiId) != 1){
			throw new BaseAppException("操作失败!");
		}
	}

	@Override
	public InterfaceFolder getFolderById(Integer folderId) {
		if(folderId == null){
			return null;
		}
		return sqlSession.selectOne("interfacefolder.getFolderById", folderId);
	}

	@Override
	@Transactional
	public void saveFolder(PageParam pageParam) {
		if(!pageParam.containsKey("name") || StringUtils.isEmpty(pageParam.get("name"))){
			throw new BaseAppException("请填写分类名称");
		}
		if(!pageParam.containsKey("projectId") || StringUtils.isEmpty(pageParam.get("projectId"))){
			throw new BaseAppException("操作失败,请刷新重试");
		}
		if(!pageParam.containsKey("moduleId") || StringUtils.isEmpty(pageParam.get("moduleId"))){
			throw new BaseAppException("操作失败,请刷新重试");
		}
		InterfaceFolder folder = new InterfaceFolder();
		folder.setProjectid(Integer.valueOf(pageParam.getString("projectId")));
		folder.setModuleid(Integer.valueOf(pageParam.getString("moduleId")));
		folder.setName(pageParam.getString("name"));
		if(!StringUtils.isEmpty(pageParam.get("folderId"))){
			// update
			folder.setId(Integer.valueOf(pageParam.getString("folderId")));
			int count = sqlSession.update("interfacefolder.updateFolder", folder);
			if(count != 1){
				throw new BaseAppException("更新失败,请刷新重试");
			}
		}else{
			// add
			sqlSession.insert("interfacefolder.insertFolder", folder);
		}
	}

	@Override
	public InterfaceWithBLOBs getApiByID(Integer id) {
		return sqlSession.selectOne("interface.getApiByID", id);
	}

	@Override
	public List<Interface> queryApisByFolderId(Integer folderId) {
		return sqlSession.selectList("interface.queryApisByFolderId", folderId);
	}
	@Override
	public List<InterfaceWithBLOBs> queryApiBlobsByFolderId(Integer folderId) {
		List<InterfaceWithBLOBs> list = sqlSession.selectList("interface.queryApiBlobsByFolderId", folderId);
		for (InterfaceWithBLOBs interfaceWithBLOBs : list) {
			if (interfaceWithBLOBs.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())) {
				interfaceWithBLOBs.setInterfaceWS(getApiWsByApiid(interfaceWithBLOBs.getId()));
			}
		}
		return list;
	}
	@Override
	public List<Map<String,Object>> queryApiBlobList(Integer folderId) {
		return sqlSession.selectList("interface.queryApiBlobList", folderId);
	}

	private static final String rootElement = "xml";
	
	@Override
	@Transactional
	public Integer saveApi(PageParam pageParam) {
		if(StringUtils.isEmpty(pageParam.get("projectId")) || StringUtils.isEmpty(pageParam.get("moduleId"))
				|| StringUtils.isEmpty(pageParam.get("folderId"))){
			throw new BaseAppException("操作失败,请稍后刷新重试");
		}
		if(StringUtils.isEmpty(pageParam.get("name"))){
			throw new BaseAppException("接口名称不能为空");
		}
		if(StringUtils.isEmpty(pageParam.get("url"))){
			throw new BaseAppException("请求地址不能为空");
		}
		
		InterfaceWithBLOBs api = new InterfaceWithBLOBs();
		InterfaceWS ws = null;
		api.setName(pageParam.getString("name"));
		api.setDescription(pageParam.getString("description"));
		api.setExample(pageParam.getString("example"));
		api.setFolderid(Integer.valueOf(pageParam.getString("folderId")));
		api.setUrl(pageParam.getString("url"));
		api.setProtocol(pageParam.getString("protocol"));
		if(api.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())){
			ws = new InterfaceWS();
			ws.setTargetnamespace(pageParam.getString("targetNamespace"));
			ws.setMethodname(pageParam.getString("methodName"));
			String url = api.getUrl();
			if(url.contains("?")){
				ws.setEndpointaddress(url.substring(0, url.indexOf("?")));
			}else{
				ws.setEndpointaddress(api.getUrl());
			}
			api.setRequestmethod("");
		}else{
			api.setRequestmethod(pageParam.getString("requestMethod"));
		}
		api.setContenttype(pageParam.getString("contentType"));
		if(ContentType.APPLICATION_XML.getMimeType().equals(api.getContenttype())){
			api.setRequestrootelement(StringUtils.isEmpty(pageParam.get("requestRootElement"))?rootElement : pageParam.getString("requestRootElement"));
		}else {
			api.setRequestrootelement("");
		}
		api.setRequestheaders(pageParam.getString("requestHeaders"));
		api.setRequestargs(pageParam.getString("requestArgs"));
		api.setResponseargs(pageParam.getString("responseArgs"));
		api.setModuleid(Integer.valueOf(pageParam.getString("moduleId")));
		api.setProjectid(Integer.valueOf(pageParam.getString("projectId")));
		api.setDatatype(pageParam.getString("dataType"));
		if(HYConst.DataType.XML.getName().equals(api.getDatatype())){
			api.setResponserootelement(StringUtils.isEmpty(pageParam.get("responseRootElement"))?rootElement : pageParam.getString("responseRootElement"));
		}else{
			api.setResponserootelement("");
		}
		api.setStatus(pageParam.getString("status"));
		
		if(StringUtils.isEmpty(pageParam.get("id"))){
			// 新增
			sqlSession.insert("interface.insertApi", api);
			if(ws != null){
				ws.setApiid(api.getId());
				sqlSession.insert("interface.insertApiWs", ws);
			}
		}else{
			// 更新
			api.setId(Integer.valueOf(pageParam.getString("id")));
			if(sqlSession.update("interface.updateApi", api) != 1){
				throw new BaseAppException("更新接口失败,请稍后重试");
			}
			if(ws != null){
				ws.setApiid(api.getId());
				if (getApiWsByApiid(api.getId()) == null) {
					// 新增接口的Webservice部分
					sqlSession.insert("interface.insertApiWs", ws);
				}else{
					// 更新接口的Webservice部分
					if (sqlSession.update("interface.updateApiWs", ws) != 1) {
						throw new BaseAppException("删除失败,请稍后重试");
					}
				}

			}
		}
		handleToastr(pageParam);
		return api.getId();
	}

	/**
	 * 处理通知消息
	  * @return void 
	  * @author 刘惠涛
	 */
	private void handleToastr(PageParam pageParam) {
		StringBuffer content = new StringBuffer();
		User user = (User) pageParam.get(HYConst.SESSION_USER);
		content.append(user.getNickname()+ " ");
		if(StringUtils.isEmpty(pageParam.get("id"))){
			// 新增
			content.append("新增了接口:"+pageParam.getString("name"));
		}else{
			// 更新
			content.append("修改了接口:"+pageParam.getString("name"));
		}
		List<Map<String, Object>> prousers = projectService.queryProUsers(Integer.valueOf(pageParam.getString("projectId")));
		for (Map<String, Object> map : prousers) {
			if(map.get("userId") != null && !user.getId().equals((Integer)map.get("userId"))){
				// 不推送给自己
				MessagePush push = new MessagePush();
				push.send2User((Integer)map.get("userId"), content.toString());
			}
		}
	}

	@Override
	@Transactional
	public void deleteApiById(Integer id) {
		Interface api = getApiByID(id);
		if(api.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())){
			deleteApiWsByApiid(id);
		}
		if(sqlSession.delete("interface.deleteById", id) != 1){
			throw new BaseAppException("删除失败,请稍后重试");
		}
	}

	@Override
	public InterfaceWS getApiWsByApiid(Integer apiId) {
		return sqlSession.selectOne("interface.selectApiWsByapiId", apiId);
	}

	@Override
	@Transactional
	public Integer saveOperationApi(PageParam pageParam) {
		String type = pageParam.getString("operation");
		if(!"copy".equals(type) && !"move".equals(type)){
			throw new BaseAppException("未知的操作请求!!");
		}
		// 原接口id
		Integer oldId = Integer.valueOf(pageParam.get("id")+"");
		Integer projectId = Integer.valueOf(pageParam.get("projectId")+"");
		Integer moduleId = Integer.valueOf(pageParam.get("moduleId")+"");
		Integer folderId = Integer.valueOf(pageParam.get("folderId")+"");
		InterfaceWithBLOBs oldApi = getApiByID(oldId);
		oldApi.setProjectid(projectId);
		oldApi.setModuleid(moduleId);
		oldApi.setFolderid(folderId);
		PageParam param = new PageParam();
		param.put(HYConst.SESSION_USER, (User)pageParam.get(HYConst.SESSION_USER));
		if("copy".equals(type)){
			// 复制
			api2PageParam(oldApi, param, false);
		}else{
			// 移动
			api2PageParam(oldApi, param, true);
		}
		return saveApi(param);
	}

	private void api2PageParam(InterfaceWithBLOBs oldApi, PageParam param, boolean b) {
		if(b){
			param.put("id", oldApi.getId());
		}
		param.put("name", oldApi.getName()+"_COPY");
		param.put("description", oldApi.getDescription());
		param.put("example", oldApi.getExample());
		param.put("folderId", oldApi.getFolderid());
		param.put("url", oldApi.getUrl());
		param.put("requestMethod", oldApi.getRequestmethod());
		param.put("contentType", oldApi.getContenttype());
		param.put("requestHeaders", oldApi.getRequestheaders());
		param.put("requestArgs", oldApi.getRequestargs());
		param.put("requestRootElement", oldApi.getRequestrootelement());
		param.put("responseArgs", oldApi.getResponseargs());
		param.put("responseRootElement", oldApi.getResponserootelement());
		param.put("moduleId", oldApi.getModuleid());
		param.put("projectId", oldApi.getProjectid());
		param.put("dataType", oldApi.getDatatype());
		param.put("protocol", oldApi.getProtocol());
		param.put("status", oldApi.getStatus());
		
		if(oldApi.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())){
			InterfaceWS ws = getApiWsByApiid(oldApi.getId());
			param.put("targetNamespace", ws.getTargetnamespace());
			param.put("methodName", ws.getMethodname());
		}
	}

}
