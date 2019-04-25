<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
 	<title>签到系统--</title> 
 	<link type="text/css" href="${cxt}/css/login.css" rel="stylesheet" />
	<script type="text/javascript" src="${cxt}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${cxt}/js/jqueryWaitingBar.js"></script>
</head>
<body>
<div class="mian-login-container">
	<div class="mian-login-top">
    	<img src="${cxt}/css/img/loginbg.jpg" width="980" height="275" />
    </div>
	<div class="mian-login-bottom">
    	<div class="left">
		<form action="" onsubmit="return usersLogin();" name="userForm" id="userForm" method="post"> 
        	<div class="mian-login-textfield mb15">
            	<span class="use"></span><input style="width:362px;" type="text" name="loginName" id="loginName" class="inp" placeholder="请输入用户名" />
            </div>
            <div class="mian-login-textfield">
            	<span class="pw"></span><input style="width:362px;" type="password" name="password" id="password"  class="inp" placeholder="请输入用密码" />
            </div>
           
            <div class="mian-login-btnbox">
	           	 <div class="f-pw">
	             	<div class="messagebox"><div class="message ac" id="checkMessage">${message }</div></div>
	            	<a href="${cxt}/person/gotoFindPassword.action" >忘记密码？</a>
	            </div>
                <!-- <a href="javascript:;">登录</a> -->
                <input type="submit" class="login-btn" value="登 录"/>
            </div>
		</form>
        </div>
        <div class="right">
        	<div class="mian-login-mod mb15">
            	<h2 class="hd">随便看看？</h2>
                <div class="bd">
                	<a href="javascript:;">查看学习目录</a>
                </div>
            </div>
            <div class="mian-login-mod">
            	<h2 class="hd">帮助问题？</h2>
                <div class="bd">
                	<p>地址：北京市海淀区车公庄西路乙19号华通大厦B座北塔12层1224</p>
                    <p>联系电话：010-88355586</p>
                    <p>传真：010-88355586-802</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!--con结束-->
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->
</body>
</html>
