package com.yinhai.ec.base.util;  

/**
* @package com.yinhai.ec.base.util
* <p>Title: HYConst.java</p>
* <p>Description: HY常量</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 久远银海</p>
* @author LIUHUITAO
* @date 2015-12-31 上午9:58:43
* @version 1.0
 */
public class HYConst {
	/*** status 1有效*/
	public static final Integer STATUS_YES = 1;
	/*** status 0无效*/
	public static final Integer STATUS_NO = 0;
	/*** USER_LOCK 1未锁定*/
	public static final Integer USER_LOCK_NO = 1;
	/*** USER_LOCK 0锁定*/
	public static final Integer USER_LOCK_YES = 0;
	/*** 不对匹配该值的访问路径拦截（正则） */
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(websocket)).*";
	/*** 当前人员sessionkey */
	public static final String SESSION_USER = "CURRENT_SESSION_USER";
	/*** 默认登陆地址 */
	public static final String LOGIN = "/login.jsp";
	public static final String REGISTER = "/register.jsp";
	public static final String INDEX = "/index";
	/*** 没有访问权限时 跳转的地址 */
	public static final String ACCESS_DENIED = "/UI/accessdenied.jsp";
	/*** 锁屏地址 */
	public static final String LOCKSCREEN = "/UI/lockscreen.jsp";
	/*** 系统设置 跳转的地址 */
	public static final String SYSTEM_SETTINGS = "/UI/settings.jsp";
	/*** 系统设置 跳转的地址 */
	public static final String PAGE_500 = "/UI/500.jsp";
	/*** 系统设置 跳转的地址 */
	public static final String PAGE_404 = "/UI/404.jsp";
	/*** 用户权限菜单 */
	public static final String USER_PERMISSION_URLS = "user_permission_urls";
	/*** 用户岗位 */
	public static final String USER_ROLES = "user_roles";
	/*** 强制退出key */
	public static final String SESSION_FORCE_LOGOUT_KEY = "session_force_logout";
	/*** 重复登录key */
	public static final String SESSION_REPEAT_LOGIN_KEY = "session_is_repeat_login";
	/*** 用户屏幕锁定 */
	public static final String USER_SCREEN_LOCK = "USER_SCREEN_LOCK";
	/*** 系统管理员专属验证角色字符串*/
	public static final String ADMIN_CHECK_STRING = "admin";
	/*** 系统管理员专属验证角色字符串*/
	public static final String ADMINISTRATOR_CHECK_STRING = "administrator";
	/*** 无权限code 401 */
	public static final String ACCESS_DENIED_CODE = "401";
	/*** 每个url的默认显示界面的地址 */
	public static final String PATH_DEFAULT = "default";
	/*** 默认码表ehcache缓存名称 */
	public static final String BASE_CODE_CACHE_NAME = "baseCodeCache";
	/***找回密码地址*/
	public static final String FIND_PASSWORD_URL = "/u/forwardPwdpage";
	/**人员状态 项目状态*/
	public enum AccountStatus {
		DISABLE { @Override
		public String getName(){ return "DISABLE" ;}},
		ENABLE { @Override
		public String getName() { return "ENABLE"; }};
		
		public abstract String getName();
	}
	/**项目成员状态*/
	public enum ProUserStatus {
		NORMAL  { @Override
		public String getName(){ return "NORMAL" ;}},
		OWNER { @Override
		public String getName() { return "OWNER"; }},
		TEAM { @Override
		public String getName() { return "TEAM"; }};
		public abstract String getName();
	}
	/**YesOrNo*/
	public enum YesOrNo {
		YES { @Override
		public String getName(){ return "YES" ;}},
		NO { @Override
		public String getName() { return "NO"; }};
		
		public abstract String getName();
	}
	/**项目可见度*/
	public enum ProjectPermission {
		PRIVATE { @Override
		public String getName(){ return "PRIVATE" ;}},
		PUBLIC { @Override
		public String getName() { return "PUBLIC"; }};
		
		public abstract String getName();
	}
	/**团队成员类型*/
	public enum TeamUserType {
		OWNER { @Override
		public String getName(){ return "OWNER" ;}},
		MEMBER { @Override
		public String getName() { return "MEMBER"; }};
		
		public abstract String getName();
	}
	/**编辑模式 or 浏览模式*/
	public enum EditORView {
		EDIT { @Override
		public String getName(){ return "edit" ;}},
		VIEW { @Override
		public String getName() { return "view"; }};
		
		public abstract String getName();
	}
	
	/**编辑模式 or 浏览模式*/
	public enum Protocol {
		HTTP { @Override
		public String getName(){ return "HTTP" ;}},
		WEBSERVICE { @Override
			public String getName(){ return "WEBSERVICE" ;}},
		SOCKET { @Override
				public String getName(){ return "SOCKET" ;}},
		HTTPS { @Override
		public String getName() { return "HTTPS"; }};
		
		public abstract String getName();
	}
	
	/**编辑模式 or 浏览模式*/
	public enum DataType {
		JSON { @Override
		public String getName(){ return "JSON" ;}},
		XML { @Override
			public String getName(){ return "XML" ;}},
		HTML { @Override
				public String getName(){ return "HTML" ;}},
		JSONP { @Override
		public String getName() { return "JSONP"; }},
		TEXT { @Override
			public String getName() { return "TEXT"; }};
		
		public abstract String getName();
	}
}
 