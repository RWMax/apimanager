package com.liuht.ec.base.filter;

import com.alibaba.fastjson.JSON;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.ResultBean;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
  
/**
* @package com.liuht.ec.base.filter
* <p>Title: BaseAccessCheckFilter.java</p>
* <p>Description: URL访问权限拦截器</p>
* <p>Copyright: Copyright (c) 2014</p>
*
* @author LIUHUITAO
* @date 2016-1-7 下午4:19:08
* @version 1.0
 */
public class BaseAccessCheckFilter extends AccessControlFilter{
	private static final Logger logger = LoggerFactory.getLogger(com.liuht.ec.base.filter.BaseAccessCheckFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object object) throws Exception {
		HttpServletRequest servletRequest = (HttpServletRequest) request; 
		String path = servletRequest.getServletPath();
		if (path.matches(HYConst.NO_INTERCEPTOR_PATH)) {
			return true;
		}else{
			//shiro管理的session
			if (path.startsWith("/")) {
				path = path.substring(1, path.length());
			}
			path = path.replaceAll("/", ":");
			Subject subject = getSubject(request, response);
			Session session = subject.getSession();
			if (session == null) {
				return false;
			}
			return subject.isPermitted(path);
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest servletRequest = (HttpServletRequest) request; 
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String path = servletRequest.getServletPath();
		Subject subject = getSubject(request, response);
		ResultBean bean = new ResultBean();
		bean.setError(true);
		bean.setError_code(HYConst.ACCESS_DENIED_CODE);
		if (subject.getPrincipal() == null) {  
            if (isAjax(servletRequest)) {
            	bean.setError_msg("您尚未登录或登录时间过长,请重新登录!");
            	sendJson(servletResponse, JSON.toJSONString(bean));
            } else {  
                saveRequestAndRedirectToLogin(request, response);  
            }
            if (logger.isWarnEnabled()) {
    			logger.warn("Session is timeout for request url ["+path+"]");
    		}
        } else {  
            if (isAjax(servletRequest)) { 
            	bean.setError_msg("您没有足够的权限执行该操作!");
            	sendJson(servletResponse, JSON.toJSONString(bean));
            } else {  
            	WebUtils.issueRedirect(request, response, HYConst.ACCESS_DENIED);
            }
            if (logger.isWarnEnabled()) {
    			logger.warn("Access denied for url ["+path+"]");
    		}
        }
		return false;
	}
	
	private Boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())) ;
	}
	
	private void sendJson(HttpServletResponse response,String json) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}
}
 