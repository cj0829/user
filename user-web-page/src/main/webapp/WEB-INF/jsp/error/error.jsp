<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" /> 
	<title>没有主机</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
</head> 
<body>
	<div style="width:563px;margin: 50px auto 10px;"> 
		 <img src="${jsPath}/css/img/error.jpg" width="563"/> 
		 <div style="color:red;margin:20px 0;">${message }</div> 
		 <div>请与系统管理员联系</div>
	</div>
</body>
</html>