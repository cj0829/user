<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改版本升级--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="updateVersionForm" id="updateVersionForm" onsubmit="return updateUpdateVersion(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="formtit">基本信息</div>
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">设备升级</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="ac_updateversion" name="ac_updateversion" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;"> 升级id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="id" name="id" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">升级下载链接</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="url" name="url" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">升级下载链接</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="newVersion" name="newVersion" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">版本描述</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="content" name="content" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">社区id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesId" name="agenciesId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	    <!--tab结束-->
	    <!--tab 二  	开始-->
		<div  title="第二teb "  style="padding:10px">
			<div class="main-formbox">
	       		<div class="formtit">基本信息</div>
	           	<div class="e-tabbox">
	               	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
			<div class="main-formbox">
	       		<div class="formtit">第二</div>
	           	<div class="e-tabbox">
	               	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
		<!--tab 二  	结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${updateVersion.id}"/>
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
 		$("#updateVersionForm").form("load",{
			ac_updateversion:"${updateVersion.ac_updateversion}",
			id:"${updateVersion.id}",
			url:"${updateVersion.url}",
			newVersion:"${updateVersion.newVersion}",
			content:"${updateVersion.content}",
			agenciesId:"${updateVersion.agenciesId}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateUpdateVersion(){
		try{
			if($("#updateVersionForm").form("validate")){
				WaitingBar.getWaitingbar("updateupdateVersion","数据修改中，请等待...","${jsPath}").show();
				var params=jQuery("#updateVersionForm").serialize();
				jQuery.post("${cxt}/mobile/updateVersion/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updateupdateVersion").hide();
					if(data.status){
						showMsg("info","编辑版本升级成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑版本升级失败!");
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
