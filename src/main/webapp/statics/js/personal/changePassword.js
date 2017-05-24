$(document).ready(function(){
	changePwd();
});
function checkPwd(){
	var password = document.getElementById("password").value;
	var pwd = document.getElementById("pwd").value;
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
		password: password,
		pwd:pwd
	};
}
function changePwd(){
	$(document).on("click",'#confirm',function(){
		var pwd = checkPwd();
		var data = {};
		data.token = $("#getToken").attr("token");
		if(!pwd){
			return;
		}
		data = $.extend({},data,pwd);
		Base.ajax({
			'type':'POST',
			'url':Base.globvar.basePath+'u/updatePwd',
			'data':data,
			success:function(data){
				if(!data.error){
				
				}
			}
		});
	});
}