<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<%@include file="/include/webuploader.jsp"%>
 	<title>上传用户人脸--${system_name}</title>  
 	<style>
		img {
			width: auto\9;
			height: auto;
			max-width: 100%;
			vertical-align: middle;
			border: 0;
			-ms-interpolation-mode: bicubic;
		}
	</style>
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="userGroupForm" id="userGroupForm" onsubmit="return saveUserGroup()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一张图" style="height:310px;">
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
                	<tr><th class="e-form-th" style="width:92px;">人脸名称</th>
						<td class="e-form-td">
							<input class="easyui-textbox" type="text" name="userName" id="userName"/>
							<input type="hidden" name="index" value="1"/>
						</td>
						<td>
							<div style="text-align:center;">
							  	<div class="e-tab-btnbox">
							  		<a id="swfupload-control1" href="javascript:;"></a>
								</div>		
							</div>
						</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">绑定机构</th>
						<td class="e-form-td">
							${agencie.name}
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">人脸图片张图</th>
						<td class="e-form-td">
						<img id="target1" src="${userFaceBean.facepath1}"/>
					</td>
					
					</tr>
					</table>
				</div>
			</div>
		</div>
		<!--tab结束-->
	</div>
	</form>
	<!--内容结束-->
</div>
<!--弹出窗口结束-->
<script type="text/javascript">
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
 	
 	var WebUploaderError=new Map();
 	WebUploaderError.put("Q_TYPE_DENIED","请选择正确格式的图片！");
 	WebUploaderError.put("F_EXCEED_SIZE","请上传小于10M的图片！");
 	WebUploaderError.put("Q_EXCEED_SIZE_LIMIT","请上传小于10M的图片！");
 	WebUploaderError.put("Q_EXCEED_NUM_LIMIT","只会有一张图片生效！");
 	var waitingBar=WaitingBar.getWaitingbar("login","正在上传头像，请等待...","${jsPath}");

 	//-->上传 第一图片
 	var uploader1 = WebUploader.create({
 		auto : true,
 		pick : {
 			id : "#swfupload-control1",
 			label : "选择图片"
 		},
 		accept : {
 			title: 'Images',
 	       	extensions: 'gif,jpg,jpeg,png,bmp',
 	       	mimeTypes: 'image/.gif,image/.jpg,image/.jpeg,image/.png,image/.bmp'
 		},
 		swf : "${cxt}/js/webuploader/Uploader.swf",
 		server : "${cxt}/face/userFacedb/ajax/addCollectFace.action",
 		fileVal : "image",
 		duplicate : true,
 		fileNumLimit : 1,
 		fileSingleSizeLimit : 10*1024*1024, 
 		formData:{}
 	});
 	uploader1.on('uploadStart', function(type){
 		waitingBar.show();
 	});
 	uploader1.on('uploadBeforeSend', function(file, data, header) {
		data["agenciesId"]="${agencie.id}";
		data["userName"]=$("#userName").val();
		data["index"]=1;		
		data["random"]=Math.random();
		
	});
 	uploader1.on('error', function(type){
 		showMsg("error",WebUploaderError.get(type));
 		waitingBar.hide();
 	});
 	uploader1.on('uploadSuccess', function( file,response ) {
 		if(response.status==1){
 			if(response.data){
 				top.callbackPage();
	 			$("#target1").attr("src",response.data.facepath);
	 			cancelWindow();
 			}
 		}else{
 			if(response.message){
 				showMsg("error",response.message);
 			}else{
 				showMsg("error","文件格式不正确或文件已损坏");
 			}
 		}
 		uploader1.removeFile( file, true );
 		waitingBar.hide();
 	});
 	uploader1.on('uploadError', function( file,reason ) {
 		uploader1.removeFile( file, true );
 		showMsg("error","文件格式不正确或文件已损坏");
 		waitingBar.hide();
 	});
 	uploader1.on( 'uploadComplete', function( file,reason ) {
 		waitingBar.hide();
 	});
 	//-->
 	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
