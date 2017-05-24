$(document).ready(function(){
	doUpdate();
});

function checkEmail(val){
	if(val){
		var check = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(val);	
		if(!check){
			parent.Base.alert("邮箱格式不正确");
			return false;
		}
		return true;
	}
	return false;
}
function checkPwd(){
	
	var password = document.getElementById("password").value;
	var pwd = document.getElementById("pwd").value;
	if(password && pwd){
		if(password != pwd){
			parent.Base.alert('两次输入的密码不一致');
			return;
		}
	}else{
		parent.Base.alert('请输入密码!');
		return;
	}
	return {
		password: password,
		pwd:pwd
	};
}

function doUpdate(){
	$(".btn-primary").on("click",function(){
		var email = document.getElementById('email').value;
		if(email){
			if(!checkEmail(email)){
				parent.Base.alert("请检查邮箱格式是否正确");
				return;
			}
		}else{
			parent.Base.alert("请输入邮箱");
			return;
		}
		var pwd = checkPwd();
		if(!pwd){
			return;
		}
		var data = $.extend({},{'email':email},pwd);
		parent.Base.ajax({
			'type':'POST',
			'url':Base.globvar.basePath+'user/doUpdate',
			'data':data,
			success:function(data){
				if(!data.error){
					var doc = parent.document.getElementById('myiframe').contentDocument || parent.document.frames['myiframe'].document;
					doc.location.reload();
					parent.layer.closeAll();
				}
			}
		});
	});
}