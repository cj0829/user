<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<link type="text/css" href="${cxt}/css/user/dark-transparent/userSpecialFunctionPoint.css" rel="stylesheet" />
 	<title>新建${userTitle}--${system_name}</title>
</head>
<body>
	<!--内容开始-->
	<c:set var="checkbox" value="true" scope="request"/>
	<form action="#" method="post" name="roleFunctionPointForm" id="roleFunctionPointForm">
	<div class="e-tabbox main-expand-form">
	<div class="main-ordinary-tab">
	<table width="100%"><c:forEach items="${functionPointList }" var="topfuns"><tr id="roleList" sort="false" >
		<th width="165px" >${topfuns.name }模块</th>
		<th>功能点</th>
	</tr><c:forEach items="${topfuns.children }" var="functionPoint">
	<tr><td style="text-align:center" class="td_1">${functionPoint.name }</td>
	<td id="code_${functionPoint.id }"><c:forEach items="${functionPoint.children }" var="subPoint">
<div class="f-left">
	<span>${subPoint.name }</span>
</div><c:if test="${fn:length(subPoint.children) > 0}"><c:set var="children" value="${subPoint.children}" scope="request" /><jsp:include page="/WEB-INF/jsp/common/role/recursion.jsp"/></c:if></c:forEach></td></tr></c:forEach> 
	</c:forEach>
	</table>
	</div>
	<input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
	</div>
	</form>
<script type="text/javascript">
window.onload=function(){
	$(".e-tabbox").height($(this).height()-70);
};

jQuery(document).ready(function(){
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height());
	});
});
</script>
</body>
</html>