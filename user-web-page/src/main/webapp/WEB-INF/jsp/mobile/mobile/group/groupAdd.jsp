<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加组管理--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="groupForm" id="groupForm" onsubmit="return saveGroup()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="组信息" style="height:310px;">
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">组名称</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="groupName" name="groupName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">组描述</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="content" name="content" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					
					
					</table>
				</div>
			</div>
		</div>
		<!--tab结束-->
		
	</div>
	<div style="text-align:center;">
	  	<div class="e-tab-btnbox">
			<button class="btn mr25" type="submit" >保存</button>
			<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
		</div>		
	</div>
	</form>
	<!--内容结束-->
</div>
<!--弹出窗口结束-->
<script type="text/javascript">
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
		
		$('#datagridList').datagrid({nowrap: true,
			url:"${cxt}/mobile/userGroup/ajax/findCheck.action",
			collapsible:true,
			showfolder:true,
			toolbar:[{
				text:"添加成员",
				handler: function(){topWin()}
			}],
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			showPageList : false,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'机构',field:'agenciesName',dictionary:'yesOrNo',width:100},
				{title:'是否必须打卡',field:'userId',width:300}
			]],
			pagination:true
		});
	});
	//提交表单的方法
	function saveGroup(){
		try{
			if($("#groupForm").form("validate")){
				WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#groupForm").serialize();
				jQuery.post("${cxt}/mobile/group/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addgroup").hide();
					if(data.status){
						showMsg("info","添加组管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加组管理失败!");
						}
					}
				},"json");
			}
		} catch (e) {}
		return false;
	}
	
	<%--本地调用，添加用户-需要把对象转化为对象--%>
	function addPeople(userList){
		var userRows=$('#datagridList').datagrid("getRows");
		for(var i=0;i<userList.length;i++){
			
			$('#datagridList').datagrid("appendRow",{
				id:userList[i].uuid,
				userId:userList[i].id,
				user:userList[i].name
			});
		
		}
		$("#datagridList").datagrid("resize");
	}
	
	function topWin(){
		top.$("#popPeople").window({ 
			title:"选择组成员",   
		    width:700,
		    height:568,
		    iframe:"${cxt}/mobile/group/popPeople.action",
		    modal:true
		});
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
