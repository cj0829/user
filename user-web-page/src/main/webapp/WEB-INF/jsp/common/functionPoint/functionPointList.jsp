<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
	<title>功能点管理--${system_name}</title>  
  
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
				<div class="e-tabchange-wrap">
					<div class="e-tabchange-bar">
						<span class="title">功能点管理</span>
					</div>
					<div class="e-tabchange-bd">
						<!-- start 搜索 -->
						<!-- <div class="e-search-wrap mb10">
						<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">用户名:</label><input id="loginName" type="text" style="width:155px; height:22px;"/></li>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">名:</label><input id="name" type="text" style="width:155px; height:22px;"/></li>
									</ul>
									</td>
									<td class="e-search-btn-box" style="width:126px;">
										<button class="btn" type="submit" >搜索</button>
									</td>
								</tr>
							</table>
						</form>
						</div> -->
						<!-- end 搜索 -->
						<!--start 表格操作btn -->
						<div class="e-tabbtn-box mb2">
							<button class="tabbtn fr" type="button" onclick="preAddClass();">添加功能点</button>
						</div>
						<!--end 表格操作btn -->
						<div class="main-tableContainer cl-margin">
							<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
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
 var agenciesId;
	$(function(){
		$('#datagridList').treegrid({
			nowrap: true,
			url:'${cxt}/functionPoint/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			emptyMessage:true,
			fitColumns:true,
			idField:'id',
			treeField:'name',	
			scrollbarSize:0,
			queryParams:function(){
				try{
					return {
					"auto":true,//自动拼接
					"user.loginName!s":"like:$"+$("#loginName").val(),
					"user.name!s":"like:$"+$("#name").val(),
					};
				}catch (e) {alert(e);}
				return {};
			},
			
			columns:[[
 				{field:'code',title:'编码',width:50},
				{title:'功能点名称',field:'name',width:170},
			    {field:'isAnonymous',title:'匿名访问',dictionary:'yesOrNo',width:45},	
			    {field:'operationLogLevel',dictionary:'operationLogType',title:'写日志',width:60},
				{field:'authenticationMode',dictionary:'authenticationMode',title:'授权方式',width:50},
				{field:'forwardUrl',title:'功能点入口',width:200},
				{field:'urlRule',title:'功能点规则',width:200},
				{field:'helpStatus',dictionary:'existStatus',title:'帮助',width:30},
				{field:'id',title:'操作',width:200, selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						//if(verifyPermissions('0104002')){
							if(rec.functionPointType==1){
								buttonHtml.push("<a href=\"javascript:preUpdateClass('"+value+"');\">编辑菜单分类</a>"," ");
							}else{
								buttonHtml.push("<a href=\"javascript:editfunctionPoint('"+value+"');\">编辑功能点</a>"," ");
							}
							buttonHtml.push("<a href=\"javascript:addfunctionPoint('"+value+"');\">添加子功能</a>"," ");
						//}
						//if(verifyPermissions('0104001')){
						//}
						//if(verifyPermissions('0104003')){
						//}
						//if(rec.functionPointType==2){
							if(verifyPermissions('0104004') && rec.helpStatus!=1){
								buttonHtml.push("<a href=\"javascript:addHelp('"+rec.code+"');\">添加帮助</a>"," ");
							}
							if(verifyPermissions('0104005') && rec.helpStatus==1){
								buttonHtml.push("<a href=\"javascript:editHelp('"+rec.code+"');\">编辑帮助</a>"," ");
								buttonHtml.push("<a href=\"javascript:deleteHelp('"+rec.code+"');\">删除帮助</a>"," ");
							}
						//}
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
		});
	});
	 // 参数详细信息
	function infoOperationLog(id){
		$('#win').window({ 
			title:"操作日志详细信息",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/loginLog/preInfo.action?id="+id,
		    modal:true
		});
	}
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	};
    
   //回调方法
	function callbackPage(){    
		reloadData();
	};
	//添加功能点
	function preAddClass(){
		$('#win').window({ 
			title:"添加功能点",
		    width:800,    
		    height:600,
		    iframe:"${cxt}/functionPoint/preAddClass.action",
		    modal:true
		});
	}
	
	//进入编辑功能点分类页面
	function preUpdateClass(id){
		$('#win').window({ 
			title:"添加功能点分类",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/functionPoint/preUpdateClass.action?id="+id,
		    modal:true
		});
	}
	//编辑功能点
	function editfunctionPoint(id){
		$('#win').window({ 
			title:"修改功能点",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/functionPoint/preUpdate.action?id="+id,
		    modal:true
		});
	}
	
	//删除功能点
	function delfunctionPoint(id){
		if(confirm("确认是否删除?")){
		jQuery.post("${cxt}/functionPoint/ajax/delete.action",{deleteIds:id},function call(result){
			if(result.status){
				removeNode();
			}else{
				top.showMsg("error",result.message);
			}
		},'json');
		}
	}
	//添加帮助
	function addHelp(code){
		$('#win').window({ 
			title:"添加帮助",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/help/preAdd.action?functionPointCode="+code,
		    modal:true
		});
	}
	//编辑帮助
	function editHelp(code){
		$('#win').window({ 
			title:"编辑帮助",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/help/preUpdate.action?functionPointCode="+code,
		    modal:true
		});
	}
	//删除帮助
	function deleteHelp(code){
	   jQuery.post("${cxt}/help/ajax/delete.action",{functionPointCode:code},function call(result){
		   if(result.status){
			   reloadTree();
			}else{
				top.showMsg("error",result.message);
			}	
		},'json');
	}
	function reloadTree(){
		$('#datagridList').treegrid('reload');
	}
	function getChildren(){
		var node = $('#datagridList').treegrid('getSelected');
		var nodes =null;
		if (node){
			nodes=$('#datagridList').treegrid('getChildren', node.code);
		} else {
			nodes = $('#datagridList').treegrid('getChildren');
		}
		var s = '';
		for(var i=0; i<nodes.length; i++){
			s += nodes[i].code + ',';
		}
	}
	function getSelectedId(){
		var node = $('#datagridList').treegrid('getSelected');
		if(node){
			return node.id;
		}
		return "";
	}
	
	function removeNode(){
		var node = $('#datagridList').treegrid('getSelected');
		if (node){
			$('#datagridList').treegrid('remove', node.id);
		}
	}
	   //回调方法
	function callbackAdd(node,parentId){    
		$('#datagridList').treegrid('append', {
			pid: parentId,
			node: node
		});
	} 	
	  
	//添加功能点
	function addfunctionPoint(id){
		$('#win').window({ 
			title:"添加功能点",
		    width:800,    
		    height:600,
		    iframe:"${cxt}/functionPoint/preAdd.action?id="+id,
		    modal:true
		});
	}
	//回调方法
	function callbackUpdate(node){
		$('#datagridList').treegrid('reload');
		//$('#datagridList').treegrid('update', {id:node.id,row:node});
	}
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
