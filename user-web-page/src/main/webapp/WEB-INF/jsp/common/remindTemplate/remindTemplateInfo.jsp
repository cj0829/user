<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../common/common.jsp"%>
<title>模版详细信息--${system_name }</title>  
</head>
<body>   
<div class="mainWindow">
      <div class="mainTitle">
      	<h3>模版详细信息</h3>
      </div>
      <div class="mainContainer">
      	<div class="formBox">
            <div class="formBoxItem">
              <label for="name">模版代码：</label>${remindTemplate.code}
            </div>
            <div class="formBoxItem">
              <label for="mailTemplate">邮件模版：</label>${remindTemplate.mailTemplate}
            </div>
            <div class="formBoxItem">
              <label for="smsTemplate">短信模版：</label>${remindTemplate.smsTemplate}
            </div>
            <div class="formBoxItem">
              <label for="remark">备注：</label>${remindTemplate.remark}
            </div>
            <div class="formBoxItem formBoxButton"> 
              <input type="button" value="关闭窗口" class="btn" onclick="cancelWindow()" />
            </div>  
        </div>
      </div>
    </div> 
 <script  type="text/javascript">  
		function cancelWindow(){
		   top.jQuery.dialog.list['batchSendMsgDialog'].close();
		}
 </script>
</body>
</html>