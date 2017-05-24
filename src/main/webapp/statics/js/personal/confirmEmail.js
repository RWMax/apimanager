$(document).ready(function(){
	updateEmail();
});
function checkEmail(val){
	if(val){
		var check = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(val);	
		if(!check){
			Base.alert("邮箱格式不正确");
			return false;
		}
		return true;
	}
	return false;
}
function updateEmail(){
	$(document).on('click','#confirm_email',function(){
		var email = document.getElementById('email').value;
		if(email){
			if(!checkEmail(email)){
			 Base.alert("请检查邮箱格式是否正确");
				return;
			}
		}else{
			parent.Base.alert("请输入邮箱");
			return;
		}
		Base.ajax({
			'type':'POST',
			'url':Base.globvar.basePath+'u/findPwd',
			'data':{'email':email},
			success:function(data){
				if(!data.error){
					
				}
			}
		});
	});
	
}