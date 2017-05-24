<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>注册</title>
	<%@include file="UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/login.css"/>
</head>
<body>
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div class="logo"></div>

            <div class="m-t">
                 <div class="form-group">
                    <input type="text" class="form-control" id="nickname" placeholder="输入姓名方便团队识别" required="true">
                </div>
                <div class="form-group">
                    <input type="email" id="email" class="form-control" placeholder="输入邮箱可以找回密码" required="true" onblur="checkEmail(this.value)">
                </div>
                <div class="form-group">
                    <input type="password" id="password" class="form-control" placeholder="密码" required="true">
                </div>
                <div  class="btn btn-primary block full-width m-b">免费注册</div>

                <p class="text-muted text-left"> <a href="<%=basePath%>login.jsp"><small>返回登录</small></a>
                </p>

            </div>
        </div>
    </div>
</body>
<%@include file="UI/basic.inc.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	doRegister();
});

function checkEmail(val){
	if(val){
		var check = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(val);	
		if(!check){
			Base.alert("邮箱格式不正确");
			return false;
		}
		return true;
	}
	return false;
}
function doRegister(){
	$(".btn-primary").on("click",function(){
		var nickname = document.getElementById('nickname').value;
		var email = document.getElementById('email').value;
		var password = document.getElementById('password').value;
		if(!nickname){
			Base.alert("请输入昵称");
			return;
		}
		if(email){
			if(!checkEmail(email)){
				Base.alert("请检查邮箱格式是否正确");
				return;
			}
		}else{
			Base.alert("请输入邮箱");
			return;
		}
		if(!password){
			Base.alert("请输入密码");
			return;
		}
		Base.ajax({
			'type':'POST',
			'url':Base.globvar.basePath+'u/doRegister',
			'data':{
				'nickname':nickname,
				'email': email,
				'password':password
			},
			success:function(data){
				if(!data.error){
					Base.alert("注册成功!");
					setTimeout(function(){
						window.location.href= Base.globvar.basePath+"login";
					},1000);
				}
			}
		});
	});
}
</script>
</html>