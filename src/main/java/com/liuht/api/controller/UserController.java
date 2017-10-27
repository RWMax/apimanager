package com.liuht.api.controller;

import com.liuht.api.common.domain.User;
import com.liuht.api.service.UserService;
import com.liuht.ec.base.controller.BaseController;
import com.liuht.ec.base.exception.BaseAppException;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.PageParam;
import com.liuht.ec.base.util.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * 个人中心
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;

	@RequestMapping("/personalIndex")
	public String personalIndex(ModelMap map){
		
		return "/api/personalCenter/personal_index.jsp";
	}
	
	@RequestMapping("/personalInfo")
	public String personalInfo(ModelMap map){
		//查询个人信息
		Integer id = getUserId();
		User user = userService.getUserInfo(id);
		Date date = user.getCreatetime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("user",userService.getUserInfo(id));
		map.put("createtime", df.format(date));
		return "/api/personalCenter/personal_info.jsp";
	}
	@RequestMapping("/personalSecurity")
	public String personalSecurity(ModelMap map){
		//查询个人信息
		Integer id = getUserId();
		User user = userService.getUserInfo(id);
		map.put("email",user.getEmail());
		return "/api/personalCenter/personal_security.jsp";
	}
	
	/**
	 * 修改用户昵称
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateName")
	@ResponseBody
	public ResultBean setUserName(HttpServletRequest request){
		PageParam param = getPageParam(request);
		param.put("id", getUserId());
		ResultBean bean  = getResultBean();
		try {
			userService.updateName(param);
			User user = getCurrentSessionUser();
			user.setNickname(param.getString("nickname"));
			SecurityUtils.getSubject().getSession(false).setAttribute(HYConst.SESSION_USER, user);
			bean.setError(false);
			bean.setSuccess_msg("修改成功");
		} catch (Exception e) {
			bean.setError(true);
			if(e instanceof BaseAppException){
				bean.setError_msg(e.getMessage());
			}else{
				bean.setError_msg("修改失败");
			}
		}
		return bean;
	}
	/**
	 * 修改用户邮箱
	 * @param request
	 * @return
	 */
	@RequestMapping("/doUpdate")
	@ResponseBody
	public ResultBean doUpdate(HttpServletRequest request){
		ResultBean bean = getResultBean();
		try {
			PageParam param = getPageParam(request);
			param.put("id", getUserId());
			userService.updateEmail(param);
			User user = getCurrentSessionUser();
			user.setEmail(param.getString("email"));
			SecurityUtils.getSubject().getSession(false).setAttribute(HYConst.SESSION_USER, user);
			bean.setError(false);
			bean.setSuccess_msg("修改成功");
		} catch (Exception e) {
			bean.setError(true);
			if(e instanceof BaseAppException){
				bean.setError_msg(e.getMessage());
			}else{
				bean.setError_msg("修改失败");
			}
		}
		return bean;
	}
	@RequestMapping("/updatePwd")
	@ResponseBody
	public ResultBean updatePwd(HttpServletRequest request){
		ResultBean bean = getResultBean();
		try {
			PageParam param = getPageParam(request);
			param.put("id",getUserId());
			userService.updatePwdById(param);
			bean.setError(false);
			bean.setSuccess_msg("修改成功");
		} catch (Exception e) {
			bean.setError(true);
			if(e instanceof BaseAppException){
				bean.setError_msg(e.getMessage());
			}else{
				bean.setError_msg("操作失败");
			}
		}
		return bean;
	}
}
