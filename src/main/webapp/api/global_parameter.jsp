<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>模块全局参数</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_interface.css"/>
</head>
<body>
	<div class="content-box">
		<div class="info5-box col-sm-12">
			<label class="color-3 fs-12">全局请求头</label>
		</div>
		<div class="col-sm-12 fs-12">
			<table class="col-sm-12 data_01">
				<tr>
					<td class="col-sm-1">操作</td>
					<td class="col-sm-3">参数名称</td>
					<td class="col-sm-2">是否必须</td>
					<td class="col-sm-6">描述</td>
				</tr>
				<tr>
					<td class="col-sm-1">
						<i class="fa fa-close fs-16"></i>
					</td>
					<td class="col-sm-3 color-red">detail_infos</td>
					<td class="col-sm-2">
						<select>
							<option>true</option>
							<option>false</option>
						</select>
					</td>
					<td class="col-sm-6">
						<input type="text" value="是否执行成功"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="add_btns brb">
			<button type="button" class="btn btn-default btn-xs add_data_01">添加参数</button>
			<button type="button" class="btn btn-default btn-xs add-json">导入json</button>
		</div>
		
		<div class="info6-box col-sm-12">
			<label class="color-3 fs-12">全局请求头</label>
		</div>
		<div class="col-sm-12 fs-12">
			<table class="col-sm-12 data_02">
				<tr>
					<td class="col-sm-1">操作</td>
					<td class="col-sm-2">参数名称</td>
					<td class="col-sm-1">是否必须</td>
					<td class="col-sm-2">类型</td>
					<td class="col-sm-4">描述</td>
					<td class="col-sm-2">默认值</td>
				</tr>
				<tr>
					<td class="col-sm-1">
						<i class="fa fa-close fs-16"></i>
					</td>
					<td class="col-sm-2 color-red">detail_infos</td>
					<td class="col-sm-1">
						<select>
							<option>true</option>
							<option>false</option>
						</select>
					</td>
					<td class="col-sm-2">
						<select>
							<option>string</option>
							<option>number</option>
						</select>
					</td>
					<td class="col-sm-4">
						<input type="text"/>
					</td>
					<td class="col-sm-2">
						<input type="text"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="add_btns">
			<button type="button" class="btn btn-default btn-xs add_data_02">添加参数</button>
			<button type="button" class="btn btn-default btn-xs add-json">导入json</button>
		</div>
			
        <div class="save">
        	<div class="save-btn color-w text-center">保存修改</div>
        </div>
        
	</div>
</body>
<%@ include file="/UI/basic.inc.jsp" %>
<script type="text/javascript">
$(function(){
	bindEventListener();
});
function bindEventListener(){
	
	/*添加JSON弹窗*/	
	$('.add-json').on("click",function(){
		var options = {};
		options.href = "${basePath}api/import_json.jsp";
		options.width = 530;
		options.height = 380;
		Base.openIframe(options);
	});
	
	/*删除表格一行*/
	$(document).on("click",'.fa-close',function(){
		var $this = $(this);
		$this.parent().parent().remove();
		
	});
	
	/*响应数据添加参数*/
	$('.add_data_01').on("click",function(){
		$('.data_01').append(
				'<tr>'
				+'<td class="col-sm-1">'
					+'<i class="fa fa-close fs-16"></i>'
				+'</td>'
				+'<td class="col-sm-3 color-red">'
					+'<input type="text"/>'
				+'</td>'
				+'<td class="col-sm-2">'
					+'<select>'
						+'<option>true</option>'
						+'<option>false</option>'
					+'</select>'
				+'</td>'
				+'<td class="col-sm-6">'
					+'<input type="text"/>'
				+'</td>'
			+'</tr>'
				);
		
	});
	
	/*发送数据添加参数*/
	$('.add_data_02').on("click",function(){
		$('.data_02').append(
				'<tr>'
					+'<td class="col-sm-1">'
						+'<i class="fa fa-close fs-16"></i>'
					+'</td>'
					+'<td class="col-sm-2 color-red">'
						+'<input type="text"/>'
					+'</td>'
					+'<td class="col-sm-1">'
						+'<select>'
							+'<option>true</option>'
							+'<option>false</option>'
						+'</select>'
					+'</td>'
					+'<td class="col-sm-2">'
						+'<select>'
							+'<option>string</option>'
							+'<option>number</option>'
						+'</select>'
					+'</td>'
					+'<td class="col-sm-4">'
						+'<input type="text"/>'
					+'</td>'
					+'<td class="col-sm-2">'
						+'<input type="text"/>'
					+'</td>'
				+'</tr>'
				);
		
	});

}
</script>
</html>