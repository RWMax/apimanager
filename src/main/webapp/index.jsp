<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
	<%@include file="UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/index.css"/>
	<style type="text/css">
		.webuploader-pick + div {
			display: none;
		}
	</style>
</head>
<body>
     <!-- 左侧导航开始 -->
	 <nav class="navbar-default navbar-static-side" role="navigation">
	    <div class="left-nav float-l">
	    	<div class="logo"></div>
	    	<div class="search-box pd-left">
	    		<i class="fa fa-search fs-16"></i>
	    		<input type="text" class="fs-12 color-3" placeholder="快速查找项目">
	    	</div>
	    	<div class="add-box pd-left" id="add_pro">
	    		<span class="glyphicon glyphicon-plus"></span>
	    		<span class="fs-12 color-3">创建项目</span>
	    	</div>
			<div class="add-box pd-left" id="import_pro">
				<span class="glyphicon glyphicon-import"></span>
				<span class="fs-12 color-3"><label id="importPro">导入项目</label></span>
			</div>
	    	<div class="scroll">
	    		<c:if test="${not empty myProjects || not empty publicProjects}">
	    			<div class="pro-box brb">
			    		<span class="pro-title pd-left fs-16 color-3">我的项目</span>
			    		<c:forEach items="${myProjects}" var="p">
			    			<div class="item pd-left" _projectId="${p.projectId}">
					    		<i class="fa fa-cloud-upload fs-16"></i>
					    		<span class="fs-12 color-3">${p.project_name}</span>
				    		</div>
			    		</c:forEach>
			    		<c:forEach items="${publicProjects}" var="p">
			    			<div class="item pd-left" _projectId="${p.projectId}">
					    		<i class="fa fa-cloud-upload fs-16"></i>
					    		<span class="fs-12 color-3">${p.project_name}</span>
				    		</div>
			    		</c:forEach>
			    	</div>
	    		</c:if>
		    	<c:if test="${not empty teamProjects}">
		    		<div class="pro-box">
			    		<span class="pro-title pd-left fs-16 color-3">团队项目</span>
			    		<c:forEach items="${teamProjects}" var="p">
			    			<div class="item pd-left" _projectId="${p.projectId}">
					    		<i class="fa fa-cloud-upload fs-16"></i>
					    		<span class="fs-12 color-3">${p.project_name}</span>
				    		</div>
			    		</c:forEach>
			    	</div>
		    	</c:if>
	    	</div>
	    </div>
	 </nav>
     <!-- 左侧导航结束 -->
	 <!--右侧部分开始-->
     <div id="page-wrapper" class="gray-bg dashbard-1">
     
         <div class="row border-bottom">
             <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                 <div class="navbar-header">
                  <span class="fs-14 color-3" id="index">首页</span>
                  <span class="fs-14 color-3">帮助</span>
                 </div>
              	<div class="nav navbar-right">
                     <a href="#" class="percenal-panel">
                        <span class="fs-14 color-3"><i class="fa fa-user-circle-o" aria-hidden="true"></i>&nbsp;个人面板</span>
                     </a>
                     <div class="list display">
                     	 <div class="info-box brb">
                     	 	<div class="float-l">
                     	 		<img alt="" src="<%=basePath%>statics/image/user_img.png">
                     	 	</div>
                     	 	<div class="float-l">
                      	 	<p>${user.nickname}</p>
                      	 	<p>${user.email}</p>
                     	 	</div>
                     	 </div>
                     	 <ul>
                     	 	<li class="brb user-center"><i class="fa fa-user-circle-o" aria-hidden="true"></i>&nbsp;个人中心</li>
                      	 	<li class="brb user-center"><i class="fa fa-cog" aria-hidden="true"></i>&nbsp;安全设置</li>
                      	 	<li onclick="window.location.href='<%=basePath%>logout'"><i class="fa fa-sign-out"></i>&nbsp;退出登录</li>
                     	 </ul>
                     </div>
                 </div>
             </nav>
         </div>
         
         <div class="row J_mainContent" id="content-main">
         	<c:choose>
         		<c:when test="${not empty iframesrc}">
         			<iframe class="J_iframe" id="myiframe" onload="changeFrameHeight()" name="iframe0" width="100%" height="100%" src="${iframesrc}" frameborder="0" data-id="" seamless></iframe>
         		</c:when>
         		<c:otherwise>
         			<iframe class="J_iframe" id="myiframe" onload="changeFrameHeight()" name="iframe0" width="100%" height="100%" src="<%=basePath%>api/home.jsp" frameborder="0" data-id="" seamless></iframe>
         		</c:otherwise>
         	</c:choose>
  		 </div>
  	
    </div>
 	<!--右侧部分结束-->
</body>
<script type="text/javascript" src="<%=basePath%>UI/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="<%=basePath%>UI/js/plugins/layer/layer.min.js"></script>
<script src="<%=basePath%>UI/js/plugins/webuploader/webuploader.min.js"></script>
<script type="text/javascript">
	$(function(){
		window.onresize=function(){  
		     changeFrameHeight();
		     setScroll();
		} 
		 bindEvent();
		 setScroll();
		 function setScroll(){
			var height = (document.documentElement.clientHeight)-232;
			$(".scroll").css({height: height})
				.slimScroll({
				 width: '220px',
				 height: height+'px',
				 alwaysVisible: true
			 });
		}

		initWebUploader();
		$('#importPro').css("cursor", "pointer");
	});
	function initWebUploader() {
		var uploader = WebUploader.create({
			auto: true,
			swf: Base.globvar.basePath + 'UI/js/plugins/webuploader/Uploader.swf',
			// 文件接收服务端。
			server: Base.globvar.basePath + "project/importFile",
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick: {
				id: '#importPro',
				multiple: false
			},
			accept: {
				extensions: '*',
				mimeTypes: 'text/json'
			},
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize: false
		});
		uploader.on('uploadAccept', function (object, ret) {
			if (ret.error) {
				layer.msg(ret.error_msg);
			} else {
				layer.msg(ret.success_msg);
				window.location.reload();
			}
		});
	}
	function changeFrameHeight(){
	    var ifm= document.getElementById("myiframe"); 
	    ifm.height=(document.documentElement.clientHeight)-56;
	}
	function bindEvent(){
		$('#add_pro').on('click',function(){
			$('#myiframe').attr({"src":"${basePath}project/goAdd"});
		});
		
		$('#index').on('click',function(){
			$('#myiframe').attr({"src":"${basePath}api/home.jsp"});
		});
		
		$('.item').on('click',function(){
			var _projectId = $(this).attr("_projectId");
			window.location.href = "${basePath}project/item"+_projectId;
		});
		
		$('.user-center').on('click',function(){
			window.location.href = "${basePath}user/personalIndex";
		});
		
		$('.percenal-panel').on('click',function(){
			$('.navbar-right>.list').toggleClass('display');
		});
	}
</script>
</html>