<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>参数详细信息--${system_name }</title>   
</head>
<body>
<div class="e-tabbox main-expand-form">
	<div class="e-form-mod">
		<table class="e-form-tab" width=100%>
			<tr>
				<th class="e-form-th" style="width:106px;"><span>*</span><label>参数名：</label></th>
				<td class="e-form-td"><input type="text" class="easyui-textbox" id="name" name="name" value="${parameter.name}" /></td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;"><span>*</span><label>参数值：</label></th>
				<td class="e-form-td"><input type="text" class="easyui-textbox" id="parameterValue" name="parameterValue" data-options="required:true" value="${parameter.parameterValue}" /></td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;"><span>*</span><label>参数类型：</label></th>
				<td class="e-form-td"><core:select dictType="parameterType" value="${parameter.parameterType}" name="parameterType" id="parameterType" /></td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;"><label>备注：</label></th>
				<td class="e-form-td"><input type="text" class="easyui-textbox" id="remark" name="remark" value="${parameter.remark}" /></td>
			</tr>
		</table>
	</div>
</div>
<div class="e-tab-btnbox">
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
</div>
<script  type="text/javascript">  
window.onload=function(){
	$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
};
	jQuery(document).ready(function(){
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	});
});
var parameterType = '${parameterType}';   
function cancelWindow(){
	top.jQuery("#win").window("close");
}
</script>
</body>
</html>