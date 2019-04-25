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
<form action="#" method="get" name="processForm" id="processForm" onsubmit="return taskPass();">
<div class="e-tabbox">
	<!-- start one 内容 -->
	<div class="e-form-mod mt10">
		<table class="e-form-tab main-expand-form" width="100%" >
			<tr>
				<th class="e-form-th pl30" style="width:150px;" ><label></label></th>
				<td class="e-form-td">当前流程节点：${taskNode.name}</td>
			</tr>
			<tr>
				<th class="e-form-th pl30"><label>审批意见：</label></th>
				<td class="e-form-td">
					<textarea name="opinion" id="opinion" class="easyui-validatebox" data-options="required:true"  rows="6" cols="50"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<!-- start one 内容 -->
	<div class="form-tab-wrap mt10">
	<!--start 表格操作btn -->
	<div class="e-tabbtn-box mb2" id="e-tabbtn-box">
		<span class="fl title">流程审批记录<a href="javascript:searchFormInfo(${userTaskInstance.id});"></a></span>
	</div>
	</div>
</div>
<!-- start 操作按钮 -->
<div class="e-tab-btnbox">
	<input name="userTaskInstanceId" value="${userTaskInstance.id}" type="hidden"/>
	<c:if test="${isTo}">
		<button class="btn mr25" type="submit" >提交到${toNode.name}</button>
	</c:if>
	<c:if test="${isreject}">
		<button class="btn mr25" type="button" onclick="taskRebut()">驳回到${rejectNode.name}</button>
	</c:if>
	
	<button class="btn cancel" type="button" onclick="cancelWindow();" >取消</button>
</div>
<!-- end 操作按钮 -->
</form>

<script type="text/javascript"> 

var waitingbar;
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
	};
	jQuery(document).ready(function(){
		
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
	});
	
});
	
	function searchFormInfo(userTaskInstanceId){
		top.searchFormInfo(userTaskInstanceId);
	}
	//提交流程
	function taskPass(){
		try{
			if($("#processForm").form("validate")){
				var params=jQuery('#processForm').serialize();
				waitingbar=jQuery.getWaitingbar("正在审批${title}，请等待...",true,"${jsPath}");
				jQuery.post("${cxt}/taskInstance/${fncode}/ajax/taskPass.action",params,function call(data){
					waitingbar.hide();
					if(data.status){
						showMsg("info","审批成功");
						parent.reloadData();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","审批失败!");
						}
					}
				},"json").error(function(data) {
					waitingbar.hide();
					if(data.status){
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","审批失败!");
						}
					}
				});
			}	
		}catch(e){alert(e);}
		return false;
	}
	
	//驳回流程
	function taskRebut(){
		try{
			if($("#processForm").form("validate")){
				var params=jQuery('#processForm').serialize();
				jQuery.post("${cxt}/taskInstance/${fncode}/ajax/taskRebut.action",params,function call(data){
					if(data.status){
						showMsg("info","添加库成功");
						parent.reloadData();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加库失败!");
						}
					}
				},"json");
			}	
		}catch(e){alert(e);}
		return false;
	}
	
	function cancelWindow(){
		parent.jQuery("#win").window("close");
	}
</script>
</body>
</html>
