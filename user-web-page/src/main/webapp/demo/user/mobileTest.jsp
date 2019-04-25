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
  
  <form action="${cxt}/general/app/ajax/downloadUser.action" method="post">
  		机构<input name="agenciesId" value="5">
  		已存在用户<input name="userList" value="1006">
 		下载类型<input name="inquiryMode" value="1">
  		是否下机构下的全部<input name="findChildren" value="2">
  		<button type="submit">下载通信录</button>
  </form>
   
   <form action="${cxt}/wisecraftsman/app/ajax/addCollectFace.action" method="post" enctype="multipart/form-data">
  		用户id<input name="userId" value="5">
  		用户名<input name="userName" value="1006">
 		图片位置<input name="index" value="1">
  		图片<input name="avatar" type="file">
  		<button type="submit">上传人脸</button>
  </form>
  
  <form action="${cxt}/wisecraftsman/app/ajax/updateCollectFace.action" method="post" enctype="multipart/form-data">
  		用户id<input name="userId" value="5">
  		用户名<input name="userName" value="1006">
 		图片位置<input name="index" value="1">
  		图片<input name="avatar" type="file">
  		<button type="submit">修改人脸</button>
  </form>
  
  <form action="${cxt}/wisecraftsman/app/ajax/del.action" method="post">
  		用户id<input name="userId" value="5">
  		<button type="submit">删除人脸</button>
  </form>
  =======================================================
    <form action="${cxt}/general/app/ajax/comparisonOneVSN.action" method="post" enctype="multipart/form-data">
  		图片<input name="avatar" type="file">
  		<button type="submit">上传人脸</button>
  </form>
  
    <form action="${cxt}/general/app/ajax/comparison.action" method="post" enctype="multipart/form-data">
  		图片<input name="avatar" type="file">
  		<button type="submit">上传人脸</button>
  </form>
   
  </body>
</html>
