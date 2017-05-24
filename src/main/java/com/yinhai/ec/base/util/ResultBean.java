package com.yinhai.ec.base.util;  

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
/**
* @package com.yinhai.ec.base.util
* <p>Title: ResultBean.java</p>
* <p>Description: 用于请求返回数据时统一使用</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 久远银海</p>
* @author LIUHUITAO
* @date 2016-1-8 下午3:35:53
* @version 1.0
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ResultBean implements Serializable{
	private static final long serialVersionUID = 5830857194872732536L;
	protected boolean error;
	protected String error_msg;
	protected String error_code;
	protected String success_msg;
	protected Map lists;
	protected String tipMsg;
	protected String focus;
	protected String uploadedPath; //上传之后的文件路劲
	
	public ResultBean() {
		this.error = false;
	}

	public ResultBean(boolean flag) {
		this.error = flag;
	}

	public ResultBean(boolean flag, String msg) {
		this.error = flag;
		if(flag){
			this.error_msg = msg;
		}else{
			this.success_msg = msg;
		}
	}
	
	public ResultBean(boolean flag,String error_code,String msg) {
		this.error = flag;
		if(flag){
			this.error_msg = msg;
			this.error_code = error_code;
		}else{
			this.success_msg = msg;
		}
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getSuccess_msg() {
		return success_msg;
	}

	public void setSuccess_msg(String success_msg) {
		this.success_msg = success_msg;
	}

	public Map getLists() {
		return lists;
	}

	public void setLists(Map lists) {
		this.lists = lists;
	}
	
	public void setList(String name,List list) {
		if (this.lists == null) {
			this.lists = new HashMap<String, Object>();
		}
		this.lists.put(name, list);
	}

	public String getTipMsg() {
		return tipMsg;
	}

	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}
	
	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getUploadedPath() {
		return uploadedPath;
	}

	public void setUploadedPath(String uploadedPath) {
		this.uploadedPath = uploadedPath;
	}
}
 