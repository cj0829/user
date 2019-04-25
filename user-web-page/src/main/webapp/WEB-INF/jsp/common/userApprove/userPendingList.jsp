<%@page import="org.csr.common.user.constant.ImportApprovalStatus"%>
<%@page import="org.csr.core.constant.YesorNo"%>
<%@page import="org.csr.core.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
  	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<title>用户待批列表--${system_name}</title>  
  
</head>
<body>
	
	<div class="e-tabbox">
		<div class="form-tab-wrap">
			<table id="datagridList" > </table>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" onclick="passUser();">通过</button>
		<button class="btn mr25" type="button" onclick="refusalUser();">驳回</button>
		<button class="btn cancel" type="button" onclick="cancelWindow();">关闭</button>
	</div>
<script  type="text/javascript">
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
			url:'${cxt}/userApprove/ajax/userPendingList.action',
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
						"auto":true,//自动拼接
						"importFileId":"${importFileId}"
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户编码',field:'id',width:100},
				{title:'用户名',field:'loginName',width:120},
				{title:'名称',field:'name',width:120},
				{title:'性别',field:'gender',width:80,dictionary:'gender'},
				{title:'email',field:'email',width:100},
				{title:'组织',field:'agenciesName',width:90}
			]],
			pagination:true
		});
	});
	
	//通过用户
	function passUser(){
		
		var checked=$("#datagridList").datagrid("getChecked");
		if(checked==null || checked.length<=0){
			showMsg("error","请您选择用户");
			return;
		}
		var formIds=[];
		for(var i=0;i<checked.length;i++){
			formIds.push(checked[i].id);
		}
		var param=$.param({userCommandIds:formIds},true);
		showConfirm("确认通过?",function(){
			//提交表单
			WaitingBar.getWaitingbar("passUser","用户提交中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/userApprove/ajax/passUsers.action",param,function call(data){
				if(data.status){
					callbackPage();
					showMsg("info","审批通过");
					top.callbackPage();
				}else{
					$("#datagridList").datagrid('clearChecked');
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","审批失败!");
					}
				}
				WaitingBar.getWaitingbar("passUser").hide();
			},'json');
		});
	}
	
	//驳回用户
	function refusalUser(){
		var checked=$("#datagridList").datagrid("getChecked");
		if(checked==null || checked.length<=0){
			showMsg("error","请您选择用户");
			return;
		}
		showConfirm("您确定要驳回？<br>意见：<textarea id=\"explain_text\" style=\"width: 200px;height: 100px;\" onchange=\"updateCounter(255,'explain_text','rdescriptionsShow');\" onclick=\"updateCounter(255,'explain_text','rdescriptionsShow');\"   onkeydown=\"updateCounter(255,'explain_text','rdescriptionsShow');\" onkeyup=\"updateCounter(255,'explain_text','rdescriptionsShow');\"></textarea><p>字数限制:255</p><p id=\"rdescriptionsShow\"></p>",function(){
			var formIds=[];
			for(var i=0;i<checked.length;i++){
				formIds.push(checked[i].id);
			}
			var param=$.param({userCommandIds:formIds,explain:$("#explain_text").val()},true);
			WaitingBar.getWaitingbar("refusalUser","用户驳回中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/userApprove/ajax/refusalUser.action",param,function call(data){
				if(data.status){
					callbackPage();
					showMsg("info","驳回成功");
					top.callbackPage();
				}else{
					$("#datagridList").datagrid('clearChecked');
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","驳回失败!");
					}
				}
				WaitingBar.getWaitingbar("refusalUser").hide();
			},"json");
		});
		
	}
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('clearChecked');
		$("#datagridList").datagrid('reload');
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
