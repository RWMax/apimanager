<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>创建模块</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_sort.css"/>
</head>
<body>
	<div class="content-box">
		<div class="text-center fs-18 title">创建模块</div>
		<input type="hidden" id="projectId" name="projectId" value="${projectId}">
		<input type="hidden" id="moduleId" name="moduleId" value="${module.id}">
		<div class="enbox">
			<input id="name" name="name" type="text" class="" placeholder="请输入文件夹名称" value="${module.name}"/>
		</div>
		<div class="btnbox text-center">
			<button class="btn btn-default btn-sm" type="reset" onclick="parent.layer.closeAll()">关闭</button>
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
				url:Base.globvar.basePath + "project/module/save",
				type:'post',
				data:{projectId:$("#projectId").val(),moduleId:$("#moduleId").val(),name:$("#name").val()},
				dataType:'json',
				//mask:false,
				success:function(data){
					if(!data.error){
						parent.reFreshModules();
						parent.layer.closeAll();
					}
				}
			});
		}
	</script>
</body>
</html>