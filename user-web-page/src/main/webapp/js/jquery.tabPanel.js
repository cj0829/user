/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
/**
 * tabPanel - jQuery EasyUI
 * 
 */
(function($) {

	function tabDelClick(target,bnt){
		var opts = $.data(target, "tabPanel").options;
		var parentTabl=$(bnt).parent();
		var ptab=$(parentTabl).data("tabbar");
		if(parentTabl.hasClass("curr")){
			var firstTabl=$(target).find(">.pmt-tabbar>a:first").addClass("curr");
			opts.current=firstTabl.data("tabbar");
			$(target).find(">.pmt-context>.pmt-tabcon-panel:first").addClass("curr");
		}
		$(target).find(">.pmt-context>.pmt-tabcon-panel:eq("+ptab.index+")").remove();
		//选择第一个
		parentTabl.remove();
	}
	//选择
	function tabChangeClick(target,tab){
		var opts = $.data(target, "tabPanel").options;
		var tabData=$(tab).data("tabbar");
		if(opts.current==tabData){
			return false;
		}
		opts.current.tg.removeClass("curr");
		$(target).find(">.pmt-context>.pmt-tabcon-panel").removeClass("curr");
		tabData.tg.addClass("curr");
		opts.current=tabData;
		$(target).find(">.pmt-context>.pmt-tabcon-panel:eq("+opts.current.index+")").addClass("curr");
	}
	
	function setTabPanel(target) {
		var opts = $.data(target, "tabPanel").options;
		var tabbars=$(target).find(">.pmt-tabbar>a");
		tabbars.each(function(i) {
			var type="";
			var bnt=$(this).children("em");
			var title=$(this).children("span");
			if(bnt.hasClass("del")){
				type="del";
				bnt.click(function(){
					tabDelClick(target,bnt);
					return false;
				});
			}else{
				type="lock";
			}
			var ltab={index:i,title:title,type:type,tg:$(this)};
			$(this).data("tabbar",ltab);
			if($(this).hasClass("curr")){
				opts.current=ltab;
			}
			$(this).click(function() {
				tabChangeClick(target,this);
			});
		});
		//重置选项
		var tabPanels=$(target).find(">.pmt-context>.pmt-tabcon-panel");
		if(tabPanels.length<tabbars.length){
			var pmt_context=$(target).find(">.pmt-context");
			for(var i=0;i<tabbars.length-tabPanels.length;i++){
				pmt_context.append("<div class=\"pmt-tabcon-panel\"></div>");
			}
		}
	};
	
	function createTab(target, params){
		var tabPanel = $.data(target, "tabPanel");
		var opts = tabPanel.options;
		
		if (params.queryParams) {
			opts.queryParams = queryParams;
		}
		if (!params.href) {
			return;
		}
		
		if (!tabPanel.isLoaded) {
			var optsQueryParams=opts.queryParams;
			if(typeof(opts.queryParams)=="function"){
				optsQueryParams=opts.queryParams();
			}
			var param={};
			if(optsQueryParams){
				if(typeof(optsQueryParams)=="object"){
					param=$.extend({},optsQueryParams);
					param.queryString=$.parser.objConvertStr(optsQueryParams);
				}else{
					param=optsQueryParams;
				}
			}
			if (opts.onBeforeLoad.call(target, param) == false) {
				return;
			}
			tabPanel.isLoaded = false;
			
			//创建<em class=\"del\"></em>
			var title="";
			if(params.title){
				title=params.title;
			}
			
			var tabObjectData=null;
			var tab=null;
			var tabbarAs=$(target).find(">.pmt-tabbar>a").each(function(){
				var tabData=$(this).data("tabbar");
				if(title==tabData.title){
					tabObjectData = tabData;
					tab=$(this);
					return false;
				}
			});
			
			
			var tabbar=$(target).find(">.pmt-tabbar");
			var pmt_context=$(target).find(">.pmt-context");
			
			var ptmTabPanel=null;
			if(!tabObjectData){
				tab=$("<a class=\"curr\" href=\"javascript:;\"><span>"+title+"</span></a>").click(function() {
					tabChangeClick(target,this);
				});
				tabObjectData=$.extend({},{index:tabbarAs.length,tg:tab}, params);
				var btn=$("<em></em>");
				tab.append(btn);
				if(params.type=="del"){
					btn.addClass("del").click(function(){
						tabDelClick(target,this);
						return false;
					});
				}else{
					btn.addClass("lock");
				}
				tab.data("tabbar",tabObjectData);
				tabbar.append(tab);
				ptmTabPanel=$("<div class=\"pmt-tabcon-panel\"></div>");
				pmt_context.append(ptmTabPanel);
			}else{
				tab.addClass("curr");
			}
		
			if (opts.loadingMessage) {
				pmt_context.html($("<div class=\"tabPanel-loading\"></div>").html(opts.loadingMessage));
			}
			
			if(opts.iframe){
				if(tabObjectData.iframeObject==null){
					tabObjectData.iframeObject=document.createElement("iframe");
					tabObjectData.iframeObject.setAttribute('name', "_internal_iframe_post_id_"+this.id);
					tabObjectData.iframeObject.setAttribute('frameborder', "no");
					tabObjectData.iframeObject.setAttribute('border', "0");
					tabObjectData.iframeObject.setAttribute('marginwidth', "0");
					tabObjectData.iframeObject.setAttribute('marginheight', "0");
					tabObjectData.iframeObject.setAttribute('scrolling', "no");
					tabObjectData.iframeObject.setAttribute('allowtransparency', "yes");
					tabObjectData.iframeObject.setAttribute('width', "100%");
					tabObjectData.iframeObject.setAttribute('height', params.height||"400");
					ptmTabPanel.append(tabObjectData.iframeObject);
				}
				$(tabObjectData.iframeObject).load(function(){
					tabObjectData.iframeObject.contentWindow.parentObject=tabObjectData;
					opts.onIfarmeLoad.apply(target);
	            });
				if(param){
					if(typeof(param)=="object"){
						if(params.href.lastIndexOf("?")>0){
							tabObjectData.iframeObject.src=params.href+"&"+$.param(param,true);
						}else{
							tabObjectData.iframeObject.src=params.href+"?"+$.param(param,true);
						}
						
					}else if(typeof(param)=="string"){
						if(params.href.lastIndexOf("?")>0){
							tabObjectData.iframeObject.src=params.href+"&"+param;
						}else{
							tabObjectData.iframeObject.src=params.href+"?"+param;
						}
					}else{
						tabObjectData.iframeObject.src=params.href;
					}
				}else{
					tabObjectData.iframeObject.src=params.href;
				}
				
			}else{
				$.ajax({
					type : opts.method,
					url : params.href,
					cache : false,
					data : param,
					dataType : "html",
					success : function(data) {
						ptmTabPanel.html(opts.extractor.call(target, data));
						$.parser.parse($(target));
						opts.onLoad.apply(target, arguments);
						tabPanel.isLoaded = true;
					},
					error : function() {
						opts.onLoadError.apply(target, arguments);
					}
				});
			}
			
			//设置选中
			if(opts.current){
				opts.current.tg.removeClass("curr");
				$(target).find(">.pmt-context>.pmt-tabcon-panel").removeClass("curr");
			}
			opts.current=tabObjectData;
			$(target).find(">.pmt-context>.pmt-tabcon-panel:last").addClass("curr");
			
		}
	}
	
	$.fn.tabPanel = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.tabPanel.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, 'tabPanel');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'tabPanel', {
					options : $.extend({}, $.fn.tabPanel.defaults, options)
				});
			}
			if (state.options.border == false) {
				$(this).addClass('tabPanel-noborder');
			}
			setTabPanel(this);
		});
	};
	
	$.fn.tabPanel.methods = {
		options: function(jq){
			var cc = jq[0];
			var opts = $.data(cc, 'tabPanel').options;
			return opts;
		},
		createTab: function(jq,param){
			return jq.each(function(){
				createTab(this, param);
			});
		}
	};
	$.fn.tabPanel.defaults = {
		current : null,
		//是否采用ifarem加载
		iframe : true,
		extractor : function(data) {
			var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
			var matches = pattern.exec(data);
			if (matches) {
				return matches[1];
			} else {
				return data;
			}
		},
		onLoad : function() {},
		onBeforeLoad : function(_74) {},
		onIfarmeLoad : function() {},
		onSelect : function(date) {
		},
		onChange : function(newDate, oldDate) {
		}
	};
})(jQuery);
