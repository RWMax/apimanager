<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/edit_navbar.css"/>
<div class="left-nav float-l">
	<div class="btns-box">
		<div id="add_sort" class="float-l btn color-3"><i class="fa fa-plus"></i>&nbsp;添加分类</div>
		<div class="float-r btn color-3" onclick="addFolderApi()"><i class="fa fa-plus"></i>&nbsp;添加接口</div>
	</div>
	<ul>
		<!-- <li class="nav-bar document <c:if test="${document}">active-bar</c:if>">文档说明</li>
		<li class="nav-bar global_parameter">模块全局参数</li> -->
		<c:forEach items="${folders}" var="f">
			<li class="nav-bar folder-li" _folderId="${f.id}">
				<i class="fa fa-caret-down" style="width:13px;"></i>&nbsp;&nbsp;${f.name}
				<span class="float-r">
					<i class="fa fa-plus add_api_module" _folderId="${f.id}"></i>
					&nbsp;&nbsp;
					<i class="fa fa-caret-down show_operate">
						<div class="operate text-center display">
							<!-- <a class="color-6">
								<i class="fa fa-copy"></i>&nbsp;&nbsp;<span>复制</span>
							</a>
							<a class="color-6">
								<i class="fa fa-exchange"></i>&nbsp;&nbsp;<span>移动</span>
							</a> -->
							<a class="color-6" onclick="editFolder(${f.id})">
								<i class="fa fa-edit"></i>&nbsp;&nbsp;<span>编辑</span>
							</a>
							<a class="color-6" onclick="deleteFolder(${f.id})">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;<span>删除</span>
							</a>
						</div>
					</i> 
				</span>
			</li>
		</c:forEach>
		
		<!-- <ul>
			<li class="list">测试rest url</li>
			<li class="list">测试rest url</li>
		</ul>
		<li class="nav-bar">
			<i class="fa fa-caret-down"></i> 测试RAW
			<span class="float-r">
				<i class="fa fa-plus"></i>
				&nbsp;&nbsp;
				<i class="fa fa-caret-down show_operate">
					<div class="operate text-center display">
						<a class="color-6">
							<i class="fa fa-copy"></i>&nbsp;&nbsp;<span>复制</span>
						</a>
						<a class="color-6">
							<i class="fa fa-exchange"></i>&nbsp;&nbsp;<span>移动</span>
						</a>
						<a class="color-6">
							<i class="fa fa-edit"></i>&nbsp;&nbsp;<span>编辑</span>
						</a>
						<a class="color-6">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;<span>删除</span>
						</a>
					</div>
				</i> 
			</span>
		</li>
		<ul>
			<li class="list">测试RAW
				<span class="float-r">
					<i class="fa fa-caret-down show_operate">
						<div class="operate text-center display">
							<a class="color-6">
								<i class="fa fa-copy"></i>&nbsp;&nbsp;<span>复制</span>
							</a>
							<a class="color-6">
								<i class="fa fa-exchange"></i>&nbsp;&nbsp;<span>移动</span>
							</a>
							<a class="color-6">
								<i class="fa fa-edit"></i>&nbsp;&nbsp;<span>编辑</span>
							</a>
							<a class="color-6">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;<span>删除</span>
							</a>
						</div>
					</i> 
				</span>
			</li>
			<li class="list">测试RAW</li>
			<li class="list">测试RAW</li>
		</ul> -->
	</ul>
</div>
<script type="text/javascript">
$(function(){
	bindOperate();
	bindEvent();
	changeWidth();
	window.onresize=function(){  
	    changeWidth();
	}
	$(document).on("click",function(event){
		event.cancelBubble = true
		event.stopPropagation();
		$('.operate').each(function(){
			if(!$(this).hasClass("display")){
		        $(this).addClass('display');
		    }
		});
	})
	
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
					html.push('</a><span class="folder-operation">');
					html.push('<i class="fa fa-caret-down show_operate_'+folderId+'">');
					html.push('<div class="operate text-center display">');
					html.push('<a class="color-6" onclick="copyApi('+api.id+')"><i class="fa fa-copy"></i>&nbsp;&nbsp;<span>复制</span></a>'); // 复制
					html.push('<a class="color-6" onclick="moveApi('+api.id+')"><i class="fa fa-exchange"></i>&nbsp;&nbsp;<span>移动</span></a>'); //移动
					//html.push('<a class="color-6"><i class="fa fa-edit"></i>&nbsp;&nbsp;<span>编辑</span></a>'); // 编辑
					html.push('<a class="color-6" onclick="deleteApi('+api.id+')"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;<span>删除</span></a>'); // 删除
					html.push('</div></i></span>');
					html.push('</li>');
				}
				html.push('</ul>');
				$obj.after(html.join(''));
				$('.show_operate_'+folderId).on('click',function(event){
					event.cancelBubble = true
					event.stopPropagation();
					var $this = $(this);
					$this.children('.operate').toggleClass('display');
				})
			}
		}
	});
}

