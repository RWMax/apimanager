package com.liuht.ec.base.exception.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.liuht.ec.base.exception.BaseParamException;
import com.liuht.ec.base.exception.BaseUpdateException;
import com.liuht.ec.base.util.HYConst;
import com.liuht.ec.base.util.ResultBean;
import com.liuht.ec.util.WebUtils;

/**
* @package com.liuht.ec.base.exception.resolver
* <p>Title: BaseHandlerExceptionResolver.java</p>
* <p>Description: HY框架异常处理(唯一处理点)</p>
* <p>Copyright: Copyright (c) 2014</p>
*
* @author LIUHUITAO
* @date 2016-1-20 下午1:22:50
* @version 1.0
 */
public class BaseHandlerExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			Exception exception) {
		// logger记录异常
		String msg = getExceptionMsg(exception);
		if (logger.isErrorEnabled()) {
			logger.error(msg);
			logger.error("Catch Exception By Hyman", exception);
		}
		
		// 程序异常处理 异步以及同步
		if (WebUtils.isAjax(httpservletrequest)) {
			// 异步
			ResultBean bean = new ResultBean(true, "异步请求出错,请检查!!");
			try {
				WebUtils.sendJson(httpservletresponse, JSON.toJSONString(bean));
			} catch (IOException e) {
				if (logger.isErrorEnabled()) {
					logger.error("异步请求返回json出错",e);
				}
				if (getDebugModel()){
					e.printStackTrace();
				}
			}
		}else{
			// 同步 如果是开发模式,跳转到异常页面,并且显示异常详细信息
			if (getDebugModel()) {
				exception.printStackTrace();
				StackTraceElement [] elements = exception.getStackTrace();
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < elements.length; i++) {
					builder.append(elements[i].toString());
					if (i != elements.length -1) {
						builder.append("\n");
					}
				}
				httpservletrequest.setAttribute("exception", exception);
				msg = msg == null?"Catch Exception By Hyman":msg;
				httpservletrequest.setAttribute("exceptionDetail", msg +":"+ "\n" +builder.toString());
			}
			return new ModelAndView(HYConst.PAGE_500);
		}
		return new ModelAndView();
	}
	
	/**
	  * @package com.liuht.ec.base.exception.resolver
	  * @method getExceptionMsg 方法 
	  * @describe <p>方法说明:获取异常提示</p>
	  * @return String 
	  * @author LIUHUITAO
	  * @date 2016-1-20
	 */
	private String getExceptionMsg(Exception e) {
		String msg = "";
		if (e instanceof BaseParamException) {
			BaseParamException baseParamException = (BaseParamException) e;
			if (baseParamException.getFiled() == null) {
				msg = baseParamException.getMessage();
			}else{
				msg = "参数:["+baseParamException.getFiled() +"] "+baseParamException.getMessage();
			}
		}else if (e instanceof BaseUpdateException) {
			BaseUpdateException baseUpdateException = (BaseUpdateException) e;
			if (baseUpdateException.getUpdateCount() == null) {
				msg = baseUpdateException.getMessage();
			}else{
				msg = baseUpdateException.getMessage() + "更新数量为:["+baseUpdateException.getUpdateCount()+"]";
			}
		}else{
			msg = e.getMessage() == null ?"Catch Exception: ":e.getMessage();
		}
		return msg;
	}

	public void setDebugModel(Boolean debugModel) {
		this.debugModel = debugModel;
	}
	
	public Boolean getDebugModel() {
		return debugModel;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Boolean debugModel = false;
}
 