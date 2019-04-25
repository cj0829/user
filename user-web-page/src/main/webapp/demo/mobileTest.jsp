<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <form action="${cxt}/mobile/punch/ajax/recordPunch.action" enctype="multipart/form-data" method="post">
  		设备<input name="iccid" value="862037029051678">
  		机构<input name="agenciesId" value="6">
  		会议<input name="targetId">
  		人脸图片<input name="file" type="file">
  		<button type="submit">全部消息测试</button>
  </form>
  ----------------------奥森打卡-----------------------
   <form action="${cxt}/mobile/ampunch/ajax/testCreatePunch.action" enctype="multipart/form-data" method="post">
  		设备<input name="iccid" value="862037029051678">
  		机构<input name="agenciesId" value="6">
  		会议<input name="targetId">
  		打卡方式（5.本地校验）<input name="punchType" value="5">
  		用户<input name="userId" value="64224">
  		用户比分<input name="score" value="86">
  		人脸图片<input name="image" type="file">
  		人脸图片<input name="panorama" type="file">
  		<button type="submit">全部消息测试</button>
  </form>
  </body>
</html>
