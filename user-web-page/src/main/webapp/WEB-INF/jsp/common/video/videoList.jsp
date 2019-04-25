<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ include file="/include/common.jsp"%> <%@ include file="/include/unbasePage.jsp"%> 
 <title>视频--${system_name }</title>  
</head>
<body>  
<div class="wrapper">
    <%@ include file="/include/headers.inc"%> 
    <!-- middle begin -->
    <div class="middle">
        <!-- left_menu begin -->
	    <div class="left_menu">
	        <%@ include file="/include/menu.jsp"%>
	    </div>
	    <!-- left_menu end -->
	    <!-- right_content begin -->
	    <div class="right_content"> 
			<div class="list_page">
				<form name="roleForm" id="roleForm">
				    <table>
						<tr id="roleList" sort="false" >
							<th field="pmt_list_sequence" width="10%" >序号</th>
							<th field="pmt_list_checkbox" isCheck="isDisplayApplicationButton" property="id" width="10%"><input type="checkbox" id="checkbox"/></th>
							<th field="fileName" linked="1"  width="35%">文件名</th> 
							<th field="fileSize" width="10%">文件大小</th> 
							<th field="pmt_list_operate" width="30%">操作</th>
						</tr>
					</table>
					<div id="rolePageFoot"></div> 
					<div id="com_button" buttonType="上传|删除" buttonCode="" method="addVideo()|deleteVideos()"></div> 
				</form>
			</div>
		</div>
		<!-- right_content end -->
    </div>
    <!-- middle end --> 
    
</div>
<%@ include file="/include/footer.inc"%>  
 
 <script  type="text/javascript"> 
	 pmt_list_trId="roleList";
	 pmt_list_pageFoot="rolePageFoot";
	 pmt_list_page.orderBy="r.id desc";
	 pmt_list_url="ajax/list.action";
	 
	 pmt_list_headCheckboxId="checkbox";
	 pmt_list_checkboxName="roleIds";
     //pmt_list_buttonCode = "<s:property value='#session.SECURITY_CONTEXT.userSession.authorityCodes'/>";
	 var roleType = '1';
	 pmt_list_linkedList=[{url:"videoPlayer.action",
		  arg:["roleId","id"], 
		  method:"videoPlayer"}]; 
	 
	 pmt_list_operate=[{name:"编辑", 
						   url:"preUpdate.action", 
						   buttonCode:"0201002", 
						   isCheck:"isDisplayApplicationButton",
						   arg:["roleId","id"],
						   method:"editVideo"
						},{name:"删除", 
						   url:"ajax/delete.action", 
						   buttonCode:"0201003", 
						   isCheck:"isDisplayApplicationButton",
						   arg:["roleIds","id"],
						   method:"deleteRole"
						}]; 
	  
	 pmt_com_buttonId="com_button"; 
	 
	jQuery(document).ready(function(){
		loadData(1);	
		pmt_create_button(); 	
	});
	
	//播放视频
	function videoPlayer(col,url){
	   popupDialog(url,'视频播放');
	}
	
	//添加视频
	function addVideo(){
	   popupDialog('preUpload.action','上传视频');
	}
	// 编辑视频
	function editVideo(col,url){ 
	   popupDialog(url,'编辑视频');
	}
	//删除视频
	function deleteVideos(){ 
	    if(pmt_list_getCheckboxValue(1)){
		    if(confirm("确认是否删除?")){
			    var params=jQuery('#roleForm').serialize();
				jQuery.post("ajax/delete.action",params, function call(result){
					if(result.status){
						showMsg("success","删除成功");
					    loadData(1);
					}else{
						showMsg("error",msg);
					}
				},'json'); 
			}
		}
	}
	
	//删除视频
	function deleteRole(col,url){ 
		if(confirm("确认是否删除?")){
	        jQuery.post(url,"", function call(result){
				if(result.status){
					showMsg("success","删除成功");
				    loadData(1);
				}else{
					top.showMsg("error",result.message);
				}
			},'json'); 
		}
	}
	
	
   //判断操作栏的是否显示
	function isDisplayApplicationButton(col){  
	  	//var name = pmt_list_page.listData[col].name;
	  	//var roleType = pmt_list_page.listData[col].roleType;
	  	//if(name=='superadmin' || roleType==1){
	    //  	return false;
	  	//}
	  	return true;
	}
   
    
   //回调方法
	function callbackPage(){ 
		 loadData(1);	
	} 	
	
	function reloadPage(){ 
		showMsg("success","操作成功");
		loadData(1);	
	} 	
</script>
</body>
</html>