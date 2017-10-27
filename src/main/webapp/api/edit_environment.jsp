<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加新环境</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_environment.css"/>
</head>
<body>
	<div class="content-box">
		<div class="text-center fs-18 title">编辑环境</div>
		<div class="detail">
			<p>环境变量运行在URL中,你可以配置多个(线上、灰度、开发)环境变量。在URL中使用方式$变量名$,例：</p>
			<p>线上环境：prefix => http://www.XXXX.com.cn 则</p>
			<p>请求URL：$prefix$/say => http://www.XXXX.com.cn/say</p>
		</div>
		<div class="enbox" envid="${envid}">
			<input type="text" id="env_name" placeholder="请输入环境名称" value ="${name}" org="${name}"/>
		</div>
		
		<c:forEach items="${list}" var="t">
			<div class="vabox" _id="${t.id}">
				<input  type="text" class="va_name" placeholder="变量名" value="${t.paramName}" org="${t.paramName}"/><input type="text" class="va_value" placeholder="变量值" value="${t.paramValue}" org="${t.paramValue}"/><span class="glyphicon glyphicon-remove"></span>
				</div>
		</c:forEach>
		<div class="btnbox text-center">
			<button class="btn btn-default btn-sm" type="reset">取消</button>
			<button class="btn btn-primary btn-sm" id="confirm" type="submit">确定</button>
		</div>
	</div>
</body>
<%@include file="../UI/basic.inc.jsp"%>
<script type="text/javascript">
$(function(){
	var del = [];
	bindEvent(del);
});

function bindEvent(del){
	$(document).on("click",'.glyphicon-remove',function(){
		var $this = $(this);	
		$this.parent().remove();
		var _id = $this.parent().attr("_id");
		if(_id !=null && _id != '' && _id !=undefined){
			del.push(_id);
		}
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
		var param = {};
		var envid = $('.enbox').attr('envid');
		var update_paramName = [];
		var update_paramValue = [];
		var insert_paramName = [];
		var insert_paramValue= [];
		var id = [];
		var name = $('#env_name').val();
		if(name != $('#env_name').attr('org')){
			param.env_name = name;
		}
		$('.va_name').each(function(index){
			var _id = this.parentNode.getAttribute('_id') ;
			if(_id!=null && _id!= undefined && _id != ''){
				var paramName = this.value;
				var next = $(this).next();
				var paramValue = next.val();
				if(paramName && paramName != $(this).attr('org') || paramValue && paramValue != next.attr('org')){
					 id.push(_id); 
					 update_paramName.push(paramName);
					 update_paramValue.push(paramValue);
				}
			}else{
				var paramName = this.value;
				var paramValue = $(this).next().val()
				if(paramName!='' && paramValue){
					insert_paramName.push(paramName);
					insert_paramValue.push(paramValue);
				}
			}
		});
		if(update_paramName.length>0){
			param.update_paramName = update_paramName.join(',');
			param.update_paramValue = update_paramValue.join(',');
			param.id = id.join(',');
		}
		if(insert_paramName.length>0){
			param.insert_paramName = insert_paramName.join(',');
			param.insert_paramValue = insert_paramValue.join(',');
		}
		if(del.length > 0){
			param.del = del.join(',');
		}
		var len = 0;
		for(var p in param) { len++};
		if(len == 0){
			top.Base.alert('你没有做任何修改!');
			return;
		}else{
			param.envId = envid;
			console.log(param);
			Base.ajax({
				'type':'POST',
				'url':Base.globvar.basePath+'env/updateEnv',
				'data':param,
				success:function(data){
					if(!data.error){
						top.window.getEnvList();
						top.window.queryDefaultEnv();
						var doc = top.document.getElementById('myframe' ).contentDocument || top.document.frames['myframe'].document;
						doc.location.reload();
						top.layer.closeAll();
					}
				}
			});
		}
	});
}
</script>
</html>