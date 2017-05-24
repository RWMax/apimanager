<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/edit_navbar.css"/>
<div class="left-nav float-l" style="padding:10px 6px;">
	<ul>
		<!-- <li class="nav-bar">文档说明</li> -->
		<c:forEach items="${folders}" var="f">
			<li class="nav-bar folder-li" _folderId="${f.id}">
				<i class="fa fa-caret-right"></i>&nbsp;&nbsp;${f.name}
			</li>
		</c:forEach>
	</ul>
</div>
<script type="text/javascript">
$(function(){
	bindEvent();
	changeWidth();
	window.onresize=function(){  
	    changeWidth();
	}
	$(".folder-li").each(function(){
		$this = $(this);
		var folderId = $this.attr("_folderId");
		initAPI(folderId, $this);
	});
});

function getPageParam(url) {
	var param = {};
	var args = url.split("?");
	if(args[0] == url){
		// 参数为空
	}else{
		var strs = args[1].split("&");
		for ( var i in strs) {
			var str = strs[i];
			var ss = str.split("=");
			param[ss[0]] = ss[1];
		}
	}
	return param;
}

//查询分类下接口
function initAPI(folderId,$obj) {
	var html = [];
	Base.ajax({
		url:Base.globvar.basePath + "api/query/"+folderId,
		type:'get',
		data:null,
		dataType:'json',
		mask:false,
		success:function(data){
			if(data.length>0){
				// 待定
				html.push('<ul>');
				var pageparam = getPageParam(window.location.href);
				for ( var i in data) {
					var api = data[i];
					if(pageparam.id && pageparam.id == api.id){
						html.push('<li class="list active-bar" onclick="refreshPage('+folderId+','+api.id+')"><a title="'+api.name+'" class="folder-name text-ellipsis">'+api.name);
					}else{
						html.push('<li class="list" onclick="refreshPage('+folderId+','+api.id+')"><a title="'+api.name+'" class="folder-name text-ellipsis">'+api.name);
					}
					html.push('</a></li>');
				}
				html.push('</ul>');
				$obj.after(html.join(''));
			}
		}
	});
}

function refreshPage(folderId,id) {
	var url = window.location.href;
	url = url.split("?")[0];
	url += "?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId+"&folderId="+folderId+"&id="+id;
	__iframebox.src = url;
}

function changeWidth(){
	var width = document.body.clientWidth - 251;
	$('.right-content').css({'width':width});
}

function bindEvent(){
	$('.nav-bar').on("click",function(){
		var $this = $(this);
		if($this.hasClass('folder-li')){
			var flag = $this.next('ul').length
			if(flag){
				$this.next('ul').slideToggle();
				$this.siblings('.nav-bar').removeClass('active-bar');
			}
		}else{
			$('li').removeClass('active-bar');
			$this.addClass('active-bar');
		}
	});

	$('.list').on("click",function(){
		var $this = $(this);
		$('li').removeClass('active-bar');
		$this.addClass('active-bar').siblings('.list');
	})
}
</script>