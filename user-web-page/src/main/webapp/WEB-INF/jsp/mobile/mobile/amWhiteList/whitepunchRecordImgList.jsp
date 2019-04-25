<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<%@include file="/include/webuploader.jsp"%>
	<title>白名单打卡记录管理--${system_name}</title>  
  
</head>
<body>
<%@include file="../common/header.jsp"%>
<!--content开始-->
<div class="content-wrap ml20 mr20">
	<!--当前标题开始-->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!--当前标题结束-->
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bar">
			<a href="${cxt}/mobile/amWhiteList/whitepreList.action" class="curr">按条件搜索</a>
            <a href="${cxt}/mobile/amWhiteList/preWhitepunchRecordImgList.action">按图搜索</a>
		</div>
		<div class="e-tabchange-bd">
			<!-- 搜索 start  -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					</td>
					<td class="e-search-btn-box" style="width:244px;">
						  <img id="target1" width="50" height="50"/>
						<a id="swfupload-control1" href="javascript:;"></a>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0" class="tablemode"></table>
			</div>			
			 <!--table表格结束-->
		</div>
	</div>
</div>
<!--content结束-->
<!--版权开始-->
<%@include file="../common/footer.jsp"%>
<!--版权结束-->

<script type="text/javascript">
	var fileId;
	$(function(){
		$("#datagridList").datagrid({
			nowrap: true,
			url:"${cxt}/mobile/amWhiteList/ajax/findWhitepunchRecordByImg.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:"id",
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:"ck",checkbox:true},
						{title:"姓名",field:"name",width:150},
						{title:"设备",field:"iccid",width:150},
						{title:"时间",field:"createTime",width:150},
						{title:"比对照片",field:"dataUrl",width:50,formatter:function(value,rec){
							var buttonHtml=[];
							if(rec.dataUrl){
								buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.dataUrl+"\">"," ");
							}
							return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
						}},
						{title:"人脸比分",field:"score",width:50},
						{title:"底库照片",field:"localImage",width:80,formatter:function(value,rec){
							var buttonHtml=[];
							if(rec.localImage){
								buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.localImage+"\">"," ");
							}
							return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
						}},
						{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:deletePunchRecord('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	//查询方法名称
	function queryParams(){
		try{
			return {
			"auto":true,//自动拼接
			"fileId":fileId
			};
		}catch (e) {}
		return {};
	};
	
	//删除全部
	function deletePunchRecords(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...","${jsPath}").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/mobile/amBlackList/punchRecord/ajax/delete.action",param, function call(data){
			WaitingBar.getWaitingbar("deletes").hide();
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
			},"json"); 
		}
	}
	
	// 删除
	function deletePunchRecord(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/mobile/punchRecord/ajax/delete.action",{"ids":id}, function call(data){
				WaitingBar.getWaitingbar("delete").hide();
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
			},"json"); 
		}
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid("reload");
		return false;
	}
    
   //回调方法
	function callbackPage(){
		reloadData();
	}
   
	//===================================================
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
 			label : "选择图片查询"
 		},
 		accept : {
 			title: 'Images',
 	       	extensions: 'gif,jpg,jpeg,png,bmp',
 	       	mimeTypes: 'image/.gif,image/.jpg,image/.jpeg,image/.png,image/.bmp'
 		},
 		swf : "${cxt}/js/webuploader/Uploader.swf",
 		//server : "${cxt}/mobile/amBlackList/ajax/findSetParam.action?userId=${userId}&index=1",
 		server : "${cxt}/file/ajax/upload.action",
 		fileVal : "image",
 		duplicate : true,
 		fileNumLimit : 1,
 		fileSingleSizeLimit : 10*1024*1024, 
 		formData:{}
 	});
 	
 	uploader1.on('uploadStart', function(type){
 		fileId=null;
 		waitingBar.show();
 	});

 	uploader1.on('error', function(type){
 		showMsg("error",WebUploaderError.get(type));
 		waitingBar.hide();
 	});
 	uploader1.on('uploadSuccess', function( file,response ) {
 		if(response.status==1){
 			if(response.data){
	 			$("#target1").attr("src",response.data.url);
 			}
 			fileId=response.data.id
 			$("#datagridList").datagrid("reload");
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
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
