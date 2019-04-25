<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<title>积分等级管理--${system_name}</title>  
  
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
					<a class="tabbtn fr" href="javascript:addPointsLevelRules();">添加积分等级</a>
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
		$(window).unbind('div.right_content').bind('resize.right_datagrid', function(){
			$("#datagridList").datagrid('resize');
		});
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/pointsLevelRules/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'积分等级名称',field:'levelName',width:180},
				{title:'积分等级',field:'level',width:80},
				{title:'分数',field:'points',width:80},
				{title:'操作',field:'oper_',width:100,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editPointsLevelRules('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deletePointsLevelRules('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	});
	//添加积分等级
	function addPointsLevelRules(){
	   $("#win").window({ 
			title:"添加积分等级",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/pointsLevelRules/preAdd.action",
		    modal:true
		});
	}
	// 编辑积分等级
	function editPointsLevelRules(id){
	   $("#win").window({ 
			title:"编辑积分等级",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/pointsLevelRules/preUpdate.action?id="+id,
		    modal:true
		});
	}
	//删除积分等级
	function deletePointsLevelRuless(){
	    if(confirm("确认是否删除?")){
		    var params=jQuery('#pointsLevelRulesForm').serialize();
			jQuery.post("${cxt}/pointsLevelRules/ajax/delete.action",params, function call(result){
				if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					top.showMsg("error",data.message);
				}
			},'json'); 
		}
	}
	
	//删除积分等级
	function deletePointsLevelRules(id){
		if(confirm("确认是否删除?")){
	        jQuery.post("${cxt}/pointsLevelRules/ajax/delete.action","pointsLevelRulesIds="+id, function call(result){
	        	if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					top.showMsg("error",data.message);
				}
			},'json'); 
		}
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