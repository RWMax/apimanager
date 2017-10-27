package com.liuht.api.controller;

import com.liuht.api.common.domain.User;
import com.liuht.api.service.UserService;
import com.liuht.ec.base.controller.BaseController;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.PageParam;
import com.liuht.ec.base.util.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		
		return HYConst.LOGIN;
	}
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public ResultBean doLogin(HttpServletRequest request) {
		PageParam param = getPageParam(request);
		ResultBean rb = getResultBean();
		try {
			Subject subject = getShiroSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(param.getString("email"), param.getString("password"));
			subject.login(token);
			SecurityUtils.getSubject().getSession(false).setAttribute(HYConst.SESSION_USER, userService.queryUser(param));
			param = null;
			rb.setError(false);
		} catch (AuthenticationException e) {
			param = null;
			rb.setError(true);
			if(e instanceof IncorrectCredentialsException){
				rb.setError_msg("邮箱或密码不正确");
			}else{
				String msg = e.getMessage();
				if(msg != null){
					rb.setError_msg(msg);
				}else{
					rb.setError_msg("身份验证未通过,不能登录");
				}
				getLogger().error("login failed: {}",e.getMessage());
			}
		}
		return rb;
	}
	
	@RequestMapping("/logout")
	public String logout() {
		Subject subject = getShiroSubject();
		Session session = subject.getSession();
		if (session != null && session.getAttribute(HYConst.SESSION_USER) != null) {
			User user = (User) session.getAttribute(HYConst.SESSION_USER);
			subject.logout();
			getLogger().debug("用户["+user.getNickname()+"] 成功退出系统");
		}
		return "redirect:/login";
	}
}
