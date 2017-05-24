if(typeof Messenger !== 'undefined'){
	Messenger.options = {
		parentLocations: ['body'],
		maxMessages: 9,
	    extraClasses: 'messenger-fixed messenger-on-top',
	    theme: 'flat',
	    instance: undefined,
	    messageDefaults: {
	    	hideAfter: 5,
	    	closeButtonText: "x",
	    	showCloseButton: true,
	    	type: "info",
	    	escapeText: false,
	    	scroll: true
	    }
	}
}

/**
 * @metod Base.alert()
 * 弹框提示,取代alert();
 * options 可以直接是string
 * 也可以是对象 对象具体操作,参照messenger官方API http://www.bootcss.com/p/messenger/
 * {
 * 	message:"消息",
 * 	type:"info/error/success",
 * 	showCloseButton: true/false,
 * }
 * */
Base.alert = function(options){
	if(options.type && options.type == "warning"){
		options.type = "info";
	}
	return Messenger().post(options);
}

Base.msgTopTip = function(message){
	Base.alert(message);
}

/**
 * @method Base.openIframe()
 * 	以iframe的形式打开一个完整的页面(该方法只能以iframe的形式打开),不支持autoSize和fitToView以提高性能
 * @param options.href 路劲,请使用全局路劲
 * @param options.title 标题
 * @param options.width 宽度 可使用百分比,百分比需要用引号包起来
 * @param options.height 高度 可使用百分比,百分比需要用引号包起来
 * @param options.maxWidth 最大宽度
 * @param options.maxHeight 最大高度
 * @param options.minWidth 最小宽度
 * @param options.minHeight 最小高度
 * @param options.padding padding属性
 * @param options.margin margin属性
 * @param options.scrolling 滚动条 Can be set to 'auto', 'yes', 'no' or 'visible'	 默认'auto'
 * @param options.closeBtn 关闭按钮,默认显示true
 * @param options.beforeLoad 开始加载content之前的回调函数 Note: If false is returned by the callback, the loading will be halted
 * @param options.afterLoad Called after content is loaded. Note: If false is returned by the callback, the content will not be shown.
 * @param options.beforeClose Called right after closing has been triggered but not yet started Note: If false is returned by the callback, the closing will be halted.
 * @param options.afterClose Called after closing animation has ended
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
	if(!opts.type || opts.type != 'iframe'){
		opts.type = 'iframe';
	}
	if(!opts.openEffect){
		opts.openEffect = 'elastic';
	}
	if(!opts.closeEffect ){
		opts.closeEffect = 'elastic';
	}
	if(!opts.padding){
		opts.padding = 0;
	}
	if(!opts.helpers){
		opts.helpers = {
			/*overlay : {
				closeClick : false
			},*/
			title:{
				position: 'top',
				type:'inside'
			}
		};
	}
	opts.fitToView = false;
	opts.autoSize = false;
	$.fancybox.open(opts);
}

/**
 * 关闭打开的Iframe窗口
 */
Base.closeIframe = function(){
	$.fancybox.close();
}

/**
 * 弹出bootstrap确认提示框
 * @param msg 需要提示的消息
 * @param callback 回调函数 比如:function(yes){if(yes){}}
 * @param type 提示信息的类型 可选 primary info success warning danger
 * */
Base.confirm = function(msg,callback,type) {
	var _opts = {};
	_opts.title = "确认提示";
	_opts.type = type?"type-"+type:BootstrapDialog.TYPE_PRIMARY;
	_opts.size = BootstrapDialog.SIZE_SMALL;
	_opts.message = msg ? msg : "";
	_opts.closable = true;
	_opts.buttons = [
		{
			label: '取消',
			action: function(dialogRef) {
				if(callback){
					Base.alert({message:"很遗憾,您还是没有下定决心...",type:'info',hideAfter:3,showCloseButton:false});
	    			callback(false);
	    		}
				dialogRef.close();
			}
		},
	    {
	    	label: '确认',
	    	cssClass:type?'btn-'+type:'btn-primary', 
	    	action: function(dialogRef) {
	    		if(callback){
	    			callback(true);
	    		}
	    		dialogRef.close();
	    	}
    	}
	];
	BootstrapDialog.show(_opts);
}

/**
 * 弹出bootstrap输入提示框
 * @param msg 需要提示的消息,放在input框上面
 * @param callback 回调函数,点击确认时 返回input的值 比如:function(value){alert(value);}
 * @param type 提示信息的类型 可选 primary info success warning danger
 * */
Base.prompt = function(msg,callback,type) {
	var _opts = {};
	_opts.title = "请输入";
	_opts.type = type?"type-"+type:BootstrapDialog.TYPE_PRIMARY;
	_opts.size = BootstrapDialog.SIZE_SMALL;
	var message = msg?msg:"";
	_opts.message = message+'<input type="text" class="form-control">';
	_opts.closable = true;
	_opts.buttons = [
		{
			label: '取消',
			action: function(dialogRef) {
				dialogRef.close();
			}
		},
	    {
	    	label: '确认',
	    	cssClass:type?'btn-'+type:'btn-primary', 
	    	action: function(dialogRef) {
	    		var value = dialogRef.getModalBody().find('input').val();
	    		if(callback){
	    			callback(value);
	    		}
	    		dialogRef.close();
	    	}
    	}
	];
	BootstrapDialog.show(_opts);
}