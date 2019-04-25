//过滤html标签
function replaceHTML(str){
    if(str == undefined){
       return "";
    }
    var regu = "^([+-]?)\\d*\\.?\\d+$"; 
	var re = new RegExp(regu);  
	if(str != ""){
		 if(!re.test(str)){    
		     str = str.replace(/&/g, '&amp;');
		     str = str.replace(/</g, '&lt;');
		  str = str.replace(/>/g, '&gt;');
		 }
	}
   return str;
 }  
//判断页面元素是否为空
function isEmpty(str){
	if(str==null){
		return true;
	}else if(str.length==0){
		return true;
	}
	return false;
}

//判断权限,
function verifyPermissions(code){
	try{
		if(pmt_list_buttonCode){
			var index=pmt_list_buttonCode.indexOf(code);
			var allFunCode = pmt_list_buttonCode.indexOf("AllFunCode");
			if(index!=-1 || allFunCode != -1){
				return true;
			}
		}
	}catch (e) {}
	return false;
}
function isArray(obj) {  
  return Object.prototype.toString.call(obj) === '[object Array]';   
}
//组合对象，更改为数据
function arrayToNameIds(array,name,key){
	try{
		var ids=[];
		if(isArray(array)){
			for(var i=0;i<array.length;i++){
				if(key){
					ids.push("&"+name+"="+array[i][key]);
				}else{
					ids.push("&"+name+"="+array[i]);
				}
			}
		}
		return ids.join("");
	}catch (e) {}
	return false;
}
//填出窗口名称
//2015.12.30 top:document.body.scrollTop+document.documentElement.scrollTop 修改成20;
function showMsg(winType,msg,title){
	$.messager.show({
		title:title||"消息提示",
		msg:msg||"操作错误",
		showType:"slide",
		timeout:2000,
		style:{
			right:'',
			top:20,
			bottom:''
		}
	},winType);
}

//动态统计字数的文本域方法
//count : 限制的字数 ;textareaId : 文本域Id ; dynamicShowId :动态显示追加数据的id
function updateCounter(count,textareaId,dynamicShowId){
	//获取文本框输入的内容
	var content = jQuery("#"+textareaId).val();
	//获取输入的字符的长度
	var len = content.length;
	if(len>count){
		jQuery("#"+textareaId).val(content.substring(0,count));
	}
	//输入剩下的字符长度
	var sublen = count - len;
	if(sublen==0){
		jQuery("#"+dynamicShowId).html("已达到字符限制!");
	}else if(sublen<0){
		jQuery("#"+textareaId).html(content.substring(0,count));
	}else{
		jQuery("#"+dynamicShowId).html("剩下的字符数:"+sublen);
	}
} 
//创建富文本域
function kindEditorCreate(textareaId,minWidthpx,minHeightpx,choiceItem) {
	var editor = KindEditor.create('#'+textareaId,
			{resizeType : 0,
			minWidth: minWidthpx ||'460',
			minHeight: minHeightpx || '178',
			allowPreviewEmoticons : false,
			allowImageUpload : false,
			items : choiceItem || ['formatblock','fontname','fontsize','|','bold','italic','underline','|', 'justifyleft','justifycenter','justifyright','|','insertorderedlist','insertunorderedlist','indent','outdent','|','link','unlink','forecolor']
			});
	return editor;
}
//日期组件，屏蔽比大时间
function setMaxDate(date,min,max){
	if(max){
		try{var maxDate=jQuery("#"+max).datebox("getText");
		if(maxDate && date>$.parser.parseDate('yy-mm-dd',maxDate)){
			return false;
		}}catch(e){}
	}
	return true;
}
//日期组件，屏蔽比小时间
function setMinDate(date,min,max){
	if(min){
		try{var minDate=jQuery("#"+min).datebox("getText");
		if(minDate && date<$.parser.parseDate('yy-mm-dd',minDate)){
			return false;
		}}catch(e){}
	}
	return true;
}
//日期组件，屏蔽比小时间
function setCurrentDate(date,min,max){
	try{
	var minDate=jQuery("#"+min).datebox("getText");
	if(minDate){
		var miDate=new Date(minDate);
		if(miDate<=new Date()){
			miDate = new Date();
		}
		if( date<=miDate){
			return false;
		}
	}else{
		if(date<=new Date()){
			return false;
		}
	}}catch(e){}
	return true;
}

//日期组件，屏蔽比小时间
function setCurrentMinDate(date,min,max){
	try{
	var minDate=jQuery("#"+min).val();
	if(minDate){
		var miDate=new Date(minDate);
		if( date<=miDate){
			return false;
		}
	}else{
		if(date<=new Date()){
			return false;
		}
	}}catch(e){}
	return true;
}
//日期组件，屏蔽比大时间
function setCurrentMaxDate(date,min,max){
	try{
		var maxDate=jQuery("#"+max).val();
		var maxD=new Date(maxDate);
		if(date>=maxD){
		return false;}
	}catch(e){}
	return true;
}
//日期组件，屏蔽比小时间
function setCurrentSysDate(date,min,max){
	try{
			var miDate=new Date();
			if( date<=miDate){
				return false;
			}
		}catch(e){}
		return true;
}
	
	//
	
	
	// JavaScript Document

