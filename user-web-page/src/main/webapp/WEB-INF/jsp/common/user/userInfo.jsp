<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title></title>  
</head>
<body>
<!--内容开始-->
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:80px;" ><label>用户名</label></th>
			<td class="e-form-td">${user.loginName}</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>姓名</label></th>
			<td class="e-form-td">${user.name }</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>组织</label></th>
			<td class="e-form-td">${agencies }</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>管理人</label></th>
			<td class="e-form-td">${manager }</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>性别</label></th>
			<td class="e-form-td"><core:property dictType="gender" value="${user.gender }"></pmt:property></td>
		</tr>
		<tr>
			<th class="e-form-th"><label>电子邮件</label></th>
			<td class="e-form-td">${user.email }</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>默认主页</label></th>
			<td class="e-form-td">${dufHome }</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>皮肤</label></th>
			<td class="e-form-td">${user.skinName }</td>
		</tr>
	</table>
</div>
<div class="e-tab-btnbox">
	<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
</div>
<!--内容结束-->
<script  type="text/javascript">
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
	};
	function setHeight(){
		parentObject.iframeObject.setAttribute('height', $(this).height()-$(".e-tab-btnbox").height()-100);
	}
    jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
    function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
</script>
</body>
</html>
