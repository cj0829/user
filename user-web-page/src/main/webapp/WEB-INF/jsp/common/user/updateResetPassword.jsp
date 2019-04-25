<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>编辑${userTitle}--${system_name}</title>  
</head>
<body>
<!--内容开始-->
<div class="e-tabbar">
	<a class="t-btn" href="${cxt}/user/preUpdate.action?id=${userId}" ><span class="btntxt">主要</span></a>
	<a class="t-btn curr" href="javascript:;" ><span class="btntxt">密码</span></a>
	<a class="t-btn" href="${cxt}/avatar/userHeaderImg.action?userId=${userId}" >用户头像</a>
	<a class="t-btn" href="${cxt}/user/preUserPreferences.action?userId=${userId}" ><span class="btntxt">首选项</span></a>
	<a class="t-btn" href="${cxt}/userMore/preAdd.action?userId=${user.id}" >用户详细信息</a>
</div>
<form action="#" method="get" name="userForm" id="userForm" onsubmit="return updateResetPassword(this)">
<div class="e-tabbox main-expand-form">
	<div class="e-form-mod">
		<h2 class="e-form-mod-tit mb5">更改密码</h2>
		<table class="e-form-tab" width=100%>
			<tr>
				<th class="e-form-th" style="width:80px;" ><label>用户名</label></th>
				<td class="e-form-td">${userLoginName}</td>
			</tr>
			<!-- <tr>
				<th class="e-form-th"><label>旧密码</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" id="oldPassword" name="oldPassword" data-options="required:true"  msg="旧密码" maxlength="128" style="width:200px;height: 25px;" type="password"/>
				</td>
			</tr> -->
			<tr>
				<th class="e-form-th"><label>新密码*</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" id="password" name="password"  data-options="validType:'length[1,30]' "  msg="新密码" maxlength="128" style="width:200px;height: 25px;" type="password"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>确认密码*</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" id="aliases" name="aliases" data-options="validType:'length[1,30]' " msg="确认密码" maxlength="0" style="width:200px;height: 25px;" type="password"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="e-form-mod mt15">
		<h2 class="e-form-mod-tit mb5">密码提示问题</h2>
		<table class="e-form-tab" width=100%>
			<tr>
				<th class="e-form-th" style="width:150px;" ><label>提示问题</label></th>
				<td class="e-form-td">
					<div class="expand-dropdown-select" style="display:inline;">
					<input class="easyui-combobox" id="passwordProblem" name="passwordProblem" value="${userPasswordProblem.id}"
					 maxlength="128" style="width:200px;height: 25px;" type="text"
					 data-options="url:'${cxt}/user/ajax/findUserPasswordProblemList.action?userId=${userId}',valueField:'id',textField:'question',method:'post'"/>
					</div>
				</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>提示问题答案</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" id="answer" name="answer"  value="${userPasswordProblem.answer}"  maxlength="128" style="width:200px;height: 25px;" type="text"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th"><label>确认提示问题答案</label></th>
				<td class="e-form-td">
				<input class="easyui-textbox" id="answerRepeat" name="answerRepeat"  value="${userPasswordProblem.answer}"  maxlength="0" style="width:200px;height: 25px;" type="text"/>
				</td>
			</tr>
		</table>
	</div> <%-- --%>
	<input type="hidden" id="userId" name="userId" value="${userId}"/>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<!--内容结束-->
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
		});
	});
	//提交表单的方法
	function updateResetPassword(form1){
		//提交表单
		 if($('#userForm').form('validate')){
			if($("#aliases").val()!=$("#password").val()){
				showMsg("error","两次输入的密码不一致");
				return false;
			}
			if($("#answer").val()!=$("#answerRepeat").val()){
				showMsg("error","两次输入的提示问题回答不一致");
				return false;
			}
			var params=jQuery('#userForm').serialize();
			var problemId=$("#passwordProblem").combobox('getValue');
			if(problemId){
				params+="&problemId="+problemId;
			}else{
				problemName=$("#passwordProblem").combobox('getText');
				params+="&problemName="+problemName;
			}
			jQuery.post("${cxt}/user/ajax/updateResetPassword.action",params,function call(data){
				if(data.status){
					showMsg("info","用户密码修改成功");
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","用户密码修改失败!");
					}
				}
			},'json');
		}	
		return false;
	}
	
	function setAnswer(newValue){
		var data=$("#passwordProblem").combobox("getData");
		if(data && data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].id==newValue){
					if(data[i].answer){
						$("#answer").textbox("setText",data[i].answer);
						$("#answerRepeat").textbox("setText",data[i].answer);
					}
					return;
				}
			}
		};
	}
		
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>

