 /**
 * 表单
 * @module Base
 */
if(typeof Base === "undefined") {
	var Base = {};
}

/**
 * 返回复制的对象
 */
Base.clone = function(obj) {
	if (typeof obj == 'object') {
		var o = new Object();
		for ( var i in obj) {
			o[i] = obj[i];
		}
		return o;
	} else {
		return obj;
	}
}
/**
 * 秒倒计时
 * @method countDownTime
 * @param {object} $wrap 计时器放入对象 必选
 * @param {number} seconds 计时时间 可选  默认60 
 * @param {function} func 回调方法 可选  
 */
Base.countDownTime = function($wrap, seconds, func) {
	var num = seconds || 60;
	var divTime = "";
	var arr = [ "00", "01", "02", "03", "04", "05", "06", "07", "08", "09" ];
	var $time = $("<span class='count-time'>" + num + "</span>");
	var timer;
	$wrap.empty().append($time);
	function countTime() {
		num = num - 1;
		var now = num;
		if (num < 10) {
			now = arr[now]
		}
		$time.html(now);
		if (now == "00" && func) {
			clearInterval(timer);
			func();
		}
	}
	timer = setInterval(countTime, 1000);
}

/**
 * 过滤字符串，去掉头尾空格
 * @method trim
 * @param  {String} str 必选 字符串
 * @returns {String}  去掉空格的字符串 
 */
