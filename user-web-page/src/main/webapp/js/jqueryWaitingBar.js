(function() {
	var c_s_waitingbarMap={};
	function Ms_WaitingBar(content,path,hideOff){
		this.content = content;
		this.faqbg;
		this.faqdiv;
		this.closeDiv;
		this.closeBut;
		this.tableBar;
		this.wcspan;
		this.wcspan;
		
		var imgPath="";
		if(path){
			imgPath=path+"/";
		}
		if(typeof Ms_WaitingBar._initialized == 'undefined'){
			faqbg=jQuery("<div></div>");
			faqdiv=jQuery("<div style=\"display:none;\"></div>");
			
			if(!hideOff){
				closeBut=jQuery("<img style=\"position:absolute; top:5px;right:5px;\" src=\""+imgPath+"css/img/delete.gif\"/>");
				closeBut.bind("click",function(){
					faqbg.css("display","none");
					faqdiv.css("display","none");
				});
				faqdiv.append(closeBut);
			}
			
			tableBar=jQuery("<div></div>");
			wispan=jQuery("<img style=\"vertical-align:middle; margin-right:5px;height:35px;\" id=\"wispan\" alt=\"\" src=\""+imgPath+"css/img/loading.gif\"/>");
			wcspan=jQuery("<span id=\"wcspan\" class=\"waitingbar-content\">");
		
			faqdiv.append(tableBar);
			tableBar.append(wispan);
			tableBar.append(wcspan);
			
			jQuery("body").append(faqbg); 
			jQuery("body").append(faqdiv); 
			faqbg.css({backgroundColor:'#666666',position:'absolute',zIndex:'99',left:'0',top:'0',display:'none',width:'100%',height:'100%',opacity:'0.3'});
			faqdiv.css({position:'fixed','min-width':'200px',left:'50%',top:'50%',marginLeft:'-130px',marginTop:'-80px',height:'36px',zIndex:'100',backgroundColor:'#fff',padding:'1px'});	
			faqdiv.find("h2").css({height:'25px',fontSize:'14px',backgroundColor:'#8FA4F5',position:'relative',paddingleft:'10px',lineHeight:'25px'});		
			faqdiv.find("h2 a").css({position:'absolute',right:'5px',fontSize:'12px',color:'#FF0000'});		
	        Ms_WaitingBar._initialized = true;
	    }
		if(this.content!=null&&this.content.length>0){
			wcspan.html(this.content);
		}	
		this.show = function(){
			faqbg.css({display:"block",height:jQuery(document).height()});
			faqdiv.css("display","block");
			wispan.show();
			document.documentElement.scrollTop=0;		
		};
		this.hide = function(){
			faqbg.css("display","none");
			faqdiv.css("display","none");
		};
		this.exceptionTips = function(msg, flag) {
			if(flag != false) {
				wispan.hide();
				wcspan.html(msg);
			}else {
				wcspan.html(msg);
			}
		};
	};
	/**
	 * 获取进度条对象
	 * @param {Object} msg   进度条提示语句，默认为“请稍候...”
	 * @param {Object} flag  是否获取对象马上显示，默认为马上显示，当值为“false”时则不显示
	 */
	$.extend({
		getWaitingbar:function(msg,flag,path,hideOff){   
			var waitingbar;
			if(msg) {
				waitingbar = new Ms_WaitingBar(msg,path,hideOff);
			}else {
				waitingbar = new Ms_WaitingBar("请稍候...",path,hideOff);
			} 
			if(flag != false) {
				waitingbar.show();
			}else{
				waitingbar.hide();
			}
			return waitingbar;
		}
	});
	/**
	 * 获取进度条对象
	 * @param {Object} id   id”
	 * @param {Object} msg   进度条提示语句，默认为“请稍候...”
	 * @param {Object} flag  是否获取对象马上显示，默认为马上显示，当值为“false”时则不显示
	 * @param {Object} path  自己定义图片路径
	 * @param {Object} hideOff  是否显示右上角的关闭按钮
	 */
	window.WaitingBar = {
		getWaitingbar:function(id,msg,path,hideOff){   
			var waitingbar = c_s_waitingbarMap[id];
			if(waitingbar){
				return waitingbar;
			}
			if(msg) {
				waitingbar = new Ms_WaitingBar(msg,path,hideOff);
			}else {
				waitingbar = new Ms_WaitingBar("请稍候...",path,hideOff);
			}
			c_s_waitingbarMap[id] = waitingbar;
			return waitingbar;
		}
	};
	
})(window);