<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>武汉大学-测绘地理信息虚拟仿真实验教学中心</title>
    <meta name="keywords" content="武汉大学-测绘地理信息虚拟仿真实验教学中心">
    <meta name="description" content="武汉大学-测绘地理信息虚拟仿真实验教学中心">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=2.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="${jsPath}/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath }/js/jquery.min.js" type="text/javascript"></script>
	<script src="${jsPath }/js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/js/common.js" type="text/javascript"></script>
  </head>
  
  <body>
    <div class="wh_top area">
		<ul>
			<li class="left fl">
				<p class="txt">测绘地理信息虚拟仿真实验教学平台</p>
			</li>
			<!-- <li class="right fr">
				<span></span>
			</li> -->
		</ul>
	</div>
	<div class="area ovh whregisterCon">
		<div class="reg_conbd">
			<form name="regUserForm" id="regUserForm" action="#" onsubmit="return regUser(this);">
			<ul class="reg_list">
				<li class="regli">
					<label class="lab">登录名：</label>
					<div class="w_r"><input name="loginName" type="text" maxlength="10" class="inp"></div>
				</li>
				<li class="regli">
					<label class="lab">姓名：</label>
					<div class="w_r"><input name="name" type="text" maxlength="10" class="inp"></div>
				</li>
				<li class="regli">
					<label class="lab">组织机构：</label>
					<div class="w_r">
					<div class="wh-expand-tree">
						<input name="agencies" id="agencies" class="easyui-combotree inp" data-options="url:'${cxt}/agencies/ajax/findDropDownTree.action'" type="text" maxlength="10">
					</div>
					</div>
				</li>
				<li class="regli">
					<label class="lab">邮箱：</label>
					<div class="w_r"><input name="email" class="easyui-validatebox inp" data-options="validType:'email'" type="text" maxlength="20"></div>
				</li>
				<!-- <li class="regli">
					<label class="lab">QQ：</label>
					<div class="w_r"><input type="text" maxlength="10" class="inp"></div>
				</li> -->
				<li class="regli">
					<label class="lab"></label>
					<input type="submit" maxlength="10" class="regbtn" value="立即注册">
				</li>
			</ul>
			</form>
		</div>
		
	</div>
	<script type="text/javascript">
		//保存
		function regUser(form){
			if($("#regUserForm").form("validate")){
				var params=jQuery('#regUserForm').serialize();
				$.post("${cxt}/userRegister/ajax/regUser.action",params,function call(data){
					if(data.status){
						$.messager.alert("消息确认","添加成功，等待审批","",function(){
					    	window.location="${cxt}/login.jsp";
						});    
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加失败!");
						}
					}
				},'json');
			}
			return false;
		}

	</script>
  </body>
</html>
