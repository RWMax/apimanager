package com.yinhai.api.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinhai.api.common.domain.User;
import com.yinhai.api.service.EmailService;
import com.yinhai.api.service.UserService;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.shiro.EndecryptUtils;
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private EmailService emailService;
	@Override
	public User queryUser(Map param) {
		
		return (User)sqlSession.selectOne("api_user.getUser", param);
		
	}

	@Transactional
	@Override
	public ResultBean insertUser(Map param) {
		ResultBean rb = new ResultBean();
		//检查用户名是否存在
		int i = Integer.valueOf(sqlSession.selectOne("api_user.getEmail",param).toString());
		if(i != 0){
			param = null;
			rb.setError(true);
			rb.setError_msg("邮箱已被注册");
		}else{
			sqlSession.insert("api_user.insertUser", param);
			param = null;
			rb.setError(false);
			rb.setSuccess_msg("注册成功");
		}
		return rb;
	}

	@Override
	public User getUserInfo(Integer id) {
		
		return (User)sqlSession.selectOne("api_user.getUserInfo", id);
	}

	@Override
	@Transactional
	public void updateName(PageParam param) {
		if(param.containsKey("password")){
			param.remove("password");
		}
		int count = sqlSession.update("api_user.updateUser", param);
		if(count != 1){
			throw new BaseAppException("修改失败");
		}
	}

	@Override
	@Transactional
	public void updateEmail(PageParam param) {
		if(!param.containsKey("email") || "".equals(param.get("email").toString())){
			throw new BaseAppException("参数错误");
		}
		if(!param.containsKey("password") || "".equals(param.get("password").toString())){
			throw new BaseAppException("参数错误");
		}
		if(!param.containsKey("pwd") || "".equals(param.get("pwd").toString())){
			throw new BaseAppException("参数错误");
		}
		String password = param.getString("password");
		String pwd = param.getString("pwd");
		String email = param.getString("email");
		param.remove("email");
		if(password.equals(pwd)){
			//检查密码是否匹配
			User user = sqlSession.selectOne("api_user.getUser", param);
			if(email.equals(user.getEmail())){
				throw new BaseAppException("你没有做任何修改");
			}else{
				if(EndecryptUtils.md5Password(user.getEmail(), password).equals(user.getPassword())){
					//检查邮箱是否重复
					param.put("email", email);
					int i = Integer.valueOf(sqlSession.selectOne("api_user.getEmail",param).toString());
					if(i != 0){
						throw new BaseAppException("邮箱已存在");
					}else{
						//修改邮箱
						param.put("password", EndecryptUtils.md5Password(email, password));
						int count = sqlSession.update("api_user.updateUser", param);
						if(count != 1){
							throw new BaseAppException("修改失败");
						}
					}
				}else{
					throw new BaseAppException("密码验证失败");
				}
			}
		}else{
			throw new BaseAppException("两次输入的密码不一致");
		}
	}

	@Override
	@Transactional
	public void findPwd(PageParam param) {
		if(!param.containsKey("email") || "".equals(param.get("email").toString())){
			throw new BaseAppException("参数错误");
		}
		User user = sqlSession.selectOne("api_user.getUser",param);
		String token_str = "";
		if(user == null){
			throw new BaseAppException("邮箱不存在");
		}else{
			Map map = new HashMap();
			map.put("userid", user.getId());
			Map  token = sqlSession.selectOne("api_user.checkToken", map);
			if(token == null){
				//新增
				map.put("status", 0);
				token_str = EndecryptUtils.md5Password(user.getId().toString(),UUID.randomUUID() + String.valueOf(System.currentTimeMillis()));
				map.put("token", token_str);
				sqlSession.insert("api_user.insertToken", map);
			}else{
				Integer status = Integer.valueOf(token.get("status").toString());
				if(status == 0){//有效
					token_str = token.get("token").toString();
				}else{//无效
					token_str = EndecryptUtils.md5Password(user.getId().toString(),UUID.randomUUID() + String.valueOf(System.currentTimeMillis()));
					map.put("token", token_str);
					int i = sqlSession.update("api_user.updateToken", map);
					if(i !=1){
						throw new BaseAppException("操作失败");
					}
				}
			}
		}
		Map model = new HashMap();
		model.put("username",user.getNickname());
		model.put("url", param.getString("project") +HYConst.FIND_PASSWORD_URL + "?token="+token_str);
		emailService.sendEmail(param.getString("email"),"密码找回",model);
	}

	@Override
	public int checkToken(PageParam param) {
		Integer i = sqlSession.selectOne("api_user.checkTokenStatus",param);
		return i;
	}

	@Override
	@Transactional
	public void updatePwd(PageParam param) {
		String password = param.getString("password");
		String pwd = param.getString("pwd");
		if(password.equals(pwd)){
			//通过token查询id
			Map res = sqlSession.selectOne("api_user.getUserid", param);
			param.put("id", res.get("userid").toString());
			User user = sqlSession.selectOne("api_user.getUser", param);
			param.put("password", EndecryptUtils.md5Password(user.getEmail(), password));
			int count = sqlSession.update("api_user.updateUser",param);
			if(count == 1){
				param.remove("token");
				param.put("status", 1);
				param.put("userid", res.get("userid").toString());
				int j = sqlSession.update("api_user.updateToken",param);
				if(j!=1){
					throw new BaseAppException("修改失败");
				}
			}else{
				throw new BaseAppException("修改失败");
			}
		}else{
			throw new BaseAppException("两次输入密码不一致");
		}
	}

	@Override
	@Transactional
	public void updatePwdById(PageParam param) {
		String password = param.getString("password");
		String pwd = param.getString("pwd");
		if(password.equals(pwd)){
			User user = sqlSession.selectOne("api_user.getUser", param);
			if(user.getPassword().equals(EndecryptUtils.md5Password(user.getEmail(), param.getString("_password")))){
				param.put("password", EndecryptUtils.md5Password(user.getEmail(), password));
				int count = sqlSession.update("api_user.updateUser",param);
				if(count != 1){
					throw new BaseAppException("修改失败");
				}
			}else{
				throw new BaseAppException("密码验证失败");
			}
		}else{
			throw new BaseAppException("两次输入密码不一致");
		}
		
	}

	
}
