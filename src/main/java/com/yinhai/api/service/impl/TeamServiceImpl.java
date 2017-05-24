package com.yinhai.api.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinhai.api.common.domain.Team;
import com.yinhai.api.common.domain.TeamUser;
import com.yinhai.api.service.TeamService;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.util.StringUtils;

@Service
public class TeamServiceImpl extends BaseServiceImpl implements TeamService{

	@Transactional()
	@Override
	public void addTeam(PageParam pageParam) {
		if(!pageParam.containsKey("name") || StringUtils.isEmpty(pageParam.get("name"))){
			throw new BaseAppException("请填写组织名称");
		}
		String name = pageParam.getString("name");
		Integer count = queryCountByName(name);
		if(count > 0){
			throw new BaseAppException("团队已经存在啦,另外取个名吧");
		}
		Team team = new Team();
		team.setName(pageParam.getString("name"));
		team.setDescription(pageParam.getString("description"));
		team.setUserid(Integer.valueOf(pageParam.getString("userid")));
		sqlSession.insert("team.insertTeam",team);
		
		// 新增团队成员
		TeamUser teamUser = new TeamUser();
		teamUser.setUserid(team.getUserid());
		teamUser.setTeamid(team.getId());
		teamUser.setTeamusertype(HYConst.TeamUserType.OWNER.getName());
		addTeamUser(teamUser);
	}

	@Override
	@Transactional()
	public void addTeamUser(TeamUser teamUser) {
		sqlSession.insert("team.insertTeamUser",teamUser);
	}

	private Integer queryCountByName(String name) {
		return sqlSession.selectOne("team.selectCountByName", name);
	}

	@Override
	public List<Team> getMyTeams(Integer userid) {
		if(userid == null){
			return Collections.emptyList();
		}
		return sqlSession.selectList("team.getMyTeams", userid);
	}

	@Override
	public void removeTeamUser(TeamUser teamUser) {
		if(teamUser.getTeamid() == null || teamUser.getUserid() == null){
			throw new BaseAppException("删除团队成员失败");
		}
		sqlSession.delete("team.deleteTeamUser", teamUser);
	}
	
}
