<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
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
					<label class="mr8 lab" style="width:60px;">班级:</label>
				</div></td>
				<td class="veraT" style="width:215px;">
					<form>
						<div class="fl">
							<a id="swfupload-control1" class="icon-spirit uploading test-upload" href="javascript:;"></a>
						</div>
					</form>
					<a class="tabbtn fl ml8" type="button" href="${cxt}/temp/ContactsUserModel.xlsx">下载用户模板</a>
				</td>
			</tr>
			<tr>
				<td class="veraT"><div class="colorc" id="uploaded_info"></div></td>
				<td class="veraT" style="width:215px;">
				</td>
			</tr>
		</table>
			<!-- <button class="tabbtn mr15 fr" type="button" >上传试题</button> -->
		</div>
		
		<!--end 表格操作btn -->
		<div class="form-tab-wrap">
			<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<!-- <button class="btn mr25" type="button" onclick="save();">保存</button> -->
		<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
	</div>
<script  type="text/javascript">
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
			accept : {
				title: 'excel',
                extensions: 'cvs,xls,xlsx',
                mimeTypes: 'excel/*'
			},
			swf : "${cxt}/js/webuploader/Uploader.swf",
			server : "${cxt}/mobile/usercontacts/ajax/upload.action",
			fileVal : "excel",
			duplicate : true,
			fileSingleSizeLimit : 200*1024*1024, //20M  默认 2 M
			formData:{
		    	"magnitude":"angel",
				"agenciesId":"${agenciesId}",
			},
		});
		
		uploader.on('uploadBeforeSend', function(file, data, header) {
			data["random"]=Math.random();
		});
		uploader.on( 'startUpload', function( file ) {
			waitingbar=jQuery.getWaitingbar("文件正在导入，请等待...",true,"${cxt}");
		});
		uploader.on('uploadSuccess', function( file,response ) {
			if(response.status==1){
				reloadData();
				showMsg("info","上传成功");
			}else{
				showMsg("error","上传失败");
				uploaded_info.innerHTML=response.message;
			}
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
		
		$('#datagridList').datagrid({
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,			
			columns:[[
				{field:'ck',checkbox:true},
				{title:'文件名',field:'originalFileName',width:100},
				{title:'用户总数',field:'userTotal',width:100},
				{title:'待批数',field:'userEffectedTotal',width:100},
				{title:'通过数',field:'userPassTotal',width:100},
				{title:'未通过数',field:'userUnPassTotal',width:100,formatter:function(value,rec,index){
					if(rec.userUnPassTotal==0){
						return "<a>"+rec.userUnPassTotal+"</a>";
					}else{
						return "<a href=javascript:userUnPassTotal("+rec.id+");>"+rec.userUnPassTotal+"</a>";
					};
				}},
				{title:'审核结果',field:'auditStatus',width:150,formatter:function(value,rec,index){
					if(rec.userTotal==rec.userPassTotal){
						return "已完成";
					}else if(rec.userTotal>rec.userPassTotal){
						return "未完成";
					}else{
						return "待批";
					}
				}},
				{title:'上传时间',field:'upLoadDate',width:150},
				{title:'上传者',field:'upLoadUserId',width:150,formatter:function(value,rec,indec){
					return rec;
				}}
			]],
			pagination:true
		});
	}); 
	//查询用户通过数
	function userPassTotal(id){
		top.$('#winDetail').window({
			title:"用户通过数",
		    width:800,
		    height:600,
		    iframe:"${cxt}/userImportFile/preUserPassTotal.action?id="+id,
		    modal:true
		});
	}
	//查询用户未通过数
	function userUnPassTotal(id){
		top.$('#winDetail').window({
			title:"用户未通过数",
		    width:800,
		    height:600,
		    iframe:"${cxt}/userImportFile/preUserUnPassTotal.action?id="+id,
		    modal:true
		});
	}
	
	//下载模板
	function downloadModel(){  
		jQuery.post("${cxt}/userImportFile/ajax/delete.action",param, function call(data){
			if(data.status){
				showMsg("info","删除成功");
				reloadData();
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","删除失败!");
				}
			}
		},'json');
	}
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	}
    
   //回调方法
	function callbackPage(){    
		reloadData();
	};
	function cancelWindow(){
		top.jQuery("#win").window("close");
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