Base.trim = function(str) {
	if (String.prototype.trim) {
		return str == null ? "" : String.prototype.trim.call(str);
	} else {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
};

/**
 * 过滤所有字符串，去掉所有的空格
 * @method trimAll
 * @param  {String} str 必选 字符串
 * @returns {String} 去掉空格的字符串 
 */
Base.trimAll = function(str) {
	return str.replace(/\s*/g, '');
};

/**
 * 是否为元素
 * @method isElement
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isElement = function(obj) {
	return !!(obj && obj.nodeType == 1);
};

/**
 * 是否为url
 * @method isUrl
 * @param  {String} url 
 * @returns {boolean} true/false
 */
Base.isUrl = function(url){
	if(!Base.isExist(url)){
		return false;
	}
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
		+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
		+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
		+ "|" // 允许IP和DOMAIN（域名）
		+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
		+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
		+ "[a-z]{2,6})" // first level domain- .com or .museum
		+ "(:[0-9]{1,4})?" // 端口- :80
		+ "((/?)|" // a slash isn't required if there is no file name
		+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var re = new RegExp(strRegex);
	return re.test(url);
}

/**
 * 是否为数组
 * @method isArray
 * @param  {object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isArray = function(obj) {
	if (Array.isArray) {
		return Array.isArray(obj);
	} else {
		return obj instanceof Array;
	}
};

/**
 * 是否为函数
 * @method isFunction
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isFunction = function(obj) {
	return Object.prototype.toString.call(obj) === '[object Function]';
};

/**
 * 是否为字符串
 * @method isString
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isString = function(obj) {
	return Object.prototype.toString.call(obj) === '[object String]';
};

/**
 * 是否是object对象
 * @method isObject
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isObject = function(obj) {
	return Object.prototype.toString.call(obj) === '[object Object]';
};

/**
 * 是否是空的object对象
 * @method isEmptyObject
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isEmptyObject = function(obj) {
	if (JSON.stringify(obj) === '{}') {
		return true;
	}
	return false;
};

/**
 * 是否是window对象
 * @method isWindow
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isWindow = function(obj) {
	return obj != null && obj == obj.window;
};

/**
 * 是否是纯对象
 * @method isPlainObject
 * @param  {Object} obj 必选 判断的对象
 * @returns {boolean} true/false 是返回true不是返回false 
 */
Base.isPlainObject = function(obj) {
	return m.isObject(obj) && !m.isWindow(obj)
			&& Object.getPrototypeOf(obj) == Object.prototype;
};


/**
 * json转字符串
 * @method jsonToStr 
 * @param  {object} o 必选 转换的对象
 * @returns {string} 字符串
 */
Base.objToJsonStr = Base.jsonToStr = function(o) {
	if(o==null || o=='undefined')return null;
    var r = [];  
    if (typeof o == "string") return o;  
    if (typeof o == "object") {  
    	if (!jQuery.isArray(o)) {  
            for (var i in o) {
            	if(typeof o[i]=='string' || typeof o[i] =='number'){
            	  if(o[i] != undefined){
            	  	r.push("\"" + i + "\":\"" +o[i].toString().replace(/\"/g,"\\\"")+"\""); 
            	  }else{
            	  	r.push("\"" + i + "\":null"); 
            	  }
                }else{
                  r.push("\"" + i + "\":" + Base.jsonToStr(o[i]));
                }
            }
            if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {  
                r.push("toString:" + o.toString.toString());  
            }  
            r = "{" + r.join() + "}"; 
        } else {  
            for (var i = 0; i < o.length; i++)
                r.push(Base.jsonToStr(o[i]));
            r = "[" + r.join() + "]"; 
        }  
        return r;  
    }  
    return o.toString();  
};

/**
 * 字符串转json
 * @method strToJson
 * @param  {string} str 必选 转换的字符串
 * @returns {object} json对象
 */
Base.strToJson = function(str) {
	if(str==null || str=='undefined')return null;
	if (Base.isString(str)) {
		//return JSON && JSON.parse(str);
		return eval('('+str+')');
	}else{
		return str;
	}
};

/**
 * 判断js对象是否存在
 * @method isExist
 * @param  obj 必选 验证的对象
 * @returns {boolean} true/fasle 
 */
Base.isExist = function(obj) {
	return (typeof obj != "undefined") && (obj != null) && (obj != "")
}
/**
 * 验证是否是手机号码
 * @method isMobilePhone
 * @param {string} mp 必选 手机号码
 * @returns {object} true/fasle  
 */
Base.isMobilePhone = function(mp) {
	return /^1[3|4|5|8][0-9]\d{8}$/.test(mp);
}
/**
 * 验证是否是正整数不包含0
 * @method isInteger
 * @param {String} str 必选 字符串
 * @returns {boolean} true/fasle  
 */
Base.isInteger = function(str) {
	return /^[1-9]\d*$/.test(str);
}
/**
 * 格式化金额
 * @param {number} m 必选 金额
 * @param {int} n 可选 保留几位小数默认2位
 * @returns {number} 返回格式化后的金额
 */
Base.formatMoney = function(m, n) {
	var n = n || 2;
	if (typeof m != 'number') {
		m = Number(m);
	}
	var _j = Math.pow(10, n);
	m = (Math.ceil(m * _j)) / _j;
	return m.toFixed(n);
}
/**
 * 验证是否是正整数包含0
 * @method isIntegerConZero
 * @param {string} str 字符串
 * @returns {boolean} true/fasle  
 */
Base.isIntegerConZero = function(str) {
	return /^[1-9]\d*|0$/.test(str);
}

/****cookie操作****/
/** 
	类 Cookie 
	将此类放入用到的js文件中即可使用 
	1.add(name,value,100); 添加一个cookie 
	2.get(name); 
	3.remove(name); 
	用例: 
	Base.cookie.add("sk","ss",3); 
	alert(Base.cookie.get("sk")); 
	Base.cookie.remove("sk"); 
*/ 
Base.cookie = new function(){ 
	//添加cookie 
	this.add=function(name,value,hours){
		var life=new Date().getTime(); 
		life = hours*1000*60*60; 
		var cookieStr=name+"="+escape(value)+";Path=/;expires="+new Date(new Date().getTime()+life).toGMTString(); 
		document.cookie=cookieStr; 
	}; 
	//获取cookie值 
	this.get=function(name){ 
		var cookies = document.cookie.split(";"); 
		if(cookies.length>0){ 
			for ( var i in cookies) {
				var coo = cookies[i];
				var cookie=coo.split("="); 
				if(Base.trim(cookie[0]) == name){
					return unescape(cookie[1]); 
				}
				if(i == cookies.length-1){
					return null;
				}
			}
		}
	}; 
	//删除cookie 
	this.remove=function(name){ 
		var cookieStr=name+"="+escape('null')+";Path=/;expires="+new Date().toGMTString(); 
		document.cookie=cookieStr; 
	};
}

if(typeof layer == 'undefined'){
	throw 'api.base.js 依赖layer弹框组建,请添加引用.';
}

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
	var lindex;
	if (mask === undefined) {
		lindex = layer.load();
	}else if(mask){
		lindex = layer.load();
	}
	var data = _options["data"];
	if(!data){
		data = new Object();
	}
	_options["data"] = data;
	
	if(!_options.dataType){
		_options.dataType = 'json';
	}
	
	if(!_options.type){
		_options.type = 'post';
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
			Base.alert('请求的地址没有找到');
		}
	}
	
	var completeFun = _options["complete"];
	_options["complete"] = completeFun ? function(xhr, status) {
		if(lindex){
			layer.close(lindex);
		}
		completeFun(xhr, status);
	} : function(xhr, status) {
		if(lindex){
			layer.close(lindex);
		}
	}
	
	$.ajax(_options);
}

