<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<%@ include file="/include/webuploader.jsp"%>
 	<title>添加版本升级--${system_name}</title>  
 	<script>
 		<c:if test="${message}">
 			alert("${message}");
 		</c:if>
 	</script>
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="${cxt}/mobile/updateVersion/add.action" method="post" enctype="multipart/form-data" name="updateVersionForm" id="updateVersionForm"  onsubmit="return saveUpdateVersion()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" style="height:310px;">
		${message}
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">版本描述</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="content" name="content" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">版本号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="newVersion" name="newVersion" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">社区</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="required:true" value="${agenciesId}"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">软件标识</th>
						<td class="e-form-td">
							<input class="easyui-textbox" id="type" name="type" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
						</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">是否为系统版本</th>
						<td class="e-form-td">
							<label><input id="system" name="system" type="checkbox"  value="<%=YesorNo.YES %>" />(是否为系统版本)</label>
						</td>
					</tr>
					<!-- <tr><th class="e-form-th" style="width:92px;">上传文件</th>
						<td class="e-form-td">
							<label><input id="system" name="system" type="file"/>(上传文件)</label>
						</td>
					</tr> -->
					</table>
				</div>
			</div>
		</div>
		<!--tab结束-->
	</div>
	<div style=" padding:10px 200px 0px 100px;">
	  	<div class="e-tab-btnbox">
			<a id="swfupload-control1" href="javascript:;"></a>
			<button class="btn cancel" type="submit"  style="position: absolute;margin: 0px 10px;">取消</button>
		</div>		
	</div>
	</form>
	<!--内容结束-->
</div>
<!--弹出窗口结束-->
<script type="text/javascript">

	var WebUploaderError=new Map();
	WebUploaderError.put("Q_TYPE_DENIED","请选择正确格式的apk！");
	WebUploaderError.put("F_EXCEED_SIZE","请上传小于10M的apk！");
	WebUploaderError.put("Q_EXCEED_SIZE_LIMIT","请上传小于10M的apk！");
	WebUploaderError.put("Q_EXCEED_NUM_LIMIT","只会有一apk片生效！");
	
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
 	
 	var uploader = WebUploader.create({
		auto : true,
		pick : {
			id : '#swfupload-control1',
			label : "选择文件并保存",
		},
		accept : {
			title: '选择 APK 文件',
            extensions: 'apk',
            mimeTypes: 'application/vnd.android.package-archive'
		},
		swf : "${cxt}/js/webuploader/Uploader.swf",
		server : "${cxt}/mobile/updateVersion/ajax/add.action",
		fileVal : "apk",
		duplicate : true,
		fileNumLimit : 1,
		fileSingleSizeLimit : 200*1024*1024 //20M  默认 2 M
	});
	
	uploader.on('uploadBeforeSend', function(file, data, header) {
		data["agenciesId"]=$("#agenciesId").combogrid('getValue');
		data["newVersion"]=$("#newVersion").val();
		data["content"]=$("#content").val();
		data["type"]=$("#type").val();
		if($("#system").is(":checked")) {
			data["system"]="<%=YesorNo.YES %>";
		}else{
			data["system"]="<%=YesorNo.NO %>";
		}
		data["random"]=Math.random();
		
	});
	uploader.on('startUpload', function( file ) {
		WaitingBar.getWaitingbar("addupdateVersion","文件正在导入，请等待...","${cxt}").show();
		return false;
	});
	uploader.on('error', function(type){
 		showMsg("error",WebUploaderError.get(type));
 		WaitingBar.getWaitingbar("addupdateVersion").hide();
 	});
	uploader.on('uploadSuccess', function( file,response ) {
		if(response.status==1){
			showMsg("info","上传成功");
			top.reloadData();
			cancelWindow();
		}else{
			showMsg("error",response.message);
		}
		uploader.removeFile( file, true );
		WaitingBar.getWaitingbar("addupdateVersion").hide();
	});
	uploader.on( 'uploadError', function( file,reason ) {
		uploader.removeFile( file, true );
		alert("上传失败"+reason );
	});

	uploader.on( 'uploadComplete', function( file ) {
		uploader.removeFile( file, true );
		WaitingBar.getWaitingbar("addupdateVersion").hide();
	});
 	
 	
	//提交表单的方法
	function saveUpdateVersion(){
		try{
			if($("#updateVersionForm").form("validate")){
				return true;
				/*
				WaitingBar.getWaitingbar("addupdateVersion","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#updateVersionForm").serialize();
				jQuery.post("${cxt}/mobile/updateVersion/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addupdateVersion").hide();
					if(data.status){
						showMsg("info","添加版本升级成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加版本升级失败!");
						}
					}
				},"json");
				*/
			}
		} catch (e) {}
		return false;
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
