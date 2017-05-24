<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>注册</title>
	<%@include file="/UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/login.css"/>
</head>
<body>
    <div class="middle-box text-center loginscreen  animated fadeInDown" style="height:auto;padding:20px;">
        <div>
            <div class="m-t" >
            	<form>
            	<div class="form-group">
                    <input type="password" id="_password" class="form-control" placeholder="请输入原密码" required="true">
                </div>
                <div class="form-group">
                    <input type="password" id="password" class="form-control" placeholder="请输入新的密码" required="true">
                </div>
                 <div class="form-group">
                    <input type="password" id="pwd" class="form-control" placeholder="请再次输入密码" required="true">
                </div>
                </form>
                <div  class="btn btn-primary block full-width m-b" id="confirm">确认</div>
            </div>
        </div>
    </div>
</body>
<%@include file="/UI/basic.inc.jsp"%>
<script type="text/javascript" src="<%=staticsPath%>js/personal/changePwd.js"></script>

</html>