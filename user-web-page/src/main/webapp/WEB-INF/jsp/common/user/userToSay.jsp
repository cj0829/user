<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>编辑${userTitle}--${system_name}</title>  
</head>
<body>
	<form action="#" method="get" name="userForm" id="userForm" onsubmit="">
		<div class="e-tabbox main-expand-form">
			<textarea id="words" name="words" style="width:350px;height:150px;" cols="40" rows="5" onchange="updateCounter(140,'words','descriptionsShow');" onclick="updateCounter(140,'words','wordsShow');" onkeydown="updateCounter(140,'words','wordsShow');" onkeyup="updateCounter(140,'words','wordsShow');" ></textarea>
			<p style="color:#fff;">字数限制:140</p>
			<p id="wordsShow" style="color:#fff;"></p>
		</div>
	</form>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" onclick="toSay();">发表</button>
		<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
	</div>
<script type="text/javascript">
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
	function toSay(){
		var userInfo={};
		userInfo.words=$("#words").val();
		$.post("${cxt}/examUserInfo/ajax/update.action",{"words":$("#words").val(),"isWords":true},function call(data){
			if(data.status){
				cancelWindow();
				top.window.location="${cxt}/sysUser/preHome.action";
			}
		},'json');
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>
