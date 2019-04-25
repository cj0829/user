<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
  	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>用户通过数--${system_name}</title>  
  
</head>
<body>
	
	<div class="e-tabbox">
		<div class="form-tab-wrap">
			<table id="datagridList" > </table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<!-- <button class="btn mr25" type="button" onclick="save();">保存</button> -->
		<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
	</div>
<script  type="text/javascript">
var waitingbar;
	$(function(){
		 $(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
		 jQuery(document).ready(function(){
				$(window).unbind('#winBody').bind('resize.winBody', function(){
					$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
			});
		});
		 
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/userApprove/ajax/findUserPassTotal.action?importFileId=${importFileId}',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{title:'用户编码',field:'id',width:100},
				{title:'用户名',field:'loginName',width:100},
				{title:'名称',field:'name',width:100},
				{title:'性别',field:'gender',dictionary:"gender",width:100},
				{title:'email',field:'email',width:100},
				{title:'组织',field:'agenciesName',width:100}
			]],
			pagination:true
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
