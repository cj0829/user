<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改设备管理--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="appcapitalForm" id="appcapitalForm" onsubmit="return updateAppcapital(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">设备imei</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccid" name="iccid" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备状态</th>
						<td class="e-form-td">
					<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="appStatus" dictType="enableBan" name="appStatus"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">社区</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="required:true" value="${agenciesId}"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="address" name="address" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">升级方式</th>
						<td class="e-form-td">
						<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="upgradeType" dictType="upgradeType" name="upgrade"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备类型</th>
						<td class="e-form-td">
						<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="appType" dictType="appType" name="appType"/>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	    <!--tab结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${appcapital.id}"/>
		<input name="agenciesId" value="${appcapital.agenciesId}" type="hidden"/>
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
 		$("#appcapitalForm").form("load",{
			id:"${appcapital.id}",
			iccid:"${appcapital.iccid}",
			appStatus:"${appcapital.appStatus}",
			agenciesId:"${appcapital.agenciesId}",
			address:"${appcapital.address}",
			appType:"${appcapital.appType}",
			appVersion:"${appcapital.appVersion}",
			upgrade:"${appcapital.upgrade}",
			online:"${appcapital.online}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateAppcapital(){
		try{
			if($("#appcapitalForm").form("validate")){
				WaitingBar.getWaitingbar("updateappcapital","数据修改中，请等待...","${jsPath}").show();
				var params=jQuery("#appcapitalForm").serialize();
				jQuery.post("${cxt}/mobile/appcapital/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updateappcapital").hide();
					if(data.status){
						showMsg("info","编辑设备管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑设备管理失败!");
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
