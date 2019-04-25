<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
	<title>模板管理--${system_name}</title>  
  
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
			<span class="title">模板管理</span>
		</div>
		<div class="e-tabchange-bd">
			<!-- start 搜索 -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td">
							<label class="mr8 lab" style="width:70px;">模板编码</label>
							<input id="code" type="text" class="easyui-textbox" style="width:115px; height:22px;"/>
						</li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:126px;">
						<button class="btn" type="button" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- end 搜索 -->
			<div class="main-tableContainer cl-margin">
				<!--start 表格操作btn -->
				<div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addRemindTemplate();">创建模板管理</button>
				</div>
				<!--end 表格操作btn -->
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
			url:"${cxt}/remindTemplate/ajax/list.action",
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
						"code":"like:$"+$("#code").val(),
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{field:"ck",checkbox:true},
				{title:"名称",field:"name",width:150},
				{title:"模板编码",field:"code",width:150},
				{title:"操作",field:"id_1",width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editRemindTemplate('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteRemindTemplate('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	
	function addRemindTemplate(){
		$("#win").window({ 
			title:"新建",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/remindTemplate/preAdd.action",
		    modal:true
		});
	}
	
	// 编辑
	function editRemindTemplate(id){ 
		$("#win").window({ 
			title:"编辑",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/remindTemplate/preUpdate.action?id="+id,
		    modal:true
		});
	}
	
	//删除全部
	function deleteRemindTemplates(){  
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/remindTemplate/ajax/delete.action",param, function call(data){
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
	function deleteRemindTemplate(id){
	     if(confirm("确认是否删除?")){
	         jQuery.post("${cxt}/remindTemplate/ajax/delete.action",{"remindTemplateIds":id}, function call(data){
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
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
