<%@page import="org.csr.core.persistence.business.domain.Organization"%>
<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<title>域--${system_name}</title>  
</head>
<body> 
<%@ include file="../common/header.jsp"%> 
<div class="content-wrap ml10">
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bar">
			<a href="${cxt}/organization/preTreeList.action">浏览</a>
            <a href="${cxt}/organization/preList.action" class="curr">搜索</a>
		</div>
		<div class="e-tabchange-bd">
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap main-expand-form mb10">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">域名称</label><input id="name" type="text" style="width:115px; height:22px;" /></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:90px;">
						<button class="btn fl" type="submit">搜索</button>
						<!-- <button class="btn" type="button" onclick="open();">新建</button> -->
					</td>
				</tr>
			</table>
			</div>
			</form>
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<a class="tabbtn fr" href="javascript:open();">新建</a>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
			</div>
		</div>
	</div>
</div>
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->
 <script  type="text/javascript"> 
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/organization/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'域名称',field:'name',width:150},
				{title:'父域',field:'parentName',width:150},
				{title:'别名（英文）',field:'aliases',width:150},
				{title:'激活状态',field:'organizationStatus',dictionary:"yesOrNo",width:150},
				{title:'域管理员',field:'adminUserName',width:150},
				{title:'操作',field:'id_1',width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:addChildorganization('"+rec.id+"');\">添加下级域</a>"," ");						
						if(rec.id!=<%=Organization.global%>){
							buttonHtml.push("<a href=\"javascript:editOrganization('"+rec.id+"');\">编辑</a>"," ");
						}
						if(rec.organizationStatus==<%=YesorNo.YES%> &&　rec.id!=<%=Organization.global%>){
							buttonHtml.push("<a href=\"javascript:preAuthorize('"+rec.id+"');\">授权功能</a>"," ");
						}
						if(rec.organizationStatus==<%=YesorNo.YES%> &&　rec.id!=<%=Organization.global%>){
							buttonHtml.push("<a href=\"javascript:preOrgDefaultPermissions('"+rec.id+"');\">默认授权</a>"," ");
						}
						if(rec.organizationStatus==<%=YesorNo.NO%>){
							buttonHtml.push("<a href=\"javascript:activatingOrganization('"+rec.id+"');\">激活域</a>"," ");
							buttonHtml.push("<a href=\"javascript:deleteOrganization('"+rec.id+"');\">删除</a>"," ");
						}
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	
	function open(){
		$('#win').window({ 
			title:"新建",   
		    width:600,    
		    height:450,
		    iframe:"${cxt}/organization/preAdd.action",
		    modal:true
		});
	}
	function addChildorganization(parent){
		   $('#win').window({ 
				title:"新建",   
			    width:600,    
			    height:450,
			    iframe:"${cxt}/organization/preChildrenAdd.action?id="+parent,
		    modal:true
		});
	}
	// 编辑
	function editOrganization(id){ 
		$('#win').window({ 
			title:"编辑",   
		    width:600,    
		    height:450,
		    iframe:"${cxt}/organization/preUpdate.action?id="+id,
		    modal:true
		});
	}

	//编辑机构权限
	function preAuthorize(id){
		$('#win').window({
			title:"编辑机构权限",
		    width:600,
		    height:450,
		    iframe:"${cxt}/organization/preMyselfAuthorize.action?organizationId="+id,
		    modal:true
		});
	}
	//编辑机构权限
	function preOrgDefaultPermissions(id){
		$('#win').window({
			title:"编辑机构权限",
		    width:600,
		    height:450,
		    iframe:"${cxt}/organization/preOrgDefaultPermissions.action?organizationId="+id,
		    modal:true
		});
	}
	// 删除
	function deleteOrganization(id){
		showConfirm("确认是否删除?",function(){
	         jQuery.post("${cxt}/organization/ajax/delete.action",{"organizationId":id}, function call(data){
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
		});
	}
	
	// 删除
	function activatingOrganization(id){
		showConfirm("确认是激活安全?如果激活安全域，则不能删除安全域",function(){
	         jQuery.post("${cxt}/organization/ajax/activatingOrganization.action",{"organizationId":id}, function call(data){
				if(data.status){
					showMsg("info","安全域激活成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","安全域激活失败!");
					}
				}
			 },"json"); 
		});
	}
	
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
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	}
    
   //回调方法
	function callbackPage(node,parentId){    
		reloadData();
	}
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
