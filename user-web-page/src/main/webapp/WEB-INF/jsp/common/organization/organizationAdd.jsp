<%@page import="org.csr.core.constant.YesorNo"%>
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
<form action="#" method="get" name="organizationForm" id="organizationForm" onsubmit="return saveOrganization(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width="100%">
		<tr>
			<th class="e-form-th" style="width:92px;"><label>父域</label></th>
			<td class="e-form-td">
				<span>${empty parentName?'global':parentName}</span>
           		<input type="hidden" id="parentId"  name="parentId" value="${parentId}"/> 
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>域名称</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" id="name" name="name"  data-options="required:true,validType:'unique[\'${cxt}/organization/ajax/findHasOrganizationName.action?parentId=${parentId}\',\'name\',\'域名已重复\']'"  msg="机构名称" maxlength="128" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>域管理员</label></th>
			<td class="e-form-td">
				<user:usergrid allRoot="true" name="adminUserId" id="adminUserId"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>别名（英文）</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" id="aliases" name="aliases" data-options="required:true,validType:['english','unique[\'${cxt}/organization/ajax/checkOrganizationAliases.action\',\'aliases\',\'别名（英文）已重复\']']" msg="别名（英文）" maxlength="0" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		
	</table>
</div>
<div class="e-tab-btnbox">
	<input type="hidden" name="organizationStatus" id="organizationStatus" value="<%=YesorNo.NO%>"/>
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
	function saveOrganization(){
		if($("#organizationForm").form("validate")){ 
			var params=jQuery('#organizationForm').serialize();
			jQuery.post("${cxt}/organization/ajax/add.action",params,function call(data){
				if(data.status){
					showMsg("info","添加域成功");
					top.callbackPage(data.data.orga,data.data.parentId);
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","添加域失败!");
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
