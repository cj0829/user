
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>用户首选项--${system_name}</title>  
</head>
<body>
<!--内容开始-->
<div class="e-tabbar">
	<a class="t-btn" href="${cxt}/user/preUpdate.action?id=${userId}" ><span class="btntxt">主要</span></a>
	<a class="t-btn" href="${cxt}/user/preUpdateResetPassword.action?userId=${userId}" ><span class="btntxt">密码</span></a>
	<a class="t-btn" href="${cxt}/avatar/userHeaderImg.action?userId=${userId}" >用户头像</a>
	<a class="t-btn curr" href="javascript:;" ><span class="btntxt">首选项</span></a>
	<a class="t-btn" href="${cxt}/userMore/preAdd.action?userId=${userId}" >用户详细信息</a>
</div>
<div class="e-tabbox main-expand-form">
<form action="#" method="get" name="userForm" id="userForm" onsubmit="return updatePreferrences(this)">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:80px;" ><label>默认主页</label></th>
			<td class="e-form-td">
			<div class="expand-dropdown-select" style="display:inline;">
				<input class="easyui-combobox" id="dufHome" value="${user.dufHome}" name="dufHome" style="width:200px;height: 25px;" type="text"
				data-options="defaultEntry:true,url:'${cxt}/user/ajax/findUserDufMeun.action?userId=${userId}',method:'post',valueField:'id',textField:'name',"/>
			</div>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>皮肤</label></th>
			<td class="e-form-td">
			<div class="expand-dropdown-select" style="display:inline;">
			<core:select className="easyui-combobox"  id="skinName" name="skinName" value="${user.skinName}" dictType="skinName" style="width:200px;height: 25px;" />
			</div>
			</td>
		</tr>
	</table>
	<input type="hidden" id="id" name="id" value="${userId}"/>
	</form>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="button" onclick="submit();">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
<!--内容结束-->
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
		});
	});
 	//
 	function submit(){
 		$("#userForm").submit();
 	}
	//提交表单的方法
	function updatePreferrences(form1){
		//提交表单
		 if($('#userForm').form('validate')){
			var params=jQuery('#userForm').serialize();
			jQuery.post("${cxt}/user/ajax/saveUserPreferences.action",params,function call(data){
				if(data.status){
					showMsg("info","用户首选项修改成功");
					top.callbackPage();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","用户首选项修改失败!");
					}
				}
			},'json');
		}	
		return false;
	}
	//当开始日期没有输入的情况下允许添加课时模板
	function checkDateStart(){
		$("#sessionTemplate").butcombo("panel").panel("refreshIframe","${cxt}/user/winUserPasswordProblem.action?userId=${userId}");
		return true;
	};
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>

