﻿<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="考试系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
  	<%@ include file="/include/webuploader.jsp"%>
  	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>用户文件导入--${system_name}</title>  
  
</head>
<body>
	
	<div class="e-tabbox">
		<!--start 表格操作btn -->
		<div class="e-tabbtn-box mb2">
		<table width=100%;>
			<tr>
				<td class="veraT"><div class="colorc" id="">
					<label class="mr8 lab" style="width:60px;">机构:</label>${agenciesName}<br/>
					上传压缩包，压缩包里的图片名，作为用户名。
				</div></td>
				<td class="veraT" style="width:215px;">
					<form>
						<div class="fl">
							<a id="swfupload-control1" class="icon-spirit uploading test-upload" href="javascript:;"></a>
						</div>
					</form>
				</td>
			</tr>
			<tr>
				<td class="veraT"><div class="colorc" id="uploaded_info"></div></td>
				<td class="veraT" style="width:215px;">
				</td>
			</tr>
		</table>
		</div>
		
		<!--end 表格操作btn -->
		<div class="form-tab-wrap">
			<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
	</div>
<script  type="text/javascript">
var WebUploaderError=new Map();
WebUploaderError.put("Q_TYPE_DENIED","请选择正确格式的图片！");
WebUploaderError.put("F_EXCEED_SIZE","请上传小于10M的图片！");
WebUploaderError.put("Q_EXCEED_SIZE_LIMIT","请上传小于10M的图片！");
WebUploaderError.put("Q_EXCEED_NUM_LIMIT","只会有一张图片生效！");
var waitingBar=WaitingBar.getWaitingbar("login","正在上传头像，请等待...","${jsPath}");

var waitingbar;
	$(function(){
		 $(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		 jQuery(document).ready(function(){
				$(window).unbind('#winBody').bind('resize.winBody', function(){
					$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
				});
			});
		var uploader = WebUploader.create({
			auto : true,
			pick : {
				id : '#swfupload-control1',
				label : "选择文件",
			},
		
			swf : "${cxt}/js/webuploader/Uploader.swf",
			server : "${cxt}/face/userAmFace/ajax/addAll.action",
			duplicate : true,
			fileSingleSizeLimit : 200*1024*1024*1024, //20M  默认 2 M
			formData:{
		    	"magnitude":"angel",
				"agenciesId":"${agenciesId}",
			},
		});
		uploader.on('uploadBeforeSend', function(file, data, header) {
			data["random"]=Math.random();
		});
		uploader.on('error', function(type){
			showMsg("error",WebUploaderError.get(type));
			waitingBar.hide();
		});
		uploader.on( 'startUpload', function( file ) {
			waitingbar=jQuery.getWaitingbar("文件正在导入，请等待...",true,"${cxt}");
		});
		uploader.on('uploadSuccess', function( file,response ) {
			if(response.status==1){
				showMsg("info","上传成功");
			}else{
				showMsg("error","上传失败");
			}
			uploaded_info.innerHTML=response.message;
			uploader.removeFile( file, true );
			waitingbar.hide();
		});
		uploader.on( 'uploadError', function( file,reason ) {
			uploader.removeFile( file, true );
			alert("上传失败"+reason );
		});

		uploader.on( 'uploadComplete', function( file ) {
			uploader.removeFile( file, true );
			waitingbar.hide();
		});
		
	}); 
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