Base._dealdata = function(data){
	//只有json格式的时候才处理
	if(typeof data != "object")return;
	if(data){
		if (data.error) {
			if(data.error_msg){
				layer.msg(data.error_msg,{
					icon:2
				});
			}
		}else{
			if(data.success_msg){
				layer.msg(data.success_msg,{
					icon:1
				});
			}
		}
		
		if(data.tipMsg){
			Base.alert(data.tipMsg);
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
/*if(typeof toastr !== 'undefined'){
	toastr.options = {
	  "closeButton": true,
	  "debug": false,
	  "progressBar": true,
	  "positionClass": "toast-top-center",
	  "onclick": null,
	  "showDuration": "400",
	  "hideDuration": "1000",
	  "timeOut": "7000",
	  "extendedTimeOut": "1000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}
}*/

/**
 * @metod Base.alert()
 * 弹框提示,取代alert();
 * */
Base.alert = function(options){
	var type = 'info';
	var msg = '';
	if(Base.isObject(options)){
		type = options.type ? options.type:'info';
		msg = options.message ? options.message:'info';
	}else if(Base.isString(options)){
		msg = options;
	}else{
		return false;
	}
	layer.msg(msg);
}

/**
 * msg 消息
 * selector 吸附元素选择器，如#id
 * time 持续时间 默认3000 3秒
 * color 背景颜色 可以不指定
 */
Base.tips = function(msg,selector,time,color){
	time = time || 3000;
	color = color || '#ed5565';
	layer.tips(msg, selector, {
	    tips: [1, color],
	    time: time
	});
}

/**
 * @method Base.openIframe()
 * 	以iframe的形式打开一个完整的页面(该方法只能以iframe的形式打开),不支持autoSize和fitToView以提高性能
 * @param options.href 路劲,请使用全局路劲
 * @param options.title 标题
 * @param options.width 宽度 可使用百分比,百分比需要用引号包起来
 * @param options.height 高度 可使用百分比,百分比需要用引号包起来
 * @param options.after
 * 
 * 返回 index Iframe层的index
 */
Base.openIframe = function(options){
	if(!options){
		return;
	}
	if($.type(options) != 'object' || !$.isPlainObject(options)){
		return;
	}
	var opts = options;
	delete options;
	
	return layer.open({
        type: 2,
        shift:0,
        title: opts.title,
        //shadeClose: false,
        //shade: 0.3,
        maxmin: true, //开启最大化最小化按钮
        area: [opts.width,opts.height],
        content: opts.href,
        end: opts.afterClose || null
    });
}

/**
 * 关闭打开的Iframe窗口
 */
Base.closeIframe = function(index){
	if(index){
		layer.close(index);
	}
}

/**
 * 弹出bootstrap确认提示框
 * @param msg 需要提示的消息
 * @param callback 回调函数 比如:function(yes){if(yes){}}
 * */
Base.confirm = function(msg,callback) {
	var _opts = {};
	_opts.title = "确认提示";
	_opts.message = msg ? msg : "";
		
	layer.confirm(_opts.message, {
	    btn: ['确认','取消'] //按钮
	}, function(index){
	    if(callback){
	    	callback(true);
	    }
	    layer.close(index);
	}, function(){
		if(callback){
	    	callback(false);
	    }
	});
}

/**
 * 将焦点至于某一输入框内
 */
Base.focus = function(id){
	var obj = document.getElementById(id);
	if (obj) {
		$(obj).focus();
	}else{
		obj = $(":input[name="+id+"]");
		if(obj){
			obj.focus();
		}
	}
}

var CommonFormatter = {
    format: function (value, rec, index) {
        return moment(value).format('YYYY-MM-DD HH:mm:ss');
    }
};

/********************表格操作****************************/
/**
 * 刷新表格数据,通过指定的地址从后台获取
 * @method loadGridData
 * @param gridId表格ID,必须以string形式传过来
 * @param submitIds 需要提交的组件id（非input 输入框）  只能是一个id ,需要提交多个找liuht
 * @param params 需要提交的数据 建议格式为:{"role_id":11} 
 */
Base.loadGridData = function(gridId,submitIds,params){
	if(typeof gridId != 'string'){return;}
	var param = "";
	var dto = {};
	if(submitIds){
		param = $("#"+submitIds).serializeArray();
		for(var i = 0; i< param.length ;i ++ ){
			dto[param[i].name] = param[i].value;
		}
	}
	if(params){
		dto = $.extend({},dto,params);
	}
	$("#"+gridId).datagrid("load",dto);
}

/**
 * fter changing data to server, refresh the front data.
 * 重载行. 后台修改数据后,刷新前台数据,但是它将保持在当前页
 * @method reloadGridData
 * @param gridId表格ID,必须以string形式传过来
 */
Base.reloadGridData = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("reload");
}

/**
 * 重载表格数据,调用此方法,datagrid不用预先指定URL
 * @method queryEcGrid
 * @param url查询地址,必须以string形式传过来
 * @param gridId表格ID,必须以string形式传过来
 * @param submitIds 需要提交的组件id（非input 输入框）  只能是一个id ,需要提交多个找liuht
 * @param params 需要提交的数据 建议格式为:{"role_id":1} 
 */
Base.queryEcGrid = function(url,gridId,submitIds,params){
	if(typeof url != 'string'){return;}
	if(typeof gridId != 'string'){return;}
	url = (url.indexOf('?')==-1?(url+"?_r="+Math.random()):(url+"&_r="+Math.random()));
	var param = "";
	var dto = {};
	if(submitIds){
		param = $("#"+submitIds).serializeArray();
		for(var i = 0; i< param.length ;i ++ ){
			dto[param[i].name] = param[i].value;
		}
	}
	if(params){
		dto = $.extend({},dto,params);
	}
	$("#"+gridId).datagrid({url:url,queryParams:dto});
	//var options = $("#"+gridId).datagrid("options");
	//if(options === undefined){
	//	throw "获取表格属性options失败";
	//}
	//options.queryParams = dto;
	//options.url = url;
	//$("#"+gridId).datagrid("load",dto);
}

/**
 * 更新指定行数据
 * @method updateDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 * @param row 更新行的新数据,{name: '新名称',note: '新消息'}
 */
Base.updateDatagridRow = function(gridId,index,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("updateRow",{
		index:index,
		row:row
	});
}

/**
 * 追加一个新行。新行将被添加到最后的位置。 
 * @method appendDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param row 新行的新数据,{name: '新名称',note: '新消息'}
 */
Base.appendDatagridRow = function(gridId,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'string'){return;}
	$("#"+gridId).datagrid("appendRow",row);
	// 获取表格列字段
	var fields = $('#'+gridId).datagrid('getColumnFields');
	for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = $('#'+gridId).datagrid('getColumnOption', field);
        if(col.editor && col.editor.type){
        	// 具有编辑属性 开启编辑模式
        	if(Base.endDatagridEditing(gridId)){
        		Base.acceptDatagridChanges(gridId);
        		___editIndex = $('#'+gridId).datagrid('getRows').length-1;
        		$("#"+gridId).datagrid('selectRow',___editIndex).datagrid('beginEdit', ___editIndex);
        	}
        	break;
        }
    }
}

