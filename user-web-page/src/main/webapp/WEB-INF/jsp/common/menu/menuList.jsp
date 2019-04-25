<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
	<title>菜单管理--${system_name}</title>  
  
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
			<span class="title">菜单管理</span>
		</div>
		<div class="e-tabchange-bd">
			<!-- start 搜索 -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<!-- <div class="e-search-wrap mb10">
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
			</div> -->
			</form>
			<!-- end 搜索 -->
			<div class="main-tableContainer cl-margin" onclick="test(e)">
				<!--start 表格操作btn -->
				<div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addMenu('');">创建菜单管理</button>
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
function test(e){
	alert("adsf");
	e.stopPropagation();
}
	$(function(){
		
		$("#datagridList").treegrid({
			nowrap: true,
			url:"${cxt}/menu/ajax/treeList.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:"id",
			treeField:'name',
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
				{title:"菜单名称",field:"name",width:150},
				{title:"菜单是否显示",field:"display",dictionary:"yesOrNo",width:150},
				{title:"入口功能点",field:"functionPoint",width:150,formatter:function(value,rec){
					if(value){
						return value.name;
					}
				}},
				{title:"备注",field:"remark",width:150},
				{title:"操作",field:"id_1",width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:addMenu('"+rec.id+"');\">添加子菜单</a>"," ");
						buttonHtml.push("<a href=\"javascript:editMenu('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteMenu('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]]
		});
	}); 
	//添加
	function addMenu(id){
		$("#win").window({ 
			title:"新建",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/menu/preAdd.action?id="+id,
		    modal:true
		});
	
	}
	
	// 编辑
	function editMenu(id){ 
		$("#win").window({ 
			title:"编辑",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/menu/preUpdate.action?id="+id,
		    modal:true
		});
	}
	
	// 删除
	function deleteMenu(id){
	     if(confirm("确认是否删除?")){
	         jQuery.post("${cxt}/menu/ajax/delete.action",{"ids":id}, function call(data){
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
		$("#datagridList").treegrid("reload");
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
