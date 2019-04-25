<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加功能点--${system_name }</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge " />
<meta content="签到系统" name="keywords" />
<%@ include file="../common/common.jsp"%>
</head> 
<body>
<form action="#" method="post" name="funForm" id="funForm" onsubmit="return savefunctionPoint(this);"> 
	<div class="e-tabbox main-expand-form">
	    <div class="formBoxItem">
	      	<label class="formBoxItemL" for="functionType"><span>*</span>功能点分类名称：</label>
	      		<div class="formBoxItemR">
	       	<input type="text" class="text" id="name" onblur="checkHasFunName();"
			check="empty" min="1" max="32" maxlength="32"  msg="功能点名称" msgId="functionNameMsg" name="name" /> 
	   		<span class="advert" id="functionNameMsg"></span>
	  	</div>
	</div>
	<div class="formBoxItem">
	  <label class="formBoxItemL">功能点类型：</label>
	  <div class="formBoxItemR">
	   <span>分类</span>
	   <input type="hidden" class="text" name="functionPointType" value="${typeId}"/>
	   <span class="advert" id="functionCodeMsg"></span>
	  </div>
	</div>
	<div class="formBoxItem">
	  	<label  class="formBoxItemL" for="remark"><span>*</span>描述：</label>
	  	 <div class="formBoxItemR">
	  		<input type="text" class="text" id="remark"  check="empty" min="1" max="512" maxlength="512"  msg="描述" msgId="remarkMsg" name="remark" />
	  	<span class="advert" id="remarkMsg"></span>
	  	</div>
	</div>
	</div>
	<div class="e-tab-btnbox">
	  <input type="submit" value="确定" class="btn mr10" /> 
	  <input type="button" value="取消" class="btn cancel" onclick="cancelWindow()" />
	</div>
</form>
</body>
  
 <script  type="text/javascript">
  window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	};

 jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
	});
		//验证功能点名是否已经存在的方法
		function checkHasFunName(){
			//获得功能点名称
			var name = jQuery("#name").val();
			jQuery("#functionNameMsg").html("");
			//检查是否存在
			if(name!=""){
				jQuery.post("${cxt}/functionPoint/ajax/findHasAddName.action", "name="+name, function call(result){
					if(!result.status){
						jQuery("#functionNameMsg").html(result.message); 
						jQuery("#name").focus();
					}		
				},'json');
			} 
		}
		//提交表单的方法
		function savefunctionPoint(form1){
			try{
				//提交表单
				if($("#funForm").form("validate")){
					var params=jQuery('#funForm').serialize();
					jQuery.post("${cxt}/functionPoint/ajax/add.action",params,function call(result){
						if(result.status){
							parent.callbackAdd(result.data.fun,result.data.parentId);
							cancelWindow();
						}else{
							if(result.message){
								showMsg("error",result.message);
							}else{
								showMsg("error","字典数据失败!");
							}
						}	
					},'json');
				}	
			}catch(e){}
			return false;
		}
		
		function cancelWindow(){
			top.jQuery("#win").window("close");
		}
</script>
</html>
