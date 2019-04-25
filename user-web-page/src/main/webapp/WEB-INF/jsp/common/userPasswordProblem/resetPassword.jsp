<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ taglib prefix="core" uri="/core-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="${jsPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<script type="text/javascript" src="${jsPath}/js/common.js"></script>
	<script type="text/javascript" src="${jsPath}/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${jsPath}/js/jquery.extend.validatebox.js"></script>
	<link href="${jsPath}/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="${jsPath}/css/exam/dark-transparent/expandWindow.css" rel="stylesheet" />
	<link type="text/css" href="${cxt}/css/login.css" rel="stylesheet" />
	<title>个人中心--${system_name}</title>   
</head>
<body>  
<div class="passwordContainer">
    <c:if test="${not empty error}">
    	${error}
    </c:if>
    <c:if test="${empty error}">
    <div class="header">
    	<div class="login"><span></span><a href="${cxt}/login.jsp">登录</a></div>
    </div>
    <div class="passwordcon">
    	<div class="p-main">
    		<div class="h_0">重设密码</div>
    		<form action="#" method="get" name="userForm" id="userForm" onsubmit="return resetPassword(this)">
    		<div class="info mt10">
            	<div class="textfield mb15">
            	<span class="pw"></span><input id="newPassword" style="width:334px;" type="password" name="newPassword" placeholder="请输入新密码" class="easyui-validatebox inp" data-options="required:true,validType:'length[1,30]'"/>
            	</div>
            	<div class="textfield mb15">
            	<span class="pw"></span><input id="aliases" style="width:334px;" type="password" placeholder="请重新输入密码" class="easyui-validatebox inp" data-options="required:true,validType:'length[1,30]'"/>
            	</div>
            	<div class="tjbtnbox">
            		<button class="login-btn " type="submit">提   交</button>
				</div>
    		</div>
    		<input name="userId" value="${userId}" type="hidden" />
    		<input name="uuid" value="${uuid}" type="hidden" />
    		</form>
    	</div>
    </div>
    </c:if>
</div>
<%@ include file="/include/footer.jsp"%>  
 
<script type="text/javascript">  
	//提交表单的方法
	function resetPassword(form1){
		//提交表单
		if($('#userForm').form('validate')){
			if($("#aliases").val()!=$("#newPassword").val()){
				showMsg("error","两次输入的密码不一致");
				return false;
			}
			var params=jQuery('#userForm').serialize();
			jQuery.post("${cxt}/userPasswordProblem/ajax/resetPassword.action",params,function call(data){
				if(data.status){
					window.location="${cxt}/login.jsp";
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","密码修改失败！");
					}
				}
			},'json');
		}	
		return false;
	}
</script>
</body>
</html>