<%@page import="com.tmai.attendance.checkin.constant.MeetingStatus"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
request.setAttribute("NOTSTARTED", MeetingStatus.NOTSTARTED);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<title>会议管理--${system_name}</title>  
</head>
<body>
<%@include file="../common/header.jsp"%>
<!--content开始-->
<div class="content-wrap ml20 mr20">
	<!--当前标题开始-->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!--当前标题结束-->
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<!-- 搜索 start  -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:70px;">会议主题:</label><input id="subject" class="main-inp" type="text" style="width:115px; height:21px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:70px;">会议时间:</label><input id="meetingtime" class="easyui-datetimebox" type="text" style="width:155px; height:27px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:30px;">社区:</label>
							<user:agenciesgrid id="agenciesId" name="agenciesId"  value="${agenciesId}" allRoot="all" editable="true" style="width:115px; height:27px;"/>
						</li>
						<li class="e-search-td"><label class="mr8 lab" style="width:50px;">状态:</label>
							<core:select id="meetingStatus" name="meetingStatus" dictType="meetingStatus" className="easyui-combobox" style="width:115px; height:27px;"/>
						</li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
					
						<button class="btn" type="submit" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addAttendMeeting();">新建</button>
					<button class="tabbtn fr mr5" type="button" onclick="deleteAttendMeetings();">删除</button>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0" class="tablemode"></table>
			</div>			
			 <!--table表格结束-->
		</div>
	</div>
</div>
<!--content结束-->
<!--版权开始-->
<%@include file="../common/footer.jsp"%>
<!--版权结束-->

<script type="text/javascript">
	$(function(){
		$("#datagridList").datagrid({
			nowrap: true,
			url:"${cxt}/attendance/attendMeeting/ajax/list.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:"id",
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:"ck",checkbox:true},
				{title:"会议名称",field:"subject",width:150},
				{title:"会议时间",field:"meetingtime",width:150},
				{title:"会议地址",field:"meetingadd",width:150},
				{title:"社区",field:"agenciesName",width:150},
				{title:"状态",field:"meetingStatus",dictionary:"meetingStatus",width:150},
				{title:"签到有效时间",field:"meetingStartTime",width:150},
				{title:"时长",field:"duration",width:150},
				{title:"签出有效时间",field:"meetingEndTime",width:150},
				{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					if(rec.meetingStatus==<%=MeetingStatus.NOTSTARTED%> || rec.meetingStatus==<%=MeetingStatus.CHECKIN%>  || rec.meetingStatus==<%=MeetingStatus.MEETING%> ){
						buttonHtml.push("<a href=\"javascript:editAttendMeeting('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteAttendMeeting('"+rec.id+"');\">删除</a>"," ");
						buttonHtml.push("<a href=\"javascript:addAttendMeetingPeople('"+rec.id+"');\">添加人员</a>"," ");
					}
					if(rec.meetingStatus==<%=MeetingStatus.MEETING%>){
						buttonHtml.push("<a href=\"javascript:closeAttendMeeting('"+rec.id+"');\">结束会议</a>"," ");
					}
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}}
			]],
			pagination:true
		});
	}); 
	//查询方法名称
	function queryParams(){
		try{
			return {
			"auto":true,//自动拼接
			"subject!s":"like:$"+$("#subject").val(),
			"meetingtime":$("#meetingtime").datetimebox("getValue"),
			"agenciesId":$("#agenciesId").combogrid("getValue"),
			"meetingStatus":$("#meetingStatus").combobox("getValue")
		};
		}catch (e) {}
		return {};
	};
	
	function addAttendMeeting(){
		$("#win").window({
			title:"新建",
			width:800,
			height:500,
			iframe:"${cxt}/attendance/attendMeeting/preAdd.action",
			modal:true
		});
	}
	
	// 编辑
	function editAttendMeeting(id){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:500,
			iframe:"${cxt}/attendance/attendMeeting/preUpdate.action?id="+id,
			modal:true
		});
	}
	
	// 添加任意
	function addAttendMeetingPeople(id){
		$("#attendMeetingPeople").window({
			title:"编辑",   
			width:800,
			height:550,
			iframe:"${cxt}/attendance/attendMeeting/attendMeetingPeople.action?id="+id,
			modal:true
		});
	}
	//删除全部
	function deleteAttendMeetings(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...","${jsPath}").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/attendance/attendMeeting/ajax/delete.action",param, function call(data){
			WaitingBar.getWaitingbar("deletes").hide();
				if(data.status){
					showMsg("info","删除成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败!");
					}
				}
			},"json"); 
		}
	}
	
	// 删除
	function deleteAttendMeeting(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/attendance/attendMeeting/ajax/delete.action",{"ids":id}, function call(data){
				WaitingBar.getWaitingbar("delete").hide();
				if(data.status){
					showMsg("info","删除成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败!");
					}
				}
			},"json"); 
		}
	}
	
	
	// 删除
	function closeAttendMeeting(id){
	     if(confirm("确认是否结束会议?")){
			WaitingBar.getWaitingbar("close","结束会议，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/attendance/attendMeeting/ajax/close.action",{"id":id}, function call(data){
				WaitingBar.getWaitingbar("close").hide();
				if(data.status){
					showMsg("info","结束会议成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","结束会议失败!");
					}
				}
			},"json"); 
		}
	}
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid("reload");
		return false;
	}
    
   //回调方法
	function callbackPage(){
		reloadData();
	}
</script>
<!--弹出窗口开始-->
<div id="win"></div>
<div id="attendMeetingPeople"></div>
<div id="popPeople"></div>
<div id="popGroup"></div>
</body>
</html>