function refreshPage(folderId,id) {
    var url = Base.globvar.basePath + 'api/edit';
	url += "?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId+"&folderId="+folderId+"&id="+id;
	__iframebox.src = url;
}

// 移动API
function moveApi(id) {
	var options = {};
	options.href = Base.globvar.basePath+"api/operation?operation=move&id="+id;
	options.width = 440+'px';
	options.height = 280+'px';
	options.title = '';
	Base.openIframe(options);
}

// 复制API
function copyApi(id) {
	var options = {};
	options.href = Base.globvar.basePath+"api/operation?operation=copy&id="+id;
	options.width = 440+'px';
	options.height = 280+'px';
	options.title = '';
	Base.openIframe(options);
}

// 删除API
function deleteApi(id) {
	if(id){
		Base.confirm('确认删除吗?',function(yes){
			if(yes){
				// 确认删除
				Base.ajax({
					url:Base.globvar.basePath + "api/delete/"+id,
					type:'get',
					data:null,
					dataType:'json',
					success:function(data){
						if(!data.error){
							// 删除成功以后
							window.location.reload();
						}
					}
				});
			}
		});
	}
}

function deleteFolder(folderId) {
	if(folderId){
		Base.confirm('删除分类,分类以及API将会一起删除,确认删除?',function(yes){
			if(yes){
				// 确认删除
				Base.ajax({
					url:Base.globvar.basePath + "api/folder/delete/"+folderId,
					type:'get',
					data:null,
					dataType:'json',
					success:function(data){
						if(!data.error){
							// 删除成功以后
							window.location.reload();
						}
					}
				});
			}
		});
	}
}

function editFolder(folderId) {
	var options = {};
	if(folderId){
		options.href = Base.globvar.basePath+"api/folder/add?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId+"&folderId="+folderId;
	}else{
		options.href = Base.globvar.basePath+"api/folder/add?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId;
	}
	options.width = 440+'px';
	options.height = 280+'px';
	options.title = '';
	Base.openIframe(options);
}

function changeWidth(){
	var width = document.body.clientWidth - 251;
	$('.right-content').css({'width':width});
}

function bindOperate() {
	$('.show_operate').on("click",function(event){
		event.cancelBubble = true
		event.stopPropagation();
		var $this = $(this);
		$this.children('.operate').toggleClass('display');
	});
	
	$('.add_api_module').on('click',function(event){
		event.cancelBubble = true
		event.stopPropagation();
		var $this = $(this);
		addFolderApi($this.attr('_folderId'));
	})
	
	$('.operate').on('blur',function(){
		$('.operate').addClass('display');
	});
	
	$('.nav-bar').on("click",function(){
		var $this = $(this);
		if($this.hasClass('folder-li')){
			var flag = $this.next('ul').length
			if(flag){
				$this.next('ul').slideToggle();
				$this.siblings('.nav-bar').removeClass('active-bar');
				if($this.children('.fa').hasClass('fa-caret-down')){
					$this.children('.fa').removeClass('fa-caret-down').addClass('fa-caret-right');
				}else{
					$this.children('.fa').removeClass('fa-caret-right').addClass('fa-caret-down');
				}
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

function bindEvent(){
	/*添加分类*/
	$('#add_sort').on("click",function(){
		var options = {};
		options.href = Base.globvar.basePath+"api/folder/add?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId;
		options.width = 440+'px';
		options.height = 280+'px';
		options.title = '';
		Base.openIframe(options);
	});	
	
	// 模块全局参数设置
	$('.global_parameter').on("click",function(){
		//待定
	})

	// 文档说明
	$('.document').on('click',function(){
        var url = Base.globvar.basePath + 'api/document?projectId='+__curr_projectId+'&moduleId='+__curr_moduleId;
		__iframebox.src = url;
	})
}
</script>