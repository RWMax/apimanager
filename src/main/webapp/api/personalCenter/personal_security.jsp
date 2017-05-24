<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>安全设置</title>
	<%@include file="../../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/personalCenter/personal_info.css"/>
</head>
<body>
	<div class="content-box security">
		<h3 class="color-3 fs-20">安全设置</h3>
		 <div class="row brb">
		    <p class="col-sm-1">更换邮箱</p>
		    <p class="col-sm-5 text-left">${email}</p>
		    <div class="col-sm-6">
		      <button class="btn btn-info btn-sm" id="updateEmail" type="submit">修改</button>
		    </div>
		  </div>
         <div class="row">
            <p class="col-sm-1">更换密码</p>
			<p class="col-sm-5">建议您三个月修改一次</p>
			<div class="col-sm-6">
				<button class="btn btn-info btn-sm" id="updatePwd" type="submit">修改</button>
       		</div>
        </div>
	</div>
</body>
<script type="text/javascript" src="<%=staticsPath%>js/personal/personalSecurity.js"></script>
</html>