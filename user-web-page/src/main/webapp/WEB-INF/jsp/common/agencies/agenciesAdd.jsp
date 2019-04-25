<%@page import="org.csr.common.user.constant.AgenciesType"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../common/common.jsp"%>
 	<title>添加外部组织--${system_name}</title>  
</head>
<body>
<form action="#" method="get" name="orginternalForm" id="orginternalForm" onsubmit="return saveOrginternal(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:92px;"><label>上级组织名称</label></th>
			<td class="e-form-td">
				<span class="color0">${empty parentName?'根节点':parentName}</span>
				<input type="hidden" id="parentId" name="parentId" value="${parentId}" />
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>组织名称</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="name"
				maxlength="256" msg="组织名称" data-options="required:true" name="name" />
			</td>
		</tr>
		<c:if test="${empty parentId}">
		<tr>
			<th class="e-form-th"><label>域</label></th>
			<td class="e-form-td">
				<div class="m-expand-tree drop-down-tree">
				<input style="width:200px;height: 25px;" class="easyui-combotree"
				type="text" id="orgId" msg="域"
				data-options="url:'${cxt}/organization/ajax/findDropDownTree.action',method:'post',required:true"
				value="${organizationId}" name="org" style="width:170px;" />
				</div>
			</td>
		</tr>
		</c:if>
		
		<tr>
			<th class="e-form-th"><label>地址编码</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="code"
				maxlength="256" data-options="required:true" name="code" />
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>详细地址</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="address1"
				maxlength="256" data-options="required:true" name="address1" />
			</td>
		</tr>
	</table>
</div>

<div class="e-tab-btnbox">
	<c:if test="${not empty parentId}"><input name="org" type="hidden" value="${organizationId}"/></c:if>
	<input name="type" type="hidden" value="<%=AgenciesType.PARENT%>"/>
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
 
 	//
 	function submit(){
 		$("#orginternalForm").submit();
 	}
	var status = '${status}';
	
	//提交表单的方法
	function saveOrginternal(form1){
		if($("#orginternalForm").form("validate")){ 
			var params=jQuery('#orginternalForm').serialize();
			WaitingBar.getWaitingbar("addagencies","数据添加中，请等待...").show();
			jQuery.post("${cxt}/agencies/ajax/add.action",params,function call(data){
				WaitingBar.getWaitingbar("addagencies").hide();
				if(data.status){
					showMsg("info","内部组织添加成功");				
					top.callbackPage(${parentId});
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","内部组织添加失败！");
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
