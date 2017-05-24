<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.yinhai.api.common.domain.InterfaceWithBLOBs"%>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>API-浏览/测试/模拟</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/test_environment.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>UI/js/plugins/jsoneditor/jsoneditor.css">
	<script type="text/javascript">
		var __curr_projectId = ${projectId};
		var __curr_moduleId = ${moduleId};
		var __iframebox = parent.document.getElementById('myframe');
	</script>
	
	<%
		InterfaceWithBLOBs api = (InterfaceWithBLOBs)request.getAttribute("api");
		if(api != null){
			String hea = api.getRequestheaders();
			JSONArray requestheaders = JSONArray.parseArray(hea);
			request.setAttribute("requestheaders", requestheaders);

			String req = api.getRequestargs();
			JSONArray requestargs = JSONArray.parseArray(req);
			Map<String, Object> requestParamTables = new HashMap<String, Object>();
			for (int i = 0; i < requestargs.size(); i++) {
				JSONObject json = (JSONObject)requestargs.get(i);
				if ("array".equals(json.get("paramType")+"") || "array".equals(json.get("paramType")+"")) {
					requestParamTables.put(json.get("paramName")+"",json.get("defaultValue"));
					json.put("defaultValue","");
				}
			}
			if(!requestParamTables.isEmpty()){
				request.setAttribute("requestParamTables", requestParamTables);
			}
			request.setAttribute("requestargs", requestargs);

			String res = api.getResponseargs();
			JSONArray responseargs = JSONArray.parseArray(res);
			Map<String, Object> responseParamTables = new HashMap<String, Object>();
			for (int i = 0; i < responseargs.size(); i++) {
				JSONObject json = (JSONObject)responseargs.get(i);
				if ("array".equals(json.get("paramType")+"") || "object".equals(json.get("paramType")+"")) {
					responseParamTables.put(json.get("paramName")+"",json.get("defaultValue"));
                    json.put("objectJson",json.get("defaultValue"));
                    json.put("defaultValue","");
				}
			}
			if(!responseParamTables.isEmpty()){
				request.setAttribute("responseParamTables", responseParamTables);
			}
			request.setAttribute("responseargs", responseargs);
		}
	%>
