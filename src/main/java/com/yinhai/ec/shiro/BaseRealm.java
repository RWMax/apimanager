package com.yinhai.ec.shiro;  

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.yinhai.api.common.domain.User;
import com.yinhai.api.service.UserService;
import com.yinhai.ec.base.util.HYConst;
  
/**
* @package com.yinhai.ec.base.shiro.realm
* <p>Title: BaseRealm.java</p>
* <p>Description: shiro Realm 用以注入管理用户认证以及用户相关权限</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 久远银海</p>
* @author LIUHUITAO
* @date 2015-12-31 上午10:04:12
* @version 1.0
 */
public class BaseRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	
	@Override
	public String getName() {
		return "API-Realm";
	}
	
	/**
	 * 用户认证 登陆认证 只执行一次
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 String username = (String)token.getPrincipal();
	     Map param = new HashMap();
	     param.put("email", username);
	     User user = userService.queryUser(param);
	     if(user == null){
	    	 throw new AccountException("用户不存在");
	     }else{
	    	 String status = user.getStatus();
	    	 if(HYConst.AccountStatus.DISABLE.equals(status)){
	    		 throw new LockedAccountException("账号被禁用");
	    	 }
	     }
	     return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
	}
	
	/**
	 * 权限注入
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalcollection) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("*");
		return info;
	}
}
 