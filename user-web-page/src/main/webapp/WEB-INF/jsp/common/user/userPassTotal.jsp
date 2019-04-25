<%@page import="org.csr.common.user.constant.ImportApprovalStatus"%>
<%-- <%@page import="org.csr.exam.constant.ImportApprovalStatus"%> --%>
<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
 <%-- 	<%@include file="../common/common.jsp"%> --%>
  	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>试题通过数--${system_name}</title>  
  
</head>
<body>
	
	<div class="e-tabbox">
		<div class="form-tab-wrap">
			<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<!-- <button class="btn mr25" type="button" onclick="save();">保存</button> -->
		<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
	</div>
<script  type="text/javascript">
var waitingbar;
	$(function(){
		 $(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		 jQuery(document).ready(function(){
				$(window).unbind('#winBody').bind('resize.winBody', function(){
					$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
				});
			});
		 
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/sysUser/ajax/findUserPassTotalByFileId.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:function(){
				var id = ${id};
				try{
					return {
						"auto":true,//自动拼接
						"fileId!l":"=:$"+id,
						"userStatus!i":"=:$<%=ImportApprovalStatus.PASS%>",
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户名',field:'loginName',width:150,formatter:function(value,rec,index){
					return rec.user.loginName;
				}},
				{title:'用户编码',field:'type',width:100,formatter:function(value,rec,index){
					return rec.user.loginName;
				}},
				{title:'名称',field:'name',width:100,formatter:function(value,rec,index){
					return rec.user.name;
				}},
				{title:'主域',field:'organizationName',width:100,formatter:function(value,rec,index){
					return rec.user.organizationName;
				}},
				{title:'组织',field:'agenciesName',width:110,formatter:function(value,rec,index){
					return rec.user.agenciesName;
				}},
				{title:'操作',field:'id_1',width:110,formatter:function(value,rec,index){
					return "<a href=\"javascript:viewDetail('"+rec.user.id+"',"+rec.user.loginName+");\" >查看详细</a>";
				}}
			]],
			pagination:true,
		});
	});
	
	//查看详细
	function edit(userId,loginName){
		top.$('#winEdit').window({
			title:loginName+":详细信息",
		    width:800,
		    height:600,
		    iframe:"${cxt}/sysUser/preWinUpdate.action?id="+userId+"&isDetail=<%=YesorNo.YES%>",
		    modal:true
		});
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	}

   //回调方法
	function callbackPage(){    
		reloadData();
	};
	function cancelWindow(){
		top.jQuery("#winDetail").window("close");
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
