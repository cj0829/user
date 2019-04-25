<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传图片</title>
    <script type="text/javascript" src="${cxt}/js/ueditor/dialogs/internal.js"></script>
    <!-- jquery -->
    <script type="text/javascript" src="${jsPath}/js/jquery.min.js"></script>
    <%@ include file="/include/webuploader.jsp"%>
    <!-- attachment dialog -->
    <link rel="stylesheet" href="${cxt}/js/ueditor/dialogs/photoimg/photoimg.css" type="text/css" />
</head>
<body>
	<div class="wrapper">
		<div id="tabhead" class="tabhead">
            <span class="tab" data-content-id="remote">插入图片</span>
            <span class="tab focus" data-content-id="upload">本地上传</span>
            <span class="tab" data-content-id="online">在线管理</span>
        </div>
        <div id="tabbody" class="tabbody">
            <div id="upload" class="panel focus">
            	<div id="queueList" class="queueList">
                    <div id="dndArea" class="placeholder">
                        <div class="filePickerContainer">
                            <div id="filePickerReady"></div>
                        </div>
                    </div>
                    <ul class="filelist element-invisible">
                        <li id="filePickerBlock" class="filePickerBlock"></li>
                    </ul>
                    <div class="statusBar element-invisible">
                        <div class="progress">
                            <span class="text">0%</span>
                            <span class="percentage"></span>
                        </div><div class="info"></div>
                        <div class="btns">
                            <div id="filePickerBtn"></div>
                            <!-- <div class="uploadBtn"><var id="lang_start_upload"></var></div> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
    var fileAllowFiles = [".png", ".jpg", ".jpeg", ".gif", ".bmp"];
    (function () {
    	var remoteImage,
        uploadImage,
        onlineImage;
    	var lang= UE.I18N['zh-cn'].insertimage;
        var uploadFile;
        window.onload = function () {
        	initTabs();
            setTabFocus('upload');
            initButtons();
        };
        /* 初始化tab标签 */
        function initTabs() {
            var tabs = $G('tabhead').children;
            for (var i = 0; i < tabs.length; i++) {
                domUtils.on(tabs[i], "click", function (e) {
                    var target = e.target || e.srcElement;
                    setTabFocus(target.getAttribute('data-content-id'));
                });
            }

            var img = editor.selection.getRange().getClosedNode();
            if (img && img.tagName && img.tagName.toLowerCase() == 'img') {
                setTabFocus('remote');
            } else {
                setTabFocus('upload');
            }
        }

        /* 初始化onok事件 */
        function initButtons() {
            dialog.onok = function () {
                var list = uploadFile.getInsertList();
                var count = uploadFile.getQueueCount();
                if (count) {
                    $('.info', '#queueList').html('<span style="color:red;">' + '还有2个未上传文件'.replace(/[\d]/, count) + '</span>');
                    return false;
                }
                switch (id) {
                case 'remote':
                    list = remoteImage.getInsertList();
                    break;
                case 'upload':
                    list = uploadImage.getInsertList();
                    var count = uploadImage.getQueueCount();
                    if (count) {
                        $('.info', '#queueList').html('<span style="color:red;">' + '还有2个未上传文件'.replace(/[\d]/, count) + '</span>');
                        return false;
                    }
                    break;
                case 'online':
                    list = onlineImage.getInsertList();
                    break;
            }
                if(list){
                	parent.imgs=list;
	                editor.execCommand('insertimage', list);
                }
            };
        }
        
        /* 初始化tabbody */
        function setTabFocus(id) {
        	 if(!id) return;
             var i, bodyId, tabs = $G('tabhead').children;
             for (i = 0; i < tabs.length; i++) {
                 bodyId = tabs[i].getAttribute('data-content-id');
                 if (bodyId == id) {
                     domUtils.addClass(tabs[i], 'focus');
                     domUtils.addClass($G(bodyId), 'focus');
                 } else {
                     domUtils.removeClasses(tabs[i], 'focus');
                     domUtils.removeClasses($G(bodyId), 'focus');
                 }
             }
             switch (id) {
                 case 'remote':
                     remoteImage = remoteImage || new RemoteImage();
                     break;
                 case 'upload':
                     setAlign(editor.getOpt('imageInsertAlign'));
                     uploadImage = uploadImage || new UploadImage('queueList');
                     break;
                 case 'online':
                     setAlign(editor.getOpt('imageManagerInsertAlign'));
                     onlineImage = onlineImage || new OnlineImage('imageList');
                     onlineImage.reset();
                     break;
             }
        }


        /* 上传附件 */
        function UploadFile(target) {
            this.$wrap = target.constructor == String ? $('#' + target) : $(target);
            this.init();
        }
        UploadFile.prototype = {
            init: function () {
                this.fileList = [];
                this.initContainer();
                this.initUploader();
            },
            initContainer: function () {
                this.$queue = this.$wrap.find('.filelist');
            },
            /* 初始化容器 */
            initUploader: function () {
                var _this = this,
                    $ = jQuery,    // just in case. Make sure it's not an other libaray.
                    $wrap = _this.$wrap,
                // 图片容器
                    $queue = $wrap.find('.filelist'),
                // 状态栏，包括进度和控制按钮
                    $statusBar = $wrap.find('.statusBar'),
                // 文件总体选择信息。
                    $info = $statusBar.find('.info'),
                // 上传按钮
                 //   $upload = $wrap.find('.uploadBtn'),
                // 上传按钮
                    $filePickerBlock = $wrap.find('.filePickerBlock'),
                    // 上传按钮
                    $filePickerBtn = $wrap.find('.filePickerBtn'),
                // 没选择文件之前的内容。
                    $placeHolder = $wrap.find('.placeholder'),
                // 总体进度条
                    $progress = $statusBar.find('.progress').hide(),
                // 添加的文件数量
                    fileCount = 0,
                // 添加的文件总大小
                    fileSize = 0,
                // 优化retina, 在retina下这个值是2
                    ratio = window.devicePixelRatio || 1,
                // 缩略图大小
                    thumbnailWidth = 113 * ratio,
                    thumbnailHeight = 113 * ratio,
                // 可能有pedding, ready, uploading, confirm, done.
                    state = '',
                // 所有文件的进度信息，key为file id
                    percentages = {},
                    supportTransition = (function () {
                        var s = document.createElement('p').style,
                            r = 'transition' in s ||
                                'WebkitTransition' in s ||
                                'MozTransition' in s ||
                                'msTransition' in s ||
                                'OTransition' in s;
                        s = null;
                        return r;
                    })();
                // WebUploader实例
                if (!WebUploader.Uploader.support()) {
                    $('#filePickerReady').after($('<div>').html(lang.errorNotSupport)).hide();
                    return;
                } 
                var acceptExtensions = (fileAllowFiles || []).join('').replace(/\./g, ',').replace(/^[,]/, '');
                var actionUrl = "${cxt}/elearning/photo/ajax/uploadPhoto.action";
                uploader = _this.uploader = WebUploader.create({
                	auto : true,
                    pick: {
                        id: '#filePickerReady',
                        label: lang.uploadSelectFile
                    },
                    accept : {
        				title: 'image',
                       	extensions: 'png,jpg,jpeg,gif,bmp',
                      	mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
        			},
                    swf: "${cxt}/js/webuploader/Uploader.swf",
                    server: actionUrl,
                    fileVal: "image",
                    duplicate: true,
                    fileSingleSizeLimit: 200*1024*1024,
                    compress: false
                });
                uploader.addButton({
                    id: '#filePickerBlock'
                });
                uploader.addButton({
                    id: '#filePickerBtn',
                    label: lang.uploadAddFile
                });

                setState('pedding');

                // 当有文件添加进来时执行，负责view的创建
                function addFile(file) {
                    var $li = $('<li id="' + file.id + '">' +
                            '<p class="title">' + file.name + '</p>' +
                            '<p class="imgWrap"></p>' +
                            '<p class="progress"><span></span></p>' +
                            '</li>'),

                        $btns = $('<div class="file-panel">' +
                            '<span class="cancel">' + lang.uploadDelete + '</span>' +
                            '<span class="rotateRight">' + lang.uploadTurnRight + '</span>' +
                            '<span class="rotateLeft">' + lang.uploadTurnLeft + '</span></div>').appendTo($li),
                        $prgress = $li.find('p.progress span'),
                        $wrap = $li.find('p.imgWrap'),
                        $info = $('<p class="error"></p>').hide().appendTo($li),

                        showError = function (code) {
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
                                    text = lang.errorFileType;
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
                        $wrap.text(lang.uploadPreview);
                        if ('|png|jpg|jpeg|bmp|gif|'.indexOf('|'+file.ext.toLowerCase()+'|') == -1) {
                            $wrap.empty().addClass('notimage').append('<i class="file-preview file-type-' + file.ext.toLowerCase() + '"></i>' +
                            '<span class="file-title" title="' + file.name + '">' + file.name + '</span>');
                        } else {
                            if (browser.ie && browser.version <= 7) {
                                $wrap.text(lang.uploadNoPreview);
                            } else {
                                uploader.makeThumb(file, function (error, src) {
                                    if (error || !src) {
                                        $wrap.text(lang.uploadNoPreview);
                                    } else {
                                        var $img = $('<img src="' + src + '">');
                                        $wrap.empty().append($img);
                                        $img.on('error', function () {
                                            $wrap.text(lang.uploadNoPreview);
                                        });
                                    }
                                }, thumbnailWidth, thumbnailHeight);
                            }
                        }
                        percentages[ file.id ] = [ file.size, 0 ];
                        file.rotation = 0;

                        /* 检查文件格式 */
                        if (!file.ext || acceptExtensions.indexOf(file.ext.toLowerCase()) == -1) {
                            showError('not_allow_type');
                            uploader.removeFile(file);
                        }
                    }

                    file.on('statuschange', function (cur, prev) {
                        if (prev === 'progress') {
                            $prgress.hide().width(0);
                        } else if (prev === 'queued') {
                        	//关闭删除文件
                            /* $li.off('mouseenter mouseleave');
                            $btns.remove(); */
                        }
                        // 成功
                        if (cur === 'error' || cur === 'invalid') {
                            showError(file.statusText);
                            percentages[ file.id ][ 1 ] = 1;
                        } else if (cur === 'interrupt') {
                            showError('interrupt');
                        } else if (cur === 'queued') {
                            percentages[ file.id ][ 1 ] = 0;
                        } else if (cur === 'progress') {
                            $info.hide();
                            $prgress.css('display', 'block');
                        } else if (cur === 'complete') {
                        }

                        $li.removeClass('state-' + prev).addClass('state-' + cur);
                    });

                    $li.on('mouseenter', function () {
                        $btns.stop().animate({height: 30});
                    });
                    $li.on('mouseleave', function () {
                        $btns.stop().animate({height: 0});
                    });

                    $btns.on('click', 'span', function () {
                        var index = $(this).index(),
                            deg;

                        switch (index) {
                            case 0:
                           	 	var $file = $('#' + file.id);
                           	 	var streamId = $file.find(">span.success").attr("datastreamId");
                           	 	if(streamId){
	                           	 	jQuery.post("${cxt}{cxt}/elearning/photo/ajax/deletePhoto.action",{"streamId":streamId},function call(data){
	                        			if(data.status){
											uploader.removeFile(file);
	                        			}
	                        		},"json");
                           	 	}else{
									uploader.removeFile(file);
                           	 	}
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
                            $wrap.css({
                                '-webkit-transform': deg,
                                '-mos-transform': deg,
                                '-o-transform': deg,
                                'transform': deg
                            });
                        } else {
                            $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                        }

                    });

                    $li.insertBefore($filePickerBlock);
                }

                // 负责view的销毁
                function removeFile(file) {
                    var $li = $('#' + file.id);
                    delete percentages[ file.id ];
                    updateTotalProgress();
                    $li.off().find('.file-panel').off().end().remove();
                }

                function updateTotalProgress() {
                    var loaded = 0,
                        total = 0,
                        spans = $progress.children(),
                        percent;

                    $.each(percentages, function (k, v) {
                        total += v[ 0 ];
                        loaded += v[ 0 ] * v[ 1 ];
                    });

                    percent = total ? loaded / total : 0;

                    spans.eq(0).text(Math.round(percent * 100) + '%');
                    spans.eq(1).css('width', Math.round(percent * 100) + '%');
                    updateStatus();
                }

                function setState(val, files) {

                    if (val != state) {

                        var stats = uploader.getStats();

                       // $upload.removeClass('state-' + state);
                       // $upload.addClass('state-' + val);

                        switch (val) {

                            /* 未选择文件 */
                            case 'pedding':
                                $queue.addClass('element-invisible');
                                $statusBar.addClass('element-invisible');
                                $placeHolder.removeClass('element-invisible');
                                $progress.hide(); $info.hide();
                                uploader.refresh();
                                break;

                            /* 可以开始上传 */
                            case 'ready':
                                $placeHolder.addClass('element-invisible');
                                $queue.removeClass('element-invisible');
                                $statusBar.removeClass('element-invisible');
                                $progress.hide(); $info.show();
                          //      $upload.text(lang.uploadStart);
                                uploader.refresh();
                                break;

                            /* 上传中 */
                            case 'uploading':
                                $progress.show(); $info.hide();
                            //    $upload.text(lang.uploadPause);
                                break;

                            /* 暂停上传 */
                            case 'paused':
                                $progress.show(); $info.hide();
                           //     $upload.text(lang.uploadContinue);
                                break;

                            case 'confirm':
                                $progress.show(); $info.hide();
                           //     $upload.text(lang.uploadStart);

                                stats = uploader.getStats();
                                if (stats.successNum && !stats.uploadFailNum) {
                                    setState('finish');
                                    return;
                                }
                                break;

                            case 'finish':
                                $progress.hide(); $info.show();
                                if (stats.uploadFailNum) {
                                  //  $upload.text(lang.uploadRetry);
                                } else {
                                 //   $upload.text(lang.uploadStart);
                                }
                                break;
                        }

                        state = val;
                        updateStatus();

                    }
					/* 
                    if (!_this.getQueueCount()) {
                        $upload.addClass('disabled');
                    } else {
                        $upload.removeClass('disabled');
                    } */

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
                        text = lang.updateStatusFinish.replace('_', fileCount).
                            replace('_KB', WebUploader.formatSize(fileSize)).
                            replace('_', stats.successNum);

                        if (stats.uploadFailNum) {
                            text += lang.updateStatusError.replace('_', stats.uploadFailNum);
                        }
                    }

                    $info.html(text);
                }

                uploader.on('fileQueued', function (file) {
                    fileCount++;
                    fileSize += file.size;

                    if (fileCount === 1) {
                        $placeHolder.addClass('element-invisible');
                        $statusBar.show();
                    }

                    addFile(file);
                });

                uploader.on('fileDequeued', function (file) {
                    fileCount--;
                    fileSize -= file.size;

                    removeFile(file);
                    updateTotalProgress();
                });

                uploader.on('filesQueued', function (file) {
                    if (!uploader.isInProgress() && (state == 'pedding' || state == 'finish' || state == 'confirm' || state == 'ready')) {
                        setState('ready');
                    }
                    updateTotalProgress();
                });

                uploader.on('all', function (type, files) {
                    switch (type) {
                        case 'uploadFinished':
                            setState('confirm', files);
                            break;
                        case 'startUpload':
                            /* 添加额外的GET参数 */
                            uploader.option('server', actionUrl);
                            setState('uploading', files);
                            break;
                        case 'stopUpload':
                            setState('paused', files);
                            break;
                    }
                });

                uploader.on('uploadBeforeSend', function (file, data, header) {
                    //这里可以通过data对象添加POST参数
                    header['X_Requested_With'] = 'XMLHttpRequest';
                });

                uploader.on('uploadProgress', function (file, percentage) {
                    var $li = $('#' + file.id),
                        $percent = $li.find('.progress span');

                    $percent.css('width', percentage * 100 + '%');
                    percentages[ file.id ][ 1 ] = percentage;
                    updateTotalProgress();
                });

                uploader.on('uploadSuccess', function (file, ret) {
                    var $file = $('#' + file.id);
                    try {
                        var responseText = (ret._raw || ret),
                            json = jQuery.parseJSON(responseText);
                        if (json.state == 'SUCCESS') {
                            _this.fileList.push(json);
                            if(json.streamId){
                            	$file.append('<span class="success" datastreamId="'+json.streamId+'"></span>');
                            }else{
                            	$file.append('<span class="success"></span>');
                            }
                        } else {
                            $file.find('.error').text(json.state).show();
                        }
                    } catch (e) {
                        $file.find('.error').text(lang.errorServerUpload).show();
                    }
                });

                uploader.on('uploadError', function (file, code) {
                });
                uploader.on('error', function (code, file) {
                    if (code == 'Q_TYPE_DENIED' || code == 'F_EXCEED_SIZE') {
                        addFile(file);
                    }
                });
                uploader.on('uploadComplete', function (file, ret) {
                });

                /* $upload.on('click', function () {
                	var list = uploadFile.getInsertList();
                	if(list && list.length>0){
                		var streamIds="";
                		for(var i=0;i<list.length;i++){
                			if(i==0){
                				streamIds=list[i].streamId;
                			}else{
                				streamIds=streamIds+","+list[i].streamId;
                			}
                		}
                		 var photoAlbumId=$("#photoAlbumId").val();
                		jQuery.post("${cxt}/elearning/photo/ajax/savePhoto.action",{"streamIds":streamIds,"photoAlbumId":photoAlbumId},function call(data){
                			if(!data.status){
								top.$("#showAddPhoto").windos("close");
                			}else{
                				if(data.message){
                					showMsg("error",data.message);
                				}else{
                					showMsg("error","上传图片失败!");
                				}
                			}
                		},"json");
                	}
                });

                $upload.addClass('state-' + state);
                */
                updateTotalProgress();
            },
            getQueueCount: function () {
                var file, i, status, readyFile = 0, files = this.uploader.getFiles();
                for (i = 0; file = files[i++]; ) {
                    status = file.getStatus();
                    if (status == 'queued' || status == 'uploading' || status == 'progress') readyFile++;
                }
                return readyFile;
            },
            getInsertList: function () {
                var i, link, data, list = [];
                for (i = 0; i < this.fileList.length; i++) {
                    data = this.fileList[i];
                    link = data.url;
                    list.push({
                        title: data.original || link.substr(link.lastIndexOf('/') + 1),
                        src: link,
                        streamId:data.streamId || 0
                    });
                }
                return list;
            }
        };
    })();
    </script>
</body>
</html>