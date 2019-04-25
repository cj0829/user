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
	<title>安全角色--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%> 
<div class="content-wrap ml10">
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap main-expand-form mb10" style="width:99%;">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">安全角色名称</label><input id="name" type="text" style="width:115px; height:22px;" /></li>
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
					<a class="tabbtn fr" href="javascript:addRole();">新建新安全角色</a>
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
	jQuery(document).ready(function(){ 
		$(window).unbind('div.right_content').bind('resize.right_content', function(){
			$("#datagridList").datagrid('resize');
		});
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/role/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'安全角色名',field:'name',width:180,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a href=\"javascript:infoRole('"+rec.id+"');\">"+value+"</a>"," ");
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}},
				{title:'安全角色类型',field:'roleType',dictionary:'roleType',width:100},
				{title:'备注',field:'remark',width:300},
				{title:'操作',field:'id_1',width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						if(<%=RoleType.SYSTEM%>!=rec.roleType){
							buttonHtml.push("<a href=\"javascript:editRole('"+rec.id+"');\">编辑</a>"," ");
						}
						buttonHtml.push("<a href=\"javascript:infoRole('"+rec.id+"');\">授权信息</a>"," ");
						if(rec.roleType!=1){
							buttonHtml.push("<a href=\"javascript:deleteRole('"+rec.id+"');\">删除</a>"," ");
							buttonHtml.push("<a href=\"javascript:addRoleFunctionPoint('"+rec.id+"');\">安全角色授权</a>"," ");
						}
						buttonHtml.push("<a href=\"javascript:addUserRole('"+rec.id+"');\">用户授权</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	
	//新建安全角色
	function addRole(){
	   $('#win').window({ 
			title:"新建安全角色",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/role/preAdd.action",
		    modal:true
		});
	}
	// 编辑安全角色
	function editRole(id){ 
		$('#win').window({ 
			title:"编辑安全角色",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/role/preUpdate.action?roleId="+id,
		    modal:true
		});
	}
	
	//关联功能点
	function addRoleFunctionPoint(id){
		$('#win').window({ 
			title:"组件授权",   
		    width:600,    
		    height:400,
		    iframe:"${cxt}/role/preAddRoleFunctionPoint.action?roleId="+id,
		    modal:true
		});
	}
	// 查看详细info
	function infoRole(id){
		$('#win').window({ 
			title:"安全角色授权信息",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/role/preRoleFunctionPointInfo.action?roleId="+id,
		    modal:true
		});
	}
	//删除安全角色
	function deleteRoles(){ 
	    if(confirm("确认是否删除?")){
	    	var row = $('#datagridList').datagrid('getSelections');
			if (row.length>0){
				jQuery.post("${cxt}/role/ajax/delete.action",arrayToNameIds(row,"roleIds","id"), function call(data){
					if(data.status){
						showMsg("success","删除安全角色成功");
						reloadData();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","删除安全角色失败!");
						}
					}
				},'json'); 
			}
			
		}
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
	//删除安全角色
	function deleteRole(id){
		if(confirm("确认是否删除?")){
	        jQuery.post('${cxt}/role/ajax/delete.action',{"roleIds":id}, function call(data){
				if(data.status){
					showMsg("success","删除安全角色成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除安全角色失败!");
					}
				}
			},'json'); 
		}
	}
	
	//关联功能点
	function addRoleFunctionPoint(id){
		$('#win').window({ 
			title:"安全角色授权",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/role/preAddRoleFunctionPoint.action?roleId="+id,
		    modal:true
		});
	}
	
	//批量授权
	function addUserRole(id){ 
		$('#win').window({ 
			title:"用户授权",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/role/preAddUserRole.action?roleId="+id,
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