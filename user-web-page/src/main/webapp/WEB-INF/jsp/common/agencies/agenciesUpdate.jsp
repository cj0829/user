<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>添加外部组织--${system_name}</title>
<style>
.panel-body-noheader {
	overflow: auto;
}
</style>
</head>
<body>
<form action="#" method="get" name="orginternalForm" id="orginternalForm" onsubmit="return updateOrginternal(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<c:if test="${SECURITY_CONTEXT.loginName == 'super'}">
		<tr>
		<th class="e-form-th" style="width:92px;"><label>上级组织名称</label></th>
			<td class="e-form-td">
				<div class="m-expand-tree drop-down-tree"><input id="agenciesTree" name="parentId" class="easyui-combotree" style="width:200px; height:25px;" value="${agencies.parentId}" /></div>
			</td>
		</tr>
		</c:if>
		<c:if test="${SECURITY_CONTEXT.loginName != 'super'}">
		<tr>
			<th class="e-form-th" style="width:92px;"><label>上级组织名称</label></th>
			<td class="e-form-td">
				<span>${empty parentName?'根节点':parentName}</span>
				<input type="hidden" id="parentId" name="parentId" value="${agencies.parentId}" />
			</td>
		</tr>
		</c:if>
		<tr>
			<th class="e-form-th"><label>组织机构名称</label></th>
			<td class="e-form-td">
				<input class="easyui-textbox"
				style="width:200px;height: 25px;" type="text" id="name"
				maxlength="128" msg="组织名称" data-options="required:true" name="name" value="${agencies.name}" />
			</td>
		</tr>
		<c:if test="${SECURITY_CONTEXT.loginName == 'super'}">
		<tr>
			<th class="e-form-th"><label>域</label></th>
			<td class="e-form-td">
				<div class="m-expand-tree drop-down-tree">
				<input style="width:200px;height: 25px;" class="easyui-combotree"
				type="text" id="orgId" msg="域"
				data-options="url:'${cxt}/organization/ajax/findDropDownTree.action',method:'post',required:true"
				value="${agencies.organizationId}" name="org" style="width:170px;" />
				</div>
			</td>
		</tr>	
		</c:if>
		<c:if test="${SECURITY_CONTEXT.loginName == 'super'}">
		<tr>
			<th class="e-form-th"><label>组织类型<br/>（机构、公司）</label></th>
			<td class="e-form-td">
				<core:select class="easyui-combobox" style="width:200px;height: 25px;" id="type" name="type" dictType="AgenciesType" value="${agencies.type}" />
				（如果选择公司，将无法在创建下级公司了）
			</td>
		</tr>
		</c:if>
		
	</table>
</div>
<div class="e-tab-btnbox">
	<input type="hidden" id="id" name="id" value="${agencies.id}"/>
	<button class="btn mr25" type="submit">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};


 	jQuery(document).ready(function(){
		//查询本地学习注册主管
		jQuery("#localRegAdminListTable").datagrid({
			nowrap: true,
			url:'${cxt}/agenciesLocalRegAdmin/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:function(){
				return {agenciesId:"${agencies.id}"};
			},
			columns:[[
				{field:'userId',title:'用户编码号',width:100},
				{field:'loginName',title:'登录名',width:150},
				{field:'name',title:'登录名',width:150},
				{field:'agenciesName',title:'组织名',width:150},
				{title:'操作',field:'id_1',width:80,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:deleteLocalRegAdmin('"+rec.agenciesLocalRegAdminId+"');\">删除</a>"," ");
						return buttonHtml.join("");
					}
				}
			]],
			pagination:true
		});
		
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-40);
		});
		<c:if test="${SECURITY_CONTEXT.loginName == 'super'}">
		$('#agenciesTree').combotree({
		    url:"${cxt}/agencies/ajax/findTreeNotIncludedList.action?id=${agencies.id}",
		    checkbox:false,
		    multiple:false,
		    cascadeCheck:false,
		    animate:true
		});
		</c:if>
	});
 
 	//
 	function submit(){
 		$("#orginternalForm").submit();
 	}
 	
	//提交表单的方法
	function updateOrginternal(form1){
		//提交表单
		 if($('#orginternalForm').form('validate')){ 
			var params=jQuery('#orginternalForm').serialize();
			WaitingBar.getWaitingbar("updateagencies","数据修改中，请等待...").show();
			jQuery.post("${cxt}/agencies/ajax/update.action",params,function call(data){
				WaitingBar.getWaitingbar("updateagencies").hide();
				if(data.status){
					showMsg("info","内部组织添加成功");				
					top.callbackPage(${parentId});
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","内部组织添加失败！");
					}
				}
			},'json');
		}	  
		return false;
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	
</script>
</body>
</html>
