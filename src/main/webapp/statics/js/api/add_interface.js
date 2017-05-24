$(function(){
	initForm();
	bindEventListener();
});

/**
 * 初始化表单 改为异步提交
 */
function initForm() {
	var validator = $("#form1").validate({
		submitHandler:function(form) {
			var data = $(form).serialize();
			// 处理请求头数据
			var requestHeaders = getRequestHeaders();
			// 处理请求参数数据
			var requestArgs = getRequestArgs();
			// 处理响应数据
			var responseArgs = getResponseArgs();
			data += "&requestHeaders="+Base.objToJsonStr(requestHeaders);
			data += "&requestArgs="+Base.objToJsonStr(requestArgs);
			data += "&responseArgs="+Base.objToJsonStr(responseArgs);
			
			Base.ajax({
				type:'POST',
				url:Base.globvar.basePath+'api/save',
				data:data,
				success:function(data){
					if(!data.error){
						//Base.alert(data.success_msg);
						if(Base.isExist(data.id)){
							if(!Base.isExist($("#id").val())){
								// 不是更新才刷新页面
								refreshPage($('#folderId').val(),data.id);
							}
						}
					}else {
						//Base.alert(data.error_msg);
					}
				}
			});
		},
		errorPlacement: function(error, element) {  
			layer.tips($(error).text(), element, {
			    tips: [1, '#ed5565'],
			});
		}
	});
}

var headerTable = $("#headerTable");
var requestTable = $("#requestTable");
var responseTable = $("#responseTable");

// 点击分类上的添加接口
function addFolderApi(folderId) {
	var url = window.location.href;
	if(url.indexOf('document') > 0) {
		var url = Base.globvar.basePath + 'api/edit';
		url += "?projectId="+__curr_projectId+"&moduleId="+__curr_moduleId;
		__iframebox.src = url;
		return;
	}
	// 删除所有二级table
	$('#form1').find('table').each(function(){
		if($(this).attr('_level') == '2'){
			$(this).parent().remove();
		}
	});
	headerTable.find("tr").each(function(index,element){
		if(index > 0){
			$(element).remove();
		}
	});
	requestTable.find("tr").each(function(index,element){
		if(index > 0){
			$(element).remove();
		}
	});
	responseTable.find("tr").each(function(index,element){
		if(index > 0){
			$(element).remove();
		}
	});
	$("#id").val('');
	$("#name").val('');
	$("#url").val('');
	$("#description").val('');
	$("#example").val('');
	$('#targetNamespace').val();
	$('#methodName').val();
	$('li').removeClass('active-bar');
	if(folderId){
		$("#folderId").val(folderId);
	}
}

function getRequestHeaders() {
	var arr = [];
	headerTable.find("tr").each(function(index,element){
		if(index > 0){
			var tr = {};
			var paramType = $(element).find('td').eq(3).find('select').val();
			var paramName = $(element).find('td').eq(1).find(':input').val();
			tr.paramName = paramName;
			tr.isRequired = $(element).find('td').eq(2).find('select').val();
			tr.paramType = paramType;
			tr.paramDesc = $(element).find('td').eq(4).find('input').val();
			tr.defaultValue = $(element).find('td').eq(5).find('input').val();
			arr.push(tr);
		}
	});
	return arr;
}

function getRequestArgs() {
	var arr = [];
	requestTable.find("tr").each(function(index,element){
		if(index > 0){
			var tr = {};
			var paramName = $(element).find('td').eq(1).find(':input').val();
			var isRequired = $(element).find('td').eq(2).find('select').val();
			var paramType = $(element).find('td').eq(3).find('select').val();
			var paramDesc = $(element).find('td').eq(4).find('input').val();
			var defaultValue = $(element).find('td').eq(5).find('input').val();
			tr.paramName = paramName;
			tr.isRequired = isRequired;
			tr.paramDesc = paramDesc;
			tr.paramType = paramType;
			if(paramType == 'array' || paramType == 'object'){
				var paramTableId = 'table_requestTable_'+paramName;
				tr.defaultValue = getParamArgs(paramTableId);
			}else{
                if(paramType != 'textarea'){
                    tr.defaultValue = defaultValue;
                }else{
                    tr.defaultValue = $(element).find('td').eq(5).find('textarea').val();
                }
			}
			arr.push(tr);
		}
	});
	return arr;
}

function getParamArgs(paramTableId) {
	var arr = [];
	$('#'+paramTableId).find("tr").each(function(index,element){
		if(index > 0){
			var tr = {};
			var paramName = $(element).find('td').eq(1).find(':input').val();
			var isRequired = $(element).find('td').eq(2).find('select').val();
			var paramType = $(element).find('td').eq(3).find('select').val();
			var paramDesc = $(element).find('td').eq(4).find('input').val();
			var defaultValue = $(element).find('td').eq(5).find('input').val();
			tr.paramName = paramName;
			tr.isRequired = isRequired;
			tr.paramDesc = paramDesc;
			tr.paramType = paramType;
            if(paramType != 'textarea'){
                tr.defaultValue = defaultValue;
            }else{
                tr.defaultValue = $(element).find('td').eq(5).find('textarea').val();
            }
			arr.push(tr);
		}
	});
	return arr;
}

