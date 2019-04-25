<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>安全资源--${system_name}</title>  
</head>
<body>
<form action="#" method="get" name="safeResourceForm" id="safeResourceForm" onsubmit="return save(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:92px;"><label>安全资源名称</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox" id="name" name="name" data-options="required:true,validType:'unique[\'${cxt}/safeResourceCollection/ajax/findName.action?id=${safeResourceCollection.id}\',\'name\',\'安全资源名称已存在\']'" maxlength="128" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>备注</label></th>
			<td class="e-form-td">
				<input type="text" class="easyui-textbox" id="remarks" maxlength="512" name="remarks" style="width:200px;height: 25px;"/>
			</td>
		</tr>
	</table>
</div>
<input type="hidden" id="id" name="id" value="${safeResourceCollection.id}"/>
<input type="hidden" id="isSystem" name="isSystem" value="${safeResourceCollection.isSystem}"/>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<script type="text/javascript"> 
 	window.onload=function(){
 		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		$("#safeResourceForm").form("load",{
	 		name:"${safeResourceCollection.name}",
	 		remarks:"${safeResourceCollection.remarks}",
		});
	};
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
		
	});
	
	//提交表单的方法
	function save(form1){
		if($("#safeResourceForm").form("validate")){ 
			var params=jQuery('#safeResourceForm').serialize();
			jQuery.post("${cxt}/safeResourceCollection/ajax/update.action",params,function call(data){
				if(data.status){
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","编辑安全资源失败!");
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
