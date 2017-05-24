<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>创建组织</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_project.css"/>
</head>
<body>
	<div class="content-box">
		<h3 class="color-3 fs-20">添加组织(团队)</h3>
		<p class="color-3 fs-12">欢迎使用API-MANAGER，在这里您可以创建项目、接口，通过简单操作便可以完成对接口管理。</p>
		<form id="form1" class="form-horizontal" role="form">
	        <div class="form-group">
	            <p class="col-sm-1 control-label text-right">组织名称</p>
	
	            <div class="col-sm-6">
	                <input id="name" name="name" maxlength="25" required type="text" class="form-control fs-12 color-3" placeholder="请输入组织名称"/>
	            </div>
	        </div>
	        <div class="form-group">
	            <p class="col-sm-1 control-label text-right">添加描述</p>
	
	            <div class="col-sm-6">
	                <textarea id="description" name="description" maxlength="250" class="form-control fs-12 color-3" placeholder="随便写点什么吧,目标?理想?Money Or Girls"></textarea>
	            </div>
	        </div>
	         <div class="form-group">
	             <div class="col-sm-6 col-sm-offset-1">
	                 <button id="submitButton" class="btn btn-primary" type="submit">创建组织</button>
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
		})
		
		function submitHandler() {
			var validator = $("#form1").validate({
				submitHandler:function(form) {
					Base.ajax({
						type:'POST',
						url:Base.globvar.basePath+'team/add',
						data:$(form).serialize(),
						success:function(data){
							if(!data.error){
								form.reset();
								Base.alert("创建团队成功,马上开始添加属于自己的项目吧!");
								setTimeout(function(){
									window.location.href = Base.globvar.basePath+"project/goAdd";
								}, 2000);
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