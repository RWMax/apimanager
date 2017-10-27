<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
	<html>

	<head>
		<title>【API】${project.name}: ${project.description}</title>
		<%@ include file="../UI/head.inc.jsp"%>
		<link href="<%=basePath%>UI/js/plugins/toastr/toastr.min.css" rel="stylesheet"/>
		<link href="<%=basePath%>statics/css/pro_index.css" rel="stylesheet"/>
		<style>
			.env .divider {
				margin: 0;
			}
			.hover-table {
				position: absolute;
				width: 350px;
				height: 30px;
				top: 0;
				right: 160px;
				background-color: #fff;
				display: none;	
				z-index: 1000;
			}
			.hover-table > ul {
				border: 1px solid #C3C3C3;
			}
			.hover-table > ul , .hover-table > li, .list-item > li{
				height: 100%;
				line-height: 30px;
				text-align: left;
			}
			.hover-table > ul > li:first-child, .list-item > li:first-child {
				border-right: 1px solid #C3C3C3;
			}
			.table-list {
				background-color: #fff;
				display: none;
				border-radius: 0 0 0 4px;			
			}
			.list-item {
				height: 100%;
				border-right: 1px solid #C3C3C3;
				border-left: 1px solid #C3C3C3;
				border-bottom: 1px solid #C3C3C3;
			}
			.fr {
				margin-left: 20px;
			}
			.cb > i {
				font-size: 14px;
			}
			ul,li {
				list-style: none;
				margin: 0;
				padding: 0;
			}
			.dropdown-menu>li>a {line-height: 20px;}
		</style>
	</head>

	<body class="fixed-sidebar full-height-layout gray-bg mini-navbar">
		<div id="wrapper">
			<!--左侧导航开始-->
			<nav class="navbar-default navbar-static-side" role="navigation">
				<div class="nav-close">
					<i class="fa fa-times-circle"></i>
				</div>
				<div class="sidebar-collapse">
					<ul class="nav" id="side-menu">
						<li class="nav-header">
							<%-- <div class="dropdown profile-element">
								<span>
                            		<img alt="image" class="img-circle" src="${staticsPath}image/logo_01.png" />
                            	</span>
								<a data-toggle="dropdown" class="dropdown-toggle" href="#">
									<span class="clear">
	                               	<span class="block m-t-xs text-muted">
	                               		<strong class="font-bold">Beaut-zihan</strong>
	                               		<b class="caret"></b>
	                               	</span>
								</a>
								<ul class="dropdown-menu animated fadeInRight m-t-xs">
									<li>
										<a class="">修改邮箱</a>
									</li>
									<li>
										<a class="">修改密码</a>
									</li>
									<li class="divider"></li>
									<li><a href="${basePath}logout">安全退出</a>
									</li>
								</ul>
							</div> --%>
							<div class="logo-element">API</div>
						</li>
						<%-- <li class="add_btn_box m-t-xs m-b-xs">	
						    <div class="text-center profile-element display">
							    <button type="button" class="btn btn-outline btn-primary" id="add_sort">
							    	添加分类
							    </button>
							    <button type="button" class="btn btn-outline btn-info" onclick="__iframebox.src='${basePath}api/show?type='+__curr_editType+'&projectId='+__curr_projectId+'&moduleId='+__curr_moduleId">
							    	添加接口
							    </button>
						    </div>
						    <div class="logo-element">
						    	<i class="fa fa-plus"></i>
						    </div>
						</li> --%>
						<!-- <li>
							<a id="add_sort" class="add-pro">
								<i class="fa fa-plus"></i>
								<span class="nav-label">添加分类</span>
							</a>
						</li> -->
						<li>
							<a class="add-pro" href="${basePath}home" style="margin-top:20px;font-size: 36px;margin-left: -5px;" title="添加项目">
								<i class="fa fa-plus-circle"></i>
							</a>
						</li>
						<%-- <li>
							<a class="J_menuItem  doc_descript" href="${basePath}api/api_document.jsp">
								<i class="fa fa-file-text"></i>
								<span class="nav-label">文档说明</span>
							</a>
						</li> --%>
						<!-- <li class="global_data display">
							<a id="moduleParameter" class="" href="javascript:void(0)">
								<i class="fa fa-columns"></i>
								<span class="nav-label">模块全局参数</span>
							</a>
						</li> -->
						<%-- <li>
							<a>
								<i class="fa fa-tasks"></i>
								<span class="nav-label">默认分类</span>
								<span class="fa arrow"></span>
							</a>
							<ul class="nav nav-second-level">
								<li>
									<a class="J_menuItem list" href="${basePath}api/test_environment.jsp">
										医保线上支付预结算
									</a>
								</li>
							</ul>
						</li> --%>
					</ul>
				</div>
			</nav>
			<!--左侧导航结束-->
			
			<!--右侧部分开始-->
			<div id="page-wrapper" class="white-bg dashbard-1">
				<!-- 导航1开始 -->
				<div class="row border-bottom text-center">
					<nav class="navbar" role="navigation">
						<!-- <div class="navbar-header" style="height:50px; width: 300px;">
							<a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#"><i class="fa fa-bars"></i> </a>
							<form role="search" class="navbar-form-custom">
								<div class="form-group">
									<input type="text" placeholder="请输入您需要查找的接口名称 …" class="form-control" name="top-search" id="top-search">
								</div>
							</form>
						</div> -->
						<div class="mode-btnbox">
							<c:if test="${not empty projectUser and projectUser.status ne 'NORMAL'}">
								<a class="color-6 fs-13" tag="edit" style="margin-right:20px;">编辑模式</a>
							</c:if>
							<a class="color-6 fs-13 active" tag="view">浏览模式</a>
						</div>
						<ul class="nav navbar-top-links navbar-right">
							 <li class="dropdown">
	                            <a id="currentProject" _projectId="${project.id}" title="${project.description}" class="dropdown-toggle count-info fs-12 color-9" data-toggle="dropdown" href="#">
	                                <i class="fa fa-th-large"></i>
	                                <span id="my_project">${project.name}</span>
	                            </a>
	                            <ul class="dropdown-menu dropdown-alerts">
	                            	<c:forEach items="${myProjects}" var="p">
	                            		<li>
		                                    <a class="project" _projectId="${p.projectId}" href="${basePath}project/item${p.projectId}">
		                                        <div>
		                                            <i class="fa fa-medkit"></i>
		                                            <span>${p.project_name}</span> 
		                                        </div>
		                                    </a>
		                                </li>
	                            	</c:forEach>
	                                <li class="divider"></li>
	                                <li>
	                                    <div class="text-center link-block">
	                                        <a class="all_pro" href="${basePath}home">
	                                            <strong>查看所有 </strong>
	                                            <i class="fa fa-angle-right"></i>
	                                        </a>
	                                    </div>
	                                </li>
	                            </ul>
                        	</li>
							<li>
                                <a class="count-info fs-12 color-9" onclick="window.location.href='${basePath}home'">
                                	<i class="fa fa-home"></i>首页
	                            </a>
	                         </li>
						</ul>
					</nav>
				</div>
				<!-- 导航1结束 -->
				<!-- 导航2开始 -->
				<div class="row content-tabs">
					<button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                	</button>
				 	<a class="add_module" title="添加模块">
				 		<span class="glyphicon glyphicon-plus"></span>
				 	</a>

					<nav class="page-tabs J_menuTabs">
						 <div id="modules" class="page-tabs-content">
					 		<!-- <a class="J_menuTab active">
								<span>医保在线支付1</span>
								<span class="caret display">
									<div class="caret-op display">
										<div><i class="fa fa-edit"></i>编辑</div>
										<div><i class="fa fa-remove"></i>删除</div>
									</div>
								</span>
							</a>
							<a class="J_menuTab">
								<span>HIS医疗服务2</span>
								<span class="caret display">
									<div class="caret-op display">
										<div><i class="fa fa-edit"></i>编辑</div>
										<div><i class="fa fa-remove"></i>删除</div>
									</div>
								</span>
							</a> -->
	                    </div>
					</nav>
					<button class="roll-nav roll-right J_tabRight">
						<i class="fa fa-forward"></i>
               		</button>
					<div class="btn-group roll-nav roll-right">
						<button class="dropdown J_tabClose" data-toggle="dropdown" id="dropenv">
							<c:if test="${env.name != null}">
								${env.name}<span class="caret" default_id = "${env.envId}"></span>
							</c:if>
							<c:if test="${env.name == null}">
								添加环境<span class="caret"></span>
							</c:if>
                    	</button>
						<ul role="menu" class="dropdown-menu dropdown-menu-right env" id="env-list">
							<li class="J_tabCloseAll" id="add_enment">
								<a>添加环境</a>
							</li>
							<div class="hover-table" id="hover-table">
								<ul>
									<li class="col-sm-4">变量</li>
									<li class="col-sm-8">
										<div class="cb">值
											 <i class="fr fa fa-copy"></i> 
											 <i class="fr fa fa-edit"></i> 
											 <i class="fr fa fa-close"></i>
										</div>
									</li>
								</ul>
								
							</div>
						</ul>
						
					</div>
					<div class="roll-nav roll-right J_tabExit">
						<a href="" class="roll-nav roll-right J_tabExit dropdown J_tabClose" data-toggle="dropdown"> 
							 更多<i class="fa fa-caret-right"></i>
						</a>
						<div class="dropdown-menu dropdown-menu-right more-menus">
							<div class="item">
								<c:if test="${not empty projectUser and projectUser.status eq 'OWNER'}">
									<c:if test="${project.permission eq 'PRIVATE'}">
										<a class="color-6 fs-12" href="${basePath}home?iframesrc=${basePath}project/user/manager/${project.id}">
											<i class="fa fa-user fs-20"></i>
											<p>成员管理</p>
										</a>
										<a class="color-6 fs-12" href="${basePath}home?iframesrc=${basePath}project/user/manager/${project.id}">
											<i class="fa fa-exchange fs-20"></i>
											<p>项目转让</p>
										</a>
									</c:if>
									<a class="color-6 fs-12" href="${basePath}home?iframesrc=${basePath}project/goAdd/?projectId=${project.id}">
										<i class="fa fa-cog fs-20"></i>
										<p>项目设置</p>
									</a>
									<a class="color-6 fs-12" href="#" onclick="deleteProject(${project.id})">
										<i class="fa fa-trash fs-20"></i>
										<p>删除项目</p>
									</a>
									<a class="color-6 fs-12" href="#" onclick="exportProject(${project.id})">
										<i class="fa fa-reply fs-20"></i>
										<p>导出项目</p>
									</a>
									<a class="color-6 fs-12" href="#" onclick="exportJson(${project.id})">
										<i class="fa fa-reply fs-20"></i>
										<p>导出JSON</p>
									</a>
								</c:if>
								<a class="text-center color-6 fs-12">
									<i class="fa fa-wrench fs-20"></i>
									<p>帮助</p>
								</a>
								<a class="color-6 fs-12" href="${basePath}logout">
									<i class="fa fa-sign-out fs-20"></i>
									<p>安全退出</p>
								</a>
							</div>
						</div>
					</div>
				</div>
				<!-- 导航2结束 -->
				
				<div class="row J_mainContent" id="content-main">
					<iframe class="J_iframe" name="iframe0" id="myframe" width="100%" height="100%" frameborder="0" seamless></iframe>
				</div>
				
				<div class="footer">
					<div class="pull-right">
						&copy<a href="http://www.yinhai.com/" target="_blank"></a>
					</div>
				</div>
			</div>
			<!--右侧部分开始-->
		</div>
		<%@ include file="/UI/basic.inc.jsp" %>
		<%-- <script src="${basePath}UI/js/plugins/metisMenu/jquery.metisMenu.js"></script> --%>
		<script src="${basePath}UI/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<%-- <script src="${staticsPath}js/hplus.min.js?v=4.1.0"></script> --%>
		<script src="${staticsPath}js/contabs.min.js"></script>
		<script src="${staticsPath}js/project/pro_index.js"></script>
		<script src="${staticsPath}js/project/pro_env.js"></script>
		<script type='text/javascript' src='<%=basePath %>UI/js/plugins/toastr/toastr.min.js'></script>
		<!-- dwr -->
		<script type='text/javascript' src='<%=basePath %>dwr/engine.js'></script>  
  		<script type='text/javascript' src='<%=basePath %>dwr/util.js'></script>  
 		<script type="text/javascript" src="<%=basePath %>dwr/interface/MessagePush.js"></script>
 		<script type="text/javascript">
	 		dwr.engine.setActiveReverseAjax(true);
			dwr.engine.setNotifyServerOnPageUnload(true);
			//设置DWR调用服务出错时，不打印(alert)调试信息 
			dwr.engine.setErrorHandler(function() { 
			    // 
			});
			//推送信息  接受后台推送过来的消息
			function showMessage(content){
				toastr.success(content,"消息通知",{
					"progressBar":true,
					"closeButton": true,
					"timeOut": "0",
					"onclick":function(){}
				});
			}
 		</script>
	</body>
	</html>