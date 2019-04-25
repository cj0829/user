<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords"/>
	<%@ include file="../common/common.jsp"%>
 	<title>签到系统--${system_name}</title>  
</head>
  
  <body>
  	<form action="" name="taskNodeForm" id="taskNodeForm">
	<div class="e-tabbox">
		<div class="form-tab-wrap">
			<!-- start one -->
			<div class="e-form-mod">
			</div>
			<!-- end one -->
			
			<!--start 表格操作btn -->
			<div class="e-tabbtn-box mb2" id="e-tabbtn-box">
				<span class="fl title">流程关系列表</span>
			</div>
			<!--end 表格操作btn -->
			<table id="datagridList" > </table>
		</div>	
	</div>
	</form>
	<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height());
	};

	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height());
		});
		$("#datagridList").datagrid({
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			toolbar:"#e-tabbtn-box",
			idField:'id',
			scrollbarSize:0,
			url:"${cxt}/taskNode/ajax/listByTaskTemp.action?taskTempId=${taskTempId}",
			columns:[[
				//{field:'ck',checkbox:true},
				{title:'流程名称',field:'name',width:80},
				{title:'下一流程',field:'toNodeName',width:80},
				{title:'回退到流程',field:'rejectNodeName',width:80},
				{title:'管理人员',field:'chainName',width:200,formatter:function(value,rec){
					return "<span title=\""+value+"\">"+value+"</span>";
				}},
				{title:'操作',field:'id_1',width:100,selected:true,
					formatter:function(value,rec){
						if(rec.nodeType!="EndNode"){
							var buttonHtml=[];
							buttonHtml.push("<a id='menu_"+rec.id,"' ");
							buttonHtml.push("href=\"javascript:editShow('"+rec.id+"');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">编辑节点审批人</a>"," ");
							return "<span>"+buttonHtml.join("")+"</span>";
						}else{
							return "";
						}
					}
				}
				]],
			pagination:true
		});
	});
	<%--添加审批关系人窗口--%>
	function editShow(id){
		var divEidt=top.document.createElement("DIV");
		top.document.body.appendChild(divEidt);
		top.jQuery(divEidt).window({
				title:"编辑审批节点管理人",   
			    width:700,    
			    height:400,
			    onClose:function(){
			    	top.jQuery(divEidt).window("destroy");
			    	$("#datagridList").datagrid("reload");
			    },
			    iframe:"${cxt}/taskNodeApprovalChain/preAdd.action?taskNodeId="+id,
				onIfarmeLoad:function(t){
					var iframe=top.jQuery(divEidt).window("getIframe");
					if(iframe){
						iframe.contentWindow.iniWindowParam(divEidt);
					}
				},
				modal:true
			});
		
	}
	</script>
  </body>
</html>
