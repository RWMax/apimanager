<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.liuht.api.common.domain.InterfaceWithBLOBs"%>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加接口</title>
	<%@include file="../UI/head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/add_interface.css"/>
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
	<%@ include file="edit_navbar.jsp" %>
	<div class="right-content">
		<div class="content-box">
			<form id="form1">
				<input id="projectId" name="projectId" type="hidden" value="${projectId}">
				<input id="moduleId" name="moduleId" type="hidden" value="${moduleId}">
				<input id="id" name="id" type="hidden" value="${api.id}">
				<div>
					<label class="color-3 fs-12 m-b-sm">基本信息</label>
				</div>
				<div class="info1-box form-group brb">
					<span>
			            <span>分类</span>
			            <select id="folderId" name="folderId" class="input" required>
			            	<c:forEach items="${folders}" var="f">
			            		<c:choose>
			            			<c:when test="${folder.id eq f.id}">
			            				<option value="${f.id}" selected>${f.name}</option>
			            			</c:when>
			            			<c:otherwise>
			            				<option value="${f.id}">${f.name}</option>
			            			</c:otherwise>
			            		</c:choose>
			            	</c:forEach>
			            </select>
					</span>
					<span>
			            <span>协议</span>
			            <select id="protocol" name="protocol" class="input" required>
			            	<option value="HTTP" <c:if test="${api.protocol eq 'HTTP'}">selected</c:if> >HTTP</option>
			            	<option value="HTTPS" <c:if test="${api.protocol eq 'HTTPS'}">selected</c:if> >HTTPS</option>
			            	<option value="WEBSERVICE" <c:if test="${api.protocol eq 'WEBSERVICE'}">selected</c:if> >WEBSERVICE</option>
			            	<%-- <option value="SOCKET" <c:if test="${api.protocol eq 'socket'}">selected</c:if> >SCOKET</option>
			            	<option value="WS" <c:if test="${api.protocol eq 'ws'}">selected</c:if> >WEBSERVICE</option> --%>
			            </select>
					</span>
					<span class="<c:if test="${api.protocol eq 'WEBSERVICE'}">display</c:if>">
			            <span>请求方法</span>
			            <select id="requestMethod" name="requestMethod" class="input" required <c:if test="${api.protocol eq 'WEBSERVICE'}">disabled</c:if>>
				            <option value="GET" <c:if test="${api.requestmethod eq 'GET'}">selected</c:if> >GET</option>
				            <option value="POST" <c:if test="${api.requestmethod eq 'POST'}">selected</c:if> >POST</option>
				            <option value="PUT" <c:if test="${api.requestmethod eq 'PUT'}">selected</c:if> >PUT</option>
				            <option value="DELETE" <c:if test="${api.requestmethod eq 'DELETE'}">selected</c:if> >DELETE</option>
			            </select>
					</span>
					<span>
			            <span>请求数据类型</span>
			            <select id="contentType" name="contentType" class="input" required>
			            	<option value="application/x-www-form-urlencoded" <c:if test="${api.contenttype eq 'application/x-www-form-urlencoded'}">selected</c:if> >application/x-www-form-urlencoded</option>
			            	<option value="application/json" <c:if test="${api.contenttype eq 'application/json'}">selected</c:if> >application/json</option>
			            	<option value="application/xml" <c:if test="${api.contenttype eq 'application/xml'}">selected</c:if> >application/xml</option>
			            	<%-- <option value="application/octet-stream" <c:if test="${api.contenttype eq 'application/octet-stream'}">selected</c:if> >application/octet-stream</option> --%>
			            </select>
					</span>
					<span>
			            <span>响应类型</span>
			            <select id="dataType" name="dataType" class="input" required>
			            	<option value="JSON" <c:if test="${api.datatype eq 'JSON'}">selected</c:if> >JSON</option>
			            	<option value="XML" <c:if test="${api.datatype eq 'XML'}">selected</c:if> >XML</option>
			            	<%-- <option value="HTML" <c:if test="${api.datatype eq 'HTML'}">selected</c:if> >HTML</option>
			            	<option value="JSONP" <c:if test="${api.datatype eq 'JSONP'}">selected</c:if> >JSONP</option>
			            	<option value="TEXT" <c:if test="${api.datatype eq 'TEXT'}">selected</c:if> >TEXT</option> --%>
			            </select>
					</span>
					<span>
			            <span>状态</span>
			            <select id="status" name="status" class="input" required>
			            	<option value="ENABLE" <c:if test="${api.status eq 'ENABLE'}">selected</c:if> >启用</option>
			            	<option value="DISABLE" <c:if test="${api.status eq 'DISABLE'}">selected</c:if> >禁用</option>
			            </select>
					</span>
				</div>
				<div class="info2-box brb col-sm-12 form-group">
					<p class="color-3 fs-12 col-sm-1 text-left">接口名称</p>
					<div class="col-sm-11">
						<input id="name" name="name" type="text" class="form-control fs-12 color-3" placeholder="请输入接口名称" required value="${api.name}"/>
					</div>
				</div>
				<div id="targetNamespaceBox" class="info2-box brb col-sm-12 form-group <c:if test="${api.protocol ne 'WEBSERVICE'}">display</c:if>">
					<p class="color-3 fs-12 col-sm-1 text-left">命名空间</p>
					<div class="col-sm-11">
						<input id="targetNamespace" name="targetNamespace" type="url" class="form-control fs-12 color-3" placeholder="请输入webservice命名空间" required value="${apiws.targetnamespace}"
						<c:if test="${api.protocol ne 'WEBSERVICE'}">disabled</c:if>/>
					</div>
				</div>
				<div id="methodNameBox" class="info2-box brb col-sm-12 form-group <c:if test="${api.protocol ne 'WEBSERVICE'}">display</c:if>">
					<p class="color-3 fs-12 col-sm-1 text-left">方法名称</p>
					<div class="col-sm-11">
						<input id="methodName" name="methodName" type="text" class="form-control fs-12 color-3" placeholder="请输入webservice方法名称" required value="${apiws.methodname}"
						<c:if test="${api.protocol ne 'WEBSERVICE'}">disabled</c:if>/>
					</div>
				</div>
				<div class="info3-box brb col-sm-12 form-group">
					<p class="color-3 fs-12 col-sm-1 text-left">请求地址</p>
					<div class="col-sm-11">
						<input id="url" name="url" type="text" class="fs-12 color-3 form-control" placeholder="请输入接口URL,Webservice请输入WSDL地址" value="${api.url}" required/>
						<span class="help-block m-b-none url_real">实际请求地址:<em id="url_real"></em></span>
						<span class="help-block m-b-none variable environments_container">
							变量:
							<em id="environments_container">
						
							</em>
							
						</span>
					</div>
				</div>
				<div class="info4-box brb col-sm-12 form-group">
					<p class="color-3 fs-12 col-sm-1 text-left">接口描述</p>
					<div class="col-sm-11">
						<textarea id="description" name="description" class="fs-12 color-3 form-control" maxlength="1500">${api.description}</textarea>
					</div>
				</div>
				<div class="info5-box brb col-sm-12 form-group">
					<div>
						<label class="color-3 fs-12">发送数据</label>
					</div>
					<div class="btns-box">
						<span class="btn color-3 fs-12 active-btn">请求头(Header)</span>
					</div>
				</div>
				<div class="col-sm-12 form-group fs-12 m-b-xs">
					<table id="headerTable" class="col-sm-12" _level="1">
						<tr>
							<td class="col-sm-1">操作</td>
							<td class="col-sm-2">参数名称</td>
							<td class="col-sm-1">是否必须</td>
							<td class="col-sm-2">类型</td>
							<td class="col-sm-4">描述</td>
							<td class="col-sm-2">默认值</td>
						</tr>
						<c:forEach items="${requestheaders}" var="ra">
							<tr>
								<td class="col-sm-1">
									<i class="fa fa-close fs-16 delete_send"></i>
								</td>
								<td class="col-sm-2 color-red">
									<input type="text" value="${ra.paramName}">
								</td>
								<td class="col-sm-1">
									<select>
										<option value="true" <c:if test="${ra.isRequired eq 'true'}">selected</c:if> >true</option>
										<option value="false" <c:if test="${ra.isRequired eq 'false'}">selected</c:if> >false</option>
									</select>
								</td>
								<td class="col-sm-2">
									<select onchange="paramTypeUpdate(this)">
										<option value="string" <c:if test="${ra.paramType eq 'string'}">selected</c:if> >string</option>
										<option value="number" <c:if test="${ra.paramType eq 'number'}">selected</c:if> >number</option>
										<option value="boolean" <c:if test="${ra.paramType eq 'boolean'}">selected</c:if> >boolean</option>
										<option value="date" <c:if test="${ra.paramType eq 'date'}">selected</c:if> >date</option>
										<option value="array" <c:if test="${ra.paramType eq 'array'}">selected</c:if> >array</option>
										<option value="object" <c:if test="${ra.paramType eq 'object'}">selected</c:if> >object</option>
									</select>
								</td>
								<td class="col-sm-4">
									<input type="text" value="${ra.paramDesc}"/>
								</td>
								<td class="col-sm-2">
									<input type="text" value="${ra.defaultValue}"/>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="add_btns">
					<button type="button" class="btn btn-default btn-xs add-header"><i class="fa fa-plus"></i>&nbsp;添加参数</button>
					<button type="button" class="btn btn-default btn-xs add-json" _tableId="headerTable"><i class="fa fa-upload"></i>&nbsp;导入json</button>
				</div>
				
				<div class="info5-box brb col-sm-12 form-group">
					<div class="btns-box">
						<span class="btn color-3 fs-12 active-btn">请求参数(Body)</span>
					</div>
				</div>
				<div id="contentTypeXMLROOT" class="info3-box col-sm-12 form-group <c:if test="${api.contenttype ne 'application/xml'}">display</c:if>">
					<p class="color-red fs-12 col-sm-2 text-left m-t-sm">请求根元素</p>
					<div class="col-sm-6 m-l-n-xl">
						<input id="requestRootElement" name="requestRootElement" type="text" class="fs-12 color-3 form-control" placeholder="请输入请求XML Root Element" value="${api.requestrootelement}" required
							<c:if test="${api.contenttype ne 'application/xml'}">disabled</c:if>/>
					</div>
				</div>
				<div class="col-sm-12 form-group fs-12 m-b-xs">
					<table id="requestTable" class="col-sm-12" _level="1">
						<tr>
							<td class="col-sm-1">操作</td>
							<td class="col-sm-2">参数名称</td>
							<td class="col-sm-1">是否必须</td>
							<td class="col-sm-2">类型</td>
							<td class="col-sm-4">描述</td>
							<td class="col-sm-2">默认值</td>
						</tr>
						<c:forEach items="${requestargs}" var="ra">
							<tr>
								<td class="col-sm-1">
									<i class="fa fa-close fs-16 delete_send"></i>
								</td>
								<td class="col-sm-2 color-red">
									<input type="text" value="${ra.paramName}">
								</td>
								<td class="col-sm-1">
									<select>
										<option value="true" <c:if test="${ra.isRequired eq 'true'}">selected</c:if> >true</option>
										<option value="false" <c:if test="${ra.isRequired eq 'false'}">selected</c:if> >false</option>
									</select>
								</td>
								<td class="col-sm-2">
									<select onchange="paramTypeUpdate(this)">
										<option value="string" <c:if test="${ra.paramType eq 'string'}">selected</c:if> >string</option>
										<option value="number" <c:if test="${ra.paramType eq 'number'}">selected</c:if> >number</option>
										<option value="boolean" <c:if test="${ra.paramType eq 'boolean'}">selected</c:if> >boolean</option>
										<option value="date" <c:if test="${ra.paramType eq 'date'}">selected</c:if> >date</option>
										<option value="textarea" <c:if test="${ra.paramType eq 'textarea'}">selected</c:if> >textarea</option>
										<option value="array" <c:if test="${ra.paramType eq 'array'}">selected</c:if> >array</option>
										<option value="object" <c:if test="${ra.paramType eq 'object'}">selected</c:if> >object</option>
									</select>
								</td>
								<td class="col-sm-4">
									<input type="text" value="${ra.paramDesc}"/>
								</td>
								<td class="col-sm-2">
									<c:choose>
										<c:when test="${ra.paramType eq 'textarea'}">
											<textarea class="m-t-xs">${ra.defaultValue}</textarea>
										</c:when>
										<c:otherwise>
											<input type="text" value="${ra.defaultValue}"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="add_btns">
					<button type="button" class="btn btn-default btn-xs add-body"><i class="fa fa-plus"></i>&nbsp;添加参数</button>
					<button type="button" class="btn btn-default btn-xs add-json" _tableId="requestTable"><i class="fa fa-upload"></i>&nbsp;导入json</button>
				</div>

				<c:forEach items="${requestParamTables}" var="rpm">
					<div class="col-sm-12 form-group fs-12 m-b-xs m-t" _param_for="${rpm.key}" _table_for="requestTable">
						<table id="table_requestTable_${rpm.key}" class="col-sm-12" _level="2">
							<tr>
								<td class="text-center fs-16 color-green" colspan="6">参数${rpm.key}属性列表</td>
							</tr>
							<c:forEach items="${rpm.value}" var="ra">
								<tr>
									<td class="col-sm-1">
										<i class="fa fa-close fs-16 delete_send"></i>
									</td>
									<td class="col-sm-2 color-red">
										<input type="text" value="${ra.paramName}">
									</td>
									<td class="col-sm-1">
										<select>
											<option value="true" <c:if test="${ra.isRequired eq 'true'}">selected</c:if> >true</option>
											<option value="false" <c:if test="${ra.isRequired eq 'false'}">selected</c:if> >false</option>
										</select>
									</td>
									<td class="col-sm-2">
										<select>
											<option value="string" <c:if test="${ra.paramType eq 'string'}">selected</c:if> >string</option>
											<option value="number" <c:if test="${ra.paramType eq 'number'}">selected</c:if> >number</option>
											<option value="boolean" <c:if test="${ra.paramType eq 'boolean'}">selected</c:if> >boolean</option>
											<option value="date" <c:if test="${ra.paramType eq 'date'}">selected</c:if> >date</option>
											<option value="textarea" <c:if test="${ra.paramType eq 'textarea'}">selected</c:if> >textarea</option>
										</select>
									</td>
									<td class="col-sm-4">
										<input type="text" value="${ra.paramDesc}"/>
									</td>
									<td class="col-sm-2">
										<c:choose>
											<c:when test="${ra.paramType eq 'textarea'}">
												<textarea class="m-t-xs">${ra.defaultValue}</textarea>
											</c:when>
											<c:otherwise>
												<input type="text" value="${ra.defaultValue}"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</table>
						<button id="addParam_table_requestTable_${rpm.key}" _paramTableId="table_requestTable_${rpm.key}" type="button" class="btn btn-default btn-xs tableParamButton"><i class="fa fa-plus"></i>&nbsp;添加参数</button>
					</div>
				</c:forEach>

				<div class="info6-box col-sm-12 m-b-xs">
					<label class="color-3 fs-12">响应数据</label>
				</div>
				<div id="dateTypeXMLROOT" class="info3-box col-sm-12 form-group <c:if test="${api.datatype ne 'XML'}">display</c:if>">
					<p class="color-red fs-12 col-sm-2 text-left m-t-sm">响应根元素</p>
					<div class="col-sm-6 m-l-n-xl">
						<input id="responseRootElement" name="responseRootElement" type="text" class="fs-12 color-3 form-control" placeholder="请输入响应XML Root Element" value="${api.responserootelement}" required
							<c:if test="${api.datatype ne 'XML'}">disabled</c:if>/>
					</div>
				</div>
				<div class="col-sm-12 form-group fs-12 m-b-xs">
					<table id="responseTable" class="col-sm-12 response_data" _level="1">
						<tr>
							<td class="col-sm-1">操作</td>
							<td class="col-sm-2">参数名称</td>
							<td class="col-sm-1">是否必须</td>
							<td class="col-sm-2">类型</td>
							<td class="col-sm-4">描述</td>
							<td class="col-sm-2">默认值</td>
						</tr>
						<c:forEach items="${responseargs}" var="ra">
							<tr>
								<td class="col-sm-1">
									<i class="fa fa-close fs-16 delete_send"></i>
								</td>
								<td class="col-sm-2 color-red">
									<input type="text" value="${ra.paramName}">
								</td>
								<td class="col-sm-1">
									<select>
										<option value="true" <c:if test="${ra.isRequired eq 'true'}">selected</c:if> >true</option>
										<option value="false" <c:if test="${ra.isRequired eq 'false'}">selected</c:if> >false</option>
									</select>
								</td>
								<td class="col-sm-2">
									<select onchange="paramTypeUpdate(this)">
										<option value="string" <c:if test="${ra.paramType eq 'string'}">selected</c:if> >string</option>
										<option value="number" <c:if test="${ra.paramType eq 'number'}">selected</c:if> >number</option>
										<option value="boolean" <c:if test="${ra.paramType eq 'boolean'}">selected</c:if> >boolean</option>
										<option value="date" <c:if test="${ra.paramType eq 'date'}">selected</c:if> >date</option>
										<option value="textarea" <c:if test="${ra.paramType eq 'textarea'}">selected</c:if> >textarea</option>
										<option value="array" <c:if test="${ra.paramType eq 'array'}">selected</c:if> >array</option>
										<option value="object" <c:if test="${ra.paramType eq 'object'}">selected</c:if> >object</option>
									</select>
								</td>
								<td class="col-sm-4">
									<input type="text" value="${ra.paramDesc}"/>
								</td>
								<td class="col-sm-2">
									<c:choose>
										<c:when test="${ra.paramType eq 'textarea'}">
											<textarea class="m-t-xs">${ra.defaultValue}</textarea>
										</c:when>
										<c:otherwise>
											<input type="text" value="${ra.defaultValue}"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="add_btns">
					<button type="button" class="btn btn-default btn-xs add_response"><i class="fa fa-plus"></i>&nbsp;添加参数</button>
					<button type="button" class="btn btn-default btn-xs add-json" _tableId="responseTable"><i class="fa fa-upload"></i>&nbsp;导入json</button>
				</div>

				<c:forEach items="${responseParamTables}" var="rpm">
					<div class="col-sm-12 form-group fs-12 m-b-xs m-t" _param_for="${rpm.key}" _table_for="responseTable">
						<table id="table_responseTable_${rpm.key}" class="col-sm-12" _level="2">
							<tr>
								<td class="text-center fs-16 color-green" colspan="6">参数${rpm.key}属性列表</td>
							</tr>
							<c:forEach items="${rpm.value}" var="ra">
								<tr>
									<td class="col-sm-1">
										<i class="fa fa-close fs-16 delete_send"></i>
									</td>
									<td class="col-sm-2 color-red">
										<input type="text" value="${ra.paramName}">
									</td>
									<td class="col-sm-1">
										<select>
											<option value="true" <c:if test="${ra.isRequired eq 'true'}">selected</c:if> >true</option>
											<option value="false" <c:if test="${ra.isRequired eq 'false'}">selected</c:if> >false</option>
										</select>
									</td>
									<td class="col-sm-2">
										<select>
											<option value="string" <c:if test="${ra.paramType eq 'string'}">selected</c:if> >string</option>
											<option value="number" <c:if test="${ra.paramType eq 'number'}">selected</c:if> >number</option>
											<option value="boolean" <c:if test="${ra.paramType eq 'boolean'}">selected</c:if> >boolean</option>
											<option value="date" <c:if test="${ra.paramType eq 'date'}">selected</c:if> >date</option>
											<option value="textarea" <c:if test="${ra.paramType eq 'textarea'}">selected</c:if> >textarea</option>
										</select>
									</td>
									<td class="col-sm-4">
										<input type="text" value="${ra.paramDesc}"/>
									</td>
									<td class="col-sm-2">
										<c:choose>
											<c:when test="${ra.paramType eq 'textarea'}">
												<textarea class="m-t-xs">${ra.defaultValue}</textarea>
											</c:when>
											<c:otherwise>
												<input type="text" value="${ra.defaultValue}"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</table>
						<button id="addParam_table_responseTable_${rpm.key}" _paramTableId="table_responseTable_${rpm.key}" type="button" class="btn btn-default btn-xs tableParamButton"><i class="fa fa-plus"></i>&nbsp;添加参数</button>
					</div>
				</c:forEach>

				<div class="info7-box col-sm-12">
					<label class="color-3 fs-12">示列数据</label>
				</div>
				<div class="info8-box col-sm-12 form-group fs-12">
					<textarea id="example" name="example" class="form-control" placeholder="请添加一些示列数据">${api.example}</textarea>
				</div>
				
				 <div class="save">
		        	<button class="save-btn color-w text-center" type="submit">保存修改</button>
		        </div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>UI/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="<%=basePath%>UI/js/plugins/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/js/plugins/moment/moment.min.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/js/api.base.min.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/js/plugins/jquery-validate/local/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=staticsPath%>js/api/add_interface.js"></script>
