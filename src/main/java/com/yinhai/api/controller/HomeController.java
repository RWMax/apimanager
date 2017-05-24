package com.yinhai.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yinhai.api.common.domain.User;
import com.yinhai.api.service.ProjectService;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.util.StringUtils;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping()
	public String home(HttpServletRequest request, ModelMap map) {
		String iframesrc = request.getParameter("iframesrc");
		if(!StringUtils.isEmpty(iframesrc)){
			map.put("iframesrc", iframesrc);
		}
		User user = getCurrentSessionUser();
		List<Map<String, Object>> myProjects = projectService.getMyProjects(user.getId());
		List<Map<String, Object>> teamProjects = projectService.getMyTeamProjects(user.getId());
		List<Map<String, Object>> publicProjects = projectService.getPublicProjects(user.getId());
		map.put("myProjects", myProjects);
		map.put("teamProjects", teamProjects);
		map.put("publicProjects", publicProjects);
		map.put("user", getCurrentSessionUser());
		return "/index.jsp";
	}
	
}
