<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>创建分类</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_sort.css"/>
</head>
<body>
	<div class="content-box">
		<div class="text-center fs-18 title">创建分类</div>
		<input id="projectId" type="hidden" value="${projectId}">
		<input id="moduleId" type="hidden" value="${moduleId}">
		<input id="folderId" type="hidden" value="${folder.id}">
		<div class="enbox">
			<input  id="name" type="text" class="" placeholder="请输入分类名称" value="${folder.name}"/>
		</div>
		<div class="btnbox text-center">
			<button class="btn btn-default btn-sm" type="reset" onclick="parent.layer.closeAll();">取消</button>
			<button class="btn btn-primary btn-sm" type="button" onclick="save()">确定</button>
		</div>
	</div>
	<%@ include file="/UI/basic.inc.jsp" %>
	<script type="text/javascript">
		function save() {
			if($("#name").val().trim() == ''){
				layer.tips("好歹打两个字?",$("#name"),{tips:[3,'#ed5565']});
				return;
			}
			Base.ajax({
				url:Base.globvar.basePath + "api/folder/save",
				type:'post',
				data:{projectId:$("#projectId").val(),moduleId:$("#moduleId").val(),folderId:$("#folderId").val(),name:$("#name").val()},
				dataType:'json',
				//mask:false,
				success:function(data){
					if(!data.error){
						parent.window.location.reload(true);
					}
				}
			});
		}
	</script>
</body>
</html>