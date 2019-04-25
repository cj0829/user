<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改会议管理--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="attendMeetingForm" id="attendMeetingForm" onsubmit="return updateAttendMeeting(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">会议名称</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="subject" name="subject" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议主持</th>
						<td class="e-form-td">
						<user:usergrid id="managerUserId" name="managerUserId" dataOptions="required:true" value="${attendMeeting.managerUserId}" authority="ture"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">总社区</th>
						<td class="e-form-td">
						<label><input type="checkbox" name="generalMeeting" value="<%=YesorNo.YES%>"/>选择全部社区</label>（下发总会议，选择总社区，自选社区无效）
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">自选社区</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="required:true" value="${attendMeeting.agenciesId}"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议时间</th>
						<td class="e-form-td">
						<input class="easyui-datetimebox" id="meetingtime" name="meetingtime" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="meetingadd" name="meetingadd" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议开前</th>
						<td class="e-form-td">
						<input class="easyui-numberspinner" id="meetingStartTime" name="meetingStartTime" data-options="required:true" style="width:80px;;height: 25px;" type="text"/>
						分钟，签到有效
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">时长</th>
						<td class="e-form-td">
						<input class="easyui-numberspinner" id="duration" name="duration" data-options="required:true" style="width:80px;height: 25px;" type="text"/>
						分钟
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议结束后</th>
						<td class="e-form-td">
						<input class="easyui-numberspinner" id="meetingEndTime" name="meetingEndTime" data-options="required:true" style="width:80px;height: 25px;" type="text"/>
						分钟，签出有效
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	    <!--tab结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${attendMeeting.id}"/>
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
 		$("#attendMeetingForm").form("load",{
			subject:"${attendMeeting.subject}",
			meetingtime:"${attendMeeting.meetingtime}",
			generalMeeting:"${attendMeeting.generalMeeting}",
			meetingadd:"${attendMeeting.meetingadd}",
			meetingStatus:"${attendMeeting.meetingStatus}",
			duration:"${attendMeeting.duration}",
			meetingEndTime:"${attendMeeting.meetingEndTime}",
			meetingStartTime:"${attendMeeting.meetingStartTime}"
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateAttendMeeting(){
		try{
			if($("#attendMeetingForm").form("validate")){
				WaitingBar.getWaitingbar("updateattendMeeting","数据修改中，请等待...","${jsPath}").show();
				var params=jQuery("#attendMeetingForm").serialize();
				jQuery.post("${cxt}/attendance/attendMeeting/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updateattendMeeting").hide();
					if(data.status){
						showMsg("info","编辑会议管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑会议管理失败!");
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
