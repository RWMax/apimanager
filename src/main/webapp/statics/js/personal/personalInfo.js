var info = function(){
	this.url = parent.Base.globvar.basePath+'user/updateName';
	this.$el =  document.getElementById('nickname');
	this.setName =  function(){
		$(window).data('nickname',this._name());
	};
	this.getName = function(){
		return $(window).data('nickname');
	}
	this._name =function(){
		return this.$el.value;
	};
	this.update =  function(){
		var $this = this;
		var nickname = $this._name();
		if($this.getName() != nickname){
			parent.Base.ajax({
				'type':'POST',
				'url':$this.url,
				'data':{
					'nickname': nickname
				},
				success:function(data){
					
				}
			});
		}
	}
}
$(document).ready(function(){
	var _info = new  info();
	_info.setName();
	$(document).on('click','#confirm',function(){
		_info.update();
	});
});