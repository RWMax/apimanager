var env_list = null;
$(document).ready(function(){
	getEnvList();
	EnvControll();
	configEnv();
	top.window.getEnvList = getEnvList;
	top.window.queryDefaultEnv = queryDefaultEnv;
	top.env_list = env_list;
});

function configEnv(){
	$(document).on('click','.J_tabShowActive',function(){
		var href = top.window.location.href;
		var project_id= href.slice(href.lastIndexOf('/') + 5);
		var envId= $(this).attr('envid');
		var name = $(this).find('a').eq(0).text();
		Base.ajax({
			'type':'POST',
			'url':Base.globvar.basePath+'project/env',
			'data':{
				'projectId': project_id,
				'envId': envId
			},
			success:function(data){
				if(!data.error){
					$('#dropenv').html(name+'<span class="caret" default_id='+envId+'>');
					env_list = data.result;
					updateChildWindow(data.result);
				}else{
					top.Base.alert(data.msg);
				}
			}
		});
	});
}

function updateChildWindow(result){
	var doc = document.getElementById('myframe' ).contentDocument || document.frames['myframe'].document;
	$(doc).ready(function(){
		if(__curr_editType == EDIT_TYPE.EDIT){
			$('.environments_container',doc).show();
			$('.url_real',doc).show();
			var container = doc.getElementById('environments_container');
			var id = doc.getElementById('id');
			var html = '';
			var url = doc.getElementById('url').value || '';
			for(var i = 0, len = result.length; i < len ; i++){
				html +='<i>' + result[i].paramName + '</i>';
				var reg = '\\\$' + result[i].paramName + '\\\$';
				url = url.replace(new RegExp(reg,'gm'),result[i].paramValue);
			}
			doc.getElementById('url_real').innerHTML = url;
			container.innerHTML = html;
		}else{
			doc.location.reload();
		}
		
	});
}
function queryDefaultEnv(){
	var href = top.window.location.href;
	var project_id= href.slice(href.lastIndexOf('/') + 5);
	Base.ajax({
		'type':'POST',
		'url':Base.globvar.basePath+'project/queryDefaultEnv',
		'data':{
			'projectId': project_id
		},
		success:function(data){
			if(!data.error){
				if(data.result!=null){
					$('#dropenv').html(data.result.name + '<span class="caret" default_id ='+data.result.envId+'></span>');
				}else{
					$('#dropenv').html('添加环境' + '<span class="caret"></span>');
				}
			}else{
				$('#dropenv').html('添加环境' + '<span class="caret"></span>');
			}
		}
	});
}
function getEnvList(){
	var href = top.window.location.href;
	var project_id= href.slice(href.lastIndexOf('/') + 5);
	Base.ajax({
		'type':'POST',
		'url':Base.globvar.basePath+'env/getEnvList',
		'data':{
			'projectId': project_id
		},
		success:function(data){
			if(data!= null){
				//清空元素
				$('#env-list').empty();
				var html = [];
				html.push('<li class="J_tabCloseAll" id="add_enment">');
				html.push('<a>添加环境</a></li><div class="hover-table" id="hover-table">');
				html.push('<ul><li class="col-sm-4">变量</li><li class="col-sm-8">值<div class="cb float-r m-t-xs">');
				html.push('<i id="env-edit" title="编辑" class="fa fa-edit"></i>');
				html.push('<i id="env-copy" title="复制" class="fa fa-files-o m-l-xs"></i>');
				html.push('<i id="env-del" title="删除" style="font-size:16px;" class="fa fa-remove m-l-xs"></i>');
				html.push('</div></li></ul></div>');
				$('#env-list').html(html.join(''));
				var i = 0;
				var list = [];
				var table = [];
				for(var item in data){
					list.push('<li class="J_tabShowActive" _index='+i+' envid='+item+'>');
					list.push('<a>'+data[item][0].name+'</a></li>')
					list.push('<li class="divider"></li>');
					var temp = data[item];
					table.push('<div class="table-list">');
					for(var j = 0, len = temp.length; j < len ;j++){
						table.push('<div class="list-item">');
						table.push('<li class="col-sm-4">'+temp[j].paramName+'</li>');
						table.push('<li class="col-sm-8">'+temp[j].paramValue+'</li>');
						table.push('</div>');
					}
					table.push('</div>');
					i++;
				}
				$('#add_enment').before(list.join(''));
				$('#hover-table').append(table.join(''));
				addEventListener();
			}
		}
	});
}
function addEventListener(){
	$('.J_tabShowActive').hover(function() {
		var $this = this;
		var _index = this.getAttribute('_index');
		var table = document.getElementById("hover-table");
		table.style.display = "block";
		table.style.top = 40 * _index + 'px';
		$('.table-list').each(function(index) {
			if (index == _index) {
				this.style.display = 'block';
				$('.cb').attr('envid', $this.getAttribute('envid'));
			} else {
				this.style.display = 'none';
			}
		});
	});
	$('#env-list').hover(null, function() {
		var table = document.getElementById("hover-table");
		table.style.display = "none";
		$('.table-list').hide();
	});
	$('#add_enment').hover(function() {
		var table = document.getElementById("hover-table");
		table.style.display = "none";
		$('.table-list').hide();
	});
}
function EnvControll() {
	//删除
	$(document).on('click', '#env-del', function() {
		var envid = this.parentNode.getAttribute('envid');
		var href = top.window.location.href;
		var project_id= href.slice(href.lastIndexOf('/') + 5);
		Base.ajax({
			'type': 'POST',
			'url': Base.globvar.basePath + 'env/deleteEnv/'+ envid,
			'data':{'projectId': project_id},
			success: function(data) {
				if(!data.error){
					queryDefaultEnv();
					getEnvList();
					$("#dropenv").trigger('click');
					var doc = document.getElementById('myframe' ).contentDocument || document.frames['myframe'].document;
					doc.location.reload();
				}
			}
		});
	});
	//编辑
	$(document).on('click','#env-edit',function(){
		var options = {};
		var envid = this.parentNode.getAttribute('envid');
		options.href = Base.globvar.basePath+"env/editPage/"+ envid;
		options.title = '编辑环境';
		options.width = 530+'px';
		options.height = 430+'px';
		Base.openIframe(options);
	});
	//复制
	$(document).on('click','#env-copy',function(){
		var envid = this.parentNode.getAttribute('envid');
		Base.ajax({
			'type': 'POST',
			'url': Base.globvar.basePath + 'env/copyEnv',
			'data':{'envId': envid},
			success: function(data) {
				if(!data.error){
					getEnvList();
					$("#dropenv").trigger('click');
				}
			}
		});
	});
}