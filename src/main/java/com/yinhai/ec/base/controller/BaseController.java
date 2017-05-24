package com.yinhai.ec.base.controller;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yinhai.api.common.domain.User;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;


/**
* @package com.yinhai.ec.base.controller
* <p>Title: BaseController.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 四川久远银海软件股份有限公司</p>
* @author 刘惠涛
* @date 2016年1月25日 下午2:09:33
* @version 1.0
 */
public class BaseController {
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getModelAndView 方法 
	  * @describe <p>方法说明:获取视图对象</p>
	  * @return ModelAndView 
	  * @author LIUHUITAO
	  * @date 2015-12-31
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	public PageParam getPageParam(HttpServletRequest request) {
		PageParam pageParam = new PageParam(request);
		return pageParam;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getResultBean 方法 
	  * @describe <p>方法说明:异步方法需要返回的标准response bean</p>
	  * @return ResultBean 
	  * @author LIUHUITAO
	  * @date 2016-1-8
	 */
	public ResultBean getResultBean() {
		return new ResultBean();
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getRequest 方法 
	  * @describe <p>方法说明:获取request对象</p>
	  * @return HttpServletRequest 
	  * @author LIUHUITAO
	  * @date 2015-12-31
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getResponse 方法 
	  * @describe <p>方法说明:获取response对象</p>
	  * @return HttpServletResponse 
	  * @author 刘惠涛
	  * @date 2016年4月7日 上午10:14:34
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
		return response;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getShiroSecurityManager 方法 
	  * @describe <p>方法说明:获取ShiroSecurityManager</p>
	  * @return SecurityManager 
	  * @author 刘惠涛
	  * @date 2016年3月22日 上午10:58:17
	 */
	public SecurityManager getShiroSecurityManager() {
		return SecurityUtils.getSecurityManager(); 
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getShiroSubject 方法 
	  * @describe <p>方法说明:获取shiro管理的subject</p>
	  * @return Session 
	  * @author LIUHUITAO
	  * @date 2016-1-7
	 */
	public Subject getShiroSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getShiroSession 方法 
	  * @describe <p>方法说明:获取shiro管理的session</p>
	  * @return Session 
	  * @author LIUHUITAO
	  * @date 2016-1-7
	 */
	public User getCurrentSessionUser() {
		return (User)getShiroSubject().getSession(false).getAttribute(HYConst.SESSION_USER);
	}
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getUserId 方法 
	  * @describe <p>方法说明:获取shiro管理的session userid</p>
	  * @return Integer 
	  * @author LIUHUITAO
	  * @date 2016-1-7
	 */
	public Integer getUserId(){
		return getCurrentSessionUser().getId();
	}
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getUserName 方法 
	  * @describe <p>方法说明:获取shiro管理的session username</p>
	  * @return Integer 
	  * @author LIUHUITAO
	  * @date 2016-1-7
	 */
	public String getUserName(){
		return getCurrentSessionUser().getEmail();
	}
	public static Logger getLogger() {
		return logger;
	}
	
	public static Logger getLogger(Class<?> class1) {
		return LoggerFactory.getLogger(class1);
	}
	
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
}
 