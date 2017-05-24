<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>关联账户</title>
	<%@include file="../../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/personalCenter/personal_info.css"/>
</head>
<body>
	<div class="content-box">
		<div>
			<h3 class="color-3 fs-28">关联账户</h3>
			<p>描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述</p>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<div class="box text-center">
					<p>Github</p>
					<button type="button" class="btn btn-default">关联Github</button>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="box text-center">
					<p>微博</p>
					<button type="button" class="btn btn-default">关联围脖</button>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="box text-center">
					<p>QQ</p>
					<button type="button" class="btn btn-default">关联QQ</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>