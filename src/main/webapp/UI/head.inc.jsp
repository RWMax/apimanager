<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String staticsPath = basePath + "statics/" ;
	pageContext.setAttribute("staticsPath", staticsPath);
	request.setAttribute("basePath", basePath);
%>
<meta http-equiv="pragma" content="pragma"/>
<meta http-equiv="cache-control" content="public,max-age=86400"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<link rel="shortcut icon" href="<%=basePath%>UI/favicon.ico" />
<link href="<%=basePath%>UI/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath%>UI/css/font-awesome.min93e3.css?v=4.7.0" rel="stylesheet">
<link href="<%=basePath%>UI/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>UI/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<link href="<%=basePath%>statics/css/common.css" rel="stylesheet">
<script src="<%=basePath%>UI/js/jquery.min.js?v=2.1.4"></script>
<script type="text/javascript">
	if(typeof Base == 'undefined'){
		var Base = {};
	}
	Base.globvar = {
		contentPath:'<%=path%>',
		basePath:'<%=basePath%>'
	}
</script>