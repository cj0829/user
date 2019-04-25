<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" /> 
	<title>404 - 页面不存在</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
</head> 
<body>
	<div style="width:563px;margin: 50px auto 10px;"> 
		 <img src="${jsPath}/css/img/404.jpg" width="563"/> 
		 <div style="color:red;margin:20px 0;">原因：1、你输入的地址不存在。2、地址已搬家。</div> 
	</div>
</body>
</html>