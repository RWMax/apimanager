<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>

	<head>
		<title>项目成员管理</title>
		<%@ include file="../UI/head.inc.jsp"%>
		<link href="<%=basePath%>statics/css/pro_userManager.css" rel="stylesheet"/>
	</head>

	<body>
		<div class="wrapper wrapper-content">
		<input name="projectId" value="${project.id}" type="hidden">
		<div class="user-list">
			<ul class="nav nav-tabs">
				<li class="active">
					<a data-toggle="tab" href="#tab-1" aria-expanded="true"><i class="fa fa-users"></i> 成员列表</a>
				</li>
				<li class="">
					<a data-toggle="tab" href="#tab-2"aria-expanded="false"><i class="fa fa-user"></i> 邀请</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab-1" class="tab-pane active ibox">
					<div class="table-responsive ibox-content">
						<table class="table table-hover">
							<tbody>
								<c:forEach items="${projectUsers}" var="u">
									<tr>
										<td>
											<i class="fa fa-user-circle"></i>
										</td>
										<td>${project.name}</td>
										<td>${u.nickname}</td>
										<td>
											<i class="fa fa-envelope-o"></i>&nbsp;&nbsp;${u.email}
										</td>
										<td>
											<c:if test="${u.status eq 'OWNER'}">
												<span class="label label-primary">项目拥有者</span>
											</c:if>
											<c:if test="${u.status eq 'TEAM'}">
												<span class="label label-warning">团队成员</span>
											</c:if>
											<c:if test="${u.status eq 'NORMAL'}">
												<span class="label label-info">普通成员</span>
											</c:if>
										</td>
										<td class="client-status">
											<c:if test="${u.status ne 'OWNER'}">
												<button class="btn btn-danger btn-xs" type="button" onclick="removeUser(${u.projectId},${u.userId})">从项目移除</button>
											</c:if>
											<c:if test="${u.status eq 'NORMAL'}">
												<button class="btn btn-warning btn-xs" type="button" onclick="improve(${u.projectId},${u.userId})">提升为团队成员</button>
											</c:if>
											<c:if test="${u.status eq 'TEAM'}">
												<button class="btn btn-primary btn-xs" type="button" onclick="exchange(${u.projectId},${u.userId})">转让给他</button>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div id="tab-2" class="tab-pane ibox">
					<div class="ibox-content">
						<div class="form-group">
	                        <label class="col-sm-2 control-label text-center m-t-sm">邮箱邀请</label>
	                        <div class="col-sm-8">
	                            <input name="normal_email" type="text" class="form-control" placeholder="普通邀请,将没有编辑的权限">
	                        </div>
	                        <div>
	                        	<button class="btn btn-danger" type="button" onclick="V('normal_email')">邀请</button>
	                        </div>
	                    </div>
	                    <div class="hr-line-dashed"></div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label text-center m-t-sm">团队邀请</label>
	                        <div class="col-sm-8">
	                            <input name="team_email" type="text" class="form-control" placeholder="团队邀请,具有编辑API的权限">
	                        </div>
	                        <div>
	                        	<button class="btn btn-danger" type="button" onclick="V('team_email')">邀请</button>
	                        </div>
	                    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/UI/basic.inc.jsp" %>
	<script type="text/javascript">
		function removeUser(projectId,userId) {
			Base.confirm('确认移除?',function(yes){
				if(yes){
					// 确认删除
					Base.ajax({
						url:Base.globvar.basePath + "project/user/remove",
						type:'post',
						data:{projectId:projectId,userId:userId},
						dataType:'json',
						success:function(data){
							if(!data.error){
								window.location.reload(true);
							}
						}
					});
				}
			});
		}
		function improve(projectId,userId) {
			Base.ajax({
				url:Base.globvar.basePath + "project/user/improve",
				type:'post',
				data:{projectId:projectId,userId:userId},
				dataType:'json',
				success:function(data){
					if(!data.error){
						window.location.reload(true);
					}
				}
			});
		}
		function exchange(projectId,userId) {
			Base.ajax({
				url:Base.globvar.basePath + "project/user/exchange",
				type:'post',
				data:{projectId:projectId,userId:userId},
				dataType:'json',
				success:function(data){
					if(!data.error){
						window.location.reload(true);
					}
				}
			});
		}
		function V(name) {
			var email = $(':input[name="'+name+'"]').val();
			if(!Base.isExist(email)){
				layer.tips("请输入邮箱地址",$(':input[name="'+name+'"]'),{tips:[3,'#ed5565']});
				return;
			}
			Base.ajax({
				url:Base.globvar.basePath + "project/user/add/"+name,
				type:'get',
				data:{email:email,projectId:$(':input[name="projectId"]').val()},
				dataType:'json',
				success:function(data){
					if(!data.error){
						window.location.reload(true);
					}
				}
			});
		}
	</script>
	</body>
</html>