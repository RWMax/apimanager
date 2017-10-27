package com.liuht.api.controller;

import com.liuht.api.service.UserService;
import com.liuht.ec.base.controller.BaseController;
import com.liuht.ec.base.exception.BaseAppException;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.PageParam;
import com.liuht.ec.base.util.ResultBean;
import com.liuht.ec.shiro.EndecryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/u")
public class RegisterController extends BaseController{
	@Autowired
	private UserService userService;
	@RequestMapping("/register")
	public String login() {
		
		return HYConst.REGISTER;
	}
	/**
	 * 新用户注册
	 * @param request
	 * @return Map 注册结果
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public ResultBean doRegister(HttpServletRequest request){
		try {
			PageParam param = getPageParam(request);
			param.put("password", EndecryptUtils.md5Password(param.getString("email"),param.getString("password")));
			return userService.insertUser(param);
		} catch (Exception e) {
			ResultBean r = getResultBean();
			r.setError(true);
			r.setError_msg("注册失败");
			return r;
		}
	}
	@RequestMapping("/confirm")
	public String toFindPage(){
		return "/api/personalCenter/confirm_email.jsp";
	}
	
	@RequestMapping("/findPwd")
	@ResponseBody
	public ResultBean findPassword(HttpServletRequest request){
		ResultBean bean = getResultBean();
		try {
			PageParam param = getPageParam(request);
			param.put("project",request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath());
			userService.findPwd(param);
			bean.setError(false);
			bean.setSuccess_msg("邮件已发送,请前往邮箱修改密码");
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
	@RequestMapping("/forwardPwdpage")
	public String findPwdPage(HttpServletRequest request,ModelMap map){
		PageParam param = getPageParam(request);
		if(!param.containsKey("token")){
			return "/UI/500.jsp";
		}
		int i = userService.checkToken(param);
		if(i == 0){
			return "/UI/500.jsp";
		}else{
			map.put("token", param.getString("token"));
			return "/api/personalCenter/change_password.jsp";
		}
	}
	@RequestMapping("/updatePwd")
	@ResponseBody
	public ResultBean updatePwd(HttpServletRequest request){
		ResultBean bean = getResultBean();
		try {
			PageParam param = getPageParam(request);
			userService.updatePwd(param);
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
