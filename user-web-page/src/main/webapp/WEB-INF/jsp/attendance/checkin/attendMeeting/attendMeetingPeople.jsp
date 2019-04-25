<%@page import="com.tmai.attendance.checkin.constant.ParticipantsType"%>
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
	<form action="#" method="get" name="attendMeetingForm" id="attendMeetingForm">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议主题</th>
						<td class="e-form-td">
							${attendMeeting.subject}
						</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议时间</th>
						<td class="e-form-td">
						${attendMeeting.meetingtime}
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">会议地址</th>
						<td class="e-form-td">
						${attendMeeting.meetingadd}
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">参会人员</th>
						<td class="e-form-td">
						<button class="btn" type="button" onclick="topWin();">添加成员</button>
						<button class="btn" type="button" onclick="updateTable(<%=YesorNo.YES%>);">设置必打卡</button>
						<button class="btn" type="button" onclick="updateTable(<%=YesorNo.NO%>);">设置非必打卡</button>
						<button class="btn" type="button" onclick="deletePeopleAll();">批量删除</button>
						<table id="datagridList" width="100%"  border="0" cellpadding="0" cellspacing="0"></table>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	    <!--tab结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${attendMeeting.id}"/>
	</form>
<!--内容结束-->
</div>
<script type="text/javascript">
	window.onload=function(){
		//加载数据
 		$("#attendMeetingForm").form("load",{
			id:"${attendMeeting.id}",
			subject:"${attendMeeting.subject}",
			meetingtime:"${attendMeeting.meetingtime}",
			meetingadd:"${attendMeeting.meetingadd}",
			agenciesId:"${attendMeeting.agenciesId}"
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
		
		$('#datagridList').datagrid({
			nowrap: true,
			url:"${cxt}/attendance/attendParticipants/ajax/list.action?meetingId=${attendMeeting.id}",
			collapsible:true,
			showfolder:true,
			border:false,
			height:320,
			idField:'id',
			scrollbarSize:0,
			showPageList : false,
			columns:[[
 				{field:'ck',checkbox:true},
 				{title:'名称',field:'name',width:180},
				{title:'类型',field:'participantsType',dictionary:'ParticipantsType',width:100},
				{title:'是否必须打卡',field:'isMust',dictionary:'yesOrNo',width:100},
				{title:"操作",field:"id_1",width:80,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					if(rec.participantsType==<%=ParticipantsType.People%>){
						buttonHtml.push("<a href=\"javascript:deletePeople('"+rec.userId+"');\">删除人员</a>"," ");
					}else{
						buttonHtml.push("<a href=\"javascript:deleteGroup('"+rec.groupId+"');\">删除组</a>"," ");
					}
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}}
			]],
			pagination:true
		});
	});
	
	function topWin(){
		top.$("#popPeople").window({
			title:"选择组成员",
		    width:700,
		    height:568,
		    iframe:"${cxt}/attendance/attendMeeting/popPeople.action?attendMeetingId=${attendMeeting.id}",
		    modal:true
		});
	}
	
	function updateTable(isMust){
 		try{
			if($("#groupForm").form("validate")){
			
				var params=jQuery("#groupForm").serialize();
				var checkedsUser=$("#datagridList").datagrid("getChecked");
				if(!checkedsUser || checkedsUser.length<=0){
					showMsg("info","请您选择用户");
					return false;
				}
				var ids=[];
				var userIds=[];
				for(var i=0;i<checkedsUser.length;i++){
					ids.push(checkedsUser[i].id);
					userIds.push(checkedsUser[i].userId);
				}
				WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
				var param = {isMust:isMust,userIds:userIds.join(","),meetingId:"${attendMeeting.id}"};
				jQuery.post("${cxt}/attendance/attendParticipants/ajax/updateTable.action",param,function call(data){
					WaitingBar.getWaitingbar("addgroup").hide();
					if(data.status){
						showMsg("info","修改成功");
						$("#datagridList").datagrid("clearChecked");
						$("#datagridList").datagrid("reload");
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加组管理失败!");
						}
					}
				},"json");
			}
		} catch (e) {}
		return false;
 	}
	//删除组
	function deleteGroup(groupId){
		showConfirm("确认是否删除组?如果您要删除组。",function(){
 		try{
			var param = {groupId:groupId,meetingId:"${attendMeeting.id}"};
			jQuery.post("${cxt}/attendance/attendParticipants/ajax/deleteGroup.action",param,function call(data){
				WaitingBar.getWaitingbar("addgroup").hide();
				if(data.status){
					showMsg("info","删除成功");
					$("#datagridList").datagrid("clearChecked");
					$("#datagridList").datagrid("reload");
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败!");
					}
				}
			},"json");
		} catch (e) {}
		});
		return false;
 	}
	
	//删除人员
	function deletePeopleAll(){
		showConfirm("确认是否删除参会人员?如果您要删除组。",function(){
 		try{
 			var params=jQuery("#groupForm").serialize();
			var checkedsUser=$("#datagridList").datagrid("getChecked");
			if(!checkedsUser || checkedsUser.length<=0){
				showMsg("info","请您选择用户");
				return false;
			}
			var ids=[];
			var userIds=[];
			for(var i=0;i<checkedsUser.length;i++){
				ids.push(checkedsUser[i].id);
				userIds.push(checkedsUser[i].userId);
			}
			WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
			var param = {userId:userIds.join(","),meetingId:"${attendMeeting.id}"};
			
			jQuery.post("${cxt}/attendance/attendParticipants/ajax/deletePeople.action",param,function call(data){
				WaitingBar.getWaitingbar("addgroup").hide();
				if(data.status){
					showMsg("info","删除参会人员成功");
					$("#datagridList").datagrid("clearChecked");
					$("#datagridList").datagrid("reload");
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除参会人员失败!");
					}
				}
			},"json");
		} catch (e) {}
		});
		return false;
 	}
	
	
	//删除人员
	function deletePeople(userId){
		showConfirm("确认是否删除参会人员?如果您要删除组。",function(){
 		try{
 			WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
			var param = {userId:userId,meetingId:"${attendMeeting.id}"};
			jQuery.post("${cxt}/attendance/attendParticipants/ajax/deletePeople.action",param,function call(data){
				WaitingBar.getWaitingbar("addgroup").hide();
				if(data.status){
					showMsg("info","修改成功");
					$("#datagridList").datagrid("clearChecked");
					$("#datagridList").datagrid("reload");
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","添加组管理失败!");
					}
				}
			},"json");
		} catch (e) {}
		});
		return false;
 	}
	
	function reload(){
		jQuery("#datagridList").datagrid("reload");
	}
	
	function cancelWindow(){
		top.jQuery("#attendMeetingPeople").window("close");
	}
</script>
</body>
</html>
