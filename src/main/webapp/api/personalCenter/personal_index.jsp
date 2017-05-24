<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>
	<%@include file="../../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/index.css"/>
</head>
<body>
     <!-- 左侧导航开始 -->
	 <nav class="navbar-default navbar-static-side" role="navigation">
	    <div class="left-nav float-l">
	    	<div class="logo"></div>
	    	<div class="person_nav">
	    		<ul>
	    			<li tag="center">个人中心</li>
	    			<li tag="security">安全设置</li>
	    			<li tag="account">关联账户</li>
	    			<li tag="exit">退出登录</li>
	    		</ul>
	    	</div>
	    </div>
	 </nav>
     <!-- 左侧导航结束 -->
	 <!--右侧部分开始-->
     <div id="page-wrapper" class="gray-bg dashbard-1">
     
         <div class="row border-bottom">
             <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                 <div class="navbar-header">
	                  <span class="back">返回控制台</span>
	                  <span class="fs-14 color-3 index">首页</span>
	                  <span class="fs-14 color-3">帮助</span>
                 </div>
             </nav>
         </div>
         
         <div class="row J_mainContent" id="content-main">
         	<iframe class="J_iframe" id="myiframe" onload="changeFrameHeight()" name="iframe0" width="100%" height="100%" src="<%=basePath%>user/personalInfo" frameborder="0" data-id="" seamless></iframe>
  		 </div>
  	
    </div>
 	<!--右侧部分结束-->
</body>
<%@ include file="/UI/basic.inc.jsp" %>
<script type="text/javascript" src="<%=basePath%>UI/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript">
	$(function(){
		window.onresize=function(){  
		     changeFrameHeight();
		} 
		 bindEvent();
	});
	function changeFrameHeight(){
	    var ifm= document.getElementById("myiframe"); 
	    ifm.height=(document.documentElement.clientHeight)-56;
	}
	function bindEvent(){
		$('li').on('click',function(){
			var $this = $(this);
			var flag = $this.attr('tag');
			var iframeDom = $('#myiframe');
			var iframeSrc;
			switch(flag){
				case "center":
					iframeSrc = "${basePath}user/personalInfo";
				break;
				case "security":
					iframeSrc = "${basePath}user/personalSecurity";
				break;
				case "account":
					iframeSrc = "${basePath}api/personalCenter/account_link.jsp";
				break;
				case "exit":
					window.location.href = "${basePath}logout";
				break;
			}
			iframeDom.attr({"src":iframeSrc});
		});
		$('.index,.back').on('click',function(){
			window.location.href = "${basePath}home";
		});
		
		$('.percenal-panel').on('click',function(){
			$('.navbar-right>.list').toggleClass('display');
		});
	}
</script>
</html>