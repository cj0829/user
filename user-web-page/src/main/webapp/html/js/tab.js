//tab
jQuery.fn.tab = function(options) {
	var settings = {
		current: null, 
		select: 'personal_nav_hover',
		noSelect: 'personal_nav_on'
	};
	if(options) {
		jQuery.extend(settings, options);
	}
	var container = jQuery(this);

	//初始化
	function init() {
		settings.tabs = jQuery('a', container);  
		settings.tabs.each(function(i) { 
			jQuery(settings.tabs[i]).removeClass(settings.select); 
			jQuery(this).click(function() {
				if((i + 1) != settings.current) { 
					jQuery(settings.tabs[settings.current - 1]).removeClass(settings.select); 
					jQuery(settings.tabs[settings.current - 1]).addClass(settings.noSelect); 
					settings.current = i + 1;
					jQuery(settings.tabs[settings.current - 1]).removeClass(settings.noSelect); 
					jQuery(settings.tabs[settings.current - 1]).addClass(settings.select); 
				}
			}); 
		});
		if(settings.current>0){
		    jQuery(settings.tabs[settings.current - 1]).addClass(settings.select);  
		}
	}; 
	init();
}; 