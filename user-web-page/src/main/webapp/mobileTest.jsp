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
  <form action="/checkin/general/app/ajax/login.action" method="post">
  		设备<input name="iccid" value="8603540311168">
  		版本<input name="appVersion">
  		<button type="submit">登录</button>
  </form>
   <form action="/checkin/general/app/ajax/downloadUser.action" method="post">
  		机构<input name="agenciesId" value="1006">
  		已存在用户<input name="userList" value="1006">
  		<button type="submit">下载通信录</button>
  </form>
   <form action="/checkin/general/app/ajax/upgrade.action" method="post">
  		设备<input name="iccid" value="8603540311168">
  		版本<input name="appVersion">
  		升级方式<input name="upgrade">
  		<button type="submit">更新版本</button>
  </form>
   <form action="/checkin/general/app/ajax/updateTime.action" method="post">
  		设备<input name="iccid" value="8603540311168">
  		版本<input name="appVersion">
  		<button type="submit">时间同步</button>
  </form>
  =======================================================
  
   <form action="/checkin/sev/face/ajax/collectFace.action" enctype="multipart/form-data" method="post">
  		用户<input name="userId" value="1007">
  		上传图片位置<input name="index" value="1">
  		人脸<input name="phont" type="file">
  		<button type="submit">上传图片</button>
  </form>
   <form action="/checkin/attendance/app/ajax/downMeeting.action"  method="post">
  		机构id<input name="agenciesId" value="4606">
  		机构id<input name="iccid" value="8603540311168">
  		<button type="submit">下载会议</button>
  </form>
   <form action="/checkin/attendance/app/ajax/createAttend.action" enctype="multipart/form-data" method="post">
  		设备<input name="iccid" value="862037029051678">
  		机构<input name="agenciesId" value="6">
  		会议<input name="targetId">
  		人脸图片<input name="file" type="file">
  		<button type="submit">打卡记录</button>
  </form>
  </body>
</html>
