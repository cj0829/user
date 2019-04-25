<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
  <title>修改密码--${system_name }</title>   
</head>
<body>  

<div class="mainWindow">
      <div class="mainTitle">
      	<h3>修改密码</h3>
      </div>
      <div class="mainContainer"> 
           <div class="formBox">
            <form action="#" method="post" name="userForm" id="userForm" onsubmit="return savePassword(this)"> 
	            <div class="formBoxItem">
		              <label for="oldPassword"><span>*</span>旧密码：</label>
		              <input type="text" class="text" id="oldPassword" check="empty" min="6" max="18" maxlength="18"  msg="旧密码" msgId="oldPasswordMsg" name="oldPassword" /> 
		              <span class="advert" id="oldPasswordMsg"></span>
	            </div>
	            <div class="formBoxItem">
		              <label for="password"><span>*</span>新密码：</label>
		              <input type="text" class="text" id="password" check="empty" min="6" max="18" maxlength="18"  msg="新密码" msgId="passwordMsg" name="user.password" />
		              <span class="advert" id="passwordMsg"></span>
	            </div> 
	            <div class="formBoxItem formBoxButton">
		              <input type="submit" value="确定" class="btn" /> 
		              <input type="button" value="取消" class="btn" onclick="cancelWindow()" />
	            </div>
            </form>
        </div>
      </div>
</div> 
 <script  type="text/javascript">    
		//提交表单的方法
		function savePassword(form1){ 
			//验证表单内容
			if(checkForm(form1)){ 
				var params=jQuery('#userForm').serialize();
				jQuery.post("person!updatePassword.action?ajaxFlag=1",params,function call(result){
					if(result.status){
						top.showMsg("success","编辑成功");
						top.window.location.href="${cxt}/person/logout.action"; 
					}else{
						top.showMsg("error",data.message);
					}
				},'json');
			}	
			return false;
		}
		
		function cancelWindow(){
		   top.$.dialog.list['batchSendMsgDialog'].close();
		}
</script>
</body>
</html>