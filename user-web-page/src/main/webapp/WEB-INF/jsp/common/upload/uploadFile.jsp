<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/> 
 <script type="text/javascript" src="js/jquery.min.js"></script> 
<script type="text/javascript" src="js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="js/uploadify/jquery.uploadify.comm.js"></script>
<link rel="stylesheet" type="text/css" href="js/uploadify/uploadify.css" /> 
</head> 
<body style="background:#fffff;"> 
 <div style="margin:10px 0 0 50px;"><input type="file" id="file_upload" multiple="false" name="uploadFile"/></div>
<div id="myqueue"></div>
<script type="text/javascript" >  
   jQuery(document).ready(function(){    
	    uploadFn({'uploadid':'file_upload',
	    	    'queueid':'myqueue',
	    	    'contextPath':'<%=request.getContextPath()%>',
	    	    'uploaderurl':'upload!uploadImage.action?ajaxFlag=1&objectType=User',
	    		'buttonText':'上传文件',
	    		'multi':"${multi}",
	    		'fileSizeLimit':'3MB',
	    		'queueSizeLimit':8,
	    		'fileTypeDesc':'',
	    		'fileTypeExts':'*.jpg; *.jpeg; *.gif; *.bmp; *.png',
	    		'formData':{'jessionid':'<%=request.getSession().getId()%>'}
	     });
   });
   
  function singleUpload(data){ 
       top.loadUploadReply(data.imgServiceUrl+data.obj.attachmentPath,data.obj.attachmentId);
       top.$.dialog.list['batchSendMsgDialog'].close();
  }
</script>
</body>
</html>