</head>
<body>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/moment/moment.min.js"></script>
	<%@ include file="scan_navbar.jsp" %>
	<div class="right-content">
		<div class="content-box">
			<div class="header m-b">
				<h3 class="color-3 fs-12 text-left">基本信息
					<span class="float-r color-6">更新时间: <fmt:formatDate value="${api.lastupdatetime}" type="both"/></span>
				</h3>
			</div>
			<div>
				<p class="color-6 fs-12">
					请求类型: ${api.protocol}
				</p>
				<p class="color-6 fs-12" id="api_url">
					接口地址: ${api.url}
				</p>
				<c:if test="${api.protocol eq 'WEBSERVICE'}">
		        	<p class="color-6 fs-12">
						方法名称: ${apiws.methodname}
					</p>
					<p class="color-6 fs-12">
						命名空间: ${apiws.targetnamespace}
					</p>
		        </c:if>
				<p class="color-6 fs-12 <c:if test="${api.protocol eq 'WEBSERVICE'}">display</c:if>">
					请求方式: ${api.requestmethod}
				</p>
				<p class="color-6 fs-12">
					数据类型: ${api.contenttype}
				</p>
				<c:if test="${api.contenttype eq 'application/xml'}">
					<p class="color-6 fs-12">
						请求根节点: ${api.requestrootelement}
					</p>
				</c:if>
				<p class="color-6 fs-12">
					响应类型: ${api.datatype}
				</p>
				<c:if test="${api.datatype eq 'XML'}">
					<p class="color-6 fs-12">
						响应根节点: ${api.responserootelement}
					</p>
				</c:if>
				<p class="color-6 fs-12">
					接口状态: <c:choose>
						<c:when test="${api.status eq 'ENABLE'}">启用</c:when>
						<c:otherwise>禁用</c:otherwise>
					</c:choose>
				</p>
			</div>
			<c:if test="${not empty api.description}">
				<div>
					<h3 class="color-3 fs-12 m-b m-t">接口描述</h3>
					<p class="color-6 fs-12">${api.description}</p>
				</div>
			</c:if>
			<c:if test="${not empty api.example}">
				<div>
					<h3 class="color-3 fs-12 m-b m-t">示例数据</h3>
					<textarea id="example" name="example" style="height: 150px;" readonly="readonly" class="form-control" placeholder="请添加一些示列数据">${api.example}</textarea>
				</div>
			</c:if>
			<c:if test="${not empty requestheaders}">
				<div>
					<h3 class="color-3 fs-12 m-b m-t">请求头</h3>
				</div>
				<table class="col-sm-12">
					<tr>
						<td class="col-sm-2">参数名称</td>
						<td class="col-sm-1">是否必须</td>
						<td class="col-sm-1">类型</td>
						<td class="col-sm-5">描述</td>
						<td class="col-sm-2">默认值</td>
					</tr>
					<c:forEach items="${requestheaders}" var="ra">
						<tr>
							<td class="col-sm-2 color-red">${ra.paramName}</td>
							<td class="col-sm-1">${ra.isRequired}</td>
							<td class="col-sm-1">${ra.paramType}</td>
							<td class="col-sm-5">${ra.paramDesc}</td>
							<td class="col-sm-2">${ra.defaultValue}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			<c:if test="${not empty requestargs}">
				<div>
					<h3 class="color-3 fs-12 m-b m-t">请求参数</h3>
				</div>
				<table class="col-sm-12">
					<tr>
						<td class="col-sm-2">参数名称</td>
						<td class="col-sm-1">是否必须</td>
						<td class="col-sm-1">类型</td>
						<td class="col-sm-5">描述</td>
						<td class="col-sm-2">默认值</td>
					</tr>
					<c:forEach items="${requestargs}" var="ra">
						<tr>
							<td class="col-sm-2 color-red">${ra.paramName}</td>
							<td class="col-sm-1">${ra.isRequired}</td>
							<td class="col-sm-1">${ra.paramType}</td>
							<td class="col-sm-5">${ra.paramDesc}</td>
							<td class="col-sm-2">${ra.defaultValue}</td>
						</tr>
					</c:forEach>
				</table>
				<c:forEach items="${requestParamTables}" var="rpm">
					<table class="col-sm-12 m-t">
						<tr>
							<td class="text-center fs-16 color-green" colspan="5">参数${rpm.key}属性列表</td>
						</tr>
						<c:forEach items="${rpm.value}" var="ra">
							<tr>
								<td class="col-sm-2 color-red">${ra.paramName}</td>
								<td class="col-sm-1">${ra.isRequired}</td>
								<td class="col-sm-1">${ra.paramType}</td>
								<td class="col-sm-5">${ra.paramDesc}</td>
								<td class="col-sm-2">
									<c:choose>
										<c:when test="${ra.paramType eq 'textarea'}">
											<textarea class="m-t-xs" readonly>${ra.defaultValue}</textarea>
										</c:when>
										<c:otherwise>
											${ra.defaultValue}
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:forEach>
			</c:if>
			
			<c:if test="${not empty responseargs}">
				<div class="row">
					<h3 class="color-3 fs-12 col-sm-2 text-left m-b m-t">响应数据</h3>
				</div>
				<table class="col-sm-12">
					<tr>
						<td class="col-sm-2">参数名称</td>
						<td class="col-sm-1">是否必须</td>
						<td class="col-sm-1">数据类型</td>
						<td class="col-sm-5">描述</td>
						<td class="col-sm-2">默认值</td>
					</tr>
					<c:forEach items="${responseargs}" var="ra">
						<tr>
							<td class="col-sm-2 color-red">${ra.paramName}</td>
							<td class="col-sm-1">${ra.isRequired}</td>
							<td class="col-sm-1">${ra.paramType}</td>
							<td class="col-sm-5">${ra.paramDesc}</td>
							<td class="col-sm-2">
								<c:choose>
									<c:when test="${ra.paramType eq 'textarea'}">
										<textarea class="m-t-xs" readonly>${ra.defaultValue}</textarea>
									</c:when>
									<c:otherwise>
										${ra.defaultValue}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
				<c:forEach items="${responseParamTables}" var="rpm">
					<table class="col-sm-12 m-t">
						<tr>
							<td class="text-center fs-16 color-green" colspan="5">参数${rpm.key}属性列表</td>
						</tr>
						<c:forEach items="${rpm.value}" var="ra">
							<tr>
								<td class="col-sm-2 color-red">${ra.paramName}</td>
								<td class="col-sm-1">${ra.isRequired}</td>
								<td class="col-sm-1">${ra.paramType}</td>
								<td class="col-sm-5">${ra.paramDesc}</td>
								<td class="col-sm-2">${ra.defaultValue}</td>
							</tr>
						</c:forEach>
					</table>
				</c:forEach>
			</c:if>
			
			<form class="form-horizontal <c:if test="${api.protocol eq 'WEBSERVICE'}">display</c:if>">
				<div class="row">
					<h3 class="color-3 fs-12 col-sm-2 text-left m-b m-t">后台模拟地址</h3>
				</div>
				<div class="form-group">
		            <div class="col-sm-6 fs-16">
		            	<a href="${basePath}mock/api/${api.id}" target="_blank">${basePath}mock/api/${api.id}</a>
		                <%-- <input name="mockUrl" type="text" value="${basePath}mock/api/${api.id}" readonly="readonly" class="form-control fs-12 color-3"/> --%>
		            </div>
		        </div>
			</form>

			<c:if test="${api.protocol eq 'WEBSERVICE'}">
				<div class="header row">
					<h3 class="color-3 fs-12 col-sm-12 text-left m-b m-t">
						后台模拟参数&nbsp;&nbsp;<a id="ws-code" class="btn btn-xs btn-primary"><i class="fa fa-pencil"></i> 查看示例代码</a>
					</h3>
				</div>
				<div class="code-area display">
					<textarea id="code_editor" class="code-editor" spellcheck="false" placeholder="在此处输入代码" readonly="readonly">${ws_code}</textarea>
				</div>
				<div>
					<p class="color-6 fs-12">
						WSDL地址: <a target="_blank" href="${basePath}services/mockWS?wsdl">${basePath}services/mockWS?wsdl</a>
					</p>
					<p class="color-6 fs-12">
						命名空间: http://ws.api.yinhai.com/
					</p>
					<p class="color-6 fs-12">
						方法名称: <c:if test="${api.contenttype eq 'application/x-www-form-urlencoded'}">mockURLENCODED</c:if>
								<c:if test="${api.contenttype eq 'application/json'}">mockJSON</c:if>
								<c:if test="${api.contenttype eq 'application/xml'}">mockXML</c:if>
					</p>
					<p class="color-6 fs-12">
						apiId: ${api.id}
					</p>
				</div>
	        </c:if>
			
			<form id="form1" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${api.id}">
				<div class="row">
					<h3 class="color-3 fs-12 col-sm-2 text-left m-b m-t">前台模拟示例</h3>
				</div>
				<div class="form-group">
		            <p class="col-sm-2 control-label text-left">实际请求地址</p>
		            <div class="col-sm-6">
		                <input name="requestUrl" type="text" class="form-control fs-12 color-3" value="${api.url}" original_url = "${original_url}" id="req_url" required/>
		            </div>
		            <div class="col-sm-2">
			            <select class="form-control input" id="env_selectinput">
			            	<c:forEach var="p" items="${names}">
			            		<option value="${p.id}">${p.name}</option>
			            	</c:forEach>
			            </select>
		            </div>
		        </div>
		        <c:if test="${not empty requestargs}">
		        	<div class="row">
						<h3 class="color-3 fs-12 col-sm-1 text-left">请求参数
						</h3>
					</div>
					<c:forEach items="${requestargs}" var="ra">
						<div class="form-group col-sm-6">
				            <p class="col-sm-3 control-label color-3" style="text-align:left;">${ra.paramName}</p>
				            <div class="col-sm-9">
				                <input name="${ra.paramName}" value="${ra.defaultValue}" <c:if test="${ra.isRequired eq true}">required</c:if> type="text" class="form-control fs-12 color-3"/>
				            </div>
				        </div>
					</c:forEach>
		        </c:if>
		         <div class="form-group">
		             <div class="col-sm-6 col-sm-offset-2">
		                 <button class="btn btn-primary" type="submit">发送</button>
		                 <c:if test="${not empty responseargs}">
		                 	<!-- 有响应才会有mock -->
		                 	<button id="mock" class="btn btn-danger" type="button">mock</button>
		                 </c:if>
		             </div>
	              </div>
	         </form>
		</div>
		<div id="jsonBox" class="box json-box display m-b-xxl">
		</div>
	</div>
	<script type="text/javascript" src="<%=basePath%>UI/js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/api.base.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/local/messages_zh.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>mock/dist/mock-min.js"></script>
	<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jsoneditor/jsoneditor.js"></script>
	<script type="text/javascript">
	$(function(){
		initForm();
		bindClick();
		selectInput();
	})
	/**
	 * 初始化表单 改为异步提交 这里对接口发起ajax请求
	 */
	function initForm() {
		var validator = $("#form1").validate({
			onfocusout:false,//在得到焦点时是否验证
			onkeyup :false,//在键盘弹起时验证
			submitHandler:function(form) {
				var data = $(form).serialize();
				var url = $(':input[name="requestUrl"]').val();
				var index = url.indexOf('http');
				if(index != 0){
					showJSONResult({'success':false,result:'请求地址错误,服务器无响应或JavaScript跨域错误'});
					return;
				}
				Base.ajax({
					url:Base.globvar.basePath + 'req/send',
					type:'post',
					dataType:'json',
					data:data,
					success:function(data, status, xhr){
						showJSONResult(data);
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
	
	function showJSONResult(data) {
		var resultBox = document.getElementById('jsonBox');
		$(resultBox).empty().removeClass('display');
		var editor = new JSONEditor(resultBox, {
	    	mode: 'code'
	    });
	    editor.set(data);
	    
	    window.location.hash = '#jsonBox';
	}
	
	function bindClick() {
		// 点击进行模拟请求
		$('#mock').on('click',function(){
			// 相应数据
			var responseargs = eval('('+'${responseargs}'+')');
			// 准备模拟数据模板
			var template = initTemplate(responseargs);
			// 生成模板数据
			var data = Mock.mock(template);
			showJSONResult(data);
		})
		// 点击查看调用模拟地址示例代码
		$('#ws-code').on('click',function(){
			$('.code-area').removeClass('display');
			layer.open({
				type: 1,
				shade: false,
				title: false, //不显示标题
				area: ['800px', '95px'], //宽高
				content: $('.code-area'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
				cancel: function(){
					$('.code-area').addClass('display');
				}
			});
		});
	}
		
	function selectInput(){
		$(document).on('change','#env_selectinput',function(){
			var id = this.value;
			parent.Base.ajax({
				'type':'POST',
				'url':parent.Base.globvar.basePath+'api/getRealUrl',
				'data':{
					'envId': id
				},
				success:function(data){
					if(!data.error){
						var url = $('#req_url').attr('original_url');
						var result = data.result;
						for(var i = 0, len = result.length; i < len ; i++){
							var reg = '\\\$' + result[i].paramName + '\\\$';
							url = url.replace(new RegExp(reg,'gm'),result[i].paramValue);
						}
						$('#req_url').val(url);
					}
				}
			});
		})
	}
	function initTemplate(responseargs) {
		var template = {};
		for ( var i in responseargs) {
			var res = responseargs[i];
			switch (res.paramType) {
			case 'string':
				template[res.paramName] = '@string';
				break;
			case 'boolean':
				template[res.paramName] = '@boolean';
				break;
			case 'number':
				template[res.paramName] = '@integer(1, 9999)';
				break;
			case 'array':
				template[res.paramName+'|1-10'] = [initTemplate(res.objectJson)];
				break;
			case 'object':
				template[res.paramName] = initTemplate(res.objectJson);
				break;
			case 'date':
				template[res.paramName] = '@date("yyyy-MM-dd HH:mm:ss")';
				break;
			default:
				break;
			}
		}
		return template;
	}
	</script>
</body>
</html>