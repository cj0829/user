<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@include file="../common/common.jsp"%>
	<title>数据字典管理</title>  
  
</head>
<body>

<%@ include file="../common/header.jsp"%> 
<div class="content-wrap ml10">
	<!-- start adv -->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bar">
			<span class="title">菜单管理</span>
		</div>
		<div class="e-tabchange-bd">
			<!-- start 搜索 -->
			<!-- <form id="searchInfoForm" action="#" onsubmit="return search();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:120px;">数据字典类型:</label><input id="dictType" class="easyui-textbox" type="text" style="width:115px; height:25px;" /></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:126px;">
						<button class="btn" type="submit" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form> -->
			<!-- end 搜索 -->
			<div class="main-tableContainer cl-margin">
				<!--start 表格操作btn -->
				<div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addDictionary();">添加数据字典</button>
				</div>
				<!--end 表格操作btn -->
				<table id="dictionaryTree" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
			</div>	
		</div>
	</div>
</div>
<%@ include file="../common/common.jsp"%>

 <script  type="text/javascript"> 
 
 	$(function(){
		$(window).unbind('div.right_content').bind('resize.right_content', function(){
			$("#dictionaryTree").treegrid('resize');
		});
	});
	
	$(function(){
		initTreeData("${cxt}/dictionary/ajax/list.action");
	}); 
	
	function initTreeData(url){
		$("#dictionaryTree").treegrid({
			nowrap: true,
			url:url,
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:"id",
			treeField:'dictType',
			scrollbarSize:0,
			columns:[[
				{title:'数据字典类型',field:'dictType',width:200},
				{field:'dictValue',title:'数据字典值',width:150},
				{field:'remark',title:'描述',width:300},
				{field:'id',title:'操作',width:200,align:'left',
					formatter:function(value,rec){
						var buttonHtml=[];
						if(rec.parentId==null){
							buttonHtml.push("<a href=\"javascript:addChildDictionary('"+value+"');\">添加子类型</a>"," ");
							buttonHtml.push("<a href=\"javascript:editDictionary('"+value+"');\">编辑</a>"," ");
							buttonHtml.push("<a href=\"javascript:delDictionary('"+value+"');\">删除</a>"," ");
						}else{
							buttonHtml.push("<a href=\"javascript:editDictionary('"+value+"');\">编辑</a>"," ");
							buttonHtml.push("<a href=\"javascript:delDictionary('"+value+"');\">删除</a>"," ");
						}
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]]
		});
	}
	
	function search(){
		var dictType = $("#dictType").val();
		initTreeData("${cxt}/dictionary/ajax/list.action?dictType="+dictType);
	}
	
	//添加一级数据字典
	function addDictionary(){
	   $("#win").window({ 
			title:"添加数据字典",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/dictionary/preAdd.action",
		    modal:true
		});
	}
	//添加数据字典
	function addChildDictionary(parent){
	   $("#win").window({ 
			title:"添加数据字典",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/dictionary/preAddChildren.action?id="+parent,
		    modal:true
		});
	}
	//编辑数据字典
	function editDictionary(id){
	   $("#win").window({ 
			title:"修改数据字典",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/dictionary/preUpdate.action?id="+id,
		    modal:true
		});
	}
	
	//删除数据字典
	function delDictionary(id){
		if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/dictionary/ajax/delete.action",{deleteIds:id},function call(result){
			  	if(result.status){
			  		reloadTree();
				}else{
					top.showMsg("error",result.message);
				}	
			},'json');
		}
	}
	function reloadTree(){
		$("#dictionaryTree").treegrid("reload");
		return false;
	}
	
	function getChildren(){
		var node = $('#dictionaryTree').treegrid('getSelected');
		if (node){
			var nodes = $('#dictionaryTree').treegrid('getChildren', node.code);
		} else {
			var nodes = $('#dictionaryTree').treegrid('getChildren');
		}
		var s = '';
		for(var i=0; i<nodes.length; i++){
			s += nodes[i].code + ',';
		}
		alert(s);
	}
	function getSelectedId(){
		var node = $('#dictionaryTree').treegrid('getSelected');
		if(node){
			return node.id;
		}
		return "";
	}
	
	function removeNode(){
		var node = $('#dictionaryTree').treegrid('getSelected');
		if (node){
			$('#dictionaryTree').treegrid('remove', node.id);
		}
	}
	
	   //回调方法
	function callbackAdd(node,parentId){    
		$('#dictionaryTree').treegrid('append', {
			pid: parentId,
			node: node
		});
	} 	
	   
	//回调方法
	function callbackUpdate(node){
		$('#dictionaryTree').treegrid('update', node);
	} 
</script>
<!-- 弹出窗口开始 -->
<div id="win"></div>
</body>
</html>