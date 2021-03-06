/* 上传图片 */
var lang;
function UploadImage(target,serverUrl) {
	this.serverUrl=serverUrl;
	this.init();
}
/* 获取对齐方式 */
function getAlign(){
    var align = $("#alignIcon .curr").attr("value") || "none";
    return align == "none" ? "" :align;
}

UploadImage.prototype = {
	init : function() {
		lang=NE.I18N['zh-cn'].insertimage;
		this.imageList = [];
		this.initContainer();
		this.initUploader();
	},
	initContainer : function() {
		// 图片容器
		this.queueList = jQuery.find('.filelist');
	},
	/* 初始化容器 */
	initUploader : function() {
		var _this = this; // just in case. Make sure it's not an
										// other libaray.
		// 图片容器
		var queueList = jQuery('#filelist');
		// 状态栏，包括进度和控制按钮
		var statusBar = jQuery('#statusBar');
		// 文件总体选择信息。
		var $info = jQuery('#info');
		// 上传按钮
		var $upload = jQuery("#uploadBtn");
		// 上传按钮
		var $filePickerBlock = jQuery('#filePickerBlock');
		// 总体进度条
		var $progress = jQuery('#progress').hide();
		// 添加的文件数量
		var fileCount = 0;
		// 添加的文件总大小
		var fileSize = 0;
		// 优化retina, 在retina下这个值是2
		var ratio = window.devicePixelRatio || 1;
		// 缩略图大小
		var thumbnailWidth = 113 * ratio, thumbnailHeight = 113 * ratio;
		// 可能有pedding, ready, uploading, confirm, done.
		var state = '';
		// 所有文件的进度信息，key为file id
		var percentages = {}, supportTransition = (function() {
			var s = document.createElement('p').style, r = 'transition' in s || 'WebkitTransition' in s || 'MozTransition' in s || 'msTransition' in s || 'OTransition' in s;
			s = null;
			return r;
		})();
		// WebUploader实例
		var uploader = null;
		var actionUrl = this.serverUrl+"/attachment/ajax/upload.action" || editor.getOpt('serverUrl') ;
		var acceptExtensions = (editor.getOpt('imageAllowFiles') || []).join('').replace(/\./g, ',').replace(/^[,]/, '');
		var imageMaxSize = editor.getOpt('imageMaxSize');
		var imageCompressBorder = editor.getOpt('imageCompressBorder');

		if (!WebUploader.Uploader.support()) {
			$('#message').after($('<div>').html(lang.errorNotSupport)).hide();
			return;
		}

		uploader = _this.uploader = WebUploader.create({
			pick : {
				id : '#filePickerBtn',
				label : "继续添加",
				width:20,
				height:20,
					
			},
			accept : {
				title : 'Images',
				extensions : acceptExtensions,
				mimeTypes : 'image/*'
			},
			swf : '../../third-party/webuploader/Uploader.swf',
			server : actionUrl,
			fileVal : editor.getOpt('imageFieldName'),
			duplicate : true,
			fileSingleSizeLimit : imageMaxSize, // 默认 2 M
			compress : editor.getOpt('imageCompressEnable') ? {
				width : imageCompressBorder,
				height : imageCompressBorder,
				// 图片质量，只有type为`image/jpeg`的时候才有效。
				quality : 90,
				// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
				allowMagnify : false,
				// 是否允许裁剪。
				crop : false,
				// 是否保留头部meta信息。
				preserveHeaders : true
			} : false
		});
		uploader.addButton({
			id : '#filePickerBlock'
		});

		setState('pedding');

		// 当有文件添加进来时执行，负责view的创建
		function addFile(file) {
			var $li = $('<li id="' + file.id + '"><p class="title">'+ file.name + '</p><p class="imgWrap"></p><p class="progress"><span></span></p></li>');
			var $btns = $('<div class="file-panel">' +
                    '<span class="cancel">' + lang.uploadDelete + '</span>' +
                    '<span class="rotateRight">' + lang.uploadTurnRight + '</span>' +
                    '<span class="rotateLeft">' + lang.uploadTurnLeft + '</span></div>').appendTo($li);
			
			var $prgress = $li.find('p.progress span');
			var $imgWrap = $li.find('p.imgWrap');
			var $info = $('<p class="error"></p>').hide().appendTo($li);

			var showError = function(code) {
				switch (code) {
				case 'exceed_size':
					text = lang.errorExceedSize;
					break;
				case 'interrupt':
					text = lang.errorInterrupt;
					break;
				case 'http':
					text = lang.errorHttp;
					break;
				case 'not_allow_type':
					text = this.lang.errorFileType;
					break;
				default:
					 text = lang.errorUploadRetry;
					break;
				}
				$info.text(text).show();
			};

			if (file.getStatus() === 'invalid') {
				showError(file.statusText);
			} else {
				$imgWrap.text(lang.uploadPreview);
				if (browser.ie && browser.version <= 7) {
					$imgWrap.text(lang.uploadNoPreview);
				} else {
					uploader.makeThumb(file, function(error, src) {
						if (error || !src) {
							$imgWrap.text(lang.uploadNoPreview);
						} else {
							var $img = $('<img src="' + src + '">');
							$imgWrap.empty().append($img);
							$img.on('error', function() {
								$imgWrap.text(lang.uploadNoPreview);
							});
						}
					}, thumbnailWidth, thumbnailHeight);
				}
				percentages[file.id] = [ file.size, 0 ];
				file.rotation = 0;

				/* 检查文件格式 */
				if (!file.ext || acceptExtensions.indexOf(file.ext.toLowerCase()) == -1) {
					showError('not_allow_type');
					file.error=true;
					uploader.removeFile(file);
				}
			}

			file.on('statuschange', function(cur, prev) {
				if (prev === 'progress') {
					$prgress.hide().width(0);
				} else if (prev === 'queued') {
					$li.off('mouseenter mouseleave');
					$btns.remove();
				}
				// 成功
				if (cur === 'error' || cur === 'invalid') {
					showError(file.statusText);
					percentages[file.id][1] = 1;
				} else if (cur === 'interrupt') {
					showError('interrupt');
				} else if (cur === 'queued') {
					percentages[file.id][1] = 0;
				} else if (cur === 'progress') {
					$info.hide();
					$prgress.css('display', 'block');
				} else if (cur === 'complete') {
				}

				$li.removeClass('state-' + prev).addClass('state-' + cur);
			});

			$li.on('mouseenter', function() {
				$btns.stop().animate({
					height : 30
				});
			});
			$li.on('mouseleave', function() {
				$btns.stop().animate({
					height : 0
				});
			});

			$btns.on('click', 'span', function() {
				var index = $(this).index(), deg;

				switch (index) {
				case 0:
					uploader.removeFile(file);
					return;
				case 1:
					file.rotation += 90;
					break;
				case 2:
					file.rotation -= 90;
					break;
				}

				if (supportTransition) {
					deg = 'rotate(' + file.rotation + 'deg)';
					jQuery.css({
						'-webkit-transform' : deg,
						'-mos-transform' : deg,
						'-o-transform' : deg,
						'transform' : deg
					});
				} else {
					jQuery.css('filter','progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation / 90) % 4 + 4) % 4)+ ')');
				}

			});

			$li.insertBefore($filePickerBlock);
		}

		// 负责view的销毁
		function removeFile(file) {
			var $li = $('#' + file.id);
			delete percentages[file.id];
			updateTotalProgress();
			$li.off().find('.file-panel').off().end().remove();
		}

		function updateTotalProgress() {
			var loaded = 0, total = 0, spans = $progress.children(), percent;

			$.each(percentages, function(k, v) {
				total += v[0];
				loaded += v[0] * v[1];
			});

			percent = total ? loaded / total : 0;

			spans.eq(0).text(Math.round(percent * 100) + '%');
			spans.eq(1).css('width', Math.round(percent * 100) + '%');
			updateStatus();
		}

		function setState(val, files) {

			if (val != state) {

				var stats = uploader.getStats();

				$upload.removeClass('state-' + state);
				$upload.addClass('state-' + val);

				switch (val) {

				/* 未选择文件 */
				case 'pedding':
					queueList.addClass('element-invisible');
					statusBar.addClass('element-invisible');
//					$placeHolder.removeClass('element-invisible');
					$progress.hide();
					$info.hide();
					uploader.refresh();
					break;

				/* 可以开始上传 */
				case 'ready':
//					$placeHolder.addClass('element-invisible');
					queueList.removeClass('element-invisible');
					statusBar.removeClass('element-invisible');
					$progress.hide();
					$info.show();
					$upload.text(lang.uploadStart);
					uploader.refresh();
					break;

				/* 上传中 */
				case 'uploading':
					$progress.show();
					$info.hide();
					$upload.text(lang.uploadPause);
					break;

				/* 暂停上传 */
				case 'paused':
					$progress.show();
					$info.hide();
					$upload.text(lang.uploadContinue);
					break;

				case 'confirm':
					$progress.show();
					$info.hide();
					$upload.text(lang.uploadStart);

					stats = uploader.getStats();
					if (stats.successNum && !stats.uploadFailNum) {
						setState('finish');
						return;
					}
					break;

				case 'finish':
					$progress.hide();
					$info.show();
					if (stats.uploadFailNum) {
						$upload.text(lang.uploadRetry);
					} else {
						$upload.text(lang.uploadStart);
					}
					break;
				}

				state = val;
				updateStatus();

			}

			if (!_this.getQueueCount()) {
				$upload.addClass('disabled');
			} else {
				$upload.removeClass('disabled');
			}

		}

		function updateStatus() {
			var text = '', stats;

			if (state === 'ready') {
				text = lang.updateStatusReady.replace('_', fileCount).replace('_KB', WebUploader.formatSize(fileSize));
			} else if (state === 'confirm') {
				stats = uploader.getStats();
				if (stats.uploadFailNum) {
					 text = lang.updateStatusConfirm.replace('_', stats.successNum).replace('_', stats.successNum);
				}
			} else {
				stats = uploader.getStats();
				 text = lang.updateStatusFinish.replace('_', fileCount).replace('_KB', WebUploader.formatSize(fileSize)).replace('_', stats.successNum);

				if (stats.uploadFailNum) {
					text += lang.updateStatusError.replace('_', stats.uploadFailNum);
				}
			}

			$info.html(text);
		}

		uploader.on('fileQueued', function(file) {
			fileCount++;
			fileSize += file.size;

			if (fileCount === 1) {
//				$placeHolder.addClass('element-invisible');
				statusBar.show();
			}
			addFile(file);
		});

		uploader.on('fileDequeued', function(file) {
			if(!file.error){
				fileCount--;
				fileSize -= file.size;
			}
			removeFile(file);
			updateTotalProgress();
		});

		uploader.on('filesQueued', function(file) {
			if (!uploader.isInProgress() && (state == 'pedding' || state == 'finish' || state == 'confirm' || state == 'ready')) {
				setState('ready');
			}
			updateTotalProgress();
		});

		uploader.on('all', function(type, files) {
			switch (type) {
			case 'uploadFinished':
				setState('confirm', files);
				break;
			case 'startUpload':
				setState('uploading', files);
				break;
			case 'stopUpload':
				setState('paused', files);
				break;
			}
		});

		uploader.on('uploadBeforeSend', function(file, data, header) {
			// 这里可以通过data对象添加POST参数
			// if (actionUrl.toLowerCase().indexOf('action') != -1) {
			// header['X_Requested_With'] = 'XMLHttpRequest';
			// }
		});

		uploader.on('uploadProgress', function(file, percentage) {
			var $li = $('#' + file.id);
			var $percent = $li.find('.progress span');

			$percent.css('width', percentage * 100 + '%');
			percentages[file.id][1] = percentage;
			updateTotalProgress();
		});

		uploader.on('uploadSuccess', function(file, ret) {
			var $file = $('#' + file.id);
			try {
				var responseText = (ret._raw || ret), json = utils.str2json(responseText);
				if (json.status == 1) {
					_this.imageList.push(json);
					$file.append('<span class="success"></span>');
				} else {
					$file.find('.error').text(json.message).show();
				}
			} catch (e) {
				$file.find('.error').text(lang.errorServerUpload).show();
			}
		});

		uploader.on('uploadError', function(file, code) {
		});
		uploader.on('error', function(code, file) {
			if (code == 'Q_TYPE_DENIED' || code == 'F_EXCEED_SIZE') {
				addFile(file);
			}
		});
		uploader.on('uploadComplete', function(file, ret) {
		});

		$upload.on('click', function() {
			if ($(this).hasClass('disabled')) {
				return false;
			}

			if (state === 'ready') {
				uploader.upload();
			} else if (state === 'paused') {
				uploader.upload();
			} else if (state === 'uploading') {
				uploader.stop();
			}
		});

		$upload.addClass('state-' + state);
		updateTotalProgress();
	},
	getQueueCount : function() {
		var file, i, status, readyFile = 0, files = this.uploader.getFiles();
		for (i = 0; file = files[i++];) {
			status = file.getStatus();
			if (status == 'queued' || status == 'uploading'
					|| status == 'progress')
				readyFile++;
		}
		return readyFile;
	},
	destroy : function() {
		this.jQuery.remove();
	},
	getInsertList : function() {
		var i, data, list = [], align = getAlign();
		for (i = 0; i < this.imageList.length; i++) {
			data = this.imageList[i];
			list.push({
				src : this.serverUrl+"/attachment/download.action?uuid="+data.id,
				_src :this.serverUrl+"/attachment/download.action?uuid="+data.id,
				alt : data.fileName,
				 width:300,
                 height:300,
				floatStyle : align
			});
		}
		return list;
	}
};
