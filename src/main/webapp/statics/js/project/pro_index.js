$(function(){
	initModules();
	bindEvent();
	openDialog();
	
	$(document).on("click",function(event){
		event.cancelBubble = true
		event.stopPropagation();
		$('.caret-op').each(function(){
			if(!$(this).hasClass("display")){
		        $(this).addClass('display');
		    }
		});
	})
})
var EDIT_TYPE = {
	VIEW:'view',
	EDIT:'edit'
}
var __curr_projectId = $("#currentProject").attr("_projectId");
var __modulesContainer = $("#modules");
var __curr_moduleId;
var __curr_editType = EDIT_TYPE.VIEW;// 默认编辑模式
var __iframebox = document.getElementById('myframe');

function initModules() {
	reFreshModules();
}

function reFreshModules() {
	// 查询模块
	Base.ajax({
		url:Base.globvar.basePath + "project/"+__curr_projectId+"/modules",
		type:'get',
		data:null,
		dataType:'json',
		mask:false,
		success:function(data){
			createModules(data);
		}
	});
}

//创建模板 默认选中第一个模板
function createModules(modules) {
	var defaultM = '<a class="J_menuTab active">'+
						'<span>默认模块</span>'+
						'<span class="caret display">'+
							'<div class="caret-op display">'+
								'<div onclick="editModule('+__curr_moduleId+')"><i class="fa fa-edit"></i>编辑</div>'+
								'<div onclick="deleteModule('+__curr_moduleId+')"><i class="fa fa-remove"></i>删除</div>'+
							'</div>'+
						'</span>'+
					'</a>';
	__modulesContainer.empty();
	var html = [];
	if(modules.length > 0){
		for ( var i in modules) {
			var m = modules[i];
			html.push('<a class="J_menuTab ');
			if(i == 0){
				html.push('active');
				__curr_moduleId = m.id;
			}
			html.push('" _moduleId="'+m.id+'">');
			html.push('<span>'+m.name+'</span>');
			html.push('<span class="caret display">');
			html.push('<div class="caret-op display animated fadeInRight">');
			html.push('<div onclick="editModule('+m.id+')"><i class="fa fa-edit"></i>编辑</div>');
			html.push('<div onclick="deleteModule('+m.id+')"><i class="fa fa-remove"></i>删除</div>');
			html.push('</div></span></a>');
		}
		__modulesContainer.html(html.join(''));
	}else {
		__modulesContainer.html(defaultM);
	}
	
	$(".caret").on("click",function(event){
		event.cancelBubble = true
		event.stopPropagation();
		var target = $(this).find('.caret-op');
		if($(target).hasClass('display')){
			$(target).removeClass('display');
		}else{
			$(target).addClass('display');
		}
	});
	
	$('.J_menuTab').on("click",function(){
		$(this).siblings().find(".caret-op").each(function(){
			if(!$(this).hasClass('display')){
				$(this).addClass('display');
			}
		});
		__curr_moduleId = parseInt($(this).attr("_moduleId"));
		if(!$(this).hasClass('active')){
			addApi();
		}
	});
	
	addApi();
	
	changePageMode(__curr_editType);
}

function addApi(folderId,id) {
	var url = Base.globvar.basePath + "api/"+__curr_editType+"?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId;
	if(folderId){
		url += "&folderId="+folderId;
	}/*else {
		layer.tips("先添加一个分类吧!",$("#add_sort"),{tips:[3,'#ed5565']});
		return;
	}*/
	if(id){
		url += "&id="+id;
	}
	__iframebox.src = url;
}

function editModule(moduleId) {
	var options = {};
	if(moduleId){
		options.href = Base.globvar.basePath+"project/module/add?projectId="+__curr_projectId+"&moduleId="+moduleId;
	}else{
		options.href = Base.globvar.basePath+"project/module/add?projectId="+__curr_projectId;
	}
	options.title = '';
	options.width = 440+'px';
	options.height = 280+'px';
	Base.openIframe(options);
}
function deleteModule(moduleId) {
	if(!moduleId || moduleId == 0){
		Base.alert('默认模块,保留一个火种吧!');
		return;
	}
	Base.confirm('删除模块,模块下的分类,API将会一起删除,确认删除?',function(yes){
		if(yes){
			// 确认删除
			Base.ajax({
				url:Base.globvar.basePath + "project/module/delete?projectId="+__curr_projectId+"&moduleId="+moduleId,
				type:'get',
				data:null,
				dataType:'json',
				success:function(data){
					if(!data.error){
						reFreshModules();
					}
				}
			});
		}
	});
}

function changePageMode(type) {
	if(type == EDIT_TYPE.EDIT){
		$('.page-tabs').css({'left':'40px'}).find('.caret').removeClass('display');
		$('.add_module').removeClass('display');
	}else if(type == EDIT_TYPE.VIEW){
		$('.page-tabs').css({'left':'0px'}).find('.caret').addClass('display');
		$('.add_module').addClass('display');
	}
}

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

function bindEvent(){
	
	/*编辑模式浏览模式切换*/
	$('.mode-btnbox>a').on("click",function(){
		var $this = $(this);
		if($this.attr('tag') == __curr_editType){
			return;
		}
		__curr_editType = $this.attr('tag');
		$this.addClass('active').siblings().removeClass('active');
		changePageMode(__curr_editType);
		if(__iframebox.src){
			var param = getPageParam(__iframebox.src);
			addApi(param.folderId,param.id);
		}
	});
	
	/*选择项目*/
	$('.project').on("click",function(){
		var $this = $(this);
		var text = $this.children().children('span').text();
		$('#my_project').text(text);
	});
}

function openDialog(){
	var options = {};

	/*添加模块*/
	$('.add_module').on("click",function(){
		options.href = Base.globvar.basePath+"project/module/add?projectId="+__curr_projectId;
		options.width = 440+'px';
		options.height = 280+'px';
		options.title = '';
		Base.openIframe(options);
	});
	
	/*添加环境*/
	$(document).on("click",'#add_enment',function(){
		options.href = Base.globvar.basePath+"api/add_environment.jsp";
		options.title = '添加环境';
		options.width = 530+'px';
		options.height = 430+'px';
		Base.openIframe(options);
	});	
}

function exportProject(projectId) {
	window.location.href = Base.globvar.basePath + "project/exportPdf?projectId="+projectId;
}
function exportJson(projectId) {
	window.location.href = Base.globvar.basePath + "project/exportJson?projectId="+projectId;
}
function deleteProject(projectId) {
	Base.confirm('确认删除?',function(yes){
		if(yes){
			// 确认删除
			Base.ajax({
				url:Base.globvar.basePath + "project/delete/"+projectId,
				type:'get',
				data:null,
				dataType:'json',
				success:function(data){
					if(!data.error){
						setTimeout(function(){
							window.location.href = Base.globvar.basePath + 'home';
						}, 600);
					}
				}
			});
		}
	});
} 