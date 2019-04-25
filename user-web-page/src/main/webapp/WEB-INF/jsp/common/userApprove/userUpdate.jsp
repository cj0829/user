<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../common/common.jsp"%>
 	<title>编辑${userTitle}--${system_name}</title>  
</head>
<body>
<form action="#" method="get" name="userForm" id="userForm" onsubmit="return updateUser(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:90px;" ><label>用户名</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="loginName" maxlength="64" msg="名" data-options="required:true" name="loginName"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>姓名</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="name" maxlength="64" msg="名" data-options="required:true" name="name"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>角色</label></th>
			<td class="e-form-td">
				${role.name }
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>管理人</label></th>
			<td class="e-form-td">
				${user.managerUserName}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>性别</label></th>
			<td class="e-form-td">
				<div class="expand-dropdown-select" style="display:inline;">
				<core:select className="easyui-combobox"  id="gender" name="gender" dictType="gender" style="width:200px;height:25px;" data-options="required:true,panelHeight:'80px'" />
				</div>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>电子邮件</label></th>
			<td class="e-form-td">
			<input class="easyui-validatebox" style="width:200px;height: 25px;" type="text" id="email" maxlength="128" msg="电子邮件" data-options="validType:'email',regchars:[]" invalidMessage="请填写正确格式的邮箱。" name="email"/>
			</td>
		</tr>
	</table>
</div>
<input type="hidden" id="agenciesId" name="agenciesId" value="${user.agenciesId}"/>
<input type="hidden" id="id" name="id" value="${user.id}"/>
</form>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="button" onclick="submit();">修改提交</button>
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
 	
	var parentDiv;
	var reloadDataFn;
	function iniWindowParam(parentId,fn){
		this.parentDiv=parentId;
		this.reloadDataFn=fn;
	}
   
 	$("#userForm").form("load",{
 		loginName:"${user.loginName}",
 		name:"${user.name}",
 		root:"${user.root}",
 		roleId:"${role.id}",
 		userStatus:"${user.userStatus}",
 		userType:"${userInfo.userType}",
 		gender:"${user.gender}",
 		email:"${user.email}",
	});
 	function submit(){
 		$("#userForm").submit();
 	}
 	
	//提交表单的方法
	function updateUser(form1){
		//提交表单
		 if($('#userForm').form('validate')){
			var params=jQuery('#userForm').serialize();
			jQuery.post("${cxt}/userApprove/ajax/updateUserTask.action",params,function call(data){
				if(data.status){
					showMsg("info","内部用户编辑成功");
					if(reloadDataFn){
						reloadDataFn();
					}
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","内部用户编辑失败!");
					}
				}
			},'json');
		}
		return false;
	}
	
	function cancelWindow(){
		top.jQuery(parentDiv).window("destroy");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>