/**
 * 插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
 * @method insertDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 * @param row 行的新数据,{name: '新名称',note: '新消息'}
 */
Base.insertDatagridRow = function(gridId,index,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("insertRow",{
		index:index,
		row:row
	});
	
	// 获取表格列字段
	var fields = $('#'+gridId).datagrid('getColumnFields');
	for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = $('#'+gridId).datagrid('getColumnOption', field);
        if(col.editor && col.editor.type){
        	// 具有编辑属性 开启编辑模式
        	if(Base.endDatagridEditing(gridId)){
        		Base.acceptDatagridChanges(gridId);
        		___editIndex = index;
        		$("#"+gridId).datagrid('selectRow',___editIndex).datagrid('beginEdit', ___editIndex);
        	}
        	break;
        }
    }
}

/**
 * 删除行
 * @method deleteDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.deleteDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	if(___editIndex != undefined && ___editIndex == index){
		$("#"+gridId).datagrid('cancelEdit', index);
		___editIndex = undefined;
	}
	$("#"+gridId).datagrid("deleteRow",index);
}

/**
 * 删除多行
 * @method deleteDatagridRows
 * @param gridId 表格ID,必须以string形式传过来
 * @param row 行数据
 */
Base.deleteDatagridRows = function(gridId,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row != 'object'){return;}
	for ( var i in row) {
		var index = Base.getDatagridRowIndex(gridId,row[i]);
		Base.deleteDatagridRow(gridId,index);
	}
}

