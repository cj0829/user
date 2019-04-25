<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ include file="/include/common.jsp"%>
 <%@ include file="/include/upload.jsp" %>
 <%@ include file="/include/popAddWin.jsp"%>
 <title>导入数据--${system_name }</title>  
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
	    	<div class="mainContainer">
			<div class="formBox">
            <form action="ajax/add.action" method="post" name="newsForm" id="newsForm" onsubmit="return saveNews(this)">  
	            
	            <div class="formBoxItem">
	              	<label>文件：</label> 
	              	<a id="swfupload-control1" class="uploadimg" style="float:left;" >
	              	<input type="button" id="file_upload1"/></a>
	              	
	              	<span id="divStatus1" style="padding-left: 10px">提示</span>
	              	<span class="showImg" id="resourcePathMsg"></span> 
	              	<input type="hidden" id="resourcePath" name="resourcePath" />
	            </div>
	            
	            <!--  
	            <div class="formBoxItem formBoxButton">
		              <input type="submit" value="确定" class="btn" /> 
		              <input type="button" value="取消" class="btn" onclick="cancelWindow()" /> 
		              <input type="hidden" name="newsStatus" value="1"/>
		              <input type="hidden" name="newsType" value="1"/>
		              <input type="hidden" name="nodeId" value="${nodeId}"/> 
		              <input type="hidden" name="releaseType" value="2"/>
		              <input type="hidden" name="onclick" value="0"/>
		              <input type="hidden" name="positionIds" id="positionIds" value=""/>
	            </div>
	            -->
	            
	            <div class="formBoxItem">
	              	<span id="info" ></span>
	              	
	            </div>
            </form>
        </div>
		</div>
		</div>
		<!-- right_content end -->
    </div>
    <!-- middle end --> 
    
</div>
<%@ include file="/include/footer.inc"%>  
<script type="text/javascript">   
	//提交表单的方法
	function saveNews(form1){ 
	   findPositionIds();
		//提交表单
		if(checkForm(form1)){  
               return true; 
		}	
		return false;
	}
	
	function cancelWindow(){
	   top.jQuery.dialog.list['batchSendMsgDialog'].close();
	}
		
	 jQuery(document).ready(function(){
		$("#swfupload-control1").swfupload({
			contextPath :"${cxt}",
			upload_url : "${cxt}/upload/ajax/saveImport.action",
			post_params: {
	            "jessionid" : "<%=request.getSession().getId()%>"
	        },
			button_placeholder : $("#file_upload1")[0],
			file_types : this.fileTypes || "*.csv; *.xls; *.xlsx;", 
			file_types_description : "文件",
			text_id:"resourcePath",
			message_Id:"divStatus",
			callback:{
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete
			}
		});
	});
		
	function uploadError(file, errorCode, message) {
		alert("message" + message);
	}
	uploadComplete = function(file, serverData) {
		//alert("uploadComplete serverData" + serverData);;
	};
	function uploadSuccess(file, serverData) { 
		var serverDataJson = eval('(' + serverData + ')');
	 	//alert("serverData:" + serverData);
	 	if(serverDataJson.status=='1'){
	 		//top.showMsg("success",serverDataJson.message);
	 	}else{
	 		
	 		//top.showMsg("error",serverDataJson.message);
	 	}
	 	
	 	jQuery("#divStatus1").html(serverDataJson.message);  
	 	
	};
</script>
</body>
</html>