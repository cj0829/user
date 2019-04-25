<%@page import="org.csr.core.constant.YesorNo"%>
<%-- <%@page import="org.csr.exam.constant.ImportApprovalStatus"%> --%>
 <%@page import="org.csr.common.user.constant.ImportApprovalStatus"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@include file="../common/common.jsp"%>
  	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>试题通过数--${system_name}</title>  
  
</head> --%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>安全角色--${system_name}</title>  
  
</head>
<body>
	<div class="e-tabbox">
		<div class="form-tab-wrap">
			<table id="datagridList" > </table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<!-- <button class="btn mr25" type="button" onclick="save();">保存</button> -->
		<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
	</div>
<script  type="text/javascript">

var parentDiv;
var reloadDataFn;
function iniWindowParam(parentId,fn){
	this.parentDiv=parentId;
	this.reloadDataFn=fn;
}

var waitingbar;
	$(function(){
		 $(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		 jQuery(document).ready(function(){
				$(window).unbind('#winBody').bind('resize.winBody', function(){
					$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
				});
			});
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/userApprove/ajax/findUserUnPassTotal.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			
			queryParams:function(){
				try{
					return {
						"importFileId":"${id}"
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{title:'用户编码',field:'id',width:100},
				{title:'用户名',field:'loginName',width:120},
				{title:'名称',field:'name',width:120},
				{title:'性别',field:'gender',width:80,dictionary:'gender'},
				{title:'email',field:'email',width:100},
				{title:'拒绝原因',field:'opinion',width:100,formatter:function(value,rec,index){
					return rec.userTaskInstance.opinion;
				}},
				{title:'操作',field:'id_1',width:100,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:edit('"+rec.id+"','"+rec.loginName+"');\">编辑</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	});
	
	// 编辑
	function edit(userId,loginName){
		var divAddMange=top.document.createElement("DIV");
		top.document.body.appendChild(divAddMange);
		top.$(divAddMange).window({ 
			title:"编辑",
		 	onClose:function(){
		    	top.jQuery(divAddMange).window("destroy");
		    },
		    width:700,
		    height:500,
		    onIfarmeLoad:function(t){
				var iframe=top.jQuery(divAddMange).window("getIframe");
				if(iframe){
					iframe.contentWindow.iniWindowParam(divAddMange,reloadData);
				}
			},
			iframe:"${cxt}/userApprove/preUpdate.action?id="+userId,
		    modal:true
		});
	}
	
	/**进入流程处理页面*/
	function processFlow(userTaskInstanceId){
		$("#win").window({
			title:"流程处理",
		    width:700,
		    height:500,
		    iframe:"${cxt}/taskInstance/preProcessFlow.action?winParentId=win&userTaskInstanceId="+userTaskInstanceId,
		    modal:true
		});
	}

	
	function editSingle(id){
		$("#datagridList").datagrid("clearChecked");
		$("#datagridList").datagrid("selectRecord",id);
		edit();
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		if(reloadDataFn){
			reloadDataFn();
		}
		return false;
	}
    
   //回调方法
	function callbackPage(){
		reloadData();
	};
	function cancelWindow(){
		top.jQuery("#winDetail").window("close");
	};
	
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
