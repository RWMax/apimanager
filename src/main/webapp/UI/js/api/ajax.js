/**
 * 数据请求 （ 自动带上本地存储的access_token（key为：access_token））
 * @method ajax
 * @param {object} options 请求的键值对集合
 *    @param {string} options.type (默认： “GET”)：请求方法 (“GET”, “POST”, or other)
 *    @param {string} options.url (默认： 当前地址)：发送请求的地址
 *    @param {object} options.data (默认：none)：发送到服务器的数据；如果是GET请求，它会自动被作为参数拼接到url上。非String对象将通过 $.param 得到序列化字符串。
 *    @param {string} options.dataType (默认： json)：预期服务器返回的数据类型(“json”, “jsonp”, “xml”, “html”, or “text”)
 *    @param {int} options.timeout (默认： 0): 以毫秒为单位的请求超时时间, 0 表示不超时。   
 *    @param {object} options.header Ajax请求中额外的HTTP信息头对象
 *    @param {boolean} options.async (默认：true): 默认设置下，所有请求均为异步。如果需发送同步请求，请将此设置为 false。
 *    @param {function} options.beforeSend (xhr,settings)：在请求之前调用。return false 会阻止该次请求。
 *    @param {function} options.success (data, status, xhr)：请求成功之后调用。传入返回后的数据，以及包含成功代码的字符串。    
 *    @param {function} options.error (xhr, errorType, error)：请求出错时调用。 (超时，解析错误，或者状态码不在HTTP 2xx)。
 *    @param {function} options.complete (xhr, status)：请求完成时调用，无论请求失败或成功。
 *    @param {boolean} options.mask 是否显示蒙层 true显示， false不显示 默认显示。
 *    
 */
Base.ajax = function(options) {
	var _options = options;
	var mask = _options["mask"]; 
	if (mask === undefined) {
		Base.showMask();
	}else if(mask){
		Base.showMask();
	}
	var data = _options["data"];
	if(!data){
		data = new Object();
	}
	_options["data"] = data;
	
	if(!_options.dataType){
		_options.dataType = 'json';
	}
	
	var beforeSend = _options["beforeSend"];
	_options["beforeSend"] = beforeSend ? function(xhr, settings) {
		beforeSend(xhr,settings);
	} : function(xhr, settings) {
		
	}
	
	var success = _options["success"];
	_options["success"] = success ? function(data, status, xhr) {
		Base._dealdata(data);
		success(data, status, xhr);
	} : function(data, status, xhr) {
		Base._dealdata(data);
	}
	
	var errorFun = _options["error"];
	_options["error"] = errorFun ? function(xhr, errorType, error) {
		errorFun(xhr, errorType, error);
	} : function(xhr, errorType, error) {
		if(xhr.status == 404){
			return "请求的地址未找到!";
		}
	}
	
	var completeFun = _options["complete"];
	_options["complete"] = completeFun ? function(xhr, status) {
		Base.removeMask();
		completeFun(xhr, status);
	} : function(xhr, status) {
		Base.removeMask();
	}
	Messenger().run({
		errorMessage: "异步请求出错..."
	},options);
	//$.ajax(_options);
}

Base._dealdata = function(data){
	//只有json格式的时候才处理
	if(typeof data != "object")return;
	if(data){
		if (data.error) {
			if(data.error_msg){
				Base.alert({message:data.error_msg,type:'error'});
			}
		}else{
			if(data.success_msg){
				Base.alert({message:data.success_msg,type:'success'});
			}
		}
		
		if(data.tipMsg){
			Base.alert({message:data.tipMsg,type:'info'});
		}
		
		if(data.focus){
			Base.focus(data.focus);
		}
		
		if(data.lists){
			var _lists = data.lists;
			for(var list in _lists){
				Base.setDatagridData(list,_lists[list]);
			}
		}
	}
}