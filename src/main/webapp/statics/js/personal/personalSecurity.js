$(document).ready(function(){
	updateEmail();
});
function updateEmail(){
	$(document).on('click','#updateEmail',function(){
		var options = {};
		options.href = Base.globvar.basePath+"api/personalCenter/personal_confirm.jsp";
		options.title = '修改确认';
		options.width = 450+'px';
		options.height = 300+'px';
		parent.Base.openIframe(options);
	});
	$(document).on('click','#updatePwd',function(){
		var options = {}
		options.href = Base.globvar.basePath+"api/personalCenter/change_pwd.jsp";
		options.title = '修改密码';
		options.width = 450+'px';
		options.height = 300+'px';
		parent.Base.openIframe(options);
	});
}