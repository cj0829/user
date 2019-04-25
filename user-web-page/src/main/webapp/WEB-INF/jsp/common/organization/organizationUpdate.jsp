<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>添加域--${system_name}</title>  
</head>
<body>
<form action="#" method="get" name="organizationForm" id="organizationForm">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:92px;"><label>父域</label></th>
			<td class="e-form-td">
				<div class="m-expand-tree drop-down-tree">
				<input style="width:200px;height: 25px;"  class="easyui-combotree" type="text" id="parentId" name="parentId" msg="主安全角色" data-options="url:'${cxt}/organization/ajax/treeList.action',method:'post',required:true"  style="width:200px;height: 25px;"/>
				</div>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>域名称</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" id="name" name="name" data-options="required:true,validType:'unique[\'${cxt}/organization/ajax/findHasUpdateOrganizationName.action?parentId=${parentId}&id=${orga.id}\',\'name\',\'域名称已重复\']'" msg="域名称" maxlength="0" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>域管理员</label></th>
			<td class="e-form-td">
				<user:usergrid allRoot="true" value="${orga.adminUserId}" id="adminUserId" name="adminUserId"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>别名（英文）</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" id="aliases" name="aliases" data-options="required:true,validType:['english','unique[\'${cxt}/organization/ajax/checkOrganizationAliases.action?id=${orga.id}\',\'aliases\',\'别名（英文）已重复\']']" msg="别名（英文）" maxlength="0" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		
	</table>
</div>
<input type="hidden" id="id" name="id" value="${orga.id}"/>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="button" onclick="updateOrganization()">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
	});
		//加载数据
 	$("#organizationForm").form("load",{
 		parentId:"${parentId}",
 		name:"${orga.name}",
 		aliases:"${orga.aliases}",
 		adminUserId:"${orga.adminUserId}",
	});
	//提交表单的方法
	function updateOrganization(form1){
		//提交表单
		 if($('#organizationForm').form('validate')){ 
			var params=jQuery('#organizationForm').serialize();
			jQuery.post("${cxt}/organization/ajax/update.action",params,function call(data){
				if(data.status){
					showMsg("info","编辑域成功");
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","编辑域失败!");
					}
				}
			},'json');
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
