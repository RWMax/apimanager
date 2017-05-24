<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>文档说明</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/api_document.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>UI/js/plugins/summernote/summernote.css"/>
    <script type="text/javascript">
        var __curr_projectId = ${projectId};
        var __curr_moduleId = ${moduleId};
        var __iframebox = parent.document.getElementById('myframe');
    </script>
</head>
<body>
    <%@ include file="edit_navbar.jsp" %>
    <div class="right-content">
        <div class="summernote"></div>
        <div class="text-center">
            <button id="submit" type="button" class="btn btn-primary btn-large">保存</button>
        </div>
    </div>
    <script type="text/javascript" src="<%=basePath%>UI/js/bootstrap.min.js?v=3.3.6"></script>
    <script type="text/javascript" src="<%=basePath%>UI/js/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>UI/js/plugins/moment/moment.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>UI/js/api.base.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/local/messages_zh.min.js"></script>
    <script type="text/javascript" src="<%=staticsPath%>js/api/add_interface.js"></script>
    <script type="text/javascript" src="<%=basePath%>UI/js/plugins/summernote/summernote.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.summernote').summernote({
                height: 300,
                minHeight: null,
                maxHeight: null,
                focus: true
            });
        });
        // $('.summernote').summernote('code', markupStr);
    </script>
</body>

</html>