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
						<a href="javascript:;"  class="curr"><span class="title">用户人脸照片管理</span><em class="lock"></em></a>
					</div>
					
					<div class="e-tabchange-bd pmt-context">
						<div class="pmt-tabcon-panel curr">
						<!-- start 搜索 -->
						<div class="e-search-wrap mb10">
						<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
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
								<button class="tabbtn fr" type="button" onclick="addUserFace();">新建</button>
								<button class="tabbtn fr mr5" type="button" onclick="deleteUpdateVersions();">删除</button>
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
					"auto":true//自动拼接
					};
				}catch (e) {alert(e);}
				return {};
			},
			
			columns:[[
				{field:'ck',checkbox:true},
				{title:'姓名',field:'loginName',width:120,selected:true,
					formatter:function(value,rec){
						return "<a href=\"javascript:infoUser('"+rec.id+"');\">"+value+"</a>";
				}},
				{title:"照片",field:"logType",width:150,formatter:function(value,rec){
					var buttonHtml=[];
					if(rec.facepath1){
						buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.facepath1+"\">"," ");
					}else{
						buttonHtml.push("为上传照片"," ");
					}
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}},
				{title:'操作',field:'id_1',width:80,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:updateUserFace('"+rec.userFaceId+"','"+rec.loginName+"');\">修改人脸照片</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteFace('"+rec.userFaceId+"');\">删除人脸照片</a>"," "); 
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
		    iframe:"${cxt}/face/userFacedb/preAdd.action?agenciesId="+agenciesId,
		    modal:true
		}); 
	}
	
	//上传人脸
	function updateUserFace(id){
		 $('#win').window({
			title:"修改人脸照片",
		    width:700,
		    height:500,
		    iframe:"${cxt}/face/userFacedb/preUpdate.action?userId="+id,
		    modal:true
		}); 
	}
	
	/**点击菜单时间，必须要存在*/
	function menuItemHandler(node){
		if(node.popedom==<%=YesorNo.YES%>){
			agenciesId = node.id;
			$('#datagridList').datagrid("load","${cxt}/face/userFacedb/ajax/userFacelist.action?agenciesId="+agenciesId);
		}
	
	}
	
	// 删除
	function deleteFace(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/face/userFacedb/ajax/deleteFace.action",{"userId":id}, function call(data){
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
