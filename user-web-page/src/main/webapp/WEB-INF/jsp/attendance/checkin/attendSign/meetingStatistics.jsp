<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<title>签到管理--${system_name}</title>  
  
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
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">会议:</label>
						<input id="subject" name="subject" class="main-inp" type="text" style="width:115px; height:25px;"/>
						</li>
						<li class="e-search-td"><label class="mr8 lab" style="width:30px;">社区:</label>
							<user:agenciesgrid id="agenciesId" name="agenciesId"  value="${agenciesId}" style="width:115px; height:27px;"/>
						</li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="submit" >搜索</button>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="button" onclick="exportExcel()">导出</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					
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
			url:"${cxt}/attendance/attendSign/ajax/listMeetingCount.action",
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
					{title:"社区",field:"agenciesName",width:150},
					{title:"会议时间",field:"meetingtime",width:150},
					{title:"会议应到人数",field:"attendanceNum",width:150},
					{title:"实到人数",field:"realNum",width:150},
					{title:"迟到人数",field:"lateNum",width:150},
					{title:"早退人数",field:"leaveEarlyNum",width:150},
					{title:"未签出人数",field:"noAttendOutNum",width:150},
					
					{title:"迟到+早退人数",field:"earlyAndLateNum",width:150},
					{title:"旷会人数",field:"absentNum",width:150},
					{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a href=\"javascript:attendInfo('"+rec.id+"');\">详情</a>"," ");
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
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
				"agenciesId":$("#agenciesId").combogrid("getValue")
		};
		}catch (e) {}
		return {};
	};
	
	function addAttendSign(){
		$("#win").window({
			title:"新建",
			width:800,
			height:600,
			iframe:"${cxt}/attendance/attendSign/preAdd.action",
			modal:true
		});
	}
	
	// 编辑
	function attendInfo(id){
		$("#win").window({
			title:"详情",   
			width:800,
			height:600,
			iframe:"${cxt}/attendance/attendSign/preInfo.action?meetingId="+id,
			modal:true
		});
	}
	// 编辑
	function attendInfo(id){
		$("#win").window({
			title:"详情",   
			width:800,
			height:600,
			iframe:"${cxt}/attendance/attendSign/preInfo.action?meetingId="+id,
			modal:true
		});
	}
	//导入Excel
	function exportExcel(){
		try{
			var rows = $("#datagridList").datagrid("getChecked");
			if(rows.length<=0){
				alert("至少选择一条做为导出的数据!");
				return;
			}
			
			var attendMeetingIds = [];
			for ( var i = 0; i < rows.length; i++) {
				attendMeetingIds.push(rows[i].id);
			}
			download_file("${cxt}/attendance/attendSign/ajax/findMeetingStatisticsToExl.action?attendMeetingIds="+attendMeetingIds);
		}catch (e) {alert(e);}
	}
	//删除全部
	function deleteAttendSigns(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...","${jsPath}").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/attendance/attendSign/ajax/delete.action",param, function call(data){
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
	function deleteAttendSign(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/attendance/attendSign/ajax/delete.action",{"ids":id}, function call(data){
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
</body>
</html>
