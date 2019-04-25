<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>添加参数--${system_name }</title>  
</head>
<body>
<form action="#" method="post" name="organizationParameterForm" id="organizationParameterForm" onsubmit="return saveOrganizationParameter(this);">
	<div class="e-tabbox main-expand-form">
		<div class="e-form-mod">
			<table class="e-form-tab" width=100%>
				<tr>
					<th class="e-form-th" style="width:106px;"><span>*</span><label>机构参数名：</label></th>
					<td class="e-form-td"><input type="text" class="easyui-textbox" id="name" name="name" data-options="required:true,validType:'unique[\'${cxt}/parameter/ajax/findName.action\',\'name\',\'名称必须唯一\']'" />
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><span>*</span><label>机构参数值：</label></th>
					<td class="e-form-td"><input type="text" class="easyui-textbox" id="parameterValue" name="parameterValue" data-options="required:true,regChars:[]" /></td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><span>*</span><label>参数类型：</label></th>
					<td class="e-form-td">用户参数</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><label>备注：</label></th>
					<td class="e-form-td"><input type="text" class="easyui-textbox" id="remark" name="remark" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="submit">提交</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
	</div>
	<input type="hidden" name="parameterType" value="${parameterType}" />
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
//提交表单的方法
function saveOrganizationParameter(form1){
	if($("#organizationParameterForm").form("validate")){
		//提交表单
		var params=jQuery('#organizationParameterForm').serialize();
		jQuery.post("${cxt}/organizationParameter/ajax/add.action",params,function call(result){
			if(result.status){
				top.reloadData();
				cancelWindow();
			}else{
				top.showMsg("error",result.message);
			}	
		},'json');
	}
	return false;
}
		
function cancelWindow(){
	top.jQuery("#win").window("close");
}
</script>
</body>
</html>