<script type="text/javascript">
	var env_list0 = null; 
	$(document).ready(function(){
		initEnv();
		itemClick();
	});
	function initEnv() {
		var id = $('#dropenv',window.top.document).find('span').eq(0).attr('default_id');
		if(!id){
			$('.environments_container').hide();
			$('.url_real').hide();
			return;
		}
		parent.Base.ajax({
			'type':'POST',
			'url':parent.Base.globvar.basePath+'project/getEnvList',
			'data':{
				'envId': id
			},
			success:function(data){
				if(!data.error){
					env_list0 = data.result;
					$('.environments_container').show();
					$('.url_real').show();
					updateEnv(data.result);
				}else{
					parent.Base.alert(data.msg);
				}
			}
		});
	}
	function updateEnv(result){
		var container = document.getElementById('environments_container');
		var id = document.getElementById('id');
		var html = '';
		var url = document.getElementById('url').value || '';
		for(var i = 0, len = result.length; i < len ; i++){
			html +='<i>' + result[i].paramName + '</i>';
			var reg = '\\\$' + result[i].paramName + '\\\$';
			url = url.replace(new RegExp(reg,'gm'),result[i].paramValue);
		}
		document.getElementById('url_real').innerHTML = url;
		container.innerHTML = html;
	}
	function itemClick(){
		$(document).on('click','#environments_container i',function(){
			var list = null;
			if(!top.env_list){
				list = env_list0;
			}else{
				list = top.env_list;
			}
			var url = document.getElementById('url');
			url.value = url.value + '$'+$(this).text()+'$';
			updateEnv(list);
		});
	}
</script>
</html>