/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 */

window.NE = {
    plugins:{},
    commands:{},
    I18N:{},
    instants:{},
    version:'1.5.0'
};
(function($) {
	var defaults_config={
			  /* 上传图片配置项 */
				"cxt":"/exam",
			    "imageActionName": "uploadimage", /* 执行上传图片的action名称 */
			    "imageFieldName": "upfile", /* 提交的图片表单名称 */
			    "imageMaxSize": 2048000, /* 上传大小限制，单位B */
			    "imageAllowFiles": [".png", ".jpg", ".jpeg", ".gif", ".bmp"], /* 上传图片格式显示 */
			    "imageCompressEnable": true, /* 是否压缩图片,默认是true */
			    "imageCompressBorder": 1600, /* 图片压缩最长边限制 */
			    "imageInsertAlign": "none", /* 插入的图片浮动方式 */
			    "imageUrlPrefix": "", /* 图片访问路径前缀 */
			    "imagePathFormat": "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
			                                /* {filename} 会替换成原文件名,配置这项需要注意中文乱码问题 */
			                                /* {rand:6} 会替换成随机数,后面的数字是随机数的位数 */
			                                /* {time} 会替换成时间戳 */
			                                /* {yyyy} 会替换成四位年份 */
			                                /* {yy} 会替换成两位年份 */
			                                /* {mm} 会替换成两位月份 */
			                                /* {dd} 会替换成两位日期 */
			                                /* {hh} 会替换成两位小时 */
			                                /* {ii} 会替换成两位分钟 */
			                                /* {ss} 会替换成两位秒 */
			                                /* 非法字符 \ : * ? " < > | */
			                                /* 具请体看线上文档: fex.baidu.com/ueditor/#use-format_upload_filename */

			    /* 涂鸦图片上传配置项 */
			    "scrawlActionName": "uploadscrawl", /* 执行上传涂鸦的action名称 */
			    "scrawlFieldName": "upfile", /* 提交的图片表单名称 */
			    "scrawlPathFormat": "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
			    "scrawlMaxSize": 2048000, /* 上传大小限制，单位B */
			    "scrawlUrlPrefix": "", /* 图片访问路径前缀 */
			    "scrawlInsertAlign": "none",

			    /* 截图工具上传 */
			    "snapscreenActionName": "uploadimage", /* 执行上传截图的action名称 */
			    "snapscreenPathFormat": "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
			    "snapscreenUrlPrefix": "", /* 图片访问路径前缀 */
			    "snapscreenInsertAlign": "none", /* 插入的图片浮动方式 */

			    /* 抓取远程图片配置 */
			    "catcherLocalDomain": ["127.0.0.1", "localhost", "img.baidu.com"],
			    "catcherActionName": "catchimage", /* 执行抓取远程图片的action名称 */
			    "catcherFieldName": "source", /* 提交的图片列表表单名称 */
			    "catcherPathFormat": "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
			    "catcherUrlPrefix": "", /* 图片访问路径前缀 */
			    "catcherMaxSize": 2048000, /* 上传大小限制，单位B */
			    "catcherAllowFiles": [".png", ".jpg", ".jpeg", ".gif", ".bmp"], /* 抓取图片格式显示 */

			    /* 上传视频配置 */
			    "videoActionName": "uploadvideo", /* 执行上传视频的action名称 */
			    "videoFieldName": "upfile", /* 提交的视频表单名称 */
			    "videoPathFormat": "/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
			    "videoUrlPrefix": "", /* 视频访问路径前缀 */
			    "videoMaxSize": 102400000, /* 上传大小限制，单位B，默认100MB */
			    "videoAllowFiles": [
			        ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
			        ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"], /* 上传视频格式显示 */

			    /* 上传文件配置 */
			    "fileActionName": "uploadfile", /* controller里,执行上传视频的action名称 */
			    "fileFieldName": "upfile", /* 提交的文件表单名称 */
			    "filePathFormat": "/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
			    "fileUrlPrefix": "", /* 文件访问路径前缀 */
			    "fileMaxSize": 51200000, /* 上传大小限制，单位B，默认50MB */
			    "fileAllowFiles": [
			        ".png", ".jpg", ".jpeg", ".gif", ".bmp",
			        ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
			        ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
			        ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
			        ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"
			    ], /* 上传文件格式显示 */

			    /* 列出指定目录下的图片 */
			    "imageManagerActionName": "listimage", /* 执行图片管理的action名称 */
			    "imageManagerListPath": "/ueditor/jsp/upload/image/", /* 指定要列出图片的目录 */
			    "imageManagerListSize": 20, /* 每次列出文件数量 */
			    "imageManagerUrlPrefix": "", /* 图片访问路径前缀 */
			    "imageManagerInsertAlign": "none", /* 插入的图片浮动方式 */
			    "imageManagerAllowFiles": [".png", ".jpg", ".jpeg", ".gif", ".bmp"], /* 列出的文件类型 */

			    /* 列出指定目录下的文件 */
			    "fileManagerActionName": "listfile", /* 执行文件管理的action名称 */
			    "fileManagerListPath": "/ueditor/jsp/upload/file/", /* 指定要列出文件的目录 */
			    "fileManagerUrlPrefix": "", /* 文件访问路径前缀 */
			    "fileManagerListSize": 20, /* 每次列出文件数量 */
			    "fileManagerAllowFiles": [
			        ".png", ".jpg", ".jpeg", ".gif", ".bmp",
			        ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
			        ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
			        ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
			        ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"
			    ] /* 列出的文件类型 */
	};
	var _selectionChangeTimer;
	var EventBase = {
			addEvent : function(evType, evFunc) {
				if(evFunc) {
					this.eventList = this.eventList || {};
					this.eventList[evType] = this.eventList[evType] || [];
					this.eventList[evType].push(evFunc);
				}
				return this;
			},
			fireEvent : function() {
				var args = bkLib.toArray(arguments), evType = args.shift();
				if(this.eventList && this.eventList[evType]) {
					for(var i=0;i<this.eventList[evType].length;i++) {
						this.eventList[evType][i].apply(this,args);
					}
				}
			}
	};

	var bkLib = {
			addEvent : function(obj, type, fn) {
				(obj.addEventListener) ? obj.addEventListener( type, fn, false ) : obj.attachEvent("on"+type, fn);
			},

			toArray : function(iterable) {
				var length = iterable.length, results = new Array(length);
		    	while (length--) { results[length] = iterable[length];};
		    	return results;
			},

			noSelect : function(element) {
				if(element.setAttribute && element.nodeName.toLowerCase() != 'input' && element.nodeName.toLowerCase() != 'textarea') {
					element.setAttribute('unselectable','on');
				}
				for(var i=0;i<element.childNodes.length;i++) {
					bkLib.noSelect(element.childNodes[i]);
				}
			},
			camelize : function(s) {
				return s.replace(/\-(.)/g, function(m, l){return l.toUpperCase();});
			},
			inArray : function(arr,item) {
			    return (bkLib.search(arr,item) != null);
			},
			search : function(arr,itm) {
				for(var i=0; i < arr.length; i++) {
					if(arr[i] == itm)
						return i;
				}
				return null;
			},
			cancelEvent : function(e) {
				e = e || window.event;
				if(e.preventDefault && e.stopPropagation) {
					e.preventDefault();
					e.stopPropagation();
				}
				return false;
			},
		};

	Function.prototype.closure = function() {
	  var __method = this, args = bkLib.toArray(arguments), obj = args.shift();
	  return function() { if(typeof(bkLib) != 'undefined') { return __method.apply(obj,args.concat(bkLib.toArray(arguments))); } };
	};

	Function.prototype.closureListener = function() {
	  	var __method = this, args = bkLib.toArray(arguments), object = args.shift();
	  	return function(e) {
	  	e = e || window.event;
	  	if(e.target) { var target = e.target; } else { var target =  e.srcElement;};
		  	return __method.apply(object, [e,target].concat(args) );
		};
	};

	
	var bkExtend = function(){
		var args = arguments;
		if (args.length == 1) args = [this, args[0]];
		for (var prop in args[1]) args[0][prop] = args[1][prop];
		return args[0];
	};
	
	
	function BaseClass() {}
	//
	BaseClass.prototype.construct = function() {};
	BaseClass.prototype.fireEvent = function(){
		EventBase.fireEvent();
	},
	BaseClass.extend = function(def) {
	  var classDef = function() {
	      if (arguments[0] !== BaseClass) { return this.construct.apply(this, arguments); }
	  };
	  var proto = new this(BaseClass);
	  bkExtend(proto,def);
	  classDef.prototype = proto;
	  classDef.extend = this.extend;
	  
	  return classDef;
	};

	var bkElement = BaseClass.extend({
		construct : function(elm,d) {
			if(typeof(elm) == "string") {
				elm = (d || document).createElement(elm);
			}
			elm = $BK(elm);
			return elm;
		},

		appendTo : function(elm) {
			jQuery(this).appendTo(elm);
			return this;
		},

		appendBefore : function(elm) {
			jQuery(elm).before(this);
			return this;
		},
		addEvent : function(type, fn) {
//			jQuery(this).bind(type,fn);
//			addEvent.
			bkLib.addEvent(this,type,fn);
			return this;
		},
		
		html : function(c) {
			jQuery(this).html(c);
			return this;
		},

		pos : function() {
			var curleft = curtop = 0;
			var obj = this;
			if (obj.offsetParent) {
				do {
					curleft += obj.offsetLeft;
					curtop += obj.offsetTop;
				} while (obj = obj.offsetParent);
			}
			var b = (!window.opera) ? parseInt(this.getStyle('border-width') || this.style.border) || 0 : 0;
			return [curleft+b,curtop+b+this.offsetHeight];
		},

		noSelect : function() {
			bkLib.noSelect(this);
			return this;
		},

		parentTag : function(t) {
			var elm = this;
			 do {
				if(elm && elm.nodeName && elm.nodeName.toUpperCase() == t) {
					return elm;
				}
				elm = elm.parentNode;
			} while(elm);
			return false;
		},

		hasClass : function(cls) {
			return $(this).hasClass(cls);
		},

		addClass : function(cls) {
			if (!this.hasClass(cls)) {$(this).addClass("nicEdit-"+cls);};
			return this;
		},

		removeClass : function(cls) {
			if (this.hasClass(cls)) {
				$(this).removeClass(cls);
			}
			return this;
		},

		setStyle : function(st) {
			$(this).css(st);
			return this;
		},

		getStyle : function( cssRule, d ) {
			var doc = (!d) ? document.defaultView : d;
			if(this.nodeType == 1){
				return (doc && doc.getComputedStyle) ? doc.getComputedStyle( this, null ).getPropertyValue(cssRule) : this.currentStyle[ bkLib.camelize(cssRule) ];
			}
		},

		remove : function() {
			this.parentNode.removeChild(this);
			return this;
		},

		attr : function(at) {
			jQuery(this).attr(at);
			return this;
		}
	});

	function $BK(elm) {
		if(typeof(elm) == "string") {
			elm = document.getElementById(elm);
		}
		return (elm && !elm.appendTo) ? bkExtend(elm,bkElement.prototype) : elm;
	}


	function __(s) {
		return s;
	}

	/* START CONFIG */
	var nicEditorConfig = BaseClass.extend({
		buttons : {
			'bold' : {name : __('粗体'), command : 'Bold', tags : ['B','STRONG'], css : {'font-weight' : 'bold'}, key : 'b'},
			'italic' : {name : __('斜体'), command : 'Italic', tags : ['EM','I'], css : {'font-style' : 'italic'}, key : 'i'},
			'underline' : {name : __('下划线'), command : 'Underline', tags : ['U'], css : {'text-decoration' : 'underline'}, key : 'u'},
			'left' : {name : __('左对齐'), command : 'justifyleft', noActive : true},
			'center' : {name : __('居中对齐'), command : 'justifycenter', noActive : true},
			'right' : {name : __('右对齐'), command : 'justifyright', noActive : true},
			'justify' : {name : __('两端对齐'), command : 'justifyfull', noActive : true},
			'ol' : {name : __('编号'), command : 'insertorderedlist', tags : ['OL']},
			'ul' : 	{name : __('项目符号'), command : 'insertunorderedlist', tags : ['UL']},
			'subscript' : {name : __('下角标'), command : 'subscript', tags : ['SUB']},
			'superscript' : {name : __('上角标'), command : 'superscript', tags : ['SUP']},
			'strikethrough' : {name : __('中划线'), command : 'strikeThrough', css : {'text-decoration' : 'line-through'}},
			'removeformat' : {name : __('清除格式'), command : 'removeformat', noActive : true},
			'indent' : {name : __('增加缩进'), command : 'indent', noActive : true},
			'outdent' : {name : __('减少缩进'), command : 'outdent', noActive : true},
			'hr' : {name : __('横线'), command : 'insertHorizontalRule', noActive : true}
		},
		iconsPath : '/exam/css/exam/dark-transparent/images/nicEditIcons.gif',
		//iconsPath : 'http://js.nicedit.com/nicEditIcons-latest.gif',
		buttonList : ['save','bold','italic','underline','left','center','right','justify','ol','ul','fontSize','fontFamily','fontFormat','indent','outdent','image','uploadvideo','upload','link','unlink','forecolor','bgcolor'],
		//
		iconList : {"fontFamily":29,"fontSize":28,"xhtml":1,"bgcolor":2,"forecolor":3,"bold":4,"center":5,"hr":6,"indent":7,"italic":8,"justify":9,"left":10,"ol":11,"outdent":12,"removeformat":13,"right":14,"save":25,"strikethrough":16,"subscript":17,"superscript":18,"ul":19,"underline":20,"image":21,"link":22,"unlink":23,"close":24,"arrow":26,"upload":27,"uploadvideo":30},
	});
	/* END CONFIG */
	var nicEditors = {
		nicPlugins : [],
		editors : [],
		registerPlugin : function(plugin,options) {
			this.nicPlugins.push({p : plugin, o : options});
		},
		findEditor : function(e) {
			var editors = nicEditors.editors;
			for(var i=0;i<editors.length;i++) {
				if(editors[i].instanceById(e)) {
					return editors[i].instanceById(e);
				}
			}
		}
	};

	var nicEditor = BaseClass.extend({
		construct : function(o) {
			this.options = new nicEditorConfig();
			bkExtend(this.options,o);
			this.nicInstances = new Array();
			this.loadedPlugins = new Array();

			var plugins = nicEditors.nicPlugins;
			for(var i=0;i<plugins.length;i++) {
				this.loadedPlugins.push(new plugins[i].p(this,plugins[i].o));
			}
			nicEditors.editors.push(this);
			bkLib.addEvent(document.body,'mousedown', this.selectCheck.closureListener(this));
//			jQuery(document.body).bind('mousedown', this.selectCheck.closureListener(this));
		},

		panelInstance : function(e,o) {
			e = this.checkReplace($BK(e));
			var panelElm = new bkElement('DIV').setStyle({width : (parseInt(e.getStyle('width')) || e.clientWidth)+'px'}).appendBefore(e);
			this.setPanel(panelElm);
			return this.addInstance(e,o);
		},

		checkReplace : function(e) {
			var r = nicEditors.findEditor(e);
			if(r) {
				r.removeInstance(e);
				r.removePanel();
			}
			return e;
		},
		createEle:function(eleName){
			return new bkElement(eleName);
		},
		addInstance : function(e,o) {
			e = this.checkReplace($BK(e));
			var newInstance=null;
			if( e.contentEditable || !!window.opera ) {
				newInstance = new nicEditorInstance(e,o,this);
			} else {
				newInstance = new nicEditorIFrameInstance(e,o,this);
			}
			this.nicInstances.push(newInstance);
			return this;
		},

		removeInstance : function(e) {
			e = $BK(e);
			var instances = this.nicInstances;
			for(var i=0;i<instances.length;i++) {
				if(instances[i].e == e) {
					instances[i].remove();
					this.nicInstances.splice(i,1);
				}
			}
		},

		removePanel : function(e) {
			if(this.nicPanel) {
				this.nicPanel.remove();
				this.nicPanel = null;
			}
		},

		instanceById : function(e) {
			e = $BK(e);
			var instances = this.nicInstances;
			for(var i=0;i<instances.length;i++) {
				if(instances[i].e == e) {
					return instances[i];
				}
			}
		},

		setPanel : function(e) {
			this.nicPanel = new nicEditorPanel($BK(e),this.options,this);
			this.fireEvent('panel',this.nicPanel);
			return this;
		},

		nicCommand : function(cmd,args) {
			if(this.selectedInstance) {
				this.selectedInstance.nicCommand(cmd,args);
			}
		},

		getIcon : function(iconName,options) {
			var icon = this.options.iconList[iconName];
			var file = (options.iconFiles) ? options.iconFiles[iconName] : '';
			return {backgroundImage : "url('"+((icon) ? this.options.iconsPath : file)+"')", backgroundPosition : ((icon) ? ((icon-1)*-18) : 0)+'px 0px'};
		},

		selectCheck : function(e,t) {
			do{
				if(t.className && t.className.indexOf('nicEdit') != -1) {
					return false;
				}
			} while(t = t.parentNode);
			this.fireEvent('blur',this.selectedInstance,t);
			this.lastSelectedInstance = this.selectedInstance;
			this.selectedInstance = null;
			return false;
		}

	});
	nicEditor = nicEditor.extend(EventBase);
	
	var nicEditorInstance = BaseClass.extend({
		isSelected : false,
		construct : function(e,option,nicEditor) {
			this.ne = nicEditor;
			//
			this.inputRules = [];
			this.outputRules = [];
			
			this.elm = this.e = e;
			this.options = $.extend({},defaults_config, option);
			
//			UE.plugins['fiximgclick'].apply(this);
			
			newX = parseInt(e.getStyle('width')) || e.clientWidth;
			newY = parseInt(e.getStyle('height')) || e.clientHeight;
			this.initialHeight = newY-8;

			var isTextarea = (e.nodeName.toLowerCase() == "textarea");
			if(isTextarea || this.options.hasPanel) {
				var ie7s = (browser.ie && !((typeof document.body.style.maxHeight != "undefined") && document.compatMode == "CSS1Compat"));
				var s = {width: newX+'px', border : '1px solid #ccc', borderTop : 0, overflowY : 'auto', overflowX: 'hidden' };
				s[(ie7s) ? 'height' : 'maxHeight'] = (this.ne.options.maxHeight) ? this.ne.options.maxHeight+'px' : null;
				this.editorContain = new bkElement('DIV').setStyle(s).appendBefore(e);
				var editorElm = new bkElement('DIV').setStyle({width : (newX-8)+'px', margin: '4px', minHeight : newY+'px'}).addClass('main').appendTo(this.editorContain);

				e.setStyle({display : 'none'});

				editorElm.innerHTML = e.innerHTML;
				if(isTextarea) {
					editorElm.html(e.value);
					this.copyElm = e;
					var f = e.parentTag('FORM');
					if(f) { 
//						bkLib.addEvent( f, 'submit', this.saveContent.closure(this));
						jQuery(f).bind('submit', this.saveContent.closure(this));
					}
				}
				editorElm.setStyle((ie7s) ? {height : newY+'px'} : {overflow: 'hidden'});
				this.elm = editorElm;
			}
			this.ne.addEvent('blur',this.blur.closure(this));

			this.init();
			this.blur();
		},

		init : function() {
			this.elm.setAttribute('contentEditable','true');
			if(this.getContent() == "") {
				this.html(' ');
			}
			this.instanceDoc = document.defaultView;
			this.selection = new dom.Selection(document);
//			this.elm.addEvent('mousedown',this.selected.closureListener(this)).addEvent('focus',this.selected.closure(this)).addEvent('blur',this.blur.closure(this)).addEvent('keyup',this.selected.closure(this));
//			this.elm.addEvent('mousedown',this.selected.closureListener(this));
			this.elm.addEvent('focus',this.focus.closureListener(this));
			this.elm.addEvent('click',this.selected.closureListener(this));
			this.elm.addEvent('click',this.selected.closure(this));
			this.elm.addEvent('blur',this.blur.closure(this));
			this.elm.addEvent('keyup',this.selected.closure(this));
			this.elm.addEvent('keyup',this.keyUp.closure(this));
			//增加组件
//			NE.plugins['fiximgclick'].apply(this);
			NE.plugins['video'].apply(this);
			this.ne.fireEvent('add',this);
		},

		remove : function() {
			this.saveContent();
			if(this.copyElm || this.options.hasPanel) {
				this.editorContain.remove();
				this.e.setStyle({'display' : 'block'});
				this.ne.removePanel();
			}
			this.disable();
			this.ne.fireEvent('remove',this);
		},

		disable : function() {
			this.elm.setAttribute('contentEditable','false');
		},
		setOpt: function (key, val) {
            var obj = {};
            if (utils.isString(key)) {
                obj[key] = val;
            } else {
                obj = key;
            }
            jQuery.extend(this.options, obj);
        },
        getOpt:function(key){
            return this.options[key];
        },
        
		getSel : function() {
			return this.selection;
		},

		getRng : function() {
			return this.selection.getRange();
		},

		selRng : function(rng,s) {
			if(window.getSelection) {
				s.clearRange();
			} else {
				rng.select();
			}
		},

		selElm : function() {
			var r = this.getRng();
			if(r.startContainer) {
				var contain = r.startContainer;
				return $BK(contain);
			} else {
				return $BK((this.getSel().type == "Control") ? r.item(0) : r.parentElement());
			}
		},

		saveRng : function() {
			this.savedRange = this.getRng();
			this.savedSel = this.getSel();
		},

		keyUp : function(e,t) {
//			if(e.ctrlKey) {
			this.ne.fireEvent('key',this,e);
//			}
		},
		focus : function() {
			var root = UE.htmlparser(this.getContent(),true);
			this.filterInputRule(root);
			this.getElm().innerHTML = root.toHtml();
			this.isFocused = true;
		},
		selected : function(e,t) {
//			
			if(!t) {
				t = this.selElm();
			}else{
				if (e.target.tagName == 'IMG') {
	                var range = new dom.Range(document);
	                range.selectNode(e.target).select();
	            }
				if (e.target.tagName == 'OBJECT') {
	                var range = new dom.Range(document);
	                range.selectNode(e.target).select();
	            }
			}
			
			if(!e.ctrlKey) {
				var selInstance = this.ne.selectedInstance;
				if(selInstance != this) {
					if(selInstance) {
						this.ne.fireEvent('blur',selInstance,t);
					}
					this.ne.selectedInstance = this;
					this.ne.fireEvent('focus',selInstance,t);
				}
				this.ne.fireEvent('selected',selInstance,t);
				this.isFocused = true;
				this.elm.addClass('selected');
			}
			return false;
		},

		blur : function() {
			if(this.isFocused){
				var root = UE.htmlparser(this.getContent(),true);
	            this.filterOutputRule(root);
	            this.getElm().innerHTML =root.toHtml();
				this.isFocused = false;
				this.elm.removeClass('selected');
			}
		},
		//新增方法
		addListener:function(evType, evFunc){
//			jQuery(this.elm).bind(evType)
			this.elm.addEvent(evType,evFunc.closureListener(this));
		},
		saveContent : function() {
			if(this.copyElm || this.options.hasPanel) {
				this.ne.fireEvent('save',this);
				(this.copyElm) ? this.copyElm.value = this.getContent() : this.e.innerHTML = this.getContent();
			}
		},

		getElm : function() {
			return this.elm;
		},

		getContent : function() {
			this.content = this.getElm().innerHTML;
			this.ne.fireEvent('get',this);
			return this.content;
		},

		html : function(e) {
			this.content = e;
			this.ne.fireEvent('set',this);
			this.elm.innerHTML = this.content;
		},
		nicCommand : function(cmd,args) {
			document.execCommand(cmd,false,args);
		},
		getOpt:function(key){
            return this.options[key];
        },
        getActionUrl: function(action){
            var actionName = this.getOpt(action) || action,
                imageUrl = this.getOpt('imageUrl'),
                serverUrl = this.getOpt('serverUrl');

            if(!serverUrl && imageUrl) {
                serverUrl = imageUrl.replace(/^(.*[\/]).+([\.].+)$/, '$1controller$2');
            }

            if(serverUrl) {
                serverUrl = serverUrl + (serverUrl.indexOf('?') == -1 ? '?':'&') + 'action=' + (actionName || '');
                return utils.formatUrl(serverUrl);
            } else {
                return '';
            }
        },
        /**
         * 设置当前编辑区域可以编辑
         * @method setEnabled
         * @example
         * ```javascript
         * editor.setEnabled()
         * ```
         */
        setEnabled: function () {
            var me = this;
            var range;
            if (me.elm.contentEditable == 'false') {
                me.elm.contentEditable = true;
                range = me.selection.getRange();
                //有可能内容丢失了
                try {
                    range.moveToBookmark(me.lastBk);
                    delete me.lastBk
                } catch (e) {
                    range.setStartAtFirst(me.body).collapse(true);
                }
                range.select(true);
                if (me.bkqueryCommandState) {
                    me.queryCommandState = me.bkqueryCommandState;
                    delete me.bkqueryCommandState;
                }
                if (me.bkqueryCommandValue) {
                    me.queryCommandValue = me.bkqueryCommandValue;
                    delete me.bkqueryCommandValue;
                }
                me.fireEvent('selectionchange');
            }
        },
        getLang: function (path) {
            var lang = UE.I18N[this.options.lang];
            if (!lang) {
                throw Error("not import language file");
            }
            path = (path || "").split(".");
            for (var i = 0, ci; ci = path[i++];) {
                lang = lang[ci];
                if (!lang)break;
            }
            return lang;
        },
		_selectionChange: function (delay, evt) {
	            var me = this;
	            //有光标才做selectionchange 为了解决未focus时点击source不能触发更改工具栏状态的问题（source命令notNeedUndo=1）
//	            if ( !me.selection.isFocus() ){
//	                return;
//	            }
	            var hackForMouseUp = false;
	            var mouseX, mouseY;
	            if (browser.ie && browser.version < 9 && evt && evt.type == 'mouseup') {
	                var range = this.selection.getRange();
	                if (!range.collapsed) {
	                    hackForMouseUp = true;
	                    mouseX = evt.clientX;
	                    mouseY = evt.clientY;
	                }
	            }
	            clearTimeout(_selectionChangeTimer);
	            _selectionChangeTimer = setTimeout(function () {
	                if (!me.selection || !me.selection.getNative()) {
	                    return;
	                }
	                //修复一个IE下的bug: 鼠标点击一段已选择的文本中间时，可能在mouseup后的一段时间内取到的range是在selection的type为None下的错误值.
	                //IE下如果用户是拖拽一段已选择文本，则不会触发mouseup事件，所以这里的特殊处理不会对其有影响
	                var ieRange;
	                if (hackForMouseUp && me.selection.getNative().type == 'None') {
	                    ieRange = me.document.body.createTextRange();
	                    try {
	                        ieRange.moveToPoint(mouseX, mouseY);
	                    } catch (ex) {
	                        ieRange = null;
	                    }
	                }
	                var bakGetIERange;
	                if (ieRange) {
	                    bakGetIERange = me.selection.getIERange;
	                    me.selection.getIERange = function () {
	                        return ieRange;
	                    };
	                }
	                me.selection.cache();
	                if (bakGetIERange) {
	                    me.selection.getIERange = bakGetIERange;
	                }
	                if (me.selection._cachedRange && me.selection._cachedStartElement) {
	                    me.fireEvent('beforeselectionchange');
	                    // 第二个参数causeByUi为true代表由用户交互造成的selectionchange.
	                    me.fireEvent('selectionchange', !!evt);
	                    me.fireEvent('afterselectionchange');
	                    me.selection.clear();
	                }
	            }, delay || 50);
	        },

		/**
         * 注册输入过滤规则
         * @method  addInputRule
         * @param { Function } rule 要添加的过滤规则
         * @example
         * ```javascript
         * editor.addInputRule(function(root){
         *   $.each(root.getNodesByTagName('div'),function(i,node){
         *       node.tagName="p";
         *   });
         * });
         * ```
         */
        addInputRule: function (rule) {
            this.inputRules.push(rule);
        },

        /**
         * 执行注册的过滤规则
         * @method  filterInputRule
         * @param { UE.uNode } root 要过滤的uNode节点
         * @remind 执行editor.setContent方法和执行'inserthtml'命令后，会运行该过滤函数
         * @example
         * ```javascript
         * editor.filterInputRule(editor.body);
         * ```
         * @see UE.Editor:addInputRule
         */
        filterInputRule: function (root) {
            for (var i = 0, ci; ci = this.inputRules[i++];) {
                ci.call(this, root)
            }
        },

        /**
         * 注册输出过滤规则
         * @method  addOutputRule
         * @param { Function } rule 要添加的过滤规则
         * @example
         * ```javascript
         * editor.addOutputRule(function(root){
         *   $.each(root.getNodesByTagName('p'),function(i,node){
         *       node.tagName="div";
         *   });
         * });
         * ```
         */
        addOutputRule: function (rule) {
            this.outputRules.push(rule);
        },

        /**
         * 根据输出过滤规则，过滤编辑器内容
         * @method  filterOutputRule
         * @remind 执行editor.getContent方法的时候，会先运行该过滤函数
         * @param { UE.uNode } root 要过滤的uNode节点
         * @example
         * ```javascript
         * editor.filterOutputRule(editor.body);
         * ```
         * @see UE.Editor:addOutputRule
         */
        filterOutputRule: function (root) {
            for (var i = 0, ci; ci = this.outputRules[i++];) {
                ci.call(this, root)
            }
        },

		
		  /**
         * 执行编辑命令
         * @method _callCmdFn
         * @private
         * @param { String } fnName 函数名称
         * @param { * } args 传给命令函数的参数
         * @return { * } 返回命令函数运行的返回值
         */
		_callCmdFn: function (fnName, args) {
            var cmdName = args[0].toLowerCase(),
                cmd, cmdFn;
            cmd = UE.commands[cmdName];
            cmdFn = cmd && cmd[fnName];
            //没有querycommandstate或者没有command的都默认返回0
            if ((!cmd || !cmdFn) && fnName == 'queryCommandState') {
                return 0;
            } else if (cmdFn) {
                return cmdFn.apply(this, args);
            }
        },
        /**
         * 根据传入的command命令，查选编辑器当前的选区，返回命令的状态
         * @method  queryCommandState
         * @param { String } cmdName 需要查询的命令名称
         * @remind 具体命令的使用请参考<a href="#COMMAND.LIST">命令列表</a>
         * @return { Number } number 返回放前命令的状态，返回值三种情况：(-1|0|1)
         * @example
         * ```javascript
         * editor.queryCommandState(cmdName)  => (-1|0|1)
         * ```
         * @see COMMAND.LIST
         */
        queryCommandState: function (cmdName) {
            return this._callCmdFn('queryCommandState', arguments);
        },
        
        queryCommandValue: function (cmdName) {
            return this._callCmdFn('queryCommandValue', arguments);
        },
        /**
         * 执行编辑命令cmdName，完成富文本编辑效果
         * @method execCommand
         * @param { String } cmdName 需要执行的命令
         * @remind 具体命令的使用请参考<a href="#COMMAND.LIST">命令列表</a>
         * @return { * } 返回命令函数运行的返回值
         * @example
         * ```javascript
         * editor.execCommand(cmdName);
         * ```
         */
        execCommand: function (cmdName) {
            cmdName = cmdName.toLowerCase();
            var me = this,
                result,
                cmd = UE.commands[cmdName];
            if (!cmd || !cmd.execCommand) {
                return null;
            }
            if (!cmd.notNeedUndo && !me.__hasEnterExecCommand) {
                me.__hasEnterExecCommand = true;
                if (me.queryCommandState.apply(me,arguments) != -1) {
//                    me.fireEvent('saveScene');
//                    me.fireEvent.apply(me, ['beforeexeccommand', cmdName].concat(arguments));
                    result = this._callCmdFn('execCommand', arguments);
                    //保存场景时，做了内容对比，再看是否进行contentchange触发，这里多触发了一次，去掉
//                    (!cmd.ignoreContentChange && !me._ignoreContentChange) && me.fireEvent('contentchange');
//                    me.fireEvent.apply(me, ['afterexeccommand', cmdName].concat(arguments));
//                    me.fireEvent('saveScene');
                }
                me.__hasEnterExecCommand = false;
            } else {
                result = this._callCmdFn('execCommand', arguments);
                (!me.__hasEnterExecCommand && !cmd.ignoreContentChange && !me._ignoreContentChange) && me.fireEvent('contentchange')
            }
            (!me.__hasEnterExecCommand && !cmd.ignoreContentChange && !me._ignoreContentChange) && me._selectionChange();
            return result;
        },
	});
	
	var nicEditorPanel = BaseClass.extend({
		construct : function(e,options,nicEditor) {
			this.elm = e;
			this.options = options;
			this.ne = nicEditor;
			this.panelButtons = new Array();
			this.buttonList = bkExtend([],this.ne.options.buttonList);

			this.panelContain = new bkElement('DIV').addClass('panelContain');
			this.panelElm = new bkElement('DIV').setStyle({margin : '2px', marginTop : '0px', zoom : 1, overflow : 'hidden'}).addClass('panel').appendTo(this.panelContain);
			this.panelContain.appendTo(e);

			var opt = this.ne.options;
			var buttons = opt.buttons;
			for(button in buttons) {
					this.addButton(button,opt,true);
			}
			this.reorder();
			e.noSelect();
		},

		addButton : function(buttonName,options,noOrder) {
			var button = options.buttons[buttonName];
			var type = (button['type']) ? eval('(typeof('+button['type']+') == "undefined") ? null : '+button['type']+';') : nicEditorButton;
			var hasButton = bkLib.inArray(this.buttonList,buttonName);
			if(type && (hasButton || this.ne.options.fullPanel)) {
				this.panelButtons.push(new type(this.panelElm,buttonName,options,this.ne));
				if(!hasButton) {
					this.buttonList.push(buttonName);
				}
			}
		},

		findButton : function(itm) {
			for(var i=0;i<this.panelButtons.length;i++) {
				if(this.panelButtons[i].name == itm)
					return this.panelButtons[i];
			}
		},

		reorder : function() {
			var bl = this.buttonList;
			for(var i=0;i<bl.length;i++) {
				var button = this.findButton(bl[i]);
				if(button) {
					this.panelElm.appendChild(button.margin);
				}
			}
		},

		remove : function() {
			this.elm.remove();
		}
	});
	
	var nicEditorIFrameInstance = nicEditorInstance.extend({
		savedStyles : [],

		init : function() {
			var c = this.elm.innerHTML.replace(/^\s+|\s+$/g, '');
			this.elm.innerHTML = '';
			(!c) ? c = "<br />" : c;
			this.initialContent = c;
			this.elmFrame = new bkElement('iframe').attr({'src' : 'javascript:;', 'frameBorder' : 0, 'allowTransparency' : 'true', 'scrolling' : 'no'}).setStyle({height: '100px', width: '100%'}).addClass('frame').appendTo(this.elm);
			if(this.copyElm) { this.elmFrame.setStyle({width : (this.elm.offsetWidth-4)+'px'}); }

			var styleList = ['font-size','font-family','font-weight','color'];
			for(itm in styleList) {
				this.savedStyles[bkLib.camelize(itm)] = this.elm.getStyle(itm);
			}

			setTimeout(this.initFrame.closure(this),50);
		},

		disable : function() {
			this.elm.innerHTML = this.getContent();
		},

		initFrame : function() {
			var fd = $BK(this.elmFrame.contentWindow.document);
			fd.designMode = "on";
			fd.open();
			var css = this.ne.options.externalCSS;
			fd.write('<html><head>'+((css) ? '<link href="'+css+'" rel="stylesheet" type="text/css" />' : '')+'</head><body id="nicEditContent" style="margin: 0 !important; background-color: transparent !important;">'+this.initialContent+'</body></html>');
			fd.close();
			this.frameDoc = fd;

			this.frameWin = $BK(this.elmFrame.contentWindow);
			this.frameContent = $BK(this.frameWin.document.body).setStyle(this.savedStyles);
			this.instanceDoc = this.frameWin.document.defaultView;

			this.heightUpdate();
			this.frameDoc.addEvent('mousedown', this.selected.closureListener(this)).addEvent('keyup',this.heightUpdate.closureListener(this)).addEvent('keydown',this.keyDown.closureListener(this)).addEvent('keyup',this.selected.closure(this));
			this.ne.fireEvent('add',this);
		},

		getElm : function() {
			return this.frameContent;
		},

		html : function(c) {
			this.content = c;
			this.ne.fireEvent('set',this);
			this.frameContent.innerHTML = this.content;
			this.heightUpdate();
		},

		getSel : function() {
			return (this.frameWin) ? this.frameWin.getSelection() : this.frameDoc.selection;
		},

		heightUpdate : function() {
			this.elmFrame.style.height = Math.max(this.frameContent.offsetHeight,this.initialHeight)+'px';
		},

		nicCommand : function(cmd,args) {
			this.frameDoc.execCommand(cmd,false,args);
			setTimeout(this.heightUpdate.closure(this),100);
		}
	});
	
	var nicEditorButton = BaseClass.extend({
		construct : function(e,buttonName,options,nicEditor) {
			this.options = options.buttons[buttonName];
			this.name = buttonName;
			this.ne = nicEditor;
			this.elm = e;

			this.margin = new bkElement('DIV').setStyle({'float' : 'left', marginTop : '2px'}).appendTo(e);
			this.contain = new bkElement('DIV').addClass('buttonContain').appendTo(this.margin);
			this.border = new bkElement('DIV').appendTo(this.contain);
			this.button = new bkElement('DIV').setStyle({width : '18px', height : '18px', overflow : 'hidden', zoom : 1, cursor : 'pointer'}).addClass('button').setStyle(this.ne.getIcon(buttonName,options)).appendTo(this.border);
			this.button.addEvent('mouseover', this.hoverOn.closure(this)).addEvent('mouseout',this.hoverOff.closure(this)).addEvent('mousedown',this.mouseClick.closure(this)).noSelect();

			if(!window.opera) {
				this.button.onmousedown = this.button.onclick = bkLib.cancelEvent;
			}
			nicEditor.addEvent('selected', this.enable.closure(this)).addEvent('blur', this.disable.closure(this)).addEvent('key',this.key.closure(this));
			this.disable();
			this.init();
		},

		init : function() {},

		hide : function() {
			this.contain.setStyle({display : 'none'});
		},

		updateState : function() {
			if(this.isDisabled) { this.setBg(); }
			else if(this.isHover) { this.setBg('hover'); }
			else if(this.isActive) { this.setBg('active'); }
			else { this.setBg("remove"); }
		},

		setBg : function(state) {
			var stateStyle=null;
			switch(state) {
				case 'hover':
					stateStyle = {border : '1px solid #666', backgroundColor : '#000'};
					break;
				case 'active':
					stateStyle = {border : '1px solid #666', backgroundColor : '#000'};
					break;
				default:
					stateStyle = {border : '1px solid transparent', backgroundColor : 'transparent'};
			}
			this.border.setStyle(stateStyle).addClass('button-'+state);
		},

		checkNodes : function(e) {
			var elm = e;
			do {
				if(this.options.tags && bkLib.inArray(this.options.tags,elm.nodeName)) {
					this.activate();
					return true;
				}
			} while(elm = elm.parentNode && elm.className != "nicEdit");
			elm = $BK(e);
			while(elm.nodeType == 3) {
				elm = $BK(elm.parentNode);
			}
			if(this.options.css) {
				for(itm in this.options.css) {
					if(elm.getStyle(itm,this.ne.selectedInstance.instanceDoc) == this.options.css[itm]) {
						this.activate();
						return true;
					}
				}
			}
			this.deactivate();
			return false;
		},

		activate : function() {
			if(!this.isDisabled) {
				this.isActive = true;
				this.updateState();
				this.ne.fireEvent('buttonActivate',this);
			}
		},

		deactivate : function() {
			this.isActive = false;
			this.updateState();
			if(!this.isDisabled) {
				this.ne.fireEvent('buttonDeactivate',this);
			}
		},

		enable : function(ins,t) {
			this.isDisabled = false;
			this.contain.setStyle({'opacity' : 1}).addClass('buttonEnabled');
			this.updateState();
			this.checkNodes(t);
		},

		disable : function(ins,t) {
			this.isDisabled = true;
			this.contain.setStyle({'opacity' : 0.6}).removeClass('buttonEnabled');
			this.updateState();
		},

		toggleActive : function() {
			(this.isActive) ? this.deactivate() : this.activate();
		},

		hoverOn : function() {
			if(!this.isDisabled) {
				this.isHover = true;
				this.updateState();
//				this.ne.fireEvent("buttonOver",this);
			}
		},

		hoverOff : function() {
			this.isHover = false;
			this.updateState();
//			this.ne.fireEvent("buttonOut",this);
		},

		mouseClick : function() {
			if(this.options.command) {
				this.ne.nicCommand(this.options.command,this.options.commandArgs);
				if(!this.options.noActive) {
					this.toggleActive();
				}
			}
			this.ne.fireEvent("buttonClick",this);
		},

		key : function(nicInstance,e) {
			if(this.options.key && e.ctrlKey && String.fromCharCode(e.keyCode || e.charCode).toLowerCase() == this.options.key) {
				this.mouseClick();
				if(e.preventDefault) e.preventDefault();
			}
		}

	});


	var nicPlugin = BaseClass.extend({
		construct : function(nicEditor,options) {
			this.options = options;
			this.ne = nicEditor;
			this.ne.addEvent('panel',this.loadPanel.closure(this));
			this.init();
		},

		loadPanel : function(np) {
			var buttons = this.options.buttons;
			for(var button in buttons) {
				np.addButton(button,this.options);
			}
			np.reorder();
		},

		init : function() {  }
	});

	 /* START CONFIG */
	var nicPaneOptions = { };
	/* END CONFIG */

	var insidePanel = BaseClass.extend({
		construct : function(elm,nicEditor,options,openButton) {
			this.ne = nicEditor;
			this.elm = elm;
			this.pos = elm.pos();

			this.contain = new bkElement('div').setStyle({zIndex : '99999', overflow : 'hidden', position : 'absolute'});
			this.pane = new bkElement('div').addClass('pane').setStyle(options).appendTo(this.contain);

			if(openButton && !openButton.options.noClose) {
				this.close = new bkElement('div').setStyle({'float' : 'right', height: '16px', width : '16px', cursor : 'pointer'}).setStyle(this.ne.getIcon('close',nicPaneOptions)).addEvent('mousedown',openButton.removePane.closure(this)).appendTo(this.pane);
			}
			var p=$(elm).closest(".nicEdit-buttonContain").parent();
			$(this.contain.noSelect()).appendTo(p);
			this.init();
		},

		init : function() { },

		position : function() {
			if(this.ne.nicPanel) {
				var panelElm = this.ne.nicPanel.elm;
				var panelPos = panelElm.pos();
				var newLeft = panelPos[0]+parseInt(panelElm.getStyle('width'))-(parseInt(this.pane.getStyle('width'))+8);
				if(newLeft < this.pos[0]) {
					this.contain.setStyle({left : newLeft+'px'});
				}
			}
		},

		toggle : function() {
			this.isVisible = !this.isVisible;
			this.contain.setStyle({display : ((this.isVisible) ? 'block' : 'none')});
		},

		remove : function() {
			if(this.contain) {
				this.contain.remove();
				this.contain = null;
			}
		},

		append : function(c) {
			c.appendTo(this.pane);
		},

		html : function(c) {
			this.pane.html(c);
		}

	});
	
	var nicEditorAdvancedButton = nicEditorButton.extend({
		init : function() {
			this.ne.addEvent('selected',this.removePane.closure(this)).addEvent('blur',this.removePane.closure(this));
		},
		mouseClick : function() {
			if(!this.isDisabled) {
				if(this.pane && this.pane.pane) {
					this.removePane();
				} else {
					this.pane = new insidePanel(this.contain,this.ne,{width : (this.width || '270px'), backgroundColor : '#fff'},this);
					this.addPane();
					this.ne.selectedInstance.saveRng();
				}
			}
		},
		addForm : function(f,elm) {
			this.form = new bkElement('form').addEvent('submit',this.submit.closureListener(this));
			this.pane.append(this.form);
			this.inputs = {};

			for(itm in f) {
				var field = f[itm];
				var val = '';
				if(elm) {
					val = elm.getAttribute(itm);
				}
				if(!val) {
					val = field['value'] || '';
				}
				var type = f[itm].type;

				if(type == 'title') {
						new bkElement('div').html(field.txt).setStyle({fontSize : '14px',color:"#000", fontWeight: 'bold', padding : '0px', margin : '2px 0'}).appendTo(this.form);
				} else {
					var contain = new bkElement('div').setStyle({overflow : 'hidden', clear : 'both'}).appendTo(this.form);
					if(field.txt) {
						new bkElement('label').attr({'for' : itm}).html(field.txt).setStyle({margin : '2px 4px',color:"#000", fontSize : '13px', width: '50px', lineHeight : '20px', textAlign : 'right', 'float' : 'left'}).appendTo(contain);
					}

					switch(type) {
						case 'text':
							this.inputs[itm] = new bkElement('input').attr({id : itm, 'value' : val, 'type' : 'text'}).setStyle({margin : '2px 0', fontSize : '13px', 'float' : 'left', height : '20px', border : '1px solid #ccc', overflow : 'hidden'}).setStyle(field.style).appendTo(contain);
							break;
						case 'select':
							this.inputs[itm] = new bkElement('select').attr({id : itm}).setStyle({border : '1px solid #ccc', 'float' : 'left', margin : '2px 0'}).appendTo(contain);
							for(opt in field.options) {
								new bkElement('option').attr({value : opt, selected : (opt == val) ? 'selected' : ''}).html(field.options[opt]).appendTo(this.inputs[itm]);
							}
							break;
						case 'content':
							this.inputs[itm] = new bkElement('textarea').attr({id : itm}).setStyle({border : '1px solid #ccc', 'float' : 'left'}).setStyle(field.style).appendTo(contain);
							this.inputs[itm].value = val;
					}
				}
			}
			new bkElement('input').attr({'type' : 'submit'}).setStyle({backgroundColor : '#efefef',border : '1px solid #ccc', margin : '3px 0', 'float' : 'left', 'clear' : 'both'}).appendTo(this.form);
			this.form.onsubmit = bkLib.cancelEvent;
		},

		submit : function() {},

		findElm : function(tag,attr,val) {
			var list = this.ne.selectedInstance.getElm().getElementsByTagName(tag);
			for(var i=0;i<list.length;i++) {
				if(list[i].getAttribute(attr) == val) {
					return $BK(list[i]);
				}
			}
		},

		removePane : function() {
			if(this.pane) {
				this.pane.remove();
				this.pane = null;
//				this.ne.selectedInstance.restoreRng();
			}
		}
	});


	var nicButtonTips = BaseClass.extend({
		construct : function(nicEditor) {
			this.ne = nicEditor;
			nicEditor.addEvent('buttonOver',this.show.closure(this)).addEvent('buttonOut',this.hide.closure(this));

		},

		show : function(button) {
			this.timer = setTimeout(this.create.closure(this,button),400);
		},

		create : function(button) {
			this.timer = null;
			if(!this.pane) {
				this.pane = new insidePanel(button.button,this.ne,{fontSize : '12px', marginTop : '5px',padding:"0 5px",color:"#000", backgroundColor : '#FFFFC9'});
				this.pane.html(button.options.name);
			}
		},

		hide : function(button) {
			if(this.timer) {
				clearTimeout(this.timer);
			}
			if(this.pane) {
				this.pane = this.pane.remove();
			}
		}
	});
	nicEditors.registerPlugin(nicButtonTips);
	 /* START CONFIG */
	var nicSelectOptions = {
		buttons : {
			'fontSize' : {name : __('Select Font Size'), type : 'nicEditorFontSizeSelect', command : 'fontsize'},
			'fontFamily' : {name : __('Select Font Family'), type : 'nicEditorFontFamilySelect', command : 'fontname'},
//			'fontFormat' : {name : __('Select Font Format'), type : 'nicEditorFontFormatSelect', command : 'formatBlock'}
		}
	};
	/* END CONFIG */
	var nicEditorSelect = nicEditorButton.extend({
		construct : function(e,buttonName,options,nicEditor) {
			this.options = options.buttons[buttonName];
			this.elm = e;
			this.ne = nicEditor;
			this.name = buttonName;
			this.selOptions = new Array();
			this.margin = new bkElement('DIV').setStyle({'float' : 'left', marginTop : '2px'}).appendTo(e);
			this.contain = new bkElement('DIV').addClass('buttonContain').appendTo(this.margin);
			this.border = new bkElement('DIV').appendTo(this.contain);
			this.button = new bkElement('DIV').setStyle({width : '18px', height : '18px', overflow : 'hidden', zoom : 1, cursor : 'pointer'}).addClass('button').setStyle(this.ne.getIcon(buttonName,options)).addEvent('click',this.toggle.closure(this)).appendTo(this.border);
			this.button.addEvent('mouseover', this.hoverOn.closure(this)).addEvent('mouseout',this.hoverOff.closure(this)).addEvent('mousedown',this.mouseClick.closure(this)).noSelect();
			
			if(!window.opera) {
				this.button.onmousedown = this.button.onclick = bkLib.cancelEvent;
			}
			nicEditor.addEvent('selected', this.enable.closure(this)).addEvent('blur', this.disable.closure(this)).addEvent('key',this.key.closure(this));
			var select=this;
			$(this.margin).hover(function(){},function(e){
				select.close();
			});
			this.margin.noSelect();
			this.disable();
			this.init();
			function iclose(){
				this.colse();
			}
		},
		setDisplay : function(txt) {
			//this.txt.html(txt);
		},
		toggle : function() {
			if(!this.isDisabled) {
				(this.pane) ? this.close() : this.open();
			}
		},

		open : function() {
			this.pane = new insidePanel(this.button,this.ne,{width : '90px', padding: '0px', borderTop : 0, borderLeft : '1px solid #ccc', borderRight : '1px solid #ccc', borderBottom : '0px', backgroundColor : '#fff'});

			for(var i=0;i<this.selOptions.length;i++) {
				var opt = this.selOptions[i];
				var itmContain = new bkElement('div').setStyle({overflow : 'hidden', borderBottom : '1px solid #ccc', width: '90px',fontSize:"12px",color:"#000", textAlign : 'left', overflow : 'hidden', cursor : 'pointer'});
				var itm = new bkElement('div').setStyle({padding : '0px 4px'}).html(opt[1]).appendTo(itmContain).noSelect();
				itm.addEvent('click',this.update.closure(this,opt[0])).addEvent('mouseover',this.over.closure(this,itm)).addEvent('mouseout',this.out.closure(this,itm)).attr('id',opt[0]);
				this.pane.append(itmContain);
				if(!window.opera) {
					itm.onmousedown = bkLib.cancelEvent;
				}
			}
		},

		close : function() {
			if(this.pane) {
				this.pane = this.pane.remove();
			}
		},

		over : function(opt) {
			opt.setStyle({backgroundColor : '#ccc'});
		},

		out : function(opt) {
			opt.setStyle({backgroundColor : '#fff'});
		},


		add : function(k,v) {
			this.selOptions.push(new Array(k,v));
		},

		update : function(elm) {
			this.ne.nicCommand(this.options.command,elm);
			this.close();
		}
		
	});

	var nicEditorFontSizeSelect = nicEditorSelect.extend({
		sel : {1 : '8pt', 2 : '10pt', 3 : '12pt', 4 : '14pt', 5 : '18pt', 6 : '24pt'},
		init : function() {
			this.setDisplay('大小');
			for(itm in this.sel) {
				this.add(itm,'<font size="'+itm+'">'+this.sel[itm]+'</font>');
			}
		}
	});

	var nicEditorFontFamilySelect = nicEditorSelect.extend({
		sel : {'宋体' : '宋体','幼圆' : '幼圆','楷体' : '楷体','隶书' : '隶书','arial' : 'Arial','comic sans ms' : 'Comic Sans','courier new' : 'Courier New','georgia' : 'Georgia', 'helvetica' : 'Helvetica', 'impact' : 'Impact', 'times new roman' : 'Times', 'trebuchet ms' : 'Trebuchet', 'verdana' : 'Verdana'},

		init : function() {
			this.setDisplay('字&nbsp;体');
			for(itm in this.sel) {
				this.add(itm,'<font face="'+itm+'">'+this.sel[itm]+'</font>');
			}
		}
	});

	nicEditors.registerPlugin(nicPlugin,nicSelectOptions);
	/* START CONFIG */
	var nicColorOptions = {
		buttons : {
			'forecolor' : {name : __('文字颜色'), type : 'nicEditorColorButton', noClose : true},
			'bgcolor' : {name : __('背景颜色'), type : 'nicEditorBgColorButton', noClose : true}
		}
	};
	/* END CONFIG */

	var nicEditorColorButton = nicEditorAdvancedButton.extend({
		init:function(){
			$(this.margin).hover(function(){},this.removePane.closure(this));
		},
		addPane : function() {
				var colorList = {0 : '00',1 : '33',2 : '66',3 :'99',4 : 'CC',5 : 'FF'};
				var colorItems = new bkElement('DIV').setStyle({width: '270px'});

				for(var r in colorList) {
					for(var b in colorList) {
						for(var g in colorList) {
							var colorCode = '#'+colorList[r]+colorList[g]+colorList[b];

							var colorSquare = new bkElement('DIV').setStyle({'cursor' : 'pointer', 'height' : '15px', 'float' : 'left'}).appendTo(colorItems);
							var colorBorder = new bkElement('DIV').setStyle({border: '2px solid '+colorCode}).appendTo(colorSquare);
							var colorInner = new bkElement('DIV').setStyle({backgroundColor : colorCode, overflow : 'hidden', width : '11px', height : '11px'}).addEvent('click',this.colorSelect.closure(this,colorCode)).addEvent('mouseover',this.on.closure(this,colorBorder)).addEvent('mouseout',this.off.closure(this,colorBorder,colorCode)).appendTo(colorBorder);

							if(!window.opera) {
								colorSquare.onmousedown = colorInner.onmousedown = bkLib.cancelEvent;
							}

						}
					}
				}
				this.pane.append(colorItems.noSelect());
		},

		colorSelect : function(c) {
			this.ne.nicCommand('foreColor',c);
			this.removePane();
		},

		on : function(colorBorder) {
			colorBorder.setStyle({border : '2px solid #000'});
		},

		off : function(colorBorder,colorCode) {
			colorBorder.setStyle({border : '2px solid '+colorCode});
		}
	});

	var nicEditorBgColorButton = nicEditorColorButton.extend({
		colorSelect : function(c) {
			this.ne.nicCommand('hiliteColor',c);
			this.removePane();
		}
	});

	nicEditors.registerPlugin(nicPlugin,nicColorOptions);



	/* START CONFIG */
	var nicImageOptions = {
		buttons : {
			'image' : {name : '上传图片', type : 'nicImageButton', tags : ['IMG']}
		}

	};
	/* END CONFIG */

	var nicImageButton = nicEditorAdvancedButton.extend({
		
		init : function() {
			this.ne.addEvent('selected',this.removePane.closure(this)).addEvent('blur',this.removePane.closure(this));
		},

		mouseClick : function() {
			this.winDiv=new bkElement('DIV').setStyle({ width: '100%', height: '20px', border : '1px solid #ccc'});
			top.document.body.appendChild(this.winDiv);
			var editor = this.ne.selectedInstance;
			if(editor){
				jQuery(this.winDiv).window({
					title:"插入图片",
					 width:542,
					 height:412,    
					 modal:true,
					 iframe:defaults_config.cxt+"/fileDialogs/imageWin.action",
					 onIfarmeLoad:function(t){
						 var iframe=jQuery(t).window("getIframe");
						 if(iframe){
							 iframe.contentWindow.iniWindowParam(editor,jQuery(t));
						 }
					 }
				});
			}
		},

		addForm : function(f,elm) {
		},
		submit : function() { },

		findElm : function(tag,attr,val) {
		},

		removePane : function() {
			if(this.pane) {
				this.pane.remove();
				this.pane = null;
			}
		}
	});

	nicEditors.registerPlugin(nicPlugin,nicImageOptions);

	/* START CONFIG */
	var nicUploadOptions = {
		buttons : {
			'uploadvideo' : {name : '上传视频', type : 'nicUploadButton'}
		}

	};
	nicEditors.registerPlugin(nicPlugin,nicUploadOptions);
	/* END CONFIG */

	var nicUploadButton = nicEditorAdvancedButton.extend({
		
		init : function() {
			this.ne.addEvent('selected',this.removePane.closure(this)).addEvent('blur',this.removePane.closure(this));
		},

		mouseClick : function() {
			this.winDiv=new bkElement('DIV').setStyle({ width: '100%', height: '20px', border : '1px solid #ccc'});
			top.document.body.appendChild(this.winDiv);
			var editor = this.ne.selectedInstance;
			if(editor){
				jQuery(this.winDiv).window({
					title:"插入图片",
					 width:542,
					 height:412,    
					 modal:true,
					 iframe:defaults_config.cxt+"/fileDialogs/videoWin.action",
					 onIfarmeLoad:function(t){
						 var iframe=jQuery(t).window("getIframe");
						 if(iframe){
							 iframe.contentWindow.iniWindowParam(editor,jQuery(t));
						 }
					 }
				});
			}
		},

		addForm : function(f,elm) {
		},
		submit : function() { },

		findElm : function(tag,attr,val) {
		},

		removePane : function() {
			if(this.pane) {
				this.pane.remove();
				this.pane = null;
			}
		}
	});

	nicUploadButton.statusCb = function(o) {
		nicUploadButton.lastPlugin.update(o);
	};

	var nicXHTML = BaseClass.extend({
		stripAttributes : ['_moz_dirty','_moz_resizing','_extended'],
		noShort : ['style','title','script','textarea','a'],
		cssReplace : {'font-weight:bold;' : 'strong', 'font-style:italic;' : 'em'},
		sizes : {1 : 'xx-small', 2 : 'x-small', 3 : 'small', 4 : 'medium', 5 : 'large', 6 : 'x-large'},

		construct : function(nicEditor) {
			this.ne = nicEditor;
			if(this.ne.options.xhtml) {
				nicEditor.addEvent('get',this.cleanup.closure(this));
			}
		},

		cleanup : function(ni) {
			var node = ni.getElm();
			var xhtml = this.toXHTML(node);
			ni.content = xhtml;
		},

		toXHTML : function(n,r,d) {
			var txt = '';
			var attrTxt = '';
			var cssTxt = '';
			var nType = n.nodeType;
			var nName = n.nodeName.toLowerCase();
			var nChild = n.hasChildNodes && n.hasChildNodes();
			var extraNodes = new Array();

			switch(nType) {
				case 1:
					var nAttributes = n.attributes;

					switch(nName) {
						case 'b':
							nName = 'strong';
							break;
						case 'i':
							nName = 'em';
							break;
						case 'font':
							nName = 'span';
							break;
					}

					if(r) {
						for(var i=0;i<nAttributes.length;i++) {
							var attr = nAttributes[i];

							var attributeName = attr.nodeName.toLowerCase();
							var attributeValue = attr.nodeValue;

							if(!attr.specified || !attributeValue || bkLib.inArray(this.stripAttributes,attributeName) || typeof(attributeValue) == "function") {
								continue;
							}

							switch(attributeName) {
								case 'style':
									var css = attributeValue.replace(/ /g,"");
									for(itm in this.cssReplace) {
										if(css.indexOf(itm) != -1) {
											extraNodes.push(this.cssReplace[itm]);
											css = css.replace(itm,'');
										}
									}
									cssTxt += css;
									attributeValue = "";
								break;
								case 'class':
									attributeValue = attributeValue.replace("Apple-style-span","");
								break;
								case 'size':
									cssTxt += "font-size:"+this.sizes[attributeValue]+';';
									attributeValue = "";
								break;
							}

							if(attributeValue) {
								attrTxt += ' '+attributeName+'="'+attributeValue+'"';
							}
						}

						if(cssTxt) {
							attrTxt += ' style="'+cssTxt+'"';
						}

						for(var i=0;i<extraNodes.length;i++) {
							txt += '<'+extraNodes[i]+'>';
						}

						if(attrTxt == "" && nName == "span") {
							r = false;
						}
						if(r) {
							txt += '<'+nName;
							if(nName != 'br') {
								txt += attrTxt;
							}
						}
					}

					if(!nChild && !bkLib.inArray(this.noShort,attributeName)) {
						if(r) {
							txt += ' />';
						}
					} else {
						if(r) {
							txt += '>';
						}

						for(var i=0;i<n.childNodes.length;i++) {
							var results = this.toXHTML(n.childNodes[i],true,true);
							if(results) {
								txt += results;
							}
						}
					}

					if(r && nChild) {
						txt += '</'+nName+'>';
					}

					for(var i=0;i<extraNodes.length;i++) {
						txt += '</'+extraNodes[i]+'>';
					}

					break;
				case 3:
					//if(n.nodeValue != '\n') {
						txt += n.nodeValue;
					//}
					break;
			}

			return txt;
		}
	});
	nicEditors.registerPlugin(nicXHTML);

	var nicBBCode = BaseClass.extend({
		construct : function(nicEditor) {
			this.ne = nicEditor;
			if(this.ne.options.bbCode) {
				nicEditor.addEvent('get',this.bbGet.closure(this));
				nicEditor.addEvent('set',this.bbSet.closure(this));

				var loadedPlugins = this.ne.loadedPlugins;
				for(itm in loadedPlugins) {
					if(loadedPlugins[itm].toXHTML) {
						this.xhtml = loadedPlugins[itm];
					}
				}
			}
		},

		bbGet : function(ni) {
			var xhtml = this.xhtml.toXHTML(ni.getElm());
			ni.content = this.toBBCode(xhtml);
		},

		bbSet : function(ni) {
			ni.content = this.fromBBCode(ni.content);
		},

		toBBCode : function(xhtml) {
			function rp(r,m) {
				xhtml = xhtml.replace(r,m);
			}
			rp(/\n/gi,"");
			rp(/<strong>(.*?)<\/strong>/gi,"[b]$1[/b]");
			rp(/<em>(.*?)<\/em>/gi,"[i]$1[/i]");
			rp(/<span.*?style="text-decoration:underline;">(.*?)<\/span>/gi,"[u]$1[/u]");
			rp(/<ul>(.*?)<\/ul>/gi,"[list]$1[/list]");
			rp(/<li>(.*?)<\/li>/gi,"[*]$1[/*]");
			rp(/<ol>(.*?)<\/ol>/gi,"[list=1]$1[/list]");
			rp(/<img.*?src="(.*?)".*?>/gi,"[img]$1[/img]");
			rp(/<a.*?href="(.*?)".*?>(.*?)<\/a>/gi,"[url=$1]$2[/url]");
			rp(/<br.*?>/gi,"\n");
			rp(/<.*?>.*?<\/.*?>/gi,"");
			return xhtml;
		},

		fromBBCode : function(bbCode) {
			function rp(r,m) {
				bbCode = bbCode.replace(r,m);
			}
			rp(/\[b\](.*?)\[\/b\]/gi,"<strong>$1</strong>");
			rp(/\[i\](.*?)\[\/i\]/gi,"<em>$1</em>");
			rp(/\[u\](.*?)\[\/u\]/gi,"<span style=\"text-decoration:underline;\">$1</span>");
			rp(/\[list\](.*?)\[\/list\]/gi,"<ul>$1</ul>");
			rp(/\[list=1\](.*?)\[\/list\]/gi,"<ol>$1</ol>");
			rp(/\[\*\](.*?)\[\/\*\]/gi,"<li>$1</li>");
			rp(/\[img\](.*?)\[\/img\]/gi,"<img src=\"$1\" />");
			rp(/\[url=(.*?)\](.*?)\[\/url\]/gi,"<a href=\"$1\">$2</a>");
			rp(/\n/gi,"<br />");
			//rp(/\[.*?\](.*?)\[\/.*?\]/gi,"$1");
			return bbCode;
		}


	});
	nicEditors.registerPlugin(nicBBCode);



	nicEditor = nicEditor.extend({
	        floatingPanel : function() {
	                this.floating = new bkElement('DIV').setStyle({position: 'absolute', top : '-1000px'}).appendTo(document.body);
	                this.addEvent('focus', this.reposition.closure(this)).addEvent('blur', this.hide.closure(this));
	                this.setPanel(this.floating);
	        },

	        reposition : function() {
	                var e = this.selectedInstance.e;
	                this.floating.setStyle({ width : (parseInt(e.getStyle('width')) || e.clientWidth)+'px' });
	                var top = e.offsetTop-this.floating.offsetHeight;
	                if(top < 0) {
	                        top = e.offsetTop+e.offsetHeight;
	                }

	                this.floating.setStyle({ top : top+'px', left : e.offsetLeft+'px', display : 'block' });
	        },

	        hide : function() {
	                this.floating.setStyle({ top : '-1000px'});
	        }
	});



	/* START CONFIG */
	var nicCodeOptions = {
		buttons : {
			'xhtml' : {name : '编辑 HTML', type : 'nicCodeButton'}
		}

	};
	/* END CONFIG */
	var nicCodeButton = nicEditorAdvancedButton.extend({
		width : '350px',

		addPane : function() {
			this.addForm({
				'' : {type : 'title', txt : '编辑 HTML'},
				'code' : {type : 'content', 'value' : this.ne.selectedInstance.getContent(), style : {width: '340px', height : '200px'}}
			});
		},

		submit : function(e) {
			var code = this.inputs['code'].value;
			this.ne.selectedInstance.html(code);
			this.removePane();
		}
	});
	nicEditors.registerPlugin(nicPlugin,nicCodeOptions);
	
	return $.fn.nicEditor = function(options) {
		return new nicEditor(options);
	  };
	  
	  
	
})(jQuery);