function getResponseArgs() {
	var arr = [];
	responseTable.find("tr").each(function(index,element){
		if(index > 0){
			var tr = {};
			var paramName = $(element).find('td').eq(1).find(':input').val();
			var isRequired = $(element).find('td').eq(2).find('select').val();
			var paramType = $(element).find('td').eq(3).find('select').val();
			var paramDesc = $(element).find('td').eq(4).find('input').val();
			var defaultValue = $(element).find('td').eq(5).find('input').val();
			tr.paramName = paramName;
			tr.isRequired = isRequired;
			tr.paramDesc = paramDesc;
			tr.paramType = paramType;
			if(paramType == 'array' || paramType == 'object'){
				var paramTableId = 'table_responseTable_'+paramName;
				tr.defaultValue = getParamArgs(paramTableId);
			}else{
                if(paramType != 'textarea'){
                    tr.defaultValue = defaultValue;
                }else{
                    tr.defaultValue = $(element).find('td').eq(5).find('textarea').val();
                }
			}
			arr.push(tr);
		}
	});
	return arr;
}

var DEFAULTROW = '<tr>'
    +'<td class="col-sm-1">'
    +'<i class="fa fa-close fs-16"></i>'
    +'</td>'
    +'<td class="col-sm-2 color-red">'
    +'<input type="text"/>'
    +'</td>'
    +'<td class="col-sm-1">'
    +'<select>'
    +'<option value="true">true</option>'
    +'<option value="false">false</option>'
    +'</select>'
    +'</td>'
    +'<td class="col-sm-2">'
    +'<select onchange="paramTypeUpdate(this)">'
    +'<option value="string">string</option>'
    +'<option value="number">number</option>'
    +'<option value="boolean">boolean</option>'
    +'<option value="date">date</option>'
	+'<option value="textarea">textarea</option>'
    +'<option value="array">array</option>'
    +'<option value="object">object</option>'
    +'</select>'
    +'</td>'
    +'<td class="col-sm-4">'
    +'<input type="text"/>'
    +'</td>'
    +'<td class="col-sm-2">'
    +'<input type="text"/>'
    +'</td>'
    +'</tr>';

function bindEventListener(){
	/*添加JSON弹窗*/	
	$('.add-json').on("click",function(){
		var $btn = $(this);
		var $table = $('#'+$btn.attr('_tableId'));
		layer.prompt({
			formType: 2,
			title: '只能以大括号开头,大括号结尾 key:value形式',
			area: ['400px', '200px'] //自定义文本域宽高
		}, function(value, index, elem){
			try {
				value = eval('('+value+')');
				if(!Base.isObject(value)){
					throw 'json格式不满足要求';
					return;
				}
				var html = [];
				for(var x in value){
					html.push('<tr>'
							+'<td class="col-sm-1">'
							+'<i class="fa fa-close fs-16"></i>'
						+'</td>'
						+'<td class="col-sm-2 color-red">'
							+'<input type="text" value="'+x+'"/>'
						+'</td>'
						+'<td class="col-sm-1">'
							+'<select>'
								+'<option value="true">true</option>'
								+'<option value="false">false</option>'
							+'</select>'
						+'</td>'
						+'<td class="col-sm-2">'
							+'<select onchange="paramTypeUpdate(this)">'
								+'<option value="string">string</option>'
								+'<option value="number">number</option>'
								+'<option value="boolean">boolean</option>'
								+'<option value="date">date</option>'
								+'<option value="array">array</option>'
								+'<option value="object">object</option>'
							+'</select>'
						+'</td>'
						+'<td class="col-sm-4">'
							+'<input type="text"/>'
						+'</td>'
						+'<td class="col-sm-2">'
							+'<input type="text" value="'+value[x]+'"/>'
						+'</td>'
					+'</tr>');
				}
				$table.append(html.join(''));
				layer.close(index);
			} catch (e) {
				layer.msg('输入的json格式错误',{
					icon:2
				});
			}
		});
	});
	
	/*删除表格一行*/
	$(document).on("click",'.fa-close',function(){
		var $this = $(this);
        // 检查是否是一级参数,是的话,级联删除其下级参数
		var $table = $this.parents('table');
        var tableId = $table.attr('id');
        var level = $table.attr('_level');
        var paramTypeInput = $this.parent().next().next().next().find(':input');
        var paramType = $(paramTypeInput).val();
        var paramNameInput = $this.parent().next().find(':input');
        var paramName = $(paramNameInput).val();
        if(level == 1 || paramType == 'array' || paramType == 'object'){
            var paramTableId = 'table_'+tableId+'_'+paramName;
            if($('#'+paramTableId).length){
                $('#'+paramTableId).parent().remove();
            }
        }
        // 最后删除自己
        $this.parent().parent().remove();
	});
	// 参数列表添加参数button
	$(document).on("click",'.tableParamButton',function(){
		var _paramTableId = $(this).attr('_paramTableId');
		$('#'+_paramTableId).append(DEFAULTROW_NOSELECTUPDATE);
	});

	/*发送数据添加参数*/
	$('.add-header').on("click",function(){
		headerTable.append(DEFAULTROW);
	});
	$('.add-body').on("click",function(){
		requestTable.append(DEFAULTROW);
	});
	/*响应数据添加参数*/
	$('.add_response').on("click",function(){
		responseTable.append(DEFAULTROW);
	});
	
	$('#protocol').on('change',function(){
		var val = $(this).val();
		var $box = $('#targetNamespaceBox');
		var $requestMethod = $('#requestMethod');
		var $methodNameBox = $('#methodNameBox');
		if(val == 'WEBSERVICE'){
			$box.find('input').prop('disabled',false);
			$box.removeClass('display');
			$methodNameBox.find('input').prop('disabled',false);
			$methodNameBox.removeClass('display');
			$requestMethod.prop('disabled',true);
			$requestMethod.parent('span').addClass('display');
		}else{
			$box.find('input').prop('disabled',true);
			$box.addClass('display');
			$methodNameBox.find('input').prop('disabled',true);
			$methodNameBox.addClass('display');
			$requestMethod.prop('disabled',false);
			$requestMethod.parent('span').removeClass('display');
		}
	});
	
	$('#contentType').on('change',function(){
		var val = $(this).val();
		var $box = $('#contentTypeXMLROOT');
		if(val == 'application/xml'){
			$box.find('input').prop('disabled',false);
			$box.removeClass('display');
		}else{
			$box.find('input').prop('disabled',true);
			$box.addClass('display');
		}
	});
	
	$('#dataType').on('change',function(){
		var val = $(this).val();
		var $box = $('#dateTypeXMLROOT');
		if(val == 'XML'){
			$box.find('input').prop('disabled',false);
			$box.removeClass('display');
		}else{
			$box.find('input').prop('disabled',true);
			$box.addClass('display');
		}
	});
}

