<%@page import="org.csr.common.flow.constant.FormOperType"%>
<%@page import="org.csr.core.constant.YesorNo"%>
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
	<div class="content-wrap ml10 ml20 mr20">
		<!-- start adv -->
		<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}">
		</div>
		<!-- end adv -->
		<!-- start Navigation-->
		<div class="navigation-bar mb15">
			<ul class="navigation-menu">
				<li><a href="javascript:;">首页</a></li>
				<li ><a href="javascript:;">系统管理</a></li>
				<li class="active"><a href="javascript:;">用户审核</a></li>

			</ul>
		</div>
		<!-- end Navigation-->
		<!-- start 主要内容区域 -->
		<table class="e-main-con-box" width=100% >
			<tr>
				<td class="e-main-con-left">
					<!--start left -->
					<div class="e-tabchange-wrap">
						<!--<div class="e-tabchange-bar">
							<a class="curr" href="javascript:;">用户审批列表</a>
						</div>-->
						<div class="e-tabchange-bd">
							<!-- start 搜索 -->
							<div class="e-search-wrap mb10">
							<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<%--
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">审批任务:</label>
											<div class="expand-dropdown-select" style="display:inline;">
											<select class="easyui-combobox" id="taskNodeId" style="width:155px; height:26px;">
												<option value="">-请选择-</option><c:forEach items="${taskNodeList}" var="taskNode">
												<option value="${taskNode.id}">${taskNode.name}</option>
											</c:forEach>
											</select>
											</div>
										</li>
										 --%>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">操作类型:</label>
											<div class="expand-dropdown-select" style="display:inline;">
											<select class="easyui-combobox" id="formOperType" dicttype="formOperType" style="width:155px; height:26px;">
												<option value="">-请选择-</option>
												<option value="${SHARE}"><core:property dictType="FormOperType" value="${SHARE}"/> </option>
												<option value="${MOVE}"><core:property dictType="FormOperType" value="${MOVE}"/> </option>
												<option value="${IMPORT}"><core:property dictType="FormOperType" value="${IMPORT}"/> </option>
												<option value="${CHANGECATEGORY }"><core:property dictType="FormOperType" value="${CHANGECATEGORY}"/> </option>
											</select>
											</div>
										</li>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">新建时间<:</label><div class="expand-date" style="display:inline;"><input id="createTime" type="text" class="easyui-datebox" style="width:155px; height:26px;" /></div></li>
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
								<div class="e-tabbtn-box mb2" id="e-tabbtn-box">
									<button id="batchProcessFlow" class="tabbtn fr" type="button" onclick="batchProcessFlow()">流程处理</button>
								</div>
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
			url:"${cxt}/userApprove/ajax/findUserApproveList.action",
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			toolbar:"#e-tabbtn-box",
			emptyMessage:true,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:function(){
				try{
					<%--var queryDate = $("#createTime").datebox("getValue");
					var newDate;
					if(queryDate){
						var data=$.parser.parseDate("yy-mm-dd",queryDate);
						var lastDate = new Date((data/1000+86400)*1000);
						newDate=$.parser.formatDate("yy-mm-dd",lastDate);
					}else{
						var nDate = new Date((new Date()/1000+86400)*1000);
						newDate = $.parser.formatDate("yy-mm-dd",nDate);
					}--%>
					return {
					'auto':true,//自动拼接
					'formOperType!i':"=:$"+$("#formOperType").combobox("getValue"),
					/* 'current.id!l':"=:$"+$("#taskNodeId").combobox("getValue"), */
					'createTime!date':"<:$"+$("#createTime").datebox("getValue")
				};
				}catch (e) {}
				return {};
			},
			columns:[[
			{field:'ck',checkbox:true},
			{title:'流程名称',field:'currentName',width:150},
			{title:'用户名称',field:'loginName',width:150},
			{title:'审批意见',field:'opinion',width:150},
			{title:'新建时间',field:'createTime',width:150},
			{title:'提交人',field:'updateUserName',width:150},
			{title:'操作类型',field:'formOperType',dictionary:"FormOperType",width:100},
			{title:'操作',field:'id_1',width:230,selected:true,
				formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a id='menu_"+rec.id,"' ");
					buttonHtml.push("href=\"javascript:processFlow('"+rec.id+"');\"");
					buttonHtml.push("class=\"easyui-butcombo-menu\">流程处理</a>"," ");
					return "<span>"+buttonHtml.join("")+"</span>";
				}
			}
			]],
			onLoadSuccess:function(){
				var rows=$("#datagridList").datagrid("getRows");
				if(rows<=0){
					$("#datagridList").datagrid({
						url:"",
						pagination:false,
					});
					$("#batchProcessFlow").hide();
				}
			},
			pagination:true
		});
	});
	
	function getChecked(){
		var checked=$("#datagridList").datagrid("getChecked");
		if(checked==null || checked.length<=0){
			showMsg("error","请您选择要审批的用户");
			return;
		}
		return checked;
	}
	
	function batchProcessFlow(){
		var checked=$("#datagridList").datagrid("getChecked");
		if(checked==null || checked.length<=0){
			showMsg("error","请您选择要审批的用户");
			return;
		}
		for(var i=0;i<checked.length;i++){
			var b=checked[i];
			for(var n=0;n<checked.length;n++){
				var bb=checked[n];
				if(b!=bb && b.currentId && bb.currentId){
					if(b.currentId!=bb.currentId){
						showMsg("error","请您选择审批的用户,有不同的流程，请您选择相同的流程");
						return;
					}
				}
			}
		}
		$("#win").window({ 
			title:"流程处理",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/taskInstance/batchProcessFlow.action?winParentId=win&taskNodeid="+checked[0].currentId,
		    modal:true
		});
	}
	
	/**进入流程处理页面*/
	function processFlow(userTaskInstanceId){
		$("#win").window({ 
			title:"流程处理",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/taskInstance/preProcessFlow.action?winParentId=win&userTaskInstanceId="+userTaskInstanceId,
		    modal:true
		});
	}
	/**点击菜单时间，必须要存在*/
	function menuItemHandler(node){
		
	}
	function reloadData(){
		try{
			$("#datagridList").datagrid("clearChecked");
			$('#datagridList').datagrid("reload");
		}catch (e) {alert(e)}
		return false;
	}
	//下拉菜单
	$(function(){dropdown("#examgl","#examgl .e-menu-navbg");});
	//tab切换
	$(function(){tabsclick("#tab .e-sm-tabchange-bar a","#tab .e-sm-tabchange-bd .e-tree-con");});
</script>
  </body>
</html>
