﻿<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加签到管理--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="attendSignForm" id="attendSignForm" onsubmit="return saveAttendSign()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" style="height:310px;">
			<div class="main-formbox">
        		<div class="formtit">基本信息</div>
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">会议考勤</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="ac_attendsign" name="ac_attendsign" data-options="" style="width:200px;height: 25px;" type="text"/>
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
					<tr><th class="e-form-th" style="width:92px;">用户id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userId" name="userId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
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
					<tr><th class="e-form-th" style="width:92px;">会议id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="meetingId" name="meetingId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签到时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="signTime" name="signTime" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">结束打卡时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="endTime" name="endTime" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">打卡记录id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="attendId" name="attendId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">结束记录id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="endId" name="endId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
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
	               	<table width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
			<div class="main-formbox">
	       		<div class="formtit">第二</div>
	           	<div class="e-tabbox">
	               	<table width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
		<!--tab 二  	结束-->
	</div>
	<div style="text-align:center;">
	  	<div class="e-tab-btnbox">
			<button class="btn mr25" type="submit" >保存</button>
			<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
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
	function saveAttendSign(){
		try{
			if($("#attendSignForm").form("validate")){
				WaitingBar.getWaitingbar("addattendSign","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#attendSignForm").serialize();
				jQuery.post("${cxt}/attendance/attendSign/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addattendSign").hide();
					if(data.status){
						showMsg("info","添加签到管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加签到管理失败!");
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
