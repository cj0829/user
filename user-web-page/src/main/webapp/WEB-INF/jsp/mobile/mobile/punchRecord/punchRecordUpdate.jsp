<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改打卡记录管理--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="punchRecordForm" id="punchRecordForm" onsubmit="return updatePunchRecord(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="formtit">基本信息</div>
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">打卡记录</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="ac_attenddata" name="ac_attenddata" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签到id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="id" name="id" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签到人姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="name" name="name" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签到人id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userid" name="userid" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签到人设备</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccid" name="iccid" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">社区id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesId" name="agenciesId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">目标id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="targetId" name="targetId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">打卡类型</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="attendType" name="attendType" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">打卡數據</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="data" name="data" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">记录类型</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="logType" name="logType" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
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
		<input type="hidden" id="id" name="id" value="${punchRecord.id}"/>
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
 		$("#punchRecordForm").form("load",{
			ac_attenddata:"${punchRecord.ac_attenddata}",
			id:"${punchRecord.id}",
			name:"${punchRecord.name}",
			userid:"${punchRecord.userid}",
			iccid:"${punchRecord.iccid}",
			agenciesId:"${punchRecord.agenciesId}",
			targetId:"${punchRecord.targetId}",
			attendType:"${punchRecord.attendType}",
			data:"${punchRecord.data}",
			logType:"${punchRecord.logType}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updatePunchRecord(){
		try{
			if($("#punchRecordForm").form("validate")){
				WaitingBar.getWaitingbar("updatepunchRecord","数据修改中，请等待...","${jsPath}").show();
				var params=jQuery("#punchRecordForm").serialize();
				jQuery.post("${cxt}/mobile/punchRecord/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updatepunchRecord").hide();
					if(data.status){
						showMsg("info","编辑打卡记录管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑打卡记录管理失败!");
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
