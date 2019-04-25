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
	<title>用户管理--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%>
<!-- start 内容 -->
<div class="content-wrap ml10">
	<!-- start 主要内容区域 -->
	<table class="e-main-con-box" width=100% >
		<tr>
			<td class="e-main-con-left">
				<!--start left -->
				<div id="pmt-tabwrap" class="e-tabchange-wrap">
				 	<div class="e-tabchange-bar pmt-tabbar">
						<a href="${cxt}/user/preList.action"><span class="title">用户管理</span><em class="lock"></em></a>
						<a href="javascript:;"  class="curr"><span class="title">未添加照片用户管理</span><em class="lock"></em></a>
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
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">用户名:</label><input id="loginName" type="text" style="width:155px; height:22px;"/></li>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">姓名:</label><input id="name" type="text" style="width:155px; height:22px;"/></li>
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
							<div class="e-tabbtn-box mb2">
								
							</div>
							<!--end 表格操作btn -->
							<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
						</div>
						</div>
					</div>
				</div>
				<!--end left -->
			</td>
			<td class="e-main-con-right" style="width:290px;">
				<!--start right -->
				<div class="e-sm-tabchange-wrap ml15" id="tab">
					<h2 class="e-sm-tabchange-bar">
						<a href="javascript:;" class="curr">机构</a>
					</h2>
					<div class="e-sm-tabchange-bd">
						<!-- end search -->
						<div class="e-tree-wrap">
							<!-- start tree -->
							<div class="e-tree-con curr">
								<ul id="orgTree" class="m-expand-tree"></ul>  
							</div>
							<!-- end tree -->
							<div class="e-tree-line"></div>
						</div>
					</div>
				</div>
				<!--end right -->
			</td>
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
			collapsible:true,
			showfolder:true,
			border:false,
			emptyMessage:true,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:function(){
				try{
					return {
					"auto":true,//自动拼接
					"loginName!s":"like:$"+$("#loginName").val(),
					"name!s":"like:$"+$("#name").val(),
					};
				}catch (e) {alert(e);}
				return {};
			},
			
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户名',field:'loginName',width:20,selected:true,
					formatter:function(value,rec){
						return "<a href=\"javascript:infoUser('"+rec.id+"');\">"+value+"</a>";
					}},
				{title:'姓名',field:'name',width:20},
				{title:'组织机构',field:'agenciesName',width:10},
				{title:'用户类型',field:'userRoleType',dictionary:'userRoleType',width:10},
				{title:'操作',field:'id_1',width:18,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:addUserFace('"+rec.id+"','"+rec.loginName+"');\">添加人脸照片</a>"," "); 
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
		
		$('#orgTree').tree({
		    url:"${cxt}/agencies/ajax/treeList.action?agenciesType=1",
		    onClick:menuItemHandler,
		    onLoadSuccess:function(node, data){
		    	if(data.length>0){
		    		var findNode=$(this).tree('find',data[0].id);
		    		$(this).tree('select',findNode.target);
			    	menuItemHandler(findNode);
		    	}
		    },
		    onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
		    }
		});
	});
	//上传人脸
	function addUserFace(id){
		 $('#win').window({
			title:"添加人脸照片",
		    width:700,
		    height:500,
		    iframe:"${cxt}/face/userFace/preAdd.action?userId="+id,
		    modal:true
		}); 
	}
	
	/**点击菜单时间，必须要存在*/
	function menuItemHandler(node){
		if(node.popedom==<%=YesorNo.YES%>){
			agenciesId = node.id;
			$('#datagridList').datagrid("load","${cxt}/face/userFace/ajax/userFacelist.action?agenciesId="+agenciesId);
		}
	
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
