<%@page import="org.csr.common.user.constant.UserRoleType"%>
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
<div class="content-wrap">
	<!-- start 主要内容区域 -->
	<table class="e-main-con-box" width=100% >
		<tr>
			<td class="e-main-con-right" style="width:290px;">
				<!--start right -->
				<div class="e-sm-tabchange-wrap ml15" id="tab">
					<h2 class="e-sm-tabchange-bar">
						<a href="javascript:;" class="curr">通讯录机构</a>
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
			<td class="e-main-con-left">
				<!--start left -->
				<div id="pmt-tabwrap" class="e-tabchange-wrap">
					<div class="e-tabchange-bar pmt-tabbar">
						<a href="javascript:;" class="curr"><span class="title">用户管理</span><em class="lock"></em></a>
						<%-- <a href="${cxt}/userRegister/preList.action"><span class="title">注册管理</span><em class="lock"></em></a>
						<a href="${cxt}/userApprove/preUserImportApproveList.action"><span class="title">导入管理</span><em class="lock"></em></a> --%>
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
								<%-- <button class="tabbtn fr ml10" type="button" onclick="javascript:importUser();" >批量导入通讯录</button> --%>
								<button class="tabbtn fr ml10" type="button" onclick="javascript:importUserImg();" >批量导入用户照片</button>
								<a class="tabbtn fr ml10" href="javascript:addUser();">新建通讯录</a>
							</div>
							<!--end 表格操作btn -->
							<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
						</div>
						</div>
					</div>
				</div>
				<!--end left -->
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
					"loginName":$("#loginName").val(),
					"name":$("#name").val(),
					};
				}catch (e) {alert(e);}
				return {};
			},
			
			columns:[[
				{field:'ck',checkbox:true},
				{title:"用户编号",field:'id',width:15},
				{title:"用户名",field:'loginName',width:20,selected:true,
					formatter:function(value,rec){
						return "<a href=\"javascript:infoUser('"+rec.id+"');\">"+value+"</a>";
					}},
				{title:"姓名",field:'name',width:20},
				{title:"照片",field:"logType",width:20,formatter:function(value,rec){
					var buttonHtml=[];
					if(rec.facepath1){
						buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.facepath1+"\">"," ");
					}
					if(rec.facepath2){
						buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.facepath2+"\">"," ");
					}
					if(rec.facepath3){
						buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.facepath3+"\">"," ");
					}
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}},

				{title:"组织机构",field:'agenciesName',width:10},
				{title:"用户类型",field:'userRoleType',dictionary:'userRoleType',width:10},
				
				{title:'操作',field:'id_1',width:18,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editUser('"+rec.id+"','"+rec.loginName+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:addUserFace('"+rec.id+"','"+rec.loginName+"');\">添加人脸照片</a>"," "); 
						buttonHtml.push("<a href=\"javascript:deleteUser('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
		
		$('#orgTree').tree({
		    url:"${cxt}/agencies/ajax/treeList.action",
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
	
	function importUserImg(){
		var node=$('#orgTree').tree("getSelected");
		if(node==null){
			showMsg("error","您必须选择域");
			return;
		}
		if(confirm("确认是否导入到 \""+node.name+"\"")){
			 $("#win").window({
				title:"导入用户照片",
			    width:800,
			    height:500,
			    iframe:"${cxt}/face/userAmFace/preImportUserImg.action?agenciesId="+node.id,
			    modal:true
			}); /**/
		}
	}
	//批量导入用户
	function importUser(){
		var node=$('#orgTree').tree("getSelected");
		if(node==null){
			showMsg("error","您必须选择域");
			return;
		}
		if(confirm("确认是否导入到 \""+node.name+"\"")){
			 $("#win").window({
				title:"导入用户",
			    width:800,
			    height:500,
			    iframe:"${cxt}/mobile/usercontacts/preImportUser.action?agenciesId="+node.id,
			    modal:true
			}); /**/
		}
	}
	// 新建
	function addUser(){ 
		$('#win').window({ 
			title:"新建通讯录",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/mobile/usercontacts/preAdd.action?roleType=<%=UserRoleType.GENERAL%>&agenciesId="+agenciesId,
		    modal:true
		}); 
	}
	
	// 编辑
	function editUser(userId,loginName){ 
		$('#win').window({ 
			title:"编辑-"+loginName,   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/mobile/usercontacts/preUpdate.action?id="+userId,
		    modal:true
		}); 
	}
	//删除用户
	function deleteUser(userId){
		showConfirm("确认是否删除用户?",function(){
			WaitingBar.getWaitingbar("delUpUser","删除用户，请等待...").show();
			$.post("${cxt}/mobile/usercontacts/ajax/delete.action",{'ids':userId},function call(data){
				if(data.status){
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败!");
					}
				}
				WaitingBar.getWaitingbar("delUpUser").hide();
			},'json');
		});
	}
	/**点击菜单时间，必须要存在*/
	function menuItemHandler(node){
		if(node.popedom==<%=YesorNo.YES%>){
			agenciesId = node.id;
			$('#datagridList').datagrid("load","${cxt}/mobile/usercontacts/ajax/usersFaceByAgenciesList.action?face=<%=YesorNo.YES%>&agenciesId="+agenciesId);
		}
	}
	//上传人脸
	function addUserFace(id){
		 $('#win').window({
			title:"授权安全资源",
		    width:700,
		    height:500,
		    iframe:"${cxt}/face/userAmFace/preAdd.action?userId="+id,
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
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
<div id="winDetail"></div>
<div id="winEdit"></div>
<div id="popPeople"></div>
</body>
</html>