//tab切换
function tabs(clickbtn,control){
	$(clickbtn).click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(control).children().eq($(this).index()).addClass("curr").siblings().removeClass("curr")
	})
}

function Choose(tagChoose,tagcontrol){
	$(tagChoose).click(function(){
		$(tagcontrol).show();
	})
	$(tagChoose).hover(
		function(){},
		function(){$(tagcontrol).hide();
	})
}

//JavaScript Document

/*解决input中placeholder值在ie中无法支持的问题*/
var JPlaceHolder = {
	    //检测
	    _check : function(){
	        return 'placeholder' in document.createElement('input');
	    },
	    //初始化
	    init : function(){
	        if(!this._check()){
	            this.fix();
	        }
	    },
	    //修复
	    fix : function(){
	        jQuery(':input[placeholder]').each(function(index, element) {
	            var self = $(this), txt = self.attr('placeholder');
	            self.wrap($('<div></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
	            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
	            var holder = $('<span class="placeholder-span"></span>').text(txt).appendTo(self.parent());
	            self.focusin(function(e) {
	                holder.hide();
	            }).focusout(function(e) {
	                if(!self.val()){
	                    holder.show();
	                }
	            });
	            holder.click(function(e) {
	                holder.hide();
	                self.focus();
	            });
	        });
	    }
	};
	//执行
	jQuery(function(){
	    JPlaceHolder.init();    
	});

/*$(function(){
if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
    $('[placeholder]').focus(function() {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
            input.val('');
            input.removeClass('placeholder');
        }
    }).blur(function() {
        var input = $(this);
        if (input.val() == '' || input.val() == input.attr('placeholder')) {
            input.addClass('placeholder');
            input.val(input.attr('placeholder'));
        }
    }).blur();
};
});
function placeholderSupport() {
    return 'placeholder' in document.createElement('input');
}*/


//弹出窗口
function dialog(popupWin,clickBtn,closeBtn,cancel){
	$(clickBtn).click(function(){
		$(popupWin).show();
		$(closeBtn).click(function(){
		$(popupWin).hide();
		});
		$(cancel).click(function(){
		$(popupWin).hide();
		});
	});
}


//tab切换鼠标点击
function tabsclick(tabObj,contralBox){
	$(tabObj).click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(contralBox).eq($(this).index()).addClass("curr").siblings().removeClass("curr");
	});
}

// JavaScript Document
/*tab切换*/
function tabs(tabObj,conttalBox,hoverObj,showObj){
	$(tabObj).click(function(){
		$(this).addClass("current").siblings().removeClass("current");
		$(conttalBox).eq($(this).index()).addClass("current").siblings().removeClass("current");
	});
	$(hoverObj).mouseover(function(){
		$(this).addClass("current").siblings().removeClass("current");
	}),
	$(hoverObj).mouseout(function(){
		$(this).removeClass("current");
	});
}
/*2015-12-1   给定一个下载的url*/
function download_file(url)
{
    if (typeof (download_file.iframe) == "undefined")
    {
        var iframe = document.createElement("iframe");
        download_file.iframe = iframe;
        document.body.appendChild(download_file.iframe);
    }
    download_file.iframe.src = url;
    download_file.iframe.style.display = "none";
}
/*2016-1-13  显示确认框*/
function showConfirm(message,confirmFN,cancelFN,title){
	$.messager.confirm(title||"",message||"",function(r){    
	    if (r){
	    	confirmFN.apply(this);
	    }else{
	    	if(cancelFN){
		    	cancelFN.apply(this);
	    	}
	    }
	});
}
//下拉菜单
function dropdown(objhover,showcon){
	$(objhover).mouseover(function(){
		$(this).children("a").addClass("hover");
		$(showcon).show();
	}),
	$(objhover).mouseout(function(){
		$(this).children("a").removeClass("hover");
		$(showcon).hide();
	});
};
//tab切换
function tabs(clickbtn,control){
	$(clickbtn).click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(control).children().eq($(this).index()).addClass("curr").siblings().removeClass("curr")
	});
}

//文字计数
function countCharNum(str,source,sum){
	   jQuery("#"+source).html((sum-byteLength(document.getElementById(str).value))+"/"+sum);
}
 
function byteLength(aString) { 
	for (var i=0,iLen=0; aString.charAt(i); i++) {
		if (aString.charCodeAt(i)>256)
			iLen+=2;
		else
			iLen++;
	}
	return iLen; 
}

