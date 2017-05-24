<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>添加项目</title>
</head>
<body>
<%@include file="../UI/head.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_project.css"/>
	<div class="content-box">
		<h3 class="color-3 fs-20">添加项目</h3>
		<p class="color-3 fs-12">欢迎使用API-MANAGER，在这里您可以创建项目、接口，通过简单操作便可以完成对接口管理。</p>
		<form id="form1" class="form-horizontal" role="form">
			<input name="id" value="${project.id}" type="hidden">
	        <div class="form-group">
	            <p class="col-sm-1 control-label text-right">项目名称</p>
	            <div class="col-sm-6">
	                <input name="name" type="text" class="form-control fs-12 color-3" value="${project.name}" maxlength="25" placeholder="请输入项目名称" required/>
	            </div>
	        </div>
	        <div class="form-group">
	            <p class="col-sm-1 control-label text-right">是否公开</p>
	
	            <div class="col-sm-6">
		               <div class="radio">
		                   <label>
				               <input type="radio" <c:if test="${not empty project}">checked</c:if> <c:if test="${empty project}">checked</c:if> <c:if test="${project.permission eq 'PRIVATE'}">checked</c:if> value="PRIVATE" id="permission1" name="permission" required>
			                   <span style="display: block;margin-top: -5px;">私有项目（建议此模式：只有项目邀请或者是团队邀请的人员才能看见）</span>
		                   </label>
	                 </div>
	                 <div class="radio">
		                    <label>
		                        <input type="radio" <c:if test="${project.permission eq 'PUBLIC'}">checked</c:if> value="PUBLIC" id="permission2" name="permission">
		                      	<span style="display: block;margin-top: -5px;"> 公开项目（所有用户均能看见）</span>
		                    </label>
	                 </div>
	            </div>
	        </div>
	        <div class="form-group choose-team <c:if test="${not empty project and project.permission eq 'PUBLIC'}">display</c:if>">
	            <p class="col-sm-1 control-label text-right">选择团队</p>
	            <div class="col-sm-6">
	            	<select id="teamid" name="teamid" class="form-control fs-12 color-3" required>
	            		<c:forEach var="t" items="${teams}">
	            			<option value="${t.id}" <c:if test="${project.teamid eq t.id }">selected</c:if> >${t.name}</option>
	            		</c:forEach>
	            	</select>
	            	<button class="btn btn-danger btn-xs m-t-sm" type="button" onclick="window.location.href='<%=basePath%>api/add_team.jsp'">创建团队</button>
	            </div>
	        </div>
	        <div class="form-group">
	            <p class="col-sm-1 control-label text-right">项目描述</p>
	
	            <div class="col-sm-6">
	                <textarea name="description" style="height: 95px;" class="form-control fs-12 color-3" maxlength="150" placeholder="随便讲两句,也许以后就没机会了">${project.description}</textarea>
	            </div>
	        </div>
	         <div class="form-group">
	             <div class="col-sm-6 col-sm-offset-1">
	                 <button class="btn btn-primary" type="submit">保存项目</button>
	             </div>
              </div>
         </form>
	</div>
	<%@include file="/UI/basic.inc.jsp"%>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/local/messages_zh.min.js"></script>
	<script type="text/javascript">
		$(function(){
			submitHandler();
			$("input[name='permission']").click(function(){
				if($(this).val() == 'PRIVATE'){
					$(".choose-team").removeClass('display');
					$('#teamid').prop('disabled',false);
				}else if($(this).val() == 'PUBLIC'){
					$(".choose-team").addClass('display');
					$('#teamid').prop('disabled',true);
				}
			});
		})
		
		function submitHandler() {
			var validator = $("#form1").validate({
				submitHandler:function(form) {
					Base.ajax({
						type:'POST',
						url:Base.globvar.basePath+'project/add',
						data:$(form).serialize(),
						success:function(data){
							if(!data.error){
								form.reset();
								setTimeout(function(){
									parent.window.location.reload();
								}, 400);
							}
						}
					});
				},
				errorPlacement: function(error, element) {  
					var time = 3000;
					var color = '#ed5565';
					layer.tips($(error).text(), element, {
					    tips: [1, color],
					    time: time
					});
				}
			});
		}
	</script>
</body>

</html>