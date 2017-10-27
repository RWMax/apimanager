<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>添加新环境</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_environment.css"/>
</head>
<body>
	<div class="content-box">
		<div class="text-center fs-18 title">添加新环境</div>
		<div class="detail">
			<p>环境变量运行在URL中,你可以配置多个(线上、灰度、开发)环境变量。在URL中使用方式$变量名$,例：</p>
			<p>线上环境：prefix => http://www.XXXX.com.cn 则</p>
			<p>请求URL：$prefix$/say => http://www.XXXX.com.cn/say</p>
		</div>
		<div class="enbox">
			<input type="text" id="env_name" placeholder="请输入环境名称"/>
		</div>
		<div class="vabox">
			<input type="text" class="va_name" placeholder="变量名"/><input type="text" class="va_value" placeholder="变量值"/><span class="glyphicon glyphicon-remove"></span>
		</div>
		<div class="btnbox text-center">
			<button class="btn btn-default btn-sm" type="reset">取消</button>
			<button class="btn btn-primary btn-sm" id="confirm" type="submit">确定</button>
		</div>
	</div>
</body>
<%@include file="../UI/basic.inc.jsp"%>
<script type="text/javascript">
$(function(){
	bindEvent();
});

function bindEvent(){
	$(document).on("click",'.glyphicon-remove',function(){
		var $this = $(this);	
		$this.parent().remove();
	});
	$(document).on("focus",'.va_name',function(){
		var $this = $(this);
		var flag = $this.parent().next('.vabox').length;
		if(!flag){
			$this.parent().after(
				'<div class="vabox">'
					+'<input type="text" class="va_name" placeholder="变量名"/>'
					+'<input type="text" class="va_value" placeholder="变量值"/>'
					+'<span class="glyphicon glyphicon-remove"></span>'
				+'</div>'	
			);
		}
	});
	$(document).on("click",'#confirm',function(){
		var result = getMuiltValue($('.va_name'),$('.va_value'));
		if(result === 'error'){
			return;
		}else{
			if(result.env_name && result.paramName && result.paramValue){
				Base.ajax({
					'type':'POST',
					'url':Base.globvar.basePath+'env/addEnv',
					'data':result,
					success:function(data){
						if(!data.error){
							top.window.getEnvList();
							top.layer.closeAll();
						}
					}
				});
			}else{
				top.Base.alert('请检查数据项');
			}
		}
	})
	
	function getMuiltValue(n,v){
		var name = [];
		var value = [];
		for(var i = 0,len = n.length; i< len; i++ ){
			if(n[i].value!='' && v[i].value!=''){
				name.push(n[i].value);
				value.push(v[i].value);
			}else if(n[i].value =='' && v[i].value == ''){}
			else{
				Base.alert('变量名称和值没有对应，请检查数据项');
				return 'error';
			}
		}
		var href = top.window.location.href;
		var project_id= href.slice(href.lastIndexOf('/') + 5);
		return {
			projectId: project_id,
			env_name: document.getElementById('env_name').value,
			paramName: name.join(','),
			paramValue:value.join(',')
		};
	}
}
</script>
</html>