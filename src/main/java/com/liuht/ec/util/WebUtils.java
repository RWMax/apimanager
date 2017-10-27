package com.liuht.ec.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
  
public class WebUtils {
	
	/**
	  * @package com.liuht.ec.base.util
	  * @method isAjax 方法 
	  * @describe <p>方法说明:判断是否是Ajax请求</p>
	  * @return Boolean 
	  * @author LIUHUITAO
	  * @date 2016-1-7
	 */
	public static Boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())) ;
	}
	
	/**
	  * @package com.liuht.ec.base.util
	  * @method sendJson 方法 
	  * @describe <p>方法说明:发送json字符串到客户端</p>
	  * @return void 
	  * @author LIUHUITAO
	 * @throws IOException 
	  * @date 2016-1-7
	 */
	public static void sendJson(HttpServletResponse response,String json) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}
}
 