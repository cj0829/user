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
					<a class="tabbtn fr" href="javascript:addPointsRules();">添加积分规则</a>
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
			url:'${cxt}/pointsRules/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'积分规则名',field:'operation',width:80,selected:true,
					formatter:function(value,rec){
						return "<a href=\"javascript:infoPointsRules('"+rec.id+"');\">"+value+"</a>";
					}},
				{title:'分数1',field:'points1',width:80},
				{title:'分数2',field:'points2',width:80},
				{title:'每日次数',field:'countDay',width:80},
				{title:'操作',field:'oper_',width:100,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editPointsRules('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deletePointsRules('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	});
	//添加积分规则
	function addPointsRules(){
	   $("#win").window({ 
			title:"添加积分规则",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/pointsRules/preAdd.action",
		    modal:true
		});
	}
	
	// 编辑积分规则
	function editPointsRules(id){ 
	   $("#win").window({ 
			title:"编辑积分规则",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/pointsRules/preUpdate.action?id="+id,
		    modal:true
		});
	}
	// 积分规则详细信息
	function infoPointsRules(id){ 
	   $("#win").window({ 
			title:"积分规则详细信息",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/pointsRules/preInfo.action?id="+id,
		    modal:true
		});
	}
	//删除积分规则
	function deletePointsRuless(){ 
	    if(confirm("确认是否删除?")){
		    var params=jQuery('#pointsRulesForm').serialize();
			jQuery.post("${cxt}/pointsRules/ajax/delete.action",params, function call(result){
				if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					showMsg("error",data.message);
				}
			},'json'); 
		}
	}
	
	//删除积分规则
	function deletePointsRules(id){ 
		if(confirm("确认是否删除?")){
	        jQuery.post("${cxt}/pointsRules/ajax/delete.action","pointsRulesIds="+id, function call(result){
	        	if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					showMsg("error",data.message);
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