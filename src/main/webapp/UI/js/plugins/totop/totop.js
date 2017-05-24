/**
 * 
 * 
 * 
 */

var click_to_top = (function($, global) {
	function create_goup(params) {
		/*参数设置*/
		var params = $.extend({
			location: 'right', 					// 位置
			bottomOffset: 20, 					// 位置偏移量
			locationOffset: 20,
			containerClass: 'goup-container', 	// 主体的class
			containerSize : 35,					// 设置主体部分大小
			containerRadius : 10,				// 设置主题为圆角
			arrowClass: 'goup-arrow',			// 箭头的class
			alwaysVisible: false, 				// 持续可见,默认为否
			trigger: 100, 						// 可见触发距离
			entryAnimation: 'fade', 			// 动画
			goupSpeed: 'slow', 					// 移动速度
			hideUnderWidth : 500,
			containerColor : '#000',			// 背景色
			arrowColor : '#fff',				// 箭头颜色
            title : '回到顶部', 					// 标题
            titleAsText : false, 
            titleAsTextClass : 'goup-text' 		// title样式			
		}, params);
		
		$('body').append('<div style="display:none" class="'+params.containerClass+'"></div>');
		var container = $('.'+params.containerClass);	// 获取主体
		$(container).html('<div class="'+params.arrowClass+'"></div>');
		var arrow = $('.'+params.arrowClass);		// 获取内部箭头

		/* 对外部传入参数做一个检测,并校对   */
		if (params.location != 'right' && params.location != 'left') {
			params.location = 'right';
		}
		
		if (params.locationOffset < 0) {
			params.locationOffset = 0;
		}
		
		if (params.bottomOffset < 0) {
			params.bottomOffset = 0;
		}
		
		if (params.containerSize < 20) {
			params.containerSize = 20;
		}

		if (params.containerRadius < 0) {
			params.containerRadius = 0;
		}
		
		if (params.trigger < 0) {
			params.trigger = 0;
		}

		var checkColor = /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i;
		if (!checkColor.test(params.containerColor)) {
			params.containerColor = '#f1f1f1';
		}
		if (!checkColor.test(params.arrowColor)) {
			params.arrowColor = '#38f';
		}
        
        if (params.title === '') {
            params.titleAsText = false;
        }		

        /*设置主体部分*/

        /*对主体部分CSS设置*/
        var containerStyle = {};
		containerStyle = {
			position : 'fixed',
			width : params.containerSize,
			height : params.containerSize,
			background : params.containerColor,
			cursor : 'pointer',
			opacity: 0.7
		};
		containerStyle['bottom'] = params.bottomOffset;
		containerStyle[params.location] = params.locationOffset;
		containerStyle['border-radius'] = params.containerRadius;
		containerStyle['z-index'] = 100;
		$(container).css(containerStyle);

		/*对主体部分标题设置*/
		if (!params.titleAsText) {
            $(container).attr('title', params.title);
        } else {
            $('body').append('<div class="'+params.titleAsTextClass+'">'+params.title+'</div>');
            var textContainer = $('.'+params.titleAsTextClass);
            $(textContainer).attr('style', $(container).attr('style'));
            $(textContainer).css('background', 'transparent')
                .css('width', params.containerSize + 40)
                .css('height', 'auto')
                .css('text-align', 'center')
                .css(params.location, params.locationOffset - 20);
            var containerNewBottom = $(textContainer).height() + 10;
            $(container).css('bottom', '+='+containerNewBottom+'px');
        }

        /*主体部分箭头样式设置*/	
		var arrowStyle = {};
		var borderSize = 0.25 * params.containerSize;
		arrowStyle = {
			width : 0,
			height : 0,
			margin : '0 auto',
			'padding-top' : Math.ceil(0.325 * params.containerSize),
			'border-style' : 'solid',
			'border-width' : '0 '+borderSize+'px '+borderSize+'px '+borderSize+'px',
			'border-color' : 'transparent transparent '+params.arrowColor+' transparent' 
		};
		$(arrow).css(arrowStyle);

		/*设置回到顶部的隐藏距离*/
		var isHidden = false; // 设置隐藏标记，为true时隐藏
		global.resize(function() {
			if(global.outerWidth() <= params.hideUnderWidth) {
				isHidden = true;
				do_animation($(container), 'hide', params.entryAnimation);
	            if (textContainer)
	                do_animation($(textContainer), 'hide', params.entryAnimation);
			} else {
				isHidden = false;
				global.trigger('scroll');
			}

		});

		/* 如果页面最大长度不到隐藏距离，那么直接不生成 */
		if (global.outerWidth() <= params.hideUnderWidth) {
			isHidden = true;
			$(container).hide();
            if (textContainer)
                $(textContainer).hide();
		}

		/* 显示事件 */
		if (!params.alwaysVisible) {
			global.scroll(function(){
				if (global.scrollTop() >= params.trigger && !isHidden) {
					do_animation($(container), 'show', params.entryAnimation);
                    if (textContainer)
                        do_animation($(textContainer), 'show', params.entryAnimation);
				}
				
				if (global.scrollTop() < params.trigger && !isHidden) {
					do_animation($(container), 'hide', params.entryAnimation);
                    if (textContainer)
                        do_animation($(textContainer), 'hide', params.entryAnimation);
				}
			});
		} else {
			do_animation($(container), 'show', params.entryAnimation);
            if (textContainer)
                do_animation($(textContainer), 'show', params.entryAnimation);
		}

		/* 不出现scroll事件 */
		if (global.scrollTop() >= params.trigger && !isHidden) {
			do_animation($(container), 'show', params.entryAnimation);
            if (textContainer)
                do_animation($(textContainer), 'show', params.entryAnimation);
		}
		
		/* 点击事件 */
		var notClicked = true;
		$(container).add(textContainer).on('click', function(){
			if (notClicked) {
				notClicked = false;
				$('html,body').animate({ scrollTop: 0 }, params.goupSpeed, function(){ notClicked = true });
			}
			return false;

		});
		
		/* 鼠标移动事件    */
		$(container).hover( function() {
			$(this).css({opacity: 1});
		}, function() {
			$(this).css({opacity: 0.7});
        });
	}	

	/* 动画 */
	function do_animation(obj, type, animation) {
		if (type == 'show') {
			switch(animation) {
				case 'fade':
					obj.fadeIn();
				break;
				
				case 'slide':
					obj.slideDown();
				break;
				
				default:
					obj.fadeIn();
			}
		} else {
			switch(animation) {
				case 'fade':
					obj.fadeOut();
				break;
				
				case 'slide':
					obj.slideUp();
				break;
				
				default:
					obj.fadeOut();
			}
		}
	}
	// API
	return {
		goup: create_goup
	};

})(jQuery, jQuery(window));
$(document).ready(function(){
	click_to_top.goup();
});