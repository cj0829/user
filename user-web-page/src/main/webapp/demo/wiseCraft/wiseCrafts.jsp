<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
 	<title>添加黑名单--${system_name}</title> 
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="${jsPath}/js/jquery.min.js" type="text/javascript"></script>

  </head>
  
  <body>
  
   <form action="${cxt}/wisecraftsman/app/ajax/bindproject.action" method="post">
     <input type="text" name="userId" value="101"/>
     <input type="text" name="projectId" value="3"/>
      <input type="submit" value="绑定人工到项目"/>
   </form>
   
     <form action="${cxt}/wisecraftsman/app/ajax/unBindproject.action" method="post">
     <input type="text" name="userId" value="102"/>
     <input type="text" name="projectId" value=""/>
      <input type="submit" value="解绑人工到项目"/>
   </form>
   
   <form action="${cxt}/wisecraftsman/app/ajax/unBindproject.action" method="post">
     <input type="text" name="userId" value="101"/>
     <input type="text" name="projectId" value=""/>
      <input type="submit" value="解绑"/>
   </form>
   
    <form action="${cxt}/wisecraftsman/app/ajax/addProject.action" method="post">
     <input type="text" name="projectId" value="23804"/>
     <input type="text" name="projectName" value="5"/>
      <input type="submit" value="ADD项目"/>
   </form>
   
    <form action="${cxt}/wisecraftsman/app/ajax/updateproject.action" method="post">
     <input type="text" name="projectId" value="101"/>
     <input type="text" name="projectName" value="23803"/>
      <input type="submit" value="UPDATE修改"/>
   </form>
   
    <form action="${cxt}/wisecraftsman/app/ajax/deleteproject.action" method="post">
     <input type="text" name="userId" value="102"/>
     <input type="text" name="projectId" value="23803"/>
     
      <input type="submit" value="DELETE删除"/>
   </form>
</body>
</html>
