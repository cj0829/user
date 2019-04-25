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
		<div title="修改信息" style="height:310px;">
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
						
					<tr><th class="e-form-th" style="width:92px;">编辑打卡规则</th>
					<td class="e-form-td">
						<button class="btn" type="button" onclick="topWin();">添加成员</button>
						<button class="btn" type="button" onclick="updateTable1();">添加非必须打卡</button>
						<button class="btn" type="button" onclick="updateTable2();">添加必须打卡</button>
						<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"></table>
					</td>
					</tr>
					</table>
						<%-- <user:usergrid id="managerUser" multiple="true" name="userId"  style="width:130px;"/> --%>
						<table id="datagridList"  width="100%" border="0" cellpadding="0" cellspacing="0"></table>
					
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
		window.onload=function(){
			//加载数据
				$("#groupForm").form("load",{
				id:"${group.id}",
				groupName:"${group.groupName}",
				content:"${group.content}",
			});
		};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
		
		$('#datagridList').datagrid({nowrap: true,
			url:"${cxt}/mobile/userGroup/ajax/list.action?groupId=${groupId}",
			collapsible:true,
			showfolder:true,
			
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			showPageList : false,
			columns:[[
 				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:180},
				{title:'机构',field:'agenciesName',width:100},
				{title:'状态',field:'isMust', dictionary:"yesOrNo",width:100},
				{title:"操作",field:"id_1",width:80,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a href=\"javascript:deletePeople('"+rec.userId+"');\">删除人员</a>"," ");
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}}
				
			]],
			pagination:true
		});
	});
 	function updateTable1(){
 		try{
			if($("#groupForm").form("validate")){
				WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#groupForm").serialize();
				var checkedsUser=$("#datagridList").datagrid("getChecked");
				if(!checkedsUser || checkedsUser.length<=0){
					showMsg("info","请您选择用户");
					return;
				}
				var ids=[];
				var userIds=[];
				for(var i=0;i<checkedsUser.length;i++){
					
					ids.push(checkedsUser[i].id);
					userIds.push(checkedsUser[i].userId);
				}
				jQuery.post("${cxt}/mobile/userGroup/ajax/updateTable.action",{ids:ids.join(","),userIds:userIds.join(","),isMust:2,groupId:"${groupId}"},function call(data){
					WaitingBar.getWaitingbar("addgroup").hide();
					if(data.status){
						showMsg("info","修改成功");
						$('#datagridList').datagrid("reload");
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
 	function updateTable2(){
 		try{
			if($("#groupForm").form("validate")){
				WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#groupForm").serialize();
				var checkedsUser=$("#datagridList").datagrid("getChecked");
				if(!checkedsUser || checkedsUser.length<=0){
					showMsg("info","请您选择用户");
					return;
				}
				var ids=[];
				var userIds=[];
				for(var i=0;i<checkedsUser.length;i++){
					
					ids.push(checkedsUser[i].id);
					userIds.push(checkedsUser[i].userId);
				}
				jQuery.post("${cxt}/mobile/userGroup/ajax/updateTable.action",{ids:ids.join(","),userIds:userIds.join(","),isMust:1,groupId:"${groupId}"},function call(data){
					WaitingBar.getWaitingbar("addgroup").hide();
					if(data.status){
						showMsg("info","修改成功");
						$('#datagridList').datagrid("reload");
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
 	//删除人员
	function deletePeople(userId){
		showConfirm("确认是否删除该人员?",function(){
 		try{
			var param = {userId:userId,groupId:"${groupId}"};
			jQuery.post("${cxt}/mobile/userGroup/ajax/deletePeople.action",param,function call(data){
				WaitingBar.getWaitingbar("addgroup").hide();
				if(data.status){
					showMsg("info","修改成功");
					$("#datagridList").datagrid("clearChecked");
					$("#datagridList").datagrid("reload");
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","添加组管理失败!");
					}
				}
			},"json");
		} catch (e) {}
		});
		return false;
 	}
	//提交表单的方法
	function saveGroup(){
		try{
			if($("#groupForm").form("validate")){
				WaitingBar.getWaitingbar("addgroup","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#groupForm").serialize();
				var groupName=$("#groupName").val();
				var content=$("#content").val();

				jQuery.post("${cxt}/mobile/userGroup/ajax/update.action",{groupName:groupName,content:content,groupId:"${groupId}"},function call(data){
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
		    iframe:"${cxt}/mobile/group/popPeople.action?groupId=${groupId}",
		    modal:true
		});	
	}
	function addUser(){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:600,
			iframe:"${cxt}/mobile/userGroup/preAddUser.action?id=${groupId}",
			modal:true
		});
	}
	
	function reload(){
		$("#datagridList").datagrid("reload");
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
