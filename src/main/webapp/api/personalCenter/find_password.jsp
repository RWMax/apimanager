<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>注册</title>
	<%@include file="/UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/login.css"/>
</head>
<body style="height: auto;">
    <div class="middle-box text-center loginscreen  animated fadeInDown" style="padding: 0;">
        <div>
            <div class="m-t">
                 <div class="form-group">
                    <input type="email" class="form-control" id="email" placeholder="输入邮箱" required="true">
                </div>
                <div class="form-group">
                    <input type="password" id="password" class="form-control" placeholder="输入密码" required="true">
                </div>
                <div class="form-group">
                    <input type="password" id="pwd" class="form-control" placeholder="请再次输入密码" required="true">
                </div>
                <div  class="btn btn-primary block full-width m-b">确认修改</div>

            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="<%=staticsPath%>js/personal/personalConfirm.js"></script>

</html>