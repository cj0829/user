<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<link type="text/css" href="${cxt}/css/user/dark-transparent/userSpecialFunctionPoint.css" rel="stylesheet" />
 	<title>安全资源授权--${system_name}</title>
</head>
<body>
<div class="e-tabbox main-expand-form">
	<div class="e-search-wrap">
		<form name="searchForm" id="searchForm" onsubmit="return updateUser()">
		<table width="100%">
			<tr>
				<td class="e-search-con">
				<ul>
					<li class="e-search-td"><label class="mr8 lab" style="width:560px;">请选择您要授权的角色，勾选表示已经授权</label></li>
				</ul>
				</td>
				<td class="e-search-btn-box" style="width:88px;">
					<button class="btn" type="button" onclick="updateUser();" >授权</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div class="form-tab-wrap mt10">
		<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"></table>
		<input type="hidden" id="userId" name="userId" value="${userId}"/>
	</div>
</div>
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tabbar").height());
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tabbar").height());
		});
	});
	 jQuery(document).ready(function(){ 
			$('#datagridList').datagrid({nowrap: true,
				url:"${cxt}/userafeResource/ajax/listAddSafeResource.action?userId=${userId}",
				collapsible:true,
				showfolder:true,
				border:false,
				fitColumns:true,
				idField:'id',
				scrollbarSize:0,
				showPageList : false,
				columns:[[
					{field:'ck',checkbox:true},
					{title:'安全资源',field:'name',width:180},
					{title:'安全资源类型',field:'isSystem',dictionary:'yesOrNo',width:100},
					{title:'备注',field:'remark',width:300}
				]],
				pagination:true
			});
		}); 
	
	//提交表单的方法
	function updateUser(form1){
		//提交表单
		 if($('#userForm').form('validate')){
				 WaitingBar.getWaitingbar("updateUserRole","数据添加中，请等待...").show();
				var params=jQuery("#groupForm").serialize();
				var checkedsUser=$("#datagridList").datagrid("getChecked");
			
				var safeResourceCollectionIds=[];
				for(var i=0;i<checkedsUser.length;i++){
					safeResourceCollectionIds.push(checkedsUser[i].id);
				}
				var params = {safeResourceCollectionIds:safeResourceCollectionIds.join(","),userId:"${userId}"};
					
				jQuery.post("${cxt}/userafeResource/ajax/updateUserSafeResourceCollection.action",params,function call(data){
					if(data.status){
						WaitingBar.getWaitingbar("updateUserRole").hide();
						showMsg("info","用户角色编辑成功");
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","用户角色编辑失败!");
						}
					}
				},'json');
		}
		return false;
	}
</script>
</body>
</html>