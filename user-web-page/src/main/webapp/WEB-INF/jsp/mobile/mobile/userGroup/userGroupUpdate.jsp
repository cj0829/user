﻿<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改用户组中间--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="userGroupForm" id="userGroupForm" onsubmit="return updateUserGroup(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="formtit">基本信息</div>
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">用户组关系</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="ac_usergroup" name="ac_usergroup" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;"> id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="id" name="id" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">用户id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userId" name="userId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">组id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="groupId" name="groupId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">是否必须打卡</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="isMust" name="isMust" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	    <!--tab结束-->
	    <!--tab 二  	开始-->
		<div  title="第二teb "  style="padding:10px">
			<div class="main-formbox">
	       		<div class="formtit">基本信息</div>
	           	<div class="e-tabbox">
	               	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
			<div class="main-formbox">
	       		<div class="formtit">第二</div>
	           	<div class="e-tabbox">
	               	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
		<!--tab 二  	结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${userGroup.id}"/>
		<div style="text-align:center;">
			<div class="e-tab-btnbox">
				<button class="btn mr25" type="submit" >保存</button>
				<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
			</div>		
		</div>
	</form>
<!--内容结束-->
</div>
<script type="text/javascript">
	window.onload=function(){
		//加载数据
 		$("#userGroupForm").form("load",{
			ac_usergroup:"${userGroup.ac_usergroup}",
			id:"${userGroup.id}",
			userId:"${userGroup.userId}",
			groupId:"${userGroup.groupId}",
			isMust:"${userGroup.isMust}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateUserGroup(){
		try{
			if($("#userGroupForm").form("validate")){
				WaitingBar.getWaitingbar("updateuserGroup","数据修改中，请等待...","${jsPath}").show();
				var params=jQuery("#userGroupForm").serialize();
				jQuery.post("${cxt}/mobile/userGroup/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updateuserGroup").hide();
					if(data.status){
						showMsg("info","编辑用户组中间成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑用户组中间失败!");
						}
					}
				},"json");
			}
		} catch (e) {}
		return false;
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
