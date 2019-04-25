<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../../WEB-INF/jsp/common/common/common.jsp"%>
 	<title>编辑${userTitle}--${system_name}</title>  
</head>
<body>
<div id="easyui-tabs" style="width:100%;" class="expand-tabs">
	<div title="主要 ">
	<form action="#" method="get" name="userForm" id="userForm" onsubmit="return updateUser(this)">
	<div class="e-tabbox main-expand-form">
	 	<table class="e-form-tab" width=100%>
			<tr>
				<th class="e-form-th" style="width:80px;" ><label>用户名</label></th>
				<td class="e-form-td">${user.loginName}</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>姓名</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="name" maxlength="64" msg="名" data-options="required:true" name="name"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th">组织机构</th>
				<td class="e-form-td">
					<div class="m-expand-tree drop-down-tree"><input id="agenciesTree" name="agencies" class="easyui-combotree" style="width:200px; height:25px;" value="${user.agenciesId}" /></div>
				</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>管理人</label></th>
				<td class="e-form-td">
				<user:usergrid id="managerUser" name="managerUser" value="${user.managerUserId}"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>性别</label></th>
				<td class="e-form-td">
					<div class="expand-dropdown-select" style="display:inline;">
					<core:select className="easyui-combobox"  id="gender" name="gender" dictType="gender" style="width:200px;height:25px;" data-options="required:true,panelHeight:'80px'" />
					</div>
				</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>电子邮件</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="email" maxlength="128" msg="电子邮件" data-options="validType:'email',regChars:[]" name="email" />
				</td>
			</tr>
		</table>
		</div>
		</form>
		<div class="e-tab-btnbox">
			<button class="btn mr25" type="button" onclick="submit();">保存</button>
			<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
		</div>
	</div>
	<div title="内容">
	</div>
</div>
<!--内容开始-->
<!--内容结束-->
<script  type="text/javascript">
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".tabs").height()-40);
	};
	function setHeight(){
		parentObject.iframeObject.setAttribute('height', $(this).height()-$(".e-tab-btnbox").height()-30);
	}
   jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".tabs").height()-40);
		});
		$('#agenciesTree').combotree({
		    url:"${cxt}/agencies/ajax/treeList.action?agenciesType=1",
		    checkbox:false,
		    multiple:false,
		    cascadeCheck:false,
		    animate:true
		});
	});
 	
 	$("#userForm").form("load",{
 		name:"${user.name}",
 		root:"${user.root}",
 		userStatus:"${user.userStatus}",
 		userType:"${userInfo.userType}",
 		gender:"${user.gender}",
 		email:"${user.email}",
	});
 	function submit(){
 		$("#userForm").submit();
 	}
 	
	var status = '${status}';
		//提交表单的方法
		function updateUser(form1){
			//提交表单
			 if($('#userForm').form('validate')){
				var params=jQuery('#userForm').serialize();
				jQuery.post("${cxt}/user/ajax/update.action",params,function call(data){
					if(data.status){
						showMsg("info","内部用户编辑成功");
						cancelWindow();
						top.callbackPage();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","内部用户编辑失败!");
						}
					}
				},'json');
			}
			return false;
		}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>
