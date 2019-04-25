<%@page import="org.csr.common.flow.constant.FormOperType"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
request.setAttribute("SHARE", FormOperType.SHARE);
request.setAttribute("MOVE", FormOperType.MOVE);
request.setAttribute("IMPORT", FormOperType.IMPORT);
request.setAttribute("CHANGECATEGORY", FormOperType.CHANGECATEGORY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords"/>
	<%@ include file="../common/common.jsp"%>
 	<title>签到系统--${system_name}</title>  
</head>
  
  <body>
	<!--start navigation-->
	<%@ include file="../common/header.jsp"%>
	<!--end navigation-->
	<div class="content-wrap ml10">
		<!-- end Navigation-->
		<!-- start 主要内容区域 -->
		<table class="e-main-con-box" width=100% >
			<tr>
				<td class="e-main-con-left">
					<!--start left -->
					<div class="e-tabchange-wrap">
						<div class="e-tabchange-bar pmt-tabbar">
							<a href="${cxt}/user/preList.action"><span class="title">用户管理</span><em class="lock"></em></a>
							<a href="${cxt}/userRegister/preList.action"><span class="title">注册管理</span><em class="lock"></em></a>
							<a href="javascript:;"  class="curr"><span class="title">导入管理</span><em class="lock"></em></a>
						</div>
						<div class="e-tabchange-bd">
							<!-- start 搜索 -->
							<div class="e-search-wrap mb10">
							<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">上传时间:</label><div class="expand-date" style="display:inline;"><input id="upLoadDate" type="text" class="easyui-datebox" style="width:155px; height:26px;" data-options="editable:false"/></div></li>
									</ul>
									</td>
									<td class="e-search-btn-box" style="width:90px;">
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
							<!--start btn -->
							<div class="e-btn-box mt15">
							</div>
							<!--end btn -->
						</div>
					</div>
					<!--end left -->
				</td>
				
			</tr>
		</table>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@ include file="/include/footer.jsp"%>
	<!-- end foot -->
	<div id="win"></div>
	<div id="mm" class="m-expand-menu" style="width:120px;">
	
	</div>
<script type="text/javascript"> 
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/userApprove/ajax/findUserImportApproveList.action',
			collapsible:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			sortName:"userTotal-userPassTotal",
			sortOrder:"desc",
			scrollbarSize:0,
			queryParams:function(){
				try{
					return {
						"auto":true,//自动拼接
						'upLoadDate!date':">=:$"+ $("#upLoadDate").datebox("getValue")
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{title:'文件名',field:'originalFileName',width:100},
				{title:'用户总数',field:'userTotal',width:80},
				{title:'待批数',field:'userTotal-userPassTotal',sortable:true,width:80,formatter:function(value,rec,index){
					if(rec.userEffectedTotal==0){
						return rec.userEffectedTotal;
					}else{
						return "<a href=javascript:userPending("+rec.id+");>"+rec.userEffectedTotal+"</a>";
					};
				}},
				{title:'通过数',field:'userPassTotal',width:80,formatter:function(value,rec,index){
					if(rec.userPassTotal==0){
						return rec.userPassTotal;
					}else{
						return "<a href=javascript:userPassTotal("+rec.id+");>"+rec.userPassTotal+"</a>";
					};
				}},
				{title:'未通过数',field:'userUnPassTotal',width:80,formatter:function(value,rec,index){
					if(rec.userUnPassTotal==0){
						return rec.userUnPassTotal;
					}else{
						return "<a href=javascript:userUnPassTotal("+rec.id+");>"+rec.userUnPassTotal+"</a>";
					};
				}},
				{title:'审核结果',field:'auditStatus',width:100,formatter:function(value,rec,index){
					if(rec.userTotal==rec.userPassTotal){
						return "已完成";
					}else if(rec.userTotal>rec.userPassTotal){
						return "未完成";
					}else{
						return "待批";
					}
				}},
				{title:'上传时间',field:'upLoadDate',width:100},
				{title:'上传者',field:'upLoadUser',width:150,formatter:function(value,rec,indec){
					if(value){
						return value.loginName;
					}else{
						return "";
					}
				}},
				{title:'操作',field:'oper',width:80,formatter:function(value,rec,index){
					if(rec.userEffectedTotal==0){
						return "";
					}else{
						return "<a href=javascript:userPending("+rec.id+");>审批</a>";
					};
				}}
			]],
			pagination:true
		});
	});
	
	//查询用户通过数
	function userPassTotal(id){
		top.$('#winDetail').window({
			title:"用户通过数",
		    width:800,
		    height:500,
		    iframe:"${cxt}/userApprove/preUserPassTotal.action?importFileId="+id,
		    modal:true
		});
	}
	//用户待批
	function userPending(id){
		top.$('#winDetail').window({
			title:"用户审批",
		    width:800,
		    height:500,
		    iframe:"${cxt}/userApprove/preUserPendingList.action?importFileId="+id,
		    modal:true
		});
	}
	//查询用户未通过数
	function userUnPassTotal(id){
		top.$('#winDetail').window({
			title:"用户未通过数",
		    width:800,
		    height:500,
		    iframe:"${cxt}/userApprove/preUserUnPassTotal.action?importFileId="+id,
		    modal:true
		});
	}
	
	function callbackPage(){
		$("#datagridList").datagrid('reload');
	}
	
	function reloadData(){
		try{
			$('#datagridList').datagrid("reload");
		}catch (e) {alert(e);}
		return false;
	}
	
	//下拉菜单
	$(function(){dropdown("#examgl","#examgl .e-menu-navbg");});
	//tab切换
	$(function(){tabsclick("#tab .e-sm-tabchange-bar a","#tab .e-sm-tabchange-bd .e-tree-con");});
</script>
<div id="win"></div>
<div id="winDetail"></div>
<div id="winEdit"></div>
</body>
</html>
