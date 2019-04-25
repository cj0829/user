<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 
 <title>编辑机构参数--${system_name }</title>  
</head>
<body>   
<div class="e-tabbox">
<div class="mainWindow">
      <div class="mainTitle">
      	<h3>编辑机构参数</h3>
      </div>
      <div class="mainContainer">
      	<div class="formBox">
            <form action="#" method="post" name="parameterForm" id="parameterForm" onsubmit="return updateParameter(this)"> 
	            <div class="formBoxItem">
	              <label for="name">机构参数名：</label>${parameter.name}
	            </div>
	            <div class="formBoxItem">
	              <label for="parameterValue"><span>*</span>机构参数值：</label>
	              <input type="text" class="text" id="parameterValue" check="empty" isEmpety="isEmpety" 
	              min="1" max="512" maxlength="512"  msg="机构参数值" msgId="parameterValueMsg" name="value" value="${parameter.parameterValue}" />
	              <span class="advert" id="parameterValueMsg"></span>
	            </div>
	            
	            <input type="hidden" id="parameterid" name="parameterId" value="${parameter.id}"/>
	            <input type="hidden" id="organizationId" name="organizationId" value="${parameter.organizationId}"/>
            </form>
        </div>
      </div>
    </div> 
    </div>
    <div class="e-tab-btnbox ">
	              <input type="submit" value="确定" class="btn mr10" /> 
	              <input type="button" value="取消" class="btn cancel" onclick="cancelWindow()" />
	            </div>
 <script  type="text/javascript">   
 window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);}
 jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
	});
		//提交表单的方法
		function updateParameter(form1){
			//提交表单
			var params=jQuery('#parameterForm').serialize();
			jQuery.post("${cxt}/registerOrganization/ajax/updateOrganizationParameter.action",params,function call(result){
				if(result.status){
					cancelWindow();
					top.showMsg("success","机构参数修改成功");
					top.callbackPage();
				}else{
					if(result.message){
						showMsg("error",result.message);
					}else{
						showMsg("error","字典数据失败!");
					}
				}
				
			},'json');
			return false;
		}
		function cancelWindow(){
			top.jQuery("#win").window("close");
		}
</script>
</body>
</html>