$(document).ready(function(){
	changePwd();
});
function checkPwd(){
	var password = document.getElementById("password").value;
	var _password = document.getElementById("_password").value;
	var pwd = document.getElementById("pwd").value;
	if(!password){
		Base.alert('请输入原密码!');
		return;
	}
	if(password && pwd){
		if(password != pwd){
			Base.alert('两次输入的密码不一致');
			return;
		}
	}else{
		Base.alert('请输入密码!');
		return;
	}
	return {
		'_password':_password,
		password: password,
		pwd:pwd
	};
}
function changePwd(){
	$(document).on("click",'#confirm',function(){
		var pwd = checkPwd();
		var data = {};
		if(!pwd){
			return;
		}
		data = $.extend({},data,pwd);
		Base.ajax({
			'type':'POST',
			'url':Base.globvar.basePath+'user/updatePwd',
			'data':data,
			success:function(data){
				if(!data.error){
					top.window.location.href=Base.globvar.basePath+'logout';
				}
			}
		});
	});
}