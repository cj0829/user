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
 * parser - jQuery EasyUI
 * 
 */

(function($){
	$.parser = {
		auto: true,
		onComplete: function(context){},
		plugins:['draggable','droppable','resizable','pagination','tooltip',
		         'linkbutton','butcombo','butcombogrid','menu','menubutton','splitbutton','progressbar',
				 'tree','textbox','filebox','combo','combobox','combotree','combogrid','numberbox','validatebox','searchbox',
				 'spinner','numberspinner','timespinner','datetimespinner','calendar','datebox','datetimebox','slider',
				 'layout','panel','datagrid','propertygrid','treegrid','tabs','accordion','window','dialog','form'
		],
		parse: function(context){
			var aa = [];
			for(var i=0; i<$.parser.plugins.length; i++){
				var name = $.parser.plugins[i];
				var r = $('.easyui-' + name, context);
				if (r.length){
					if (r[name]){
						r[name]();
					} else {
						aa.push({name:name,jq:r});
					}
				}
			}
			if (aa.length && window.easyloader){
				var names = [];
				for(var i=0; i<aa.length; i++){
					names.push(aa[i].name);
				}
				easyloader.load(names, function(){
					for(var i=0; i<aa.length; i++){
						var name = aa[i].name;
						var jq = aa[i].jq;
						jq[name]();
					}
					$.parser.onComplete.call($.parser, context);
				});
			} else {
				$.parser.onComplete.call($.parser, context);
			}
		},
		
		parseValue: function(property, value, parent, delta){
			delta = delta || 0;
			var v = $.trim(String(value||''));
			var endchar = v.substr(v.length-1, 1);
			if (endchar == '%'){
				v = parseInt(v.substr(0, v.length-1));
				if (property.toLowerCase().indexOf('width') >= 0){
					v = Math.floor((parent.width()-delta) * v / 100.0);
				} else {
					v = Math.floor((parent.height()-delta) * v / 100.0);
				}
			} else {
				v = parseInt(v) || undefined;
			}
			return v;
		},
		
		/**
		 * parse options, including standard 'data-options' attribute.
		 * 
		 * calling examples:
		 * $.parser.parseOptions(target);
		 * $.parser.parseOptions(target, ['id','title','width',{fit:'boolean',border:'boolean'},{min:'number'}]);
		 */
		parseOptions: function(target, properties){
			var t = $(target);
			var options = {};
			
			var s = $.trim(t.attr('data-options'));
			if (s){
				if (s.substring(0, 1) != '{'){
					s = '{' + s + '}';
				}
				options = (new Function('return ' + s))();
			}
			$.map(['width','height','left','top','minWidth','maxWidth','minHeight','maxHeight'], function(p){
				var pv = $.trim(target.style[p] || '');
				if (pv){
					if (pv.indexOf('%') == -1){
						pv = parseInt(pv) || undefined;
					}
					options[p] = pv;
				}
			});
				
			if (properties){
				var opts = {};
				for(var i=0; i<properties.length; i++){
					var pp = properties[i];
					if (typeof pp == 'string'){
						opts[pp] = t.attr(pp);
					} else {
						for(var name in pp){
							var type = pp[name];
							if (type == 'boolean'){
								opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
							} else if (type == 'number'){
								opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
							}
						}
					}
				}
				$.extend(options, opts);
			}
			return options;
		},
		 objConvertStr: function(o){
	        if (o == undefined) {
	            return "";
	        }
	        var r = [];
	        if (typeof o == "string") return o.replace(/([\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t");
	        if (typeof o == "object") {
	            if (!o.sort) {
	                for (var i in o)
	                    r.push( i + "\:" + $.parser.objConvertStr(o[i]));
	                if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
	                    r.push("toString:" + o.toString.toString());
	                }
	                r =  r.join();
	            } else {
	                for (var i = 0; i < o.length; i++)
	                    r.push($.parser.objConvertStr(o[i]));
	                r =  r.join();
	            }
	            return r;
	        }
	        return o.toString().replace(/\"\:/g, '":""');
	    },
	    /** Format a date object into a string value.*/
	    formatDate:function(format, date) {
			if (!date) {
				return "";
			}
			var iFormat,
			// Check whether a format character is doubled
			lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
				if (matches) {
					iFormat++;
				}
				return matches;
			},
			// Format a number, with leading zero if necessary
			formatNumber = function(match, value, len) {
				var num = "" + value;
				if (lookAhead(match)) {
					while (num.length < len) {
						num = "0" + num;
					}
				}
				return num;
			},
			// Format a name, short or long as requested(此处需要修改)
			formatName = function(match, value, shortNames, longNames) {
				return (lookAhead(match) ? longNames[value] : shortNames[value]);
			},
			output = "",
			literal = false;
			if (date) {
				for (iFormat = 0; iFormat < format.length; iFormat++) {
					if (literal) {
						if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
							literal = false;
						} else {
							output += format.charAt(iFormat);
						}
					} else {
						switch (format.charAt(iFormat)) {
							case "d":
								output += formatNumber("d", date.getDate(), 2);
								break;
							case "D":
								output += formatName("D", date.getDay(), dayNamesShort, dayNames);
								break;
							case "o":
								output += formatNumber("o",Math.round((new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime() - new Date(date.getFullYear(), 0, 0).getTime()) / 86400000), 3);
								break;
							case "m":
								output += formatNumber("m", date.getMonth() + 1, 2);
								break;
							case "M":
								output += formatName("M", date.getMonth(), monthNamesShort, monthNames);
								break;
							case "y":
								output += (lookAhead("y") ? date.getFullYear() :
									(date.getYear() % 100 < 10 ? "0" : "") + date.getYear() % 100);
								break;
							case "@":
								output += date.getTime();
								break;
							case "!":
								output += date.getTime() * 10000 + this._ticksTo1970;
								break;
							case "'":
								if (lookAhead("'")) {
									output += "'";
								} else {
									literal = true;
								}
								break;
							default:
								output += format.charAt(iFormat);
						}
					}
				}
			}
			return output;
		},
		/** Parse a string value into a date object.*/
		parseDate:function(format, value) {
			if (format == null || value == null) {
				throw "Invalid arguments";
			}
			value = (typeof value === "object" ? value.toString() : value + "");
			if (value === "") {
				return null;
			}
			var iFormat, dim, extra,
				iValue = 0,
				year = -1,
				month = -1,
				day = -1,
				doy = -1,
				literal = false,
				date,
				// Check whether a format character is doubled
				lookAhead = function(match) {
					var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
					if (matches) {
						iFormat++;
					}
					return matches;
				},
				// Extract a number from the string value
				getNumber = function(match) {
					var isDoubled = lookAhead(match),
						size = (match === "@" ? 14 : (match === "!" ? 20 :
						(match === "y" && isDoubled ? 4 : (match === "o" ? 3 : 2)))),
						digits = new RegExp("^\\d{1," + size + "}"),
						num = value.substring(iValue).match(digits);
					if (!num) {
						throw "Missing number at position " + iValue;
					}
					iValue += num[0].length;
					return parseInt(num[0], 10);
				},
				// Confirm that a literal character matches the string value
				checkLiteral = function() {
					if (value.charAt(iValue) !== format.charAt(iFormat)) {
						throw "Unexpected literal at position " + iValue;
					}
					iValue++;
				};
				/** Handle switch to/from daylight saving.*/
				_daylightSavingAdjust = function(date) {
					if (!date) {
						return null;
					}
					date.setHours(date.getHours() > 12 ? date.getHours() + 2 : 0);
					return date;
				};

			for (iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal) {
					if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
						literal = false;
					} else {
						checkLiteral();
					}
				} else {
					switch (format.charAt(iFormat)) {
						case "d":
							day = getNumber("d");
							break;
						case "D":
							getName("D", dayNamesShort, dayNames);
							break;
						case "o":
							doy = getNumber("o");
							break;
						case "m":
							month = getNumber("m");
							break;
						case "M":
							month = getName("M", monthNamesShort, monthNames);
							break;
						case "y":
							year = getNumber("y");
							break;
						case "@":
							date = new Date(getNumber("@"));
							year = date.getFullYear();
							month = date.getMonth() + 1;
							day = date.getDate();
							break;
						case "!":
							date = new Date((getNumber("!") - this._ticksTo1970) / 10000);
							year = date.getFullYear();
							month = date.getMonth() + 1;
							day = date.getDate();
							break;
						case "'":
							if (lookAhead("'")){
								checkLiteral();
							} else {
								literal = true;
							}
							break;
						default:
							checkLiteral();
					}
				}
			}

			if (iValue < value.length){
				extra = value.substr(iValue);
				if (!/^\s+/.test(extra)) {
					throw "Extra/unparsed characters found in date: " + extra;
				}
			}
			if (year === -1) {
				year = new Date().getFullYear();
			} else if (year < 100) {
				year += new Date().getFullYear() - new Date().getFullYear() % 100 +
					(year <= shortYearCutoff ? 0 : -100);
			}

			if (doy > -1) {
				month = 1;
				day = doy;
				do {
					dim = _getDaysInMonth(year, month - 1);
					if (day <= dim) {
						break;
					}
					month++;
					day -= dim;
				} while (true);
			}

			date = _daylightSavingAdjust(new Date(year, month - 1, day));
			if (date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day) {
				throw "Invalid date"; // E.g. 31/02/00
			}
			return date;
		}
		
	};
	$(function(){
		var d = $('<div style="position:absolute;top:-1000px;width:100px;height:100px;padding:5px"></div>').appendTo('body');
		$._boxModel = d.outerWidth()!=100;
		d.remove();
		
		if (!window.easyloader && $.parser.auto){
			$.parser.parse();
		}
	});
	
	/**
	 * extend plugin to set box model width
	 */
	$.fn._outerWidth = function(width){
		if (width == undefined){
			if (this[0] == window){
				return this.width() || document.body.clientWidth;
			}
			return this.outerWidth()||0;
		}
		return this._size('width', width);
	};
	
	/**
	 * extend plugin to set box model height
	 */
	$.fn._outerHeight = function(height){
		if (height == undefined){
			if (this[0] == window){
				return this.height() || document.body.clientHeight;
			}
			return this.outerHeight()||0;
		}
		return this._size('height', height);
	};
	
	$.fn._scrollLeft = function(left){
		if (left == undefined){
			return this.scrollLeft();
		} else {
			return this.each(function(){$(this).scrollLeft(left);});
		}
	};
	
	$.fn._propAttr = $.fn.prop || $.fn.attr;
	
	$.fn._size = function(options, parent){
		if (typeof options == 'string'){
			if (options == 'clear'){
				return this.each(function(){
					$(this).css({width:'',minWidth:'',maxWidth:'',height:'',minHeight:'',maxHeight:''});
				});
			} else if (options == 'unfit'){
				return this.each(function(){
					_fit(this, $(this).parent(), false);
				});
			} else {
				if (parent == undefined){
					return _css(this[0], options);
				} else {
					return this.each(function(){
						_css(this, options, parent);
					});
				}
			}
		} else {
			return this.each(function(){
//				var topwrap =  $(this).closest("div.easyui-top-wrap");
//				if(topwrap.length>0){
//					parent = parent || topwrap.parent();
//				}else{
//					parent = parent || $(this).parent();
//				}
				//2015-7-1 修改弹出窗口的问题， 用于表格的样式
				var topwrap =  $(this).closest("div.datagrid-top-wrap");
				if(topwrap.length>0){
					parent = parent || topwrap.parent();
				}else{
					parent = parent || $(this).parent();
				}
				$.extend(options, _fit(this, parent, options.fit)||{});
				var r1 = _setSize(this, 'width', parent, options);
				var r2 = _setSize(this, 'height', parent, options);
				if (r1 || r2){
					$(this).addClass('easyui-fluid');
				} else {
					$(this).removeClass('easyui-fluid');
				}
			});
		}
		
		function _fit(target, parent, fit){
			var t = $(target)[0];
			var p = parent[0];
			
			var fcount =  (p&&p.fcount) || 0;
			if (fit){
				if (!t.fitted){
					t.fitted = true;
					p.fcount = fcount + 1;
					$(p).addClass('panel-noscroll');
					if (p.tagName == 'BODY'){
						$('html').addClass('panel-fit');
					}
				}
				return {
					width: ($(p).width()||1),
					height: ($(p).height()||1)
				};
			} else {
				if (t.fitted){
					t.fitted = false;
					p.fcount = fcount - 1;
					if (p.fcount == 0){
						$(p).removeClass('panel-noscroll');
						if (p.tagName == 'BODY'){
							$('html').removeClass('panel-fit');
						}
					}
				}
				return false;
			}
		}
		function _setSize(target, property, parent, options){
			var t = $(target);
			var p = property;
			var p1 = p.substr(0,1).toUpperCase() + p.substr(1);
			var min = $.parser.parseValue('min'+p1, options['min'+p1], parent);// || 0;
			var max = $.parser.parseValue('max'+p1, options['max'+p1], parent);// || 99999;
			var val = $.parser.parseValue(p, options[p], parent);
			var fluid = (String(options[p]||'').indexOf('%') >= 0 ? true : false);
			
			if (!isNaN(val)){
				var v = Math.min(Math.max(val, min||0), max||99999);
				if (!fluid){
					options[p] = v;
				}
				t._size('min'+p1, '');
				t._size('max'+p1, '');
				t._size(p, v);
			} else {
				t._size(p, '');
				t._size('min'+p1, min);
				t._size('max'+p1, max);
			}
			return fluid || options.fit;
		}
		function _css(target, property, value){
			var t = $(target);
			if (value == undefined){
				value = parseInt(target.style[property]);
				if (isNaN(value)){return undefined;}
				if ($._boxModel){
					value += getDeltaSize();
				}
				return value;
			} else if (value === ''){
				t.css(property, '');
			} else {
				if ($._boxModel){
					value -= getDeltaSize();
					if (value < 0){value = 0;}
				}
				t.css(property, value+'px');
			}
			function getDeltaSize(){
				if (property.toLowerCase().indexOf('width') >= 0){
					return t.outerWidth() - t.width();
				} else {
					return t.outerHeight() - t.height();
				}
			}
		}
	};
	
})(jQuery);

/**
 * support for mobile devices
 */
(function($){
	var longTouchTimer = null;
	//var dblTouchTimer = null;
	var isDblClick = false;
	
	function onTouchStart(e){
		if (e.touches.length != 1){return;}
		if (!isDblClick){
			isDblClick = true;
			dblClickTimer = setTimeout(function(){
				isDblClick = false;
			}, 500);
		} else {
			clearTimeout(dblClickTimer);
			isDblClick = false;
			fire(e, 'dblclick');
//			e.preventDefault();
		}
		longTouchTimer = setTimeout(function(){
			fire(e, 'contextmenu', 3);
		}, 1000);
		fire(e, 'mousedown');
		if ($.fn.draggable.isDragging || $.fn.resizable.isResizing){
			e.preventDefault();
		}
	}
	function onTouchMove(e){
		if (e.touches.length != 1){return;}
		if (longTouchTimer){
			clearTimeout(longTouchTimer);
		}
		fire(e, 'mousemove');
		if ($.fn.draggable.isDragging || $.fn.resizable.isResizing){
			e.preventDefault();
		}
	}
	function onTouchEnd(e){
//		if (e.touches.length > 0){return}
		if (longTouchTimer){
			clearTimeout(longTouchTimer);
		}
		fire(e, 'mouseup');
		if ($.fn.draggable.isDragging || $.fn.resizable.isResizing){
			e.preventDefault();
		}
	}
	
	function fire(e, name, which){
		var event = new $.Event(name);
		event.pageX = e.changedTouches[0].pageX;
		event.pageY = e.changedTouches[0].pageY;
		event.which = which || 1;
		$(e.target).trigger(event);
	}
	
	if (document.addEventListener){
		document.addEventListener("touchstart", onTouchStart, true);
		document.addEventListener("touchmove", onTouchMove, true);
		document.addEventListener("touchend", onTouchEnd, true);
	}
})(jQuery);

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
 * draggable - jQuery EasyUI
 * 
 */
(function($){
//	var isDragging = false;
	function drag(e){
		var state = $.data(e.data.target, 'draggable');
		var opts = state.options;
		var proxy = state.proxy;
		
		var dragData = e.data;
		var left = dragData.startLeft + e.pageX - dragData.startX;
		var top = dragData.startTop + e.pageY - dragData.startY;
		
		if (proxy){
			if (proxy.parent()[0] == document.body){
				if (opts.deltaX != null && opts.deltaX != undefined){
					left = e.pageX + opts.deltaX;
				} else {
					left = e.pageX - e.data.offsetWidth;
				}
				if (opts.deltaY != null && opts.deltaY != undefined){
					top = e.pageY + opts.deltaY;
				} else {
					top = e.pageY - e.data.offsetHeight;
				}
			} else {
				if (opts.deltaX != null && opts.deltaX != undefined){
					left += e.data.offsetWidth + opts.deltaX;
				}
				if (opts.deltaY != null && opts.deltaY != undefined){
					top += e.data.offsetHeight + opts.deltaY;
				}
			}
		}
		
//		if (opts.deltaX != null && opts.deltaX != undefined){
//			left = e.pageX + opts.deltaX;
//		}
//		if (opts.deltaY != null && opts.deltaY != undefined){
//			top = e.pageY + opts.deltaY;
//		}
		
		if (e.data.parent != document.body) {
			left += $(e.data.parent).scrollLeft();
			top += $(e.data.parent).scrollTop();
		}
		
		if (opts.axis == 'h') {
			dragData.left = left;
		} else if (opts.axis == 'v') {
			dragData.top = top;
		} else {
			dragData.left = left;
			dragData.top = top;
		}
	}
	
	function applyDrag(e){
		var state = $.data(e.data.target, 'draggable');
		var opts = state.options;
		var proxy = state.proxy;
		if (!proxy){
			proxy = $(e.data.target);
		}
//		if (proxy){
//			proxy.css('cursor', opts.cursor);
//		} else {
//			proxy = $(e.data.target);
//			$.data(e.data.target, 'draggable').handle.css('cursor', opts.cursor);
//		}
		proxy.css({
			left:e.data.left,
			top:e.data.top
		});
		$('body').css('cursor', opts.cursor);
	}
	
	function doDown(e){
//		isDragging = true;
		$.fn.draggable.isDragging = true;
		var state = $.data(e.data.target, 'draggable');
		var opts = state.options;
		
		var droppables = $('.droppable').filter(function(){
			return e.data.target != this;
		}).filter(function(){
			var accept = $.data(this, 'droppable').options.accept;
			if (accept){
				return $(accept).filter(function(){
					return this == e.data.target;
				}).length > 0;
			} else {
				return true;
			}
		});
		state.droppables = droppables;
		
		var proxy = state.proxy;
		if (!proxy){
			if (opts.proxy){
				if (opts.proxy == 'clone'){
					proxy = $(e.data.target).clone().insertAfter(e.data.target);
				} else {
					proxy = opts.proxy.call(e.data.target, e.data.target);
				}
				state.proxy = proxy;
			} else {
				proxy = $(e.data.target);
			}
		}
		
		proxy.css('position', 'absolute');
		drag(e);
		applyDrag(e);
		
		opts.onStartDrag.call(e.data.target, e);
		return false;
	}
	
	function doMove(e){
		var state = $.data(e.data.target, 'draggable');
		drag(e);
		if (state.options.onDrag.call(e.data.target, e) != false){
			applyDrag(e);
		}
		
		var source = e.data.target;
		state.droppables.each(function(){
			var dropObj = $(this);
			if (dropObj.droppable('options').disabled){return;}
			
			var p2 = dropObj.offset();
			if (e.pageX > p2.left && e.pageX < p2.left + dropObj.outerWidth()
					&& e.pageY > p2.top && e.pageY < p2.top + dropObj.outerHeight()){
				if (!this.entered){
					$(this).trigger('_dragenter', [source]);
					this.entered = true;
				}
				$(this).trigger('_dragover', [source]);
			} else {
				if (this.entered){
					$(this).trigger('_dragleave', [source]);
					this.entered = false;
				}
			}
		});
		
		return false;
	}
	
	function doUp(e){
//		isDragging = false;
		$.fn.draggable.isDragging = false;
//		drag(e);
		doMove(e);
		
		var state = $.data(e.data.target, 'draggable');
		var proxy = state.proxy;
		var opts = state.options;
		if (opts.revert){
			if (checkDrop() == true){
				$(e.data.target).css({
					position:e.data.startPosition,
					left:e.data.startLeft,
					top:e.data.startTop
				});
			} else {
				if (proxy){
					var left, top;
					if (proxy.parent()[0] == document.body){
						left = e.data.startX - e.data.offsetWidth;
						top = e.data.startY - e.data.offsetHeight;
					} else {
						left = e.data.startLeft;
						top = e.data.startTop;
					}
					proxy.animate({
						left: left,
						top: top
					}, function(){
						removeProxy();
					});
				} else {
					$(e.data.target).animate({
						left:e.data.startLeft,
						top:e.data.startTop
					}, function(){
						$(e.data.target).css('position', e.data.startPosition);
					});
				}
			}
		} else {
			$(e.data.target).css({
				position:'absolute',
				left:e.data.left,
				top:e.data.top
			});
			checkDrop();
		}
		
		opts.onStopDrag.call(e.data.target, e);
		
		$(document).unbind('.draggable');
		setTimeout(function(){
			$('body').css('cursor','');
		},100);
		
		function removeProxy(){
			if (proxy){
				proxy.remove();
			}
			state.proxy = null;
		}
		
		function checkDrop(){
			var dropped = false;
			state.droppables.each(function(){
				var dropObj = $(this);
				if (dropObj.droppable('options').disabled){return;}
				
				var p2 = dropObj.offset();
				if (e.pageX > p2.left && e.pageX < p2.left + dropObj.outerWidth()
						&& e.pageY > p2.top && e.pageY < p2.top + dropObj.outerHeight()){
					if (opts.revert){
						$(e.data.target).css({
							position:e.data.startPosition,
							left:e.data.startLeft,
							top:e.data.startTop
						});
					}
					$(this).trigger('_drop', [e.data.target]);
					removeProxy();
					dropped = true;
					this.entered = false;
					return false;
				}
			});
			if (!dropped && !opts.revert){
				removeProxy();
			}
			return dropped;
		}
		
		return false;
	}
	
	$.fn.draggable = function(options, param){
		if (typeof options == 'string'){
			return $.fn.draggable.methods[options](this, param);
		}
		
		return this.each(function(){
			var opts;
			var state = $.data(this, 'draggable');
			if (state) {
				state.handle.unbind('.draggable');
				opts = $.extend(state.options, options);
			} else {
				opts = $.extend({}, $.fn.draggable.defaults, $.fn.draggable.parseOptions(this), options || {});
			}
			var handle = opts.handle ? (typeof opts.handle=='string' ? $(opts.handle, this) : opts.handle) : $(this);
			
			$.data(this, 'draggable', {
				options: opts,
				handle: handle
			});
			
			if (opts.disabled) {
				$(this).css('cursor', '');
				return;
			}
			
			handle.unbind('.draggable').bind('mousemove.draggable', {target:this}, function(e){
//				if (isDragging) return;
				if ($.fn.draggable.isDragging){return;}
				var opts = $.data(e.data.target, 'draggable').options;
				if (checkArea(e)){
					$(this).css('cursor', opts.cursor);
				} else {
					$(this).css('cursor', '');
				}
			}).bind('mouseleave.draggable', {target:this}, function(e){
				$(this).css('cursor', '');
			}).bind('mousedown.draggable', {target:this}, function(e){
				if (checkArea(e) == false) return;
				$(this).css('cursor', '');

				var position = $(e.data.target).position();
				var offset = $(e.data.target).offset();
				var data = {
					startPosition: $(e.data.target).css('position'),
					startLeft: position.left,
					startTop: position.top,
					left: position.left,
					top: position.top,
					startX: e.pageX,
					startY: e.pageY,
					offsetWidth: (e.pageX - offset.left),
					offsetHeight: (e.pageY - offset.top),
					target: e.data.target,
					parent: $(e.data.target).parent()[0]
				};
				
				$.extend(e.data, data);
				var opts = $.data(e.data.target, 'draggable').options;
				if (opts.onBeforeDrag.call(e.data.target, e) == false) return;
				
				$(document).bind('mousedown.draggable', e.data, doDown);
				$(document).bind('mousemove.draggable', e.data, doMove);
				$(document).bind('mouseup.draggable', e.data, doUp);
//				$('body').css('cursor', opts.cursor);
			});
			
			// check if the handle can be dragged
			function checkArea(e) {
				var state = $.data(e.data.target, 'draggable');
				var handle = state.handle;
				var offset = $(handle).offset();
				var width = $(handle).outerWidth();
				var height = $(handle).outerHeight();
				var t = e.pageY - offset.top;
				var r = offset.left + width - e.pageX;
				var b = offset.top + height - e.pageY;
				var l = e.pageX - offset.left;
				
				return Math.min(t,r,b,l) > state.options.edge;
			}
			
		});
	};
	
	$.fn.draggable.methods = {
		options: function(jq){
			return $.data(jq[0], 'draggable').options;
		},
		proxy: function(jq){
			return $.data(jq[0], 'draggable').proxy;
		},
		enable: function(jq){
			return jq.each(function(){
				$(this).draggable({disabled:false});
			});
		},
		disable: function(jq){
			return jq.each(function(){
				$(this).draggable({disabled:true});
			});
		}
	};
	
	$.fn.draggable.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, 
				$.parser.parseOptions(target, ['cursor','handle','axis',
				       {'revert':'boolean','deltaX':'number','deltaY':'number','edge':'number'}]), {
			disabled: (t.attr('disabled') ? true : undefined)
		});
	};
	
	$.fn.draggable.defaults = {
		proxy:null,	// 'clone' or a function that will create the proxy object, 
					// the function has the source parameter that indicate the source object dragged.
		revert:false,
		cursor:'move',
		deltaX:null,
		deltaY:null,
		handle: null,
		disabled: false,
		edge:0,
		axis:null,	// v or h
		
		onBeforeDrag: function(e){},
		onStartDrag: function(e){},
		onDrag: function(e){},
		onStopDrag: function(e){}
	};
	
	$.fn.draggable.isDragging = false;
	
//	$(function(){
//		function touchHandler(e) {
//			var touches = e.changedTouches, first = touches[0], type = "";
//
//			switch(e.type) {
//				case "touchstart": type = "mousedown"; break;
//				case "touchmove":  type = "mousemove"; break;        
//				case "touchend":   type = "mouseup";   break;
//				default: return;
//			}
//			var simulatedEvent = document.createEvent("MouseEvent");
//			simulatedEvent.initMouseEvent(type, true, true, window, 1,
//									  first.screenX, first.screenY,
//									  first.clientX, first.clientY, false,
//									  false, false, false, 0/*left*/, null);
//
//			first.target.dispatchEvent(simulatedEvent);
//			if (isDragging){
//				e.preventDefault();
//			}
//		}
//		
//		if (document.addEventListener){
//			document.addEventListener("touchstart", touchHandler, true);
//			document.addEventListener("touchmove", touchHandler, true);
//			document.addEventListener("touchend", touchHandler, true);
//			document.addEventListener("touchcancel", touchHandler, true); 
//		}
//	});
})(jQuery);
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
 * droppable - jQuery EasyUI
 * 
 */
(function($){
	function init(target){
		$(target).addClass('droppable');
		$(target).bind('_dragenter', function(e, source){
			$.data(target, 'droppable').options.onDragEnter.apply(target, [e, source]);
		});
		$(target).bind('_dragleave', function(e, source){
			$.data(target, 'droppable').options.onDragLeave.apply(target, [e, source]);
		});
		$(target).bind('_dragover', function(e, source){
			$.data(target, 'droppable').options.onDragOver.apply(target, [e, source]);
		});
		$(target).bind('_drop', function(e, source){
			$.data(target, 'droppable').options.onDrop.apply(target, [e, source]);
		});
	}
	
	$.fn.droppable = function(options, param){
		if (typeof options == 'string'){
			return $.fn.droppable.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'droppable');
			if (state){
				$.extend(state.options, options);
			} else {
				init(this);
				$.data(this, 'droppable', {
					options: $.extend({}, $.fn.droppable.defaults, $.fn.droppable.parseOptions(this), options)
				});
			}
		});
	};
	
	$.fn.droppable.methods = {
		options: function(jq){
			return $.data(jq[0], 'droppable').options;
		},
		enable: function(jq){
			return jq.each(function(){
				$(this).droppable({disabled:false});
			});
		},
		disable: function(jq){
			return jq.each(function(){
				$(this).droppable({disabled:true});
			});
		}
	};
	
	$.fn.droppable.parseOptions = function(target){
		var t = $(target);
		return $.extend({},	$.parser.parseOptions(target, ['accept']), {
			disabled: (t.attr('disabled') ? true : undefined)
		});
	};
	
	$.fn.droppable.defaults = {
		accept:null,
		disabled:false,
		onDragEnter:function(e, source){},
		onDragOver:function(e, source){},
		onDragLeave:function(e, source){},
		onDrop:function(e, source){}
	};
})(jQuery);
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
 * resizable - jQuery EasyUI
 * 
 */
(function($){
//	var isResizing = false;
	$.fn.resizable = function(options, param){
		if (typeof options == 'string'){
			return $.fn.resizable.methods[options](this, param);
		}
		
		function resize(e){
			var resizeData = e.data;
			var options = $.data(resizeData.target, 'resizable').options;
			if (resizeData.dir.indexOf('e') != -1) {
				var width = resizeData.startWidth + e.pageX - resizeData.startX;
				width = Math.min(
							Math.max(width, options.minWidth),
							options.maxWidth
						);
				resizeData.width = width;
			}
			if (resizeData.dir.indexOf('s') != -1) {
				var height = resizeData.startHeight + e.pageY - resizeData.startY;
				height = Math.min(
						Math.max(height, options.minHeight),
						options.maxHeight
				);
				resizeData.height = height;
			}
			if (resizeData.dir.indexOf('w') != -1) {
				var width = resizeData.startWidth - e.pageX + resizeData.startX;
				width = Math.min(
							Math.max(width, options.minWidth),
							options.maxWidth
						);
				resizeData.width = width;
				resizeData.left = resizeData.startLeft + resizeData.startWidth - resizeData.width;
				
//				resizeData.width = resizeData.startWidth - e.pageX + resizeData.startX;
//				if (resizeData.width >= options.minWidth && resizeData.width <= options.maxWidth) {
//					resizeData.left = resizeData.startLeft + e.pageX - resizeData.startX;
//				}
			}
			if (resizeData.dir.indexOf('n') != -1) {
				var height = resizeData.startHeight - e.pageY + resizeData.startY;
				height = Math.min(
							Math.max(height, options.minHeight),
							options.maxHeight
						);
				resizeData.height = height;
				resizeData.top = resizeData.startTop + resizeData.startHeight - resizeData.height;
				
//				resizeData.height = resizeData.startHeight - e.pageY + resizeData.startY;
//				if (resizeData.height >= options.minHeight && resizeData.height <= options.maxHeight) {
//					resizeData.top = resizeData.startTop + e.pageY - resizeData.startY;
//				}
			}
		}
		
		function applySize(e){
			var resizeData = e.data;
			var t = $(resizeData.target);
			t.css({
				left: resizeData.left,
				top: resizeData.top
			});
			if (t.outerWidth() != resizeData.width){t._outerWidth(resizeData.width)}
			if (t.outerHeight() != resizeData.height){t._outerHeight(resizeData.height)}
//			t._outerWidth(resizeData.width)._outerHeight(resizeData.height);
		}
		
		function doDown(e){
//			isResizing = true;
			$.fn.resizable.isResizing = true;
			$.data(e.data.target, 'resizable').options.onStartResize.call(e.data.target, e);
			return false;
		}
		
		function doMove(e){
			resize(e);
			if ($.data(e.data.target, 'resizable').options.onResize.call(e.data.target, e) != false){
				applySize(e)
			}
			return false;
		}
		
		function doUp(e){
//			isResizing = false;
			$.fn.resizable.isResizing = false;
			resize(e, true);
			applySize(e);
			$.data(e.data.target, 'resizable').options.onStopResize.call(e.data.target, e);
			$(document).unbind('.resizable');
			$('body').css('cursor','');
//			$('body').css('cursor','auto');
			return false;
		}
		
		return this.each(function(){
			var opts = null;
			var state = $.data(this, 'resizable');
			if (state) {
				$(this).unbind('.resizable');
				opts = $.extend(state.options, options || {});
			} else {
				opts = $.extend({}, $.fn.resizable.defaults, $.fn.resizable.parseOptions(this), options || {});
				$.data(this, 'resizable', {
					options:opts
				});
			}
			
			if (opts.disabled == true) {
				return;
			}
			
			// bind mouse event using namespace resizable
			$(this).bind('mousemove.resizable', {target:this}, function(e){
//				if (isResizing) return;
				if ($.fn.resizable.isResizing){return}
				var dir = getDirection(e);
				if (dir == '') {
					$(e.data.target).css('cursor', '');
				} else {
					$(e.data.target).css('cursor', dir + '-resize');
				}
			}).bind('mouseleave.resizable', {target:this}, function(e){
				$(e.data.target).css('cursor', '');
			}).bind('mousedown.resizable', {target:this}, function(e){
				var dir = getDirection(e);
				if (dir == '') return;
				
				function getCssValue(css) {
					var val = parseInt($(e.data.target).css(css));
					if (isNaN(val)) {
						return 0;
					} else {
						return val;
					}
				}
				
				var data = {
					target: e.data.target,
					dir: dir,
					startLeft: getCssValue('left'),
					startTop: getCssValue('top'),
					left: getCssValue('left'),
					top: getCssValue('top'),
					startX: e.pageX,
					startY: e.pageY,
					startWidth: $(e.data.target).outerWidth(),
					startHeight: $(e.data.target).outerHeight(),
					width: $(e.data.target).outerWidth(),
					height: $(e.data.target).outerHeight(),
					deltaWidth: $(e.data.target).outerWidth() - $(e.data.target).width(),
					deltaHeight: $(e.data.target).outerHeight() - $(e.data.target).height()
				};
				$(document).bind('mousedown.resizable', data, doDown);
				$(document).bind('mousemove.resizable', data, doMove);
				$(document).bind('mouseup.resizable', data, doUp);
				$('body').css('cursor', dir+'-resize');
			});
			
			// get the resize direction
			function getDirection(e) {
				var tt = $(e.data.target);
				var dir = '';
				var offset = tt.offset();
				var width = tt.outerWidth();
				var height = tt.outerHeight();
				var edge = opts.edge;
				if (e.pageY > offset.top && e.pageY < offset.top + edge) {
					dir += 'n';
				} else if (e.pageY < offset.top + height && e.pageY > offset.top + height - edge) {
					dir += 's';
				}
				if (e.pageX > offset.left && e.pageX < offset.left + edge) {
					dir += 'w';
				} else if (e.pageX < offset.left + width && e.pageX > offset.left + width - edge) {
					dir += 'e';
				}
				
				var handles = opts.handles.split(',');
				for(var i=0; i<handles.length; i++) {
					var handle = handles[i].replace(/(^\s*)|(\s*$)/g, '');
					if (handle == 'all' || handle == dir) {
						return dir;
					}
				}
				return '';
			}
			
			
		});
	};
	
	$.fn.resizable.methods = {
		options: function(jq){
			return $.data(jq[0], 'resizable').options;
		},
		enable: function(jq){
			return jq.each(function(){
				$(this).resizable({disabled:false});
			});
		},
		disable: function(jq){
			return jq.each(function(){
				$(this).resizable({disabled:true});
			});
		}
	};
	
	$.fn.resizable.parseOptions = function(target){
		var t = $(target);
		return $.extend({},
				$.parser.parseOptions(target, [
					'handles',{minWidth:'number',minHeight:'number',maxWidth:'number',maxHeight:'number',edge:'number'}
				]), {
			disabled: (t.attr('disabled') ? true : undefined)
		})
	};
	
	$.fn.resizable.defaults = {
		disabled:false,
		handles:'n, e, s, w, ne, se, sw, nw, all',
		minWidth: 10,
		minHeight: 10,
		maxWidth: 10000,//$(document).width(),
		maxHeight: 10000,//$(document).height(),
		edge:5,
		onStartResize: function(e){},
		onResize: function(e){},
		onStopResize: function(e){}
	};
	
	$.fn.resizable.isResizing = false;
	
})(jQuery);
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
 * linkbutton - jQuery EasyUI
 * 
 */
(function($){
	function setSize(target, param){
		var opts = $.data(target, 'linkbutton').options;
		if (param){
			$.extend(opts, param);
		}
		if (opts.width || opts.height || opts.fit){
			var spacer = $('<div style="display:none"></div>').insertBefore(target);
			var btn = $(target);
			var parent = btn.parent();
			btn.appendTo('body');
			btn._size(opts, parent);
			var left = btn.find('.l-btn-left');
			left.css('margin-top', parseInt((btn.height()-left.height())/2)+'px');
			btn.insertAfter(spacer);
			spacer.remove();
		}
	}
	
	function createButton(target) {
		var opts = $.data(target, 'linkbutton').options;
		var t = $(target).empty();
		
		t.addClass('l-btn').removeClass('l-btn-plain l-btn-selected l-btn-plain-selected');
		t.removeClass('l-btn-small l-btn-medium l-btn-large').addClass('l-btn-'+opts.size);
		if (opts.plain){t.addClass('l-btn-plain');}
		if (opts.selected){
			t.addClass(opts.plain ? 'l-btn-selected l-btn-plain-selected' : 'l-btn-selected');
		}
		t.attr('group', opts.group || '');
		t.attr('id', opts.id || '');
		
		var inner = $('<span class="l-btn-left"></span>').appendTo(t);
		if (opts.text){
			$('<span class="l-btn-text"></span>').html(opts.text).appendTo(inner);
		} else {
			$('<span class="l-btn-text l-btn-empty">&nbsp;</span>').appendTo(inner);
		}
		if (opts.iconCls){
			$('<span class="l-btn-icon">&nbsp;</span>').addClass(opts.iconCls).appendTo(inner);
			inner.addClass('l-btn-icon-'+opts.iconAlign);
		}
		
		t.unbind('.linkbutton').bind('focus.linkbutton',function(){
			if (!opts.disabled){
				$(this).addClass('l-btn-focus');
			}
		}).bind('blur.linkbutton',function(){
			$(this).removeClass('l-btn-focus');
		}).bind('click.linkbutton',function(){
			if (!opts.disabled){
				if (opts.toggle){
					if (opts.selected){
						$(this).linkbutton('unselect');
					} else {
						$(this).linkbutton('select');
					}
				}
				opts.onClick.call(this);
			}
//			return false;
		});
//		if (opts.toggle && !opts.disabled){
//			t.bind('click.linkbutton', function(){
//				if (opts.selected){
//					$(this).linkbutton('unselect');
//				} else {
//					$(this).linkbutton('select');
//				}
//			});
//		}
		
		setSelected(target, opts.selected);
		setDisabled(target, opts.disabled);
	}
	
	function setSelected(target, selected){
		var opts = $.data(target, 'linkbutton').options;
		if (selected){
			if (opts.group){
				$('a.l-btn[group="'+opts.group+'"]').each(function(){
					var o = $(this).linkbutton('options');
					if (o.toggle){
						$(this).removeClass('l-btn-selected l-btn-plain-selected');
						o.selected = false;
					}
				});
			}
			$(target).addClass(opts.plain ? 'l-btn-selected l-btn-plain-selected' : 'l-btn-selected');
			opts.selected = true;
		} else {
			if (!opts.group){
				$(target).removeClass('l-btn-selected l-btn-plain-selected');
				opts.selected = false;
			}
		}
	}
	
	function setDisabled(target, disabled){
		var state = $.data(target, 'linkbutton');
		var opts = state.options;
		$(target).removeClass('l-btn-disabled l-btn-plain-disabled');
		if (disabled){
			opts.disabled = true;
			var href = $(target).attr('href');
			if (href){
				state.href = href;
				$(target).attr('href', 'javascript:void(0)');
			}
			if (target.onclick){
				state.onclick = target.onclick;
				target.onclick = null;
			}
			opts.plain ? $(target).addClass('l-btn-disabled l-btn-plain-disabled') : $(target).addClass('l-btn-disabled');
		} else {
			opts.disabled = false;
			if (state.href) {
				$(target).attr('href', state.href);
			}
			if (state.onclick) {
				target.onclick = state.onclick;
			}
		}
	}
	
	$.fn.linkbutton = function(options, param){
		if (typeof options == 'string'){
			return $.fn.linkbutton.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'linkbutton');
			if (state){
				$.extend(state.options, options);
			} else {
				$.data(this, 'linkbutton', {
					options: $.extend({}, $.fn.linkbutton.defaults, $.fn.linkbutton.parseOptions(this), options)
				});
				$(this).removeAttr('disabled');
				$(this).bind('_resize', function(e, force){
					if ($(this).hasClass('easyui-fluid') || force){
						setSize(this);
					}
					return false;
				});
			}
			
			createButton(this);
			setSize(this);
		});
	};
	
	$.fn.linkbutton.methods = {
		options: function(jq){
			return $.data(jq[0], 'linkbutton').options;
		},
		resize: function(jq, param){
			return jq.each(function(){
				setSize(this, param);
			});
		},
		enable: function(jq){
			return jq.each(function(){
				setDisabled(this, false);
			});
		},
		disable: function(jq){
			return jq.each(function(){
				setDisabled(this, true);
			});
		},
		select: function(jq){
			return jq.each(function(){
				setSelected(this, true);
			});
		},
		unselect: function(jq){
			return jq.each(function(){
				setSelected(this, false);
			});
		}
	};
	
	$.fn.linkbutton.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, 
			['id','iconCls','iconAlign','group','size',{plain:'boolean',toggle:'boolean',selected:'boolean'}]
		), {
			disabled: (t.attr('disabled') ? true : undefined),
			text: $.trim(t.html()),
			iconCls: (t.attr('icon') || t.attr('iconCls'))
		});
	};
	
	$.fn.linkbutton.defaults = {
		id: null,
		disabled: false,
		toggle: false,
		selected: false,
		group: null,
		plain: false,
		text: '',
		iconCls: null,
		iconAlign: 'left',
		size: 'small',	// small,large
		onClick: function(){}
	};
	
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function buildToolbar(target) {
		var pagination = $.data(target, "pagination");
		var opts = pagination.options;
		// 设置bb缓存
		var bb = pagination.bb = {};
		var pageBody=$(target).addClass("pagination");
		var hmtlPage=[];
		if ("before"==opts.buttonPosition && opts.buttons) {
			if($.isFunction(opts.buttons)){
				var jBut=opts.buttons();
				if(jBut instanceof jQuery){
					hmtlPage.push("<div class=\"fl mb8\">");
					hmtlPage.push(opts.buttons().html());
					hmtlPage.push("</div>");
				}else if(typeof jBut == "string"){
					hmtlPage.push("<div class=\"fl mb8\">");
					hmtlPage.push(jBut);
					hmtlPage.push("</div>");
				}
			}else if(typeof jBut == "string"){
				hmtlPage.push("<div class=\"fl mb8\">");
				hmtlPage.push($(opts.buttons));
				hmtlPage.push("</div>");
			}
			//$("<td><div class=\"pagination-btn-separator\"></div></td>").appendTo(tr);
		}
		//需要清空之前的东西不然的，会出现
		pageBody.empty();
		pageBody.append($(hmtlPage.join("")));
		var pagetConext=$("<div class=\"e-expand-page\"></div>");
		
		var pager = opts.message||"";
		if(opts.total && opts.total>0){
			pager = $("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr></tr></table>");
			var tr = pager.find("tr");
			var layoutArray = $.extend([], opts.layout);
			if (!opts.showPageList) {
				_inarray(layoutArray, "list");
			}
			if (!opts.showRefresh) {
				_inarray(layoutArray, "refresh");
			}
			if (layoutArray[0] == "sep") {
				layoutArray.shift();
			}
			if (layoutArray[layoutArray.length - 1] == "sep") {
				layoutArray.pop();
			}
			
			for (var i = 0; i < layoutArray.length; i++) {
				var lay = layoutArray[i];
				if (lay == "list") {
					var ps = $("<select class=\"pagination-page-list\"></select>");
					ps.bind("change", function() {
						opts.pageSize = parseInt($(this).val());
						opts.onChangePageSize.call(target, opts.pageSize);
						selectPage(target, opts.pageNumber);
					});
					for (var m = 0; m < opts.pageList.length; m++) {
						$("<option></option>").text(opts.pageList[m]).appendTo(ps);
					}
					$("<td></td>").append(ps).appendTo(tr);
				} else { if (lay == "sep") {
					$("<td><div class=\"pagination-btn-separator\"></div></td>").appendTo(tr);
				} else { if (lay == "first") {
					bb.first = _findNavButtion("first");
				} else { if (lay == "prev") {
					bb.prev = _findNavButtion("prev");
				} else { if (lay == "next") {
					bb.next = _findNavButtion("next");
				} else { if (lay == "last") {
					bb.last = _findNavButtion("last");
				} else { if (lay == "manual") {
					if(opts.showPageStyle=="links"){
						$("<td class=\"pagination-links\"></td>").appendTo(tr);
					}else{
						$("<span style=\"padding-left:6px;\"></span>").html(opts.beforePageText).appendTo(tr).wrap("<td></td>");
						bb.num = $("<input class=\"pagination-num\" type=\"text\" value=\"1\" size=\"2\"><span class=\"_total_pageNum\"></span>").appendTo(tr).wrap("<td></td>");
						bb.num.unbind(".pagination").bind("keydown.pagination",function(e) {
								if (e.keyCode == 13) {
								var _a = parseInt($(this).val()) || 1;
								selectPage(target, _a);
								return false;
							}
						});
						bb.after = $("<span style=\"padding-right:6px;\"></span>").appendTo(tr).wrap("<td></td>");
					}
				} else {if (lay == "refresh") {
					bb.refresh = _findNavButtion("refresh");
				} else { if (lay == "links") {
					if(opts.showPageStyle=="manual"){
						$("<span style=\"padding-left:6px;\"></span>").appendTo(tr).wrap("<td></td>");
						bb.num = $("<input class=\"pagination-num\" type=\"text\" value=\"1\" size=\"2\"><span class=\"_total_pageNum\"></span>").appendTo(tr).wrap("<td></td>");
						bb.num.unbind(".pagination").bind("keydown.pagination",function(e) {
								if (e.keyCode == 13) {
								var _a = parseInt($(this).val()) || 1;
								selectPage(target, _a);
								return false;
							}
						});
						//bb.after = $("<span style=\"padding-right:6px;\"></span>").appendTo(tr).wrap("<td></td>");
					}else{
						$("<td class=\"pagination-links\"></td>").appendTo(tr);
					}
					
				}}}}}}}}}
			}
			if ("after"==opts.buttonPosition && opts.buttons) {
				$("<td><div class=\"pagination-btn-separator\"></div></td>").appendTo(tr);
				if ($.isArray(opts.buttons)) {
					for (var n = 0; n < opts.buttons.length; n++) {
						var _b = opts.buttons[n];
						if (_b == "-") {
							$("<td><div class=\"pagination-btn-separator\"></div></td>").appendTo(tr);
						} else {
							var td = $("<td></td>").appendTo(tr);
							var a = $("<a href=\"javascript:void(0)\"></a>").appendTo(td);
							a[0].onclick = eval(_b.handler || function() { });
							a.linkbutton($.extend({}, _b, {
								plain : true
							}));
						}
					}
				}else if($.isFunction(opts.buttons)){
					opts.buttons().appendTo($("<td></td>").appendTo(tr)).show();
				}else{
					$(opts.buttons).appendTo($("<td></td>").appendTo(tr)).show();
				}
			}
		}
		pagetConext.append(pager);
		pagetConext.append($("<div class=\"pagination-info\"></div>"));
		pagetConext.append($("<div style=\"clear:both;\"></div>"));
		pageBody.append(pagetConext);
//		$("<div class=\"pagination-info\"></div>").appendTo(pager);
//		$("<div style=\"clear:both;\"></div>").appendTo(pager);
		function _findNavButtion(name) {
			var nav = opts.nav[name];
			var a = $("<a href=\"javascript:void(0)\"></a>").appendTo(tr);
			a.wrap("<td></td>");
			a.linkbutton({
				iconCls : nav.iconCls,
				plain : true
			}).unbind(".pagination").bind("click.pagination", function() {
				nav.handler.call(target);
			});
			return a;
		};
		function _inarray(array, value) {
			var _f = $.inArray(value, array);
			if (_f >= 0) {
				array.splice(_f, 1);
			}
			return array;
		};
	};
	function selectPage(target, page) {
		var opts = $.data(target, "pagination").options;
		refresh(target, {
			pageNumber : page
		});
		opts.onSelectPage.call(target, opts.pageNumber, opts.pageSize);
	};
	function refresh(target, param) {
		var pagination = $.data(target, "pagination");
		var opts = pagination.options;
		var bb = pagination.bb;
		$.extend(opts, param || {});
		if(opts.total<=0){
			return;
		}
		var ps = $(target).find("select.pagination-page-list");
		if (opts.showPageList && ps.length) {
			ps.val(opts.pageSize + "");
			opts.pageSize = parseInt(ps.val());
		}
		var pageNum = Math.ceil(opts.total / opts.pageSize) || 1;
		if (opts.pageNumber < 1) {
			opts.pageNumber = 1;
		}
		if (opts.pageNumber > pageNum) {
			opts.pageNumber = pageNum;
		}
		if (opts.total == 0) {
			opts.pageNumber = 0;
			pageNum = 0;
		}
		if (bb.num) {
			bb.num.val(opts.pageNumber);
		}
		
		var tdPageNum = $(target).find("span._total_pageNum");
		if(tdPageNum && tdPageNum.length>0){
			tdPageNum.html("/"+pageNum+"\u9875");
		}
		
		if (bb.after) {
			bb.after.html(opts.afterPageText.replace(/{pages}/, pageNum));
		}
		var td = $(target).find("td.pagination-links");
		if (td.length) {
			td.empty();
			var meanNum = opts.pageNumber - Math.floor(opts.links / 2);
			if (meanNum < 1) {
				meanNum = 1;
			}
			var _1b = meanNum + opts.links - 1;
			if (_1b > pageNum) {
				_1b = pageNum;
			}
			meanNum = _1b - opts.links + 1;
			if (meanNum < 1) {
				meanNum = 1;
			}
			for (var i = meanNum; i <= _1b; i++) {
				var a = $("<a class=\"pagination-link\" href=\"javascript:void(0)\"></a>").appendTo(td);
				a.linkbutton({
					plain : true,
					text : i
				});
				if (i == opts.pageNumber) {
					a.linkbutton("select");
				} else {
					a.unbind(".pagination").bind("click.pagination", {
						pageNumber : i
					}, function(e) {
						selectPage(target, e.data.pageNumber);
					});
				}
			}
		}
		if(opts.showMsg){
			var message = opts.displayMsg;
			message = message.replace(/{from}/, opts.total == 0 ? 0 : opts.pageSize * (opts.pageNumber - 1) + 1);
			message = message.replace(/{to}/, Math.min(opts.pageSize * (opts.pageNumber), opts.total));
			message = message.replace(/{total}/, opts.total);
			$(target).find("div.pagination-info").html(message);
		}
		if (bb.first) {
			bb.first.linkbutton({
				disabled : ((!opts.total) || opts.pageNumber == 1)
			});
		}
		if (bb.prev) {
			bb.prev.linkbutton({
				disabled : ((!opts.total) || opts.pageNumber == 1)
			});
		}
		if (bb.next) {
			bb.next.linkbutton({
				disabled : (opts.pageNumber == pageNum)
			});
		}
		if (bb.last) {
			bb.last.linkbutton({
				disabled : (opts.pageNumber == pageNum)
			});
		}
		setLoadStatus(target, opts.loading);
	};
	function setLoadStatus(target, loading) {
		var pagination = $.data(target, "pagination");
		var opts = pagination.options;
		opts.loading = loading;
		if (opts.showRefresh && pagination.bb.refresh) {
			pagination.bb.refresh.linkbutton({
				iconCls : (opts.loading ? "pagination-loading" : "pagination-load")
			});
		}
	};
	
	function reload(target){
		var pagination = $.data(target, "pagination");
		var opts = pagination.options;
		if (opts.onBeforeRefresh.call(target, opts.pageNumber, opts.pageSize) != false) {
			$(target).pagination("select", opts.pageNumber);
			opts.onRefresh.call(target, opts.pageNumber, opts.pageSize);
		}
	};
	
	$.fn.pagination = function(options, param) {
		if (typeof options == "string") {
			return $.fn.pagination.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var opts;
			var pagination = $.data(this, "pagination");
			if (pagination) {
				opts = $.extend(pagination.options, options);
			} else {
				opts = $.extend({}, $.fn.pagination.defaults, $.fn.pagination.parseOptions(this), options);
				$.data(this, "pagination", {
					options : opts
				});
			}
			buildToolbar(this);
			refresh(this);
		});
	};
	$.fn.pagination.methods = {
		options : function(jq) {
			return $.data(jq[0], "pagination").options;
		},
		loading : function(jq) {
			return jq.each(function() {
				setLoadStatus(this, true);
			});
		},
		loaded : function(jq) {
			return jq.each(function() {
				setLoadStatus(this, false);
			});
		},
		refresh : function(jq, param) {
			return jq.each(function() {
				refresh(this, param);
			});
		},
		reload : function(jq, param) {
			return jq.each(function() {
				reload(this, param);
			});
		},
		select : function(jq, param) {
			return jq.each(function() {
				selectPage(this, param);
			});
		}
	};
	$.fn.pagination.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [ {
			total : "number",
			pageSize : "number",
			pageNumber : "number",
			links : "number"
		}, {
			loading : "boolean",
			showPageList : "boolean",
			showRefresh : "boolean"
		} ]), {
			pageList : (t.attr("pageList") ? eval(t.attr("pageList"))
					: undefined)
		});
	};
	$.fn.pagination.defaults = {
		total : 1,
		pageSize : 10,
		//before
		buttonPosition : "after",
		pageNumber : 1,
		pageList : [ 10, 20, 30, 50 ],
		loading : false,
		buttons : null,
		showPageList : true,
		showRefresh : true,
		showMsg:true,
		links : 10,
		showPageStyle:"links",
		layout : [ "list", "first", "prev", "links", "next", "last", "refresh" ],
		onSelectPage : function(pageNumber, pageSize) {
		},
		onBeforeRefresh : function(pageNumber, pageSize) {
		},
		onRefresh : function(pageNumber, pageSize) {
		},
		onChangePageSize : function(pageSize) {
		},
		message : "",
		beforePageText : "\u9875",
		afterPageText : "\u9875\u7801:{pages}",
		displayMsg : "\u663E\u793A {from} \u5230 {to} \u603B\u6570:{total} ",
		nav : {
			first : {
				iconCls : "pagination-first",
				handler : function() {
					var opts = $(this).pagination("options");
					if (opts.pageNumber > 1) {
						$(this).pagination("select", 1);
					}
				}
			},
			prev : {
				iconCls : "pagination-prev",
				handler : function() {
					var opts = $(this).pagination("options");
					if (opts.pageNumber > 1) {
						$(this).pagination("select", opts.pageNumber - 1);
					}
				}
			},
			next : {
				iconCls : "pagination-next",
				handler : function() {
					var opts = $(this).pagination("options");
					var pageNumber = Math.ceil(opts.total / opts.pageSize);
					if (opts.pageNumber < pageNumber) {
						$(this).pagination("select", opts.pageNumber + 1);
					}
				}
			},
			last : {
				iconCls : "pagination-last",
				handler : function() {
					var opts = $(this).pagination("options");
					var pageNumber = Math.ceil(opts.total / opts.pageSize);
					if (opts.pageNumber < pageNumber) {
						$(this).pagination("select", pageNumber);
					}
				}
			},
			refresh : {
				iconCls : "pagination-refresh",
				handler : function() {
					var opts = $(this).pagination("options");
					if (opts.onBeforeRefresh.call(this, opts.pageNumber, opts.pageSize) != false) {
						$(this).pagination("select", opts.pageNumber);
						opts.onRefresh.call(this, opts.pageNumber, opts.pageSize);
					}
				}
			}
		}
	};
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function validateboxAddClas(target) {
		$(target).addClass("validatebox-text");
	};
	function destroy(target) {
		var validatebox = $.data(target, "validatebox");
		validatebox.validating = false;
		if (validatebox.timer) {
			clearTimeout(validatebox.timer);
		}
		$(target).tooltip("destroy");
		$(target).unbind();
		$(target).remove();
	};
	function bindEvent(target) {
		var opts = $.data(target, "validatebox").options;
		var box = $(target);
		box.unbind(".validatebox");
		if (opts.novalidate || box.is(":disabled")) {
			return;
		}
		for ( var e in opts.events) {
			$(target).bind(e + ".validatebox", {
				target : target
			}, opts.events[e]);
		}
	};
	function focusEvent(e) {
		var target = e.data.target;
		var validatebox = $.data(target, "validatebox");
		var box = $(target);
		if ($(target).attr("readonly")) {
			return;
		}
		validatebox.validating = true;
		validatebox.value = undefined;
		(function() {
			if (validatebox.validating) {
				if (validatebox.value != box.val()) {
					validatebox.value = box.val();
					if (validatebox.timer) {
						clearTimeout(validatebox.timer);
					}
					validatebox.timer = setTimeout(function() {
						$(target).validatebox("validate");
					}, validatebox.options.delay);
				} else {
					repositionTooltip(target);
				}
				setTimeout(arguments.callee, 200);
			}
		})();
	}
	;
	function blurEvent(e) {
		var target = e.data.target;
		var validatebox = $.data(target, "validatebox");
		if (validatebox.timer) {
			clearTimeout(validatebox.timer);
			validatebox.timer = undefined;
		}
		validatebox.validating = false;
		hideTooltip(target);
	};
	function mouseenterEvent(e) {
		var target = e.data.target;
		if ($(target).hasClass("validatebox-invalid")) {
			showTooltip(target);
		}
	};
	function mouseleaveEvent(e) {
		var target = e.data.target;
		var validatebox = $.data(target, "validatebox");
		if (!validatebox.validating) {
			hideTooltip(target);
		}
	}
	;
	function showTooltip(target) {
		var validatebox = $.data(target, "validatebox");
		var opts = validatebox.options;
		$(target).tooltip($.extend({}, opts.tipOptions, {
			cls:"error-tooltip",
			content : validatebox.message,
			position : opts.tipPosition,
			deltaX : opts.deltaX
		})).tooltip("show");
		validatebox.tip = true;
	};
	function repositionTooltip(target) {
		var validatebox = $.data(target, "validatebox");
		if (validatebox && validatebox.tip) {
			$(target).tooltip("reposition");
		}
	};
	function hideTooltip(target) {
		var validatebox = $.data(target, "validatebox");
		validatebox.tip = false;
		$(target).tooltip("hide");
	};
	function validate(target) {
		var validatebox = $.data(target, "validatebox");
		var opts = validatebox.options;
		var box = $(target);
		opts.onBeforeValidate.call(target);
		var _25 = _26();
		opts.onValidate.call(target, _25);
		return _25;
		function _setMessage(msg) {
			validatebox.message = msg;
		};
		function _28(validName, rulesValue) {
			var boxValue = box.val();
			var _validName = /([a-zA-Z_]+)(.*)/.exec(validName);
			var _rulesName = opts.rules[_validName[1]];
			if (_rulesName && boxValue) {
				var _rulesValue = rulesValue || opts.validParams || eval(_validName[2]);
				if (!_rulesName["validator"].call(target, boxValue, _rulesValue)) {
					box.addClass("validatebox-invalid");
					var message = _rulesName["message"];
					if (_rulesValue) {
						for (var i = 0; i < _rulesValue.length; i++) {
							message = message.replace(new RegExp("\\{" + i + "\\}", "g"), _rulesValue[i]);
						}
					}
					_setMessage(opts.invalidMessage || message);
					if (validatebox.validating) {
						showTooltip(target);
					}
					return false;
				}
			}
			return true;
		};
		function _26() {
			box.removeClass("validatebox-invalid");
			hideTooltip(target);
			if (opts.novalidate || box.is(":disabled")) {
				return true;
			}
			if (opts.required) {
				if (box.val() == "" || box.val() == "--\u8BF7\u9009\u62E9--") {
					box.addClass("validatebox-invalid");
					_setMessage(opts.missingMessage);
					if (validatebox.validating) {
						showTooltip(target);
					}
					return false;
				}
			}
			if (opts.validType) {
				if ($.isArray(opts.validType)) {
					for (var i = 0; i < opts.validType.length; i++) {
						if (!_28(opts.validType[i])) {
							return false;
						}
					}
				} else {
					if (typeof opts.validType == "string") {
						if (!_28(opts.validType)) {
							return false;
						}
					} else {
						for ( var validName in opts.validType) {
							var _31 = opts.validType[validName];
							if (!_28(validName, _31)) {
								return false;
							}
						}
					}
				}
			}
			return true;
		};
	};
	function setValidationStatus(target, status) {
		var opts = $.data(target, "validatebox").options;
		if (status != undefined) {
			opts.novalidate = status;
		}
		if (opts.novalidate) {
			$(target).removeClass("validatebox-invalid");
			hideTooltip(target);
		}
		validate(target);
		bindEvent(target);
	};
	$.fn.validatebox = function(options, param) {
		if (typeof options == "string") {
			return $.fn.validatebox.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var validatebox = $.data(this, "validatebox");
			if (validatebox) {
				$.extend(validatebox.options, options);
			} else {
				validateboxAddClas(this);
				$.data(this, "validatebox", {
					options : $.extend({}, $.fn.validatebox.defaults, $.fn.validatebox.parseOptions(this), options)
				});
			}
			setValidationStatus(this);
			validate(this);
		});
	};
	$.fn.validatebox.methods = {
		options : function(jq) {
			return $.data(jq[0], "validatebox").options;
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		},
		validate : function(jq) {
			return jq.each(function() {
				validate(this);
			});
		},
		isValid : function(jq) {
			return validate(jq[0]);
		},
		enableValidation : function(jq) {
			return jq.each(function() {
				setValidationStatus(this, false);
			});
		},
		disableValidation : function(jq) {
			return jq.each(function() {
				setValidationStatus(this, true);
			});
		}
	};
	$.fn.validatebox.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [ "validType",
				"missingMessage", "invalidMessage", "tipPosition", {
					delay : "number",
					deltaX : "number"
				} ]), {
			required : (t.attr("required") ? true : undefined),
			novalidate : (t.attr("novalidate") != undefined ? true : undefined)
		});
	};
	$.fn.validatebox.defaults = {
		required : false,
		validType : null,
		validParams : null,
		delay : 200,
		missingMessage : "本字段为必填项.",
		invalidMessage : null,
		tipPosition : "right",
		deltaX : 0,
		novalidate : false,
		events : {
			focus : focusEvent,
			blur : blurEvent,
			mouseenter : mouseenterEvent,
			mouseleave : mouseleaveEvent,
			click : function(e) {
				var t = $(e.data.target);
				if (!t.is(":focus")) {
					t.trigger("focus");
				}
			}
		},
		tipOptions : {
			showEvent : "none",
			hideEvent : "none",
			showDelay : 0,
			hideDelay : 0,
			zIndex : "",
			onShow : function() {
//				$(this).tooltip("tip").css({
//					color : "#000",
//					borderColor : "#CC9933",
//					backgroundColor : "#FFFFCC"
//				});
			},
			onHide : function() {
				$(this).tooltip("destroy");
			}
		},
		rules : {
			email : {
				validator : function(_3a) {
					return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(_3a);
				},
				//message : "Please enter a valid email address."
				message : "\u8BF7\u8F93\u5165\u6709\u6548\u7684\u90AE\u4EF6\u5730\u5740"
			},
			url : {
				validator : function(_3b) {
					return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(_3b);
				},
				//message : "Please enter a valid URL."
				message : "\u8BF7\u8F93\u5165\u6709\u6548\u7684URL"
			},
			length : {
				validator : function(_3c, _3d) {
					var len = $.trim(_3c).length;
					return len >= _3d[0] && len <= _3d[1];
				},
				//message : "Please enter a value between {0} and {1}."
				message : "\u957F\u5EA6\u9700\u8981\u5728{0}-{1}\u4E4B\u95F4"
			},
			remote : {
				validator : function(_3e, _3f) {
					var _40 = {};
					_40[_3f[1]] = _3e;
					var _41 = $.ajax({
						url : _3f[0],
						dataType : "json",
						data : _40,
						async : false,
						cache : false,
						type : "post"
					}).responseText;
					return _41 == "true";
				},
				message : "Please fix this field."
			}
		},
		onBeforeValidate : function() {
		},
		onValidate : function(_42) {
		}
	};
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function _1(_2) {
		var _3 = $(_2);
		_3.addClass("tree");
		return _3;
	}
	;
	function _4(_5) {
		var _6 = $.data(_5, "tree").options;
		$(_5).unbind().bind("mouseover", function(e) {
			var tt = $(e.target);
			var _7 = tt.closest("div.tree-node");
			if (!_7.length) {
				return;
			}
			_7.addClass("tree-node-hover");
			if (tt.hasClass("tree-hit")) {
				if (tt.hasClass("tree-expanded")) {
					tt.addClass("tree-expanded-hover");
				} else {
					tt.addClass("tree-collapsed-hover");
				}
			}
			e.stopPropagation();
		}).bind("mouseout", function(e) {
			var tt = $(e.target);
			var _8 = tt.closest("div.tree-node");
			if (!_8.length) {
				return;
			}
			_8.removeClass("tree-node-hover");
			if (tt.hasClass("tree-hit")) {
				if (tt.hasClass("tree-expanded")) {
					tt.removeClass("tree-expanded-hover");
				} else {
					tt.removeClass("tree-collapsed-hover");
				}
			}
			e.stopPropagation();
		}).bind("click", function(e) {
			var tt = $(e.target);
			var _9 = tt.closest("div.tree-node");
			if (!_9.length) {
				return;
			}
			if (tt.hasClass("tree-hit")) {
				_81(_5, _9[0]);
				return false;
			} else {
				if (tt.hasClass("tree-checkbox")) {
					_34(_5, _9[0], !tt.hasClass("tree-checkbox1"));
					return false;
				} else {
					_db(_5, _9[0]);
					_6.onClick.call(_5, _c(_5, _9[0]));
				}
			}
			e.stopPropagation();
		}).bind("dblclick", function(e) {
			var _a = $(e.target).closest("div.tree-node");
			if (!_a.length) {
				return;
			}
			_db(_5, _a[0]);
			_6.onDblClick.call(_5, _c(_5, _a[0]));
			e.stopPropagation();
		}).bind("contextmenu", function(e) {
			var _b = $(e.target).closest("div.tree-node");
			if (!_b.length) {
				return;
			}
			_6.onContextMenu.call(_5, e, _c(_5, _b[0]));
			e.stopPropagation();
		});
	}
	;
	function _d(_e) {
		var _f = $.data(_e, "tree").options;
		_f.dnd = false;
		var _10 = $(_e).find("div.tree-node");
		_10.draggable("disable");
		_10.css("cursor", "pointer");
	}
	;
	function _11(_12) {
		var _13 = $.data(_12, "tree");
		var _14 = _13.options;
		var _15 = _13.tree;
		_13.disabledNodes = [];
		_14.dnd = true;
		_15
				.find("div.tree-node")
				.draggable(
						{
							disabled : false,
							revert : true,
							cursor : "pointer",
							proxy : function(_16) {
								var p = $(
										"<div class=\"tree-node-proxy\"></div>")
										.appendTo("body");
								p
										.html("<span class=\"tree-dnd-icon tree-dnd-no\">&nbsp;</span>"
												+ $(_16).find(".tree-title")
														.html());
								p.hide();
								return p;
							},
							deltaX : 15,
							deltaY : 15,
							onBeforeDrag : function(e) {
								if (_14.onBeforeDrag.call(_12, _c(_12, this)) == false) {
									return false;
								}
								if ($(e.target).hasClass("tree-hit")
										|| $(e.target)
												.hasClass("tree-checkbox")) {
									return false;
								}
								if (e.which != 1) {
									return false;
								}
								$(this).next("ul").find("div.tree-node")
										.droppable({
											accept : "no-accept"
										});
								var _17 = $(this).find("span.tree-indent");
								if (_17.length) {
									e.data.offsetWidth -= _17.length
											* _17.width();
								}
							},
							onStartDrag : function() {
								$(this).draggable("proxy").css({
									left : -10000,
									top : -10000
								});
								_14.onStartDrag.call(_12, _c(_12, this));
								var _18 = _c(_12, this);
								if (_18.id == undefined) {
									_18.id = "easyui_tree_node_id_temp";
									_56(_12, _18);
								}
								_13.draggingNodeId = _18.id;
							},
							onDrag : function(e) {
								var x1 = e.pageX, y1 = e.pageY, x2 = e.data.startX, y2 = e.data.startY;
								var d = Math.sqrt((x1 - x2) * (x1 - x2)
										+ (y1 - y2) * (y1 - y2));
								if (d > 3) {
									$(this).draggable("proxy").show();
								}
								this.pageY = e.pageY;
							},
							onStopDrag : function() {
								$(this).next("ul").find("div.tree-node")
										.droppable({
											accept : "div.tree-node"
										});
								for (var i = 0; i < _13.disabledNodes.length; i++) {
									$(_13.disabledNodes[i]).droppable("enable");
								}
								_13.disabledNodes = [];
								var _19 = _ce(_12, _13.draggingNodeId);
								if (_19 && _19.id == "easyui_tree_node_id_temp") {
									_19.id = "";
									_56(_12, _19);
								}
								_14.onStopDrag.call(_12, _19);
							}
						})
				.droppable(
						{
							accept : "div.tree-node",
							onDragEnter : function(e, _1a) {
								if (_14.onDragEnter.call(_12, this, _1b(_1a)) == false) {
									_1c(_1a, false);
									$(this)
											.removeClass(
													"tree-node-append tree-node-top tree-node-bottom");
									$(this).droppable("disable");
									_13.disabledNodes.push(this);
								}
							},
							onDragOver : function(e, _1d) {
								if ($(this).droppable("options").disabled) {
									return;
								}
								var _1e = _1d.pageY;
								var top = $(this).offset().top;
								var _1f = top + $(this).outerHeight();
								_1c(_1d, true);
								$(this)
										.removeClass(
												"tree-node-append tree-node-top tree-node-bottom");
								if (_1e > top + (_1f - top) / 2) {
									if (_1f - _1e < 5) {
										$(this).addClass("tree-node-bottom");
									} else {
										$(this).addClass("tree-node-append");
									}
								} else {
									if (_1e - top < 5) {
										$(this).addClass("tree-node-top");
									} else {
										$(this).addClass("tree-node-append");
									}
								}
								if (_14.onDragOver.call(_12, this, _1b(_1d)) == false) {
									_1c(_1d, false);
									$(this)
											.removeClass(
													"tree-node-append tree-node-top tree-node-bottom");
									$(this).droppable("disable");
									_13.disabledNodes.push(this);
								}
							},
							onDragLeave : function(e, _20) {
								_1c(_20, false);
								$(this)
										.removeClass(
												"tree-node-append tree-node-top tree-node-bottom");
								_14.onDragLeave.call(_12, this, _1b(_20));
							},
							onDrop : function(e, _21) {
								var _22 = this;
								var _23, _24;
								if ($(this).hasClass("tree-node-append")) {
									_23 = _25;
									_24 = "append";
								} else {
									_23 = _26;
									_24 = $(this).hasClass("tree-node-top") ? "top"
											: "bottom";
								}
								if (_14.onBeforeDrop.call(_12, _22, _1b(_21),
										_24) == false) {
									$(this)
											.removeClass(
													"tree-node-append tree-node-top tree-node-bottom");
									return;
								}
								_23(_21, _22, _24);
								$(this)
										.removeClass(
												"tree-node-append tree-node-top tree-node-bottom");
							}
						});
		function _1b(_27, pop) {
			return $(_27).closest("ul.tree").tree(pop ? "pop" : "getData", _27);
		}
		;
		function _1c(_28, _29) {
			var _2a = $(_28).draggable("proxy").find("span.tree-dnd-icon");
			_2a.removeClass("tree-dnd-yes tree-dnd-no").addClass(
					_29 ? "tree-dnd-yes" : "tree-dnd-no");
		}
		;
		function _25(_2b, _2c) {
			if (_c(_12, _2c).state == "closed") {
				_75(_12, _2c, function() {
					_2d();
				});
			} else {
				_2d();
			}
			function _2d() {
				var _2e = _1b(_2b, true);
				$(_12).tree("append", {
					parent : _2c,
					data : [ _2e ]
				});
				_14.onDrop.call(_12, _2c, _2e, "append");
			}
			;
		}
		;
		function _26(_2f, _30, _31) {
			var _32 = {};
			if (_31 == "top") {
				_32.before = _30;
			} else {
				_32.after = _30;
			}
			var _33 = _1b(_2f, true);
			_32.data = _33;
			$(_12).tree("insert", _32);
			_14.onDrop.call(_12, _30, _33, _31);
		}
		;
	}
	;
	function _34(_35, _36, _37) {
		var _38 = $.data(_35, "tree").options;
		if (!_38.checkbox) {
			return;
		}
		var _39 = _c(_35, _36);
		if (_38.onBeforeCheck.call(_35, _39, _37) == false) {
			return;
		}
		var _3a = $(_36);
		var ck = _3a.find(".tree-checkbox");
		ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
		if (_37) {
			ck.addClass("tree-checkbox1");
		} else {
			ck.addClass("tree-checkbox0");
		}
		if (_38.cascadeCheck) {
			_3b(_3a);
			_3c(_3a);
		}
		_38.onCheck.call(_35, _39, _37);
		function _3c(_3d) {
			var _3e = _3d.next().find(".tree-checkbox");
			_3e.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
			if (_3d.find(".tree-checkbox").hasClass("tree-checkbox1")) {
				_3e.addClass("tree-checkbox1");
			} else {
				_3e.addClass("tree-checkbox0");
			}
		}
		;
		function _3b(_3f) {
			var _40 = _8c(_35, _3f[0]);
			if (_40) {
				var ck = $(_40.target).find(".tree-checkbox");
				ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
				if (_41(_3f)) {
					ck.addClass("tree-checkbox1");
				} else {
					if (_42(_3f)) {
						ck.addClass("tree-checkbox0");
					} else {
						ck.addClass("tree-checkbox2");
					}
				}
				_3b($(_40.target));
			}
			function _41(n) {
				var ck = n.find(".tree-checkbox");
				if (ck.hasClass("tree-checkbox0")
						|| ck.hasClass("tree-checkbox2")) {
					return false;
				}
				var b = true;
				n.parent().siblings().each(
						function() {
							if (!$(this).children("div.tree-node").children(
									".tree-checkbox")
									.hasClass("tree-checkbox1")) {
								b = false;
							}
						});
				return b;
			}
			;
			function _42(n) {
				var ck = n.find(".tree-checkbox");
				if (ck.hasClass("tree-checkbox1")
						|| ck.hasClass("tree-checkbox2")) {
					return false;
				}
				var b = true;
				n.parent().siblings().each(
						function() {
							if (!$(this).children("div.tree-node").children(
									".tree-checkbox")
									.hasClass("tree-checkbox0")) {
								b = false;
							}
						});
				return b;
			}
			;
		}
		;
	}
	;
	function _43(_44, _45) {
		var _46 = $.data(_44, "tree").options;
		if (!_46.checkbox) {
			return;
		}
		var _47 = $(_45);
		if (_48(_44, _45)) {
			var ck = _47.find(".tree-checkbox");
			if (ck.length) {
				if (ck.hasClass("tree-checkbox1")) {
					_34(_44, _45, true);
				} else {
					_34(_44, _45, false);
				}
			} else {
				if (_46.onlyLeafCheck) {
					$("<span class=\"tree-checkbox tree-checkbox0\"></span>")
							.insertBefore(_47.find(".tree-title"));
				}
			}
		} else {
			var ck = _47.find(".tree-checkbox");
			if (_46.onlyLeafCheck) {
				ck.remove();
			} else {
				if (ck.hasClass("tree-checkbox1")) {
					_34(_44, _45, true);
				} else {
					if (ck.hasClass("tree-checkbox2")) {
						var _49 = true;
						var _4a = true;
						var _4b = _4c(_44, _45);
						for (var i = 0; i < _4b.length; i++) {
							if (_4b[i].checked) {
								_4a = false;
							} else {
								_49 = false;
							}
						}
						if (_49) {
							_34(_44, _45, true);
						}
						if (_4a) {
							_34(_44, _45, false);
						}
					}
				}
			}
		}
	}
	;
	function _4d(_4e, ul, _4f, _50) {
		var _51 = $.data(_4e, "tree");
		var _52 = _51.options;
		var _53 = $(ul).prevAll("div.tree-node:first");
		_4f = _52.loadFilter.call(_4e, _4f, _53[0]);
		var _54 = _55(_4e, "domId", _53.attr("id"));
		if (!_50) {
			_54 ? _54.children = _4f : _51.data = _4f;
			$(ul).empty();
		} else {
			if (_54) {
				_54.children && _54.children.length>0  ? _54.children = _54.children.concat(_4f): _54.children = _4f;
			} else {
				_51.data = _51.data.concat(_4f);
			}
		}
		_52.view.render.call(_52.view, _4e, ul, _4f);
		if (_52.dnd) {
			_11(_4e);
		}
		if (_54) {
			_56(_4e, _54);
		}
		var _57 = [];
		var _58 = [];
		for (var i = 0; i < _4f.length; i++) {
			var _59 = _4f[i];
			if (!_59.checked) {
				_57.push(_59);
			}
		}
		_5a(_4f, function(_5b) {
			if (_5b.checked) {
				_58.push(_5b);
			}
		});
		var _5c = _52.onCheck;
		_52.onCheck = function() {
		};
		if (_57.length) {
			_34(_4e, $("#" + _57[0].domId)[0], false);
		}
		for (var i = 0; i < _58.length; i++) {
			_34(_4e, $("#" + _58[i].domId)[0], true);
		}
		_52.onCheck = _5c;
		setTimeout(function() {
			_5d(_4e, _4e);
		}, 0);
		_52.onLoadSuccess.call(_4e, _54, _4f);
	}
	;
	function _5d(_5e, ul, _5f) {
		var _60 = $.data(_5e, "tree").options;
		if (_60.lines) {
			$(_5e).addClass("tree-lines");
		} else {
			$(_5e).removeClass("tree-lines");
			return;
		}
		if (!_5f) {
			_5f = true;
			$(_5e).find("span.tree-indent").removeClass(
					"tree-line tree-join tree-joinbottom");
			$(_5e).find("div.tree-node").removeClass(
					"tree-node-last tree-root-first tree-root-one");
			var _61 = $(_5e).tree("getRoots");
			if (_61.length > 1) {
				$(_61[0].target).addClass("tree-root-first");
			} else {
				if (_61.length == 1) {
					$(_61[0].target).addClass("tree-root-one");
				}
			}
		}
		$(ul).children("li").each(function() {
			var _62 = $(this).children("div.tree-node");
			var ul = _62.next("ul");
			if (ul.length) {
				if ($(this).next().length) {
					_63(_62);
				}
				_5d(_5e, ul, _5f);
			} else {
				_64(_62);
			}
		});
		var _65 = $(ul).children("li:last").children("div.tree-node").addClass(
				"tree-node-last");
		_65.children("span.tree-join").removeClass("tree-join").addClass(
				"tree-joinbottom");
		function _64(_66, _67) {
			var _68 = _66.find("span.tree-icon");
			_68.prev("span.tree-indent").addClass("tree-join");
		}
		;
		function _63(_69) {
			var _6a = _69.find("span.tree-indent, span.tree-hit").length;
			_69.next().find("div.tree-node").each(
					function() {
						$(this).children("span:eq(" + (_6a - 1) + ")")
								.addClass("tree-line");
					});
		}
		;
	}
	;
	function _6b(_6c, ul, _6d, _6e) {
		var _6f = $.data(_6c, "tree").options;
		_6d = $.extend({}, _6f.queryParams, _6d || {});
		var _70 = null;
		if (_6c != ul) {
			var _71 = $(ul).prev();
			_70 = _c(_6c, _71[0]);
		}
		if (_6f.onBeforeLoad.call(_6c, _70, _6d) == false) {
			return;
		}
		var _72 = $(ul).prev().children("span.tree-folder");
		_72.addClass("tree-loading");
		var _73 = _6f.loader.call(_6c, _6d, function(_74) {
			_72.removeClass("tree-loading");
			_4d(_6c, ul, _74);
			if (_6e) {
				_6e();
			}
		}, function() {
			_72.removeClass("tree-loading");
			_6f.onLoadError.apply(_6c, arguments);
			if (_6e) {
				_6e();
			}
		});
		if (_73 == false) {
			_72.removeClass("tree-loading");
		}
	}
	;
	function _75(_76, _77, _78) {
		var _79 = $.data(_76, "tree").options;
		var hit = $(_77).children("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-expanded")) {
			return;
		}
		var _7a = _c(_76, _77);
		if (_79.onBeforeExpand.call(_76, _7a) == false) {
			return;
		}
		hit.removeClass("tree-collapsed tree-collapsed-hover").addClass(
				"tree-expanded");
		hit.next().addClass("tree-folder-open");
		var ul = $(_77).next();
		if (ul.length) {
			if (_79.animate) {
				ul.slideDown("normal", function() {
					_7a.state = "open";
					_79.onExpand.call(_76, _7a);
					if (_78) {
						_78();
					}
				});
			} else {
				ul.css("display", "block");
				_7a.state = "open";
				_79.onExpand.call(_76, _7a);
				if (_78) {
					_78();
				}
			}
		} else {
			var _7b = $("<ul style=\"display:none\"></ul>").insertAfter(_77);
			_6b(_76, _7b[0], {
				id : _7a.id
			}, function() {
				if (_7b.is(":empty")) {
					_7b.remove();
				}
				if (_79.animate) {
					_7b.slideDown("normal", function() {
						_7a.state = "open";
						_79.onExpand.call(_76, _7a);
						if (_78) {
							_78();
						}
					});
				} else {
					_7b.css("display", "block");
					_7a.state = "open";
					_79.onExpand.call(_76, _7a);
					if (_78) {
						_78();
					}
				}
			});
		}
	}
	;
	function _7c(_7d, _7e) {
		var _7f = $.data(_7d, "tree").options;
		var hit = $(_7e).children("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-collapsed")) {
			return;
		}
		var _80 = _c(_7d, _7e);
		if (_7f.onBeforeCollapse.call(_7d, _80) == false) {
			return;
		}
		hit.removeClass("tree-expanded tree-expanded-hover").addClass(
				"tree-collapsed");
		hit.next().removeClass("tree-folder-open");
		var ul = $(_7e).next();
		if (_7f.animate) {
			ul.slideUp("normal", function() {
				_80.state = "closed";
				_7f.onCollapse.call(_7d, _80);
			});
		} else {
			ul.css("display", "none");
			_80.state = "closed";
			_7f.onCollapse.call(_7d, _80);
		}
	}
	;
	function _81(_82, _83) {
		var hit = $(_83).children("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-expanded")) {
			_7c(_82, _83);
		} else {
			_75(_82, _83);
		}
	}
	;
	function _84(_85, _86) {
		var _87 = _4c(_85, _86);
		if (_86) {
			_87.unshift(_c(_85, _86));
		}
		for (var i = 0; i < _87.length; i++) {
			_75(_85, _87[i].target);
		}
	}
	;
	function _88(_89, _8a) {
		var _8b = [];
		var p = _8c(_89, _8a);
		while (p) {
			_8b.unshift(p);
			p = _8c(_89, p.target);
		}
		for (var i = 0; i < _8b.length; i++) {
			_75(_89, _8b[i].target);
		}
	}
	;
	function _8d(_8e, _8f) {
		var c = $(_8e).parent();
		while (c[0].tagName != "BODY" && c.css("overflow-y") != "auto") {
			c = c.parent();
		}
		var n = $(_8f);
		var _90 = n.offset().top;
		if (c[0].tagName != "BODY") {
			var _91 = c.offset().top;
			if (_90 < _91) {
				c.scrollTop(c.scrollTop() + _90 - _91);
			} else {
				if (_90 + n.outerHeight() > _91 + c.outerHeight() - 18) {
					c.scrollTop(c.scrollTop() + _90 + n.outerHeight() - _91
							- c.outerHeight() + 18);
				}
			}
		} else {
			c.scrollTop(_90);
		}
	}
	;
	function _92(_93, _94) {
		var _95 = _4c(_93, _94);
		if (_94) {
			_95.unshift(_c(_93, _94));
		}
		for (var i = 0; i < _95.length; i++) {
			_7c(_93, _95[i].target);
		}
	}
	;
	function _96(_97, _98) {
		var _99 = $(_98.parent);
		var _9a = _98.data;
		if (!_9a) {
			return;
		}
		_9a = $.isArray(_9a) ? _9a : [ _9a ];
		if (!_9a.length) {
			return;
		}
		var ul;
		if (_99.length == 0) {
			ul = $(_97);
		} else {
			if (_48(_97, _99[0])) {
				var _9b = _99.find("span.tree-icon");
				_9b.removeClass("tree-file").addClass(
						"tree-folder tree-folder-open");
				var hit = $("<span class=\"tree-hit tree-expanded\"></span>")
						.insertBefore(_9b);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
			ul = _99.next();
			if (!ul.length) {
				ul = $("<ul></ul>").insertAfter(_99);
			}
		}
		_4d(_97, ul[0], _9a, true);
		_43(_97, ul.prev());
	}
	;
	function _9c(_9d, _9e) {
		var ref = _9e.before || _9e.after;
		var _9f = _8c(_9d, ref);
		var _a0 = _9e.data;
		if (!_a0) {
			return;
		}
		_a0 = $.isArray(_a0) ? _a0 : [ _a0 ];
		if (!_a0.length) {
			return;
		}
		_96(_9d, {
			parent : (_9f ? _9f.target : null),
			data : _a0
		});
		var _a1 = _9f ? _9f.children : $(_9d).tree("getRoots");
		for (var i = 0; i < _a1.length; i++) {
			if (_a1[i].domId == $(ref).attr("id")) {
				for (var j = _a0.length - 1; j >= 0; j--) {
					_a1.splice((_9e.before ? i : (i + 1)), 0, _a0[j]);
				}
				_a1.splice(_a1.length - _a0.length, _a0.length);
				break;
			}
		}
		var li = $();
		for (var i = 0; i < _a0.length; i++) {
			li = li.add($("#" + _a0[i].domId).parent());
		}
		if (_9e.before) {
			li.insertBefore($(ref).parent());
		} else {
			li.insertAfter($(ref).parent());
		}
	}
	;
	function _a2(_a3, _a4) {
		var _a5 = del(_a4);
		$(_a4).parent().remove();
		if (_a5) {
			if (!_a5.children || !_a5.children.length) {
				var _a6 = $(_a5.target);
				_a6.find(".tree-icon").removeClass("tree-folder").addClass(
						"tree-file");
				_a6.find(".tree-hit").remove();
				$("<span class=\"tree-indent\"></span>").prependTo(_a6);
				_a6.next().remove();
			}
			_56(_a3, _a5);
			_43(_a3, _a5.target);
		}
		_5d(_a3, _a3);
		function del(_a7) {
			var id = $(_a7).attr("id");
			var _a8 = _8c(_a3, _a7);
			var cc = _a8 ? _a8.children : $.data(_a3, "tree").data;
			for (var i = 0; i < cc.length; i++) {
				if (cc[i].domId == id) {
					cc.splice(i, 1);
					break;
				}
			}
			return _a8;
		}
		;
	}
	;
	function _56(_a9, _aa) {
		var _ab = $.data(_a9, "tree").options;
		var _ac = $(_aa.target);
		var _ad = _c(_a9, _aa.target);
		var _ae = _ad.checked;
		if (_ad.iconCls) {
			_ac.find(".tree-icon").removeClass(_ad.iconCls);
		}
		$.extend(_ad, _aa);
		_ac.find(".tree-title").html(_ab.formatter.call(_a9, _ad));
		if (_ad.iconCls) {
			_ac.find(".tree-icon").addClass(_ad.iconCls);
		}
		if (_ae != _ad.checked) {
			_34(_a9, _aa.target, _ad.checked);
		}
	}
	;
	function _af(_b0, _b1) {
		if (_b1) {
			var p = _8c(_b0, _b1);
			while (p) {
				_b1 = p.target;
				p = _8c(_b0, _b1);
			}
			return _c(_b0, _b1);
		} else {
			var _b2 = _b3(_b0);
			return _b2.length ? _b2[0] : null;
		}
	}
	;
	function _b3(_b4) {
		var _b5 = $.data(_b4, "tree").data;
		for (var i = 0; i < _b5.length; i++) {
			_b6(_b5[i]);
		}
		return _b5;
	}
	;
	function _4c(_b7, _b8) {
		var _b9 = [];
		var n = _c(_b7, _b8);
		var _ba = n ? n.children : $.data(_b7, "tree").data;
		_5a(_ba, function(_bb) {
			_b9.push(_b6(_bb));
		});
		return _b9;
	}
	;
	function _8c(_bc, _bd) {
		var p = $(_bd).closest("ul").prevAll("div.tree-node:first");
		return _c(_bc, p[0]);
	}
	;
	function _be(_bf, _c0) {
		_c0 = _c0 || "checked";
		if (!$.isArray(_c0)) {
			_c0 = [ _c0 ];
		}
		var _c1 = [];
		for (var i = 0; i < _c0.length; i++) {
			var s = _c0[i];
			if (s == "checked") {
				_c1.push("span.tree-checkbox1");
			} else {
				if (s == "unchecked") {
					_c1.push("span.tree-checkbox0");
				} else {
					if (s == "indeterminate") {
						_c1.push("span.tree-checkbox2");
					}
				}
			}
		}
		var _c2 = [];
		$(_bf).find(_c1.join(",")).each(function() {
			var _c3 = $(this).parent();
			_c2.push(_c(_bf, _c3[0]));
		});
		return _c2;
	}
	;
	function _c4(_c5) {
		var _c6 = $(_c5).find("div.tree-node-selected");
		return _c6.length ? _c(_c5, _c6[0]) : null;
	}
	;
	function _c7(_c8, _c9) {
		var _ca = _c(_c8, _c9);
		if (_ca && _ca.children) {
			_5a(_ca.children, function(_cb) {
				_b6(_cb);
			});
		}
		return _ca;
	}
	;
	function _c(_cc, _cd) {
		return _55(_cc, "domId", $(_cd).attr("id"));
	}
	;
	function _ce(_cf, id) {
		return _55(_cf, "id", id);
	}
	;
	function _55(_d0, _d1, _d2) {
		var _d3 = $.data(_d0, "tree").data;
		var _d4 = null;
		_5a(_d3, function(_d5) {
			if (_d5[_d1] == _d2) {
				_d4 = _b6(_d5);
				return false;
			}
		});
		return _d4;
	}
	;
	function _b6(_d6) {
		var d = $("#" + _d6.domId);
		_d6.target = d[0];
		_d6.checked = d.find(".tree-checkbox").hasClass("tree-checkbox1");
		return _d6;
	}
	;
	function _5a(_d7, _d8) {
		var _d9 = [];
		for (var i = 0; i < _d7.length; i++) {
			_d9.push(_d7[i]);
		}
		while (_d9.length) {
			var _da = _d9.shift();
			if (_d8(_da) == false) {
				return;
			}
			if (_da.children) {
				for (var i = _da.children.length - 1; i >= 0; i--) {
					_d9.unshift(_da.children[i]);
				}
			}
		}
	}
	;
	function _db(_dc, _dd) {
		var _de = $.data(_dc, "tree").options;
		var _df = _c(_dc, _dd);
		if (_de.onBeforeSelect.call(_dc, _df) == false) {
			return;
		}
		$(_dc).find("div.tree-node-selected").removeClass("tree-node-selected");
		$(_dd).addClass("tree-node-selected");
		_de.onSelect.call(_dc, _df);
	}
	;
	function _48(_e0, _e1) {
		return $(_e1).children("span.tree-hit").length == 0;
	}
	;
	function _e2(_e3, _e4) {
		var _e5 = $.data(_e3, "tree").options;
		var _e6 = _c(_e3, _e4);
		if (_e5.onBeforeEdit.call(_e3, _e6) == false) {
			return;
		}
		$(_e4).css("position", "relative");
		var nt = $(_e4).find(".tree-title");
		var _e7 = nt.outerWidth();
		nt.empty();
		var _e8 = $("<input class=\"tree-editor\">").appendTo(nt);
		_e8.val(_e6.name).focus();
		_e8.width(_e7 + 20);
		_e8.height(document.compatMode == "CSS1Compat" ? (18 - (_e8.outerHeight() - _e8.height())) : 18);
		_e8.bind("click", function(e) {
			return false;
		}).bind("mousedown", function(e) {
			e.stopPropagation();
		}).bind("mousemove", function(e) {
			e.stopPropagation();
		}).bind("keydown", function(e) {
			if (e.keyCode == 13) {
				_e9(_e3, _e4);
				return false;
			} else {
				if (e.keyCode == 27) {
					_ef(_e3, _e4);
					return false;
				}
			}
		}).bind("blur", function(e) {
			e.stopPropagation();
			_e9(_e3, _e4);
		});
	};
	function _e9(_ea, _eb) {
		var _ec = $.data(_ea, "tree").options;
		$(_eb).css("position", "");
		var _ed = $(_eb).find("input.tree-editor");
		var val = _ed.val();
		_ed.remove();
		var _ee = _c(_ea, _eb);
		_ee.name = val;
		_56(_ea, _ee);
		_ec.onAfterEdit.call(_ea, _ee);
	};
	function _ef(_f0, _f1) {
		var _f2 = $.data(_f0, "tree").options;
		$(_f1).css("position", "");
		$(_f1).find("input.tree-editor").remove();
		var _f3 = _c(_f0, _f1);
		_56(_f0, _f3);
		_f2.onCancelEdit.call(_f0, _f3);
	};
	$.fn.tree = function(_f4, _f5) {
		if (typeof _f4 == "string") {
			return $.fn.tree.methods[_f4](this, _f5);
		}
		var _f4 = _f4 || {};
		return this.each(function() {
			var _f6 = $.data(this, "tree");
			var _f7;
			if (_f6) {
				_f7 = $.extend(_f6.options, _f4);
				_f6.options = _f7;
			} else {
				_f7 = $.extend({}, $.fn.tree.defaults, $.fn.tree.parseOptions(this), _f4);
				$.data(this, "tree", {
					options : _f7,
					tree : _1(this),
					data : []
				});
				var _f8 = $.fn.tree.parseData(this);
				if (_f8.length) {
					_4d(this, this, _f8);
				}
			}
			_4(this);
			if (_f7.data) {
				_4d(this, this, $.extend(true, [], _f7.data));
			}
			_6b(this, this);
		});
	};
	$.fn.tree.methods = {
		options : function(jq) {
			return $.data(jq[0], "tree").options;
		},
		loadData : function(jq, _f9) {
			return jq.each(function() {
				_4d(this, this, _f9);
			});
		},
		getNode : function(jq, _fa) {
			return _c(jq[0], _fa);
		},
		getData : function(jq, _fb) {
			return _c7(jq[0], _fb);
		},
		reload : function(jq, _fc) {
			return jq.each(function() {
				if (_fc) {
					var _fd = $(_fc);
					var hit = _fd.children("span.tree-hit");
					hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
					_fd.next().remove();
					_75(this, _fc);
				} else {
					$(this).empty();
					_6b(this, this);
				}
			});
		},
		getRoot : function(jq, _fe) {
			return _af(jq[0], _fe);
		},
		getRoots : function(jq) {
			return _b3(jq[0]);
		},
		getParent : function(jq, _ff) {
			return _8c(jq[0], _ff);
		},
		getChildren : function(jq, _100) {
			return _4c(jq[0], _100);
		},
		getChecked : function(jq, _101) {
			return _be(jq[0], _101);
		},
		getSelected : function(jq) {
			return _c4(jq[0]);
		},
		isLeaf : function(jq, _102) {
			return _48(jq[0], _102);
		},
		find : function(jq, id) {
			return _ce(jq[0], id);
		},
		select : function(jq, _103) {
			return jq.each(function() {
				_db(this, _103);
			});
		},
		check : function(jq, _104) {
			return jq.each(function() {
				_34(this, _104, true);
			});
		},
		uncheck : function(jq, _105) {
			return jq.each(function() {
				_34(this, _105, false);
			});
		},
		collapse : function(jq, _106) {
			return jq.each(function() {
				_7c(this, _106);
			});
		},
		expand : function(jq, _107) {
			return jq.each(function() {
				_75(this, _107);
			});
		},
		collapseAll : function(jq, _108) {
			return jq.each(function() {
				_92(this, _108);
			});
		},
		expandAll : function(jq, _109) {
			return jq.each(function() {
				_84(this, _109);
			});
		},
		expandTo : function(jq, _10a) {
			return jq.each(function() {
				_88(this, _10a);
			});
		},
		scrollTo : function(jq, _10b) {
			return jq.each(function() {
				_8d(this, _10b);
			});
		},
		toggle : function(jq, _10c) {
			return jq.each(function() {
				_81(this, _10c);
			});
		},
		append : function(jq, _10d) {
			return jq.each(function() {
				_96(this, _10d);
			});
		},
		insert : function(jq, _10e) {
			return jq.each(function() {
				_9c(this, _10e);
			});
		},
		remove : function(jq, _10f) {
			return jq.each(function() {
				_a2(this, _10f);
			});
		},
		pop : function(jq, _110) {
			var node = jq.tree("getData", _110);
			jq.tree("remove", _110);
			return node;
		},
		update : function(jq, _111) {
			return jq.each(function() {
				_56(this, _111);
			});
		},
		enableDnd : function(jq) {
			return jq.each(function() {
				_11(this);
			});
		},
		disableDnd : function(jq) {
			return jq.each(function() {
				_d(this);
			});
		},
		beginEdit : function(jq, _112) {
			return jq.each(function() {
				_e2(this, _112);
			});
		},
		endEdit : function(jq, _113) {
			return jq.each(function() {
				_e9(this, _113);
			});
		},
		cancelEdit : function(jq, _114) {
			return jq.each(function() {
				_ef(this, _114);
			});
		}
	};
	$.fn.tree.parseOptions = function(_115) {
		var t = $(_115);
		return $.extend({}, $.parser.parseOptions(_115, [ "url", "method", {
			checkbox : "boolean",
			cascadeCheck : "boolean",
			onlyLeafCheck : "boolean"
		}, {
			animate : "boolean",
			lines : "boolean",
			dnd : "boolean"
		} ]));
	};
	$.fn.tree.parseData = function(_116) {
		var data = [];
		_117(data, $(_116));
		return data;
		function _117(aa, tree) {
			tree.children("li").each(
					function() {
						var node = $(this);
						var item = $.extend({}, $.parser.parseOptions(this, ["id", "iconCls", "state" ]), {
							checked : (node.attr("checked") ? true : undefined)
						});
						item.name = node.children("span").html();
						if (!item.name) {
							item.name = node.html();
						}
						var _118 = node.children("ul");
						if (_118.length) {
							item.children = [];
							_117(item.children, _118);
						}
						aa.push(item);
					});
		}
		;
	};
	var _119 = 1;
	var _11a = {
		render : function(_11b, ul, data) {
			var opts = $.data(_11b, "tree").options;
			var _11c = $(ul).prev("div.tree-node").find("span.tree-indent, span.tree-hit").length;
			var cc = _11d(_11c, data);
			$(ul).append(cc.join(""));
			function _11d(_11e, _11f) {
				var cc = [];
				for (var i = 0; i < _11f.length; i++) {
					var item = _11f[i];
					if (item.state != "open" && item.state != "closed") {
						item.state = "open";
					}
					item.domId = "_easyui_tree_" + _119++;
					cc.push("<li>");
					cc.push("<div id=\"" + item.domId
							+ "\" class=\"tree-node\">");
					for (var j = 0; j < _11e; j++) {
						cc.push("<span class=\"tree-indent\"></span>");
					}
					var _120 = false;
					if (item.state == "closed") {
						cc.push("<span class=\"tree-hit tree-collapsed\"></span>");
						cc.push("<span class=\"tree-icon tree-folder " + (item.iconCls ? item.iconCls : "") + "\"></span>");
					} else {
						if (item.children && item.children.length) {
							cc.push("<span class=\"tree-hit tree-expanded\"></span>");
							cc.push("<span class=\"tree-icon tree-folder tree-folder-open " + (item.iconCls ? item.iconCls : "") + "\"></span>");
						} else {
							cc.push("<span class=\"tree-indent\"></span>");
							cc.push("<span class=\"tree-icon tree-file " + (item.iconCls ? item.iconCls : "") + "\"></span>");
							_120 = true;
						}
					}
					if (opts.checkbox) {
						if ((!opts.onlyLeafCheck) || _120) {
							cc.push("<span class=\"tree-checkbox tree-checkbox0\"></span>");
						}
					}
					cc.push("<span class=\"tree-title\">"
							+ opts.formatter.call(_11b, item) + "</span>");
					cc.push("</div>");
					if (item.children && item.children.length) {
						var tmp = _11d(_11e + 1, item.children);
						cc.push("<ul style=\"display:" + (item.state == "closed" ? "none" : "block") + "\">");
						cc = cc.concat(tmp);
						cc.push("</ul>");
					}
					cc.push("</li>");
				}
				return cc;
			}
			;
		}
	};
	$.fn.tree.defaults = {
		url : null,
		method : "post",
		animate : false,
		checkbox : false,
		cascadeCheck : true,
		onlyLeafCheck : false,
		lines : false,
		dnd : false,
		data : null,
		queryParams : {},
		formatter : function(node) {
			return node.name;
		},
		loader : function(_121, _122, _123) {
			var opts = $(this).tree("options");
			if (!opts.url) {
				return false;
			}
			$.ajax({
				type : opts.method,
				url : opts.url,
				data : _121,
				dataType : "json",
				success : function(data) {
					_122(data);
				},
				error : function() {
					_123.apply(this, arguments);
				}
			});
		},
		loadFilter : function(data, _124) {
			return data;
		},
		view : _11a,
		onBeforeLoad : function(node, _125) {
		},
		onLoadSuccess : function(node, data) {
		},
		onLoadError : function() {
		},
		onClick : function(node) {
		},
		onDblClick : function(node) {
		},
		onBeforeExpand : function(node) {
		},
		onExpand : function(node) {
		},
		onBeforeCollapse : function(node) {
		},
		onCollapse : function(node) {
		},
		onBeforeCheck : function(node, _126) {
		},
		onCheck : function(node, _127) {
		},
		onBeforeSelect : function(node) {
		},
		onSelect : function(node) {
		},
		onContextMenu : function(e, node) {
		},
		onBeforeDrag : function(node) {
		},
		onStartDrag : function(node) {
		},
		onStopDrag : function(node) {
		},
		onDragEnter : function(_128, _129) {
		},
		onDragOver : function(_12a, _12b) {
		},
		onDragLeave : function(_12c, _12d) {
		},
		onBeforeDrop : function(_12e, _12f, _130) {
		},
		onDrop : function(_131, _132, _133) {
		},
		onBeforeEdit : function(node) {
		},
		onAfterEdit : function(node) {
		},
		onCancelEdit : function(node) {
		}
	};
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	
	function checkChar(target,charNum){
		var textbox = $.data(target, "textbox"),opts = textbox.options,regChars = opts.regChars;
		for(var i=0;regChars && i<regChars.length;i++){
			if(regChars[i].charCodeAt()==charNum){
				return true;
			}
		}
		return false;
	}
	//此方法只在此js的setValue方法中调用，格式化输入的值。
	function parser(opt,s) {
		if(s && typeof s == 'string'){
			var sarray = s.split("");
			var ns=[];
			for(var sr=0;sr<sarray.length;sr++){
				if(!checkChar(opt,sarray[sr].charCodeAt())){
					ns.push(sarray[sr]);
				}
			}
			return ns.join("");
		}
		return s;
	}
	
	function createTextbox(target) {
		$(target).addClass("textbox-f").hide();
		var textboxWrap = $("<span class=\"textbox\">"
		+ "<input class=\"textbox-text\" autocomplete=\"off\">"
		+ "<span class=\"textbox-addon\"><span class=\"textbox-icon\"></span></span>"
		+ "<input type=\"hidden\" class=\"textbox-value\">"
		+ "</span>").insertAfter(target);
		
		var name = $(target).attr("name");
		if (name) {
			textboxWrap.find("input.textbox-value").attr("name", name);
			$(target).removeAttr("name").attr("textboxName", name);
		}
		textboxWrap.bind("_resize", function(e, _bool) {
			if ($(this).hasClass("easyui-fluid") || _bool) {
				resize(target);
			}
			return false;
		}).bind("click",function(){
			var textbox = $.data(target, "textbox");
			var opts = textbox.options;
			opts.onClick.call(target);
		});
		return textboxWrap;
	};
	function initTextbox(target) {
		var textbox = $.data(target, "textbox");
		var opts = textbox.options;
		var tb = textbox.textbox;
		tb.find(".textbox-text").remove();
		if (opts.multiline) {
			$("<textarea class=\"textbox-text\" autocomplete=\"off\"></textarea>").prependTo(tb);
		} else {
			$("<input type=\"" + opts.type + "\" class=\"textbox-text\" autocomplete=\"off\">").prependTo(tb);
		}
		tb.find(".textbox-addon").remove();
		var bb = opts.icons ? $.extend(true, [], opts.icons) : [];
		if (opts.iconCls) {
			bb.push({
				iconCls : opts.iconCls,
				disabled : true
			});
		}
		if (bb.length) {
			var bc = $("<span class=\"textbox-addon\"></span>").prependTo(tb);
			bc.addClass("textbox-addon-" + opts.iconAlign);
			for (var i = 0; i < bb.length; i++) {
				bc.append("<a href=\"javascript:void(0)\" class=\"textbox-icon " + bb[i].iconCls + "\" icon-index=\"" + i + "\"></a>");
			}
		}
		tb.find(".textbox-button").remove();
		if (opts.buttonText || opts.buttonIcon) {
			var _b = $("<a href=\"javascript:void(0)\" class=\"textbox-button\"></a>").prependTo(tb);
			_b.addClass("textbox-button-" + opts.buttonAlign).linkbutton({
				text : opts.buttonText,
				iconCls : opts.buttonIcon,
				onClick : function() {
					opts.onClickButton.call(target);
				}
			});
		}
		setDisplayState(target, opts.disabled);
		readonly(target, opts.readonly);
	};
	function destroy(target) {
		var tb = $.data(target, "textbox").textbox;
		tb.find(".textbox-text").validatebox("destroy");
		tb.remove();
		$(target).remove();
	};
	function resize(target, width) {
		var textbox = $.data(target, "textbox");
		var opts = textbox.options;
		var tb = textbox.textbox;
		var parent = tb.parent();
		if (width) {
			opts.width = width;
		}
		if (isNaN(parseInt(opts.width))) {
			var c = $(target).clone();
			c.css("visibility", "hidden");
			c.insertAfter(target);
			opts.width = c.outerWidth();
			c.remove();
		}
		tb.appendTo("body");
		var text = tb.find(".textbox-text");
		var btn = tb.find(".textbox-button");
		var addon = tb.find(".textbox-addon");
		var icon = addon.find(".textbox-icon");
		tb._size(opts, parent);
		btn.linkbutton("resize", {
			height : tb.height()
		});
		btn.css({
			left : (opts.buttonAlign == "left" ? 0 : ""),
			right : (opts.buttonAlign == "right" ? 0 : "")
		});
		addon.css({
			left : (opts.iconAlign == "left" ? (opts.buttonAlign == "left" ? btn._outerWidth() : 0) : ""),
			right : (opts.iconAlign == "right" ? (opts.buttonAlign == "right" ? btn._outerWidth() : 0) : "")
		});
		icon.css({
			width : opts.iconWidth + "px",
			height : tb.height() + "px"
		});
		text.css({
			paddingLeft : (target.style.paddingLeft || ""),
			paddingRight : (target.style.paddingRight || ""),
			marginLeft : getPosition("left"),
			marginRight : getPosition("right")
		});
		if (opts.multiline) {
			text.css({
				paddingTop : (target.style.paddingTop || ""),
				paddingBottom : (target.style.paddingBottom || "")
			});
			text._outerHeight(tb.height());
		} else {
			var textHeight = Math.floor((tb.height() - text.height()) / 2);
			text.css({
				paddingTop : textHeight + "px",
				paddingBottom : textHeight + "px"
			});
		}
		text._outerWidth(tb.width() - icon.length * opts.iconWidth
				- btn._outerWidth());
		tb.insertAfter(target);
		opts.onResize.call(target, opts.width, opts.height);
		function getPosition(position) {
			return (opts.iconAlign == position ? addon._outerWidth() : 0) + (opts.buttonAlign == position ? btn._outerWidth() : 0);
		};
	};
	function validatebox(target) {
		var opts = $(target).textbox("options");
		var tb = $(target).textbox("textbox");
		tb.validatebox($.extend({}, opts, {
			deltaX : $(target).textbox("getTipX"),
			onBeforeValidate : function() {
				var box = $(this);
				if (!box.is(":focus")) {
					opts.oldInputValue = box.val();
					box.val(opts.value);
				}
			},
			onValidate : function(_1f) {
				var box = $(this);
				if (opts.oldInputValue != undefined) {
					box.val(opts.oldInputValue);
					opts.oldInputValue = undefined;
				}
				var tb = box.parent();
				if (_1f) {
					tb.removeClass("textbox-invalid");
				} else {
					tb.addClass("textbox-invalid");
				}
			}
		}));
	};
	function bindEvent(target) {
		var textbox = $.data(target, "textbox");
		var opts = textbox.options;
		var tb = textbox.textbox;
		var tbText = tb.find(".textbox-text");
		tbText.attr("placeholder", opts.prompt);
		tbText.unbind(".textbox");
		if (!opts.disabled && !opts.readonly) {
			tbText.bind("blur.textbox", function(e) {
				if (!tb.hasClass("textbox-focused")) {
					return;
				}
				opts.value = $(this).val();
				if (opts.value == "") {
					$(this).val(opts.prompt).addClass("textbox-prompt");
				} else {
					$(this).removeClass("textbox-prompt");
				}
				tb.removeClass("textbox-focused");
			}).bind("focus.textbox", function(e) {
				if ($(this).val() != opts.value) {
					$(this).val(opts.value);
				}
				$(this).removeClass("textbox-prompt");
				tb.addClass("textbox-focused");
			});
			for ( var _25 in opts.inputEvents) {
				tbText.bind(_25 + ".textbox", {
					target : target
				}, opts.inputEvents[_25]);
			}
		}
		var addon = tb.find(".textbox-addon");
		addon.unbind().bind("click",{target : target},function(e) {
			var icon = $(e.target).closest("a.textbox-icon:not(.textbox-icon-disabled)");
			if (icon.length) {
				var index = parseInt(icon.attr("icon-index"));
				var _icon = opts.icons[index];
				if (_icon && _icon.handler) {
					_icon.handler.call(icon[0], e);
					opts.onClickIcon.call(target, index);
				}
			}
		});
		addon.find(".textbox-icon").each(function(index) {
			var _icon = opts.icons[index];
			var iconHtml = $(this);
			if (!_icon || _icon.disabled || opts.disabled || opts.readonly) {
				iconHtml.addClass("textbox-icon-disabled");
			} else {
				iconHtml.removeClass("textbox-icon-disabled");
			}
		});
		tb.find(".textbox-button").linkbutton((opts.disabled || opts.readonly) ? "disable" : "enable");
	};
	function setDisplayState(target, state) {
		var textbox = $.data(target, "textbox");
		var opts = textbox.options;
		var tb = textbox.textbox;
		if (state) {
			opts.disabled = true;
			$(target).attr("disabled", "disabled");
			tb.find(".textbox-text,.textbox-value").attr("disabled", "disabled");
		} else {
			opts.disabled = false;
			$(target).removeAttr("disabled");
			tb.find(".textbox-text,.textbox-value").removeAttr("disabled");
		}
	};
	function readonly(target, _32) {
		var textbox = $.data(target, "textbox");
		var opts = textbox.options;
		opts.readonly = _32 == undefined ? true : _32;
		var _35 = textbox.textbox.find(".textbox-text");
		_35.removeAttr("readonly").removeClass("textbox-text-readonly");
		if (opts.readonly || !opts.editable) {
			_35.attr("readonly", "readonly").addClass("textbox-text-readonly");
		}
	};
	$.fn.textbox = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.textbox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.each(function() {
					var textbox = $(this).textbox("textbox");
					textbox.validatebox(options, param);
				});
			}
		}
		options = options || {};
		return this.each(function() {
			var textbox = $.data(this, "textbox");
			if (textbox) {
				$.extend(textbox.options, options);
				if (options.value != undefined) {
					textbox.options.originalValue = options.value;
				}
			} else {
				textbox = $.data(this, "textbox", {
					options : $.extend({}, $.fn.textbox.defaults, $.fn.textbox.parseOptions(this), options),
					textbox : createTextbox(this)
				});
				textbox.options.originalValue = textbox.options.value;
			}
			initTextbox(this);
			bindEvent(this);
			resize(this);
			validatebox(this);
			$(this).textbox("initValue", textbox.options.value);
		});
	};
	$.fn.textbox.methods = {
		options : function(jq) {
			return $.data(jq[0], "textbox").options;
		},
		textbox : function(jq) {
			return $.data(jq[0], "textbox").textbox.find(".textbox-text");
		},
		button : function(jq) {
			return $.data(jq[0], "textbox").textbox.find(".textbox-button");
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		},
		resize : function(jq, _3b) {
			return jq.each(function() {
				resize(this, _3b);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				setDisplayState(this, true);
				bindEvent(this);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				setDisplayState(this, false);
				bindEvent(this);
			});
		},
		readonly : function(jq, _3c) {
			return jq.each(function() {
				readonly(this, _3c);
				bindEvent(this);
			});
		},
		isValid : function(jq) {
			return jq.textbox("textbox").validatebox("isValid");
		},
		clear : function(jq) {
			return jq.each(function() {
				$(this).textbox("setValue", "");
			});
		},
		setText : function(jq, _3d) {
			return jq.each(function() {
				var _3e = $(this).textbox("options");
				var _3f = $(this).textbox("textbox");
				if ($(this).textbox("getText") != _3d) {
					_3e.value = _3d;
					_3f.val(_3d);
				}
				if (!_3f.is(":focus")) {
					if (_3d) {
						_3f.removeClass("textbox-prompt");
					} else {
						_3f.val(_3e.prompt).addClass("textbox-prompt");
					}
				}
				$(this).textbox("validate");
			});
		},
		initValue : function(jq, _40) {
			return jq.each(function() {
				var opt = $.data(this, "textbox").options;
				var _41 = $.data(this, "textbox");
				_41.options.value = "";
				//重新 编码
				if(opt.regChars && opt.regChars.length>0){
					_40= parser(this,_40);
				}
				$(this).textbox("setText", _40);
				_41.textbox.find(".textbox-value").val(_40);
				$(this).val(_40);
			});
		},
		setValue : function(jq, _42) {
			return jq.each(function() {
				var _43 = $.data(this, "textbox").options;
				var _44 = $(this).textbox("getValue");
				$(this).textbox("initValue", _42);
				if (_44 != _42) {
					_43.onChange.call(this, _42, _44);
				}
			});
		},
		getText : function(jq) {
			var textbox = jq.textbox("textbox");
			if (textbox.is(":focus")) {
				return textbox.val();
			} else {
				return jq.textbox("options").value;
			}
		},
		getValue : function(jq) {
			return jq.data("textbox").textbox.find(".textbox-value").val();
		},
		reset : function(jq) {
			return jq.each(function() {
				var _46 = $(this).textbox("options");
				$(this).textbox("setValue", _46.originalValue);
			});
		},
		getIcon : function(jq, _47) {
			return jq.data("textbox").textbox.find(".textbox-icon:eq(" + _47 + ")");
		},
		getTipX : function(jq) {
			var _48 = jq.data("textbox");
			var _49 = _48.options;
			var tb = _48.textbox;
			var _4a = tb.find(".textbox-text");
			var _4b = tb.find(".textbox-addon")._outerWidth();
			var _4c = tb.find(".textbox-button")._outerWidth();
			if (_49.tipPosition == "right") {
				return (_49.iconAlign == "right" ? _4b : 0)
						+ (_49.buttonAlign == "right" ? _4c : 0) + 1;
			} else {
				if (_49.tipPosition == "left") {
					return (_49.iconAlign == "left" ? -_4b : 0)
							+ (_49.buttonAlign == "left" ? -_4c : 0) - 1;
				} else {
					return _4b / 2 * (_49.iconAlign == "right" ? 1 : -1);
				}
			}
		}
	};
	$.fn.textbox.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.validatebox.parseOptions(target), $.parser.parseOptions(target, 
				[ "prompt", "iconCls", "iconAlign",
				"buttonText", "buttonIcon", "buttonAlign", {
					multiline : "boolean",
					editable : "boolean",
					iconWidth : "number"
				} ]), {
			value : (t.val() || undefined),
			type : (t.attr("type") ? t.attr("type") : undefined),
			disabled : (t.attr("disabled") ? true : undefined),
			readonly : (t.attr("readonly") ? true : undefined)
		});
	};
	$.fn.textbox.defaults = $.extend({}, $.fn.validatebox.defaults, {
		width : "auto",
		height : 22,
		prompt : "",
		value : "",
		type : "text",
		multiline : false,
		editable : true,
		disabled : false,
		readonly : false,
		icons : [],
		regChars:["<",">","~","!","@","$","%","^","&","*","(",")","{","=","+","}","[","]","\"","`","/","'"],
		iconCls : null,
		iconAlign : "right",
		iconWidth : 18,
		buttonText : "",
		buttonIcon : null,
		buttonAlign : "right",
		inputEvents : {
			keypress : function(e) {
				var _14 = e.data.target;
				var _15 = $(_14).textbox("options");
				return _15.filter.call(_14, e);
			},
			blur : function(e) {
				var t = $(e.data.target);
				var _4e = t.textbox("options");
				t.textbox("setValue", _4e.value);
			}
		},
		filter : function(e) {
			var isExist=checkChar(this,e.which);
			if (e.ctrlKey == true && isExist) {
				return false;
			}else if(e.shiftKey == true && isExist){
				return false;
			} else if(isExist){
				return false;
			} else{
				return true;
			}
		},
		onChange : function(_4f, _50) {
		},
		onResize : function(_51, _52) {
		},
		onClickButton : function() {
		},
		onClickIcon : function(_53) {
		},
		onClick : function(_53) {
		}
	});
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function tooltipAddClas(target) {
		$(target).addClass("tooltip-f");
	};
	function bindEvent(target) {
		var opts = $.data(target, "tooltip").options;
		$(target).unbind(".tooltip").bind(opts.showEvent + ".tooltip", function(e) {
			$(target).tooltip("show", e);
		}).bind(opts.hideEvent + ".tooltip", function(e) {
			$(target).tooltip("hide", e);
		}).bind("mousemove.tooltip", function(e) {
			if (opts.trackMouse) {
				opts.trackMouseX = e.pageX;
				opts.trackMouseY = e.pageY;
				$(target).tooltip("reposition");
			}
		});
	};
	function clearTooltip(target) {
		var tooltip = $.data(target, "tooltip");
		if (tooltip.showTimer) {
			clearTimeout(tooltip.showTimer);
			tooltip.showTimer = null;
		}
		if (tooltip.hideTimer) {
			clearTimeout(tooltip.hideTimer);
			tooltip.hideTimer = null;
		}
	};
	function reposition(target) {
		var tooltip = $.data(target, "tooltip");
		if (!tooltip || !tooltip.tip) {
			return;
		}
		var opts = tooltip.options;
		var tip = tooltip.tip;
		var position = {
			left : -100000,
			top : -100000
		};
		if ($(target).is(":visible")) {
			position = _getPosition(opts.position);
			if (opts.position == "top" && position.top < 0) {
				position = _getPosition("bottom");
			} else {
				if ((opts.position == "bottom") && (position.top + tip._outerHeight() > $(window)._outerHeight() + $(document).scrollTop())) {
					position = _getPosition("top");
				}
			}
			if (position.left < 0) {
				if (opts.position == "left") {
					position = _getPosition("right");
				} else {
					$(target).tooltip("arrow").css("left", tip._outerWidth() / 2 + position.left);
					position.left = 0;
				}
			} else {
				if (position.left + tip._outerWidth() > $(window)._outerWidth() + $(document)._scrollLeft()) {
					if (opts.position == "right") {
						position = _getPosition("left");
					} else {
						var _10 = position.left;
						position.left = $(window)._outerWidth() + $(document)._scrollLeft() - tip._outerWidth();
						$(target).tooltip("arrow").css("left", tip._outerWidth() / 2 - (position.left - _10));
					}
				}
			}
		}
		tip.css({
			left : position.left,
			top : position.top,
			zIndex : (opts.zIndex != undefined ? opts.zIndex : ($.fn.window ? $.fn.window.defaults.zIndex++ : ""))
		});
		opts.onPosition.call(target, position.left, position.top);
		function _getPosition(_position) {
			opts.position = _position || "bottom";
			tip.removeClass("tooltip-top tooltip-bottom tooltip-left tooltip-right").addClass("tooltip-" + opts.position);
			var left, top ,t = null;
			if (opts.trackMouse) {
				t = $();
				left = opts.trackMouseX + opts.deltaX;
				top = opts.trackMouseY + opts.deltaY;
			} else {
				t = $(target);
				left = t.offset().left + opts.deltaX;
				top = t.offset().top + opts.deltaY;
			}
			switch (opts.position) {
			case "right":
				left += t._outerWidth() + 12 + (opts.trackMouse ? 12 : 0);
				top -= (tip._outerHeight() - t._outerHeight()) / 2;
				break;
			case "left":
				left -= tip._outerWidth() + 12 + (opts.trackMouse ? 12 : 0);
				top -= (tip._outerHeight() - t._outerHeight()) / 2;
				break;
			case "top":
				left -= (tip._outerWidth() - t._outerWidth()) / 2;
				top -= tip._outerHeight() + 12 + (opts.trackMouse ? 12 : 0);
				break;
			case "bottom":
				left -= (tip._outerWidth() - t._outerWidth()) / 2;
				top += t._outerHeight() + 12 + (opts.trackMouse ? 12 : 0);
				break;
			}
			return {
				left : left,
				top : top
			};
		};
	};
	function show(target, e) {
		var tooltip = $.data(target, "tooltip");
		var opts = tooltip.options;
		var tip = tooltip.tip;
		if (!tip) {
			tip = $("<div tabindex=\"-1\" class=\"tooltip\">"
			+ "<div class=\"tooltip-content\"></div>"
			+ "<div class=\"tooltip-arrow-outer\"></div>"
			+ "<div class=\"tooltip-arrow\"></div>" + "</div>").appendTo("body");
			if(opts.cls){
				tip.addClass(opts.cls);
			}
			tooltip.tip = tip;
			update(target);
		}
		clearTooltip(target);
		tooltip.showTimer = setTimeout(function() {
			$(target).tooltip("reposition");
			tip.show();
			opts.onShow.call(target, e);
			var outer = tip.children(".tooltip-arrow-outer");
			var arrow = tip.children(".tooltip-arrow");
			var bc = "border-" + opts.position + "-color";
			outer.add(arrow).css({
				borderTopColor : "",
				borderBottomColor : "",
				borderLeftColor : "",
				borderRightColor : ""
			});
//			outer.css(bc, tip.css(bc));
//			arrow.css(bc, tip.css("backgroundColor"));
		}, opts.showDelay);
	};
	function hide(target, e) {
		var tooltip = $.data(target, "tooltip");
		if (tooltip && tooltip.tip) {
			clearTooltip(target);
			tooltip.hideTimer = setTimeout(function() {
				tooltip.tip.hide();
				tooltip.options.onHide.call(target, e);
			}, tooltip.options.hideDelay);
		}
	};
	function update(target, content) {
		var tooltip = $.data(target, "tooltip");
		var opts = tooltip.options;
		if (content) {
			opts.content = content;
		}
		if (!tooltip.tip) {
			return;
		}
		var cc = typeof opts.content == "function" ? opts.content.call(target) : opts.content;
		tooltip.tip.children(".tooltip-content").html(cc);
		opts.onUpdate.call(target, cc);
	};
	function destroy(target) {
		var tooltip = $.data(target, "tooltip");
		if (tooltip) {
			clearTooltip(target);
			var opts = tooltip.options;
			if (tooltip.tip) {
				tooltip.tip.remove();
			}
			if (opts._title) {
				$(target).attr("title", opts._title);
			}
			$.removeData(target, "tooltip");
			$(target).unbind(".tooltip").removeClass("tooltip-f");
			opts.onDestroy.call(target);
		}
	};
	$.fn.tooltip = function(options, param) {
		if (typeof options == "string") {
			return $.fn.tooltip.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var tooltip = $.data(this, "tooltip");
			if (tooltip) {
				$.extend(tooltip.options, options);
			} else {
				$.data(this, "tooltip", {
					options : $.extend({}, $.fn.tooltip.defaults, $.fn.tooltip.parseOptions(this), options)
				});
				tooltipAddClas(this);
			}
			bindEvent(this);
			update(this);
		});
	};
	$.fn.tooltip.methods = {
		options : function(jq) {
			return $.data(jq[0], "tooltip").options;
		},
		tip : function(jq) {
			return $.data(jq[0], "tooltip").tip;
		},
		arrow : function(jq) {
			return jq.tooltip("tip").children(".tooltip-arrow-outer,.tooltip-arrow");
		},
		show : function(jq, e) {
			return jq.each(function() {
				show(this, e);
			});
		},
		hide : function(jq, e) {
			return jq.each(function() {
				hide(this, e);
			});
		},
		update : function(jq, _28) {
			return jq.each(function() {
				update(this, _28);
			});
		},
		reposition : function(jq) {
			return jq.each(function() {
				reposition(this);
			});
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		}
	};
	$.fn.tooltip.parseOptions = function(target) {
		var t = $(target);
		var opts = $.extend({}, $.parser.parseOptions(target, [ "position","showEvent", "hideEvent", "content", {
			trackMouse : "boolean",
			deltaX : "number",
			deltaY : "number",
			showDelay : "number",
			hideDelay : "number"
		} ]), {
			_title : t.attr("title")
		});
		t.attr("title", "");
		if (!opts.content) {
			opts.content = opts._title;
		}
		return opts;
	};
	$.fn.tooltip.defaults = {
		position : "bottom",
		content : null,
		trackMouse : false,
		cls : null,
		deltaX : 0,
		deltaY : 0,
		showEvent : "mouseenter",
		hideEvent : "mouseleave",
		showDelay : 200,
		hideDelay : 100,
		onShow : function(e) {
		},
		onHide : function(e) {
		},
		onUpdate : function(_2b) {
		},
		onPosition : function(_2c, top) {
		},
		onDestroy : function() {
		}
	};
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	$.fn._remove = function() {
		return this.each(function() {
			$(this).remove();
			try {
				this.outerHTML = "";
			} catch (err) {
			}
		});
	};
	function removeNode(target) {
		target._remove();
	};
	function resizePanel(target, options) {
		var panel = $.data(target, "panel");
		var opts = panel.options;
		var ppanel = panel.panel;
		var panelHeader = ppanel.children("div.panel-header");
		var panelBody = ppanel.children("div.panel-body");
		if (options) {
			$.extend(opts, {
				width : options.width,
				height : options.height,
				minWidth : options.minWidth,
				maxWidth : options.maxWidth,
				minHeight : options.minHeight,
				maxHeight : options.maxHeight,
				left : options.left,
				top : options.top
			});
		}
		ppanel._size(opts);
		panelHeader.add(panelBody)._outerWidth(ppanel.width());
		if (!isNaN(parseInt(opts.height))) {
			panelBody._outerHeight(ppanel.height() - panelHeader._outerHeight());
		} else {
			panelBody.css("height", "");
			var minHeight = $.parser.parseValue("minHeight", opts.minHeight, ppanel.parent());
			var maxHeight = $.parser.parseValue("maxHeight", opts.maxHeight, ppanel.parent());
			var outerHeight = panelHeader._outerHeight() + ppanel._outerHeight() - ppanel.height();
			panelBody._size("minHeight", minHeight ? (minHeight - outerHeight) : "");
			panelBody._size("maxHeight", maxHeight ? (maxHeight - outerHeight) : "");
		}
		ppanel.css({
			height : "",
			minHeight : "",
			maxHeight : "",
			left : opts.left,
			top : opts.top
		});
		opts.onResize.apply(target, [ opts.width, opts.height ]);
		$(target).panel("doLayout");
	};
	function movePanel(target, options) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (options) {
			if (options.left != null) {
				opts.left = options.left;
			}
			if (options.top != null) {
				opts.top = options.top;
			}
		}
		panel.css({
			left : opts.left,
			top : opts.top
		});
		opts.onMove.apply(target, [ opts.left, opts.top ]);
	};
	function wrapPanel(target) {
		$(target).addClass("panel-body")._size("clear");
		var panel = $("<div class=\"panel\"></div>").insertBefore(target);
		panel[0].appendChild(target);
		panel.bind("_resize", function(e, _bl) {
			if ($(this).hasClass("easyui-fluid") || _bl) {
				resizePanel(target);
			}
			return false;
		});
		return panel;
	};
	function setBorder(target) {
		var panel = $.data(target, "panel");
		var opts = panel.options;
		var pp = panel.panel;
		pp.css(opts.style);
		pp.addClass(opts.cls);
		setHeader();
		var panelHeader = $(target).panel("header");
		var panelBody = $(target).panel("body");
		if (opts.border) {
			panelHeader.removeClass("panel-header-noborder");
			panelBody.removeClass("panel-body-noborder");
		} else {
			panelHeader.addClass("panel-header-noborder");
			panelBody.addClass("panel-body-noborder");
		}
		panelHeader.addClass(opts.headerCls);
		panelBody.addClass(opts.bodyCls);
		$(target).attr("id", opts.id || "");
		if (opts.content) {
			$(target).panel("clear");
			$(target).html(opts.content);
			$.parser.parse($(target));
		}
		function setHeader() {
			if (opts.tools && typeof opts.tools == "string") {
				pp.find(">div.panel-header>div.panel-tool .panel-tool-a").appendTo(opts.tools);
			}
			removeNode(pp.children("div.panel-header"));
			if (opts.title && !opts.noheader) {
				var header = $("<div class=\"panel-header\"></div>").prependTo(pp);
				var title = $("<div class=\"panel-title\"></div>").html(opts.title).appendTo(header);
				if (opts.iconCls) {
					title.addClass("panel-with-icon");
					$("<div class=\"panel-icon\"></div>").addClass(opts.iconCls).appendTo(header);
				}
				var tool = $("<div class=\"panel-tool\"></div>").appendTo(header);
				tool.bind("click", function(e) {
					e.stopPropagation();
				});
				if (opts.tools) {
					if ($.isArray(opts.tools)) {
						for (var i = 0; i < opts.tools.length; i++) {
							var t = $("<a href=\"javascript:void(0)\"></a>").addClass(opts.tools[i].iconCls).appendTo(tool);
							if (opts.tools[i].handler) {
								t.bind("click", eval(opts.tools[i].handler));
							}
						}
					} else {
						$(opts.tools).children().each(function() {
							$(this).addClass($(this).attr("iconCls")).addClass("panel-tool-a").appendTo(tool);
						});
					}
				}
				if (opts.collapsible) {
					$("<a class=\"panel-tool-collapse\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click", function() {
						if (opts.collapsed == true) {
							expandPanel(target, true);
						} else {
							collapsePanel(target, true);
						}
						return false;
					});
				}
				if (opts.minimizable) {
					$("<a class=\"panel-tool-min\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click", function() {
						minimizePanel(target);
						return false;
					});
				}
				if (opts.maximizable) {
					$("<a class=\"panel-tool-max\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click", function() {
						if (opts.maximized == true) {
							restorePanel(target);
						} else {
							maximizePanel(target);
						}
						return false;
					});
				}
				if (opts.closable) {
					$("<a class=\"panel-tool-close\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click", function() {
						closePanel(target);
						return false;
					});
				}
				pp.children("div.panel-body").removeClass("panel-body-noheader");
			} else {
				pp.children("div.panel-body").addClass("panel-body-noheader");
			}
		};
	};
	function refreshPanel(target, queryParams) {
		var panel = $.data(target, "panel");
		var opts = panel.options;
		if (queryParams) {
			opts.queryParams = queryParams;
		}
		if (!opts.href && !opts.iframe) {
			return;
		}
		if (!panel.isLoaded || !opts.cache) {
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
			panel.isLoaded = false;
			$(target).panel("clear");
			if (opts.loadingMessage) {
				$(target).html($("<div class=\"panel-loading\"></div>").html(opts.loadingMessage));
			}
			opts.loader.call(target, param, function(data) {
				$(target).html(opts.extractor.call(target, data));
				$.parser.parse($(target));
				opts.onLoad.apply(target, [$(target)]);
				panel.isLoaded = true;
			}, function() {
				opts.onLoadError.apply(target, [$(target)]);
			},function(){
				$(target).find(".panel-loading").remove();
				opts.onIfarmeLoad.apply(target, [$(target)]);
			});
		}
	};
	function clear(target) {
		var t = $(target);
		t.find(".combo-f").each(function() {
			$(this).combo("destroy");
		});
		t.find(".m-btn").each(function() {
			$(this).menubutton("destroy");
		});
		t.find(".s-btn").each(function() {
			$(this).splitbutton("destroy");
		});
		t.find(".tooltip-f").each(function() {
			$(this).tooltip("destroy");
		});
		t.children("div").each(function() {
			$(this)._size("unfit");
		});
		t.empty();
	};
	function setResizable(target) {
		$(target).panel("doLayout", true);
	};
	function openPanel(target, forceOpen) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (forceOpen != true) {
			if (opts.onBeforeOpen.call(target) == false) {
				return;
			}
		}
		panel.show();
		opts.closed = false;
		opts.minimized = false;
		var restore = panel.children("div.panel-header").find("a.panel-tool-restore");
		if (restore.length) {
			opts.maximized = true;
		}
		opts.onOpen.call(target);
		if (opts.maximized == true) {
			opts.maximized = false;
			maximizePanel(target);
		}
		if (opts.collapsed == true) {
			opts.collapsed = false;
			collapsePanel(target);
		}
		if (!opts.collapsed) {
			refreshPanel(target);
			setResizable(target);
		}
	};
	function closePanel(target, forceClose) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (forceClose != true) {
			if (opts.onBeforeClose.call(target) == false) {
				return;
			}
		}
		panel._size("unfit");
		panel.hide();
		opts.closed = true;
		if(opts.clear){
			$(target).panel("clear");
		}
		opts.onClose.call(target);
	};
	function destroyPanel(target, forceDestroy) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (forceDestroy != true) {
			if (opts.onBeforeDestroy.call(target) == false) {
				return;
			}
		}
		$(target).panel("clear");
		removeNode(panel);
		opts.onDestroy.call(target);
	};
	function collapsePanel(target, animate) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var panelBody = panel.children("div.panel-body");
		var panelHeader = panel.children("div.panel-header").find("a.panel-tool-collapse");
		if (opts.collapsed == true) {
			return;
		}
		panelBody.stop(true, true);
		if (opts.onBeforeCollapse.call(target) == false) {
			return;
		}
		panelHeader.addClass("panel-tool-expand");
		if (animate == true) {
			panelBody.slideUp("normal", function() {
				opts.collapsed = true;
				opts.onCollapse.call(target);
			});
		} else {
			panelBody.hide();
			opts.collapsed = true;
			opts.onCollapse.call(target);
		}
	};
	function expandPanel(target, animate) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var panelBody = panel.children("div.panel-body");
		var panelHeader = panel.children("div.panel-header").find("a.panel-tool-collapse");
		if (opts.collapsed == false) {
			return;
		}
		panelBody.stop(true, true);
		if (opts.onBeforeExpand.call(target) == false) {
			return;
		}
		panelHeader.removeClass("panel-tool-expand");
		if (animate == true) {
			panelBody.slideDown("normal", function() {
				opts.collapsed = false;
				opts.onExpand.call(target);
				refreshPanel(target);
				setResizable(target);
			});
		} else {
			panelBody.show();
			opts.collapsed = false;
			opts.onExpand.call(target);
			refreshPanel(target);
			setResizable(target);
		}
	};
	function maximizePanel(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var panelHeader = panel.children("div.panel-header").find("a.panel-tool-max");
		if (opts.maximized == true) {
			return;
		}
		panelHeader.addClass("panel-tool-restore");
		if (!$.data(target, "panel").original) {
			$.data(target, "panel").original = {
				width : opts.width,
				height : opts.height,
				left : opts.left,
				top : opts.top,
				fit : opts.fit
			};
		}
		opts.left = 0;
		opts.top = 0;
		opts.fit = true;
		resizePanel(target);
		opts.minimized = false;
		opts.maximized = true;
		opts.onMaximize.call(target);
	};
	function minimizePanel(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		panel._size("unfit");
		panel.hide();
		opts.minimized = true;
		opts.maximized = false;
		opts.onMinimize.call(target);
	};
	function restorePanel(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var panelHeader = panel.children("div.panel-header").find("a.panel-tool-max");
		if (opts.maximized == false) {
			return;
		}
		panel.show();
		panelHeader.removeClass("panel-tool-restore");
		$.extend(opts, $.data(target, "panel").original);
		resizePanel(target);
		opts.minimized = false;
		opts.maximized = false;
		$.data(target, "panel").original = null;
		opts.onRestore.call(target);
	};
	function setTitle(target, title) {
		$.data(target, "panel").options.title = title;
		$(target).panel("header").find("div.panel-title").html(title);
	};
	var resized = null;
	$(window).unbind(".panel").bind("resize.panel", function() {
		if (resized) {
			clearTimeout(resized);
		}
		resized = setTimeout(function() {
			var layout = $("body.layout");
			if (layout.length) {
				layout.layout("resize");
			} else {
				$("body").panel("doLayout");
			}
			resized = null;
		}, 100);
	});
	$.fn.panel = function(method, options) {
		if (typeof method == "string") {
			return $.fn.panel.methods[method](this, options);
		}
		method = method || {};
		return this.each(function() {
			var panel = $.data(this, "panel");
			var opts;
			if (panel) {
				opts = $.extend(panel.options, method);
				panel.isLoaded = false;
			} else {
				opts = $.extend({}, $.fn.panel.defaults, $.fn.panel.parseOptions(this), method);
				$(this).attr("title", "");
				panel = $.data(this, "panel", {
					options : opts,
					panel : wrapPanel(this),
					isLoaded : false
				});
			}
			setBorder(this);
			if (opts.doSize == true) {
				panel.panel.css("display", "block");
				resizePanel(this);
			}
			if (opts.closed == true || opts.minimized == true) {
				panel.panel.hide();
			} else {
				openPanel(this);
			}
		});
	};
	$.fn.panel.methods = {
		options : function(jq) {
			return $.data(jq[0], "panel").options;
		},
		panel : function(jq) {
			return $.data(jq[0], "panel").panel;
		},
		getIframe : function(jq) {
			return $.data(jq[0], "panel").options.iframeObject;
		},
		header : function(jq) {
			return $.data(jq[0], "panel").panel.find(">div.panel-header");
		},
		body : function(jq) {
			return $.data(jq[0], "panel").panel.find(">div.panel-body");
		},
		setTitle : function(jq, title) {
			return jq.each(function() {
				setTitle(this, title);
			});
		},
		open : function(jq, forceOpen) {
			return jq.each(function() {
				openPanel(this, forceOpen);
			});
		},
		close : function(jq, forceClose) {
			return jq.each(function() {
				closePanel(this, forceClose);
			});
		},
		destroy : function(jq, forceDestroy) {
			return jq.each(function() {
				destroyPanel(this, forceDestroy);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				clear(this);
			});
		},
		refresh : function(jq, href) {
			return jq.each(function() {
				var panel = $.data(this, "panel");
				panel.isLoaded = false;
				if (href) {
					if (typeof href == "string") {
						panel.options.href = href;
					} else {
						panel.options.queryParams = href;
					}
				}
				refreshPanel(this);
			});
		},
		refreshIframe : function(jq, iframe) {
			return jq.each(function() {
				var panel = $.data(this, "panel");
				panel.isLoaded = false;
				if (iframe) {
					if (typeof iframe == "string") {
						panel.options.iframe = iframe;
					} 
				}
				refreshPanel(this);
			});
		},
		resize : function(jq, options) {
			return jq.each(function() {
				resizePanel(this, options);
			});
		},
		doLayout : function(jq, all) {
			return jq.each(function() {
						var _6a = this;
						var body = _6a == $("body")[0];
						var s = $(this).find("div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible,.easyui-fluid:visible").filter(function(_6c, el) {
							var p = $(el).parents("div.panel-body:first");
							if (body) {
								return p.length == 0;
							} else {
								return p[0] == _6a;
							}
						});
						s.trigger("_resize", [ all || false ]);
					});
		},
		move : function(jq, options) {
			return jq.each(function() {
				movePanel(this, options);
			});
		},
		maximize : function(jq) {
			return jq.each(function() {
				maximizePanel(this);
			});
		},
		minimize : function(jq) {
			return jq.each(function() {
				minimizePanel(this);
			});
		},
		restore : function(jq) {
			return jq.each(function() {
				restorePanel(this);
			});
		},
		collapse : function(jq, animate) {
			return jq.each(function() {
				collapsePanel(this, animate);
			});
		},
		expand : function(jq, animate) {
			return jq.each(function() {
				expandPanel(this, animate);
			});
		}
	};
	$.fn.panel.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [ "id", "width",
				"height", "left", "top", "title", "iconCls", "cls",
				"headerCls", "bodyCls", "tools", "href", "method", {
					cache : "boolean",
					fit : "boolean",
					border : "boolean",
					noheader : "boolean"
				}, {
					collapsible : "boolean",
					minimizable : "boolean",
					maximizable : "boolean"
				}, {
					closable : "boolean",
					collapsed : "boolean",
					minimized : "boolean",
					maximized : "boolean",
					closed : "boolean"
				} ]), {
			loadingMessage : (t.attr("loadingMessage") != undefined ? t.attr("loadingMessage") : undefined)
		});
	};
	$.fn.panel.defaults = {
		id : null,
		title : null,
		iconCls : null,
		width : "auto",
		height : "auto",
		left : null,
		top : null,
		cls : null,
		headerCls : null,
		bodyCls : null,
		style : {},
		href : null,
		cache : true,
		fit : false,
		border : true,
		doSize : true,
		noheader : false,
		content : null,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		collapsed : false,
		minimized : false,
		maximized : false,
		closed : false,
		clear:false,
		tools : null,
		queryParams : {},
		method : "get",
		href : null,
		iframe: null,//iframe url
		iframeObject: null,//iframe url
		internalForm:null,
		loadingMessage : "Loading...",
		loader : function(data, successCall, callError,ifarmeCall) {
			var opts = $(this).panel("options");
			if (opts.iframe) {
				opts.iframeObject=document.createElement("iframe");
				opts.iframeObject.setAttribute('name', "_internal_iframe_post_id_"+this.id);
				opts.iframeObject.setAttribute('frameborder', "no");
				opts.iframeObject.setAttribute('border', "0");
				opts.iframeObject.setAttribute('marginwidth', "0");
				opts.iframeObject.setAttribute('marginheight', "0");
				opts.iframeObject.setAttribute('scrolling', "no");
				opts.iframeObject.setAttribute('allowtransparency', "yes");
				if(opts.width){
					if(opts.width=="auto"){
						opts.iframeObject.setAttribute('width', "100%");
					}else{
						opts.iframeObject.setAttribute('width', opts.width);
					}
				}
				if(opts.height){
					if(opts.height=="auto"){
						opts.iframeObject.setAttribute('height', "100%");
					}else{
						opts.iframeObject.setAttribute('height', opts.height);
					}
				}
			
				this.appendChild(opts.iframeObject);
				$(opts.iframeObject).load(function(){
					ifarmeCall();
                });
				if(data){
					if(typeof(data)=="object"){
						if(opts.iframe.lastIndexOf("?")>0){
							opts.iframeObject.src=opts.iframe+"&"+$.param(data,true);
						}else{
							opts.iframeObject.src=opts.iframe+"?"+$.param(data,true);
						}
						
					}else if(typeof(data)=="string"){
						if(opts.iframe.lastIndexOf("?")>0){
							opts.iframeObject.src=opts.iframe+"&"+data;
						}else{
							opts.iframeObject.src=opts.iframe+"?"+data;
						}
					}else{
						opts.iframeObject.src=opts.iframe;
					}
				}else{
					opts.iframeObject.src=opts.iframe;
				}
				
			}else{
				if (!opts.href) {
					return false;
				}
				$.ajax({
					type : opts.method,
					url : opts.href,
					cache : false,
					data : data,
					dataType : "html",
					success : function(data) {
						successCall(data);
					},
					error : function() {
						callError.apply(this, arguments);
					}
				});
			}
		},
		extractor : function(data) {
			var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
			var matches = pattern.exec(data);
			if (matches) {
				return matches[1];
			} else {
				return data;
			}
		},
		onBeforeLoad : function(_74) {},
		onLoad : function() {},
		onLoadError : function() {},
		onBeforeOpen : function() {},
		onOpen : function() {},
		onBeforeClose : function() {},
		onClose : function() {},
		onBeforeDestroy : function() {},
		onDestroy : function() {},
		onResize : function(_6c, _6d) {},
		onMove : function(_6e, top) {},
		onMaximize : function() {},
		onRestore : function() {},
		onMinimize : function() {},
		onBeforeCollapse : function() {},
		onBeforeExpand : function() {},
		onCollapse : function() {},
		onExpand : function() {},
		onIfarmeLoad : function() {}
	};
})(jQuery);
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
 * window - jQuery EasyUI
 * 
 * Dependencies:
 * 	 panel
 *   draggable
 *   resizable
 * 
 */
(function($){
	function moveWindow(target, param){
		var state = $.data(target, 'window');
		if (param){
			if (param.left != null) state.options.left = param.left;
			if (param.top != null) state.options.top = param.top;
		}
		$(target).panel('move', state.options);
		if (state.shadow){
			state.shadow.css({
				left: state.options.left,
				top: state.options.top
			});
		}
	}
	
	/**
	 *  center the window only horizontally
	 */
	function hcenter(target, tomove){
		var opts = $.data(target, 'window').options;
		var pp = $(target).window('panel');
		var width = pp._outerWidth();
		if (opts.inline){
			var parent = pp.parent();
			opts.left = Math.ceil((parent.width() - width) / 2 + parent.scrollLeft());
		} else {
			opts.left = Math.ceil(($(window)._outerWidth() - width) / 2 + $(document).scrollLeft());
		}
		if (tomove){moveWindow(target);}
	}
	
	/**
	 * center the window only vertically
	 */
	function vcenter(target, tomove){
		var opts = $.data(target, 'window').options;
		var pp = $(target).window('panel');
		var height = pp._outerHeight();
		if (opts.inline){
			var parent = pp.parent();
			opts.top = Math.ceil((parent.height() - height) / 2 + parent.scrollTop());
		} else {
			opts.top= toNumber("50%", $(window)._outerHeight() - height)+ $(document).scrollTop();
		}
		if (tomove){moveWindow(target);}
	}
	/**
	 * px与%单位转换成数值 (百分比单位按照最大值换算)
	 * 其他的单位返回原值
	 */
	function toNumber( thisValue, maxValue ){
		if(maxValue<=0){
			return 0;
		}
		if( typeof thisValue === 'number' )
			return thisValue;
		
		if( thisValue.indexOf('%') !== -1 )
			thisValue = parseInt(maxValue * thisValue.split('%')[0] / 100);
		
		return thisValue;
	}
	function create(target){
		var state = $.data(target, 'window');
		var opts = state.options;
		var win = $(target).panel($.extend({}, state.options, {
			border: false,
			doSize: true,	// size the panel, the property undefined in window component
			closed: true,	// close the panel
			cls: 'window',
			method:opts.method,
			headerCls: 'window-header',
			bodyCls: 'window-body ' + (opts.noheader ? 'window-body-noheader' : ''),
			
			onBeforeDestroy: function(){
				if (opts.onBeforeDestroy.call(target) == false){return false;}
				if (state.shadow){state.shadow.remove();}
				if (state.mask){state.mask.remove();}
			},
			onClose: function(){
				if (state.shadow){state.shadow.hide();}
				if (state.mask){state.mask.hide();}
				opts.onClose.call(target);
			},
			onOpen: function(){
				if (state.mask){
					state.mask.css({
						display:'block',
						zIndex: $.fn.window.defaults.zIndex++
					});
				}
				if (state.shadow){
					state.shadow.css({
						display:'block',
						zIndex: $.fn.window.defaults.zIndex++,
						left: opts.left,
						top: opts.top,
						width: state.window._outerWidth(),
						height: state.window._outerHeight()
					});
				}
				state.window.css('z-index', $.fn.window.defaults.zIndex++);
				state.window.css('position', "absolute");
				opts.onOpen.call(target);
			},
			onResize: function(width, height){
				var popts = $(this).panel('options');
				$.extend(opts, {
					width: popts.width,
					height: popts.height,
					left: popts.left,
					top: popts.top
				});
				if (state.shadow){
					state.shadow.css({
						left: opts.left,
						top: opts.top,
						width: state.window._outerWidth(),
						height: state.window._outerHeight()
					});
				}
				opts.onResize.call(target, width, height);
			},
			onMinimize: function(){
				if (state.shadow){state.shadow.hide();}
				if (state.mask){state.mask.hide();}
				state.options.onMinimize.call(target);
			},
			onBeforeCollapse: function(){
				if (opts.onBeforeCollapse.call(target) == false){return false;}
				if (state.shadow){state.shadow.hide();}
			},
			onExpand: function(){
				if (state.shadow){state.shadow.show();}
				opts.onExpand.call(target);
			}
		}));
		
		state.window = win.panel('panel');
		
		// create mask
		if (state.mask){state.mask.remove();}
		if (opts.modal == true){
			state.mask = $('<div class="window-mask"></div>').insertAfter(state.window);
			state.mask.css({
				width: (opts.inline ? state.mask.parent().width() : getPageArea().width),
				height: (opts.inline ? state.mask.parent().height() : getPageArea().height),
				display: 'none'
			});
		}
		
		// create shadow
		if (state.shadow){state.shadow.remove();}
		if (opts.shadow == true){
			state.shadow = $('<div class="window-shadow"></div>').insertAfter(state.window);
			state.shadow.css({
				display: 'none'
			});
		}
		
		// if require center the window
		if (opts.relocation || opts.left == null){hcenter(target);}
		if (opts.relocation || opts.top == null){vcenter(target);}
		moveWindow(target);
		
		if (!opts.closed){
			win.window('open');	// open the window
		}
	}
	
	
	/**
	 * set window drag and resize property
	 */
	function setProperties(target){
		var state = $.data(target, 'window');
		
		state.window.draggable({
			handle: '>div.panel-header>div.panel-title',
			disabled: state.options.draggable == false,
			onStartDrag: function(e){
				if (state.mask) state.mask.css('z-index', $.fn.window.defaults.zIndex++);
				if (state.shadow) state.shadow.css('z-index', $.fn.window.defaults.zIndex++);
				state.window.css('z-index', $.fn.window.defaults.zIndex++);
				
				if (!state.proxy){
					state.proxy = $('<div class="window-proxy"></div>').insertAfter(state.window);
				}
				state.proxy.css({
					display:'none',
					zIndex: $.fn.window.defaults.zIndex++,
					left: e.data.left,
					top: e.data.top+100
				});
				state.proxy._outerWidth(state.window._outerWidth());
				state.proxy._outerHeight(state.window._outerHeight());
				setTimeout(function(){
					if (state.proxy) state.proxy.show();
				}, 500);
			},
			onDrag: function(e){
				state.proxy.css({
					display:'block',
					left: e.data.left,
					top: e.data.top
				});
				return false;
			},
			onStopDrag: function(e){
				state.options.left = e.data.left;
				state.options.top = e.data.top;
				$(target).window('move');
				state.proxy.remove();
				state.proxy = null;
			}
		});
		
		state.window.resizable({
			disabled: state.options.resizable == false,
			onStartResize:function(e){
				if (state.pmask){state.pmask.remove();}
				state.pmask = $('<div class="window-proxy-mask"></div>').insertAfter(state.window);
				state.pmask.css({
					zIndex: $.fn.window.defaults.zIndex++,
					left: e.data.left,
					top: e.data.top,
					width: state.window._outerWidth(),
					height: state.window._outerHeight()
				});
				if (state.proxy){state.proxy.remove();}
				state.proxy = $('<div class="window-proxy"></div>').insertAfter(state.window);
				state.proxy.css({
					zIndex: $.fn.window.defaults.zIndex++,
					left: e.data.left,
					top: e.data.top
				});
				state.proxy._outerWidth(e.data.width)._outerHeight(e.data.height);
			},
			onResize: function(e){
				state.proxy.css({
					left: e.data.left,
					top: e.data.top
				});
				state.proxy._outerWidth(e.data.width);
				state.proxy._outerHeight(e.data.height);
				return false;
			},
			onStopResize: function(e){
				$(target).window('resize', e.data);
				state.pmask.remove();
				state.pmask = null;
				state.proxy.remove();
				state.proxy = null;
			}
		});
	}
	
	function getPageArea() {
		if (document.compatMode == 'BackCompat') {
			return {
				width: Math.max(document.body.scrollWidth, document.body.clientWidth),
				height: Math.max(document.body.scrollHeight, document.body.clientHeight)
			};
		} else {
			return {
				width: Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth),
				height: Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight)
			};
		}
	}
	
	// when window resize, reset the width and height of the window's mask
	$(window).resize(function(){
		$('body>div.window-mask').css({
			width: $(window)._outerWidth(),
			height: $(window)._outerHeight()
		});
		setTimeout(function(){
			$('body>div.window-mask').css({
				width: getPageArea().width,
				height: getPageArea().height
			});
		}, 50);
	});
	
	$.fn.window = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.window.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.panel(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'window');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'window', {
					options: $.extend({}, $.fn.window.defaults, $.fn.window.parseOptions(this), options)
				});
				if (!state.options.inline){
//					$(this).appendTo('body');
					document.body.appendChild(this);
				}
			}
			create(this);
			setProperties(this);
		});
	};
	
	$.fn.window.methods = {
		options: function(jq){
			var popts = jq.panel('options');
			var wopts = $.data(jq[0], 'window').options;
			return $.extend(wopts, {
				closed: popts.closed,
				collapsed: popts.collapsed,
				minimized: popts.minimized,
				maximized: popts.maximized
			});
		},
		window: function(jq){
			return $.data(jq[0], 'window').window;
		},
		move: function(jq, param){
			return jq.each(function(){
				moveWindow(this, param);
			});
		},
		hcenter: function(jq){
			return jq.each(function(){
				hcenter(this, true);
			});
		},
		vcenter: function(jq){
			return jq.each(function(){
				vcenter(this, true);
			});
		},
		center: function(jq){
			return jq.each(function(){
				hcenter(this);
				vcenter(this);
				moveWindow(this);
			});
		}
	};
	
	$.fn.window.parseOptions = function(target){
		return $.extend({}, $.fn.panel.parseOptions(target), $.parser.parseOptions(target, [
			{draggable:'boolean',resizable:'boolean',shadow:'boolean',modal:'boolean',inline:'boolean'}
		]));
	};
	
	// Inherited from $.fn.panel.defaults
	$.fn.window.defaults = $.extend({}, $.fn.panel.defaults, {
		zIndex: 9000,
		draggable: true,
		resizable: true,
		shadow: false,
		modal: false,
		clear:true,
		//重新定位，如果为false，将会记住上次的位置。
		relocation:true,
		inline: false,	// true to stay inside its parent, false to go on top of all elements
		method:"get",
		// window's property which difference from panel
		title: 'New Window',
		collapsible: false,
		minimizable: false,
		maximizable: false,
		closable: true,
		closed: false
	});
})(jQuery);
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
 * tabs - jQuery EasyUI
 * 
 * Dependencies:
 * 	 panel
 *   linkbutton
 * 
 */
(function($){
	
	/**
	 * set the tabs scrollers to show or not,
	 * dependent on the tabs count and width
	 */
	function setScrollers(container) {
		var opts = $.data(container, 'tabs').options;
		if (opts.tabPosition == 'left' || opts.tabPosition == 'right' || !opts.showHeader){return}
		
		var header = $(container).children('div.tabs-header');
		var tool = header.children('div.tabs-tool');
		var sLeft = header.children('div.tabs-scroller-left');
		var sRight = header.children('div.tabs-scroller-right');
		var wrap = header.children('div.tabs-wrap');
		
		// set the tool height
		var tHeight = header.outerHeight();
		if (opts.plain){
			tHeight -= tHeight - header.height();
		}
		tool._outerHeight(tHeight);
		
		var tabsWidth = 0;
		$('ul.tabs li', header).each(function(){
			tabsWidth += $(this).outerWidth(true);
		});
		var cWidth = header.width() - tool._outerWidth();
		
		if (tabsWidth > cWidth) {
			sLeft.add(sRight).show()._outerHeight(tHeight);
			if (opts.toolPosition == 'left'){
				tool.css({
					left: sLeft.outerWidth(),
					right: ''
				});
				wrap.css({
					marginLeft: sLeft.outerWidth() + tool._outerWidth(),
					marginRight: sRight._outerWidth(),
					width: cWidth - sLeft.outerWidth() - sRight.outerWidth()
				});
			} else {
				tool.css({
					left: '',
					right: sRight.outerWidth()
				});
				wrap.css({
					marginLeft: sLeft.outerWidth(),
					marginRight: sRight.outerWidth() + tool._outerWidth(),
					width: cWidth - sLeft.outerWidth() - sRight.outerWidth()
				});
			}
		} else {
			sLeft.add(sRight).hide();
			if (opts.toolPosition == 'left'){
				tool.css({
					left: 0,
					right: ''
				});
				wrap.css({
					marginLeft: tool._outerWidth(),
					marginRight: 0,
					width: cWidth
				});
			} else {
				tool.css({
					left: '',
					right: 0
				});
				wrap.css({
					marginLeft: 0,
					marginRight: tool._outerWidth(),
					width: cWidth
				});
			}
		}
	}
	
	function addTools(container){
		var opts = $.data(container, 'tabs').options;
		var header = $(container).children('div.tabs-header');
		if (opts.tools) {
			if (typeof opts.tools == 'string'){
				$(opts.tools).addClass('tabs-tool').appendTo(header);
				$(opts.tools).show();
			} else {
				header.children('div.tabs-tool').remove();
				var tools = $('<div class="tabs-tool"><table cellspacing="0" cellpadding="0" style="height:100%"><tr></tr></table></div>').appendTo(header);
				var tr = tools.find('tr');
				for(var i=0; i<opts.tools.length; i++){
					var td = $('<td></td>').appendTo(tr);
					var tool = $('<a href="javascript:void(0);"></a>').appendTo(td);
					tool[0].onclick = eval(opts.tools[i].handler || function(){});
					tool.linkbutton($.extend({}, opts.tools[i], {
						plain: true
					}));
				}
			}
		} else {
			header.children('div.tabs-tool').remove();
		}
	}
	
	function setSize(container, param) {
		var state = $.data(container, 'tabs');
		var opts = state.options;
		var cc = $(container);
		
		if (param){
			$.extend(opts, {
				width: param.width,
				height: param.height
			});
		}
		cc._size(opts);
		
		var header = cc.children('div.tabs-header');
		var panels = cc.children('div.tabs-panels');
		var wrap = header.find('div.tabs-wrap');
		var ul = wrap.find('.tabs');
		
		for(var i=0; i<state.tabs.length; i++){
			var p_opts = state.tabs[i].panel('options');
			var p_t = p_opts.tab.find('a.tabs-inner');
			var width = parseInt(p_opts.tabWidth || opts.tabWidth) || undefined;
			if (width){
				p_t._outerWidth(width);
			} else {
				p_t.css('width', '');
			}
			p_t._outerHeight(opts.tabHeight);
			p_t.css('lineHeight', p_t.height()+'px');
		}
		if (opts.tabPosition == 'left' || opts.tabPosition == 'right'){
			header._outerWidth(opts.showHeader ? opts.headerWidth : 0);
//			header._outerWidth(opts.headerWidth);
			panels._outerWidth(cc.width() - header.outerWidth());
			header.add(panels)._outerHeight(opts.height);
			wrap._outerWidth(header.width());
			ul._outerWidth(wrap.width()).css('height','');
		} else {
			var lrt = header.children('div.tabs-scroller-left,div.tabs-scroller-right,div.tabs-tool');
			header._outerWidth(opts.width).css('height','');
			if (opts.showHeader){
				header.css('background-color','');
				wrap.css('height','');
				lrt.show();
			} else {
				header.css('background-color','transparent');
				header._outerHeight(0);
				wrap._outerHeight(0);
				lrt.hide();
			}
			ul._outerHeight(opts.tabHeight).css('width','');
			
			setScrollers(container);
			
			panels._size('height', isNaN(opts.height) ? '' : (opts.height-header.outerHeight()));
			panels._size('width', isNaN(opts.width) ? '' : opts.width);
		}
	}
	
	/**
	 * set selected tab panel size
	 */
	function setSelectedSize(container){
		var opts = $.data(container, 'tabs').options;
		var tab = getSelectedTab(container);
		if (tab){
			var panels = $(container).children('div.tabs-panels');
			var width = opts.width=='auto' ? 'auto' : panels.width();
			var height = opts.height=='auto' ? 'auto' : panels.height();
			if(opts.extendsTabWidth){
				tab.panel('resize', {
					width: width,
					height: height
				});
			}
		}
	}
	
	/**
	 * wrap the tabs header and body
	 */
	function wrapTabs(container) {
		var tabs = $.data(container, 'tabs').tabs;
		var cc = $(container);
		cc.addClass('tabs-container');
		var pp = $('<div class="tabs-panels"></div>').insertBefore(cc);
		cc.children('div').each(function(){
			pp[0].appendChild(this);
		});
		cc[0].appendChild(pp[0]);
//		cc.wrapInner('<div class="tabs-panels"/>');
		$('<div class="tabs-header">'
				+ '<div class="tabs-scroller-left"></div>'
				+ '<div class="tabs-scroller-right"></div>'
				+ '<div class="tabs-wrap">'
				+ '<ul class="tabs"></ul>'
				+ '</div>'
				+ '</div>').prependTo(container);
		
		cc.children('div.tabs-panels').children('div').each(function(i){
			var opts = $.extend({}, $.parser.parseOptions(this), {
				selected: ($(this).attr('selected') ? true : undefined)
			});
			var pp = $(this);
			tabs.push(pp);
			createTab(container, pp, opts);
		});
		
		cc.children('div.tabs-header').find('.tabs-scroller-left, .tabs-scroller-right').hover(
				function(){$(this).addClass('tabs-scroller-over');},
				function(){$(this).removeClass('tabs-scroller-over');}
		);
		cc.bind('_resize', function(e,force){
			if ($(this).hasClass('easyui-fluid') || force){
				setSize(container);
				setSelectedSize(container);
			}
			return false;
		});
	}
	
	function bindEvents(container){
		var state = $.data(container, 'tabs')
		var opts = state.options;
		$(container).children('div.tabs-header').unbind().bind('click', function(e){
			if ($(e.target).hasClass('tabs-scroller-left')){
				$(container).tabs('scrollBy', -opts.scrollIncrement);
			} else if ($(e.target).hasClass('tabs-scroller-right')){
				$(container).tabs('scrollBy', opts.scrollIncrement);
			} else {
				var li = $(e.target).closest('li');
				if (li.hasClass('tabs-disabled')){return;}
				var a = $(e.target).closest('a.tabs-close');
				if (a.length){
					closeTab(container, getLiIndex(li));
				} else if (li.length){
//					selectTab(container, getLiIndex(li));
					var index = getLiIndex(li);
					var popts = state.tabs[index].panel('options');
					if (popts.collapsible){
						popts.closed ? selectTab(container, index) : unselectTab(container, index);
					} else {
						selectTab(container, index);
					}
				}
			}
		}).bind('contextmenu', function(e){
			var li = $(e.target).closest('li');
			if (li.hasClass('tabs-disabled')){return;}
			if (li.length){
				opts.onContextMenu.call(container, e, li.find('span.tabs-title').html(), getLiIndex(li));
			}
		});
		
		function getLiIndex(li){
			var index = 0;
			li.parent().children('li').each(function(i){
				if (li[0] == this){
					index = i;
					return false;
				}
			});
			return index;
		}
	}
	
	function setProperties(container){
		var opts = $.data(container, 'tabs').options;
		var header = $(container).children('div.tabs-header');
		var panels = $(container).children('div.tabs-panels');
		
		header.removeClass('tabs-header-top tabs-header-bottom tabs-header-left tabs-header-right');
		panels.removeClass('tabs-panels-top tabs-panels-bottom tabs-panels-left tabs-panels-right');
		if (opts.tabPosition == 'top'){
			header.insertBefore(panels);
		} else if (opts.tabPosition == 'bottom'){
			header.insertAfter(panels);
			header.addClass('tabs-header-bottom');
			panels.addClass('tabs-panels-top');
		} else if (opts.tabPosition == 'left'){
			header.addClass('tabs-header-left');
			panels.addClass('tabs-panels-right');
		} else if (opts.tabPosition == 'right'){
			header.addClass('tabs-header-right');
			panels.addClass('tabs-panels-left');
		}
		
		if (opts.plain == true) {
			header.addClass('tabs-header-plain');
		} else {
			header.removeClass('tabs-header-plain');
		}
		if (opts.border == true){
			header.removeClass('tabs-header-noborder');
			panels.removeClass('tabs-panels-noborder');
		} else {
			header.addClass('tabs-header-noborder');
			panels.addClass('tabs-panels-noborder');
		}
	}
	
	function createTab(container, pp, options) {
		var state = $.data(container, 'tabs');
		options = options || {};
		
		// create panel
		pp.panel($.extend({}, options, {
			border: false,
			noheader: true,
			closed: true,
			doSize: false,
			iconCls: (options.icon ? options.icon : undefined),
			onLoad: function(){
				if (options.onLoad){
					options.onLoad.call(this, arguments);
				}
				state.options.onLoad.call(container, $(this));
			}
		}));
		
		var opts = pp.panel('options');
		
		var tabs = $(container).children('div.tabs-header').find('ul.tabs');
		
		opts.tab = $('<li></li>').appendTo(tabs);	// set the tab object in panel options
		opts.tab.append(
				'<a href="javascript:void(0)" class="tabs-inner">' +
				'<span class="tabs-title"></span>' +
				'<span class="tabs-icon"></span>' +
				'</a>'
		);
		
		$(container).tabs('update', {
			tab: pp,
			options: opts
		});
	}
	
	function addTab(container, options) {
		var opts = $.data(container, 'tabs').options;
		var tabs = $.data(container, 'tabs').tabs;
		if (options.selected == undefined) options.selected = true;
		
		var pp = $('<div id="'+tabs.length+'"></div>').appendTo($(container).children('div.tabs-panels'));
		tabs.push(pp);
		createTab(container, pp, options);
		
		opts.onAdd.call(container, options.title, tabs.length-1);
		
//		setScrollers(container);
		setSize(container);
		if (options.selected){
			selectTab(container, tabs.length-1);	// select the added tab panel
		}
	}
	
	/**
	 * update tab panel, param has following properties:
	 * tab: the tab panel to be updated
	 * options: the tab panel options
	 */
	function updateTab(container, param){
		var selectHis = $.data(container, 'tabs').selectHis;
		var pp = param.tab;	// the tab panel
		var oldTitle = pp.panel('options').title; 
		pp.panel($.extend({}, param.options, {
			iconCls: (param.options.icon ? param.options.icon : undefined)
		}));
		
		var opts = pp.panel('options');	// get the tab panel options
		var tab = opts.tab;
		
		var s_title = tab.find('span.tabs-title');
		var s_icon = tab.find('span.tabs-icon');
		s_title.html(opts.title);
		s_icon.attr('class', 'tabs-icon');
		
		tab.find('a.tabs-close').remove();
		if (opts.closable){
			s_title.addClass('tabs-closable');
			$('<a href="javascript:void(0)" class="tabs-close"></a>').appendTo(tab);
		} else{
			s_title.removeClass('tabs-closable');
		}
		if (opts.iconCls){
			s_title.addClass('tabs-with-icon');
			s_icon.addClass(opts.iconCls);
		} else {
			s_title.removeClass('tabs-with-icon');
		}
		
		if (oldTitle != opts.title){
			for(var i=0; i<selectHis.length; i++){
				if (selectHis[i] == oldTitle){
					selectHis[i] = opts.title;
				}
			}
		}
		
		tab.find('span.tabs-p-tool').remove();
		if (opts.tools){
			var p_tool = $('<span class="tabs-p-tool"></span>').insertAfter(tab.find('a.tabs-inner'));
			if ($.isArray(opts.tools)){
				for(var i=0; i<opts.tools.length; i++){
					var t = $('<a href="javascript:void(0)"></a>').appendTo(p_tool);
					t.addClass(opts.tools[i].iconCls);
					if (opts.tools[i].handler){
						t.bind('click', {handler:opts.tools[i].handler}, function(e){
							if ($(this).parents('li').hasClass('tabs-disabled')){return;}
							e.data.handler.call(this);
						});
					}
				}
			} else {
				$(opts.tools).children().appendTo(p_tool);
			}
			var pr = p_tool.children().length * 12;
			if (opts.closable) {
				pr += 8;
			} else {
				pr -= 3;
				p_tool.css('right','5px');
			}
			s_title.css('padding-right', pr+'px');
		}
		
//		setProperties(container);
//		setScrollers(container);
		setSize(container);
		
		$.data(container, 'tabs').options.onUpdate.call(container, opts.title, getTabIndex(container, pp));
	}
	
	/**
	 * close a tab with specified index or title
	 */
	function closeTab(container, which) {
		var opts = $.data(container, 'tabs').options;
		var tabs = $.data(container, 'tabs').tabs;
		var selectHis = $.data(container, 'tabs').selectHis;
		
		if (!exists(container, which)) return;
		
		var tab = getTab(container, which);
		var title = tab.panel('options').title;
		var index = getTabIndex(container, tab);
		
		if (opts.onBeforeClose.call(container, title, index) == false) return;
		
		var tab = getTab(container, which, true);
		tab.panel('options').tab.remove();
		tab.panel('destroy');
		
		opts.onClose.call(container, title, index);
		
//		setScrollers(container);
		setSize(container);
		
		// remove the select history item
		for(var i=0; i<selectHis.length; i++){
			if (selectHis[i] == title){
				selectHis.splice(i, 1);
				i --;
			}
		}
		
		// select the nearest tab panel
		var hisTitle = selectHis.pop();
		if (hisTitle){
			selectTab(container, hisTitle);
		} else if (tabs.length){
			selectTab(container, 0);
		}
	}
	
	/**
	 * get the specified tab panel
	 */
	function getTab(container, which, removeit){
		var tabs = $.data(container, 'tabs').tabs;
		if (typeof which == 'number'){
			if (which < 0 || which >= tabs.length){
				return null;
			} else {
				var tab = tabs[which];
				if (removeit) {
					tabs.splice(which, 1);
				}
				return tab;
			}
		}
		for(var i=0; i<tabs.length; i++){
			var tab = tabs[i];
			if (tab.panel('options').title == which){
				if (removeit){
					tabs.splice(i, 1);
				}
				return tab;
			}
		}
		return null;
	}
	
	function getTabIndex(container, tab){
		var tabs = $.data(container, 'tabs').tabs;
		for(var i=0; i<tabs.length; i++){
			if (tabs[i][0] == $(tab)[0]){
				return i;
			}
		}
		return -1;
	}
	
	function getSelectedTab(container){
		var tabs = $.data(container, 'tabs').tabs;
		for(var i=0; i<tabs.length; i++){
			var tab = tabs[i];
			if (tab.panel('options').closed == false){
				return tab;
			}
		}
		return null;
	}
	
	/**
	 * do first select action, if no tab is setted the first tab will be selected.
	 */
	function doFirstSelect(container){
		var state = $.data(container, 'tabs')
		var tabs = state.tabs;
		for(var i=0; i<tabs.length; i++){
			if (tabs[i].panel('options').selected){
				selectTab(container, i);
				return;
			}
		}
//		if (tabs.length){
//			selectTab(container, 0);
//		}
		selectTab(container, state.options.selected);
	}
	
	function selectTab(container, which){
		var state = $.data(container, 'tabs');
		var opts = state.options;
		var tabs = state.tabs;
		var selectHis = state.selectHis;
		
		if (tabs.length == 0) {return;}
		
		var panel = getTab(container, which); // get the panel to be activated
		if (!panel){return}
		var sop=panel.panel('options');
		
		var selected = getSelectedTab(container);
		if (selected){
			if (panel[0] == selected[0]){
				setSelectedSize(container);
				return;
			}
			unselectTab(container, getTabIndex(container, selected));
			if (!selected.panel('options').closed){return}
		}
		
		panel.panel('open');
		var title = panel.panel('options').title;	// the panel title
		selectHis.push(title);	// push select history
		
		var tab = panel.panel('options').tab;	// get the tab object
		tab.addClass('tabs-selected');
		
		// scroll the tab to center position if required.
		var wrap = $(container).find('>div.tabs-header>div.tabs-wrap');
		var left = tab.position().left;
		var right = left + tab.outerWidth();
		if (left < 0 || right > wrap.width()){
			var deltaX = left - (wrap.width()-tab.width()) / 2;
			$(container).tabs('scrollBy', deltaX);
		} else {
			$(container).tabs('scrollBy', 0);
		}
		
		setSelectedSize(container);
		
		opts.onSelect.call(container, title, getTabIndex(container, panel));
	}
	function selectReloadTab(container, which,href,iframe){
		var state = $.data(container, 'tabs');
		var opts = state.options;
		var tabs = state.tabs;
		var selectHis = state.selectHis;
		
		if (tabs.length == 0) {return;}
		
		var panel = getTab(container, which); // get the panel to be activated
		if (!panel){return}
		var sop=panel.panel('options');
		
		var selected = getSelectedTab(container);
		if (selected){
			if (panel[0] == selected[0]){
				setSelectedSize(container);
				return;
			}
			unselectTab(container, getTabIndex(container, selected));
			if (!selected.panel('options').closed){return}
		}
		if(href){
			panel.panel('options').href=href;
		}else if(iframe){
			panel.panel('options').iframe=iframe;
		}
		panel.panel('open');
		var title = panel.panel('options').title;	// the panel title
		selectHis.push(title);	// push select history
		
		var tab = panel.panel('options').tab;	// get the tab object
		tab.addClass('tabs-selected');
		
		// scroll the tab to center position if required.
		var wrap = $(container).find('>div.tabs-header>div.tabs-wrap');
		var left = tab.position().left;
		var right = left + tab.outerWidth();
		if (left < 0 || right > wrap.width()){
			var deltaX = left - (wrap.width()-tab.width()) / 2;
			$(container).tabs('scrollBy', deltaX);
		} else {
			$(container).tabs('scrollBy', 0);
		}
		
		setSelectedSize(container);
		
		opts.onSelect.call(container, title, getTabIndex(container, panel));
	}
	function unselectTab(container, which){
		var state = $.data(container, 'tabs');
		var p = getTab(container, which);
		if (p){
			var opts = p.panel('options');
			if (!opts.closed){
				p.panel('close');
				if (opts.closed){
					opts.tab.removeClass('tabs-selected');
					state.options.onUnselect.call(container, opts.title, getTabIndex(container, p));
				}
			}
		}
	}
	
	function exists(container, which){
		return getTab(container, which) != null;
	}
	
	function showHeader(container, visible){
		var opts = $.data(container, 'tabs').options;
		opts.showHeader = visible;
		$(container).tabs('resize');
	}
	
	
	$.fn.tabs = function(options, param){
		if (typeof options == 'string') {
			return $.fn.tabs.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'tabs');
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, 'tabs', {
					options: $.extend({},$.fn.tabs.defaults, $.fn.tabs.parseOptions(this), options),
					tabs: [],
					selectHis: []
				});
				wrapTabs(this);
			}
			
			addTools(this);
			setProperties(this);
			setSize(this);
			bindEvents(this);
			
			doFirstSelect(this);
		});
	};
	
	$.fn.tabs.methods = {
		options: function(jq){
			var cc = jq[0];
			var opts = $.data(cc, 'tabs').options;
			var s = getSelectedTab(cc);
			opts.selected = s ? getTabIndex(cc, s) : -1;
			return opts;
		},
		tabs: function(jq){
			return $.data(jq[0], 'tabs').tabs;
		},
		resize: function(jq, param){
			return jq.each(function(){
				setSize(this, param);
				setSelectedSize(this);
			});
		},
		add: function(jq, options){
			return jq.each(function(){
				addTab(this, options);
			});
		},
		close: function(jq, which){
			return jq.each(function(){
				closeTab(this, which);
			});
		},
		getTab: function(jq, which){
			return getTab(jq[0], which);
		},
		getTabIndex: function(jq, tab){
			return getTabIndex(jq[0], tab);
		},
		getSelected: function(jq){
			return getSelectedTab(jq[0]);
		},
		select: function(jq, which){
			return jq.each(function(){
				selectTab(this, which);
			});
		},
		selectReload: function(jq, param){
			return jq.each(function(){
				selectReloadTab(this, param.which,param.href,param.iframe);
			});
		},
		unselect: function(jq, which){
			return jq.each(function(){
				unselectTab(this, which);
			});
		},
		exists: function(jq, which){
			return exists(jq[0], which);
		},
		update: function(jq, options){
			return jq.each(function(){
				updateTab(this, options);
			});
		},
		enableTab: function(jq, which){
			return jq.each(function(){
				$(this).tabs('getTab', which).panel('options').tab.removeClass('tabs-disabled');
			});
		},
		disableTab: function(jq, which){
			return jq.each(function(){
				$(this).tabs('getTab', which).panel('options').tab.addClass('tabs-disabled');
			});
		},
		showHeader: function(jq){
			return jq.each(function(){
				showHeader(this, true);
			});
		},
		hideHeader: function(jq){
			return jq.each(function(){
				showHeader(this, false);
			});
		},
		scrollBy: function(jq, deltaX){	// scroll the tab header by the specified amount of pixels
			return jq.each(function(){
				var opts = $(this).tabs('options');
				var wrap = $(this).find('>div.tabs-header>div.tabs-wrap');
				var pos = Math.min(wrap._scrollLeft() + deltaX, getMaxScrollWidth());
				wrap.animate({scrollLeft: pos}, opts.scrollDuration);
				
				function getMaxScrollWidth(){
					var w = 0;
					var ul = wrap.children('ul');
					ul.children('li').each(function(){
						w += $(this).outerWidth(true);
					});
					return w - wrap.width() + (ul.outerWidth() - ul.width());
				}
			});
		}
	};
	
	$.fn.tabs.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target, [
			'tools','toolPosition','tabPosition',
			{fit:'boolean',border:'boolean',plain:'boolean',headerWidth:'number',tabWidth:'number',tabHeight:'number',selected:'number',showHeader:'boolean'}
		]));
	};
	
	$.fn.tabs.defaults = {
		width: 'auto',
		height: 'auto',
		headerWidth: 150,	// the tab header width, it is valid only when tabPosition set to 'left' or 'right' 
		tabWidth: 'auto',	// the tab width
		tabHeight: 27,		// the tab height
		selected: 0,		// the initialized selected tab index
		showHeader: true,
		plain: false,
		fit: false,
		border: true,
		extendsTabWidth: true,
		tools: null,
		toolPosition: 'right',	// left,right
		tabPosition: 'top',		// possible values: top,bottom
		scrollIncrement: 100,
		scrollDuration: 400,
		onLoad: function(panel){},
		onSelect: function(title, index){},
		onUnselect: function(title, index){},
		onBeforeClose: function(title, index){},
		onClose: function(title, index){},
		onAdd: function(title, index){},
		onUpdate: function(title, index){},
		onContextMenu: function(e, title, index){}
	};
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	var _1 = 0;
	var editIndex = undefined;
	
	function findIndex(array, o) {
		for (var i = 0, len = array.length; i < len; i++) {
			if (array[i] == o) {return i;}
		}
		return -1;
	};
	
	function deleteObject(array, o, id) {
		if (typeof o == "string") {
			for (var i = 0, len = array.length; i < len; i++) {
				if (array[i][o] == id) {
					array.splice(i, 1);
					return;
				}
			}
		} else {
			var index = findIndex(array, o);
			if (index != -1) {
				array.splice(index, 1);
			}
		}
	};

	function pushNewObject(array, o, r) {
		for (var i = 0, len = array.length; i < len; i++) {
			if (array[i][o] == r[o]) {
				return;
			}
		}
		array.push(r);
	};
	
	function createStyleSheet(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var panel = datagrid.panel;
		var dc = datagrid.dc;
		var ss = null;
		if (opts.sharedStyleSheet) {
			ss = typeof opts.sharedStyleSheet == "boolean" ? "head" : opts.sharedStyleSheet;
		} else {
			ss = panel.closest("div.datagrid-view");
			if (!ss.length) {
				ss = dc.view;
			}
		}
		var cc = $(ss);
		var _e = $.data(cc[0], "ss");
		if (!_e) {
			_e = $.data(cc[0], "ss", {
				cache : {},
				dirty : []
			});
		}
		return {
			add : function(_f) {
				var ss = [ "<style type=\"text/css\" easyui=\"true\">" ];
				for (var i = 0; i < _f.length; i++) {
					_e.cache[_f[i][0]] = {
						width : _f[i][1]
					};
				}
				var _10 = 0;
				for ( var s in _e.cache) {
					var _11 = _e.cache[s];
					_11.index = _10++;
					ss.push(s + "{width:" + _11.width + "}");
				}
				ss.push("</style>");
				$(ss.join("\n")).appendTo(cc);
				cc.children("style[easyui]:not(:last)").remove();
			},
			getRule : function(_12) {
				var _13 = cc.children("style[easyui]:last")[0];
				var _14 = _13.styleSheet ? _13.styleSheet
						: (_13.sheet || document.styleSheets[document.styleSheets.length - 1]);
				var _15 = _14.cssRules || _14.rules;
				return _15[_12];
			},
			set : function(_16, _17) {
				var _18 = _e.cache[_16];
				if (_18) {
					_18.width = _17;
					var _19 = this.getRule(_18.index);
					if (_19) {
						_19.style["width"] = _17;
					}
				}
			},
			remove : function(_1a) {
				var tmp = [];
				for ( var s in _e.cache) {
					if (s.indexOf(_1a) == -1) {
						tmp.push([ s, _e.cache[s].width ]);
					}
				}
				_e.cache = {};
				this.add(tmp);
			},
			dirty : function(_1b) {
				if (_1b) {
					_e.dirty.push(_1b);
				}
			},
			clean : function() {
				for (var i = 0; i < _e.dirty.length; i++) {
					this.remove(_e.dirty[i]);
				}
				_e.dirty = [];
			}
		};
	};
	
	function endEditing(target){
		if (editIndex == undefined){return true;}
		if (validateRow(target,editIndex)){
			endEdit(target,editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function setCellEditing(target,index, field){
		if (endEditing(target)){
			selectRow(target, index);
			editCell(target,{index:index,field:field});
			editIndex = index;
		}
	}
	
	function resizeGrid(target, options) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var panel = datagrid.panel;
		if (options) {
			$.extend(opts, options);
		}
		if (opts.fit == true) {
			//表格可以暂时不需要。2015-5-20
			var p = panel.panel("panel").parent().parent();
			//var p = panel.panel("panel").parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		panel.panel("resize", opts);
	};
	function setSize(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		var panel = datagrid.panel;
		var pwidth = panel.width();
		var pheight = panel.height();
		var view = dc.view;
		var view1 = dc.view1;
		var view2 = dc.view2;
		var header1 = view1.children("div.datagrid-header");
		var header2 = view2.children("div.datagrid-header");
		var table1 = header1.find("table");
		var table2 = header2.find("table");
		view.width(pwidth);
		var inner = header1.children("div.datagrid-header-inner").show();
		view1.width(inner.find("table").width());
		if (!opts.showHeader) {
			inner.hide();
		}
		view2.width(pwidth - view1._outerWidth());
		view1.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(view1.width());
		view2.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(view2.width());
		var hh;
		header1.add(header2).css("height", "");
		table1.add(table2).css("height", "");
		hh = Math.max(table1.height(), table2.height());
		table1.add(table2).height(hh);
		header1.add(header2)._outerHeight(hh);
		dc.body1.add(dc.body2).children("table.datagrid-btable-frozen").css({
			position : "absolute",
			top : dc.header2._outerHeight()
		});
		var marginTop = dc.body2.children("table.datagrid-btable-frozen")._outerHeight();
		var headerheight = marginTop + view2.children("div.datagrid-header")._outerHeight()
				+ view2.children("div.datagrid-footer")._outerHeight()
				+ panel.children("div.datagrid-toolbar")._outerHeight();
		panel.children("div.datagrid-pager").each(function() {
			headerheight += $(this)._outerHeight();
		});
		var panelheight = panel.outerHeight() - panel.height();
		var panelminHeight = panel._size("minHeight") || "";
		var panelmaxHeight = panel._size("maxHeight") || "";
		view1.add(view2).children("div.datagrid-body").css({
			marginTop : marginTop,
			height : (isNaN(parseInt(opts.height)) ? "" : (pheight - headerheight)),
			minHeight : (panelminHeight ? panelminHeight - panelheight - headerheight : ""),
			maxHeight : (panelmaxHeight ? panelmaxHeight - panelheight - headerheight : "")
		});
		view.height(view2.height());
	};
	function fixRowHeight(target, id, _bool) {
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		if (!dc.body1.is(":empty") && (!opts.nowrap || opts.autoRowHeight || _bool)) {
			if (id != undefined) {
				var tr1 = opts.finder.getTr(target, id, "body", 1);
				var tr2 = opts.finder.getTr(target, id, "body", 2);
				_setMaxHeight(tr1, tr2);
			} else {
				var tr1 = opts.finder.getTr(target, 0, "allbody", 1);
				var tr2 = opts.finder.getTr(target, 0, "allbody", 2);
				_setMaxHeight(tr1, tr2);
				if (opts.showFooter) {
					var tr1 = opts.finder.getTr(target, 0, "allfooter", 1);
					var tr2 = opts.finder.getTr(target, 0, "allfooter", 2);
					_setMaxHeight(tr1, tr2);
				}
			}
		}
		setSize(target);
		if (opts.height == "auto") {
			var parent = dc.body1.parent();
			var body2 = dc.body2;
			var hw = _firameWidthHeight(body2);
			var height = hw.height;
			if (hw.width > body2.width()) {
				height += 18;
			}
			height -= parseInt(body2.css("marginTop")) || 0;
			parent.height(height);
			body2.height(height);
			dc.view.height(dc.view2.height());
		}
		dc.body2.triggerHandler("scroll");
		function _setMaxHeight(_tr1, _tr2) {
			for (var i = 0; i < _tr2.length; i++) {
				var tr1 = $(_tr1[i]);
				var tr2 = $(_tr2[i]);
				tr1.css("height", "");
				tr2.css("height", "");
				var maxHeight = Math.max(tr1.height(), tr2.height());
				tr1.css("height", maxHeight);
				tr2.css("height", maxHeight);
			}
		};
		function _firameWidthHeight(body) {
			var outerWidth = 0;
			var outerHeigh = 0;
			$(body).children().each(function() {
				var c = $(this);
				if (c.is(":visible")) {
					outerHeigh += c._outerHeight();
					if (outerWidth < c._outerWidth()) {
						outerWidth = c._outerWidth();
					}
				}
			});
			return {
				width : outerWidth,
				height : outerHeigh
			};
		};
	};
	
	function freezeRow(target, id) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		if (!dc.body2.children("table.datagrid-btable-frozen").length) {
			dc.body1.add(dc.body2).prepend("<table class=\"datagrid-btable datagrid-btable-frozen\" cellspacing=\"0\" cellpadding=\"0\"></table>");
		}
		_btableFrozen(true);
		_btableFrozen(false);
		setSize(target);
		function _btableFrozen(viewType) {
			var _btr = viewType ? 1 : 2;
			var tr = opts.finder.getTr(target, id, "body", _btr);
			(viewType ? dc.body1 : dc.body2).children("table.datagrid-btable-frozen").append(tr);
		}
		;
	};
	function buildDatagrid(target, rownumbers) {
		function _createDatagridColumns() {
			var frozenColumns = [];
			var columns = [];
			$(target).children("thead").each(function() {
				var opt = $.parser.parseOptions(this, [ {
					frozen : "boolean"
				} ]);
				$(this).find("tr").each(function() {
					var colarray = [];
					$(this).find("th").each(function() {
					var th = $(this);
					var col = $.extend({},$.parser.parseOptions(this,
						["field",
						"align",
						"halign",
						"order",
						"width",{
						sortable : "boolean",
						checkbox : "boolean",
						resizable : "boolean",
						fixed : "boolean"
						},{
						rowspan : "number",
						colspan : "number"
						}]),{
						title : (th.html() || undefined),
						hidden : (th.attr("hidden") ? true : undefined),
						formatter : (th.attr("formatter") ? eval(th.attr("formatter")) : undefined),
						styler : (th.attr("styler") ? eval(th.attr("styler")) : undefined),
						sorter : (th.attr("sorter") ? eval(th.attr("sorter")) : undefined)
						});
						if (col.width&& String(col.width).indexOf("%") == -1) {
							col.width = parseInt(col.width);
						}
						if (th.attr("editor")) {
							var s = $.trim(th.attr("editor"));
							if (s.substr(0,1) == "{") {
								col.editor = eval("("+ s+ ")");
							} else {
								col.editor = s;
							}
						}
						colarray.push(col);
					});
					opt.frozen ? frozenColumns.push(colarray) : columns.push(colarray);
				});
			});
			return [ frozenColumns, columns ];
		};
		var datagridWrap = $("<div class=\"datagrid-wrap\">"
						+ "<div class=\"datagrid-view\">"
						+ "<div class=\"datagrid-view1\">"
						+ "<div class=\"datagrid-header\">"
						+ "<div class=\"datagrid-header-inner\"></div>"
						+ "</div>" + "<div class=\"datagrid-body\">"
						+ "<div class=\"datagrid-body-inner\"></div>"
						+ "</div>" + "<div class=\"datagrid-footer\">"
						+ "<div class=\"datagrid-footer-inner\"></div>"
						+ "</div>" + "</div>"
						+ "<div class=\"datagrid-view2\">"
						+ "<div class=\"datagrid-header\">"
						+ "<div class=\"datagrid-header-inner\"></div>"
						+ "</div>" + "<div class=\"datagrid-body\"></div>"
						+ "<div class=\"datagrid-footer\">"
						+ "<div class=\"datagrid-footer-inner\"></div>"
						+ "</div>" + "</div>" + "</div>" + "</div>").insertAfter(target);
		datagridWrap.panel({
			doSize : false,
			cls : "datagrid"
		});
		//表格可以暂时不需要。2015-5-20
		var tableWarp=$('<div class="datagrid-top-wrap" style="position: relative;"></div>');
		tableWarp.data("datagridWrap",datagridWrap);  
		datagridWrap.parent().wrap(tableWarp);
		$(target).hide().appendTo(datagridWrap.children("div.datagrid-view"));
		var cc = _createDatagridColumns();
		var view = datagridWrap.children("div.datagrid-view");
		var view1 = view.children("div.datagrid-view1");
		var view2 = view.children("div.datagrid-view2");
		return {
			panel : datagridWrap,
			frozenColumns : cc[0],
			columns : cc[1],
			dc : {
				view : view,
				view1 : view1,
				view2 : view2,
				header1 : view1.children("div.datagrid-header").children("div.datagrid-header-inner"),
				header2 : view2.children("div.datagrid-header").children("div.datagrid-header-inner"),
				body1 : view1.children("div.datagrid-body").children("div.datagrid-body-inner"),
				body2 : view2.children("div.datagrid-body"),
				footer1 : view1.children("div.datagrid-footer").children("div.datagrid-footer-inner"),
				footer2 : view2.children("div.datagrid-footer").children("div.datagrid-footer-inner")
			}
		};
	};
	function createDatagrid(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		var panel = datagrid.panel;
		datagrid.ss = $(target).datagrid("createStyleSheet");
		panel.panel($.extend({}, opts, {
			id : null,
			doSize : false,
			onResize : function(width, height) {
				setTimeout(function() {
					if ($.data(target, "datagrid")) {
						setSize(target);
						fitColumns(target);
						opts.onResize.call(panel, width, height);
					}
				}, 0);
			},
			onExpand : function() {
				fixRowHeight(target);
				opts.onExpand.call(panel);
			}
		}));
		datagrid.rowIdPrefix = "datagrid-row-r" + (++_1);
		datagrid.cellClassPrefix = "datagrid-cell-c" + _1;
		_createColumns(dc.header1, opts.frozenColumns, true);
		_createColumns(dc.header2, opts.columns, false);
		_setDatagridPrefix();
		dc.header1.add(dc.header2).css("display",opts.showHeader ? "block" : "none");
		dc.footer1.add(dc.footer2).css("display",opts.showFooter ? "block" : "none");
		if (opts.toolbar) {
			if ($.isArray(opts.toolbar)) {
				$("div.datagrid-toolbar", panel).remove();
				var tb = $("<div class=\"datagrid-toolbar\"><table cellspacing=\"0\" cellpadding=\"0\"><tr></tr></table></div>").prependTo(panel);
				var tr = tb.find("tr");
				for (var i = 0; i < opts.toolbar.length; i++) {
					var btn = opts.toolbar[i];
					if (btn == "-") {
						$("<td><div class=\"datagrid-btn-separator\"></div></td>").appendTo(tr);
					} else {
						var td = $("<td></td>").appendTo(tr);
						var _a = $("<a href=\"javascript:void(0)\"></a>").appendTo(td);
						_a[0].onclick = eval(btn.handler || function() { });
						_a.linkbutton($.extend({}, btn, {
							plain : true
						}));
					}
				}
			} else {
				$(opts.toolbar).addClass("datagrid-toolbar").prependTo(panel);
				$(opts.toolbar).show();
			}
		} else {
			$("div.datagrid-toolbar", panel).remove();
		}
		$("div.datagrid-pager", panel).remove();
		if (opts.pagination) {
			var pager = $("<div class=\"datagrid-pager pager-default\"></div>");
			if (opts.pagePosition == "bottom") {
				pager.appendTo(panel);
			} else {
				if (opts.pagePosition == "top") {
					pager.addClass("datagrid-pager-top").prependTo(panel);
				} else {
					var pagertop = $("<div class=\"datagrid-pager datagrid-pager-top\"></div>").prependTo(panel);
					pager.appendTo(panel);
					pager = pager.add(pagertop);
				}
			}
			pager.pagination({
				total : (opts.pageNumber * opts.pageSize),
				pageNumber : opts.pageNumber,
				pageSize : opts.pageSize,
				pageList : opts.pageList,
				buttons :opts.pageButtons,
				buttonPosition: opts.buttonPosition,
				showPageList :opts.showPageList,
				showPageStyle :opts.showPageStyle,
				showMsg:opts.showMsg,
				onSelectPage : function(pageNumber, pageSize) {
					opts.pageNumber = pageNumber;
					opts.pageSize = pageSize;
//					pager.pagination("refresh", {
//						pageNumber : pageNumber,
//						pageSize : pageSize
//					});
					request(target);
				}
			});
			opts.pageSize = pager.pagination("options").pageSize;
		}
		function _createColumns(headerView, columns, _bool) {
			if (!columns) {
				return;
			}
			$(headerView).show();
			$(headerView).empty();
			var sortName = [];
			var sortOrder = [];
			if (opts.sortName) {
				sortName = opts.sortName.split(",");
				sortOrder = opts.sortOrder.split(",");
			}
			var t = $("<table class=\"datagrid-htable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody></tbody></table>").appendTo(headerView);
			for (var i = 0; i < columns.length; i++) {
				var tr = $("<tr class=\"datagrid-header-row\"></tr>").appendTo($("tbody", t));
				var column = columns[i];
				for (var j = 0; j < column.length; j++) {
					var col = column[j];
					var rowspanHtml = "";
					if (col.rowspan) {
						rowspanHtml += "rowspan=\"" + col.rowspan + "\" ";
					}
					if (col.colspan) {
						rowspanHtml += "colspan=\"" + col.colspan + "\" ";
					}
					var td = $("<td " + rowspanHtml + "></td>").appendTo(tr);
					if (col.checkbox) {
						td.attr("field", col.field);
						$("<div class=\"datagrid-header-check\"></div>").html("<input type=\"checkbox\"/>").appendTo(td);
					} else {
						if (col.field) {
							td.attr("field", col.field);
							td.append("<div class=\"datagrid-cell\"><span></span><span class=\"datagrid-sort-icon\"></span></div>");
							//
							if(col.titleFn){
								$("span", td).html(col.titleFn(col.title,col));
							}else{
								$("span", td).html(col.title);
							}
							$("span.datagrid-sort-icon", td).html("&nbsp;");
							var cell = td.find("div.datagrid-cell");
							var pos = findIndex(sortName, col.field);
							if (pos >= 0) {
								cell.addClass("datagrid-sort-" + sortOrder[pos]);
							}
							if (col.resizable == false) {
								cell.attr("resizable", "false");
							}
							if (col.width) {
								var colWidth = $.parser.parseValue("width",col.width, dc.view, opts.scrollbarSize);
								cell._outerWidth(colWidth - 1);
								col.boxWidth = parseInt(cell[0].style.width);
								col.deltaWidth = colWidth - col.boxWidth;
							} else {
								col.auto = true;
							}
							cell.css("text-align",(col.halign || col.align || ""));
							col.cellClass = datagrid.cellClassPrefix + "-" + col.field.replace(/[\.|\s]/g, "-");
							cell.addClass(col.cellClass).css("width", "");
						} else {
							$("<div class=\"datagrid-cell-group\"></div>").html(col.title).appendTo(td);
						}
					}
					if (col.hidden) {
						td.hide();
					}
				}
			}
			if (_bool && opts.rownumbers) {
				var _td = $("<td rowspan=\"" + opts.frozenColumns.length + "\"><div class=\"datagrid-header-rownumber\"></div></td>");
				if ($("tr", t).length == 0) {
					_td.wrap("<tr class=\"datagrid-header-row\"></tr>").parent().appendTo($("tbody", t));
				} else {
					_td.prependTo($("tr:first", t));
				}
			}
		};
		function _setDatagridPrefix() {
			var colArray = [];
			var _fields = getColumnFields(target, true).concat(getColumnFields(target));
			for (var i = 0; i < _fields.length; i++) {
				var col = getColumnOption(target, _fields[i]);
				if (col && !col.checkbox) {
					colArray.push([ "." + col.cellClass,col.boxWidth ? col.boxWidth + "px" : "auto" ]);
				}
			}
			datagrid.ss.add(colArray);
			datagrid.ss.dirty(datagrid.cellSelectorPrefix);
			datagrid.cellSelectorPrefix = "." + datagrid.cellClassPrefix;
		};
	};
	function bindEvent(target) {
		var datagrid = $.data(target, "datagrid");
		var panel = datagrid.panel;
		var opts = datagrid.options;
		var dc = datagrid.dc;
		var headers = dc.header1.add(dc.header2);
		headers.find("input[type=checkbox]").unbind(".datagrid").bind("click.datagrid", function(e) {
			if (opts.singleSelect && opts.selectOnCheck) {
				return false;
			}
			if ($(this).is(":checked")) {
				checkAll(target);
			} else {
				uncheckAll(target);
			}
			e.stopPropagation();
		});
		var datagridCells = headers.find("div.datagrid-cell");
		datagridCells.closest("td").unbind(".datagrid").bind("mouseenter.datagrid",function() {
			if (datagrid.resizing) {
				return;
			}
			$(this).addClass("datagrid-header-over");
		}).bind("mouseleave.datagrid", function() {
			$(this).removeClass("datagrid-header-over");
		}).bind("contextmenu.datagrid", function(e) {
			var fieldLabel = $(this).attr("field");
			opts.onHeaderContextMenu.call(target, e, fieldLabel);
		});
		datagridCells.unbind(".datagrid").bind("click.datagrid", function(e) {
			var p1 = $(this).offset().left + 5;
			var p2 = $(this).offset().left + $(this)._outerWidth() - 5;
			if (e.pageX < p2 && e.pageX > p1) {
				sortGrid(target, $(this).parent().attr("field"));
			}
		}).bind("dblclick.datagrid",function(e) {
			var p1 = $(this).offset().left + 5;
			var p2 = $(this).offset().left + $(this)._outerWidth() - 5;
			var _7d = opts.resizeHandle == "right" ? (e.pageX > p2) : (opts.resizeHandle == "left" ? (e.pageX < p1) : (e.pageX < p1 || e.pageX > p2));
			if (_7d) {
				var pfieldLebel = $(this).parent().attr("field");
				var col = getColumnOption(target, pfieldLebel);
				if (col.resizable == false) {
					return;
				}
				$(target).datagrid("autoSizeColumn", pfieldLebel);
				col.auto = false;
			}
		});
		var ew = opts.resizeHandle == "right" ? "e" : (opts.resizeHandle == "left" ? "w" : "e,w");
		datagridCells.each(function() {$(this).resizable({
		handles : ew,
		disabled : ($(this).attr("resizable") ? $(this).attr("resizable") == "false" : false),
		minWidth : 25,
		onStartResize : function(e) {
			datagrid.resizing = true;
			headers.css("cursor", $("body").css("cursor"));
			if (!datagrid.proxy) {
				datagrid.proxy = $("<div class=\"datagrid-resize-proxy\"></div>").appendTo(dc.view);
			}
			datagrid.proxy.css({
				left : e.pageX - $(panel).offset().left - 1,
				display : "none"
			});
			setTimeout(function() {
				if (datagrid.proxy) {
					datagrid.proxy.show();
				}
			}, 500);
		},
		onResize : function(e) {
			datagrid.proxy.css({
				left : e.pageX - $(panel).offset().left - 1,
				display : "block"
			});
			return false;
		},
		onStopResize : function(e) {
			headers.css("cursor", "");
			$(this).css("height", "");
			var pfieldLebel = $(this).parent().attr("field");
			var col = getColumnOption(target, pfieldLebel);
			col.width = $(this)._outerWidth();
			col.boxWidth = col.width - col.deltaWidth;
			col.auto = undefined;
			$(this).css("width", "");
			fixColumnSize(target, pfieldLebel);
			datagrid.proxy.remove();
			datagrid.proxy = null;
			if ($(this).parents("div:first.datagrid-header").parent().hasClass("datagrid-view1")) {
				setSize(target);
			}
			fitColumns(target);
			opts.onResizeColumn.call(target, pfieldLebel,col.width);
			setTimeout(function() {
				datagrid.resizing = false;
			}, 0);
		}});});
		dc.body1.add(dc.body2).unbind().bind("mouseover", function(e) {
			if (datagrid.resizing) {
				return;
			}
			var tr = $(e.target).closest("tr.datagrid-row");
			if (!_trlen(tr)) {
				return;
			}
			var id = _rowIndexId(tr);
			highlightRow(target, id);
			e.stopPropagation();
		}).bind("mouseout", function(e) {
			var tr = $(e.target).closest("tr.datagrid-row");
			if (!_trlen(tr)) {
				return;
			}
			var id = _rowIndexId(tr);
			opts.finder.getTr(target, id).removeClass("datagrid-row-over");
			e.stopPropagation();
		}).bind("click", function(e) {
			var tt = $(e.target);
			var tr = tt.closest("tr.datagrid-row");
			if (!_trlen(tr)) {
				return;
			}
			var id = _rowIndexId(tr);
			if (tt.parent().hasClass("datagrid-cell-check")) {
				if (opts.singleSelect && opts.selectOnCheck) {
					if (!opts.checkOnSelect) {
						uncheckAll(target, true);
					}
					checkRow(target, id);
				} else {
					if (tt.is(":checked")) {
						checkRow(target, id);
					} else {
						uncheckRow(target, id);
					}
				}
			} else {
				var row = opts.finder.getRow(target, id);
				var td = tt.closest("td[field]", tr);
				if (td.length) {
					var fieldLabel = td.attr("field");
					if(opts.showEditor){
						setCellEditing(target,id,fieldLabel);
					}
					opts.onClickCell.call(target, id, fieldLabel, row[fieldLabel]);
				}
				if (opts.singleSelect == true) {
					selectRow(target, id);
				} else {
					if (opts.ctrlSelect) {
						if (e.ctrlKey) {
							if (tr.hasClass("datagrid-row-selected")) {
								unselectRow(target, id);
							} else {
								selectRow(target, id);
							}
						} else {
							$(target).datagrid("clearSelections");
							selectRow(target, id);
						}
					} else {
						if (tr.hasClass("datagrid-row-selected")) {
							unselectRow(target, id);
						} else {
							selectRow(target, id);
						}
					}
				}
				opts.onClickRow.call(target, id, row);
			}
			e.stopPropagation();
		}).bind("dblclick", function(e) {
			var tt = $(e.target);
			var tr = tt.closest("tr.datagrid-row");
			if (!_trlen(tr)) {
				return;
			}
			var id = _rowIndexId(tr);
			var row = opts.finder.getRow(target, id);
			var td = tt.closest("td[field]", tr);
			if (td.length) {
				var fieldLabel = td.attr("field");
				opts.onDblClickCell.call(target, id, fieldLabel, row[fieldLabel]);
			}
			opts.onDblClickRow.call(target, id, row);
			e.stopPropagation();
		}).bind("contextmenu", function(e) {
			var tr = $(e.target).closest("tr.datagrid-row");
			if (!_trlen(tr)) {
				return;
			}
			var id = _rowIndexId(tr);
			var row = opts.finder.getRow(target, id);
			opts.onRowContextMenu.call(target, e, id, row);
			e.stopPropagation();
		});
		dc.body2.bind("scroll", function() {
			var body = dc.view1.children("div.datagrid-body");
			body.scrollTop($(this).scrollTop());
			var body1 = dc.body1.children(":first");
			var body2 = dc.body2.children(":first");
			if (body1.length && body2.length) {
				var body1Top = body1.offset().top;
				var body2Top = body2.offset().top;
				if (body1Top != body2Top) {
					body.scrollTop(body.scrollTop() + body1Top - body2Top);
				}
			}
			dc.view2.children("div.datagrid-header,div.datagrid-footer")._scrollLeft($(this)._scrollLeft());
			dc.body2.children("table.datagrid-btable-frozen").css("left",-$(this)._scrollLeft());
		});
		function _rowIndexId(tr) {
			if (tr.attr("datagrid-row-index")) {
				return parseInt(tr.attr("datagrid-row-index"));
			} else {
				return tr.attr("node-id");
			}
		};
		function _trlen(tr) {
			return tr.length && tr.parent().length;
		};
	};
	function sortGrid(target, sortParam) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		sortParam = sortParam || {};
		var _sortParam = {
			sortName : opts.sortName,
			sortOrder : opts.sortOrder
		};
		if (typeof sortParam == "object") {
			$.extend(_sortParam, sortParam);
		}
		var sortNameArray = [];
		var sortOrderArray = [];
		if (_sortParam.sortName) {
			sortNameArray = _sortParam.sortName.split(",");
			sortOrderArray = _sortParam.sortOrder.split(",");
		}
		if (typeof sortParam == "string") {
			var sortName = sortParam;
			var col = getColumnOption(target, sortName);
			if (!col.sortable || datagrid.resizing) {
				return;
			}
			var colOrder = col.order || "asc";
			var pos = findIndex(sortNameArray, sortName);
			if (pos >= 0) {
				var sortType = sortOrderArray[pos] == "asc" ? "desc" : "asc";
				if (opts.multiSort && sortType == colOrder) {
					sortNameArray.splice(pos, 1);
					sortOrderArray.splice(pos, 1);
				} else {
					sortOrderArray[pos] = sortType;
				}
			} else {
				if (opts.multiSort) {
					sortNameArray.push(sortName);
					sortOrderArray.push(colOrder);
				} else {
					sortNameArray = [ sortName ];
					sortOrderArray = [ colOrder ];
				}
			}
			_sortParam.sortName = sortNameArray.join(",");
			_sortParam.sortOrder = sortOrderArray.join(",");
		}
		if (opts.onBeforeSortColumn.call(target, _sortParam.sortName, _sortParam.sortOrder) == false) {
			return;
		}
		$.extend(opts, _sortParam);
		var dc = datagrid.dc;
		var headers = dc.header1.add(dc.header2);
		headers.find("div.datagrid-cell").removeClass("datagrid-sort-asc datagrid-sort-desc");
		for (var i = 0; i < sortNameArray.length; i++) {
			var col = getColumnOption(target, sortNameArray[i]);
			headers.find("div." + col.cellClass).addClass("datagrid-sort-" + sortOrderArray[i]);
		}
		if (opts.remoteSort) {
			request(target);
		} else {
			renderDatagridLoadData(target, $(target).datagrid("getData"));
		}
		opts.onSortColumn.call(target, opts.sortName, opts.sortOrder);
	};
	function fitColumns(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		var headers = dc.view2.children("div.datagrid-header");
		dc.body2.css("overflow-x", "");
		_setFixColumnSize();
		_a0();
		if (headers.width() >= headers.find("table").width()) {
			dc.body2.css("overflow-x", "hidden");
		}
		function _a0() {
			if (!opts.fitColumns) {
				return;
			}
			if (!datagrid.leftWidth) {
				datagrid.leftWidth = 0;
			}
			var _a1 = 0;
			var cc = [];
			var _a2 = getColumnFields(target, false);
			for (var i = 0; i < _a2.length; i++) {
				var col = getColumnOption(target, _a2[i]);
				if (_a3(col)) {
					_a1 += col.width;
					cc.push({
						field : col.field,
						col : col,
						addingWidth : 0
					});
				}
			}
			if (!_a1) {
				return;
			}
			cc[cc.length - 1].addingWidth -= datagrid.leftWidth;
			var _a4 = headers.children("div.datagrid-header-inner").show();
			var _a5 = headers.width() - headers.find("table").width() - opts.scrollbarSize + datagrid.leftWidth;
			var _a6 = _a5 / _a1;
			if (!opts.showHeader) {
				_a4.hide();
			}
			for (var i = 0; i < cc.length; i++) {
				var c = cc[i];
				var _a7 = parseInt(c.col.width * _a6);
				c.addingWidth += _a7;
				_a5 -= _a7;
			}
			if(_a5>0){
				_a5=0-_a5;
			}
			cc[cc.length - 1].addingWidth += _a5;
			for (var i = 0; i < cc.length; i++) {
				var c = cc[i];
				if (c.col.boxWidth + c.addingWidth > 0) {
					c.col.boxWidth += c.addingWidth;
					c.col.width += c.addingWidth;
				}
			}
			datagrid.leftWidth = _a5;
			fixColumnSize(target);
		};
		function _setFixColumnSize() {
			var _bool = false;
			var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
			$.map(fields, function(_aa) {
				var col = getColumnOption(target, _aa);
				if (String(col.width || "").indexOf("%") >= 0) {
					var colWidth = $.parser.parseValue("width", col.width, dc.view, opts.scrollbarSize) - col.deltaWidth;
					if (colWidth > 0) {
						col.boxWidth = colWidth;
						_bool = true;
					}
				}
			});
			if (_bool) {
				fixColumnSize(target);
			}
		};
		function _a3(col) {
			if (String(col.width || "").indexOf("%") >= 0) {
				return false;
			}
			if (!col.hidden && !col.checkbox && !col.auto && !col.fixed) {
				return true;
			}
		};
	};
	function autoSizeColumn(target, fieldLabel) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		var tmp = $("<div class=\"datagrid-cell\" style=\"position:absolute;left:-9999px\"></div>").appendTo("body");
		if (fieldLabel) {
			_resizeGrid(fieldLabel);
			if (opts.fitColumns) {
				setSize(target);
				fitColumns(target);
			}
		} else {
			var _b = false;
			var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i];
				var col = getColumnOption(target, field);
				if (col.auto) {
					_resizeGrid(field);
					_b = true;
				}
			}
			if (_b && opts.fitColumns) {
				setSize(target);
				fitColumns(target);
			}
		}
		tmp.remove();
		function _resizeGrid(_fieldLabel) {
			var datagridCell = dc.view.find("div.datagrid-header td[field=\"" + _fieldLabel + "\"] div.datagrid-cell");
			datagridCell.css("width", "");
			var col = $(target).datagrid("getColumnOption", _fieldLabel);
			col.width = undefined;
			col.boxWidth = undefined;
			col.auto = true;
			$(target).datagrid("fixColumnSize", _fieldLabel);
			var _b5 = Math.max(_getCellWidth("header"), _getCellWidth("allbody"), _getCellWidth("allfooter")) + 1;
			datagridCell._outerWidth(_b5 - 1);
			col.width = _b5;
			col.boxWidth = parseInt(datagridCell[0].style.width);
			col.deltaWidth = _b5 - col.boxWidth;
			datagridCell.css("width", "");
			$(target).datagrid("fixColumnSize", _fieldLabel);
			opts.onResizeColumn.call(target, _fieldLabel, col.width);
			function _getCellWidth(viewType) {
				var cellWidth = 0;
				if (viewType == "header") {
					cellWidth = _b9(datagridCell);
				} else {
					opts.finder.getTr(target, 0, viewType).find("td[field=\"" + _fieldLabel + "\"] div.datagrid-cell").each(function() {
						var w = _b9($(this));
						if (cellWidth < w) {
							cellWidth = w;
						}
					});
				}
				return cellWidth;
				function _b9(_cell) {
					return _cell.is(":visible") ? _cell._outerWidth() : tmp.html(_cell.html())._outerWidth();
				};
			};
		};
	};
	function fixColumnSize(target, index) {
		var datagrid = $.data(target, "datagrid");
		var dc = datagrid.dc;
		var ftable = dc.view.find("table.datagrid-btable,table.datagrid-ftable");
		ftable.css("table-layout", "fixed");
		if (index) {
			fix(index);
		} else {
			var ff = getColumnFields(target, true).concat(getColumnFields(target, false));
			for (var i = 0; i < ff.length; i++) {
				fix(ff[i]);
			}
		}
		ftable.css("table-layout", "auto");
		setMergedWidth(target);
		fixRowHeight(target);
		setEditableWidth(target);
		function fix(indx) {
			var col = getColumnOption(target, indx);
			if (col.cellClass) {
				datagrid.ss.set("." + col.cellClass, col.boxWidth ? col.boxWidth + "px" : "auto");
			}
		};
	};
	function setMergedWidth(target) {
		var dc = $.data(target, "datagrid").dc;
		dc.view.find("td.datagrid-td-merged").each(function() {
			var td = $(this);
			var colspan = td.attr("colspan") || 1;
			var col = getColumnOption(target, td.attr("field"));
			var boxWidth = col.boxWidth + col.deltaWidth - 1;
			for (var i = 1; i < colspan; i++) {
				td = td.next();
				col = getColumnOption(target, td.attr("field"));
				boxWidth += col.boxWidth + col.deltaWidth;
			}
			$(this).children("div.datagrid-cell")._outerWidth(boxWidth);
		});
	};
	function setEditableWidth(target) {
		var dc = $.data(target, "datagrid").dc;
		dc.view.find("div.datagrid-editable").each(function() {
			var editable = $(this);
			var pfield = editable.parent().attr("field");
			var col = $(target).datagrid("getColumnOption", pfield);
			editable._outerWidth(col.boxWidth + col.deltaWidth - 1);
			var ed = $.data(this, "datagrid.editor");
			if (ed.actions.resize) {
				ed.actions.resize(ed.target, editable.width());
			}
		});
	};
	function getColumnOption(target, _cb) {
		function _cc(_cd) {
			if (_cd) {
				for (var i = 0; i < _cd.length; i++) {
					var cc = _cd[i];
					for (var j = 0; j < cc.length; j++) {
						var c = cc[j];
						if (c.field == _cb) {
							return c;
						}
					}
				}
			}
			return null;
		};
		var opts = $.data(target, "datagrid").options;
		var col = _cc(opts.columns);
		if (!col) {
			col = _cc(opts.frozenColumns);
		}
		return col;
	};
	function getColumnFields(target, _bool) {
		var opts = $.data(target, "datagrid").options;
		var array = (_bool == true) ? (opts.frozenColumns || [ [] ]) : opts.columns;
		if (array.length == 0) {
			return [];
		}
		var columns = [];
		var _index = _mapColspanIndex();
		for (var i = 0; i < array.length; i++) {
			columns[i] = new Array(_index);
		}
		for (var _n = 0; _n < array.length; _n++) {
			$.map(array[_n], function(col) {
				var _inx = _isUndefined(columns[_n]);
				if (_inx >= 0) {
					var _d8 = col.field || "";
					for (var c = 0; c < (col.colspan || 1); c++) {
						for (var r = 0; r < (col.rowspan || 1); r++) {
							columns[_n + r][_inx] = _d8;
						}
						_inx++;
					}
				}
			});
		}
		return columns[columns.length - 1];
		function _mapColspanIndex() {
			var index = 0;
			$.map(array[0], function(col) {
				index += col.colspan || 1;
			});
			return index;
		};
		function _isUndefined(a) {
			for (var i = 0; i < a.length; i++) {
				if (a[i] == undefined) {
					return i;
				}
			}
			return -1;
		};
	};
	function renderDatagridLoadData(target, data) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		data = opts.loadFilter.call(target, data);
		data.total = parseInt(data.total);
		datagrid.data = data;
		if (data.footer) {
			datagrid.footer = data.footer;
		}
		if(data.total <=0 && opts.emptyMessage){
			dc.view1.children("div.datagrid-header").hide();
			dc.view2.children("div.datagrid-header").hide();
		}else{
			dc.view1.children("div.datagrid-header").show();
			dc.view2.children("div.datagrid-header").show();
		}
		if (opts.toolbar && $(opts.toolbar).length) {
			if(data.total <=0 && opts.emptyMessage){
				$(opts.toolbar).hide();
			}else{
				$(opts.toolbar).show();
			}
		}
		
		if (!opts.remoteSort && opts.sortName) {
			var sortNameArray = opts.sortName.split(",");
			var sortOrderArray = opts.sortOrder.split(",");
			data.rows.sort(function(r1, r2) {
				var r = 0;
				for (var i = 0; i < sortNameArray.length; i++) {
					var sn = sortNameArray[i];
					var so = sortOrderArray[i];
					var col = getColumnOption(target, sn);
					var _e0 = col.sorter || function(a, b) {
						return a == b ? 0 : (a > b ? 1 : -1);
					};
					r = _e0(r1[sn], r2[sn]) * (so == "asc" ? 1 : -1);
					if (r != 0) {
						return r;
					}
				}
				return r;
			});
		}
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, target, data.rows);
		}
		opts.view.render.call(opts.view, target, dc.body2, false);
		opts.view.render.call(opts.view, target, dc.body1, true);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, target, dc.footer2, false);
			opts.view.renderFooter.call(opts.view, target, dc.footer1, true);
		}
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, target);
		}
		datagrid.ss.clean();
		var pager = $(target).datagrid("getPager");
		if(data.total <=0 && opts.emptyMessage){
			pager.hide();
		}else{
			pager.show();
			if (pager.length) {
				var pagerOpts = pager.pagination("options");
				if (pagerOpts.total != data.total) {
					pager.pagination("refresh", {
						total : data.total
					});
					if (data.total>0 && opts.pageNumber != pagerOpts.pageNumber) {
						opts.pageNumber = pagerOpts.pageNumber;
						request(target);
					}
				}
			}
		}
		
		
		fixRowHeight(target);
		dc.body2.triggerHandler("scroll");
		setSelectionState(target);
		autoSizeColumn(target);
		opts.onLoadSuccess.call(target, data);
		resizeGrid(target);
		
	};
	function setSelectionState(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var dc = datagrid.dc;
		dc.header1.add(dc.header2).find("input[type=checkbox]")._propAttr("checked", false);
		if (opts.idField) {
			var isTree = $.data(target, "treegrid") ? true : false;
			var onSelect = opts.onSelect;
			var onCheck = opts.onCheck;
			opts.onSelect = opts.onCheck = function() {
			};
			var rows = opts.finder.getRows(target);
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var idField = isTree ? row[opts.idField] : i;
				if (_compareIdField(datagrid.selectedRows, row)) {
					selectRow(target, idField, true);
				}
				if (_compareIdField(datagrid.checkedRows, row)) {
					checkRow(target, idField, true);
				}
			}
			opts.onSelect = onSelect;
			opts.onCheck = onCheck;
		}
		//2016-1-29增加，当前为checkd时，保存到选中去
		function _compareIdField(a, r) {
			for (var i = 0; i < a.length; i++) {
				if (a[i][opts.idField] == r[opts.idField]) {
					a[i] = r;
					return true;
				}
			}
			if(r["checked"]==true){
				return true;
			}else{
				return false;
			}
		};
	};
	function getRowIndex(target, row) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var rows = datagrid.data.rows;
		if (typeof row == "object") {
			return findIndex(rows, row);
		} else {
			for (var i = 0; i < rows.length; i++) {
				if (rows[i][opts.idField] == row) {
					return i;
				}
			}
			return -1;
		}
	};
	function getSelectedRow(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		if (opts.idField) {
			return datagrid.selectedRows;
		} else {
			var selectedArray = [];
			opts.finder.getTr(target, "", "selected", 2).each(function() {
				selectedArray.push(opts.finder.getRow(target, $(this)));
			});
			return selectedArray;
		}
	};
	function getChecked(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		if (opts.idField) {
			return datagrid.checkedRows;
		} else {
			var checkedArray = [];
			opts.finder.getTr(target, "", "checked", 2).each(function() {
				checkedArray.push(opts.finder.getRow(target, $(this)));
			});
			return checkedArray;
		}
	};
	function scrollTo(target, id) {
		var datagrid = $.data(target, "datagrid");
		var dc = datagrid.dc;
		var opts = datagrid.options;
		var tr = opts.finder.getTr(target, id);
		if (tr.length) {
			if (tr.closest("table").hasClass("datagrid-btable-frozen")) {
				return;
			}
			var headerOuterHeight = dc.view2.children("div.datagrid-header")._outerHeight();
			var body2 = dc.body2;
			var body2OuterHeight = body2.outerHeight(true) - body2.outerHeight();
			var top = tr.position().top - headerOuterHeight - body2OuterHeight;
			if (top < 0) {
				body2.scrollTop(body2.scrollTop() + top);
			} else {
				if (top + tr._outerHeight() > body2.height() - 18) {
					body2.scrollTop(body2.scrollTop() + top + tr._outerHeight() - body2.height() + 18);
				}
			}
		}
	};
	function highlightRow(target, id) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		opts.finder.getTr(target, datagrid.highlightIndex).removeClass("datagrid-row-over");
		opts.finder.getTr(target, id).addClass("datagrid-row-over");
		datagrid.highlightIndex = id;
	};
	function selectRow(target, id, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var selectedRows = datagrid.selectedRows;
		if (opts.singleSelect) {
			unselectAll(target);
			selectedRows.splice(0, selectedRows.length);
		}
		if (!_bool && opts.checkOnSelect) {
			checkRow(target, id, true);
		}
		var row = opts.finder.getRow(target, id);
		if (opts.idField) {
			pushNewObject(selectedRows, opts.idField, row);
		}
		opts.finder.getTr(target, id).addClass("datagrid-row-selected");
		opts.onSelect.call(target, id, row);
		scrollTo(target, id);
	};
	function unselectRow(target, id, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (!_bool && opts.checkOnSelect) {
			uncheckRow(target, id, true);
		}
		opts.finder.getTr(target, id).removeClass("datagrid-row-selected");
		var row = opts.finder.getRow(target, id);
		if (opts.idField) {
			deleteObject(selectedRows, opts.idField, row[opts.idField]);
		}
		opts.onUnselect.call(target, id, row);
	};
	function selectAll(target, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var rows = opts.finder.getRows(target);
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (!_bool && opts.checkOnSelect) {
			checkAll(target, true);
		}
		opts.finder.getTr(target, "", "allbody").addClass("datagrid-row-selected");
		if (opts.idField) {
			for (var _i = 0; _i < rows.length; _i++) {
				pushNewObject(selectedRows, opts.idField, rows[_i]);
			}
		}
		opts.onSelectAll.call(target, rows);
	};
	function unselectAll(target, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var rows = opts.finder.getRows(target);
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (!_bool && opts.checkOnSelect) {
			uncheckAll(target, true);
		}
		opts.finder.getTr(target, "", "selected").removeClass("datagrid-row-selected");
		if (opts.idField) {
			for (var _i = 0; _i < rows.length; _i++) {
				deleteObject(selectedRows, opts.idField, rows[_i][opts.idField]);
			}
		}
		opts.onUnselectAll.call(target, rows);
	};
	
	
	function checkRow(target, id, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		if (!_bool && opts.selectOnCheck) {
			selectRow(target, id, true);
		}
		var tr = opts.finder.getTr(target, id).addClass("datagrid-row-checked");
		var ck = tr.find("div.datagrid-cell-check input[type=checkbox]");
		ck._propAttr("checked", true);
		tr = opts.finder.getTr(target, "", "checked", 2);
		if (tr.length == opts.finder.getRows(target).length) {
			var dc = datagrid.dc;
			var headers = dc.header1.add(dc.header2);
			headers.find("input[type=checkbox]")._propAttr("checked", true);
		}
		var row = opts.finder.getRow(target, id);
		if (opts.idField) {
			pushNewObject(datagrid.checkedRows, opts.idField, row);
		}
		opts.onCheck.call(target, id, row);
	};
	
	function uncheckRow(target, id, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		if (!_bool && opts.selectOnCheck) {
			unselectRow(target, id, true);
		}
		var tr = opts.finder.getTr(target, id).removeClass("datagrid-row-checked");
		var ck = tr.find("div.datagrid-cell-check input[type=checkbox]");
		ck._propAttr("checked", false);
		var dc = datagrid.dc;
		var headers = dc.header1.add(dc.header2);
		headers.find("input[type=checkbox]")._propAttr("checked", false);
		var row = opts.finder.getRow(target, id);
		if (opts.idField) {
			deleteObject(datagrid.checkedRows, opts.idField, row[opts.idField]);
		}
		opts.onUncheck.call(target, id, row);
	};
	function checkAll(target, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var rows = opts.finder.getRows(target);
		if (!_bool && opts.selectOnCheck) {
			selectAll(target, true);
		}
		var dc = datagrid.dc;
		var hck = dc.header1.add(dc.header2).find("input[type=checkbox]");
		var bck = opts.finder.getTr(target, "", "allbody").addClass("datagrid-row-checked").find("div.datagrid-cell-check input[type=checkbox]");
		hck.add(bck)._propAttr("checked", true);
		if (opts.idField) {
			for (var i = 0; i < rows.length; i++) {
				pushNewObject(datagrid.checkedRows, opts.idField, rows[i]);
			}
		}
		opts.onCheckAll.call(target, rows);
	};
	function uncheckAll(target, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var rows = opts.finder.getRows(target);
		if (!_bool && opts.selectOnCheck) {
			unselectAll(target, true);
		}
		var dc = datagrid.dc;
		var hck = dc.header1.add(dc.header2).find("input[type=checkbox]");
		var bck = opts.finder.getTr(target, "", "checked").removeClass("datagrid-row-checked").find("div.datagrid-cell-check input[type=checkbox]");
		hck.add(bck)._propAttr("checked", false);
		if (opts.idField) {
			for (var i = 0; i < rows.length; i++) {
				deleteObject(datagrid.checkedRows, opts.idField, rows[i][opts.idField]);
			}
		}
		opts.onUncheckAll.call(target, rows);
	};
	
	
	function beginEdit(target, id) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, id);
		var row = opts.finder.getRow(target, id);
		if (tr.hasClass("datagrid-row-editing")) {
			return;
		}
		if (opts.onBeforeEdit.call(target, id, row) == false) {
			return;
		}
		tr.addClass("datagrid-row-editing");
		setContextmenu(target, id);
		setEditableWidth(target);
		tr.find("div.datagrid-editable").each(function() {
			var pfield = $(this).parent().attr("field");
			var ed = $.data(this, "datagrid.editor");
			ed.actions.setValue(ed.target, row[pfield]);
		});
		validateRow(target, id);
		opts.onBeginEdit.call(target, id, row);
	};
	function endEdit(target, id, _bool) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var updatedRows = datagrid.updatedRows;
		var insertedRows = datagrid.insertedRows;
		var tr = opts.finder.getTr(target, id);
		var row = opts.finder.getRow(target, id);
		if (!tr.hasClass("datagrid-row-editing")) {
			return;
		}
		var fieldObject = {};
		if (!_bool) {
			if (!validateRow(target, id)) {
				return;
			}
			var _b = false;
			tr.find("div.datagrid-editable").each(function() {
				var fieldLabel = $(this).parent().attr("field");
				var ed = $.data(this, "datagrid.editor");
				var fieldValue = ed.actions.getValue(ed.target);
				if (row[fieldLabel] != fieldValue) {
					row[fieldLabel] = fieldValue;
					_b = true;
					fieldObject[fieldLabel] = fieldValue;
				}
			});
			if (_b) {
				if (findIndex(insertedRows, row) == -1) {
					if (findIndex(updatedRows, row) == -1) {
						updatedRows.push(row);
					}
				}
			}
			opts.onEndEdit.call(target, id, row, fieldObject);
		}
		tr.removeClass("datagrid-row-editing");
		setEditableCell(target, id);
		$(target).datagrid("refreshRow", id);
		if (!_bool) {
			opts.onAfterEdit.call(target, id, row, fieldObject);
		} else {
			opts.onCancelEdit.call(target, id, row);
		}
	};
	
	function editCell(target,param){
		var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
		for(var i=0; i<fields.length; i++){
			var col = getColumnOption(target, fields[i]);
			col.editor1 = col.editor;
			if (fields[i] != param.field){
				col.editor = null;
			}
		}
		beginEdit(target,param.index);
		for(var n=0; n<fields.length; n++){
			var col_ = getColumnOption(target, fields[n]);
			col_.editor = col_.editor1;
		}
	}
	
	function getEditors(target, id) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, id);
		var tdArray = [];
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				tdArray.push(ed);
			}
		});
		return tdArray;
	};
	function getEditor(target, _14c) {
		var _14d = getEditors(target, _14c.index != undefined ? _14c.index : _14c.id);
		for (var i = 0; i < _14d.length; i++) {
			if (_14d[i].field == _14c.field) {
				return _14d[i];
			}
		}
		return null;
	};
	function setContextmenu(target, id) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, id);
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-cell");
			var fieldLabel = $(this).attr("field");
			var col = getColumnOption(target, fieldLabel);
			if (col && col.editor) {
				var editorType, editorOpts = null;
				if (typeof col.editor == "string") {
					editorType = col.editor;
				} else {
					editorType = col.editor.type;
					editorOpts = col.editor.options;
				}
				var editorsTool = opts.editors[editorType];
				if (editorsTool) {
					var cellHtml = cell.html();
					var cellWidth = cell._outerWidth();
					cell.addClass("datagrid-editable");
					cell._outerWidth(cellWidth);
					cell.html("<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>");
					cell.children("table").bind("click dblclick contextmenu",function(e) {
						e.stopPropagation();
					});
					$.data(cell[0], "datagrid.editor", {
						actions : editorsTool,
						target : editorsTool.init(cell.find("td"),editorOpts),
						field : fieldLabel,
						type : editorType,
						oldHtml : cellHtml
					});
				}
			}
		});
		fixRowHeight(target, id, true);
	};
	function setEditableCell(target, id) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, id);
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				if (ed.actions.destroy) {
					ed.actions.destroy(ed.target);
				}
				cell.html(ed.oldHtml);
				$.removeData(cell[0], "datagrid.editor");
				cell.removeClass("datagrid-editable");
				cell.css("width", "");
			}
		});
	};
	function validateRow(target, _159) {
		var tr = $.data(target, "datagrid").options.finder.getTr(target, _159);
		if (!tr.hasClass("datagrid-row-editing")) {
			return true;
		}
		var vbox = tr.find(".validatebox-text");
		vbox.validatebox("validate");
		vbox.trigger("mouseleave");
		var _15a = tr.find(".validatebox-invalid");
		return _15a.length == 0;
	};
	function getChanges(target, _15d) {
		var insertedRows = $.data(target, "datagrid").insertedRows;
		var deletedRows = $.data(target, "datagrid").deletedRows;
		var updatedRows = $.data(target, "datagrid").updatedRows;
		if (!_15d) {
			var rows = [];
			rows = rows.concat(insertedRows);
			rows = rows.concat(deletedRows);
			rows = rows.concat(updatedRows);
			return rows;
		} else {
			if (_15d == "inserted") {
				return insertedRows;
			} else {
				if (_15d == "deleted") {
					return deletedRows;
				} else {
					if (_15d == "updated") {
						return updatedRows;
					}
				}
			}
		}
		return [];
	};
	function deleteRow(target, index) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var data = datagrid.data;
		var insertedRows = datagrid.insertedRows;
		var deletedRows = datagrid.deletedRows;
		endEdit(target, index, true);
		var row = opts.finder.getRow(target, index);
		if (findIndex(insertedRows, row) >= 0) {
			deleteObject(insertedRows, row);
		} else {
			deletedRows.push(row);
		}
		deleteObject(datagrid.selectedRows, opts.idField, row[opts.idField]);
		deleteObject(datagrid.checkedRows, opts.idField, row[opts.idField]);
		data.total -= 1;
		data.rows.splice(index, 1);
		renderDatagridLoadData(target, data);
	};
	function insertRow(target, _169) {
		var data = $.data(target, "datagrid").data;
		var view = $.data(target, "datagrid").options.view;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		view.insertRow.call(view, target, _169.index, _169.row);
		insertedRows.push(_169.row);
		$(target).datagrid("getPager").pagination("refresh", {
			total : data.total
		});
	};
	function appendRow(target, row) {
		var data = $.data(target, "datagrid").data;
		var view = $.data(target, "datagrid").options.view;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		view.insertRow.call(view, target, null, row);
		insertedRows.push(row);
		$(target).datagrid("getPager").pagination("refresh", {
			total : data.total
		});
	};
	function loadData(target) {
		var datagrid = $.data(target, "datagrid");
		var data = datagrid.data;
		var rows = data.rows;
		var originalRows = [];
		for (var i = 0; i < rows.length; i++) {
			originalRows.push($.extend({}, rows[i]));
		}
		datagrid.originalRows = originalRows;
		datagrid.updatedRows = [];
		datagrid.insertedRows = [];
		datagrid.deletedRows = [];
	};
	function acceptChanges(target) {
		var data = $.data(target, "datagrid").data;
		var ok = true;
		for (var i = 0, len = data.rows.length; i < len; i++) {
			if (validateRow(target, i)) {
				endEdit(target, i, false);
			} else {
				ok = false;
			}
		}
		if (ok) {
			loadData(target);
		}
	};
	function rejectChanges(target) {
		var datagrid = $.data(target, "datagrid");
		var opts = datagrid.options;
		var originalRows = datagrid.originalRows;
		var insertedRows = datagrid.insertedRows;
		var deletedRows = datagrid.deletedRows;
		var selectedRows = datagrid.selectedRows;
		var checkedRows = datagrid.checkedRows;
		var data = datagrid.data;
		function _pushIds(a) {
			var ids = [];
			for (var i = 0; i < a.length; i++) {
				ids.push(a[i][opts.idField]);
			}
			return ids;
		};
		function _findRowIndex(ids, _17e) {
			for (var i = 0; i < ids.length; i++) {
				var id = getRowIndex(target, ids[i]);
				if (id >= 0) {
					(_17e == "s" ? selectRow : checkRow)(target, id, true);
				}
			}
		};
		for (var i = 0; i < data.rows.length; i++) {
			endEdit(target, i, true);
		}
		var selectedRowsIds = _pushIds(selectedRows);
		var checkedRowsIds = _pushIds(checkedRows);
		selectedRows.splice(0, selectedRows.length);
		checkedRows.splice(0, checkedRows.length);
		data.total += deletedRows.length - insertedRows.length;
		data.rows = originalRows;
		renderDatagridLoadData(target, data);
		_findRowIndex(selectedRowsIds, "s");
		_findRowIndex(checkedRowsIds, "c");
		loadData(target);
	};
	function request(target, param) {
		var opts = $.data(target, "datagrid").options;
		if (param) {
			opts.queryParams = param;
		}
		var optsQueryParams=opts.queryParams;
		if(typeof(opts.queryParams)=="function"){
			optsQueryParams=opts.queryParams();
		}
		var queryParams={};
		if(optsQueryParams){
			if(typeof(optsQueryParams)=="object"){
				queryParams=$.extend({},optsQueryParams);
				queryParams.queryString=$.parser.objConvertStr(optsQueryParams);
				if (opts.pagination) {
					$.extend(queryParams, {
						page : opts.pageNumber,
						rows : opts.pageSize
					});
				}
				if (opts.sortName) {
					$.extend(queryParams, {
						sort : opts.sortName,
						order : opts.sortOrder
					});
				}
			}else{
				//如果，使用字符串作为参数的话，就无法使用分页功能，那么分页功能必须作为字符串的形式传入
				queryParams=optsQueryParams;
				if (opts.pagination) {
					queryParams=queryParams+"&page="+opts.pageNumber+"&rows="+opts.pageSize;
				}
				if (opts.sortName) {
					queryParams=queryParams+"&sort="+opts.sortName+"&order="+opts.sortOrder;
				}
			}
		}else{
			if (opts.pagination) {
				$.extend(queryParams, {
					page : opts.pageNumber,
					rows : opts.pageSize
				});
			}
			if (opts.sortName) {
				$.extend(queryParams, {
					sort : opts.sortName,
					order : opts.sortOrder
				});
			}
		}
		
		if (opts.onBeforeLoad.call(target, queryParams) == false) {
			return;
		}
		$(target).datagrid("loading");
		setTimeout(function() {_loaderData();}, 0);
		function _loaderData() {
			var _bool = opts.loader.call(target, queryParams, function(data) {
				setTimeout(function() {$(target).datagrid("loaded");}, 0);
				renderDatagridLoadData(target, data);
				setTimeout(function() {loadData(target);}, 0);
			}, function() {
				setTimeout(function() {$(target).datagrid("loaded");}, 0);
				opts.onLoadError.apply(target, arguments);
			});
			if (_bool == false) {
				$(target).datagrid("loaded");
			}
		};
	};
	function mergeCells(target, cellObject) {
		var opts = $.data(target, "datagrid").options;
		cellObject.type = cellObject.type || "body";
		cellObject.rowspan = cellObject.rowspan || 1;
		cellObject.colspan = cellObject.colspan || 1;
		if (cellObject.rowspan == 1 && cellObject.colspan == 1) {
			return;
		}
		var tr = opts.finder.getTr(target, (cellObject.index != undefined ? cellObject.index : cellObject.id), cellObject.type);
		if (!tr.length) {
			return;
		}
		var td = tr.find("td[field=\"" + cellObject.field + "\"]");
		td.attr("rowspan", cellObject.rowspan).attr("colspan", cellObject.colspan);
		td.addClass("datagrid-td-merged");
		_tdHide(td.next(), cellObject.colspan - 1);
		for (var i = 1; i < cellObject.rowspan; i++) {
			tr = tr.next();
			if (!tr.length) {
				break;
			}
			td = tr.find("td[field=\"" + cellObject.field + "\"]");
			_tdHide(td, cellObject.colspan);
		}
		setMergedWidth(target);
		function _tdHide(td, _len) {
			for (var i = 0; i < _len; i++) {
				td.hide();
				td = td.next();
			}
		};
	};
	$.fn.datagrid = function(options, param) {
		if (typeof options == "string") {
			return $.fn.datagrid.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var datagrid = $.data(this, "datagrid");
			var opts;
			if (datagrid) {
				opts = $.extend(datagrid.options, options);
				datagrid.options = opts;
			} else {
				opts = $.extend({}, $.extend({}, $.fn.datagrid.defaults, {
					queryParams : {}
				}), $.fn.datagrid.parseOptions(this), options);
				$(this).css("width", "").css("height", "");
				var datagridObject = buildDatagrid(this, opts.rownumbers);
				if (!opts.columns) {
					opts.columns = datagridObject.columns;
				}
				if (!opts.frozenColumns) {
					opts.frozenColumns = datagridObject.frozenColumns;
				}
				opts.columns = $.extend(true, [], opts.columns);
				opts.frozenColumns = $.extend(true, [], opts.frozenColumns);
				opts.view = $.extend({}, opts.view);
				$.data(this, "datagrid", {
					options : opts,
					panel : datagridObject.panel,
					dc : datagridObject.dc,
					ss : null,
					selectedRows : [],
					checkedRows : [],
					data : {
						total : 0,
						rows : []
					},
					originalRows : [],
					updatedRows : [],
					insertedRows : [],
					deletedRows : []
				});
			}
			createDatagrid(this);
			bindEvent(this);
			resizeGrid(this);
			if (opts.data) {
				renderDatagridLoadData(this, opts.data);
				loadData(this);
			} else {
				var data = $.fn.datagrid.parseData(this);
				if (data.total > 0) {
					renderDatagridLoadData(this, data);
					loadData(this);
				}
			}
			if (opts.url) {
				request(this);
			}
			
		});
	};
	function _190(_191) {
		var _192 = {};
		$.map(_191, function(name) {
			_192[name] = _193(name);
		});
		return _192;
		function _193(name) {
			function isA(_194) {
				return $.data($(_194)[0], name) != undefined;
			};
			return {
				init : function(target, val) {
					var parent = $("<input type=\"text\" class=\"datagrid-editable-input\">").appendTo(target);
					if (parent[name] && name != "text") {
						return parent[name](val);
					} else {
						return parent;
					}
				},
				destroy : function(target) {
					if (isA(target, name)) {
						$(target)[name]("destroy");
					}
				},
				getValue : function(target) {
					if (isA(target, name)) {
						var opts = $(target)[name]("options");
						if (opts.multiple) {
							return $(target)[name]("getValues").join(opts.separator);
						} else {
							return $(target)[name]("getValue");
						}
					} else {
						return $(target).val();
					}
				},
				setValue : function(target, val) {
					if (isA(target, name)) {
						var opts = $(target)[name]("options");
						if (opts.multiple) {
							if (val) {
								$(target)[name]("setValues", val.split(opts.separator));
							} else {
								$(target)[name]("clear");
							}
						} else {
							$(target)[name]("setValue", val);
						}
					} else {
						$(target).val(val);
					}
				},
				resize : function(target, width) {
					if (isA(target, name)) {
						$(target)[name]("resize", width);
					} else {
						$(target)._outerWidth(width)._outerHeight(22);
					}
				}
			};
		};
	};
	var editorsTool = $.extend({},_190([ "text", "textbox", "numberbox", "numberspinner",
		"combobox", "combotree", "combogrid", "datebox",
		"datetimebox", "timespinner", "datetimespinner","butcombo","butcombogrid"]),
	{
		butcombo : {
			init : function(target, _1a0) {
				var textarea = $("<a class=\"easyui-butcombo\">sadf</a>").appendTo(target);
				textarea.butcombo(_1a0);
				return target;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		butcombogrid : {
			init : function(target, _1a0) {
				var textarea = $("<textarea class=\"datagrid-editable-input\"></textarea>").appendTo(target);
				return textarea;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		textarea : {
			init : function(target, _1a0) {
				var textarea = $("<textarea class=\"datagrid-editable-input\"></textarea>").appendTo(target);
				return textarea;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		checkbox : {
			init : function(target, val) {
				var checkbox = $("<input type=\"checkbox\">").appendTo(target);
				checkbox.val(val.on);
				checkbox.attr("offval", val.off);
				return checkbox;
			},
			getValue : function(target) {
				if ($(target).is(":checked")) {
					return $(target).val();
				} else {
					return $(target).attr("offval");
				}
			},
			setValue : function(target, val) {
				var _bool = false;
				if ($(target).val() == val) {
					_bool = true;
				}
				$(target)._propAttr("checked", _bool);
			}
		},
		validatebox : {
			init : function(target, val) {
				var box = $("<input type=\"text\" class=\"datagrid-editable-input\">").appendTo(target);
				box.validatebox(val);
				return box;
			},
			destroy : function(target) {
				$(target).validatebox("destroy");
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width)._outerHeight(22);
			}
		}
	});
	$.fn.datagrid.methods = {
		options : function(jq) {
			var _opts = $.data(jq[0], "datagrid").options;
			var _pPpts = $.data(jq[0], "datagrid").panel.panel("options");
			var opts = $.extend(_opts, {
				width : _pPpts.width,
				height : _pPpts.height,
				closed : _pPpts.closed,
				collapsed : _pPpts.collapsed,
				minimized : _pPpts.minimized,
				maximized : _pPpts.maximized
			});
			return opts;
		},
		setSelectionState : function(jq) {
			return jq.each(function() {
				setSelectionState(this);
			});
		},
		createStyleSheet : function(jq) {
			return createStyleSheet(jq[0]);
		},
		getPanel : function(jq) {
			return $.data(jq[0], "datagrid").panel;
		},
		getPager : function(jq) {
			return $.data(jq[0], "datagrid").panel.children("div.datagrid-pager");
		},
		getColumnFields : function(jq, param) {
			return getColumnFields(jq[0], param);
		},
		getColumnOption : function(jq, param) {
			return getColumnOption(jq[0], param);
		},
		resize : function(jq, param) {
			return jq.each(function() {
				resizeGrid(this, param);
			});
		},
		load : function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid("options");
				if (typeof param == "string") {
					opts.url = param;
					param = null;
				}
				opts.pageNumber = 1;
				var pager = $(this).datagrid("getPager");
				pager.pagination("refresh", {pageNumber : 1});
				request(this, param);
			});
		},
		reload : function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid("options");
				if (typeof param == "string") {
					opts.url = param;
					param = null;
				}
				request(this, param);
			});
		},
		reloadFooter : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				var dc = $.data(this, "datagrid").dc;
				if (param) {
					$.data(this, "datagrid").footer = param;
				}
				if (opts.showFooter) {
					opts.view.renderFooter.call(opts.view, this, dc.footer2, false);
					opts.view.renderFooter.call(opts.view, this, dc.footer1, true);
					if (opts.view.onAfterRender) {
						opts.view.onAfterRender.call(opts.view, this);
					}
					$(this).datagrid("fixRowHeight");
				}
			});
		},
		loading : function(jq) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				$(this).datagrid("getPager").pagination("loading");
				if (opts.loadMsg) {
					var panel = $(this).datagrid("getPanel");
					if (!panel.children("div.datagrid-mask").length) {
						$("<div class=\"datagrid-mask\" style=\"display:block\"></div>").appendTo(panel);
						var msg = $("<div class=\"datagrid-mask-msg\" style=\"display:block;left:50%\"></div>").html(opts.loadMsg).appendTo(panel);
						msg._outerHeight(40);
						msg.css({
							marginLeft : (-msg.outerWidth() / 2),
							lineHeight : (msg.height() + "px")
						});
					}
				}
			});
		},
		loaded : function(jq) {
			return jq.each(function() {
				$(this).datagrid("getPager").pagination("loaded");
				var panel = $(this).datagrid("getPanel");
				panel.children("div.datagrid-mask-msg").remove();
				panel.children("div.datagrid-mask").remove();
			});
		},
		fitColumns : function(jq) {
			return jq.each(function() {
				fitColumns(this);
			});
		},
		fixColumnSize : function(jq, param) {
			return jq.each(function() {
				fixColumnSize(this, param);
			});
		},
		fixRowHeight : function(jq, param) {
			return jq.each(function() {
				fixRowHeight(this, param);
			});
		},
		freezeRow : function(jq, param) {
			return jq.each(function() {
				freezeRow(this, param);
			});
		},
		autoSizeColumn : function(jq, param) {
			return jq.each(function() {
				autoSizeColumn(this, param);
			});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				renderDatagridLoadData(this, data);
				loadData(this);
			});
		},
		getData : function(jq) {
			return $.data(jq[0], "datagrid").data;
		},
		getRows : function(jq) {
			return $.data(jq[0], "datagrid").data.rows;
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], "datagrid").footer;
		},
		getRowIndex : function(jq, id) {
			return getRowIndex(jq[0], id);
		},
		getChecked : function(jq) {
			return getChecked(jq[0]);
		},
		getSelected : function(jq) {
			var rows = getSelectedRow(jq[0]);
			return rows.length > 0 ? rows[0] : null;
		},
		getSelections : function(jq) {
			return getSelectedRow(jq[0]);
		},
		clearSelections : function(jq) {
			return jq.each(function() {
				var datagrid = $.data(this, "datagrid");
				var selectedRows = datagrid.selectedRows;
				selectedRows.splice(0, selectedRows.length);
				unselectAll(this);
				if (datagrid.options.checkOnSelect) {
					var checkedRows = datagrid.checkedRows;
					checkedRows.splice(0, checkedRows.length);
				}
			});
		},
		clearChecked : function(jq) {
			return jq.each(function() {
				var datagrid = $.data(this, "datagrid");
				var selectedRows = datagrid.selectedRows;
				var checkedRows = datagrid.checkedRows;
				checkedRows.splice(0, checkedRows.length);
				uncheckAll(this);
				if (datagrid.options.selectOnCheck) {
					selectedRows.splice(0, selectedRows.length);
				}
			});
		},
		scrollTo : function(jq, param) {
			return jq.each(function() {
				scrollTo(this, param);
			});
		},
		highlightRow : function(jq, param) {
			return jq.each(function() {
				highlightRow(this, param);
				scrollTo(this, param);
			});
		},
		selectAll : function(jq) {
			return jq.each(function() {
				selectAll(this);
			});
		},
		unselectAll : function(jq) {
			return jq.each(function() {
				unselectAll(this);
			});
		},
		selectRow : function(jq, param) {
			return jq.each(function() {
				selectRow(this, param);
			});
		},
		selectRecord : function(jq, id) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				if (opts.idField) {
					var _id = getRowIndex(this, id);
					if (_id >= 0) {
						$(this).datagrid("selectRow", _id);
					}
				}
			});
		},
		unselectRow : function(jq, param) {
			return jq.each(function() {
				unselectRow(this, param);
			});
		},
		checkRow : function(jq, param) {
			return jq.each(function() {
				checkRow(this, param);
			});
		},
		uncheckRow : function(jq, param) {
			return jq.each(function() {
				uncheckRow(this, param);
			});
		},
		checkAll : function(jq) {
			return jq.each(function() {
				checkAll(this);
			});
		},
		uncheckAll : function(jq) {
			return jq.each(function() {
				uncheckAll(this);
			});
		},
		beginEdit : function(jq, param) {
			return jq.each(function() {
				beginEdit(this, param);
			});
		},
		endEdit : function(jq, param) {
			return jq.each(function() {
				endEdit(this, param, false);
			});
		},
		cancelEdit : function(jq, param) {
			return jq.each(function() {
				endEdit(this, param, true);
			});
		},
		editCell: function(jq,param){
			return jq.each(function(){
				editCell(this,param);
			});
		},
		getEditors : function(jq, param) {
			return getEditors(jq[0], param);
		},
		getEditor : function(jq, param) {
			return getEditor(jq[0], param);
		},
		refreshRow : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				opts.view.refreshRow.call(opts.view, this, param);
			});
		},
		validateRow : function(jq, param) {
			return validateRow(jq[0], param);
		},
		updateRow : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				opts.view.updateRow.call(opts.view, this, param.index, param.row);
			});
		},
		appendRow : function(jq, row) {
			return jq.each(function() {
				appendRow(this, row);
			});
		},
		insertRow : function(jq, param) {
			return jq.each(function() {
				insertRow(this, param);
			});
		},
		deleteRow : function(jq, param) {
			return jq.each(function() {
				deleteRow(this, param);
			});
		},
		getChanges : function(jq, param) {
			return getChanges(jq[0], param);
		},
		acceptChanges : function(jq) {
			return jq.each(function() {
				acceptChanges(this);
			});
		},
		rejectChanges : function(jq) {
			return jq.each(function() {
				rejectChanges(this);
			});
		},
		mergeCells : function(jq, param) {
			return jq.each(function() {
				mergeCells(this, param);
			});
		},
		showColumn : function(jq, param) {
			return jq.each(function() {
				var panel = $(this).datagrid("getPanel");
				panel.find("td[field=\"" + param + "\"]").show();
				$(this).datagrid("getColumnOption", param).hidden = false;
				$(this).datagrid("fitColumns");
			});
		},
		hideColumn : function(jq, param) {
			return jq.each(function() {
				var panel = $(this).datagrid("getPanel");
				panel.find("td[field=\"" + param + "\"]").hide();
				$(this).datagrid("getColumnOption", param).hidden = true;
				$(this).datagrid("fitColumns");
			});
		},
		sort : function(jq, param) {
			return jq.each(function() {
				sortGrid(this, param);
			});
		}
	};
	$.fn.datagrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.panel.parseOptions(target), $.parser.parseOptions(target,
			[ "url", "toolbar", "idField", "sortName", "sortOrder", "pagePosition", "resizeHandle", 
			 {
				sharedStyleSheet : "boolean",
				fitColumns : "boolean",
				autoRowHeight : "boolean",
				striped : "boolean",
				nowrap : "boolean"
			}, {
				rownumbers : "boolean",
				singleSelect : "boolean",
				ctrlSelect : "boolean",
				checkOnSelect : "boolean",
				selectOnCheck : "boolean"
			}, {
				pagination : "boolean",
				pageSize : "number",
				pageNumber : "number"
			}, {
				multiSort : "boolean",
				remoteSort : "boolean",
				showHeader : "boolean",
				showFooter : "boolean"
			}, {
				scrollbarSize : "number"
			} ]), {
			pageList : (t.attr("pageList") ? eval(t.attr("pageList")) : undefined),
			loadMsg : (t.attr("loadMsg") != undefined ? t.attr("loadMsg") : undefined),
			rowStyler : (t.attr("rowStyler") ? eval(t.attr("rowStyler")) : undefined)
		});
	};
	$.fn.datagrid.parseData = function(target) {
		var t = $(target);
		var data = {
			total : 0,
			rows : []
		};
		var columnFields = t.datagrid("getColumnFields", true).concat(t.datagrid("getColumnFields", false));
		t.find("tbody tr").each(function() {
			data.total++;
			var row = {};
			$.extend(row, $.parser.parseOptions(this, [ "iconCls", "state" ]));
			for (var i = 0; i < columnFields.length; i++) {
				row[columnFields[i]] = $(this).find("td:eq(" + i + ")").html();
			}
			data.rows.push(row);
		});
		return data;
	};
	var viewTool = {
		render : function(target, body, _bool) {
			var datagrid = $.data(target, "datagrid");
			var opts = datagrid.options;
			var rows = datagrid.data.rows;
			var _field = $(target).datagrid("getColumnFields", _bool);
			if (_bool) {
				if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
					return;
				}
			}
			var btable = [ "<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\""];
			if(!_bool && rows.length<=0 && opts.emptyMessage){
				btable.push(" width=\"100%\" height=\"30\"");
			}
			btable.push(" border=\"0\"><tbody>");
			for (var i = 0; i < rows.length; i++) {
				var css = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : "";
				var className = "";
				var styleName = "";
				if (typeof css == "string") {
					styleName = css;
				} else {
					if (css) {
						className = css["class"] || "";
						styleName = css["style"] || "";
					}
				}
				var cls = "class=\"datagrid-row " + (i % 2 && opts.striped ? "datagrid-row-alt " : " ") + className + "\"";
				var _styleCls = styleName ? "style=\"" + styleName + "\"" : "";
				var _rowIdStr = datagrid.rowIdPrefix + "-" + (_bool ? 1 : 2) + "-" + i;
				btable.push("<tr id=\"" + _rowIdStr+ "\" datagrid-row-index=\"" + i + "\" " + cls + " " + _styleCls + ">");
				btable.push(this.renderRow.call(this, target, _field, _bool, i, rows[i]));
				btable.push("</tr>");
			}
			if(!_bool && rows.length<=0 && opts.emptyMessage){
				btable.push("<tr><td align='center'>您没有查询到数据</td></tr>");
			}
			btable.push("</tbody></table>");
			
			
			$(body).html(btable.join(""));
		},
		renderFooter : function(target, footer, _bool) {
			var opts = $.data(target, "datagrid").options;
			var rows = $.data(target, "datagrid").footer || [];
			var _field = $(target).datagrid("getColumnFields", _bool);
			var ftable = [ "<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
			for (var i = 0; i < rows.length; i++) {
				ftable.push("<tr class=\"datagrid-row\" datagrid-row-index=\"" + i + "\">");
				ftable.push(this.renderRow.call(this, target, _field, _bool, i, rows[i]));
				ftable.push("</tr>");
			}
			ftable.push("</tbody></table>");
			$(footer).html(ftable.join(""));
		},
		renderRow : function(target, _fields, _bool, index, row) {
			var opts = $.data(target, "datagrid").options;
			var tdHtml = [];
			if (_bool && opts.rownumbers) {
				var rownumbers = index + 1;
				if (opts.pagination) {
					rownumbers += (opts.pageNumber - 1) * opts.pageSize;
				}
				tdHtml.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rownumbers + "</div></td>");
			}
			for (var i = 0; i < _fields.length; i++) {
				var fieldIndex = _fields[i];
				var col = $(target).datagrid("getColumnOption", fieldIndex);
				if (col) {
					var rowField = row[fieldIndex];
					var css = col.styler ? (col.styler(rowField, row, index) || "") : "";
					var className = "";
					var styleName = "";
					if (typeof css == "string") {
						styleName = css;
					} else {
						if (css) {
							className = css["class"] || "";
							styleName = css["style"] || "";
						}
					}
					var cls = className ? "class=\"" + className + "\"" : "";
					var colHidden = col.hidden ? "style=\"display:none;" + styleName + "\"" : (styleName ? "style=\"" + styleName + "\"" : "");
					tdHtml.push("<td field=\"" + fieldIndex + "\" " + cls + " " + colHidden + ">");
					colHidden = "";
					if (!col.checkbox) {
						if (col.align) {
							colHidden += "text-align:" + col.align + ";";
						}
						if (!opts.nowrap) {
							colHidden += "white-space:normal;height:auto;";
						} else {
							if (opts.autoRowHeight) {
								colHidden += "height:auto;";
							}
						}
					}
					tdHtml.push("<div style=\"" + colHidden + "\" ");
					tdHtml.push(col.checkbox ? "class=\"datagrid-cell-check\"" : "class=\"datagrid-cell " + col.cellClass + "\"");
					tdHtml.push(">");
					if (col.checkbox) {
						tdHtml.push("<input type=\"checkbox\" " + (row.checked ? "checked=\"checked\"" : ""));
						tdHtml.push(" name=\"" + fieldIndex + "\" value=\"" + (rowField != undefined ? rowField : "") + "\">");
					} else {
						if (col.formatter) {
							tdHtml.push(col.formatter(rowField, row, index,fieldIndex));
						} else {
							var dictionString=null;
							try{dictionString=dictionaryJson;}catch(e){};
							if(col.dictionary && dictionString){
								try{rowField=dictionaryJson[col.dictionary][rowField];}catch(e){};
							}
							if(col.dateFormatt && rowField){
								try{
								var data=$.parser.parseDate(col.dateFormatt,rowField);
								rowField=$.parser.formatDate(col.dateFormatt,data);}catch(e){};
							}
							tdHtml.push(rowField || '');
						}
					}
					tdHtml.push("</div>");
					tdHtml.push("</td>");
				}
			}
			return tdHtml.join("");
		},
		refreshRow : function(target, index) {
			this.updateRow.call(this, target, index, {});
		},
		updateRow : function(target, id, row) {
			var opts = $.data(target, "datagrid").options;
			var rows = $(target).datagrid("getRows");
			$.extend(rows[id], row);
			var css = opts.rowStyler ? opts.rowStyler.call(target, id, rows[id]) : "";
			var className = "";
			var styleName = "";
			if (typeof css == "string") {
				styleName = css;
			} else {
				if (css) {
					className = css["class"] || "";
					styleName = css["style"] || "";
				}
			}
			className = "datagrid-row " + (id % 2 && opts.striped ? "datagrid-row-alt " : " ") + className;
			function selectRow(viewType) {
				var _field = $(target).datagrid("getColumnFields", viewType);
				var tr = opts.finder.getTr(target, id, "body", (viewType ? 1 : 2));
				var checked = tr.find("div.datagrid-cell-check input[type=checkbox]").is(":checked");
				tr.html(this.renderRow.call(this, target, _field, viewType, id, rows[id]));
				tr.attr("style", styleName).attr("class",tr.hasClass("datagrid-row-selected") ? className + " datagrid-row-selected" : className);
				if (checked) {
					tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked", true);
				}
			};
			selectRow.call(this, true);
			selectRow.call(this, false);
			$(target).datagrid("fixRowHeight", id);
		},
		insertRow : function(target, size, row) {
			var datagrid = $.data(target, "datagrid");
			var opts = datagrid.options;
			var dc = datagrid.dc;
			var data = datagrid.data;
			if (size == undefined || size == null) {
				size = data.rows.length;
			}
			if (size > data.rows.length) {
				size = data.rows.length;
			}
			function _insertRows(viewType) {
				var _view = viewType ? 1 : 2;
				for (var i = data.rows.length - 1; i >= size; i--) {
					var tr = opts.finder.getTr(target, i, "body", _view);
					tr.attr("datagrid-row-index", i + 1);
					tr.attr("id", datagrid.rowIdPrefix + "-" + _view + "-" + (i + 1));
					if (viewType && opts.rownumbers) {
						var rownumbers = i + 2;
						if (opts.pagination) {
							rownumbers += (opts.pageNumber - 1) * opts.pageSize;
						}
						tr.find("div.datagrid-cell-rownumber").html(rownumbers);
					}
					if (opts.striped) {
						tr.removeClass("datagrid-row-alt").addClass((i + 1) % 2 ? "datagrid-row-alt" : "");
					}
				}
			};
			function _insertFields(viewType) {
				var _view = viewType ? 1 : 2;
				$(target).datagrid("getColumnFields", viewType);
				var idName = datagrid.rowIdPrefix + "-" + _view + "-" + size;
				var tr = "<tr id=\"" + idName + "\" class=\"datagrid-row\" datagrid-row-index=\"" + size + "\"></tr>";
				if (size >= data.rows.length) {
					if (data.rows.length) {
						opts.finder.getTr(target, "", "last", _view).after(tr);
					} else {
						var cc = viewType ? dc.body1 : dc.body2;
						cc.html("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" + tr + "</tbody></table>");
					}
				} else {
					opts.finder.getTr(target, size + 1, "body", _view).before(tr);
				}
			};
			_insertRows.call(this, true);
			_insertRows.call(this, false);
			_insertFields.call(this, true);
			_insertFields.call(this, false);
			data.total += 1;
			data.rows.splice(size, 0, row);
			//显示表头
			if(data.total <=0 && opts.emptyMessage){
				dc.view1.children("div.datagrid-header").hide();
				dc.view2.children("div.datagrid-header").hide();
			}else{
				dc.view1.children("div.datagrid-header").show();
				dc.view2.children("div.datagrid-header").show();
			}
			this.refreshRow.call(this, target, size);
		},
		onBeforeRender : function(target, rows) {
		},
		onAfterRender : function(target) {
			var opts = $.data(target, "datagrid").options;
			if (opts.showFooter) {
				var footer = $(target).datagrid("getPanel").find("div.datagrid-footer");
				footer.find("div.datagrid-cell-rownumber,div.datagrid-cell-check").css("visibility", "hidden");
			}
		}
	};
	$.fn.datagrid.defaults = $.extend({},$.fn.panel.defaults,{
		sharedStyleSheet : false,
		frozenColumns : undefined,
		columns : undefined,
		fitColumns : false,
		resizeHandle : "right",
		autoRowHeight : true,
		toolbar : null,
		striped : false,
		method : "post",
		nowrap : true,
		idField : null,
		url : null,
		data : null,
		loadMsg : "处理中, 请稍等 ...",
		rownumbers : false,
		singleSelect : false,
		ctrlSelect : false,
		selectOnCheck : true,
		checkOnSelect : true,
		pagination : false,
		showPageList : true,
		showMsg:true,
		showPageStyle : "links",
		pagePosition : "bottom",
		buttonPosition : "after",
		pageButtons : null,
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		queryParams : {},
		sortName : null,
		sortOrder : "asc",
		multiSort : false,
		emptyMessage:false,
		remoteSort : true,
		showHeader : true,
		showFooter : false,
		scrollbarSize : 18,
		showEditor:false,
		finder : {
			getTr : function(target, id, type, body) {
				type = type || "body";
				body = body || 0;
				var grid = $.data(target, "datagrid");
				var dc = grid.dc;
				var opts = grid.options;
				if (body == 0) {
					var tr1 = opts.finder.getTr(target, id,type, 1);
					var tr2 = opts.finder.getTr(target, id,type, 2);
					return tr1.add(tr2);
				} else {if (type == "body") {
					var tr = $("#" + grid.rowIdPrefix + "-" + body + "-" + id);
					if (!tr.length) {
					tr = (body == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr[datagrid-row-index=" + id + "]");
					}
					return tr;
				} else {if (type == "footer") {
					return (body == 1 ? dc.footer1 : dc.footer2).find(">table>tbody>tr[datagrid-row-index=" + id + "]");
				} else {if (type == "selected") {
					return (body == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr.datagrid-row-selected");
				} else {if (type == "highlight") {
					return (body == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr.datagrid-row-over");
				} else {if (type == "checked") {
					return (body == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr.datagrid-row-checked");
				} else {if (type == "last") {
					return (body == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr[datagrid-row-index]:last");
				} else {if (type == "allbody") {
					return (body == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr[datagrid-row-index]");
				} else {if (type == "allfooter") {
					return (body == 1 ? dc.footer1 : dc.footer2).find(">table>tbody>tr[datagrid-row-index]");
				}}}}}}}}}
		},
		getRow : function(target, p) {
			var index = (typeof p == "object") ? p.attr("datagrid-row-index") : p;
			return $.data(target, "datagrid").data.rows[parseInt(index)];
		},
		getRows : function(target) {
			return $(target).datagrid("getRows");
		}},
		rowStyler : function(target, param) {
		},
		loader : function(param, successCall, errorCall) {
			var opts = $(this).datagrid("options");
			if (!opts.url) {
				return false;
			}
			$.ajax({
				type : opts.method,
				url : opts.url,
				data : param,
				dataType : "json",
				success : function(data) {
					if(jQuery.isArray(data) || data.status){
						successCall(data);
					}else{
						$.messager.show({
							title:"消息提示",
							msg:data.message||"加载数据异常",
							showType:"slide",
							style:{
								right:'',
								top:document.body.scrollTop+document.documentElement.scrollTop,
								bottom:''
							}
						},"error");
						errorCall.apply(this, arguments);
					}
				},
				error : function() {
					errorCall.apply(this, arguments);
				}
			});
		},
		loadFilter : function(data) {
			if (typeof data.length == "number" && typeof data.splice == "function") {
				return {
					total : data.length,
					rows : data
				};
			} else {
				return data;
			}
		},
		editors : editorsTool,
		view : viewTool,
		onBeforeLoad : function(_24b) {
		},
		onLoadSuccess : function() {
		},
		onLoadError : function() {
		},
		onClickRow : function(_24c, _24d) {
		},
		onDblClickRow : function(_24e, _24f) {
		},
		onClickCell : function(_250, _251, _252) {
		},
		onDblClickCell : function(_253, _254, _255) {
		},
		onBeforeSortColumn : function(sort, _256) {
		},
		onSortColumn : function(sort, _257) {
		},
		onResizeColumn : function(_258, _259) {
		},
		onSelect : function(_25a, _25b) {
		},
		onUnselect : function(_25c, _25d) {
		},
		onSelectAll : function(rows) {
		},
		onUnselectAll : function(rows) {
		},
		onCheck : function(_25e, _25f) {
		},
		onUncheck : function(_260, _261) {
		},
		onCheckAll : function(rows) {
		},
		onUncheckAll : function(rows) {
		},
		onBeforeEdit : function(_262, _263) {
		},
		onBeginEdit : function(_264, _265) {
		},
		onEndEdit : function(_266, _267, _268) {
		},
		onAfterEdit : function(_269, _26a, _26b) {
		},
		onCancelEdit : function(_26c, _26d) {
		},
		onHeaderContextMenu : function(e, _26e) {
		},
		onRowContextMenu : function(e, _26f, _270) {
		}
	});
})(jQuery);/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function initialSetup(target) {
		var treegrid = $.data(target, "treegrid");
		var opts = treegrid.options;
		$(target).datagrid($.extend({}, opts, {
			url : null,
			data : null,
			loader : function() {
				return false;
			},
			onBeforeLoad : function() {
				return false;
			},
			onLoadSuccess : function() {
			},
			onResizeColumn : function(field, resize) {
				fixRowHeight(target);
				opts.onResizeColumn.call(target, field, resize);
			},
			onSortColumn : function(sortName, sortOrder) {
				opts.sortName = sortName;
				opts.sortOrder = sortOrder;
				if (opts.remoteSort) {
					request(target);
				} else {
					var data = $(target).treegrid("getData");
					loadData(target, 0, data);
				}
				opts.onSortColumn.call(target, sortName, sortOrder);
			},
			onBeforeEdit : function(_a, _b) {
				if (opts.onBeforeEdit.call(target, _b) == false) {
					return false;
				}
			},
			onAfterEdit : function(_c, _d, _e) {
				opts.onAfterEdit.call(target, _d, _e);
			},
			onCancelEdit : function(_f, row) {
				opts.onCancelEdit.call(target, row);
			},
			onSelect : function(id) {
				opts.onSelect.call(target, findNode(target, id));
			},
			onUnselect : function(id) {
				opts.onUnselect.call(target, findNode(target, id));
			},
			onCheck : function(id) {
				opts.onCheck.call(target, findNode(target, id));
			},
			onUncheck : function(id) {
				opts.onUncheck.call(target, findNode(target, id));
			},
			onClickRow : function(id) {
				opts.onClickRow.call(target, findNode(target, id));
			},
			onDblClickRow : function(id) {
				opts.onDblClickRow.call(target, findNode(target, id));
			},
			onClickCell : function(id, _17) {
				opts.onClickCell.call(target, _17, findNode(target, id));
			},
			onDblClickCell : function(id, _19) {
				opts.onDblClickCell.call(target, _19, findNode(target, id));
			},
			onRowContextMenu : function(e, id) {
				opts.onContextMenu.call(target, e, findNode(target, id));
			}
		}));
		if (!opts.columns) {
			var gridOpts = $.data(target, "datagrid").options;
			opts.columns = gridOpts.columns;
			opts.frozenColumns = gridOpts.frozenColumns;
		}
		treegrid.dc = $.data(target, "datagrid").dc;
		if (opts.pagination) {
			var pager = $(target).datagrid("getPager");
			pager.pagination({
				pageNumber : opts.pageNumber,
				pageSize : opts.pageSize,
				pageList : opts.pageList,
				buttons :opts.pageButtons,
				buttonPosition: opts.buttonPosition,
				showPageList :opts.showPageList,
				showPageStyle :opts.showPageStyle,
				showMsg:opts.showMsg,
				onSelectPage : function(pageNumber, pageSize) {
					opts.pageNumber = pageNumber;
					opts.pageSize = pageSize;
					request(target);
				}
			});
			opts.pageSize = pager.pagination("options").pageSize;
		}
	};
	function fixRowHeight(target, id) {
		var gridOpts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		if (!dc.body1.is(":empty") && (!gridOpts.nowrap || gridOpts.autoRowHeight)) {
			if (id != undefined) {
				var childrens = getChildren(target, id);
				for (var i = 0; i < childrens.length; i++) {
					_setFields(childrens[i][gridOpts.idField]);
				}
			}
		}
		$(target).datagrid("fixRowHeight", id);
		function _setFields(nodeId) {
			var tr1 = gridOpts.finder.getTr(target, nodeId, "body", 1);
			var tr2 = gridOpts.finder.getTr(target, nodeId, "body", 2);
			tr1.css("height", "");
			tr2.css("height", "");
			var maxHeight = Math.max(tr1.height(), tr2.height());
			tr1.css("height", maxHeight);
			tr2.css("height", maxHeight);
		};
	};
	function setRownumber(target) {
		var dc = $.data(target, "datagrid").dc;
		var opts = $.data(target, "treegrid").options;
		if (!opts.rownumbers) {
			return;
		}
		dc.body1.find("div.datagrid-cell-rownumber").each(function(i) {
			$(this).html(i + 1);
		});
	};
	function bindEvent(target) {
		var dc = $.data(target, "datagrid").dc;
		var bodys = dc.body1.add(dc.body2);
		var bodyHandler = ($.data(bodys[0], "events") || $._data(bodys[0], "events")).click[0].handler;
		dc.body1.add(dc.body2).bind("mouseover",function(e) {
			var tt = $(e.target);
			var tr = tt.closest("tr.datagrid-row");
			if (!tr.length) {
				return;
			}
			if (tt.hasClass("tree-hit")) {
				tt.hasClass("tree-expanded") ? tt.addClass("tree-expanded-hover") : tt.addClass("tree-collapsed-hover");
			}
			e.stopPropagation();
		}).bind("mouseout",function(e) {
			var tt = $(e.target);
			var tr = tt.closest("tr.datagrid-row");
			if (!tr.length) {
				return;
			}
			if (tt.hasClass("tree-hit")) {
				tt.hasClass("tree-expanded") ? tt.removeClass("tree-expanded-hover") : tt.removeClass("tree-collapsed-hover");
			}
			e.stopPropagation();
		}).unbind("click").bind("click", function(e) {
			var tt = $(e.target);
			var tr = tt.closest("tr.datagrid-row");
			if (!tr.length) {
				return;
			}
			if (tt.hasClass("tree-hit")) {
				toggleNode(target, tr.attr("node-id"));
			} else {
				bodyHandler(e);
			}
			e.stopPropagation();
		});
	};
	function appendTreeNode(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr1 = opts.finder.getTr(target, id, "body", 1);
		var tr2 = opts.finder.getTr(target, id, "body", 2);
		var fieldsLen1 = $(target).datagrid("getColumnFields", true).length + (opts.rownumbers ? 1 : 0);
		var fieldsLen2 = $(target).datagrid("getColumnFields", false).length;
		_insertTree(tr1, fieldsLen1);
		_insertTree(tr2, fieldsLen2);
		function _insertTree(tr, len) {
			$("<tr class=\"treegrid-tr-tree\">" + "<td style=\"border:0px\" colspan=\"" + len + "\">" + "<div></div>" + "</td>" + "</tr>").insertAfter(tr);
		};
	};
	function loadData(target, id, data, _bool) {
		var treegrid = $.data(target, "treegrid");
		var opts = treegrid.options;
		var dc = treegrid.dc;
		data = opts.loadFilter.call(target, data, id);
		var node = findNode(target, id);
		if (node) {
			var body1 = opts.finder.getTr(target, id, "body", 1);
			var body2 = opts.finder.getTr(target, id, "body", 2);
			var cc1 = body1.next("tr.treegrid-tr-tree").children("td").children("div");
			var cc2 = body2.next("tr.treegrid-tr-tree").children("td").children("div");
			if (!_bool) {
				node.children = [];
			}
		} else {
			var cc1 = dc.body1;
			var cc2 = dc.body2;
			if (!_bool) {
				treegrid.data = [];
			}
		}
		if (!_bool) {
			cc1.empty();
			cc2.empty();
		}
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, target, id, data);
		}
		opts.view.render.call(opts.view, target, cc1, true);
		opts.view.render.call(opts.view, target, cc2, false);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, target, dc.footer1, true);
			opts.view.renderFooter.call(opts.view, target, dc.footer2, false);
		}
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, target);
		}
		if (!id && opts.pagination) {
			var total = $.data(target, "treegrid").total;
			var pager = $(target).datagrid("getPager");
			if (pager.pagination("options").total != total) {
				pager.pagination("refresh",{
					total : total
				});
			}
		}
		fixRowHeight(target);
		setRownumber(target);
		showLines(target);
		$(target).treegrid("setSelectionState");
		$(target).treegrid("autoSizeColumn");
		opts.onLoadSuccess.call(target, node, data);
		$(target).datagrid("resize");
	};
	function request(target, id, queryParams, _bool, fnCall) {
		var opts = $.data(target, "treegrid").options;
		var bodys = $(target).datagrid("getPanel").find("div.datagrid-body");
		if (queryParams) {
			opts.queryParams = queryParams;
		}
		var optsQueryParams=opts.queryParams;
		if(typeof(opts.queryParams)=="function"){
			optsQueryParams=opts.queryParams();
		}
		var _queryParams={};
		if(optsQueryParams){
			if(typeof(optsQueryParams)=="object"){
				_queryParams=$.extend({},optsQueryParams);
				_queryParams.queryString=$.parser.objConvertStr(optsQueryParams);
				if (opts.pagination) {
					$.extend(_queryParams, {
						page : opts.pageNumber,
						rows : opts.pageSize
					});
				}
				if (opts.sortName) {
					$.extend(_queryParams, {
						sort : opts.sortName,
						order : opts.sortOrder
					});
				}
			}else{
				//如果，使用字符串作为参数的话，就无法使用分页功能，那么分页功能必须作为字符串的形式传入
				_queryParams=optsQueryParams;
				if (opts.pagination) {
					_queryParams=_queryParams+"&page="+opts.pageNumber+"&rows="+opts.pageSize;
				}
				if (opts.sortName) {
					_queryParams=_queryParams+"&sort="+opts.sortName+"&order="+opts.sortOrder;
				}
			}
		}else{
			if (opts.pagination) {
				$.extend(_queryParams, {
					page : opts.pageNumber,
					rows : opts.pageSize
				});
			}
			if (opts.sortName) {
				$.extend(_queryParams, {
					sort : opts.sortName,
					order : opts.sortOrder
				});
			}
		}
		
		var row = findNode(target, id);
		if (opts.onBeforeLoad.call(target, row, _queryParams) == false) {
			return;
		}
		var folder = bodys.find("tr[node-id=\"" + id + "\"] span.tree-folder");
		folder.addClass("tree-loading");
		$(target).treegrid("loading");
		var _bb = opts.loader.call(target, _queryParams, function(data) {
			folder.removeClass("tree-loading");
			$(target).treegrid("loaded");
			loadData(target, id, data, _bool);
			if (fnCall) {
				fnCall();
			}
		}, function() {
			folder.removeClass("tree-loading");
			$(target).treegrid("loaded");
			opts.onLoadError.apply(target, arguments);
			if (fnCall) {
				fnCall();
			}
		});
		if (_bb == false) {
			folder.removeClass("tree-loading");
			$(target).treegrid("loaded");
		}
	};
	function getRoot(target) {
		var roots = getRoots(target);
		if (roots.length) {
			return roots[0];
		} else {
			return null;
		}
	};
	function getRoots(target) {
		return $.data(target, "treegrid").data;
	};
	function getParent(target, id) {
		var row = findNode(target, id);
		if (row._parentId) {
			return findNode(target, row._parentId);
		} else {
			return null;
		}
	};
	function getChildren(target, id) {
		var opts = $.data(target, "treegrid").options;
//		var _5c = $(target).datagrid("getPanel").find("div.datagrid-view2 div.datagrid-body");
		var nodeArray = [];
		if (id) {
			_findChildrenNode(id);
		} else {
			var roots = getRoots(target);
			for (var i = 0; i < roots.length; i++) {
				nodeArray.push(roots[i]);
				_findChildrenNode(roots[i][opts.idField]);
			}
		}
		function _findChildrenNode(nodeId) {
			var node = findNode(target, nodeId);
			if (node && node.children) {
				for (var i = 0, len = node.children.length; i < len; i++) {
					var childrenNode = node.children[i];
					nodeArray.push(childrenNode);
					_findChildrenNode(childrenNode[opts.idField]);
				}
			}
		};
		return nodeArray;
	};
	function getLevel(target, id) {
		if (!id) {
			return 0;
		}
		var opts = $.data(target, "treegrid").options;
		var views = $(target).datagrid("getPanel").children("div.datagrid-view");
		var td = views.find("div.datagrid-body tr[node-id=\"" + id + "\"]").children("td[field=\"" + opts.treeField + "\"]");
		return td.find("span.tree-indent,span.tree-hit").length;
	};
	function findNode(target, id) {
		var opts = $.data(target, "treegrid").options;
		var data = $.data(target, "treegrid").data;
		var nodeArray = [ data ];
		while (nodeArray.length) {
			var c = nodeArray.shift();
			for (var i = 0; i < c.length; i++) {
				var node = c[i];
				if (node[opts.idField] == id) {
					return node;
				} else {
					if (node["children"]) {
						nodeArray.push(node["children"]);
					}
				}
			}
		}
		return null;
	};
	function collapseNode(target, id) {
		var opts = $.data(target, "treegrid").options;
		var row = findNode(target, id);
		var tr = opts.finder.getTr(target, id);
		var hit = tr.find("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-collapsed")) {
			return;
		}
		if (opts.onBeforeCollapse.call(target, row) == false) {
			return;
		}
		// 是否只显示tree的一个图片
		if(opts.showfolder){
			hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
			hit.removeClass("tree-folder-open");
			hit.last().removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
		}else{
			hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
			hit.next().removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
			hit.next().removeClass("tree-folder-open");
		}
		tr.removeClass("tr-tree-haschild");
		row.state = "closed";
		tr = tr.next("tr.treegrid-tr-tree");
		var cc = tr.children("td").children("div");
		if (opts.animate) {
			cc.slideUp("normal", function() {
				$(target).treegrid("autoSizeColumn");
				fixRowHeight(target, id);
				opts.onCollapse.call(target, row);
			});
		} else {
			cc.hide();
			$(target).treegrid("autoSizeColumn");
			fixRowHeight(target, id);
			opts.onCollapse.call(target, row);
		}
	}
	;
	function expandNode(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr = opts.finder.getTr(target, id);
		var hit = tr.find("span.tree-hit");
		var row = findNode(target, id);
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-expanded")) {
			return;
		}
		if (opts.onBeforeExpand.call(target, row) == false) {
			return;
		}
		// 是否只显示tree的一个图片
		if(opts.showfolder){
			hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded tree-folder-open");
			hit.last().removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
		}else{
			hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
			hit.next().removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
			hit.next().addClass("tree-folder-open");
		}
		tr.addClass("tr-tree-haschild");
		var nextTr = tr.next("tr.treegrid-tr-tree");
		if (nextTr.length) {
			var cc = nextTr.children("td").children("div");
			_openTreeNode(cc);
		} else {
			appendTreeNode(target, row[opts.idField]);
			var _nextTr = tr.next("tr.treegrid-tr-tree");
			var trDiv = _nextTr.children("td").children("div");
			trDiv.hide();
			var queryParams = $.extend({}, opts.queryParams || {});
			queryParams.id = row[opts.idField];
			request(target, row[opts.idField], queryParams, true, function() {
				if (trDiv.is(":empty")) {
					_nextTr.remove();
				} else {
					_openTreeNode(trDiv);
				}
			});
		}
		function _openTreeNode(cc) {
			row.state = "open";
			if (opts.animate) {
				cc.slideDown("normal", function() {
					$(target).treegrid("autoSizeColumn");
					fixRowHeight(target, id);
					opts.onExpand.call(target, row);
				});
			} else {
				cc.show();
				$(target).treegrid("autoSizeColumn");
				fixRowHeight(target, id);
				opts.onExpand.call(target, row);
			}
		};
	};
	function toggleNode(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr = opts.finder.getTr(target, id);
		var hit = tr.find("span.tree-hit");
		if (hit.hasClass("tree-expanded")) {
			collapseNode(target, id);
		} else {
			expandNode(target, id);
		}
	};
	function collapseAll(target, node) {
		var opts = $.data(target, "treegrid").options;
		var childrens = getChildren(target, node);
		if (node) {
			childrens.unshift(findNode(target, node));
		}
		for (var i = 0; i < childrens.length; i++) {
			collapseNode(target, childrens[i][opts.idField]);
		}
	};
	function expandAll(target, node) {
		var opts = $.data(target, "treegrid").options;
		var childrens = getChildren(target, node);
		if (node) {
			childrens.unshift(findNode(target, node));
		}
		for (var i = 0; i < childrens.length; i++) {
			expandNode(target, childrens[i][opts.idField]);
		}
	}
	;
	function expandTo(target, node) {
		var opts = $.data(target, "treegrid").options;
		var ids = [];
		var pNode = getParent(target, node);
		while (pNode) {
			var id = pNode[opts.idField];
			ids.unshift(id);
			pNode = getParent(target, id);
		}
		for (var i = 0; i < ids.length; i++) {
			expandNode(target, ids[i]);
		}
	};
	function appendNode(target, node) {
		var opts = $.data(target, "treegrid").options;
		if (node.parent) {
			var tr = opts.finder.getTr(target, node.parent);
			if (tr.next("tr.treegrid-tr-tree").length == 0) {
				appendTreeNode(target, node.parent);
			}
			var cell = tr.children("td[field=\"" + opts.treeField + "\"]").children("div.datagrid-cell");
			var icon = cell.children("span.tree-icon");
			if (icon.hasClass("tree-file")) {
				icon.removeClass("tree-file").addClass("tree-folder tree-folder-open");
				var hit = $("<span class=\"tree-hit tree-expanded\"></span>").insertBefore(icon);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
		}
		loadData(target, node.parent, node.data, true);
	};
	function insertNode(target, node) {
		var ref = node.before || node.after;
		var opts = $.data(target, "treegrid").options;
		var parent = getParent(target, ref);
		appendNode(target, {
			parent : (parent ? parent[opts.idField] : null),
			data : [ node.data ]
		});
		var rootNodes = parent ? parent.children : $(target).treegrid("getRoots");
		for (var i = 0; i < rootNodes.length; i++) {
			if (rootNodes[i][opts.idField] == ref) {
				var root = rootNodes[rootNodes.length - 1];
				rootNodes.splice(node.before ? i : (i + 1), 0, root);
				rootNodes.splice(rootNodes.length - 1, 1);
				break;
			}
		}
		_insertTrHtml(true);
		_insertTrHtml(false);
		setRownumber(target);
		showLines(target);
		function _insertTrHtml(viewType) {
			var type = viewType ? 1 : 2;
			var tr = opts.finder.getTr(target, node.data[opts.idField], "body", type);
			var btable = tr.closest("table.datagrid-btable");
			tr = tr.parent().children();
			var _tr = opts.finder.getTr(target, ref, "body", type);
			if (node.before) {
				tr.insertBefore(_tr);
			} else {
				var sub = _tr.next("tr.treegrid-tr-tree");
				tr.insertAfter(sub.length ? sub : _tr);
			}
			btable.remove();
		};
	};
	function removeNode(target, id) {
		var treegrid = $.data(target, "treegrid");
		$(target).datagrid("deleteRow", id);
		setRownumber(target);
		treegrid.total -= 1;
		$(target).datagrid("getPager").pagination("refresh", {
			total : treegrid.total
		});
		showLines(target);
	};
	function showLines(target) {
		var t = $(target);
		var opts = t.treegrid("options");
		if (opts.lines) {
			t.treegrid("getPanel").addClass("tree-lines");
		} else {
			t.treegrid("getPanel").removeClass("tree-lines");
			return;
		}
		t.treegrid("getPanel").find("span.tree-indent").removeClass("tree-line tree-join tree-joinbottom");
		t.treegrid("getPanel").find("div.datagrid-cell").removeClass("tree-node-last tree-root-first tree-root-one");
		var roots = t.treegrid("getRoots");
		if (roots.length > 1) {
			_getCellHtml(roots[0]).addClass("tree-root-first");
		} else {
			if (roots.length == 1) {
				_getCellHtml(roots[0]).addClass("tree-root-one");
			}
		}
		_addJoinBottom(roots);
		_addTreeLine(roots);
		function _addJoinBottom(_roots) {
			$.map(_roots, function(_node) {
				if (_node.children && _node.children.length) {
					_addJoinBottom(_node.children);
				} else {
					var cellHt = _getCellHtml(_node);
					cellHt.find(".tree-icon").prev().addClass("tree-join");
				}
			});
			var celllestHt = _getCellHtml(_roots[_roots.length - 1]);
			celllestHt.addClass("tree-node-last");
			celllestHt.find(".tree-join").removeClass("tree-join").addClass("tree-joinbottom");
		};
		function _addTreeLine(_roots) {
			$.map(_roots, function(_node) {
				if (_node.children && _node.children.length) {
					_addTreeLine(_node.children);
				}
			});
			for (var i = 0; i < _roots.length - 1; i++) {
				var _nd = _roots[i];
				var _level = t.treegrid("getLevel", _nd[opts.idField]);
				var tr = opts.finder.getTr(target, _nd[opts.idField]);
				var cc = tr.next().find("tr.datagrid-row td[field=\"" + opts.treeField + "\"] div.datagrid-cell");
				cc.find("span:eq(" + (_level - 1) + ")").addClass("tree-line");
			}
		};
		function _getCellHtml(_node) {
			var tr = opts.finder.getTr(target, _node[opts.idField]);
			var cellHt = tr.find("td[field=\"" + opts.treeField + "\"] div.datagrid-cell");
			return cellHt;
		};
	};
	$.fn.treegrid = function(options, param) {
		if (typeof options == "string") {
			var methods = $.fn.treegrid.methods[options];
			if (methods) {
				return methods(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var treegrid = $.data(this, "treegrid");
			if (treegrid) {
				$.extend(treegrid.options, options);
			} else {
				treegrid = $.data(this, "treegrid", {
					options : $.extend({}, $.fn.treegrid.defaults,$.fn.treegrid.parseOptions(this), options),
					data : []
				});
			}
			initialSetup(this);
			if (treegrid.options.data) {
				$(this).treegrid("loadData", treegrid.options.data);
			}
			if (treegrid.options.url) {
				request(this);
			}
			bindEvent(this);
		});
	};
	$.fn.treegrid.methods = {
		options : function(jq) {
			return $.data(jq[0], "treegrid").options;
		},
		resize : function(jq, param) {
			return jq.each(function() {
				$(this).datagrid("resize", param);
			});
		},
		fixRowHeight : function(jq, param) {
			return jq.each(function() {
				fixRowHeight(this, param);
			});
		},
		loadData : function(jq, node) {
			return jq.each(function() {
				loadData(this, node.parent, node);
			});
		},
		load : function(jq, param) {
			return jq.each(function() {
				$(this).treegrid("options").pageNumber = 1;
				$(this).treegrid("getPager").pagination({
					pageNumber : 1
				});
				$(this).treegrid("reload", param);
			});
		},
		reload : function(jq, id) {
			return jq.each(function() {
				var opts = $(this).treegrid("options");
				var _queryParams = {};
				
				
				if (typeof id == "object") {
					_queryParams = id;
				} else {
					if(typeof(opts.queryParams)=="function"){
						_queryParams=opts.queryParams();
					}else{
						_queryParams = $.extend({}, opts.queryParams);
					}
					_queryParams.id = id;
				}
				if (_queryParams.id) {
					var node = $(this).treegrid("find", _queryParams.id);
					if (node.children) {
						node.children.splice(0, node.children.length);
					}
					opts.queryParams = _queryParams;
					var tr = opts.finder.getTr(this, _queryParams.id);
					tr.next("tr.treegrid-tr-tree").remove();
					tr.find("span.tree-hit").removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
					expandNode(this, _queryParams.id);
				} else {
					request(this, null, _queryParams);
				}
			});
		},
		reloadFooter : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				var dc = $.data(this, "datagrid").dc;
				if (param) {
					$.data(this, "treegrid").footer = param;
				}
				if (opts.showFooter) {
					opts.view.renderFooter.call(opts.view, this, dc.footer1, true);
					opts.view.renderFooter.call(opts.view, this, dc.footer2, false);
					if (opts.view.onAfterRender) {
						opts.view.onAfterRender.call(opts.view, this);
					}
					$(this).treegrid("fixRowHeight");
				}
			});
		},
		getData : function(jq) {
			return $.data(jq[0], "treegrid").data;
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], "treegrid").footer;
		},
		getRoot : function(jq) {
			return getRoot(jq[0]);
		},
		getRoots : function(jq) {
			return getRoots(jq[0]);
		},
		getParent : function(jq, id) {
			return getParent(jq[0], id);
		},
		getChildren : function(jq, id) {
			return getChildren(jq[0], id);
		},
		getLevel : function(jq, id) {
			return getLevel(jq[0], id);
		},
		find : function(jq, id) {
			return findNode(jq[0], id);
		},
		isLeaf : function(jq, id) {
			var opts = $.data(jq[0], "treegrid").options;
			var tr = opts.finder.getTr(jq[0], id);
			var hit = tr.find("span.tree-hit");
			return hit.length == 0;
		},
		select : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("selectRow", id);
			});
		},
		unselect : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("unselectRow", id);
			});
		},
		collapse : function(jq, id) {
			return jq.each(function() {
				collapseNode(this, id);
			});
		},
		expand : function(jq, id) {
			return jq.each(function() {
				expandNode(this, id);
			});
		},
		toggle : function(jq, id) {
			return jq.each(function() {
				toggleNode(this, id);
			});
		},
		collapseAll : function(jq, id) {
			return jq.each(function() {
				collapseAll(this, id);
			});
		},
		expandAll : function(jq, id) {
			return jq.each(function() {
				expandAll(this, id);
			});
		},
		expandTo : function(jq, id) {
			return jq.each(function() {
				expandTo(this, id);
			});
		},
		append : function(jq, param) {
			return jq.each(function() {
				appendNode(this, param);
			});
		},
		insert : function(jq, param) {
			return jq.each(function() {
				insertNode(this, param);
			});
		},
		remove : function(jq, id) {
			return jq.each(function() {
				removeNode(this, id);
			});
		},
		pop : function(jq, id) {
			var row = jq.treegrid("find", id);
			jq.treegrid("remove", id);
			return row;
		},
		refresh : function(jq, id) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				opts.view.refreshRow.call(opts.view, this, id);
			});
		},
		update : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				opts.view.updateRow.call(opts.view, this, param.id, param.row);
			});
		},
		beginEdit : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("beginEdit", id);
				$(this).treegrid("fixRowHeight", id);
			});
		},
		endEdit : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("endEdit", id);
			});
		},
		cancelEdit : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("cancelEdit", id);
			});
		},
		showLines : function(jq) {
			return jq.each(function() {
				showLines(this);
			});
		}
	};
	$.fn.treegrid.parseOptions = function(target) {
		return $.extend({}, $.fn.datagrid.parseOptions(target), $.parser.parseOptions(target, [ "treeField", {animate : "boolean"} ]));
	};
	var viewTool = $.extend({},$.fn.datagrid.defaults.view,{
		render : function(target, body, _bool) {
			var opts = $.data(target, "treegrid").options;
			var _field = $(target).datagrid("getColumnFields", _bool);
			var rowIdPrefix = $.data(target, "datagrid").rowIdPrefix;
			if (_bool) {
				if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
					return;
				}
			}
			var viewToolthis = this;
			if (this.treeNodes && this.treeNodes.length) {
				var gridht = _gridHtml(_bool, this.treeLevel,this.treeNodes);
				$(body).append(gridht.join(""));
			}
			function _gridHtml(_bool, _treeLevel, _treeNodes) {
				var parent = $(target).treegrid("getParent",_treeNodes[0][opts.idField]);
				var leng = (parent ? parent.children.length : $(target).treegrid("getRoots").length) - _treeNodes.length;
				var btable = [ "<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
				for (var i = 0; i < _treeNodes.length; i++) {
					var row = _treeNodes[i];
					if (row.state != "open" && row.state != "closed") {
						row.state = "open";
					}
					
					var css = opts.rowStyler ? opts.rowStyler.call(target, row) : "";
					var claName = "";
					var styleName = "";
					if (typeof css == "string") {
						styleName = css;
					} else {
						if (css) {
							claName = css["class"] || "";
							styleName = css["style"] || "";
						}
					}
					var cls = "";
					if (row.state == "open") {
						cls = "class=\"datagrid-row tr-tree-haschild " + (leng++ % 2 && opts.striped ? "datagrid-row-alt " : " ") + claName + "\"";
					}else{
						cls = "class=\"datagrid-row " + (leng++ % 2 && opts.striped ? "datagrid-row-alt " : " ") + claName + "\"";
					}
					var stName = styleName ? "style=\"" + styleName + "\"" : "";
					var idName = rowIdPrefix + "-" + (_bool ? 1 : 2) + "-" + row[opts.idField];
					btable.push("<tr id=\"" + idName + "\" node-id=\"" + row[opts.idField] + "\" " + cls + " " + stName + ">");
					btable = btable.concat(viewToolthis.renderRow.call(viewToolthis,target, _field, _bool, _treeLevel, row));
					btable.push("</tr>");
					if (row.children && row.children.length) {
						var tt = _gridHtml(_bool, _treeLevel + 1, row.children);
						var v = row.state == "closed" ? "none" : "block";
						//改变下选中样式
						var childStyle = _treeLevel%2==0?"treegrid-tr-tree-f":"treegrid-tr-tree-t";
						btable.push("<tr class=\"treegrid-tr-tree "+childStyle+"\"><td style=\"border:0px\" colspan=" + (_field.length + (opts.rownumbers ? 1 : 0)) + "><div style=\"display:" + v + "\">");
						btable = btable.concat(tt);
						btable.push("</div></td></tr>");
					}
				}
				btable.push("</tbody></table>");
				return btable;
			};
		},
		renderFooter : function(target, footer, _bool) {
			var opts = $.data(target, "treegrid").options;
			var footers = $.data(target, "treegrid").footer || [];
			var _field = $(target).datagrid("getColumnFields", _bool);
			var ftable = [ "<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
			for (var i = 0; i < footers.length; i++) {
				var row = footers[i];
				row[opts.idField] = row[opts.idField] || ("foot-row-id" + i);
				ftable.push("<tr class=\"datagrid-row\" node-id=\"" + row[opts.idField] + "\">");
				ftable.push(this.renderRow.call(this, target, _field, _bool, 0, row));
				ftable.push("</tr>");
			}
			ftable.push("</tbody></table>");
			$(footer).html(ftable.join(""));
		},
		renderRow : function(target, _fields, _bool, id, row) {
			var opts = $.data(target, "treegrid").options;
			var cc = [];
			if (_bool && opts.rownumbers) {
				cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">0</div></td>");
			}
			for (var i = 0; i < _fields.length; i++) {
				var field = _fields[i];
				var col = $(target).datagrid("getColumnOption", field);
				if (col) {
					var css = col.styler ? (col.styler(row[field], row) || "") : "";
					var className = "";
					var styleName = "";
					if (typeof css == "string") {
						styleName = css;
					} else {
						if (cc) {
							className = css["class"] || "";
							styleName = css["style"] || "";
						}
					}
					var cls = className ? "class=\"" + className + "\"" : "";
					var fieldName = col.hidden ? "style=\"display:none;" + styleName + "\"" : (styleName ? "style=\"" + styleName + "\"" : "");
					cc.push("<td field=\"" + field + "\" " + cls + " " + fieldName + ">");
					var fieldName = "";
					if (!col.checkbox) {
						if (col.align) {
							fieldName += "text-align:" + col.align + ";";
						}
						if (!opts.nowrap) {
							fieldName += "white-space:normal;height:auto;";
						} else {
							if (opts.autoRowHeight) {
								fieldName += "height:auto;";
							}
						}
					}
					cc.push("<div style=\"" + fieldName + "\" ");
					if (col.checkbox) {
						cc.push("class=\"datagrid-cell-check ");
					} else {
						cc.push("class=\"datagrid-cell " + col.cellClass);
					}
					cc.push("\">");
					if (col.checkbox) {
						if (row.checked) {
							cc.push("<input type=\"checkbox\" checked=\"checked\"");
						} else {
							cc.push("<input type=\"checkbox\"");
						}
						cc.push(" name=\"" + field + "\" value=\"" + (row[field] != undefined ? row[field] : "") + "\">");
					} else {
						var val = null;
						if (col.formatter) {
							val = col.formatter(row[field], row,field);
						} else {
							var value=row[field];
							var dictionString=null;
							try{dictionString=dictionaryJson;}catch(e){};
							if(col.dictionary && dictionString){
								try{value=dictionaryJson[col.dictionary][value];}catch(e){};
							}
							if(col.dateFormatt && value){
								try{
									var data=$.parser.parseDate(col.dateFormatt,value);
									value=$.parser.formatDate(col.dateFormatt,data);}catch(e){};
							}
							val = value || "";
						}
						if (field == opts.treeField) {
							for (var j = 0; j < id; j++) {
								cc.push("<span class=\"tree-indent\"></span>");
							}
							if (row.state == "closed") {
								cc.push("<span class=\"tree-hit tree-collapsed\"></span>");
							  //cc.push("<span class=\"tree-icon tree-folder " + (row.iconCls ? row.iconCls : "") + "\"></span>");
								if(opts.showfolder){
									cc.push("<span class=\"tree-hit tree-icon tree-folder tree-collapsed " + (row.iconCls ? row.iconCls : "") + "\"></span>");
								}else{
									cc.push("<span class=\"tree-icon tree-folder tree-collapsed " + (row.iconCls ? row.iconCls : "") + "\"></span>");
								}
							} else {
								if (row.children && row.children.length) {
									cc.push("<span class=\"tree-hit tree-expanded\"></span>");
								  //cc.push("<span class=\"tree-icon tree-folder tree-folder-open " + (row.iconCls ? row.iconCls : "") + "\"></span>");
									if(opts.showfolder){
										cc.push("<span class=\"tree-hit tree-icon tree-folder tree-folder-open tree-expanded " + (row.iconCls ? row.iconCls : "") + "\"></span>");
									}else{
										cc.push("<span class=\"tree-icon tree-folder tree-folder-open tree-expanded " + (row.iconCls ? row.iconCls : "") + "\"></span>");
									}
								} else {
									cc.push("<span class=\"tree-indent\"></span>");
									cc.push("<span class=\"tree-icon tree-file " + (row.iconCls ? row.iconCls : "") + "\"></span>");
								}
							}
							cc.push("<span class=\"tree-title\">" + val + "</span>");
						} else {
							cc.push(val);
						}
					}
					cc.push("</div>");
					cc.push("</td>");
				}
			}
			return cc.join("");
		},
		refreshRow : function(_e9, id) {
			this.updateRow.call(this, _e9, id, {});
		},
		updateRow : function(target, id, row) {
			var opts = $.data(target, "treegrid").options;
			var node = $(target).treegrid("find", id);
			$.extend(node, row);
			var level = $(target).treegrid("getLevel", id) - 1;
			var rowStyler = opts.rowStyler ? opts.rowStyler.call(target, node) : "";
			var rowIdPrefix = $.data(target, "datagrid").rowIdPrefix;
			var nodeId = node[opts.idField];
			function _checkedBoxSelected(viewType) {
				var columnFields = $(target).treegrid("getColumnFields", viewType);
				var tr = opts.finder.getTr(target, id, "body", (viewType ? 1 : 2));
				var rowHtml = tr.find("div.datagrid-cell-rownumber").html();
				var checked = tr.find("div.datagrid-cell-check input[type=checkbox]").is(":checked");
				tr.html(this.renderRow(target, columnFields, viewType, level, node));
				if(row.state == "open"){
					tr.addClass("tr-tree-haschild");
				}
				tr.attr("style", rowStyler || "");
				tr.find("div.datagrid-cell-rownumber").html(rowHtml);
				if (checked) {
					tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked", true);
				}
				if (nodeId != id) {
					tr.attr("id", rowIdPrefix + "-" + (viewType ? 1 : 2)+ "-" + nodeId);
					tr.attr("node-id", nodeId);
				}
			};
			_checkedBoxSelected.call(this, true);
			_checkedBoxSelected.call(this, false);
			$(target).treegrid("fixRowHeight", id);
		},
		deleteRow : function(target, id) {
			var opts = $.data(target, "treegrid").options;
			var tr = opts.finder.getTr(target, id);
			tr.next("tr.treegrid-tr-tree").remove();
			tr.remove();
			var node = del(id);
			if (node) {
				if (node.children.length == 0) {
					tr = opts.finder.getTr(target, node[opts.idField]);
					tr.next("tr.treegrid-tr-tree").remove();
					var cell = tr.children("td[field=\"" + opts.treeField + "\"]").children("div.datagrid-cell");
					cell.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
					cell.find(".tree-hit").remove();
					$("<span class=\"tree-indent\"></span>").prependTo(cell);
				}
			}
			function del(id) {
				var cc;
				var parent = $(target).treegrid("getParent", id);
				if (parent) {
					cc = parent.children;
				} else {
					cc = $(target).treegrid("getData");
				}
				for (var i = 0; i < cc.length; i++) {
					if (cc[i][opts.idField] == id) {
						cc.splice(i, 1);
						break;
					}
				}
				return parent;
			};
		},
		onBeforeRender : function(target, id, pageObject) {
			if ($.isArray(id)) {
				pageObject = {
					total : id.length,
					rows : id
				};
				id = null;
			}
			if (!pageObject) {
				return false;
			}
			var treegrid = $.data(target, "treegrid");
			var opts = treegrid.options;
			if (pageObject.length == undefined) {
				if (pageObject.footer) {
					treegrid.footer = pageObject.footer;
				}
				if (pageObject.total) {
					treegrid.total = pageObject.total;
				}
				pageObject = this.transfer(target, id, pageObject.rows);
			} else {
				function _setRowParentId(_pageObject, _id) {
					for (var i = 0; i < _pageObject.length; i++) {
						var row = _pageObject[i];
						row._parentId = _id;
						if (row.children && row.children.length) {
							_setRowParentId(row.children, row[opts.idField]);
						}
					}
				};
				_setRowParentId(pageObject, id);
			}
			var node = findNode(target, id);
			if (node) {
				if (node.children) {
					node.children = node.children.concat(pageObject);
				} else {
					node.children = pageObject;
				}
			} else {
				treegrid.data = treegrid.data.concat(pageObject);
			}
			this.sort(target, pageObject);
			this.treeNodes = pageObject;
			this.treeLevel = $(target).treegrid("getLevel", id);
		},
		sort : function(target, pageObject) {
			var opts = $.data(target, "treegrid").options;
			if (!opts.remoteSort && opts.sortName) {
				var sortNameArray = opts.sortName.split(",");
				var sortOrderArray = opts.sortOrder.split(",");
				_106(pageObject);
			}
			function _106(_pageObject) {
				_pageObject.sort(function(r1, r2) {
					var r = 0;
					for (var i = 0; i < sortNameArray.length; i++) {
						var sn = sortNameArray[i];
						var so = sortOrderArray[i];
						var col = $(target).treegrid("getColumnOption", sn);
						var _107 = col.sorter || function(a, b) { return a == b ? 0 : (a > b ? 1 : -1); };
						r = _107(r1[sn], r2[sn]) * (so == "asc" ? 1 : -1);
						if (r != 0) {
							return r;
						}
					}
					return r;
				});
				for (var i = 0; i < _pageObject.length; i++) {
					var children = _pageObject[i].children;
					if (children && children.length) {
						_106(children);
					}
				}
			};
		},
		transfer : function(target, _10a, data) {
			var opts = $.data(target, "treegrid").options;
			var rows = [];
			for (var i = 0; i < data.length; i++) {
				rows.push(data[i]);
			}
			var _10b = [];
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (!_10a) {
					if (!row._parentId) {
						_10b.push(row);
						rows.splice(i, 1);
						i--;
					}
				} else {
					if (row._parentId == _10a) {
						_10b.push(row);
						rows.splice(i, 1);
						i--;
					}
				}
			}
			var toDo = [];
			for (var i = 0; i < _10b.length; i++) {
				toDo.push(_10b[i]);
			}
			while (toDo.length) {
				var node = toDo.shift();
				for (var i = 0; i < rows.length; i++) {
					var row = rows[i];
					if (row._parentId == node[opts.idField]) {
						if (node.children) {
							node.children.push(row);
						} else {
							node.children = [ row ];
						}
						toDo.push(row);
						rows.splice(i, 1);
						i--;
					}
				}
			}
			return _10b;
		}
	});
	$.fn.treegrid.defaults = $.extend({},$.fn.datagrid.defaults,{
		treeField : null,
		lines : false,
		animate : false,
		singleSelect : true,
		showfolder:false,
		view : viewTool,
		loader : function(param, successCall, errorCall) {
			var opts = $(this).treegrid("options");
			if (!opts.url) {
				return false;
			}
			$.ajax({
				type : opts.method,
				url : opts.url,
				data : param,
				dataType : "json",
				success : function(data) {
					if(jQuery.isArray(data) || data.status){
						successCall(data);
					}else{
						$.messager.show({
							title:"消息提示",
							msg:data.message||"加载数据异常",
							showType:"slide",
							style:{
								right:'',
								top:document.body.scrollTop+document.documentElement.scrollTop,
								bottom:''
							}
						},"error");
						errorCall.apply(this, arguments);
					}
				},
				error : function() {
					errorCall.apply(this, arguments);
				}
			});
		},
		loadFilter : function(data, _10f) {
			return data;
		},
		finder : {
			getTr : function(target, id, type, body) {
				type = type || "body";
				body = body || 0;
				var dc = $.data(target, "datagrid").dc;
				if (body == 0) {
					var opts = $.data(target, "treegrid").options;
					var tr1 = opts.finder.getTr(target, id, type,1);
					var tr2 = opts.finder.getTr(target, id, type,2);
					return tr1.add(tr2);
				} else { if (type == "body") {
					var tr = $("#" + $.data(target, "datagrid").rowIdPrefix + "-" + body + "-" + id);
					if (!tr.length) {
						tr = (body == 1 ? dc.body1 : dc.body2).find("tr[node-id=\"" + id + "\"]");
					}
					return tr;
				} else { if (type == "footer") {
					return (body == 1 ? dc.footer1 : dc.footer2).find("tr[node-id=\"" + id + "\"]");
				} else { if (type == "selected") {
					return (body == 1 ? dc.body1 : dc.body2).find("tr.datagrid-row-selected");
				} else { if (type == "highlight") {
					return (body == 1 ? dc.body1 : dc.body2).find("tr.datagrid-row-over");
				} else { if (type == "checked") {
					return (body == 1 ? dc.body1 : dc.body2).find("tr.datagrid-row-checked");
				} else { if (type == "last") {
					return (body == 1 ? dc.body1 : dc.body2).find("tr:last[node-id]");
				} else { if (type == "allbody") {
					return (body == 1 ? dc.body1 : dc.body2).find("tr[node-id]");
				} else { if (type == "allfooter") {
					return (body == 1 ? dc.footer1 : dc.footer2) .find("tr[node-id]");
				}}}}}}}}}
			},
			getRow : function(target, p) {
				var id = (typeof p == "object") ? p.attr("node-id") : p;
				return $(target).treegrid("find", id);
			},
			getRows : function(target) {
				return $(target).treegrid("getChildren");
			}
		},
		onBeforeLoad : function(row, _114) {
		},
		onLoadSuccess : function(row, data) {
		},
		onLoadError : function() {
		},
		onBeforeCollapse : function(row) {
		},
		onCollapse : function(row) {
		},
		onBeforeExpand : function(row) {
		},
		onExpand : function(row) {
		},
		onClickRow : function(row) {
		},
		onDblClickRow : function(row) {
		},
		onClickCell : function(_115, row) {
		},
		onDblClickCell : function(_116, row) {
		},
		onContextMenu : function(e, row) {
		},
		onBeforeEdit : function(row) {
		},
		onAfterEdit : function(row, _117) {
		},
		onCancelEdit : function(row) {
		}
	});
})(jQuery);
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
 * menu - jQuery EasyUI
 * 
 */
(function($){
	
	/**
	 * initialize the target menu, the function can be invoked only once
	 */
	function init(target){
		$(target).appendTo('body');
		$(target).addClass('menu-top');	// the top menu
		
		$(document).unbind('.menu').bind('mousedown.menu', function(e){
//			var allMenu = $('body>div.menu:visible');
//			var m = $(e.target).closest('div.menu', allMenu);
			var m = $(e.target).closest('div.menu,div.combo-p');
			if (m.length){return}
			$('body>div.menu-top:visible').menu('hide');
		});
		
		var menus = splitMenu($(target));
		for(var i=0; i<menus.length; i++){
			createMenu(menus[i]);
		}
		
		function splitMenu(menu){
			var menus = [];
			menu.addClass('menu');
			menus.push(menu);
			if (!menu.hasClass('menu-content')){
				menu.children('div').each(function(){
					var submenu = $(this).children('div');
					if (submenu.length){
						submenu.insertAfter(target);
						this.submenu = submenu;		// point to the sub menu
						var mm = splitMenu(submenu);
						menus = menus.concat(mm);
					}
				});
			}
			return menus;
		}
		
		function createMenu(menu){
			var wh = $.parser.parseOptions(menu[0], ['width','height']);
			menu[0].originalHeight = wh.height || 0;
			if (menu.hasClass('menu-content')){
				menu[0].originalWidth = wh.width || menu._outerWidth();
			} else {
				menu[0].originalWidth = wh.width || 0;
				menu.children('div').each(function(){
					var item = $(this);
					var itemOpts = $.extend({}, $.parser.parseOptions(this,['name','iconCls','href',{separator:'boolean'}]), {
						disabled: (item.attr('disabled') ? true : undefined)
					});
					if (itemOpts.separator){
						item.addClass('menu-sep');
					}
					if (!item.hasClass('menu-sep')){
						item[0].itemName = itemOpts.name || '';
						item[0].itemHref = itemOpts.href || '';
						
						var text = item.addClass('menu-item').html();
						item.empty().append($('<div class="menu-text"></div>').html(text));
						if (itemOpts.iconCls){
							$('<div class="menu-icon"></div>').addClass(itemOpts.iconCls).appendTo(item);
						}
						if (itemOpts.disabled){
							setDisabled(target, item[0], true);
						}
						if (item[0].submenu){
							$('<div class="menu-rightarrow"></div>').appendTo(item);	// has sub menu
						}
						
						bindMenuItemEvent(target, item);
					}
				});
				$('<div class="menu-line"></div>').prependTo(menu);
			}
			setMenuSize(target, menu);
			menu.hide();
			
			bindMenuEvent(target, menu);
		}
	}
	
	function setMenuSize(target, menu){
		var opts = $.data(target, 'menu').options;
		var style = menu.attr('style') || '';
		menu.css({
			display: 'block',
			left:-10000,
			height: 'auto',
			overflow: 'hidden'
		});
		
		var el = menu[0];
		var width = el.originalWidth || 0;
		if (!width){
			width = 0;
			menu.find('div.menu-text').each(function(){
				if (width < $(this)._outerWidth()){
					width = $(this)._outerWidth();
				}
				$(this).closest('div.menu-item')._outerHeight($(this)._outerHeight()+2);
			});
			width += 40;
		}
		
		width = Math.max(width, opts.minWidth);
//		var height = el.originalHeight || menu.outerHeight();
		var height = el.originalHeight || 0;
		if (!height){
			height = menu.outerHeight();
			
			if (menu.hasClass('menu-top') && opts.alignTo){
				var at = $(opts.alignTo);
				var h1 = at.offset().top - $(document).scrollTop();
				var h2 = $(window)._outerHeight() + $(document).scrollTop() - at.offset().top - at._outerHeight();
				height = Math.min(height, Math.max(h1, h2));
			} else if (height > $(window)._outerHeight()){
				height = $(window).height();
				style += ';overflow:auto';
			} else {
				style += ';overflow:hidden';
			}
			
//			if (height > $(window).height()-5){
//				height = $(window).height()-5;
//				style += ';overflow:auto';
//			} else {
//				style += ';overflow:hidden';
//			}
		}
		var lineHeight = Math.max(el.originalHeight, menu.outerHeight()) - 2;
		menu._outerWidth(width)._outerHeight(height);
		menu.children('div.menu-line')._outerHeight(lineHeight);
		
		style += ';width:' + el.style.width + ';height:' + el.style.height;
		
		menu.attr('style', style);
	}
	
	/**
	 * bind menu event
	 */
	function bindMenuEvent(target, menu){
		var state = $.data(target, 'menu');
		menu.unbind('.menu').bind('mouseenter.menu', function(){
			if (state.timer){
				clearTimeout(state.timer);
				state.timer = null;
			}
		}).bind('mouseleave.menu', function(){
			if (state.options.hideOnUnhover){
				state.timer = setTimeout(function(){
					hideAll(target);
				}, state.options.duration);
			}
		});
	}
	
	/**
	 * bind menu item event
	 */
	function bindMenuItemEvent(target, item){
		if (!item.hasClass('menu-item')){return}
		item.unbind('.menu');
		item.bind('click.menu', function(){
			if ($(this).hasClass('menu-item-disabled')){
				return;
			}
			// only the sub menu clicked can hide all menus
			if (!this.submenu){
				hideAll(target);
				var href = this.itemHref;
				if (href){
					location.href = href;
				}
			}
			var item = $(target).menu('getItem', this);
			$.data(target, 'menu').options.onClick.call(target, item);
		}).bind('mouseenter.menu', function(e){
			// hide other menu
			item.siblings().each(function(){
				if (this.submenu){
					hideMenu(this.submenu);
				}
				$(this).removeClass('menu-active');
			});
			// show this menu
			item.addClass('menu-active');
			
			if ($(this).hasClass('menu-item-disabled')){
				item.addClass('menu-active-disabled');
				return;
			}
			
			var submenu = item[0].submenu;
			if (submenu){
				$(target).menu('show', {
					menu: submenu,
					parent: item
				});
			}
		}).bind('mouseleave.menu', function(e){
			item.removeClass('menu-active menu-active-disabled');
			var submenu = item[0].submenu;
			if (submenu){
				if (e.pageX>=parseInt(submenu.css('left'))){
					item.addClass('menu-active');
				} else {
					hideMenu(submenu);
				}
				
			} else {
				item.removeClass('menu-active');
			}
		});
	}
	
	/**
	 * hide top menu and it's all sub menus
	 */
	function hideAll(target){
		var state = $.data(target, 'menu');
		if (state){
			if ($(target).is(':visible')){
				hideMenu($(target));
				state.options.onHide.call(target);
			}
		}
		return false;
	}
	
	/**
	 * show the menu, the 'param' object has one or more properties:
	 * left: the left position to display
	 * top: the top position to display
	 * menu: the menu to display, if not defined, the 'target menu' is used
	 * parent: the parent menu item to align to
	 * alignTo: the element object to align to
	 */
	function showMenu(target, param){
		var left,top;
		param = param || {};
		var menu = $(param.menu || target);
		$(target).menu('resize', menu[0]);
		if (menu.hasClass('menu-top')){
			var opts = $.data(target, 'menu').options;
			$.extend(opts, param);
			left = opts.left;
			top = opts.top;
			if (opts.alignTo){
				var at = $(opts.alignTo);
				left = at.offset().left;
				top = at.offset().top + at._outerHeight();
				if (opts.align == 'right'){
					left += at.outerWidth() - menu.outerWidth();
				}
			}
			if (left + menu.outerWidth() > $(window)._outerWidth() + $(document)._scrollLeft()){
				left = $(window)._outerWidth() + $(document).scrollLeft() - menu.outerWidth() - 5;
			}
			if (left < 0){left = 0;}
			top = _fixTop(top, opts.alignTo);
		} else {
			var parent = param.parent;	// the parent menu item
			left = parent.offset().left + parent.outerWidth() - 2;
			if (left + menu.outerWidth() + 5 > $(window)._outerWidth() + $(document).scrollLeft()){
				left = parent.offset().left - menu.outerWidth() + 2;
			}
			top = _fixTop(parent.offset().top - 3);
		}
		
		function _fixTop(top, alignTo){
			if (top + menu.outerHeight() > $(window)._outerHeight() + $(document).scrollTop()){
				if (alignTo){
					top = $(alignTo).offset().top - menu._outerHeight();
				} else {
					top = $(window)._outerHeight() + $(document).scrollTop() - menu.outerHeight();
				}
			}
			if (top < 0){top = 0;}
			return top;
		}
		
		menu.css({left:left,top:top});
		menu.show(0, function(){
			if (!menu[0].shadow){
				menu[0].shadow = $('<div class="menu-shadow"></div>').insertAfter(menu);
			}
			menu[0].shadow.css({
				display:'block',
				zIndex:$.fn.menu.defaults.zIndex++,
				left:menu.css('left'),
				top:menu.css('top'),
				width:menu.outerWidth(),
				height:menu.outerHeight()
			});
			menu.css('z-index', $.fn.menu.defaults.zIndex++);
			if (menu.hasClass('menu-top')){
				$.data(menu[0], 'menu').options.onShow.call(menu[0]);
			}
		});
	}
	
	function hideMenu(menu){
		if (!menu) return;
		
		hideit(menu);
		menu.find('div.menu-item').each(function(){
			if (this.submenu){
				hideMenu(this.submenu);
			}
			$(this).removeClass('menu-active');
		});
		
		function hideit(m){
			m.stop(true,true);
			if (m[0].shadow){
				m[0].shadow.hide();
			}
			m.hide();
		}
	}
	
	function findItem(target, text){
		var result = null;
		var tmp = $('<div></div>');
		function find(menu){
			menu.children('div.menu-item').each(function(){
				var item = $(target).menu('getItem', this);
				var s = tmp.empty().html(item.text).text();
				if (text == $.trim(s)) {
					result = item;
				} else if (this.submenu && !result){
					find(this.submenu);
				}
			});
		}
		find($(target));
		tmp.remove();
		return result;
	}
	
	function setDisabled(target, itemEl, disabled){
		var t = $(itemEl);
		if (!t.hasClass('menu-item')){return}
		
		if (disabled){
			t.addClass('menu-item-disabled');
			if (itemEl.onclick){
				itemEl.onclick1 = itemEl.onclick;
				itemEl.onclick = null;
			}
		} else {
			t.removeClass('menu-item-disabled');
			if (itemEl.onclick1){
				itemEl.onclick = itemEl.onclick1;
				itemEl.onclick1 = null;
			}
		}
	}
	
	function appendItem(target, param){
		var menu = $(target);
		if (param.parent){
			if (!param.parent.submenu){
				var submenu = $('<div class="menu"><div class="menu-line"></div></div>').appendTo('body');
				submenu.hide();
				param.parent.submenu = submenu;
				$('<div class="menu-rightarrow"></div>').appendTo(param.parent);
			}
			menu = param.parent.submenu;
		}
		if (param.separator){
			var item = $('<div class="menu-sep"></div>').appendTo(menu);
		} else {
			var item = $('<div class="menu-item"></div>').appendTo(menu);
			$('<div class="menu-text"></div>').html(param.text).appendTo(item);
		}
		if (param.iconCls) $('<div class="menu-icon"></div>').addClass(param.iconCls).appendTo(item);
		if (param.id) item.attr('id', param.id);
		if (param.name){item[0].itemName = param.name}
		if (param.href){item[0].itemHref = param.href}
		if (param.onclick){
			if (typeof param.onclick == 'string'){
				item.attr('onclick', param.onclick);
			} else {
				item[0].onclick = eval(param.onclick);
			}
		}
		if (param.handler){item[0].onclick = eval(param.handler)}
		if (param.disabled){setDisabled(target, item[0], true)}
		
		bindMenuItemEvent(target, item);
		bindMenuEvent(target, menu);
		setMenuSize(target, menu);
	}
	
	function removeItem(target, itemEl){
		function removeit(el){
			if (el.submenu){
				el.submenu.children('div.menu-item').each(function(){
					removeit(this);
				});
				var shadow = el.submenu[0].shadow;
				if (shadow) shadow.remove();
				el.submenu.remove();
			}
			$(el).remove();
		}
		var menu = $(itemEl).parent();
		removeit(itemEl);
		setMenuSize(target, menu);
	}
	
	function setVisible(target, itemEl, visible){
		var menu = $(itemEl).parent();
		if (visible){
			$(itemEl).show();
		} else {
			$(itemEl).hide();
		}
		setMenuSize(target, menu);
	}
	
	function destroyMenu(target){
		$(target).children('div.menu-item').each(function(){
			removeItem(target, this);
		});
		if (target.shadow) target.shadow.remove();
		$(target).remove();
	}
	
	function removeAllItem(target){
		$(target).children('div.menu-item').each(function(){
			removeItem(target, this);
		});
		
	}
	$.fn.menu = function(options, param){
		if (typeof options == 'string'){
			return $.fn.menu.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'menu');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'menu', {
					options: $.extend({}, $.fn.menu.defaults, $.fn.menu.parseOptions(this), options)
				});
				init(this);
			}
			$(this).css({
				left: state.options.left,
				top: state.options.top
			});
		});
	};
	
	$.fn.menu.methods = {
		options: function(jq){
			return $.data(jq[0], 'menu').options;
		},
		show: function(jq, pos){
			return jq.each(function(){
				showMenu(this, pos);
			});
		},
		hide: function(jq){
			return jq.each(function(){
				hideAll(this);
			});
		},
		destroy: function(jq){
			return jq.each(function(){
				destroyMenu(this);
			});
		},
		/**
		 * set the menu item text
		 * param: {
		 * 	target: DOM object, indicate the menu item
		 * 	text: string, the new text
		 * }
		 */
		setText: function(jq, param){
			return jq.each(function(){
				$(param.target).children('div.menu-text').html(param.text);
			});
		},
		/**
		 * set the menu icon class
		 * param: {
		 * 	target: DOM object, indicate the menu item
		 * 	iconCls: the menu item icon class
		 * }
		 */
		setIcon: function(jq, param){
			return jq.each(function(){
				$(param.target).children('div.menu-icon').remove();
				if (param.iconCls){
					$('<div class="menu-icon"></div>').addClass(param.iconCls).appendTo(param.target);
				}
			});
		},
		/**
		 * get the menu item data that contains the following property:
		 * {
		 * 	target: DOM object, the menu item
		 *  id: the menu id
		 * 	text: the menu item text
		 * 	iconCls: the icon class
		 *  href: a remote address to redirect to
		 *  onclick: a function to be called when the item is clicked
		 * }
		 */
		getItem: function(jq, itemEl){
			var t = $(itemEl);
			var item = {
				target: itemEl,
				id: t.attr('id'),
				text: $.trim(t.children('div.menu-text').html()),
				disabled: t.hasClass('menu-item-disabled'),
//				href: t.attr('href'),
//				name: t.attr('name'),
				name: itemEl.itemName,
				href: itemEl.itemHref,
				onclick: itemEl.onclick
			}
			var icon = t.children('div.menu-icon');
			if (icon.length){
				var cc = [];
				var aa = icon.attr('class').split(' ');
				for(var i=0; i<aa.length; i++){
					if (aa[i] != 'menu-icon'){
						cc.push(aa[i]);
					}
				}
				item.iconCls = cc.join(' ');
			}
			return item;
		},
		findItem: function(jq, text){
			return findItem(jq[0], text);
		},
		/**
		 * append menu item, the param contains following properties:
		 * parent,id,text,iconCls,href,onclick
		 * when parent property is assigned, append menu item to it
		 */
		appendItem: function(jq, param){
			return jq.each(function(){
				appendItem(this, param);
			});
		},
		removeItem: function(jq, itemEl){
			return jq.each(function(){
				removeItem(this, itemEl);
			});
		},
		removeAllItem: function(jq){
			return jq.each(function(){
				removeAllItem(this);
			});
		},
		enableItem: function(jq, itemEl){
			return jq.each(function(){
				setDisabled(this, itemEl, false);
			});
		},
		disableItem: function(jq, itemEl){
			return jq.each(function(){
				setDisabled(this, itemEl, true);
			});
		},
		showItem: function(jq, itemEl){
			return jq.each(function(){
				setVisible(this, itemEl, true);
			});
		},
		hideItem: function(jq, itemEl){
			return jq.each(function(){
				setVisible(this, itemEl, false);
			});
		},
		resize: function(jq, menuEl){
			return jq.each(function(){
				setMenuSize(this, $(menuEl));
			});
		}
	};
	
	$.fn.menu.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target, [{minWidth:'number',duration:'number',hideOnUnhover:'boolean'}]));
	};
	
	$.fn.menu.defaults = {
		zIndex:110000,
		left: 0,
		top: 0,
		alignTo: null,
		align: 'left',
		minWidth: 120,
		duration: 100,	// Defines duration time in milliseconds to hide when the mouse leaves the menu.
		hideOnUnhover: true,	// Automatically hides the menu when mouse exits it
		onShow: function(){},
		onHide: function(){},
		onClick: function(item){}
	};
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
(function($){
function _1(_2){
var _3=$.data(_2,"menubutton").options;
var _4=$(_2);
_4.linkbutton(_3);
_4.removeClass(_3.cls.btn1+" "+_3.cls.btn2).addClass("m-btn");
_4.removeClass("m-btn-small m-btn-medium m-btn-large").addClass("m-btn-"+_3.size);
var _5=_4.find(".l-btn-left");
$("<span></span>").addClass(_3.cls.arrow).appendTo(_5);
$("<span></span>").addClass("m-btn-line").appendTo(_5);
if(_3.menu){
$(_3.menu).menu({duration:_3.duration});
var _6=$(_3.menu).menu("options");
var _7=_6.onShow;
var _8=_6.onHide;
$.extend(_6,{onShow:function(){
var _9=$(this).menu("options");
var _a=$(_9.alignTo);
var _b=_a.menubutton("options");
_a.addClass((_b.plain==true)?_b.cls.btn2:_b.cls.btn1);
_7.call(this);
},onHide:function(){
var _c=$(this).menu("options");
var _d=$(_c.alignTo);
var _e=_d.menubutton("options");
_d.removeClass((_e.plain==true)?_e.cls.btn2:_e.cls.btn1);
_8.call(this);
}});
}
};
function _f(_10){
var _11=$.data(_10,"menubutton").options;
var btn=$(_10);
var t=btn.find("."+_11.cls.trigger);
if(!t.length){
t=btn;
}
t.unbind(".menubutton");
var _12=null;
t.bind("click.menubutton",function(){
if(!_13()){
_14(_10);
return false;
}
}).bind("mouseenter.menubutton",function(){
if(!_13()){
_12=setTimeout(function(){
_14(_10);
},_11.duration);
return false;
}
}).bind("mouseleave.menubutton",function(){
if(_12){
clearTimeout(_12);
}
$(_11.menu).triggerHandler("mouseleave");
});
function _13(){
return $(_10).linkbutton("options").disabled;
};
};
function _14(_15){
var _16=$.data(_15,"menubutton").options;
if(_16.disabled||!_16.menu){
return;
}
$("body>div.menu-top").menu("hide");
var btn=$(_15);
var mm=$(_16.menu);
if(mm.length){
mm.menu("options").alignTo=btn;
mm.menu("show",{alignTo:btn,align:_16.menuAlign});
}
btn.blur();
};
$.fn.menubutton=function(_17,_18){
if(typeof _17=="string"){
var _19=$.fn.menubutton.methods[_17];
if(_19){
return _19(this,_18);
}else{
return this.linkbutton(_17,_18);
}
}
_17=_17||{};
return this.each(function(){
var _1a=$.data(this,"menubutton");
if(_1a){
$.extend(_1a.options,_17);
}else{
$.data(this,"menubutton",{options:$.extend({},$.fn.menubutton.defaults,$.fn.menubutton.parseOptions(this),_17)});
$(this).removeAttr("disabled");
}
_1(this);
_f(this);
});
};
$.fn.menubutton.methods={options:function(jq){
var _1b=jq.linkbutton("options");
return $.extend($.data(jq[0],"menubutton").options,{toggle:_1b.toggle,selected:_1b.selected,disabled:_1b.disabled});
},destroy:function(jq){
return jq.each(function(){
var _1c=$(this).menubutton("options");
if(_1c.menu){
$(_1c.menu).menu("destroy");
}
$(this).remove();
});
}};
$.fn.menubutton.parseOptions=function(_1d){
var t=$(_1d);
return $.extend({},$.fn.linkbutton.parseOptions(_1d),$.parser.parseOptions(_1d,["menu",{plain:"boolean",duration:"number"}]));
};
$.fn.menubutton.defaults=$.extend({},$.fn.linkbutton.defaults,{plain:true,menu:null,menuAlign:"left",duration:100,cls:{btn1:"m-btn-active",btn2:"m-btn-plain-active",arrow:"m-btn-downarrow",trigger:"m-btn"}});
})(jQuery);

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
 * form - jQuery EasyUI
 * 
 */
(function($){
	/**
	 * submit the form
	 */
	function ajaxSubmit(target, options){
		var opts = $.data(target, 'form').options;
		$.extend(opts, options||{});
		
		var optsQueryParams=opts.queryParams;
		if(typeof(opts.queryParams)=="function"){
			optsQueryParams=opts.queryParams();
		}
		var param={};
		if(optsQueryParams){
			if(jQuery.type(optsQueryParams)=="object"){
				param=$.extend({},optsQueryParams);
				param.queryString=$.parser.objConvertStr(optsQueryParams);
			}else{
				param=optsQueryParams;
			}
		}
		if (opts.onSubmit.call(target, param) == false){return;}
		var frameId = 'easyui_frame_' + (new Date().getTime());
		var frame = $('<iframe id='+frameId+' name='+frameId+'></iframe>').appendTo('body');
		frame.attr('src', window.ActiveXObject ? 'javascript:false' : 'about:blank');
		frame.css({
			position:'absolute',
			top:-1000,
			left:-1000
		});
		frame.bind('load', cb);
		
		submit(param);
		
		function submit(param){
			var form = $(target);
			if (opts.url){
				form.attr('action', opts.url);
			}
			var t = form.attr('target'), a = form.attr('action');
			form.attr('target', frameId);
			var paramFields = $();
			try {
				for(var n in param){
					var field = $('<input type="hidden" name="' + n + '">').val(param[n]).appendTo(form);
					paramFields = paramFields.add(field);
				}
				checkState();
				form[0].submit();
			} finally {
				form.attr('action', a);
				t ? form.attr('target', t) : form.removeAttr('target');
				paramFields.remove();
			}
		}
		
		function checkState(){
			var f = $('#'+frameId);
			if (!f.length){return;}
			try{
				var s = f.contents()[0].readyState;
				if (s && s.toLowerCase() == 'uninitialized'){
					setTimeout(checkState, 100);
				}
			} catch(e){
				cb();
			}
		}
		
		var checkCount = 10;
		function cb(){
			var f = $('#'+frameId);
			if (!f.length){return;}
			f.unbind();
			var data = '';
			try{
				var body = f.contents().find('body');
				data = body.html();
				if (data == ''){
					if (--checkCount){
						setTimeout(cb, 100);
						return;
					}
				}
				var ta = body.find('>textarea');
				if (ta.length){
					data = ta.val();
				} else {
					var pre = body.find('>pre');
					if (pre.length){
						data = pre.html();
					}
				}
			} catch(e){
			}
			opts.success(data);
			setTimeout(function(){
				f.unbind();
				f.remove();
			}, 100);
		}
	}
	
	/**
	 * load form data
	 * if data is a URL string type load from remote site, 
	 * otherwise load from local data object. 
	 */
	function load(target, data){
		var opts = $.data(target, 'form').options;
		
		if (typeof data == 'string'){
			var param = {};
			if (opts.onBeforeLoad.call(target, param) == false) return;
			
			$.ajax({
				url: data,
				data: param,
				dataType: 'json',
				success: function(data){
					_load(data);
				},
				error: function(){
					opts.onLoadError.apply(target, arguments);
				}
			});
		} else {
			_load(data);
		}
		
		function _load(data){
			var form = $(target);
			for(var name in data){
				var val = data[name];
				var rr = _checkField(name, val);
				if (!rr.length){
					var count = _loadOther(name, val);
					if (!count){
						$('input[name="'+name+'"]', form).val(val);
						$('textarea[name="'+name+'"]', form).val(val);
						$('select[name="'+name+'"]', form).val(val);
					}
				}
				_loadCombo(name, val);
			}
			opts.onLoadSuccess.call(target, data);
			validate(target);
		}
		
		/**
		 * check the checkbox and radio fields
		 */
		function _checkField(name, val){
			var rr = $(target).find('input[name="'+name+'"][type=radio], input[name="'+name+'"][type=checkbox]');
			rr._propAttr('checked', false);
			rr.each(function(){
				var f = $(this);
				if (f.val() == String(val) || $.inArray(f.val(), $.isArray(val)?val:[val]) >= 0){
					f._propAttr('checked', true);
				}
			});
			return rr;
		}
		
		function _loadOther(name, val){
			var count = 0;
			var pp = ['textbox','numberbox','slider'];
			for(var i=0; i<pp.length; i++){
				var p = pp[i];
				var f = $(target).find('input['+p+'Name="'+name+'"]');
				if (f.length){
					f[p]('setValue', val);
					count += f.length;
				}
			}
			return count;
		}
		
		function _loadCombo(name, val){
			var form = $(target);
			var cc = ['combobox','combotree','combogrid','datetimebox','datebox','combo'];
			var c = form.find('[comboName="' + name + '"]');
			if (c.length){
				for(var i=0; i<cc.length; i++){
					var type = cc[i];
					if (c.hasClass(type+'-f')){
						if (c[type]('options').multiple){
							c[type]('setValues', val);
						} else {
							c[type]('setValue', val);
						}
						return;
					}
				}
			}
		}
	}
	
	/**
	 * clear the form fields
	 */
	function clear(target){
		$('input,select,textarea', target).each(function(){
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'hidden' || t == 'password' || tag == 'textarea'){
				this.value = '';
			} else if (t == 'file'){
				var file = $(this);
				var newfile = file.clone().val('');
				newfile.insertAfter(file);
				if (file.data('validatebox')){
					file.validatebox('destroy');
					newfile.validatebox();
				} else {
					file.remove();
				}
			} else if (t == 'checkbox' || t == 'radio'){
				this.checked = false;
			} else if (tag == 'select'){
				this.selectedIndex = -1;
			}
			
		});
		
		var t = $(target);
		var plugins = ['textbox','combo','combobox','combotree','combogrid','slider'];
		for(var i=0; i<plugins.length; i++){
			var plugin = plugins[i];
			var r = t.find('.'+plugin+'-f');
			if (r.length && r[plugin]){
				r[plugin]('clear');
			}
		}
		validate(target);
	}
	
	function reset(target){
		target.reset();
		var t = $(target);
		
		var plugins = ['textbox','combo','combobox','combotree','combogrid','datebox','datetimebox','spinner','timespinner','numberbox','numberspinner','slider'];
		for(var i=0; i<plugins.length; i++){
			var plugin = plugins[i];
			var r = t.find('.'+plugin+'-f');
			if (r.length && r[plugin]){
				r[plugin]('reset');
			}
		}
		validate(target);
	}
	
	/**
	 * set the form to make it can submit with ajax.
	 */
	function setForm(target){
		var options = $.data(target, 'form').options;
		$(target).unbind('.form');
		if (options.ajax){
			$(target).bind('submit.form', function(){
				setTimeout(function(){
					ajaxSubmit(target, options);
				}, 0);
				return false;
			});
		}
		setValidation(target, options.novalidate);
	}
	
	function initForm(target, options){
		options = options || {};
		var state = $.data(target, 'form');
		if (state){
			$.extend(state.options, options);
		} else {
			$.data(target, 'form', {
				options: $.extend({}, $.fn.form.defaults, $.fn.form.parseOptions(target), options)
			});
		}
	}
	
	function validate(target){
		if ($.fn.validatebox){
			var t = $(target);
			t.find('.validatebox-text:not(:disabled)').validatebox('validate');
			var invalidbox = t.find('.validatebox-invalid');
			invalidbox.filter(':not(:disabled):first').focus();
			return invalidbox.length == 0;
		}
		return true;
	}
	
	function setValidation(target, novalidate){
		var opts = $.data(target, 'form').options;
		opts.novalidate = novalidate;
		$(target).find('.validatebox-text:not(:disabled)').validatebox(novalidate ? 'disableValidation' : 'enableValidation');
	}
	
	$.fn.form = function(options, param){
		if (typeof options == 'string'){
			this.each(function(){
				initForm(this);
			});
			return $.fn.form.methods[options](this, param);
		}
		
		return this.each(function(){
			initForm(this, options);
			setForm(this);
		});
	};
	
	$.fn.form.methods = {
		options: function(jq){
			return $.data(jq[0], 'form').options;
		},
		submit: function(jq, options){
			return jq.each(function(){
				ajaxSubmit(this, options);
			});
		},
		load: function(jq, data){
			return jq.each(function(){
				load(this, data);
			});
		},
		clear: function(jq){
			return jq.each(function(){
				clear(this);
			});
		},
		reset: function(jq){
			return jq.each(function(){
				reset(this);
			});
		},
		validate: function(jq){
			return validate(jq[0]);
		},
		disableValidation: function(jq){
			return jq.each(function(){
				setValidation(this, true);
			});
		},
		enableValidation: function(jq){
			return jq.each(function(){
				setValidation(this, false);
			});
		}
	};
	
	$.fn.form.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [{ajax:'boolean'}]), {
			url: (t.attr('action') ? t.attr('action') : undefined)
		});
	};
	
	$.fn.form.defaults = {
		novalidate: false,
		ajax: true,
		url: null,
		queryParams: {},
		onSubmit: function(param){return $(this).form('validate');},
		success: function(data){},
		onBeforeLoad: function(param){},
		onLoadSuccess: function(data){},
		onLoadError: function(){}
	};
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function createCombo(target) {
		var combo = $.data(target, "combo");
		var opts = combo.options;
		if (!combo.panel) {
			if(opts.isBody){
				combo.panel = $("<div class=\"combo-panel\"></div>").appendTo("body");
			}else{
				var tablewrapCombo=$(target).closest("div.datagrid-top-wrap");
				if(tablewrapCombo.length<=0){
					$(target).data("tablewrapCombo",null);
					combo.panel = $("<div class=\"combo-panel\"></div>").appendTo($(target).wrap('<div class="easyui-top-wrap"></div>').parent());
				}else{
					$(target).data("tablewrapCombo",tablewrapCombo);
					combo.panel = $("<div class=\"combo-panel\"></div>").appendTo(tablewrapCombo);
				}
			}
			combo.panel.panel({
				minWidth : opts.panelMinWidth,
				maxWidth : opts.panelMaxWidth,
				minHeight : opts.panelMinHeight,
				maxHeight : opts.panelMaxHeight,
				doSize : false,
				closed : true,
				cls : "combo-p",
				style : {
					position : "absolute",
					zIndex : 10
				},
				onOpen : function() {
					var p = $(this).panel("panel");
					if ($.fn.menu) {
						p.css("z-index", $.fn.menu.defaults.zIndex++);
					} else {
						if ($.fn.window) {
							p.css("z-index", $.fn.window.defaults.zIndex++);
						}
					}
					$(this).panel("resize");
				},
				onBeforeClose : function() {
					comboClose(this);
				},
				onClose : function() {
					var combo = $.data(target, "combo");
					if (combo) {
						combo.options.onHidePanel.call(target);
					}
				}
			});
		}
		var icons = $.extend(true, [], opts.icons);
		if (opts.hasDownArrow) {
			icons.push({
				iconCls : "combo-arrow",
				handler : function(e) {
					comboArrow(e.data.target);
				}
			});
		}
		$(target).addClass("combo-f").textbox($.extend({}, opts, {
			icons : icons,
			onChange : function() {
			}
		}));
		$(target).attr("comboName", $(target).attr("textboxName"));
		combo.combo = $(target).next();
		combo.combo.addClass("combo");
	};
	function destroy(target) {
		var combo = $.data(target, "combo");
		combo.panel.panel("destroy");
		$(target).textbox("destroy");
	};
	function comboArrow(target) {
		var panel = $.data(target, "combo").panel;
		if (panel.is(":visible")) {
			hidePanel(target);
		} else {
			var p = $(target).closest("div.combo-panel");
			$("div.combo-panel:visible").not(panel).not(p).panel("close");
			$(target).combo("showPanel");
		}
		$(target).combo("textbox").focus();
	};
	function comboClose(target) {
		$(target).find(".combo-f").each(function() {
			var p = $(this).combo("panel");
			if (p.is(":visible")) {
				p.panel("close");
			}
		});
	};
	function bindEvent(target) {
		$(document).unbind(".combo").bind("mousedown.combo", function(e) {
			var p = $(e.target).closest("span.combo,div.combo-p,div.easyui-top-wrap,div.datagrid-top-wrap");
			if (p.length) {
				comboClose(p);
				return;
			}
			$("body>div.combo-p>div.combo-panel:visible").panel("close");
			$("div.easyui-top-wrap>div.combo-p>div.combo-panel:visible").panel("close");
			$("div.datagrid-top-wrap>div.combo-p>div.combo-panel:visible").panel("close");
		});
	};
	function clickEvent(e) {
		var target = e.data.target;
		var combo = $.data(target, "combo");
		var opts = combo.options;
		var panel = combo.panel;
		if (!opts.editable) {
			comboArrow(target);
		} else {
			var p = $(target).closest("div.combo-panel");
			$("div.combo-panel:visible").not(panel).not(p).panel("close");
		}
	};
	function keydownEvent(e) {
		var target = e.data.target;
		var t = $(target);
		var combo = t.data("combo");
		var opts = t.combo("options");
		switch (e.keyCode) {
		case 38:
			opts.keyHandler.up.call(target, e);
			break;
		case 40:
			opts.keyHandler.down.call(target, e);
			break;
		case 37:
			opts.keyHandler.left.call(target, e);
			break;
		case 39:
			opts.keyHandler.right.call(target, e);
			break;
		case 13:
			e.preventDefault();
			opts.keyHandler.enter.call(target, e);
			return false;
		case 9:
		case 27:
			hidePanel(target);
			break;
		default:
			if (opts.editable) {
				if (combo.timer) {
					clearTimeout(combo.timer);
				}
				combo.timer = setTimeout(function() {
					var q = t.combo("getText");
					if (combo.previousText != q) {
						combo.previousText = q;
						t.combo("showPanel");
						opts.keyHandler.query.call(target, q, e);
						t.combo("validate");
					}
				}, opts.delay);
			}
		}
	};
	function showPanel(target) {
		var combo = $.data(target, "combo");
		var combocb = combo.combo;
		var panel = combo.panel;
		var opts = $(target).combo("options");
		panel.panel("move", {
			left : _getLeft(),
			top : _getTop()
		});
		if (panel.panel("options").closed) {
			panel.panel("open").panel("resize", {
				width : (opts.panelWidth ? opts.panelWidth : combocb._outerWidth()),
				height : opts.panelHeight
			});
			opts.onShowPanel.call(target);
		}
		(function() {
			if (panel.is(":visible")) {
				panel.panel("move", {
					left : _getLeft(),
					top : _getTop()
				});
				setTimeout(arguments.callee, 200);
			}
		})();
		function _getLeft() {
			var tablewrapCombo=$(target).data("tablewrapCombo");
			if(tablewrapCombo){
				var tableLeft=tablewrapCombo.position().left;
				var panelWidth=panel._outerWidth();
				var combocbWidth=combocb._outerWidth();
				var left = combocb.position().left;
				var winWidth=$(window)._outerWidth()+$(document).scrollLeft();
				if (tableLeft+left+panelWidth +38> winWidth) {
					return winWidth - panelWidth - combocbWidth -38;
				}else{
					return left-combocbWidth;
				}
			}else{
				var left = combocb.position().left;
				if (opts.panelAlign == "right") {
					left += combocb._outerWidth() - panel._outerWidth();
				}
				if (left + panel._outerWidth() > $(window)._outerWidth() + $(document).scrollLeft()) {
					left = $(window)._outerWidth() + $(document).scrollLeft() - panel._outerWidth();
				}
				if (left < 0) {
					left = 0;
				}
				return left;
			}
		};
		function _getTop() {
			var tablewrapCombo=$(target).data("tablewrapCombo");
			if(tablewrapCombo){
				var top = 0;
				var tableTop=tablewrapCombo.position().top;
				var pTop = combocb.position().top;
				var combOutH= combocb._outerHeight();
				var winOutH=$(window)._outerHeight();
				var panelOutH= panel._outerHeight();
				var docSTop= $(document).scrollTop();
				if (tableTop + pTop + panelOutH +5 > winOutH+docSTop) {
					return top-panelOutH+pTop-5;
				}else{
					return top+pTop+combOutH+5;
				}
			}else{
				var comnoTop=combocb.position().top;
				var top = comnoTop + combocb._outerHeight();
				//如果是在表格中，
				if (top + panel._outerHeight() > $(window)._outerHeight() + $(document).scrollTop()) {
					top = combocb.offset().top - panel._outerHeight();
				}
				if (top < $(document).scrollTop()) {
					top = combocb.offset().top + combocb._outerHeight();
				}
				return top;
			}
			
		};
	};
	function hidePanel(target) {
		var panel = $.data(target, "combo").panel;
		panel.panel("close");
	};
	function clear(target) {
		var combo = $.data(target, "combo");
		var opts = combo.options;
		var combocb = combo.combo;
		$(target).textbox("clear");
		if (opts.multiple) {
			combocb.find(".textbox-value").remove();
		} else {
			combocb.find(".textbox-value").val("");
		}
	};
	function setText(target, val) {
		var combo = $.data(target, "combo");
		var text = $(target).textbox("getText");
		if (text != val) {
			$(target).textbox("setText", val);
			combo.previousText = val;
		}
	};
	function getValues(target) {
		var values = [];
		var combobc = $.data(target, "combo").combo;
		var combo = $.data(target, "combo");
		var opts = combo.options;
		combobc.find(".textbox-value").each(function() {
			var strVale=$(this).val();
			if(strVale){
				var staList=strVale.split(opts.separator);
				for(var i=0;i<staList.length;i++){
					values.push(staList[i]);
				}
			}else{
				values.push("");
			}
		});
		return values;
	};
	function setValues(target, arrayVals) {
		if (!$.isArray(arrayVals)) {
			arrayVals = [ arrayVals ];
		}
		var combo = $.data(target, "combo");
		var opts = combo.options;
		var combobc = combo.combo;
		var values = getValues(target);
		combobc.find(".textbox-value").remove();
		var textboxName = $(target).attr("textboxName") || "";
		for (var i = 0; i < arrayVals.length; i++) {
			var textboxValue = $("<input type=\"hidden\" class=\"textbox-value\">").appendTo(combobc);
			textboxValue.attr("name", textboxName);
			if (opts.disabled) {
				textboxValue.attr("disabled", "disabled");
			}
			textboxValue.val(arrayVals[i]);
		}
		var _3d = (function() {
			if (values.length != arrayVals.length) {
				return true;
			}
			var a1 = $.extend(true, [], values);
			var a2 = $.extend(true, [], arrayVals);
			a1.sort();
			a2.sort();
			for (var i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return true;
				}
			}
			return false;
		})();
		if (_3d) {
			if (opts.multiple) {
				opts.onChange.call(target, arrayVals, values);
			} else {
				opts.onChange.call(target, arrayVals[0], values[0]);
			}
		}
	};
	function getValue(target) {
		var values = getValues(target);
		return values[0];
	};
	function setValue(target, values) {
		setValues(target, [ values ]);
	};
	
	function setValue(target, values) {
		setValues(target, [ values ]);
	};
	function initValue(target) {
		var opts = $.data(target, "combo").options;
		var changeEvent = opts.onChange;
		opts.onChange = function() {};
		if (opts.multiple) {
			setValues(target, opts.value ? opts.value : []);
		} else {
			setValue(target, opts.value);
		}
		opts.onChange = changeEvent;
	};
	$.fn.combo = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.combo.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.textbox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var combo = $.data(this, "combo");
			if (combo) {
				$.extend(combo.options, options);
				if (options.value != undefined) {
					combo.options.originalValue = options.value;
				}
			} else {
				combo = $.data(this, "combo", {
					options : $.extend({}, $.fn.combo.defaults, $.fn.combo.parseOptions(this), options),
					previousText : ""
				});
				combo.options.originalValue = combo.options.value;
			}
			createCombo(this);
			bindEvent(this);
			initValue(this);
		});
	};
	$.fn.combo.methods = {
		options : function(jq) {
			var opts = jq.textbox("options");
			return $.extend($.data(jq[0], "combo").options, {
				width : opts.width,
				height : opts.height,
				disabled : opts.disabled,
				readonly : opts.readonly
			});
		},
		panel : function(jq) {
			return $.data(jq[0], "combo").panel;
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		},
		showPanel : function(jq) {
			return jq.each(function() {
				showPanel(this);
			});
		},
		hidePanel : function(jq) {
			return jq.each(function() {
				hidePanel(this);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				clear(this);
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var _4d = $.data(this, "combo").options;
				if (_4d.multiple) {
					$(this).combo("setValues", _4d.originalValue);
				} else {
					$(this).combo("setValue", _4d.originalValue);
				}
			});
		},
		setText : function(jq, _4e) {
			return jq.each(function() {
				setText(this, _4e);
			});
		},
		getValues : function(jq) {
			return getValues(jq[0]);
		},
		setValues : function(jq, _4f) {
			return jq.each(function() {
				setValues(this, _4f);
			});
		},
		getValue : function(jq) {
			return getValue(jq[0]);
		},
		setValue : function(jq, _50) {
			return jq.each(function() {
				setValue(this, _50);
			});
		},
		setValue : function(jq, _50) {
			return jq.each(function() {
				setValue(this, _50);
			});
		}
	};
	$.fn.combo.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.textbox.parseOptions(target), $.parser
				.parseOptions(target, [ "separator", "panelAlign", {
					panelWidth : "number",
					hasDownArrow : "boolean",
					delay : "number",
					selectOnNavigation : "boolean"
				}, {
					panelMinWidth : "number",
					panelMaxWidth : "number",
					panelMinHeight : "number",
					panelMaxHeight : "number"
				} ]), {
			panelHeight : (t.attr("panelHeight") == "auto" ? "auto"
					: parseInt(t.attr("panelHeight")) || undefined),
			multiple : (t.attr("multiple") ? true : undefined)
		});
	};
	$.fn.combo.defaults = $.extend({}, $.fn.textbox.defaults, {
		inputEvents : {
			click : clickEvent,
			keydown : keydownEvent,
			paste : keydownEvent,
			drop : keydownEvent
		},
		isBody:false,
		selectObject:null,
		panelWidth : null,
		panelHeight : 200,
		panelMinWidth : null,
		panelMaxWidth : null,
		panelMinHeight : null,
		panelMaxHeight : null,
		panelAlign : "left",
		multiple : false,
		selectOnNavigation : true,
		separator : ",",
		hasDownArrow : true,
		delay : 200,
		regChars:[],
		keyHandler : {
			up : function(e) {
			},
			down : function(e) {
			},
			left : function(e) {
			},
			right : function(e) {
			},
			enter : function(e) {
			},
			query : function(q, e) {
			}
		},
		onShowPanel : function() {
		},
		onHidePanel : function() {
		},
		onChange : function(_52, _53) {
		}
	});
})(jQuery);
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
 * combobox - jQuery EasyUI
 * 
 * Dependencies:
 *   combo
 * 
 */
(function($){
	var COMBOBOX_SERNO = 0;
	
	function getRowIndex(target, value){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		var data = state.data;
		for(var i=0; i<data.length; i++){
			if (data[i][opts.valueField] == value){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * scroll panel to display the specified item
	 */
	function scrollTo(target, value){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		var item = opts.finder.getEl(target, value);
		if (item.length){
			if (item.position().top <= 0){
				var h = panel.scrollTop() + item.position().top;
				panel.scrollTop(h);
			} else if (item.position().top + item.outerHeight() > panel.height()){
				var h = panel.scrollTop() + item.position().top + item.outerHeight() - panel.height();
				panel.scrollTop(h);
			}
		}
	}
	
	function nav(target, dir){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combobox('panel');
		var item = panel.children('div.combobox-item-hover');
		if (!item.length){
			item = panel.children('div.combobox-item-selected');
		}
		item.removeClass('combobox-item-hover');
		var firstSelector = 'div.combobox-item:visible:not(.combobox-item-disabled):first';
		var lastSelector = 'div.combobox-item:visible:not(.combobox-item-disabled):last';
		if (!item.length){
			item = panel.children(dir=='next' ? firstSelector : lastSelector);
//			item = panel.children('div.combobox-item:visible:' + (dir=='next'?'first':'last'));
		} else {
			if (dir == 'next'){
				item = item.nextAll(firstSelector);
//				item = item.nextAll('div.combobox-item:visible:first');
				if (!item.length){
					item = panel.children(firstSelector);
//					item = panel.children('div.combobox-item:visible:first');
				}
			} else {
				item = item.prevAll(firstSelector);
//				item = item.prevAll('div.combobox-item:visible:first');
				if (!item.length){
					item = panel.children(lastSelector);
//					item = panel.children('div.combobox-item:visible:last');
				}
			}
		}
		if (item.length){
			item.addClass('combobox-item-hover');
			var row = opts.finder.getRow(target, item);
			if (row){
				scrollTo(target, row[opts.valueField]);
				if (opts.selectOnNavigation){
					select(target, row[opts.valueField]);
				}
			}
		}
	}
	
	/**
	 * select the specified value
	 */
	function select(target, value){
		var opts = $.data(target, 'combobox').options;
		var values = $(target).combo('getValues');
		if ($.inArray(value+'', values) == -1){
			if (opts.multiple){
				values.push(value);
			} else {
				values = [value];
			}
			setValues(target, values);
			opts.onSelect.call(target, opts.finder.getRow(target, value));
		}
	}
	
	/**
	 * unselect the specified value
	 */
	function unselect(target, value){
		var opts = $.data(target, 'combobox').options;
		var values = $(target).combo('getValues');
		var index = $.inArray(value+'', values);
		if (index >= 0){
			values.splice(index, 1);
			setValues(target, values);
			opts.onUnselect.call(target, opts.finder.getRow(target, value));
		}
	}
	
	/**
	 * set values
	 */
	function setValues(target, values, remainText){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		
		panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
		var vv = [], ss = [];
		for(var i=0; i<values.length; i++){
			var v = values[i];
			var s = v;
			opts.finder.getEl(target, v).addClass('combobox-item-selected');
			var row = opts.finder.getRow(target, v);
			if (row){
				s = row[opts.textField];
				opts.selectObject=row;
			}
			vv.push(v);
			ss.push(s);
		}
		
		$(target).combo('setValues', vv);
		if (!remainText){
			$(target).combo('setText', ss.join(opts.separator));
		}
	}
	
	/**
	 * load data, the old list items will be removed.
	 */
	function loadData(target, data, remainText){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		state.data = opts.loadFilter.call(target, data);
		state.groups = [];
		data = state.data;
		
		var selected = $(target).combobox('getValues');
		var dd = [];
		var group = undefined;
		if(opts.defaultEntry){
			var obj={};
			obj[opts.valueField]="";
			obj[opts.textField]="--\u8BF7\u9009\u62E9--";
			data.unshift(obj);
		}
		for(var i=0; i<data.length; i++){
			var row = data[i];
			var v = row[opts.valueField]+'';
			var s = row[opts.textField];
			var g = row[opts.groupField];
			
			if (g){
				if (group != g){
					group = g;
					state.groups.push(g);
					dd.push('<div id="' + (state.groupIdPrefix+'_'+(state.groups.length-1)) + '" class="combobox-group">');
					dd.push(opts.groupFormatter ? opts.groupFormatter.call(target, g) : g);
					dd.push('</div>');
				}
			} else {
				group = undefined;
			}
			
			var cls = 'combobox-item' + (row.disabled ? ' combobox-item-disabled' : '') + (g ? ' combobox-gitem' : '');
			dd.push('<div id="' + (state.itemIdPrefix+'_'+i) + '" class="' + cls + '">');
			dd.push(opts.formatter ? opts.formatter.call(target, row) : s);
			dd.push('</div>');
			
//			if (item['selected']){
//				(function(){
//					for(var i=0; i<selected.length; i++){
//						if (v == selected[i]) return;
//					}
//					selected.push(v);
//				})();
//			}
			if (row['selected'] && $.inArray(v, selected) == -1){
				selected.push(v);
			}
		}
		$(target).combo('panel').html(dd.join(''));
		
		if (opts.multiple){
			setValues(target, selected, remainText);
		} else {
			setValues(target, selected.length ? [selected[selected.length-1]] : [], remainText);
		}
		
		opts.onLoadSuccess.call(target, data);
	}
	
	/**
	 * request remote data if the url property is setted.
	 */
	function request(target, url, queryParams, remainText){
		var opts = $.data(target, 'combobox').options;
		if (url){
			opts.url = url;
		}
//		if (!opts.url) return;
		if (queryParams) {
			opts.queryParams = queryParams;
		}
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
		
		if (opts.onBeforeLoad.call(target, param) == false) return;

		opts.loader.call(target, param, function(data){
			loadData(target, data, remainText);
		}, function(){
			opts.onLoadError.apply(this, arguments);
		});
	}
	
	/**
	 * do the query action
	 */
	function doQuery(target, q){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		
		if (opts.multiple && !q){
			setValues(target, [], true);
		} else {
			setValues(target, [q], true);
		}
		
		if (opts.mode == 'remote'){
			request(target, null, {q:q}, true);
		} else {
			var panel = $(target).combo('panel');
			panel.find('div.combobox-item-selected,div.combobox-item-hover').removeClass('combobox-item-selected combobox-item-hover');
			panel.find('div.combobox-item,div.combobox-group').hide();
			var data = state.data;
			var vv = [];
			var qq = opts.multiple ? q.split(opts.separator) : [q];
			$.map(qq, function(q){
				q = $.trim(q);
				var group = undefined;
				for(var i=0; i<data.length; i++){
					var row = data[i];
					if (opts.filter.call(target, q, row)){
						var v = row[opts.valueField];
						var s = row[opts.textField];
						var g = row[opts.groupField];
						var item = opts.finder.getEl(target, v).show();
						if (s.toLowerCase() == q.toLowerCase()){
							vv.push(v);
							item.addClass('combobox-item-selected');
						}
						if (opts.groupField && group != g){
							$('#'+state.groupIdPrefix+'_'+$.inArray(g, state.groups)).show();
							group = g;
						}
					}
				}
			});
			setValues(target, vv, true);
		}
	}
	
	function doEnter(target){
		var t = $(target);
		var opts = t.combobox('options');
		var panel = t.combobox('panel');
		var item = panel.children('div.combobox-item-hover');
		if (item.length){
			var row = opts.finder.getRow(target, item);
			var value = row[opts.valueField];
			if (opts.multiple){
				if (item.hasClass('combobox-item-selected')){
					t.combobox('unselect', value);
				} else {
					t.combobox('select', value);
				}
			} else {
				t.combobox('select', value);
			}
		}
		var vv = [];
		$.map(t.combobox('getValues'), function(v){
			if (getRowIndex(target, v) >= 0){
				vv.push(v);
			}
		});
		t.combobox('setValues', vv);
		if (!opts.multiple){
			t.combobox('hidePanel');
		}
	}
	
	/**
	 * create the component
	 */
	function create(target){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		
		COMBOBOX_SERNO++;
		state.itemIdPrefix = '_easyui_combobox_i' + COMBOBOX_SERNO;
		state.groupIdPrefix = '_easyui_combobox_g' + COMBOBOX_SERNO;
		
		$(target).addClass('combobox-f');
		$(target).combo($.extend({}, opts, {
			onShowPanel: function(){
				$(target).combo('panel').find('div.combobox-item,div.combobox-group').show();
				scrollTo(target, $(target).combobox('getValue'));
				opts.onShowPanel.call(target);
			}
		}));
		
		$(target).combo('panel').unbind().bind('mouseover', function(e){
			$(this).children('div.combobox-item-hover').removeClass('combobox-item-hover');
			var item = $(e.target).closest('div.combobox-item');
			if (!item.hasClass('combobox-item-disabled')){
				item.addClass('combobox-item-hover');
			}
			e.stopPropagation();
		}).bind('mouseout', function(e){
			$(e.target).closest('div.combobox-item').removeClass('combobox-item-hover');
			e.stopPropagation();
		}).bind('click', function(e){
			var item = $(e.target).closest('div.combobox-item');
			if (!item.length || item.hasClass('combobox-item-disabled')){return;}
			var row = opts.finder.getRow(target, item);
			if (!row){return;}
			var value = row[opts.valueField];
			if (opts.multiple){
				if (item.hasClass('combobox-item-selected')){
					unselect(target, value);
				} else {
					select(target, value);
				}
			} else {
				select(target, value);
				$(target).combo('hidePanel');
			}
			e.stopPropagation();
		});
	}
	
	$.fn.combobox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.combobox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'combobox');
			if (state){
				$.extend(state.options, options);
				create(this);
			} else {
				state = $.data(this, 'combobox', {
					options: $.extend({}, $.fn.combobox.defaults, $.fn.combobox.parseOptions(this), options),
					data: []
				});
				create(this);
				var data = $.fn.combobox.parseData(this);
				if (data.length){
					loadData(this, data);
				}
			}
			if (state.options.data){
				loadData(this, state.options.data);
			}
			if (state.options.url) {
				request(this);
			}
		});
	};
	
	
	$.fn.combobox.methods = {
		options: function(jq){
			var copts = jq.combo('options');
			return $.extend($.data(jq[0], 'combobox').options, {
				width: copts.width,
				height: copts.height,
				originalValue: copts.originalValue,
				disabled: copts.disabled,
				readonly: copts.readonly
			});
		},
		getData: function(jq){
			return $.data(jq[0], 'combobox').data;
		},
		setValues: function(jq, values){
			return jq.each(function(){
				setValues(this, values);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				setValues(this, [value]);
			});
		},
		clear: function(jq){
			return jq.each(function(){
				$(this).combo('clear');
				var panel = $(this).combo('panel');
				panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
			});
		},
		reset: function(jq){
			return jq.each(function(){
				var opts = $(this).combobox('options');
				if (opts.multiple){
					$(this).combobox('setValues', opts.originalValue);
				} else {
					$(this).combobox('setValue', opts.originalValue);
				}
			});
		},
		loadData: function(jq, data){
			return jq.each(function(){
				loadData(this, data);
			});
		},
		reload: function(jq, url){
			return jq.each(function(){
				request(this, url);
			});
		},
		select: function(jq, value){
			return jq.each(function(){
				select(this, value);
			});
		},
		unselect: function(jq, value){
			return jq.each(function(){
				unselect(this, value);
			});
		}
	};
	
	$.fn.combobox.parseOptions = function(target){
		return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target,[
			'valueField','textField','groupField','mode','method','url'
		]));
	};
	
	$.fn.combobox.parseData = function(target){
		var data = [];
		var opts = $(target).combobox('options');
		$(target).children().each(function(){
			if (this.tagName.toLowerCase() == 'optgroup'){
				var group = $(this).attr('label');
				$(this).children().each(function(){
					_parseItem(this, group);
				});
			} else {
				_parseItem(this);
			}
		});
		return data;
		
		function _parseItem(el, group){
			var t = $(el);
			var row = {};
			row[opts.valueField] = t.attr('value')!=undefined ? t.attr('value') : t.text();
			row[opts.textField] = t.text();
			row['selected'] = t.is(':selected');
			row['disabled'] = t.is(':disabled');
			if (group){
				opts.groupField = opts.groupField || 'group';
				row[opts.groupField] = group;
			}
			data.push(row);
		}
	};
	
	$.fn.combobox.defaults = $.extend({}, $.fn.combo.defaults, {
		defaultEntry:false,
		valueField: 'value',
		textField: 'text',
		groupField: null,
		groupFormatter: function(group){return group;},
		mode: 'local',	// or 'remote'
		method: 'post',
		queryParams:null,
		url: null,
		data: null,
		keyHandler: {
			up: function(e){nav(this,'prev');e.preventDefault();},
			down: function(e){nav(this,'next');e.preventDefault();},
			left: function(e){},
			right: function(e){},
			enter: function(e){doEnter(this);},
			query: function(q,e){doQuery(this, q);}
		},
		filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].toLowerCase().indexOf(q.toLowerCase()) == 0;
		},
		formatter: function(row){
			var opts = $(this).combobox('options');
			return row[opts.textField];
		},
		loader: function(param, success, error){
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: param,
				dataType: 'json',
				success: function(data){
					success(data);
				},
				error: function(){
					error.apply(this, arguments);
				}
			});
		},
		loadFilter: function(data){
			return data;
		},
		finder:{
			getEl:function(target, value){
				var index = getRowIndex(target, value);
				var id = $.data(target, 'combobox').itemIdPrefix + '_' + index;
				return $('#'+id);
			},
			getRow:function(target, p){
				var state = $.data(target, 'combobox');
				var index = (p instanceof jQuery) ? p.attr('id').substr(state.itemIdPrefix.length+1) : getRowIndex(target, p);
				return state.data[parseInt(index)];
			}
		},
		onBeforeLoad: function(param){},
		onLoadSuccess: function(){},
		onLoadError: function(){},
		onSelect: function(record){},
		onUnselect: function(record){}
	});
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function createButcombo(target) {
		var butcombo = $.data(target, "butcombo");
		var opts = butcombo.options;
		if (!butcombo.panel) {
			if(opts.isBody){
				butcombo.panel = $("<div class=\"butcombo-panel\"></div>").appendTo("body");
			}else{
				var tablewrapCombo=$(target).closest("div.datagrid-top-wrap");
				if(tablewrapCombo.length<=0){
					$(target).data("tablewrapCombo",null);
					butcombo.panel = $("<div class=\"butcombo-panel\"></div>").appendTo($(target).wrap('<div class="easyui-top-wrap"></div>').parent());
				}else{
					$(target).data("tablewrapCombo",tablewrapCombo);
					butcombo.panel = $("<div class=\"butcombo-panel\"></div>").appendTo(tablewrapCombo);
				}
			}
			butcombo.panel.panel({
				minWidth : opts.panelMinWidth,
				maxWidth : opts.panelMaxWidth,
				minHeight : opts.panelMinHeight,
				maxHeight : opts.panelMaxHeight,
				doSize : false,
				closed : true,
				cls : "butcombo-p",
				method:opts.method,
				queryParams:opts.queryParams,
				href:opts.href,
				iframe:opts.iframe,
				style : {
					position : "absolute",
					zIndex : 10
				},
				onOpen : function() {
					var p = $(this).panel("panel");
					if ($.fn.menu) {
						p.css("z-index", $.fn.menu.defaults.zIndex++);
					} else {
						if ($.fn.window) {
							p.css("z-index", $.fn.window.defaults.zIndex++);
						}
					}
					$(this).panel("resize");
				},
				onBeforeClose : function() {
				},
				onClose : function() {
					var butcombo = $.data(target, "butcombo");
					if (butcombo) {
						butcombo.options.onHidePanel.call(target);
					}
				}
			});
		}
		$(target).attr("comboName", $(target).attr("textboxName"));
		butcombo.butcombo = $(target);
		butcombo.butcombo.addClass("butcombo");
	};
	function destroy(target) {
		var butcombo = $.data(target, "butcombo");
		butcombo.panel.panel("destroy");
	};
	function comboArrow(target) {
		var panel = $.data(target, "butcombo").panel;
		if (panel.is(":visible")) {
			hidePanel(target);
		} else {
			var opts = $(target).butcombo("options");
			var show=opts.onBeforeShowPanel.call(target);
			if(show){
				var p = $(target).closest("div.butcombo-panel");
				$("div.butcombo-panel:visible").not(panel).not(p).panel("close");
				$(target).butcombo("showPanel");
			}			
		}
	};
	function bindEvent(target) {
		$(target).unbind(".butcombo").bind("mousedown.butcombo",function(e) {
			comboArrow(target);
		});
		$(document).unbind(".butcombo").bind("mousedown.butcombo", function(e) {
			var p = $(e.target).closest("span.butcombo,div.butcombo-p,div.easyui-top-wrap,div.datagrid-top-wrap");
			if (p.length) {
				return;
			}
			$("body>div.butcombo-p>div.butcombo-panel:visible").panel("close");
			$("div.easyui-top-wrap>div.butcombo-p>div.butcombo-panel:visible").panel("close");
			$("div.datagrid-top-wrap>div.butcombo-p>div.butcombo-panel:visible").panel("close");
		});
	};
	function clickEvent(e) {
		var target = e.data.target;
		var butcombo = $.data(target, "butcombo");
		var opts = butcombo.options;
		var panel = butcombo.panel;
		if (!opts.editable) {
			comboArrow(target);
		} else {
			var p = $(target).closest("div.butcombo-panel");
			$("div.butcombo-panel:visible").not(panel).not(p).panel("close");
		}
	};
	function keydownEvent(e) {
		var target = e.data.target;
		var t = $(target);
		var butcombo = t.data("butcombo");
		var opts = t.butcombo("options");
		switch (e.keyCode) {
		case 38:
			opts.keyHandler.up.call(target, e);
			break;
		case 40:
			opts.keyHandler.down.call(target, e);
			break;
		case 37:
			opts.keyHandler.left.call(target, e);
			break;
		case 39:
			opts.keyHandler.right.call(target, e);
			break;
		case 13:
			e.preventDefault();
			opts.keyHandler.enter.call(target, e);
			return false;
		case 9:
		case 27:
			hidePanel(target);
			break;
		}
	};
	function showContent(target,html) {
		var butcombo = $.data(target, "butcombo");
		var panel = butcombo.panel;
		$(html).appendTo(panel);
	}
	
	function showPanel(target) {
		var butcombo = $.data(target, "butcombo");
		var combocb = butcombo.butcombo;
		var panel = butcombo.panel;
		var opts = $(target).butcombo("options");
		panel.panel("move", {
			left : _getLeft(),
			top : _getTop()
		});
		if (panel.panel("options").closed) {
			panel.panel("open").panel("resize", {
				width : (opts.panelWidth ? opts.panelWidth : combocb._outerWidth()),
				height :(opts.panelHeight ? opts.panelHeight : combocb._outerHeight())
			});
			opts.onShowPanel.call(target);
		}
		(function() {
			if (panel.is(":visible")) {
				panel.panel("move", {
					left : _getLeft(),
					top : _getTop()
				});
				setTimeout(arguments.callee, 200);
			}
		})();
		function _getLeft() {
			var tablewrapCombo=$(target).data("tablewrapCombo");
			if(tablewrapCombo){
				var tableLeft=tablewrapCombo.position().left;
				var panelWidth=panel._outerWidth();
				var combocbWidth=combocb._outerWidth();
				var left = combocb.position().left;
				var winWidth=$(window)._outerWidth()+$(document).scrollLeft();
				if (tableLeft+left+panelWidth +38> winWidth) {
					return winWidth - panelWidth - combocbWidth -38;
				}else{
					return left-combocbWidth;
				}
			}else{
				var left = combocb.position().left;
				if (opts.panelAlign == "right") {
					left += combocb._outerWidth() - panel._outerWidth();
				}
				if (left + panel._outerWidth() > $(window)._outerWidth() + $(document).scrollLeft()) {
					left = $(window)._outerWidth() + $(document).scrollLeft() - panel._outerWidth();
				}
				if (left < 0) {
					left = 0;
				}
				return left;
			}
		};
		function _getTop() {
			var tablewrapCombo=$(target).data("tablewrapCombo");
			if(tablewrapCombo){
				var top = 0;
				var tableTop=tablewrapCombo.position().top;
				var pTop = combocb.position().top;
				var combOutH= combocb._outerHeight();
				var winOutH=$(window)._outerHeight();
				var panelOutH= panel._outerHeight();
				var docSTop= $(document).scrollTop();
				if (tableTop + pTop + panelOutH +5 > winOutH+docSTop) {
					return top-panelOutH+pTop-5;
				}else{
					return top+pTop+combOutH+5;
				}
			}else{
				var top = combocb.position().top + combocb._outerHeight();
				if (top + panel._outerHeight() > $(window)._outerHeight() + $(document).scrollTop()) {
					top = combocb.offset().top - panel._outerHeight();
				}
				if (top < $(document).scrollTop()) {
					top = combocb.offset().top + combocb._outerHeight();
				}
				return top;
			}
			
		};
	};
	function hidePanel(target) {
		var panel = $.data(target, "butcombo").panel;
		panel.panel("close");
	};
	
	$.fn.butcombo = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.butcombo.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.linkbutton(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var butcombo = $.data(this, "butcombo");
			if (butcombo) {
				$.extend(butcombo.options, options);
				if (options.value != undefined) {
					butcombo.options.originalValue = options.value;
				}
			} else {
				butcombo = $.data(this, "butcombo", {
					options : $.extend({}, $.fn.butcombo.defaults, $.fn.butcombo.parseOptions(this), options),
					previousText : ""
				});
				butcombo.options.originalValue = butcombo.options.value;
			}
			createButcombo(this);
			bindEvent(this);
			if(butcombo.options.content){
				showContent(this,butcombo.options.content);
			}
		});
	};
	$.fn.butcombo.methods = {
		options : function(jq) {
			return $.data(jq[0], "butcombo").options;
		},
		panel : function(jq) {
			return $.data(jq[0], "butcombo").panel;
		},
		content : function(jq,html) {
			return jq.each(function() {
				showContent(this,html);
			});
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		},
		showPanel : function(jq) {
			return jq.each(function() {
				showPanel(this);
			});
		},
		hidePanel : function(jq) {
			return jq.each(function() {
				hidePanel(this);
			});
		}
	};
	$.fn.butcombo.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [  "panelAlign", {
					panelWidth : "number"
				}, {
					panelMinWidth : "number",
					panelMaxWidth : "number",
					panelMinHeight : "number",
					panelMaxHeight : "number"
				} ]), {
			panelHeight : (t.attr("panelHeight") == "auto" ? "auto" : parseInt(t.attr("panelHeight")) || undefined)
		});
	};
	$.fn.butcombo.defaults = $.extend({}, {
		inputEvents : {
			click : clickEvent,
			keydown : keydownEvent,
			paste : keydownEvent,
			drop : keydownEvent
		},
		isBody:false,
		panelWidth : 500,
		panelHeight : 260,
		panelMinWidth : null,
		panelMaxWidth : null,
		panelMinHeight : null,
		panelMaxHeight : null,
		queryParams:null,
		href:null,
		iframe:null,
		content:null,
		method:"get",
		panelAlign : "right",
		keyHandler : {
			up : function(e) {
			},
			down : function(e) {
			},
			left : function(e) {
			},
			right : function(e) {
			},
			enter : function(e) {
			},
			query : function(q, e) {
			}
		},
		onBeforeShowPanel : function() {
			return true;
		},
		onShowPanel : function() {
		},
		onHidePanel : function() {
		},
		onChange : function(_52, _53) {
		}
	});
})(jQuery);
﻿/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function _1(el, _2, _3, _4) {
		var _5 = $(el).window("window");
		if (!_5) {
			return;
		}
		switch (_2) {
		case null:
			_5.show();
			break;
		case "slide":
			_5.slideDown(_3);
			break;
		case "fade":
			_5.fadeIn(_3);
			break;
		case "show":
			_5.show(_3);
			break;
		}
		var _6 = null;
		if (_4 > 0) {
			_6 = setTimeout(function() {
				_7(el, _2, _3);
			}, _4);
		}
		_5.hover(function() {
			if (_6) {
				clearTimeout(_6);
			}
		}, function() {
			if (_4 > 0) {
				_6 = setTimeout(function() {
					_7(el, _2, _3);
				}, _4);
			}
		});
	};
	function _7(el, _8, _9) {
		if (el.locked == true) {
			return;
		}
		el.locked = true;
		var _a = $(el).window("window");
		if (!_a) {
			return;
		}
		switch (_8) {
		case null:
			_a.hide();
			break;
		case "slide":
			_a.slideUp(_9);
			break;
		case "fade":
			_a.fadeOut(_9);
			break;
		case "show":
			_a.hide(_9);
			break;
		}
		setTimeout(function() {
			$(el).window("destroy");
		}, _9);
	};
	function _b(_c,winType) {
		var message = "<div>" + _c.msg + "</div>";
		switch (winType) {
		case "error":
			message = "<div class=\"messager-icon messager-error\"></div>" + message;
			break;
		case "info":
			message = "<div class=\"messager-icon messager-info\"></div>" + message;
			break;
		case "question":
			message = "<div class=\"messager-icon messager-question\"></div>" + message;
			break;
		case "warning":
			message = "<div class=\"messager-icon messager-warning\"></div>" + message;
			break;
		}
		message += "<div style=\"clear:both;\"/>";
		_c.msg=message;
		var _d = $.extend({}, $.fn.window.defaults, {
			collapsible : false,
			minimizable : false,
			maximizable : false,
			shadow : false,
			draggable : false,
			resizable : false,
			closed : true,
			style : {
				left : "",
				top : "",
				right : 0,
				zIndex : $.fn.window.defaults.zIndex++,
				bottom : -document.body.scrollTop - document.documentElement.scrollTop
			},
			onBeforeOpen : function() {
				_1(this, _d.showType, _d.showSpeed, _d.timeout);
				return false;
			},
			onBeforeClose : function() {
				_7(this, _d.showType, _d.showSpeed);
				return false;
			}
		}, {
			title : "",
			width : 250,
			showType : "slide",
			showSpeed : 600,
			msg : "",
			timeout : 4000
		}, _c);
		_d.style.zIndex = $.fn.window.defaults.zIndex++;
		var _e = $("<div class=\"messager-body\"></div>").html(_d.msg).appendTo("body");
		_e.window(_d);
		_e.window("window").css(_d.style);
		_e.window("window").addClass("message");
		_e.window("open");
		return _e;
	};
	function _f(_10, _11, _12) {
		var win = $("<div class=\"messager-body\"></div>").appendTo("body");
		win.append(_11);
		if (_12) {
			var tb = $("<div class=\"messager-button\"></div>").appendTo(win);
			for ( var _13 in _12) {
				$("<a></a>").attr("href", "javascript:void(0)").text(_13).css("margin-left", 10).bind("click", eval(_12[_13])).appendTo(tb).linkbutton();
			}
		}
		win.window({
			title : _10,
			noheader : (_10 ? false : true),
			width : 300,
			height : "auto",
			modal : true,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			resizable : false,
			onClose : function() {
				setTimeout(function() {
					win.window("destroy");
				}, 100);
			}
		});
		win.window("window").addClass("messager-window messager-expand-window");
		win.children("div.messager-button").children("a:first").focus();
		return win;
	};
	$.messager = {
		show : function(_14,winType) {
			return _b(_14,winType);
		},
		alert : function(_15, msg, _16, fn) {
			var _17 = "<div>" + msg + "</div>";
			switch (_16) {
			case "error":
				_17 = "<div class=\"messager-icon messager-error\"></div>"
						+ _17;
				break;
			case "info":
				_17 = "<div class=\"messager-icon messager-info\"></div>" + _17;
				break;
			case "question":
				_17 = "<div class=\"messager-icon messager-question\"></div>"
						+ _17;
				break;
			case "warning":
				_17 = "<div class=\"messager-icon messager-warning\"></div>"
						+ _17;
				break;
			}
			_17 += "<div style=\"clear:both;\"/>";
			var _18 = {};
			_18[$.messager.defaults.ok] = function() {
				win.window("close");
				if (fn) {
					fn();
					return false;
				}
			};
			var win = _f(_15, _17, _18);
			return win;
		},
		confirm : function(_19, msg, fn) {
			var _1a = "<div class=\"messager-icon messager-question\"></div>"
					+ "<div>" + msg + "</div>" + "<div style=\"clear:both;\"/>";
			var _1b = {};
			_1b[$.messager.defaults.ok] = function() {
				if (fn) {
					fn(true);
					win.window("close");
					return false;
				}
				win.window("close");
			};
			_1b[$.messager.defaults.cancel] = function() {
				if (fn) {
					fn(false);
					win.window("close");
					return false;
				}
				win.window("close");
			};
			var win = _f(_19, _1a, _1b);
			return win;
		},
		confirmNoCancle : function(_19, msg, fn) {
			var _1a = "<div class=\"messager-icon messager-question\"></div>"
					+ "<div>" + msg + "</div>" + "<div style=\"clear:both;\"/>";
			var _1b = {};
			_1b[$.messager.defaults.ok] = function() {
				if (fn) {
					fn(true);
					win.window("close");
					return false;
				}
				win.window("close");
			};
			var win = _f(_19, _1a, _1b);
			return win;
		},
		prompt : function(_1c, msg, fn) {
			var _1d = "<div class=\"messager-icon messager-question\"></div>"
					+ "<div>"
					+ msg
					+ "</div>"
					+ "<br/>"
					+ "<div style=\"clear:both;\"/>"
					+ "<div><input class=\"messager-input\" type=\"text\"/></div>";
			var _1e = {};
			_1e[$.messager.defaults.ok] = function() {
				win.window("close");
				if (fn) {
					fn($(".messager-input", win).val());
					return false;
				}
			};
			_1e[$.messager.defaults.cancel] = function() {
				win.window("close");
				if (fn) {
					fn();
					return false;
				}
			};
			var win = _f(_1c, _1d, _1e);
			win.children("input.messager-input").focus();
			return win;
		},
		progress : function(_1f) {
			var _20 = {
				bar : function() {
					return $("body>div.messager-window").find(
							"div.messager-p-bar");
				},
				close : function() {
					var win = $("body>div.messager-window>div.messager-body:has(div.messager-progress)");
					if (win.length) {
						win.window("close");
					}
				}
			};
			if (typeof _1f == "string") {
				var _21 = _20[_1f];
				return _21();
			}
			var _22 = $.extend({
				title : "",
				msg : "",
				text : undefined,
				interval : 300
			}, _1f || {});
			var _23 = "<div class=\"messager-progress\"><div class=\"messager-p-msg\"></div><div class=\"messager-p-bar\"></div></div>";
			var win = _f(_22.title, _23, null);
			win.find("div.messager-p-msg").html(_22.msg);
			var bar = win.find("div.messager-p-bar");
			bar.progressbar({
				text : _22.text
			});
			win.window({
				closable : false,
				onClose : function() {
					if (this.timer) {
						clearInterval(this.timer);
					}
					$(this).window("destroy");
				}
			});
			if (_22.interval) {
				win[0].timer = setInterval(function() {
					var v = bar.progressbar("getValue");
					v += 10;
					if (v > 100) {
						v = 0;
					}
					bar.progressbar("setValue", v);
				}, _22.interval);
			}
			return win;
		}
	};
	$.messager.defaults = {
		ok : "\u786E\u8BA4",
		cancel : "\u53D6\u6D88"
	};
})(jQuery);
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
 * calendar - jQuery EasyUI
 * 
 */
(function($){
	
	function setSize(target, param){
		var opts = $.data(target, 'calendar').options;
		var t = $(target);
		if (param){
			$.extend(opts, {
				width: param.width,
				height: param.height
			});
		}
		t._size(opts, t.parent());
		t.find('.calendar-body')._outerHeight(t.height() - t.find('.calendar-header')._outerHeight());
		if (t.find('.calendar-menu').is(':visible')){
			showSelectMenus(target);
		}
	}
	
	function init(target){
		$(target).addClass('calendar').html(
				'<div class="calendar-header">' +
					'<div class="calendar-prevmonth"></div>' +
					'<div class="calendar-nextmonth"></div>' +
					'<div class="calendar-prevyear"></div>' +
					'<div class="calendar-nextyear"></div>' +
					'<div class="calendar-title">' +
						'<span>Aprial 2010</span>' +
					'</div>' +
				'</div>' +
				'<div class="calendar-body">' +
					'<div class="calendar-menu">' +
						'<div class="calendar-menu-year-inner">' +
							'<span class="calendar-menu-prev"></span>' +
							'<span><input class="calendar-menu-year" type="text"></input></span>' +
							'<span class="calendar-menu-next"></span>' +
						'</div>' +
						'<div class="calendar-menu-month-inner">' +
						'</div>' +
					'</div>' +
				'</div>'
		);
		
		$(target).find('.calendar-title span').hover(
			function(){$(this).addClass('calendar-menu-hover');},
			function(){$(this).removeClass('calendar-menu-hover');}
		).click(function(){
			var menu = $(target).find('.calendar-menu');
			if (menu.is(':visible')){
				menu.hide();
			} else {
				showSelectMenus(target);
			}
		});
		
		$('.calendar-prevmonth,.calendar-nextmonth,.calendar-prevyear,.calendar-nextyear', target).hover(
			function(){$(this).addClass('calendar-nav-hover');},
			function(){$(this).removeClass('calendar-nav-hover');}
		);
		$(target).find('.calendar-nextmonth').click(function(){
			showMonth(target, 1);
		});
		$(target).find('.calendar-prevmonth').click(function(){
			showMonth(target, -1);
		});
		$(target).find('.calendar-nextyear').click(function(){
			showYear(target, 1);
		});
		$(target).find('.calendar-prevyear').click(function(){
			showYear(target, -1);
		});
		
		$(target).bind('_resize', function(e,force){
			if ($(this).hasClass('easyui-fluid') || force){
				setSize(target);
			}
			return false;
		});
	}
	
	/**
	 * show the calendar corresponding to the current month.
	 */
	function showMonth(target, delta){
		var opts = $.data(target, 'calendar').options;
		opts.month += delta;
		if (opts.month > 12){
			opts.year++;
			opts.month = 1;
		} else if (opts.month < 1){
			opts.year--;
			opts.month = 12;
		}
		show(target);
		
		var menu = $(target).find('.calendar-menu-month-inner');
		menu.find('span.calendar-selected').removeClass('calendar-selected');
		menu.find('span:eq(' + (opts.month-1) + ')').addClass('calendar-selected');
	}
	
	/**
	 * show the calendar corresponding to the current year.
	 */
	function showYear(target, delta){
		var opts = $.data(target, 'calendar').options;
		opts.year += delta;
		show(target);
		
		var menu = $(target).find('.calendar-menu-year');
		menu.val(opts.year);
	}
	
	/**
	 * show the select menu that can change year or month, if the menu is not be created then create it.
	 */
	function showSelectMenus(target){
		var opts = $.data(target, 'calendar').options;
		$(target).find('.calendar-menu').show();
		
		if ($(target).find('.calendar-menu-month-inner').is(':empty')){
			$(target).find('.calendar-menu-month-inner').empty();
			var t = $('<table class="calendar-mtable"></table>').appendTo($(target).find('.calendar-menu-month-inner'));
			var idx = 0;
			for(var i=0; i<3; i++){
				var tr = $('<tr></tr>').appendTo(t);
				for(var j=0; j<4; j++){
					$('<td class="calendar-menu-month"></td>').html(opts.months[idx++]).attr('abbr',idx).appendTo(tr);
				}
			}
			
			$(target).find('.calendar-menu-prev,.calendar-menu-next').hover(
					function(){$(this).addClass('calendar-menu-hover');},
					function(){$(this).removeClass('calendar-menu-hover');}
			);
			$(target).find('.calendar-menu-next').click(function(){
				var y = $(target).find('.calendar-menu-year');
				if (!isNaN(y.val())){
					y.val(parseInt(y.val()) + 1);
					setDate();
				}
			});
			$(target).find('.calendar-menu-prev').click(function(){
				var y = $(target).find('.calendar-menu-year');
				if (!isNaN(y.val())){
					y.val(parseInt(y.val() - 1));
					setDate();
				}
			});
			
			$(target).find('.calendar-menu-year').keypress(function(e){
				if (e.keyCode == 13){
					setDate(true);
				}
			});
			
			$(target).find('.calendar-menu-month').hover(
					function(){$(this).addClass('calendar-menu-hover');},
					function(){$(this).removeClass('calendar-menu-hover');}
			).click(function(){
				var menu = $(target).find('.calendar-menu');
				menu.find('.calendar-selected').removeClass('calendar-selected');
				$(this).addClass('calendar-selected');
				setDate(true);
			});
		}
		
		function setDate(hideMenu){
			var menu = $(target).find('.calendar-menu');
			var year = menu.find('.calendar-menu-year').val();
			var month = menu.find('.calendar-selected').attr('abbr');
			if (!isNaN(year)){
				opts.year = parseInt(year);
				opts.month = parseInt(month);
				show(target);
			}
			if (hideMenu){menu.hide();}
		}
		
		var body = $(target).find('.calendar-body');
		var sele = $(target).find('.calendar-menu');
		var seleYear = sele.find('.calendar-menu-year-inner');
		var seleMonth = sele.find('.calendar-menu-month-inner');
		
		seleYear.find('input').val(opts.year).focus();
		seleMonth.find('span.calendar-selected').removeClass('calendar-selected');
		seleMonth.find('span:eq('+(opts.month-1)+')').addClass('calendar-selected');
		
		sele._outerWidth(body._outerWidth());
		sele._outerHeight(body._outerHeight());
		seleMonth._outerHeight(sele.height() - seleYear._outerHeight());
	}
	
	/**
	 * get weeks data.
	 */
	function getWeeks(target, year, month){
		var opts = $.data(target, 'calendar').options;
		var dates = [];
		var lastDay = new Date(year, month, 0).getDate();
		for(var i=1; i<=lastDay; i++) dates.push([year,month,i]);
		
		// group date by week
		var weeks = [], week = [];
//		var memoDay = 0;
		var memoDay = -1;
		while(dates.length > 0){
			var date = dates.shift();
			week.push(date);
			var day = new Date(date[0],date[1]-1,date[2]).getDay();
			if (memoDay == day){
				day = 0;
			} else if (day == (opts.firstDay==0 ? 7 : opts.firstDay) - 1){
				weeks.push(week);
				week = [];
			}
			memoDay = day;
		}
		if (week.length){
			weeks.push(week);
		}
		
		var firstWeek = weeks[0];
		if (firstWeek.length < 7){
			while(firstWeek.length < 7){
				var firstDate = firstWeek[0];
				var date = new Date(firstDate[0],firstDate[1]-1,firstDate[2]-1);
				firstWeek.unshift([date.getFullYear(), date.getMonth()+1, date.getDate()]);
			}
		} else {
			var firstDate = firstWeek[0];
			var week = [];
			for(var i=1; i<=7; i++){
				var date = new Date(firstDate[0], firstDate[1]-1, firstDate[2]-i);
				week.unshift([date.getFullYear(), date.getMonth()+1, date.getDate()]);
			}
			weeks.unshift(week);
		}
		
		var lastWeek = weeks[weeks.length-1];
		while(lastWeek.length < 7){
			var lastDate = lastWeek[lastWeek.length-1];
			var date = new Date(lastDate[0], lastDate[1]-1, lastDate[2]+1);
			lastWeek.push([date.getFullYear(), date.getMonth()+1, date.getDate()]);
		}
		if (weeks.length < 6){
			var lastDate = lastWeek[lastWeek.length-1];
			var week = [];
			for(var i=1; i<=7; i++){
				var date = new Date(lastDate[0], lastDate[1]-1, lastDate[2]+i);
				week.push([date.getFullYear(), date.getMonth()+1, date.getDate()]);
			}
			weeks.push(week);
		}
		
		return weeks;
	}
	
	/**
	 * show the calendar day.
	 */
	function show(target){
		var opts = $.data(target, 'calendar').options;
		if (opts.current && !opts.validator.call(target, opts.current,opts.minDate,opts.maxDate)){
			opts.current = null;
		}
		
		var now = new Date();
		var todayInfo = now.getFullYear()+','+(now.getMonth()+1)+','+now.getDate();
		//var currentInfo = opts.current ? (opts.current.getFullYear()+','+(opts.current.getMonth()+1)+','+opts.current.getDate()) : '';
		// calulate the saturday and sunday index
		var saIndex = 6 - opts.firstDay;
		var suIndex = saIndex + 1;
		if (saIndex >= 7) saIndex -= 7;
		if (suIndex >= 7) suIndex -= 7;
		
		$(target).find('.calendar-title span').html(opts.months[opts.month-1] + ' ' + opts.year);
		
		var body = $(target).find('div.calendar-body');
		body.children('table').remove();
		
		var data = ['<table class="calendar-dtable" cellspacing="0" cellpadding="0" border="0">'];
		data.push('<thead><tr>');
		for(var i=opts.firstDay; i<opts.weeks.length; i++){
			data.push('<th>'+opts.weeks[i]+'</th>');
		}
		for(var i=0; i<opts.firstDay; i++){
			data.push('<th>'+opts.weeks[i]+'</th>');
		}
		data.push('</tr></thead>');
		
		data.push('<tbody>');
		var weeks = getWeeks(target, opts.year, opts.month);
		for(var i=0; i<weeks.length; i++){
			var week = weeks[i];
			var cls = '';
			if (i == 0){cls = 'calendar-first';}
			else if (i == weeks.length - 1){cls = 'calendar-last';}
			data.push('<tr class="' + cls + '">');
			for(var j=0; j<week.length; j++){
				var day = week[j];
				var s = day[0]+','+day[1]+','+day[2];
				var dvalue = new Date(day[0], parseInt(day[1])-1, day[2]);
				var d = opts.formatter.call(target, dvalue);
				var css = opts.styler.call(target, dvalue);
				var classValue = '';
				var styleValue = '';
				if (typeof css == 'string'){
					styleValue = css;
				} else if (css){
					classValue = css['class'] || '';
					styleValue = css['style'] || '';
				}
				var today="calendar-state-default";
				var cls = 'calendar-day';
				if (!(opts.year == day[0] && opts.month == day[1])){
					cls += ' calendar-other-month';
					today += ' calendar-span-other-month';
				}
				//if (s == currentInfo){cls += ' calendar-selected';}
				if (j == saIndex){cls += ' calendar-saturday';}
				else if (j == suIndex){cls += ' calendar-sunday';}
				if (j == 0){cls += ' calendar-first';}
				else if (j == week.length-1){cls += ' calendar-last';}
				
				cls += ' ' + classValue;
				
				if (!opts.validator.call(target, dvalue,opts.minDate,opts.maxDate)){
					cls += ' calendar-disabled';
				}
				if (s == todayInfo){today = ' calendar-today';}
				data.push('<td class="' + cls + '" abbr="' + s + '" style="' + styleValue + '"><span class="' + today + '">' + d + '</span></td>');
			}
			data.push('</tr>');
		}
		data.push('</tbody>');
		data.push('</table>');
		
		body.append(data.join(''));
		
		var t = body.children('table.calendar-dtable').prependTo(body);
		
		t.find('td.calendar-day:not(.calendar-disabled)').hover(
			function(){$(this).children("span").addClass('calendar-hover');},
			function(){$(this).children("span").removeClass('calendar-hover');}
		).click(function(){
			var oldValue = opts.current;
			t.find('.calendar-selected').removeClass('calendar-selected');
			$(this).children("span").addClass('calendar-selected');
			var parts = $(this).attr('abbr').split(',');
			opts.current = new Date(parts[0], parseInt(parts[1])-1, parts[2]);
			opts.onSelect.call(target, opts.current);
			if (!oldValue || oldValue.getTime() != opts.current.getTime()){
				opts.onChange.call(target, opts.current, oldValue);
			}
		});
	}
	
	$.fn.calendar = function(options, param){
		if (typeof options == 'string'){
			return $.fn.calendar.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'calendar');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'calendar', {
					options:$.extend({}, $.fn.calendar.defaults, $.fn.calendar.parseOptions(this), options)
				});
				init(this);
			}
			if (state.options.border == false){
				$(this).addClass('calendar-noborder');
			}
			setSize(this);
			show(this);
			$(this).find('div.calendar-menu').hide();	// hide the calendar menu
		});
	};
	
	$.fn.calendar.methods = {
		options: function(jq){
			return $.data(jq[0], 'calendar').options;
		},
		show : function(jq, param){
			return jq.each(function(){
				show(this, param);
			});
		},
		resize: function(jq, param){
			return jq.each(function(){
				setSize(this, param);
			});
		},
		moveTo: function(jq, date){
			return jq.each(function(){
				var opts = $(this).calendar('options');
				if (opts.validator.call(this, date,opts.minDate,opts.maxDate)){
					var oldValue = opts.current;
					$(this).calendar({
						year: date.getFullYear(),
						month: date.getMonth()+1,
						current: date
					});
					if (!oldValue || oldValue.getTime() != date.getTime()){
						opts.onChange.call(this, opts.current, oldValue);
					}
				}
			});
		}
	};
	
	$.fn.calendar.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target, [
			{firstDay:'number',fit:'boolean',border:'boolean'}
		]));
	};
	
	$.fn.calendar.defaults = {
		width:180,
		height:180,
		minDate:null,
		maxDate:null,
		fit:false,
		border:true,
		firstDay:0,
		weeks:['S','M','T','W','T','F','S'],
		months:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
		year:new Date().getFullYear(),
		month:new Date().getMonth()+1,
		current:(function(){
			var d = new Date();
			return new Date(d.getFullYear(), d.getMonth(), d.getDate());
		})(),
		
		formatter:function(date){return date.getDate();},
		styler:function(date){return '';},
		validator:function(date,minDate,maxDate){return true;},
		
		onSelect: function(date){},
		onChange: function(newDate, oldDate){}
	};
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
(function($){
function _1(_2){
var _3=$.data(_2,"spinner");
var _4=_3.options;
var _5=$.extend(true,[],_4.icons);
_5.push({iconCls:"spinner-arrow",handler:function(e){
_6(e);
}});
$(_2).addClass("spinner-f").textbox($.extend({},_4,{icons:_5}));
var _7=$(_2).textbox("getIcon",_5.length-1);
_7.append("<a href=\"javascript:void(0)\" class=\"spinner-arrow-up\"></a>");
_7.append("<a href=\"javascript:void(0)\" class=\"spinner-arrow-down\"></a>");
$(_2).attr("spinnerName",$(_2).attr("textboxName"));
_3.spinner=$(_2).next();
_3.spinner.addClass("spinner");
};
function _6(e){
var _8=e.data.target;
var _9=$(_8).spinner("options");
var up=$(e.target).closest("a.spinner-arrow-up");
if(up.length){
_9.spin.call(_8,false);
_9.onSpinUp.call(_8);
$(_8).spinner("validate");
}
var _a=$(e.target).closest("a.spinner-arrow-down");
if(_a.length){
_9.spin.call(_8,true);
_9.onSpinDown.call(_8);
$(_8).spinner("validate");
}
};
$.fn.spinner=function(_b,_c){
if(typeof _b=="string"){
var _d=$.fn.spinner.methods[_b];
if(_d){
return _d(this,_c);
}else{
return this.textbox(_b,_c);
}
}
_b=_b||{};
return this.each(function(){
var _e=$.data(this,"spinner");
if(_e){
$.extend(_e.options,_b);
}else{
_e=$.data(this,"spinner",{options:$.extend({},$.fn.spinner.defaults,$.fn.spinner.parseOptions(this),_b)});
}
_1(this);
});
};
$.fn.spinner.methods={options:function(jq){
var _f=jq.textbox("options");
return $.extend($.data(jq[0],"spinner").options,{width:_f.width,value:_f.value,originalValue:_f.originalValue,disabled:_f.disabled,readonly:_f.readonly});
}};
$.fn.spinner.parseOptions=function(_10){
return $.extend({},$.fn.textbox.parseOptions(_10),$.parser.parseOptions(_10,["min","max",{increment:"number"}]));
};
$.fn.spinner.defaults=$.extend({},$.fn.textbox.defaults,{regChars:[],min:null,max:null,increment:1,spin:function(_11){
},onSpinUp:function(){
},onSpinDown:function(){
}});
})(jQuery);

/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function _1(_2) {
		var _3 = 0;
		if (_2.selectionStart) {
			_3 = _2.selectionStart;
		} else {
			if (_2.createTextRange) {
				var _4 = _2.createTextRange();
				var s = document.selection.createRange();
				s.setEndPoint("StartToStart", _4);
				_3 = s.text.length;
			}
		}
		return _3;
	}
	;
	function _5(_6, _7, _8) {
		if (_6.selectionStart) {
			_6.setSelectionRange(_7, _8);
		} else {
			if (_6.createTextRange) {
				var _9 = _6.createTextRange();
				_9.collapse();
				_9.moveEnd("character", _8);
				_9.moveStart("character", _7);
				_9.select();
			}
		}
	}
	;
	function _a(_b) {
		var _c = $.data(_b, "timespinner").options;
		$(_b).addClass("timespinner-f").spinner(_c);
		var _d = _c.formatter.call(_b, _c.parser.call(_b, _c.value));
		$(_b).timespinner("initValue", _d);
	}
	;
	function _e(e) {
		var _f = e.data.target;
		var _10 = $.data(_f, "timespinner").options;
		var _11 = _1(this);
		for ( var i = 0; i < _10.selections.length; i++) {
			var _12 = _10.selections[i];
			if (_11 >= _12[0] && _11 <= _12[1]) {
				_13(_f, i);
				return;
			}
		}
	}
	;
	function _13(_14, _15) {
		var _16 = $.data(_14, "timespinner").options;
		if (_15 != undefined) {
			_16.highlight = _15;
		}
		var _17 = _16.selections[_16.highlight];
		if (_17) {
			var tb = $(_14).timespinner("textbox");
			_5(tb[0], _17[0], _17[1]);
			tb.focus();
		}
	}
	;
	function _18(_19, _1a) {
		var _1b = $.data(_19, "timespinner").options;
		var _1a = _1b.parser.call(_19, _1a);
		var _1c = _1b.formatter.call(_19, _1a);
		$(_19).spinner("setValue", _1c);
	}
	;
	function _1d(_1e, _1f) {
		var _20 = $.data(_1e, "timespinner").options;
		var s = $(_1e).timespinner("getValue");
		var _21 = _20.selections[_20.highlight];
		var s1 = s.substring(0, _21[0]);
		var s2 = s.substring(_21[0], _21[1]);
		var s3 = s.substring(_21[1]);
		var v = s1 + ((parseInt(s2) || 0) + _20.increment * (_1f ? -1 : 1))
				+ s3;
		$(_1e).timespinner("setValue", v);
		_13(_1e);
	}
	;
	$.fn.timespinner = function(_22, _23) {
		if (typeof _22 == "string") {
			var _24 = $.fn.timespinner.methods[_22];
			if (_24) {
				return _24(this, _23);
			} else {
				return this.spinner(_22, _23);
			}
		}
		_22 = _22 || {};
		return this.each(function() {
			var _25 = $.data(this, "timespinner");
			if (_25) {
				$.extend(_25.options, _22);
			} else {
				$.data(this, "timespinner", {
					options : $.extend({}, $.fn.timespinner.defaults,
							$.fn.timespinner.parseOptions(this), _22)
				});
			}
			_a(this);
		});
	};
	$.fn.timespinner.methods = {
		options : function(jq) {
			var _26 = jq.data("spinner") ? jq.spinner("options") : {};
			return $.extend($.data(jq[0], "timespinner").options, {
				width : _26.width,
				value : _26.value,
				originalValue : _26.originalValue,
				disabled : _26.disabled,
				readonly : _26.readonly
			});
		},
		setValue : function(jq, _27) {
			return jq.each(function() {
				_18(this, _27);
			});
		},
		getHours : function(jq) {
			var _28 = $.data(jq[0], "timespinner").options;
			var vv = jq.timespinner("getValue").split(_28.separator);
			return parseInt(vv[0], 10);
		},
		getMinutes : function(jq) {
			var _29 = $.data(jq[0], "timespinner").options;
			var vv = jq.timespinner("getValue").split(_29.separator);
			return parseInt(vv[1], 10);
		},
		getSeconds : function(jq) {
			var _2a = $.data(jq[0], "timespinner").options;
			var vv = jq.timespinner("getValue").split(_2a.separator);
			return parseInt(vv[2], 10) || 0;
		}
	};
	$.fn.timespinner.parseOptions = function(_2b) {
		return $.extend({}, $.fn.spinner.parseOptions(_2b), $.parser
				.parseOptions(_2b, [ "separator", {
					showSeconds : "boolean",
					highlight : "number"
				} ]));
	};
	$.fn.timespinner.defaults = $.extend({}, $.fn.spinner.defaults, {
		inputEvents : $.extend({}, $.fn.spinner.defaults.inputEvents, {
			click : function(e) {
				_e.call(this, e);
			},
			blur : function(e) {
				var t = $(e.data.target);
				t.timespinner("setValue", t.timespinner("getText"));
			}
		}),
		formatter : function(_2c) {
			if (!_2c) {
				return "";
			}
			var _2d = $(this).timespinner("options");
			var tt = [ _2e(_2c.getHours()), _2e(_2c.getMinutes()) ];
			if (_2d.showSeconds) {
				tt.push(_2e(_2c.getSeconds()));
			}
			return tt.join(_2d.separator);
			function _2e(_2f) {
				return (_2f < 10 ? "0" : "") + _2f;
			}
			;
		},
		parser : function(s) {
			var _30 = $(this).timespinner("options");
			var _31 = _32(s);
			if (_31) {
				var min = _32(_30.min);
				var max = _32(_30.max);
				if (min && min > _31) {
					_31 = min;
				}
				if (max && max < _31) {
					_31 = max;
				}
			}
			return _31;
			function _32(s) {
				if (!s) {
					return null;
				}
				var tt = s.split(_30.separator);
				return new Date(1900, 0, 0, parseInt(tt[0], 10) || 0, parseInt(
						tt[1], 10) || 0, parseInt(tt[2], 10) || 0);
			}
			;
			if (!s) {
				return null;
			}
			var tt = s.split(_30.separator);
			return new Date(1900, 0, 0, parseInt(tt[0], 10) || 0, parseInt(
					tt[1], 10) || 0, parseInt(tt[2], 10) || 0);
		},
		selections : [ [ 0, 2 ], [ 3, 5 ], [ 6, 8 ] ],
		separator : ":",
		showSeconds : false,
		highlight : 0,
		spin : function(_33) {
			_1d(this, _33);
		}
	});
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function createCombogrid(target) {
		var combogrid = $.data(target, "combogrid");
		var opts = combogrid.options;
		var grid = combogrid.grid;
		$(target).addClass("combogrid-f").combo($.extend({},opts,{
			onShowPanel : function() {
				var p = $(this).combogrid("panel");
				var outerHeight = p.outerHeight()- p.height();
			var minHeight = p._size("minHeight");
			var maxHeight = p._size("maxHeight");
			$(this).combogrid("grid").datagrid("resize",{
						width : "100%",
						height : (isNaN(parseInt(opts.panelHeight)) ? "auto" : "100%"),
						minHeight : (minHeight ? minHeight - outerHeight : ""),
						maxHeight : (maxHeight ? maxHeight - outerHeight : "")
			});
			opts.onShowPanel.call(this);
		}}));
		var panel = $(target).combo("panel");
		if (!grid) {
			grid = $("<table></table>").appendTo(panel);
			combogrid.grid = grid;
		}
		var outerHeight = panel.outerHeight()- panel.height();
		var minHeight = panel._size("minHeight");
		var maxHeight = panel._size("maxHeight");
		grid.datagrid($.extend({}, opts, {
			width : "100%",
			height : (isNaN(parseInt(opts.panelHeight)) ? "auto" : "100%"),
			minHeight : (minHeight ? minHeight - outerHeight : ""),
			maxHeight : (maxHeight ? maxHeight - outerHeight : ""),
			//不清除默认选择上的内容
			checkOnSelect:true,
			selectOnCheck : true,
			showPageList:false,
			showPageStyle:"manual",
			border : false,
			singleSelect : (!opts.multiple),
			onLoadSuccess : function(_a) {
				var _b = $(target).combo("getValues");
				var _c = opts.onSelect;
				opts.onSelect = function() {
				};
				setValues(target, _b, combogrid.remainText);
				opts.onSelect = _c;
				opts.onLoadSuccess.apply(target, arguments);
			},
			onClickRow : clickRow,
			onSelect : function(_e, _f) {
				_select();
				opts.onSelect.call(this, _e, _f);
			},
			onUnselect : function(_11, row) {
				_select();
				opts.onUnselect.call(this, _11, row);
			},
			onSelectAll : function(rows) {
				_select();
				opts.onSelectAll.call(this, rows);
			},
			onUnselectAll : function(rows) {
				if (opts.multiple) {
					_select();
				}
				opts.onUnselectAll.call(this, rows);
			}
		}));
		function clickRow(_14, row) {
			combogrid.remainText = false;
			_select();
			if (!opts.multiple) {
				$(target).combo("hidePanel");
			}
			opts.onClickRow.call(this, _14, row);
		};
		function _select() {
			var _15 = grid.datagrid("getSelections");
			var vv = [], ss = [];
			opts.selectObject=[];
			for (var i = 0; i < _15.length; i++) {
				vv.push(_15[i][opts.idField]);
				ss.push(_15[i][opts.textField]);
				opts.selectObject.push(_15[i]);
			}
			if (!opts.multiple) {
				$(target).combo("setValues", (vv.length ? vv : [ "" ]));
			} else {
				$(target).combo("setValues", vv);
			}
			if (!combogrid.remainText) {
				$(target).combo("setText", ss.join(opts.separator));
			}
		};
	};
	function nav(target, dir) {
		var combogrid = $.data(target, "combogrid");
		var opts = combogrid.options;
		var grid = combogrid.grid;
		var len = grid.datagrid("getRows").length;
		if (!len) {
			return;
		}
		var tr = opts.finder.getTr(grid[0], null, "highlight");
		if (!tr.length) {
			tr = opts.finder.getTr(grid[0], null, "selected");
		}
		var _1b=null;
		if (!tr.length) {
			_1b = (dir == "next" ? 0 : len - 1);
		} else {
			var _1b = parseInt(tr.attr("datagrid-row-index"));
			_1b += (dir == "next" ? 1 : -1);
			if (_1b < 0) {
				_1b = len - 1;
			}
			if (_1b >= len) {
				_1b = 0;
			}
		}
		grid.datagrid("highlightRow", _1b);
		if (opts.selectOnNavigation) {
			combogrid.remainText = false;
			grid.datagrid("selectRow", _1b);
		}
	};
	function setValues(target, values, _1f) {
		var combogrid = $.data(target, "combogrid");
		var opts = combogrid.options;
		var grid = combogrid.grid;
		var rows = grid.datagrid("getRows");
		var checkeds = grid.datagrid("getChecked");
		var ss = [];
		var combOpts = $(target).combo("options");
		var onChange = combOpts.onChange;
		combOpts.onChange = function() {
		};
		if(!opts.rememberAll){
			grid.datagrid("clearSelections");
		}
		for (var i = 0; i < values.length; i++) {
			var rowId = grid.datagrid("getRowIndex", values[i]);
			if (rowId >= 0) {
				grid.datagrid("selectRow", rowId);
				ss.push(rows[rowId]);
			}
		}
		//查询是否在old选择
		for (var i = 0; i < values.length; i++) {
			var idF=values[i];
			for (var ci = 0; ci < checkeds.length; ci++) {
				if(checkeds[ci][opts.idField]==idF){
					var add=true;
					for(var chs=0;chs<ss.length;chs++){
						if(ss[chs][opts.idField]==idF){
							add=false;
							break;
						}
					}
					if(add){
						ss.push(checkeds[ci]);
					}
				}
			}
		}
		combOpts.onChange = onChange;
		//如果在list表中没有查询到
		if(ss && ss.length>0){
			opts.selectObject=ss;
			var oldId=[];
			var oldName=[];
			for (var n = 0; n < opts.selectObject.length; n++) {
				oldId.push(opts.selectObject[n][opts.idField]);
				oldName.push(opts.selectObject[n][opts.textField]);
			}
			$(target).combo("setValues", oldId.join(opts.separator));
			if (!_1f) {
				var s = oldName.join(opts.separator);
				if ($(target).combo("getText") != s) {
					$(target).combo("setText", s);
				}
			}
		}else{
			if($.isArray(opts.selectObject)){
				var oldId=[];
				var oldName=[];
				for (var n = 0; n < opts.selectObject.length; n++) {
					oldId.push(opts.selectObject[n][opts.idField]);
					oldName.push(opts.selectObject[n][opts.textField]);
				}
				$(target).combo("setValues", oldId.join(opts.separator));
				$(target).combo("setText", oldName.join(opts.separator));
			}else if(typeof opts.selectObject == "string"){
				var seleObj=$.getJSON(opts.selectObject);
				if(seleObj){
					opts.selectObject=seleObj;
					$(target).combo("setValues", opts.selectObject[opts.idField]);
					$(target).combo("setText", opts.selectObject[opts.textField]);
				}
			}else{
				if(opts.selectObject){
					$(target).combo("setValues", opts.selectObject[opts.idField]);
					$(target).combo("setText", opts.selectObject[opts.textField]);
				}
			}
		}
	};
	function query(target, q) {
		var combogrid = $.data(target, "combogrid");
		var opts = combogrid.options;
		var grid = combogrid.grid;
		combogrid.remainText = true;
		if (opts.multiple && !q) {
			setValues(target, [], true);
		} else {
			setValues(target, [ q ], true);
		}
		if (opts.mode == "remote") {
			grid.datagrid("clearSelections");
			grid.datagrid("load", $.extend({}, opts.queryParams, {
				q : q
			}));
		} else {
			if (!q) {
				return;
			}
			grid.datagrid("clearSelections").datagrid("highlightRow", -1);
			var _2d = grid.datagrid("getRows");
			var qq = opts.multiple ? q.split(opts.separator) : [ q ];
			$.map(qq, function(q) {
				q = $.trim(q);
				if (q) {
					$.map(_2d, function(row, i) {
						if (q == row[opts.textField]) {
							grid.datagrid("selectRow", i);
						} else {
							if (opts.filter.call(target, q, row)) {
								grid.datagrid("highlightRow", i);
							}
						}
					});
				}
			});
		}
	};
	function enter(target) {
		var combogrid = $.data(target, "combogrid");
		var opts = combogrid.options;
		var grid = combogrid.grid;
		var tr = opts.finder.getTr(grid[0], null, "highlight");
		combogrid.remainText = false;
		if (tr.length) {
			var row = parseInt(tr.attr("datagrid-row-index"));
			if (opts.multiple) {
				if (tr.hasClass("datagrid-row-selected")) {
					grid.datagrid("unselectRow", row);
				} else {
					grid.datagrid("selectRow", row);
				}
			} else {
				grid.datagrid("selectRow", row);
			}
		}
		var vv = [];
		$.map(grid.datagrid("getSelections"), function(row) {
			vv.push(row[opts.idField]);
		});
		$(target).combogrid("setValues", vv);
		if (!opts.multiple) {
			$(target).combogrid("hidePanel");
		}
	};
	$.fn.combogrid = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.combogrid.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var combogrid = $.data(this, "combogrid");
			if (combogrid) {
				$.extend(combogrid.options, options);
			} else {
				combogrid = $.data(this, "combogrid", {
					options : $.extend({}, $.fn.combogrid.defaults,$.fn.combogrid.parseOptions(this), options)
				});
			}
			createCombogrid(this);
		});
	};
	$.fn.combogrid.methods = {options : function(jq) {
			var opts = jq.combo("options");
			return $.extend($.data(jq[0], "combogrid").options, {
				width : opts.width,
				height : opts.height,
				originalValue : opts.originalValue,
				disabled : opts.disabled,
				readonly : opts.readonly
			});
		},
		grid : function(jq) {
			return $.data(jq[0], "combogrid").grid;
		},
		loadGrid : function(jq, param) {
			return jq.each(function() {
				$(this).combogrid("grid").datagrid("load",param);
			});
		},
		setValues : function(jq, _39) {
			return jq.each(function() {
				setValues(this, _39);
			});
		},
		setValue : function(jq, _3a) {
			return jq.each(function() {
				setValues(this, [ _3a ]);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				var opts = $(this).combogrid("options");
				$(this).combogrid("grid").datagrid("clearSelections");
				$(this).combo("clear");
				opts.selectObject=new Array();
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).combogrid("options");
				if (opts.multiple) {
					$(this).combogrid("setValues", opts.originalValue);
				} else {
					$(this).combogrid("setValue", opts.originalValue);
				}
			});
		}
	};
	$.fn.combogrid.parseOptions = function(target) {
		return $.extend({}, $.fn.combo.parseOptions(target), $.fn.datagrid.parseOptions(target), $.parser.parseOptions(target, [ "idField","textField", "mode" ]));
	};
	$.fn.combogrid.defaults = $.extend({}, $.fn.combo.defaults,$.fn.datagrid.defaults, {
		loadMsg : null,
		idField : null,
		textField : null,
		//是否记住全部选择内容
		rememberAll : false,
		mode : "local",
		keyHandler : {
			up : function(e) {
				nav(this, "prev");
				e.preventDefault();
			},
			down : function(e) {
				nav(this, "next");
				e.preventDefault();
			},
			left : function(e) {
			},
			right : function(e) {
			},
			enter : function(e) {
				enter(this);
			},
			query : function(q, e) {
				query(this, q);
			}
		},
		filter : function(q, row) {
			var opts = $(this).combogrid("options");
			return row[opts.textField].toLowerCase().indexOf(q.toLowerCase()) == 0;
		}
	});
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function createCombotree(target) {
		var combotree = $.data(target, "combotree");
		var opts = combotree.options;
		var tree = combotree.tree;
		$(target).addClass("combotree-f");
		$(target).combo(opts);
		var panel = $(target).combo("panel");

		/*if(opts.defaultEntry){
			var dd = [];
			dd.push('<div id="' + (combotree.itemIdPrefix+'_'+0) + '>');
			dd.push("--\u8BF7\u9009\u62E9--");
			dd.push('</div>');
			panel.html(dd.join(''));
		}*/
		
		
		if (!tree) {
			tree = $("<ul></ul>").appendTo(panel);
			$.data(target, "combotree").tree = tree;
		}
		tree.tree($.extend({}, opts, {
			checkbox : opts.multiple,
			onLoadSuccess : function(_7, _8) {
				var _9 = $(target).combotree("getValues");
				if (opts.multiple) {
					var _a = tree.tree("getChecked");
					for (var i = 0; i < _a.length; i++) {
						var id = _a[i].id;
						(function() {
							for (var i = 0; i < _9.length; i++) {
								if (id == _9[i]) {
									return;
								}
							}
							_9.push(id);
						})();
					}
				}
				var _b = $(this).tree("options");
				var _c = _b.onCheck;
				var _d = _b.onSelect;
				_b.onCheck = _b.onSelect = function() {
				};
				$(target).combotree("setValues", _9);
				_b.onCheck = _c;
				_b.onSelect = _d;
				opts.onLoadSuccess.call(this, _7, _8);
			},
			onClick : function(_e) {
				if (opts.multiple) {
					$(this).tree(_e.checked ? "uncheck" : "check", _e.target);
				} else {
					$(target).combo("hidePanel");
				}
				_11(target);
				opts.onClick.call(this, _e);
			},
			onCheck : function(_f, _10) {
				_11(target);
				opts.onCheck.call(this, _f, _10);
			}
		}));
	};
	function _11(_12) {
		var _13 = $.data(_12, "combotree");
		var _14 = _13.options;
		var _15 = _13.tree;
		var vv = [], ss = [];
		if (_14.multiple) {
			var _16 = _15.tree("getChecked");
			for (var i = 0; i < _16.length; i++) {
				vv.push(_16[i].id);
				ss.push(_16[i].name);
			}
		} else {
			var _17 = _15.tree("getSelected");
			if (_17) {
				vv.push(_17.id);
				ss.push(_17.name);
			}
		}
		$(_12).combo("setValues", vv).combo("setText", ss.join(_14.separator));
	}
	;
	function _18(_19, _1a) {
		var _1b = $.data(_19, "combotree").options;
		var _1c = $.data(_19, "combotree").tree;
		_1c.find("span.tree-checkbox").addClass("tree-checkbox0").removeClass(
				"tree-checkbox1 tree-checkbox2");
		var vv = [], ss = [];
		for (var i = 0; i < _1a.length; i++) {
			var v = _1a[i];
			var s = v;
			var _1d = _1c.tree("find", v);
			if (_1d) {
				s = _1d.name;
				_1c.tree("check", _1d.target);
				_1c.tree("select", _1d.target);
			}
			vv.push(v);
			ss.push(s);
		}
		$(_19).combo("setValues", vv).combo("setText", ss.join(_1b.separator));
	}
	;
	$.fn.combotree = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.combotree.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var combotree = $.data(this, "combotree");
			if (combotree) {
				$.extend(combotree.options, options);
			} else {
				$.data(this, "combotree", {
					options : $.extend({}, $.fn.combotree.defaults,
							$.fn.combotree.parseOptions(this), options)
				});
			}
			createCombotree(this);
		});
	};
	$.fn.combotree.methods = {
		options : function(jq) {
			var _22 = jq.combo("options");
			return $.extend($.data(jq[0], "combotree").options, {
				width : _22.width,
				height : _22.height,
				originalValue : _22.originalValue,
				disabled : _22.disabled,
				readonly : _22.readonly
			});
		},
		tree : function(jq) {
			return $.data(jq[0], "combotree").tree;
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				var opts = $.data(this, "combotree").options;
				opts.data = data;
				var tree = $.data(this, "combotree").tree;
				tree.tree("loadData", data);
			});
		},
		reload : function(jq, url) {
			return jq.each(function() {
				var _26 = $.data(this, "combotree").options;
				var _27 = $.data(this, "combotree").tree;
				if (url) {
					_26.url = url;
				}
				_27.tree({
					url : _26.url
				});
			});
		},
		setValues : function(jq, _28) {
			return jq.each(function() {
				_18(this, _28);
			});
		},
		setValue : function(jq, _29) {
			return jq.each(function() {
				_18(this, [ _29 ]);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				var _2a = $.data(this, "combotree").tree;
				_2a.find("div.tree-node-selected").removeClass(
						"tree-node-selected");
				var cc = _2a.tree("getChecked");
				for (var i = 0; i < cc.length; i++) {
					_2a.tree("uncheck", cc[i].target);
				}
				$(this).combo("clear");
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var _2b = $(this).combotree("options");
				if (_2b.multiple) {
					$(this).combotree("setValues", _2b.originalValue);
				} else {
					$(this).combotree("setValue", _2b.originalValue);
				}
			});
		}
	};
	$.fn.combotree.parseOptions = function(_2c) {
		return $.extend({}, $.fn.combo.parseOptions(_2c), $.fn.tree.parseOptions(_2c));
	};
	$.fn.combotree.defaults = $.extend({}, $.fn.combo.defaults,
			$.fn.tree.defaults, {
				editable : false
			});
})(jQuery);
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
 * datebox - jQuery EasyUI
 * 
 * Dependencies:
 * 	 calendar
 *   combo
 * 
 */
(function($){
	/**
	 * create date box
	 */
	function createBox(target){
		var state = $.data(target, 'datebox');
		var opts = state.options;
		
		$(target).addClass('datebox-f').combo($.extend({}, opts, {
			onShowPanel:function(){
				setCalendar();
				setValue(target, $(target).datebox('getText'), true);
//				setValue(target, $(target).datebox('getText'));
				opts.onShowPanel.call(target);
			}
		}));
		$(target).combo('textbox').parent().addClass('datebox');
		
		/**
		 * if the calendar isn't created, create it.
		 */
		if (!state.calendar){
			createCalendar();
		}
		setValue(target, opts.value);
		
		function createCalendar(){
			var panel = $(target).combo('panel').css('overflow','hidden');
			panel.panel('options').onBeforeDestroy = function(){
				var sc = $(this).find('.calendar-shared');
				if (sc.length){
					sc.insertBefore(sc[0].pholder);
				}
			};
			var cc = $('<div class="datebox-calendar-inner"></div>').appendTo(panel);
			if (opts.sharedCalendar){
				var sc = $(opts.sharedCalendar);
				if (!sc[0].pholder){
					sc[0].pholder = $('<div class="calendar-pholder" style="display:none"></div>').insertAfter(sc);
				}
				sc.addClass('calendar-shared').appendTo(cc);
				if (!sc.hasClass('calendar')){
					sc.calendar();
				}
				state.calendar = sc;
//				state.calendar = $(opts.sharedCalendar).appendTo(cc);
//				if (!state.calendar.hasClass('calendar')){
//					state.calendar.calendar();
//				}
			} else {
				state.calendar = $('<div></div>').appendTo(cc).calendar();
			}
			$.extend(state.calendar.calendar('options'), {
				fit:true,
				border:false,
				validator:opts.validator,
				minDate:opts.minDate,
				maxDate:opts.maxDate,
				onSelect:function(date){
					var opts = $(this.target).datebox('options');
					setValue(this.target, opts.formatter.call(this.target, date));
					$(this.target).combo('hidePanel');
					opts.onSelect.call(target, date);
				}
			});
//			setValue(target, opts.value);
			
			var button = $('<div class="datebox-button"><table cellspacing="0" cellpadding="0" style="width:100%"><tr></tr></table></div>').appendTo(panel);
			var tr = button.find('tr');
			for(var i=0; i<opts.buttons.length; i++){
				var td = $('<td></td>').appendTo(tr);
				var btn = opts.buttons[i];
				var t = $('<a href="javascript:void(0)"></a>').html($.isFunction(btn.text) ? btn.text(target) : btn.text).appendTo(td);
				t.bind('click', {target: target, handler: btn.handler}, function(e){
					e.data.handler.call(this, e.data.target);
				});
			}
			tr.find('td').css('width', (100/opts.buttons.length)+'%');
		}
		
		function setCalendar(){
			state.calendar.calendar('show');
			var panel = $(target).combo('panel');
			var cc = panel.children('div.datebox-calendar-inner');
			panel.children()._outerWidth(panel.width());
			state.calendar.appendTo(cc);
			state.calendar[0].target = target;
			if (opts.panelHeight != 'auto'){
				var height = panel.height();
				panel.children().not(cc).each(function(){
					height -= $(this).outerHeight();
				});
				cc._outerHeight(height);
			}
			state.calendar.calendar('resize');
		}
	}
	
	/**
	 * called when user inputs some value in text box
	 */
	function doQuery(target, q){
		setValue(target, q, true);
	}
	
	/**
	 * called when user press enter key
	 */
	function doEnter(target){
		var state = $.data(target, 'datebox');
		var opts = state.options;
		var current = state.calendar.calendar('options').current;
		if (current){
			setValue(target, opts.formatter.call(target, current));
			$(target).combo('hidePanel');
		}
	}
	
	function setValue(target, value, remainText){
		var state = $.data(target, 'datebox');
		var opts = state.options;
		var calendar = state.calendar;
		$(target).combo('setValue', value);
		calendar.calendar('moveTo', opts.parser.call(target, value));
		if (!remainText){
			if (value){
			//	value = opts.formatter.call(target, calendar.calendar('options').current);
				$(target).combo('setValue', value).combo('setText', value);
			} else {
				$(target).combo('setText', value);
			}
		}
	}
	
	$.fn.datebox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.datebox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'datebox');
			if (state){
				$.extend(state.options, options);
			} else {
				$.data(this, 'datebox', {
					options: $.extend({}, $.fn.datebox.defaults, $.fn.datebox.parseOptions(this), options)
				});
			}
			createBox(this);
		});
	};
	
	$.fn.datebox.methods = {
		options: function(jq){
			var copts = jq.combo('options');
			return $.extend($.data(jq[0], 'datebox').options, {
				width: copts.width,
				height: copts.height,
				originalValue: copts.originalValue,
				disabled: copts.disabled,
				readonly: copts.readonly
			});
		},
		calendar: function(jq){	// get the calendar object
			return $.data(jq[0], 'datebox').calendar;
		},
		setValue: function(jq, value){
			return jq.each(function(){
				setValue(this, value);
			});
		},
		reset: function(jq){
			return jq.each(function(){
				var opts = $(this).datebox('options');
				$(this).datebox('setValue', opts.originalValue);
			});
		}
	};
	
	$.fn.datebox.parseOptions = function(target){
		return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target, ['sharedCalendar']));
	};
	
	$.fn.datebox.defaults = $.extend({}, $.fn.combo.defaults, {
		panelWidth:180,
		panelHeight:'auto',
		sharedCalendar:null,
		minDate:null,
		maxDate:null,
		keyHandler: {
			up:function(e){},
			down:function(e){},
			left: function(e){},
			right: function(e){},
			enter:function(e){doEnter(this);},
			query:function(q,e){doQuery(this, q);}
		},
		
		currentText:'今天',
		closeText:'关闭',
		clearText:'清除',
		okText:'确定',
		
		buttons:[{
			text: function(target){return $(target).datebox('options').currentText;},
			handler: function(target){
				$(target).datebox('calendar').calendar({
					year:new Date().getFullYear(),
					month:new Date().getMonth()+1,
					current:new Date()
				});
				doEnter(target);
			}
		},{
			text: function(target){return $(target).datebox('options').clearText;},
			handler: function(target){
				
				var oldValue=$(target).datebox('getValue');
				setValue(target, "");
				$(this).closest('div.combo-panel').panel('close');
				$(target).datebox('options').onAfterClear(oldValue);
			}
		},{
			text: function(target){return $(target).datebox('options').closeText;},
			handler: function(target){
				$(this).closest('div.combo-panel').panel('close');
			}
		}],
		
		formatter:function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
//			return (m<10?('0'+m):m)+'/'+(d<10?('0'+d):d)+'/'+y;
//			return m+'/'+d+'/'+y;
		},
		parser:function(s){
			if (!s) return new Date();
			var ss = s.split('-');
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		},
		
		onSelect:function(date){},
		onAfterClear:function(date){}
	});
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function createDatetimebox(target) {
		var datetimebox = $.data(target, "datetimebox");
		var opts = datetimebox.options;
		$(target).datebox($.extend({}, opts, {
			onShowPanel : function() {
				var value = $(target).datetimebox("getValue");
				setValue(target, value, true);
				opts.onShowPanel.call(target);
			},
			formatter : $.fn.datebox.defaults.formatter,
			parser : $.fn.datebox.defaults.parser
		}));
		$(target).removeClass("datebox-f").addClass("datetimebox-f");
		$(target).datebox("calendar").calendar({
			onSelect : function(date) {
				var opts = $(this.target).datetimebox('options');
				var hours=$(this.target).datetimebox("spinner").timespinner("getHours");
				var minutes=$(this.target).datetimebox("spinner").timespinner("getMinutes");
				if(!isNaN(hours)){
					date.setHours(hours);
				}
				if(!isNaN(minutes)){
					date.setMinutes(minutes);
				}
				setValue(this.target, opts.formatter.call(this.target, date));
				$(this.target).combo('hidePanel');
				opts.onSelect.call(target, date);
			}
		});
		var panel = $(target).datebox("panel");
		if (!datetimebox.spinner) {
			var p = $("<div style=\"padding:2px\"><input style=\"width:80px\"></div>").insertAfter(panel.children("div.datebox-calendar-inner"));
			datetimebox.spinner = p.children("input");
		}
		datetimebox.spinner.timespinner({
			width : opts.spinnerWidth,
			showSeconds : opts.showSeconds,
			separator : opts.timeSeparator
		}).unbind(".datetimebox").bind("mousedown.datetimebox", function(e) {
			e.stopPropagation();
		});
		setValue(target, opts.value);
	};
	function setCalendar(target) {
		var c = $(target).datetimebox("calendar");
		var t = $(target).datetimebox("spinner");
		var _b = c.calendar("options").current;
		return new Date(_b.getFullYear(), _b.getMonth(), _b.getDate(), t.timespinner("getHours"), t.timespinner("getMinutes"), t.timespinner("getSeconds"));
	};
	function doQuery(_d, q) {
		setValue(_d, q, true);
	};
	function doEnter(target) {
		var opts = $.data(target, "datetimebox").options;
		var _11 = setCalendar(target);
		setValue(target, opts.formatter.call(target, _11));
		$(target).combo("hidePanel");
	};
	function setValue(target, value, remainText) {
		var opts = $.data(target, "datetimebox").options;
		$(target).combo("setValue", value);
		if (!remainText) {
			if (value) {
				var _16 = opts.parser.call(target, value);
				$(target).combo("setValue", opts.formatter.call(target, _16));
				$(target).combo("setText", opts.formatter.call(target, _16));
			} else {
				$(target).combo("setText", value);
			}
		}
		var _16 = opts.parser.call(target, value);
		$(target).datetimebox("calendar").calendar("moveTo", _16);
		$(target).datetimebox("spinner").timespinner("setValue", _17(_16));
		function _17(_18) {
			function _19(_1a) {
				return (_1a < 10 ? "0" : "") + _1a;
			}
			;
			var tt = [ _19(_18.getHours()), _19(_18.getMinutes()) ];
			if (opts.showSeconds) {
				tt.push(_19(_18.getSeconds()));
			}
			return tt.join($(target).datetimebox("spinner").timespinner("options").separator);
		};
	};
	$.fn.datetimebox = function(options, param) {
		if (typeof options == "string") {
			var methods = $.fn.datetimebox.methods[options];
			if (methods) {
				return methods(this, param);
			} else {
				return this.datebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var datetimebox = $.data(this, "datetimebox");
			if (datetimebox) {
				$.extend(datetimebox.options, options);
			} else {
				$.data(this, "datetimebox", {
					options : $.extend({}, $.fn.datetimebox.defaults,$.fn.datetimebox.parseOptions(this), options)
				});
			}
			createDatetimebox(this);
		});
	};
	$.fn.datetimebox.methods = {
		options : function(jq) {
			var opts = jq.datebox("options");
			return $.extend($.data(jq[0], "datetimebox").options, {
				originalValue : opts.originalValue,
				disabled : opts.disabled,
				readonly : opts.readonly
			});
		},
		spinner : function(jq) {
			return $.data(jq[0], "datetimebox").spinner;
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				setValue(this, value);
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).datetimebox("options");
				$(this).datetimebox("setValue", opts.originalValue);
			});
		}
	};
	$.fn.datetimebox.parseOptions = function(target) {
		return $.extend({}, $.fn.datebox.parseOptions(target), $.parser.parseOptions(target, [ "timeSeparator", "spinnerWidth", {
			showSeconds : "boolean"
		} ]));
	};
	$.fn.datetimebox.defaults = $.extend({}, $.fn.datebox.defaults,{
		spinnerWidth : "100%",
		showSeconds : true,
		timeSeparator : ":",
		keyHandler : {
			up : function(e) {
			},
			down : function(e) {
			},
			left : function(e) {
			},
			right : function(e) {
			},
			enter : function(e) {
				doEnter(this);
			},
			query : function(q, e) {
				doQuery(this, q);
			}
		},
		buttons : [ {
			text : function(_23) {
				return $(_23).datetimebox("options").currentText;
			},
			handler : function(_24) {
				$(_24).datetimebox("calendar").calendar({
					year : new Date().getFullYear(),
					month : new Date().getMonth() + 1,
					current : new Date()
				});
				doEnter(_24);
			}
		}, {
			text : function(_25) {
				return $(_25).datetimebox("options").okText;
			},
			handler : function(_26) {
				doEnter(_26);
			}
		}, {
			text : function(_27) {
				return $(_27).datetimebox("options").closeText;
			},
			handler : function(_28) {
				$(this).closest("div.combo-panel").panel("close");
			}
		} ],
		formatter : function(_29) {
			var h = _29.getHours();
			var M = _29.getMinutes();
			var s = _29.getSeconds();
			function _2a(_2b) {
				return (_2b < 10 ? "0" : "") + _2b;
			};
			var _2c = $(this).datetimebox("spinner").timespinner("options").separator;
			var r = $.fn.datebox.defaults.formatter(_29) + " " + _2a(h) + _2c + _2a(M);
			if ($(this).datetimebox("options").showSeconds) {
				r += _2c + _2a(s);
			}
			return r;
		},
		parser : function(s) {
			if ($.trim(s) == "") {
				return new Date();
			}
			var dt = s.split(" ");
			var d = $.fn.datebox.defaults.parser(dt[0]);
			if (dt.length < 2) {
				return d;
			}
			var _2d = $(this).datetimebox("spinner").timespinner("options").separator;
			var tt = dt[1].split(_2d);
			var _2e = parseInt(tt[0], 10) || 0;
			var _2f = parseInt(tt[1], 10) || 0;
			var _30 = parseInt(tt[2], 10) || 0;
			return new Date(d.getFullYear(), d.getMonth(), d.getDate(),_2e, _2f, _30);
		}
	});
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt To use it
 * on other terms please contact us at info@jeasyui.com
 * 
 */
(function($) {
	function _1(_2) {
		var _3 = $.data(_2, "numberbox");
		var _4 = _3.options;
		$(_2).addClass("numberbox-f").textbox(_4);
		$(_2).textbox("textbox").css({
			imeMode : "disabled"
		});
		$(_2).attr("numberboxName", $(_2).attr("textboxName"));
		_3.numberbox = $(_2).next();
		_3.numberbox.addClass("numberbox");
		var _5 = _4.parser.call(_2, _4.value);
		var _6 = _4.formatter.call(_2, _5);
		$(_2).numberbox("initValue", _5).numberbox("setText", _6);
	};
	function _7(_8, _9) {
		var _a = $.data(_8, "numberbox");
		var _b = _a.options;
		var _9 = _b.parser.call(_8, _9);
		var _c = _b.formatter.call(_8, _9);
		_b.value = _9;
		$(_8).textbox("setValue", _9).textbox("setText", _c);
	};
	$.fn.numberbox = function(options, param) {
		if (typeof options == "string") {
			var fn = $.fn.numberbox.methods[options];
			if (fn) {
				return fn(this, param);
			} else {
				return this.textbox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var numberbox = $.data(this, "numberbox");
			if (numberbox) {
				$.extend(numberbox.options, options);
			} else {
				numberbox = $.data(this, "numberbox", {
					options : $.extend({}, $.fn.numberbox.defaults,
							$.fn.numberbox.parseOptions(this), options)
				});
			}
			_1(this);
		});
	};
	$.fn.numberbox.methods = {
		options : function(jq) {
			var _11 = jq.data("textbox") ? jq.textbox("options") : {};
			return $.extend($.data(jq[0], "numberbox").options, {
				width : _11.width,
				originalValue : _11.originalValue,
				disabled : _11.disabled,
				readonly : _11.readonly
			});
		},
		fix : function(jq) {
			return jq.each(function() {
				$(this).numberbox("setValue", $(this).numberbox("getText"));
			});
		},
		setValue : function(jq, _12) {
			return jq.each(function() {
				_7(this, _12);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				$(this).textbox("clear");
				$(this).numberbox("options").value = "";
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				$(this).textbox("reset");
				$(this).numberbox("setValue", $(this).numberbox("getValue"));
			});
		}
	};
	$.fn.numberbox.parseOptions = function(_13) {
		var t = $(_13);
		return $.extend({}, $.fn.textbox.parseOptions(_13), $.parser.parseOptions(_13, 
				[ "decimalSeparator", "groupSeparator","suffix",
				{min : "number",
					max : "number",
					precision : "number"} ]),
				{prefix : (t.attr("prefix") ? t.attr("prefix") : undefined)});
	};
	$.fn.numberbox.defaults = $.extend({},$.fn.textbox.defaults,{
		inputEvents : {
			keypress : function(e) {
				var _14 = e.data.target;
				var _15 = $(_14).numberbox("options");
				return _15.filter.call(_14, e);
			},
			blur : function(e) {
				var _16 = e.data.target;
				$(_16).numberbox("setValue",$(_16).numberbox("getText"));
			}
		},
		min : null,
		max : null,
		precision : 0,
		decimalSeparator : ".",
		groupSeparator : "",
		prefix : "",
		suffix : "",
		filter : function(e) {
			var _17 = $(this).numberbox("options");
			if (e.which == 45) {
				return ($(this).val().indexOf("-") == -1 ? true : false);
			}
			var c = String.fromCharCode(e.which);
			if (c == _17.decimalSeparator) {
				return ($(this).val().indexOf(c) == -1 ? true : false);
			} else {
				if (c == _17.groupSeparator) {
					return true;
				} else {
					if ((e.which >= 48 && e.which <= 57 && e.ctrlKey == false && e.shiftKey == false) || e.which == 0 || e.which == 8) {
						return true;
					} else {
						if (e.ctrlKey == true && (e.which == 99 || e.which == 118)) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		},
		formatter : function(_18) {
			if (!_18) {
				return _18;
			}
			_18 = _18 + "";
			var _19 = $(this).numberbox("options");
			var s1 = _18, s2 = "";
			var _1a = _18.indexOf(".");
			if (_1a >= 0) {
				s1 = _18.substring(0, _1a);
				s2 = _18.substring(_1a + 1, _18.length);
			}
			if (_19.groupSeparator) {
				var p = /(\d+)(\d{3})/;
				while (p.test(s1)) {
					s1 = s1.replace(p, "$1" + _19.groupSeparator + "$2");
				}
			}
			if (s2) {
				return _19.prefix + s1 + _19.decimalSeparator + s2 + _19.suffix;
			} else {
				return _19.prefix + s1 + _19.suffix;
			}
		},
		parser : function(s) {
			s = s + "";
			var _1b = $(this).numberbox("options");
			if (parseFloat(s) != s) {
				if (_1b.prefix) {
					s = $.trim(s.replace(new RegExp("\\" + $.trim(_1b.prefix), "g"), ""));
				}
				if (_1b.suffix) {
					s = $.trim(s.replace(new RegExp("\\" + $.trim(_1b.suffix), "g"), ""));
				}
				if (_1b.groupSeparator) {
					s = $.trim(s.replace(new RegExp("\\" + _1b.groupSeparator, "g"), ""));
				}
				if (_1b.decimalSeparator) {
					s = $.trim(s.replace(new RegExp("\\" + _1b.decimalSeparator, "g"), "."));
				}
				s = s.replace(/\s/g, "");
			}
			var val = parseFloat(s).toFixed(_1b.precision);
			if (isNaN(val)) {
				val = "";
			} else {
				if (typeof (_1b.min) == "number"
						&& val < _1b.min) {
					val = _1b.min.toFixed(_1b.precision);
				} else {
					if (typeof (_1b.max) == "number"
							&& val > _1b.max) {
						val = _1b.max.toFixed(_1b.precision);
					}
				}
			}
			return val;
		}
	});
})(jQuery);
/**
 * jQuery EasyUI 1.4
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
(function($){
function _1(_2){
$(_2).addClass("numberspinner-f");
var _3=$.data(_2,"numberspinner").options;
$(_2).numberbox(_3).spinner(_3);
$(_2).numberbox("setValue",_3.value);
};
function _4(_5,_6){
var _7=$.data(_5,"numberspinner").options;
var v=parseFloat($(_5).numberbox("getValue")||_7.value)||0;
if(_6){
v-=_7.increment;
}else{
v+=_7.increment;
}
$(_5).numberbox("setValue",v);
};
$.fn.numberspinner=function(_8,_9){
if(typeof _8=="string"){
var _a=$.fn.numberspinner.methods[_8];
if(_a){
return _a(this,_9);
}else{
return this.numberbox(_8,_9);
}
}
_8=_8||{};
return this.each(function(){
var _b=$.data(this,"numberspinner");
if(_b){
$.extend(_b.options,_8);
}else{
$.data(this,"numberspinner",{options:$.extend({},$.fn.numberspinner.defaults,$.fn.numberspinner.parseOptions(this),_8)});
}
_1(this);
});
};
$.fn.numberspinner.methods={options:function(jq){
var _c=jq.numberbox("options");
return $.extend($.data(jq[0],"numberspinner").options,{width:_c.width,value:_c.value,originalValue:_c.originalValue,disabled:_c.disabled,readonly:_c.readonly});
}};
$.fn.numberspinner.parseOptions=function(_d){
return $.extend({},$.fn.spinner.parseOptions(_d),$.fn.numberbox.parseOptions(_d),{});
};
$.fn.numberspinner.defaults=$.extend({},$.fn.spinner.defaults,$.fn.numberbox.defaults,{spin:function(_e){
_4(this,_e);
}});
})(jQuery);

