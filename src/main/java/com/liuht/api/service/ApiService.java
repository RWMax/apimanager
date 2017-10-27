package com.liuht.api.service;

import com.liuht.api.common.domain.Interface;
import com.liuht.api.common.domain.InterfaceFolder;
import com.liuht.api.common.domain.InterfaceWS;
import com.liuht.api.common.domain.InterfaceWithBLOBs;
import com.liuht.ec.base.service.BaseService;
import com.liuht.ec.base.util.PageParam;

import java.util.List;
import java.util.Map;

public interface ApiService extends BaseService{

	/**
	 * 删除folders
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteFolders(Integer projectId, Integer moduleId);
	
	/**
	 * 删除folder
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteFolder(Integer folderId);
	
	/**
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteApis(Integer folderId);


	/**
	 * 根据folderid查询folder
	  * @return InterfaceFolder 
	  * @author 刘惠涛
	 */
	InterfaceFolder getFolderById(Integer folderId);


	/**
	 * 添加分类
	  * @return void 
	  * @author 刘惠涛
	 */
	void saveFolder(PageParam pageParam);


	/**
	 * 根据id查询接口
	  * @return InterfaceWithBLOBs 
	  * @author 刘惠涛
	 */
	InterfaceWithBLOBs getApiByID(Integer id);


	/**
	 * 根据分类查询接口
	  * @return List<Interface> 
	  * @author 刘惠涛
	 */
	List<Interface> queryApisByFolderId(Integer folderId);

	/**
	 * 根据分类查询接口(含所有字段)
	 * @param folderId 分类id
	 * @return List<InterfaceWithBLOBs>
     */
	List<InterfaceWithBLOBs> queryApiBlobsByFolderId(Integer folderId);

	/**
	 * 根据分类查询接口(含所有字段)
	 * @param folderId 分类id
	 * @return List<Map<String,Object>>
     */
	List<Map<String,Object>> queryApiBlobList(Integer folderId);

	/**
	 * 添加 更新接口
	  * @return void 
	  * @author 刘惠涛
	 */
	Integer saveApi(PageParam pageParam);

	/**
	 * 根据id 删除API
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteApiById(Integer id);

	/**
	 * 删除接口 webservice部分
	  * @return void 
	  * @author 刘惠涛
	 */
	void deleteApiWsByApiid(Integer apiId);
	
	/**
	 * 查询接口 webservice部分
	  * @return InterfaceWS 
	  * @author 刘惠涛
	 */
	InterfaceWS getApiWsByApiid(Integer apiId);

	/**
	 * 保存复制移动api
	  * @return Integer 
	  * @author 刘惠涛
	 */
	Integer saveOperationApi(PageParam pageParam);
}
