package com.liuht.api.controller;

import com.liuht.api.service.TeamService;
import com.liuht.ec.base.controller.BaseController;
import com.liuht.ec.base.exception.BaseAppException;
import com.liuht.ec.base.util.PageParam;
import com.liuht.ec.base.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
* @package com.liuht.api.controller
* <p>Title: TeamController.java</p>
* <p>团队管理 团队成员管理</p>
* @author 刘惠涛
* @date 2016年11月24日 下午5:14:12
 */
@Controller
@RequestMapping("/team")
public class TeamController extends BaseController{
	@Autowired
	private TeamService teamService;
	
	@RequestMapping("/add")
	@ResponseBody
	public ResultBean addTeam(HttpServletRequest request) {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		try {
			pageParam.put("userid", getUserId()+"");
			teamService.addTeam(pageParam);
		} catch (BaseAppException e) {
			bean.setError(true);
			bean.setError_msg(e.getMessage());
		}
		return bean;
	}
}
