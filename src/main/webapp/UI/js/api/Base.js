/**
 * 表单
 * @module Base
 */
if(typeof Base === "undefined") {
	var Base = {};
}
String.prototype.replaceAll = function(s1,s2){   
	return this.replace(new RegExp(s1,"gm"),s2);   
}
/**
 * @description 全局变量
 * @type 还有
 */
Base.globvar = {
	isSubmitNow:false, //当前是否出于提交状态，用于控制按钮的热键不能用，在jquery.hotkeys.js里面
	developMode:true,
	//columnsWidthsOverView:false,
	//commitNullField : false,
	indexStyle:"default",
	indexHeightOffset:5, // 首页高度偏移量
	userScreenLock:false, // 用户屏幕是否锁定标志
	indexMenuCollapse:false, // 首页左边菜单是否收起 false未收起 true收起
	indexAllMenus:null, // 首页菜单
};

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

/**
 * 显示蒙层
 */
Base.showMask = function() {
	var height = $(window).height();
  	var width = $(window).width();
  	var $mask = $("<div class=\"gm-mask\"></div>");
  	var $wrap = $("<div class='gm-wrap'></div>");
  	var $gif = $("<span class=\"gm-gif\"><i class=\"fa fa-spinner fa-spin\"></i> 请稍后...</span>");
  	$mask.width(width);
  	$mask.height(height);
  	$wrap.width(width);
  	$wrap.height(height);
  	$gif.css("margin-top",height/2+"px");
  	$wrap.append($gif);
  	$("body").append($mask).append($wrap);
}

/**
 * 移除蒙层
 */
Base.removeMask = function() {
	$(".gm-mask").remove();
  	$(".gm-wrap").remove();
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