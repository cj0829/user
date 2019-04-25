<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${jsPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
<script type="text/javascript" src="${jsPath}/js/common.js"></script>
<script type="text/javascript" src="${jsPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${jsPath}/js/jquery.extend.validatebox.js"></script>
<link href="${jsPath}/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="${jsPath}/css/exam/dark-transparent/expandWindow.css" rel="stylesheet" />
<link type="text/css" href="${cxt}/css/login.css" rel="stylesheet" />

<title>个人中心--${system_name}</title>
</head>
<body>
	<div class="passwordContainer">
		<div class="header">
			<div class="login">
				<span></span><a href="${cxt}/login.jsp">登录</a>
			</div>
		</div>
		<div class="passwordcon">
			<div class="p-main">
				<div class="h_0">找回密码</div>
				<form action="#" method="get" name="userForm" id="userForm"
					onsubmit="return findPassword(this)">
					<div class="info mt10">
						<div class="textfield mb15">
							<span class="use"></span><input style="width:334px;" type="text"
								name="userLoginName" placeholder="请输入户名" class="inp" />
						</div>
						<div class="textfield mb15">
							<span class="question"></span><input style="width:334px;"
								type="text" name="question" placeholder="请输入问题" class="inp" />
						</div>
						<div class="textfield">
							<span class="answer"></span><input style="width:334px;"
								type="text" name="answer" placeholder="请输入用答案" class="inp" />
						</div>
						<div class="tjbtnbox">
							<button class="login-btn " type="submit">找寻密码</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/include/footer.jsp"%>

	<script type="text/javascript">
		var waitingbar;
		//提交表单的方法
		function findPassword(form1) {
			waitingbar = jQuery.getWaitingbar("邮件发送中，请等待...", true,"${cxt}");
			//提交表单
			if ($('#userForm').form('validate')) {
				var params = jQuery('#userForm').serialize();
				jQuery.post("${cxt}/userPasswordProblem/ajax/findPassword.action", params,
						function call(data) {
							if (data.status) {
								showMsg("info", "用户密码以找到，请您查询您的邮箱");
							} else {
								if (data.message) {
									showMsg("error", data.message);
								} else {
									showMsg("error", "用户密码找寻失败!");
								}
							}
							waitingbar.hide();
						}, 'json');
			}
			return false;
		}
	</script>
</body>
</html>