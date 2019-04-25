<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改签到管理--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="attendSignForm" id="attendSignForm" onsubmit="return updateAttendSign(this)">
	<div id="easyui-tabs">
	
		会议开始时间：${meeting.meetingtime } 会议结束时间：${ meeting.endtime}
		<!--tab开始-->
		<div title="应到人员名单" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<table id="datagridList1"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
	    <!--tab结束-->
	    <!--tab 二  	开始-->
		<div  title="实到人员名单"  style="padding:10px">
			<div class="main-formbox">
				<table id="datagridList2"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
		<!--tab 二  	结束-->
		 <!--tab 三  	开始-->
		<div  title="迟到人员名单"  style="padding:10px">
			<div class="main-formbox">
				<table id="datagridList3"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
		<!--tab 三  	结束-->
		<!--tab 四 	开始-->
		<div  title="早退人员名单"  style="padding:10px">
			<div class="main-formbox">
				<table id="datagridList4"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
		<!--tab 四  	结束-->
		<!--tab 四 	开始-->
		<div  title="迟到+早退人员名单"  style="padding:10px">
			<div class="main-formbox">
				<table id="datagridList5"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
		<!--tab 四  	结束-->
		<!--tab 四 	开始-->
		<div  title="旷会人员名单"  style="padding:10px">
			<div class="main-formbox">
				<table id="datagridList6"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
		<!--tab 四  	结束-->
	</div>
		
	</form>
<!--内容结束-->
</div>
<script type="text/javascript">
	
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});

		$('#datagridList1').datagrid({nowrap: true,
			url:"${cxt}/attendance/attendSign/ajax/findMeetingUser.action?meetingId=${meetingId}",
			collapsible:true,
			showfolder:true,
			
			border:false,
			fitColumns:true,
			idField:'id',
			
			scrollbarSize:0,
			height:450,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'机构',field:'agenciesName',width:100},
			]],
			pagination:true
		});
		$('#datagridList2').datagrid({nowrap: true,
			url:"${cxt}/attendance/attendSign/ajax/findMeetingReal.action?meetingId=${meetingId}",
			collapsible:true,
			showfolder:true,
			
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			height:450,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'机构',field:'agenciesName',width:100},
			]],
			pagination:true
		});
		$('#datagridList3').datagrid({nowrap: true,
			url:"${cxt}/attendance/attendSign/ajax/findMeetingLate.action?meetingId=${meetingId}",
			collapsible:true,
			showfolder:true,
			
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			height:450,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'签到时间',field:'signTime',width:180},
				{title:'迟到时间(秒)',field:'lateTime',width:180},
				{title:'机构',field:'agenciesName',width:100}
			]],
			pagination:true
		});
		$('#datagridList4').datagrid({nowrap: true,
			url:"${cxt}/attendance/attendSign/ajax/findMeetingEarly.action?meetingId=${meetingId}",
			collapsible:true,
			showfolder:true,
			
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			height:450,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'签出时间',field:'endTime',width:180},
				{title:'早退时间(秒)',field:'earlyTime',width:180},
				{title:'机构',field:'agenciesName',width:100}
			]],
			pagination:true
		});
		$('#datagridList5').datagrid({nowrap: true,
			url:"${cxt}/attendance/attendSign/ajax/findMeetingEarlyAndLate.action?meetingId=${meetingId}",
			collapsible:true,
			showfolder:true,
			
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			height:450,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'迟到时间(分钟)',field:'lateTime',width:180},
				{title:'早退时间(分钟)',field:'earlyTime',width:180},
				{title:'机构',field:'agenciesName',width:100}
			]],
			pagination:true
		});
		$('#datagridList6').datagrid({nowrap: true,
			url:"${cxt}/attendance/attendSign/ajax/findMeetingAbsent.action?meetingId=${meetingId}",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			height:450,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'机构',field:'agenciesName',width:100},
			]],
			pagination:true
		});
	});
		
	//提交表单的方法
	function updateAttendSign(){
		try{
			if($("#attendSignForm").form("validate")){
				WaitingBar.getWaitingbar("updateattendSign","数据修改中，请等待...","${jsPath}").show();
				var params=jQuery("#attendSignForm").serialize();
				jQuery.post("${cxt}/attendance/attendSign/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updateattendSign").hide();
					if(data.status){
						showMsg("info","编辑签到管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑签到管理失败!");
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
