<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
	<title>用户管理--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%>
<!-- start 内容 -->
<div class="content-wrap ml10">
	<!-- start adv -->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!-- end adv -->
	<!-- start 主要内容区域 -->
	<table class="e-main-con-box" width=100% >
		<tr>
			<td class="e-main-con-left">
				<!--start left -->
				<div id="pmt-tabwrap" class="e-tabchange-wrap">
					<div class="e-tabchange-bar pmt-tabbar">
						<a href="${cxt}/user/preList.action"><span class="title">用户管理</span><em class="lock"></em></a>
						<a href="javascript:;" class="curr"><span class="title">注册管理</span><em class="lock"></em></a>
						<a href="${cxt}/userApprove/preUserImportApproveList.action"><span class="title">导入管理</span><em class="lock"></em></a>
					</div>
					
					<div class="e-tabchange-bd pmt-context">
						<div class="pmt-tabcon-panel curr">
						<!-- start 搜索 -->
						<div class="e-search-wrap mb10">
						<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">注册情况:</label>
										</li>
									</ul>
									</td>
									<td class="e-search-btn-box" style="width:80px;">
										<button class="btn" type="submit" >搜索</button>
									</td>
								</tr>
							</table>
						</form>
						</div>
						<!-- end 搜索 -->
						<div class="main-tableContainer cl-margin">
							<!--start 表格操作btn -->
							<!--end 表格操作btn -->
							<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
						</div>
						</div>
					</div>
				</div>
				<!--end left -->
			</td>
			<!-- <td class="e-main-con-right" style="width:290px;">
				start right
				<div class="e-sm-tabchange-wrap ml15" id="tab">
					<h2 class="e-sm-tabchange-bar">
						<a href="javascript:;" class="curr">机构</a>
					</h2>
					<div class="e-sm-tabchange-bd">
						end search
						<div class="e-tree-wrap">
							start tree
							<div class="e-tree-con curr">
								<ul id="orgTree" class="m-expand-tree"></ul>  
							</div>
							end tree
							<div class="e-tree-line"></div>
						</div>
					</div>
				</div>
				end right
			</td> -->
		</tr>
	</table>
	<!-- end 主要内容区域 -->
</div>
<!-- end 内容 -->
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->

 <script  type="text/javascript"> 
 var agenciesId = 0;
	$(function(){
		
		$('#datagridList').datagrid({
			url:"${cxt}/userRegister/ajax/findRegisterUsers.action",
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			emptyMessage:true,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户名',field:'loginName',width:20,selected:true,
					formatter:function(value,rec){
						return "<a href=\"javascript:infoUser('"+rec.id+"');\">"+value+"</a>";
					}},
				{title:'姓名',field:'name',width:20},
				{title:'组织机构',field:'agenciesName',width:10},
				{title:'用户类型',field:'userRoleType',dictionary:'userRoleType',width:10},
				{title:'用户状态',field:'userStatus',width:10,formatter:function(value,rec){
					if(rec.userStatus>1){
						return "已注册";
					}else{
						return "未注册";
					}
				}},
				{title:'操作',field:'id_1',width:17,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						if(rec.userStatus>1){
							buttonHtml.push("用户已注册成功"," ");
						}else {
							buttonHtml.push("<a href=\"javascript:editUser('"+rec.id+"','"+rec.loginName+"');\">同意注册</a>"," ");
							buttonHtml.push("<a href=\"javascript:deleteUser('"+rec.id+"','"+rec.loginName+"');\">拒绝注册</a>"," ");
						}						
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
		
	});
	//允许用户注册
	function editUser(id,name){
			jQuery.post("${cxt}/userRegister/ajax/updatePassUser.action?id="+id,function call(data){
				if(data.status){
					showMsg("info","用户已成功注册");
					callbackPage();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","用户注册失败!");
					}
				}
			},'json');
		return false;
	}
	//拒绝用户注册
	function deleteUser(id,name){
		if(confirm("是否确认拒绝用户注册，用户一旦被拒绝,将被删除")){
			jQuery.post("${cxt}/userRegister/ajax/delete.action?id="+id,function call(data){
				if(data.status){
					showMsg("info","删除用户成功");
					callbackPage();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","拒绝失败");
					}
				}
			},'json');
		}	
			
		return false;
	}
	
	 
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	}
    
   //回调方法
	function callbackPage(){    
		reloadData();
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
<div id="winDetail"></div>
<div id="winEdit"></div>
</body>
</html>
