<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>添加区域--${system_name }</title>  
</head>
<body>  

<form action="#" method="post" name="districtForm" id="districtForm" onsubmit="return saveDistrict(this);">
	<div class="e-tabbox main-expand-form">
		<div class="e-form-mod">
			<table class="e-form-tab" width=100%>
				<tr>
					<th class="e-form-th" style="width:106px;"><span>*</span><label>区域名：</label></th>
					<td class="e-form-td"><input type="text" class="easyui-textbox" id="name" name="name" data-options="required:true,validType:'unique[\'${cxt}/district/ajax/findName.action\',\'name\',\'名称必须唯一\']'" />
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><span>*</span><label>行政区：</label></th>
					<td class="e-form-td">
					<div class="expand-dropdown-select">
					<input type="text" class="easyui-combobox" id="provinceIds" name="provinceIds" data-options="required:true,valueField: 'id',textField: 'name', url: '${cxt}/province/ajax/findAll.action'" />
					</div>
					</td>
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
function saveDistrict(form1){
	if($("#districtForm").form("validate")){
		//提交表单
		var params=jQuery("#districtForm").serialize();
		jQuery.post("${cxt}/district/ajax/add.action",params,function call(result){
			if(result.status){
				top.reloadData();
				cancelWindow();
			}else{
				if(result.message){
					showMsg("error",result.message);
				}else{
					showMsg("error","添加区域失败!");
				}
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