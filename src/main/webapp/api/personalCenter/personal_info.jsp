<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人信息</title>
	<%@include file="../../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/personalCenter/personal_info.css"/>
</head>
<body>
	<div class="content-box">
	 		<div class="row brb">
	 			<p class="col-sm-1 h-text">头像</p>
	            <div class="col-sm-11">
	                <img alt="" src="<%=basePath%>statics/image/user_img.png">
	            </div>
	        </div>
	        <div class="row brb">
	            <p class="col-sm-1 n-text">姓名</p>
	            <div class="col-sm-5">
	                <input name="name" id="nickname" type="text" class="form-control fs-12 color-3" value="${user.nickname}"/>
	            </div>
	        </div>
	         <div class="row brb">
	            <p class="col-sm-1">邮箱</p>
				<p class="col-sm-11">${user.email}</p>
	        </div>
	         <div class="row brb">
	            <p class="col-sm-1">注册时间</p>
				<p class="col-sm-11">${createtime}</p>
	        </div>
	    <button class="btn btn-primary btn-sm col-sm-offset-1" id="confirm" type="submit">确定</button>
	</div>
</body>
<script type="text/javascript" src="<%=staticsPath%>js/personal/personalInfo.js"></script>
</html>