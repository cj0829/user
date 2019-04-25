<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>添加安全角色--${system_name}</title>  
</head>
<body>
<form action="#" method="get" name="organizationForm" id="organizationForm" onsubmit="return saveOrganization(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:92px;"><label>安全角色名称</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" id="name" name="name"  data-options="required:true,validType:'unique[\'${cxt}/role/ajax/findAddRoleName.action\',\'name\',\'安全角色名称已存在\']'"  msg="安全角色名称" maxlength="128" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>备注</label></th>
			<td class="e-form-td">
				<input type="text" class="easyui-textbox" id="remark" max="512" maxlength="512"  msg="备注" msgId="remarkMsg" name="remark" style="width:200px;height: 25px;"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>域</label></th>
			<td class="e-form-td">
				<div class="m-expand-tree drop-down-tree">
				<input style="width:200px;height: 25px;" class="easyui-combotree" type="text" id="orgId" name="orgId" msg="主安全角色" data-options="url:'${cxt}/organization/ajax/findDropDownTree.action',method:'post',required:true" value="${organizationId}" style="width:200px;height: 25px;"/>
				</div>
			</td>
		</tr>
	</table>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<script type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
	});
 	function submit(){
 		$("#organizationForm").submit();
 	}
	var status = '${status}';
	
	//提交表单的方法
	function saveOrganization(form1){
		if($("#organizationForm").form("validate")){ 
			var params=jQuery('#organizationForm').serialize();
			jQuery.post("${cxt}/role/ajax/add.action",params,function call(data){
				if(data.status){
					showMsg("info","添加安全角色成功");
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","添加安全角色失败!");
					}
				}
			},"json");
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
