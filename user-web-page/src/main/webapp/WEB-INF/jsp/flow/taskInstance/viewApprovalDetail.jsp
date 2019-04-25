<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../common/common.jsp"%>
	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
 	<title>添加库--${system_name}</title>  
</head>
<body>
<div class="e-tabbox">
	<div class="form-tab-wrap mt10">
	<!--start 表格操作btn -->
	<div class="e-tabbtn-box mb2" id="e-tabbtn-box">
		<span class="fl title">流程审批记录<a href="javascript:searchFormInfo(${userTaskInstance.id});"></a></span>
	</div>
	<!--end 表格操作btn -->
	<table id="datagridList" > </table>
	</div>
</div>
<!-- start 操作按钮 -->
<div class="e-tab-btnbox">
	<input name="userTaskInstanceId" value="${userTaskInstance.id}" type="hidden"/>
	<%-- <button class="btn cancel" type="button" onclick="cancelWindow();" >取消</button>--%>
</div>

<script type="text/javascript"> 
var waitingbar;
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
	};
	jQuery(document).ready(function(){
		
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
	});
	
	$('#datagridList').datagrid({
		nowrap: true,
		url:"${cxt}/taskInstance/${fncode}/ajax/listTaskProcessByUserTaskInstanceId.action?userTaskInstanceId=${userTaskInstance.id}",
		collapsible:true,
		showfolder:true,
		border:false,
		emptyMessage:true,
		fitColumns:true,
		idField:'id',
		onLoadSuccess:function(){
			$(this).datagrid("resize");
		},
		scrollbarSize:0,
		columns:[[
			{title:'审批结果',field:'taskNodeName',width:80},
			{title:'处理意见',field:'opinion',width:300},
			{title:'处理时间',field:'processingTime',width:130},
			{title:'处理者 ',field:'currentLoginName',width:100},
		]]
	});
});
	
function searchFormInfo(userTaskInstanceId){
	top.searchFormInfo(userTaskInstanceId);
}
	
function cancelWindow(){
	parent.jQuery("#win").window("close");
}
</script>
</body>
</html>
