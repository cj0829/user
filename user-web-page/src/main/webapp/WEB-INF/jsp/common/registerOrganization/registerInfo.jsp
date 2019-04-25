<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<title>用户注册--${system_name }</title>  
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <style>
	.f-left{
		float:left;
		width:20%;
		margin-left:20px;
	}
 </style>
</head>
<body>  
<div class="wrapper">
    <!-- middle begin -->
    <div class="middle">
        <!-- left_menu begin -->
	    <div class="left_menu">
	    </div>
	    <!-- left_menu end -->
	    <!-- right_content begin -->
	    <div class="right_content"> 
	   	 	<div class="list_page">
	   	 		<form action="#" method="post" name="registerForm" id="registerForm" onsubmit="return registerUser(this);">
		   	 	<div id="searchForm" class="list_search">
				    <div class="formBox">
				   	  <div class="formBoxItem">
						说明：新机构注册成功,用户名和密码已发生到邮箱${register.email}中
			           </div>
				       <div class="formBoxItem">
			              <label for="loginName">用户名：</label>
			              <label for="loginName">${register.loginName}</label>
			            </div>
			            <div class="formBoxItem">
			              <label for="password">密码：</label>
			              <label for="password">${register.password}</label>
			            </div>
				    </div>
				</div>
				<div id="com_button" buttonType="返回" buttonCode="0203001" method="callbackRole()"></div> 
			</form>
			</div>
		</div>
		<!-- right_content end -->
    </div>
    <!-- middle end --> 
    
</div>
<%@ include file="/include/footer.jsp"%>  
<script  type="text/javascript">  
	pmt_com_buttonId="com_button"; 
	jQuery(document).ready(function(){
		pmt_create_button(); 	
	});
	
	//返回角色列表页
	function callbackRole(user){
		window.location.href="preList.action";
	};
</script>
</body>
</html>