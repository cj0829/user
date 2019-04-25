<%@page import="org.csr.core.constant.YesorNo"%>
<%@page import="org.csr.common.user.constant.RoleType"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<title>安全资源--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%> 
<div class="content-wrap ml10">
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap main-expand-form mb10">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">安全资源名称</label><input id="name" type="text" style="width:115px; height:22px;" /></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:90px;">
						<button class="btn fl" type="submit">搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<a class="tabbtn fr" href="javascript:addSafeResourceCollection();">新建安全资源</a>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"></table>
			</div>
		</div>
	</div>
</div>
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->
<script  type="text/javascript"> 
	jQuery(document).ready(function(){ 
		$(window).unbind('div.right_content').bind('resize.right_content', function(){
			$("#datagridList").datagrid('resize');
		});
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/safeResourceCollection/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{title:'安全资源名',field:'name',width:200,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a href=\"javascript:editSafeResourceCollection('"+rec.id+"');\">"+value+"</a>"," ");
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}},
				{title:'系统资源',field:'isSystem',dictionary:'yesOrNo',width:200},
				{title:'备注',field:'remarks',width:200},
				{title:'操作',field:'id_1',width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editSafeResourceCollection('"+rec.id+"');\">编辑</a>"," ");
						if(<%=YesorNo.NO%>==rec.isSystem){
							buttonHtml.push("<a href=\"javascript:deleteSafeResourceCollection('"+rec.id+"');\">删除</a>"," ");
						}
						buttonHtml.push("<a href=\"javascript:authSafeResource('"+rec.id+"');\">安全资源授权</a>"," ");
						buttonHtml.push("<a href=\"javascript:authorize('"+rec.id+"');\">用户授权</a>"," ");
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
			'auto':true,//自动拼接
			'name!s':"like:$"+$("#name").val(),
			};
		}catch (e) {}
		return {};
	};
	//新建安全资源
	function addSafeResourceCollection(){
	   $('#win').window({ 
			title:"新建安全资源",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/safeResourceCollection/preAdd.action",
		    modal:true
		});
	}
	// 编辑安全资源
	function editSafeResourceCollection(id){ 
		$('#win').window({ 
			title:"编辑安全资源",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/safeResourceCollection/preUpdate.action?id="+id,
		    modal:true
		});
	}
	//删除安全资源
	function deleteSafeResourceCollection(id){
		if(confirm("确认是否删除?")){
	        jQuery.post('${cxt}/safeResourceCollection/ajax/delete.action',{"id":id}, function call(data){
				if(data.status){
					showMsg("success","删除安全资源成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除安全资源失败!");
					}
				}
			},'json'); 
		}
	}
	//安全资源授权。
	function authSafeResource(safeResourceCollectionId){
		$('#win').window({ 
			title:"安全资源授权",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/safeResource/preList.action?safeResourceCollectionId="+safeResourceCollectionId,
		    modal:true
		});
	}
	//用户授权
	function authorize(id){ 
		$('#win').window({ 
			title:"用户授权",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/safeResource/preNotAuthorizedUserList.action?safeResourceCollectionId="+id,
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
	}
</script>
<div id="win"></div>
</body>
</html>