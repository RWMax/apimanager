package com.yinhai.api.service;

import java.util.Map;

import com.yinhai.api.common.domain.User;
import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;

@SuppressWarnings("rawtypes")
public interface UserService extends BaseService{

	/**
	 * 查询用户
	 * @param param	 
	 *  @return
	 */
	public User queryUser(Map param);
	/**
	 * 用户注册
	 * @param param
	 * @return
	 */
	public ResultBean insertUser(Map param);
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	public User getUserInfo(Integer id);
	/**
	 * 更新用户昵称
	 * @param param
	 */
	public void updateName(PageParam param);
	/**
	 * 更新邮箱
	 * @param param
	 */
	public void updateEmail(PageParam param);
	/**
	 * 找回密码
	 * @param param
	 * @return
	 */
	public void findPwd(PageParam param);
	/**
	 * 检查token是否可用
	 * @param param
	 * @return
	 */
	public int checkToken(PageParam param);
	/**
	 * 修改密码
	 * @param param
	 */
	public void updatePwd(PageParam param);
	/**
	 * 修改密码
	 * @param param
	 */
	public void updatePwdById(PageParam param);
}
