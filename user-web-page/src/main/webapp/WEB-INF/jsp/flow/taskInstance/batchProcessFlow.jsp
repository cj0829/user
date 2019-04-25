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
					<textarea name="opinion" id="opinion" class="easyui-validatebox" data-options="required:true" rows="6" cols="50"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<!-- start one 内容 -->
	<div class="form-tab-wrap mt10">
	<!--start 表格操作btn -->
	<div class="e-tabbtn-box mb2" id="e-tabbtn-box">
		<span class="fl title">当前批量审批流程</span>
	</div>
	<!--end 表格操作btn -->
	<table id="datagridList" > </table>
	</div>
</div>
<!-- start 操作按钮 -->
<div class="e-tab-btnbox">
	<input name="userTaskInstanceId" value="${userTaskInstance.id}" type="hidden"/>
	<c:if test="${isTo}">
		<button class="btn mr25" type="submit" >通过</button>
	</c:if>
	<c:if test="${isreject}">
		<button class="btn mr25" type="button" onclick="taskRebut()">驳回</button>
	</c:if>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
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
		var checkeds= top.getChecked();
		$('#datagridList').datagrid({
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			data:checkeds,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{title:'流程名称',field:'currentName',width:100},
				{title:'审批意见',field:'opinion',width:100},
				{title:'内容',field:'context',width:100},
				{title:'操作类型',field:'operType',dictionary:"FormOperType",width:100},
				{title:'操作',field:'id_1',width:100,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a id='menu_"+rec.id,"' ");
						buttonHtml.push("href=\"javascript:processFlow('"+rec.id+"');\"");
						buttonHtml.push("class=\"easyui-butcombo-menu\">删除</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
			]]
		});
	});
	
	function searchFormInfo(userTaskInstanceId){
		top.searchFormInfo(userTaskInstanceId);
	}
	//提交流程
	function taskPass(){
		try{
			if($("#processForm").form("validate")){
				var row=$("#datagridList").datagrid("getRows");
				if(row==null || row.length<=0){
					showMsg("error","请您选择要审批的试题");
					return;
				}
				var ids=[];
				for(var i=0;i<row.length;i++){
					ids.push(row[i].id);
				}
				waitingbar=jQuery.getWaitingbar("文件正在导入，请等待...",true,"${jsPath}");
				var param=$.param({userTaskInstanceId:ids,opinion:$("#opinion").val()},true);
				jQuery.post("${cxt}/taskInstance/ajax/taskPass.action",param,function call(data){
					waitingbar.hide();
					if(data.status){
						$("#datagridList").datagrid("clearChecked");
						showMsg("info","添加库成功");
						top.reloadData();
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
	
	//驳回流程
	function taskRebut(){
		try{
			if($("#processForm").form("validate")){
				var row=$("#datagridList").datagrid("getRows");
				if(row==null || row.length<=0){
					showMsg("error","请您选择要审批的试题");
					return;
				}
				var ids=[];
				for(var i=0;i<row.length;i++){
					ids.push(row[i].id);
				}
				var param=$.param({userTaskInstanceId:ids,opinion:$("#opinion").val()},true);
				jQuery.post("${cxt}/taskInstance/ajax/taskRebut.action",param,function call(data){
					if(data.status){
						$("#datagridList").datagrid("clearChecked");
						showMsg("info","添加库成功");
						top.reloadData();
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
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
