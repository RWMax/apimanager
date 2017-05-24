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