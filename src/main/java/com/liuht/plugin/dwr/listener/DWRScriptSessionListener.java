package com.liuht.plugin.dwr.listener;

import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liuht.api.common.domain.User;
import com.liuht.ec.base.util.HYConst;

/**
* @package com.liuht.ec.demo.dwr.listener
* <p>Title: DWRScriptSessionListener.java</p>
* <p>Description: DWR推送 ScriptSession监听</p>
* @author 刘惠涛
* @date 2016年7月27日 下午5:15:44
* @version 1.0
 */
public class DWRScriptSessionListener implements ScriptSessionListener{
	private static final Logger LOG = LoggerFactory.getLogger(DWRScriptSessionListener.class);

	@Override
	public void sessionCreated(ScriptSessionEvent scriptsessionevent) {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		ScriptSession scriptSession = scriptsessionevent.getSession();
		if(session.getAttribute(HYConst.SESSION_USER) != null) {
			User user = (User) session.getAttribute(HYConst.SESSION_USER);
			scriptSession.setAttribute(HYConst.SESSION_USER, user);
			LOG.debug("成功向scriptSession中添加用户信息");
		}
	}

	@Override
	public void sessionDestroyed(ScriptSessionEvent scriptsessionevent) {
		
	}
}
