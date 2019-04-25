<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加设备管理--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="appcapitalForm" id="appcapitalForm" onsubmit="return saveAppcapital()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="设备信息" style="height:310px;">
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">设备imei</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccid" name="iccid" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">社区</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="required:true" value="${agenciesId}"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备状态</th>
						<td class="e-form-td">
					<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="appStatus" dictType="enableBan" name="appStatus"/>
					</td>
					</tr>
					
					<tr><th class="e-form-th" style="width:92px;">设备地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="address" name="address" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					
					<tr><th class="e-form-th" style="width:92px;">升级方式</th>
						<td class="e-form-td">
							<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="upgradeType" dictType="upgradeType" name="upgrade"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备类型</th>
						<td class="e-form-td">
						
						<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="appType" dictType="enableBan" name="appType"/>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
		<!--tab结束-->
	</div>
	<div style="text-align:center;">
	  	<div class="e-tab-btnbox">
			<button class="btn mr25" type="submit" >保存</button>
			<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
			<input name="agenciesId" value="${agencies.id}" type="hidden"/>
		</div>		
	</div>
	</form>
	<!--内容结束-->
</div>
<!--弹出窗口结束-->
<script type="text/javascript">
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
	//提交表单的方法
	function saveAppcapital(){
		try{
			if($("#appcapitalForm").form("validate")){
				WaitingBar.getWaitingbar("addappcapital","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#appcapitalForm").serialize();
				jQuery.post("${cxt}/mobile/appcapital/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addappcapital").hide();
					if(data.status){
						showMsg("info","添加设备管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加设备管理失败!");
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