var DEFAULTROW_NOSELECTUPDATE = '<tr>'
    +'<td class="col-sm-1">'
    +'<i class="fa fa-close fs-16"></i>'
    +'</td>'
    +'<td class="col-sm-2 color-red">'
    +'<input type="text"/>'
    +'</td>'
    +'<td class="col-sm-1">'
    +'<select>'
    +'<option value="true">true</option>'
    +'<option value="false">false</option>'
    +'</select>'
    +'</td>'
    +'<td class="col-sm-2">'
    +'<select>'
    +'<option value="string">string</option>'
    +'<option value="number">number</option>'
    +'<option value="boolean">boolean</option>'
    +'<option value="date">date</option>'
	+'<option value="textarea">textarea</option>'
    //+'<option value="array">array</option>'
    //+'<option value="object">object</option>'
    +'</select>'
    +'</td>'
    +'<td class="col-sm-4">'
    +'<input type="text"/>'
    +'</td>'
    +'<td class="col-sm-2">'
    +'<input type="text"/>'
    +'</td>'
    +'</tr>';

function paramTypeUpdate(target) {
    var paramType = $(target).val();
    var paramNameInput = $(target).parent().prev().prev().find(':input');
    var paramName = $(paramNameInput).val();
    var tableId = $(target).parents('table').attr('id');
    if(!Base.isExist(paramName)){
        Base.alert('请输入参数名称');
        $(paramNameInput).focus();
        return;
    }

	var defaultValueTd = $(target).parent().next().next();
	if(paramType == 'textarea'){
		$(defaultValueTd).html('<textarea class="m-t-xs"></textarea>');
	} else {
		$(defaultValueTd).html('<input type="text"/>');
	}

    var paramTableId = 'table_'+tableId+'_'+paramName;
    if($('#'+paramTableId).length){
        var paramTable = $('#'+paramTableId);
        // 参数表已经存在了
        if(paramType != 'array' && paramType != 'object'){
            paramTable.parent().remove();
        }
    }else{
        // 参数表不存在
        if(paramType != 'array' && paramType != 'object'){
            return;
        }
        var html = [];
        html.push('<div class="col-sm-12 form-group fs-12 m-b-xs m-t" _param_for="'+paramName+'" _table_for="'+tableId+'">');
        html.push('<table id="'+paramTableId+'" class="col-sm-12" _level="2">');
        html.push('<tr>');
        html.push('<td class="text-center fs-16 color-green" colspan="6">参数'+paramName+'属性列表</td>');
        html.push('</tr>');
        html.push(DEFAULTROW_NOSELECTUPDATE);
        html.push('</table>');
        html.push('<button id="addParam_'+paramTableId+'" _paramTableId="'+paramTableId+'" type="button" class="btn btn-default btn-xs tableParamButton"><i class="fa fa-plus"></i>&nbsp;添加参数</button>');
        html.push('</div>');
        $('#'+tableId).parent().next('.add_btns').after(html.join(''));
    }
}