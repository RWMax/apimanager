package com.yinhai.plugin.dwr.message;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import com.yinhai.api.common.domain.User;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.plugin.dwr.model.BaseMessageModel;

/**
* @package com.yinhai.plugin.dwr.message
* <p>Title: MessagePush.java</p>
* <p>Description: DWR 反向推送例子</p>
* @author 刘惠涛
* @date 2016年7月28日 上午11:50:31
* @version 1.0
 */
public class MessagePush extends BaseMessageModel{
	
	/**
	  * @package com.yinhai.plugin.dwr.message
	  * @method onPageLoad 方法 
	  * @describe <p>方法说明:在打开jsp页面时,调用该方法,在ScriptSession中设定自己需要的值</p>
	  * @return void 
	  * @author 刘惠涛
	  * @date 2016年7月28日 上午11:50:27
	 */
	@Override
	public void onPageLoad(final String key, final String value){
        //获取当前的ScriptSession
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        scriptSession.setAttribute(key, value);
	}
	
	/**
	  * @package com.yinhai.plugin.dwr.message
	  * @method send2All 方法 
	  * @describe <p>方法说明:向所有ScriptSession客户端发送消息</p>
	  * @return void 
	  * @author 刘惠涛
	  * @date 2016年7月28日 上午11:51:58
	 */
	public void send2All(final String content) {
		// 执行推送(全部scriptSession)
		Browser.withAllSessions(new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			@Override
			public void run() {
				// 设置要调用的 js及参数
				script.appendCall("showMessage", content);
				// 得到所有ScriptSession
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				// 遍历每一个ScriptSession
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
	
	/**
	 * 想指定单个浏览器用户推送消息
	  * @return void 
	  * @author 刘惠涛
	 */
	public void send2User(final Integer userId, final String content) {
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			@Override
			public boolean match(ScriptSession scriptsession) {
				if(scriptsession.getAttribute(HYConst.SESSION_USER) != null){
					User user = (User) scriptsession.getAttribute(HYConst.SESSION_USER);
					if(userId == user.getId()){
						return true;
					}
				}
				return false;
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			@Override
			public void run() {
				script.appendCall("showMessage", content);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
