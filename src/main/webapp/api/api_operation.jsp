<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>api 复制移动</title>
	<%@include file="../UI/head.inc.jsp"%>
</head>
<body>
	<div class="wrapper wrapper-content">
		<form id="form1" class="form-horizontal">
			<input type="hidden" id="projectId" name="projectId" value="${api.projectid}"/>
			<input type="hidden" id="id" name="id" value="${api.id}"/>
			<input type="hidden" id="operation" name="operation" value="${operation}"/>
			<div class="form-group">
                 <label class="col-sm-4 control-label">选择模块：</label>
                 <div class="col-sm-8">
                 	<select id="moduleId" name="moduleId" class="form-control" required>
                 		<option value="-1">请选择...</option>
                 		<c:forEach items="${modules}" var="m">
                 			<option value="${m.id}">${m.name}</option>
                 		</c:forEach>
                 	</select>
                 </div>
             </div>
             <div class="form-group">
                 <label class="col-sm-4 control-label">选择分类：</label>
                 <div class="col-sm-8">
                 	<select id="folderId" name="folderId" class="form-control" required>
                 	</select>
                 </div>
             </div>
             <p>
                 <button type="submit" class="btn btn-block btn-primary">提&nbsp;&nbsp;&nbsp;&nbsp;交</button>
             </p>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>UI/js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/moment/moment.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/api.base.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/local/messages_zh.min.js"></script>
	<script type="text/javascript">
		$(function(){
			bindClick();
			initForm();
		})
		
		function initForm() {
			var validator = $("#form1").validate({
				submitHandler:function(form) {
					var data = $(form).serialize();
					if($('#moduleId').val() == -1){
						layer.tips('请选择模块', $('#moduleId'), {
						    tips: [1, '#ed5565'],
						});
						return;
					}
					Base.ajax({
						type:'POST',
						url:Base.globvar.basePath+'api/operation/save',
						data:data,
						success:function(data){
							if(!data.error){
								Base.alert(data.success_msg);
								setTimeout(function(){
									parent.window.location.href = Base.globvar.basePath + "api/"+top.__curr_editType+"?projectId="+parent.__curr_projectId+"&moduleId="+$('#moduleId').val()+"&folderId="+$('#folderId').val()+"&id="+data.id;
								}, 400);
							}else {
								Base.alert(data.error_msg);
							}
						}
					});
				},
				errorPlacement: function(error, element) {  
					layer.tips($(error).text(), element, {
					    tips: [1, '#ed5565'],
					});
				}
			});
		}
		
		function bindClick() {
			$('#moduleId').on('change',function(){
				if($(this).val() != -1){
					// 查询模块下的分类
					Base.ajax({
						type:'POST',
						url:Base.globvar.basePath+'project/${api.projectid}/'+$(this).val()+"/folders",
						data:null,
						success:function(data){
							if(!data.error){
								createFolderOptions(data);
							}
						}
					});
				}else{
					$('#folderId').empty();
				}
			});
		}
		
		function createFolderOptions(data) {
			var html = [];
			for(var i in data){
				var f = data[i];
				html.push('<option value="'+f.id+'">'+f.name+'</option>');
			}
			$('#folderId').empty().html(html.join(''));
		}
	</script>
</body>
</html>