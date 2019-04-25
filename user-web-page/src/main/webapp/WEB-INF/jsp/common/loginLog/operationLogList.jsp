<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
  	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>操作日志管理--${system_name}</title>  
  
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
			url:'${cxt}/loginLog/ajax/listOperationLog.action?loginLogId=${loginLogId}',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:function(){
				try{
					return {
						"auto":true,//自动拼接
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{field:'ck',checkbox:true},
				{title:'操作名',field:'operation',width:120},
				{title:'操作时间',field:'operationTimeString',width:120},
				{title:'操作类型',field:'operationLogType',dictionary:'operationLogType',width:140},
				{title:'功能点名称',field:'functionPointCodeName',width:140},
				{title:'功能点编码',field:'functionPointCode',width:140},
			]],
			pagination:true
		});
	}); 
	
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
		top.jQuery("#win").window("close");
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
