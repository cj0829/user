<%@page import="org.csr.common.user.constant.AgenciesType"%>
<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<%
	pageContext.setAttribute("PARENT", AgenciesType.PARENT);
	pageContext.setAttribute("COMPANY", AgenciesType.COMPANY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<title>组织机构--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%>
<c:set var="agencies" value="orgexternal"/>
<c:set var="agenciesTitle" value="组织机构"/>
<div class="content-wrap ml10">
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bar">
			<a href="${cxt}/agencies/preTreeList.action?agenciesType=${agenciesType}" class="curr">浏览</a>
            <a href="${cxt}/agencies/preList.action?agenciesType=${agenciesType}">搜索</a>
		</div>
		<div class="e-tabchange-bd">
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<c:if test="${SECURITY_CONTEXT.loginName == 'super'}">
					<button class="tabbtn fr ml10" type="button" onclick="saveBatchUnitdoorincremental();">初始化机构</button>
					<button class="tabbtn fr ml10" type="button" onclick="parameterQuery();">设置系统参数</button>
					<button class="tabbtn fr ml10" type="button" onclick="openNew();">新建根机构</button>
				</c:if>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
			</div>
		</div>
	</div>
</div>
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->

<%--<%@ include file="/include/footer.inc"%>--%>
<script  type="text/javascript"> 
	$(function(){
		$('#datagridList').treegrid({
			nowrap: true,
			url:'${cxt}/agencies/ajax/treeList.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			treeField:'name',
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
		
				{title:'组织机构名称',field:'name',width:150},
				{title:'域',field:'organizationName',width:150},
				{title:'编号',field:'prejectId',width:150},
				{title:'详细地址',field:'address1',width:150},
				{title:'机构类型',field:'type',dictionary:'AgenciesType',width:150},
				{title:'操作',field:'id_1',width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						if(rec.popedom==<%=YesorNo.YES%>){
							if(rec.type<=<%=AgenciesType.PARENT%>){
								buttonHtml.push("<a href=\"javascript:addChildorganization('"+rec.id+"');\">添加下级组织</a>"," ");
							}
							buttonHtml.push("<a href=\"javascript:editOrginternal('"+rec.id+"');\">编辑</a>"," ");
							buttonHtml.push("<a href=\"javascript:deleteOrginternal('"+rec.id+"');\">删除</a>"," ");
						}
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	
	function openNew(){
		$('#win').window({ 
			title:"新增",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/agencies/preAdd.action?agenciesType=${agenciesType}",
		    modal:true
		});
	}
	//添加组织机构
	function addChildorganization(parent){
	   $('#win').window({ 
			title:"新增子组织",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/agencies/preChildrenAdd.action?agenciesType=${agenciesType}&id="+parent,
		    modal:true
		});
	}
	// 编辑
	function editOrginternal(id){ 
		$('#win').window({ 
			title:"编辑",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/agencies/preUpdate.action?agenciesType=${agenciesType}&id="+id,
		    modal:true
		});
	}
	
	//删除全部
	function deleteOrginternals(){
		var row = $('#datagridList').datagrid('getSelections');
		var param=arrayToNameIds(row,"ids","id");
		showConfirm("确认是否删除?",function(){
			jQuery.post("${cxt}/agencies/ajax/delete.action",param, function call(data){
				if(data.status){
					showMsg("info","删除成功");				
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败！");
					}
				}
			},'json'); 
		});
	}
	
	// 删除
	function deleteOrginternal(id){
		showConfirm("确认是否删除?",function(){
		 jQuery.post('${cxt}/agencies/ajax/delete.action',{"ids":id}, function call(data){
			 if(data.status){
					showMsg("info","删除成功");				
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败！");
					}
				}
			 },'json'); 
		});
	}
	//查询方法名称
	function queryParams(){
		try{
			return {agenciesType:"${agenciesType}"};
		}catch (e) {}
		return {};
	};
	
	// 编辑设置参数
	function parameterQuery(id){ 
		$('#win').window({ 
			title:"编辑",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/external/parameterQuery.action",
		    modal:true
		});
	}
	
	//提交表单的方法
	function saveBatchUnitdoorincremental(){
		WaitingBar.getWaitingbar("saveParameter","数据添加中，请等待...").show();
		jQuery.post("${cxt}/external/ajax/batchUnitdoorincremental.action",{},function call(data){
			WaitingBar.getWaitingbar("saveParameter").hide();
			if(data.status){
				showMsg("info","机构数据同步成功");
				$("#datagridList").datagrid("reload");
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","机构数据同步失败！");
				}
			}
		},"json");
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").treegrid('reload');
	}
    
   //回调方法
	function callbackPage(){    
		reloadData();
	}
</script>
<!--弹出窗口开始-->
<div id="win"></div>
<div id="winadd"></div>
</body>
</html>
