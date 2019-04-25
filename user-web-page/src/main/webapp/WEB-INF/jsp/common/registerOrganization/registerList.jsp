<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>注册机构--${system_name }</title>  
</head>
<body>  
<%@ include file="../common/header.jsp"%> 
<div class="content-wrap ml10">
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bar">
			<span class="title">注册机构</span>
		</div>
		<div class="e-tabchange-bd">
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap main-expand-form mb10" style="width:99%;">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="e-search-con">
<!-- 					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">安全角色名称</label><input id="name" type="text" style="width:115px; height:22px;" /></li>
					</ul>
 -->					</td>
					<td class="e-search-btn-box" style="width:90px;">
						<button class="btn fl" type="submit">搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<a class="tabbtn fr" href="javascript:registrationOrganization();">注册新机构</a>
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
			url:'${cxt}/registerOrganization/ajax/organizationList.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'机构名称',field:'name',width:180,
					formatter:function(value,rec){
						return "<a href=\"javascript:infoDistrict('"+rec.id+"');\">"+value+"</a>";
					}},
				{title:'状态',field:'organizationStatus',dictionary:'organizationStatus',width:100},
				{title:'管理员帐号',field:'remark',width:100},
				{title:'操作',field:'id_1',width:300,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editOrganization('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteOrganization('"+rec.id+"');\">删除</a>"," ");
						buttonHtml.push("<a href=\"javascript:preOrganizationParameter('"+rec.id+"');\">机构参数管理</a>"," ");
						buttonHtml.push("<a href=\"javascript:initUser('"+rec.id+"');\">重置管理员密码</a>"," ");
						if(rec.organizationStatus==1){
							buttonHtml.push("<a href=\"javascript:freeze('"+rec.id+"');\">冻结</a>"," ");
						}else if(rec.organizationStatus==2){
							buttonHtml.push("<a href=\"javascript:activating('"+rec.id+"');\">激活</a>"," ");
						}
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	 
	//添加注册机构
	function registrationOrganization(){
		 $("#win").window({ 
			title:"添加注册机构",
		    width:800,    
		    height:600,
		    iframe:"${cxt}/registerOrganization/preAdd.action",
		    modal:true
		});
	}
	// 编辑注册机构
	function editOrganization(id){ 
	    $("#win").window({ 
			title:"编辑注册机构",
		    width:800,    
		    height:600,
		    iframe:"${cxt}/registerOrganization/preUpdate.action?organizationId="+id,
		    modal:true
		});
	}
	//进入系统参数配置
	function preOrganizationParameter(id){
		window.location.href="${cxt}/registerOrganization/preListOrganizationParameter.action?organizationId="+id;
	}
	//删除注册机构
	function deleteOrganizations(){ 
	    if(confirm("确认是否删除?")){
		    var params=jQuery('#registerForm').serialize();
		    jQuery.post("${cxt}/registerOrganization/ajax/delete.action",params, function call(result){
				if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					showMsg("error",result.message);
				}
			},'json'); 
		}
	}

	//删除注册机构
	function deleteOrganization(id){ 
		if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/registerOrganization/ajax/delete.action","organizationId="+id, function call(result){
				if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					showMsg("error",result.message);
				}
			},'json');
		}
	}
	//初始化密码
	function initUser(id){
		if(confirm("确认是否初始化用户密码?")){
			jQuery.post("${cxt}/registerOrganization/ajax/resetAdminPassword.action",{"organizationId":id},"初始化成功");
		}
	}
	//冻结
	function freeze(id){
		if(confirm("确认是否冻结?")){
			jQuery.post("${cxt}/registerOrganization/ajax/freeze.action",{"organizationId":id},"冻结成功");
		}
	}
	//激活
	function activating(id){
		if(confirm("确认是否激活?")){
			jQuery.post("${cxt}/registerOrganization/ajax/activating.action",{"organizationId":id},"激活成功");
		}
	}
	//执行操作
	function jQueryPost(url,param,info){ 
		jQuery.post(url,param, function call(result){
			if(result.status){
				showMsg("success",info);
				reloadData();
			}else{
				showMsg("error",result.message);
			}
		},'json'); 
	}
   
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
	}
   //回调方法
	function callbackPage(){    
		reloadData();
	}
</script>
<div id="win"></div>
</body>
</html>