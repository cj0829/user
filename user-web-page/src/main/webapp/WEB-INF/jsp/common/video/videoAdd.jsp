<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <%@ include file="/include/common.jsp"%>
   <%@ include file="/include/popAddWin.jsp"%>
   <%@ include file="/include/upload.jsp" %>
   <link rel="stylesheet" type="text/css" href="${cxt}/js/ztree/zTreeStyle.css"/> 
   <script type="text/javascript" src="${cxt}/js/ztree/jquery.ztree.core-3.5.min.js"></script>
   <script type="text/javascript" src="${cxt}/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
   <title>视频信息--${system_name }</title>   
</head>
<body>  

<div class="mainWindow">
    <div class="mainTitle">
      	<h3>视频信息</h3>
    </div>
    <div class="mainContainer">
      	<div class="formBox">
            <form action="#" method="post" name="videoForm" id="videoForm" onsubmit="return saveVideo(this)">  
	            <div class="formBoxItem">
	              	<label for="fileName"><span>*</span>文件名：</label>
	              	<input type="text" class="text" id="fileName" check="empty" value="${attachment.fileName }" min="1" max="48" maxlength="48"  msg="文件名" msgId="fileNameMsg" name="fileName" /> 
	              	<span class="advert" id="fileNameMsg"></span>
	            </div>
	           
	            <div class="formBoxItem">
		        	<label>视频文件：</label> 
		            <a id="swfupload-control3" class="uploadimg" style="float:left;">
		            <input type="button"  id="file_upload3"/></a>
		            <input type="text" class="text" readonly="readonly" id="attachmentPath" name="attachmentPath" value="${attachment.attachmentPath }" check="empty" min="1" msg="视频文件" msgId="pathMsg"/>
		            <span class="advert" id="pathMsg"></span>
		            <span id="divStatus3" style="padding-left: 10px">提示</span>
	            </div>
	            
	            <div class="formBoxItem formBoxButton">
		              <input type="submit" value="确定" class="btn"/> 
		              <input type="button" value="取消" class="btn" onclick="cancelWindow()" /> 
		              <input type="hidden" name="id" value="${attachment.id }"/>
		              <input type="hidden" name="status" id="status">
					  <input type="hidden" name="fileSize" id="fileSize" value="${attachment.fileSize}">
	            </div>
            </form>
        </div>
    </div>
</div> 
 <script  type="text/javascript">   
		
		//提交表单的方法
		function saveVideo(form1){
			//提交表单
			if(checkForm(form1)){ 
				var params=jQuery('#videoForm').serialize();
				jQuery.post("ajax/upload.action",params,function call(data){
					top.reloadPage();
		  			cancelWindow();
				},'json');
			}	
			return false;
		}
		
		function cancelWindow(){
		   top.jQuery.dialog.list['batchSendMsgDialog'].close();
		}
		
		 function findPositionIds(){
		    var treeObj = jQuery.fn.zTree.getZTreeObj("treeDemo");
		    var nodeIdStr="";
		    var nodes = treeObj.getCheckedNodes(true);
		    if (nodes.length > 0) {
			    for (var i=0, l=nodes.length; i<l; i++) {
			        if(i==0){
			           nodeIdStr=nodes[i].id;
			        }else{
			           nodeIdStr+=","+nodes[i].id;
			        } 
				} 
			}
			jQuery("#positionIds").val(nodeIdStr);
		}
		 jQuery(document).ready(function(){
			
			$("#swfupload-control3").swfupload({
				contextPath :"${cxt}",
				upload_url : "http://192.168.10.19/baseframwork/upload!uploadImage.action",
				button_placeholder : $("#file_upload3")[0],
				file_types : "*.flv; *.mp4;*.wmv;*.3gp;*.mov;*.asf;*.avi", 
				file_types_description : "视频",
				text_id:"attachmentPath",
				message_Id:"divStatus3",
				post_params: {
		            'reBackUrl':'<%=basePath%>'
		        },
				callback:{
					upload_success_handler : uploadSuccess
				}
			})
		});
		
		function uploadSuccess(file, serverData) { 
			//alert(serverData);
			var serverDataJson = eval('(' + serverData + ')');
			jQuery("#fileName").val(serverDataJson.obj.fileName);
			jQuery("#attachmentPath").val(serverDataJson.obj.fileUrl); 
			jQuery("#status").val(serverDataJson.obj.status);
			jQuery("#fileSize").val(serverDataJson.obj.fileSize);
			jQuery("#divStatus3").html("");
			  
		 	
		};
		
</script>
</body>
</html>