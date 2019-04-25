<%@page import="org.csr.common.user.constant.UserRoleType"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	request.setAttribute("GENERAL", UserRoleType.GENERAL);
%>
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
<form action="#" method="get" name="userForm" id="userForm" onsubmit="return updateUser(this);">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:80px;" ><label>用户名</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="loginName" name="loginName"  data-options="required:true,validType:'unique[\'${cxt}/mobile/usercontacts/ajax/findLoginName.action\',\'loginName\',\'用户名称已存在\']'"  
	                maxlength="128" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>姓名</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="name" maxlength="64" msg="名" data-options="required:true" name="name"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>组织机构</label></th>
			<td class="e-form-td">
				<div class="m-expand-tree drop-down-tree">
				<span>${agenciesName }</span>
				<input type="hidden" name="agencies" value="${agenciesId}" />
				</div>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>性别</label></th>
			<td class="e-form-td">
				<div class="expand-dropdown-select" style="display:inline;">
				<core:select className="easyui-combobox"  id="gender" name="gender" dictType="gender" style="width:200px;height:25px;" data-options="required:true,panelHeight:'95px'" />
				</div>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>手机号</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="mobile" validtype="mobile" invalidMessage="手机格式不正确" name="mobile" data-options=""/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>电子邮件</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="email" maxlength="128" validtype="email" invalidMessage="邮箱格式不正确" name="email" data-options="regChars:[]"/>
			</td>
		</tr>
	</table>
</div>
   <div class="e-tab-btnbox">
	<input type="hidden" value="${roleType}" name="userRoleType"/>
	<button class="btn mr25" type="button" onclick="updateUser();">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<script  type="text/javascript">
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		/* parentObject.height=$(this).height()-$(".e-tab-btnbox").height()-100; */
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
			/* parentObject.height=$(this).height()-$(".e-tab-btnbox").height()-100; */
		});
	});
 	function submit(){
 		$("#userForm").submit();
 	}
	var status = '${status}';
	//提交表单的方法
	function updateUser(){
		//提交表单
		try{
			if($('#userForm').form('validate')){
				var params=jQuery('#userForm').serialize();
				jQuery.post("${cxt}/mobile/usercontacts/ajax/add.action",params,function call(data){
					if(data.status){
						showMsg("info","内部用户添加成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","内部用户添加失败!");
						}
					}
				},'json');
			}
		}catch (e) {
			alert(e);
		}
		return false;
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>