/**
 * 返回指定行的索引号
 * @method getDatagridRowIndex
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.getDatagridRowIndex = function(gridId,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'undefined'){return;}
	return $("#"+gridId).datagrid("getRowIndex",row);
}

/**
 * 从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，可以使用的值有：inserted,deleted,updated等。当类型参数未配置的时候返回所有改变的行。
 * @method getDatagridChanges
 * @param gridId 表格ID,必须以string形式传过来
 * @param type 可以使用的值有：inserted,deleted,updated等
 */
Base.getDatagridChanges = function(gridId,type){
	if(typeof gridId != 'string'){return;}
	if(typeof type != 'string'){
		type = undefined;
	}
	if(Base.endDatagridEditing(gridId)){
		return $("#"+gridId).datagrid("getChanges",type);
	}
	return [];
}

/**
 * 提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
 * @method acceptDatagridChanges
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.acceptDatagridChanges = function(gridId){
	if(typeof gridId != 'string'){return;}
	if(Base.endDatagridEditing(gridId)){
		$("#"+gridId).datagrid("acceptChanges");
		Base.rejectDatagridChanges(gridId);
	}
}

/**
 * 回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
 * @method rejectDatagridChanges
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.rejectDatagridChanges = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("rejectChanges");
	___editIndex = undefined;
}

/**
 * 获取指定行数据,为分页时不建议使用该方法,前台较慢
 * @method getDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.getDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	var rows = $("#"+gridId).datagrid("getRows");
	return rows[index];
}

/**
 * 返回加载完毕后的数据,分页的话依然是当前页的数据
 * @method getDatagridAllRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridAllRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getData");
}

/**
 * 返回页脚行
 * @method getDatagridFooterRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridFooterRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getFooterRows");
}

/**
 * 做调整和布局
 * @method resizeDatagrid
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.resizeDatagrid = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("resize");
}

/**
 * 加载本地数据，旧的行将被移除。
 * @method setDatagridData
 * @param gridId 表格ID,必须以string形式传过来
 * @param rows 加载的数据 按照标准json格式{"total":10,"rows":[{"":"",...},{"":"",...}]}
 */
Base.setDatagridData = function(gridId,data){
	if(typeof gridId != 'string'){return;}
	if(data.list){
		data.rows = data.list;
	}
	$("#"+gridId).datagrid("loadData",data);
}

/**
 * 返回第一个被选中的行或如果没有选中的行则返回null。
 * @method getDatagridSelected
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridSelected = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getSelected");
}

/**
 * 返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
 * @method getDatagridSelections
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridSelections = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getSelections");
}

/**
 * 清除所有选择的行。
 * @method clearDatagridSelections
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.clearDatagridSelections = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("clearSelections");
}

/**
 * 滚动到指定的行
 * @method scrollToDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.scrollToDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("scrollTo",index);
}

/**
 * 高亮指定行
 * @method highlightDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.highlightDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("highlightRow",index);
}

/**
 * 勾选当前页中的所有行
 * @method checkAllDatagridRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.checkAllDatagridRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("checkAll");
}

/**
 * 取消勾选当前页中的所有行
 * @method uncheckAllDatagridRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.uncheckAllDatagridRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("uncheckAll");
}

/**
 * 勾选一行，行索引从0开始
 * @method checkDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.checkDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("checkRow",index);
}

/**
 * 取消勾选一行，行索引从0开始
 * @method uncheckDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.uncheckDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("uncheckRow",index);
}

/**
 * 合并单元格
 * @method mergeDatagridCells
 * @param gridId 表格ID,必须以string形式传过来
 * @param options包含以下属性：
 * index：行索引。
 * field：字段名称。
 * rowspan：合并的行数。
 * colspan：合并的列数。如:{index:2,rowspan:2,field:"user_id"}
 */
Base.mergeDatagridCells = function(gridId,options){
	if(typeof gridId != 'string'){return;}
	if(typeof options != 'object'){return;}
	$("#"+gridId).datagrid("mergeCells",{
		index:options.index,
		field:options.field,
		rowspan:options.rowspan,
		colspan:options.colspan
	});
}

