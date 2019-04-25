<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<% 
	String basePath = request.getScheme()+"://"+request.getServerName();  
%> 
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge " />
<meta content="会议签到管理系统" name="keywords"/>
<style type="">

inp-cl{
::-ms-reveal
}
</style>
<title>会议签到管理系统</title>
<link type="text/css" href="${jsPath}/css/login.css" rel="stylesheet" />
<script type="text/javascript" src="${jsPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
<script src="${jsPath}/js/common.js" type="text/javascript"></script>
</head>

<body>
<!--con开始-->
<div class="mian-login-container">
	<div class="mian-login-top">
    	<img src="${jsPath}/css/img/loginbg.jpg" width="980" height="275" />
    </div>
	<div class="mian-login-bottom">
    	<div class="left">
		<form action="" onsubmit="return usersLogin();" name="userForm" id="userForm" method="post"> 
        	<div class="mian-login-textfield mb15">
            	<em class="use"></em><input style="width:362px;" type="text" name="loginName" id="loginName" class="inp"/>
            </div>
            <div class="mian-login-textfield">
            	<em class="pw"></em><input style="width:362px;" type="password" name="password" id="password"  class="inp inp-c" />
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
            	<h1 class="hd">会议签到管理系统 </h1>
               <!--  <div class="bd">
                	<a href="javascript:;">查看学习目录</a> 
                </div> -->
            </div>
            <div class="mian-login-mod">
            	<!-- <h2 class="hd">帮助问题？</h2> -->
                <div class="bd">
                	<p>地址：北京市朝阳区望京优乐汇E</p>
                    <p>联系电话：400-610-9811</p>
                
                </div>
            </div>
        </div>
    </div>
</div>
<!--con结束-->
<!--版权开始-->
<div class="footer">
	<p>Copyright© 天脉科技 All Rights Reserved</p>
</div>
<!--版权结束-->
<script type="text/javascript">
	//登录
	function usersLogin(){  
	    jQuery("#checkMessage").html("");
	    
		if(jQuery.trim(jQuery("#loginName").val())==""){
		     jQuery("#checkMessage").html("用户名不能为空！");
		     return false;
		} 
		if(jQuery.trim(jQuery("#password").val())==""){
		     jQuery("#checkMessage").html("密码不能为空！");
		     return false;
		} 
		WaitingBar.getWaitingbar("login","系统登录中，请等待...","${jsPath}").show();
		var params=jQuery('#userForm').serialize();   
		jQuery.post("${cxt}/login",params,function call(data){
			try{
				if(data.status==0){
					jQuery("#checkMessage").html("用户名或密码错误！");
					WaitingBar.getWaitingbar("login").hide();
				}else if(data.status==2){
					jQuery("#checkMessage").html("用户名没有激活或被禁用！");
					WaitingBar.getWaitingbar("login").hide();
				}else if(data.status==1){
					if(data.forwardUrl){
						window.location.href="${cxt}"+data.forwardUrl;
					}else if('logout'=='${code}'){
						window.location.href="${cxt}/person/preCenter.action";
					}else{
						window.location.href="${cxt}/person/preCenter.action";
					}
				}else{
					jQuery("#checkMessage").html(data.message);
					WaitingBar.getWaitingbar("login").hide();
				}
			}catch(e){
				WaitingBar.getWaitingbar("login").hide();	
			}
		},'json'); 
		return false;
	}
	jQuery(document).ready(function(){ 	 
		 document.userForm.reset(); 
	});
 
</script>
</body>
</html>