package com.liuht.api.service;

import java.util.List;

import com.liuht.api.common.domain.Team;
import com.liuht.api.common.domain.TeamUser;
import com.liuht.ec.base.service.BaseService;
import com.liuht.ec.base.util.PageParam;

public interface TeamService extends BaseService{
	
	/**
	 * 新增团队
	  * @return void 
	  * @author 刘惠涛
	 */
	void addTeam(PageParam pageParam);

	/**
	 * 查询我的团队
	  * @return List<Team> 
	  * @author 刘惠涛
	 */
	List<Team> getMyTeams(Integer userid);
	
	/**
	 * 添加团队成员
	  * @return void 
	  * @author 刘惠涛
	 */
	void addTeamUser(TeamUser teamUser);

	/**
	 * 删除团队成员
	  * @return void 
	  * @author 刘惠涛
	 */
	void removeTeamUser(TeamUser teamUser);
	
}
