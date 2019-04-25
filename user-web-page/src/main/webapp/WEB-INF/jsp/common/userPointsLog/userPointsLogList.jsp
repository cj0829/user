<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
	<title>积分日志管理--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%>
<!-- start 内容 -->
<div class="content-wrap ml10">
	<!-- start adv -->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!-- end adv -->
	<!-- start 主要内容区域 -->
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bar">
			<span class="title">积分日志管理</span>
		</div>
		<div class="e-tabchange-bd">
			<!-- start 搜索 -->
			<!-- <form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:126px;">
						<button class="btn" type="button" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form> -->
			<!-- end 搜索 -->
			<div class="main-tableContainer cl-margin">
				<!-- <div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addUserPointsLog();">创建积分日志管理</button>
				</div> -->
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
			</div>	
		</div>
	</div>
	<!-- end 主要内容区域 -->
</div>
<!-- end 内容 -->
<!--版权开始-->
<%@ include file="../common/common.jsp"%>
<!--版权结束-->

 <script  type="text/javascript"> 
	$(function(){
		$("#datagridList").datagrid({
			nowrap: true,
			url:"${cxt}/userPointsLog/ajax/list.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:"id",
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
				{title:"用户ID",field:"id",width:150},
				{title:"操作描述",field:"operation",width:150},
				{title:"积分1",field:"points1",width:150},
				{title:"积分2",field:"points2",width:150},
				{title:"操作时间",field:"operationDate",dateFormatt:'yy-mm-dd',width:150},
				{title:"备注",field:"remark",width:150},
			]],
			pagination:true
		});
	}); 
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid("reload");
		return false;
	}
    
   //回调方法
	function callbackPage(){    
		reloadData();
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
