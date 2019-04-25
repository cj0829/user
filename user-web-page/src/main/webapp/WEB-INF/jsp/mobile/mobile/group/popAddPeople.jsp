<%@page import="org.csr.common.flow.constant.FormApproverType"%>
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
  	<!-- start tab切换按钮 -->
  	<div class="e-tabbar">
  		<a href="javascript:;" class="t-btn curr">人员</a>
  	</div>
	<!-- end tab切换按钮 -->
	<div class="e-tabbox">
		<div class="e-search-wrap">
		<form action="" onsubmit="return reload();">
		<table width="100%">
			<tr>
				<td class="e-search-con">
				<ul>
					<li class="e-search-td"><label class="mr8 lab" style="width:78px;">用户名:</label><input id="loginName" name="loginName" type="text" style="width:115px; height:25px;" class="easyui-textbox"/></li>
					<li class="e-search-td"><label class="mr8 lab" style="width:78px;">姓名:</label><input id="name" name="name" type="text" style="width:115px; height:25px;" class="easyui-textbox"/></li>
					<li class="e-search-td"><label class="mr8 lab" style="width:30px;">社区:</label>
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="rememberAll:true" style="width:115px; height:27px;"/>
					</li>
				</ul>
				</td>
				<td class="e-search-btn-box" style="width:94px;">
					<button class="btn" type="submit" >查询</button>
				</td>
			</tr>
		</table>
		</form>
		</div>
		<div class="form-tab-wrap mt10">
			<table id="datagridList"></table>
		</div>	
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" onclick="submitChange()">保存</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
	</div>
	<script  type="text/javascript">
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};

	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
		});
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/user/ajax/findAuthorityList.action',
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
					"loginName":$("#loginName").val(),
					"name":$("#name").val(),
					"agenciesId":$("#agenciesId").combogrid("getValue")
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户名',field:'loginName',width:150},
				{title:'用户编码',field:'id',width:150},
				{title:'姓名',field:'name',width:150},
				{title:'组织',field:'agenciesName',width:150},
				]],
			pagination:true
		});
		
	});
	<%--选择表格--%>
	function submitChange(){
		var checkedsUser=$("#datagridList").datagrid("getChecked");
		if(!checkedsUser || checkedsUser.length<=0){
			showMsg("info","请您选择用户");
			return;
		}
		var userIds=[];
		for(var i=0;i<checkedsUser.length;i++){
			
			userIds.push(checkedsUser[i].id);		
		}
		<%--获取，当前的用户id数据--%>
		jQuery.post("${cxt}/mobile/userGroup/ajax/add.action",{userids:userIds.join(","),groupId:"${groupId}"},function call(data){
			if(data.status){
				var iframe=top.jQuery("#win").window("getIframe");
				iframe.contentWindow.reload();
				cancelWindow();
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","添加$!{templateName}失败!");
				}
			}
		},"json");
		
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
	
	function reload(){
		try{
		$("#datagridList").datagrid("reload");
		}catch (e) {alert(e);}
		return false;
	}
	
	function cancelWindow(){
		top.jQuery("#popPeople").window("close");
	};//
	</script>
  </body>
</html>