/**
 * 显示指定的列
 * @method showDatagridColumn
 * @param gridId 表格ID,必须以string形式传过来
 * @param field 列名
 */
Base.showDatagridColumn = function(gridId,field){
	if(typeof gridId != 'string'){return;}
	if(typeof field != 'string'){return;}
	$("#"+gridId).datagrid("showColumn",field);
}

/**
 * 隐藏指定的列
 * @method hideDatagridColumn
 * @param gridId 表格ID,必须以string形式传过来
 * @param field 列名
 */
Base.hideDatagridColumn = function(gridId,field){
	if(typeof gridId != 'string'){return;}
	if(typeof field != 'string'){return;}
	$("#"+gridId).datagrid("hideColumn",field);
}
/**
 * 禁用指定的选项卡面板
 * @method disableEcTab
 * @param id 选项卡tabs的ID,参数String
 * @param title 选项卡tab的title,参数String
 */
Base.disableEcTab = function(id,title){
	if(typeof id != 'string'){return;}
	if(typeof title != 'string'){return;}
	$('#'+id).tabs('disableTab', title);
}
/**
 * 启用指定的选项卡面板
 * @method enableEcTab
 * @param id 选项卡tabs的ID,参数String
 * @param title 选项卡tab的title,参数String
 */
Base.enableEcTab = function(id,title){
	if(typeof id != 'string'){return;}
	if(typeof title != 'string'){return;}
	$('#'+id).tabs('enableTab', title);
}

/**
 * 页面跳到指定的选项卡面板
 * @method selectEcTab
 * @param id 选项卡tabs的ID,参数String
 * @param title 选项卡tab的title,参数String
 */
Base.selectEcTab = function(id,title){
	if(typeof id != 'string'){return;}
	if(typeof title != 'string'){return;}
	$('#'+id).tabs('select', title);
}
/********************tree操作****************************/
/**
 * 获取所有选中的节点
 * @method getEcTreeChecked
 * @param id treeID,参数String
 * @param state 可用值有：'checked','unchecked','indeterminate'。如果'state'未指定，将返回'checked'节点,参数String
 */
Base.getEcTreeChecked = function(id,state){
	if(typeof id != 'string'){return;}
	return $('#'+id).tree('getChecked',state);
}

/********************combotree操作****************************/

/**
 * 获取选择的节点,返回node
 * 注意:针对单选
 * @method getEcComboTreeSelected
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeSelected = function(id){
	if(typeof id != 'string'){return;}
	var t = $("#"+id).combotree("tree");
	return t.tree('getSelected');
}

/**
 * 获取所有选择的节点,返回node数组
 * 'state'可用值有：'checked 选择的节点(默认)','unchecked 未选择的节点','indeterminate 不确定的节点'
 * 注意:针对多选
 * @method getEcComboTreeSelected
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeChecked = function(id,state){
	if(typeof id != 'string'){return;}
	var t = $("#"+id).combotree("tree");
	if(state){
		return t.tree('getChecked',state);
	}else{
		return t.tree('getChecked');
	}
}

/**
 * 获取所有选中的节点,针对ec:combotree标签,获取到的ID值数组,["11", "111", "112", "113", "121"]
 * 注意:适用于多选
 * @method getEcComboTreeValues
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeValues = function(id){
	if(typeof id != 'string'){return;}
	return $("#"+id).combotree("getValues");
}

/**
 * 获取选中的节点,针对ec:combotree标签,获取到的ID值,"11"
 * 注意:适用于单选
 * @method getEcComboTreeValue
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeValue = function(id){
	if(typeof id != 'string'){return;}
	return $("#"+id).combotree("getValue");
}

/**
 * 获取返回文本框的值,针对ec:combotree标签,获取到值,"Program Files,Intel,Java,Microsoft Office,Games"
 * 注意:单选,多选都可以
 * @method getEcComboTreeText
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeText = function(id){
	if(typeof id != 'string'){return;}
	return $("#"+id).combotree("textbox").val();
}

/**
 * 设置combotree的值 单选 
 * value 如6
 */
Base.setEcComboTreeValue = function(id,value){
	if(typeof id != 'string'){return;}
	if(value){
		$("#"+id).combotree("setValue",value);
	}
}

/**
 * 设置combotree的值 多选 
 * value 如[5,6,1]
 */
Base.setEcComboTreeValues = function(id,value){
	if(typeof id != 'string'){return;}
	if(value){
		$("#"+id).combotree("setValues",value);
	}
}