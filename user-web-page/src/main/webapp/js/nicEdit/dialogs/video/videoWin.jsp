<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
	<%@ include file="/include/user.jsp"%>
	<%@ include file="/include/nicEdit.jsp"%>
	<%@ include file="/include/webuploader.jsp"%>
	<script type="text/javascript" src="${jsPath}/js/nicEdit/dialogs/video/uploadVideo.js"></script>
	<link rel="stylesheet" type="text/css" href="${jsPath}/css/exam/${SECURITY_CONTEXT.skinName}/uploadImg.css"/>
	<script type="text/javascript" src="${cxt}/js/jqueryWaitingBar.js"></script>
 	<title>视频--${system_name}</title>  
</head>
  
  <body>
  	<div class="e-tabhead">
	  	<div class="e-tabhdL">
			<span class="tab curr" type="remote">插入视频</span>
			<span class="tab" type="upload">上传视频</span>
		</div>
		<div class="video-alignBar">
			<!-- 
			<label class="algnLabel">图片浮动方式：</label>
			<span id="alignIcon" class="alignIcon">
				<span class="none-align curr" value="none" title="无浮动"></span>
				<span class="left-align"  value="left" title="左浮动"></span>
				<span class="right-align"  value="right" title="右浮动"></span>
				<span class="center-align"  value="center" title="居中独占一行"></span>
			</span>
			 -->
		</div>
	</div>
	<div class="e-tabbox main-expand-form e-tabbody">
		<div class="panel curr">
			<table class="videotop" width="100%;">
				<tbody>
					<tr>
						<td style="width:64px;"><label class="url">视频网址</label></td>
						<td><input id="videoUrl" style="width:100%;" type="text" class="txt" /></td>
					</tr>
				</tbody>
			</table>
			<div id="videoPreview" class="videoPreview"></div>
			<div class="videoInfo">
				<fieldset class="videoinfo-mode mb10">
                    <legend>视频尺寸</legend>
                    <table>
                        <tbody>
	                        <tr><td class="pb5"><label class="mr5">宽度</label></td><td class="pb5"><input id="videoWidth" style="width:80px; height:24px;" type="text" class="easyui-numberspinner" /></td></tr>
	                        <tr><td><label class="mr5">高度</label></td><td><input id="videoHeight" style="width:80px; height:24px;" type="text" class="easyui-numberspinner" /></td></tr>
                    	</tbody>
                    </table>
                </fieldset>
			</div>
		</div>
		<div class="panel">
		<div class="queueList">
		<div id="statusBar" class="statusBar">
			<div id="progress" class="progress">
                          <span class="text">0%</span>
                          <span class="percentage"></span>
                      </div>
			<div class="info" id="info"></div>
			<div class="btns">
				<div id="filePickerBtn" class="filePickerBtn"></div>
				<!-- <button id="filePickerReady" type="button" class="tabbtn other mr10">继续添加</button> -->
				<button type="button" id="uploadBtn" class="tabbtn fl ml10">开始添加</button>
			</div>
		</div>
		</div>
		<table width="100%">
			<tr>
				<td class="upload_left">
					<div class="queueList">
						<ul id="filelist" class="filelist indertvideo">
							<li id="filePickerBlock" class="filePickerBlock"></li>
						</ul>
					</div>
				</td>
				<td class="uploadVideoInfo" style="width:135px;">
					<fieldset class="videoinfo-mode mb10 mt8">
	                    <legend>视频尺寸</legend>
	                    <table>
	                        <tbody>
		                        <tr><td class="pb5"><label class="mr5">宽度</label></td><td class="pb5"><input id="videoListWidth" style="width:80px; height:24px;" type="text" class="easyui-numberspinner" /></td></tr>
		                        <tr><td><label class="mr5">高度</label></td><td><input id="videoListHeight" style="width:80px; height:24px;" type="text" class="easyui-numberspinner" /></td></tr>
	                    	</tbody>
	                    </table>
	                </fieldset>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" onclick="saveVideo()">保&nbsp;&nbsp;存</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取&nbsp;&nbsp;消</button>
	</div>
	<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tabhead").height()-$(".e-tab-btnbox").height()-30);
	};
	
	var editor,parentDiv;
	var  uploadVideo;
	var waitingbar;
	/**
	*
	*/
	function iniWindowParam(ed,t){
		this.editor=ed;
		this.parentDiv=t;
		var video = editor.selection.getRange().getClosedNode();
		if (video) {
		    this.setVideo(video);
		}
	}
   jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tabhead").height()-$(".e-tab-btnbox").height()-30);
		});
	});
   function setVideo(video){
       /* 不是正常的图片 */
       if (!video.tagName || video.tagName.toLowerCase() != 'video' && !video.getAttribute("src") || !video.src) return;
       	/* 设置表单内容 */
		var url = video.getAttribute("_url");
		if(url){
			$("#videoUrl").val(url || '');
			createPreviewVideo(url);
		}
        $("#videoWidth").numberspinner("setValue",video.width || '') ;
        $("#videoHeight").numberspinner("setValue",video.height || '');
       
   }
	function saveVideo(){
		var list=[];
		var type= $(".e-tabhdL .tab.curr").attr("type");
		if(type){
			switch (type) {
		       case 'remote':
				  var width = $("#videoWidth").numberspinner("getValue"),
		            height = $("#videoHeight").numberspinner("getValue"),
		            url=$("#videoUrl").val();
		        	if(!url) return false;
			        editor.execCommand('insertvideo', {
			            url: convert_url(url),
			            width: width.value,
			            height: height.value
			        }, 'object');
		           break;
		       case 'upload':
		           list = uploadVideo.getInsertList();
		           var count = uploadVideo.getQueueCount();
		           if (count) {
		               $("#info").html('<span style="color:red;">' + '还有2个未上传文件'.replace(/[\d]/, count) + '</span>');
		               return false;
		           }
		           break;
			}
			if(list) {
				editor.execCommand('insertVideo', list,"object");
				cancelWindow();
	        }
		}
   }
   //tab切换
   $(function(){
	   $(".e-tabhdL .tab").click(function(){
			var type=$(this).attr("type");
			$(this).addClass("curr").siblings().removeClass("curr");
			$(".e-tabbody").children().eq($(this).index()).addClass("curr").siblings().removeClass("curr");
			switch (type) {
		        case 'remote':
		            break;
		        case 'upload':
		            uploadVideo = uploadVideo || new UploadVideo('queueList',"${file_server}");
		            break;
			}
		});
	   $("#videoUrl").bind("change",function(){
		   createPreviewVideo($("#videoUrl").val());
	   });
   });
   
   function createPreviewVideo(url){
       if ( !url )return;

       var conUrl = convert_url(url);
       var objectHtml=[];
       var objectParam=[];
      //var ss= "http://127.0.0.1:8081/fss/attachment/flv/aac21116-0f82-4a85-94a0-e2fbad8cf40e/video.flv";
       objectParam.push("flashplayer=",encodeURIComponent("/exam/js/jwplayer/player.swf"),"&");
       objectParam.push("playlist=",encodeURIComponent('[[JSON]][{"sources":[{"file":"'+conUrl+'","default":false}],"tracks":[]}]'),"&");
       objectParam.push("base=",encodeURIComponent('http://127.0.0.1:8080/exam/>'));
       
       objectHtml.push('<object type="application/x-shockwave-flash" data="/exam/js/jwplayer/player.swf" width="330" height="215" bgcolor="#000000" tabindex="0">');
       objectHtml.push('<param name="flashvars" ','value=',objectParam.join(""),'>');
       objectHtml.push('<param name="allowfullscreen" value="true">');
       objectHtml.push('<param name="allowscriptaccess" value="always">');
       objectHtml.push('<param name="seamlesstabbing" value="true">');
       objectHtml.push('<param name="wmode" value="opaque">');
       objectHtml.push('</object>');
       $("#videoPreview").html(objectHtml.join(""));
   }
   
   function cancelWindow(){
		parentDiv.window("destroy");
	}
	</script>
  </body>
</html>
