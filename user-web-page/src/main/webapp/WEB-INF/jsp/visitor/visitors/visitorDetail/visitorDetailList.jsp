﻿<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<title>访客详情--${system_name}</title>  
  
</head>
<body>
<%@include file="../common/header.jsp"%>
<!--content开始-->
<div class="content-wrap ml20 mr20">
	<!--当前标题开始-->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!--当前标题结束-->
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<!-- 搜索 start  -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">记录id:</label><input id="orderid" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">访客姓名:</label><input id="visitorName" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">访客身份证号:</label><input id="visitorIdcard" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">创建时间:</label><input id="createtime" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">访问事由:</label><input id="content" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">身份证照片:</label><input id="idcardimgFileId" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">访客照片:</label><input id="picturesFileId" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="submit" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addVisitorDetail();">新建</button>
					<button class="tabbtn fr mr5" type="button" onclick="deleteVisitorDetails();">删除</button>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0" class="tablemode"></table>
			</div>			
			 <!--table表格结束-->
		</div>
	</div>
</div>
<!--content结束-->
<!--版权开始-->
<%@include file="../common/footer.jsp"%>
<!--版权结束-->

<script type="text/javascript">
	$(function(){
		$("#datagridList").datagrid({
			nowrap: true,
			url:"${cxt}/visitorDetail/ajax/list.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:"id",
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:"ck",checkbox:true},
						{title:"记录id",field:"orderid",width:150},
						{title:"访客姓名",field:"visitorName",width:150},
						{title:"访客身份证号",field:"visitorIdcard",width:150},
						{title:"创建时间",field:"createtime",width:150},
						{title:"访问事由",field:"content",width:150},
						{title:"身份证照片",field:"idcardimgFileId",width:150},
						{title:"访客照片",field:"picturesFileId",width:150},
						{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editVisitorDetail('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteVisitorDetail('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	//查询方法名称
	function queryParams(){
		try{
			return {
			"auto":true,//自动拼接
			"orderid":"like:$"+$("#orderid").val(),
			"visitorName":"like:$"+$("#visitorName").val(),
			"visitorIdcard":"like:$"+$("#visitorIdcard").val(),
			"createtime":"like:$"+$("#createtime").val(),
			"content":"like:$"+$("#content").val(),
			"idcardimgFileId":"like:$"+$("#idcardimgFileId").val(),
			"picturesFileId":"like:$"+$("#picturesFileId").val(),
};
		}catch (e) {}
		return {};
	};
	
	function addVisitorDetail(){
		$("#win").window({
			title:"新建",
			width:800,
			height:600,
			iframe:"${cxt}/visitorDetail/preAdd.action",
			modal:true
		});
	}
	
	// 编辑
	function editVisitorDetail(id){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:600,
			iframe:"${cxt}/visitorDetail/preUpdate.action?id="+id,
			modal:true
		});
	}
	
	//删除全部
	function deleteVisitorDetails(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/visitorDetail/ajax/delete.action",param, function call(data){
			WaitingBar.getWaitingbar("deletes").hide();
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
	function deleteVisitorDetail(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...").show();
			jQuery.post("${cxt}/visitorDetail/ajax/delete.action",{"ids":id}, function call(data){
				WaitingBar.getWaitingbar("delete").hide();
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
	}
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
