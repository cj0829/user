<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@include file="../common/common.jsp"%>
	<title>用户管理--${system_name}</title>  
  
</head> --%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<title>安全角色--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%>
<!-- start 内容 -->
<div class="content-wrap ml10">
	<!-- start adv -->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!-- end adv -->
	<!-- start Navigation-->
	<div class="navigation-bar mb15">
		<ul class="navigation-menu">
			<li><a href="javascript:;">首页</a></li>
			<li><a href="javascript:;">系统管理</a></li>
			<li class="active"><a href="javascript:;">用户管理</a></li>
		</ul>
	</div>
	<!-- end Navigation-->
	<!-- start 主要内容区域 -->
	<table class="e-main-con-box" width=100% >
		<tr>
			<td class="e-main-con-left">
				<!--start left -->
				<div class="e-tabchange-wrap">
					<div class="e-tabchange-bar">
						<a class="title" href="${cxt}/sysUser/preList.action">用户列表</a>
						<a class="curr infor-box" href="javascript:;">用户导入<span id="totaldata_id" style="display: none" class="information" title="有新消息"></span></a>
					</div>
					<div class="e-tabchange-bd">
						<!-- start 搜索 -->
						<div class="e-search-wrap mb10">
						<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">上传时间:</label><div class="expand-date" style="display:inline;"><input id="upLoadDate" type="text" class="easyui-datebox" style="width:155px; height:24px;" data-options="editable:false"/></div></li>
									</ul>
									</td>
									<td class="e-search-btn-box" style="width:92px;">
										<button class="btn" type="submit" >搜索</button>
									</td>
								</tr>
							</table>
						</form>
						</div>
						<!-- end 搜索 -->
						<div class="main-tableContainer cl-margin">
							<table id="datagridList" > </table>
						</div>
					</div>
				</div>
				<!--end left -->
			</td>
		</tr>
	</table>
	<!-- end 主要内容区域 -->
</div>
<!-- end 内容 -->
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->

<script  type="text/javascript">
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			url:"${cxt}/userImportFile/ajax/listByCreated.action",
			collapsible:true,
			showfolder:true,
			fitColumns:true,
			border:false,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			sortName:"userUnPassTotal",
			sortOrder:"desc",
			queryParams:function(){
				try{
					return {
						"auto":true,//自动拼接
						"upLoadDate!date":">=:$"+$("#upLoadDate").datebox("getValue")
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{title:'文件名',field:'originalFileName',width:150},
				{title:'组织机构',field:'agenciesName',width:150},
				{title:'用户总数',field:'userTotal',width:80},
				{title:'待批数',field:'userEffectedTotal',width:80,formatter:function(value,rec,index){
					return rec.userEffectedTotal;
				}},
				{title:'通过数',field:'userPassTotal',width:80},
				{title:'未通过数',field:'userUnPassTotal',sortable:true,width:80,formatter:function(value,rec,index){
					if(rec.userUnPassTotal==0){
						return rec.userUnPassTotal;
					}else{
						return "<a href=javascript:userUnPassTotal("+rec.id+");>"+rec.userUnPassTotal+"</a>";
					};
				}},
				{title:'审核结果',field:'auditStatus',width:80,formatter:function(value,rec,index){
					if(rec.userTotal==rec.userPassTotal){
						return "已完成";
					}else if(rec.userTotal>rec.userPassTotal){
						return "未完成";
					}else{
						return "待批";
					}
				}},
				{title:'上传时间',field:'upLoadDate',width:150}
			]],
			pagination:true
		});
		initRejectNum();
	});	
	//驳回的数量
	function initRejectNum(){
		jQuery.post("${cxt}/userImportFile/ajax/countByCreated.action", function call(data){
			if(data.status){
				$("#totaldata_id").text(data.message);
				if(parseInt(data.message)>0){
					$("#totaldata_id").show();
				}
			}
		},'json'); 
	}
	//查询用户通过数
	function userPassTotal(id){
		$('#winDetail').window({
			title:"用户通过数",
		    width:800,
		    height:600,
		    iframe:"${cxt}/userImportFile/preUserPassTotal.action?id="+id,
		    modal:true
		});
	}
	//查询用户未通过数
	function userUnPassTotal(id){
		$('#winDetail').window({
			title:"用户未通过数",
		    width:800,
		    height:600,
		    iframe:"${cxt}/userImportFile/preUserUnPassTotal.action?id="+id,
		    onIfarmeLoad:function(t){
				var iframe=jQuery('#winDetail').window("getIframe");
				if(iframe){
					iframe.contentWindow.iniWindowParam(jQuery('#winDetail'),callbackPage);
				}
			},
		    modal:true
		});
	}
   //回调方法
	function callbackPage(){    
		reloadData();
		initRejectNum();
	};
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	}
	
</script>
<!--弹出窗口开始-->
<div id="win"></div>
<div id="winDetail"></div>
<div id="winEdit"></div>
</body>
</html>
