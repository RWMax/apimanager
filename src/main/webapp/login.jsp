<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/login.css"/>
    <title>登录</title>
</head>
<body>
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div class="m-t" >
            	<form>
                <div class="form-group">
                    <input type="email" id="email" name="email" class="form-control" placeholder="请输入注册邮箱" required="" onblur="checkEmail(this.value)" autocomplete="off">
                </div>
                <div class="form-group">
                    <input type="password" id="password" name="password" class="form-control" placeholder="请输入注册密码" required="">
                </div>
                </form>
                <div  class="btn btn-primary block full-width m-b">登 录</div>
                <p class="text-muted text-center"> <a href="<%=basePath%>u/confirm" ><small id="findPwd">找回密码</small></a> | <a href="<%=basePath%>u/register">注册一个新账号</a>
                </p>
            </div>
        </div>
    </div>
</body>
<%@include file="UI/basic.inc.jsp"%>
<script src="<%=basePath%>UI/js/jquery.hotkeys.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		doLogin();
		$(document).bind('keydown','return',function(event){
			$(".btn-primary").trigger('click');
			return false;
		});
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
	function doLogin(){
		$(".btn-primary").on("click",function(){
			var email = document.getElementById('email').value;
			var password = document.getElementById('password').value;
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
				'url':Base.globvar.basePath+'doLogin',
				'data':{
					'email': email,
					'password':password
				},
				success:function(data){
					if(!data.error){
						window.location.href= Base.globvar.basePath+"home";
					}
				}
			});
		});
	}
</script>

</html>