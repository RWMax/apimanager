package com.yinhai.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinhai.api.service.EnvService;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
@Controller
@RequestMapping("/env")
@SuppressWarnings({ "rawtypes" })
public class EnvController extends BaseController{
	
	@Autowired
	private EnvService envService;
	
	/**
	 * 添加环境
	 * @param request
	 * @return
	 */
	@RequestMapping("/addEnv")
	@ResponseBody
	public ResultBean addEnv(HttpServletRequest request){
		PageParam param = getPageParam(request);
		ResultBean bean;
		try {
			bean =  envService.insertEnv(param);
		} catch (Exception e) {
			bean = getResultBean();
			bean.setError(true);
			if(e instanceof BaseAppException){
				bean.setError_msg(e.getMessage());
			}else{
				bean.setError_msg("添加失败");
			}
		}
		return bean;
	}
	/**
	 * 查询环境列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEnvList")
	@ResponseBody
	public Map getEnvList(HttpServletRequest request){
		PageParam param = getPageParam(request);
		return envService.getEnvList(param);
	}
	@RequestMapping("/deleteEnv/{envId}")
	@ResponseBody
	public  ResultBean deleteEnv(@PathVariable final String envId,HttpServletRequest request){
		ResultBean bean;
		try {
			PageParam param =getPageParam(request);
			param.put("envId", envId);
			bean = envService.deleteEnv(param);
		} catch (Exception e) {
			bean = getResultBean();
			bean.setError(true);
			if(e instanceof BaseAppException){
				bean.setError_msg(e.getMessage());
			}else{
				bean.setError_msg("删除失败,请重试");
			}
		}
		return bean;
	}
	/**
	 * 跳转编辑页面
	 * @return
	 */
	@RequestMapping("/editPage/{envId}")
	public String toEnvEditPage(@PathVariable final String envId,HttpServletRequest request){
		PageParam param = new PageParam();
		param.put("envId",envId);
		List list = envService.getEnvByEnvid(param);
		request.setAttribute("list", list);
		//编辑页面
		request.setAttribute("name", ((Map)list.get(0)).get("name").toString());
		request.setAttribute("envid", envId);
		return "/api/edit_environment.jsp";
	}
	/**
	 * 修改环境
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateEnv")
	@ResponseBody
	public ResultBean updateEnv(HttpServletRequest request){
	    PageParam param = getPageParam(request);
		ResultBean bean;
		try {
			bean = envService.updateEnvByEnvid(param);
		} catch (Exception e) {
			bean = getResultBean();
			bean.setError(true);
			if(e instanceof BaseAppException){
				bean.setError_msg(e.getMessage());
			}else{
				bean.setError_msg("修改失败,请重试");
			}
		}
		return bean;
	}
	/**
	 * 环境拷贝
	 * @param request
	 * @return
	 */
	@RequestMapping("/copyEnv")
	@ResponseBody
	public ResultBean copyEnv(HttpServletRequest request){
		PageParam param = getPageParam(request);
		ResultBean bean;
		try {
			bean = envService.addEnvCopy(param);
		} catch (Exception e) {
			bean = getResultBean();
			bean.setError(true);
			bean.setError_msg("复制失败,请重试");
		}
		return bean;
	}
}
