<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>注册</title>
	<%@include file="/UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/login.css"/>
</head>
<body>
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div class="logo"></div>
            <div class="m-t" >
            	<form>
                <div class="form-group">
                    <input type="email" id="email" name="email" class="form-control" placeholder="请输入注册邮箱" required="" onblur="checkEmail(this.value)" autocomplete="off">
                </div>
                </form>
                <div  class="btn btn-primary block full-width m-b" id="confirm_email">确认</div>
                </p>
            </div>
        </div>
    </div>
</body>
<%@include file="/UI/basic.inc.jsp"%>
<script type="text/javascript" src="<%=staticsPath%>js/personal/confirmEmail.js"></script>

</html>