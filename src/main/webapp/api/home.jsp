<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>home</title>
</head>
<body>
<%@include file="../UI/head.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/home.css"/>
	<div class="content-box">
		<h3 class="color-3 fs-20">在线接口管理工具，在线模拟，生成模板数据</h3>
		<p class="color-9">欢迎使用，创建团队，创建项目，项目模块，模块分类，创建接口，在线模拟。。。</p>
		<div class="i-box row">
			<div class="float-l box col-md-4 team">
				<div class="float-l icon">
					<i class="fa fa-qq fs-36 color-3"></i>
				</div>
				<div class="float-l">
					<h3  class="color-3">创建组织</h3>
					<p>开始旅途，从我做起。</p>
				</div>
			</div>
			<div class="float-l box col-md-4 project">
				<div class="float-l icon">
					<i class="fa fa-folder-o fs-36 color-3"></i>
				</div>
				<div class="float-l">
					<h3  class="color-3">创建项目</h3>
					<p>小手一抖，项目我有。</p>
				</div>
			</div>
			<div class="float-l box col-md-4 advice">
				<div class="float-l icon">
					<i class="fa fa-comments-o fs-36 color-3"></i>
				</div>
				<div class="float-l">
					<h3 class="color-3">反馈</h3>
					<p>bug,建议都来这儿。</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$(".team").on("click",function(){
				window.location.href = "${basePath}api/add_team.jsp";
			})
			$(".project").on("click",function(){
				window.location.href = "${basePath}project/goAdd";
			})
			$(".advice").on("click",function(){
				
			})
		})
	</script>
</body>
</html>